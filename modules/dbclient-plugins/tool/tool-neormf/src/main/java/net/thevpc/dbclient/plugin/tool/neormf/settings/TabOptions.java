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

import net.thevpc.common.swing.layout.DumbGridBagLayout;
import net.thevpc.dbclient.plugin.tool.neormf.NUtils;
import org.vpc.neormf.jbgen.config.ConfigNode;
import org.vpc.neormf.jbgen.config.ConfigFilter;

import javax.swing.*;
import java.awt.*;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 20 avr. 2007 03:15:53
 */
class TabOptions extends NSettingPanel {
    JCheckBox info_if_found_table_file;
    JCheckBox warn_if_missing_table_file;
    JCheckBox info_if_found_do_file;
    JCheckBox warn_if_missing_do_file;
    JCheckBox info_if_found_bo_file;
    JCheckBox warn_if_missing_bo_file;
    JCheckBox option_user_code_comments_if_found;
    JCheckBox option_user_code_comments_if_not_found;
    JCheckBox option_entity_optimize_getAll;
    JCheckBox option_check_queries;
    JCheckBox accept_column_names;
    JCheckBox entity_optimize_getAll;
    JCheckBox[] opt;
    JCheckBox[] logs;

    public TabOptions(NeormfSettingsComponent neormfSettingsComponent) {
        super("options", "Options", neormfSettingsComponent);
        setLayout(new DumbGridBagLayout()
                .addLine("[<$=+Log][<$=+Misc]")
                .setInsets(".*", new Insets(3, 3, 3, 3))
        );
        info_if_found_table_file = new JCheckBox();
        info_if_found_table_file.setName("info-if-found-table-file");
        info_if_found_table_file.setText("info-if-found-table-file");
        info_if_found_table_file.setToolTipText("Trace info if the *.table.xml file does exist");
        info_if_found_table_file.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        info_if_found_table_file.setMargin(new java.awt.Insets(0, 0, 0, 0));

        warn_if_missing_table_file = new JCheckBox();
        warn_if_missing_table_file.setName("warn-if-missing-table-file");
        warn_if_missing_table_file.setText("warn-if-missing-table-file");
        warn_if_missing_table_file.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        warn_if_missing_table_file.setMargin(new java.awt.Insets(0, 0, 0, 0));

        info_if_found_do_file = new JCheckBox();
        info_if_found_do_file.setName("info-if-found-do-file");
        info_if_found_do_file.setText("info-if-found-do-file");
        info_if_found_do_file.setToolTipText("Trace info if the *.do.xml file does exist");
        info_if_found_do_file.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        info_if_found_do_file.setMargin(new java.awt.Insets(0, 0, 0, 0));

        warn_if_missing_do_file = new JCheckBox();
        warn_if_missing_do_file.setName("warn-if-missing-do-file");
        warn_if_missing_do_file.setText("warn-if-missing-do-file");
        warn_if_missing_do_file.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        warn_if_missing_do_file.setMargin(new java.awt.Insets(0, 0, 0, 0));

        info_if_found_bo_file = new JCheckBox();
        info_if_found_bo_file.setName("info-if-found-bo-file");
        info_if_found_bo_file.setText("info-if-found-bo-file");
        info_if_found_bo_file.setToolTipText("Trace info if the *.bo.xml file does exist");
        info_if_found_bo_file.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        info_if_found_bo_file.setMargin(new java.awt.Insets(0, 0, 0, 0));

        warn_if_missing_bo_file = new JCheckBox();
        warn_if_missing_bo_file.setName("warn-if-missing-bo-file");
        warn_if_missing_bo_file.setText("warn-if-missing-bo-file");
        warn_if_missing_bo_file.setToolTipText("Trace info if the *.bo.xml file does not exist");
        warn_if_missing_bo_file.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        warn_if_missing_bo_file.setMargin(new java.awt.Insets(0, 0, 0, 0));

        option_user_code_comments_if_found = new JCheckBox();
        option_user_code_comments_if_found.setName("option-user-code-comments-if-found");
        option_user_code_comments_if_found.setText("option-user-code-comments-if-found");
        option_user_code_comments_if_found.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        option_user_code_comments_if_found.setMargin(new java.awt.Insets(0, 0, 0, 0));

        option_user_code_comments_if_not_found = new JCheckBox();
        option_user_code_comments_if_not_found.setName("option-user-code-comments-if-not-found");
        option_user_code_comments_if_not_found.setText("option-user-code-comments-if-not-found");
        option_user_code_comments_if_not_found.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        option_user_code_comments_if_not_found.setMargin(new java.awt.Insets(0, 0, 0, 0));

        option_entity_optimize_getAll= new JCheckBox();
        option_entity_optimize_getAll.setName("option-entity-optimize-getAll");
        option_entity_optimize_getAll.setText("option-entity-optimize-getAll");
        option_entity_optimize_getAll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        option_entity_optimize_getAll.setMargin(new java.awt.Insets(0, 0, 0, 0));

        logs = new JCheckBox[]{
                info_if_found_table_file, warn_if_missing_table_file, info_if_found_do_file, warn_if_missing_do_file, info_if_found_bo_file, warn_if_missing_bo_file
        };

        JPanel p = new JPanel(new GridLayout(-1, 1,3,3));
        for (JCheckBox c : logs) {
            p.add(c);

        }
        p.setBorder(BorderFactory.createTitledBorder("JBGen Console Options"));
        this.add(p, "Log");

        p = new JPanel(new GridLayout(-1, 1,3,3));
        option_check_queries = new JCheckBox();
        option_check_queries.setName("option-check-queries");
        option_check_queries.setText("option-check-queries");
        option_check_queries.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        option_check_queries.setMargin(new Insets(0, 0, 0, 0));
        entity_optimize_getAll = new JCheckBox();
        entity_optimize_getAll.setName("option-entity-optimize-getAll");
        entity_optimize_getAll.setText("option-entity-optimize-getAll");
        entity_optimize_getAll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        entity_optimize_getAll.setMargin(new Insets(0, 0, 0, 0));
        accept_column_names = new JCheckBox();
        accept_column_names.setName("option-accept-column-names");
        accept_column_names.setText("option-accept-column-names");
        accept_column_names.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        accept_column_names.setMargin(new Insets(0, 0, 0, 0));
        opt = new JCheckBox[]{
                option_check_queries, entity_optimize_getAll, accept_column_names,
                option_user_code_comments_if_found,
                option_user_code_comments_if_not_found,
                option_entity_optimize_getAll
        };
        p.setBorder(BorderFactory.createTitledBorder("Misc..."));
        for (JCheckBox c : opt) {
            p.add(c);
        }

        this.add(p, "Misc");
    }

