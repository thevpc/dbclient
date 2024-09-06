/**
 * ====================================================================
 *             DBClient yet another Jdbc client tool
 *
 * DBClient is a new Open Source Tool for connecting to jdbc
 * compliant relational databases. Specific extensions will take care of
 * each RDBMS implementation.
 *
 * Copyright (C) 2006-2008 Taha BEN SALAH
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 * ====================================================================
 */

package net.thevpc.dbclient.plugin.system.sql.objects;

import net.thevpc.dbclient.api.sql.DBCConnection;
import net.thevpc.dbclient.api.sql.objects.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 13 juil. 2005 14:33:48
 */
public class DBSequenceImpl extends DefaultDBObject implements DBSequence {
    private LinkedHashMap<String, DBTableColumn> allFields = new LinkedHashMap<String, DBTableColumn>();
    private ArrayList<DBTableColumn> allPk = new ArrayList<DBTableColumn>();
    public boolean allPkLoaded = false;
    public String typeName;
    public SQLObjectTypes type;

    public void init(DBCConnection session, ResultSet rs) throws SQLException {
        super.init(session, "Tree.Table." + rs.getString("TABLE_TYPE"), rs.getString("TABLE_CAT"), rs.getString("TABLE_SCHEM"), rs.getString("TABLE_TYPE"), rs.getString("TABLE_NAME"), false);
        setSystemObject(session.isSystemType(typeName));
        type = session.getTypeByNativeName(typeName);
    }


    public String getTypeName() {
        return typeName;
    }

    public DBTableFolder getTableType() {
        return (DBTableFolder) getParentObject();
    }

    public String getPrefix() {
        StringBuilder sb = new StringBuilder();
        if (getCatalogName() != null && getCatalogName().length() > 0) {
            sb.append(getCatalogName()).append(".");
        }
        if (getSchemaName() != null && getSchemaName().length() > 0) {
            sb.append(getSchemaName()).append(".");
        }
        if (sb.length() > 0 && sb.charAt(sb.length() - 1) == '.') {
            sb.delete(sb.length() - 1, sb.length());
        }
        return sb.toString();
    }

    @Override
    protected void loadChildren() throws SQLException {
        allFields.clear();
        allPk.clear();
        allPkLoaded = false;

        ResultSet rs = getConnection().getMetaData().getColumns(
                getCatalogName(),
                getSchemaName(),
                getName(),
                null);

        while (rs.next()) {
            DBTableColumn item = getConnection().getFactory().newInstance(DBTableColumn.class);
            item.init(getConnection(), rs);
            DBTableColumn tableColumn = item;
//            tableColumn.setTable(this);
            allFields.put(tableColumn.getName(), tableColumn);
            addChild(tableColumn);
        }
        rs.close();
        for (DBTableColumn tableColumnNode : getColumns()) {
            if (tableColumnNode.isPk()) {
                allPk.add(tableColumnNode);
            }
        }
//        rs = getSession().getConnection().getMetaData().getPrimaryKeys(
//                catalogName,
//                schemaName,
//                getActionName());
//        while (rs.next()) {
//            String colName = rs.getString("COLUMN_NAME");
//            int keyseq = rs.getInt("KEY_SEQ");
//            TableColumn fi = ((TableColumn) allFields.get(colName));
//            fi.setPkSeq(keyseq);
//            allPk.add(fi);
//        }
//        rs.close();
        allPkLoaded = true;
    }


    public DBTableColumn getColumn(String name) {
        ensureLoading();
        DBTableColumn col = allFields.get(name);
        if (col == null) {
            throw new NoSuchElementException("Unknown Column \"" + name + "\" in Table \"" + getName() + "\"");
        }
        return col;
    }

    public DBTableColumn[] getColumns() {
        ensureLoading();
        Collection<DBTableColumn> c = allFields.values();
        return c.toArray(new DBTableColumn[c.size()]);
    }

    public DBTableColumn[] getPrimaryColumns() {
        ensureLoading();
        return allPk.toArray(new DBTableColumn[allPk.size()]);
    }

    public DBTableColumn[] getUpdatableColumns() {
        ensureLoading();
        ArrayList<DBTableColumn> found = new ArrayList<DBTableColumn>();
        TreeSet<String> pks = new TreeSet<String>();
        for (DBTableColumn tableColumnNode : allPk) {
            pks.add(tableColumnNode.getName());
        }
        for (DBTableColumn explorerNode : getColumns()) {
            if (!pks.contains(explorerNode.getName())) {
                found.add(explorerNode);
            }
        }
        return found.toArray(new DBTableColumn[found.size()]);
    }

    //TODO

    public DBTableColumn[] getInsertableMandatoryColumns() {
        ensureLoading();
        ArrayList<DBTableColumn> found = new ArrayList<DBTableColumn>();
//        TreeSet<String> pks = new TreeSet<String>();
//        for (TableColumn tableColumnNode : allPk) {
//            pks.add(tableColumnNode.getActionName());
//        }
        for (DBTableColumn explorerNode : getColumns()) {
//            if (!pks.contains(explorerNode.getActionName())) {
            found.add(explorerNode);
//            }
        }
        return found.toArray(new DBTableColumn[found.size()]);
    }
    //TODO

    public DBTableColumn[] getInsertableColumns() {
        ensureLoading();
        ArrayList<DBTableColumn> found = new ArrayList<DBTableColumn>();
//        TreeSet<String> pks = new TreeSet<String>();
//        for (TableColumn tableColumnNode : allPk) {
//            pks.add(tableColumnNode.getActionName());
//        }
        for (DBTableColumn explorerNode : getColumns()) {
//            if (!pks.contains(explorerNode.getActionName())) {
            found.add(explorerNode);
//            }
        }
        return found.toArray(new DBTableColumn[found.size()]);
    }

    public DBTableColumn[] getNonNullUpdatableColumns() {
        ensureLoading();
        ArrayList<DBTableColumn> found = new ArrayList<DBTableColumn>();
        TreeSet<String> pks = new TreeSet<String>();
        for (DBTableColumn tableColumnNode : allPk) {
            pks.add(tableColumnNode.getName());
        }
        for (DBTableColumn explorerNode : getColumns()) {
            if (!pks.contains(explorerNode.getName()) && explorerNode.isNullable()) {
                found.add(explorerNode);
            }
        }
        return found.toArray(new DBTableColumn[found.size()]);
    }

    public SQLObjectTypes getType() {
        return type;
    }

    @Override
    public String getStringPath() {
        DBObject parent = getParentObject();
        if (parent != null) {
            return super.getStringPath();///AllCatalogs//DBCLIENT/AllProcedures
        }
        return "/AllCatalogs/" + getCatalogName() + "/" + getSchemaName() + "/" + getTypeName() + "/" + getName();
    }

    public String getDropSQL() {
        return getFullName();
    }
}
