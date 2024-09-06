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

import net.thevpc.dbclient.api.sql.parser.*;
import net.thevpc.dbclient.api.sql.util.SQLUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 3 juil. 2006 09:56:14
 */
public class DefaultSQLStatement implements SQLStatement {
    private SQLToken[] sqlTokens;
    private int leadingToken;
    private SQLStatementType statementType;
    private int tokenStartIndex;
    private int lineStartIndex;
    private int nonWhiteLineStartIndex;
    private int lineEndIndex;

    public DefaultSQLStatement(SQLStatementType statementType,int leadingToken, Collection<SQLToken> sqlTokens, int tokenStartIndex) {
        this(statementType,leadingToken, sqlTokens.toArray(new SQLToken[sqlTokens.size()]), tokenStartIndex);
    }

    public DefaultSQLStatement(SQLStatementType statementType,int leadingToken, String token) {
        this(statementType,leadingToken, new SQLToken[]{new SQLToken(-1, SQLTokenGroup.UNKNOWN, token)}, 0);
    }

    public DefaultSQLStatement(SQLStatementType statementType,int leadingToken, SQLToken[] sqlTokens, int tokenStartIndex) {
        this.sqlTokens = sqlTokens;
        this.leadingToken = leadingToken;
        this.statementType = statementType;
        this.tokenStartIndex = tokenStartIndex;
        lineStartIndex = sqlTokens[0].getRow();
        lineEndIndex = sqlTokens[sqlTokens.length - 1].getRow() + 1;
        nonWhiteLineStartIndex = -1;
        LOOP:
        for (SQLToken sqlToken : sqlTokens) {
            switch (sqlToken.getGroup()) {
                case SQLTokenGroup.WHITE: {
                    //ignore
                    break;
                }
                default: {
                    nonWhiteLineStartIndex = sqlToken.getRow();
                    break LOOP;
                }
            }
        }
    }


    public int getLineStartIndex() {
        return lineStartIndex;
    }

    public int getNonWhiteLineStartIndex() {
        return nonWhiteLineStartIndex;
    }

    public int getLineEndIndex() {
        return lineEndIndex;
    }

    public String toSQL() {
        StringBuilder sb = new StringBuilder();
        for (SQLToken sqlToken : sqlTokens) {
            if ((sqlToken.getDecoration() & SQLToken.DECORATION_TRAILING) == 0) {
                sb.append(sqlToken.getValue());
            }
        }
        return sb.toString();
    }

    public String toDecoratedString() {
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        int x = 1;
        for (SQLToken sqlToken : sqlTokens) {
            sb.append(sqlToken.getValue());
            if (sqlToken.getDecoration() != SQLToken.DECORATION_CORRECT) {
                sb.append("<*").append(x).append(">");
                sb2.append("<*").append(x).append("> ").append(sqlToken.getDecorationMessage()).append("\n");
                x++;
            }
        }
        if (x > 0) {
            return sb.toString() + "\n" + sb2.toString();
        } else {
            return sb.toString();
        }
    }

    public int getLeadingToken() {
        return leadingToken;
    }

    public Iterator<SQLToken> iterator() {
        return Arrays.asList(sqlTokens).iterator();
    }

    public int getCharEndIndex() {
        return sqlTokens[sqlTokens.length - 1].getCharEndIndex();
    }

    public int getCharStartIndex() {
        return sqlTokens[0].getCharStartIndex();
    }

    public int getTokenEndIndex() {
        return tokenStartIndex + sqlTokens.length;
    }

    public int getTokenStartIndex() {
        return tokenStartIndex;
    }

    public SQLToken[] getTokens() {
        SQLToken[] o = new SQLToken[sqlTokens.length];
        System.arraycopy(sqlTokens, 0, o, 0, sqlTokens.length);
        return o;
    }

//    public SQLDeclaration[] getTokenDeclarationsAt(int charIndex) {
//        ArrayList<SQLDeclaration> decs=new ArrayList<SQLDeclaration>();
//        int stmtCurTokenIndex = SQLUtils.indexOfTokenByCharIndex(charIndex, sqlTokens);
//
//        int indexBack = stmtCurTokenIndex /*-1*/;
//        int indexFore = stmtCurTokenIndex + 1;
//        while (indexBack >= 0 || indexFore < sqlTokens.length) {
//            if (indexBack >= 0) {
//                SQLToken[] dec = checkDeclaration(indexBack, null);
//                if (dec != null) {
//                    decs.add(new SQLDeclaration(sqlTokens[indexBack],dec));
//                }
//                indexBack--;
//            }
//            if (indexFore < sqlTokens.length) {
//                SQLToken t = sqlTokens[indexFore];
//                if (t.getType() == SQLToken.OPEN_PAR) {
//                    int pars = 1;
//                    while (pars > 1 && indexFore < sqlTokens.length) {
//                        t = sqlTokens[indexFore];
//                        switch (t.getType()) {
//                            case SQLToken.OPEN_PAR: {
//                                pars++;
//                                break;
//                            }
//                            case SQLToken.CLOSE_PAR: {
//                                pars--;
//                                break;
//                            }
//                        }
//                        indexFore++;
//                    }
//                }
//                if (indexFore < sqlTokens.length) {
//                    SQLToken[] dec = checkDeclaration(indexFore, null);
//                    if (dec != null) {
//                        decs.add(new SQLDeclaration(sqlTokens[indexFore],dec));
//                    }
//                    indexFore++;
//                }
//            }
//        }
//        return (SQLDeclaration[]) decs.toArray(new SQLDeclaration[decs.size()]);
//    }

