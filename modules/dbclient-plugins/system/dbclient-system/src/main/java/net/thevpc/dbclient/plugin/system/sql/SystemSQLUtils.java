package net.thevpc.dbclient.plugin.system.sql;

import java.util.logging.Level;

import net.thevpc.common.swing.util.SwingsStringUtils;
import net.thevpc.dbclient.api.sql.objects.DBObject;
import net.thevpc.dbclient.api.sql.objects.SQLObjectTypes;
import net.thevpc.dbclient.api.sql.parser.SQLParser;
import net.thevpc.dbclient.api.sql.parser.SQLStatement;
import net.thevpc.dbclient.api.sql.parser.SQLToken;
import net.thevpc.dbclient.api.sql.parser.SQLTokenGroup;
import net.thevpc.dbclient.plugin.system.sql.parser.DefaultSQLParser;

import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import net.thevpc.dbclient.api.sql.DBCConnection;
import net.thevpc.dbclient.api.sql.objects.DBCatalog;
import net.thevpc.dbclient.api.sql.objects.DBCatalogFolder;
import net.thevpc.dbclient.api.sql.objects.DBProcedure;
import net.thevpc.dbclient.api.sql.objects.DBProcedureColumn;
import net.thevpc.dbclient.api.sql.objects.DBProcedureFolder;
import net.thevpc.dbclient.api.sql.objects.DBSchema;
import net.thevpc.dbclient.api.sql.objects.DBServer;
import net.thevpc.dbclient.api.sql.objects.DBTable;
import net.thevpc.dbclient.api.sql.objects.DBTableColumn;
import net.thevpc.dbclient.api.sql.objects.DBTableColumnFolder;
import net.thevpc.dbclient.api.sql.objects.DBTableFolder;
import net.thevpc.dbclient.api.sql.objects.DBTableIndex;
import net.thevpc.dbclient.api.sql.objects.DBTableIndexFolder;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)  alias vpc
 * @creationtime 2009/08/12 11:56:07
 */
public final class SystemSQLUtils {

    private SystemSQLUtils() {
    }

    public static String getPreferredName(DBObject node, String schema, String catalog) {
        if (schema == null) {
            schema = "";
        }
        if (catalog == null) {
            catalog = "";
        }
        String schema0 = node.getSchemaName();
        String catalog0 = node.getCatalogName();
        if (schema0 == null) {
            schema0 = "";
        }
        if (catalog0 == null) {
            catalog0 = "";
        }
        String name = node.getName();
        if (schema0.equalsIgnoreCase(schema) && catalog0.equalsIgnoreCase(catalog)) {
            return name;
        }
        return node.getFullName();
    }

    public static String getFullName(String catalog, String schema, String parentName, String objectName, SQLObjectTypes objectType) {
        String[] x = new String[]{catalog, schema, parentName, objectName};
        StringBuilder sb = new StringBuilder();
        for (String s : x) {
            if (s != null && s.length() > 0) {
                if (sb.length() > 0) {
                    sb.append(".");
                }
                sb.append(s);
            }
        }
        return sb.toString();
    }

    public static class ScriptInfo {

        public int statementsCount;
        public boolean jstSqlEnabled;

        public ScriptInfo(boolean jstSqlEnabled, int statementsCount) {
            this.jstSqlEnabled = jstSqlEnabled;
            this.statementsCount = statementsCount;
        }

        public boolean isJstSqlEnabled() {
            return jstSqlEnabled;
        }

        public int getStatementsCount() {
            return statementsCount;
        }
    }

    public static ScriptInfo getScriptInfo(Reader reader, SQLParser parser) throws IOException {
        parser.setDocument(reader);
        SQLStatement s = null;
        int sc = 0;
        boolean je = false;
        while ((s = parser.readStatement()) != null) {
            if (!s.isEmpty()) {
                sc++;
                for (SQLToken t : s) {
                    if (t.getGroup() == SQLTokenGroup.SCRIPT) {
                        je = true;
                        break;
                    }
                }
            }
        }
        reader.close();
        return new ScriptInfo(je, sc);
    }

