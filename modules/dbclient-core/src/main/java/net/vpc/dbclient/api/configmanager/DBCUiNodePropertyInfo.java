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
 * DO NOT EDIT MANUALLY
 * GENERATED AUTOMATICALLY BY JBGen (0.1)
 *
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @framework neormf (license GPL)
 */
public class DBCUiNodePropertyInfo extends DBCAbstractInfo implements Cloneable {
    public static final String UNP_CODE = "unpCode";
    public static final String UNP_PATH = "unpPath";
    public static final String UNP_SES_ID = "unpSesId";
    public static final String UNP_VALUE = "unpValue";

    /**
     * Constructor
     */
    public DBCUiNodePropertyInfo() {
    }

    /**
     * getter for unpCode
     */
    public String getUnpCode() {
        return ((String) map.get(UNP_CODE));
    }

    /**
     * setter for unpCode
     */
    public void setUnpCode(String value) {
        map.put(UNP_CODE, value);
    }

    /**
     * true if record contains the field unpCode
     */
    public boolean containsUnpCode() {
        return map.containsKey(UNP_CODE);
    }

    /**
     * remove the field unpCode
     */
    public DBCUiNodePropertyInfo unsetUnpCode() {
        map.remove(UNP_CODE);
        return this;
    }

    /**
     * getter for unpPath
     */
    public String getUnpPath() {
        return ((String) map.get(UNP_PATH));
    }

    /**
     * setter for unpPath
     */
    public void setUnpPath(String value) {
        map.put(UNP_PATH, value);
    }

    /**
     * true if record contains the field unpPath
     */
    public boolean containsUnpPath() {
        return map.containsKey(UNP_PATH);
    }

    /**
     * remove the field unpPath
     */
    public DBCUiNodePropertyInfo unsetUnpPath() {
        map.remove(UNP_PATH);
        return this;
    }

    /**
     * getter for unpSesId
     */
    public int getUnpSesId() {
        return ((Integer) map.get(UNP_SES_ID)).intValue();
    }

    /**
     * setter for unpSesId
     */
    public void setUnpSesId(int value) {
        map.put(UNP_SES_ID, new Integer(value));
    }

    /**
     * true if record contains the field unpSesId
     */
    public boolean containsUnpSesId() {
        return map.containsKey(UNP_SES_ID);
    }

    /**
     * remove the field unpSesId
     */
    public DBCUiNodePropertyInfo unsetUnpSesId() {
        map.remove(UNP_SES_ID);
        return this;
    }

    /**
     * getter for unpValue
     */
    public String getUnpValue() {
        return ((String) map.get(UNP_VALUE));
    }

    /**
     * setter for unpValue
     */
    public void setUnpValue(String value) {
        map.put(UNP_VALUE, value);
    }

    /**
     * true if record contains the field unpValue
     */
    public boolean containsUnpValue() {
        return map.containsKey(UNP_VALUE);
    }

    /**
     * remove the field unpValue
     */
    public DBCUiNodePropertyInfo unsetUnpValue() {
        map.remove(UNP_VALUE);
        return this;
    }

    public String getCombinedKey() {
        return getUnpSesId() + ";" + getUnpCode() + ";" + getUnpPath();
    }
}