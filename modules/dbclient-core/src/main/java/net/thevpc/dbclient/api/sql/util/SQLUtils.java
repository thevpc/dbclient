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
package net.thevpc.dbclient.api.sql.util;

import net.thevpc.dbclient.api.sql.objects.DBTableColumn;
import net.thevpc.dbclient.api.sql.parser.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import net.thevpc.dbclient.api.DBCSession;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 3 juil. 2006 09:56:14
 */
public final class SQLUtils {

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
    public static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static String[] DEFAULT_DATATYPES = new String[]{"VARCHAR", "NUMBER"};
    public static String[] DEFAULT_FUNCTIONS = new String[]{"SUM", "AVG", "COUNT", "CONCAT"};
    public static String[] DEFAULT_KEYWORDS = new String[]{
            "AS", "SELECT", "FROM", "WHERE", "AND", "OR", "NOT", "IN", "LIKE", "IS", "SET", "INNER", "OUTER",
            "FULL", "OUTER", "JOIN", "ON", "ORDER", "BY", "DESC", "ASC", "UPDATE", "DELETE", "INSERT",
            "INTO", "VALUES", "CREATE", "TABLE", "VIEW", "DATABASE", "DISTINCT",
            "DROP", "DEFAULT", "CONSTRAINT", "ADD", "ALTER", "EXISTS",
            "END", "TRIGGER", "ORDER", "NULL", "PRIMARY", "KEY", "GROUP", "UNION",
            "FOREIGN", "PROCEDURE", "FUNCTION", "HAVING", "REFERENCES", "LEFT", "RIGHT", "OUTER", "INNER",
            "IN", "AND", "OR", "NOT", "IS"
    };
    public static String[] DEFAULT_OPERATORS = new String[]{
            "+", "-", "/", "*", ":", "||", "|", "&&", "&", "<", "<=", ">", ">=", "<>", "!", "!=", "%", "="
    };
    public static String[] DEFAULT_SEPARATORS = new String[]{
            "{", "}", "[", "]", ";",
            ","
    };


    public static String getSqlTypeName(int i,DBCSession session) {
        Field[] f = Types.class.getFields();
        try {
            for (Field field : f) {
                if (field.getInt(null) == i) {
                    return field.getName();
                }
            }
        } catch (IllegalAccessException e) {
            session.getLogger(SQLUtils.class.getName()).log(java.util.logging.Level.SEVERE, null, e);
        }
        return "UNKNOWN(" + i + ")";
    }

    public static int saveValue(PreparedStatement ps, int index, Object value, DBTableColumn col) {
        return saveValue(ps, index, value, col.getSqlType(), col.getPrecision(), DATE_FORMAT, TIME_FORMAT, TIMESTAMP_FORMAT);
    }

    public static int saveValue(PreparedStatement ps, int index, Object value, int sqlType, int precision) {
        return saveValue(ps, index, value, sqlType, precision, DATE_FORMAT, TIME_FORMAT, TIMESTAMP_FORMAT);
    }

