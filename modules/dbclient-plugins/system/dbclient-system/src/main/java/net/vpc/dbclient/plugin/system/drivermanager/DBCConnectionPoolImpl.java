package net.vpc.dbclient.plugin.system.drivermanager;

import net.vpc.dbclient.api.DBCApplication;
import net.vpc.dbclient.api.DBCApplicationView;
import net.vpc.dbclient.api.configmanager.DBCSessionInfo;
import net.vpc.dbclient.api.configmanager.DBCSessionProperties;
import net.vpc.dbclient.api.drivermanager.DBCConnectionPool;
import net.vpc.dbclient.api.pluginmanager.DBCAbstractPluggable;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.CancellationException;

public class DBCConnectionPoolImpl extends DBCAbstractPluggable implements DBCConnectionPool {
    public DBCConnectionPoolImpl() {
    }

    public Connection createConnection(DBCApplication application, DBCSessionInfo sessionInfo) throws ClassNotFoundException, SQLException {

        DBCSessionProperties sessionProperties = sessionInfo.getProperties();
        String passwd = sessionInfo.getCnxPassword();
        Boolean aBoolean = sessionInfo.isAskForPassword();
        final String cnxLogin = sessionInfo.getCnxLogin();
        if (aBoolean != null && aBoolean) {
            final DBCApplicationView view = application.getView();
            final String qualifiedName = sessionInfo.getQualifiedName();
            passwd = JOptionPane.showInputDialog(null,
                    view.getMessageSet().get("DBCConnectionPool.askPassword.message", new Object[]{qualifiedName, cnxLogin}),
                    view.getMessageSet().get("DBCConnectionPool.askPassword.title", new Object[]{qualifiedName, cnxLogin}),
                    JOptionPane.QUESTION_MESSAGE);
            if (passwd == null) {
                throw new CancellationException();
            }
        }
        return application.getDriverManager().getConnection(
                sessionInfo.getCnxUrl(),
                sessionInfo.getCnxDriver(), cnxLogin,
                passwd,
                sessionProperties == null ? null : sessionProperties.toProperties()
        );
    }

}
