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

/**
 * some parts of code are privided by Joseph Bowbeer in the DynamicTree example
 *
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 8 mars 2007 21:44:18
 */
public interface TreeNodeFactory<T> {
    /**
     * Creates and returns an array of child nodes for a newly-expanded parent
     * node. Called on worker thread. The <tt>userObject</tt> parameter is
     * the parent node's link to its counterpart in the remote model.
     * <p/>
     * The {@link javax.swing.tree.DefaultMutableTreeNode#allowsChildren allowsChildren} property
     * of each child is set false if the corresponding remote node is a leaf;
     * otherwise it is set true to indicate that the child can be expanded.
     * Each child is also assigned a <tt>userObject</tt> that links the child
     * to its counterpart in the remote model.
     *
     * @param userObject parent node's link to remote model
     * @return array of children
     * @throws Exception if failed to create children
     */
    T[] createChildren(Object userObject) throws Exception;
}
