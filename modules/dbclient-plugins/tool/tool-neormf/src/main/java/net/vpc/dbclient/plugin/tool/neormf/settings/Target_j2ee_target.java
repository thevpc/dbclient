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
import net.vpc.dbclient.plugin.tool.neormf.NeormfPluginSession;
import org.vpc.neormf.jbgen.config.ConfigNode;
import org.vpc.neormf.jbgen.info.JBGenProjectInfo;
import org.vpc.neormf.jbgen.projects.J2eeTarget;
import net.vpc.swingext.DumbGridBagLayout;

import javax.swing.*;
import java.awt.*;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 20 avr. 2007 03:15:23
 */
class Target_j2ee_target extends NSettingPanel {
    ChangeJ2EELocationPanel locationPanel;
    JTextField projectName = new JTextField();
    JTextField datasource = new JTextField();
    JComboBox appServer = new JComboBox(JBGenProjectInfo.SUPPORTED_SERVERS.toArray());
    JComboBox ejbVersion = new JComboBox(JBGenProjectInfo.EJB_VERSIONS.toArray());
    JTextField root_package = new JTextField();
    JComboBox log_api = new JComboBox(JBGenProjectInfo.SUPPORTED_LOG_API_JAVA.toArray());
    JCheckBox enable = new JCheckBox("enable");
    private ModulesPanel modulesPanel;
    private class ModulesPanel extends NSettingPanel{
        NSettingPanel[] modules;
        private ModulesPanel(NeormfSettingsComponent neormfSettingsComponent) {
            super(J2eeTarget.NAME, "J2EE Project", neormfSettingsComponent);
            modules = new NSettingPanel[]{
                    new Module_dto_module(neormfSettingsComponent, locationPanel.root_path),
                    new Module_dao_module(neormfSettingsComponent, locationPanel.root_path),
                    new Module_ejb_module(neormfSettingsComponent, locationPanel.root_path),
                    new Module_ejb_business_delegate_module(neormfSettingsComponent, locationPanel.root_path)
            };
            DumbGridBagLayout bagLayout2=new DumbGridBagLayout("");
            setLayout(bagLayout2);
            addModules(bagLayout2, modules);
        }

        public void store(ConfigNode _target) {
            for (NSettingPanel module : modules) {
                module.store(_target);
            }
        }

        public void load(ConfigNode _target) {
            for (NSettingPanel module : modules) {
                module.load(_target);
            }
        }

        public void setEnabledComponent(boolean enabled) {
            for (NSettingPanel module : modules) {
                module.setEnabledComponent(enabled);
            }
        }
    }
    public Target_j2ee_target(NeormfPluginSession plugin,NeormfSettingsComponent neormfSettingsComponent) {
        super(J2eeTarget.NAME, "J2EE Project", neormfSettingsComponent);
        locationPanel = new ChangeJ2EELocationPanel(J2eeTarget.NAME, "J2EE Project", neormfSettingsComponent);
        modulesPanel=new ModulesPanel(neormfSettingsComponent);
        JTabbedPane jTabbedPane=new JTabbedPane();
        JPanel general=new JPanel();
        DumbGridBagLayout bagLayout2 = new DumbGridBagLayout()
                .addLine("[<projectNameLabel ] [<=-projectName    ::]")
                .addLine("[<pathLabel   ] [<=-path    ::]")
                .addLine("[<packageLabel] [<=-package ::]")
                .addLine("[<dataLabel    ] [<=-data     ][<logLabel    ] [<=-log     ]")
                .addLine("[<appLabel    ] [<=-app     ][<verLabel    ] [<=-version  ]")
                .setInsets(".*", new Insets(3, 3, 3, 3));
        general.setLayout(bagLayout2);


        general.add(new JLabel("Datasource"), "dataLabel");
        general.add(datasource, "data");
        general.add(new JLabel("Application Server"), "appLabel");
        general.add(appServer, "app");
        general.add(new JLabel("EJB Version"), "verLabel");
        general.add(ejbVersion, "version");

        general.add(new JLabel("J2EE Module Name"), "projectNameLabel");
        general.add(projectName, "projectName");
        general.add(new JLabel("Package"), "packageLabel");
        general.add(root_package, "package");
        general.add(new JLabel("Log API"), "logLabel");
        general.add(log_api, "log");


        jTabbedPane.addTab("General",general);
        jTabbedPane.addTab("Folders",locationPanel);
        jTabbedPane.addTab("Generation",modulesPanel);
        setLayout(new BorderLayout());
        add(jTabbedPane);
    }

    public void store(ConfigNode _target) {
        if (enable.isSelected()) {
            ConfigNode _dao_target = NUtils.findChild(_target,getTagId(),NUtils.NotFoundAction.DELETE);
            _dao_target.setEnabled(true);
            _dao_target.setName(projectName.getText());
            _dao_target.setAttribute("application-server", appServer.getSelectedItem().toString());
            _dao_target.setAttribute("ejb-version", ejbVersion.getSelectedItem().toString());
            NUtils.findChild(_dao_target,"package",NUtils.NotFoundAction.DISABLE).setValue(root_package.getText());
            locationPanel.store(_dao_target);
            NUtils.storeName(NUtils.findChild(_dao_target,"datasource",NUtils.NotFoundAction.DISABLE),datasource,"",true);

            Object selectedLogApi = log_api.getSelectedItem();
            ConfigNode logNode = NUtils.findChild(_dao_target, "log-generation", NUtils.NotFoundAction.DISABLE);
            logNode.setEnabled(!"none".equalsIgnoreCase(selectedLogApi.toString()));
            logNode.setAttribute("api", selectedLogApi.toString());
            modulesPanel.store(_dao_target);
        } else {
            ConfigNode _csharp_dao_target = _target.getChild(getTagId(), false);
            if (_csharp_dao_target != null) {
                _csharp_dao_target.setEnabled(false);
            }
        }
    }

    public void load(ConfigNode _target) {
        if (_target != null) {
            ConfigNode _dao_target = _target.getChild(getTagId(), false);
            if (_dao_target != null) {
                enable.setSelected(_dao_target.isEnabled());
                projectName.setText(_dao_target.getName());
                appServer.setSelectedItem(_dao_target.getAttribute("application-server"));
                ejbVersion.setSelectedItem(_dao_target.getAttribute("ejb-version"));
                ConfigNode _datasource = _dao_target.getChild("datasource", false);
                datasource.setText(_datasource == null ? "" : _datasource.getName());
                ConfigNode log=NUtils.findChild(_dao_target,"log-generation",false,true,NUtils.NotFoundAction.DELETE);
                log_api.setSelectedItem((log==null || !log.isEnabled())?"none":log.getAttribute("api"));

                ConfigNode _package = _dao_target.getChild("package", false);
                root_package.setText(_package == null ? "" : _package.getValue());
                locationPanel.load(_dao_target);
                modulesPanel.load(_dao_target);
                return;
            }
        }
        enable.setSelected(false);
        projectName.setText("");
        appServer.setSelectedItem(null);
        ejbVersion.setSelectedItem(null);
        datasource.setText("");
        log_api.setSelectedItem("none");
        root_package.setText("");
        locationPanel.load(null);
        modulesPanel.load(null);
    }

    public void setEnabledComponent(boolean enabled) {
       locationPanel.setEnabledComponent(enabled);
        modulesPanel.setEnabledComponent(enabled);
    }
}
