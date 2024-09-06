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

package net.thevpc.dbclient.plugin.system.actionmanager.treeactions;

import net.thevpc.dbclient.api.DBCSession;
import net.thevpc.dbclient.api.actionmanager.DBCActionLocation;
import net.thevpc.dbclient.api.actionmanager.DBCTreeNodeAction;
import net.thevpc.dbclient.api.sql.objects.DBObject;

import java.awt.event.ActionEvent;
import net.thevpc.dbclient.api.sql.objects.DBCatalog;

public class CatalogSetTreeNodeAction extends DBCTreeNodeAction {

    public CatalogSetTreeNodeAction() {
        super("Action.CatalogSetTreeNodeAction");
        addLocationPath(DBCActionLocation.POPUP, "/");
    }

    public boolean shouldEnable(DBObject activeNode, DBObject[] selectedNodes) {
        return (activeNode != null && activeNode instanceof DBCatalog && activeNode.getName()!=null && activeNode.getName().trim().length()>0);
    }

    public void actionPerformedImpl(ActionEvent e) throws Throwable {
        final DBCatalog object = (DBCatalog) getSelectedNode();
        final DBCSession _session = getSession();
        _session.setCatalog(object.getName());
    }
}
