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

import net.thevpc.dbclient.api.sql.parser.TokenRichType;
import net.thevpc.dbclient.api.sql.parser.UnvalidTokenException;

import java.io.IOException;


/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 28 juil. 2006 19:41:11
 */
public abstract class DefaultTokenResolver implements TokenResolver {
    private int theType;
    TokenRichType richType;

    public DefaultTokenResolver(int theType) {
        this.theType = theType;
        richType = TokenRichType.valueOf(theType);
    }

    public abstract boolean accept(char c, StringBuilder currentChars) throws IOException;

    public TokenRichType resolve(StringBuilder currentChars, DefaultSQLParser defaultSQLParser) throws IOException {
        char c = defaultSQLParser.readChar();
        if (accept(c, currentChars)) {
            currentChars.append(c);
            try {
                while ((c = defaultSQLParser.readChar()) != -1 && !defaultSQLParser.isEof() && accept(c, currentChars)) {
                    currentChars.append(c);
                }
                defaultSQLParser.unreadChar(c);
            } catch (UnvalidTokenException e) {
                for (int j = currentChars.length() - 1; j >= 0; j++) {
                    defaultSQLParser.unreadChar(currentChars.charAt(j));
                }
                currentChars.delete(0, currentChars.length());
                return null;
            }
            return richType;
        }
        defaultSQLParser.unreadChar(c);
        return null;
    }

}
