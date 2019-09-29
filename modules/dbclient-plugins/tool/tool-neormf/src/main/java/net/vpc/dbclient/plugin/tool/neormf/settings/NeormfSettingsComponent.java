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

import net.vpc.dbclient.api.configmanager.DBCSessionSettingsComponent;
import net.vpc.dbclient.api.pluginmanager.DBCPluginSession;
import net.vpc.dbclient.api.DBCSession;
import net.vpc.prs.plugin.Inject;
import net.vpc.prs.plugin.Initializer;
import net.vpc.dbclient.api.viewmanager.DBCPluggablePanel;
import net.vpc.dbclient.plugin.tool.neormf.NUtils;
import net.vpc.dbclient.plugin.tool.neormf.NeormfPluginSession;
import org.vpc.neormf.jbgen.JBGenMain;
import org.vpc.neormf.jbgen.config.ConfigNode;
import net.vpc.swingext.dialog.MessageDialogType;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Level;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 20 avr. 2007 01:21:30
 */
public class NeormfSettingsComponent extends DBCPluggablePanel implements DBCSessionSettingsComponent {

    static Font descFont = new Font("Dialog", 2, 12);
    @Inject
    private DBCPluginSession pluginSession;
    private CardLayout moduleLayout;
    private JPanel modulePanel;
    ProjectTypeConfigPanel projectTypeConfigPanel;
    //    private SourcePanel sourcePanel;
    private ProjectConfigPanel projectConfigPanel;
    private Target_csharp_dao_target csharp_dao_target;
    private Target_java_dao_target java_dao_target;
    private Target_java_dao_zerolib_target java_dao_zerolib_target;
    private Target_j2ee_target j2ee_target;
    private SourcePanel sourcePanel;
    private TabOptions optionsPanel;
    private CodeStylePanel codeStylePanel;
    private JTabbedPane pages;

    public NeormfSettingsComponent() {
        int x=4;
        x++;
    }


    public boolean acceptSession(DBCSession pluginSession) {
        return true;
    }

