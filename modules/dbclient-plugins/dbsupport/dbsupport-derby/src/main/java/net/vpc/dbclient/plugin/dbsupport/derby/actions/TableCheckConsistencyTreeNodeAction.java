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

import net.vpc.dbclient.api.DBCSession;
import net.vpc.dbclient.api.actionmanager.DBCActionLocation;
import net.vpc.dbclient.api.actionmanager.DBCTreeNodeAction;
import net.vpc.dbclient.api.sql.DBCConnection;
import net.vpc.dbclient.api.sql.objects.DBObject;
import net.vpc.dbclient.api.sql.objects.DBTable;
import net.vpc.dbclient.plugin.dbsupport.derby.DerbyConnection;
import net.vpc.swingext.dialog.MessageDialogType;

import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class TableCheckConsistencyTreeNodeAction extends DBCTreeNodeAction {

    public TableCheckConsistencyTreeNodeAction() {
        super("Action.TableCheckConsistencyNodeAction");
        addLocationPath(DBCActionLocation.POPUP, "/Admin");
    }

    public boolean shouldEnable(DBObject activeNode, DBObject[] selectedNodes) {
        return (activeNode != null && activeNode instanceof DBTable);
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
        final DBTable object = (DBTable) getSelectedNode();
        DBCConnection c = getSession().getConnection();
        if (c instanceof DerbyConnection) {
            DerbyConnection dc = (DerbyConnection) c;
            boolean b;
            try {
                b = dc.getFeatureSYSCSUTIL().SYSCS_CHECK_TABLE(object.getCatalogName(), object.getSchemaName(), object.getName());
            } catch (SQLException e1) {
                getSession().getView().getDialogManager().showMessage(null, "Unable to check Table " + object + " consistence", MessageDialogType.ERROR, "Consistency Check");
                return;
            }
            if (b) {
                getSession().getView().getDialogManager().showMessage(null, "Table " + object + " is Consistent", MessageDialogType.INFO, "Consistency Check");
            } else {
                getSession().getView().getDialogManager().showMessage(null, "Table " + object + " is Not Consistent", MessageDialogType.ERROR, "Consistency Check");
            }
        } else {
            getSession().getView().getDialogManager().showMessage(null, "Not recognized as DerbyConnection", MessageDialogType.ERROR, "Consistency Check");
        }
    }
}