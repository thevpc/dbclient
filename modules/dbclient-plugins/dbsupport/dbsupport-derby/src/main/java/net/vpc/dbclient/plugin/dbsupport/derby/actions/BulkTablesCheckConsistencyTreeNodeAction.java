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

package net.vpc.dbclient.plugin.dbsupport.derby.actions;

import java.sql.DatabaseMetaData;
import net.vpc.dbclient.api.DBCSession;
import net.vpc.dbclient.api.actionmanager.DBCActionLocation;
import net.vpc.dbclient.api.actionmanager.DBCResultTableAction;
import net.vpc.dbclient.api.actionmanager.DBCTreeNodeAction;
import net.vpc.dbclient.api.sessionmanager.DBCInternalWindow;
import net.vpc.dbclient.api.sessionmanager.DBCResultTable;
import net.vpc.dbclient.api.sessionmanager.DBCSessionView;
import net.vpc.dbclient.api.sessionmanager.DBCTableModel;
import net.vpc.dbclient.api.sql.ColumnMetaData;
import net.vpc.dbclient.api.sql.DBCConnection;
import net.vpc.dbclient.api.sql.DefaultResultSetMetaData;
import net.vpc.dbclient.api.sql.ListResultSet;
import net.vpc.dbclient.api.sql.objects.DBCatalog;
import net.vpc.dbclient.api.sql.objects.DBObject;
import net.vpc.dbclient.api.sql.objects.DBSchema;
import net.vpc.dbclient.api.sql.objects.DBTableFolder;
import net.vpc.dbclient.plugin.dbsupport.derby.DerbyConnection;
import net.vpc.dbclient.plugin.system.SystemUtils;
import net.vpc.swingext.dialog.MessageDialogType;

import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BulkTablesCheckConsistencyTreeNodeAction extends DBCTreeNodeAction {

    public BulkTablesCheckConsistencyTreeNodeAction() {
        super("Action.BulkTablesCheckConsistencyNodeAction");
        addLocationPath(DBCActionLocation.POPUP, "/Admin");
    }

    public boolean shouldEnable(DBObject activeNode, DBObject[] selectedNodes) {
        return (activeNode != null &&
                (
                        activeNode instanceof DBSchema
                                || activeNode instanceof DBCatalog
                                || activeNode instanceof DBTableFolder
                )
        );
    }

    @Override
    public boolean acceptSession(DBCSession session) {
        try {
            return session.getConnection() instanceof DerbyConnection;
        } catch (SQLException e) {
            return false;
        }
    }

    public void actionPerformedImpl(ActionEvent e) throws Throwable {
        DBCResultTable table = getSession().getFactory().newInstance(DBCResultTable.class);
        table.getModel().setTitle("Schema");
        final DBObject node = getSelectedNode();
        table.setRefreshAction(new DBCResultTableAction("Refresh", getPluginSession()) {
            @Override
            public void actionPerformedImpl(ActionEvent e) throws Throwable {
                refreshResultTable(getResultTable(), node);
            }
        });
        table.refresh();
        DBCInternalWindow window = getSession().getView().addWindow(table, DBCSessionView.Side.Workspace, true);
        SystemUtils.configureResultTableWindow(window, this);
    }

    private void refreshResultTable(DBCResultTable table, DBObject node) throws Throwable {
        DBCTableModel model = table.getModel();
        List<Object[]> values = new ArrayList<Object[]>();

        DBCConnection c = getSession().getConnection();
        if (c instanceof DerbyConnection) {
            DerbyConnection dc = (DerbyConnection) c;
            ResultSet rs = null;
            try {
                final String catName = node.getCatalogName();
                final String schemName = node.getSchemaName();
                final DatabaseMetaData metaData = c.getMetaData();
                if (node instanceof DBSchema) {
                    rs = metaData.getTables(
                            catName != null && catName.length() == 0 ? null : catName,
                            schemName != null && schemName.length() == 0 ? null : schemName,
                            "%",
                            null
                    );
                } else if (node instanceof DBCatalog) {
                    rs = metaData.getTables(
                            catName != null && catName.length() == 0 ? null : catName,
                            null,
                            "%",
                            null
                    );
                } else if (node instanceof DBTableFolder) {
                    rs = metaData.getTables(
                            catName != null && catName.length() == 0 ? null : catName,
                            schemName != null && schemName.length() == 0 ? null : schemName,
                            "%",
                            new String[]{((DBTableFolder) node).getTypeName()}
                    );
                }
                if (rs != null) {
                    while (rs.next()) {
                        String cn = rs.getString("TABLE_CAT");
                        String sn = rs.getString("TABLE_SCHEM");
                        String tn = rs.getString("TABLE_NAME");
                        boolean b = false;
                        try {
                            b = dc.getFeatureSYSCSUTIL().SYSCS_CHECK_TABLE(cn, sn, tn);
                        } catch (SQLException e) {
                            //ignore
                        }
                        values.add(new Object[]{cn, sn, tn, b});
                    }
                }
            } finally {
                if (rs != null) {
                    rs.close();
                }
            }
            DefaultResultSetMetaData rsmd = new DefaultResultSetMetaData();
            ColumnMetaData catColumn = new ColumnMetaData("Catalog", String.class);
            ColumnMetaData schemColumn = new ColumnMetaData("Schema", String.class);
            ColumnMetaData tableColumn = new ColumnMetaData("Table", String.class);
            ColumnMetaData validColumn = new ColumnMetaData("Valid", Boolean.class);
            rsmd.addColumn(catColumn);
            rsmd.addColumn(schemColumn);
            rsmd.addColumn(tableColumn);
            rsmd.addColumn(validColumn);
            ListResultSet listResultSet = new ListResultSet(null, rsmd, values);
            model.displayQuery(listResultSet, null);
        } else {
            getSession().getView().getDialogManager().showMessage(null, "Not recognized as DerbyConnection", MessageDialogType.ERROR, "Consistency Check");
        }
    }
}