    @Initializer
    public void init() {
        setLayout(new BorderLayout());
        pages = new JTabbedPane();
        pages.addTab("General", new JScrollPane(projectConfigPanel = new ProjectConfigPanel(this)));
        pages.addTab("Project Type", new JScrollPane(projectTypeConfigPanel = new ProjectTypeConfigPanel(this)));
        pages.addTab("Source", new JScrollPane(sourcePanel = new SourcePanel(this)));
        projectTypeConfigPanel.addPropertyChangeListener("TargetChanged", new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                targetchanged();
            }
        });
        pages.addTab("Project Properties", new JScrollPane(createTabModules()));
        pages.addTab("Code Style", new JScrollPane(createTabCodeStyle()));
        pages.addTab("Options", new JScrollPane(createTabOptions()));
        projectConfigPanel.addPropertyChangeListener("ConfigNode", new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                loadConfig0();
            }
        });
        projectConfigPanel.addPropertyChangeListener("NeormfEnabled", new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                validateEnableNeormf();
            }
        });
        add(pages);
        validateEnableNeormf();
    }

    public void validateEnableNeormf() {
        boolean b = projectConfigPanel.isNeormfEnabled();
        sourcePanel.setEnabledComponent(b);
        optionsPanel.setEnabledComponent(b);
        codeStylePanel.setEnabledComponent(b);
        pages.setSelectedIndex(0);
        for (int i = 1; i < pages.getTabCount(); i++) {
            pages.setEnabledAt(i, b);
        }
    }

    public JComponent createTabProject() {
        JPanel main = new JPanel(new BorderLayout());

        return main;
    }

    public JComponent createTabCodeStyle() {
        codeStylePanel = new CodeStylePanel(getPluginSession());
        return codeStylePanel;
    }

    public JComponent createTabOptions() {
        optionsPanel = new TabOptions(this);
        return optionsPanel;
    }

    public JComponent createTabModules() {
        modulePanel = new JPanel();
        moduleLayout = new CardLayout();
        modulePanel.setLayout(moduleLayout);
        modulePanel.add(csharp_dao_target = new Target_csharp_dao_target(this), "csharp_dao_target");
        modulePanel.add(java_dao_target = new Target_java_dao_target(this), "java_dao_target");
        modulePanel.add(java_dao_zerolib_target = new Target_java_dao_zerolib_target(this), "java_dao_zerolib_target");
        modulePanel.add(j2ee_target = new Target_j2ee_target(getPluginSession(), this), "j2ee_target");
        targetchanged();
        return modulePanel;
    }

    private void targetchanged() {
        moduleLayout.show(modulePanel, projectTypeConfigPanel.getTarget());
    }

    public String getId() {
        return getClass().getSimpleName();
    }

    public String getTitle() {
        return getPluginSession().getMessageSet().get("NeormfSettingsComponent");
    }

    public Icon getIcon() {
        return getPluginSession().getIconSet().getIconR("NeormfIcon");
    }

    public JComponent getComponent() {
        return this;
    }

    public void loadConfig() {
        projectConfigPanel.load();
    }

    public void loadConfig0() {
        try {
            ConfigNode root = projectConfigPanel.getRootConfigNode();
            sourcePanel.load(root);
            optionsPanel.load(root);
            codeStylePanel.load(root);
//                    sourcePanel.load(root.getChild("source",false));
            ConfigNode _target = root == null ? null : NUtils.findChild(root, "target", true, false, NUtils.NotFoundAction.DISABLE);
            csharp_dao_target.load(_target);
            java_dao_target.load(_target);
            java_dao_zerolib_target.load(_target);
            j2ee_target.load(_target);
            projectTypeConfigPanel.setTarget(
                    csharp_dao_target.enable.isSelected() ? "csharp_dao_target"
                            : j2ee_target.enable.isSelected() ? "j2ee_target"
                            : java_dao_target.enable.isSelected() ? "java_dao_target"
                            : java_dao_zerolib_target.enable.isSelected() ? "java_dao_zerolib_target" : null
            );
        } catch (Throwable e) {
            e.printStackTrace();
            //
        }
    }

    public void saveConfig() {
        try {
            getPluginSession().setSupported(projectConfigPanel.isNeormfEnabled());
            projectConfigPanel.store();
            ConfigNode root = projectConfigPanel.getRootConfigNode();
//                projectConfigPanel.store(root);
            if (root != null) {
                optionsPanel.store(root);
                sourcePanel.store(root);
                codeStylePanel.store(root);
                ConfigNode _target = NUtils.findChild(root, "target", NUtils.NotFoundAction.DISABLE);
                csharp_dao_target.enable.setSelected("csharp_dao_target".equals(projectTypeConfigPanel.getTarget()));
                java_dao_target.enable.setSelected("java_dao_target".equals(projectTypeConfigPanel.getTarget()));
                java_dao_zerolib_target.enable.setSelected("java_dao_zerolib_target".equals(projectTypeConfigPanel.getTarget()));
                j2ee_target.enable.setSelected("j2ee_target".equals(projectTypeConfigPanel.getTarget()));

                csharp_dao_target.store(_target);
                java_dao_target.store(_target);
                java_dao_zerolib_target.store(_target);
                j2ee_target.store(_target);
                root.store(JBGenMain.getProjectFile(projectConfigPanel.getProjectConfigfile()));
            }
        } catch (Throwable e) {
            getPluginSession().getSession().getView().getDialogManager().showMessage(this, null, MessageDialogType.ERROR, null, e);
            getPluginSession().getSession().getLogger(getClass().getName()).log(Level.SEVERE,"saveConfig failed",e);
        }
        //
    }

    public int getPosition() {
        return 0;
    }

    public CodeStylePanel getCodeStylePanel() {
        return codeStylePanel;
    }

    public Target_csharp_dao_target getCsharp_dao_target() {
        return csharp_dao_target;
    }

    public Target_j2ee_target getJ2ee_target() {
        return j2ee_target;
    }

    public Target_java_dao_zerolib_target getJava_dao_zerolib_target() {
        return java_dao_zerolib_target;
    }

    public Target_java_dao_target getJava_dao_target() {
        return java_dao_target;
    }

    public JPanel getModulePanel() {
        return modulePanel;
    }

    public TabOptions getOptionsPanel() {
        return optionsPanel;
    }

    public JTabbedPane getPages() {
        return pages;
    }

    public ProjectConfigPanel getProjectConfigPanel() {
        return projectConfigPanel;
    }

    public ProjectTypeConfigPanel getProjectTypeConfigPanel() {
        return projectTypeConfigPanel;
    }

    public SourcePanel getSourcePanel() {
        return sourcePanel;
    }

    public NeormfPluginSession getPluginSession() {
        return (NeormfPluginSession) pluginSession;
    }
}
