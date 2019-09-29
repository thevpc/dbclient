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

package net.vpc.dbclient.api.sql.urlparser;

import net.vpc.prs.messageset.MessageSet;

import java.io.Serializable;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 1 juil. 2007 12:08:35
 */
public final class DBCDriverParameter implements Serializable, Comparable {
    private String id;
    private String label;
    private String defaultValue;
    private DBCDriverUrlParser.ParameterType type;
    private String[] allValues;
    private MessageSet messageSet;

    public DBCDriverParameter(String id, String label, String defaultValue, DBCDriverUrlParser.ParameterType type, String[] allValues, MessageSet mset) {
        this.id = id;
        this.label = label;
        this.defaultValue = defaultValue;
        this.type = type;
        this.allValues = allValues;
        this.messageSet = mset;
    }


    public String getId() {
        return id;
    }

    public String getLabel() {
        return messageSet == null ? (label == null ? id : label) : this.messageSet.get((label == null ? ("DriverUrlParser." + id) : label));
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public DBCDriverUrlParser.ParameterType getType() {
        return type;
    }

    public String[] getAllValues() {
        return allValues;
    }


    public int compareTo(Object o) {
        if (o instanceof DBCDriverParameter) {
            DBCDriverParameter p = (DBCDriverParameter) o;
            return id.compareTo(p.id);
        }
        return -10;
    }

    @Override
    public boolean equals(Object obj) {
        return compareTo(obj) == 0;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
