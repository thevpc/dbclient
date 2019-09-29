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
package net.vpc.dbclient.plugin.system.sessionmanager.chooser;

import net.vpc.dbclient.api.DBCApplication;
import net.vpc.dbclient.api.DBCApplicationView;
import net.vpc.dbclient.api.configmanager.DBCDriverInfo;
import net.vpc.dbclient.api.configmanager.DBCSessionInfo;
import net.vpc.dbclient.api.configmanager.DBCSessionProperties;
import net.vpc.dbclient.api.drivermanager.DBCDriverManager;
import net.vpc.prs.plugin.Inject;
import net.vpc.dbclient.api.sessionmanager.DBCSessionListEditor;
import net.vpc.dbclient.api.sessionmanager.DBCSessionListItemEditor;
import net.vpc.dbclient.api.sessionmanager.DBCURLBuilder;
import net.vpc.dbclient.api.viewmanager.DBCPluggablePanel;
import net.vpc.dbclient.api.viewmanager.DBCTable;
import net.vpc.swingext.PRSManager;
import net.vpc.swingext.DumbGridBagLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.Map.Entry;
import java.util.TreeSet;
import java.util.Vector;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 28 nov. 2006 22:59:15
 */
public final class DBCSessionListItemEditorImpl extends DBCPluggablePanel implements DBCSessionListItemEditor {

    private int currentSessionId;
    private JCheckBox hideInvalidDrivers;
    private JCheckBox doNotStorePwd;
    private JCheckBox autoConnect;
    private JComboBox groupsComboBox;
    private JComboBox driversComboBox;
    private DBCTable propertiesTable;
    private JTextArea urlTextField;
    private JTextField connectionNameTextField;
    private JTextField loginTextField;
    private JPasswordField passwordTextField;
    private JTabbedPane tabbedPane;
    @Inject
    private DBCApplication application;
    private DBCSessionListEditor chooser;
    public ActionType actionType;
    private JButton builder;

    private static enum SearchDriverType {

        CLASS, NAME, NAME_IGNORE_CASE
    }

    private DBCDriverInfo getDriverDesc(String name, SearchDriverType searchDriverType) {
        DBCDriverInfo[] drivers = application.getDriverManager().getDrivers();
        switch (searchDriverType) {
            case CLASS: {
                for (int i = drivers.length - 1; i >= 0; i--) {
                    DBCDriverInfo d = drivers[i];
                    String v = d.getDriverClassName();
                    if (v != null && v.equals(name)) {
                        return d;
                    }
                }
                break;
            }
            case NAME: {
                for (int i = drivers.length - 1; i >= 0; i--) {
                    DBCDriverInfo d = drivers[i];
                    String v = d.getName();
                    if (v != null && v.equals(name)) {
                        return d;
                    }
                }
                break;
            }
            case NAME_IGNORE_CASE: {
                for (int i = drivers.length - 1; i >= 0; i--) {
                    DBCDriverInfo d = drivers[i];
                    String v = d.getName();
                    if (v != null && v.equalsIgnoreCase(name)) {
                        return d;
                    }
                }
                break;
            }
        }
        return null;
    }

    private void revalidateBuilder() {
        if (driversComboBox.isEnabled()) {
            String cls = getDriverClassName();
            builder.setEnabled(cls != null && chooser.getURLBuilder(cls) != null);
            return;
        }
        builder.setEnabled(false);
    }

    private void revalidateCombo() {
        DBCDriverManager driverManager = application.getDriverManager();
        DBCDriverInfo[] drivers = driverManager.getDrivers();
        Vector<DBCDriverInfo> driverClassNames = new Vector<DBCDriverInfo>();
        if (hideInvalidDrivers.isSelected()) {
            for (DBCDriverInfo driver : drivers) {
                if (driverManager.isLoadableDriver(driver.getDriverClassName())) {
                    driverClassNames.add(driver);
                }
            }

        } else {
            driverClassNames.addAll(Arrays.asList(drivers));
        }
        driversComboBox.setModel(new DefaultComboBoxModel(driverClassNames));
        revalidateBuilder();
    }

    public DBCSessionListItemEditorImpl() {
        super(new BorderLayout());
    }

