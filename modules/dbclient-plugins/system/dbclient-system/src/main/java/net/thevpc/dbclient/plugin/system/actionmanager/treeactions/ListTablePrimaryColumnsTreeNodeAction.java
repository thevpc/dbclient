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
import net.thevpc.dbclient.api.actionmanager.DBCResultTableAction;
import net.thevpc.dbclient.api.actionmanager.DBCTreeNodeAction;
import net.thevpc.dbclient.api.sessionmanager.DBCInternalWindow;
import net.thevpc.dbclient.api.sessionmanager.DBCResultTable;
import net.thevpc.dbclient.api.sessionmanager.DBCSessionView;
import net.thevpc.dbclient.api.sessionmanager.DBCTableModel;
import net.thevpc.dbclient.api.sql.objects.DBObject;
import net.thevpc.dbclient.plugin.system.SystemUtils;

import java.awt.event.ActionEvent;
import net.thevpc.dbclient.plugin.system.sql.SystemSQLUtils;
import net.thevpc.dbclient.plugin.system.sql.SystemSQLUtils.TableColumnPath;

public class ListTablePrimaryColumnsTreeNodeAction extends DBCTreeNodeAction {

    public ListTablePrimaryColumnsTreeNodeAction() {
        super("Action.ListTablePrimaryColumnsTreeNodeAction");
        addLocationPath(DBCActionLocation.POPUP, "/List");
    }

    public boolean shouldEnable(DBObject activeNode, DBObject[] selectedNodes) {
        return (activeNode != null && SystemSQLUtils.isTableDown(activeNode));
    }

    public void actionPerformedImpl(ActionEvent e) throws Throwable {
        DBCResultTable table = getSession().getFactory().newInstance(DBCResultTable.class);
        table.getModel().setTitle("Table");
        DBObject object = getSelectedNode();
        final TableColumnPath cp = SystemSQLUtils.getTableColumnPath(object);
        cp.nullifyNames();
        table.setRefreshAction(new DBCResultTableAction("Refresh", getPluginSession()) {
            @Override
            public void actionPerformedImpl(ActionEvent e) throws Throwable {
                DBCTableModel model = getResultTable().getModel();
                model.displayQuery(getSession().getConnection().getMetaData().getPrimaryKeys(
                        cp.getCatalogName(),
                        cp.getSchemaName(),
                        cp.getTableName()
                ), null);

            }
        });
        table.refresh();
        DBCInternalWindow window = getSession().getView().addWindow(table, DBCSessionView.Side.Workspace, true);
        SystemUtils.configureResultTableWindow(window, this);
        window.setTitle(getName() + " : " + object.getName());
    }
}
