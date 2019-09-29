package net.vpc.dbclient.plugin.toolbox.actions.tree;

import net.vpc.dbclient.api.actionmanager.DBCActionLocation;
import net.vpc.dbclient.api.actionmanager.DBCTreeNodeAction;
import net.vpc.dbclient.api.sql.DBCConnection;
import net.vpc.dbclient.api.sql.features.GenerateSQLDropFeature;
import net.vpc.dbclient.api.sql.objects.DBObject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

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
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 17 mai 2006 23:43:16
 */
public class SQLDropTreeNodeAction extends DBCTreeNodeAction {

    public SQLDropTreeNodeAction() {
        super("Action.SQLDropNodeAction");
        addLocationPath(DBCActionLocation.POPUP, "/");
        addLocationPath(DBCActionLocation.MENUBAR, "/edit");
    }

    public boolean shouldEnable(DBObject activeNode, DBObject[] selectedNodes) {
        try {
            return (
                    activeNode != null
                            && getSession().getConnection().getFeatureGenerateSQLDrop().isSupported(activeNode.getType())
            );
        } catch (SQLException ex) {
            getSession().getLogger(getClass().getName()).log(Level.SEVERE,"ShouldEnable Failed", ex);
            return false;
        }
    }

    public boolean preActionPerformed(ActionEvent event) {
        //getSelectedNode()
        return JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(getSession().getView().getMainComponent(), "Confirm Drop Nodes?", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
    }

    public void actionPerformedImpl(ActionEvent e) throws Throwable {
        DBCConnection cnx = getSession().getConnection();
        Statement stmt = null;
        boolean someChanges = false;
        try {
            stmt = cnx.createStatement();
            DBObject[] dbObjects = getSelectedNodes();
            boolean[] dropped = new boolean[dbObjects.length];
            boolean forceDrop = true;
            Throwable lastException = null;
            GenerateSQLDropFeature dropFeature = cnx.getFeatureGenerateSQLDrop();
            if (!forceDrop) {
                for (DBObject dbObject : dbObjects) {
                    if (dropFeature.isSupported(dbObject.getType())) {
                        stmt.executeUpdate(dropFeature.getSQL(
                                dbObject.getCatalogName(),
                                dbObject.getSchemaName(),
                                dbObject.getParentName(), dbObject.getName(), dbObject.getType()
                        ));
                        someChanges = true;
                    }
                }
            } else {
                boolean err;
                do {
                    err = false;
                    boolean someDropped = false;
                    for (int i = 0; i < dropped.length; i++) {
                        if (!dropped[i]) {
                            DBObject dbObject = dbObjects[i];
                            try {
                                if (dropFeature.isSupported(dbObject.getType())) {
                                    stmt.executeUpdate(dropFeature.getSQL(
                                            dbObject.getCatalogName(),
                                            dbObject.getSchemaName(),
                                            dbObject.getParentName(), dbObject.getName(), dbObject.getType()
                                    ));
                                    someChanges = true;
                                    dropped[i] = true;
                                    someDropped = true;
                                }
                            } catch (Throwable th) {
                                lastException = th;
                                err = true;
                            }
                        }
                    }
                    if (!someDropped) {
                        if (lastException == null) {
                            throw lastException;
                        } else {
                            break;
                        }
                    }
                } while (err);
            }

        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (someChanges) {
                getSession().getView().refreshView();
            }
        }
    }
}
