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

import net.thevpc.common.io.FileUtils;
import net.thevpc.common.io.URLUtils;
import net.thevpc.common.prs.xml.XmlUtils;
import net.thevpc.common.swing.layout.DumbGridBagLayout;
import net.thevpc.dbclient.api.DBCApplication;
import net.thevpc.dbclient.api.configmanager.DBCApplicationConfig;
import net.thevpc.dbclient.api.configmanager.DBCDriverInfo;
import net.thevpc.dbclient.api.configmanager.DBCIfFoundAction;
import net.thevpc.dbclient.api.configmanager.DBCIfNotFoundAction;
import net.thevpc.dbclient.api.drivermanager.DBCDriverManager;
import net.thevpc.dbclient.api.drivermanager.DBCDriverManagerEditor;
import net.thevpc.dbclient.api.viewmanager.DBCApplicationMessageDialogManager;
import net.thevpc.common.prs.plugin.Initializer;
import net.thevpc.common.prs.plugin.Inject;
import net.thevpc.dbclient.api.viewmanager.DBCPluggablePanel;
import net.thevpc.dbclient.api.viewmanager.DBCTable;
import net.thevpc.common.swing.prs.PRSManager;
import net.thevpc.common.swing.dialog.MessageDialogType;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 15 dec. 2006 01:41:47
 */
public class DBCDriverManagerEditorImpl extends DBCPluggablePanel implements DBCDriverManagerEditor {

    private DBCTable table;
    @Inject
    private DBCApplication application;
    private JScrollPane jsp;
    private JTextField location;
    //private JCheckBox relative;
    private DBCDriverInfo newDriverInfo;
    private DBCDriverPropertiesPanel driverPropertiesPanelpluginPropertiesPanel;
    private Logger logger;

    public DBCDriverManagerEditorImpl() {
        super(new BorderLayout());
    }

    public Component getComponent() {
        return this;
    }

    @Initializer
    private void init() {
        logger=application.getLogger(DBCDriverManagerEditorImpl.class.getName());
        table = application.getFactory().newInstance(DBCTable.class);
        DBCDriverInfo[] drivers = application.getDriverManager().getDrivers();
        table.setModel(new DBCDriverManagerEditorImpl.DriversModel(drivers, newDriverInfo));
        jsp = new JScrollPane(table.getComponent());
        jsp.setPreferredSize(new Dimension(600, 300));
        JPanel leftExplorer = new JPanel(new DumbGridBagLayout().addLine("[$$==+Table.]").addLine("[==Nothing][reloadAllButton][newButton][removeButton]").setInsets(".*", new Insets(2, 2, 2, 2)).setInsets("Table", new Insets(0, 0, 0, 0)));
        leftExplorer.add(new JPanel(), "Nothing");

        leftExplorer.add(jsp, "Table");

        JButton reloadAllButton = PRSManager.createButton("Reset");
        reloadAllButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    actionReloadAll();
                } catch (Exception e1) {
                    getApplication().getView().getDialogManager().showMessage(DBCDriverManagerEditorImpl.this, null, MessageDialogType.ERROR, null, e1);
                }
            }
        });

        JButton newButton = PRSManager.createButton("New");
        newButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    actionNew();
                } catch (Exception e1) {
                    getApplication().getView().getDialogManager().showMessage(DBCDriverManagerEditorImpl.this, null, MessageDialogType.ERROR, null, e1);
                }
            }
        });

        JButton removeButton = PRSManager.createButton("Delete");

        leftExplorer.add(reloadAllButton, "reloadAllButton");
        leftExplorer.add(newButton, "newButton");
        leftExplorer.add(removeButton, "removeButton");

        removeButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    actionDelete();
                } catch (Exception e1) {
                    getApplication().getView().getDialogManager().showMessage(DBCDriverManagerEditorImpl.this, null, MessageDialogType.ERROR, null, e1);
                }
            }
        });


        driverPropertiesPanelpluginPropertiesPanel = new DBCDriverPropertiesPanel(this);
        final JSplitPane jsplit = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                leftExplorer,
                driverPropertiesPanelpluginPropertiesPanel);
        jsplit.setDividerSize(2);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowSelectionAllowed(true);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                int index = table.getSelectedRow();
                if (index >= 0) {
                    index = table.convertRowIndexToModel(index);
                    DBCDriverManagerEditorImpl.DriversModel pm = (DBCDriverManagerEditorImpl.DriversModel) table.getModel();
                    selectDriver(pm.drivers[index].getId());
                    //driverPropertiesPanelpluginPropertiesPanel.setDriver(pm.drivers[index]);
                }
            }
        });
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {

            Color initialColor = getForeground();
            Color unloadedColor = Color.RED.darker();

            //            Color initialBg=getBackground();
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (initialColor == null) {
                    initialColor = getForeground();
                }
                super.getTableCellRendererComponent(jtable, value, isSelected, hasFocus, row, column);
                DBCDriverManagerEditorImpl.DriversModel pm = (DBCDriverManagerEditorImpl.DriversModel) jtable.getModel();
                DBCDriverManager driverManager = application.getDriverManager();
                row = table.convertRowIndexToModel(row);
                if (row >= 0) {
                    DBCDriverInfo driver = pm.getDriverByIndex(row);
                    setForeground(driverManager.isLoadableDriver(driver.getDriverClassName()) ? initialColor : unloadedColor);
                }
                return this;
            }
        });

        String repositoryURL = application.getDriverManager().getDefaultLibraryRepository();
