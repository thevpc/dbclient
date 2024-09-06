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

package net.thevpc.dbclient.plugin.system.sql.parser;

import net.thevpc.dbclient.api.sql.parser.SQLParser;
import net.thevpc.dbclient.api.sql.parser.SQLTokenType;
import net.thevpc.dbclient.api.sql.parser.TokenRichType;
import net.thevpc.dbclient.api.sql.parser.WordTokenResolver;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.TreeSet;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 28 juil. 2006 19:41:52
 */
public class CollectionWordTokenResolver implements WordTokenResolver {
    private Map<String, Integer> idMap;
    private int type;
    private TreeSet<String> all;
    TokenRichType tokenRichType;

    public CollectionWordTokenResolver(int type, Map<String, Integer> idMap, String... all) {
        this(type, idMap, Arrays.asList(all));
    }

    public CollectionWordTokenResolver(int type, Map<String, Integer> idMap, Collection<String> all) {
        this.type = type;
        this.idMap = idMap == null ? new HashMap<String, Integer>() : idMap;
        this.all = new TreeSet<String>();
        for (String s : all) {
            this.all.add(s.toLowerCase());
        }
        tokenRichType = TokenRichType.valueOf(type, SQLTokenType.UNKNOWN);
    }

    public TokenRichType resolve(String word, int supposedType, SQLParser parser) throws IOException {
        String lower = word.toLowerCase();
        if (all.contains(lower)) {
            Integer i = idMap.get(lower);
            return i == null ? tokenRichType : TokenRichType.valueOf(type, i);
        }
        return null;
    }
}
