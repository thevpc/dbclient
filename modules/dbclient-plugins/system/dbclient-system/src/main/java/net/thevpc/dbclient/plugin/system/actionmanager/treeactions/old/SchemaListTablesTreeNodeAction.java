package net.thevpc.dbclient.plugin.system.actionmanager.treeactions.old;

///**
// * ====================================================================
// *             DBClient yet another Jdbc client tool
// *
// * DBClient is a new Open Source Tool for connecting to jdbc
// * compliant relational databases. Specific extensions will take care of
// * each RDBMS implementation.
// *
// * Copyright (C) 2006-2008 Taha BEN SALAH
// *
// * This program is free software; you can redistribute it and/or modify
// * it under the terms of the GNU General Public License as published by
// * the Free Software Foundation; either version 2 of the License, or
// * (at your option) any later version.
// *
// * This program is distributed in the hope that it will be useful,
// * but WITHOUT ANY WARRANTY; without even the implied warranty of
// * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// * GNU General Public License for more details.
// *
// * You should have received a copy of the GNU General Public License along
// * with this program; if not, write to the Free Software Foundation, Inc.,
// * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
// * ====================================================================
// */
//package net.thevpc.dbclient.plugin.system.actionmanager.treeactions;
//
//import net.thevpc.dbclient.api.actionmanager.DBCActionLocation;
//import net.thevpc.dbclient.api.actionmanager.DBCResultTableAction;
//import net.thevpc.dbclient.api.actionmanager.DBCTreeNodeAction;
//import net.thevpc.dbclient.api.sessionmanager.*;
//import net.thevpc.dbclient.api.sql.DefaultClassFilter;
//import net.thevpc.dbclient.api.sql.SQLRecord;
//import net.thevpc.dbclient.api.sql.objects.DBObject;
//import net.thevpc.dbclient.api.sql.objects.DBSchema;
//import net.thevpc.dbclient.api.sql.objects.DBTable;
//import net.thevpc.dbclient.plugin.system.SystemUtils;
//
//import java.awt.event.ActionEvent;
//import java.sql.SQLException;
//import java.util.logging.Level;
//
//public class SchemaListTablesTreeNodeAction extends DBCTreeNodeAction {
//
//    public SchemaListTablesTreeNodeAction() {
//        super("Action.SchemaListTablesNodeAction");
//        addLocationPath(DBCActionLocation.POPUP, "/List");
//    }
//
//    public boolean shouldEnable(DBObject activeNode, DBObject[] selectedNodes) {
//        return (activeNode != null && activeNode instanceof DBSchema);
//    }
//
//    public void actionPerformedImpl(ActionEvent e) throws Throwable {
//        DBCResultTable table = getSession().getFactory().newInstance(DBCResultTable.class);
//        table.getModel().setTitle("Catalog");
//        final DBSchema node = (DBSchema) getSelectedNode();
//        final String catName = node.getCatalogName();
//        final String schemName = node.getName();
//        table.setSqlNodeProvider(new DBCResultTableSQLNodeProvider(table) {
//
//            public DBObject[] getNodes() {
//                try {
//                    SQLRecord record = getTable().getRecord(getTable().getSelectedRow());
//                    return getSession().getConnection().find(
//                            (String) record.get("TABLE_CAT"),
//                            (String) record.get("TABLE_SCHEM"),
//                            null,
//                            (String) record.get("TABLE_NAME"),
//                            null,
//                            new DefaultClassFilter(DBTable.class),
//                            null);
//                } catch (SQLException ex) {
//                    getSession().getLogger(getClass().getName()).log(Level.SEVERE,"Find Failed", ex);
//                    throw new IllegalArgumentException(ex);
//                }
//            }
//        });
//        table.setRefreshAction(new DBCResultTableAction("Refresh", getPluginSession()) {
//
//            @Override
//            public void actionPerformedImpl(ActionEvent e) throws Throwable {
//                DBCTableModel model = getResultTable().getModel();
//                model.displayQuery(getSession().getConnection().getMetaData().getTables(
//                        catName != null && catName.length() == 0 ? null : catName,
//                        schemName != null && schemName.length() == 0 ? null : schemName,
//                        "%",
//                        null), null);
//            }
//        });
//        table.refresh();
//        DBCInternalWindow window = getSession().getView().addWindow(table, DBCSessionView.Side.Workspace, true);
//        SystemUtils.configureResultTableWindow(window, this);
//    }
//}
