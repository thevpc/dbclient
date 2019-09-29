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
import net.vpc.dbclient.api.actionmanager.DBCAsynchActionListener;
import net.vpc.dbclient.plugin.tool.recordeditor.propeditor.ColumnView;
import net.vpc.dbclient.plugin.tool.recordeditor.propeditor.TableView;
import net.vpc.dbclient.api.sql.SQLRecord;
import net.vpc.dbclient.api.sql.objects.DBTable;
import net.vpc.dbclient.api.sql.objects.DBTableColumn;
import net.vpc.swingext.PRSManager;
import net.vpc.swingext.DumbGridBagLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 3 août 2005 17:18:31
 */
public class DefaultTableView extends JPanel implements TableView {

    private JButton loadButton;
    private JButton clearButton;
    private JButton copyButton;
    private BasicStatusBar statusBar;
    private JButton saveButton;
    private JButton delButton;
    private boolean insertMode;
    public Component mainAncestor;
    private DBTable dbTableInfo;
    private Map<DBTableColumn, ColumnView> renderers = new HashMap<DBTableColumn, ColumnView>();
    private RecordEditorPluginSession pluginSession;

    public DefaultTableView(RecordEditorPluginSession pluginSession, DBTable dbTableInfo) {
        super(new BorderLayout());
        this.pluginSession = pluginSession;
        this.dbTableInfo = dbTableInfo;
        dbTableInfo.getChildCount();
        saveButton = PRSManager.createButton("Save");
        copyButton = PRSManager.createButton("Copy");
        clearButton = PRSManager.createButton("New");
        delButton = PRSManager.createButton("Delete");
        loadButton = PRSManager.createButton("Load");
//        saveButton.setEnabled(dbTableInfo.getColumnsFolder().isAllPkLoaded());
//        delButton.setEnabled(dbTableInfo.getColumnsFolder().isAllPkLoaded());
        loadButton.setEnabled(true);


        JToolBar south = new JToolBar();
        south.add(Box.createHorizontalGlue());
        south.add(loadButton);
        south.add(new JToolBar.Separator());
        south.add(saveButton);
        south.add(delButton);
        south.add(new JToolBar.Separator());
        south.add(copyButton);
        south.add(new JToolBar.Separator());
        south.add(clearButton);

        saveButton.addActionListener(new DBCAsynchActionListener(saveButton.getName(),null, getSession()) {

            public void actionPerformedImpl(ActionEvent e) {
                doSave();
            }
        });

        loadButton.addActionListener(new DBCAsynchActionListener(loadButton.getName(),null, getSession()) {

            public void actionPerformedImpl(ActionEvent e) {
                doLoad();
            }
        });

        delButton.addActionListener(new DBCAsynchActionListener(delButton.getName(),null, getSession()) {

            public void actionPerformedImpl(ActionEvent e) {
                doDelete();
            }
        });

        clearButton.addActionListener(new DBCAsynchActionListener(clearButton.getName(),null, getSession()) {

            public void actionPerformedImpl(ActionEvent e) {
                doClear();
            }
        });

        copyButton.addActionListener(new DBCAsynchActionListener(copyButton.getName(),null, getSession()) {

            public void actionPerformedImpl(ActionEvent e) {
                doCopy();
            }
        });
        statusBar = new BasicStatusBar();
        JComponent ii = initComponents(getGroups());

        boolean hasPk = false;
        for (DBTableColumn col : dbTableInfo.getColumns()) {
            if (col.isPk()) {
                hasPk = true;
                break;
            }
        }

        if(!hasPk){
            JPanel p=new JPanel(new BorderLayout());
            p.add(ii, BorderLayout.CENTER);
            JLabel warning=new JLabel("<html><font color=red><H1>Warning : No PK detected!</H1></font></html>");
            p.add(warning, BorderLayout.PAGE_START);
            ii=p;
        }
        ii.setMinimumSize(new Dimension(400, 280));
        ii.setPreferredSize(new Dimension(400, 280));
        JPanel center = new JPanel(new BorderLayout());
        center.add(south, BorderLayout.PAGE_END);
        center.add(ii, BorderLayout.CENTER);
        this.add(center, BorderLayout.CENTER);
        this.add(statusBar, BorderLayout.SOUTH);
        PRSManager.setComponentResourceSetHolder(this, pluginSession);
        PRSManager.update(this, pluginSession);
    }

