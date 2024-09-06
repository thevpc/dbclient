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
package net.thevpc.dbclient.plugin.system.drivermanager;

import net.thevpc.common.swing.layout.DumbGridBagLayout;
import net.thevpc.dbclient.api.DBCApplication;
import net.thevpc.dbclient.api.configmanager.DBCDriverInfo;
import net.thevpc.common.swing.prs.PRSManager;
import net.thevpc.common.swing.dialog.MessageDialogType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 30 dec. 2006 19:09:06
 */
class DBCDriverPropertiesPanel extends JPanel {

    JTextField id = new JTextField();
    private JTextField className = new JTextField();
    private JTextField defaultLogin = new JTextField();
    private JTextArea defaultDesc = new JTextArea();
    private JTextField defaultURL = new JTextField();
    private JTextField defaultPassword = new JTextField("");
    private JCheckBox driverEnabled = new JCheckBox("Enabled");
    private DBCDriverInfo driver;
    private JPanel propertiesPanel;
    private DriversLibPanel driversLibPanel;
    private DriversLibPanel systemLibPanel;
    private DriversLibPanel pluginsLibPanel;
    private DBCDriverManagerEditorImpl driverManagerEditor;
    private Logger logger;
    
    public DBCDriverPropertiesPanel(DBCDriverManagerEditorImpl _driverManagerEditor) {
        super(
                new DumbGridBagLayout().addLine("[$$==+Main : :]").addLine("[==Ressort][Save][Refresh]").setInsets(".*", new Insets(2, 2, 2, 2)).setInsets("Main", new Insets(0, 0, 0, 0)));
        this.add(new JPanel(), "Ressort");
        driverManagerEditor = _driverManagerEditor;
        final DBCApplication application = driverManagerEditor.getApplication();
        logger=application.getLogger(DBCDriverPropertiesPanel.class.getName());
        id.setEditable(true);
        className.setEditable(true);
        defaultURL.setEditable(true);
        defaultLogin.setEditable(true);
        defaultDesc.setEditable(true);
        defaultPassword.setEditable(true);
        driverEnabled.setEnabled(true);
        JButton saveButton = PRSManager.createButton("Save");
        saveButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    actionSave();
                } catch (Exception e1) {
                    application.getView().getDialogManager().showMessage(DBCDriverPropertiesPanel.this, null, MessageDialogType.ERROR, null, e1);
                }
            }
        });
        JButton reloadButton = PRSManager.createButton("Refresh");
        this.add(reloadButton, "Refresh");
        reloadButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    actionReload();
                } catch (Exception e1) {
                    application.getView().getDialogManager().showMessage(DBCDriverPropertiesPanel.this, null, MessageDialogType.ERROR, null, e1);
                }
            }
        });
        
        this.add(saveButton, "Save");
        
        propertiesPanel = new JPanel(new DumbGridBagLayout(
                "[<idLabel][<id+=][<loaded+=]\n"
                + "[<classNameLabel][<className+=.]\n"
                + "[<defaultURLLabel][<defaultURL+= .]\n"
                + "[<defaultLoginLabel][<defaultLogin+=.]\n"
                + "[<defaultPasswordLabel][<defaultPassword+=.]\n"
                + "[<defaultDescLabel.]\n"
                + "[<defaultDesc+$$=.]\n"
                + "").setInsets(".*", new Insets(2, 4, 2, 4)).setInsets("idLabel", new Insets(8, 4, 2, 4)).setInsets("defaultLoginLabel", new Insets(2, 4, 2, 4)).setInsets("defaultDescLabel", new Insets(2, 4, 2, 4)).setInsets("defaultDesc", new Insets(2, 4, 2, 4)));
        
        propertiesPanel.add("idLabel", PRSManager.createLabel("Name"));
        propertiesPanel.add("classNameLabel", PRSManager.createLabel("DriverPropertiesPanel.DriverClass"));
        propertiesPanel.add("defaultURLLabel", PRSManager.createLabel("DriverPropertiesPanel.URLPattern"));
        propertiesPanel.add("defaultPasswordLabel", PRSManager.createLabel("DriverPropertiesPanel.DefaultPassword"));
        propertiesPanel.add("defaultLoginLabel", PRSManager.createLabel("DriverPropertiesPanel.DefaultLogin"));
        
        
        propertiesPanel.add("id", id);
        propertiesPanel.add("className", className);
        propertiesPanel.add("defaultPassword", defaultPassword);
        propertiesPanel.add("defaultLogin", defaultLogin);
        propertiesPanel.add("defaultURL", defaultURL);
        propertiesPanel.add("loaded", driverEnabled);
        
        propertiesPanel.add("defaultDescLabel", PRSManager.createLabel("Description"));
        propertiesPanel.add("defaultDesc", new JScrollPane(defaultDesc));
        
        PRSManager.addSupport(driverEnabled, "DriverPropertiesPanel.DriverEnabled");
        JTabbedPane jtp = new JTabbedPane();
        PRSManager.addSupport(jtp, "DriverPropertiesPanel.TabbedPane");
        jtp.addTab("Properties", propertiesPanel);
