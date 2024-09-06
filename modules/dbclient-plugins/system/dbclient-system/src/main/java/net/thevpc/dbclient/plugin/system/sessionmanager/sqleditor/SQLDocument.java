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

package net.thevpc.dbclient.plugin.system.sessionmanager.sqleditor;

import net.thevpc.dbclient.api.sql.parser.SQLParser;
import net.thevpc.dbclient.api.sql.parser.SQLStatement;
import net.thevpc.dbclient.api.sql.parser.SQLToken;
import net.thevpc.dbclient.api.sql.util.SQLUtils;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 30 juil. 2006 15:31:20
 */
public class SQLDocument extends PlainDocument {
    protected SQLToken[] cachedTokens;
    protected SQLStatement[] cachedStatements;
    protected SQLParser parser;

    public SQLDocument(SQLParser parser) {
        this.parser = parser;
        addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                clearCache();
            }

            public void removeUpdate(DocumentEvent e) {
                clearCache();
            }

            public void changedUpdate(DocumentEvent e) {
                clearCache();
            }
        });
    }

    public synchronized void clearCache() {
        cachedTokens = null;
    }

//    public synchronized SQLToken findFirstToken(int i, int[] ignoredTypes, int[] requiredTypes, boolean forward) {
//        int index = getTokenIndexByCharIndex(i);
//        if (index < 0) {
//            return null;
//        }
//        return findFirstTokenByIndex(index, ignoredTypes, requiredTypes, forward);
//    }
//
//    public synchronized SQLToken findFirstTokenByIndex(int index, int[] ignoredTypes, int[] requiredTypes, boolean forward) {
//        if (index < 0) {
//            return null;
//        }
//        buildCache();
//        if (index >= cachedTokens.length) {
//            return null;
//        }
//        while (true) {
//            SQLToken token = cachedTokens[index];
//            int tokenType = token.getType();
//            if (ignoredTypes != null) {
//                boolean ignored = false;
//                for (int j = 0; j < ignoredTypes.length; j++) {
//                    if (ignoredTypes[j] == tokenType) {
//                        ignored = true;
//                        break;
//                    }
//                }
//                if (!ignored) {
//                    return token;
//                }
//            } else if (requiredTypes != null) {
//                boolean required = false;
//                for (int j = 0; j < requiredTypes.length; j++) {
//                    if (requiredTypes[j] == tokenType) {
//                        required = true;
//                        break;
//                    }
//                }
//                if (required) {
//                    return token;
//                }
//            }
//
//            if (forward) {
//                index++;
//                if (index >= cachedTokens.length) {
//                    return null;
//                }
//            } else {
//                index--;
//                if (index < 0) {
//                    return null;
//                }
//            }
//
//        }
//    }

    public synchronized Collection<SQLToken> getTokens() {
        return getTokens(0, getLength(), false);
    }

    public synchronized Collection<SQLToken> getTokens(int i, int j, boolean truncate) {
        if (cachedTokens == null) {
            Collection<SQLToken> alltokens = new ArrayList<SQLToken>();
            Collection<SQLStatement> allStatements = new ArrayList<SQLStatement>();
            String textPortion = "";
            try {
                textPortion = getText(0, getLength());
            } catch (BadLocationException e) {
                //
            }
            parser.setDocument(textPortion == null ? null : new StringReader(textPortion));
            Collection<SQLToken> neededTokens = new ArrayList<SQLToken>();
            int s;
            int e;
            int s2;
            int e2;
            SQLStatement stmt = null;
            while ((stmt = parser.readStatement()) != null) {
                //SQLToken startToken =null;
                //SQLToken endToken =null;
                int startTokenIndex = -1;
                //int endTokenIndex =-1;
                for (SQLToken token : stmt) {
                    if (startTokenIndex == -1) {
                        startTokenIndex = alltokens.size();
                        //startToken =token;
                    }
                    //endTokenIndex =alltokens.size()+1;
                    //endToken =token;

                    s = token.getCharStartIndex();
                    e = token.getCharEndIndex();
                    alltokens.add(token);
                    if ((s >= i && e < j)) {
                        neededTokens.add(token);
                    } else {
                        s2 = s > i ? s : i;
                        e2 = e < j ? e : j;
                        if (s2 < e2) {
                            if (truncate) {
                                neededTokens.add(new SQLToken(s2, token.getGroup(), token.getValue().substring(s2 - s, e2 - s)));
                            } else {
                                neededTokens.add(token);
                            }
                        }
                    }
                }
                allStatements.add(stmt);
            }
            cachedStatements = allStatements.toArray(new SQLStatement[allStatements.size()]);
            cachedTokens = alltokens.toArray(new SQLToken[alltokens.size()]);
            return neededTokens;
        } else {
            int startIndex = searchTokenIndexByCharIndex(i);
            int j2 = j;
            if (cachedTokens.length > 0 && j2 > cachedTokens[cachedTokens.length - 1].getCharEndIndex()) {
                j2 = cachedTokens[cachedTokens.length - 1].getCharEndIndex();
            }
            int endIndex = searchTokenIndexByCharIndex(j2 - 1);
//            System.out.printf("startIndex=%d ; endIndex=%d\n",startIndex,endIndex);
//            if((startIndex>=0 && endIndex<0) || (startIndex<0 && endIndex>=0)){
//                System.out.println("how ??? ");
//                startIndex = searchTokenIndexByCharIndex(i);
//                endIndex = searchTokenIndexByCharIndex(j - 1);
//            }
            if (startIndex < 0 || endIndex < startIndex) {
                return new ArrayList<SQLToken>();
            } else {
                ArrayList<SQLToken> neededTokens = new ArrayList<SQLToken>(endIndex - startIndex);
                SQLToken token;
                int s;
                int e;
                int s2;
                int e2;
                for (int k = startIndex; k <= endIndex; k++) {
                    token = cachedTokens[k];
                    s = token.getCharStartIndex();
                    e = token.getCharEndIndex();
                    if ((s >= i && e <= j)) {
                        neededTokens.add(token);
                    } else {
                        s2 = s > i ? s : i;
                        e2 = e < j ? e : j;
                        if (s2 < e2) {
                            if (truncate) {
                                neededTokens.add(new SQLToken(s2, token.getGroup(), token.getValue().substring(s2 - s, e2 - s)));
                            } else {
                                neededTokens.add(token);

                            }
                        }
                    }
                }
                return neededTokens;
            }
        }
    }

    public int getTokenIndexByCharIndex(int charIndex) {
        buildCache();
        return searchTokenIndexByCharIndex(charIndex);
    }

    public int getTokenIndex(SQLToken token) {
        buildCache();
        return searchTokenIndexByCharIndex(token.getCharStartIndex());
    }

    public SQLToken getTokenByIndex(int index) {
        buildCache();
        return cachedTokens[index];
    }

    private void buildCache() {
        if (cachedTokens == null) {
            getTokens(0, 0, false);
        }
    }

    public SQLToken[] getTokensCache() {
        if (cachedTokens == null) {
            getTokens(0, 0, false);
        }
        return cachedTokens;
    }

    public class ElementLocation {
        SQLStatement sqlStatement;
        int sqlStatementIndex;
        SQLToken token;
        int tokenIndex;

        public ElementLocation(SQLStatement sqlStatement, int sqlStatementIndex, SQLToken token, int tokenIndex) {
            this.sqlStatement = sqlStatement;
            this.sqlStatementIndex = sqlStatementIndex;
            this.token = token;
            this.tokenIndex = tokenIndex;
        }

        public SQLStatement getSqlStatement() {
            return sqlStatement;
        }

        public int getSqlStatementIndex() {
            return sqlStatementIndex;
        }

        public SQLToken getToken() {
            return token;
        }

        public int getTokenIndex() {
            return tokenIndex;
        }
    }

    public ElementLocation locate(int charIndex) {
        int x = SQLUtils.indexOfStatementByCharIndex(charIndex, cachedStatements);
        if (x < 0) {
            return null;
        }
        SQLStatement se = cachedStatements[x];
        int i = SQLUtils.indexOfTokenByCharIndex(charIndex, cachedTokens, se.getTokenStartIndex(), se.getTokenEndIndex() - 1);
        return new ElementLocation(cachedStatements[x], x, cachedTokens[i], i);
    }

    private int searchTokenIndexByCharIndex(int charIndex) {
        int x = SQLUtils.indexOfStatementByCharIndex(charIndex, cachedStatements);
        if (x < 0) {
            return x;
        }
        SQLStatement se = cachedStatements[x];
        return SQLUtils.indexOfTokenByCharIndex(charIndex, cachedTokens, se.getTokenStartIndex(), se.getTokenEndIndex() - 1);
    }

}
