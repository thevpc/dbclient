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

package net.thevpc.dbclient.api;

import net.thevpc.dbclient.api.configmanager.DBCSessionConfig;
import net.thevpc.dbclient.api.configmanager.DBCSessionInfo;
import net.thevpc.dbclient.api.drivermanager.DBCConnectionPool;
import net.thevpc.dbclient.api.pluginmanager.DBCPluggable;
import net.thevpc.dbclient.api.pluginmanager.DBCPluginSession;
import net.thevpc.dbclient.api.pluginmanager.DBCSessionFactory;
import net.thevpc.common.prs.plugin.Extension;
import net.thevpc.dbclient.api.sessionmanager.DBCProgressMonitor;
import net.thevpc.dbclient.api.sessionmanager.DBCSessionConnectionPool;
import net.thevpc.dbclient.api.sessionmanager.DBCSessionListener;
import net.thevpc.dbclient.api.sessionmanager.DBCSessionView;
import net.thevpc.dbclient.api.sql.DBCConnection;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.logging.Logger;
import net.thevpc.common.swing.util.ClassPath;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 13 juil. 2005 14:32:52
 */
@Extension(group = "core")
public interface DBCSession extends DBClientContext, DBCPluggable {
    public static final String PROPERTY_PROGRESS_MONITOR_ADDED = "progressMonitor.added";
    public static final String PROPERTY_PROGRESS_MONITOR_REMOVED = "progressMonitor.removed";

    public DBCSessionView getView();

    public Collection<DBCPluginSession> getPluginSessions();

    /**
     * retrive DBCPluginSession for the givern pluginId or null if not found.
     *
     * @param pluginId
     * @return DBCPluginSession for the pluginId or null if not found
     */
    public DBCPluginSession getPluginSession(String pluginId);

    public void addPluginSession(DBCPluginSession plugin);

    public String getType();

    public DBCApplication getApplication();
    
    public DBCSessionTaskManager getTaskManager();

    public ClassLoader getClassLoader();

    public DBCConnection getConnection() throws SQLException;


    public void close() throws SQLException;

    public DBCConnection createConnection() throws ClassNotFoundException, SQLException, IOException;


    public DBCSessionConfig getConfig();

    public void init(DBCApplication dbClient, DBCSessionInfo info, Connection prototypeNativeConnection, DBCConnectionPool connectionPool, String connectionType) throws ClassNotFoundException, SQLException;

    public void openSession();

    public DBCSessionConnectionPool getConnectionPool();

    public void addSessionListener(DBCSessionListener listener);

    public boolean isPluginEnabled(String pluginId);

    public void setPluginEnabled(String pluginId, boolean enable);

    public DBCSessionInfo getInfo();

    public DBCProgressMonitor createMonitor(String id);

    void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);

    void removePropertyChangeListener(String propertyName, PropertyChangeListener listener);

    void addPropertyChangeListener(PropertyChangeListener listener);

    void removePropertyChangeListener(PropertyChangeListener listener);

    DBCSessionFactory getFactory();
    
    ClassPath getClassPath();

    public Logger getLogger(String category);
    
    public void setCatalog(String catalog) throws SQLException;
    
    public String getCatalog() throws SQLException;
    
    public void revalidateConnection() throws SQLException;

    public Object getUserObject(String name) ;

    public void setUserObject(String name, Object value) ;
}