    public void init(DBCSessionListEditor chooser) {
        this.chooser = chooser;
        connectionNameTextField = new JTextField();
        builder = new JButton("...");

        urlTextField = new JTextArea();
        JPopupMenu jpop = new JPopupMenu();
        ActionListener variableValueListener = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JMenuItem mm = (JMenuItem) e.getSource();
                Object o = mm.getClientProperty("VariableValue");
                urlTextField.replaceSelection("${" + o + "}");
            }
        };
        for (Entry<String, Object> entry : application.getVariables().entrySet()) {
            JMenuItem m = new JMenuItem(application.getView().getMessageSet().get("Variable." + entry.getKey()));
            m.putClientProperty("VariableValue", entry.getKey());
            m.addActionListener(variableValueListener);
            jpop.add(m);
        }

        urlTextField.setComponentPopupMenu(jpop);
        loginTextField = new JTextField();
        passwordTextField = new JPasswordField();
        hideInvalidDrivers = PRSManager.createCheck("DBCSessionListItemEditor.hideInvalidDrivers", true);
        hideInvalidDrivers.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {
                revalidateCombo();
            }
        });

        doNotStorePwd = PRSManager.createCheck("DBCSessionListItemEditor.doNotStorePwd", false);
        doNotStorePwd.setSelected(false);
        doNotStorePwd.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {
                passwordTextField.setEnabled(!doNotStorePwd.isSelected());
            }
        });

        groupsComboBox = new JComboBox();
        groupsComboBox.setEditable(true);

        driversComboBox = new JComboBox();
        autoConnect = PRSManager.createCheck("DBCSessionListItemEditor.autoConnect", false);
        revalidateCombo();
        revalidateGroupsComboBox();
        driversComboBox.setRenderer(new DefaultListCellRenderer() {

            Color fore = getForeground();

            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (value instanceof DBCDriverInfo) {
                    DBCDriverInfo driver = ((DBCDriverInfo) value);
                    super.getListCellRendererComponent(list, driver.getBestName(), index, isSelected, cellHasFocus);
                    DBCDriverManager driverManager = application.getDriverManager();
                    if (driverManager.isLoadableDriver(driver.getDriverClassName())) {
                        super.setForeground(fore);
                    } else {
                        super.setForeground(Color.RED);
                    }
                } else {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                }
                return this;
            }
        });
        driversComboBox.setEditable(true);

        propertiesTable = application.getFactory().newInstance(DBCTable.class);
        propertiesTable.setModel(new DefaultTableModel() {

            String[][] data = new String[20][2];

            public int getRowCount() {
                return data == null ? 0 : data.length;
            }

            public int getColumnCount() {
                return data == null ? 0 : 2;
            }

            public String getColumnName(int columnIndex) {
                return columnIndex == 0 ? "Key" : "value";
            }

            public Class getColumnClass(int columnIndex) {
                return String.class;
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return true;
            }

            public Object getValueAt(int rowIndex, int columnIndex) {
                return data[rowIndex][columnIndex];
            }

            public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
                data[rowIndex][columnIndex] = (String) aValue;
            }
        });

        driversComboBox.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {
                DBCDriverInfo d = getDriverInfo();
                if (d == null) {
                    urlTextField.setText("");
                    loginTextField.setText("");
                    passwordTextField.setText("");
                    urlTextField.setText("");
                    urlTextField.setToolTipText("No help is provided");
                } else {
                    urlTextField.setText(d.getDefaultURL());
                    loginTextField.setText(d.getDefaultLogin());
                    passwordTextField.setText(d.getDefaultPassword());
                    urlTextField.setToolTipText("<HTML>" + d.getDescription() + "</html>");
                }
                revalidateBuilder();

            }
        });

        ImageIcon image = application.getView().getArtSet().getArtImage(DBCApplicationView.ARTSET_VERTICAL_BANNER_THIN);
        if (image != null) {
            add(new JLabel(image), BorderLayout.LINE_START);
        }
        application.getView().addPropertyChangeListener(DBCApplicationView.PROPERTY_ARTSET, new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                ImageIcon image = application.getView().getArtSet().getArtImage(DBCApplicationView.ARTSET_VERTICAL_BANNER_THIN);
                if (image != null) {
                    add(new JLabel(image), BorderLayout.LINE_START);
                } else {
                    add(new JLabel(""), BorderLayout.LINE_START);
                }
            }
        });
        add(tabbedPane = new JTabbedPane(), BorderLayout.CENTER);
        tabbedPane.addTab("1", createMainPanel());
        tabbedPane.addTab("2", createPropsPanel());
        PRSManager.addSupport(tabbedPane, "DBCSessionListItemEditor.TabbedPane");
    }

    private void revalidateGroupsComboBox() {
        DBCSessionInfo[] dbcSessionInfos = application.getConfig().getSessions();
        TreeSet ts = new TreeSet();
        for (DBCSessionInfo dbcSessionInfo : dbcSessionInfos) {
            String p = dbcSessionInfo.getPath();
            if (p != null && p.length() > 0) {
                ts.add(p);
            }
        }
        groupsComboBox.setModel(new DefaultComboBoxModel(ts.toArray(new Object[ts.size()])));
    }

    public JPanel createPropsPanel() {
        JLabel propsLabel = new JLabel("Properties");

        JPanel mainPanel = new JPanel(new DumbGridBagLayout(
                "[A1<+=]\n"
                + "[B1<+=$$$]\n").setInsets(".*", new Insets(2, 4, 2, 2)).setInsets("A1", new Insets(10, 4, 2, 2)));

        mainPanel.add(propsLabel, "A1");
        JScrollPane propertiesTableJsp = new JScrollPane(propertiesTable.getComponent());
        propertiesTableJsp.setBorder(null);
        propertiesTableJsp.setPreferredSize(new Dimension(200, 200));
        mainPanel.add(propertiesTableJsp, "B1");

        return mainPanel;
    }

    public void showBuilder() {
        DBCURLBuilder builder = chooser.getURLBuilder(getDriverClassName());
        if (builder != null) {
            String s = builder.buildURL(chooser, getDriverClassName(), getUrl());
            if (s != null) {
                urlTextField.setText(s);
            }
        }
    }

    public JPanel createMainPanel() {
        JLabel driverLabel = PRSManager.createLabel("DBCSessionListItemEditor.Driver");
        JLabel loginLabel = PRSManager.createLabel("DBCSessionListItemEditor.UserLogin");
        JLabel pwdLabel = PRSManager.createLabel("DBCSessionListItemEditor.UserPassword");
        JLabel connectionNameLabel = PRSManager.createLabel("DBCSessionListItemEditor.Name");
        JLabel connectionGroupLabel = PRSManager.createLabel("DBCSessionListItemEditor.Group");
        JLabel urlLabel = PRSManager.createLabel("DBCSessionListItemEditor.URL");
        builder.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                showBuilder();
            }
        });
        builder.setMargin(new Insets(1, 1, 1, 1));


        JPanel mainPanel = new JPanel(new DumbGridBagLayout(
                "[A1<+    ] [<A2+=  :         ]\n"
                + "[G1<+    ] [<G2+=  :         ]\n"
                + "[B1<+    ] [<B2+= ] [B3      ]\n"
                + "[C1<+=    :       ] [Builder>]\n"
                + "[D1<+=    :        :         ]\n"
                + "[E1<+    ] [<E2+=  :         ]\n"
                + "[F1<+    ] [<F2+= ] [<F3     ]\n"
                + "[H1<+    ]\n").setInsets(".*", new Insets(2, 4, 2, 2)).setInsets("A1", new Insets(10, 4, 2, 2)));
        mainPanel.add(connectionNameLabel, "A1");
        mainPanel.add(connectionNameTextField, "A2");
        mainPanel.add(driverLabel, "B1");
        mainPanel.add(driversComboBox, "B2");
        mainPanel.add(hideInvalidDrivers, "B3");
        mainPanel.add(connectionGroupLabel, "G1");
        mainPanel.add(groupsComboBox, "G2");
        mainPanel.add(autoConnect, "H1");
        mainPanel.add(builder, "Builder");

        mainPanel.add(urlLabel, "C1");

        JScrollPane urlJsp = new JScrollPane(urlTextField);
        urlJsp.setPreferredSize(new Dimension(400, 50));
        mainPanel.add(urlJsp, "D1");

        mainPanel.add(loginLabel, "E1");
        mainPanel.add(loginTextField, "E2");

        mainPanel.add(pwdLabel, "F1");
        mainPanel.add(passwordTextField, "F2");
        mainPanel.add(doNotStorePwd, "F3");

        return mainPanel;
    }

    public DBCDriverInfo getDriverInfo() {
        Object o = driversComboBox.getSelectedItem();
        if (o == null) {
            return null;
        }
        if (o instanceof DBCDriverInfo) {
            return ((DBCDriverInfo) o);
        }
        if (o instanceof String) {
            String s = (String) o;
            s = s.trim();
            if (s.length() == 0) {
                return null;
            }
            for (SearchDriverType searchDriverType : new SearchDriverType[]{SearchDriverType.CLASS, SearchDriverType.NAME, SearchDriverType.NAME_IGNORE_CASE}) {
                DBCDriverInfo d = getDriverDesc(s, searchDriverType);
                if (d != null) {
                    return d;
                }
            }
        }
        return null;
    }

    public String getDriverClassName() {
        Object o = driversComboBox.getSelectedItem();
        if (o == null) {
            return null;
        }
        if (o instanceof DBCDriverInfo) {
            return ((DBCDriverInfo) o).getDriverClassName();
        }
        if (o instanceof String) {
            String s = (String) o;
            s = s.trim();
            if (s.length() == 0) {
                return null;
            }
            for (SearchDriverType searchDriverType : new SearchDriverType[]{SearchDriverType.CLASS, SearchDriverType.NAME, SearchDriverType.NAME_IGNORE_CASE}) {
                DBCDriverInfo d = getDriverDesc(s, searchDriverType);
                if (d != null) {
                    return d.getDriverClassName();
                }
            }
        }
        return o.toString();
    }

    public String getUser() {
        return loginTextField.getText();
    }

    public String getUrl() {
        return urlTextField.getText();
    }

    public String getConnectionName() {
        return connectionNameTextField.getText();
    }

    public String getPassword() {
        return new String(passwordTextField.getPassword());
    }

    public void reset() {
        this.tabbedPane.setSelectedIndex(0);
    }

    public String getConfigName() {
        return connectionNameTextField.getText();
    }

    public void setActionType(ActionType type) {
        this.actionType = type;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public DBCSessionInfo showDialog(Component parent) {
        if (JOptionPane.showOptionDialog(parent, this,
                currentSessionId == -1 ? "new (*)" : "[" + connectionNameTextField.getText() + "]", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null) == JOptionPane.OK_OPTION) {
            return getConnectionInfo();
        } else {
            return null;
        }

    }

    public DBCSessionInfo getConnectionInfo() {
        if (connectionNameTextField.getText().length() == 0) {
            connectionNameTextField.setText("NO_NAME");
        }
        DBCSessionInfo s = new DBCSessionInfo();
        s.setId(currentSessionId);
        s.setName(connectionNameTextField.getText());
        s.setCnxUrl(urlTextField.getText());
        s.setCnxDriver(getDriverClassName());
        s.setCnxLogin(loginTextField.getText());
        s.setCnxPassword(new String(passwordTextField.getPassword()));
        s.setAutoConnect(autoConnect.isSelected());
        s.setAskForPassword(doNotStorePwd.isSelected());
        s.setPath((String) groupsComboBox.getSelectedItem());
        DBCSessionProperties p = new DBCSessionProperties();
        TableModel mod = propertiesTable.getModel();
        for (int i = mod.getRowCount() - 1; i >= 0; i--) {
            String key = (String) mod.getValueAt(i, 0);
            String value = (String) mod.getValueAt(i, 1);
            if (key != null && key.length() > 0) {
                if (value != null) {
                    p.setProperty(key, value);
                } else {
                    p.setProperty(key, "");
                }
            }
        }
        s.setProperties(p);
        return s;
    }

    public void setConnectionInfo(DBCSessionInfo info) {
        revalidateCombo();
        revalidateGroupsComboBox();
        if (info == null) {
            currentSessionId = -1;
            driversComboBox.setSelectedItem("");
            connectionNameTextField.setText("");
            urlTextField.setText("");
            loginTextField.setText("");
            passwordTextField.setText("");
            autoConnect.setSelected(false);
            groupsComboBox.setSelectedItem(null);
        } else {
            currentSessionId = info.getId();
            driversComboBox.setSelectedItem(info.getCnxDriver());
            connectionNameTextField.setText(info.getName());
            urlTextField.setText(info.getCnxUrl());
            loginTextField.setText(info.getCnxLogin());
            passwordTextField.setText(info.getCnxPassword());
            autoConnect.setSelected(info.isAutoConnect());
            groupsComboBox.setSelectedItem(info.getPath());
            Boolean aBoolean = info.isAskForPassword();
            doNotStorePwd.setSelected(aBoolean != null && aBoolean);
        }
    }

    public void setReadOnly(boolean readOnly) {
        //autoConnect.setEnabled(!readOnly);
        driversComboBox.setEnabled(!readOnly);
        connectionNameTextField.setEnabled(!readOnly);
        urlTextField.setEnabled(!readOnly);
        loginTextField.setEnabled(!readOnly);
        passwordTextField.setEnabled(!readOnly);
        groupsComboBox.setEnabled(!readOnly);
        hideInvalidDrivers.setEnabled(!readOnly);
        doNotStorePwd.setEnabled(!readOnly);
        revalidateBuilder();
    }

    public JComponent getComponent() {
        return this;
    }
}
