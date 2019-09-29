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

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 */
public class DBCConfigPropInfo extends DBCAbstractInfo implements Cloneable {
    public static final String CPR_NAME = "cprName";
    public static final String CPR_VALUE = "cprValue";

    /**
     * Constructor
     */
    public DBCConfigPropInfo() {
    }

    /**
     * getter for msgId
     */
    public String getCprName() {
        return ((String) map.get(CPR_NAME));
    }

    /**
     * setter for msgId
     */
    public void setCprName(String value) {
        map.put(CPR_NAME, value);
    }

    /**
     * true if record contains the field msgId
     */
    public boolean containsCprName() {
        return map.containsKey(CPR_NAME);
    }

    /**
     * remove the field msgId
     */
    public DBCConfigPropInfo unsetCprName() {
        map.remove(CPR_NAME);
        return this;
    }

    /**
     * getter for msgLocName
     */
    public String getCprValue() {
        return ((String) map.get(CPR_VALUE));
    }

    /**
     * setter for msgLocName
     */
    public void setCprValue(String value) {
        map.put(CPR_VALUE, value);
    }

    /**
     * true if record contains the field msgLocName
     */
    public boolean containsCprValue() {
        return map.containsKey(CPR_VALUE);
    }

    /**
     * remove the field msgLocName
     */
    public DBCConfigPropInfo unsetCprValue() {
        map.remove(CPR_VALUE);
        return this;
    }

}