    public SQLDeclaration getTokenDeclaration(SQLToken alias) {
        int stmtCurTokenIndex = SQLUtils.indexOfTokenByCharIndex(alias.getCharStartIndex(), sqlTokens);

        String v = alias.getValue();
        int indexBack = stmtCurTokenIndex /*-1*/;
        int indexFore = stmtCurTokenIndex + 1;
//        int parsFore = 0;
//        int parsBack = 0;
        while (indexBack >= 0 || indexFore < sqlTokens.length) {
            if (indexBack >= 0) {
                SQLToken[] dec = checkDeclaration(indexBack, v);
                if (dec != null) {
                    return new SQLDeclaration(alias, dec);
                }
                indexBack--;
            }
            if (indexFore < sqlTokens.length) {
                SQLToken t = sqlTokens[indexFore];
                if (t.getGroup() == SQLTokenGroup.OPEN_PAR) {
                    int pars = 1;
                    while (pars > 1 && indexFore < sqlTokens.length) {
                        t = sqlTokens[indexFore];
                        switch (t.getGroup()) {
                            case SQLTokenGroup.OPEN_PAR: {
                                pars++;
                                break;
                            }
                            case SQLTokenGroup.CLOSE_PAR: {
                                pars--;
                                break;
                            }
                        }
                        indexFore++;
                    }
                }
                if (indexFore < sqlTokens.length) {
                    SQLToken[] dec = checkDeclaration(indexFore, v);
                    if (dec != null) {
                        return new SQLDeclaration(alias, dec);
                    }
                    indexFore++;
                }
            }
        }
        return null;
    }

    public SQLDeclaration[] getQueryTables() {
        ArrayList<SQLToken> currentName = new ArrayList<SQLToken>();
        int pars = 0;
        boolean expect = false;
        ArrayList<SQLName> allNames = new ArrayList<SQLName>();
        for (SQLToken token : this) {
            if (!expect && !currentName.isEmpty()) {
                allNames.add(new SQLName(currentName.toArray(new SQLToken[currentName.size()])));
                currentName.clear();
            }
            switch (token.getGroup()) {
                case SQLTokenGroup.OPEN_PAR: {
                    pars++;
                    break;
                }
                case SQLTokenGroup.CLOSE_PAR: {
                    pars++;
                    break;
                }
                case SQLTokenGroup.KEYWORD: {
                    if (pars == 0) {
                        String value = token.toSQL().toLowerCase();
                        if (value.equals("from")
                                || value.equals("inner")
                                || value.equals("outer")
                                || value.equals("left")
                                || value.equals("right")
                                || value.equals("full")
                                || value.equals("cross")
                                || value.equals("join")
                                ) {
                            expect = true;
                        } else if (value.equals("on") || value.equals("where") || value.equals("having") || value.equals("order")) {
                            expect = false;
                        } else if (value.equals("as")) {
                            expect = false;
                        }
                    }
                    break;
                }
                case SQLTokenGroup.DOT: {
                    if (!currentName.isEmpty()) {
                        if (currentName.get(currentName.size() - 1).accept(SQLTokenGroup.SET_WORDS)) {
                            currentName.add(token);
                        }
                    }
                    break;
                }
                case SQLTokenGroup.VAR:
                case SQLTokenGroup.TABLE:
                case SQLTokenGroup.FUNCTION:
                case SQLTokenGroup.SCHEMA:
                case SQLTokenGroup.CATALOG:
                case SQLTokenGroup.DATATYPE:
                case SQLTokenGroup.PROCEDURE: {
                    if (pars == 0 && expect) {
                        if (!currentName.isEmpty()) {
                            if (currentName.get(currentName.size() - 1).accept(SQLTokenGroup.DOT)) {
                                currentName.add(token);
                            } else {
                                expect = false;
                            }
                        } else {
                            currentName.add(token);
                        }
                    }
                    break;
                }
            }
        }
        if (!currentName.isEmpty()) {
            allNames.add(new SQLName(currentName.toArray(new SQLToken[currentName.size()])));
            currentName.clear();
        }
        SQLDeclaration[] allDeclarations = new SQLDeclaration[allNames.size()];
        for (int i = 0; i < allNames.size(); i++) {
            SQLToken[] sqlName = allNames.get(i).getNames();
            int index = sqlName[sqlName.length - 1] == null ? -1 : SQLUtils.indexOfTokenByCharIndex(sqlName[sqlName.length - 1].getCharStartIndex(), sqlTokens);
            int n = SQLUtils.nextValidTokenIndex(index, sqlTokens);
            SQLToken alias = null;
            if (n >= 0) {
                if (sqlTokens[n].isKeyword("as")) {
                    n = SQLUtils.nextValidTokenIndex(n, sqlTokens);
                }
                if (sqlTokens[n].accept(SQLTokenGroup.SET_WORDS)) {
                    alias = sqlTokens[n];
                }
            }
            allDeclarations[i] = new SQLDeclaration(alias, sqlName);
        }
        return allDeclarations;
    }


