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

package net.thevpc.dbclient.api.viewmanager;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 9 févr. 2007 00:49:11
 */
public class DBCNamedFormat {
    private String id;
    private int code;
    private String name;
    private DBCComponentFormat componentFormat;
//    public DBCComponentFormat f0;

    public DBCNamedFormat(int code, String id, String name) {
        this.id = id;
        this.name = name;
        this.code = code;
        setFormat(null);
    }

    public int getCode() {
        return code;
    }

    public DBCComponentFormat getComponentFormat() {
        return componentFormat;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public void setFormat(DBCComponentFormat f) {
//        this.f0 = f;
        this.componentFormat = f == null ? new DBCComponentFormat() : f.clone();
    }
}
