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

import net.thevpc.dbclient.plugin.tool.neormf.explorer.NNode;
import net.thevpc.dbclient.plugin.tool.neormf.explorer.NeormfExplorer;
import org.vpc.neormf.jbgen.JBGenMain;
import org.vpc.neormf.jbgen.config.ConfigNode;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Level;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 21 avr. 2007 23:54:43
 */
public class NAddFieldAction extends NAction {

    public NAddFieldAction(NeormfExplorer explorer) {
        super("NAction.NAddFieldAction", explorer);
    }

    public void revalidate() {
        NNode[] selectedNodes = explorer.getSelectedNodes();
        if (selectedNodes.length == 1) {
            NNode node = selectedNodes[0];
            switch (node.getType()) {
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
                case DO: {
                    ConfigNode doFile = explorer.pluginSession.getDORootNode(node.getName(), true);
                    EditFieldPanel p = new EditFieldPanel(null, node.getName(), doFile.getChildOrCreateIt("object-definition").getChildOrCreateIt("do"), explorer.pluginSession.getSession(), false);
                    if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(explorer, p, "Add Field Configuration", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE)) {
                        p.store();
                        try {
                            doFile.store(explorer.pluginSession.findDOConfigFile(node.getName(), explorer.pluginSession.getValidProjectFolder()));
                        } catch (FileNotFoundException e1) {
                            getLogger().log(Level.SEVERE, "findDOConfigFile failed", e1);
                        }
                        explorer.updateModel();
                    }
                    break;
                }
                case ROOT_DO: {//enlever
                    File file = JBGenMain.getProjectFile(explorer.pluginSession.getValidProjectFolder());
                    ConfigNode root = null;
                    try {
                        root = ConfigNode.load(file);
                    } catch (Throwable ex) {
                        getLogger().log(Level.SEVERE, "ConfigNode.load failed", ex);
                    }
                    if (root == null) {
                        root = new ConfigNode("jbgen");
                    }

                    EditFieldPanel p = new EditFieldPanel(null, null, null, explorer.pluginSession.getSession(), true);
                    if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(explorer, p, "Add Field Configuration", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE)) {
                        p.store();
                        try {
                            root.store(file);
                        } catch (FileNotFoundException ex) {
                            getLogger().log(Level.SEVERE,"ConfigNode.store failed",ex);
                        }
                        explorer.updateModel();
                    }
                    break;
                }
            }
        }

    }
}
