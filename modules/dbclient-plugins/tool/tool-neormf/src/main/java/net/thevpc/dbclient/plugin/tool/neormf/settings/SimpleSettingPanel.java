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

package net.thevpc.dbclient.plugin.tool.neormf.settings;

import net.thevpc.common.swing.file.JFileTextField;
import net.thevpc.common.swing.layout.DumbGridBagLayout;
import net.thevpc.dbclient.plugin.tool.neormf.NUtils;
import org.vpc.neormf.jbgen.config.ConfigNode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 20 avr. 2007 03:16:12
 */
abstract class SimpleSettingPanel extends NSettingPanel {
    boolean filter;
    NSettingPanel[] modules;
    JCheckBox enable = new JCheckBox("enable", true);
    ChangeLocationButton changeLocationButton;
    ChangeFilterButton changeFilterButton;
    NeormfSettingsComponent neormfSettingsComponent;
    JFileTextFieldDefault rootFolder;
    public SimpleSettingPanel(String tagId, String tagTitle,NeormfSettingsComponent neormfSettingsComponent,JFileTextFieldDefault rootFolder) {
        this(tagId, tagTitle, null, true,neormfSettingsComponent,rootFolder);
   }

    public void setEnabledComponent(boolean enabled) {
        setEnabledComponent(enabled, true);
    }

    public void setEnabledComponent(boolean enabled, boolean b) {
        if (b) {
            enable.setEnabled(enabled);
        }
        changeLocationButton.root_path.setEnabled(enabled);
        if (filter) {
            changeFilterButton.setEnabled(enabled);
        }
        changeLocationButton.setEnabled(enabled);
        if (modules != null) {
            for (NSettingPanel module : modules) {
                module.setEnabledComponent(enabled);
            }
        }
    }

    public SimpleSettingPanel(String tagId, String tagTitle, NSettingPanel[] modules, boolean filter, NeormfSettingsComponent neormfSettingsComponent,JFileTextFieldDefault rootFolder) {
        super(tagId, tagTitle, neormfSettingsComponent);
        this.modules = modules;
        this.rootFolder = rootFolder;
        this.filter = filter;
        setLayout(createLayout());
        changeLocationButton = new ChangeLocationButton(this);
        changeFilterButton = new ChangeFilterButton(this);
        if (filter) {
            add(changeFilterButton, "fil");
        }
        enable.setText("Generate " + tagTitle);
        add(enable, "enable");
        add(changeLocationButton, "loc");
        if (modules != null) {
            for (int i = 0; i < modules.length; i++) {
                add(modules[i], "module" + i);
            }
        }
        rootFolder.getFileTextField().addPropertyChangeListener(JFileTextField.SELECTED_FILE,new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                changeLocationButton.root_path.setDefaultFolder(SimpleSettingPanel.this.rootFolder.getAbsoluteFile());
            }
        });
         enable.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
//                boolean b = enable.isEnabled();
                setEnabledComponent(e.getStateChange() == ItemEvent.SELECTED, false);
//                enable.setEnabled(b);
            }
        });
        setEnabledComponent(enable.isEnabled(), false);
    }

    protected DumbGridBagLayout createLayout() {
        DumbGridBagLayout bl = new DumbGridBagLayout()
                .addLine("[<=-enable][<fil][<loc]")
                ;
        if (modules != null) {
            for (int i = 0; i < modules.length; i++) {
                bl.addLine("[<=-module" + i + " . ]");
            }
        }
        return bl
                .setInsets(".*", new Insets(3, 3, 3, 3))
                .setInsets("module.*", new Insets(2, 40, 2, 2));
    }


    public void load(ConfigNode _target) {
        if (_target != null) {
            ConfigNode _dto_module = _target.getChild(getTagId(), false);
            if (_dto_module != null) {
                enable.setSelected(_dto_module.isEnabled());
                ConfigNode child = _dto_module.getChild("root-path", false);
                changeLocationButton.root_path.setFile(child == null ? null : child.getValue() == null ? null : new File(child.getValue()));
                if (filter) {
                    changeFilterButton.load(_dto_module);
                }
                if (modules != null) {
                    for (NSettingPanel module : modules) {
                        module.load(_dto_module);
                    }
                }
            }else{
                enable.setSelected(false);
            }
        }else{
            changeLocationButton.root_path.setFile(new File("src"));
        }
    }

    public void store(ConfigNode _parent) {
        if (enable.isSelected()) {
            ConfigNode _dto_module = NUtils.findChild(_parent,getTagId(),false,true,NUtils.NotFoundAction.NOTHING);
            _dto_module.setEnabled(true);
            NUtils.storeValue(NUtils.findChild(_dto_module,"root-path",NUtils.NotFoundAction.DELETE),changeLocationButton.root_path,"",true);
            if (filter) {
                changeFilterButton.store(_dto_module);
            }
            if (modules != null) {
                for (NSettingPanel module : modules) {
                    module.store(_dto_module);
                }
            }
        } else {
            ConfigNode _dto_module = _parent.getChild(getTagId(), false);
            if (_dto_module != null) {
                _dto_module.setEnabled(false);
            }

        }
    }

}
