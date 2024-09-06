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
package net.thevpc.dbclient.plugin.sql.jstsql.evaluator;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 10 avr. 2007 17:27:27
 */
public class JSTSqlTemplateCacheDB implements Serializable {
    private static final long serialVersionUID = -1010101010101000002L;
    private Map<String, JSTSqlTemplateCache> cache = new HashMap<String, JSTSqlTemplateCache>();
    private int index = 1;

    public JSTSqlTemplateCacheDB() {
    }

    public static JSTSqlTemplateCacheDB load(File cachesFile) {
        if (cachesFile.exists()) {
            try {
                ObjectInputStream ois = null;
                try {
                    ois = new ObjectInputStream(new FileInputStream(cachesFile));
                    JSTSqlTemplateCacheDB t = (JSTSqlTemplateCacheDB) ois.readObject();
                    return t;
                } finally {
                    if (ois != null) {
                        ois.close();
                    }
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void store(File cachesFile) {
        try {
            ObjectOutputStream oos = null;
            try {
                oos = new ObjectOutputStream(new FileOutputStream(cachesFile));
                oos.writeObject(this);
            } finally {
                if (oos != null) {
                    oos.close();
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public JSTSqlTemplateCache get(String id) {
        return cache.get(id);
    }

    public void set(JSTSqlTemplateCache template) {
        cache.put(template.getId(), template);
    }

    public String nextClassName() {
        String name = "TempJstsql" + index;
        index++;
        return name;
    }

}
