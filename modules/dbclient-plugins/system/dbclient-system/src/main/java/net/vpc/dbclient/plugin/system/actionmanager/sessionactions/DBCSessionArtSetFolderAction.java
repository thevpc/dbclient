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

import net.vpc.dbclient.api.DBCApplicationView;
import net.vpc.dbclient.api.actionmanager.DBCActionLocation;
import net.vpc.dbclient.api.actionmanager.DBCSessionFolderAction;
import net.vpc.prs.artset.ArtSet;
import net.vpc.prs.artset.ArtSetManager;
import net.vpc.swingext.artset.JArtSetMenu;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.logging.Level;
import net.vpc.prs.iconset.IconSet;
import net.vpc.prs.plugin.PluginDescriptor;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 31 janv. 2006 15:47:00
 */
public class DBCSessionArtSetFolderAction extends DBCSessionFolderAction {

    public DBCSessionArtSetFolderAction() {
        super("Menu.artSetMenu", "artsets");
        addLocationPath(DBCActionLocation.MENUBAR, "/options");
        //PRSManager.update(this, pluginSession);
    }

    @Override
    public Component addToComponent(Component component) {
        ArtSet a = getSession().getView().getArtSet();
        ArrayList<ArtSet> all = new ArrayList<ArtSet>();
        for (ArtSet v : ArtSetManager.getArtSets()) {
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


        final JArtSetMenu artSetMenu = new JArtSetMenu(a == null ? null : a.getId(), all.toArray(new ArtSet[all.size()]));
        getSession().getView().addPropertyChangeListener(DBCApplicationView.PROPERTY_ARTSET, new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                artSetMenu.setArtSet((String) evt.getNewValue());
            }
        });
        artSetMenu.addArtSetChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                try {
                    String newArtset = (String) evt.getNewValue();
                    getSession().getView().setArtSet(ArtSetManager.getArtSet(newArtset));
                } catch (Exception e) {
                    getSession().getLogger(DBCSessionArtSetFolderAction.class.getName()).log(Level.SEVERE, "setArtSet failed", e);
                }
            }
        });

        if (component instanceof JPopupMenu) {
            ((JPopupMenu) component).add(artSetMenu);
        } else if (component instanceof JMenuBar) {
            ((JMenuBar) component).add(artSetMenu);
        } else if (component instanceof JMenu) {
            ((JMenu) component).add(artSetMenu);
        } else if (component instanceof JToolBar) {
            ((JToolBar) component).add(artSetMenu);
        } else {
            getSession().getLogger(DBCSessionArtSetFolderAction.class.getName()).log(Level.SEVERE, "(id={0} for component type ({1}) unsupported", new Object[]{getKey(), component.getClass().getName()});
        }
        return null;
    }
}
