package net.vpc.dbclient.plugin.serverlibrary.derby;

import net.vpc.dbclient.api.pluginmanager.DBCAbstractPlugin;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: vpc
 * Date: 26 août 2009
 * Time: 05:54:33
 * To change this template use File | Settings | File Templates.
 */
public class DerbyServerPlugin extends DBCAbstractPlugin {

    public int getConnectionSupportLevel(Connection nativeConnection) throws SQLException {
        String product = nativeConnection.getMetaData().getDatabaseProductName().toLowerCase();
        if (product.contains("derby")) {
            return 1;
        }
        return -1;
    }

}