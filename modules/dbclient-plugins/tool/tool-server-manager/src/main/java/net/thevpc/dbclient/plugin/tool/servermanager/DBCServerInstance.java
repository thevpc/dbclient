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

import net.thevpc.common.prs.log.TLog;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 4 juin 2007 16:25:00
 */
public class DBCServerInstance {
    private String type;
    private DBCServerInfo serverInfo;
    private boolean stopped;
    private TLog log = TLog.NULL;
    private PropertyChangeSupport pcs;

    public DBCServerInstance(String type, DBCServerInfo serverInfo) {
        this.type = type;
        this.serverInfo = serverInfo;
        pcs = new PropertyChangeSupport(this);
    }

    public String getType() {
        return type;
    }

    public String toString() {
        return String.valueOf(serverInfo.getConfigName());
    }

    public boolean isStopped() {
        return stopped;
    }

    public void setStopped(boolean stopped) {
        boolean old = this.stopped;
        this.stopped = stopped;
        pcs.firePropertyChange("stopped", old, stopped);
    }

    public TLog getLog() {
        return log;
    }

    public void setLog(TLog log) {
        this.log = log;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void addPropertyChangeListener(String property, PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(property, listener);
    }

    public void removePropertyChangeListener(String property, PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(property, listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    public DBCServerInfo getServerInfo() {
        return serverInfo;
    }
}