//        jtp.addTab("Description", descPanel);

        
        systemLibPanel = new DriversLibPanel(application, false);
        String ps = System.getProperty("path.separator");
        String[] defaultCP = System.getProperty("java.class.path").split(ps);
        ArrayList<URL> system = new ArrayList<URL>();
        for (String s : defaultCP) {
            File f = new File(s);
            try {
                system.add(f.getCanonicalFile().toURI().toURL());
            } catch (IOException ex) {
                logger.log(Level.SEVERE, "Unable to locate URL " + f, ex);
            }
        }
        systemLibPanel.setLibraries(system.toArray(new URL[system.size()]));
        
        pluginsLibPanel = new DriversLibPanel(application, false);
        java.util.List<URL> libs = application.getDriverManager().getDefaultLibrary().getResources();
        pluginsLibPanel.setLibraries(libs.toArray(new URL[libs.size()]));
        
        driversLibPanel = new DriversLibPanel(application, true);
        
        JPanel librariesPanel = new JPanel(
                new DumbGridBagLayout().addLine("[labelLibrarySelector][-comboLibrarySelector]").addLine("[==$$+card:]").setInsets(".*", new Insets(2, 2, 2, 2)).setInsets("card", new Insets(0, 0, 0, 0)));
        
        JLabel labelLibrarySelector = PRSManager.createLabel("DriverPropertiesPanel.LibraryType");
        final int sysIndex = 0;
        final int pluginIndex = 1;
        final int userIndex = 2;
        final JComboBox comboLibrarySelector = new JComboBox(new Object[]{"System", "Plugins", "User"});
        comboLibrarySelector.setSelectedIndex(userIndex);
        final JPanel cardPanel = new JPanel();
        final CardLayout cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);
        cardPanel.add(pluginsLibPanel, "Plugins");
        cardPanel.add(driversLibPanel, "User");
        cardPanel.add(systemLibPanel, "System");
        
        cardLayout.show(cardPanel, "User");
        librariesPanel.add(cardPanel, "card");
        librariesPanel.add(comboLibrarySelector, "comboLibrarySelector");
        librariesPanel.add(labelLibrarySelector, "labelLibrarySelector");
        
        comboLibrarySelector.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {
                int ii = comboLibrarySelector.getSelectedIndex();
                switch (ii) {
                    case sysIndex: {
                        cardLayout.show(cardPanel, "System");
                        break;
                    }
                    case pluginIndex: {
                        cardLayout.show(cardPanel, "Plugins");
                        break;
                    }
                    case userIndex: {
                        cardLayout.show(cardPanel, "User");
                        break;
                    }
                }
            }
        });
        
        
        jtp.addTab("Libraries", librariesPanel);
