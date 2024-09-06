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


package net.thevpc.dbclient.plugin.tool.servermanager;

import net.thevpc.dbclient.api.configmanager.DBCAbstractInfo;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 27 juin 2007 13:19:06
 */
public class DBCServerInfo extends DBCAbstractInfo {
    public DBCServerInfo() {
    }

    public final String getConfigName() {
        return (String) getProperty("configName");
    }

    public final void setConfigName(String name) {
        setProperty("configName", name);
    }

    public int getPort() {
        Object p = getProperty("port");
        return p == null ? 0 : (Integer) p;
    }

    public final void setPort(int port) {
        setProperty("port", port);
    }

    public String getType() {
        return (String) getProperty("type");
    }

    public final void setType(String type) {
        setProperty("type", type);
    }

    public int getId() {
        Object p = getProperty("id");
        return p == null ? 0 : (Integer) p;
    }

    public final void setId(int port) {
        setProperty("id", port);
    }

    public boolean isAutoStart() {
        Object p = getProperty("autoStart");
        return p == null ? false : (Boolean) p;
    }

    public final void setAutoStart(boolean autoStart) {
        setProperty("autoStart", autoStart);
    }

    public boolean isStarted() {
        Object p = getProperty("started");
        return p == null ? false : (Boolean) p;
    }

    public final void setStarted(boolean autoStart) {
        setProperty("started", autoStart);
    }

    public boolean isStopOnExit() {
        Object p = getProperty("stopOnExit");
        return p == null ? false : (Boolean) p;
    }

    public final void setStopOnExit(boolean stopOnExit) {
        setProperty("stopOnExit", stopOnExit);
    }

    public boolean isLocal() {
        Object p = getProperty("local");
        return p == null ? true : (Boolean) p;
    }

    public final void setLocal(boolean local) {
        setProperty("local", local);
    }

    public String toString() {
        String name = getConfigName();
        return (name == null || name.length() == 0) ? "NONAME" : name;
    }
}
