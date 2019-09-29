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
import org.vpc.neormf.jbgen.config.ConfigNode;
import org.vpc.neormf.jbgen.info.JBGenProjectInfo;
import org.vpc.neormf.jbgen.projects.CsharpDAOTarget;
import net.vpc.swingext.DumbGridBagLayout;
import net.vpc.swingext.JFileTextField;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 20 avr. 2007 03:15:23
 */
class Target_csharp_dao_target extends NSettingPanel {
    NSettingPanel[] modules;
    JFileTextFieldDefault root_path = new JFileTextFieldDefault();
    JTextField projectName = new JTextField();
    JTextField root_package = new JTextField();
    JComboBox log_api = new JComboBox(JBGenProjectInfo.SUPPORTED_LOG_API_CSHARP.toArray());
    JCheckBox enable = new JCheckBox("enable");

    public Target_csharp_dao_target(NeormfSettingsComponent neormfSettingsComponent) {
        super(CsharpDAOTarget.NAME, "CSharp Project", neormfSettingsComponent);
        modules = new NSettingPanel[]{
                new Module_dto_module(neormfSettingsComponent,root_path),
                new Module_dao_module(neormfSettingsComponent,root_path)
        };
        DumbGridBagLayout bagLayout2 = new DumbGridBagLayout()
                .addLine("[^<projectNameLabel   ] [<=-projectName    ]")
                .addLine("[<pathLabel   ] [<=-path    ]")
                .addLine("[<packageLabel] [<=-package ]")
                .addLine("[<logLabel    ] [<=-log     ]")
                .setInsets(".*", new Insets(3, 3, 3, 3));
        setLayout(bagLayout2);
        addModules(bagLayout2, modules);
        root_path.getJFileChooser().setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        add(new JLabel("Csharp Module Name"), "projectNameLabel");
        add(projectName, "projectName");

        add(new JLabel("Root Path"), "pathLabel");
        add(root_path, "path");

        add(new JLabel("Package"), "packageLabel");
        add(root_package, "package");

        add(new JLabel("Log API"), "logLabel");
        add(log_api, "log");
        neormfSettingsComponent.getProjectConfigPanel().getProjectReferenceFolder().getFileTextField().addPropertyChangeListener(JFileTextField.SELECTED_FILE, new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
               root_path.setDefaultFolder(getNeormfSettingsComponent().getProjectConfigPanel().getProjectReferenceFolder().getAbsoluteFile());
            }
        });
    }

    public void load(ConfigNode _target) {
        if (_target != null) {
            ConfigNode _dao_target = _target.getChild(getTagId(), false);
            if (_dao_target != null) {
                enable.setSelected(_dao_target.isEnabled());
                projectName.setText(_dao_target.getName());
                ConfigNode _package = NUtils.findChild(_dao_target,"package",NUtils.NotFoundAction.DISABLE);
                root_package.setText(_package == null ? "" : _package.getValue());
                ConfigNode _root_path =  NUtils.findChild(_dao_target,"root-path", NUtils.NotFoundAction.DISABLE);
                root_path.setFile(_root_path == null ? "" : _root_path.getValue());
                ConfigNode log=NUtils.findChild(_dao_target,"log-generation",NUtils.NotFoundAction.DELETE);
                log_api.setSelectedItem((log==null || !log.isEnabled())?"none":log.getAttribute("api"));
                for (NSettingPanel module : modules) {
                    module.load(_dao_target);
                }
            }
        }else{
            enable.setSelected(false);
            projectName.setText("");
            root_package.setText("");
            root_path.setFile("");
            log_api.setSelectedItem("none");
            for (NSettingPanel module : modules) {
                module.load(null);
            }
        }
    }

    public void store(ConfigNode _target) {
        if (enable.isSelected()) {
            ConfigNode _dao_target = NUtils.findChild(_target,getTagId(),NUtils.NotFoundAction.DELETE);
            _dao_target.setEnabled(true);
            _dao_target.setName(projectName.getText());
            NUtils.storeValue(NUtils.findChild(_dao_target,"package",NUtils.NotFoundAction.DELETE),root_package,"",true);
            NUtils.storeValue(NUtils.findChild(_dao_target,"root-path",NUtils.NotFoundAction.DELETE),root_path,"",true);
            Object selectedLogApi = log_api.getSelectedItem();
            ConfigNode logNode = NUtils.findChild(_dao_target, "log-generation", false, true, NUtils.NotFoundAction.DELETE);
            logNode.setEnabled(!"none".equalsIgnoreCase(selectedLogApi.toString()));
            logNode.setAttribute("api", selectedLogApi.toString());
            for (NSettingPanel module : modules) {
                module.store(_dao_target);
            }
        } else {
            ConfigNode _csharp_dao_target = _target.getChild(getTagId(), false);
            if (_csharp_dao_target != null) {
                _csharp_dao_target.setEnabled(false);
            }
        }
    }

    public void setEnabledComponent(boolean enabled) {

    }
}
