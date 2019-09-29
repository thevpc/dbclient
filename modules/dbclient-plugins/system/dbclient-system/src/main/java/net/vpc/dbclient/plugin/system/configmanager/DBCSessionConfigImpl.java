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

package net.vpc.dbclient.plugin.system.configmanager;

import net.vpc.dbclient.api.DBCApplication;
import net.vpc.dbclient.api.DBCSession;
import net.vpc.dbclient.api.configmanager.*;
import net.vpc.prs.plugin.Inject;
import net.vpc.prs.plugin.Initializer;
import net.vpc.dbclient.api.sql.util.SQLUtils;

import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 28 juil. 2006 16:11:53
 */
public class DBCSessionConfigImpl extends DBCAbstractConfig implements DBCSessionConfig {
    @Inject
    private DBCSession session;
    private int sesId = -1;
    private DBCSessionInfo info;
    private Vector<DBCConfigUpdatedListener> listeners;

    public DBCSessionConfigImpl() {

    }

    @Initializer
    public void init() {
        info = session.getInfo();
        sesId = info.getId();
    }

    public DBCSession getSession() {
        return session;
    }

    public DBCApplication getApplication() {
        return session.getApplication();
    }

    public DBCSessionInfo getSessionInfo() {
        return info;
    }

    public void reloadSessionInfo() {
        this.info = getApplication().getConfig().getSession(sesId);
    }

    public void updateSessionInfo(DBCSessionInfo data) {
        data.setId(sesId);
        getApplication().getConfig().updateSession(data);
        reloadSessionInfo();
        fireConfigUpdatedEvent();
    }

    public int getSessionId() {
        return getSessionInfo().getId();
    }

    public boolean isExcludedPath(String path) {
        DBCApplicationConfig config = getApplication().getConfig();
        return config.getPathValue(getSessionId(), path, "Excluded") != null;
    }

    public void clearExcludedPaths() {
        DBCApplicationConfig config = getApplication().getConfig();
        config.clearPathsValues(getSessionId(), "*", "Excluded");
        fireConfigUpdatedEvent();
    }

    public void setExcludedPath(String path, boolean exclude) {
        DBCApplicationConfig config = getApplication().getConfig();
        config.setPathValue(getSessionId(), path, "Excluded", exclude ? "Excluded" : null);
        fireConfigUpdatedEvent();
    }

    public String[] getExcludedPaths() {
        DBCApplicationConfig config = getApplication().getConfig();
        Set<String> strings = config.getPathsValues(getSessionId(), "Excluded").keySet();
        return strings.toArray(new String[strings.size()]);
    }


    public boolean isExpandedPath(String path) {
        DBCApplicationConfig config = getApplication().getConfig();
        return config.getPathValue(getSessionId(), path, "Expanded") != null;
    }

    public String[] getExpandedPaths() {
        DBCApplicationConfig config = getApplication().getConfig();
        Set<String> strings = config.getPathsValues(getSessionId(), "Expanded").keySet();
        return strings.toArray(new String[strings.size()]);
    }

    public void clearExpandedPaths() {
        DBCApplicationConfig config = getApplication().getConfig();
        config.clearPathsValues(getSessionId(), "*", "Expanded");
        fireConfigUpdatedEvent();
    }

    public void setExpandedPath(String path, boolean expand) {
        DBCApplicationConfig config = getApplication().getConfig();
        config.setPathValue(getSessionId(), path, "Expanded", expand ? "Expanded" : null);
        fireConfigUpdatedEvent();
    }

    public void setStringProperty(String name, String value) {
        getApplication().getConfig().setStringProperty(getSessionId(), name, value);
        fireConfigUpdatedEvent();
    }

    public String getStringProperty(String name, String defaultValue) {
        return getApplication().getConfig().getStringProperty(getSessionId(), name, defaultValue);
    }

    public Map<String, String> getPathsValues(String code) {
        return getApplication().getConfig().getPathsValues(getSessionId(), code);
    }

    public void clearPathsValues(String path, String code) {
        getApplication().getConfig().clearPathsValues(getSessionId(), path, code);
        fireConfigUpdatedEvent();
    }

    public void clearProperties(String pathPattern) {
        getApplication().getConfig().clearProperties(pathPattern);
        fireConfigUpdatedEvent();
    }


    public void setPathValue(String path, String code, String value) {
        getApplication().getConfig().setPathValue(getSessionId(), path, code, value);
        fireConfigUpdatedEvent();
    }

    public String getPathValue(String path, String code) {
        return getApplication().getConfig().getPathValue(getSessionId(), path, code);
    }

    public java.text.SimpleDateFormat getDateFormat() {
        String v = getStringProperty("ui.format.date", null);
        SimpleDateFormat dateFormat = null;
        try {
            dateFormat = v == null ? SQLUtils.DATE_FORMAT : new SimpleDateFormat(v);
        } catch (Exception e) {
            e.printStackTrace();
            dateFormat = SQLUtils.DATE_FORMAT;
        }
        return dateFormat;
    }

    public java.text.SimpleDateFormat getTimeFormat() {
        String v = getStringProperty("ui.format.time", null);
        SimpleDateFormat simpleDateFormat = null;
        try {
            simpleDateFormat = v == null ? SQLUtils.TIME_FORMAT : new SimpleDateFormat(v);
        } catch (Exception e) {
            e.printStackTrace();
            simpleDateFormat = SQLUtils.TIME_FORMAT;
        }
        return simpleDateFormat;
    }

    public java.text.SimpleDateFormat getTimestampFormat() {
        String v = getStringProperty("ui.format.timestamp", null);
        SimpleDateFormat dateFormat;
        try {
            dateFormat = v == null ? SQLUtils.TIMESTAMP_FORMAT : new SimpleDateFormat(v);
        } catch (Exception e) {
            e.printStackTrace();
            dateFormat = SQLUtils.TIMESTAMP_FORMAT;
        }
        return dateFormat;
    }


    public synchronized void addConfigUpdatedListener(DBCConfigUpdatedListener listener) {
        if (listeners == null) {
            listeners = new Vector<DBCConfigUpdatedListener>();
        }
        listeners.add(listener);
    }

    public void fireConfigUpdatedEvent() {
        fireConfigUpdatedEvent(null, null);
    }

    public void fireConfigUpdatedEvent(String property, Object value) {
        if (listeners != null) {
            DBCConfigUpdatedEvent event = null;
            for (DBCConfigUpdatedListener listener : listeners) {
                if (event == null) {
                    event = new DBCConfigUpdatedEvent(getApplication(), getSession(), property, value);
                }
                listener.configUpdated(event);
            }
        }
    }

}
