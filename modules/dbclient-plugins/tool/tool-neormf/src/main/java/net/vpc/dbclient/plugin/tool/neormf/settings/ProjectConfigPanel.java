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

package net.vpc.dbclient.plugin.tool.neormf.settings;

import net.vpc.dbclient.plugin.tool.neormf.NUtils;
import org.vpc.neormf.jbgen.JBGenMain;
import org.vpc.neormf.jbgen.config.ConfigNode;
import net.vpc.swingext.DumbGridBagLayout;
import net.vpc.swingext.JFileTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 20 avr. 2007 03:23:31
 */
class ProjectConfigPanel extends NSettingPanel {
    private JCheckBox enableNeormf;
    private JFileTextField projectConfigLocation;
    private JFileTextFieldDefault projectReferenceFolder;
    private ConfigNode rootConfigNode = null;

    public ProjectConfigPanel(NeormfSettingsComponent neormfSettingsComponent) {
        super("", "", neormfSettingsComponent);
        setLayout(
                new DumbGridBagLayout()
                        .addLine("[~<=-enable]")
                        .addLine("[~<=-location]")
                        .addLine("[~<=-source]")
                        .addLine("[~<=-options]")
                        .addLine("[~<=-types]")
                        .addLine("[+$$==nothing:]")
                        .setInsets(".*", new Insets(3, 3, 3, 3))
        );

        enableNeormf = new JCheckBox("Enable Neormf Support");

        projectReferenceFolder = new JFileTextFieldDefault();
        projectReferenceFolder.getJFileChooser().setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//        projectReferenceFolder.getFileTextField().addPropertyChangeListener(JFileTextField.PRE_SELECT_FILE,new PropertyChangeListener() {
//            public void propertyChange(PropertyChangeEvent evt) {
//                projectReferenceFolder.getFileTextField().setDefaultFolder(projectConfigLocation.getFile());
//            }
//        });
        projectConfigLocation = new JFileTextField();
        projectConfigLocation.getJFileChooser().setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        projectConfigLocation.getTextField().setEditable(false);
        projectConfigLocation.addPropertyChangeListener(JFileTextField.SELECTED_FILE, new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                projectReferenceFolder.setDefaultFolder(projectConfigLocation.getAbsoluteFile());
            }
        });
        projectReferenceFolder.setDefaultFolder(projectConfigLocation.getAbsoluteFile());
        JPanel location = new JPanel(
                new DumbGridBagLayout()
                        .addLine("[<^=-desc1 :]")
                        .addLine("[<^-lab1][<=-comp1]")
                        .addLine("[<=-desc2 : ]")
                        .addLine("[<^-lab2][<=-comp2]")
                        .setInsets(".*", new Insets(3, 3, 3, 3))
                        .setInsets("lab.*", new Insets(3, 10, 3, 3))
        );
        JLabel comp = new JLabel("Select Folder into which all Neormf config files will be stored");
        JLabel compRef = new JLabel("Select Folder into which all Source files will be generated relatively to The 'Project Configuration Folder'");
        comp.setFont(NeormfSettingsComponent.descFont);
        compRef.setFont(NeormfSettingsComponent.descFont);
        JLabel jLabel = new JLabel("Project Configuration Folder");
        JLabel jLabelRef = new JLabel("Project Source Root Folder");
        location.add(comp, "desc1");
        location.add(compRef, "desc2");
        location.add(jLabel, "lab1");
        location.add(jLabelRef, "lab2");
        location.add(projectConfigLocation, "comp1");
        location.add(projectReferenceFolder, "comp2");
        location.setBorder(BorderFactory.createTitledBorder("Project Location"));

        this.add(enableNeormf, "enable");
        this.add(location, "location");
        this.add(new JPanel(), "nothing");
        projectConfigLocation.addPropertyChangeListener(JFileTextField.SELECTED_FILE, new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                if (!isLoading) {
                    projectFilechanged();
                }
            }
        });
        enableNeormf.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {
                validateEnableNeormf();
            }
        });
        validateEnableNeormf();
    }

    private boolean isLoading = false;

    public void load() {
        try {
            isLoading = true;
            enableNeormf.setSelected(getPluginSession().isSupported());
            if (getPluginSession().isProjectCreated()) {
                File folder = getPluginSession().getValidProjectFolder();
                projectConfigLocation.setFile(folder);
            } else {
                projectConfigLocation.setFile((File) null);
            }
            projectFilechanged();
        } finally {
            isLoading = false;
        }
    }

    private void projectFilechanged() {
        File folder = projectConfigLocation.getFile();
        File file = folder == null ? null : JBGenMain.getProjectFile(folder);
        rootConfigNode = null;
        if (file != null && file.exists()) {
            try {
                rootConfigNode = ConfigNode.load(file);
            } catch (Throwable e) {
                getPluginSession().getSession().getLogger(getClass().getName()).log(Level.SEVERE,"ConfigNode.load failed",e);
            }
        }
        projectReferenceFolder.setFile((File) null);
        if (rootConfigNode != null) {
            ConfigNode ref = NUtils.findChildByPath(rootConfigNode, "options.option-reference-folder", NUtils.NotFoundAction.DISABLE);
            if (ref != null) {
                projectReferenceFolder.setFile((ref.getValue() == null || ref.getValue().trim().length() == 0) ? null : new File(ref.getValue()));
            }
        }
        firePropertyChange("ConfigNode", null, rootConfigNode);
    }

    public void load(ConfigNode _target) {
        //??
    }

    public void store() throws IOException, SQLException {
        boolean b = enableNeormf.isSelected();
        if (b) {
            File configFolder = projectConfigLocation.getFile();
            getPluginSession().setProjectFolder(configFolder);
            if (!getPluginSession().isProjectCreated()) {
                getPluginSession().createProject();
            }
//            File folder = pluginSession.getValidProjectFolder();
//            File file = JBGenMain.getProjectFile(folder);
//            ConfigNode root = null;
//            try {
//                root = ConfigNode.load(file);
//            } catch (Throwable e) {
//                pluginSession.getSession().getLog().error(e);
//            }
            if (rootConfigNode == null) {
                rootConfigNode = new ConfigNode("jbgen");
            }
        }
        store(rootConfigNode);
    }

    public void store(ConfigNode _target) {
        if (_target != null) {
            if (projectReferenceFolder.getFilePath() != null) {
                NUtils.findChildByPath(_target, "options.option-reference-folder", NUtils.NotFoundAction.DISABLE).setValue(projectReferenceFolder.getFilePath());
            } else {
                NUtils.findChildByPath(_target, "options.option-reference-folder", NUtils.NotFoundAction.DISABLE).remove();
            }
        }
    }

    public void setEnabledComponent(boolean enabled) {
        projectConfigLocation.setEnabled(enabled);
        projectReferenceFolder.setEnabled(enabled);
    }

    private void validateEnableNeormf() {
        boolean b = enableNeormf.isSelected();
        setEnabledComponent(b);
        firePropertyChange("NeormfEnabled", !b, b);
    }

    public boolean isNeormfEnabled() {
        return enableNeormf.isSelected();
    }

    public ConfigNode getRootConfigNode() {
        return rootConfigNode;
    }

    public File getProjectConfigfile() {
        return projectConfigLocation.getFile();
    }

    public JFileTextFieldDefault getProjectReferenceFolder() {
        return projectReferenceFolder;
    }
}
