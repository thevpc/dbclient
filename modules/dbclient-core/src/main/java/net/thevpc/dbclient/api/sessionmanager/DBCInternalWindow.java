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

package net.thevpc.dbclient.api.sessionmanager;

import net.thevpc.common.prs.plugin.Extension;
import net.thevpc.dbclient.api.viewmanager.DBCComponent;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 13 juil. 2005 14:32:52
 */
@Extension(group = "ui.session")
public interface DBCInternalWindow extends DBCComponent {
    public static final String PROPERTY_LOCKED = "PROPERTY_LOCKED";
    public static final String PROPERTY_TITLE = "PROPERTY_TITLE";
    public static final String PROPERTY_CLOSEABLE = "PROPERTY_CLOSEABLE";
    public static final String PROPERTY_CLOSE = "PROPERTY_CLOSE";
    public static final String PROPERTY_TOOLTIP = "PROPERTY_TOOLTIP";

    public void setMainComponent(Component c);

    public Component getMainComponent();

    public boolean isClosable();

    public void setClosable(boolean closable);

    public boolean isLocked();

    public void setSide(DBCSessionView.Side side);

    public DBCSessionView.Side getSide();

    public void setLocked(boolean locked);

    public int getTabIndex();

    public void close();

    public void defaultAction();

    public void setToolTipText(String tooltip);

    public String getTitle();

    public String getId();

    public void setTitle(String name);

    public Icon getIcon();

    public void addPropertyChangeListener(PropertyChangeListener listener);

    public void addPropertyChangeListener(String roperty, PropertyChangeListener listener);

    public void removePropertyChangeListener(PropertyChangeListener listener);

    public void removePropertyChangeListener(String roperty, PropertyChangeListener listener);

    void setIcon(Icon icon);

    public DBCSessionInternalWindowBrowser getWindowBrowser();

    public void setWindowBrowser(DBCSessionInternalWindowBrowser windowBrowser);
}
