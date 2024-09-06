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

package net.thevpc.dbclient.plugin.presentation.infonode;

import net.infonode.docking.DockingWindow;
import net.infonode.docking.RootWindow;
import net.infonode.docking.TabWindow;
import net.infonode.docking.View;
import net.infonode.docking.mouse.DockingWindowActionMouseButtonListener;
import net.infonode.docking.properties.RootWindowProperties;
import net.infonode.docking.properties.ViewProperties;
import net.infonode.docking.util.DockingUtil;
import net.infonode.docking.util.ViewMap;
import net.infonode.util.Direction;
import net.thevpc.dbclient.api.pluginmanager.DBCPluginSession;
import net.thevpc.dbclient.api.sessionmanager.DBCInternalWindow;
import net.thevpc.dbclient.api.DBCSession;
import net.thevpc.common.swing.prs.PRSManager;
import net.thevpc.common.prs.ResourceSetHolder;

import javax.swing.*;
import java.awt.*;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 7 mai 2007 12:19:59
 */
public final class InfoNodeUtils {
    public static boolean isSystemWindow(DockingWindow window) {
        Boolean sw = (Boolean) window.getClientProperty("SystemWindow");
        return (sw != null && sw.booleanValue());
    }

    public static void setSystemWindow(DockingWindow window, boolean v) {
        window.putClientProperty("SystemWindow", v ? Boolean.TRUE : null);
    }

    public static void bindWindows(View view, DBCInternalWindow iwindow, ResourceSetHolder holder) {
        view.setComponent(iwindow.getComponent());
        view.putClientProperty("DBCInternalWindow", iwindow);
        iwindow.putClientProperty("View", view);
        updateViewProperties(iwindow, holder);
    }

    public static void updateViewProperties(DBCInternalWindow w, ResourceSetHolder holder) {
        View view = InfoNodeUtils.getView(w);
        view.getViewProperties().setTitle(w.getTitle());
        updateIcon(w, holder);
    }

    public static void updateIcon(DBCInternalWindow w, ResourceSetHolder holder) {
        Component comp = w.getComponent();
        if (comp instanceof JComponent) {
            Icon iconR = PRSManager.getComponentIconSet((JComponent) comp, holder.getIconSet()).getIconR(
                    w.isLocked() ? "Locked" : "Unlocked");
            ViewProperties viewProperties = InfoNodeUtils.getView(w).getViewProperties();
            try{
                viewProperties.setIcon(iconR);
            }catch(NullPointerException ex){
                //this is an infonode bug
                //ignore it
            }
        }
    }

    public static DBCInternalWindow getInternalWindow(DockingWindow window) {
        return (DBCInternalWindow) window.getClientProperty("DBCInternalWindow");
    }

    public static View getView(DBCInternalWindow window) {
        return (View) window.getClientProperty("View");
    }

    public static RootWindow prepareRootWindow(DockingWindow[] w, Direction dir, RootWindowProperties props, DBCSession ps) {
        ViewMap map = new ViewMap();
        for (int i = 0; i < w.length; i++) {
            if (w[i] instanceof View) {
                map.addView(i, (View) w[i]);
            }
            setSystemWindow(w[i], true);
            prepareDockingWindow(w[i], true);
        }
        RootWindow r = DockingUtil.createRootWindow(map, null, true);
        r.getRootWindowProperties().addSuperObject(props);
        r.getWindowBar(dir).setEnabled(true);
        r.addTabMouseButtonListener(DockingWindowActionMouseButtonListener.MIDDLE_BUTTON_CLOSE_LISTENER);
        r.getRootWindow();
        TabWindow newWindow = new TabWindow(w);
        prepareDockingWindow(newWindow, true);
        r.setWindow(newWindow);
        r.setPopupMenuFactory(new InfoNodeWindowPopupMenuFactory(ps));
        return r;
    }

    public static void prepareDockingWindow(DockingWindow w, boolean defaultWindow) {
        w.getWindowProperties().setCloseEnabled(!defaultWindow);
        w.getWindowProperties().setUndockEnabled(!defaultWindow);
        w.getWindowProperties().setUndockOnDropEnabled(!defaultWindow);
    }
}
