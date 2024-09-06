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
import net.thevpc.common.prs.plugin.Implementation;
import net.thevpc.dbclient.api.sessionmanager.DBCSessionLayoutManager;
import net.thevpc.dbclient.api.sessionmanager.DBCSessionView;
import net.thevpc.dbclient.api.windowmanager.DBCWindow;
import net.thevpc.dbclient.api.windowmanager.DBCWindowKind;
import net.thevpc.dbclient.api.windowmanager.DBCWindowListener;
import net.thevpc.common.swing.dialog.MessageDialogType;

import java.sql.SQLException;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 10 dec. 2006 15:49:28
 */
@Implementation(priority = 2)
public class DBCSessionLayoutManagerCompact extends DBCAbstractPluggable implements DBCSessionLayoutManager {
    @Inject
    DBCSession session;

    public DBCSessionLayoutManagerCompact() {

    }

    public void doLayout() {
        DBCSessionView view = session.getView();
        DBCWindow we = session.getApplication().getView().getWindowManager().addWindow(
                view.getMainComponent(),
                DBCWindowKind.SESSION,
                session,
                session.getConfig().getSessionInfo().getName() + " : " + session.getApplication().getApplicationInfo().getProductLongTitle(),
                null
        );
        we.addWindowListener(new DBCWindowListener() {
            public void windowClosed(DBCWindow window) {
                try {
                    session.close();
                } catch (SQLException e) {
                    session.getView().getDialogManager().showMessage(null, null, MessageDialogType.ERROR, null, e);
                }
            }

            public void windowHidden(DBCWindow window) {
            }

            public void windowOpened(DBCWindow window) {
            }
        });
        we.showWindow();
    }
}
