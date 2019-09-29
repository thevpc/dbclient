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

import net.vpc.dbclient.api.sessionmanager.DBCExplorerModel;
import net.vpc.dbclient.api.sessionmanager.DBCExplorerNode;
import net.vpc.dbclient.api.sessionmanager.DBCSessionExplorer;
import net.vpc.dbclient.api.sql.objects.DBObject;

/**
 * some parts of code are privided by Joseph Bowbeer in the DynamicTree example
 *
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 8 mars 2007 21:44:38
 */
public class DBObjectNodeFactory implements TreeNodeFactory<DBCExplorerNode> {

    /**
     * Absolute path to root file.
     */
    private final DBObject rootPath;
    private final DBCSessionExplorer dbcSessionExplorer;
    private final DBCExplorerModel explorerModel;


    public DBObjectNodeFactory(DBObject rootPath, DBCExplorerModel explorerModel, DBCSessionExplorer dbcSessionExplorer) {
        this.rootPath = rootPath;
        this.explorerModel = explorerModel;
        this.dbcSessionExplorer = dbcSessionExplorer;
    }

    /**
     * {@inheritDoc}
     */
    public DBCExplorerNode[] createChildren(Object userObject) {
        /* Find parent directory. */
        DBObject parent;
        if (userObject instanceof DBObject) {
            // file link
            parent = (DBObject) userObject;
        } else {
            // root link
            parent = rootPath;
        }
        /* Create children. */
        parent.ensureLoading();
        int count = parent.getChildCount();
        DBCExplorerNode[] children =
                new DBCExplorerNode[count];
        for (int i = 0; i < count; i++) {
            DBObject child = parent.getChild(i);
            boolean leaf = child.isLeaf();
            DBCExplorerNode explorerNode = dbcSessionExplorer.getSession().getFactory().newInstance(DBCExplorerNode.class);
            explorerNode.init(dbcSessionExplorer, explorerModel, child, !leaf);
            children[i] = explorerNode;
        }
        return children;
    }
}
