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

import net.thevpc.common.prs.Version;
import net.thevpc.common.swing.splash.JSplashScreen;
import net.thevpc.dbclient.api.actionmanager.DBCActionLocation;
import net.thevpc.dbclient.api.actionmanager.DBCApplicationAction;
import net.thevpc.dbclient.api.viewmanager.DBCSplashScreenImpl;
import net.thevpc.common.prs.messageset.MessageSet;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.logging.Level;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 31 janv. 2006 15:47:00
 */
public class DBCCheckVersionAction extends DBCApplicationAction {

    private DBCSplashScreenImpl splashScreen;

    public DBCCheckVersionAction() {
        super("Action.CheckVersionAction");
        addLocationPath(DBCActionLocation.MENUBAR, "/help");
    }

//    public void validateShowing(DBObject activeNode, DBObject[] selectedNodes) {
//    }
    public void actionPerformedImpl(ActionEvent e) throws Throwable {
        if (splashScreen == null) {
            splashScreen = new DBCSplashScreenImpl(getApplication().getConfigDir());
            splashScreen.setApplication(getApplication());
            splashScreen.setMessageSet(getApplication().getView().getMessageSet());
            splashScreen.setHideOnClick(true);
        }
        splashScreen.clearMessages();
        splashScreen.setMessageSet(getApplication().getView().getMessageSet());
        splashScreen.setVisible(true);
        MessageSet msg = getApplication().getView().getMessageSet();
        splashScreen.addMessage(new JSplashScreen.Message(JSplashScreen.Type.INFO, msg.get("Action.CheckVersionAction.Version", new Object[]{getApplication().getApplicationInfo().getProductVersion().toString()})));
        splashScreen.addMessage(new JSplashScreen.Message(JSplashScreen.Type.INFO, msg.get("Action.CheckVersionAction.Checking")));
        final StringBuilder dots = new StringBuilder();
        final Timer dotsPainter = new Timer(500, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                dots.append(".");
                MessageSet msg = getApplication().getView().getMessageSet();
                splashScreen.setMessageAt(1, new JSplashScreen.Message(JSplashScreen.Type.INFO, msg.get("Action.CheckVersionAction.Checking") + dots));
            }
        });
        Thread t = new Thread(new Runnable() {

            public void run() {
//                splashScreen.setBorder(BorderFactory.createEtchedBorder());
                MessageSet msg = getApplication().getView().getMessageSet();
                try {
                    Version latestVersion = getApplication().getLatestVersion();
                    splashScreen.addMessage(new JSplashScreen.Message(JSplashScreen.Type.INFO, msg.get("Action.CheckVersionAction.DeployedVersion", new Object[]{latestVersion})));
                    int versionComparaison = getApplication().getApplicationInfo().getProductVersion().compareTo(latestVersion);
                    if (latestVersion != null && !latestVersion.isEmpty() && versionComparaison == 0) {
                        splashScreen.addMessage(new JSplashScreen.Message(JSplashScreen.Type.INFO, msg.get("Action.CheckVersionAction.NoNewVersion", new Object[]{getApplication().getApplicationInfo().getProductVersion().toString()})));
//                        panel.setBorder(BorderFactory.createEtchedBorder());
                    } else if (latestVersion != null && !latestVersion.isEmpty() && versionComparaison > 0) {
                        splashScreen.addMessage(new JSplashScreen.Message(JSplashScreen.Type.ERROR, msg.get("Action.CheckVersionAction.OldVersionAvailable", new Object[]{getApplication().getApplicationInfo().getProductVersion().toString(), latestVersion})));
                    } else {
                        splashScreen.addMessage(new JSplashScreen.Message(JSplashScreen.Type.SUCCESS, msg.get("Action.CheckVersionAction.NewVersionAvailable", new Object[]{latestVersion, getApplication().getApplicationInfo().getProductVersion().toString()})));
//                        panel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
                    }
                } catch (UnknownHostException e) {
                    getApplication().getLogger(DBCCheckVersionAction.class.getName()).log(Level.SEVERE, "Unknown Host {0}", e.getMessage());
                    splashScreen.addMessage(new JSplashScreen.Message(JSplashScreen.Type.ERROR, msg.get("Action.CheckVersionAction.UnknownHostException", new Object[]{e.getMessage()})));
//                    panel.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                } catch (FileNotFoundException e) {
                    getApplication().getLogger(DBCCheckVersionAction.class.getName()).log(Level.SEVERE, "Unknown File {0}", e.getMessage());
                    splashScreen.addMessage(new JSplashScreen.Message(JSplashScreen.Type.ERROR, msg.get("Action.CheckVersionAction.FileNotFoundException", new Object[]{e.getMessage()})));
                } catch (IOException e) {
                    getApplication().getLogger(DBCCheckVersionAction.class.getName()).log(Level.SEVERE, "I/O error", e);
                    splashScreen.addMessage(new JSplashScreen.Message(JSplashScreen.Type.ERROR, msg.get("Action.CheckVersionAction.IOException", new Object[]{e.getMessage()})));
//                    panel.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                }
                dotsPainter.stop();
            }
        });
        t.start();
        dotsPainter.start();
    }
}
