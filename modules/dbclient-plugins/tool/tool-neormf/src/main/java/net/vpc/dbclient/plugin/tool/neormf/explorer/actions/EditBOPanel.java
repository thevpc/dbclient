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

package net.vpc.dbclient.plugin.tool.neormf.explorer.actions;

import net.vpc.dbclient.plugin.tool.neormf.NeormfPluginSession;
import net.vpc.dbclient.plugin.tool.neormf.explorer.NNode;
import org.vpc.neormf.jbgen.config.ConfigNode;
import net.vpc.swingext.DumbGridBagLayout;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 22 avr. 2007 18:33:44
 */
public class EditBOPanel extends JPanel {
    JTabbedPane tabbedPane;
    JTextField boName;
    NNode node;
    ConfigNode boConfigNode;
    ConfigNode rootConfigNode;
    NeormfPluginSession neormfPluginSession;
    boolean virtual;

    public EditBOPanel(NNode node, boolean virtual, ConfigNode doConfigNode, ConfigNode rootConfigNode, NeormfPluginSession neormfPluginSession) {
        super(new BorderLayout());
        this.node = node;
        this.virtual = virtual;
        this.boConfigNode = doConfigNode;
        this.rootConfigNode = rootConfigNode;
        this.neormfPluginSession = neormfPluginSession;
        tabbedPane = new JTabbedPane();
//            pane.setTabPlacement(JTabbedPane.LEFT);
        Insets insets = new Insets(3, 3, 3, 3);

        JPanel props = new JPanel(new DumbGridBagLayout()
                .addLine("[<=boL][<=-bo]")
                .addLine("[<=titleFieldL][<=-titleField]")
                .setInsets(".*", insets)
        );
        boName = new JTextField();
        boName.setEditable(node == null);
        boName.setText(node == null ? "" : node.getName());
        props.add(boName,"bo");
        props.add(new JLabel("Name"),"boL");
        tabbedPane.addTab("Properties", new JScrollPane(props));
        add(tabbedPane,BorderLayout.CENTER);
    }

    public void load() {
        ConfigNode[] virtualBos = rootConfigNode.getChildren("source.user-objects.bo<name=\"" + boName.getText() + "\">");
        virtual = virtualBos.length > 0;
    }

    public void store() throws IOException {
        if (virtual) {
            ConfigNode[] virtualDos = rootConfigNode.getChildren("source.user-objects.bo<name=\"" + boName.getText() + "\">");
            if (virtualDos.length == 0) {
                ConfigNode vsource = rootConfigNode.getChildOrCreateIt("source").getChildOrCreateIt("user-objects");
                ConfigNode vdo = new ConfigNode("bo");
                vdo.setName(boName.getText());
                vsource.add(vdo);
            }
        }else{
            ConfigNode[] virtualDos = rootConfigNode.getChildren("source.user-objects.bo<name=\"" + boName.getText() + "\">");
            for (ConfigNode virtualDo : virtualDos) {
                virtualDo.remove();
            }
        }
        if (boConfigNode == null) {
            boConfigNode = neormfPluginSession.getBORootNode(boName.getText(), true);
        }
    }
}
