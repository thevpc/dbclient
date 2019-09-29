package net.vpc.dbclient.plugin.tool.recordeditor;

import net.vpc.dbclient.api.sql.objects.DBTableColumn;
import net.vpc.dbclient.api.sql.objects.DBTable;
import net.vpc.dbclient.api.DBCSession;
import net.vpc.dbclient.plugin.tool.recordeditor.propeditor.ColumnView;
import net.vpc.swingext.DumbGridBagLayout;
import net.vpc.swingext.PRSManager;

import javax.swing.*;
import java.awt.*;
import java.util.TreeSet;

/**
 * Created by IntelliJ IDEA.
* User: vpc
* Date: 11 janv. 2009
* Time: 16:59:43
* To change this template use File | Settings | File Templates.
*/
public class ColumnProperties extends JPanel {

    private DBTableColumn[] tableColumns;
    private JComboBox isTitle;
    private JTextField title;
    private JTextField name;
    private JComboBox group;
    private JComboBox view;
    private RecordEditorPluginSession pluginSession;

    public RecordEditorPluginSession getPluginSession() {
        return pluginSession;
    }

    public DBCSession getSession() {
        return getPluginSession().getSession();
    }

    public ColumnProperties(DBTableColumn[] tableColumns,RecordEditorPluginSession pluginSession) {
        super(new DumbGridBagLayout().addLine("[<-nameL][<=-name]").addLine("[<-titleL][<=-title]").addLine("[<-groupL][<=-group]").addLine("[<-viewL][<=-view]").addLine("[<-selectorFieldL][<=-selectorField]").setInsets(".*", new Insets(3, 3, 3, 3)));
        this.pluginSession=pluginSession;
        this.tableColumns = tableColumns;
        name = new JTextField();
        name.setEditable(false);
        view = new JComboBox();
        add(PRSManager.createLabel("ColumnProperties.ColumnName"), "nameL");
        add(name, "name");
        add(PRSManager.createLabel("ColumnProperties.ColumnView"), "viewL");
        add(view, "view");
        add(PRSManager.createLabel("ColumnProperties.ColumnGroup"), "groupL");
        add(group = new JComboBox(), "group");
        group.setEditable(true);
        add(PRSManager.createLabel("ColumnProperties.ColumnTitle"), "titleL");
        add(title = new JTextField(), "title");
        isTitle = new JComboBox();
        add(PRSManager.createLabel("ColumnProperties.ColumnIsSelector"), "selectorFieldL");
        add(isTitle, "selectorField");
        PRSManager.setComponentResourceSetHolder(this, getPluginSession());
        PRSManager.update(this, getPluginSession());
        view.setRenderer(new DefaultListCellRenderer() {

            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                String sv = (String) value;
                if (sv == null) {
                    value = " ";
                } else {
                    int x = sv.lastIndexOf('.');
                    value = x > 0 ? sv.substring(x + 1) : x;
                    value = getPluginSession().getMessageSet().get("Action.ColumnViewSelection." + value);
                }
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                return this;
            }
        });
        isTitle.setRenderer(new DefaultListCellRenderer() {

            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                String sv = (String) value;
                if (sv == null) {
                    value = " ";
                } else if (sv.equals("true")) {
                    value = "Yes";
                } else {
                    value = "No";
                }
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                return this;
            }
        });
    }

    public void load() {
        view.removeAllItems();
        isTitle.removeAllItems();
        group.removeAllItems();

        isTitle.addItem(null);
        isTitle.addItem("true");
        isTitle.addItem("false");

        RecordEditorPlugin recordEditorPlugin = (RecordEditorPlugin) getPluginSession().getPlugin();
        if (tableColumns.length == 1) {
            DBTableColumn tc = this.tableColumns[0];
            ColumnView[] validColumnView = recordEditorPlugin.getValidColumnView(tc);
            view.addItem(null);
            for (ColumnView columnView : validColumnView) {
                view.addItem(columnView.getClass().getName());
            }
            DBTable table = tc.getTable();
            TreeSet<String> groups = new TreeSet<String>();
            for (DBTableColumn tableColumn : table.getColumns()) {
                String g = pluginSession.getColumnGroup(tableColumn);
                if (g != null && !groups.contains(g)) {
                    groups.add(g);
                }
            }
            for (String s : groups) {
                group.addItem(s);
            }
            group.setSelectedItem(pluginSession.getColumnGroup(tc));
            name.setText(tc.getFullName());
            title.setText(pluginSession.getColumnTitle(tc));
            isTitle.setSelectedItem(String.valueOf(pluginSession.isSelector(tc)));
            title.setEditable(true);
            view.setSelectedItem(pluginSession.getColumnView(tc));

//                isTitle.setEnabled(false);
        } else if (tableColumns.length > 1) {
            DBTable table = tableColumns[0].getTable();
            TreeSet<String> groups = new TreeSet<String>();
            ColumnView[] validColumnView = recordEditorPlugin.getValidColumnView(null);
            view.addItem(null);
            for (ColumnView columnView : validColumnView) {
                view.addItem(columnView.getClass().getName());
            }
            Object sel = null;
            for (DBTableColumn tableColumn : table.getColumns()) {
                String g = pluginSession.getColumnGroup(tableColumn);
                if (g != null && !groups.contains(g)) {
                    groups.add(g);
                    if (sel == null) {
                        sel = g;
                    } else if (!sel.equals(g)) {
                        sel = null;
                    }
                }
            }
            for (String s : groups) {
                group.addItem(s);
            }
            group.setSelectedItem(sel);

            sel = null;
            for (DBTableColumn tableColumn : tableColumns) {
                String g = pluginSession.getColumnGroup(tableColumn);
                if (sel == null) {
                    sel = g;
                } else {
                    if (!sel.equals(g)) {
                        sel = null;
                        break;
                    }
                }
            }
            isTitle.setSelectedItem(sel);
            name.setText("*");
            title.setText("");
            title.setEditable(false);
        }
    }

    public void store() {
        if (tableColumns.length == 1) {
            DBTableColumn tc = this.tableColumns[0];
            pluginSession.setColumnTitle(tc,title.getText());
            pluginSession.setSelector(tc,Boolean.parseBoolean((String) isTitle.getSelectedItem()));
            pluginSession.setColumnGroup(tc,(String) group.getSelectedItem());
            String cv = (String) view.getSelectedItem();
            pluginSession.setColumnView(tc, cv);
            if (cv != null) {
                ColumnView newView = ((RecordEditorPluginSession) getPluginSession()).createColumnView(tc);
                if (newView.isConfigurable()) {
                    newView.showConfig();
                }
            }
        } else {
            for (DBTableColumn tci : tableColumns) {
                String value = (String) isTitle.getSelectedItem();
                if (value != null) {
                    pluginSession.setSelector(tci,Boolean.parseBoolean(value));
                }
                value = (String) group.getSelectedItem();
                if (value != null) {
                    pluginSession.setColumnGroup(tci, value);
                }
            }
        }
    }

    public void showConfig() {
        if (tableColumns.length > 0) {
            load();
            int r = JOptionPane.showConfirmDialog(null, this, getPluginSession().getMessageSet().get("DBObjectEditorConfig.Column.Title"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (r == JOptionPane.OK_OPTION) {
                store();
            }
        }
    }
}
