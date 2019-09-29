package net.vpc.dbclient.plugin.dbsupport.derby.features;

import net.vpc.dbclient.api.sql.features.GenerateSQLRenameFeature;
import net.vpc.dbclient.api.sql.objects.SQLObjectTypes;
import net.vpc.dbclient.plugin.system.sql.SystemSQLUtils;

import java.sql.SQLException;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)  alias vpc
 * @creationtime 2009/08/16 01:44:57
 */
public class DerbyGenerateSQLRenameFeature implements GenerateSQLRenameFeature {
    public boolean isSupported(SQLObjectTypes objectType) throws SQLException {
        switch (objectType) {
            case TABLE_INDEX:
            case TABLE:
            case TABLE_COLUMN: {
                return true;
            }
        }
        return false;
    }

    public String getSQL(String catalog, String schema, String parentName, String objectName, String newName, SQLObjectTypes objectType) throws SQLException {
        switch (objectType) {
            case TABLE_INDEX: {
                return "RENAME INDEX " + SystemSQLUtils.getFullName(catalog, schema, parentName, objectName, SQLObjectTypes.TABLE_INDEX) + " TO " + newName;
            }
            case TABLE: {
                return "RENAME TABLE " + SystemSQLUtils.getFullName(catalog, schema, parentName, objectName, SQLObjectTypes.TABLE) + " TO " + newName;
            }
            case TABLE_COLUMN: {
                return "RENAME COLUMN " + SystemSQLUtils.getFullName(catalog, schema, parentName, objectName, SQLObjectTypes.TABLE_COLUMN) + " TO " + newName;
            }
        }
        return null;
    }
}