//        jtp.addTab("DefaultLibraries", pluginsLibPanel);
//        jtp.addTab("Libraries", driversLibPanel );
//        jtp.addTab("SystemLibraries", systemLibPanel);

        add(jtp, "Main");
        PRSManager.update(this, application.getView());
    }
    
    public void setDriver(DBCDriverInfo driver) {
        this.driver = driver;
//        driverManagerEditor.selectDriver(-1);
        id.setText(driver == null ? "" : ("" + driver.getName()));
        className.setText(driver == null ? "" : driver.getDriverClassName());
        defaultLogin.setText(driver == null ? "" : driver.getDefaultLogin());
        defaultPassword.setText(driver == null ? "" : driver.getDefaultPassword());
        defaultURL.setText(driver == null ? "" : driver.getDefaultURL());
        defaultDesc.setText(driver == null ? "" : driver.getDescription());
        driverEnabled.setSelected(driver == null || driver.isEnabled());
        driversLibPanel.setLibraries(driver == null ? new URL[0] : driver.getLibraries());
    }
    
    public DBCDriverInfo getDriver() {
        DBCDriverInfo driver = new DBCDriverInfo();
//        driver.setApplication(driverManagerEditor.getApplication());
        driver.setId(this.driver == null ? -1 : this.driver.getId());
        driver.setName(id.getText());
        driver.setDriverClassName(className.getText());
        driver.setDefaultLogin(defaultLogin.getText());
        driver.setDefaultPassword(defaultPassword.getText());
        driver.setDefaultURL(defaultURL.getText());
        driver.setDescription(defaultDesc.getText());
        driver.setEnabled(driverEnabled.isSelected());
        driver.setLibraries(driversLibPanel.getLibraries());
        return driver;
    }
    
    private class DriversLibPanel extends JPanel {

        private JList mappingTable;
        public boolean editable = true;
        private JToolBar jToolBar;
        private JCheckBox showFullPath;
        
        public void setLibraries(URL[] files) {
//            DefaultTableModel dtm = (DefaultTableModel) mappingTable.getModel();
//            while (dtm.getRowCount() > 0) {
//                dtm.removeRow(0);
//            }
//            for (URL file : files) {
//                dtm.addRow(new Object[]{file});
//            }
            DefaultListModel dtm = (DefaultListModel) mappingTable.getModel();
            while (dtm.getSize() > 0) {
                dtm.remove(0);
            }
            for (URL file : files) {
                dtm.addElement(file);
            }
        }
        
        public boolean isEditable() {
            return editable;
        }
        
        public void setEditable(boolean editable) {
            this.editable = editable;
            for (int i = 0; i < jToolBar.getComponentCount(); i++) {
                jToolBar.getComponentAtIndex(i).setEnabled(editable);
            }
            //jToolBar.setEnabled(editable);
        }
        
        public URL[] getLibraries() {
//            DefaultTableModel dtm = (DefaultTableModel) mappingTable.getModel();
//            URL[] all=new URL[dtm.getRowCount()];
//            for (int i = 0; i < all.length; i++) {
//                all[i]=(URL) dtm.getValueAt(i,0);
//            }
//            return all;
            DefaultListModel dtm = (DefaultListModel) mappingTable.getModel();
            URL[] all = new URL[dtm.getSize()];
            for (int i = 0; i < all.length; i++) {
                all[i] = (URL) dtm.getElementAt(i);
            }
            return all;
        }
        
        public DriversLibPanel(DBCApplication dbclient, boolean editable) {
            super(new BorderLayout());
            jToolBar = new JToolBar(JToolBar.VERTICAL);
            jToolBar.setFloatable(false);
            JButton addButton = PRSManager.createButton("Add");
            addButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    addLibrary();
                }
            });
            jToolBar.add(addButton);
            
            JButton removeButton = PRSManager.createButton("Remove");
            jToolBar.add(removeButton);
            removeButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    removeLibrary();
                }
            });
            
            JButton upButton = PRSManager.createButton("Up");
            jToolBar.add(upButton);
            upButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    moveUpLibrary();
                }
            });
            
            JButton downButton = PRSManager.createButton("Down");
            jToolBar.add(downButton);
            downButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    moveDownLibrary();
                }
            });

            //mappingTable = DBCUtils.newJTable(dbclient.getFactory());
//            mappingTable.setDefaultRenderer(Object.class,new DefaultTableCellRenderer(){
//                // implements javax.swing.table.TableCellRenderer
//                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//                    URL u=(URL)value;
//                    if(value==null){
//                        value="";
//                    }else  if(showFullPath.isSelected()){
//                        if("file".equals(u.getProtocol())){
//                            value=u.getFile();
//                        }
//                    }else{
//                        value=new File(u.getFile()).getName();
//                    }
//                    return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//                }
//            });
//            DefaultTableModel dtm = new DefaultTableModel();
//            dtm.setColumnIdentifiers(new Object[]{driverManagerEditor.getApplication().getView().getMessageSet().get("Libraries")});
//            mappingTable.setModel(dtm);
            mappingTable = new JList(); //DBCUtils.newJTable(dbclient.getFactory());
            mappingTable.setCellRenderer(new DefaultListCellRenderer() {

                public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    URL u = (URL) value;
                    if (value == null) {
                        value = "";
                    } else if (showFullPath.isSelected()) {
                        if ("file".equals(u.getProtocol())) {
                            value = u.getFile();
                        }
                    } else {
                        value = new File(u.getFile()).getName();
                    }
                    return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                }
            });
            DefaultListModel dtm = new DefaultListModel();
            mappingTable.setModel(dtm);
//        mappingTable.setModel(new ClassMappingModel(new Hashtable<Class, Class>()));
            JScrollPane mappingJsp = new JScrollPane(mappingTable);
