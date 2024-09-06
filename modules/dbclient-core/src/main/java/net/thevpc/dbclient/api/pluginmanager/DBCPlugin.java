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

package net.thevpc.dbclient.api.pluginmanager;

import net.thevpc.common.prs.plugin.Extension;
import net.thevpc.common.prs.plugin.Plugin;
import net.thevpc.dbclient.api.DBCApplication;
import net.thevpc.dbclient.api.DBCSession;
import net.thevpc.dbclient.api.configmanager.DBCPluginConfig;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 15 nov. 2006 23:17:05
 */
@Extension(group = "core", customizable = false)
public interface DBCPlugin extends Plugin<DBCApplication, DBCPluginManager>, DBCPluggable {
     public static final String CATEGORY_DBDRIVER = "dbdriver";

    public DBCPluginConfig getConfig();

    public DBCPluginSession createPluginSession(DBCSession session);

    /**
     * @param connection native connection
     * @return a value >0 if this plugin is to be considered as a Support Plugin. When
     *         different plugins return a positive value, the one with the highest value is selected
     * @throws java.sql.SQLException on error
     */
    public int getConnectionSupportLevel(Connection connection) throws SQLException;

    /**
     * will be called if accept returned a value >0 to retrieve Factory mapping
     *
     * @param nativeConnection native connection
     * @return Map that contains specific mapping for this
     * @throws SQLException if error
     */
    public Map<Class, Class> getConnectionFactoryMap(Connection nativeConnection) throws SQLException;

    /**
     * will be called if accept returned a value >0 to retrieve connection Server Type
     *
     * @param nativeConnection native connection
     * @return "standardaized" connection type label
     * @throws SQLException if error
     */
    public String getConnectionType(Connection nativeConnection) throws SQLException;


    public <T> T instantiate(Class<T> object);
}
