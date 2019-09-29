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

package net.vpc.dbclient.plugin.system.sessionmanager.table;

import net.vpc.dbclient.api.actionmanager.DBCResultTableAction;
import net.vpc.dbclient.api.actionmanager.DBCTreeNodeAction;
import net.vpc.dbclient.api.sessionmanager.DBCResultTable;
import net.vpc.dbclient.api.sql.objects.DBObject;
import net.vpc.dbclient.plugin.system.sessionmanager.tree.DefaultEnableIsVisibleSwingWorker;
import net.vpc.dbclient.plugin.system.viewmanager.EnableActionChunkOperation;
import net.vpc.dbclient.plugin.system.viewmanager.VisibleComponentChunkOperation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 2006/11/07
 */
public class DBCResultTableEnableIsVisibleSwingWorker extends DefaultEnableIsVisibleSwingWorker {
    int row;
    int col;
    DBCResultTable table;
    private boolean showPopup;

    public DBCResultTableEnableIsVisibleSwingWorker(JPopupMenu popupMenu, MouseEvent evt, DBCResultTable table, boolean showPopup) {
        super(table.getSession(), popupMenu, evt, (JComponent) table.getTableComponent().getComponent(), table.getSqlNodeProvider(), table.getSession().getView());
        this.table = table;
        this.showPopup = showPopup;
    }


    @Override
    public void prepareVars() {
        nodes = provider == null ? new DBObject[0] : provider.getNodes();
        row = table.getSelectedRow();
        col = table.getSelectedColumn();
    }

    @Override
    public boolean validateComponent(Component child) {
        if (child instanceof AbstractButton) {
            AbstractButton b = (AbstractButton) child;
            Action a = b.getAction();
            if (a instanceof DBCResultTableAction) {
                ((DBCResultTableAction) a).setRow(row);
                ((DBCResultTableAction) a).setColumn(col);
            }
            if (a instanceof DBCTreeNodeAction) {
                DBCTreeNodeAction mi = (DBCTreeNodeAction) b.getAction();
                mi.setSelectedNodes(nodes);
                boolean b1 = mi.setSelectedNodes(nodes);
                publish(new EnableActionChunkOperation(mi, b1));
                publish(new VisibleComponentChunkOperation(child, b1));
                return b1;
            }
        }
        return false;
    }
}
