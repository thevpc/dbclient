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

package net.vpc.dbclient.plugin.sql.jstsql.evaluator;

import java.io.File;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 4 dec. 2006 01:30:47
 */
public class JSTSqlTemplateCacheManagerImpl implements JSTSqlTemplateCacheManager {
    private static final Object lock = new Object();
    private File cachesFile;
    private JSTSqlTemplateCacheDB cache;

    public JSTSqlTemplateCacheManagerImpl(File root) {
        cachesFile = new File(root, "cache.db");
    }

    public JSTSqlTemplateCacheDB getCache() {
        synchronized (lock) {
            if (cache == null) {
                cache = JSTSqlTemplateCacheDB.load(cachesFile);
                if (cache == null) {
                    cache = new JSTSqlTemplateCacheDB();
                }
            }
        }
        return cache;
    }

    public void storeCache() {
        synchronized (lock) {
            if (cache != null) {
                cache.store(cachesFile);
            }
        }
    }

    public JSTSqlTemplateCache loadCache(JSTSqlEvalContext context) {
        String hash = context.getHashString();
        return getCache().get(hash);
    }

    public void saveCache(JSTSqlTemplateCache cache) {
        getCache().set(cache);
        storeCache();
    }

    public String nextClassName() {
        String name = getCache().nextClassName();
        storeCache();
        return name;
    }
}
