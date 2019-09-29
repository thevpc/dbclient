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
package net.vpc.dbclient.plugin.system.actionmanager.sessionactions;

import net.vpc.dbclient.api.DBCApplication;
import net.vpc.dbclient.api.actionmanager.DBCActionLocation;
import net.vpc.dbclient.api.actionmanager.DBCSessionFolderAction;
import net.vpc.dbclient.api.actionmanager.DBClientActionType;
import net.vpc.swingext.PRSManager;
import net.vpc.swingext.messageset.JLocaleMenu;
import net.vpc.swingext.dialog.MessageDialogType;

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
public class DBCSessionLocaleFolderAction extends DBCSessionFolderAction {

    public DBCSessionLocaleFolderAction() {
        super("Menu.localeMenu", "locales");
        addLocationPath(DBCActionLocation.MENUBAR, "/options");
        setType(DBClientActionType.FOLDER);
        //PRSManager.update(this, pluginSession);
    }

    @Override
    public Component addToComponent(Component component) {
        JLocaleMenu langMenu = new JLocaleMenu(DBCApplication.BOOT_LOCALE);
        langMenu.setSelectedLocale(getSession().getView().getLocale());
        langMenu.addLocaleSelectedListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                Locale loc = (Locale) evt.getNewValue();
                try {
                    //setLocale(loc);
                    getSession().getView().setLocale(loc);
                } catch (Exception e1) {
                    getSession().getView().getDialogManager().showMessage(null, null, MessageDialogType.ERROR, null, e1);
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
            getSession().getLogger(DBCSessionLocaleFolderAction.class.getName()).log(Level.SEVERE, "(id=" + getKey() + " for component type (" + component.getClass().getName() + ") unsupported");
        }
        return null;
    }
}
