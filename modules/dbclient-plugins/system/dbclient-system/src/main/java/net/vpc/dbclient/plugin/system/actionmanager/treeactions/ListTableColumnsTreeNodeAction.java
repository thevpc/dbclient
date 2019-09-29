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
import net.vpc.dbclient.api.sessionmanager.*;
import net.vpc.dbclient.api.sql.DefaultClassFilter;
import net.vpc.dbclient.api.sql.SQLRecord;
import net.vpc.dbclient.api.sql.objects.DBObject;
import net.vpc.dbclient.api.sql.objects.DBTableColumn;
import net.vpc.dbclient.plugin.system.SystemUtils;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import net.vpc.dbclient.plugin.system.sql.SystemSQLUtils;
import net.vpc.dbclient.plugin.system.sql.SystemSQLUtils.TableColumnPath;

public class ListTableColumnsTreeNodeAction extends DBCTreeNodeAction {

    public ListTableColumnsTreeNodeAction() {
        super("Action.ListTableColumnsTreeNodeAction");
        addLocationPath(DBCActionLocation.POPUP, "/List");
    }

    public boolean shouldEnable(DBObject activeNode, DBObject[] selectedNodes) {
        return (activeNode != null && SystemSQLUtils.isTableColumnFolderUp(activeNode));
    }

    public void actionPerformedImpl(ActionEvent e) throws Throwable {
        DBCResultTable table = getSession().getFactory().newInstance(DBCResultTable.class);
        table.getModel().setTitle("Table");
        final DBObject selectedNode = getSelectedNode();
        final TableColumnPath cp = SystemSQLUtils.getTableColumnPath(selectedNode);
        cp.nullifyNames();
        table.setSqlNodeProvider(new DBCResultTableSQLNodeProvider(table) {
            public DBObject[] getNodes() {
                try {
                    SQLRecord record = getTable().getRecord(getTable().getSelectedRow());
                    return getSession().getConnection().find(
                            (String) record.get("TABLE_CAT"),
                            (String) record.get("TABLE_SCHEM"),
                            (String) record.get("TABLE_NAME"),
                            (String) record.get("COLUMN_NAME"),
                            null,
                            new DefaultClassFilter(DBTableColumn.class),
                            null
                    );
                } catch (SQLException e1) {
                    getSession().getLogger(getClass().getName()).log(Level.SEVERE, "getNodes Failed",e1);
                    throw new IllegalArgumentException(e1);
                }
            }
        });

        table.setRefreshAction(new DBCResultTableAction("Refresh", getPluginSession()) {
            @Override
            public void actionPerformedImpl(ActionEvent e) throws Throwable {
                DBCTableModel model = getResultTable().getModel();
                model.displayQuery(getSession().getConnection().getMetaData().getColumns(
                        cp.getCatalogName(),
                        cp.getSchemaName(),
                        cp.getTableName(),
                        "%"
                ), null);
            }
        });
        table.refresh();

        DBCInternalWindow window = getSession().getView().addWindow(table, DBCSessionView.Side.Workspace, true);
        SystemUtils.configureResultTableWindow(window, this);
        window.setTitle(getName() + " : " + selectedNode.getName());
    }
}
