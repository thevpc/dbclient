/**
 * <pre>
 * ====================================================================
 *             DBClient yet another Jdbc client tool
 * <p/>
 * DBClient is a new Open Source Tool for connecting to jdbc
 * compliant relational databases. Specific extensions will take care of
 * each RDBMS implementation.
 * <p/>
 * Copyright (C) 2006-2008 Taha BEN SALAH
 * <p/>
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 * ====================================================================
 * </pre>
 *
 */
package net.vpc.dbclient.plugin.toolbox.actions.tree;

import net.vpc.dbclient.api.actionmanager.DBCActionLocation;
import net.vpc.dbclient.api.actionmanager.DBCTreeNodeAction;
import net.vpc.dbclient.api.sql.DBCConnection;
import net.vpc.dbclient.api.sql.objects.DBObject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 17 mai 2006 23:43:16
 */
public class SQLRenameTreeNodeAction extends DBCTreeNodeAction {

    public SQLRenameTreeNodeAction() {
        super("Action.SQLRenameNodeAction");
        addLocationPath(DBCActionLocation.POPUP, "/");
        addLocationPath(DBCActionLocation.MENUBAR, "/edit");
//        super("Properties");
    }

    public boolean shouldEnable(DBObject activeNode, DBObject[] selectedNodes) {
        try {
            return (activeNode != null
                    && getSession().getConnection().getFeatureGenerateSQLRename().isSupported(activeNode.getType()));
        } catch (SQLException ex) {
            getSession().getLogger(getClass().getName()).log(Level.SEVERE,"ShouldEnable Failed", ex);
            return false;
        }
    }

    public void actionPerformedImpl(ActionEvent e) throws Throwable {
        DBCConnection cnx = getSession().getConnection();
        boolean someChanges = false;
        for (DBObject dbObject : getSelectedNodes()) {
            String n = JOptionPane.showInputDialog(
                    getSession().getView().getMainComponent(),
                    "Rename " + dbObject.getFullName() + " to ...", "Rename", JOptionPane.QUESTION_MESSAGE);
            if (n != null) {
                if (cnx.getFeatureGenerateSQLRename().isSupported(dbObject.getType())) {
                    cnx.executeScript(cnx.getFeatureGenerateSQLRename().getSQL(
                            dbObject.getCatalogName(),
                            dbObject.getSchemaName(),
                            dbObject.getParentName(), dbObject.getName(),
                            n, dbObject.getType()));
                    someChanges = true;
                }
            }
        }
        if (someChanges) {
            getSession().getView().refreshView();
        }
    }
}
