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


package net.vpc.dbclient.plugin.dbsupport.hsql;

import net.vpc.dbclient.api.pluginmanager.DBCPlugin;
import net.vpc.prs.plugin.Inject;
import net.vpc.dbclient.api.sessionmanager.DBCSessionListEditor;
import net.vpc.dbclient.api.sessionmanager.DBCURLBuilder;
import net.vpc.dbclient.api.sql.urlparser.DBCDriverUrlParser;
import net.vpc.swingext.DumbGridBagLayout;
import net.vpc.swingext.JFileTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Properties;
import net.vpc.dbclient.api.DBCApplicationView;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 2 juil. 2007 13:10:45
 */
public class HSqlURLBuilder implements DBCURLBuilder {
    private static final int MEM=0;
    private static final int FILE=1;

    @Inject
    private DBCPlugin plugin;

    public HSqlURLBuilder() {
    }

    private static class URLPanel extends JPanel {
        String[] driverTypes = new String[]{"mem", "file", "hsql", "hsqls", "http", "https"};
        final JLabel typeLabel = new JLabel("Type");
        final JLabel serverAddressLabel = new JLabel("Server Address");
        final JLabel serverPortLabel = new JLabel("Server Port");
        final JLabel serverDBNameLabel = new JLabel("Instance Name");
        final JLabel fileLabel = new JLabel("Folder");
        final JComboBox type = new JComboBox(driverTypes);
        final JTextField serverAddress = new JTextField("");
        final JTextField serverPort = new JTextField("");
        final JTextField serverDBName = new JTextField("");
        final JFileTextField file = new JFileTextField();
        final JLabel getcolumnnameLabel = new JLabel("get_column_name");
        final JLabel ifexistLabel = new JLabel("ifexist");
        final JLabel createLabel = new JLabel("create");
        final JLabel shutdownLabel = new JLabel("shutdown");
        final JComboBox getcolumnname = new JComboBox(new Object[]{null, true, false});
        final JComboBox ifexist = new JComboBox(new Object[]{null, true, false});
        final JComboBox create = new JComboBox(new Object[]{null, true, false});
        final JComboBox shutdown = new JComboBox(new Object[]{null, true, false});

//        final JLabel sql_enforce_namesLabel = new JLabel("sql.enforce_names");
//        final JComboBox sql_enforce_names = new JComboBox(new Object[]{null, true, false});
//        final JLabel sql_enforce_refsLabel = new JLabel("sql.enforce_refs");
//        final JComboBox sql_enforce_refs = new JComboBox(new Object[]{null, true, false});
//        final JLabel sql_enforce_sizeLabel = new JLabel("sql.enforce_size");
//        final JComboBox sql_enforce_size = new JComboBox(new Object[]{null, true, false});
//        final JLabel sql_enforce_typesLabel = new JLabel("sql.enforce_types");
//        final JComboBox sql_enforce_types = new JComboBox(new Object[]{null, true, false});
//        final JLabel sql_enforce_tdc_deleteLabel = new JLabel("sql.enforce_tdc_delete");
//        final JComboBox sql_enforce_tdc_delete = new JComboBox(new Object[]{null, true, false});
//        final JLabel sql_enforce_tdc_updateLabel = new JLabel("sql.enforce_tdc_update");
//        final JComboBox sql_enforce_tdc_update = new JComboBox(new Object[]{null, true, false});

        DBCSessionListEditor chooser;
        String driver;