    public static String maskWildChars(String s) {
        return s;
        /*
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        switch (c) {
        case '%':
        case '_': {
        sb.append("\\").append(c);
        break;
        }
        default: {
        sb.append(c);
        }
        }
        }
        return sb.toString();
         */
    }

    public static String sqlToHtml(String sql) {
        StringBuffer sb = new StringBuffer();
        SQLToken token = null;
        DefaultSQLParser tok = new DefaultSQLParser(sql);
        while ((token = tok.readToken()) != null) {
            if (token.accept(SQLTokenGroup.OPEN_PAR)) {
                sb.append("<font color=blue>(</font>");
            } else if (token.accept(SQLTokenGroup.CLOSE_PAR)) {
                sb.append("<font color=blue>)</font>");
            } else if (token.accept(SQLTokenGroup.SEPARATOR)) {
                sb.append("<font color=blue>").append(SwingsStringUtils.textToHtml(token.getValue())).append("</font>");
            } else if (token.accept(SQLTokenGroup.STR)) {
                sb.append("<font color=green>").append(SwingsStringUtils.textToHtml(token.getValue())).append("</font>");
            } else if (token.accept(SQLTokenGroup.KEYWORD)) {
                sb.append("<font color=blue>").append(SwingsStringUtils.textToHtml(token.getValue())).append("</font>");
            } else if (token.accept(SQLTokenGroup.OPERATOR)) {
                sb.append("<font color=red>").append(SwingsStringUtils.textToHtml(token.getValue())).append("</font>");
            } else {
                sb.append(SwingsStringUtils.textToHtml(token.getValue()));
            }
        }
        return sb.toString();
    }

