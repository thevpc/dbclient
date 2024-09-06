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
import net.thevpc.dbclient.api.sessionmanager.DBCSQLEditor;
import net.thevpc.dbclient.api.sessionmanager.DBCSessionView;
import net.thevpc.dbclient.api.sql.DBCConnection;
import net.thevpc.dbclient.api.sql.objects.DBObject;
import net.thevpc.common.swing.prs.PRSManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 17 mai 2006 23:43:16
 */
public class ShowDDLTreeNodeAction extends DBCTreeNodeAction {

    public ShowDDLTreeNodeAction() {
        super("Action.ShowDDLNodeAction");
        addLocationPath(DBCActionLocation.POPUP, "/sql-ddl");
    }

    public boolean shouldEnable(DBObject activeNode, DBObject[] selectedNodes) {
        try {
            return (activeNode != null
                    && getSession().getConnection().isSQLCreateObjectSupported(activeNode.getType()));
        } catch (SQLException ex) {
            getSession().getLogger(getClass().getName()).log(Level.SEVERE,"ShouldEnable Failed", ex);
            return false;
        }
    }

    public void actionPerformedImpl(ActionEvent e) throws Throwable {
        DBCConnection cnx = getSession().getConnection();
        for (DBObject w : getSelectedNodes()) {
            final DBCSQLEditor p = getSession().getFactory().newInstance(DBCSQLEditor.class);
            StringBuilder sb = new StringBuilder();
            sb.append(cnx.getSQLCreateObject(
                    w.getCatalogName(),
                    w.getSchemaName(),
                    w.getParentName(), w.getName(),
                    w.getType(),
                    null));
            sb.append("\n");
            String sql = null;
            try {
                sql = cnx.getSQLConstraints(w.getCatalogName(), w.getSchemaName(), w.getParentName(), w.getName(), w.getType(), null);
            } catch (Exception ex) {
                getSession().getLogger(getClass().getName()).log(Level.SEVERE,"getSQLConstraints Failed", ex);
            }
            String goKeyword = cnx.getSQLGoKeyword();
            if (sql != null) {
                if (goKeyword != null) {
                    sb.append(goKeyword).append("\n");
                }
                sb.append(sql);
                if (goKeyword != null) {
                    sb.append(goKeyword).append("\n");
                }
            }

            p.setText(sb.toString());
            JScrollPane jsp = new JScrollPane(p.getComponent());
            p.setEditable(false);
            jsp.setBorder(null);
//            DBCInternalWindowImpl tabFrame = new DBCInternalWindowImpl(
//                    getSession().getMessageSet().get("ObjectProperties.title", new Object[]{getSelectedNode().getType(),getSelectedNode().getName()}),
//                    jsp, getSession()
//                    ,(Icon) getValue(Action.SMALL_ICON)
//
//            );
            JPanel panel = new JPanel(new BorderLayout());
            panel.add(jsp, BorderLayout.CENTER);

            DBCInternalWindow win = getSession().getView().addWindow(
                    panel, DBCSessionView.Side.Workspace, false);
            win.setTitle(w.getName() + " : " + getName());

            PRSManager.update(win.getComponent(), getSession().getView());
        }
    }
}
