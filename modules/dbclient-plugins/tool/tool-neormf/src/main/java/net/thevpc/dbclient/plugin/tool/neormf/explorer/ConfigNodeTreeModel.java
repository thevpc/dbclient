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

package net.thevpc.dbclient.plugin.tool.neormf.explorer;

import org.vpc.neormf.jbgen.config.ConfigNode;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 21 avr. 2007 16:41:44
 */
public class ConfigNodeTreeModel implements TreeModel {
    ConfigNode root;

    public ConfigNodeTreeModel(ConfigNode root) {
        this.root = root;
    }

    public Object getRoot() {
        return root;
    }

    public Object getChild(Object parent, int index) {
        return ((ConfigNode)parent).getChild(index);
    }

    public int getChildCount(Object parent) {
        return ((ConfigNode)parent).size();
    }

    public boolean isLeaf(Object node) {
        return ((ConfigNode)node).size()==0;
    }

    public void valueForPathChanged(TreePath path, Object newValue) {
        //
    }

    public int getIndexOfChild(Object parent, Object child) {
        return ((ConfigNode)parent).indexOf((ConfigNode) child);
    }

    public void addTreeModelListener(TreeModelListener l) {
        //
    }

    public void removeTreeModelListener(TreeModelListener l) {
        //
    }
}
