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
package net.thevpc.dbclient.plugin.system.actionmanager.sessionactions;

import net.thevpc.dbclient.api.actionmanager.DBCActionLocation;
import net.thevpc.dbclient.api.actionmanager.DBCSessionFolderAction;
import net.thevpc.common.swing.prs.PRSManager;
import net.thevpc.common.prs.iconset.IconSet;
import net.thevpc.common.swing.iconset.JIconSetMenu;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.logging.Level;
import net.thevpc.common.prs.iconset.IconSetDescriptor;
import net.thevpc.common.prs.iconset.IconSetManager;
import net.thevpc.common.swing.iconset.SwingIconSetManager;
import net.thevpc.common.prs.plugin.PluginDescriptor;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 31 janv. 2006 15:47:00
 */
public class DBCSessionIconSetFolderAction extends DBCSessionFolderAction {

    public DBCSessionIconSetFolderAction() {
        super("Menu.iconSetMenu", "iconsets");
        addLocationPath(DBCActionLocation.MENUBAR, "/options");
        //PRSManager.update(this, pluginSession);
    }

    @Override
    public Component addToComponent(Component component) {
        IconSet a = getSession().getView().getIconSet();
        ArrayList<IconSetDescriptor> all = new ArrayList<IconSetDescriptor>();
        for (IconSetDescriptor v : IconSetManager.getIconSetDescriptors()) {
            Object owner = v.getOwner();
            boolean ok = false;
            if (owner == null) {
                ok = true;
            } else if (owner instanceof PluginDescriptor) {
                PluginDescriptor d = (PluginDescriptor) owner;
                if (getSession().isPluginEnabled(d.getId())) {
                    ok = true;
                }
            }
            if (ok) {
                all.add(v);
            }
        }
        JIconSetMenu iconSetMenu = new JIconSetMenu(a == null ? null : a.getId(), all.toArray(new IconSetDescriptor[all.size()]));
        iconSetMenu.addIconSetChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                try {
                    String newIconset = (String) evt.getNewValue();
                    getSession().getView().setIconSet(PRSManager.getIconSet(newIconset));
                } catch (Exception e) {
                    getSession().getLogger(DBCSessionIconSetFolderAction.class.getName()).log(Level.SEVERE, "setIconSet failed", e);
                }
            }
        });

        if (component instanceof JPopupMenu) {
            ((JPopupMenu) component).add(iconSetMenu);
        } else if (component instanceof JMenuBar) {
            ((JMenuBar) component).add(iconSetMenu);
        } else if (component instanceof JMenu) {
            ((JMenu) component).add(iconSetMenu);
        } else if (component instanceof JToolBar) {
            ((JToolBar) component).add(iconSetMenu);
        } else {
            getSession().getLogger(DBCSessionIconSetFolderAction.class.getName()).log(Level.SEVERE, "(id={0} for component type ({1}) unsupported", new Object[]{getKey(), component.getClass().getName()});
        }
        return null;
    }
}
