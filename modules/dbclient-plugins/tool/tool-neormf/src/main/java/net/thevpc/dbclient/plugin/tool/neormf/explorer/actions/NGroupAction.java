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
import org.vpc.neormf.jbgen.config.ConfigNode;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 21 avr. 2007 23:54:43
 */
public class NGroupAction extends NAction {

    public NGroupAction(NeormfExplorer explorer) {
        super("NAction.NGroupAction", explorer);
    }

    @Override
    public void revalidate() {
        NNode[] selectedNodes = explorer.getSelectedNodes();
        if (selectedNodes.length >= 2) {
            NNode.Type type = selectedNodes[0].getType();
            for (NNode selectedNode : selectedNodes) {
                if (!type.equals(selectedNode.getType())) {
                    setEnabled(false);
                    return;
                }
            }
            setEnabled(true);
            return;
        }
        setEnabled(false);
    }

    public void actionPerformed(ActionEvent e) {
        NNode[] selectedNodes = explorer.getSelectedNodes();
        if (selectedNodes.length >= 2) {
            NNode.Type type = selectedNodes[0].getType();
            for (NNode selectedNode : selectedNodes) {
                if (!type.equals(selectedNode.getType())) {
                    JOptionPane.showMessageDialog(null, "Could not group heterogenious objects");
                    return;
                }
            }
            switch (type) {
                case DO: {
                    ArrayList<String> s=new ArrayList<String>();
                    s.add("");
                    s.addAll(Arrays.asList(explorer.pluginSession.getBONames()));
                    String[] bonames= s.toArray(new String[s.size()]);
                    String v=(String) JOptionPane.showInputDialog(null,"Business Object Name","Select Business Object ",JOptionPane.QUESTION_MESSAGE,null,bonames,"");
                    if(v!=null){
                        for (NNode selectedNode : selectedNodes) {
                            ConfigNode doFile = explorer.pluginSession.getDORootNode(selectedNode.getName(), true);
                            doFile.getChildOrCreateIt("object-definition").getChildOrCreateIt("do").setAttribute("bo",v.length()==0?null:v);
                            try {
                                doFile.store();
                            } catch (FileNotFoundException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                    return;
                }
                case TABLE: {
                    ArrayList<String> s=new ArrayList<String>();
                    s.add("");
                    s.addAll(Arrays.asList(explorer.pluginSession.getDONames()));
                    String[] donames= s.toArray(new String[s.size()]);
                    String v=(String) JOptionPane.showInputDialog(null,"Data Object Name","Select Data Object",JOptionPane.QUESTION_MESSAGE,null,donames,"");
                    if(v!=null){
                        for (NNode selectedNode : selectedNodes) {
                            ConfigNode tableFile = explorer.pluginSession.getTableRootNode(selectedNode.getName(), true);
                            tableFile.getChildOrCreateIt("object-definition").getChildOrCreateIt("table").setAttribute("do",v.length()==0?null:v);
                            try {
                                tableFile.store();
                            } catch (FileNotFoundException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "Could not group these objects.");
        }
    }
}
