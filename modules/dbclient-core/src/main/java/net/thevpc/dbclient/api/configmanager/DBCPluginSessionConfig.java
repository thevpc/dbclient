/**
 * ====================================================================
 *             DBCLient yet another Jdbc client tool
 *
 * DBClient is a new Open Source Tool for connecting to jdbc
 * compliant relational databases. Specific extensions will take care of
 * each RDBMS implementation.
 *
 * Copyright (C) 2006-2007 Taha BEN SALAH
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
package net.thevpc.dbclient.api.configmanager;

import net.thevpc.dbclient.api.pluginmanager.DBCPluginSession;

/**
 * @author vpc
 */
public class DBCPluginSessionConfig extends DBCAbstractConfig {

    private DBCPluginSession pluginSession;
    private String prefix;

    public DBCPluginSessionConfig() {
    }

    public void init(DBCPluginSession pluginSession) {
        this.pluginSession = pluginSession;
        this.prefix = "Plugin." + pluginSession.getPlugin().getId() + ".";
    }

    public String getStringProperty(String name, String defaultValue) {
        return pluginSession.getSession().getConfig().getStringProperty(this.prefix + name, defaultValue);
    }

    public void setStringProperty(String name, String value) {
        pluginSession.getSession().getConfig().setStringProperty(this.prefix + name, value);
    }

    public void clearProperties(String pathPattern) {
        pluginSession.getSession().getConfig().clearProperties(this.prefix + pathPattern);
    }

    public String getPathValue(String path, String code) {
        return pluginSession.getSession().getConfig().getPathValue(this.prefix + path, code);
    }

    public void clearPathsValues(String path, String code) {
        pluginSession.getSession().getConfig().clearPathsValues(this.prefix + path, code);
    }

    public void setPathValue(String path, String code, String value) {
        pluginSession.getSession().getConfig().setPathValue(this.prefix + path, code, value);
    }
}
