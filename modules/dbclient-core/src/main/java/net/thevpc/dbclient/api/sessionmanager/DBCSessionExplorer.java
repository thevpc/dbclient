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

package net.thevpc.dbclient.api.sessionmanager;

import net.thevpc.dbclient.api.DBCSession;
import net.thevpc.common.prs.plugin.Extension;
import net.thevpc.dbclient.api.sql.objects.DBObject;
import net.thevpc.dbclient.api.sql.objects.DBServer;
import net.thevpc.dbclient.api.viewmanager.DBCComponent;
import net.thevpc.dbclient.api.viewmanager.DBCTree;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 16 fevr. 2006 10:27:30
 */
@Extension(group = "ui.session")
public interface DBCSessionExplorer extends DBCComponent {
    public static final String PROPERTY_STATUS_TEXT = "PROPERTY_STATUS_TEXT";
    public static final String PROPERTY_EXPANSION_STATUS = "PROPERTY_EXPANSION_STATUS";

    public DBCSession getSession();

    public void reloadStructure();

    public DBObject getCurrentNode();

    public DBObject[] getCurrentNodes();

    public DBCTree getTreeComponent();

    public DBServer getServerNode();

    public void saveView();

    DBCSQLNodeProvider getSqlNodeProvider();
}
