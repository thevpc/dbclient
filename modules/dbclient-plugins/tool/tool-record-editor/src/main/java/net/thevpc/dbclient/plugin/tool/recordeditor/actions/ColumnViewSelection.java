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

//package net.thevpc.dbclient.plugin.tool.recordeditor.actions;
//
//import net.thevpc.dbclient.api.actions.DBCActionLocation;
//import net.thevpc.dbclient.plugin.tool.recordeditor.RecordEditorPluginSession;
//import net.thevpc.dbclient.plugin.tool.recordeditor.propeditor.ColumnView;
//import net.thevpc.dbclient.session.window.tree.actions.TreeNodeAction;
//import net.thevpc.dbclient.api.sql.objects.DBObject;
//import net.thevpc.dbclient.api.sql.objects.TableColumn;
//import net.thevpc.common.prs.PRSManager;
//
//import java.awt.event.ActionEvent;
//
//public class ColumnViewSelection extends TreeNodeAction {
//    private ColumnView colView;
//
//    public ColumnViewSelection(ColumnView colView, RecordEditorPluginSession plugin) {
//        super("Action.ColumnViewSelection." + colView.getClass().getSimpleName(), plugin);
//        this.colView = colView;
//        setLocationId(DBCActionLocation.POPUP, "ColumnViewFolder");
//
//    }
//
//
//    public boolean shouldEnable(DBObject activeNode, DBObject[] selectedNodes) {
//        return (
//                activeNode != null
//                        && activeNode instanceof TableColumn
//                        && colView.accept((TableColumn) activeNode)
//        );
//    }
//
//    public void actionPerformedImpl(ActionEvent e) throws Throwable {
//        getSession().getConfig().setPathValue(getSelectedNode().getStringPath(), "ColumnView", colView.getClass().getName());
//        ColumnView newView = ((RecordEditorPluginSession)getPluginSession()).createColumnView((TableColumn) getSelectedNode());
//        if (newView.isConfigurable()) {
//            newView.showConfig();
//        }
//    }
//
//}
