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
package net.thevpc.dbclient.plugin.tool.recordeditor.actions;

import net.thevpc.common.swing.layout.DumbGridBagLayout;
import net.thevpc.dbclient.api.actionmanager.DBCActionLocation;
import net.thevpc.dbclient.api.actionmanager.DBCTreeNodeAction;
import net.thevpc.dbclient.plugin.tool.recordeditor.ColumnProperties;
import net.thevpc.dbclient.plugin.tool.recordeditor.RecordEditorPluginSession;
import net.thevpc.dbclient.api.sql.objects.DBObject;
import net.thevpc.dbclient.api.sql.objects.DBTable;
import net.thevpc.dbclient.api.sql.objects.DBTableColumn;
import net.thevpc.dbclient.api.pluginmanager.DBCPluginSession;
import net.thevpc.common.swing.prs.PRSManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

public class DBObjectEditorConfig extends DBCTreeNodeAction {

    public DBObjectEditorConfig() {
        super("Action.DBObjectEditorConfig");
        addLocationPath(DBCActionLocation.POPUP, "/");
    }

    public boolean shouldEnable(DBObject activeNode, DBObject[] selectedNodes) {
        ArrayList<DBTable> ts = new ArrayList<DBTable>();
        TreeSet<String> tcs = new TreeSet<String>();
        ArrayList<DBTableColumn> cs = new ArrayList<DBTableColumn>();
        for (DBObject selectedNode : selectedNodes) {
            if (selectedNode instanceof DBTable) {
                ts.add((DBTable) selectedNode);
            } else if (selectedNode instanceof DBTableColumn) {
                cs.add((DBTableColumn) selectedNode);
                DBTable t = ((DBTableColumn) selectedNode).getTable();
                if (t != null) {
                    tcs.add(t.getFullName());
                }
            }
        }
        return (ts.size() == 1 && cs.size() == 0) || (ts.size() == 0 && cs.size() > 0 && tcs.size() == 1);
    }

    public void actionPerformedImpl(ActionEvent e) throws Throwable {
        DBObject[] dbObjects = getSelectedNodes();
        if (dbObjects[0] instanceof DBTableColumn) {
            new ColumnProperties(Arrays.asList(dbObjects).toArray(new DBTableColumn[dbObjects.length]),(RecordEditorPluginSession) getPluginSession()).showConfig();
        } else {
            new TableProperties((DBTable) dbObjects[0],getPluginSession()).showConfig();
        }
    }

    public static class TableProperties extends JPanel {

        private DBTable tc;
        private JTextField title;
        private JTextField name;
        private DBCPluginSession pluginSession;
        public TableProperties(DBTable tc,DBCPluginSession pluginSession) {
            super(new DumbGridBagLayout().addLine("[<-nameL][<=-name]").addLine("[<-titleL][<=-title]").setInsets(".*", new Insets(3, 3, 3, 3)));
            this.tc = tc;
            name = new JTextField();
            name.setEditable(false);
            add(PRSManager.createLabel("TableProperties.TableName"), "nameL");
            add(name, "name");
            add(PRSManager.createLabel("TableProperties.TableTitle"), "titleL");
            add(title = new JTextField(), "title");
            PRSManager.setComponentResourceSetHolder(this, pluginSession);
            PRSManager.update(this, pluginSession);
            this.pluginSession=pluginSession;
        }

        public void load() {
            name.setText(tc.getFullName());
            name.setEditable(false);
            String n = pluginSession.getSession().getConfig().getPathValue(tc.getStringPath(), "TableTitle");
            title.setText(n);
            title.setEditable(true);
        }

        public void store() {
            pluginSession.getSession().getConfig().setPathValue(tc.getStringPath(), "TableTitle", title.getText());
        }

        public void showConfig() {
            load();
            int r = JOptionPane.showConfirmDialog(null, this, pluginSession.getMessageSet().get("DBObjectEditorConfig.Column.Title"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (r == JOptionPane.OK_OPTION) {
                store();
            }
        }
    }
}