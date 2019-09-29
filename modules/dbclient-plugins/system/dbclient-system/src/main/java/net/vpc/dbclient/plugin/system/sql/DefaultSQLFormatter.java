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

package net.vpc.dbclient.plugin.system.sql;

import net.vpc.dbclient.api.pluginmanager.DBCAbstractPluggable;
import net.vpc.dbclient.api.sql.format.SQLFormatter;
import net.vpc.dbclient.api.sql.parser.SQLParser;
import net.vpc.dbclient.api.sql.parser.SQLStatement;
import net.vpc.dbclient.api.sql.parser.SQLToken;
import net.vpc.dbclient.api.sql.parser.SQLTokenGroup;
import net.vpc.dbclient.plugin.system.sql.parser.DefaultSQLParser;
import net.vpc.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Stack;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 27 juil. 2006 12:52:39
 */
public class DefaultSQLFormatter extends DBCAbstractPluggable implements SQLFormatter {
    protected final int G_START = -100;
    protected final int G_END = -101;
    protected final SQLToken TOKEN_G_START = new SQLToken(-1, -1, G_START, "");
    protected final SQLToken TOKEN_G_END = new SQLToken(-1, -1, G_END, "");
    protected final SQLToken TOKEN_WHITE = new SQLToken(-1, 0, SQLTokenGroup.WHITE, " ");
//    private static boolean $debug = false;

    private int lineWidth = 80;
    private int tabSize = 2;
    private SQLParser parser;

    public DefaultSQLFormatter() {
        this(new DefaultSQLParser());
    }

    public DefaultSQLFormatter(SQLParser parser) {
        this.parser = parser;
    }

    public String format(ArrayList<SQLToken> tokens, int lineWidthMin, int lineWidthMax, int indent) throws IOException {
        if (tokens.size() == 0) {
            return "";
        }
        if (tokens.size() == 1) {
            return formatToken(tokens.get(0));
        }
//        if ($debug) {
//            System.out.println("format " + desc(tokens));
//        }
        StringBuilder currentLine = new StringBuilder();
        StringBuilder all = new StringBuilder();
        int index = 0;
        while (index < tokens.size()) {
            SQLToken t = tokens.get(index);
//            System.out.println("\t  token["+index+"] : " + t);
            switch (t.getGroup()) {
                case G_START: {
                    ArrayList<SQLToken> subTokens = new ArrayList<SQLToken>();
                    int implicitGroups = 1;
                    int explicitGroups = 0;
                    while ((implicitGroups > 0 || explicitGroups > 0) && index < (tokens.size() - 1)) {
                        index++;
                        SQLToken t2 = tokens.get(index);
                        switch (t2.getGroup()) {
                            case G_START: {
                                implicitGroups++;
                                subTokens.add(t2);
                                break;
                            }
                            case G_END: {
                                implicitGroups--;
                                if (implicitGroups > 0 || explicitGroups > 0) {
                                    subTokens.add(t2);
                                }
                                break;
                            }
                            case SQLTokenGroup.OPEN_PAR: {
                                explicitGroups++;
                                subTokens.add(t2);
                                break;
                            }
                            case SQLTokenGroup.CLOSE_PAR: {
                                explicitGroups--;
                                if (explicitGroups < 0) {
                                    implicitGroups = 0;
                                    index--;
                                } else {
                                    subTokens.add(t2);
                                }
                                break;
                            }
                            default: {
                                subTokens.add(t2);
                            }
                        }
                    }
                    String s = format(subTokens, lineWidth, lineWidth, indent + 1);
                    int r = lineWidthMax - currentLine.length() - s.length();
                    if (r >= 0) {
                        if (currentLine.length() > 0
                                && currentLine.charAt(currentLine.length() - 1) != '.'
                                && s.length() > 0 && s.charAt(0) != '.'
                                ) {
                            currentLine.append(" ");
                        }
                        currentLine.append(s);
                    } else {
                        all.append(currentLine);
                        currentLine.delete(0, currentLine.length());
                        all.append("\n");
                        all.append(indent(s, indent * tabSize));
                        all.append("\n");
                    }
                    break;
                }
                case SQLTokenGroup.OPEN_PAR: {
                    ArrayList<SQLToken> subTokens = new ArrayList<SQLToken>();
                    int p = 1;
                    while (p > 0 && index < (tokens.size() - 1)) {
                        index++;
                        SQLToken t2 = tokens.get(index);
                        switch (t2.getGroup()) {
                            case SQLTokenGroup.OPEN_PAR: {
                                p++;
                                subTokens.add(t2);
                                break;
                            }
                            case SQLTokenGroup.CLOSE_PAR: {
                                p--;
                                if (p > 0) {
                                    subTokens.add(t2);
                                }
                                break;
                            }
                            default: {
                                subTokens.add(t2);
                            }
                        }
                    }
                    String s = format(subTokens, lineWidth, lineWidth, indent + 1);
                    int r = lineWidthMax - currentLine.length() - s.length() - 2;
                    if (r >= 0) {
                        if (currentLine.length() > 0
                                && currentLine.charAt(currentLine.length() - 1) != '.'
                                && (t == null || (t.getGroup() != G_START
                                && t.getGroup() != G_END
                                && t.getGroup() != SQLTokenGroup.SEPARATOR
                                && t.getGroup() != SQLTokenGroup.DOT))
                                ) {
                            currentLine.append(" ");
                        }
                        currentLine.append("(");
                        currentLine.append(s);
                        currentLine.append(")");
                    } else {
                        all.append(currentLine);
                        currentLine.delete(0, currentLine.length());
                        all.append("(\n");
                        all.append(indent(s, indent * tabSize));
                        all.append("\n");
                        currentLine.append(")");
                    }
                    break;
                }
                default: {
                    String s = formatToken(t);
                    int r = lineWidthMax - currentLine.length() - s.length();
                    if (r >= 0) {
                        if (currentLine.length() > 0
                                && currentLine.charAt(currentLine.length() - 1) != '.'
                                && (t == null || (t.getGroup() != G_START
                                && t.getGroup() != G_END
                                && t.getGroup() != SQLTokenGroup.SEPARATOR
                                && t.getGroup() != SQLTokenGroup.DOT))
                                ) {
                            currentLine.append(" ");
                        }
                        currentLine.append(s);
                    } else {
                        all.append(currentLine);
                        all.append("\n");
                        currentLine.delete(0, currentLine.length());
                        currentLine.append(indent(s, indent * tabSize));
                    }
                    break;
                }
            }
            index++;
        }
        all.append(currentLine);
//        if ($debug) {
//            System.out.println("\n<<<<<<<<<<<\nresult = {" + all + "} for {" + desc(tokens) + "}\n>>>>>>>>>>");
//        }
        return all.toString();
    }

