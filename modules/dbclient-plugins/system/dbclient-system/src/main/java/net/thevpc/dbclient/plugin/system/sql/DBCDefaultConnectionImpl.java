package net.thevpc.dbclient.plugin.system.sql;

import java.sql.Connection;
import java.sql.SQLException;
import net.thevpc.dbclient.api.sql.DBCDefaultConnection;

/**
 * Created by IntelliJ IDEA.
 * User: vpc
 * Date: 26 août 2009
 * Time: 02:34:21
 * To change this template use File | Settings | File Templates.
 */
public class DBCDefaultConnectionImpl extends DBCAbstractConnection implements DBCDefaultConnection {
    //should accept all connections

    public int getConnectionSupportLevel(Connection connection) throws SQLException {
        return 0;
    }
}
