package net.thevpc.dbclient.plugin.system.sql;

import net.thevpc.dbclient.api.pluginmanager.DBCAbstractPluggable;
import net.thevpc.common.prs.reflect.ClassFilter;
import net.thevpc.dbclient.api.sql.DBCConnection;
import net.thevpc.dbclient.api.sql.DBCObjectFinder;
import net.thevpc.dbclient.api.sql.FindMonitor;
import net.thevpc.dbclient.api.sql.objects.*;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

/**
 * Created by IntelliJ IDEA.
 * User: vpc
 * Date: 14 févr. 2009
 * Time: 21:37:35
 * To change this template use File | Settings | File Templates.
 */
public class DBCObjectFinderImpl extends DBCAbstractPluggable implements DBCObjectFinder {
    private DBCConnection connection;
    private Boolean storesLowerCaseIdentifiers;
    private Boolean storesUpperCaseIdentifiers;

    public void init(DBCConnection connection) throws SQLException {
        this.connection = connection;
        storesLowerCaseIdentifiers = null;
        storesUpperCaseIdentifiers = null;
    }

    public DBObject[] find(String catalogName, String schemaName, String parentName, String name, DBObject[] contextParents, ClassFilter nodeClassFilter, FindMonitor findMonitor) throws SQLException {
        if (name == null || name.length() == 0) {
            return new DBObject[0];
        }
        if (findMonitor == null) {
            findMonitor = new FindMonitor();
        }
        name = validateName(name);
        catalogName = validateName(catalogName);
        schemaName = validateName(schemaName);
        parentName = validateName(parentName);
        if (contextParents == null) {
            contextParents = new DBObject[]{null};
        }
        //
        ArrayList<String> fullPath = new ArrayList<String>();
        if (catalogName != null && catalogName.length() == 0) {
            catalogName = null;
        }
        if (catalogName != null) {
            fullPath.add(catalogName);
        }
        if (schemaName != null) {
            fullPath.add(schemaName);
        }
        if (parentName != null && parentName.length() > 0) {
            fullPath.add(parentName);
        }
        fullPath.addAll(Arrays.asList(name.split("\\.")));
        DBObject[] dbObjects = fillSearch(fullPath.toArray(new String[fullPath.size()]), contextParents, findMonitor);
        if (nodeClassFilter == null) {
            return dbObjects;
        }
        ArrayList<DBObject> needed = new ArrayList<DBObject>();
        for (DBObject dbObject : dbObjects) {
            if (dbObject!=null && nodeClassFilter.accept(dbObject.getClass())) {
                needed.add(dbObject);
            }
        }
        return needed.toArray(new DBObject[needed.size()]);
    }

    public DBObject[] fillSearch(String[] fullnames, DBObject[] contextParents, FindMonitor findMonitor) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        ArrayList<DBObject> parents = new ArrayList<DBObject>();
        StringFilter f;
        if (fullnames.length == 0) {
            return new DBObject[0];
        }
        String cur = fullnames[0];
        if (cur.endsWith("%")) {
            f = new StringFilterStart(cur.substring(0, cur.length() - 1));
        } else {
            f = new StringFilterEquals(cur);
        }
        if (contextParents == null) {
            contextParents = new DBObject[]{null};
        }

