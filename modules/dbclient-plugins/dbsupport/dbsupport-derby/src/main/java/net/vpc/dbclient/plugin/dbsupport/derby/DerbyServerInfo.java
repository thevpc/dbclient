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

import net.vpc.dbclient.plugin.tool.servermanager.DBCServerInfo;

public class DerbyServerInfo extends DBCServerInfo {

    public DerbyServerInfo() {
    }

    public final String getDatabasePath() {
        String s = (String) getProperty("databasePath");
        if (s == null || s.trim().length() == 0) {
            return ".";
        }
        return s;
    }

    public final void setDatabasePath(String name) {
        setProperty("databasePath", name);
    }

    public final String getHostName() {
        String s = (String) getProperty("hostName");
        if (s == null || s.trim().length() == 0) {
            return "localhost";
        }
        return s;
    }

    public final void setHostName(String name) {
        setProperty("hostName", name);
    }

    public final String getLibFolderPath() {
        return (String) getProperty("libFolderPath");
    }

    public final void setLibFolderPath(String name) {
        setProperty("libFolderPath", name);
    }

    public final String getLibPluginId() {
        String s = (String) getProperty("libPluginId");
        if (s == null) {
            return "library-derby-server";
        }
        return s;
    }

    public final void setLibPluginId(String name) {
        setProperty("libPluginId", name);
    }

    public final DerbyClassPathType getClassPathType() {
        String s = (String) getProperty("classPathType");
        return s == null ? DerbyClassPathType.PLUGIN : DerbyClassPathType.valueOf(s);
    }

    public final void setClassPathType(DerbyClassPathType name) {
        setProperty("classPathType", name.toString());
    }

}
