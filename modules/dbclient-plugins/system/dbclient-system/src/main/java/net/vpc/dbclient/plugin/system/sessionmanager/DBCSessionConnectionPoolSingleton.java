/**
 * ====================================================================
 *             DBClient yet another Jdbc client tool
 *
 * DBClient is a new Open Source Tool for connecting to jdbc
 * compliant relational databases. Specific extensions will take care of
 * each RDBMS implementation.
 *
 * Copyright (C) 2006-2008 Taha BEN SALAH
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 * ====================================================================
 */

package net.vpc.dbclient.plugin.system.sessionmanager;

import net.vpc.dbclient.api.DBCSession;
import net.vpc.dbclient.api.drivermanager.DBCConnectionPool;
import net.vpc.dbclient.api.pluginmanager.DBCAbstractPluggable;
import net.vpc.prs.plugin.Inject;
import net.vpc.dbclient.api.sessionmanager.DBCSessionConnectionPool;
import net.vpc.dbclient.api.sql.DBCConnection;
import net.vpc.dbclient.api.sql.DBCDefaultConnection;
import net.vpc.dbclient.plugin.system.sql.DBCDefaultConnectionImpl;

import java.sql.Connection;
import java.sql.SQLException;
import net.vpc.prs.plugin.Implementation;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 6 dec. 2006 13:36:46
 */
@Implementation(priority=-1)
public class DBCSessionConnectionPoolSingleton extends DBCAbstractPluggable implements DBCSessionConnectionPool {
    private DBCDefaultConnection connection;
    @Inject
    private DBCSession session;
    private DBCConnectionPool applicationConnectionPool;

    public DBCSessionConnectionPoolSingleton() {
    }


    public DBCConnection getConnection() {
        return connection;
    }

    public void setConnection(Connection nativeConnection) {
        this.connection = (DBCDefaultConnectionImpl) session.getFactory().newInstance(DBCDefaultConnection.class);
        this.connection.setLoggerProvider(session);
        this.connection.setConnection(nativeConnection);
        ((DBCDefaultConnectionImpl) this.connection).setClosable(false);
    }

    public void init(DBCConnectionPool applicationConnectionPool) {
        this.applicationConnectionPool = applicationConnectionPool;
    }

    public Connection createConnection() throws ClassNotFoundException, SQLException {
        if(connection==null){
            setConnection(applicationConnectionPool.createConnection(session.getApplication(), session.getInfo()));
        }
        return connection;
    }
}
