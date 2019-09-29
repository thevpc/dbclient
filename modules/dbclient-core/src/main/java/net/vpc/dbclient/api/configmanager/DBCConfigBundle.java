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

import net.vpc.prs.messageset.AbstractMessageSetBundle;

import java.util.*;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 11 sept. 2007 21:42:52
 */
public class DBCConfigBundle extends AbstractMessageSetBundle {
    private DBCApplicationConfig config;
    private Map<String, String> allMessages;

    public DBCConfigBundle(DBCApplicationConfig config) {
        this.config = config;
    }


    private synchronized Map<String, String> loadMessages() {
        if (allMessages == null) {
            allMessages = new HashMap<String, String>();
            for (DBCMessageInfo dbcMessageInfo : config.getMessages()) {
                allMessages.put(dbcMessageInfo.getCombinedKey(), dbcMessageInfo.getMsgData());
            }
        }
        return allMessages;
    }

    public String getString(String id) throws MissingResourceException {
        if (id.startsWith("=")) {
            return id;
        } else if (id.startsWith("\\=")) {
            return getString0(id.substring(1));
        } else {
            return getString0(id);
        }
    }

    public String getString0(String id) throws MissingResourceException {
        loadMessages();
        Locale locale = getLocale();
        try {
            TreeSet<String> tried = new TreeSet<String>();
            tried.add(locale.toString());
            tried.add(new Locale(locale.getLanguage(), locale.getCountry(), "").toString());
            tried.add(new Locale(locale.getLanguage(), "", "").toString());
            tried.add("*");
            for (String s : tried) {
                String m = allMessages.get(DBCMessageInfo.getCombinedKey(id, s));
                if (m != null) {
                    return m;
                }
            }
            throw new MissingResourceException("Key not found '" + id + "' in " + this, getClass().getName(), id);
        } catch (MissingResourceException e) {
            throw e;
        } catch (Throwable e) {
            throw new MissingResourceException("Key not found '" + id + "' in " + this, getClass().getName(), id);
        }
    }

    public String toString() {
        return "DBCBundle";
    }

    public void revalidate() {
        allMessages = null;
    }
}
