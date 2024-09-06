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

import net.thevpc.dbclient.api.sql.parser.SQLTokenGroup;

import java.util.Collection;
import java.util.Map;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 29 juil. 2006 00:13:42
 */
public class DatatypeCollectionConvertChecker extends CollectionWordTokenResolver {
    public DatatypeCollectionConvertChecker(Map<String, Integer> idMap, Collection<String> all) {
        super(SQLTokenGroup.DATATYPE, idMap, all);
    }

    public DatatypeCollectionConvertChecker(Map<String, Integer> idMap, String... all) {
        super(SQLTokenGroup.DATATYPE, idMap, all);
    }

//    public boolean accept(String word, int supposedType) throws IOException {
//        return supposedType== SQLToken.VAR && super.accept(word, supposedType);
//    }
}
