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
package net.thevpc.dbclient.plugin.system.actionmanager.applicationactions;

import net.thevpc.dbclient.api.actionmanager.DBCActionLocation;
import net.thevpc.dbclient.api.actionmanager.DBCApplicationAction;
import net.thevpc.dbclient.api.viewmanager.DBCSplashScreenImpl;

import java.awt.event.ActionEvent;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 31 janv. 2006 15:47:00
 */
public class DBCAboutAction extends DBCApplicationAction {

    DBCSplashScreenImpl splashScreen;

    public DBCAboutAction() {
        super("Action.AboutAction");
        setPosition(Integer.MAX_VALUE);
        addLocationPath(DBCActionLocation.MENUBAR, "/help");
        addLocationPath(DBCActionLocation.TOOLBAR, "/");
    }

    public void actionPerformedImpl(ActionEvent e) throws Throwable {
        if (splashScreen == null) {
            splashScreen = new DBCSplashScreenImpl(getApplication().getConfigDir());
            splashScreen.setApplication(getApplication());
            splashScreen.setHideOnClick(true);
            splashScreen.setTimeout(60000);
        }
        splashScreen.setMessageSet(getApplication().getView().getMessageSet());
        splashScreen.addDefaultMessages();
        splashScreen.setVisible(true);
    }
}
