/**
 * 
====================================================================
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
 * 
====================================================================
 */

package net.vpc.dbclient.plugin.tool.searchengine.actions;

import net.vpc.dbclient.api.actionmanager.DBCActionLocation;
import net.vpc.dbclient.api.actionmanager.DBCTreeNodeAction;
import net.vpc.dbclient.api.sessionmanager.DBCInternalWindow;
import net.vpc.dbclient.api.sessionmanager.DBCSessionView;
import net.vpc.dbclient.plugin.tool.searchengine.SearchEnginePluginSession;
import net.vpc.dbclient.plugin.tool.searchengine.SearchPanel;
import net.vpc.dbclient.api.sql.objects.DBObject;
import net.vpc.swingext.SwingUtilities3;

import java.awt.event.ActionEvent;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 31 janv. 2006 15:47:00
 */
public class SearchTreeNodeAction extends DBCTreeNodeAction {
    public SearchTreeNodeAction() {
        super("Action.SearchAction");
        addLocationPath(DBCActionLocation.MENUBAR, "/tools");
        addLocationPath(DBCActionLocation.POPUP, "/");
        setIconId(null);
        setIconId("Find");
    }

    public void actionPerformedImpl(ActionEvent e) throws Throwable {
        DBObject selectedNode = getSelectedNode();
        final SearchPanel searchPanel = new SearchPanel(
                selectedNode == null ? null : selectedNode.getCatalogName(),
                selectedNode == null ? null : selectedNode.getSchemaName(),
                (SearchEnginePluginSession)getPluginSession()
        );
        SwingUtilities3.invokeLater(new Runnable() {
            public void run() {
                DBCInternalWindow w = getSession().getView().addWindow(
                        searchPanel, DBCSessionView.Side.Workspace, false
                );
                w.setTitle(getMessageSet().get("SearchEngine.Title"));

            }
        });
    }

}