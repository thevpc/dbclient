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
package net.thevpc.dbclient.plugin.system.viewmanager;

import net.thevpc.common.io.FileUtils;
import net.thevpc.common.io.IOUtils;
import net.thevpc.dbclient.api.DBCApplication;
import net.thevpc.dbclient.api.pluginmanager.DBCAbstractPluggable;
import net.thevpc.common.prs.plugin.Inject;
import net.thevpc.common.prs.plugin.Initializer;
import net.thevpc.dbclient.api.viewmanager.DBCLookAndFeelManager;
import net.thevpc.dbclient.api.windowmanager.DBCWindow;
import net.thevpc.dbclient.api.windowmanager.DBCWindowKind;
import net.thevpc.common.swing.plaf.PlafItem;
import net.thevpc.common.swing.plaf.UIManager2;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 8 dec. 2006 00:33:33
 */
public class DBCLookAndFeelManagerImpl extends DBCAbstractPluggable implements DBCLookAndFeelManager {

    @Inject
    private DBCApplication application;
    private Logger logger;

    public DBCLookAndFeelManagerImpl() {
    }

    @Override
    public void start() {
        //do nothing, every thing is done in initializer
    }
    
    

    public void setLookAndFeel(String name) throws IllegalAccessException, UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException {
        if (name != null && name.trim().length() == 0) {
            name = null;
        }
        if (name != null) {
            setLookAndFeel(UIManager2.getPlafItem(name));
        }
    }

    @Initializer
    private void init() {
        logger=application.getLogger(DBCLookAndFeelManagerImpl.class.getName());
        File plafFile = new File(application.getWorkingDir(), "lib/plaf/plaf-config.xml");
        Properties p = new Properties();
        if (plafFile.exists()) {
            FileInputStream fis = null;
            try {
                try {
                    fis = new FileInputStream(plafFile);
                    p.loadFromXML(fis);
                } finally {
                    if (fis != null) {
                        fis.close();
                    }
                }
            } catch (IOException e) {
                logger.log(Level.SEVERE,"Unable to load " + FileUtils.getFilePath(plafFile),e);
            }
        }
        for (Map.Entry<Object, Object> entry : p.entrySet()) {
            try {
                UIManager.installLookAndFeel((String) entry.getKey(), (String) entry.getValue());
            } catch (Exception e) {
                logger.log(Level.SEVERE,"Unable to load LookAndFeel " + entry.getKey() + "=" + entry.getValue(),e);
            }
        }
    }

    public boolean isSupported() {
        return true;
    }

    public void setLookAndFeel(PlafItem name) throws IllegalAccessException, UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException {
        if (name == null) {
            return;
        }
        UIManager2.apply(name);
        if (application.getApplicationState().ordinal() >= DBCApplication.ApplicationState.READY.ordinal()) {
            DBCWindow[] windows = application.getView().getWindowManager().getWindows(
                    DBCWindowKind.values());
            for (DBCWindow window : windows) {
                try {
                    Component c = window.getTopLevelComponent();
                    if (c != null) {
                        SwingUtilities.updateComponentTreeUI(c);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

//        DBCSession[] allSessions = application.getAllSessions();
//        for (DBCSession s : allSessions) {
//            for (Component component : s.getView().getComponents()) {
//                try {
//                    SwingUtilities.updateComponentTreeUI(component);
//                } catch (Exception e) {
//                    //e.printStackTrace();
//                }
//            }
//        }

    }
}
