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

package net.thevpc.dbclient.api.configmanager;

import java.util.HashMap;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 */
public class DBCMessageInfo extends DBCAbstractInfo implements Cloneable {
    public static final String MSG_ID = "msgId";
    public static final String MSG_LOC_NAME = "msgLocName";
    public static final String MSG_DATA = "msgData";

    /**
     * Constructor
     */
    public DBCMessageInfo() {
        map = new HashMap();
    }

    /**
     * getter for msgId
     */
    public String getMsgId() {
        return ((String) map.get(MSG_ID));
    }

    /**
     * setter for msgId
     */
    public void setMsgId(String value) {
        map.put(MSG_ID, value);
    }

    /**
     * true if record contains the field msgId
     */
    public boolean containsMsgId() {
        return map.containsKey(MSG_ID);
    }

    /**
     * remove the field msgId
     */
    public DBCMessageInfo unsetMsgId() {
        map.remove(MSG_ID);
        return this;
    }

    /**
     * getter for msgLocName
     */
    public String getMsgLocName() {
        return ((String) map.get(MSG_LOC_NAME));
    }

    /**
     * setter for msgLocName
     */
    public void setMsgLocName(String value) {
        map.put(MSG_LOC_NAME, value);
    }

    /**
     * true if record contains the field msgLocName
     */
    public boolean containsMsgLocName() {
        return map.containsKey(MSG_LOC_NAME);
    }

    /**
     * remove the field msgLocName
     */
    public DBCMessageInfo unsetMsgLocName() {
        map.remove(MSG_LOC_NAME);
        return this;
    }

    /**
     * getter for msgData
     */
    public String getMsgData() {
        return ((String) map.get(MSG_DATA));
    }

    /**
     * setter for msgData
     */
    public void setMsgData(String value) {
        map.put(MSG_DATA, value);
    }

    /**
     * true if record contains the field msgData
     */
    public boolean containsMsgData() {
        return map.containsKey(MSG_DATA);
    }

    /**
     * remove the field msgData
     */
    public DBCMessageInfo unsetMsgData() {
        map.remove(MSG_DATA);
        return this;
    }

    public String getCombinedKey() {
        return getCombinedKey((String) map.get(MSG_ID), (String) map.get(MSG_LOC_NAME));
    }

    public static String getCombinedKey(String id, String name) {
        Object k0 = id;
        if (k0 == null) {
            return null;
        }
        Object k1 = name;
        if (k1 == null) {
            return null;
        }
        return k0 + ";" + k1;
    }

}