        for (DBObject contextParent : contextParents) {
            if (contextParent == null) {
                // keywords
                for (String s : connection.getSQLKeywords()) {
                    findMonitor.checkStop();
                    if (f.accept(s)) {
                        DBKeyword item = connection.getFactory().newInstance(DBKeyword.class);
                        item.init(connection, s);
                        parents.add(item);
                    }
                }
                //functions
                for (DBFunction s : connection.getAllFunctions()) {
                    findMonitor.checkStop();
                    if (f.accept(s.getName())) {
                        parents.add(s);
                    }
                }
                // datatypes
                for (DBDatatype s : connection.getDatatypes()) {
                    findMonitor.checkStop();
                    if (f.accept(s.getName())) {
                        parents.add(s);
                    }
                }

                //catalogs
                findMonitor.checkStop();
                ResultSet rs;
                String catalogTerm = "Database";
                try {
                    catalogTerm = metaData.getCatalogTerm();
                } catch (SQLException e) {
                    //
                }
                rs = metaData.getCatalogs();
                while (rs.next()) {
                    findMonitor.checkStop();
                    DBCatalog item = connection.getFactory().newInstance(DBCatalog.class);
                    item.init(connection, rs);
                    String cat = item.getName();
                    if (f.accept(cat)) {
                        parents.add(item);
                    }
                }
                rs.close();

            }
            if (contextParent == null || contextParent instanceof DBCatalog) {
                //schemas
                findMonitor.checkStop();
                ResultSet rs;
                rs = metaData.getSchemas();
                while (rs.next()) {
                    findMonitor.checkStop();
                    DBSchema item = connection.getFactory().newInstance(DBSchema.class);
                    item.init(connection, rs, null);
                    String sch = item.getName();
                    if (f.accept(sch)) {
                        parents.add(item);
                    }
                }
                rs.close();
            }
            if (contextParent == null || contextParent instanceof DBCatalog || contextParent instanceof DBSchema) {
                //tables
                findMonitor.checkStop();
                ResultSet rs;
                String cat = null;
                String schem = null;
                if (contextParent instanceof DBCatalog) {
                    cat = contextParent.getName();
                }
                if (contextParent instanceof DBSchema) {
                    cat = (contextParent).getCatalogName();
                    schem = contextParent.getName();
                }
                rs = metaData.getTables(validateName(cat), validateName(schem), validateName(cur), null);
                while (rs.next()) {
                    findMonitor.checkStop();
                    DBTable item = connection.getFactory().newInstance(DBTable.class);
                    item.init(connection, rs);
                    if (f.accept(item.getName())) {
                        parents.add(item);
                    }
                }
                rs.close();
            }
            if (contextParent == null || contextParent instanceof DBCatalog || contextParent instanceof DBSchema) {
                //procedures
                findMonitor.checkStop();
                ResultSet rs;
                String cat = null;
                String schem = null;
                if (contextParent instanceof DBCatalog) {
                    cat = contextParent.getName();
                }
                if (contextParent instanceof DBSchema) {
                    cat = (contextParent).getCatalogName();
                    schem = contextParent.getName();
                }
                rs = metaData.getProcedures(validateName(cat), validateName(schem), validateName(cur));
                while (rs.next()) {
                    findMonitor.checkStop();
                    DBProcedure item = connection.getFactory().newInstance(DBProcedure.class);
                    item.init(connection, rs);
                    if (f.accept(item.getName())) {
                        parents.add(item);
                    }
                }
                rs.close();
            }
            if (contextParent instanceof DBTable) {
                //procedures
                findMonitor.checkStop();
                ResultSet rs;
                String cat = contextParent.getCatalogName();
                String schem = contextParent.getSchemaName();
                String tab = contextParent.getName();
                rs = metaData.getColumns(validateName(cat), validateName(schem), validateName(tab), cur);
                while (rs.next()) {
                    findMonitor.checkStop();
                    DBTableColumn item = connection.getFactory().newInstance(DBTableColumn.class);
                    item.init(connection, rs);
                    if (f.accept(item.getName())) {
                        parents.add(item);
                    }
                }
                rs.close();
            }
            if (contextParent == null) {
                ResultSet rs = connection.getMetaData().getTableTypes();
                TreeSet<String> seenTypes = new TreeSet<String>();
                while (rs.next()) {
                    String nativeName = rs.getString(1);
                    //timmed because some drivers add some trailing spaces!
                    if (nativeName == null) {
                        nativeName = "";
                    }
                    nativeName = nativeName.trim();

                    if (!seenTypes.contains(nativeName)) {
                        seenTypes.add(nativeName);
                        if (f.accept(nativeName)) {
                            SQLObjectTypes sqlObjectTypes = connection.getTypeByNativeName(nativeName);
                            if (sqlObjectTypes == SQLObjectTypes.TABLE || sqlObjectTypes == SQLObjectTypes.VIEW) {
                                DBTableFolder folder = connection.getFactory().newInstance(DBTableFolder.class);
                                folder.init(connection, nativeName, null, null);
                                parents.add(folder);
                            } else if (sqlObjectTypes == SQLObjectTypes.TABLE_INDEX) {
                                DBTableIndexFolder folder = connection.getFactory().newInstance(DBTableIndexFolder.class);
                                folder.init(connection, nativeName, null, null);
                                parents.add(folder);
                            } else if (sqlObjectTypes == SQLObjectTypes.SEQUENCE) {
                                DBSequenceFolderAsTable folder = connection.getFactory().newInstance(DBSequenceFolderAsTable.class);
                                folder.init(connection, rs, null, null);
                                parents.add(folder);
                            }
                        }
                    }
                }
                rs.close();
            }
        }
        String[] remain = new String[fullnames.length - 1];
        System.arraycopy(fullnames, 1, remain, 0, remain.length);
        DBObject[] ret = parents.toArray(new DBObject[parents.size()]);
        if (remain.length == 0) {
            return ret;
        } else {
            return fillSearch(remain, ret, findMonitor);
        }
    }

    protected String validateName(String name) throws SQLException {
        if (name == null || name.length() == 0) {
            return name;
        }
        if (storesLowerCaseIdentifiers == null) {
            try {
                DatabaseMetaData metaData = connection.getMetaData();
                storesLowerCaseIdentifiers = metaData.storesLowerCaseIdentifiers();
            } catch (SQLException e) {
                if (storesLowerCaseIdentifiers == null) {
                    storesLowerCaseIdentifiers = false;
                }
            }
        }
        if (storesUpperCaseIdentifiers == null) {
            try {
                DatabaseMetaData metaData = connection.getMetaData();
                storesUpperCaseIdentifiers = metaData.storesUpperCaseIdentifiers();
            } catch (SQLException e) {
                if (storesUpperCaseIdentifiers == null) {
                    storesUpperCaseIdentifiers = false;
                }
            }
        }
        if (storesUpperCaseIdentifiers) {
            name = name.toUpperCase();
        } else if (storesLowerCaseIdentifiers) {
            name = name.toLowerCase();
        }
        return name;
    }


    private static class StringFilterStart implements StringFilter {
        private String expression;

        public StringFilterStart(String exp) {
            this.expression = exp.toUpperCase();
        }

        public boolean accept(String str) {
            return str != null && str.toUpperCase().startsWith(expression);
        }
    }

    private static class StringFilterEquals implements StringFilter {
        private String expression;

        public StringFilterEquals(String exp) {
            this.expression = exp.toLowerCase();
        }

        public boolean accept(String str) {
            return str != null && str.equalsIgnoreCase(expression);
        }
    }

    private static interface StringFilter {
        boolean accept(String str);
    }
}