        private URLPanel(DBCSessionListEditor chooser, String driver, String oldURL,DBCPlugin plugin) {
            super(new BorderLayout());
            this.chooser = chooser;
            this.driver = driver;
            file.getJFileChooser().setCurrentDirectory(plugin.getApplication().getWorkingDir());
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            DBCDriverUrlParser driverUrlParser = chooser.getApplication().getDriverManager().getDriverUrlParser(driver);
            Properties oldProps = driverUrlParser.parse(driver, oldURL);
            DumbGridBagLayout g1 = new DumbGridBagLayout();
            g1.addLine("[<-typeLabel][<-=type]");
            g1.addLine("[<-serverAddressLabel][<-=serverAddress]");
            g1.addLine("[<-serverPortLabel][<-=serverPort]");
            g1.addLine("[<-serverDBNameLabel][<-=serverDBName]");
            g1.addLine("[<-fileLabel][<-=file]");

            file.getJFileChooser().setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            g1.setInsets(".*", new Insets(1, 3, 1, 3));
            panel1.setLayout(g1);

            panel1.add(typeLabel, "typeLabel");
            panel1.add(serverAddressLabel, "serverAddressLabel");
            panel1.add(serverPortLabel, "serverPortLabel");
            panel1.add(serverDBNameLabel, "serverDBNameLabel");
            panel1.add(fileLabel, "fileLabel");

            panel1.add(type, "type");
            panel1.add(serverAddress, "serverAddress");
            panel1.add(serverPort, "serverPort");
            panel1.add(serverDBName, "serverDBName");
            panel1.add(file, "file");

            DumbGridBagLayout g2 = new DumbGridBagLayout();
            g2.addLine("[<-getcolumnnameLabel][<-=getcolumnname]");
            g2.addLine("[<-createLabel][<-=create]");
            g2.addLine("[<-ifexistLabel][<-=ifexist]");
            g2.addLine("[<-shutdownLabel][<-=shutdown]");
            g2.setInsets(".*", new Insets(1, 3, 1, 3));
            panel2.setLayout(g2);

            panel2.add(getcolumnname, "getcolumnname");
            panel2.add(getcolumnnameLabel, "getcolumnnameLabel");
            panel2.add(ifexist, "ifexist");
            panel2.add(ifexistLabel, "ifexistLabel");
            panel2.add(create, "create");
            panel2.add(createLabel, "createLabel");
            panel2.add(shutdown, "shutdown");
            panel2.add(shutdownLabel, "shutdownLabel");
            for (int i = 0; i < driverTypes.length; i++) {
                if (driverTypes[i].equals(oldProps.getProperty(HSqlNetworkDriverUrlParser.PARAM_DRIVER_TYPE))) {
                    type.setSelectedIndex(i);
                    break;
                }
            }
            type.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    revalidateStatus();
                }
            });
            serverAddress.setText(oldProps.getProperty(HSqlNetworkDriverUrlParser.PARAM_SERVER));
            serverPort.setText(oldProps.getProperty(HSqlNetworkDriverUrlParser.PARAM_PORT));
            serverDBName.setText(oldProps.getProperty(HSqlNetworkDriverUrlParser.PARAM_DATABASE));
            file.setFile(oldProps.getProperty(HSqlNetworkDriverUrlParser.PARAM_DBFOLDER));
            shutdown.setSelectedItem(oldProps.getProperty("shutdown"));
            ifexist.setSelectedItem(oldProps.getProperty("ifexist"));
            getcolumnname.setSelectedItem(oldProps.getProperty("get_column_name"));
            JTabbedPane jtp = new JTabbedPane();
            jtp.addTab("General", panel1);
            jtp.addTab("Advanced", panel2);
            this.add(jtp);
            revalidateStatus();

            ifexistLabel.setToolTipText(
                    "connect only if database already exists.\n" +
                    "Has an effect only with mem: and file: database.\n" +
                    "When true, will not create a new database if one does not already exist for the URL.\n" +
                    "When the property is false (the default), a new mem: or file: database will be created if it does not exist.\n" +
                    "Setting the property to true is useful when troubleshooting as no database is created if the URL is malformed");
            createLabel.setToolTipText(
                    "Similar to the ifexists property, but with opposite meaning.\n" +
                    "Has an effect only with mem: and file: database.\n" +
                    "When false, will not create a new database if one does not already exist for the URL.\n" +
                    "When the property is true (the default), a new mem: or file: database will be created if it does not exist.\n" +
                    "Setting the property to true is useful when troubleshooting as no database is created if the URL is malformed");
            shutdownLabel.setToolTipText(
                    "If this property is true, when the last connection to a database is closed,\n" +
                    "the database is automatically shut down.\n" +
                    "The property takes effect only when the first connection is made to the database.\n" +
                     "This means the connection that opens the database.\n" +
                     "It has no effect if used with subsequent connections.\n" +
                    "This command has two uses. One is for test suites, where connections to the database are made from one JVM context, immediately followed by another context.\n" +
                     "The other use is for applications where it is not easy to configure the environment to shutdown the database.\n" +
                     "Examples reported by users include web application servers, where the closing of the last connection coincides with the web app being shut down.");
            getcolumnnameLabel.setToolTipText("This property is used for compatibility with other JDBC driver implementations.\n" +
                    "When true (the default), ResultSet.getColumnName(int c) returns the underlying column name.\n" +
                    "This property can be specified differently for different connections to the same database.\n" +
                    "The default is true. When the property is false, the above method returns the same value as ResultSet.getColumnLabel(int column)");
        }

        private void revalidateStatus() {
            int i = type.getSelectedIndex();
            boolean _file=i==FILE;
            boolean _serverdbname=i!=FILE;
            boolean _server=i!=MEM && i!=FILE;
            boolean _ifexist=i==MEM || i==FILE;
            boolean _create=i==MEM || i==FILE;
            boolean _shudown=true;
            boolean _colname=true;

            file.setVisible(_file);
            fileLabel.setVisible(_file);
            serverDBName.setVisible(_serverdbname);
            serverDBNameLabel.setVisible(_serverdbname);
            serverAddress.setVisible(_server);
            serverAddressLabel.setVisible(_server);
            serverPort.setVisible(_server);
            serverPortLabel.setVisible(_server);
            ifexist.setVisible(_ifexist);
            ifexistLabel.setVisible(_ifexist);
            shutdown.setVisible(_shudown);
            shutdownLabel.setVisible(_shudown);
            getcolumnname.setVisible(_colname);
            getcolumnnameLabel.setVisible(_colname);
            create.setVisible(_create);
            createLabel.setVisible(_create);

        }

        public String getURL() {
            Properties props = new Properties();
            props.setProperty(HSqlNetworkDriverUrlParser.PARAM_DRIVER_TYPE, driverTypes[type.getSelectedIndex()]);
            if (shutdown.getSelectedItem() != null) {
                props.setProperty("shutdown", shutdown.getSelectedItem().toString());
            }
            switch (type.getSelectedIndex()) {
                case MEM: {
                    props.setProperty(HSqlNetworkDriverUrlParser.PARAM_DATABASE, serverDBName.getText());
                    if (ifexist.getSelectedItem() != null) {
                        props.setProperty(HSqlNetworkDriverUrlParser.PARAM_IFEXISTS, ifexist.getSelectedItem().toString());
                    }
                    if (ifexist.getSelectedItem() != null) {
                        props.setProperty(HSqlNetworkDriverUrlParser.PARAM_CREATE, create.getSelectedItem().toString());
                    }
                    break;
                }
                case FILE: {
                    props.setProperty(HSqlNetworkDriverUrlParser.PARAM_DATABASE, serverDBName.getText());
                    props.setProperty(HSqlNetworkDriverUrlParser.PARAM_DBFOLDER, file.getText());
                    if (ifexist.getSelectedItem() != null) {
                        props.setProperty(HSqlNetworkDriverUrlParser.PARAM_IFEXISTS, ifexist.getSelectedItem().toString());
                    }
                    if (ifexist.getSelectedItem() != null) {
                        props.setProperty(HSqlNetworkDriverUrlParser.PARAM_CREATE, create.getSelectedItem().toString());
                    }
                    break;
                }
                default: {
                    props.setProperty(HSqlNetworkDriverUrlParser.PARAM_SERVER, serverAddress.getText());
                    props.setProperty(HSqlNetworkDriverUrlParser.PARAM_PORT, serverPort.getText());
                    props.setProperty(HSqlNetworkDriverUrlParser.PARAM_DATABASE, serverDBName.getText());
                    if (getcolumnname.getSelectedItem() != null) {
                        props.setProperty(HSqlNetworkDriverUrlParser.PARAM_GET_COLUMN_NAME, getcolumnname.getSelectedItem().toString());
                    }
                    break;
                }
            }
            DBCDriverUrlParser driverUrlParser = chooser.getApplication().getDriverManager().getDriverUrlParser(driver);
            return driverUrlParser.format(driver, props);
        }
    }

    public String buildURL(DBCSessionListEditor chooser, String driver, String oldURL) {
        URLPanel panel = new URLPanel(chooser, driver, oldURL,plugin);
        ImageIcon image = chooser.getApplication().getView().getArtSet().getArtImage(DBCApplicationView.ARTSET_ICON128);
        if (JOptionPane.showConfirmDialog(chooser.getComponent(), panel, plugin.getMessageSet().get("DBCURLBuilder.DialogTitle"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, image) == JOptionPane.OK_OPTION) {
            return panel.getURL();
        } else {
            return null;
        }
    }

    public int accept(DBCSessionListEditor chooser, String driver) {
        return driver.equals("org.hsqldb.jdbcDriver") ? 10 : -1;
    }
}