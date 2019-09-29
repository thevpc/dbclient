package net.vpc.dbclient.api.sql.features;

import net.vpc.dbclient.api.sql.DBCFeature;
import net.vpc.dbclient.api.sql.objects.SQLObjectTypes;

import java.sql.SQLException;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)  alias vpc
 * @creationtime 2009/08/16 01:17:27
 */
public interface GenerateSQLCloneFeature extends DBCFeature{
    public static final GenerateSQLCloneFeature UNSUPPORTED=new GenerateSQLCloneFeature() {
        public boolean isSupported(SQLObjectTypes objectType) throws SQLException {
            return false;
        }

        public String getSQL(String catalog, String schema, String parentName, String objectName, SQLObjectTypes objectType) throws SQLException {
            return null;
        }
    };
    public boolean isSupported(SQLObjectTypes objectType) throws SQLException;
    public String getSQL(String catalog, String schema, String parentName, String objectName, SQLObjectTypes objectType) throws SQLException;
}