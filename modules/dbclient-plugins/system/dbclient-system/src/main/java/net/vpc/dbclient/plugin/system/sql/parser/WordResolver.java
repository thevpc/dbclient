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

import net.vpc.dbclient.api.sql.parser.SQLTokenGroup;
import net.vpc.dbclient.api.sql.parser.SQLTokenType;
import net.vpc.dbclient.api.sql.parser.TokenRichType;
import net.vpc.dbclient.api.sql.parser.WordTokenResolver;

import java.io.IOException;


/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 28 juil. 2006 19:42:03
 */
public class WordResolver implements TokenResolver {

    public WordResolver() {
    }

    public boolean accept(char c, StringBuilder currentChars, DefaultSQLParser defaultSQLParser) throws IOException {
        int len = currentChars.length();
        if (len == 0) {
            return Character.isLetter(c) || c == '_' || c == '@' || c == '#';
        }
        return Character.isLetterOrDigit(c) || defaultSQLParser.getExtraNameCharacters().indexOf(c) >= 0
                || c == '_' || c == '@' || c == '#';
        // || c == '{' || c == '}';
        /*|| c == '.'*/
    }

    public TokenRichType resolve(StringBuilder currentChars, DefaultSQLParser parser) throws IOException {
        char c = parser.readChar();
        if (accept(c, currentChars, parser)) {
            int type = SQLTokenGroup.VAR;
            currentChars.append(c);
            while ((c = parser.readChar()) != -1 && !parser.isEof() && accept(c, currentChars, parser)) {
                currentChars.append(c);
            }
            if (c == '(') {
                type = SQLTokenGroup.FUNCTION;
            }
            parser.unreadChar(c);
            String sbstr = currentChars.toString().toUpperCase();
            for (WordTokenResolver wordLikeConvertChecker : parser.getWordTokenResolvers()) {
                TokenRichType tokenRichType = wordLikeConvertChecker.resolve(sbstr, type, parser);
                if (tokenRichType != null) {
                    return tokenRichType;
                }
            }
            return TokenRichType.valueOf(type);
        } else if (parser.getIdentifierQuoteString().length() > 0) {
            String q = parser.getIdentifierQuoteString();
            int ii = q.indexOf(c);
            if (ii >= 0) {
                char quoteStart = q.charAt(ii);
                char quoteEnd = quoteStart;
                if (ii + 1 > q.length()) {
                    quoteEnd = q.charAt(ii + 1);
                }
                boolean lastAdded = false;
                currentChars.append(c);
                while ((c = parser.readChar()) != -1 && !parser.isEof()) {
                    currentChars.append(c);
                    if (c == quoteEnd) {
                        c = parser.readChar();
                        if (c == quoteEnd) {
                            currentChars.append(c);
                        } else {
                            lastAdded = true;
                            parser.unreadChar(c);
                            break;
                        }
                    }
                }
                String s = currentChars.toString().toUpperCase();
                String sbstr = lastAdded ? s.substring(1, s.length() - 1) : s.substring(1, s.length());
                int type = SQLTokenGroup.VAR;
                if (sbstr.length() > 0) {
                    for (WordTokenResolver wordLikeConvertChecker : parser.getWordTokenResolvers()) {
                        TokenRichType tt = wordLikeConvertChecker.resolve(sbstr, type, parser);
                        if (tt != null) {
                            return tt;
                        }
                    }
                }
                return TokenRichType.valueOf(type, SQLTokenType.UNKNOWN);
            }
        }
        parser.unreadChar(c);
        return TokenRichType.UNKNOWN;
    }

}
