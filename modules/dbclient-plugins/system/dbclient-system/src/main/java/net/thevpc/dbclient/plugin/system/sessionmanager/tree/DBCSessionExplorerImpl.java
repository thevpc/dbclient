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

import javax.swing.event.TreeModelEvent;

import net.thevpc.common.swing.prs.ComponentResourcesUpdater;
import net.thevpc.dbclient.api.DBCSession;
import net.thevpc.dbclient.api.actionmanager.DBCActionFilter;
import net.thevpc.dbclient.api.actionmanager.DBCActionLocation;
import net.thevpc.dbclient.api.actionmanager.DBCTreeNodeAction;
import net.thevpc.dbclient.api.actionmanager.DBClientAction;
import net.thevpc.common.prs.plugin.Inject;
import net.thevpc.common.prs.plugin.Initializer;
import net.thevpc.dbclient.api.sessionmanager.DBCExplorerModel;
import net.thevpc.dbclient.api.sessionmanager.DBCExplorerNode;
import net.thevpc.dbclient.api.sessionmanager.DBCSQLNodeProvider;
import net.thevpc.dbclient.api.sessionmanager.DBCSessionExplorer;
import net.thevpc.dbclient.api.sql.objects.DBObject;
import net.thevpc.dbclient.api.sql.objects.DBServer;
import net.thevpc.dbclient.api.viewmanager.DBCPluggablePanel;
import net.thevpc.dbclient.api.viewmanager.DBCTree;
import net.thevpc.common.swing.prs.PRSManager;
import net.thevpc.common.prs.iconset.IconSet;
import net.thevpc.common.prs.messageset.MessageSet;
import net.thevpc.common.swing.SwingUtilities3;

import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.datatransfer.Transferable;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Stack;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import javax.swing.event.TreeModelListener;
import net.thevpc.dbclient.api.sessionmanager.DBCExplorerModelListener;

/**
 * Simple implementation  of the DBCSessionExplorer
 * some parts of code are privided by Joseph Bowbeer in the DynamicTree example
 *
 * @author Taha BEN SALAH
 */
public class DBCSessionExplorerImpl extends DBCPluggablePanel implements DBCSessionExplorer {

    @Inject
    private DBCSession session;
    private JdbcPopupMenu popupMenu;
    private DBObjectNodeFactory factory;
    private DBCTree tree;
    private JScrollPane pane;
    private DBCSQLNodeProvider sqlNodeProvider = new DBCSQLNodeProvider() {

        public DBObject[] getNodes() {
            if (popupMenu != null) {
                return popupMenu.getCurrentNodes();
            }
            return new DBObject[0];
        }
    };
    private static DBCActionFilter EXPLORER_FILTER = new DBCActionFilter() {

        public boolean accept(DBClientAction action) {
            return action instanceof DBCTreeNodeAction;
        }
    };

//    private IntrospecterThread introspecterThread = null;
//    private class IntrospecterThread extends Thread {
//        public void run() {
//            DBCExplorerModel m = (DBCExplorerModel) getModel();
//            Database databaseNode = (Database) m.getRoot();
//            databaseNode.getChildCount();
//        }
//    }
    public DBCSessionExplorerImpl() throws SQLException {
        super();
    }

