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

import java.util.logging.Level;
import org.jdesktop.swingx.treetable.TreeTableNode;
import org.jdesktop.swingx.treetable.AbstractTreeTableModel;
import net.thevpc.dbclient.api.DBCApplication;
import net.thevpc.dbclient.api.configmanager.DBCSessionInfo;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
* @creationtime 28 nov. 2006 22:59:33
*/
public class SessionsModel extends AbstractTreeTableModel {
    DBCApplication application;
    SessionNode root;

    public SessionsModel(SessionNode root, DBCApplication application) {
        super(root);
        this.application=application;
    }

    public Object getChild(Object parent, int index) {
        return wrap(parent).getChildren().get(index);
    }

    private SessionNode wrap(Object o) {
        if(o instanceof SessionNode){
            return (SessionNode) o;
        }else if(o instanceof TreeTableNode){
            return (SessionNode) (((TreeTableNode)o).getUserObject());
        }else{
            return (SessionNode) o;
        }
    }
    public int getChildCount(Object parent) {
        return wrap(parent).getChildren().size();
    }

    public int getColumnCount() {
        return 5;
    }

    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return application.getView().getMessageSet().get("Name");
            case 1:
                return application.getView().getMessageSet().get("Type");
            case 2:
                return application.getView().getMessageSet().get("Server");
            case 3:
                return application.getView().getMessageSet().get("LastAccessedTime");
            case 4:
                return application.getView().getMessageSet().get("CreatedTime");
            default:
                return "Column " + column;
        }
    }

    public Object getValueAt(Object node, int column) {
        SessionNode sessionNode = wrap(node);
        final DBCSessionInfo file = sessionNode.getFile();
        try {
            switch (column) {
                case 0:
                    return file.getName();
                case 1:
                    return sessionNode.isDir()?"":file.getCnxFactoryName();
                case 2:
                    return sessionNode.isDir()?"":file.getServer();
                case 3:
                    return sessionNode.isDir()?"":file.getCnxLastUpdated();
                case 4:
                    return sessionNode.isDir()?"":file.getCnxCreated();
            }
        }
        catch (Exception ex) {
            application.getLogger(SessionsModel.class.getName()).log(Level.SEVERE,"getValueAt failed",ex);
        }

        return null;
    }

    public int getIndexOfChild(Object parent, Object child) {
        return wrap(parent).getChildren().indexOf(wrap(child));
    }
}
