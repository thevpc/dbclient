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

import net.vpc.dbclient.plugin.tool.neormf.NeormfPluginSession;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 20 avr. 2007 03:23:31
 */
class ProjectTypeConfigPanel extends JPanel {
    ProjectTypePanel target_choice_javaz;
    ProjectTypePanel target_choice_java;
    ProjectTypePanel target_choice_j2ee;
    ProjectTypePanel target_choice_csharp;
    ButtonGroup targetButtonGroup;
    NeormfSettingsComponent neormfSettingsComponent;
    private JTabbedPane tabbedPane;
    public ProjectTypeConfigPanel(NeormfSettingsComponent neormfSettingsComponent) {
        this.neormfSettingsComponent = neormfSettingsComponent;
        setLayout(new BorderLayout());
        targetButtonGroup = new ButtonGroup();
        tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
        {
            JPanel projects = new JPanel(new GridLayout(-1, 1));
            Icon j2eeIcon = neormfSettingsComponent.getPluginSession().getIconSet().getIconR("NeormfJ2EE");
            Icon javaIcon = neormfSettingsComponent.getPluginSession().getIconSet().getIconR("NeormfJava");
            target_choice_javaz = new ProjectTypePanel("java", "Simple Java (No Dependencies)", "DAO and DTO generation for each Table. No dependencies with neormf-commons.jar", javaIcon, targetButtonGroup);
            projects.add(target_choice_javaz);
            target_choice_java = new ProjectTypePanel("java", "Java", "DAO and DTO generation for each Table", javaIcon, targetButtonGroup);
            projects.add(target_choice_java);
            target_choice_j2ee = new ProjectTypePanel("j2ee", "J2EE", "DAO,DTO, Ejb Entity, EJB Session generation", j2eeIcon, targetButtonGroup);
            projects.add(target_choice_j2ee);
            target_choice_javaz.button.setSelected(true);
            projects.setBorder(BorderFactory.createTitledBorder("Project Type"));

            tabbedPane.addTab("Java/J2EE",projects);
        }
        {
            JPanel projects = new JPanel(new GridLayout(-1, 1));
            Icon dotnetIcon = neormfSettingsComponent.getPluginSession().getIconSet().getIconR("NeormfCSharp");
            target_choice_csharp = new ProjectTypePanel("csharp_dao_target", "C#.Net", "DAO and DTO generation for each Table", dotnetIcon, targetButtonGroup);
            projects.add(target_choice_csharp);
            target_choice_javaz.button.setSelected(true);
            projects.setBorder(BorderFactory.createTitledBorder("Project Type"));

            tabbedPane.addTab("CSharp .Net",projects);
        }
        this.add(tabbedPane, BorderLayout.CENTER);
        ItemListener il = new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                ProjectTypeConfigPanel.this.firePropertyChange("TargetChanged",false,true);
            }
        };
        target_choice_csharp.button.addItemListener(il);
        target_choice_java.button.addItemListener(il);
        target_choice_javaz.button.addItemListener(il);
        target_choice_j2ee.button.addItemListener(il);
    }
    public String getTarget(){
        if (target_choice_csharp.button.isSelected()) {
            return ("csharp_dao_target");
        } else if (target_choice_javaz.button.isSelected()) {
            return ("java_dao_zerolib_target");
        } else if (target_choice_java.button.isSelected()) {
            return ("java_dao_target");
        } else if (target_choice_j2ee.button.isSelected()) {
            return ("j2ee_target");
        }else{
            return null;
        }
    }

    public void setTarget(String target){
        boolean csharp_dao_target_selected = "csharp_dao_target".equals(target);
        boolean j2ee_target_selected = "j2ee_target".equals(target);
        boolean java_dao_target_select = "java_dao_target".equals(target);
        boolean java_dao_zerolib_target_select = "java_dao_zerolib_target".equals(target);
        target_choice_csharp.button.setSelected(csharp_dao_target_selected);
        target_choice_j2ee.button.setSelected(j2ee_target_selected);
        target_choice_java.button.setSelected(java_dao_target_select);
        target_choice_javaz.button.setSelected(java_dao_zerolib_target_select);
        if(csharp_dao_target_selected){
            tabbedPane.setSelectedIndex(1);
        }else{
            tabbedPane.setSelectedIndex(0);
        }
    }

}
