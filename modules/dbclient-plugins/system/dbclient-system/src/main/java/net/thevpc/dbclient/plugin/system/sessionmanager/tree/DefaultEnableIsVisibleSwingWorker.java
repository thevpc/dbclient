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

import net.thevpc.dbclient.api.DBCSession;
import net.thevpc.dbclient.api.actionmanager.DBCTreeNodeAction;
import net.thevpc.dbclient.api.sessionmanager.DBCSQLNodeProvider;
import net.thevpc.dbclient.api.sql.objects.DBObject;
import net.thevpc.dbclient.api.viewmanager.DBCChunkOperation;
import net.thevpc.dbclient.plugin.system.viewmanager.EnableActionChunkOperation;
import net.thevpc.dbclient.plugin.system.viewmanager.EnableIsVisibleSwingWorker;
import net.thevpc.common.swing.prs.PRSManager;
import net.thevpc.common.prs.ResourceSetHolder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 2006/11/07
 */
public class DefaultEnableIsVisibleSwingWorker extends EnableIsVisibleSwingWorker {
    protected JComponent mainComponent;
    protected DBCSQLNodeProvider provider;
    protected DBObject[] nodes;
    protected MouseEvent evt;
    protected Point point;
    protected ResourceSetHolder resourceSetHolder;

    public DefaultEnableIsVisibleSwingWorker(DBCSession session, JPopupMenu popupMenu, MouseEvent evt, JComponent mainComponent, DBCSQLNodeProvider provider, ResourceSetHolder resourceSetHolder) {
        super(popupMenu, session);
        this.mainComponent = mainComponent;
        this.provider = provider;
        this.evt = evt;
        this.resourceSetHolder = resourceSetHolder;
        point = SwingUtilities.convertPoint((Component) evt.getSource(), evt.getPoint(), mainComponent);
    }

    public void prepareVars() {
        nodes = provider == null ? new DBObject[0] : provider.getNodes();
    }

    public void endProcess() {
        publish(new DBCChunkOperation() {
            public void executeOperation() {
                try {
                    PRSManager.update(getPopupMenu(), resourceSetHolder);
                } catch (Exception e1) {
                    //
                }
                JComponent src = ((JComponent) evt.getSource());
                Point pt = src.getPopupLocation(evt);
                if (pt == null) {
                    pt = evt.getPoint();
                }
                popupMenu.pack();
                popupMenu.show(src, pt.x, pt.y);
                evt.consume();//??
            }
        });
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
