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

package net.thevpc.dbclient.plugin.system.sessionmanager;

import net.thevpc.dbclient.api.DBCSession;
import net.thevpc.dbclient.api.drivermanager.DBCConnectionPool;
import net.thevpc.dbclient.api.pluginmanager.DBCAbstractPluggable;
import net.thevpc.common.prs.plugin.Inject;
import net.thevpc.dbclient.api.sessionmanager.DBCSessionConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 6 dec. 2006 13:36:46
 */
public class DBCSessionConnectionPoolImpl extends DBCAbstractPluggable implements DBCSessionConnectionPool {
    @Inject
    private DBCSession session;
    private DBCConnectionPool applicationConnectionPool;

    public DBCSessionConnectionPoolImpl() {

    }

    public void init(DBCConnectionPool applicationConnectionPool) {
        this.applicationConnectionPool = applicationConnectionPool;
    }

    public DBCSession getSession() {
        return session;
    }

    /**
     * a simple wxrapper to applicationConnectionPool.createConnection
     *
     * @return native connection
     * @throws ClassNotFoundException if java problem
     * @throws SQLException           is sql problem
     */
    public Connection createConnection() throws ClassNotFoundException, SQLException {
        return applicationConnectionPool.createConnection(session.getApplication(), session.getConfig().getSessionInfo());
    }
}
