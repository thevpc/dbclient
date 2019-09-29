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

package net.vpc.dbclient.plugin.system.actionmanager.applicationactions;

import net.vpc.dbclient.api.actionmanager.DBCActionLocation;
import net.vpc.dbclient.api.actionmanager.DBCApplicationAction;
import net.vpc.dbclient.api.configmanager.DBCApplicationSettingsEditor;
import net.vpc.dbclient.api.windowmanager.DBCWindow;
import net.vpc.swingext.dialog.MessageDialogType;

import java.awt.event.ActionEvent;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 7 févr. 2007 01:22:29
 */
public class DBCApplicationSettingsAction extends DBCApplicationAction {
    public DBCApplicationSettingsAction() {
        super("Action.ApplicationSettingsAction");
        addLocationPath(DBCActionLocation.MENUBAR, "/options");
        setPosition(Integer.MAX_VALUE - 10);
    }

    public void actionPerformedImpl(ActionEvent e) throws Throwable {
        DBCWindow<DBCApplicationSettingsEditor> dbcWindow = getApplication().getView().getWindowManager().getApplicationSettingsWindow();
        DBCApplicationSettingsEditor dbcSettingsPanel = dbcWindow.getObject();
        dbcSettingsPanel.loadConfig();
        if (dbcSettingsPanel.isEmpty()) {
            getApplication().getView().getDialogManager().showMessage(null, "No Settings Registred", MessageDialogType.ERROR);
        } else {
            dbcWindow.pack();
            dbcWindow.showWindow();
        }
    }
}
