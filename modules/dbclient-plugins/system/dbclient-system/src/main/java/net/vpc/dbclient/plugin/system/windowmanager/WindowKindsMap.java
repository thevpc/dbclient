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

import net.vpc.dbclient.api.windowmanager.DBCWindow;
import net.vpc.dbclient.api.windowmanager.DBCWindowKind;
import net.vpc.dbclient.api.windowmanager.DBCWindowListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 2 janv. 2007 16:53:01
 */
public class WindowKindsMap {
    Map<DBCWindowKind, ArrayList<DBCWindow>> map = new HashMap<DBCWindowKind, ArrayList<DBCWindow>>();

    public void add(DBCWindow window) {
        ArrayList<DBCWindow> list = map.get(window.getKind());
        if (list == null) {
            list = new ArrayList<DBCWindow>();
            map.put(window.getKind(), list);
        }
        list.add(window);
        window.addWindowListener(new DBCWindowListener() {
            public void windowClosed(DBCWindow window) {
                remove(window);
            }

            public void windowHidden(DBCWindow window) {
            }

            public void windowOpened(DBCWindow window) {
            }
        });

    }

    private void remove(DBCWindow window) {
        ArrayList<DBCWindow> list = map.get(window.getKind());
        if (list != null) {
            list.remove(window);
        }
    }

    public DBCWindow[] getWindows(DBCWindowKind kind) {
        ArrayList<DBCWindow> list = map.get(kind);
        if (list == null) {
            list = new ArrayList<DBCWindow>();
        }
        return list.toArray(new DBCWindow[list.size()]);
    }
}
