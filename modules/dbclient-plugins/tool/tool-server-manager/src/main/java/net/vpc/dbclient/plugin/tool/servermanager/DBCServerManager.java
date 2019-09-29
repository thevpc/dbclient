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

package net.vpc.dbclient.plugin.tool.servermanager;

import net.vpc.dbclient.api.pluginmanager.DBCPluggable;
import net.vpc.prs.plugin.Extension;

/**
 * @author vpc
 */
@Extension(group = "manager")
public interface DBCServerManager extends DBCPluggable {

    void attachServer(String type, DBCServerInfo info) throws Exception;

    void close();

    DBCServerManagerHandler getDBServerManagerHandler(String type);

    DBCServerInstance getServer(int index);

    String[] getServerTypes();

    DBCServerInstance[] getServers();

    int getServersCount();

    public void registerServerType(DBCServerManagerHandler instance);

    DBCServerInfo[] getServersInfos();

    void addServersInfo(DBCServerInfo info);

    void updateServersInfo(DBCServerInfo info);

    void removeServersInfo(int id);

    void addServerListener(DBCServerListener listener);

    void removeServerListener(DBCServerListener listener);

    DBCServerInstance startServer(DBCServerInfo info) throws Exception;

    void stopServer(DBCServerInstance inst) throws Exception;

    void unregisterServerType(String type);

    /**
     * reload DBCServerInfo from config
     */
    public void reload();
}