    @Initializer
    public void init() {
        try {
            tree = session.getFactory().newInstance(DBCTree.class);
            tree.setDragEnabled(true);
            tree.setTransferHandler(new ExtendedTreeTransferHandler());
            DBCExplorerModel model = session.getFactory().newInstance(DBCExplorerModel.class);
            model.init(this);
            model.addExplorerModelListener(new DBCExplorerModelListener() {

                @Override
                public void modelPreUpdating(boolean structureChange) {
                    if (structureChange) {
                        saveView();
                    }
                }

                @Override
                public void modelPostUpdating(boolean structureChange) {
                    if (structureChange) {
                        revalidateExpandedPaths();
                    } else {
                        tree.repaint();
                    }
                }
            });
            tree.setModel(model);
            factory = new DBObjectNodeFactory(model.getRoot().getDBObject(), model, this);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        tree.setBorder(null);
        PRSManager.addSupport((JComponent) tree.getComponent(), "DBCSessionExplorerImpl", new ComponentResourcesUpdater() {

            public void update(JComponent comp, String id, MessageSet messageSet, IconSet iconSet) {
                PRSManager.update(popupMenu, messageSet, iconSet);
            }
        });
        tree.addTreeExpansionListener(new TreeExpansionListener() {

            public void treeExpanded(TreeExpansionEvent evt) {
                jTreeTreeExpanded(evt);
            }

            public void treeCollapsed(TreeExpansionEvent evt) {
                jTreeTreeCollapsed(evt);
            }
        });
        tree.addTreeSelectionListener(new TreeSelectionListener() {

            public void valueChanged(TreeSelectionEvent evt) {
                jTreeValueChanged(evt);
            }
        });
        popupMenu = createPopupMenu();
        tree.setCellRenderer(new ExplorerRenderer(session));
        reloadStructure();
        pane = new JScrollPane(tree.getComponent());
        pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        pane.setBorder(null);
        final TreeNodeExpansionMonitor blip = new TreeNodeExpansionMonitor();
        pane.setCorner(JScrollPane.LOWER_TRAILING_CORNER, blip);
        tree.addPropertyChangeListener(DBCSessionExplorer.PROPERTY_EXPANSION_STATUS, new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                if (Boolean.TRUE.equals(evt.getNewValue())) {
                    blip.taskStarted();
                } else {
                    blip.taskStopped();
                }
            }
        });
//        tree.addPropertyChangeListener(DBCSessionExplorer.PROPERTY_STATUS_TEXT, new PropertyChangeListener() {
//            public void propertyChange(PropertyChangeEvent evt) {
//                System.out.println("Tree Status = " + evt.getNewValue());
//            }
//        });
        setLayout(new BorderLayout());
        add(pane, BorderLayout.CENTER);
    }

    private JdbcPopupMenu createPopupMenu() {
        JdbcPopupMenu popup = new JdbcPopupMenu(this);
        getSession().getView().getActionManager().fillComponent(popup, DBCActionLocation.POPUP, EXPLORER_FILTER);
        return popup;
    }

    public void reloadStructure() {
        SwingUtilities3.invokeLater(new Runnable() {

            public void run() {
//                final TreePath[] selectionPaths = getSelectionPaths();
                popupMenu.reset();
                TreeModel m = tree.getModel();
                ((DBCExplorerModel) m).update();
                revalidateExpandedPaths();
//                setSelectionPaths(selectionPaths);
            }
        });
//        startIntrospect();
    }

    public DBObject getCurrentNode() {
        return popupMenu.getCurrentNode();
    }

    public DBObject[] getCurrentNodes() {
        return popupMenu.getCurrentNodes();
    }

    public String[] getExpandedPaths() {
        ArrayList<String> all = new ArrayList<String>();
        Stack<DBCExplorerNode> stack = new Stack<DBCExplorerNode>();
        DBCExplorerNode en = (DBCExplorerNode) tree.getModel().getRoot();
        stack.push(en);
        while (!stack.empty()) {
            en = stack.pop();
            if (tree.isExpanded(new TreePath(en.getPath()))) {
                all.add(en.getDBObject().getStringPath());
            }
            int max = en.getChildCount();
            for (int i = 0; i < max; i++) {
                stack.push((DBCExplorerNode) en.getChildAt(i));
            }
        }
        return all.toArray(new String[all.size()]);
    }

    public void revalidateExpandedPaths() {
        DBCExplorerNode en0 = (DBCExplorerNode) tree.getModel().getRoot();
        tree.collapsePath(new TreePath(en0.getPath()));
        tree.expandPath(new TreePath(en0.getPath()));
//        String[] strings = getSession().getConfig().getExpandedPaths();
//        System.out.println("getSession().getConfig().getExpandedPaths() = " + Arrays.asList(strings));
//        Stack<DBCExplorerNode> stack = new Stack<DBCExplorerNode>();
//        DBCExplorerNode en = (DBCExplorerNode) getModel().getRoot();
////        expandPath(new TreePath(en.getPath()));
//        stack.push(en);
//        while (!stack.empty()) {
//            en = stack.pop();
//            final TreePath treePath = new TreePath(en.getPath());
//            if (getSession().getConfig().isExpandedPath(en.getDBObject().getStringPath())) {
//                System.out.println("\t\texpanding " + en.getDBObject().getStringPath());
//                expandPath(treePath);
//                int max = en.getChildCount();
//                for (int i = 0; i < max; i++) {
//                    stack.push((DBCExplorerNode) en.getChildAt(i));
//                }
//            } else {
//                System.out.println("\t\tcollaping " + en.getDBObject().getStringPath());
//                collapsePath(treePath);
//            }
//        }
    }

    public DBCSession getSession() {
        return session;
    }

    public void updateUI() {
        if (tree != null) {
            tree.updateUI();
            JPopupMenu componentPopupMenu = tree.getComponentPopupMenu();
            if (componentPopupMenu != null) {
                SwingUtilities.updateComponentTreeUI(componentPopupMenu);
            }
        }
        if (popupMenu != null) {
            SwingUtilities.updateComponentTreeUI(popupMenu);
        }
//        TreeCellRenderer cellRenderer = getCellRenderer();
//        if (cellRenderer != null && cellRenderer instanceof Component) {
//            SwingUtilities.updateComponentTreeUI((Component) cellRenderer);
//        }
        if (session != null) {
            tree.setCellRenderer(new ExplorerRenderer(session));
        }
        if (tree != null) {
            TreeCellEditor cellEditor = tree.getCellEditor();
            if (cellEditor != null && cellEditor instanceof Component) {
                SwingUtilities.updateComponentTreeUI((Component) cellEditor);
            }
        }
    }

    public JComponent getComponent() {
        return pane;
    }

    public DBCTree getTreeComponent() {
        return tree;
    }

    public DBServer getServerNode() {
        return (DBServer) ((DBCExplorerNode) (((DBCExplorerModel) tree.getModel()).getRoot())).getDBObject();
    }

    public void saveView() {
        String[] expandedPaths = getExpandedPaths();
        getSession().getConfig().clearExpandedPaths();
        for (String p : expandedPaths) {
            getSession().getConfig().setExpandedPath(p, true);
        }
    }

    public DBCSQLNodeProvider getSqlNodeProvider() {
        return sqlNodeProvider;
    }

    public void setSqlNodeProvider(DBCSQLNodeProvider sqlNodeProvider) {
        this.sqlNodeProvider = sqlNodeProvider;
    }

    private void jTreeTreeExpanded(TreeExpansionEvent evt) {
        stopWorker();
        DBCExplorerNode node =
                (DBCExplorerNode) evt.getPath().getLastPathComponent();
        if (factory != null) {
            startWorker(factory, node);
        }
    }

    /**
     * Called when a node is collapsed. Stops the active worker, if any, and
     * removes all the children.
     *
     * @param evt event
     */
    private void jTreeTreeCollapsed(TreeExpansionEvent evt) {
//        stopWorker();
        DBCExplorerNode node =
                (DBCExplorerNode) evt.getPath().getLastPathComponent();
        node.removeAllChildren();
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();

        /*
         * To avoid having JTree re-expand the root node, we disable
         * ask-allows-children when we notify JTree about the new node
         * structure.
         */

        model.setAsksAllowsChildren(false);
        model.nodeStructureChanged(node);
        model.setAsksAllowsChildren(true);

//        jlblStatus.setText("Collapsed "+node);
    }

    /**
     * Updates the status line when a node is selected.
     *
     * @param evt event
     */
    private void jTreeValueChanged(TreeSelectionEvent evt) {
        DBCExplorerNode node = (DBCExplorerNode) evt.getPath().getLastPathComponent();
        String s = evt.isAddedPath() ? "Selected " + (node.getDBObject()).getStringPath() : "";
//        firePropertyChange(PROPERTY_STATUS_TEXT, null, s);
    }

    //    SwingWorker worker;
    protected void startWorker(final TreeNodeFactory<DBCExplorerNode> fac,
            final DBCExplorerNode node) {
        final Object userObject = node.getDBObject();

        SwingWorker<DBCExplorerNode[], DBCExplorerNode[]> worker = new SwingWorker<DBCExplorerNode[], DBCExplorerNode[]>() {

            protected DBCExplorerNode[] doInBackground() throws Exception {
                /* Create children for the expanded node. */
                return fac.createChildren(userObject);
            }

            protected void done() {
                /*
                 * Set the worker to null and stop the animation, but only if we
                 * are the active worker.
                 */
//                if (worker == this) {
//                    worker = null;
                firePropertyChange(PROPERTY_EXPANSION_STATUS, true, false);
//                }
                try {
                    /*
                     * Get the children created by the factory and insert them
                     * into the local tree model.
                     */
                    DBCExplorerNode[] children = get();
                    node.removeAllChildren();
                    for (int i = 0; i < children.length; i++) {
                        node.insert(children[i], i);
                    }
                    DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
                    model.nodeStructureChanged(node);
                    firePropertyChange(PROPERTY_STATUS_TEXT, null, "Expanded " + node);
                    for (DBCExplorerNode child : children) {
                        DBObject e = child.getDBObject();
                        if (getSession().getConfig().isExpandedPath(e.getStringPath())) {
                            tree.expandPath(new TreePath(child.getPath()));
//                            startWorker(fac,n);
                        }
                    }
                } catch (CancellationException ex) {
                    firePropertyChange(PROPERTY_STATUS_TEXT, null, "Failed expanding " + node + ": cancelled");
                } catch (ExecutionException ex) {
                    /* Handle exceptions thrown by the factory method. */
                    Throwable err = ex.getCause();
                    firePropertyChange(PROPERTY_STATUS_TEXT, null, "Failed expanding " + node + ": " + err);
                } catch (InterruptedException ex) {
                    // event-dispatch thread won't be interrupted
                    throw new IllegalStateException(ex + "");
                }
            }
        };

        /* Start worker, update status line, and start animation. */
        worker.execute();
        firePropertyChange(PROPERTY_STATUS_TEXT, null, "Expanding " + node + "...");
        firePropertyChange(PROPERTY_EXPANSION_STATUS, false, true);
    }

    /**
     * Stops the active worker, if any.
     */
    protected void stopWorker() {
//        if (worker != null) {
//            worker.cancel(true);
        // worker set to null in finished
//        }
    }

    static class ExtendedTreeTransferHandler extends TransferHandler implements Comparator {

        private JTree tree;

        /**
         * Create a Transferable to use as the source for a data transfer.
         *
         * @param c The component holding the data to be transfered.  This
         *          argument is provided to enable sharing of TransferHandlers by
         *          multiple components.
         * @return The representation of the data to be transfered.
         */
        protected Transferable createTransferable(JComponent c) {
            if (c instanceof JTree) {
                tree = (JTree) c;
                TreePath[] paths = tree.getSelectionPaths();

                if (paths == null || paths.length == 0) {
                    return null;
                }

                StringBuffer plainBuf = new StringBuffer();
                StringBuffer htmlBuf = new StringBuffer();

                htmlBuf.append("<html>\n<body>\n<ul>\n");

                TreeModel model = tree.getModel();
                TreePath lastPath = null;
                TreePath[] displayPaths = getDisplayOrderPaths(paths);

                for (int i = 0; i < displayPaths.length; i++) {
                    TreePath path = displayPaths[i];

                    Object node = path.getLastPathComponent();
                    boolean leaf = model.isLeaf(node);
                    String label = getDisplayString(path, true, leaf);

                    plainBuf.append(label + "\n");
                    htmlBuf.append("  <li>" + label + "\n");
                }

                // remove the last newline
                plainBuf.deleteCharAt(plainBuf.length() - 1);
                htmlBuf.append("</ul>\n</body>\n</html>");


                TreePath selectedPath = tree == null ? null : tree.getSelectionPath();
                DBCExplorerNode node = (DBCExplorerNode) (selectedPath == null ? null : (selectedPath.getLastPathComponent()));
                tree = null;
                return new ExtendedBasicTransferable(plainBuf.toString(), htmlBuf.toString(), node == null ? null : node.getDBObject());
            }

            return null;
        }

        public int compare(Object o1, Object o2) {
            int row1 = tree.getRowForPath((TreePath) o1);
            int row2 = tree.getRowForPath((TreePath) o2);
            return row1 - row2;
        }

        String getDisplayString(TreePath path, boolean selected, boolean leaf) {
            int row = tree.getRowForPath(path);
            boolean hasFocus = tree.getLeadSelectionRow() == row;
            Object node = path.getLastPathComponent();
            return tree.convertValueToText(node, selected, tree.isExpanded(row),
                    leaf, row, hasFocus);
        }

        /**
         * Selection paths are in selection order.  The conversion to
         * HTML requires display order.  This method resorts the paths
         * to be in the display order.
         */
        TreePath[] getDisplayOrderPaths(TreePath[] paths) {
            // sort the paths to display order rather than selection order
            ArrayList selOrder = new ArrayList();
            for (int i = 0; i < paths.length; i++) {
                selOrder.add(paths[i]);
            }
            Collections.sort(selOrder, this);
            int n = selOrder.size();
            TreePath[] displayPaths = new TreePath[n];
            for (int i = 0; i < n; i++) {
                displayPaths[i] = (TreePath) selOrder.get(i);
            }
            return displayPaths;
        }

        public int getSourceActions(JComponent c) {
            return COPY;
        }
    }

    public void requestFocus() {
        tree.requestFocus();
    }
}
