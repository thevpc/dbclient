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

package net.thevpc.dbclient.api.sql.objects;

import net.thevpc.dbclient.api.pluginmanager.DBCPluggable;
import net.thevpc.dbclient.api.sql.DBCConnection;
import net.thevpc.dbclient.api.sql.DBObjectFilter;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 13 juil. 2005 14:32:52
 */
public interface DBObject extends DBCPluggable {

    public DBObject getChild(int i);

    public int getChildCount();

    public int getIndexOfChild(DBObject obj);

    public boolean isLeaf();

    public SQLObjectTypes getType();

    public boolean isLoaded();

    public String getSQL();

    public String getDropSQL();


    public String getStringPath();

    public DBObject[] getTreePath();


    /**
     * return parent Table, View or Procedure Name if column
     * return null otherwise
     *
     * @return parent Table, View or Procedure Name if column or null otherwise
     */
    public String getParentName();

    public String getName();

    public String getFullName();

    public String getCatalogName();

    public String getSchemaName();

    DBObject getParentObject();

    String getId();


    void ensureLoading();

    boolean isSystemObject();

    void setSystemObject(boolean system);

    public String getNativeType();

    public void setNativeType(String nativeType);

    public void setParentObject(DBObject node);

    void invalidate();

    DBObjectFilter.Status getStatus();

    void setStatus(DBObjectFilter.Status status);

    public DBCConnection getConnection();
}
