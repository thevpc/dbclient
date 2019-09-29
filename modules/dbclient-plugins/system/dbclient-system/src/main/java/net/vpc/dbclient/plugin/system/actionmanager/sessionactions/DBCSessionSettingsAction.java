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
import net.vpc.dbclient.api.configmanager.DBCSessionSettingsEditor;
import net.vpc.dbclient.api.sessionmanager.DBCInternalWindow;
import net.vpc.dbclient.api.sessionmanager.DBCSessionView;

import java.awt.event.ActionEvent;
import net.vpc.swingext.dialog.MessageDialogType;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 31 janv. 2006 15:47:00
 */
public class DBCSessionSettingsAction extends DBCSessionAction {
    public DBCSessionSettingsAction() {
        super("Action.SessionSettingsAction");
        addLocationPath(DBCActionLocation.MENUBAR, "/options");
        setPosition(Integer.MAX_VALUE - 20);
    }

    public void actionPerformedImpl(ActionEvent e) throws Throwable {
        DBCSessionSettingsEditor settings = getSession().getFactory().newInstance(DBCSessionSettingsEditor.class);
        
//        if (!settings.isEmpty()) {
            DBCInternalWindow w = getSession().getView().addWindow(
                    settings, DBCSessionView.Side.Workspace,
                    true
            );
            w.setTitle(getSession().getView().getMessageSet().get(getKey()));
            w.setIcon(getIcon());
//        }else{
//            getSession().getView().getDialogManager().showMessage(null, "Nothing to configure", MessageDialogType.INFO);
//        }
    }
}
