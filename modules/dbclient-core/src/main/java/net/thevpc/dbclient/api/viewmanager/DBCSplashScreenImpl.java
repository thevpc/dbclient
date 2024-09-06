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
package net.thevpc.dbclient.api.viewmanager;

import java.beans.PropertyChangeEvent;

import net.thevpc.common.prs.util.ProgressEvent;
import net.thevpc.common.swing.splash.JSplashScreen;
import net.thevpc.dbclient.api.DBCApplication;
import net.thevpc.dbclient.api.DBClientInfo;
import net.thevpc.common.prs.artset.ArtSet;
import net.thevpc.common.prs.artset.ArtSetManager;
import net.thevpc.common.prs.messageset.MessageSet;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.thevpc.dbclient.api.DBCApplicationView;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 30 dec. 2006 15:42:17
 */
public class DBCSplashScreenImpl extends JSplashScreen implements DBCSplashScreen {

    private DBCApplication application;
    private MessageSet messageSet;

    public DBCSplashScreenImpl(File folder) {
        super(createIcon(folder), new Dimension(480, 350));
        if (getImageIcon() != null) {
            getRenderer().setForeground(Color.WHITE);
        }
        getRenderer().setFont(new Font("Arial", Font.BOLD, 10));
        setTextBounds(180, 130, 290, 130);
        setTextY(150);
    }

    private static ImageIcon createIcon(File configFolder) {
        ImageIcon i = null;

        String[] extensions = new String[]{"png", "jpg", "gif", "jpeg"};
        for (String extension : extensions) {
            File file = new File(configFolder, DBCApplicationView.ARTSET_SPLASH_SCREEN + "." + extension);
            if (file.exists()) {
                try {
                    URL url = file.toURI().toURL();
                    i = new ImageIcon(url);
                    break;
                } catch (MalformedURLException ex) {
                    Logger.getLogger(DBCSplashScreenImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        if (i == null) {
            i = ArtSetManager.getCurrent().getArtImage(DBCApplicationView.ARTSET_SPLASH_SCREEN);
        }
        return i;
    }

    public MessageSet getMessageSet() {
        return messageSet;
    }

    @Override
    public void progressStart(ProgressEvent e) {

    }

    @Override
    public void progressUpdate(ProgressEvent e) {

    }

    @Override
    public void progressEnd(ProgressEvent e) {

    }

    public void setMessageSet(MessageSet messageSet) {
        this.messageSet = messageSet;
        this.setLocale(messageSet.getLocale());
        this.applyComponentOrientation(ComponentOrientation.getOrientation(messageSet.getLocale()));
    }

    public void addDefaultMessages() {
        DBClientInfo info = getApplication() == null ? DBClientInfo.INSTANCE : getApplication().getApplicationInfo();
        if (messageSet == null) {
            addMessage(new JSplashScreen.Message(JSplashScreen.Type.INFO, "Version " + info.getProductVersion()));
            addMessage(new JSplashScreen.Message(JSplashScreen.Type.INFO, "Built on " + info.getProductBuildDate()));
            if (application != null) {
                int c = application.getPluginManager().getAllPlugins().length;
                addMessage(new JSplashScreen.Message(JSplashScreen.Type.INFO, "Plugins count " + c));
                File rf = application.getWorkingDir();
                String rfs = rf.getAbsolutePath();
                try {
                    rfs = rf.getCanonicalPath();
                } catch (IOException e) {
                    //
                }
                addMessage(new JSplashScreen.Message(JSplashScreen.Type.INFO, "Work dir " + rfs));
                File ifolder = application.getInstallDir();
                if (!ifolder.equals(rf)) {
                    rfs = rf.getAbsolutePath();
                    try {
                        rfs = rf.getCanonicalPath();
                    } catch (IOException e) {
                        //
                    }
                    addMessage(new JSplashScreen.Message(JSplashScreen.Type.INFO, "Install dir " + rfs));
                }
            }
            addMessage(new JSplashScreen.Message(JSplashScreen.Type.INFO, "Java Version " + System.getProperty("java.version")));
            addMessage(new JSplashScreen.Message(JSplashScreen.Type.INFO, "Java Vendor " + System.getProperty("java.vendor")));
        } else {
            addMessage(new JSplashScreen.Message(JSplashScreen.Type.INFO, messageSet.get("Version") + " " + info.getProductVersion()));
            addMessage(new JSplashScreen.Message(JSplashScreen.Type.INFO, messageSet.get("About.BuildDate") + " " + info.getProductBuildDate()));
            if (application != null) {
                int c = application.getPluginManager().getAllPlugins().length;
                addMessage(new JSplashScreen.Message(JSplashScreen.Type.INFO, messageSet.get("About.PluginsCount",new Object[]{c})));
                File rf = application.getWorkingDir();
                String rfs = rf.getAbsolutePath();
                try {
                    rfs = rf.getCanonicalPath();
                } catch (IOException e) {
                    //
                }
                addMessage(new JSplashScreen.Message(JSplashScreen.Type.INFO, messageSet.get("About.WorkDir") + " " + rfs));
                File irf = application.getWorkingDir();
                if (!irf.equals(rf)) {
                    rfs = rf.getAbsolutePath();
                    try {
                        rfs = rf.getCanonicalPath();
                    } catch (IOException e) {
                        //
                    }
                    addMessage(new JSplashScreen.Message(JSplashScreen.Type.INFO, messageSet.get("About.InstallDir") + " " + rfs));
                }
            }
            addMessage(new JSplashScreen.Message(JSplashScreen.Type.INFO, messageSet.get("About.JavaVersion") + " " + System.getProperty("java.version")));
            addMessage(new JSplashScreen.Message(JSplashScreen.Type.INFO, messageSet.get("About.JavaVendor") + " " + System.getProperty("java.vendor")));
        }
    }

    public DBCApplication getApplication() {
        return application;
    }

    public void setApplication(DBCApplication application) {
        this.application = application;
        artSetChanged(false);
        application.getView().addPropertyChangeListener(DBCApplicationView.PROPERTY_ARTSET, new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                artSetChanged(true);
            }
        });
    }

    public void artSetChanged(boolean reloadSplashImage) {
        ArtSet artSet = application.getView().getArtSet();
        setForegroundColor(getColor(artSet.getProperty("splash-foreground"), null));
        setBackgroundColor(getColor(artSet.getProperty("splash-background"), null));
        setErrorColor(getColor(artSet.getProperty("splash-foreground-error"), null));
        setWarnColor(getColor(artSet.getProperty("splash-foreground-warn"), null));
        setSuccessColor(getColor(artSet.getProperty("splash-foreground-success"), null));
        setInfoColor(getColor(artSet.getProperty("splash-foreground-info"), null));
        if (reloadSplashImage) {
            setImageIcon(artSet.getArtImage(DBCApplicationView.ARTSET_SPLASH_SCREEN));
        }
    }

    public Color getColor(String id, Color defaultColor) {
        if (id != null && id.trim().length() > 0) {
            try {
                return Color.decode(id);
            } catch (Exception e) {
            }
        }
        return defaultColor;
    }
}
