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

package net.thevpc.dbclient.plugin.tool.neormf.actions;

import net.thevpc.dbclient.api.actionmanager.DBCActionLocation;
import net.thevpc.dbclient.api.actionmanager.DBCSessionAction;
import net.thevpc.dbclient.api.sessionmanager.DBCInternalWindow;
import net.thevpc.dbclient.api.sessionmanager.DBCSessionView;
import net.thevpc.dbclient.plugin.tool.neormf.NeormfPluginSession;
import net.thevpc.dbclient.plugin.tool.neormf.settings.NeormfSettingsStandalonePanel;

import java.awt.event.ActionEvent;

public class NeormfProjectSettingsAction extends DBCSessionAction {

    public NeormfProjectSettingsAction() {
        super("Action.NeormfProjectSettingsAction");
        addLocationPath(DBCActionLocation.MENUBAR, "/tools");
    }

    public void actionPerformedImpl(ActionEvent e) throws Throwable {
        NeormfSettingsStandalonePanel panel = new NeormfSettingsStandalonePanel((NeormfPluginSession) getPluginSession());
        panel.getSettingsComponent().loadConfig();
        DBCInternalWindow window = getSession().getView().addWindow(panel, DBCSessionView.Side.Workspace, false);
        window.setTitle(getActionName());
    }

}