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

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 1 aout 2006 23:52:12
 */
public class SQLName {
    private SQLToken[] tokens;

    public SQLName(SQLToken[] tokens) {
        this.tokens = tokens;
    }

    public String toSQL() {
        StringBuilder sb = new StringBuilder();
        SQLToken[] names = getNames();
        for (int i = 0; i < names.length; i++) {
            SQLToken name = names[i];
            if (name != null && name.length() > 0) {
                if (sb.length() > 0) {
                    sb.append(".");
                }
                sb.append(name.toSQL());
            } else if (i == (names.length - 1) && (name == null || name.length() == 0)) {
                if (sb.length() > 0) {
                    sb.append(".");
                }
            }
        }
        return sb.toString();
    }

    public SQLToken[] getNames() {
        ArrayList<SQLToken> t = new ArrayList<SQLToken>();
        for (int i = 0; i < tokens.length; i++) {
            SQLToken token = tokens[i];
            if (token != null && !token.accept(SQLTokenGroup.DOT)) {
                t.add(token);
            }
        }
        if (tokens.length > 0 && (tokens[0] != null && tokens[0].accept(SQLTokenGroup.DOT))) {
            t.add(0, null);
        }
        if (tokens.length > 1 && (tokens[tokens.length - 1] != null && tokens[tokens.length - 1].accept(SQLTokenGroup.DOT))) {
            t.add(null);
        }
        return t.toArray(new SQLToken[t.size()]);
    }

    public SQLName getNewName(SQLName aliasName) {
        ArrayList<SQLToken> all = new ArrayList<SQLToken>();
        if (aliasName != null) {
            for (SQLToken sqlToken : aliasName.getTokens()) {
                all.add(sqlToken);
            }
            all.add(new SQLToken(-1, SQLTokenGroup.DOT, "."));
            for (int i = 1; i < tokens.length; i++) {
                all.add(tokens[i]);
            }
            return new SQLName(all.toArray(new SQLToken[all.size()]));
        } else {
            return this;
        }
    }

    public SQLToken getAlias() {
        SQLToken[] x = getNames();
        return x.length == 2 ? x[0] : null;
    }

    public SQLToken[] getParents() {
        SQLToken[] x = getNames();
        if (x.length > 1) {
            SQLToken[] y = new SQLToken[x.length - 1];
            System.arraycopy(x, 0, y, 0, y.length);
            return y;
        }
        return new SQLToken[0];
    }

    public SQLToken getName() {
        SQLToken[] x = getNames();
        return x.length == 0 ? null : x[x.length - 1];
    }

    public SQLToken[] getTokens() {
        return tokens;
    }

    @Override
    public String toString() {
        return String.valueOf(tokens == null ? null : Arrays.asList(tokens));
    }

//    public String getCatalogName() {
//
//    }
//
//    public String getSchemaName() {
//
//    }
//
//    public String getObjectName() {
//
//    }

}