//        relative = new JCheckBox("");
//        relative.setText("");
//        relative.addItemListener(new ItemListener() {
//
//            public void itemStateChanged(ItemEvent e) {
//                throw new UnsupportedOperationException("Not supported yet.");
//            }
//        });
        location = new JTextField("");
        location.setEditable(false);
        location.setText(repositoryURL);
        JButton selectRepository = new JButton("...");
        selectRepository.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                actionChangeRepository();
            }
        });

        this.setLayout(new DumbGridBagLayout().addLine("[Label][=-Location][SelectLocation]").addLine("[+$$==Center    .]").setInsets("Label|Location|SelectLocation", new Insets(2, 2, 2, 2)));
        this.add(PRSManager.createLabel("DBCDriverManagerEditor.DriversLocation"), "Label");
        this.add(jsplit, "Center");
        this.add(location, "Location");
        this.add(selectRepository, "SelectLocation");

        jsplit.setResizeWeight(0.35);
        jsplit.setDividerLocation(0.35);
        if (table.getModel().getRowCount() > 0) {
            table.setRowSelectionInterval(0, 0);
        }
        PRSManager.updateOnFirstComponentShown(this, application.getView());
        PRSManager.update(this, application.getView());
        this.addComponentListener(new ComponentListener() {

            public void componentResized(ComponentEvent e) {
                jsplit.setDividerLocation(0.4);
//                System.out.println("componentResized");
            }

            public void componentMoved(ComponentEvent e) {
//                System.out.println("componentMoved");
            }

            public void componentShown(ComponentEvent e) {
//                System.out.println("componentShown");
                jsplit.setDividerLocation(0.35);
            }

            public void componentHidden(ComponentEvent e) {
//                System.out.println("componentHidden");
            }
        });
    }

    public String getURLPath(URL repositoryURL) {
        if ("file".equalsIgnoreCase(repositoryURL.getProtocol())) {
            String s = repositoryURL.getFile();
            s = application.rewriteString(s);
            File folder = new File(s);
            String ff = FileUtils.getRelativePath(application.getWorkingDir(), folder);
            if (ff == null) {
                try {
                    return (folder.getCanonicalPath());
                } catch (IOException e) {
                    return (folder.getAbsolutePath());
                }
            } else {
                return "${app.dir}/" + ff;
            }
        } else {
            return (repositoryURL.toString());
        }
    }

    private void actionChangeRepository() {
        //String repository = dbClient.getDriverManager().getDefaultLibrariesRepository();
        String str = application.getDriverManager().getDefaultLibraryRepository();
        str = application.rewriteString(str);
        URL repositoryURL = null;
        final DBCApplicationMessageDialogManager dialogManager = getApplication().getView().getDialogManager();
        try {
            repositoryURL = new File(str).toURI().toURL();
        } catch (MalformedURLException e) {
            dialogManager.showMessage(null, null, MessageDialogType.ERROR, null, e);
            try {
                repositoryURL = new File(application.getWorkingDir(), "jdbcdrivers").toURI().toURL();
            } catch (MalformedURLException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
        JFileChooser jfc = new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jfc.setMultiSelectionEnabled(false);
        if (URLUtils.isFileURL(repositoryURL)) {
            jfc.setSelectedFile(new File(repositoryURL.getFile()));
        }

        if (JFileChooser.APPROVE_OPTION == jfc.showOpenDialog(this)) {
            try {
                URL repository = jfc.getSelectedFile().toURI().toURL();
                String s = getURLPath(repository);
                application.getDriverManager().setDefaultLibraryRepository(s);
                location.setText(s);
                modelChanged();
            } catch (MalformedURLException e) {
                dialogManager.showMessage(null, null, MessageDialogType.ERROR, null, e);
            }
        }
    }

    public class DriversModel extends AbstractTableModel {

        DBCDriverInfo[] drivers;
        private DBCDriverInfo newDriverInfo;

        public DriversModel(DBCDriverInfo[] drivers, DBCDriverInfo newDriverInfo) {
            this.newDriverInfo = newDriverInfo;
            if (newDriverInfo != null) {
                ArrayList<DBCDriverInfo> list = new ArrayList<DBCDriverInfo>();
                list.add(newDriverInfo);
                list.addAll(Arrays.asList(drivers));
                drivers = list.toArray(new DBCDriverInfo[list.size()]);
            }
            this.drivers = drivers;
        }

        public int getRowCount() {
            return drivers == null ? 0 : drivers.length;
        }

        public int getColumnCount() {
            return 2;
        }

        public String getColumnName(int columnIndex) {
            switch (columnIndex) {
                case 0: {
                    return getApplication().getView().getMessageSet().get("Name");
                }
                case 1: {
                    return getApplication().getView().getMessageSet().get("Loaded");
                    //return dbClient.getMessageSet().get("DriverPropertiesPanel.DriverClass");
                }
            }
            return super.getColumnName(columnIndex);
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            switch (columnIndex) {
                case 0: {
                    return drivers[rowIndex].getName();
                }
                case 1: {
                    DBCDriverManager driverManager = application.getDriverManager();
                    return driverManager.isLoadableDriver(drivers[rowIndex].getDriverClassName());
                    //return drivers[rowIndex].getDriverClassName();
                }
            }
            return null;
        }

        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 0: {
                    return String.class;
                }
                case 1: {
                    return Boolean.class;
                    //return drivers[rowIndex].getDriverClassName();
                }
            }
            return super.getColumnClass(columnIndex);
        }

        public DBCDriverInfo getDriverByIndex(int index) {
            return drivers[index];
        }

        public int getDriverIndexById(int id) {
            for (int i = 0; i < drivers.length; i++) {
                if (drivers[i].getId() == id) {
                    return i;
                }
            }
            return -1;
        }
    }

    public DBCApplication getApplication() {
        return application;
    }

    public void actionDelete() {
        int index = table.getSelectedRow();
        if (index >= 0) {
            index = table.convertRowIndexToModel(index);
            DBCDriverManagerEditorImpl.DriversModel pm = (DBCDriverManagerEditorImpl.DriversModel) table.getModel();
            int id = pm.drivers[index].getId();
            if (id >= 0) {
                getApplication().getDriverManager().removeDriver(id);
                modelChanged();
            }
        }
    }

    public void selectDriver(int driverId) {
        if (driverId >= 0 && newDriverInfo != null) {
            newDriverInfo = null;
            modelChanged();
        }
        DBCDriverManagerEditorImpl.DriversModel m = (DriversModel) table.getModel();
        int i = m.getDriverIndexById(driverId);
        i = table.convertRowIndexToView(i);
        if (i >= 0) {
            //Rectangle bounds = table.getCellRect(i, 0, true);
            table.setRowSelectionInterval(i, i);
            //jsp.getViewport().setViewPosition(bounds.getLocation());
            //jsp.getViewport().scrollRectToVisible(bounds);
        } else {
            table.getSelectionModel().clearSelection();
        }
        driverPropertiesPanelpluginPropertiesPanel.setDriver(m.drivers[i]);
    }

    void modelChanged() {
        DBCDriverInfo[] drivers = DBCDriverManagerEditorImpl.this.application.getDriverManager().getDrivers();
        table.setModel(new DBCDriverManagerEditorImpl.DriversModel(drivers, newDriverInfo));
    }

    public void actionReloadAll() throws IOException {
        DBCApplicationConfig config = getApplication().getConfig();
        URL resource = getClass().getClassLoader().getResource("net/thevpc/dbclient/plugin/system/configmanager/install-driver-defaults.xml");
        InputStream inputStream;
        inputStream = resource.openStream();
        Vector v = (Vector) XmlUtils.xmlToObject(inputStream, null, null);
        config.updateAll(v, DBCIfFoundAction.OVERWRITE, DBCIfNotFoundAction.ADD);
        modelChanged();
    }

    public void actionNew() {
        newDriverInfo = new DBCDriverInfo();
//        newDriverInfo.setApplication(getApplication());
        newDriverInfo.setId(-1);
        newDriverInfo.setName("NO_NAME");
        newDriverInfo.setEnabled(true);
        modelChanged();
        DBCDriverManagerEditorImpl.DriversModel m = (DriversModel) table.getModel();
        int i = m.getDriverIndexById(-1);
        i = table.convertRowIndexToView(i);
        if (i >= 0) {
            Rectangle bounds = table.getCellRect(i, 0, true);
            table.setRowSelectionInterval(i, i);
            jsp.getViewport().setViewPosition(bounds.getLocation());
            //jsp.getViewport().scrollRectToVisible(bounds);
        } else {
            table.getSelectionModel().clearSelection();
        }
        driverPropertiesPanelpluginPropertiesPanel.setDriver(newDriverInfo);
        driverPropertiesPanelpluginPropertiesPanel.id.requestFocus();
    }
}
