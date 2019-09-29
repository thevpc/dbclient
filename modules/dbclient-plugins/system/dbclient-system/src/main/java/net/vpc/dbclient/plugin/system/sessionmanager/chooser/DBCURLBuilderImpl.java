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

import net.vpc.dbclient.api.pluginmanager.DBCPlugin;
import net.vpc.prs.plugin.Inject;
import net.vpc.dbclient.api.sessionmanager.DBCSessionListEditor;
import net.vpc.dbclient.api.sessionmanager.DBCURLBuilder;
import net.vpc.dbclient.api.sql.urlparser.DBCDriverParameter;
import net.vpc.dbclient.api.sql.urlparser.DBCDriverUrlParser;
import net.vpc.swingext.DumbGridBagLayout;
import net.vpc.swingext.JFileTextField;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.Properties;
import java.util.Set;
import net.vpc.dbclient.api.DBCApplicationView;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 2 juil. 2007 13:10:45
 */
public class DBCURLBuilderImpl implements DBCURLBuilder {
    @Inject
    private DBCPlugin plugin;

    public DBCURLBuilderImpl() {
    }

    public String buildURL(DBCSessionListEditor chooser, String driver, String oldURL) {
        LinkedHashMap<String, JComponent> comps = new LinkedHashMap<String, JComponent>();
        JPanel panel = new JPanel();
        DBCDriverUrlParser driverUrlParser = plugin.getApplication().getDriverManager().getDriverUrlParser(driver);
        Properties oldProps = driverUrlParser.parse(driver, oldURL);
        Set<DBCDriverParameter> dbcDriverParameters = driverUrlParser.getParameters();
        DumbGridBagLayout g = new DumbGridBagLayout();
        for (DBCDriverParameter p : dbcDriverParameters) {
            g.addLine("[<-" + p.getId() + "Label][<-=" + p.getId() + "]");
        }
        g.setInsets(".*", new Insets(1, 3, 1, 3));
        panel.setLayout(g);
        Dimension copDimension = new Dimension(100, 10);
        for (DBCDriverParameter p : dbcDriverParameters) {
            JComponent c = null;
            switch (p.getType()) {
                case BOOLEAN: {
                    c = new JCheckBox("");
                    c.setMinimumSize(copDimension);
                    ((JCheckBox) c).setSelected("true".equals(oldProps.getProperty(p.getId())));
                    break;
                }
                case FILE: {
                    c = new JFileTextField();
                    ((JFileTextField)c).getJFileChooser().setCurrentDirectory(plugin.getApplication().getWorkingDir());
                    c.setMinimumSize(copDimension);
                    ((JFileTextField) c).getJFileChooser().setFileSelectionMode(JFileChooser.FILES_ONLY);
                    ((JFileTextField) c).setFile(oldProps.containsKey(p.getId()) ? new File(oldProps.getProperty(p.getId())) : null);
                    break;
                }
                case FOLDER: {
                    c = new JFileTextField();
                    ((JFileTextField)c).getJFileChooser().setCurrentDirectory(plugin.getApplication().getWorkingDir());
                    c.setMinimumSize(copDimension);
                    ((JFileTextField) c).getJFileChooser().setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    ((JFileTextField) c).setFile(oldProps.containsKey(p.getId()) ? new File(oldProps.getProperty(p.getId())) : null);
                    break;
                }
                case INTEGER: {
                    c = new JTextField("");
                    c.setMinimumSize(copDimension);
                    ((JTextField) c).setText(oldProps.getProperty(p.getId()));
                    break;
                }
                case STRING: {
                    c = new JTextField("");
                    c.setMinimumSize(copDimension);
                    ((JTextField) c).setText(oldProps.getProperty(p.getId()));
                    ((JTextField) c).setColumns(16);
                    break;
                }
                case ENUM: {
                    c = new JComboBox();
                    c.setMinimumSize(copDimension);
                    String[] allValues = p.getAllValues();
                    for (String value : allValues) {
                        ((JComboBox) c).addItem(value);
                    }
                    ((JComboBox) c).setSelectedItem(oldProps.getProperty(p.getId()));
                    break;
                }
            }
            comps.put(p.getId(), c);
            panel.add(c, p.getId());
            panel.add(new JLabel(p.getLabel()), p.getId() + "Label");
        }
        ImageIcon image = plugin.getApplication().getView().getArtSet().getArtImage(DBCApplicationView.ARTSET_ICON128);
        if (JOptionPane.showConfirmDialog(chooser.getComponent(), panel, plugin.getMessageSet().get("DBCURLBuilder.DialogTitle"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, image) == JOptionPane.OK_OPTION) {
            Properties props = new Properties();
            for (DBCDriverParameter p : dbcDriverParameters) {
                JComponent c = comps.get(p.getId());
                switch (p.getType()) {
                    case BOOLEAN: {
                        props.setProperty(p.getId(), ((JCheckBox) c).isSelected() ? "true" : "false");
                        break;
                    }
                    case FILE: {
                        File f = ((JFileTextField) c).getFile();
                        if (f != null) {
                            props.setProperty(p.getId(), f.getPath());
                        }
                        break;
                    }
                    case FOLDER: {
                        props.setProperty(p.getId(), ((JFileTextField) c).getFile().getPath());
                        break;
                    }
                    case INTEGER: {
                        props.setProperty(p.getId(), ((JTextField) c).getText());
                        break;
                    }
                    case STRING: {
                        props.setProperty(p.getId(), ((JTextField) c).getText());
                        break;
                    }
                    case ENUM: {
                        props.setProperty(p.getId(), (String) ((JComboBox) c).getSelectedItem());
                        break;
                    }
                }
            }
            return driverUrlParser.format(driver, props);
        } else {
            return null;
        }
    }

    public int accept(DBCSessionListEditor chooser, String driver) {
        DBCDriverUrlParser driverUrlParser = plugin.getApplication().getDriverManager().getDriverUrlParser(driver);
        return driverUrlParser != null ? 1 : -1;
    }
}
