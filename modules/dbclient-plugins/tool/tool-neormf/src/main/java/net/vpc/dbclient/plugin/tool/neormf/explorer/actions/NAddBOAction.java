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
import java.io.IOException;
import java.util.logging.Level;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 21 avr. 2007 23:54:43
 */
public class NAddBOAction extends NAction {

    public NAddBOAction(NeormfExplorer explorer) {
        super("NAction.NAddBOAction", explorer);
    }

    @Override
    public void revalidate() {
        NNode[] selectedNodes = explorer.getSelectedNodes();
        if (selectedNodes.length == 1) {
            NNode node = selectedNodes[0];
            setEnabled(NNode.Type.ROOT_BO.equals(node.getType()));
            return;
        }
        setEnabled(false);
    }

    public void actionPerformed(ActionEvent e) {
        NNode[] selectedNodes = explorer.getSelectedNodes();
        if (selectedNodes.length == 1) {
            NNode node = selectedNodes[0];
            switch (node.getType()) {
                case ROOT_BO: {
//                    ConfigNode doFile = explorer.plugin.getDOFile(node.getName(), true);
                    ConfigNode root = getExplorer().pluginSession.getRootConfigNode(true);
                    EditBOPanel p = new EditBOPanel(null, true, null, root, getExplorer().pluginSession);
                    if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(explorer, p, "Add BO Configuration", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE)) {
                        try {
                            p.store();
                            root.store();
                            ConfigNode boFile = explorer.pluginSession.getBORootNode(node.getName(), true);
                            boFile.store();
                        } catch (IOException e1) {
                            getLogger().log(Level.SEVERE,"getBORootNode failed",e1);
                        }
                        explorer.updateModel();
                    }
                    break;
                }
            }
        }

    }
}
