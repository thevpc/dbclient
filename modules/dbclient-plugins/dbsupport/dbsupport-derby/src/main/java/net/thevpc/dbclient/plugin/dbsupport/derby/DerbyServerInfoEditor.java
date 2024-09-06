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


package net.thevpc.dbclient.plugin.dbsupport.derby;

import net.thevpc.common.swing.file.JFileTextField;
import net.thevpc.common.swing.layout.DumbGridBagLayout;
import net.thevpc.dbclient.api.DBCApplication;
import net.thevpc.dbclient.api.pluginmanager.DBCPlugin;
import net.thevpc.common.prs.plugin.Inject;
import net.thevpc.dbclient.plugin.tool.servermanager.DBCServerInfo;
import net.thevpc.dbclient.plugin.tool.servermanager.DBCServerInfoEditor;
import net.thevpc.dbclient.plugin.tool.servermanager.DBCServerManagerHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 27 juin 2007 12:55:22
 */
public class DerbyServerInfoEditor extends JPanel implements DBCServerInfoEditor {
    private JTextField portText;
    private JFileTextField folder;
    private String selectionValue = "<default-port>";
    private DBCServerManagerHandler managerHandler;
    private JLabel driverDetectedLabel = new JLabel();
    private JLabel serverLabel = new JLabel();
    private JFileTextField serverFolderLocation = new JFileTextField();
    private JCheckBox serverFolder = new JCheckBox();
    private JCheckBox serverPlugin = new JCheckBox();
    private JLabel nameLabel = new JLabel("Name");
    private JTextField nameText = new JTextField();
    private JCheckBox autoStart = new JCheckBox("Auto Start");
    private JCheckBox stopOnExit = new JCheckBox("Stop on Exit");

    @Inject
    private DBCApplication application;

    public DerbyServerInfoEditor() {
    }

    public void init(DBCServerManagerHandler managerHandler) {
        setLayout(new DumbGridBagLayout()
                .addLine("[<-nameLabel:][<=-nameText]")
                .addLine("[<-portLabel:][<=-portText]")
                .addLine("[<-folderLabel:][<=-folder]")
                .addLine("[<serverLabel          ::]")
                .addLine("[A1][<serverPlugin     ] [<-driverDetectedLabel  ]")
                .addLine("[A2][<serverFolder     ] [<-serverFolderLocation  ]")
                .addLine("[<stopOnExit     :: ]")
                .addLine("[<autoStart     :: ]")
                .setInsets(".*", new Insets(3, 3, 3, 3))
        );
        this.managerHandler = managerHandler;
        portText = new JTextField(selectionValue);
        folder = new JFileTextField();
        folder.getJFileChooser().setCurrentDirectory(application.getWorkingDir());
        serverFolderLocation.getJFileChooser().setCurrentDirectory(application.getWorkingDir());
        folder.getJFileChooser().setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        folder.setFile(new File("."));
        add(new JLabel("Port"), "portLabel");
        add(new JLabel("Folder"), "folderLabel");
        add(portText, "portText");
        add(folder, "folder");
        add(driverDetectedLabel, "driverDetectedLabel");
        add(serverLabel, "serverLabel");
        add(serverFolderLocation, "serverFolderLocation");
        add(serverFolder, "serverFolder");
        add(serverPlugin, "serverPlugin");
        add(nameLabel, "nameLabel");
        add(nameText, "nameText");
        add(portText, "portText");
        add(autoStart, "autoStart");
        add(stopOnExit, "stopOnExit");
        add("A1", Box.createHorizontalStrut(30));
        add("A2", Box.createHorizontalStrut(30));
        ButtonGroup bg = new ButtonGroup();
        bg.add(serverFolder);
        bg.add(serverPlugin);
        ItemListener il = new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                serverFolderLocation.setEnabled(serverFolder.isSelected());
            }
        };
        serverFolder.addItemListener(il);
        serverPlugin.addItemListener(il);
    }

    public DBCServerInfo showDialog(Component parent, DBCServerInfo value) {
        DerbyServerInfo vvalue = (DerbyServerInfo) value;
        if (vvalue == null) {
            vvalue = (DerbyServerInfo) managerHandler.createServerInfo();
            vvalue.setConfigName(DerbyServerManager.TYPE);
            vvalue.setClassPathType(DerbyClassPathType.PLUGIN);
            vvalue.setStopOnExit(true);
        }
        DBCPlugin pp = application.getPluginManager().getValidPlugin(vvalue.getLibPluginId());
        driverDetectedLabel.setText(pp == null ? ("Plugin " + vvalue.getLibPluginId() + " not found!") : (vvalue.getLibPluginId() + " loaded successfully"));
        driverDetectedLabel.setForeground(pp == null ? Color.RED : Color.GREEN.darker());
        serverLabel.setText("Derby Server Libraries");
        serverFolder.setText("From Lib Folder");
        serverPlugin.setText("From Plugin");
        serverFolderLocation.setText(vvalue.getLibFolderPath());
        DerbyClassPathType type = vvalue.getClassPathType();
        serverFolder.setSelected(DerbyClassPathType.FOLDER.equals(type));
        serverPlugin.setSelected(DerbyClassPathType.PLUGIN.equals(type));
        serverFolderLocation.setEnabled(serverFolder.isSelected());

        String oldFolder = vvalue.getDatabasePath();
        folder.setFile((oldFolder == null || oldFolder.trim().length() == 0) ? new File(".") : new File(oldFolder));
        nameText.setText(vvalue.getConfigName());
        autoStart.setSelected(vvalue.isAutoStart());
        stopOnExit.setSelected(vvalue.isStopOnExit());
        portText.setText(vvalue.getPort() == 0 ? selectionValue : String.valueOf(vvalue.getPort()));
        boolean b = JOptionPane.showConfirmDialog(parent, this, "New " + managerHandler.getType(), JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION;
        if (b) {
            return getDBServerInfo();
        }
        return null;
    }

    private DBCServerInfo getDBServerInfo() {
        DerbyServerInfo info = new DerbyServerInfo();
        info.setAutoStart(autoStart.isSelected());
        info.setStopOnExit(stopOnExit.isSelected());
        info.setConfigName(nameText.getText());
        if (portText.getText().length() == 0 || portText.getText().equals(selectionValue)) {
            info.setPort(0);
        } else {
            info.setPort(Integer.parseInt(portText.getText()));
        }
        info.setDatabasePath(folder.getFilePath());
        info.setClassPathType(serverFolder.isSelected() ? DerbyClassPathType.FOLDER : DerbyClassPathType.PLUGIN);
        info.setLibFolderPath(serverFolder.isSelected() ? serverFolderLocation.getText() : "");
        return info;
    }
}