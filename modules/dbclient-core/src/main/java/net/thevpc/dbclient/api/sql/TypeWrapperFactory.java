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

package net.thevpc.dbclient.api.sql;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.util.HashMap;


/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 3 juil. 2006 09:56:14
 */
public class TypeWrapperFactory {
    private static TypeWrapperFactory instance;

    public static TypeWrapperFactory getInstance() {
        if (instance == null) {
            instance = new TypeWrapperFactory();
        }
        return instance;
    }

    private HashMap<Class, TypeWrapper> javaClassMap = new HashMap<Class, TypeWrapper>();
    private HashMap<Integer, Class> sqlTypeClassMap = new HashMap<Integer, Class>();

    public TypeWrapperFactory() {
        registerDefaults();
    }


    private void registerDefaults() {
        register(Types.TIME, Time.class);
        register(Types.DATE, Date.class);
        register(Types.TIMESTAMP, Timestamp.class);
        register(Types.INTEGER, Integer.class);
        register(Types.DOUBLE, Double.class);
        register(Types.FLOAT, Double.class);

        register(Types.ARRAY, Object.class);
        register(Types.BIGINT, BigInteger.class);
        register(Types.BINARY, Object.class);
        register(Types.BIT, Boolean.class);
        register(Types.BLOB, Object.class);
        register(Types.CHAR, String.class);
        register(Types.DATALINK, Object.class);
        register(Types.DECIMAL, Double.class);
        register(Types.DISTINCT, Object.class);
        register(Types.JAVA_OBJECT, Object.class);
        register(Types.LONGVARBINARY, Object.class);
        register(Types.LONGVARCHAR, String.class);
        register(Types.NULL, Object.class);
        register(Types.NUMERIC, BigDecimal.class);
        register(Types.OTHER, Object.class);
        register(Types.REAL, Double.class);
        register(Types.REF, Object.class);
        register(Types.SMALLINT, Integer.class);
        register(Types.STRUCT, Object.class);
        register(Types.TINYINT, Integer.class);
        register(Types.VARBINARY, Object.class);
        register(Types.VARCHAR, String.class);

        register(new BigDecimalWrapper(), BigDecimal.class);
        register(new BigIntegerWrapper(), BigInteger.class);
        register(new BooleanWrapper(), Boolean.class);
        register(new ByteWrapper(), Byte.class);
        register(new DateWrapper(), Date.class);

        register(new DoubleWrapper(), Double.class);
        register(new FloatWrapper(), Float.class);
        register(new IntWrapper(), Integer.class);
        register(new ObjectWrapper(), Object.class);
        register(new ShortWrapper(), Short.class);
        register(new StringWrapper(), String.class);
        register(new TimestampWrapper(), Timestamp.class);
        register(new TimeWrapper(), Time.class);

    }

    private void register(TypeWrapper wrapper, Class clazz) {
        javaClassMap.put(clazz, wrapper);
    }

    private void register(int sqlType, Class clazz) {
        sqlTypeClassMap.put(sqlType, clazz);
    }

    public TypeDesc getTypeDesc(int sqltype) {
        return getTypeDesc(sqltype, Object.class);
    }

    public TypeDesc getTypeDesc(int sqltype, Class defaultClass) {
        Class clazz = sqlTypeClassMap.get(sqltype);
        if (clazz == null) {
            clazz = Object.class;
        }
        TypeWrapper wrapper = javaClassMap.get(clazz);
        if (wrapper == null) {
            wrapper = javaClassMap.get(Object.class);
        }
        return new TypeDesc(sqltype, clazz, defaultClass, wrapper);
    }

    public static class StringWrapper implements TypeWrapper {
        public Object getObject(ResultSet rs, int index) throws SQLException {
            return rs.getString(index);
        }
    }

    public static class LongWrapper implements TypeWrapper {
        public Object getObject(ResultSet rs, int index) throws SQLException {
            long x = rs.getLong(index);
            if (rs.wasNull()) {
                return null;
            }
            return x;
        }
    }

    public static class IntWrapper implements TypeWrapper {
        public Object getObject(ResultSet rs, int index) throws SQLException {
            int x = rs.getInt(index);
            if (rs.wasNull()) {
                return null;
            }
            return x;
        }
    }

    public static class DoubleWrapper implements TypeWrapper {
        public Object getObject(ResultSet rs, int index) throws SQLException {
            double x = rs.getDouble(index);
            if (rs.wasNull()) {
                return null;
            }
            return x;
        }
    }

    public static class FloatWrapper implements TypeWrapper {
        public Object getObject(ResultSet rs, int index) throws SQLException {
            float x = rs.getFloat(index);
            if (rs.wasNull()) {
                return null;
            }
            return x;
        }
    }

    public static class ShortWrapper implements TypeWrapper {
        public Object getObject(ResultSet rs, int index) throws SQLException {
            short x = rs.getShort(index);
            if (rs.wasNull()) {
                return null;
            }
            return x;
        }
    }

    public static class BooleanWrapper implements TypeWrapper {
        public Object getObject(ResultSet rs, int index) throws SQLException {
            boolean x = rs.getBoolean(index);
            if (rs.wasNull()) {
                return null;
            }
            return x;
        }
    }

    public static class ByteWrapper implements TypeWrapper {
        public Object getObject(ResultSet rs, int index) throws SQLException {
            byte x = rs.getByte(index);
            if (rs.wasNull()) {
                return null;
            }
            return x;
        }
    }

    public static class ObjectWrapper implements TypeWrapper {
        public Object getObject(ResultSet rs, int index) throws SQLException {
            return rs.getObject(index);
        }
    }

    public static class DateWrapper implements TypeWrapper {
        public Object getObject(ResultSet rs, int index) throws SQLException {
            return rs.getDate(index);
        }
    }

    public static class TimeWrapper implements TypeWrapper {
        public Object getObject(ResultSet rs, int index) throws SQLException {
            return rs.getTime(index);
        }
    }

    public static class TimestampWrapper implements TypeWrapper {
        public Object getObject(ResultSet rs, int index) throws SQLException {
            return rs.getTimestamp(index);
        }
    }

    public static class BigIntegerWrapper implements TypeWrapper {
        public Object getObject(ResultSet rs, int index) throws SQLException {
            BigDecimal bigDecimal = rs.getBigDecimal(index);
            return bigDecimal == null ? null : bigDecimal.toBigInteger();
        }
    }

    public static class BigDecimalWrapper implements TypeWrapper {
        public Object getObject(ResultSet rs, int index) throws SQLException {
            return rs.getBigDecimal(index);
        }
    }

}
