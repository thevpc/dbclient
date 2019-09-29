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

package net.vpc.dbclient.plugin.system.sessionmanager.window;

import net.vpc.dbclient.api.DBCSession;
import net.vpc.dbclient.api.sessionmanager.DBCSessionView;
import net.vpc.dbclient.api.sessionmanager.DBCSessionViewLayoutComponent;
import net.vpc.dbclient.api.viewmanager.DBCPluggablePanel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 31 janv. 2006 15:47:00
 */
public class DBCSessionViewLayoutComponentImpl extends DBCPluggablePanel implements DBCSessionViewLayoutComponent {
    public static final String SPROP_DIVIDER_LOCATION_PROPERTY = "SQLSessionMainPanel.SPROP_DIVIDER_LOCATION_PROPERTY";

    public DBCSessionViewLayoutComponentImpl() {
        super(new BorderLayout());
    }

    public Component getComponent() {
        return this;
    }

    public void init(DBCSessionView view) {
        final DBCSession session = view.getSession();
//        try {
//            PRSManager.setComponentIconSet(panel, session.getConfig().getSessionInfo().getSesIconSet());
//        } catch (IconSetNotFoundException e) {
//            not matter
//        }

        JSplitPane split = ComponentOrientation.getOrientation(session.getView().getLocale()).isLeftToRight() ?
                new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, view.getExplorerContainer().getComponent(), view.getWorkspaceContainer().getComponent())
                : new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, view.getWorkspaceContainer().getComponent(), view.getExplorerContainer().getComponent());
        split.setBorder(null);
        split.setResizeWeight(0.2);
        split.setOneTouchExpandable(true);


        JSplitPane split2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                split,
                view.getTracerContainer().getComponent()
        );
        JPanel p = new JPanel(new BorderLayout());
        p.add(view.getToolbar(), BorderLayout.PAGE_START);
        p.add(split2, BorderLayout.CENTER);
        add(view.getMenu(), BorderLayout.PAGE_START);
        add(p, BorderLayout.CENTER);
        add(view.getStatusBar(), BorderLayout.PAGE_END);
        int integerProperty = session.getConfig().getIntegerProperty(SPROP_DIVIDER_LOCATION_PROPERTY, -1);
        if (integerProperty != -1) {
            split.setDividerLocation(integerProperty);
        }
        split.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                session.getConfig().setIntegerProperty(SPROP_DIVIDER_LOCATION_PROPERTY, (Integer) evt.getNewValue());
            }
        });
    }

}
