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

package net.vpc.dbclient.plugin.system.actionmanager.sessionactions;

import net.vpc.dbclient.api.actionmanager.DBCActionLocation;
import net.vpc.dbclient.api.actionmanager.DBCSessionAction;
import net.vpc.dbclient.api.sessionmanager.DBCInternalWindow;
import net.vpc.dbclient.api.sessionmanager.DBCSessionView;
import net.vpc.dbclient.plugin.system.sessionmanager.window.DBCDatabaseEditor;

import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class DBCSessionDatabasePropertiesAction extends DBCSessionAction {
    public DBCSessionDatabasePropertiesAction() {
        super("Action.DatabasePropertiesAction");
        addLocationPath(DBCActionLocation.MENUBAR, "/options");
        setPosition(Integer.MAX_VALUE - 30);
//        super("Properties");
    }

    public void actionPerformedImpl(ActionEvent e) {
        try {
            DBCInternalWindow window = getSession().getView().addWindow(
                    new DBCDatabaseEditor(getSession())
                    , DBCSessionView.Side.Workspace, false
            );
            window.setTitle(getSession().getView().getMessageSet().get("DatabaseEditor.title"));
            window.setIcon(getIcon());
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
}
