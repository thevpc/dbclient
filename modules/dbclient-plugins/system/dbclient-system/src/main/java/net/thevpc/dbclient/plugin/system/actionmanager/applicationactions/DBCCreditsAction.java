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

import net.thevpc.common.swing.splash.JSplashScreen;
import net.thevpc.dbclient.api.actionmanager.DBCActionLocation;
import net.thevpc.dbclient.api.actionmanager.DBCApplicationAction;
import net.thevpc.dbclient.api.pluginmanager.DBCPlugin;
import net.thevpc.dbclient.api.viewmanager.DBCSplashScreenImpl;
import net.thevpc.common.prs.messageset.MessageSet;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 31 janv. 2006 15:47:00
 */
public class DBCCreditsAction extends DBCApplicationAction {

    DBCSplashScreenImpl splashScreen;

    public DBCCreditsAction() {
        super("Action.CreditsAction");
        addLocationPath(DBCActionLocation.MENUBAR, "/help");
    }

    public void actionPerformedImpl(ActionEvent e) throws Throwable {
        if (splashScreen == null) {
            splashScreen = new DBCSplashScreenImpl(getApplication().getConfigDir());
            splashScreen.setApplication(getApplication());
            splashScreen.setHideOnClick(true);
            splashScreen.setTimeout(300000);
            splashScreen.setVerticalAnimation(true);
            splashScreen.setVerticalAnimationLoop(true);
        }
        splashScreen.setMessageSet(getApplication().getView().getMessageSet());
        splashScreen.setMessages(getCredits());
        splashScreen.setVisible(true);
    }

    public JSplashScreen.Message[] getCredits() {
        ArrayList<JSplashScreen.Message> v = new ArrayList<JSplashScreen.Message>();
        MessageSet m = getApplication().getView().getMessageSet();
        v.add(new JSplashScreen.Message(JSplashScreen.Type.INFO, m.get("Action.CreditsAction.MainAuthor") + " : " + getApplication().getApplicationInfo().getAuthorName()));
        v.add(new JSplashScreen.Message(JSplashScreen.Type.INFO, ""));
        for (DBCPlugin dbcPlugin : getApplication().getPluginManager().getAllPlugins()) {
            v.add(new JSplashScreen.Message(JSplashScreen.Type.SUCCESS, m.get("Plugin") + " " + dbcPlugin.getDescriptor().getTitle() + ""));
            v.add(new JSplashScreen.Message(JSplashScreen.Type.INFO, "    " + m.get("Category") + " : " + dbcPlugin.getDescriptor().getCategory()));
            v.add(new JSplashScreen.Message(JSplashScreen.Type.INFO, "    " + m.get("Version") + " : " + dbcPlugin.getDescriptor().getVersion()));
            v.add(new JSplashScreen.Message(JSplashScreen.Type.INFO, "    " + m.get("Author") + " : " + dbcPlugin.getDescriptor().getAuthor()));
            String contributors = dbcPlugin.getDescriptor().getContributors();
            if (contributors != null && contributors.length() > 0) {
                v.add(new JSplashScreen.Message(JSplashScreen.Type.INFO, "    " + m.get("Contributors") + " : " + contributors));
            }
            v.add(new JSplashScreen.Message(JSplashScreen.Type.INFO, ""));
        }
        return v.toArray(new JSplashScreen.Message[v.size()]);
    }
}
