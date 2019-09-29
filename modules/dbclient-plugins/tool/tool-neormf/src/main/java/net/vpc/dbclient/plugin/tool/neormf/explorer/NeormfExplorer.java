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

package net.vpc.dbclient.plugin.tool.neormf.explorer;

import net.vpc.dbclient.api.viewmanager.DBCTree;
import net.vpc.dbclient.plugin.tool.neormf.NeormfPluginSession;
import net.vpc.dbclient.plugin.tool.neormf.explorer.actions.*;
import org.vpc.neormf.jbgen.config.ConfigNode;
import net.vpc.swingext.DumbGridBagLayout;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.logging.Level;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 21 avr. 2007 16:28:42
 */
public class NeormfExplorer extends JPanel {
    public JComboBox elements;
    public DBCTree tree;
    public NeormfPluginSession pluginSession;

    public NeormfExplorer(NeormfPluginSession _plugin) {
        this.pluginSession = _plugin;
        setLayout(new BorderLayout());
        elements = new JComboBox(new Object[]{NNode.Type.TABLE, NNode.Type.DO, NNode.Type.BO, NNode.Type.BDLG});
        Font oldFont = elements.getFont();
        elements.setFont(oldFont.deriveFont(Font.PLAIN, oldFont.getSize() * 0.9f));
        elements.setRenderer(new DefaultListCellRenderer() {

            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                NNode.Type n = (NNode.Type) value;
                switch (n) {
                    case TABLE: {
                        value = pluginSession.getMessageSet().get("Explorer.Group.Tables");
                        break;
                    }
                    case DO: {
                        value = pluginSession.getMessageSet().get("Explorer.Group.DataObjects");
                        break;
                    }
                    case BO: {
                        value = pluginSession.getMessageSet().get("Explorer.Group.BusinessObjects");
                        break;
                    }
                    case BDLG: {
                        value = pluginSession.getMessageSet().get("Explorer.Group.BusinessDelegateObjects");
                        break;
                    }
                }
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                return this;
            }
        });
        elements.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    NNode.Type i = (NNode.Type) e.getItem();
                    updateModel(i);
                }
            }
        });
        tree = pluginSession.getSession().getFactory().newInstance(DBCTree.class);
        JPopupMenu jPopupMenu = createPopupMenu();
        tree.setComponentPopupMenu(jPopupMenu);
        jPopupMenu.addPopupMenuListener(new PopupMenuListener() {
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                JPopupMenu m = (JPopupMenu) e.getSource();
                int count = m.getComponentCount();
                for (int i = 0; i < count; i++) {
                    Component c = m.getComponent(i);
                    if (c instanceof JMenuItem) {
                        Action a = ((JMenuItem) c).getAction();
                        if (a instanceof NAction) {
                            ((NAction) a).revalidate();
                        }
                        c.setVisible(c.isEnabled());
                    }
                }
            }

            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                //
            }

            public void popupMenuCanceled(PopupMenuEvent e) {
                //
            }
        });
        JPanel header = new JPanel(new DumbGridBagLayout()
                .addLine("[<==-combo]").setInsets(".*", new Insets(5, 5, 5, 5))
        );
        header.add(elements, "combo");
        add(header, BorderLayout.PAGE_START);
        add(new JScrollPane(tree.getComponent()), BorderLayout.CENTER);
        tree.setCellRenderer(new NNodeCellRenderer(this));
        String s = pluginSession.getSession().getConfig().getStringProperty("Neormf.explorer.selected", null);
        if (s != null) {
            try {
                elements.setSelectedItem(NNode.Type.valueOf(s));
            } catch (IllegalArgumentException e) {
                //
            }
        }
        updateModel(null);
    }

    public JPopupMenu createPopupMenu() {
        JPopupMenu p = new JPopupMenu();
        p.add(new NEditConfigurationAction(this));
        p.add(new NExcludeAction(this));
        p.add(new NGroupAction(this));
        p.add(new NAddFieldAction(this));
        p.add(new NAddDOAction(this));
        p.add(new NAddBOAction(this));
        p.add(new NRemoveAction(this));
        p.add(new NCustomizeFieldAction(this));
        return p;
    }

    public void updateModel() {
        TreePath[] treePaths = tree.getSelectionPaths();
        Enumeration<TreePath> expandedDescendants = tree.getExpandedDescendants(new TreePath(tree.getModel().getRoot()));
        updateModel(null);
        if (expandedDescendants != null) {
            while (expandedDescendants.hasMoreElements()) {
                TreePath treePath = expandedDescendants.nextElement();
                tree.expandPath(treePath);
            }
        }
        if (treePaths != null) {
            for (TreePath treePath : treePaths) {
                tree.setSelectionPath(treePath);
            }
        }

    }

    public void updateModel(NNode.Type type) {
        if (type == null) {
            type = (NNode.Type) elements.getSelectedItem();
        }
        ConfigNode configNode = null;
        try {
            configNode = ConfigNode.load(new File(pluginSession.getValidProjectFolder(), "jbgen-output.xml"));
        } catch (Throwable e) {
            pluginSession.getSession().getLogger(NeormfExplorer.class.getName()).log(Level.SEVERE,"Unable to load config",e);
        }
        switch (type) {
            case TABLE: {
                tree.setModel(new NNodeTreeModel(loadTables(configNode)));
                break;
            }
            case DO: {
                tree.setModel(new NNodeTreeModel(loadDOs(configNode)));
                break;
            }
            case BO: {
                tree.setModel(new NNodeTreeModel(loadBOs(configNode)));
                break;
            }
            case BDLG: {
                tree.setModel(new NNodeTreeModel(loadBDLGs(configNode)));
                break;
            }
        }

        pluginSession.getConfig().setStringProperty("Neormf.explorer.selected", type.toString());
    }

    public NNode loadTables(ConfigNode configNode) {
        ConfigNode node = (configNode == null) ? null : configNode.getChild("table-elements", false);
        NNode root = new NNode(NNode.Type.ROOT_TABLE, pluginSession.getMessageSet().get("Explorer.Group.Tables"), null);
        NNode conf = new NNode(NNode.Type.CONFIG, "Config", null);
        if (node == null) {
            node = new ConfigNode("");
        } else {
            ConfigNode doFile = pluginSession.getDORootNode(node.getName(), false);
            if (doFile != null) {
                ConfigNode[] configNodes = doFile.getChildren("object-definition.do.field");
                configNodes = node.getChildren();
                Arrays.sort(configNodes, CONFIG_NODE_COMPARATOR);
                for (ConfigNode fs : configNodes) {
                    conf.add(new NNode(NNode.Type.CONFIG_FIELD, fs.getName(), fs));
                }
            }
        }


        root.add(conf);
        for (ConfigNode c : node.getChildren()) {
            root.add(createNodeForTable(c, configNode));
        }
        return root;
    }

    public NNode loadDOs(ConfigNode configNode) {
        ConfigNode node = (configNode == null) ? null : configNode.getChild("do-elements", false);
        if (node == null) {
            node = new ConfigNode("");
        }
        NNode root = new NNode(NNode.Type.ROOT_DO, pluginSession.getMessageSet().get("Explorer.Group.DataObjects"), null);
        NNode conf = new NNode(NNode.Type.CONFIG, pluginSession.getMessageSet().get("Explorer.Group.Config"), null);
        ConfigNode[] vdo = pluginSession.getRootConfigNode(true).getChildren("source.user-objects.do");
        Arrays.sort(vdo, CONFIG_NODE_COMPARATOR);
        for (ConfigNode cn : vdo) {
            conf.add(new NNode(NNode.Type.CONFIG_DO, cn.getName(), cn));
        }
        root.add(conf);
        vdo = node.getChildren();
        Arrays.sort(vdo, CONFIG_NODE_COMPARATOR);
        for (ConfigNode c : vdo) {
            root.add(createNodeForDO(c, configNode));
        }
        return root;
    }

    private static ConfigNodeComparator CONFIG_NODE_COMPARATOR = new ConfigNodeComparator();

    private static final class ConfigNodeComparator implements Comparator<ConfigNode> {

        public int compare(ConfigNode o1, ConfigNode o2) {
            String n1 = o1.getName();
            String n2 = o2.getName();
            return (n1 == null ? "" : n1).compareTo(n2 == null ? "" : n2);
        }
    }

    public NNode loadBOs(ConfigNode configNode) {
        ConfigNode node = (configNode == null) ? null : configNode.getChild("bo-elements", false);
        if (node == null) {
            node = new ConfigNode("");
        }
        NNode root = new NNode(NNode.Type.ROOT_BO, pluginSession.getMessageSet().get("Explorer.Group.BusinessObjects"), null);
        NNode conf = new NNode(NNode.Type.CONFIG, pluginSession.getMessageSet().get("Explorer.Group.Config"), null);
        ConfigNode[] vdo = pluginSession.getRootConfigNode(true).getChildren("source.user-objects.bo");
        Arrays.sort(vdo, CONFIG_NODE_COMPARATOR);
        for (ConfigNode cn : vdo) {
            conf.add(new NNode(NNode.Type.CONFIG_BO, cn.getName(), cn));
        }
        root.add(conf);
        vdo = node.getChildren();
        Arrays.sort(vdo, CONFIG_NODE_COMPARATOR);
        for (ConfigNode c : vdo) {
            root.add(createNodeForBO(c, configNode));
        }
        return root;
    }

    public NNode createNodeForTable(ConfigNode node, ConfigNode outputNode) {
        return new NNode(NNode.Type.TABLE, node.getName(), node);
    }

    public NNode createNodeForDO(ConfigNode node, ConfigNode outputNode) {
        NNode doNode = new NNode(NNode.Type.DO, node.getName(), node);
        NNode conf = new NNode(NNode.Type.CONFIG, pluginSession.getMessageSet().get("Explorer.Group.Config"), null);
        doNode.add(conf);
        ConfigNode doFile = pluginSession.getDORootNode(node.getName(), false);
        if (doFile != null) {
            ConfigNode[] configNodes = doFile.getChildren("object-definition.do.field");
            Arrays.sort(configNodes, CONFIG_NODE_COMPARATOR);
            for (ConfigNode fs : configNodes) {
                conf.add(new NNode(NNode.Type.CONFIG_FIELD, fs.getName(), fs));
            }
        }
        ConfigNode tabs = (outputNode == null) ? null : outputNode.getChild("table-elements", false);

        ConfigNode[] configNodes = tabs.getChildren("table<do=" + node.getName() + ">");
        Arrays.sort(configNodes, CONFIG_NODE_COMPARATOR);
        for (ConfigNode t : configNodes) {
            doNode.add(createNodeForTable(t, outputNode));
        }
        ConfigNode[] fieldNodes = node.getChildren("field");
        Arrays.sort(configNodes, CONFIG_NODE_COMPARATOR);
        for (ConfigNode fs : fieldNodes) {
            doNode.add(new NNode(NNode.Type.FIELD, fs.getName(), fs));
        }
        return doNode;
    }

    public NNode createNodeForBO(ConfigNode node, ConfigNode outputNode) {
        NNode doNode = new NNode(NNode.Type.BO, node.getName(), node);
        NNode conf = new NNode(NNode.Type.CONFIG, pluginSession.getMessageSet().get("Explorer.Group.Config"), null);
        doNode.add(conf);
//        ConfigNode doFile = plugin.getBOFile(node.getName(), session,false);
//        if (doFile != null) {
//            ConfigNode[] configNodes = doFile.getChildren(ConfigFilter.valueOf("object-definition.do.field"));
//            for (ConfigNode fs : configNodes) {
//                conf.add(new NNode(NNode.Type.FIELD, fs.getName(), fs));
//            }
//        }
        ConfigNode tabs = (outputNode == null) ? null : outputNode.getChild("do-elements", false);
        ConfigNode[] configNodes = tabs.getChildren("do<bo=" + node.getName() + ">");
        Arrays.sort(configNodes, CONFIG_NODE_COMPARATOR);
        for (ConfigNode t : configNodes) {
            doNode.add(createNodeForDO(t, outputNode));
        }
        return doNode;
    }

    public NNode createNodeForBDLGs(ConfigNode node, ConfigNode outputNode) {
        NNode doNode = new NNode(NNode.Type.BDLG, node.getName(), node);
        return doNode;
    }


    public NNode loadBDLGs(ConfigNode configNode) {
        ConfigNode node = (configNode == null) ? null : configNode.getChild("bdlg-elements", false);
        if (node == null) {
            node = new ConfigNode("");
        }
        NNode root = new NNode(NNode.Type.ROOT_BDLG, pluginSession.getMessageSet().get("Explorer.Group.BusinessDelegateObjects"), null);
        NNode conf = new NNode(NNode.Type.CONFIG, pluginSession.getMessageSet().get("Explorer.Group.Config"), null);
        root.add(conf);
        ConfigNode[] configNodes = node.getChildren();
        Arrays.sort(configNodes, CONFIG_NODE_COMPARATOR);
        for (ConfigNode c : configNodes) {
            root.add(createNodeForBDLGs(c, configNode));
        }
        return root;
    }

    public String getViewType() {
        return (String) elements.getSelectedItem();
    }

    public NNode[] getSelectedNodes() {
        TreePath[] treePaths = tree.getSelectionPaths();
        NNode[] all = new NNode[treePaths == null ? 0 : treePaths.length];
        for (int i = 0; i < all.length; i++) {
            all[i] = (NNode) treePaths[i].getLastPathComponent();
        }
        return all;
    }

}
