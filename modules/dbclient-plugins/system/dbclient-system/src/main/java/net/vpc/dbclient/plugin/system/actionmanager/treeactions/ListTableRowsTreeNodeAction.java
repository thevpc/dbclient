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

package net.vpc.dbclient.plugin.system.actionmanager.treeactions;

import net.vpc.dbclient.api.actionmanager.DBCActionLocation;
import net.vpc.dbclient.api.actionmanager.DBCResultTableAction;
import net.vpc.dbclient.api.actionmanager.DBCTreeNodeAction;
import net.vpc.dbclient.api.sessionmanager.DBCInternalWindow;
import net.vpc.dbclient.api.sessionmanager.DBCResultTable;
import net.vpc.dbclient.api.sessionmanager.DBCSessionView;
import net.vpc.dbclient.api.sessionmanager.DBCTableModel;
import net.vpc.dbclient.api.sql.objects.DBObject;
import net.vpc.dbclient.api.sql.objects.DBTable;
import net.vpc.dbclient.plugin.system.SystemUtils;

import java.awt.event.ActionEvent;

public class ListTableRowsTreeNodeAction extends DBCTreeNodeAction {

    public ListTableRowsTreeNodeAction() {
        super("Action.ListTableRowsTreeNodeAction");
        addLocationPath(DBCActionLocation.POPUP, "/");
        addLocationPath(DBCActionLocation.POPUP, "/List");
    }

    public boolean shouldEnable(DBObject activeNode, DBObject[] selectedNodes) {
        return (activeNode != null && activeNode instanceof DBTable);
    }

    public void actionPerformedImpl(ActionEvent e) throws Throwable {
        DBCResultTable table = getSession().getFactory().newInstance(DBCResultTable.class);
        table.getModel().setTitle("Table");
        final DBTable object = (DBTable) getSelectedNode();

        table.setRefreshAction(new DBCResultTableAction("Refresh", getPluginSession()) {
            @Override
            public void actionPerformedImpl(ActionEvent e) throws Throwable {
                DBCTableModel model = getResultTable().getModel();
                model.executeStatement("Select * from " + object.getFullName());
            }
        });
        table.refresh();

        DBCInternalWindow window = getSession().getView().addWindow(table, DBCSessionView.Side.Workspace, true);
        SystemUtils.configureResultTableWindow(window, this);
        window.setTitle(getName() + " : " + object.getName());
    }
}