    public void showInfo(String message) {
        statusBar.showInfo(message);
    }

    public void showError(String message) {
        statusBar.showError(message);
    }

    public void showWarning(String message) {
        statusBar.showWarning(message);
    }

    private GroupInfo getGroups() {
        GroupInfo all = new GroupInfo();
        for (DBTableColumn tableColumn : dbTableInfo.getColumns()) {
            String g = pluginSession.getColumnGroup(tableColumn);
            all.find(g).columns.add(tableColumn);
        }
        return all;
    }

    protected class GroupInfo {

        String title;
        ArrayList<DBTableColumn> columns = new ArrayList<DBTableColumn>();
        ArrayList<GroupInfo> children = new ArrayList<GroupInfo>();
        int level = 0;

        GroupInfo find(String group) {
            if (group == null || group.trim().length() == 0) {
                return this;
            }
            if (group.indexOf('/') >= 0) {
                String[] strings = group.trim().split("/");
                String string0 = strings.length > 0 ? strings[0] : "";
                if (string0 == null) {
                    string0 = "";
                }
                StringBuilder n = new StringBuilder();
                for (int i = 1; i < strings.length; i++) {
                    if (n.length() > 0) {
                        n.append('/');
                    }
                    n.append(strings[i].trim());
                }
                for (GroupInfo child : children) {
                    if (string0.equals(child.title)) {
                        return child.find(n.toString());
                    }
                }
                //not found so add new
                GroupInfo g = new GroupInfo();
                g.title = string0;
                g.level = level + 1;
                children.add(g);
                return g.find(n.toString());
            } else {
                for (GroupInfo child : children) {
                    if (group.equals(child.title)) {
                        return child;
                    }
                }
                //not found so add new
                GroupInfo g = new GroupInfo();
                g.title = group;
                g.level = level + 1;
                children.add(g);
                return g;
            }
        }
    }