    public static int saveValue(PreparedStatement ps, int index, Object value, int sqlType, int precision, DateFormat dateFormat, DateFormat timeFormat, DateFormat timestampFormat) {
        try {
            if (value == null || (value instanceof String && sqlType != Types.CHAR && sqlType != Types.VARCHAR && sqlType != Types.VARBINARY && sqlType != Types.LONGVARBINARY && sqlType != Types.BINARY && sqlType != Types.LONGVARCHAR && ((String) value).length() == 0)) {
                ps.setNull(index, sqlType);
                return 1;
            }
            switch (sqlType) {
                case Types.BIGINT: {
                    if (value instanceof BigDecimal) {
                        ps.setBigDecimal(index, (BigDecimal) value);
                    } else {
                        Class clz = value.getClass();
                        Constructor<BigDecimal> c;
                        c = BigDecimal.class.getConstructor(clz);
                        ps.setBigDecimal(index, c.newInstance(value));
                    }
                    break;
                }
                case Types.BOOLEAN:
                case Types.BIT: {
                    if (value instanceof Boolean) {
                        ps.setBoolean(index, (Boolean) value);
                    } else if (value instanceof Number) {
                        ps.setBoolean(index, ((Number) value).doubleValue() != 0);
                    } else {
                        Class clz = value.getClass();
                        Constructor<Boolean> c;
                        c = Boolean.class.getConstructor(clz);
                        ps.setBoolean(index, c.newInstance(value));
                    }
                    break;
                }

                case Types.DECIMAL:
                case Types.DOUBLE:
                case Types.FLOAT:
                case Types.REAL: {
                    if (value instanceof Number) {
                        ps.setDouble(index, ((Number) value).doubleValue());
                    } else {
                        Class clz = value.getClass();
                        Constructor<Double> c = Double.class.getConstructor(clz);
                        ps.setDouble(index, c.newInstance(value));
                    }
                    break;
                }
                case Types.TINYINT:
                case Types.SMALLINT:
                case Types.INTEGER: {
                    if (value instanceof Number) {
                        ps.setInt(index, ((Number) value).intValue());
                    } else {
                        Class clz = value.getClass();
                        Constructor<Integer> c = Integer.class.getConstructor(clz);
                        ps.setInt(index, c.newInstance(value));
                    }
                    break;
                }
                case Types.NUMERIC: {
                    if (precision > 0) {
                        if (value instanceof Number) {
                            ps.setDouble(index, ((Number) value).doubleValue());
                        } else {
                            Class clz = value.getClass();
                            Constructor<Double> c = Double.class.getConstructor(clz);
                            ps.setDouble(index, c.newInstance(value));
                        }
                    } else {
                        if (value instanceof Number) {
                            ps.setInt(index, ((Number) value).intValue());
                        } else {
                            Class clz = value.getClass();
                            Constructor<Integer> c = Integer.class.getConstructor(clz);
                            ps.setInt(index, c.newInstance(value));
                        }
                    }
                    break;
                }

                case Types.DATE: {
                    if (value instanceof java.sql.Date) {
                        ps.setDate(index, ((java.sql.Date) value));
                    } else if (value instanceof java.util.Date) {
                        ps.setDate(index, new java.sql.Date(((java.util.Date) value).getTime()));
                    } else if (value instanceof String) {
                        ps.setDate(index, new java.sql.Date((dateFormat.parse((String) value)).getTime()));
                    } else {
                        throw new RuntimeException("Non Expected type");
                    }
                    break;
                }
                case Types.TIME: {
                    if (value instanceof java.sql.Time) {
                        ps.setTime(index, ((java.sql.Time) value));
                    } else if (value instanceof java.util.Date) {
                        ps.setTime(index, new java.sql.Time(((java.util.Date) value).getTime()));
                    } else if (value instanceof String) {
                        ps.setTime(index, new java.sql.Time((timeFormat.parse((String) value)).getTime()));
                    } else {
                        throw new RuntimeException("Non Expected type");
                    }
                    break;
                }
                case Types.TIMESTAMP: {
                    if (value instanceof java.sql.Timestamp) {
                        ps.setTimestamp(index, ((java.sql.Timestamp) value));
                    } else if (value instanceof java.util.Date) {
                        ps.setTimestamp(index, new java.sql.Timestamp(((java.util.Date) value).getTime()));
                    } else if (value instanceof String) {
                        ps.setTimestamp(index, new java.sql.Timestamp((timestampFormat.parse((String) value)).getTime()));
                    } else {
                        throw new RuntimeException("Non Expected type");
                    }
                    break;
                }


                case Types.CHAR:
                case Types.VARCHAR:
                case Types.VARBINARY:
                case Types.LONGVARBINARY:
                case Types.BINARY:
                case Types.LONGVARCHAR: {
                    ps.setString(index, String.valueOf(value));
                    break;
                }

                case Types.ARRAY:
                case Types.STRUCT:
                case Types.NULL:
                case Types.OTHER:
                case Types.REF:
                case Types.JAVA_OBJECT:
                case Types.BLOB:
                case Types.CLOB:
                case Types.DISTINCT:
                case Types.DATALINK:
                default: {
                    //unsupported
                }
            }
        } catch (Throwable e) {
            throw new IllegalArgumentException("Error value : " + e, e);
        }
        return 1;
    }

    public static int indexOfStatementByCharIndex(int charIndex, SQLStatement[] statements) {
        return indexOfStatementByCharIndex(charIndex, statements, 0, statements.length - 1);
    }

    public static int indexOfStatementByCharIndex(int charIndex, SQLStatement[] statements, int low, int high) {
        while (low <= high) {
            int mid = (low + high) >> 1;
            SQLStatement midVal = statements[mid];
            int s = midVal.getCharStartIndex();
            int e = midVal.getCharEndIndex();
            int cmp = charIndex < s ? 1 : charIndex >= e ? -1 : 0;

            if (cmp < 0) {
                low = mid + 1;
            } else if (cmp > 0) {
                high = mid - 1;
            } else {
                return mid; // startIndexSearch found
            } // startIndexSearch found
        }
        return -(low + 1);  // startIndexSearch not found.
    }

    public static int indexOfTokenByCharIndex(int charIndex, SQLToken[] tokens) {
        return indexOfTokenByCharIndex(charIndex, tokens, 0, tokens.length - 1);
    }

