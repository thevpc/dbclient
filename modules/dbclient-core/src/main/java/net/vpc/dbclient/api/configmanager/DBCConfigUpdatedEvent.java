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

package net.vpc.dbclient.api.configmanager;

import net.vpc.dbclient.api.DBCApplication;
import net.vpc.dbclient.api.DBCSession;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 7 févr. 2007 12:50:14
 */
public class DBCConfigUpdatedEvent {
    private DBCApplication dbclient;
    private DBCSession session;
    private String property;
    private Object value;

    public DBCConfigUpdatedEvent(DBCApplication dbclient, DBCSession session, String property, Object value) {
        this.dbclient = dbclient;
        this.session = session;
        this.property = property;
        this.value = value;
    }

    public DBCApplication getApplication() {
        return dbclient;
    }

    public String getProperty() {
        return property;
    }

    public DBCSession getSession() {
        return session;
    }

    public Object getValue() {
        return value;
    }
}
