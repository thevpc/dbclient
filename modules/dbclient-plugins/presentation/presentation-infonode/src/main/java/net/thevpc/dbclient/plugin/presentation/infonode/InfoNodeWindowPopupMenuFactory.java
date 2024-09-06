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

package net.thevpc.dbclient.plugin.presentation.infonode;


import net.infonode.docking.DockingWindow;
import net.infonode.docking.WindowPopupMenuFactory;
import net.thevpc.dbclient.api.pluginmanager.DBCPluginSession;
import net.thevpc.dbclient.api.sessionmanager.DBCInternalWindow;
import net.thevpc.dbclient.api.sessionmanager.DBCInternalWindowPopupFactory;
import net.thevpc.dbclient.api.DBCSession;
import net.thevpc.common.swing.prs.PRSManager;

import javax.swing.*;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 2 dec. 2006 00:32:07
 */
public class InfoNodeWindowPopupMenuFactory implements WindowPopupMenuFactory {
    private DBCSession session;
    private DBCInternalWindowPopupFactory impl;
    private JPopupMenu menu;

    public InfoNodeWindowPopupMenuFactory(DBCSession session) {
        this.session = session;
        impl = (session.getFactory().newInstance(DBCInternalWindowPopupFactory.class));
    }

    public JPopupMenu createPopupMenu(final DockingWindow window) {
        if (menu == null) {
            menu = impl.createPopup();
        }
        final DBCInternalWindow internalWindow = InfoNodeUtils.getInternalWindow(window);
        impl.applyWindow(internalWindow, menu);
        PRSManager.update(menu, session.getView());
        return menu;
    }

}