    public static int indexOfTokenByCharIndex(int charIndex, SQLToken[] tokens, int low, int high) {
        while (low <= high) {
            int mid = (low + high) >> 1;
            SQLToken midVal = tokens[mid];
            int s = midVal.getCharStartIndex();
            int e = midVal.getCharEndIndex();
            int cmp = charIndex < s ? 1 : charIndex >= e ? -1 : 0;

            if (cmp < 0) {
                low = mid + 1;
            } else if (cmp > 0) {
                high = mid - 1;
            } else {
                return mid; // startIndexSearch found
            } // startIndexSearch found
        }
        return -(low + 1);  // startIndexSearch not found.
    }

    /**
     * be careful  charIndex is ABSOLUTE index
     *
     * @param charIndex
     * @param tokens
     * @return SQLToken[] definition
     */
    public static SQLName leftExpandName(int charIndex, SQLToken[] tokens, boolean returnPortion) {
        int stmtCurTokenIndex = indexOfTokenByCharIndex(charIndex, tokens);
        int charIndexNext = charIndex + 1;

        final int TOKEN_ANY = 0;
        final int TOKEN_VAR = 1;
        final int TOKEN_DOT = 2;
        int expect = TOKEN_ANY;
        int pos = stmtCurTokenIndex;
        if (!tokens[pos].accept(SQLTokenGroup.SET_WORDS) && !tokens[pos].accept(SQLTokenGroup.DOT) && !tokens[pos].accept(SQLTokenGroup.KEYWORD)) {
            return null;
        }
        ArrayList<SQLToken> nameTokens = new ArrayList<SQLToken>();
        LabelWhile:
        while (pos >= 0) {
            SQLToken found = tokens[pos];
            if (!found.accept(SQLTokenGroup.COMMENTS, SQLTokenGroup.WHITE)) {
                int foundType = found.accept(SQLTokenGroup.DOT) ? TOKEN_DOT : (found.accept(SQLTokenGroup.SET_WORDS) || found.accept(SQLTokenGroup.KEYWORD)) ? TOKEN_VAR : TOKEN_ANY;
                switch (expect) {
                    case TOKEN_ANY: {
                        if (foundType == TOKEN_ANY) {
                            return null;
                        }
                        if (returnPortion && charIndexNext < found.getCharEndIndex()) {
                            nameTokens.add(0, new SQLToken(found.getCharStartIndex(), charIndexNext, SQLTokenGroup.VAR, SQLTokenType.UNKNOWN, found.getValue().substring(0, charIndexNext - found.getCharStartIndex()), found.getRow(), found.getColumn()));
                        } else {
                            nameTokens.add(0, found);
                        }
                        if (foundType == TOKEN_DOT) {
                            expect = TOKEN_VAR;
                        } else {
                            expect = TOKEN_DOT;
                        }
                        break;
                    }
                    case TOKEN_DOT: {
                        if (foundType != TOKEN_DOT) {
                            break LabelWhile;
                        }
                        if (returnPortion && charIndexNext < found.getCharEndIndex()) {
                            nameTokens.add(0, new SQLToken(found.getCharStartIndex(), charIndexNext, SQLTokenGroup.VAR, SQLTokenType.UNKNOWN, found.getValue().substring(0, charIndexNext - found.getCharStartIndex()), found.getRow(), found.getColumn()));
                        } else {
                            nameTokens.add(0, found);
                        }
                        expect = TOKEN_VAR;
                        break;
                    }
                    case TOKEN_VAR: {
                        if (foundType != TOKEN_VAR) {
                            break LabelWhile;
                        }
                        if (returnPortion && charIndexNext < found.getCharEndIndex()) {
                            nameTokens.add(0, new SQLToken(found.getCharStartIndex(), charIndexNext, SQLTokenGroup.VAR, SQLTokenType.UNKNOWN, found.getValue().substring(0, charIndexNext - found.getCharStartIndex()), found.getRow(), found.getColumn()));
                        } else {
                            nameTokens.add(0, found);
                        }
                        expect = TOKEN_DOT;
                        break;
                    }
                }
            }
            pos--;
        }
        return new SQLName(nameTokens.toArray(new SQLToken[nameTokens.size()]));
    }


    public static int nextValidTokenIndex(int index, SQLToken[] sqlTokens) {
        int x = index + 1;
        while (x < sqlTokens.length && !sqlTokens[x].accept(SQLTokenGroup.WHITE, SQLTokenGroup.COMMENTS)) {
            x++;
        }
        if (x >= sqlTokens.length) {
            return -1;
        }
        return x;
    }

    public static int previousValidTokenIndex(int index, SQLToken[] sqlTokens) {
        int x = index - 1;
        while (x >= 0 && !sqlTokens[x].accept(SQLTokenGroup.WHITE, SQLTokenGroup.COMMENTS)) {
            x--;
        }
        if (x < 0) {
            return -1;
        }
        return x;
    }


    private SQLUtils() {
    }
}
