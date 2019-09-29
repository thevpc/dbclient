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
package net.vpc.dbclient.api.pluginmanager;

import net.vpc.dbclient.api.DBCApplication;
import net.vpc.dbclient.api.DBCApplicationView;
import net.vpc.dbclient.api.DBCSession;
import net.vpc.dbclient.api.configmanager.DBCPluginConfig;
import net.vpc.prs.iconset.IconSet;
import net.vpc.prs.messageset.MessageSet;
import net.vpc.prs.plugin.DefaultPlugin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 15 nov. 2006 23:42:13
 */
public abstract class DBCAbstractPlugin extends DefaultPlugin<DBCApplication, DBCPluginManager> implements DBCPlugin {
    private DBCPluginConfig config;

    public DBCAbstractPlugin() {
        setFallBackIconSetName(DBCApplicationView.DEFAULT_ICONSET_NAME);
    }
    
    
    /**
     * Return null by default to let Plugin Manager search and find PluginSession implementation for me
     *
     * @param session session
     * @return DBCPluginSession instance
     */
    public DBCPluginSession createPluginSession(DBCSession session) {
        return null;
    }

    public DBCPluginConfig getConfig() {
        if (config == null) {
            config = new DBCPluginConfig();
            config.init(this);
        }
        return config;
    }

    /**
     * @param connection native connection
     * @return 0 as default implementation or -1 whitch means that the plugin is not to be loaded
     * @return 0
     * @throws SQLException
     */
    public int getConnectionSupportLevel(Connection connection) throws SQLException {
        return 0;
    }

    /**
     * @param nativeConnection native connection
     * @return null as a default implementation
     * @throws SQLException
     */
    public Map<Class, Class> getConnectionFactoryMap(Connection nativeConnection) throws SQLException {
        return null;
    }

    /**
     * return null as default implementation
     *
     * @param nativeConnection native connection
     * @return null
     * @throws SQLException
     */
    public String getConnectionType(Connection nativeConnection) throws SQLException {
        return null;
    }

    public <T> T instantiate(Class<T> clazz) {
        return getApplication().getFactory().instantiate(clazz,getDescriptor());
    }

    public MessageSet getApplicationMessageSet() {
        return getApplication().getView().getMessageSet();
    }

    public IconSet getApplicationIconSet() {
        return getApplication().getView().getIconSet();
    }

    @Override
    public String toString() {
        return getId()+"-"+ getDescriptor().getVersion().toString();
    }
    
}
