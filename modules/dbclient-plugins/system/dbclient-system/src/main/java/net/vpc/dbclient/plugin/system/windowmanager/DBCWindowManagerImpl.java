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

package net.vpc.dbclient.plugin.system.windowmanager;

import net.vpc.dbclient.api.DBCApplication;
import net.vpc.dbclient.api.DBCApplicationView;
import net.vpc.dbclient.api.DBCSession;
import net.vpc.dbclient.api.configmanager.DBCApplicationSettingsEditor;
import net.vpc.dbclient.api.configmanager.DBCSessionInfo;
import net.vpc.dbclient.api.drivermanager.DBCDriverManagerEditor;
import net.vpc.dbclient.api.pluginmanager.DBCAbstractPluggable;
import net.vpc.dbclient.api.pluginmanager.DBCPluginManagerEditor;
import net.vpc.prs.plugin.Inject;
import net.vpc.prs.plugin.Initializer;
import net.vpc.dbclient.api.sessionmanager.DBCSessionListEditor;
import net.vpc.dbclient.api.sessionmanager.DBCSessionListListener;
import net.vpc.dbclient.api.windowmanager.DBCWindow;
import net.vpc.dbclient.api.windowmanager.DBCWindowKind;
import net.vpc.dbclient.api.windowmanager.DBCWindowListener;
import net.vpc.dbclient.api.windowmanager.DBCWindowManager;
import net.vpc.swingext.dialog.MessageDialogType;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CancellationException;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 9 dec. 2006 23:40:10
 */
public class DBCWindowManagerImpl extends DBCAbstractPluggable implements DBCWindowManager {
    @Inject
    private DBCApplication application;
    private DBCWindow<DBCPluginManagerEditor> pluginManagerWindow = null;
    private DBCWindow<DBCDriverManagerEditor> driverManagerWindow = null;
    private DBCWindow<DBCSessionListEditor> sessionChooserWindow;
    private DBCWindow<DBCApplicationSettingsEditor> applicationSettingsWindow;
    PropertyChangeListener defaultWindowsTitleUpdater;

    private WindowKindsMap map = new WindowKindsMap();

    public DBCWindowManagerImpl() {
    }


    public DBCWindow addWindow(Component component, DBCWindowKind kind, DBCSession session, String title, ImageIcon icon) {
        if (session != null) {
//            Class aClass = session.getFactory().getImplClass(DBCWindow.class,null);
//            if (aClass != null) {
            DBCWindow impl = session.getFactory().newInstance(DBCWindow.class);
            impl.init(component, kind, session, application, title, icon);
            map.add(impl);
            return impl;
//            }
        }
        DBCWindow impl = application.getFactory().newInstance(DBCWindow.class);
        impl.init(component, kind, session, application, title, icon);
        map.add(impl);
        return impl;
    }

    public DBCWindow[] getWindows(DBCWindowKind... kinds) {
        ArrayList<DBCWindow> all = new ArrayList<DBCWindow>();
        for (DBCWindowKind kind : kinds) {
            all.addAll(Arrays.asList(map.getWindows(kind)));
        }
        if (pluginManagerWindow != null && !all.contains(pluginManagerWindow)) {
            for (DBCWindowKind kind : kinds) {
                switch (kind) {
                    case PLUGIN_MANAGER: {
                        all.add(pluginManagerWindow);
                        break;
                    }
                }
            }
        }

        if (driverManagerWindow != null && !all.contains(driverManagerWindow)) {
            for (DBCWindowKind kind : kinds) {
                switch (kind) {
                    case DRIVER_MANAGER: {
                        all.add(driverManagerWindow);
                        break;
                    }
                }
            }
        }

        if (sessionChooserWindow != null && !all.contains(sessionChooserWindow)) {
            for (DBCWindowKind kind : kinds) {
                switch (kind) {
                    case SESSION_CHOOSER: {
                        all.add(sessionChooserWindow);
                        break;
                    }
                }
            }
        }

        if (applicationSettingsWindow != null && !all.contains(applicationSettingsWindow)) {
            for (DBCWindowKind kind : kinds) {
                switch (kind) {
                    case GLOBAL_SETTINGS: {
                        all.add(applicationSettingsWindow);
                        break;
                    }
                }
            }
        }

        return all.toArray(new DBCWindow[all.size()]);
    }