    private JComponent initComponents(GroupInfo group) {
        DBTableColumn[] columns = group.columns.toArray(new DBTableColumn[group.columns.size()]);
        GroupInfo[] groups = group.children.toArray(new GroupInfo[group.children.size()]);
        if (group.level == 0) {
            if (columns.length > 0 && groups.length > 0) {
                JPanel p = new JPanel(new DumbGridBagLayout().addLine("[<-=H]").addLine("[<+=$$B]").addLine("[B]").addLine("[B]"));
                JTabbedPane tp = new JTabbedPane();
                for (int i = 0; i < groups.length; i++) {
                    GroupInfo groupInfo = groups[i];
                    tp.addTab(groupInfo.title, initComponents(groupInfo));
                }
                p.add(initComponents(columns), "H");
                p.add(tp, "B");
                return p;
            } else if (columns.length > 0) {
                return (initComponents(columns));
            } else if (groups.length > 0) {
                JPanel p = new JPanel(new BorderLayout());
                JTabbedPane tp = new JTabbedPane();
                for (int i = 0; i < groups.length; i++) {
                    JComponent jComponent = initComponents(groups[i]);
                    jComponent.setBorder(BorderFactory.createTitledBorder(groups[i].title));
                    tp.addTab(groups[i].title, jComponent);
                }
                p.add(tp);
                return p;
            } else {
                throw new IllegalStateException("Never");
            }
        } else {
            if (columns.length > 0 && groups.length > 0) {
                JPanel p = new JPanel();
                StringBuilder sb = new StringBuilder("[<+=$X.]\n");
                for (int i = 0; i < groups.length; i++) {
                    sb.append("[<+=$A").append(i).append("]");
                    if (group.level % 2 == 0) {
                        sb.append("\n");
                    }
                }
                p.setLayout(new DumbGridBagLayout(sb.toString()));
                for (int i = 0; i < groups.length; i++) {
                    JComponent jComponent = initComponents(groups[i]);
                    jComponent.setBorder(BorderFactory.createTitledBorder(groups[i].title));
                    p.add(jComponent, "A" + i);
                }
                p.add(initComponents(columns), "X");
                return p;
            } else if (columns.length > 0) {
                return (initComponents(columns));
            } else if (groups.length > 0) {
                JPanel p = new JPanel();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < groups.length; i++) {
                    sb.append("[<+=$A").append(i).append("]");
                    if (group.level % 2 == 0) {
                        sb.append("\n");
                    }
                }
                p.setLayout(new DumbGridBagLayout(sb.toString()));
                for (int i = 0; i < groups.length; i++) {
                    JComponent jComponent = initComponents(groups[i]);
                    jComponent.setBorder(BorderFactory.createTitledBorder(groups[i].title));
                    p.add(jComponent, "A" + i);
                }

                return p;
            } else {
                throw new IllegalStateException("Never");
            }
        }
    }

    private JComponent initComponents(DBTableColumn[] cols) {
        JPanel center = new JPanel(new GridBagLayout());


        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0;
        c.weighty = 0;

        c.gridx = 0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.weightx = 0;
        center.add(Box.createVerticalStrut(10), c);
        c.gridy++;
        Insets insets1 = new Insets(1, 1, 1, 1);
        Insets insets2 = new Insets(2, 3, 2, 3);
        Insets insets3 = new Insets(2, 8, 2, 3);
        Insets insets4 = new Insets(1, 1, 1, 8);
        for (DBTableColumn tableColumn : cols) {
            ColumnView defaulColumnEditor = getFieldEditor(tableColumn);
            Dimension d = defaulColumnEditor.getPreferredGridDimension();

            c.fill = GridBagConstraints.NONE;
            c.insets = insets3;
            c.gridx = 0;
            c.gridwidth = 1;
            c.weightx = 0;
            center.add(defaulColumnEditor.getLabelComponent(), c);

            c.fill = GridBagConstraints.BOTH;
            c.insets = insets2;
            c.gridx = d.width;
            c.gridwidth = 3;
            c.weightx = 3;
            center.add(defaulColumnEditor.getEditComponent(), c);
            Component[] cc = defaulColumnEditor.getSuffixComponents();
            for (int j = 0; j < cc.length; j++) {
                c.fill = GridBagConstraints.NONE;
                c.insets = j == cc.length - 1 ? insets4 : insets1;
                c.gridx = 3 + j + d.width;
                c.gridwidth = 1;
                c.weightx = 0;
                center.add(cc[j], c);
            }
            c.gridy += d.height;
        }
        JScrollPane pane = new JScrollPane(center);
//        pane.setMinimumSize(new Dimension(400, 200));
//        pane.setPreferredSize(new Dimension(400, 200));
        return pane;
    }

    public void doClear() {
        setRecord(null);
    }

    public Component getMainAncestor() {
        return mainAncestor;
    }

    public void setMainAncestor(Component mainAncestor) {
        this.mainAncestor = mainAncestor;
    }

    public int doInsert() throws SQLException {
        return getSession().getConnection().executeInsertRecord(
                dbTableInfo.getCatalogName(), dbTableInfo.getSchemaName(), dbTableInfo.getName(), getRecord());
    }

    public void doLoad() {
        DefaultTableList l = new DefaultTableList(pluginSession, dbTableInfo);
        SQLRecord record = l.showDialog();
        if (record != null) {
            setInsertMode(false);
            setRecord(record);
        }
    }

    public void doSave() {
        try {
            if (isInsertMode()) {
                if (doInsert() != 1) {
                    showError("You could not delete this configuration : Unable to Save Record");
                } else {
                    setInsertMode(false);
                    showInfo("Row Inserted successfully");
                }
            } else {
                if (doUpdate() != 1) {
                    showError("Unable to Update Record");
                } else {
                    showInfo("Row Updated successfully");
                }
            }
        } catch (Exception e) {
            showError(e.getMessage() == null ? e.toString() : e.getMessage());
        }
    }

    public int doUpdate() throws SQLException {
        return getSession().getConnection().executeUpdateRecord(dbTableInfo.getCatalogName(), dbTableInfo.getSchemaName(), dbTableInfo.getName(), getRecord());
    }

    public void doCopy() {
        for (Map.Entry<DBTableColumn, ColumnView> entry : renderers.entrySet()) {
            DBTableColumn column = entry.getKey();
            ColumnView columnView = entry.getValue();
            if (column.isPk()) {
                columnView.setValue(null);
            }
        }
    }

    public void doDelete() {
        if (JOptionPane.OK_OPTION != JOptionPane.showConfirmDialog(this, "This action wil delete permanently the current row. Proceed?", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE)) {
            return;
        }
        try {
            getSession().getConnection().executeDeleteRecord(dbTableInfo.getCatalogName(), dbTableInfo.getSchemaName(), dbTableInfo.getName(), getRecord());
            showInfo("Row Deleted successfully");
        } catch (Exception e) {
            showError(e.getMessage() == null ? e.toString() : e.getMessage());
        }
    }

    public SQLRecord getRecord() {
        SQLRecord sqlRecord = new SQLRecord();
        for (Map.Entry<DBTableColumn, ColumnView> entry : renderers.entrySet()) {
            DBTableColumn column = entry.getKey();
            ColumnView columnView = entry.getValue();
            if (isInsertMode() && columnView.isInsertable()) {
                sqlRecord.put(column.getName(), columnView.getValue());
            } else if (!isInsertMode() && columnView.isUpdatable()) {
                sqlRecord.put(column.getName(), columnView.getValue());
            }
        }
        return sqlRecord;
    }

    public void setRecord(SQLRecord record) {
        if (record == null) {
            setInsertMode(true);
            for (Map.Entry<DBTableColumn, ColumnView> entry : renderers.entrySet()) {
//                TableColumn column = entry.getKey();
                ColumnView columnView = entry.getValue();
                columnView.setValue(null);
            }
        } else {
            for (Map.Entry<DBTableColumn, ColumnView> entry : renderers.entrySet()) {
                DBTableColumn column = entry.getKey();
                ColumnView columnView = entry.getValue();
                columnView.setValue(record.get(column.getName()));
            }
        }
    }

    public ColumnView getFieldEditor(DBTableColumn f) {
        ColumnView defaulColumnRenderer = renderers.get(f);
        if (defaulColumnRenderer == null) {
            defaulColumnRenderer = pluginSession.createColumnView(f);
            defaulColumnRenderer.setTableView(this);
            renderers.put(f, defaulColumnRenderer);
        }
        return defaulColumnRenderer;
    }

    public JComponent getComponent() {
        return this;
    }

    public DBCSession getSession() {
        return pluginSession.getSession();
    }

    public String getTableFullName() {
        return dbTableInfo.getFullName();
    }

    public DBTable getTable() {
        return dbTableInfo;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JButton getDelButton() {
        return delButton;
    }

    public boolean isInsertMode() {
        return insertMode;
    }

    public void setInsertMode(boolean insertMode) {
        this.insertMode = insertMode;
        for (ColumnView columnView : renderers.values()) {
            columnView.setInsertMode(insertMode);
        }
    }

    public JButton getClearButton() {
        return clearButton;
    }

    public JButton getCopyButton() {
        return copyButton;
    }
}
