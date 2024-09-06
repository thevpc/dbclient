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

package net.thevpc.dbclient.plugin.system.sessionmanager.tree;

import net.thevpc.dbclient.api.sessionmanager.DBCExplorerNode;
import net.thevpc.dbclient.api.sessionmanager.DBCSessionExplorer;
import net.thevpc.dbclient.api.sql.objects.DBObject;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 25 janv. 2006 09:08:21
 */
public class JdbcPopupMenu extends JPopupMenu {
    private DBCSessionExplorer tree;
    private DBObject currentNode;
    private DBObject[] currentObjectSelections;

    public JdbcPopupMenu(DBCSessionExplorer tree0) {
        this.tree = tree0;
        tree.getTreeComponent().addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                prepareForShowingPopupMenu(e);
            }

        });
    }

    public JMenu getMenuByName(String key, boolean createIfNotFound, int createIfNotFoundAtPosition) {
        Component[] children = getComponents();
        for (Component child : children) {
            if (child instanceof JMenu) {
                JMenu j = (JMenu) child;
                if (key.equals(j.getName())) {
                    return j;
                }
            }
        }
        if (createIfNotFound) {
            JMenu menuItem = new JMenu(key);
            menuItem.setName(key);
            if (createIfNotFoundAtPosition >= 0 && createIfNotFoundAtPosition <= children.length) {
                add(menuItem, createIfNotFoundAtPosition);
            } else {
                add(menuItem);
            }
            return menuItem;
        }
        return null;
    }


    public void prepareForShowingPopupMenu(MouseEvent e) {
        TreePath selPath = tree.getTreeComponent().getPathForLocation(e.getX(), e.getY());

        if (selPath == null) {
            return;
        }
        Object node = selPath.getLastPathComponent();
        if (node == null)
            return;
        currentNode = ((DBCExplorerNode) node).getDBObject();

        TreePath[] selectionPaths = tree.getTreeComponent().getSelectionPaths();
        currentObjectSelections = null;
        if (selectionPaths != null) {
            boolean clickedOnSelection = false;
            for (TreePath selectionPath : selectionPaths) {
                if (selectionPath.equals(selPath)) {
                    clickedOnSelection = true;
                    break;
                }
            }
            if (clickedOnSelection) {
                currentObjectSelections = new DBObject[selectionPaths.length];
                for (int i = 0; i < selectionPaths.length; i++) {
                    TreePath selectionPath = selectionPaths[i];
                    Object node2 = selectionPath.getLastPathComponent();
                    if (node2 == null) {
                        currentObjectSelections = null;
                        break;
                    }
                    currentObjectSelections[i] = ((DBCExplorerNode) node2).getDBObject();
                }
            }
        }
        if (currentObjectSelections == null) {
            currentObjectSelections = new DBObject[]{currentNode};
        }
        if (e.getClickCount() == 1 && SwingUtilities.isRightMouseButton(e)) {
            JTreeEnableIsVisibleSwingWorker sw = new JTreeEnableIsVisibleSwingWorker(JdbcPopupMenu.this, e, tree);
            sw.execute();
        }
    }


    public DBObject getCurrentNode() {
        return currentNode;
    }

    public DBObject[] getCurrentNodes() {
        return currentObjectSelections;
    }

    public void reset() {
        currentNode = null;
        currentObjectSelections = new DBObject[0];
    }

}
