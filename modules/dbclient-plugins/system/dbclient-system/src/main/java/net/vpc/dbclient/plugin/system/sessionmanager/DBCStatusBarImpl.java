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
package net.vpc.dbclient.plugin.system.sessionmanager;

import net.vpc.dbclient.api.DBCApplication;
import net.vpc.dbclient.api.DBCSession;
import net.vpc.dbclient.api.actionmanager.DBCActionFilter;
import net.vpc.dbclient.api.actionmanager.DBCActionLocation;
import net.vpc.dbclient.api.actionmanager.DBClientAction;
import net.vpc.dbclient.api.sessionmanager.DBCSessionView;
import net.vpc.dbclient.api.viewmanager.DBCPluggablePanel;
import net.vpc.dbclient.api.viewmanager.DBCStatusBar;
import net.vpc.dbclient.plugin.system.sessionmanager.window.DBCAutoCommitLabel;
import net.vpc.dbclient.plugin.system.sessionmanager.window.DBCHoldabilityLabel;
import net.vpc.dbclient.plugin.system.sessionmanager.window.DBCReadWriteLabel;
import net.vpc.swingext.MemoryUseIconTray;
import net.vpc.swingext.dialog.MessageDialogType;

import javax.swing.*;
import java.awt.*;
import net.vpc.dbclient.plugin.system.sessionmanager.window.DBCCatalogLabel;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 25 mars 2007 13:59:03
 */
public class DBCStatusBarImpl extends DBCPluggablePanel implements DBCStatusBar {

    private DBCApplication dbclient;
    private DBCSession session;
    private static DBCActionFilter DEFAULT_APP_STATUS_BAR_FILTER = new DBCActionFilter() {

        public boolean accept(DBClientAction action) {
            return true;
        }
    };

    public void init(DBCApplication dbClient, DBCSession session) {
        this.session = session;
        this.dbclient = (dbClient == null) ? session.getApplication() : dbClient;
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        add(Box.createHorizontalGlue());
        //DBCPlugin[] dbcPlugins = dbclient.getPluginManager().getValidPlugins();

        try {
            getSession().getView().getActionManager().fillComponent(this, DBCActionLocation.STATUSBAR, DEFAULT_APP_STATUS_BAR_FILTER);
        } catch (Throwable e) {
            getSession().getView().getDialogManager().showMessage(null, "Unable to create STATUSBAR", MessageDialogType.ERROR, null, e);
        }
        DBCSessionView view = session.getView();

        add(new DBCCatalogLabel(view));
        add(Box.createHorizontalStrut(20));
        
        add(new DBCHoldabilityLabel(view));
        add(Box.createHorizontalStrut(5));
        
        add(new DBCReadWriteLabel(view));
        add(Box.createHorizontalStrut(5));
        
        add(new DBCAutoCommitLabel(view));
        add(Box.createHorizontalStrut(5));
        
        add(Box.createHorizontalStrut(10));
        add(new MemoryUseIconTray(false));
    }

    public DBCApplication getApplication() {
        return dbclient;
    }

    public DBCSession getSession() {
        return session;
    }

    public Component getComponent() {
        return this;
    }
}
