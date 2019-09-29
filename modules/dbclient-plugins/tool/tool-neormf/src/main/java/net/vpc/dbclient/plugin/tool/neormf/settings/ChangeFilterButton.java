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

import org.vpc.neormf.jbgen.config.ConfigNode;
import net.vpc.swingext.DumbGridBagLayout;
import net.vpc.dbclient.plugin.tool.neormf.NUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 21 avr. 2007 12:10:39
 */
class ChangeFilterButton extends JButton {
    NSettingPanel panel;
    JTextField includeName = new JTextField("*");
    JTextField excludeName = new JTextField("");
    JTextField includeType = new JTextField("*");
    JTextField excludeType = new JTextField("");

    public ChangeFilterButton(NSettingPanel panel) {
        super(panel.getPluginSession().getIconSet().getIconR("NeormfFilter"));
        this.panel = panel;
        this.setToolTipText("Change Filter");
        this.setMargin(new Insets(0, 0, 0, 0));
        this.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeFilter();
            }
        });
    }

    public void changeFilter() {

        JPanel p = new JPanel();
        p.setLayout(new DumbGridBagLayout()
                .addLine("[<includeNameLabel][<-=includeName]")
                .addLine("[<includeTypeLabel][<-=includeType]")
                .addLine("[<excludeNameLabel][<-=excludeName]")
                .addLine("[<excludeTypeLabel][<-=excludeType]")
                .setInsets(".*", new Insets(3, 3, 3, 3))
        );
        p.add(new JLabel("Include Type"), "includeTypeLabel");
        p.add(includeType, "includeType");
        p.add(new JLabel("Include Name"), "includeNameLabel");
        p.add(includeName, "includeName");
        p.add(new JLabel("Exclude Type"), "excludeTypeLabel");
        p.add(excludeType, "excludeType");
        p.add(new JLabel("Exclude Name"), "excludeNameLabel");
        p.add(excludeName, "excludeName");

        int x = JOptionPane.showConfirmDialog(this, p, "Change " + panel.getTagTitle() + " Filter", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (x != JOptionPane.OK_OPTION) {
            //root_path.setFile(f);
        }
    }

    public void load(ConfigNode node) {
        if (node == null) {
            return;
        }
        ConfigNode child = NUtils.findChild(node,"include", true,false, NUtils.NotFoundAction.DISABLE);
        includeName.setText(child == null ? "*" : child.getName());
        includeType.setText(child == null ? "*" : child.getAttribute("type"));
        child = NUtils.findChild(node,"exclude", true,false, NUtils.NotFoundAction.DISABLE);
        excludeName.setText(child == null ? null : child.getName());
        excludeType.setText(child == null ? null : child.getAttribute("type"));
    }

    public void store(ConfigNode node) {
        if (includeName.getText().equals("*") && includeType.getText().equals("*")) {
            node.getChildOrCreateIt("include").remove();
        } else {
            node.getChildOrCreateIt("include").setName(includeName.getText());
            node.getChildOrCreateIt("include").setAttribute("type", includeType.getText());
        }
        if (excludeName.getText().equals("") && excludeType.getText().equals("")) {
            node.getChildOrCreateIt("exclude").remove();
        } else {
            node.getChildOrCreateIt("exclude").setName(excludeName.getText());
            node.getChildOrCreateIt("exclude").setAttribute("type", excludeType.getText());
        }
    }

}
