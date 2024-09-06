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

import net.thevpc.dbclient.api.DBCApplicationView;
import net.thevpc.dbclient.api.DBCSession;
import net.thevpc.dbclient.api.actionmanager.DBCActionLocation;
import net.thevpc.dbclient.api.actionmanager.DBCApplicationFolderAction;
import net.thevpc.common.swing.prs.PRSManager;
import net.thevpc.common.prs.artset.ArtSet;
import net.thevpc.common.prs.artset.ArtSetManager;
import net.thevpc.common.swing.artset.JArtSetMenu;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.logging.Level;
import net.thevpc.common.prs.plugin.PluginDescriptor;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 31 janv. 2006 15:47:00
 */
public class DBCArtSetFolderAction extends DBCApplicationFolderAction {

    public DBCArtSetFolderAction() {
        super("Menu.artSetMenu", "artsets");
        addLocationPath(DBCActionLocation.MENUBAR, "/options");
        setPosition(Integer.MIN_VALUE + 1000);
        //PRSManager.update(this, dbClient);
    }

    @Override
    public boolean acceptSession(DBCSession session) {
        return session == null;
    }

    @Override
    public Component addToComponent(Component component) {
        ArtSet a = getApplication().getView().getArtSet();
        
        ArrayList<ArtSet> all=new ArrayList<ArtSet>();
        for (ArtSet v : ArtSetManager.getArtSets()) {
            Object owner=v.getOwner();
            boolean ok=false;
            if(owner==null){
                ok=true;
            }else if (owner instanceof PluginDescriptor){
                PluginDescriptor d=(PluginDescriptor)owner;
                if(getApplication().getPluginManager().isPluginEnabled(d.getId())){
                    ok=true;
                }
            }
            if(ok){
                all.add(v);
            }
        }
        
        final JArtSetMenu artSetMenu = new JArtSetMenu(a == null ? null : a.getId(),all.toArray(new ArtSet[all.size()]));
        PRSManager.addMessageSetSupport(artSetMenu, getKey());
        getApplication().getView().addPropertyChangeListener(DBCApplicationView.PROPERTY_ARTSET, new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                artSetMenu.setArtSet((String) evt.getNewValue());
            }
        });
        artSetMenu.addArtSetChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                try {
                    String newArtset = (String) evt.getNewValue();
                    getApplication().getView().setArtSet(ArtSetManager.getArtSet(newArtset));
                } catch (Exception e) {
                    getApplication().getLogger(getClass().getName()).log(Level.SEVERE,"Unable to set ArtSet",e);
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
            getApplication().getLogger(DBCArtSetFolderAction.class.getName()).log(Level.SEVERE, "(id={0} for component type ({1}) unsupported", new Object[]{getKey(), component.getClass().getName()});
        }
        return null;
    }
}
