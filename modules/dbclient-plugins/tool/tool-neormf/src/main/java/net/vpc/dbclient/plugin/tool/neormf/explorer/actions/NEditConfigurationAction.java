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

import net.vpc.dbclient.plugin.tool.neormf.explorer.NNode;
import net.vpc.dbclient.plugin.tool.neormf.explorer.NeormfExplorer;
import org.vpc.neormf.jbgen.config.ConfigNode;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.util.logging.Level;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 22 avr. 2007 15:11:13
 */
public class NEditConfigurationAction extends NAction {
    public NEditConfigurationAction(NeormfExplorer explorer) {
        super("NAction.NEditConfigurationAction", explorer);
    }

    public void revalidate() {
        NNode[] selectedNodes = explorer.getSelectedNodes();
        if (selectedNodes.length == 1) {
            NNode node = selectedNodes[0];
            switch (node.getType()) {
                case CONFIG_FIELD:
                case CONFIG_DO:
                case CONFIG_BO:
                case FIELD:
                case BO:
                case DO: {
                    setEnabled(true);
                    return;
                }
            }
        }
        setEnabled(false);
    }

    public void actionPerformed(ActionEvent e) {
        NNode[] selectedNodes = explorer.getSelectedNodes();
        if (selectedNodes.length == 1) {
            NNode node = selectedNodes[0];
            switch (node.getType()) {
                case BO:
                case CONFIG_BO: {
                    ConfigNode root = explorer.pluginSession.getRootConfigNode(true);
                    ConfigNode boFile = explorer.pluginSession.getBORootNode(node.getName(), true);
                    EditBOPanel p = new EditBOPanel(node,false,
                            boFile.getChildOrCreateIt("object-definition").getChildOrCreateIt("bo"),
                            root,
                            explorer.pluginSession
                            
                            );
                    p.load();
                    if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(explorer, p, "Edit BO Configuration", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE)) {
                        try {
                            p.store();
                            root.store();
                            boFile.store();
                            explorer.pluginSession.storeRootConfigNode(root);
                        } catch (Exception ex) {
                            getLogger().log(Level.SEVERE,"storeRootConfigNode failed",ex);
                        }
                        explorer.updateModel();
                    }
                    break;
                }
                case DO:
                case CONFIG_DO: {
                    ConfigNode root = explorer.pluginSession.getRootConfigNode(true);
                    ConfigNode doFile = explorer.pluginSession.getDORootNode(node.getName(), true);
                    EditDOPanel p = new EditDOPanel(node,false,
                            doFile.getChildOrCreateIt("object-definition").getChildOrCreateIt("do"),
                            root,
                            explorer.pluginSession
                            
                            );
                    p.load();
                    if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(explorer, p, "Edit DO Configuration", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE)) {
                        try {
                            p.store();
                            root.store();
                            doFile.store();
                            explorer.pluginSession.storeRootConfigNode(root);
                        } catch (Exception ex) {
                            getLogger().log(Level.SEVERE,"storeRootConfigNode failed",ex);
                        }
                        explorer.updateModel();
                    }
                    break;
                }
                case CONFIG_FIELD:
                case FIELD: {
                    NNode donode = node.getType().equals(NNode.Type.CONFIG_FIELD)? (node.getParent().getParent()):node.getParent();
                    String doName = donode.getName();
                    ConfigNode doFile = explorer.pluginSession.getDORootNode(doName, true);
                    EditFieldPanel p = new EditFieldPanel(node, doName, doFile.getChildOrCreateIt("object-definition").getChildOrCreateIt("do"), explorer.pluginSession.getSession(),false);
                    p.load();
                    if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(explorer, p, "Edit Field Configuration", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE)) {
                        p.store();
                        try {
                            doFile.store(explorer.pluginSession.findDOConfigFile(doName, explorer.pluginSession.getValidProjectFolder()));
                        } catch (FileNotFoundException ex) {
                            getLogger().log(Level.SEVERE,"findDOConfigFile failed",ex);
                        }
                        explorer.updateModel();
                    }
                    break;
                }
            }
        }
    }

    public void editTable() {

    }

    public void editDO() {

    }

}
