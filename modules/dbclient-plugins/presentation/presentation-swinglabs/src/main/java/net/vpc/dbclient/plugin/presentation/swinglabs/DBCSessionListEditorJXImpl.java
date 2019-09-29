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

package net.vpc.dbclient.plugin.presentation.swinglabs;

import java.util.HashMap;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;
import net.vpc.dbclient.api.configmanager.DBCSessionInfo;
import net.vpc.prs.plugin.Implementation;
import net.vpc.dbclient.api.sessionmanager.DBCSessionListEditor;
import net.vpc.dbclient.plugin.system.sessionmanager.chooser.DBCSessionListEditorImpl;
import net.vpc.swingext.ComponentResourcesUpdater;
import net.vpc.swingext.PRSManager;
import net.vpc.prs.iconset.IconSet;
import net.vpc.prs.messageset.MessageSet;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import java.util.Hashtable;
import java.util.Map;
import java.util.logging.Level;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 31 janv. 2006 15:47:00
 */
@Implementation(priority = 10)
public class DBCSessionListEditorJXImpl extends DBCSessionListEditorImpl {

    private JXTreeTable connectionTree;

    public DBCSessionListEditorJXImpl() {
        PRSManager.addSupport(this, DBCSessionListEditorJXImpl.class.getName(), new ComponentResourcesUpdater() {
            @Override
            public void update(JComponent comp, String id, MessageSet messageSet, IconSet iconSet) {
                loadConfig();
            }
        });
    }

    @Override
    public void loadConfig() {
        Map<String, SessionNode> folders = new HashMap<String, SessionNode>();
        DBCSessionInfo dbcSessionInfo = new DBCSessionInfo();
        dbcSessionInfo.setName("root");
        SessionNode root = new SessionNode(dbcSessionInfo, true);

        DBCSessionInfo[] sessionFiles = getApplication().getConfig().getSessions();
        for (DBCSessionInfo file : sessionFiles) {
            try {
                SessionNode child = new SessionNode(file, false);
                String sessionPath = child.getFile().getPath();
                if (sessionPath != null && sessionPath.trim().length() == 0) {
                    sessionPath = null;
                }
                SessionNode parent = root;
                if (sessionPath != null) {
                    String[] pathElements = sessionPath.split("/");
                    for (String pathElement : pathElements) {
                        String spath = (parent == root ? "" : (parent.getFile().getQualifiedName() + "/")) + pathElement;
                        SessionNode sessionNode = folders.get(spath);
                        if (sessionNode == null) {
                            DBCSessionInfo snfo = new DBCSessionInfo();
                            snfo.setName(pathElement);
                            snfo.setPath(parent == root ? null : parent.getFile().getQualifiedName());
                            sessionNode = new SessionNode(snfo, true);
                            folders.put(spath, sessionNode);
                            parent.add(sessionNode);
                        }
                        parent = sessionNode;
                    }
                }
                parent.add(child);
            } catch (Throwable ex) {
                getApplication().getLogger(getClass().getName()).log(Level.SEVERE, "LoadConfig Failed",ex);
            }
        }

        checkCreated();
        connectionTree.setTreeTableModel(new SessionsModel(root, getApplication()));
        connectionTree.expandAll();
        connectionTree.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                updateButtonsStatus();
            }
        });
        int rowCount = connectionTree.getRowCount();
        int selectedSession = getApplication().getConfig().getIntegerProperty(DBCSessionListEditor.PRP_SESSION_ID, -1);
        for (int i = 0; i < rowCount; i++) {
            TreePath path = connectionTree.getPathForRow(i);
            if (path != null) {
                SessionNode c = (SessionNode) path.getLastPathComponent();
                if (c != null && !c.isDir() && c.getFile().getId() == selectedSession) {
                    connectionTree.setRowSelectionInterval(i, i);
                    break;
                }
            }
        }
        connectionTree.packAll();
    }


    protected void checkCreated() {
        if (connectionTree == null) {
            connectionTree = new JXTreeTable(new DefaultTreeTableModel());
            connectionTree.setColumnControlVisible(true);
            connectionTree.setHorizontalScrollEnabled(true);
            connectionTree.setHighlighters(HighlighterFactory.createSimpleStriping(HighlighterFactory.QUICKSILVER));
            connectionTree.setShowHorizontalLines(true);
            connectionTree.setSortable(true);
            connectionTree.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        }
    }

    @Override
    protected JComponent createSessionList() {
        checkCreated();
        return new JScrollPane(connectionTree);
    }

    @Override
    protected DBCSessionInfo getSelectedSession() {
        SessionNode sessionNode = getSelectedNode();
        return (sessionNode != null && !sessionNode.isDir()) ? sessionNode.getFile() : null;
    }

    private SessionNode getSelectedNode() {
        int r = connectionTree.getSelectedRow();
        if (r >= 0) {
            TreePath path = connectionTree.getPathForRow(r);
            if (path != null) {
                Object c = path.getLastPathComponent();
                if (c != null) {
                    return (SessionNode) c;
                }
            }
        }
        return null;
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        super.setReadOnly(readOnly);
        connectionTree.setEnabled(!readOnly);
    }
}
