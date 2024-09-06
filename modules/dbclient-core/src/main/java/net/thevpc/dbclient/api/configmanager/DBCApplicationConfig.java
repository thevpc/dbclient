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

package net.thevpc.dbclient.api.configmanager;

import net.thevpc.dbclient.api.DBCApplication;
import net.thevpc.dbclient.api.pluginmanager.DBCPluggable;
import net.thevpc.common.prs.messageset.MessageSetBundle;
import net.thevpc.common.prs.plugin.Extension;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Map;

/**
 * Configuration Manager for Application.
 * <p/>
 * All Preferences are handled (retrieved/stored) here.
 *
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 13 juil. 2005 14:32:52
 */
@Extension(group = "manager")
public interface DBCApplicationConfig extends DBCConfig, DBCPluggable {
    /*
     * When no templates are used. Actually this is the default
     */
    public static int SESSION_TEMPLATE_NONE = -1;

    /**
     * initialize Configuration Manager and install it if not yet.
     * Should call
     * <pre>
     *  configManager.setStringProperty("configVersion", DBClientVersion.CONFIG_VERSION);
     * </pre>
     * if one have to (re)install the configuration manager
     *
     * @throws net.thevpc.dbclient.api.configmanager.DBCConfigException
     *
     */
    public void init() throws DBCConfigException;

    /**
     * Returnd DBClient Instance
     *
     * @return DBClient Instance
     */
    public DBCApplication getApplication();

    /**
     * Called to free all config resources on DBClient closing.
     *
     * @throws net.thevpc.dbclient.api.configmanager.DBCConfigException
     *
     */
    public void dispose() throws DBCConfigException;

    /**
     * get Session information by Id
     *
     * @param id session Id
     * @return DBCSessionInfo session information
     * @throws net.thevpc.dbclient.api.configmanager.DBCConfigException
     *
     */
    public abstract DBCSessionInfo getSession(int id) throws DBCConfigException;

    /**
     * Retrieve All registrered Drivers
     *
     * @return All registrered Drivers
     * @throws net.thevpc.dbclient.api.configmanager.DBCConfigException
     *
     */
    public DBCDriverInfo[] getDrivers() throws DBCConfigException;

    /**
     * add/register new Driver
     *
     * @param desc driver info
     * @throws net.thevpc.dbclient.api.configmanager.DBCConfigException
     *
     */
    public void addDriver(DBCDriverInfo desc) throws DBCConfigException;

    /**
     * update driver information.
     * id should be set in the desc object. Only set attributes ( setXXX(..)) will be updated
     *
     * @param desc new values
     * @throws net.thevpc.dbclient.api.configmanager.DBCConfigException
     *
     */
    public void updateDriver(DBCDriverInfo desc) throws DBCConfigException;

    /**
     * remove PERMANENTALY the driver by Id
     *
     * @param driverId driver Id
     * @throws net.thevpc.dbclient.api.configmanager.DBCConfigException
     *
     */
    public void removeDriver(int driverId) throws DBCConfigException;

    /**
     * Retrive All Sessions
     *
     * @return
     */
    public DBCSessionInfo[] getSessions();

    /**
     * remove PERMANENTALY the Session by Id
     *
     * @param sessionId Session Id
     * @throws net.thevpc.dbclient.api.configmanager.DBCConfigException
     *
     */
    public void removeSession(int sessionId);

    public void updateSession(DBCSessionInfo info);

    public void addSession(DBCSessionInfo info, int sessionTemplate);


    public void setStringProperty(int sessionId, String name, String value);

    public String getStringProperty(int sessionId, String name, String defaultValue);

    public Map<String, String> getPathsValues(int sessionId, String code);

    public void clearPathsValues(int sessionId, String path, String code);

    public void setPathValue(int sessionId, String path, String code, String value);

    public String getPathValue(int sessionId, String path, String code);


    public DBCConfigPropInfo[] getStringProperties();

    public String getMessage(String id, String locale);

    public void removeMessage(String id, String locale);

    public void updateMessage(DBCMessageInfo msg);

    public void addMessage(DBCMessageInfo msg);

    public DBCMessageInfo[] getMessages();

    public MessageSetBundle getMessageSetBundle();

    public void updateAll(URL resource, DBCIfFoundAction ifFoundAction, DBCIfNotFoundAction ifNotFoundAction) throws IOException, IllegalArgumentException;

    public void updateAll(Collection v, DBCIfFoundAction ifFoundAction, DBCIfNotFoundAction ifNotFoundAction) throws IllegalArgumentException;

    public void clearProperties(String namePattern);

    public void clearProperties(int sessionId, String namePattern);


    public boolean isSplashScreenEnabled();

    public void setSplashScreenEnabled(boolean enabled);
}
