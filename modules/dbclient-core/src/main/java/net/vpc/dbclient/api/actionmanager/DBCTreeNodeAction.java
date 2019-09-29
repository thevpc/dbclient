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

package net.vpc.dbclient.api.actionmanager;

import net.vpc.dbclient.api.sessionmanager.DBCSessionExplorer;
import net.vpc.dbclient.api.sql.objects.DBObject;

import javax.swing.*;
import java.sql.SQLException;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 2006/11/07
 */
public abstract class DBCTreeNodeAction extends DBCSessionAction {
    private static final DBObject[] DB_OBJECTS = new DBObject[0];
    private DBObject[] selectedNodes;

    public DBCTreeNodeAction(String id) {
        super(id);
    }

    public boolean setSelectedNodes(DBObject[] selection) {
        this.selectedNodes = selection;
        return shouldEnable(selection.length == 0 ? null : selection[0], selection);
    }

    public boolean shouldEnable(DBObject activeNode, DBObject[] selectedNodes) {
        return true;
    }

    public void executeAction(DBObject[] selection) throws Throwable {
        setSelectedNodes(selection);
        actionPerformedImpl(null);
    }


    public DBObject getSelectedNode() {
        return selectedNodes == null || selectedNodes.length == 0 ? null : selectedNodes[0];
    }

    public DBObject[] getSelectedNodes() {
        return selectedNodes == null ? DB_OBJECTS : selectedNodes;
    }

    public DBCSessionExplorer getTree() throws SQLException {
        return getSession().getView().getExplorer();
    }


    public String getNodeActionName() {
        return getSelectedNode().getFullName() + " : " + getActionName();
    }

    public Icon getNodeActionIcon() {
        return getSession().getView().getObjectIcon(getSelectedNode());
    }
}
