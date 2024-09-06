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
package net.thevpc.dbclient.plugin.system.sessionmanager;

import net.thevpc.dbclient.api.DBCApplication;
import net.thevpc.dbclient.api.DBCSession;
import net.thevpc.dbclient.api.actionmanager.DBCActionFilter;
import net.thevpc.dbclient.api.actionmanager.DBCActionLocation;
import net.thevpc.dbclient.api.actionmanager.DBCActionManager;
import net.thevpc.dbclient.api.actionmanager.DBClientAction;
import net.thevpc.common.prs.plugin.Inject;
import net.thevpc.common.prs.plugin.Initializer;
import net.thevpc.dbclient.api.viewmanager.DBCMenuBar;
import net.thevpc.dbclient.plugin.system.SystemUtils;
import net.thevpc.common.swing.prs.PRSManager;
import net.thevpc.common.swing.dialog.MessageDialogType;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class DBCMenuBarImpl extends JMenuBar implements DBCMenuBar {

    @Inject
    private DBCApplication application;
    @Inject
    private DBCSession session;

    public DBCMenuBarImpl() {
    }

    public JMenuBar getMenuBarComponent() {
        return this;
    }

    private static DBCActionFilter DEFAULT_APP_MENU_FILTER = new DBCActionFilter() {
        public boolean accept(DBClientAction action) {
            return true;
        }
    };

    @Initializer
    private void init() {
        if (session == null) {
            try {
                getApplication().getView().getActionManager().fillComponent(this, DBCActionLocation.MENUBAR, DEFAULT_APP_MENU_FILTER);
            } catch (Throwable e) {
                getApplication().getView().getDialogManager().showMessage(null, "Unable to create Application Menu", MessageDialogType.ERROR, null, e);
            }
        } else {
            try {
                getSession().getView().getActionManager().fillComponent(this, DBCActionLocation.MENUBAR, DEFAULT_APP_MENU_FILTER);
            } catch (Throwable e) {
                getApplication().getView().getDialogManager().showMessage(null, "Unable to create Session Menu", MessageDialogType.ERROR, null, e);
            }
        }

        PRSManager.update(
                getActionManager().getActions(),
                this, session == null ? application.getView() : session.getView());
//        addFocusListener(new FocusListener() {
//
//            public void focusGained(FocusEvent e) {
//                System.out.println("focusGained");
//            }
//
//            public void focusLost(FocusEvent e) {
//                System.out.println("focusLost");
//            }
//        });
        int count = getMenuCount();
        PopupMenuListener popupMenuListener = new PopupMenuListener() {

            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                if (getSession() != null) {
                    SystemUtils.validateTree((JPopupMenu) e.getSource(), getSession().getView().getExplorer().getCurrentNodes());
                }
            }

            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                //
            }

            public void popupMenuCanceled(PopupMenuEvent e) {
                //
            }
        };
        for (int i = 0; i < count; i++) {
            getMenu(i).getPopupMenu().addPopupMenuListener(popupMenuListener);
        }
    }

    public DBCApplication getApplication() {
        return application;
    }

    public DBCSession getSession() {
        return session;
    }

    public DBCActionManager getActionManager() {
        return getSession() != null ? getSession().getView().getActionManager() : getApplication().getView().getActionManager();
    }

}
