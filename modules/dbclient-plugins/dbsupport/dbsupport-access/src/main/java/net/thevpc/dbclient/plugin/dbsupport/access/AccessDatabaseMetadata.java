package net.thevpc.dbclient.plugin.dbsupport.access;

import net.thevpc.dbclient.api.sql.DatabaseMetaDataAdapter;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * ignore all catalog param because ODBC driver passed the database path as catalog.
 */
public class AccessDatabaseMetadata extends DatabaseMetaDataAdapter {
    public AccessDatabaseMetadata(DatabaseMetaData instance) {
        super(instance);
    }

    @Override
    public ResultSet getAttributes(String param_1, String param_2, String param_3, String param_4) throws SQLException {
        return super.getAttributes(null, param_2, param_3, param_4);
    }

    @Override
    public ResultSet getPrimaryKeys(String param_1, String param_2, String param_3) throws SQLException {
        return super.getPrimaryKeys(null, param_2, param_3);
    }

    @Override
    public ResultSet getBestRowIdentifier(String param_1, String param_2, String param_3, int param_4, boolean param_5) throws SQLException {
        return super.getBestRowIdentifier(null, param_2, param_3, param_4, param_5);
    }


}