    private String indent(String s, int p) {
        try {
            StringBuilder sb = new StringBuilder();
            BufferedReader b = new BufferedReader(new StringReader(s));
            String line;
            StringBuilder pp = new StringBuilder();
            for (int i = 0; i < p; i++) {
                pp.append(" ");
            }
            while ((line = b.readLine()) != null) {
                sb.append(pp);
                sb.append(line);
                sb.append("\n");
            }
            if (sb.length() > 0 && sb.charAt(sb.length() - 1) == '\n') {
                sb.deleteCharAt(sb.length() - 1);
            }
            return sb.toString().trim();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String formatDocument(String sql) {
        StringBuilder sb = new StringBuilder();
        SQLParser parser2 = this.parser.clone();
        parser2.setDocument(sql == null ? null : new StringReader(sql));
        SQLStatement s;
        while ((s = parser2.readStatement()) != null) {
            sb.append(formatStatement(s)).append("\n");
        }
        return sb.toString();
    }

    public String formatStatement(SQLStatement statement) {
        try {
            ArrayList<SQLToken> tokens = new ArrayList<SQLToken>();
            //pass 1
            Stack<Integer> parGroups = new Stack<Integer>();
            parGroups.push(0);
            boolean inWord = false;
//            boolean lastDot = false;
            for (SQLToken token : statement) {
                switch (token.getGroup()) {
                    case SQLTokenGroup.WHITE: {
                        //                    tokens.add(TOKEN_WHITE);
                        break;
                    }
                    case SQLTokenGroup.COMMENTS: {
                        tokens.add(token);
                        break;
                    }
                    case SQLTokenGroup.OPEN_PAR: {
                        if (inWord) {
                            tokens.add(TOKEN_G_END);
                            inWord = false;
                        }
                        parGroups.push(0);
                        tokens.add(token);
                        break;
                    }
                    case SQLTokenGroup.CLOSE_PAR: {
                        if (inWord) {
                            tokens.add(TOKEN_G_END);
                            inWord = false;
                        }
                        if (!parGroups.empty()) {
                            int pop = parGroups.pop();
                            while (pop > 0) {
                                pop--;
                                tokens.add(TOKEN_G_END);
                            }
                        }
                        tokens.add(token);
                        break;
                    }
                    case SQLTokenGroup.KEYWORD: {
                        if (inWord) {
                            tokens.add(TOKEN_G_END);
                            inWord = false;
                        }
                        String k = token.getValue().toLowerCase();
                        if ("select".equals(k) || "drop".equals(k) || "delete".equals(k)) {
                            tokens.add(TOKEN_G_START);
                            tokens.add(token);
                            parGroups.push(parGroups.pop() + 1);
                        } else if (
                                "from".equals(k)
                                        || "set".equals(k)
                                        || "as".equals(k)
                                ) {
                            tokens.add(TOKEN_G_END);
                            tokens.add(token);
                            tokens.add(TOKEN_G_START);
                        } else if (
                                "inner".equals(k)
                                        || "select".equals(k)
                                        || "left".equals(k)
                                        || "right".equals(k)
                                        || "full".equals(k)
                                        || "cross".equals(k)
                                        || "where".equals(k)
                                        || "order".equals(k)
                                        || "group".equals(k)
                                        || "having".equals(k)
                                        || "union".equals(k)
                                ) {
                            tokens.add(TOKEN_G_END);
                            tokens.add(TOKEN_G_START);
                            tokens.add(token);
                        } else {
                            tokens.add(token);
                        }
                        break;
                    }
                    case SQLTokenGroup.VAR:
                    case SQLTokenGroup.TABLE:
                    case SQLTokenGroup.CATALOG:
                    case SQLTokenGroup.SCHEMA:
                    case SQLTokenGroup.FUNCTION:
                    case SQLTokenGroup.PROCEDURE:
                    case SQLTokenGroup.DOT: {
//                        lastDot = true;
                        if (!inWord) {
                            tokens.add(TOKEN_G_START);
                            inWord = true;
                        }
                        tokens.add(token);
                        break;
                    }
                    default: {
                        if (inWord) {
                            tokens.add(TOKEN_G_END);
                            inWord = false;
                        }
//                        lastDot = false;
                        tokens.add(token);
                    }
                }
            }
            while (!parGroups.empty()) {
                int pop = parGroups.pop();
                while (pop > 0) {
                    pop--;
                    tokens.add(TOKEN_G_END);
                }
            }
            return format(tokens, lineWidth, lineWidth, 0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String desc(ArrayList<SQLToken> tokens) {
        StringBuilder s = new StringBuilder();
        for (SQLToken sqlToken : tokens) {
            if (s.length() > 0) {
                s.append(" ");
            }
            switch (sqlToken.getGroup()) {
                case G_START: {
                    s.append("<START>");
                    break;
                }
                case G_END: {
                    s.append("<END>");
                    break;
                }
                default: {
                    s.append(sqlToken.getValue());
                }
            }
        }
        return s.toString();
    }

    public int getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
    }

    public SQLParser getParser() {
        return parser;
    }

    public void setParser(SQLParser parser) {
        this.parser = parser;
    }

    public String formatToken(SQLToken token) {
        switch (token.getGroup()) {
            case SQLTokenGroup.KEYWORD: {
                return StringUtils.capitalize(token.toSQL());
            }
            case SQLTokenGroup.COMMENTS: {
                String str = token.toSQL();
                if (str.startsWith("//") || str.startsWith("--")) {
                    return str + "\n";
                }
                return str;
            }
            case SQLTokenGroup.DATATYPE: {
                return token.toSQL().toLowerCase();
            }

            case SQLTokenGroup.FUNCTION: {
                return StringUtils.capitalize(token.toSQL());
            }

            case SQLTokenGroup.PROCEDURE:
            case SQLTokenGroup.SCHEMA:
            case SQLTokenGroup.CATALOG:
            case SQLTokenGroup.TABLE:
            case SQLTokenGroup.VAR: {
                return token.toSQL().toUpperCase();
            }
        }
        return token.toSQL();
    }

}
