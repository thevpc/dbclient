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
import net.thevpc.dbclient.api.sessionmanager.DBCInternalWindow;
import net.thevpc.dbclient.api.sessionmanager.DBCInternalWindowPopupFactory;
import net.thevpc.common.swing.prs.PRSManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 7 mai 2007 12:43:34
 */
public class DBCInternalWindowPopupFactoryImpl extends DBCAbstractPluggable implements DBCInternalWindowPopupFactory {
    @Inject
    private DBCSession session;

    public DBCInternalWindowPopupFactoryImpl() {
    }

    public void applyWindow(DBCInternalWindow window, JPopupMenu menu) {
        menu.putClientProperty("DBCInternalWindow", window);
    }

    protected DBCInternalWindow getDBCInternalWindow(ActionEvent e) {
        JComponent c = (JComponent) e.getSource();
        JPopupMenu p = (JPopupMenu) c.getClientProperty("JPopupMenu");
        return (DBCInternalWindow) p.getClientProperty("DBCInternalWindow");
    }

    public JPopupMenu createPopup() {
        final JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem menuItem;
        menuItem = PRSManager.createMenuItem("DBCSessionView.Rename");
        menuItem.putClientProperty("JPopupMenu", popupMenu);
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DBCInternalWindow window = getDBCInternalWindow(e);
                if (window != null) {
                    String old = window.getTitle();
                    String newTitle = JOptionPane.showInputDialog(popupMenu, "Enter new Name", old);
                    if (newTitle != null && !newTitle.equals(old)) {
                        window.setTitle(newTitle);
                    }
                }
            }
        });
        popupMenu.add(menuItem);
        popupMenu.addSeparator();

        menuItem = PRSManager.createMenuItem("DBCSessionView.Close");
        menuItem.putClientProperty("JPopupMenu", popupMenu);
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DBCInternalWindow window = getDBCInternalWindow(e);
                if (window != null) {
                    window.close();
                }
            }
        });
        popupMenu.add(menuItem);

        menuItem = PRSManager.createMenuItem("DBCSessionView.CloseAll");
        menuItem.putClientProperty("JPopupMenu", popupMenu);
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DBCInternalWindow window = getDBCInternalWindow(e);
                if (window != null) {
                    for (DBCInternalWindow internalWindow : window.getWindowBrowser().getWindows()) {
                        if (internalWindow.isClosable()) {
                            internalWindow.close();
                        }
                    }
                }
            }
        });
        popupMenu.add(menuItem);

        menuItem = PRSManager.createMenuItem("DBCSessionView.CloseUnlocked");
        menuItem.putClientProperty("JPopupMenu", popupMenu);
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DBCInternalWindow window = getDBCInternalWindow(e);
                if (window != null) {
                    for (DBCInternalWindow internalWindow : window.getWindowBrowser().getWindows()) {
                        if (internalWindow.isClosable() && !internalWindow.isLocked()) {
                            internalWindow.close();
                        }
                    }
                }
            }
        });
        popupMenu.add(menuItem);

        menuItem = PRSManager.createMenuItem("DBCSessionView.CloseOthers");
        menuItem.putClientProperty("JPopupMenu", popupMenu);
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DBCInternalWindow window = getDBCInternalWindow(e);
                for (DBCInternalWindow internalWindow : window.getWindowBrowser().getWindows()) {
                    if (internalWindow.isClosable() && !internalWindow.equals(window)) {
                        internalWindow.close();
                    }
                }
            }
        });
        popupMenu.add(menuItem);

        popupMenu.addSeparator();

        menuItem = PRSManager.createMenuItem("DBCSessionView.ChangeLockState");
        menuItem.putClientProperty("JPopupMenu", popupMenu);
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DBCInternalWindow window = getDBCInternalWindow(e);
                if (window != null) {
                    window.setLocked(!window.isLocked());
                }
            }
        });
        popupMenu.add(menuItem);

        menuItem = PRSManager.createMenuItem("DBCSessionView.LockAll");
        menuItem.putClientProperty("JPopupMenu", popupMenu);
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DBCInternalWindow window = getDBCInternalWindow(e);
                for (DBCInternalWindow internalWindow : window.getWindowBrowser().getWindows()) {
                    internalWindow.setLocked(true);
                }
            }
        });
        popupMenu.add(menuItem);

        menuItem = PRSManager.createMenuItem("DBCSessionView.UnlockAll");
        menuItem.putClientProperty("JPopupMenu", popupMenu);
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DBCInternalWindow window = getDBCInternalWindow(e);
                for (DBCInternalWindow internalWindow : window.getWindowBrowser().getWindows()) {
                    internalWindow.setLocked(false);
                }
            }
        });
        popupMenu.add(menuItem);

        popupMenu.addSeparator();
        final JCheckBoxMenuItem cmenuItem = PRSManager.createCheckBoxMenuItem("DBCSessionView.LockNew");
        cmenuItem.putClientProperty("JPopupMenu", popupMenu);
        cmenuItem.setSelected(session.getConfig().getBooleanProperty("DBCSessionView.LockNew", false));
        cmenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                session.getConfig().setBooleanProperty("DBCSessionView.LockNew", ((JCheckBoxMenuItem) e.getSource()).isSelected());
            }
        });
        popupMenu.add(cmenuItem);
        return popupMenu;
    }

}
