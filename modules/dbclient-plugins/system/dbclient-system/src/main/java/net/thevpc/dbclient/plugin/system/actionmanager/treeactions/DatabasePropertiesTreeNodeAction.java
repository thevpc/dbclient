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

import net.thevpc.dbclient.api.actionmanager.DBCActionLocation;
import net.thevpc.dbclient.api.actionmanager.DBCTreeNodeAction;
import net.thevpc.dbclient.api.sessionmanager.DBCInternalWindow;
import net.thevpc.dbclient.api.sessionmanager.DBCSessionView;
import net.thevpc.dbclient.api.sql.objects.DBObject;
import net.thevpc.dbclient.api.sql.objects.DBServer;
import net.thevpc.dbclient.plugin.system.sessionmanager.window.DBCDatabaseEditor;

import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class DatabasePropertiesTreeNodeAction extends DBCTreeNodeAction {
    public DatabasePropertiesTreeNodeAction() {
        super("Action.DatabasePropertiesNodeAction");
        addLocationPath(DBCActionLocation.POPUP, "/");
    }

    public boolean shouldEnable(DBObject activeNode, DBObject[] selectedNodes) {
        return (activeNode != null && activeNode instanceof DBServer);
    }

    public void actionPerformedImpl(ActionEvent e) {
        try {
            DBCInternalWindow w = getSession().getView().addWindow(
                    new DBCDatabaseEditor(getSession()), DBCSessionView.Side.Workspace, false
            );
            w.setTitle(getSession().getView().getMessageSet().get("DatabaseEditor.title"));
            w.setIcon(getIcon());
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
}
