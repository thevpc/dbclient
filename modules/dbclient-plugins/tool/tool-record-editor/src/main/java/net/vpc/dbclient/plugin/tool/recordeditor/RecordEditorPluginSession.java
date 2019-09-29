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

package net.vpc.dbclient.plugin.tool.recordeditor;

import net.vpc.dbclient.api.DBCSession;
import net.vpc.dbclient.api.pluginmanager.DBCAbstractPluginSession;
import net.vpc.dbclient.api.sessionmanager.DBCInternalWindow;
import net.vpc.dbclient.api.sessionmanager.DBCSessionView;
import net.vpc.dbclient.api.sql.objects.DBTable;
import net.vpc.dbclient.api.sql.objects.DBTableColumn;
import net.vpc.dbclient.plugin.tool.recordeditor.propeditor.ColumnView;
import net.vpc.dbclient.plugin.tool.recordeditor.propeditor.TableView;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyVetoException;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 27 avr. 2007 21:03:25
 */
public class RecordEditorPluginSession extends DBCAbstractPluginSession {
    public RecordEditorPluginSession() {
    }

    @Override
    public RecordEditorPlugin getPlugin() {
        return (RecordEditorPlugin) super.getPlugin();
    }

    public ColumnView createColumnView(DBTableColumn node) {
        ColumnView colView = createUserColumnView(node);
        if (colView == null) {
            colView = getPlugin().createDefaultColumnView(node);
        }
        colView.init(this);
        colView.setColumn(node);
        colView.configure();
        return colView;
    }

    public ColumnView createUserColumnView(DBTableColumn node) {
        String pathValue = getColumnView(node);
        if (pathValue != null) {
            try {
                return (ColumnView) getSession().getFactory().newInstance(null,Class.forName(pathValue),getPlugin().getDescriptor());
            } catch (Throwable e) {
                //
            }
        }
        return null;
    }


    public TableView createTableView(DBCSession session, DBTable object) {
        return new DefaultTableView(this, object);
    }

    public void addWindow(TableView tableView) {
        String tt = getSession().getConfig().getPathValue(tableView.getTable().getStringPath(), "TableTitle");
        String title = tt == null ? (tableView.getTable().getFullName() + " Editor") : tt;
        addWindow(tableView.getComponent(), title, getSession().getView().getObjectIcon(tableView.getTable()));
    }

    public void addWindow(JComponent comp, String title, Icon icon) {
        boolean newTab = false;
        if (newTab) {
            DBCInternalWindow w = getSession().getView().addWindow(
                    comp, DBCSessionView.Side.Workspace, false
            );
            w.setTitle(title);
            w.setIcon(icon);
        } else {
            RecordEditorExplorer r = getInstance(true);
            JInternalFrame jif = new JInternalFrame(title);
            jif.setContentPane(comp);
            jif.pack();
            jif.setVisible(true);
            jif.setClosable(true);
            jif.setIconifiable(true);
            jif.setFrameIcon(icon);
            jif.setMaximizable(true);
            jif.setResizable(true);
            //jif.setIcon(getIcon());
            r.getDesktop().add(jif);
            try {
                jif.setSelected(true);
            } catch (PropertyVetoException e) {
                //e.printStackTrace();
            }
            jif.moveToFront();
        }
    }

    public RecordEditorExplorer getInstance(boolean create) {
        DBCInternalWindow w = getInstanceWindow(create);
        if (w != null) {
            return (RecordEditorExplorer) w.getMainComponent();
        }
        return null;
    }

    public DBCInternalWindow getInstanceWindow(boolean create) {
        for (DBCInternalWindow dbcInternalWindow : getSession().getView().getWorkspaceContainer().getWindows()) {
            Component mainComponent = dbcInternalWindow.getMainComponent();
            if (mainComponent instanceof RecordEditorExplorer) {
                return dbcInternalWindow;
            }
        }
        if (create) {
            RecordEditorExplorer e = new RecordEditorExplorer();
            DBCInternalWindow window = getSession().getView().addWindow(e, DBCSessionView.Side.Workspace, true);
            window.setTitle("Record Editor Windows");
            return window;
        }
        return null;
    }

    public boolean isSelector(DBTableColumn column){
        return ("true".equals(getSession().getConfig().getPathValue(column.getStringPath(), "ColumnIsSelector")));
    }

    public void setSelector(DBTableColumn column,Boolean value){
        getSession().getConfig().setPathValue(column.getStringPath(), "ColumnIsSelector", value==null?null:String.valueOf(value));
    }

    public void setColumnTitle(DBTableColumn column,String value){
        getSession().getConfig().setPathValue(column.getStringPath(), "ColumnTitle", value);
    }

    public String getColumnTitle(DBTableColumn column){
        String g = getSession().getConfig().getPathValue(column.getStringPath(), "ColumnTitle");
        if (g == null || g.length() == 0) {
            g = column.getName();
        }
        return g;
    }

    public void setColumnGroup(DBTableColumn column,String value){
        getSession().getConfig().setPathValue(column.getStringPath(), "ColumnGroup", revalidateGroup(value));
    }

    private String revalidateGroup(String s){
        StringBuilder sb=new StringBuilder();
        if(s!=null){
            for(int i=0;i<s.length();i++){
                char c=s.charAt(i);
                if(c!='/' || (c=='/' && sb.length()>0 && sb.charAt(sb.length()-1)!='/')){
                   sb.append(c);
                }
            }
        }
        while (sb.length()>0 && sb.charAt(sb.length()-1)=='/'){
           sb.delete(sb.length()-1,sb.length());
        }
        return sb.length()>0? sb.toString():null;
    }

    public String getColumnGroup(DBTableColumn column){
        return revalidateGroup(getSession().getConfig().getPathValue(column.getStringPath(), "ColumnGroup"));
    }


    public void setColumnView(DBTableColumn column,String value){
        getSession().getConfig().setPathValue(column.getStringPath(), "ColumnView", value);
    }

    public String getColumnView(DBTableColumn column){
        return getSession().getConfig().getPathValue(column.getStringPath(), "ColumnView");
    }


}
