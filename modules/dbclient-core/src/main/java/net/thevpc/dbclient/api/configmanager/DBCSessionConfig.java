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

import net.thevpc.dbclient.api.DBCSession;
import net.thevpc.dbclient.api.pluginmanager.DBCPluggable;
import net.thevpc.common.prs.plugin.Extension;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 13 juil. 2005 14:32:52
 */
@Extension(group = "core")
public interface DBCSessionConfig extends DBCConfig, DBCPluggable {
    DBCSession getSession();

    void updateSessionInfo(DBCSessionInfo data);

    public int getSessionId();

    public DBCSessionInfo getSessionInfo();

    public void setExcludedPath(String path, boolean exclude);

    public boolean isExcludedPath(String path);

    public void clearExcludedPaths();

    public boolean isExpandedPath(String path);

    public String[] getExpandedPaths();

    public void clearExpandedPaths();

    public void setExpandedPath(String path, boolean exclude);

    java.text.SimpleDateFormat getDateFormat();

    java.text.SimpleDateFormat getTimeFormat();

    java.text.SimpleDateFormat getTimestampFormat();

    public void addConfigUpdatedListener(DBCConfigUpdatedListener listener);
}
