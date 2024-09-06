package net.thevpc.dbclient.plugin.system.sql.features;

import net.thevpc.dbclient.api.sql.features.GenerateSQLDropFeature;
import net.thevpc.dbclient.api.sql.objects.SQLObjectTypes;
import net.thevpc.dbclient.plugin.system.sql.SystemSQLUtils;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)  alias vpc
 * @creationtime 2009/08/16 01:24:23
 */
public class DefaultFeatureGenerateSQLDrop implements GenerateSQLDropFeature {


    public boolean isSupported(SQLObjectTypes objectType) {
        switch (objectType) {
            case FUNCTION:
            case PROCEDURE:
            case SEQUENCE:
            case TABLE:
            case VIEW:
            case TRIGGER:
            case TABLE_INDEX:
            case TABLE_CONSTRAINT:
            case TABLE_COLUMN: {
                return true;
            }
        }
        return false;
    }

    public String getSQL(String catalog, String schema, String parentName, String objectName, SQLObjectTypes objectType) {
        switch (objectType) {
            case FUNCTION: {
                return getSQLDropFunction(catalog, schema, parentName, objectName);
            }
            case TABLE_INDEX: {
                return getSQLDropIndex(catalog, schema, parentName, objectName);
            }
            case PROCEDURE: {
                return getSQLDropProcedure(catalog, schema, parentName, objectName);
            }
            case SEQUENCE: {
                return getSQLDropSequence(catalog, schema, parentName, objectName);
            }
            case TABLE: {
                return getSQLDropTable(catalog, schema, parentName, objectName);
            }
            case VIEW: {
                return getSQLDropView(catalog, schema, parentName, objectName);
            }
            case TRIGGER: {
                return getSQLDropTrigger(catalog, schema, parentName, objectName);
            }
            case TABLE_COLUMN: {
                return getSQLDropTableColumn(catalog, schema, parentName, objectName);
            }
        }
        throw new IllegalArgumentException("Unsupported Drop " + objectType);
    }

    public String getSQLDropFunction(String catalog, String schema, String parentName, String objectName) {
        return "DROP FUNCTION " + SystemSQLUtils.getFullName(catalog, schema, parentName, objectName, SQLObjectTypes.FUNCTION);
    }

    public String getSQLDropIndex(String catalog, String schema, String parentName, String objectName) {
        return "DROP INDEX " + SystemSQLUtils.getFullName(catalog, schema, parentName, objectName, SQLObjectTypes.TABLE_INDEX);
    }

    public String getSQLDropProcedure(String catalog, String schema, String parentName, String objectName) {
        return "DROP PROCEDURE " + SystemSQLUtils.getFullName(catalog, schema, parentName, objectName, SQLObjectTypes.PROCEDURE);
    }

    public String getSQLDropSequence(String catalog, String schema, String parentName, String objectName) {
        return "DROP SEQUENCE " + SystemSQLUtils.getFullName(catalog, schema, parentName, objectName, SQLObjectTypes.SEQUENCE);
    }

    public String getSQLDropTable(String catalog, String schema, String parentName, String objectName) {
        return "DROP TABLE " + SystemSQLUtils.getFullName(catalog, schema, parentName, objectName, SQLObjectTypes.TABLE);
    }

    public String getSQLDropView(String catalog, String schema, String parentName, String objectName) {
        return "DROP VIEW " + SystemSQLUtils.getFullName(catalog, schema, parentName, objectName, SQLObjectTypes.VIEW);
    }

    public String getSQLDropTrigger(String catalog, String schema, String parentName, String objectName) {
        return "DROP TRIGGER " + SystemSQLUtils.getFullName(catalog, schema, parentName, objectName, SQLObjectTypes.TRIGGER);
    }

    public String getSQLDropTableColumn(String catalog, String schema, String parentName, String objectName) {
        return "ALTER TABLE " + SystemSQLUtils.getFullName(catalog, schema, null, parentName, SQLObjectTypes.TABLE) + " DROP " + objectName;
    }
}
