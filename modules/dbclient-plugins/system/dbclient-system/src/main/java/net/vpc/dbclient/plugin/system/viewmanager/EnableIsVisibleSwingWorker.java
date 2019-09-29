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

package net.vpc.dbclient.plugin.system.viewmanager;

import net.vpc.dbclient.api.DBCSession;
import net.vpc.dbclient.api.viewmanager.DBCChunkOperation;
import net.vpc.swingext.dialog.MessageDialogType;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 2006/11/07
 */
public abstract class EnableIsVisibleSwingWorker extends SwingWorker<Object, DBCChunkOperation> {
    protected JPopupMenu popupMenu;
    protected boolean shouldShow;
    protected DBCSession session;

    public EnableIsVisibleSwingWorker(JPopupMenu popupMenu, DBCSession session) {
        this.popupMenu = popupMenu;
        this.session = session;
    }

    public abstract void prepareVars();

    public abstract void endProcess();

    protected Object doInBackground() throws Exception {
        try {
            prepareVars();
            Component[] children = popupMenu.getComponents();
            shouldShow = false;
            for (Component child : children) {
                validateMenuAbility(child);
            }
            if (!session.getConfig().getBooleanProperty("ui.showDisabledMenus", false)) {
                publish(new DBCChunkOperation() {
                    public void executeOperation() {
                        Component[] children = popupMenu.getComponents();
                        for (Component child : children) {
                            validateMenuVisibility(child);
                        }

                    }
                });
            }

            endProcess();
        } catch (Throwable e) {
            session.getView().getDialogManager().showMessage(null, null, MessageDialogType.ERROR, null, e);
        }
        return null;
    }

    private void validateMenuAbility(Component menuElement) {
        if (menuElement instanceof JMenu) {
            JMenu m = (JMenu) menuElement;
            for (Component c : m.getMenuComponents()) {
                validateMenuAbility(c);
            }
        } else if (menuElement instanceof JPopupMenu) {
            JPopupMenu m = (JPopupMenu) menuElement;
            for (Component c : m.getComponents()) {
                validateMenuAbility(c);
            }
        } else if (menuElement instanceof JMenuBar) {
            JMenuBar m = (JMenuBar) menuElement;
            for (Component c : m.getComponents()) {
                validateMenuAbility(c);
            }
        } else {
            validateComponent(menuElement);
        }
    }

    private boolean validateMenuVisibility(Component menuElement) {
        boolean v = false;
        Component[] children = null;
        if (menuElement instanceof JMenu) {
            JMenu m = (JMenu) menuElement;
            children = m.getMenuComponents();
        } else if (menuElement instanceof JPopupMenu) {
            JPopupMenu m = (JPopupMenu) menuElement;
            children = (m.getComponents());
        } else if (menuElement instanceof JMenuBar) {
            JMenuBar m = (JMenuBar) menuElement;
            children = (m.getComponents());
        } else if (menuElement instanceof JPopupMenu.Separator || menuElement instanceof JToolBar.Separator) {
            return false;
        } else {
            boolean b = menuElement.isEnabled();
            menuElement.setVisible(b);
            return b;
        }
        if (children != null) {
            Component lastVisible = null;
            for (Component c : children) {
                if (validateMenuVisibility(c)) {
                    v = true;
                    lastVisible = c;
                } else if (c instanceof JPopupMenu.Separator || c instanceof JToolBar.Separator) {
                    if (lastVisible == null) {
                        c.setVisible(false);
                    } else if (lastVisible instanceof JPopupMenu.Separator || lastVisible instanceof JToolBar.Separator) {
                        c.setVisible(false);
                    } else {
                        c.setVisible(true);
                        lastVisible = c;
                    }
                }
            }
            menuElement.setVisible(v);
        }
        return v;
    }

    public boolean isShouldShow() {
        return shouldShow;
    }

    public abstract boolean validateComponent(Component child);

    protected void process(List<DBCChunkOperation> chunks) {
        for (DBCChunkOperation chunk : chunks) {
            chunk.executeOperation();
        }
    }

    public JPopupMenu getPopupMenu() {
        return popupMenu;
    }
}

