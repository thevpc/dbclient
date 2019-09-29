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
package net.vpc.dbclient.plugin.system.actionmanager.applicationactions;

import net.vpc.dbclient.api.DBCSession;
import net.vpc.dbclient.api.actionmanager.DBCActionLocation;
import net.vpc.dbclient.api.actionmanager.DBCApplicationFolderAction;
import net.vpc.swingext.PRSManager;
import net.vpc.swingext.plaf.PlafItem;
import net.vpc.swingext.JPlafMenu;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 31 janv. 2006 15:47:00
 */
public class DBCApplicationPlafFolderAction extends DBCApplicationFolderAction {

    public DBCApplicationPlafFolderAction() {
        super("Menu.plafMenu", "plafs");
        addLocationPath(DBCActionLocation.MENUBAR, "/options");
        setPosition(Integer.MIN_VALUE + 1030);
        //PRSManager.update(this, dbClient);
    }

    @Override
    public boolean acceptSession(DBCSession session) {
        return session == null;
    }

    @Override
    public Component addToComponent(Component component) {
        JPlafMenu plafMenu = null;
        if (getApplication().getView().getLookAndFeelManager().isSupported()) {
            plafMenu = new JPlafMenu(null) {

                @Override
                public void applyPlaf(PlafItem item) throws IllegalAccessException, UnsupportedLookAndFeelException, InstantiationException, ClassNotFoundException {
                    getApplication().getView().setPlaf(item);
                }
            };
            PRSManager.addMessageSetSupport(plafMenu, getKey());

        }
        if (plafMenu != null) {
            if (component instanceof JPopupMenu) {
                ((JPopupMenu) component).add(plafMenu);
            } else if (component instanceof JMenuBar) {
                ((JMenuBar) component).add(plafMenu);
            } else if (component instanceof JMenu) {
                ((JMenu) component).add(plafMenu);
            } else if (component instanceof JToolBar) {
                ((JToolBar) component).add(plafMenu);
            } else {
                getApplication().getLogger(DBCApplicationPlafFolderAction.class.getName()).log(Level.SEVERE, "(id={0} for component type ({1}) unsupported", new Object[]{getKey(), component.getClass().getName()});
            }
        }
        return null;
    }
}