    @Initializer
    public void init() {
        defaultWindowsTitleUpdater = new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                if (pluginManagerWindow != null) {
                    pluginManagerWindow.setTitle(getApplication().getView().getMessageSet().get("Action.PluginManagerAction"));
                }
                if (driverManagerWindow != null) {
                    driverManagerWindow.setTitle(getApplication().getView().getMessageSet().get("Action.DriverManagerAction"));
                }
                if (applicationSettingsWindow != null) {
                    applicationSettingsWindow.setTitle(getApplication().getView().getMessageSet().get("Action.ApplicationSettingsAction"));
                }
            }
        };
        application.addPropertyChangeListener(DBCApplicationView.PROPERTY_LOCALE, defaultWindowsTitleUpdater);
    }

    public DBCWindow<DBCPluginManagerEditor> getPluginManagerWindow() {
        if (pluginManagerWindow == null) {
            DBCPluginManagerEditor e = application.getFactory().newInstance(DBCPluginManagerEditor.class);
            pluginManagerWindow = addWindow(
                    e.getComponent(),
                    DBCWindowKind.PLUGIN_MANAGER,
                    null, getApplication().getView().getMessageSet().get("Action.PluginManagerAction"), null
            );
        }
        return pluginManagerWindow;
    }


    public DBCWindow<DBCDriverManagerEditor> getDriverManagerWindow() {
        if (driverManagerWindow == null) {
            DBCDriverManagerEditor e = application.getFactory().newInstance(DBCDriverManagerEditor.class);
            driverManagerWindow = addWindow(
                    e.getComponent(),
                    DBCWindowKind.DRIVER_MANAGER,
                    null, getApplication().getView().getMessageSet().get("Action.DriverManagerAction"), null
            );
        }
        return driverManagerWindow;
    }

    public DBCWindow<DBCSessionListEditor> getSessionListWindow() {
        try {
            if (sessionChooserWindow == null) {
                DBCSessionListEditor sessionChooser = application.getFactory().newInstance(DBCSessionListEditor.class);
                sessionChooser.addSessionChooserListener(new DBCSessionListListener() {
                    public void cancel() {
                        quitIfNoMoreSessions();
                    }

                    public void connect(int sessionId) {
                        try {
                            application.openSession(sessionId);
                            sessionChooserWindow.hideWindow();
                        } catch (CancellationException e) {
                            //do nothing
                        } catch (Throwable e) {
                            getApplication().getView().getDialogManager().showMessage(null, null, MessageDialogType.ERROR, null, e);
                            e.printStackTrace();
                            getSessionListWindow().showWindow();
                        }
                    }

                });
                sessionChooserWindow = addWindow(
                        sessionChooser.getComponent(),
                        DBCWindowKind.SESSION_CHOOSER,
                        null, null, null
                );
                sessionChooserWindow.addWindowListener(
                        new DBCWindowListener() {
                            boolean first = true;

                            public void windowClosed(DBCWindow window) {
                                quitIfNoMoreSessions();
                            }

                            public void windowHidden(DBCWindow window) {
                            }

                            public void windowOpened(DBCWindow window) {
                                if (first) {
                                    first = false;

                                    DBCSessionInfo[] sessionFiles = application.getConfig().getSessions();
                                    if (sessionFiles.length <= 0) {
                                        sessionChooserWindow.getObject().doAddConnection();
                                    }
                                }
                            }

                        }
                );
            }
            sessionChooserWindow.getObject().loadConfig();
            return sessionChooserWindow;
        } catch (Throwable e) {
            application.getView().getDialogManager().showMessage(null, null, MessageDialogType.ERROR, null, e);
        }
        return null;
    }

    public DBCWindow<DBCApplicationSettingsEditor> getApplicationSettingsWindow() {
        if (applicationSettingsWindow == null) {
            DBCApplicationSettingsEditor settings = application.getFactory().newInstance(DBCApplicationSettingsEditor.class);
            applicationSettingsWindow = addWindow(
                    settings.getComponent(),
                    DBCWindowKind.GLOBAL_SETTINGS,
                    null, getApplication().getView().getMessageSet().get("Action.ApplicationSettingsAction"), null
            );
        }
        return applicationSettingsWindow;
    }


    public void quitIfNoMoreSessions() {
        if (application.getSessions().length == 0) {
            application.close();
        } else {
            if (sessionChooserWindow != null) {
                sessionChooserWindow.hideWindow();
            }
        }
    }

    public DBCApplication getApplication() {
        return application;
    }
}
