/**
 * ====================================================================
 *             DBCLient yet another Jdbc client tool
 *
 * DBClient is a new Open Source Tool for connecting to jdbc
 * compliant relational databases. Specific extensions will take care of
 * each RDBMS implementation.
 *
 * Copyright (C) 2006-2007 Taha BEN SALAH
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

package net.thevpc.dbclient.api.sql;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 3 juil. 2006 09:56:14
 */
public class TypeDesc {
    private int sqlType;
    private Class javaType;
    private Class javaNativeType;
    private TypeWrapper wrapper;

    public TypeDesc(int sqlType, Class javaType, Class javaNativeType, TypeWrapper wrapper) {
        this.sqlType = sqlType;
        this.javaType = javaType;
        this.javaNativeType = javaNativeType;
        this.wrapper = wrapper;
    }

    public int getSqlType() {
        return sqlType;
    }

    public Class getJavaType() {
        return javaType;
    }

    public Class getJavaNativeType() {
        return javaNativeType;
    }

    public TypeWrapper getWrapper() {
        return wrapper;
    }
}
