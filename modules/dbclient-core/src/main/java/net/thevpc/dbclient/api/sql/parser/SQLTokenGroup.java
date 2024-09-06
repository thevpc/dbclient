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

package net.thevpc.dbclient.api.sql.parser;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 28 juin 2007 10:32:36
 */
public class SQLTokenGroup {
    String representation;
    int scanValue;
    public static final int ID_MIN = 0;
    public static final int ID_MAX = 20;
    public static final int UNKNOWN = 0;
    public static final int WHITE = 1;
    public static final int KEYWORD = 2;
    public static final int VAR = 3;
    public static final int STR = 4;//    public static final int STR_SEP = 5;
    public static final int NUM = 6;
    public static final int OPERATOR = 7;//    public static final int PAR = 8;
    public static final int CLOSE_PAR = 9;
    public static final int OPEN_PAR = 10;
    public static final int SEPARATOR = 11;
    public static final int COMMENTS = 12;
    public static final int TABLE = 13;
    public static final int PROCEDURE = 14;
    public static final int CATALOG = 15;
    public static final int SCHEMA = 16;
    public static final int DATATYPE = 17;
    public static final int FUNCTION = 18;
    public static final int PAR = 19;
    public static final int SCRIPT = 20;
    public static final int DOT = 21;
    public static final int[] SET_WORDS = new int[]{TABLE, FUNCTION, SCHEMA, CATALOG, DATATYPE, PROCEDURE, VAR};
    public static final int[] SET_PONCTUATION = new int[]{SEPARATOR, DOT, OPEN_PAR, CLOSE_PAR, PAR};
    public static final SQLTokenGroup G_COMMENT = new SQLTokenGroup(COMMENTS);
    public static final SQLTokenGroup G_KEYWORD = new SQLTokenGroup(KEYWORD);
    public static final SQLTokenGroup G_DATAYPE = new SQLTokenGroup(DATATYPE);
    public static final SQLTokenGroup G_FUNCTION = new SQLTokenGroup(FUNCTION);
    public static final SQLTokenGroup G_STRING = new SQLTokenGroup(STR);
    public static final SQLTokenGroup G_OPEN_PAR = new SQLTokenGroup(OPEN_PAR);
    public static final SQLTokenGroup G_CLOSE_PAR = new SQLTokenGroup(CLOSE_PAR);
    public static final SQLTokenGroup G_NUM = new SQLTokenGroup(NUM);
    public static final SQLTokenGroup G_UNKNOWN = new SQLTokenGroup(UNKNOWN);
    public static final SQLTokenGroup G_VAR = new SQLTokenGroup(VAR);
    public static final SQLTokenGroup G_WHITE = new SQLTokenGroup(WHITE);
    public static final SQLTokenGroup G_OPERATOR = new SQLTokenGroup(OPERATOR);
    public static final SQLTokenGroup G_SEPARATOR = new SQLTokenGroup(SEPARATOR);
    public static final SQLTokenGroup G_SCRIPT = new SQLTokenGroup(SCRIPT);
    public static SQLTokenGroup[] T_ALL = {
            G_COMMENT,
            G_KEYWORD,
            G_STRING,
            G_OPEN_PAR,
            G_CLOSE_PAR,
            G_NUM,
            G_UNKNOWN,
            G_VAR, G_OPERATOR, G_SEPARATOR,
            G_WHITE, G_COMMENT, G_DATAYPE, G_FUNCTION, G_SCRIPT
    };

    SQLTokenGroup(int scanValue) {
        this(typeToString(scanValue), scanValue);
    }

    SQLTokenGroup(String representation, int scanValue) {
        this.representation = representation;
        this.scanValue = scanValue;
    }

    /**
     * A human presentable form of the token, useful
     * for things like lists, debugging, etc.
     */
    @Override
    public String toString() {
        return representation;
    }

    /**
     * Numeric value of this token.  This is the value
     * returned by the scanner and is the tie between
     * the lexical scanner and the tokens.
     */
    public int getScanValue() {
        return scanValue;
    }

    public String getCategory() {
        return representation;
//        String nm = getClass().getActionName();
//        int nmStart = nm.lastIndexOf('.') + 1; // not found results in 0
//        return nm.substring(nmStart, nm.length());
    }

    public static String typeToString(int type) {
        String s;
        switch (type) {
            case WHITE: // '\001'
                s = "WHITE";
                break;

            case KEYWORD: // '\002'
                s = "KEYWORD";
                break;

            case VAR: // '\004'
                s = "VAR";
                break;

//            case KEYWORD | OPERATOR: // 'B'
//                s = "KEYWORD|OPERATOR";
//                break;

            case STR: // '\b'
                s = "STR";
                break;

            case TABLE: // '\020'
                s = "TABLE";
                break;

            case PROCEDURE: // '\020'
                s = "PROCEDURE";
                break;

            case DATATYPE: // '\020'
                s = "DATATYPE";
                break;

            case FUNCTION: // '\020'
                s = "FUNCTION";
                break;

            case CATALOG: // '\020'
                s = "CATALOG";
                break;

            case SCHEMA: // '\020'
                s = "SCHEMA";
                break;

            case NUM: // ' '
                s = "NUM";
                break;

            case OPERATOR: // '@'
                s = "OPERATOR";
                break;

            case OPEN_PAR:
                s = "OPEN_PAR";
                break;

            case CLOSE_PAR:
                s = "CLOSE_PAR";
                break;

            case SEPARATOR:
                s = "SEPARATOR";
                break;

            case COMMENTS:
                s = "COMMENTS";
                break;

            case PAR:
                s = "COMMENTS";
                break;

            case SCRIPT:
                s = "SCRIPT";
                break;

            case DOT:
                s = "DOT";
                break;

            case UNKNOWN: // '\0'
                s = "UNKNOWN";
                break;

            default:
                s = "TOKEN_[" + type + "]";
                break;
        }
        return s;
    }
}
