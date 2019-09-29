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

package net.vpc.dbclient.api.pluginmanager;

import net.vpc.dbclient.api.DBCApplication;
import net.vpc.dbclient.api.DBCSession;
import net.vpc.prs.plugin.Extension;
import net.vpc.prs.plugin.PluginManager;
import net.vpc.util.ProgressMonitor;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 15 nov. 2006 23:17:35
 */
@Extension(group = "manager")
public interface DBCPluginManager extends PluginManager<DBCApplication, DBCPlugin>, DBCPluggable {
    public void applicationInitializing(ProgressMonitor monitor);

    public void applicationConfiguring(ProgressMonitor monitor);

    public void applicationOpening(ProgressMonitor monitor);

    public void applicationReady(ProgressMonitor monitor);

    public DBCPluginSession createPluginSession(DBCPlugin plugin, DBCSession session);


}
