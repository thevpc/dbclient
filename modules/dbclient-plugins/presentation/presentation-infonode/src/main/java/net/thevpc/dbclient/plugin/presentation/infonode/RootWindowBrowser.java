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
import net.infonode.util.Direction;
import net.thevpc.dbclient.api.DBCSession;
import net.thevpc.dbclient.api.pluginmanager.DBCAbstractPluggable;
import net.thevpc.common.prs.plugin.Implementation;
import net.thevpc.common.prs.plugin.Inject;
import net.thevpc.dbclient.api.sessionmanager.DBCInternalWindow;
import net.thevpc.dbclient.api.sessionmanager.DBCSessionInternalWindowBrowser;
import net.thevpc.dbclient.api.sessionmanager.DBCSessionView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;
import net.infonode.docking.SplitWindow;
import net.thevpc.common.swing.SwingUtilities3;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 7 mai 2007 14:05:40
 */
@Implementation(priority = 10)
public class RootWindowBrowser extends DBCAbstractPluggable implements DBCSessionInternalWindowBrowser {

    private RootWindow rootWindow;
    private DBCSessionView.Side side;
    @Inject
    private DBCSession session;
    private PropertyChangeListener pagesListener = new PropertyChangeListener() {

        public void propertyChange(PropertyChangeEvent evt) {
            String pn = evt.getPropertyName();
            DBCInternalWindow w = (DBCInternalWindow) evt.getSource();
            View newView = InfoNodeUtils.getView(w);
            windowPropertyChanged(pn, w, newView, evt.getNewValue());
        }
    };

    public RootWindowBrowser() {
    }

    public void init(DBCSessionView.Side side) {
        this.side = side;
    }

    public JComponent getComponent() {
        return getRootWindow();
    }

    public RootWindow getRootWindow() {
        if (rootWindow == null) {
            rootWindow = prepareRootWindow();
        }
        return rootWindow;
    }

    private RootWindow prepareRootWindow() {
        InfoNodeSessionViewLayoutComponent v = (InfoNodeSessionViewLayoutComponent) session.getView().getClientProperties().get("InfoNodeSessionViewLayoutComponent");
        Direction dir = null;
        switch (side) {
            case Explorer: {
                dir = Direction.LEFT;
                break;
            }
            case Workspace: {
                dir = Direction.RIGHT;
                break;
            }
            case Footer: {
                dir = Direction.DOWN;
                break;
            }
        }
        return InfoNodeUtils.prepareRootWindow(new DockingWindow[0], dir, v.getDockingProperties(), getSession());
    }

    public DBCSession getSession() {
        return session;
    }

    public DBCInternalWindow addWindow(Component component, boolean replaceUnlocked) {
        DBCInternalWindow window;
        if (!(component instanceof DBCInternalWindow)) {
            DBCInternalWindow availableWindow = getFirstUnlockedClosableWindow();
            if (availableWindow != null) {
                window = availableWindow;
                View view = InfoNodeUtils.getView(window);
                window.setMainComponent(component);
                window.setLocked(isNewWindowLocked());
                TabWindow tw = (TabWindow) SwingUtilities.getAncestorOfClass(TabWindow.class, view);
                if (tw != null) {
                    int nextIndex = tw.getChildWindowIndex(view);
                    if (nextIndex >= 0) {
                        tw.setSelectedTab(nextIndex);
                    }
                }
                //view.requestFocus();
                InfoNodeUtils.bindWindows(view, window, getSession().getView());
                return window;
            } else {
                window = session.getFactory().newInstance(DBCInternalWindow.class);
                window.setMainComponent(component);
                window.setSide(getSide());

                window.addPropertyChangeListener(DBCInternalWindow.PROPERTY_LOCKED, pagesListener);
                window.addPropertyChangeListener(DBCInternalWindow.PROPERTY_CLOSEABLE, pagesListener);
                window.addPropertyChangeListener(DBCInternalWindow.PROPERTY_TITLE, pagesListener);
                window.addPropertyChangeListener(DBCInternalWindow.PROPERTY_CLOSE, pagesListener);
            }
        } else {
            window = (DBCInternalWindow) component;
            component = window.getMainComponent();
            window.removePropertyChangeListener(DBCInternalWindow.PROPERTY_LOCKED, pagesListener);
            window.removePropertyChangeListener(DBCInternalWindow.PROPERTY_CLOSEABLE, pagesListener);
            window.removePropertyChangeListener(DBCInternalWindow.PROPERTY_TITLE, pagesListener);
            window.removePropertyChangeListener(DBCInternalWindow.PROPERTY_CLOSE, pagesListener);

            window.addPropertyChangeListener(DBCInternalWindow.PROPERTY_LOCKED, pagesListener);
            window.addPropertyChangeListener(DBCInternalWindow.PROPERTY_CLOSEABLE, pagesListener);
            window.addPropertyChangeListener(DBCInternalWindow.PROPERTY_TITLE, pagesListener);
            window.addPropertyChangeListener(DBCInternalWindow.PROPERTY_CLOSE, pagesListener);
        }
//        if (isNewWindowLocked()) {
//            window.setLocked(true);
//        }
        window.setWindowBrowser(this);
        View newView = new View("NO_NAME", null, window.getComponent());
        InfoNodeUtils.bindWindows(newView, window, getSession().getView());
        window.setLocked(isNewWindowLocked());
        addWindow(newView);
        return window;
    }

