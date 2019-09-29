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

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
* @creationtime 21 avr. 2007 23:30:04
*/
class NNodeCellRenderer extends DefaultTreeCellRenderer {
    private NeormfExplorer neormfExplorer;

    public NNodeCellRenderer(NeormfExplorer neormfExplorer) {
        this.neormfExplorer = neormfExplorer;
    }

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        Icon i = null;
        boolean override=false;
        if (value instanceof NNode) {
            NNode c = (NNode) value;
            value = c.getName();
            switch(c.getType()){
                case ROOT_BDLG:
                case ROOT_BO:
                case ROOT_DO:
                case ROOT_TABLE:
                {
                    i= neormfExplorer.pluginSession.getIconSet().getIconR("NeormfExplorerRoot");
                    break;
                }
                case FIELD:
                {
                    i= neormfExplorer.pluginSession.getIconSet().getIconR("NeormfExplorerField");
                    break;
                }
                case CONFIG_FIELD:
                {
                    i= neormfExplorer.pluginSession.getIconSet().getIconR("NeormfExplorerCustomField");
                    break;
                }
                case CONFIG_DO:
                {
                    i= neormfExplorer.pluginSession.getIconSet().getIconR("NeormfExplorerCustomBO");
                    break;
                }
                case CONFIG_BO:
                {
                    i= neormfExplorer.pluginSession.getIconSet().getIconR("NeormfExplorerCustomBO");
                    break;
                }
                case TABLE:
                {
                    i= neormfExplorer.pluginSession.getIconSet().getIconR("NeormfExplorerTable");
                    break;
                }
                case BO:
                {
                    i= neormfExplorer.pluginSession.getIconSet().getIconR("NeormfExplorerBO");
                    break;
                }
                case DO:
                {
                    i= neormfExplorer.pluginSession.getIconSet().getIconR("NeormfExplorerDO");
                    break;
                }
                case BDLG:
                {
                    i= neormfExplorer.pluginSession.getIconSet().getIconR("NeormfExplorerBDL");
                    break;
                }
                case CONFIG:
                {
                    i= neormfExplorer.pluginSession.getIconSet().getIconR("NeormfExplorerConfig");
                    break;
                }
            }
            override=true;
        }
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        if (override) {
            setIcon(i);
        }
        return this;
    }
}
