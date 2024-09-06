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
package net.thevpc.dbclient.plugin.system.actionmanager.applicationactions;

import net.thevpc.dbclient.api.DBCApplication;
import net.thevpc.dbclient.api.DBCSession;
import net.thevpc.dbclient.api.actionmanager.DBCActionLocation;
import net.thevpc.dbclient.api.actionmanager.DBCApplicationFolderAction;
import net.thevpc.common.swing.prs.PRSManager;
import net.thevpc.common.swing.messageset.JLocaleMenu;
import net.thevpc.common.swing.dialog.MessageDialogType;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Locale;
import java.util.logging.Level;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 31 janv. 2006 15:47:00
 */
public class DBCLocaleFolderAction extends DBCApplicationFolderAction {

    public DBCLocaleFolderAction() {
        super("Menu.localeMenu", "locales");
        addLocationPath(DBCActionLocation.MENUBAR, "/options");
        setPosition(Integer.MIN_VALUE + 1020);
        //PRSManager.update(this, dbClient);
    }

    @Override
    public boolean acceptSession(DBCSession session) {
        return session == null;
    }

    @Override
    public Component addToComponent(Component component) {
        JLocaleMenu langMenu = new JLocaleMenu(DBCApplication.BOOT_LOCALE);
        langMenu.setSelectedLocale(getApplication().getView().getLocale());
        langMenu.addLocaleSelectedListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                Locale loc = (Locale) evt.getNewValue();
                try {
                    //setLocale(loc);
                    getApplication().getView().setLocale(loc);
                } catch (Exception e1) {
                    getApplication().getView().getDialogManager().showMessage(null, null, MessageDialogType.ERROR, null, e1);
                }
            }
        });
        PRSManager.addMessageSetSupport(langMenu, "Menu.localeMenu");
        if (component instanceof JPopupMenu) {
            ((JPopupMenu) component).add(langMenu);
        } else if (component instanceof JMenuBar) {
            ((JMenuBar) component).add(langMenu);
        } else if (component instanceof JMenu) {
            ((JMenu) component).add(langMenu);
        } else if (component instanceof JToolBar) {
            ((JToolBar) component).add(langMenu);
        } else {
            getApplication().getLogger(DBCArtSetFolderAction.class.getName()).log(Level.SEVERE, "(id={0} for component type ({1}) unsupported", new Object[]{getKey(), component.getClass().getName()});
        }
        return null;
    }
}
