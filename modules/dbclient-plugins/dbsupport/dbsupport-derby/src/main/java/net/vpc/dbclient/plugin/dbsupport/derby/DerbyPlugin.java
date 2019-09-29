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

package net.vpc.dbclient.plugin.dbsupport.derby;


import net.vpc.dbclient.api.pluginmanager.DBCAbstractPlugin;
import net.vpc.prs.plugin.PluginInfo;
import net.vpc.dbclient.api.viewmanager.DBCExecutionPlanComponent;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Map;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 16 nov. 2006 00:00:32
 */
@PluginInfo(
        messageSet = "net.vpc.dbclient.plugin.dbsupport.derby.messageset.Plugin",
        iconSet = "net.vpc.dbclient.plugin.dbsupport.derby.iconset.Plugin"
)
public class DerbyPlugin extends DBCAbstractPlugin {
    public static final String ID = "dbsupport-derby";

    public DerbyPlugin() {
    }

    public static int getConnectionSupportLevel0(Connection nativeConnection) throws SQLException {
        String product = nativeConnection.getMetaData().getDatabaseProductName().toLowerCase();
        if (product.contains("derby")) {
            return 1;
        }
        return -1;
    }

    public int getConnectionSupportLevel(Connection nativeConnection) throws SQLException {
        return getConnectionSupportLevel0(nativeConnection);
    }

    public Map<Class, Class> getConnectionFactoryMap(Connection nativeConnection) throws SQLException {
        if (getConnectionSupportLevel(nativeConnection) > 0) {
            Hashtable<Class, Class> map = new Hashtable<Class, Class>();
            map.put(DBCExecutionPlanComponent.class, DerbyExecutionPlanComponent.class);
            return map;
        }
        return null;
    }

    public String getConnectionType(Connection nativeConnection) throws SQLException {
        return "Derby";
    }

}
