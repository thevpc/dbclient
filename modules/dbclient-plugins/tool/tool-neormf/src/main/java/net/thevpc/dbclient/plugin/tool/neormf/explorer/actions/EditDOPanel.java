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

package net.thevpc.dbclient.plugin.tool.neormf.explorer.actions;

import net.thevpc.common.swing.layout.DumbGridBagLayout;
import net.thevpc.dbclient.plugin.tool.neormf.NUtils;
import net.thevpc.dbclient.plugin.tool.neormf.NeormfPluginSession;
import net.thevpc.dbclient.plugin.tool.neormf.explorer.NNode;
import org.vpc.neormf.jbgen.config.ConfigNode;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 22 avr. 2007 18:33:44
 */
public class EditDOPanel extends JPanel {
    JTabbedPane tabbedPane;
    JComboBox fields;
    JComboBox bos;
    JTextField sequence;
    JTextField doName;
    JTextArea preInsert;
    JTextArea postInsert;
    JTextArea preUpdate;
    JTextArea postUpdate;
    JTextArea preDelete;
    JTextArea postDelete;
    NNode node;
    ConfigNode doConfigNode;
    ConfigNode rootConfigNode;
    NeormfPluginSession neormfPluginSession;
    boolean virtual;

    public EditDOPanel(NNode node, boolean virtual, ConfigNode doConfigNode, ConfigNode rootConfigNode, NeormfPluginSession neormfPluginSession) {
        super(new BorderLayout());
        this.node = node;
        this.virtual = virtual;
        this.doConfigNode = doConfigNode;
        this.rootConfigNode = rootConfigNode;
        this.neormfPluginSession = neormfPluginSession;
        tabbedPane = new JTabbedPane();
//            pane.setTabPlacement(JTabbedPane.LEFT);
        Dimension jspd = new Dimension(400, 100);
        Insets insets = new Insets(3, 3, 3, 3);

        JPanel props = new JPanel(new DumbGridBagLayout()
                .addLine("[<~doL][<=-do]")
                .addLine("[<=boL][<=-bo]")
                .addLine("[<=titleFieldL][<=-titleField]")
                .setInsets(".*", insets)
        );
        doName = new JTextField();
        doName.setEditable(node == null);
        doName.setText(node == null ? "" : node.getName());
        bos = new JComboBox();
        bos.addItem("");
        for (String s : neormfPluginSession.getBONames()) {
            bos.addItem(s);
        }
        fields = new JComboBox();
        fields.addItem("");
        int max = node == null ? 0 : node.size();
        for (int i = 0; i < max; i++) {
            NNode n = node.getChild(i);
            switch (n.getType()) {
                case FIELD: {
                    fields.addItem(n.getName());
                    break;
                }
            }
        }
        sequence = new JTextField();
        props.add(new JLabel("Title Field Name"), "titleFieldL");
        props.add(fields, "titleField");
        props.add(new JLabel("Business Object"), "boL");
        props.add(bos, "bo");
        props.add(new JLabel("DO Name"), "doL");
        props.add(doName, "do");

        tabbedPane.addTab("Properties", new JScrollPane(props));

        JPanel insert = new JPanel(new DumbGridBagLayout()
                .addLine("[<=sequenceL][<=-sequence]")
                .addLine("[<L1 : ]")
                .addLine("[<$+=C1 : ]")
                .addLine("[<L2 : ]")
                .addLine("[<$+=C2 : ]")
                .setInsets(".*", insets)
        );
        JScrollPane jsp;
        preInsert = new JTextArea();
        jsp = new JScrollPane(preInsert);
        jsp.setPreferredSize(jspd);
        insert.add(new JLabel("PreInsert Code"), "L1");
        insert.add(jsp, "C1");
        postInsert = new JTextArea();
        jsp = new JScrollPane(postInsert);
        jsp.setPreferredSize(jspd);
        insert.add(new JLabel("PostInsert Code"), "L2");
        insert.add(jsp, "C2");
        insert.add(new JLabel("Sequence Name"), "sequenceL");
        insert.add(sequence, "sequence");
        tabbedPane.addTab("Insert", new JScrollPane(insert));

        JPanel update = new JPanel(new DumbGridBagLayout()
                .addLine("[<L1]")
                .addLine("[<$+=C1]")
                .addLine("[<L2]")
                .addLine("[<$+=C2]")
                .setInsets(".*", insets)
        );
        preUpdate = new JTextArea();
        jsp = new JScrollPane(preUpdate);
        jsp.setPreferredSize(jspd);
        update.add(new JLabel("PreUpdate Code"), "L1");
        update.add(jsp, "C1");
        postUpdate = new JTextArea();
        jsp = new JScrollPane(postUpdate);
        jsp.setPreferredSize(jspd);
        update.add(new JLabel("PostUpdate Code"), "L2");
        update.add(jsp, "C2");
        tabbedPane.addTab("Update", new JScrollPane(update));

        JPanel delete = new JPanel(new DumbGridBagLayout()
                .addLine("[<L1]")
                .addLine("[<$+=C1]")
                .addLine("[<L2]")
                .addLine("[<$+=C2]")
                .setInsets(".*", insets)
        );
        preDelete = new JTextArea();
        jsp = new JScrollPane(preDelete);
        jsp.setPreferredSize(jspd);
        delete.add(new JLabel("PreDelete Code"), "L1");
        delete.add(jsp, "C1");
        postDelete = new JTextArea();
        jsp = new JScrollPane(postDelete);
        jsp.setPreferredSize(jspd);
        delete.add(new JLabel("PostDelete Code"), "L2");
        delete.add(jsp, "C2");
        tabbedPane.addTab("Delete", new JScrollPane(delete));
        this.add(tabbedPane, BorderLayout.CENTER);
        tabbedPane.setEnabledAt(1,!virtual);
        tabbedPane.setEnabledAt(2,!virtual);
        tabbedPane.setEnabledAt(3,!virtual);
    }

