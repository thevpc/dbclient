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

package net.thevpc.dbclient.plugin.system.sessionmanager.window;

import net.thevpc.common.swing.prs.ComponentResourcesUpdater;
import net.thevpc.dbclient.api.DBCSession;
import net.thevpc.dbclient.api.pluginmanager.DBCFactory;
import net.thevpc.common.prs.plugin.Inject;
import net.thevpc.dbclient.api.sessionmanager.DBCInternalWindow;
import net.thevpc.dbclient.api.sessionmanager.DBCInternalWindowPopupFactory;
import net.thevpc.dbclient.api.sessionmanager.DBCSessionInternalWindowBrowser;
import net.thevpc.dbclient.api.sessionmanager.DBCSessionView;
import net.thevpc.common.swing.prs.PRSManager;
import net.thevpc.common.prs.iconset.IconSet;
import net.thevpc.common.prs.messageset.MessageSet;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 12 juil. 2005 19:12:51
 */
public class DBCSessionInternalWindowBrowserImpl extends JTabbedPane implements DBCSessionInternalWindowBrowser {
    @Inject
    private DBCSession session;
    protected int counter = 1;
    private boolean enableLockingOnLeftDoubleClick = true;
    private DBCFactory factory;
    private DBCInternalWindowPopupFactory dbcInternalWindowPopupFactory;
    private DBCSessionView.Side side;
    private PropertyChangeListener pagesListener = new PropertyChangeListener() {

        public void propertyChange(PropertyChangeEvent evt) {
            String pn = evt.getPropertyName();
            DBCInternalWindow w = (DBCInternalWindow) evt.getSource();
            int index = indexOfComponent(w.getComponent());
            windowPropertyChanged(pn, w, index, evt.getNewValue());
        }
    };


    public DBCSessionInternalWindowBrowserImpl() {
    }


    public DBCInternalWindow getFirstUnlockedFrame() {
        int max = getTabCount();
        for (int i = 0; i < max; i++) {
            DBCInternalWindow tabFrame = getPaneAt(i);
            if (!tabFrame.isLocked()) {
                return tabFrame;
            }
        }
        return null;
    }

    protected DBCInternalWindow getFirstUnlockedFrame(java.util.List<DBCInternalWindow> ignoredList) {
        int max = getTabCount();
        for (int i = 0; i < max; i++) {
            DBCInternalWindow tabFrame = getPaneAt(i);
            if (!tabFrame.isLocked() && !ignoredList.contains(tabFrame)) {
                return tabFrame;
            }
        }
        return null;
    }

    public int getFirstUnlockedFrameIndex() {
        int max = getTabCount();
        for (int i = 0; i < max; i++) {
            DBCInternalWindow tabFrame = getPaneAt(i);
            if (!tabFrame.isLocked()) {
                return i;
            }
        }
        return -1;
    }

    public int getFirstUnlockedClosableFrameIndex() {
        int max = getTabCount();
        for (int i = 0; i < max; i++) {
            DBCInternalWindow tabFrame = getPaneAt(i);
            if (!tabFrame.isLocked() && tabFrame.isClosable()) {
                return i;
            }
        }
        return -1;
    }

    public void updateUI() {
        super.updateUI();
        JPopupMenu componentPopupMenu = getComponentPopupMenu();
        if (componentPopupMenu != null) {
            SwingUtilities.updateComponentTreeUI(componentPopupMenu);
        }
    }

    protected String generateName() {
        return "[" + (counter++) + "]";
    }

    public DBCInternalWindow getPaneAt(int index) {
        return (DBCInternalWindow) getComponentAt(index);
    }

    public int findPaneIndex(Class clazz, String id, Boolean lockStatus, Boolean closedStatus, int startIndex) {
        int max = getTabCount();
        for (int i = startIndex; i < max; i++) {
            DBCInternalWindow p = getPaneAt(i);
            if (
                    clazz.isInstance(p)
                            && (id == null || (id.equals(p.getId())))
                            && (lockStatus == null || lockStatus == p.isLocked())
                            && (closedStatus == null || closedStatus == p.isClosable())
                    ) {
                return i;
            }
        }
        return -1;
    }

    public boolean isNewWindowLocked() {
        return session.getConfig().getBooleanProperty("DBCSessionView.LockNew", false);
    }

    public DBCInternalWindow addWindow(Component tabFrame, boolean replaceUnlocked) {
        int index = replaceUnlocked ? getFirstUnlockedClosableFrameIndex() : -1;
        if (index >= 0) {
            removeTabAt(index);
            insertTab(null, null, tabFrame, null, index);
            setSelectedIndex(index);
            return (DBCInternalWindow) getComponentAt(index);
        } else {
            addTab(null, tabFrame);
            int count = getTabCount();
            setSelectedIndex(count - 1);
            return (DBCInternalWindow) getComponentAt(count - 1);
        }
    }


