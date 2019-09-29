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
package net.vpc.dbclient.plugin.system.sessionmanager.tree;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Level;
import net.vpc.dbclient.api.sessionmanager.DBCExplorerModel;
import net.vpc.dbclient.api.sessionmanager.DBCExplorerNode;
import net.vpc.dbclient.api.sessionmanager.DBCSessionExplorer;
import net.vpc.dbclient.api.sql.objects.DBServer;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.sql.SQLException;
import java.util.ArrayList;
import net.vpc.dbclient.api.DBCSession;
import net.vpc.dbclient.api.sessionmanager.DBCExplorerModelListener;
import net.vpc.dbclient.api.sql.objects.DBCatalog;
import net.vpc.dbclient.api.sql.objects.DBCatalogFolder;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 13 juil. 2005 14:33:09
 */
public class DBCExplorerModelImpl extends DefaultTreeModel implements DBCExplorerModel {

    private DBCSession session;
    private ArrayList<DBCExplorerModelListener> listeners = new ArrayList<DBCExplorerModelListener>(2);

    public DBCExplorerModelImpl() {
        super(new DefaultMutableTreeNode());
        setAsksAllowsChildren(true);
    }

    public void init(DBCSessionExplorer tree) throws SQLException {
        DBServer database = tree.getSession().getConnection().getFactory().newInstance(DBServer.class);
        database.init(tree.getSession().getConnection());
        DBCExplorerNode explorerNode = tree.getSession().getConnection().getFactory().newInstance(DBCExplorerNode.class);
        explorerNode.init(tree, this, database, true);
        setRoot(explorerNode);
        session = tree.getSession();
        session.addPropertyChangeListener("catalog", new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                catalogChanged();
            }
        });
    }

    private void catalogChanged() {
        DBCExplorerNode _root = getRoot();
        DBServer s = (DBServer) _root.getDBObject();
        DBCatalogFolder catalogFolder = s.getCatalogFolder();
        int c = catalogFolder.getChildCount();
        String dc = null;
        try {
            dc = session.getCatalog();
        } catch (SQLException ex) {
            //ignore
        }
        boolean update = false;
        boolean fired = false;
        for (int i = 0; i < c; i++) {
            DBCatalog v = (DBCatalog) catalogFolder.getChild(i);
            String cc = v.getCatalogName();
            boolean defaultBoolean = cc == dc || (cc != null && cc.equalsIgnoreCase(dc));
            if (v.isDefault() != defaultBoolean) {
                if (!fired) {
                    fired = true;
                    for (DBCExplorerModelListener listener : listeners) {
                        listener.modelPreUpdating(false);
                    }
                }
                v.setDefault(defaultBoolean);
            }
        }
        if (fired) {
            int rcc = _root.getChildCount();
            for (int i = 0; i < rcc; i++) {
                DBCExplorerNode fold = (DBCExplorerNode) _root.getChildAt(i);
                if (fold.getDBObject() instanceof DBCatalogFolder) {
                    fireTreeNodesChanged(rcc, getPathToRoot(fold), null, null);
                }
            }
            for (DBCExplorerModelListener listener : listeners) {
                listener.modelPostUpdating(false);
            }
        }
    }

    @Override
    public DBCExplorerNode getRoot() {
        return (DBCExplorerNode) super.getRoot();
    }

    public synchronized void update() {
        try {
            getRoot().getDBObject().invalidate();
            reload();
        } catch (Exception e) {
//            root = new Database("Unable to extract data", "Database");
            session.getLogger(DBCExplorerModelImpl.class.getName()).log(Level.SEVERE, "Update Explorer failed", e);
//            dbClient.setErr().println(e);
        }
    }

    @Override
    public void fireTreeNodesChanged(Object source, Object[] path, int[] childIndices, Object[] children) {
        super.fireTreeNodesChanged(source, path, childIndices, children);
    }

    @Override
    public void fireTreeNodesInserted(Object source, Object[] path, int[] childIndices, Object[] children) {
        super.fireTreeNodesInserted(source, path, childIndices, children);
    }

    @Override
    public void fireTreeNodesRemoved(Object source, Object[] path, int[] childIndices, Object[] children) {
        super.fireTreeNodesRemoved(source, path, childIndices, children);
    }

    @Override
    public void fireTreeStructureChanged(Object source, Object[] path, int[] childIndices, Object[] children) {
        super.fireTreeStructureChanged(source, path, childIndices, children);
    }

    @Override
    public void addExplorerModelListener(DBCExplorerModelListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeExplorerModelListener(DBCExplorerModelListener listener) {
        listeners.remove(listener);
    }
}
