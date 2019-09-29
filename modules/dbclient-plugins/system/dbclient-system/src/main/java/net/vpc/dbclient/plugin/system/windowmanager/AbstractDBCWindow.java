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
package net.vpc.dbclient.plugin.system.windowmanager;

import net.vpc.dbclient.api.DBCApplication;
import net.vpc.dbclient.api.DBCApplicationView;
import net.vpc.dbclient.api.DBCSession;
import net.vpc.dbclient.api.viewmanager.DBCPluggablePanel;
import net.vpc.dbclient.api.windowmanager.DBCWindow;
import net.vpc.dbclient.api.windowmanager.DBCWindowKind;
import net.vpc.dbclient.api.windowmanager.DBCWindowListener;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Vector;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 8 dec. 2006 10:56:29
 */
public abstract class AbstractDBCWindow<T> extends DBCPluggablePanel implements DBCWindow<T> {

    private Vector<DBCWindowListener> listeners;
    private JComponent component;
    private DBCWindowKind kind;
    private DBCSession session;
    private String title;
    private ImageIcon icon;
    private DBCApplication application;

    public AbstractDBCWindow() {
    }

    public void init(Component component, DBCWindowKind kind, DBCSession session, DBCApplication dbclient, String title, ImageIcon icon) {
        if (component instanceof JComponent) {
            this.component = (JComponent) component;
        } else {
            this.component = new JPanel();
            this.component.add(component);
        }
        this.component.putClientProperty(COMPONENT_PROPERTY, this);
        this.kind = kind;
        this.application = dbclient;
        this.session = session;
        this.title = title;
        this.icon = icon;
        if (this.title == null) {
            this.title = getApplication().getApplicationInfo().getProductLongTitle();
        }
        if (this.icon == null) {
            if (session == null) {
                dbclient.getView().addPropertyChangeListener(DBCApplicationView.PROPERTY_ARTSET, new PropertyChangeListener() {

                    public void propertyChange(PropertyChangeEvent evt) {
                        iconChanged();
                    }
                });
            } else {
                session.getView().addPropertyChangeListener(DBCApplicationView.PROPERTY_ARTSET, new PropertyChangeListener() {

                    public void propertyChange(PropertyChangeEvent evt) {
                        iconChanged();
                    }
                });
            }
            iconChanged();
        }
        setLayout(new BorderLayout());
        add(component, BorderLayout.CENTER);
    }

    private void iconChanged() {
        ImageIcon image = session != null ? session.getView().getArtSet().getArtImage(DBCApplicationView.ARTSET_ICON16) : application.getView().getArtSet().getArtImage(DBCApplicationView.ARTSET_ICON16);
        setIcon(image);
    }

    protected void fireWindowClosedListener() {
        if (listeners != null) {
            for (DBCWindowListener listener : listeners) {
                listener.windowClosed(this);
            }
        }
    }

    protected void fireWindowOpenedListener() {
        if (listeners != null) {
            for (DBCWindowListener listener : listeners) {
                listener.windowOpened(this);
            }
        }
    }

    public void addWindowListener(DBCWindowListener w) {
        if (listeners == null) {
            listeners = new Vector<DBCWindowListener>();
        }
        listeners.add(w);
    }

    public Component getComponent() {
        return this;
    }

    public DBCWindowKind getKind() {
        return kind;
    }

    public DBCSession getSession() {
        return session;
    }

    public String getTitle() {
        return title;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public DBCApplication getApplication() {
        return application;
    }

    public T getObject() {
        return (T) component;
    }
}
