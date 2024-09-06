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

import net.thevpc.dbclient.api.sql.parser.SQLPortion;
import net.thevpc.dbclient.api.sql.parser.SQLToken;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;


/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 21 aout 2006 22:08:21
 */
public class DefaultSQLPortion implements SQLPortion {
    private SQLToken[] tokens;
    private int globalOffset;
    private int offset;
    private int length;
    private Collection<SQLPortion> subPortions;

    public DefaultSQLPortion(SQLToken[] tokens, int offset, int length, int globalOffset, Collection<SQLPortion> subPortions) {
        this.tokens = tokens;
        this.offset = offset;
        this.length = length;
        this.globalOffset = globalOffset;
        this.subPortions = subPortions;
    }


    public Iterator<SQLToken> iterator() {
        SQLToken[] tokens0 = new SQLToken[length];
        System.arraycopy(tokens, offset, tokens0, 0, length);
        return Arrays.asList(tokens0).iterator();
    }

    public int getCharEndIndex() {
        return tokens[length - 1].getCharEndIndex();
    }

    public int getCharStartIndex() {
        return tokens[0].getCharStartIndex();
    }

    public String toSQL() {
        StringBuilder sb = new StringBuilder();
        for (SQLToken sqlToken : this) {
            if ((sqlToken.getDecoration() & SQLToken.DECORATION_TRAILING) == 0) {
                sb.append(sqlToken.getValue());
            }
        }
        return sb.toString();
    }

    public int getTokenEndIndex() {
        return globalOffset + length;
    }

    public int getTokenStartIndex() {
        return globalOffset;
    }

    public Collection<SQLPortion> getSubPortions() {
        return subPortions;
    }
}
