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

package net.vpc.dbclient.api.sql.parser;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 28 juin 2007 10:15:37
 */
public final class TokenRichType {
    public static final TokenRichType UNKNOWN;
    public int group;
    public int type;
    private static Map<Long, TokenRichType> cached = new HashMap<Long, TokenRichType>();

    static {
        UNKNOWN = TokenRichType.valueOf(SQLTokenGroup.UNKNOWN, SQLTokenType.UNKNOWN);
    }

    public TokenRichType(int group) {
        this.group = group;
        this.type = SQLTokenType.UNKNOWN;
    }

    public TokenRichType(int group, int id) {
        this.group = group;
        this.type = id;
    }

    public static TokenRichType valueOf(int group) {
        return valueOf(group, SQLTokenType.UNKNOWN);
    }

    public static TokenRichType valueOf(int group, int type) {
        long id = (((long) group) << 32) + type;
        TokenRichType o = cached.get(id);
        if (o == null) {
            o = new TokenRichType(group, type);
            cached.put(id, o);
        }
        return o;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TokenRichType that = (TokenRichType) o;

        if (group != that.group) return false;
        if (type != that.type) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = group;
        result = 31 * result + type;
        return result;
    }
}