//            mappingTable.setDefaultRenderer(Class.class, new DefaultTableCellRenderer() {
//                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
////                    Class c1 = (Class) table.getModel().getValueAt(row, 0);
////                    Class c2 = (Class) table.getModel().getValueAt(row, 1);
//                    super.getTableCellRendererComponent(table, ((Class) value).getSimpleName(), isSelected, hasFocus, row, column);
//                    return this;
//                }
//            });

            mappingJsp.setPreferredSize(new Dimension(600, 100));
            add(mappingJsp, BorderLayout.CENTER);
            add(jToolBar, BorderLayout.LINE_END);
            JToolBar down = new JToolBar();
            down.setFloatable(false);
            down.add(Box.createHorizontalGlue());
            showFullPath = PRSManager.createCheck("DriverPropertiesPanel.ShowFullPath", false);
            down.add(showFullPath);
            add(down, BorderLayout.PAGE_END);
            showFullPath.addItemListener(new ItemListener() {

                public void itemStateChanged(ItemEvent e) {
                    mappingTable.repaint();
                }
            });
            setEditable(editable);
        }
        private JFileChooser jfc;
        
        public JFileChooser getJFileChooser() {
            if (jfc == null) {
                jfc = new JFileChooser();
                jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                jfc.setMultiSelectionEnabled(true);
                jfc.setCurrentDirectory(driverManagerEditor.getApplication().getWorkingDir());
            }
            return jfc;
        }
        
        public void moveUpLibrary() {
//            int row = DBCUtils.tableGetSelectedRowModelIndex(driverManagerEditor.getApplication().getFactory() , mappingTable);
//            DefaultTableModel dtm = (DefaultTableModel) mappingTable.getModel();
//            if (row > 0) {
//                dtm.moveRow(row, row, row - 1);
//            }
            int row = mappingTable.getSelectedIndex();
            DefaultListModel dtm = (DefaultListModel) mappingTable.getModel();
            if (row > 0) {
                Object o = dtm.remove(row);
                dtm.add(row - 1, o);
            }
        }
        
        public void moveDownLibrary() {
//            int row = DBCUtils.tableGetSelectedRowModelIndex(driverManagerEditor.getApplication().getFactory(), mappingTable);
//            DefaultTableModel dtm = (DefaultTableModel) mappingTable.getModel();
//            if (row + 1 < dtm.getRowCount()) {
//                dtm.moveRow(row, row, row + 1);
//            }
            int row = mappingTable.getSelectedIndex();
            DefaultListModel dtm = (DefaultListModel) mappingTable.getModel();
            if (row + 1 < dtm.getSize()) {
                Object o = dtm.remove(row);
                dtm.add(row + 1, o);
            }
        }
        
        public void addLibrary() {
//            if (JFileChooser.APPROVE_OPTION == getJFileChooser().showOpenDialog(this)) {
//                File[] files = getJFileChooser().getSelectedFiles();
//                DefaultTableModel dtm = (DefaultTableModel) mappingTable.getModel();
//                for (File file : files) {
//                    try {
//                        dtm.addRow(new Object[]{file.toURI().toURL()});
//                    } catch (MalformedURLException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
            if (JFileChooser.APPROVE_OPTION == getJFileChooser().showOpenDialog(this)) {
                File[] files = getJFileChooser().getSelectedFiles();
                DefaultListModel dtm = (DefaultListModel) mappingTable.getModel();
                for (File file : files) {
                    try {
                        dtm.addElement(file.toURI().toURL());
                    } catch (MalformedURLException e) {
                         logger.log(Level.SEVERE,"Unable to Load Catalog Content", e);
                    }
                }
            }
        }
        
        public void removeLibrary() {
//            int row = DBCUtils.tableGetSelectedRowModelIndex(driverManagerEditor.getApplication().getFactory(), mappingTable);
//            DefaultTableModel dtm = (DefaultTableModel) mappingTable.getModel();
//            if (row >= 0) {
//                dtm.removeRow(row);
//            }
            int row = mappingTable.getSelectedIndex();
            DefaultListModel dtm = (DefaultListModel) mappingTable.getModel();
            if (row >= 0) {
                dtm.remove(row);
            }
        }
    }
    
    public void actionReload() {
        DBCDriverInfo dbcDriver = getDriver();
        if (dbcDriver.getId() >= 0) {
//            driverManagerEditor.selectDriver(-1);
            driverManagerEditor.selectDriver(dbcDriver.getId());
        }
        driverManagerEditor.modelChanged();
        driverManagerEditor.selectDriver(dbcDriver.getId());
    }
    
    public void actionSave() {
        DBCDriverInfo dbcDriver = getDriver();
        if (dbcDriver.getId() >= 0) {
            driverManagerEditor.getApplication().getDriverManager().updateDriver(dbcDriver);
        } else {
            driverManagerEditor.getApplication().getDriverManager().addDriver(dbcDriver);
        }
        driverManagerEditor.modelChanged();
        driverManagerEditor.selectDriver(dbcDriver.getId());
    }
}
