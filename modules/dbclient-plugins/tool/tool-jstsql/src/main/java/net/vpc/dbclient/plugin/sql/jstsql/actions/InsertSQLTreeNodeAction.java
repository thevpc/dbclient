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

package net.vpc.dbclient.plugin.sql.jstsql.actions;

import net.vpc.dbclient.api.actionmanager.DBCActionLocation;
import net.vpc.dbclient.api.actionmanager.DBCTreeNodeAction;
import net.vpc.dbclient.api.pluginmanager.DBCPluginSession;
import net.vpc.dbclient.api.sessionmanager.DBCInternalWindow;
import net.vpc.dbclient.api.sessionmanager.DBCSQLCommandPane;
import net.vpc.dbclient.api.sessionmanager.DBCSessionView;
import net.vpc.dbclient.api.sql.objects.DBObject;
import net.vpc.dbclient.api.viewmanager.DBCChunkOperation;
import net.vpc.dbclient.api.viewmanager.DBCChunkOperationVar;
import net.vpc.dbclient.api.viewmanager.DBCChunkSwingWorker;
import net.vpc.swingext.dialog.MessageDialogType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InsertSQLTreeNodeAction extends DBCTreeNodeAction implements ActionListener {
    private TreeSQLGenerator treeSQLGenerator;

    public InsertSQLTreeNodeAction(DBCPluginSession pluginSession, TreeSQLGenerator treeSQLGenerator, boolean popup, boolean toolbar) {
        super("Action.InsertSQLNodeAction." + treeSQLGenerator.getName());
        setPluginSession(pluginSession);
        //not to show override message
        setMessageId(null);
        setIconId(null);
        setMessageId("Action." + treeSQLGenerator.getName());
        setIconId("Action.InsertSQLNodeAction");
        this.treeSQLGenerator = treeSQLGenerator;
        if (popup) {
            addLocationPath(DBCActionLocation.POPUP, "/sql-ddl/SQLInsert");
        }
        if (toolbar) {
            addLocationPath(DBCActionLocation.TOOLBAR, "/");
        }
    }

    @Override
    public void putValue(String key, Object newValue) {
        if (key != null && Action.NAME.equals(key) && getMessageId() != null && getMessageId().equals(newValue)) {
            String s = treeSQLGenerator.getDefaultName();
            if (s != null) {
                newValue = s;
            }
        }
        super.putValue(key, newValue);
    }

    public boolean shouldEnable(DBObject activeNode, DBObject[] selectedNodes) {
        return (this.treeSQLGenerator.isEnabled(activeNode, selectedNodes));
    }

    public void actionPerformedImpl(ActionEvent e) throws Throwable {
        SwingWorker sw = new DBCChunkSwingWorker<Object, DBCChunkOperation>() {
            protected Object doInBackground() throws Exception {
                try {
                    DBObject[] nodes = getSelectedNodes();
                    for (DBObject node : nodes) {
                        String query = treeSQLGenerator.generateSQLString(new DBObject[]{node});
                        DBCSQLCommandPane tabFrame = getPluginSession().getSession().getFactory().newInstance(DBCSQLCommandPane.class);
                        tabFrame.setSQL(query);
                        publish(new DBCChunkOperationVar<Object[]>(new Object[]{tabFrame, query,node}) {

                            public void executeOperation(Object[] var) {
                                DBCSQLCommandPane cmdPane = (DBCSQLCommandPane) var[0];
                                String sql = (String) var[1];
                                DBObject node = (DBObject) var[2];
                                DBCInternalWindow win = getSession().getView().addWindow(cmdPane, DBCSessionView.Side.Workspace, true);
                                win.setTitle(node.getName()+" : "+InsertSQLTreeNodeAction.this.getName());
                                cmdPane.setSQL(sql);
                            }
                        });
                    }
                } catch (Throwable e1) {
                    getSession().getView().getDialogManager().showMessage(null, null, MessageDialogType.ERROR, null, e1);
                }
                return null;
            }

        };
        sw.execute();
    }


}
