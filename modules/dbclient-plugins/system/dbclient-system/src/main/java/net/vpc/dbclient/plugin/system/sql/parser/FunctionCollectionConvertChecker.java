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

package net.vpc.dbclient.plugin.system.sql.parser;

import net.vpc.dbclient.api.sql.parser.SQLParser;
import net.vpc.dbclient.api.sql.parser.SQLTokenGroup;
import net.vpc.dbclient.api.sql.parser.TokenRichType;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 29 juil. 2006 00:13:42
 */
public class FunctionCollectionConvertChecker extends CollectionWordTokenResolver {
    public FunctionCollectionConvertChecker(Map<String, Integer> idMap, Collection<String> all) {
        super(SQLTokenGroup.FUNCTION, idMap, all);
    }

    public FunctionCollectionConvertChecker(Map<String, Integer> idMap, String... all) {
        super(SQLTokenGroup.FUNCTION, idMap, all);
    }

    @Override
    public TokenRichType resolve(String word, int supposedType, SQLParser parser) throws IOException {
        if (supposedType == SQLTokenGroup.FUNCTION) {
            return super.resolve(word, supposedType, parser);
        }
        return null;
    }
}