    @Override
    public void insertTab(String title, Icon icon, Component component, String tip, int index) {
        DBCInternalWindow window;
        if (!(component instanceof DBCInternalWindow)) {
            window = getFactory().newInstance(DBCInternalWindow.class);
            window.setMainComponent(component);
            window.setSide(getSide());
        } else {
            window = (DBCInternalWindow) component;
            component = window.getMainComponent();
        }
        window.setWindowBrowser(this);
        window.setLocked(isNewWindowLocked());
        window.addPropertyChangeListener(DBCInternalWindow.PROPERTY_LOCKED, pagesListener);
        window.addPropertyChangeListener(DBCInternalWindow.PROPERTY_CLOSEABLE, pagesListener);
        window.addPropertyChangeListener(DBCInternalWindow.PROPERTY_TITLE, pagesListener);
        window.addPropertyChangeListener(DBCInternalWindow.PROPERTY_CLOSE, pagesListener);
        super.insertTab(title, icon, window.getComponent(), tip, index);
        updateIcon(window);
        setTitleAt(index, window.getTitle());
    }

    public void removeTabAt(int index) {
        DBCInternalWindow tabFrame = getPaneAt(index);
        if (tabFrame.isClosable()) {
            DBCInternalWindow component1 = (DBCInternalWindow) getComponentAt(index);
            component1.removePropertyChangeListener(DBCInternalWindow.PROPERTY_LOCKED, pagesListener);
            component1.removePropertyChangeListener(DBCInternalWindow.PROPERTY_CLOSEABLE, pagesListener);
            component1.removePropertyChangeListener(DBCInternalWindow.PROPERTY_TITLE, pagesListener);
            component1.removePropertyChangeListener(DBCInternalWindow.PROPERTY_CLOSE, pagesListener);
            super.removeTabAt(index);
        }
    }

    public DBCFactory getFactory() {
        return factory;
    }

    public void setFactory(DBCFactory componentManager) {
        this.factory = componentManager;
    }

    protected void updateIcon(DBCInternalWindow w) {
        int index = indexOfComponent(w.getComponent());
        if (index >= 0 && w.getComponent() instanceof JComponent) {
            setIconAt(index, PRSManager.getComponentIconSet((JComponent) w.getComponent(), getSession().getView().getIconSet()).getIconR(
                    w.isLocked() ? "Locked" : "Unlocked"
            ));
        }
    }

    protected void windowPropertyChanged(String pn, DBCInternalWindow w, int index, Object newValue) {
        if (DBCInternalWindow.PROPERTY_LOCKED.equals(pn)) {
            updateIcon(w);
        } else if (DBCInternalWindow.PROPERTY_CLOSEABLE.equals(pn)) {
            //?
        } else if (DBCInternalWindow.PROPERTY_TITLE.equals(pn)) {
            setTitleAt(index, w.getTitle());
        } else if (DBCInternalWindow.PROPERTY_CLOSE.equals(pn)) {
            removeTabAt(index);
        }
    }

    public Collection<DBCInternalWindow> getWindows() {
        ArrayList<DBCInternalWindow> list = new ArrayList<DBCInternalWindow>();
        int count = getTabCount();
        for (int i = 0; i < count; i++) {
            DBCInternalWindow w = getPaneAt(i);
            if (w != null) {
                list.add(w);
            }
        }
        return list;
    }

    public void init(DBCSessionView.Side side) {
        this.side = side;
        setFactory(session.getFactory());
        PRSManager.addSupport(this, "DBCSessionInternalWindowBrowserImpl", new ComponentResourcesUpdater() {
            public void update(JComponent comp, String id, MessageSet messageSet, IconSet iconSet) {
                PRSManager.update(getComponentPopupMenu(), messageSet, iconSet);
                int max = getTabCount();
                for (int i = 0; i < max; i++) {
                    DBCInternalWindow f = getPaneAt(i);
                    f.setLocked(f.isLocked());
                }
            }
        });
        setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        dbcInternalWindowPopupFactory = getFactory().newInstance(DBCInternalWindowPopupFactory.class);
        setComponentPopupMenu(dbcInternalWindowPopupFactory.createPopup());
        getComponentPopupMenu().addPopupMenuListener(new PopupMenuListener() {
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                int index = getSelectedIndex();
                dbcInternalWindowPopupFactory.applyWindow(index >= 0 ? getPaneAt(index) : null, getComponentPopupMenu());
                PRSManager.update(getComponentPopupMenu(), DBCSessionInternalWindowBrowserImpl.this.session.getView());
            }

            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                //
            }

            public void popupMenuCanceled(PopupMenuEvent e) {
                //
            }
        });
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked
                    (MouseEvent
                            e) {
                if (enableLockingOnLeftDoubleClick && e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e)) {
                    int i = getSelectedIndex();
                    if (i >= 0) {
                        getPaneAt(i).defaultAction();
                    }
                }
            }
        }

        );
    }

    public JComponent getComponent() {
        return this;
    }

    public DBCSession getSession() {
        return session;
    }

    public DBCSessionView.Side getSide() {
        return side;
    }

}
