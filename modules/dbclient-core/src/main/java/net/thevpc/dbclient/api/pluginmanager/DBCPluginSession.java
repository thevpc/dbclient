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

import net.thevpc.dbclient.api.DBCSession;
import net.thevpc.dbclient.api.configmanager.DBCPluginSessionConfig;
import net.thevpc.common.prs.ResourceSetHolder;
import net.thevpc.common.prs.iconset.IconSet;
import net.thevpc.common.prs.plugin.Extension;

import java.util.Locale;

/**
 * A PluginSession is the interaction between a Plugin and a Session
 * Each Plugin creates a PluginSession using DBCPlugin.createPluginSession method
 *
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 15 nov. 2006 23:17:05
 */
@Extension(group = "core", customizable = false)
public interface DBCPluginSession extends ResourceSetHolder, DBCPluggable {

    /**
     * Plugin Associated With this PluginSession
     *
     * @return Plugin Associated With this PluginSession
     */
    public DBCPlugin getPlugin();

    /**
     * Session Associated With this PluginSession
     *
     * @return Session Associated With this PluginSession
     */
    public DBCSession getSession();


    /**
     * Called to retrieve all Session SQLQueryTools For the current Session
     *
     * @return Collection of DBCSQLQueryTool
     */


    /**
     * Called When all PluginSessions have been Created for initializing/customizing UI
     * according to the current PluginSession.
     * SessionOpening is Called BEFORE createSessionActions and createSessionSettings
     */
    public void sessionOpening();

    /**
     * Called When all PluginSessions have been Created for initializing/customizing UI
     * according to the current PluginSession.
     * SessionOpening is Called AFTER createSessionActions and createSessionSettings
     */
    public void sessionOpened();

    /**
     * Called Before Session is Closed for freeing resources
     * used by the current PluginSession
     */
    public void sessionClosing();

    /**
     * Called each time Locale changes
     *
     * @param locale new Locale
     */
    public void setLocale(Locale locale);

    /**
     * Called each time IconSet changes
     *
     * @param iconSet new IconSet
     */
    public void setIconSet(IconSet iconSet);

    void init(DBCPlugin plugin, DBCSession session);

    DBCPluginSessionConfig getConfig();

    public <T> T instantiate(Class<T> clazz) ;
    
    public Object getUserObject(String name);
    
    public void setUserObject(String name,Object value);
}