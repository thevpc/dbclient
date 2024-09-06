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
import net.thevpc.dbclient.api.actionmanager.DBClientAction;
import net.thevpc.dbclient.api.viewmanager.DBCToolBar;
import net.thevpc.common.swing.dialog.MessageDialogType;

import javax.swing.*;
import java.awt.*;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 25 mars 2007 13:59:03
 */
public class DBCToolBarImpl extends JToolBar implements DBCToolBar {
    private DBCApplication dbclient;
    private DBCSession session;

    public DBCToolBarImpl() {
        super();
        //this.setLayout(new BoxLayout(this,BoxLayout.LINE_AXIS));
    }

    private static DBCActionFilter DEFAULT_APP_TOOL_BAR_FILTER = new DBCActionFilter() {
        public boolean accept(DBClientAction action) {
            return true;
        }
    };

    public void init(DBCApplication dbClient, DBCSession session) {
        this.session = session;
        this.dbclient = (dbClient == null) ? session.getApplication() : dbClient;
        try {
            getSession().getView().getActionManager().fillComponent(this, DBCActionLocation.TOOLBAR, DEFAULT_APP_TOOL_BAR_FILTER);
        } catch (Throwable e) {
            getSession().getView().getDialogManager().showMessage(null, "Unable to create TOOLBAR", MessageDialogType.ERROR, null, e);
        }

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
