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

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 21 avr. 2007 23:54:43
 */
public class NRemoveAction extends NAction {

    public NRemoveAction(NeormfExplorer explorer) {
        super("NAction.NRemoveAction", explorer);
    }

    public void revalidate() {
        NNode[] selectedNodes = explorer.getSelectedNodes();
        if (selectedNodes.length == 1) {
            NNode node = selectedNodes[0];
            switch (node.getType()) {
                case DO:
                case BO:
                case CONFIG_FIELD: {
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
                    String doName = node.getName();
                    File file = explorer.pluginSession.findDOConfigFile(doName, explorer.pluginSession.getValidProjectFolder());
                    if (file.exists()) {
                        file.delete();
                        explorer.updateModel();
                    }
                    break;
                }
                case BO: {
                    String doName = node.getName();
                    File file = explorer.pluginSession.findBOConfigFile(doName, explorer.pluginSession.getValidProjectFolder());
                    if (file.exists()) {
                        file.delete();
                        explorer.updateModel();
                    }
                    break;
                }
                case CONFIG_FIELD: {
                    String doName = node.getParent().getParent().getName();
                    ConfigNode doFile = explorer.pluginSession.getDORootNode(doName, false);
                    if (doFile != null) {
                        ConfigNode[] configNodes = doFile.getChildren("object-definition.do.field<name=" + node.getName() + ">", false, false);
                        boolean found = false;
                        for (ConfigNode configNode : configNodes) {
                            configNode.getParent().remove(configNode);
                            found = true;
                        }
                        if (found) {
                            try {
                                doFile.store(explorer.pluginSession.findDOConfigFile(doName, explorer.pluginSession.getValidProjectFolder()));
                            } catch (FileNotFoundException e1) {
                                e1.printStackTrace();
                            }
                            explorer.updateModel();
                        }
                    }
                }
            }
        }

    }
}