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
import net.thevpc.dbclient.api.sql.parser.TokenRichType;

import java.io.IOException;


/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 28 juil. 2006 19:41:44
 */
public class JSTSQLResolver implements TokenResolver {

    private TokenRichType richtype = TokenRichType.valueOf(SQLTokenGroup.SCRIPT);

    public JSTSQLResolver() {
    }

    public TokenRichType resolve(StringBuilder currentChars, DefaultSQLParser defaultSQLParser) throws IOException {
        char c = defaultSQLParser.readChar();
        if (c == '<') {
            c = defaultSQLParser.readChar();
            if (c == '%') {
                boolean ending = false;
                currentChars.append("<%");
//                end++;
//                end++;
                while (true) {
                    c = defaultSQLParser.readChar();
                    if (defaultSQLParser.isEof()) {
                        break;
                    }
                    currentChars.append(c);
//                    end++;
                    if (c == '%') {
                        ending = true;
                    } else if (c == '>' && ending) {
                        break;
                    } else {
                        ending = false;
                    }
                }
                return richtype;
            }
            defaultSQLParser.unreadChar(c);
            defaultSQLParser.unreadChar('<');
            return null;
        }
        defaultSQLParser.unreadChar(c);
        return null;
    }

}
