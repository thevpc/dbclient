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

package net.vpc.dbclient.plugin.presentation.infonode;

import net.infonode.docking.DockingWindow;
import net.infonode.docking.RootWindow;
import net.infonode.docking.SplitWindow;
import net.infonode.docking.properties.RootWindowProperties;
import net.infonode.docking.theme.DockingWindowsTheme;
import net.infonode.docking.theme.SlimFlatDockingTheme;
import net.infonode.util.Direction;
import net.vpc.dbclient.api.pluginmanager.DBCPluginSession;
import net.vpc.prs.plugin.Implementation;
import net.vpc.dbclient.api.sessionmanager.DBCSessionView;
import net.vpc.dbclient.api.sessionmanager.DBCSessionViewLayoutComponent;
import net.vpc.dbclient.api.viewmanager.DBCPluggablePanel;

import javax.swing.*;
import java.awt.*;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 31 janv. 2006 15:47:00
 */
@Implementation(priority = 10)
public class InfoNodeSessionViewLayoutComponent extends DBCPluggablePanel implements DBCSessionViewLayoutComponent {

    private RootWindow rootWindow;
    private RootWindowProperties dockingProperties = new RootWindowProperties();
    private DockingWindowsTheme currentTheme;

    public InfoNodeSessionViewLayoutComponent() {
        super(new BorderLayout());
    }

    public void setTheme(DockingWindowsTheme theme) {
        dockingProperties.replaceSuperObject(currentTheme.getRootWindowProperties(),
                theme.getRootWindowProperties());
        currentTheme = theme;
    }

    public void init(DBCSessionView view) {
        view.getSession().getView().getClientProperties().put("InfoNodeSessionViewLayoutComponent", this);
        DBCPluginSession pluginSession=view.getSession().getPluginSession(InfoNodePlugin.ID);
        String themeClass = pluginSession.getConfig().getStringProperty("InfoNodeSQLViewManager.windowtheme", SlimFlatDockingTheme.class.getClass().getName());
        try {
            currentTheme = (DockingWindowsTheme) Class.forName(themeClass,true,pluginSession.getPlugin().getDescriptor().getClassLoader()).newInstance();
        } catch (Throwable e) {
            currentTheme = new SlimFlatDockingTheme();
        }

        dockingProperties.addSuperObject(currentTheme.getRootWindowProperties());
        //DBCSession session = view.getSession();
//        try {
//            PRSManager.setComponentIconSet(panel, session.getConfig().getSessionInfo().getSesIconSet());
//        } catch (IconSetNotFoundException e) {
//            not matter
//        }

        rootWindow = InfoNodeUtils.prepareRootWindow(new DockingWindow[]{(DockingWindow) view.getExplorerContainer().getComponent()}, Direction.DOWN, dockingProperties, view.getSession());
        rootWindow.setWindow(
                new SplitWindow(false,
                0.8f,
                new SplitWindow(true,
                0.3f,
                (DockingWindow) view.getExplorerContainer().getComponent(),
                (DockingWindow) view.getWorkspaceContainer().getComponent()),
                (DockingWindow) view.getTracerContainer().getComponent()));

        JPanel p = new JPanel(new BorderLayout());
        p.add(view.getToolbar(), BorderLayout.PAGE_START);
        p.add(rootWindow, BorderLayout.CENTER);

        add(view.getMenu(), BorderLayout.PAGE_START);
        add(p, BorderLayout.CENTER);
        add(view.getStatusBar(), BorderLayout.PAGE_END);
    }

    public RootWindowProperties getDockingProperties() {
        return dockingProperties;
    }

    public Component getComponent() {
        return this;
    }

    // ####################################################"
    // DBCPluggable implementation
    // ####################################################"

}
