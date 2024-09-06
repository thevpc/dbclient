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

import net.thevpc.dbclient.api.DBCSession;
import net.thevpc.dbclient.api.pluginmanager.DBCAbstractPluggable;
import net.thevpc.common.prs.plugin.Inject;
import net.thevpc.dbclient.api.sessionmanager.DBCSessionLayoutManager;
import net.thevpc.dbclient.api.sessionmanager.DBCSessionView;
import net.thevpc.dbclient.api.windowmanager.DBCWindow;
import net.thevpc.dbclient.api.windowmanager.DBCWindowKind;
import net.thevpc.dbclient.api.windowmanager.DBCWindowListener;
import net.thevpc.common.swing.dialog.MessageDialogType;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 10 dec. 2006 15:49:28
 */
public class DBCSessionLayoutManagerExploded extends DBCAbstractPluggable implements DBCSessionLayoutManager, DBCWindowListener {
    @Inject
    private DBCSession session;

    public DBCSessionLayoutManagerExploded() {

    }

    DBCWindow we;
    DBCWindow wt;
    DBCWindow ws;

    public void doLayout() {
        DBCSessionView view = session.getView();
        we = session.getApplication().getView().getWindowManager().addWindow(
                view.getExplorerContainer().getComponent(),
                DBCWindowKind.SESSION_EXPLORER,
                session,
                session.getConfig().getSessionInfo().getName() + " : " + session.getApplication().getApplicationInfo().getProductLongTitle(),
                null
        );

        wt = session.getApplication().getView().getWindowManager().addWindow(
                view.getTracerContainer().getComponent(),
                DBCWindowKind.SESSION_TRACER,
                session,
                session.getConfig().getSessionInfo().getName() + " : " + session.getApplication().getApplicationInfo().getProductLongTitle(),
                null
        );


        JPanel panel = new JPanel(new BorderLayout());
        JPanel subPanel = new JPanel(new BorderLayout());
        subPanel.add(view.getToolbar(), BorderLayout.PAGE_START);
        subPanel.add(view.getWorkspaceContainer().getComponent(), BorderLayout.CENTER);
        panel.add(view.getMenu(), BorderLayout.PAGE_START);
        panel.add(subPanel, BorderLayout.CENTER);
        panel.add(view.getStatusBar(), BorderLayout.PAGE_END);

        ws = session.getApplication().getView().getWindowManager().addWindow(
                panel,
                DBCWindowKind.SESSION_WORKSPACE,
                session,
                session.getConfig().getSessionInfo().getName() + " : " + session.getApplication().getApplicationInfo().getProductLongTitle(),
                null
        );

        we.addWindowListener(this);
        wt.addWindowListener(this);
        ws.addWindowListener(this);

        we.showWindow();
        wt.showWindow();
        ws.showWindow();
    }


    public void windowClosed(DBCWindow window) {
        if (we == window) {
            we = null;
        }
        if (ws == window) {
            ws = null;
        }
        if (wt == window) {
            wt = null;
        }
        if (ws == null && we == null && wt == null) {
            try {
                session.close();
            } catch (SQLException e) {
                session.getView().getDialogManager().showMessage(null, null, MessageDialogType.ERROR, null, e);
            }
        }
    }

    public void windowHidden(DBCWindow window) {
    }

    public void windowOpened(DBCWindow window) {
    }
}
