//package net.vpc.dbclient.plugin.serverlibrary.hsql;
//
//import net.vpc.dbclient.api.pluginmanager.DBCAbstractPlugin;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//
///**
// * Created by IntelliJ IDEA.
// * User: vpc
// * Date: 26 août 2009
// * Time: 05:54:33
// * To change this template use File | Settings | File Templates.
// */
//public class HsqlServerPlugin extends DBCAbstractPlugin {
//
//    public int getConnectionSupportLevel(Connection nativeConnection) throws SQLException {
//        String product = nativeConnection.getMetaData().getDatabaseProductName().toLowerCase();
//        if (product.contains("hsql")) {
//            return 1;
//        }
//        return -1;
//    }
//
//}