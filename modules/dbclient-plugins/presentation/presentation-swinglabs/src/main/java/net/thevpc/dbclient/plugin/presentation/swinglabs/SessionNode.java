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

package net.thevpc.dbclient.plugin.presentation.swinglabs;

import net.thevpc.dbclient.api.configmanager.DBCSessionInfo;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Vector;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
* @creationtime 28 nov. 2006 22:59:42
*/
public class SessionNode extends DefaultMutableTreeNode {
    DBCSessionInfo file;
    boolean isDir;
    public SessionNode(DBCSessionInfo file,boolean isDir) {
        this.file = file;
        this.isDir = isDir;
    }

    @Override
    public boolean getAllowsChildren() {
        return isDir;
    }

    public DBCSessionInfo getFile() {
        return file;
    }

    public java.util.List getChildren(){
        if(children==null){
            children=new Vector();
        }
        return children;
    }
    @Override
    public boolean isLeaf() {
        return !isDir;
    }

    @Override
    public String toString() {
        return file.getName();
    }

    public boolean isDir() {
        return isDir;
    }
}
