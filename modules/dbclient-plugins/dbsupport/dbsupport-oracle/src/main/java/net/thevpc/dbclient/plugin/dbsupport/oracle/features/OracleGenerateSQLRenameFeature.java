package net.thevpc.dbclient.plugin.dbsupport.oracle.features;

import net.thevpc.dbclient.api.sql.features.GenerateSQLRenameFeature;
import net.thevpc.dbclient.api.sql.objects.SQLObjectTypes;
import net.thevpc.dbclient.plugin.system.sql.SystemSQLUtils;

import java.sql.SQLException;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)  alias vpc
 * @creationtime 2009/08/16 01:44:57
 */
public class OracleGenerateSQLRenameFeature implements GenerateSQLRenameFeature {
    public boolean isSupported(SQLObjectTypes objectType) throws SQLException {
        switch (objectType) {
            case TABLE_INDEX:
            case TABLE:
            case SCHEMA:
            case TRIGGER:
            case VIEW:
            case TABLE_COLUMN: {
                return true;
            }
        }
        return false;
    }

    public String getSQL(String catalog, String schema, String parentName, String objectName, String newName, SQLObjectTypes objectType) throws SQLException {
        switch (objectType) {
            case TABLE_INDEX: {
                return "ALTER INDEX " + SystemSQLUtils.getFullName(catalog, schema, parentName, objectName, SQLObjectTypes.TABLE_INDEX) + " RENAME TO " + newName;
            }
            case TABLE: {
                return "ALTER TABLE " + SystemSQLUtils.getFullName(catalog, schema, parentName, objectName, SQLObjectTypes.TABLE) + " RENAME TO " + newName;
            }
            case SCHEMA: {
                return "ALTER SCHEMA " + SystemSQLUtils.getFullName(catalog, schema, parentName, objectName, SQLObjectTypes.SCHEMA) + " RENAME TO " + newName;
            }
            case TRIGGER: {
                return "ALTER TRIGGER " + SystemSQLUtils.getFullName(catalog, schema, parentName, objectName, SQLObjectTypes.TRIGGER) + " RENAME TO " + newName;
            }
            case VIEW: {
                return "ALTER TABLE " + SystemSQLUtils.getFullName(catalog, schema, parentName, objectName, SQLObjectTypes.VIEW) + " RENAME TO " + newName;
            }
            case TABLE_COLUMN: {
                return "ALTER TABLE " + SystemSQLUtils.getFullName(catalog, schema, null, parentName, SQLObjectTypes.TABLE) + " DROP COLUMN " + objectName;
            }
        }
        return null;
    }
}