    public void load(ConfigNode _target) {
        if (_target != null) {
            ConfigNode _dto_module = _target.getChild(getTagId(), false);
            if (_dto_module != null) {
                for (int i = 0; i < logs.length; i++) {
                    ConfigNode[] configNodes = _dto_module.getChildren("option-log<name=\"" + logs[i].getName() + "\">");
                    if (configNodes.length > 0) {
                        logs[i].setSelected(configNodes[0].isEnabled());
                    }
                }
                for (JCheckBox c : opt) {
                    ConfigNode cq = _dto_module.getChild(c.getName(), false);
                    c.setSelected(cq != null && cq.isEnabled());
                }
            } else {
                option_check_queries.setSelected(true);
                for (int i = 0; i < logs.length; i++) {
                    logs[i].setSelected(true);
                }
                for (JCheckBox c : opt) {
                    c.setSelected(true);
                }
            }
        }else{
            option_check_queries.setSelected(true);
            for (int i = 0; i < logs.length; i++) {
                logs[i].setSelected(true);
            }
            for (JCheckBox c : opt) {
                c.setSelected(true);
            }
        }
    }

    public void store(ConfigNode _parent) {
        if(_parent==null){
            return;
        }
        ConfigNode _dto_module = NUtils.findChild(_parent,getTagId(),NUtils.NotFoundAction.DELETE);
        for (JCheckBox c : opt) {
            ConfigNode cq = NUtils.findChild(_dto_module,c.getName(),false,true,NUtils.NotFoundAction.DELETE);
            cq.setEnabled(c.isSelected());
        }

        for (int i = 0; i < logs.length; i++) {
            ConfigNode[] configNodes = _dto_module.getChildren(ConfigFilter.valueOf("option-log<name=\"" + logs[i].getName() + "\">",false,false));
            ConfigNode child;
            if (configNodes.length > 1) {
                for (int j = 1; j < configNodes.length; j++) {
                    _dto_module.remove(configNodes[j]);
                }
                child = configNodes[0];
            }else if (configNodes.length > 0) {
                child = configNodes[0];
            } else {
                child = new ConfigNode("option-log");
                child.setName(logs[i].getName());
                _dto_module.add(child);
            }
            child.setEnabled(logs[i].isSelected());
        }
    }

    public void setEnabledComponent(boolean enabled) {
        for (JCheckBox c : opt) {
            c.setEnabled(enabled);
        }
        for (int i = 0; i < logs.length; i++) {
            logs[i].setEnabled(enabled);
        }
    }
}