    private SQLToken[] checkDeclaration(int index, String itemName) {
        SQLToken t = sqlTokens[index];
        if ((t.accept(SQLTokenGroup.SET_WORDS)) && (itemName == null || t.getValue().equalsIgnoreCase(itemName)) && index > 0) {
//            int index1=previousValidTokenIndex(index);
//            int index2=previousValidTokenIndex(index1);
//            if(index1>=0 && index2>=2){
//                SQLToken t1 = sqlTokens[index1];
//                SQLToken t2 = sqlTokens[index2];
//                if(t1.isKeyword("as") && t2.accept(SQLToken.SET_WORDS)){
//                    SQLName dec = SQLUtils.leftExpandName(t2.getCharStartIndex(), sqlTokens);
//                    if (dec != null && dec.getActionName()!=null) {
//                        return dec.getTokens();
//                    }
//                }else if(t1.isKeyword("from") || t1.isKeyword("join")){
//                    int index3=nextValidTokenIndex(index);
//                    if(index3>=0 && (sqlTokens[index3].accept(SQLToken.SET_WORDS) || sqlTokens[index3].isKeyword("as"))){
//                        return null;
//                    }else{
//                        SQLName dec = SQLUtils.leftExpandName(t2.getCharStartIndex(), sqlTokens);
//                        if (dec != null && dec.getActionName()!=null) {
//                            return dec.getTokens();
//                        }
//                    }
//                }
//            }
            int x = index - 1;
//                    boolean foundAs=false;
            while (x >= 0) {
                t = sqlTokens[x];
                if (t.isKeyword("as")) {
//                            foundAs=true;
                } else if (!t.accept(SQLTokenGroup.WHITE, SQLTokenGroup.COMMENTS)) {
                    break;
                }
                x--;
            }
            if (x >= 0) {
                if (sqlTokens[x].accept(SQLTokenGroup.CLOSE_PAR)) {
                    return new SQLToken[]{sqlTokens[x]};
                } else if (sqlTokens[x].accept(SQLTokenGroup.SET_WORDS)) {
                    SQLName dec = SQLUtils.leftExpandName(sqlTokens[x].getCharStartIndex(), sqlTokens, false);
                    if (dec != null && dec.getName() != null) {
                        return dec.getTokens();
                    }
                }
            }
        }
        return null;
    }


    public Collection<SQLPortion> getSubPortions() {
        return null;
    }


//    private class TokensIterator implements Iterator<SQLToken> {
//        private Iterator<SQLToken> iterator;
//        private SQLToken current;
//
//        public TokensIterator(Iterator<SQLToken> iterator) {
//            this.iterator = iterator;
//        }
//
//        public boolean hasNext() {
//            while (iterator.hasNext()) {
//                SQLToken sqlToken = iterator.next();
//                if (sqlToken.getType() != SQLToken.WHITE) {
//                    current = sqlToken;
//                    return true;
//                }
//            }
//            return false;
//        }
//
//        public SQLToken next() {
//            return current;
//        }
//
//        public void remove() {
//            throw new IllegalArgumentException("No supported");
//        }
//    }

    public boolean isEmpty() {
        for (SQLToken t : sqlTokens) {
            if (t.getGroup() != SQLTokenGroup.COMMENTS && t.getGroup() != SQLTokenGroup.WHITE) {
                return false;
            }
        }
        return true;
    }

    public SQLStatement trim(boolean trimTrailingComments) {
        int i0 = 0;
        int i1 = sqlTokens.length - 1;
        while (i0 < sqlTokens.length) {
            int g = sqlTokens[i0].getGroup();
            if (g != SQLTokenGroup.WHITE && (!trimTrailingComments || (g != SQLTokenGroup.COMMENTS))) {
                break;
            }
            i0++;
        }
        while (i1 >= 0) {
            int g = sqlTokens[i1].getGroup();
            if (g != SQLTokenGroup.WHITE && (!trimTrailingComments || (g != SQLTokenGroup.COMMENTS))) {
                break;
            }
            i1--;
        }
        if (i0 == 0 && i1 == sqlTokens.length - 1) {
            return this;
        } else if (i1 < 0) {
            return null;
        } else {
            SQLToken[] sqlTokens2 = new SQLToken[i1 - i0 + 1];
            System.arraycopy(sqlTokens, i0, sqlTokens2, 0, i1 - i0 + 1);
            return new DefaultSQLStatement(statementType,leadingToken, sqlTokens2, tokenStartIndex + i0);
        }
    }

    public int getStatementLeadingToken() {
        return leadingToken;
    }

    public SQLStatementType getStatementType() {
        return statementType;
    }
}
