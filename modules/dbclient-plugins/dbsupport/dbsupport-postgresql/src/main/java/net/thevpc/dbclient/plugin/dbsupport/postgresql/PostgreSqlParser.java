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

package net.thevpc.dbclient.plugin.dbsupport.postgresql;

import net.thevpc.dbclient.plugin.system.sql.parser.DefaultSQLParser;

import java.io.Reader;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 20 avr. 2007 17:51:39
 */
public class PostgreSqlParser extends DefaultSQLParser {

    public PostgreSqlParser(String query, int pushbackBuffer) {
        super(query, pushbackBuffer);
    }

    public PostgreSqlParser(Reader reader, int pushbackBuffer) {
        super(reader, pushbackBuffer);
    }

    public PostgreSqlParser() {
    }

    public PostgreSqlParser(int pushbackBuffer) {
        super(pushbackBuffer);
    }

    public PostgreSqlParser(String query) {
        super(query);
    }

    public PostgreSqlParser(Reader reader) {
        super(reader);
    }

//    public SQLStatement readStatement() {
//        ArrayList<SQLToken> found = new ArrayList<SQLToken>();
//        SQLToken c;
//        int statementType = SQLStatement.UNKNOWN;
//        int beginCount = 0;
//        int firstTokenIndex = currentTokenIndex;
//        boolean nonEmptyStatement = false;
//        LabelStopReading:
//        while ((c = readToken()) != null) {
//            found.add(c);
//            if (c.getGroup() == SQLTokenGroup.SEPARATOR) {
//                if (beginCount == 0 && c.getValue().equals(";")) {
//                    c.addDecoration(SQLToken.DECORATION_TRAILING, null);
////                    found.remove(found.size() - 1);
//                    break;
//                }
//            } else if (c.getGroup() == SQLTokenGroup.KEYWORD && (c.getValue().equalsIgnoreCase(getBeginKeyWord()))) {
//                beginCount++;
//            } else if (c.getGroup() == SQLTokenGroup.KEYWORD && (c.getValue().equalsIgnoreCase(getEndKeyWord()))) {
//                beginCount--;
//                if (beginCount <= 0) {
//                    break;
//                }
//            } else if (c.getGroup() == SQLTokenGroup.KEYWORD && (c.getValue().equalsIgnoreCase(getGoKeyWord()))) {
//                break;
//            } else if (c.getGroup() != SQLTokenGroup.WHITE && c.getGroup() != SQLTokenGroup.COMMENTS) {
//                nonEmptyStatement = true;
//                String v = c.getValue().toLowerCase();
//                switch (statementType) {
//                    case SQLStatement.UNKNOWN: {
//                        if (c.getGroup() == SQLTokenGroup.KEYWORD) {
//                            if ("select".equals(v)) {
//                                statementType = SQLTokenType.SELECT;
//                            } else if ("update".equals(v)) {
//                                statementType = SQLTokenType.UPDATE;
//                            } else if ("delete".equals(v)) {
//                                statementType = SQLTokenType.DELETE;
//                            } else if ("insert".equals(v)) {
//                                statementType = SQLTokenType.INSERT;
//                            } else if ("drop".equals(v)) {
//                                statementType = SQLTokenType.DROP;
//                            } else if ("create".equals(v)) {
//                                statementType = SQLTokenType.CREATE;
//                            } else if ("alter".equals(v)) {
//                                statementType = SQLStatement.ALTER;
//                            } else {
//                                statementType = SQLTokenType.OTHER;
//                            }
//                        }
//                        break;
//                    }
//                    case SQLTokenType.CREATE
//                            : {
//                        if (beginCount == 0 && c.getGroup() == SQLTokenGroup.KEYWORD && v.equals(getGoKeyWord())) {
//                            //remove go word
//                            c.addDecoration(SQLToken.DECORATION_TRAILING, null);
////                            found.remove(found.size() - 1);
//                            break LabelStopReading;
//                        }
////                            if (beginCount == 0 && c.getType() == SQLToken.KEYWORD && (
////                                    "update".equals(v)
////                                            || "delete".equals(v)
////                                            || "insert".equals(v)
////                                            || "drop".equals(v)
////                                            || "insert".equals(v)
////                                            || "create".equals(v)
////                            )) {
////                                found.remove(found.size() - 1);
////                                unreadToken(c);
////                                break LabelStopReading;
////                            }
//                        break;
//                    }
////                    case SQLStatement.UPDATE:
////                    case SQLStatement.ALTER:
////                    case SQLStatement.DELETE:
////                    case SQLStatement.INSERT:
////                    case SQLStatement.DROP:
////                    case SQLStatement.SELECT:
////                    case SQLStatement.CREATE
//                    default: {
//                        if (beginCount == 0 && c.getGroup() == SQLTokenGroup.KEYWORD && v.equals(getGoKeyWord())) {
//                            //remove go word
//                            c.addDecoration(SQLToken.DECORATION_TRAILING, null);
////                            found.remove(found.size() - 1);
//                            break LabelStopReading;
//                        }
//                        if (beginCount == 0 && c.getGroup() == SQLTokenGroup.KEYWORD && (
//                                "update".equals(v)
//                                        || "delete".equals(v)
//                                        || "insert".equals(v)
//                                        || "drop".equals(v)
//                                        || "insert".equals(v)
//                                        || "create".equals(v)
//                        )) {
//                            found.remove(found.size() - 1);
//                            unreadToken(c);
//                            break LabelStopReading;
//                        }
//                        break;
//                    }
//                }
//            }
//        }
//        nonEmptyStatement = found.size() > 0;
//        if (nonEmptyStatement && found.size() > 0) {
//            currentTokenIndex += found.size();
//        }
//        return nonEmptyStatement ? createStatement(statementType, found, firstTokenIndex) : null;
//    }

}
