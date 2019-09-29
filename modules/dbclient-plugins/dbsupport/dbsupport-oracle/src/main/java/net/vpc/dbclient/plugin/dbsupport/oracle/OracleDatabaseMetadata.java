package net.vpc.dbclient.plugin.dbsupport.oracle;

import net.vpc.dbclient.api.sql.DatabaseMetaDataAdapter;
import net.vpc.dbclient.api.sql.ListResultSet;
import net.vpc.dbclient.api.sql.ResultSetUnion;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: vpc
 * Date: 30 mars 2009
 * Time: 21:50:19
 * To change this template use File | Settings | File Templates.
 */
public class OracleDatabaseMetadata extends DatabaseMetaDataAdapter {
    public OracleDatabaseMetadata(DatabaseMetaData instance) {
        super(instance);
    }

    @Override
    public ResultSet getSchemas() throws SQLException {
        ResultSet r = super.getSchemas();
        ResultSet r0 = new ListResultSet(r, r.getMetaData(), new Object[][]{{"PUBLIC", null}});
        return new ResultSetUnion(r, r0);
    }
}
