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

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 3 juil. 2006 09:56:14
 */
public class SQLToken implements SQLElement {
    public static final int DECORATION_CORRECT = 1;
    public static final int DECORATION_SYNTAX_CORRECT = 2;
    public static final int DECORATION_SEMANTIC_CORRECT = 4;
    public static final int DECORATION_TRAILING = 1024;

    private String query;
    private String value;
    private int start;
    private int end;
    public int group;
    public int type;
    public int decoration;
    public String decorationMessage;
    private int row;
    private int column;
//    public SQLToken(String query, int start, int end) {
//        this(query, start, end, SQLTokenGroup.UNKNOWN);
//    }

//    public SQLToken(String query, int start, int end, int type) {
//        this.query = query;
//        this.start = start;
//        this.end = end;
//        this.group = type;
//        this.value = query.substring(start, end);
//    }

    public SQLToken(int start, int type, String value) {
        this(start, value.length() + start, type, value);
    }

    public SQLToken(int start, int end, int group, String value) {
        this(start, end, group, SQLTokenType.UNKNOWN, value, -1, -1);
    }

    public SQLToken(int start, int end, int group, int type, String value, int row, int column) {
        this.query = null;
        this.start = start;
        this.end = end;
        this.group = group;
        this.type = type;
        this.value = value;
        this.row = row;
        this.column = column;
        if ((end - start) != value.length()) {
            throw new IllegalArgumentException("Bad value");
        }
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getQuery() {
        return query;
    }

    public String getValue() {
        return value;
    }

    public int getCharStartIndex() {
        return start;
    }

    public int getCharEndIndex() {
        return end;
    }

    public boolean isKeyword(String name) {
        return group == SQLTokenGroup.KEYWORD && value.toLowerCase().equals(name);
    }

    public int getGroup() {
        return group;
    }

    public boolean accept(int... i) {
        for (int j = 0; j < i.length; j++) {
            int i1 = i[j];
            if (group == i1) {
                return true;
            }
        }
        return false;
    }

//    public boolean accept(String value, int positive, int negative) {
//        return (positive == 0 || (type & positive) != 0) && (negative == 0 || (type & negative) == 0) && (value == null || value.equals(this.value));
//    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[").append(SQLTokenGroup.typeToString(group)).append(" ")
                .append("Start/End=")
                .append((start >= 0) ? (start + "," + end) : "")
                .append(", Row/Col=")
                .append((row >= 0) ? (row + "," + column) : "")
                .append(", \"");
//        sb.append(value);
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            switch (c) {
                case '\n': {
                    sb.append("\\n");
                    break;
                }
                case '\t': {
                    sb.append("\\t");
                    break;
                }
                case '\\': {
                    sb.append("\\\\");
                    break;
                }
                case '\r': {
                    sb.append("\\r");
                    break;
                }
                case '\f': {
                    sb.append("\\f");
                    break;
                }
                case '\b': {
                    sb.append("\\b");
                    break;
                }
                default: {
                    sb.append(c);
                }
            }
        }
        sb.append("\"]");
        return sb.toString();
    }

    public String desc() {
        return SQLTokenGroup.typeToString(group);
    }

    public String desc2() {
        return SQLTokenGroup.typeToString(group) + " " + start + "," + end + ", \"" + value + "\"";
    }

    public int length() {
        return value.length();
    }

    public int getDecoration() {
        return decoration;
    }

    public String getDecorationMessage() {
        return decorationMessage;
    }

    public void setDecoration(int decoration, String message) {
        this.decoration = decoration;
        this.decorationMessage = message;
    }

    public void addDecoration(int decoration, String message) {
        this.decoration = this.decoration | decoration;
        this.decorationMessage = message;
    }

    public String toSQL() {
        return value;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}

