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

package net.vpc.dbclient.api.windowmanager;

import net.vpc.dbclient.api.DBCApplication;
import net.vpc.dbclient.api.DBCSession;
import net.vpc.prs.plugin.Extension;
import net.vpc.dbclient.api.viewmanager.DBCComponent;

import javax.swing.*;
import java.awt.*;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 8 dec. 2006 10:55:15
 */
@Extension(group = "ui")
public interface DBCWindow<T> extends DBCComponent {
    public static final String COMPONENT_PROPERTY = "DBCWindow";

    void init(Component component, DBCWindowKind kind, DBCSession session, DBCApplication dbclient, String title, ImageIcon icon);

    void pack();
    
    void showWindow();

    void closeWindow();

    void hideWindow();

    void addWindowListener(DBCWindowListener listener);

    Component getTopLevelComponent();

    T getObject();

    DBCWindowKind getKind();

    DBCSession getSession();

    String getTitle();

    void setTitle(String title);

    ImageIcon getIcon();

    DBCApplication getApplication();

    boolean isVisible();

    void setIcon(ImageIcon icon);
}