    public void load() {
        ConfigNode[] virtualDos = rootConfigNode.getChildren("source.user-objects.do<name=\"" + doName.getText() + "\">");
        virtual = virtualDos.length > 0;
        NUtils.loadAttr(doConfigNode, sequence, "sequence", "");
        NUtils.loadAttr(doConfigNode, fields, "titleField", "");
        NUtils.loadAttr(doConfigNode , bos, "bo", "");
        preInsert.setText("");
        postInsert.setText("");
        preUpdate.setText("");
        postUpdate.setText("");
        preDelete.setText("");
        postDelete.setText("");
        if (doConfigNode != null) {
            for (ConfigNode configNode : doConfigNode.getChildren("userCode.preInsert")) {
                preInsert.setText(configNode.getValue());
                break;
            }
            for (ConfigNode configNode : doConfigNode.getChildren("userCode.postInsert")) {
                postInsert.setText(configNode.getValue());
                break;
            }
            for (ConfigNode configNode : doConfigNode.getChildren(("userCode.preUpdate"))) {
                preUpdate.setText(configNode.getValue());
                break;
            }
            for (ConfigNode configNode : doConfigNode.getChildren(("userCode.postUpdate"))) {
                postUpdate.setText(configNode.getValue());
                break;
            }
            for (ConfigNode configNode : doConfigNode.getChildren(("userCode.preDelete"))) {
                preDelete.setText(configNode.getValue());
                break;
            }
            for (ConfigNode configNode : doConfigNode.getChildren(("userCode.postDelete"))) {
                postDelete.setText(configNode.getValue());
                break;
            }
        }
    }

    public void store() throws IOException {
        if (virtual) {
            ConfigNode[] virtualDos = rootConfigNode.getChildren("source.user-objects.do<name=\"" + doName.getText() + "\">");
            if (virtualDos.length == 0) {
                ConfigNode vsource = rootConfigNode.getChildOrCreateIt("source").getChildOrCreateIt("user-objects");
                ConfigNode vdo = new ConfigNode("do");
                vdo.setName(doName.getText());
                vsource.add(vdo);
            }
        }else{
            ConfigNode[] virtualDos = rootConfigNode.getChildren("source.user-objects.do<name=\"" + doName.getText() + "\">");
            for (ConfigNode virtualDo : virtualDos) {
                virtualDo.remove();
            }
        }
        if (doConfigNode == null) {
            doConfigNode = neormfPluginSession.getDORootNode(doName.getText(), true);
        }
        NUtils.storeAttr(doConfigNode, sequence, "sequence", "");
        NUtils.storeAttr(doConfigNode, fields, "titleField", "");
        NUtils.storeAttr(doConfigNode, bos, "bo", "");
        String[] tags    =new String[]{"preInsert","postInsert","preUpdate","postUpdate","postUpdate","preDelete","postDelete"};
        JTextArea[] areas=new JTextArea[]{ preInsert, postInsert  , preUpdate , postUpdate , postUpdate , preDelete , postDelete};
        ConfigNode userCodeNode = doConfigNode.getChildOrCreateIt("userCode");
        for (int i = 0; i < tags.length; i++) {
            String tag = tags[i];
            JTextArea area = areas[i];
            if(area.getText().trim().length()==0){
                userCodeNode.getChildOrCreateIt(tag).remove();
            }else{
                userCodeNode.getChildOrCreateIt(tag).setValue(area.getText());
            }
        }
        if(userCodeNode.size()==0){
            userCodeNode.remove();
        }
    }
}
