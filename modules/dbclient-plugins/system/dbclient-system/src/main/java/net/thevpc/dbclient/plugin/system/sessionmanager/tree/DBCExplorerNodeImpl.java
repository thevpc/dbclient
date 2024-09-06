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

import net.thevpc.dbclient.api.sessionmanager.DBCExplorerModel;
import net.thevpc.dbclient.api.sessionmanager.DBCExplorerNode;
import net.thevpc.dbclient.api.sessionmanager.DBCSessionExplorer;
import net.thevpc.dbclient.api.sql.objects.DBObject;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 23 nov. 2006 00:59:29
 */
public class DBCExplorerNodeImpl extends DefaultMutableTreeNode implements DBCExplorerNode {
    private DBCSessionExplorer tree;
    private DBCExplorerModel model;
//    private boolean loading = false;
//    private boolean loaded = false;
//    private PropertyChangeSupport pcs;

    public void init(DBCSessionExplorer tree, DBCExplorerModel model, DBObject userObject, boolean allowsChildren) {
        parent = null;
        this.allowsChildren = allowsChildren;
        this.userObject = userObject;
        this.tree = tree;
        this.model = model;
    }


//    public synchronized Enumeration children() {
//        return super.children();
//    }
//
//    public synchronized TreeNode getFirstChild() {
//        return super.getFirstChild();
//    }
//
//    public synchronized void add(MutableTreeNode newChild) {
//        super.add(newChild);
//        newChild.setParent(this);
//    }

//    public synchronized int getChildCount() {
//        load();
//        if (loading) {
//            return 0;
//        }
//        return super.getChildCount();
//    }


//    public TreeNode getChildAt(int index) {
//        load();
//        if (loading) {
//            return null;
//        }
//        return super.getChildAt(index);
//    }


//    public synchronized void ensureLoading() {
//        if (!loaded && !loading) {
//            loading = true;
//            try {
//                getDBObject().ensureLoading();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            try {
//                int c = getDBObject().getChildCount();
//                for (int i = 0; i < c; i++) {
//                    DBObject dbObjects = getDBObject().getChild(i);
//                    if (!tree.getSession().getConfig().isExcludedPath(dbObjects.getStringPath())) {
//                        final DBCExplorerNode item = createExplorerNode(dbObjects);
//                        add(item);
//                    }
//                }
//            } finally {
//                loading = false;
//                loaded = true;
//            }
//            SwingUtilities.invokeLater(new Runnable() {
//                public void run() {
//                    int c;
//                    if (getChildCount() > 0) {
//                        c = children.size();
//                        final int[] indices = new int[c];
//                        for (int i = 0; i < c; i++) {
//                            indices[i] = i;
//                        }
//                        model.fireTreeNodesInserted(model, getPath(), indices, children.toArray());
//                    }
//
//                }
//            });
//        }
//    }

//    private synchronized void load() {
//        new Thread(
//                new Runnable() {
//                    public void run() {
//                        ensureLoading();
//                    }
//                }
//        ).start();
//    }

//    private DBCExplorerNode createExplorerNode(DBObject n) {
//        return new DBCExplorerNode(tree, model, n);
//    }

    public DBObject getDBObject() {
        return (DBObject) super.getUserObject();
    }


//    public synchronized void invalidate() {
//        if (loaded) {
//            if (children != null) {
//                getDBObject().invalidate();
//                int c = children.size();
//                int[] indices = new int[c];
//                for (int i = 0; i < c; i++) {
//                    indices[i] = i;
//                }
////                Object[] objects = children.toArray();
//                removeAllChildren();
//                model.nodeStructureChanged(this);
////                model.fireTreeNodesRemoved(model, getPath(), indices, objects);
////                children.clear();
//            }
//            loaded = false;
//        }
//    }
//
//    public boolean isLoading() {
//        return loading;
//    }
//
//    public boolean isLoaded() {
//        return loaded;
//    }
//
//    public synchronized void addPropertyChangeListener(
//            String propertyName,
//            PropertyChangeListener listener) {
//        pcs.addPropertyChangeListener(propertyName, listener);
//    }
//
//    public synchronized void addPropertyChangeListener(
//
//            PropertyChangeListener listener) {
//        pcs.addPropertyChangeListener(listener);
//    }
}
