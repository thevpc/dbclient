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

package net.thevpc.dbclient.plugin.system.sessionmanager.tree;

import net.thevpc.dbclient.api.actionmanager.DBCTreeNodeAction;
import net.thevpc.dbclient.api.sessionmanager.DBCSessionExplorer;
import net.thevpc.dbclient.api.sql.objects.DBObject;
import net.thevpc.dbclient.plugin.system.viewmanager.EnableActionChunkOperation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 2006/11/07
 */
public class JTreeEnableIsVisibleSwingWorker extends DefaultEnableIsVisibleSwingWorker {
    public JTreeEnableIsVisibleSwingWorker(JPopupMenu popupMenu, MouseEvent evt, DBCSessionExplorer tree) {
        super(tree.getSession(), popupMenu, evt, (JComponent) tree.getComponent(), tree.getSqlNodeProvider(), tree.getSession().getView());
    }

    public void prepareVars() {
        nodes = provider == null ? new DBObject[0] : provider.getNodes();
    }

    public boolean validateComponent(Component child) {
        if (child instanceof AbstractButton) {
            AbstractButton b = (AbstractButton) child;
            if (b.getAction() instanceof DBCTreeNodeAction) {
                DBCTreeNodeAction mi = (DBCTreeNodeAction) b.getAction();
                boolean b1 = mi.setSelectedNodes(nodes);
                publish(new EnableActionChunkOperation(mi, b1));
                return b1;
            }
        }
        return false;
    }
}