    public void addWindow(View child) {
        DockingWindow oldWindow = getRootWindow().getWindow();
        DockingWindow x = addWindow0(oldWindow, child);
        if (x != null) {
            getRootWindow().setWindow(x);
        }
    }

    private DockingWindow addWindow0(DockingWindow parent, View child) {
        if (parent instanceof TabWindow) {
            ((TabWindow) parent).addTab(child);
            return null;
        } else if (parent instanceof SplitWindow) {
            DockingWindow x = ((SplitWindow) parent).getLeftWindow();
            if (x != null) {
                DockingWindow x2 = addWindow0(x, child);
                if (x2 != null) {
                    ((SplitWindow) parent).add(x2);
                    return null;
                } else {
                    return null;
                }
            } else {
                ((SplitWindow) parent).add(child);
                return null;
            }
        } else if (parent != null) {
            return new TabWindow(new DockingWindow[]{parent, child});
        } else {
            return new TabWindow(child);
        }
    }

    public DBCInternalWindow getFirstUnlockedClosableWindow() {
        for (DBCInternalWindow dbcInternalWindow : getWindows()) {
            if (dbcInternalWindow.isClosable() && !dbcInternalWindow.isLocked()) {
                return dbcInternalWindow;
            }
        }
        return null;
    }

    protected void windowPropertyChanged(String pn, DBCInternalWindow w, View newView, Object newValue) {
        if (DBCInternalWindow.PROPERTY_LOCKED.equals(pn)) {
            InfoNodeUtils.updateViewProperties(w, getSession().getView());
        } else if (DBCInternalWindow.PROPERTY_CLOSEABLE.equals(pn)) {
            newView.getWindowProperties().setCloseEnabled(w.isClosable());
        } else if (DBCInternalWindow.PROPERTY_TITLE.equals(pn)) {
            newView.getViewProperties().setTitle(w.getTitle());
        } else if (DBCInternalWindow.PROPERTY_CLOSE.equals(pn)) {
            newView.close();
        }
    }

    public DBCSessionView.Side getSide() {
        return side;
    }

    public Collection<DBCInternalWindow> getWindows() {
        ArrayList<DBCInternalWindow> all = new ArrayList<DBCInternalWindow>();
        Stack<DockingWindow> s = new Stack<DockingWindow>();
        s.add(getRootWindow());
        while (!s.isEmpty()) {
            DockingWindow w = s.pop();
            int c = w.getChildWindowCount();
            DBCInternalWindow ww = InfoNodeUtils.getInternalWindow(w);
            if (ww != null) {
                all.add(ww);
            }
            for (int i = 0; i < c; i++) {
                s.push(w.getChildWindow(i));
            }
        }
        return all;
    }

    public boolean isNewWindowLocked() {
        return session.getConfig().getBooleanProperty("DBCSessionView.LockNew", false);
    }

    public void requestFocus() {
        rootWindow.requestFocus();
    }

    public void putClientProperty(Object key, Object value) {
        rootWindow.putClientProperty(key, value);
    }

    public Object getClientProperty(Object key) {
        return rootWindow.getClientProperty(key);
    }

    public void repaint() {
        rootWindow.repaint();
    }

    public void addMouseListener(MouseListener l) {
        rootWindow.addMouseListener(l);
    }

    public void removeMouseListener(MouseListener l) {
        rootWindow.removeMouseListener(l);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        rootWindow.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        rootWindow.removePropertyChangeListener(listener);
    }

    public void addFocusListener(FocusListener l) {
        rootWindow.addFocusListener(l);
    }

    public void removeFocusListener(FocusListener l) {
        rootWindow.removeFocusListener(l);
    }

    public void addKeyListener(KeyListener l) {
        rootWindow.addKeyListener(l);
    }

    public void removeKeyListener(KeyListener l) {
        rootWindow.removeKeyListener(l);
    }

    public Point getLocationOnScreen() {
        return rootWindow.getLocationOnScreen();
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        rootWindow.addPropertyChangeListener(propertyName, listener);
    }

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        rootWindow.removePropertyChangeListener(propertyName, listener);
    }

    public boolean isEnabled() {
        return rootWindow.isEnabled();
    }

    public void setEnabled(boolean enable) {
        rootWindow.setEnabled(enable);
    }
}
