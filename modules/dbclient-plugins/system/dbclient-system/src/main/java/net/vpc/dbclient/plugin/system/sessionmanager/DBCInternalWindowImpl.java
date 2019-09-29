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

package net.vpc.dbclient.plugin.system.sessionmanager;

import net.vpc.dbclient.api.sessionmanager.DBCInternalWindow;
import net.vpc.dbclient.api.sessionmanager.DBCSessionInternalWindowBrowser;
import net.vpc.dbclient.api.sessionmanager.DBCSessionView;
import net.vpc.dbclient.api.viewmanager.DBCPluggablePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 6 mai 2006 18:47:41
 */
public class DBCInternalWindowImpl extends DBCPluggablePanel implements DBCInternalWindow {
    protected DBCSessionInternalWindowBrowser windowBrowser;
    protected Component mainComponent;
    private boolean closable = true;
    private boolean locked = false;
    private String title;
    private String id;
    private Icon icon;
    private DBCSessionView.Side side;

    public DBCInternalWindowImpl() {
        super(new BorderLayout());
        setMainComponent(new JLabel("..."));
        setTitle("NO_NAME");
//        setHeader(null, null);
        setIcon(null);
        addHierarchyListener(new HierarchyListener() {
            public void hierarchyChanged(HierarchyEvent e) {
                setLocked(isLocked());
            }
        });
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMainComponent(Component c) {
        if (this.mainComponent != null) {
            remove(this.mainComponent);
        }
        if (c != null) {
            add(c, BorderLayout.CENTER);
        }
        this.mainComponent = c;
        invalidate();
    }

    public Component getMainComponent() {
        return mainComponent;
    }

    public boolean isClosable() {
        return closable;
    }

    public void setClosable(boolean closable) {
        boolean old = this.closable;
        this.closable = closable;
        firePropertyChange(PROPERTY_CLOSEABLE, old, closable);
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        boolean old = this.locked;
        this.locked = locked;
        firePropertyChange(PROPERTY_LOCKED, old, locked);
    }

    public int getTabIndex() {
        return -1;
//        if (getTabbedPane() != null) {
//            return getTabbedPane().getindexOfComponent(DBCInternalWindowImpl.this);
//        } else {
//            return -1;
//        }
    }

    public void defaultAction() {
        setLocked(!isLocked());
    }

    public void setToolTipText(String tooltip) {
        firePropertyChange(PROPERTY_TOOLTIP, null, tooltip);
    }

    public String getTitle() {
        return title;
    }

    public void close() {
        firePropertyChange(PROPERTY_CLOSE, false, true);
    }


    public void setTitle(String name) {
        String old = this.title;
        this.title = name == null ? "NO_NAME" : name;
        firePropertyChange(PROPERTY_TITLE, old, this.title);
    }

    public JComponent getComponent() {
        return this;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public class DefaultHeaderComponent extends JToolBar {
        private JLabel label;

        public DefaultHeaderComponent() {
            setFloatable(false);
            label = new JLabel();
            add(label);
            add(Box.createHorizontalGlue());
        }

        public JLabel getLabel() {
            return label;
        }
    }

    public DBCSessionInternalWindowBrowser getWindowBrowser() {
        return windowBrowser;
    }

    public void setWindowBrowser(DBCSessionInternalWindowBrowser windowBrowser) {
        this.windowBrowser = windowBrowser;
    }

    public DBCSessionView.Side getSide() {
        return side;
    }

    public void setSide(DBCSessionView.Side side) {
        this.side = side;
    }

}
