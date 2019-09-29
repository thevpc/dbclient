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
import net.vpc.dbclient.api.windowmanager.DBCWindow;
import net.vpc.swingext.SwingUtilities3;

import java.awt.*;
import java.awt.event.ActionEvent;

/**
 */
public class DBCSessionCloseAction extends DBCSessionAction {
    public DBCSessionCloseAction() {
        super("Action.CloseAction");
        addLocationPath(DBCActionLocation.MENUBAR, "/file");
        setPosition(999);
    }

//    public void validateShowing(DBObject activeNode, DBObject[] selectedNodes) {
//    }

    public void actionPerformedImpl(ActionEvent e) throws Throwable {
        Component mainComponent = getSession().getView().getMainComponent();
        DBCWindow p = (DBCWindow) SwingUtilities3.getAncestorByProperty(DBCWindow.COMPONENT_PROPERTY, DBCWindow.class, mainComponent);
        p.closeWindow();
    }
}