    public static String validateName(String name, Connection connection) throws SQLException {
        if (name == null || name.length() == 0) {
            return name;
        }

        Boolean storesLowerCaseIdentifiers = null;

        Boolean storesUpperCaseIdentifiers = null;

        //if (storesLowerCaseIdentifiers == null) {
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            storesLowerCaseIdentifiers = metaData.storesLowerCaseIdentifiers();
        } catch (SQLException e) {
            //if (storesLowerCaseIdentifiers == null) {
            storesLowerCaseIdentifiers = false;
            //}
        }
        //}
        //if (storesUpperCaseIdentifiers == null) {
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            storesUpperCaseIdentifiers = metaData.storesUpperCaseIdentifiers();
        } catch (SQLException e) {
            //if (storesUpperCaseIdentifiers == null) {
            storesUpperCaseIdentifiers = false;
            //}
        }
        //}
        if (storesUpperCaseIdentifiers) {
            name = name.toUpperCase();
        } else if (storesLowerCaseIdentifiers) {
            name = name.toLowerCase();
        }
        return name;
    }

    public static Boolean getBooleanIfSupported(String column, Boolean valueIfNotSupported, String key, ResultSet rs, DBCConnection connection) {
        key = key + "." + column + ".Supported";
        Boolean b = (Boolean) connection.getConnectionProperty(key, null);
        if (b == null) {
            try {
                boolean value = rs.getBoolean(column);
                connection.setConnectionProperty(key, true);
                return value;
            } catch (SQLException ex) {
                connection.getLoggerProvider().getLogger(SystemSQLUtils.class.getName()).log(Level.SEVERE, "Resultset.getBoolean failed. Next exceptions will be ignored", ex);
                connection.setConnectionProperty(key, false);
                return valueIfNotSupported;
            }
        } else if (b) {
            try {
                return rs.getBoolean(column);
            } catch (SQLException ex) {
                return valueIfNotSupported;
            }
        } else {
            return valueIfNotSupported;
        }
    }

    public static String getStringIfSupported(String column, String valueIfNotSupported, String key, ResultSet rs, DBCConnection connection) {
        key = key + "." + column + ".Supported";
        Boolean b = (Boolean) connection.getConnectionProperty(key, null);
        if (b == null) {
            try {
                String value = rs.getString(column);
                connection.setConnectionProperty(key, true);
                return value;
            } catch (SQLException ex) {
                connection.getLoggerProvider().getLogger(SystemSQLUtils.class.getName()).log(Level.SEVERE, "Resultset.getString failed. Next exceptions will be ignored", ex);
                connection.setConnectionProperty(key, false);
                return valueIfNotSupported;
            }
        } else if (b) {
            try {
                return rs.getString(column);
            } catch (SQLException ex) {
                return valueIfNotSupported;
            }
        } else {
            return valueIfNotSupported;
        }
    }

    public static int getIntIfSupported(String column, int valueIfNotSupported, String key, ResultSet rs, DBCConnection connection) {
        key = key + "." + column + ".Supported";
        Boolean b = (Boolean) connection.getConnectionProperty(key, null);
        if (b == null) {
            try {
                int value = rs.getInt(column);
                connection.setConnectionProperty(key, true);
                return value;
            } catch (SQLException ex) {
                connection.getLoggerProvider().getLogger(SystemSQLUtils.class.getName()).log(Level.SEVERE, "Resultset.getInt failed. Next exceptions will be ignored", ex);
                connection.setConnectionProperty(key, false);
                return valueIfNotSupported;
            }
        } else if (b) {
            try {
                return rs.getInt(column);
            } catch (SQLException ex) {
                return valueIfNotSupported;
            }
        } else {
            return valueIfNotSupported;
        }
    }

    public static boolean isCatalogDown(DBObject object) {
        return (object instanceof DBCatalog) || isSchemaDown(object);
    }

    public static boolean isSchemaDown(DBObject object) {
        return (object instanceof DBSchema) || isTableFolderDown(object);
    }

    public static boolean isTableFolderDown(DBObject object) {
        return (object instanceof DBTableFolder) || isTableDown(object);
    }

    public static boolean isTableDown(DBObject object) {
        return (object instanceof DBTable) || isTableColumnFolderDown(object);
    }
    
    public static boolean isTableColumnFolderDown(DBObject object) {
        return (object instanceof DBTableColumnFolder) || isTableColumn(object);
    }

    public static boolean isTableColumn(DBObject object) {
        return (object instanceof DBTableColumn);
    }

    public static boolean isTableColumnUp(DBObject object) {
        return (object instanceof DBTableColumn) || isTableColumnFolderUp(object);
    }

    public static boolean isTableColumnFolderUp(DBObject object) {
        return (object instanceof DBTableColumnFolder) || isTableUp(object);
    }

    public static boolean isTableIndexUp(DBObject object) {
        return (object instanceof DBTableIndex) || isTableIndexFolderUp(object);
    }

    public static boolean isTableIndexFolderUp(DBObject object) {
        return (object instanceof DBTableIndexFolder) || isTableUp(object);
    }

    public static boolean isTableUp(DBObject object) {
        return (object instanceof DBTable) || isTableFolderUp(object);
    }

    public static boolean isTableFolderUp(DBObject object) {
        return (object instanceof DBTableFolder) || isSchemaUp(object);
    }

    public static boolean isProcedureColumnUp(DBObject object) {
        return (object instanceof DBProcedureColumn) || isProcedureUp(object);
    }

    public static boolean isProcedureUp(DBObject object) {
        return (object instanceof DBProcedure) || isProcedureFolderUp(object);
    }

    public static boolean isProcedureFolderUp(DBObject object) {
        return (object instanceof DBProcedureFolder) || isSchemaUp(object);
    }

    public static boolean isSchemaUp(DBObject object) {
        return (object instanceof DBSchema) || isCatalogUp(object);
    }

    public static boolean isCatalogUp(DBObject object) {
        return (object instanceof DBCatalog) || isCatalogFolderUp(object);
    }

    public static boolean isCatalogFolderUp(DBObject object) {
        return (object instanceof DBCatalogFolder) || isServer(object);
    }

    public static boolean isServer(DBObject object) {
        return object instanceof DBServer;
    }

    public static String nullifyName(String name) {
        if (name != null) {
            if (name.trim().isEmpty()) {
                return null;
            }
        }
        return name;
    }

    public static TableColumnPath getTableColumnPath(DBObject object) {
        TableColumnPath p = new TableColumnPath();
        if (object instanceof DBTableColumn) {
            DBTableColumn node = (DBTableColumn) object;
            p.catalogName = node.getCatalogName();
            p.schemaName = node.getSchemaName();
            p.tableType = node.getTable().getTypeName();
            p.tableName = node.getTableName();
            p.columnName = node.getColumnName();
        } else if (object instanceof DBTableColumnFolder) {
            DBTable node = ((DBTableColumnFolder) object).getTable();
            p.catalogName = node.getCatalogName();
            p.schemaName = node.getSchemaName();
            p.tableName = node.getTableName();
            p.tableType = node.getTableType().getTypeName();
        } else if (object instanceof DBTable) {
            DBTable node = ((DBTable) object);
            p.catalogName = node.getCatalogName();
            p.schemaName = node.getSchemaName();
            p.tableName = node.getName();
            p.tableType = node.getTypeName();
        } else if (object instanceof DBTableFolder) {
            DBSchema node = ((DBTableFolder) object).getSchema();
            p.catalogName = node.getCatalogName();
            p.schemaName = node.getSchemaName();
            p.tableType = ((DBTableFolder) object).getTypeName();
        } else if (object instanceof DBSchema) {
            DBSchema node = ((DBSchema) object);
            p.catalogName = node.getCatalogName();
            p.schemaName = node.getSchemaName();
        } else if (object instanceof DBCatalog) {
            DBCatalog node = ((DBCatalog) object);
            p.catalogName = node.getCatalogName();
        } else if (object instanceof DBCatalogFolder) {
            //
        } else if (object instanceof DBServer) {
            //
        }
        return p;
    }

    public static ProcedureColumnPath getProcedureColumnPath(DBObject object) {
        ProcedureColumnPath p = new ProcedureColumnPath();
        if (object instanceof DBProcedureColumn) {
            DBProcedureColumn node = (DBProcedureColumn) object;
            p.catalogName = node.getCatalogName();
            p.schemaName = node.getSchemaName();
            p.procedureName = node.getProcedureName();
            p.columnName = node.getColumnName();
        } else if (object instanceof DBProcedure) {
            DBProcedure node = ((DBProcedure) object);
            p.catalogName = node.getCatalogName();
            p.schemaName = node.getSchemaName();
            p.procedureName = node.getName();
        } else if (object instanceof DBProcedureFolder) {
            DBSchema node = ((DBProcedureFolder) object).getSchema();
            p.catalogName = node.getCatalogName();
            p.schemaName = node.getSchemaName();
        } else if (object instanceof DBSchema) {
            DBSchema node = ((DBSchema) object);
            p.catalogName = node.getCatalogName();
            p.schemaName = node.getSchemaName();
        } else if (object instanceof DBCatalog) {
            DBCatalog node = ((DBCatalog) object);
            p.catalogName = node.getCatalogName();
        } else if (object instanceof DBCatalogFolder) {
            //
        } else if (object instanceof DBServer) {
            //
        }
        return p;
    }

    public static class TableColumnPath {

        private String catalogName;
        private String schemaName;
        private String tableName;
        private String tableType;
        private String columnName;

        public String getCatalogName() {
            return catalogName;
        }

        public String getColumnName() {
            return columnName;
        }

        public String getSchemaName() {
            return schemaName;
        }

        public String getTableName() {
            return tableName;
        }

        public String getTableType() {
            return tableType;
        }

        public void nullifyNames() {
            catalogName = nullifyName(catalogName);
            schemaName = nullifyName(schemaName);
            tableType = nullifyName(tableType);
            tableName = nullifyName(tableName);
            columnName = nullifyName(columnName);
        }
    }

    public static class ProcedureColumnPath {

        private String catalogName;
        private String schemaName;
        private String procedureName;
        private String columnName;

        public String getCatalogName() {
            return catalogName;
        }

        public String getColumnName() {
            return columnName;
        }

        public String getSchemaName() {
            return schemaName;
        }

        public String getProcedureName() {
            return procedureName;
        }

        public void nullifyNames() {
            catalogName = nullifyName(catalogName);
            schemaName = nullifyName(schemaName);
            procedureName = nullifyName(procedureName);
            columnName = nullifyName(columnName);
        }
    }
}
