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

package net.thevpc.dbclient.plugin.tool.searchengine;

import net.thevpc.dbclient.api.sql.objects.SQLObjectTypes;

import java.util.HashMap;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime  2006/11/07
 */
public class SearchResult {
    private String context;
    private SQLObjectTypes objectType;
    private String objectName;
    private String columnName;
    private Object value;
    private int row;

    public SearchResult() {
    }


    @Override
    public String toString() {
        HashMap<String,Object> m=new HashMap<String,Object>();
//        m.put("catalog",catalog);
//        m.put("schema",schema);
        m.put("name",objectName);
        m.put("type",objectType);
        m.put("context",context);
        m.put("row",row);
        m.put("value",value);
        return m.toString();
    }

    public SQLObjectTypes getObjectType() {
        return objectType;
    }

    public void setObjectType(SQLObjectTypes objectType) {
        this.objectType = objectType;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }


    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }


    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
}
