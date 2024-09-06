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

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.Map;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 14 août 2007 22:32:16
 */
public class DefaultCallableStatementWrapper extends DefaultPreparedStatementWrapper implements CallableStatementWrapper {
    public DefaultCallableStatementWrapper(ConnectionWrapper connectionWrapper, CallableStatement statement, String sql) {
        super(connectionWrapper, statement, sql);
    }

    public CallableStatement getCallableStatement() {
        return (CallableStatement) getStatement();
    }


    public boolean getBoolean(int param_1)
            throws SQLException {
        return getCallableStatement().getBoolean(param_1);
    }

    public boolean getBoolean(String param_1)
            throws SQLException {
        return getCallableStatement().getBoolean(param_1);
    }

    public boolean wasNull()
            throws SQLException {
        return getCallableStatement().wasNull();
    }


    public byte getByte(int param_1)
            throws SQLException {
        return getCallableStatement().getByte(param_1);
    }

    public byte getByte(String param_1)
            throws SQLException {
        return getCallableStatement().getByte(param_1);
    }

    public byte[] getBytes(int param_1)
            throws SQLException {
        return getCallableStatement().getBytes(param_1);
    }

    public byte[] getBytes(String param_1)
            throws SQLException {
        return getCallableStatement().getBytes(param_1);
    }

    public double getDouble(int param_1)
            throws SQLException {
        return getCallableStatement().getDouble(param_1);
    }

    public double getDouble(String param_1)
            throws SQLException {
        return getCallableStatement().getDouble(param_1);
    }

    public float getFloat(int param_1)
            throws SQLException {
        return getCallableStatement().getFloat(param_1);
    }

    public float getFloat(String param_1)
            throws SQLException {
        return getCallableStatement().getFloat(param_1);
    }

    public int getInt(int param_1)
            throws SQLException {
        return getCallableStatement().getInt(param_1);
    }

    public int getInt(String param_1)
            throws SQLException {
        return getCallableStatement().getInt(param_1);
    }


    public Object getObject(int param_1)
            throws SQLException {
        return getCallableStatement().getObject(param_1);
    }

    public Object getObject(int param_1, Map param_2)
            throws SQLException {
        return getCallableStatement().getObject(param_1, param_2);
    }

    public Object getObject(String param_1)
            throws SQLException {
        return getCallableStatement().getObject(param_1);
    }

    public Object getObject(String param_1, Map param_2)
            throws SQLException {
        return getCallableStatement().getObject(param_1, param_2);
    }

    public String getString(int param_1)
            throws SQLException {
        return getCallableStatement().getString(param_1);
    }

    public String getString(String param_1)
            throws SQLException {
        return getCallableStatement().getString(param_1);
    }

    public BigDecimal getBigDecimal(int param_1)
            throws SQLException {
        return getCallableStatement().getBigDecimal(param_1);
    }

    /**
     * @deprecated
     */
    public BigDecimal getBigDecimal(int param_1, int param_2)
            throws SQLException {
        return getCallableStatement().getBigDecimal(param_1, param_2);
    }

    public BigDecimal getBigDecimal(String param_1)
            throws SQLException {
        return getCallableStatement().getBigDecimal(param_1);
    }

    public URL getURL(int param_1)
            throws SQLException {
        return getCallableStatement().getURL(param_1);
    }

    public URL getURL(String param_1)
            throws SQLException {
        return getCallableStatement().getURL(param_1);
    }

    public Array getArray(int param_1)
            throws SQLException {
        return getCallableStatement().getArray(param_1);
    }

    public Array getArray(String param_1)
            throws SQLException {
        return getCallableStatement().getArray(param_1);
    }

    public Blob getBlob(int param_1)
            throws SQLException {
        return getCallableStatement().getBlob(param_1);
    }

    public Blob getBlob(String param_1)
            throws SQLException {
        return getCallableStatement().getBlob(param_1);
    }

    public Clob getClob(int param_1)
            throws SQLException {
        return getCallableStatement().getClob(param_1);
    }

    public Clob getClob(String param_1)
            throws SQLException {
        return getCallableStatement().getClob(param_1);
    }

    public Date getDate(int param_1)
            throws SQLException {
        return getCallableStatement().getDate(param_1);
    }

    public Date getDate(int param_1, Calendar param_2)
            throws SQLException {
        return getCallableStatement().getDate(param_1, param_2);
    }

    public Date getDate(String param_1)
            throws SQLException {
        return getCallableStatement().getDate(param_1);
    }

    public Date getDate(String param_1, Calendar param_2)
            throws SQLException {
        return getCallableStatement().getDate(param_1, param_2);
    }

    public Ref getRef(int param_1)
            throws SQLException {
        return getCallableStatement().getRef(param_1);
    }

    public Ref getRef(String param_1)
            throws SQLException {
        return getCallableStatement().getRef(param_1);
    }

    public Time getTime(int param_1)
            throws SQLException {
        return getCallableStatement().getTime(param_1);
    }

    public Time getTime(int param_1, Calendar param_2)
            throws SQLException {
        return getCallableStatement().getTime(param_1, param_2);
    }

    public Time getTime(String param_1)
            throws SQLException {
        return getCallableStatement().getTime(param_1);
    }

    public Time getTime(String param_1, Calendar param_2)
            throws SQLException {
        return getCallableStatement().getTime(param_1, param_2);
    }

    public Timestamp getTimestamp(int param_1)
            throws SQLException {
        return getCallableStatement().getTimestamp(param_1);
    }

    public Timestamp getTimestamp(int param_1, Calendar param_2)
            throws SQLException {
        return getCallableStatement().getTimestamp(param_1, param_2);
    }

    public Timestamp getTimestamp(String param_1)
            throws SQLException {
        return getCallableStatement().getTimestamp(param_1);
    }

    public Timestamp getTimestamp(String param_1, Calendar param_2)
            throws SQLException {
        return getCallableStatement().getTimestamp(param_1, param_2);
    }

    public long getLong(int param_1)
            throws SQLException {
        return getCallableStatement().getLong(param_1);
    }

    public long getLong(String param_1)
            throws SQLException {
        return getCallableStatement().getLong(param_1);
    }

    public short getShort(int param_1)
            throws SQLException {
        return getCallableStatement().getShort(param_1);
    }

    public short getShort(String param_1)
            throws SQLException {
        return getCallableStatement().getShort(param_1);
    }

    public void registerOutParameter(int param_1, int param_2)
            throws SQLException {
        getCallableStatement().registerOutParameter(param_1, param_2);
    }

    public void registerOutParameter(int param_1, int param_2, int param_3)
            throws SQLException {
        getCallableStatement().registerOutParameter(param_1, param_2, param_3);
    }

    public void registerOutParameter(int param_1, int param_2, String param_3)
            throws SQLException {
        getCallableStatement().registerOutParameter(param_1, param_2, param_3);
    }

    public void registerOutParameter(String param_1, int param_2)
            throws SQLException {
        getCallableStatement().registerOutParameter(param_1, param_2);
    }

    public void registerOutParameter(String param_1, int param_2, int param_3)
            throws SQLException {
        getCallableStatement().registerOutParameter(param_1, param_2, param_3);
    }

    public void registerOutParameter(String param_1, int param_2, String param_3)
            throws SQLException {
        getCallableStatement().registerOutParameter(param_1, param_2, param_3);
    }

    public void setAsciiStream(String param_1, InputStream param_2, int param_3)
            throws SQLException {
        getCallableStatement().setAsciiStream(param_1, param_2, param_3);
    }

    public void setBigDecimal(String param_1, BigDecimal param_2)
            throws SQLException {
        getCallableStatement().setBigDecimal(param_1, param_2);
    }

    public void setBinaryStream(String param_1, InputStream param_2, int param_3)
            throws SQLException {
        getCallableStatement().setBinaryStream(param_1, param_2, param_3);
    }

    public void setBoolean(String param_1, boolean param_2)
            throws SQLException {
        getCallableStatement().setBoolean(param_1, param_2);
    }

    public void setByte(String param_1, byte param_2)
            throws SQLException {
        getCallableStatement().setByte(param_1, param_2);
    }

    public void setBytes(String param_1, byte[] param_2)
            throws SQLException {
        getCallableStatement().setBytes(param_1, param_2);
    }

    public void setCharacterStream(String param_1, Reader param_2, int param_3)
            throws SQLException {
        getCallableStatement().setCharacterStream(param_1, param_2, param_3);
    }

    public void setDate(String param_1, Date param_2)
            throws SQLException {
        getCallableStatement().setDate(param_1, param_2);
    }

    public void setDate(String param_1, Date param_2, Calendar param_3)
            throws SQLException {
        getCallableStatement().setDate(param_1, param_2, param_3);
    }

    public void setDouble(String param_1, double param_2)
            throws SQLException {
        getCallableStatement().setDouble(param_1, param_2);
    }

    public void setFloat(String param_1, float param_2)
            throws SQLException {
        getCallableStatement().setFloat(param_1, param_2);
    }

    public void setInt(String param_1, int param_2)
            throws SQLException {
        getCallableStatement().setInt(param_1, param_2);
    }

    public void setLong(String param_1, long param_2)
            throws SQLException {
        getCallableStatement().setLong(param_1, param_2);
    }

    public void setNull(String param_1, int param_2)
            throws SQLException {
        getCallableStatement().setNull(param_1, param_2);
    }

    public void setNull(String param_1, int param_2, String param_3)
            throws SQLException {
        getCallableStatement().setNull(param_1, param_2, param_3);
    }

    public void setObject(String param_1, Object param_2)
            throws SQLException {
        getCallableStatement().setObject(param_1, param_2);
    }

    public void setObject(String param_1, Object param_2, int param_3)
            throws SQLException {
        getCallableStatement().setObject(param_1, param_2, param_3);
    }

    public void setObject(String param_1, Object param_2, int param_3, int param_4)
            throws SQLException {
        getCallableStatement().setObject(param_1, param_2, param_3, param_4);
    }

    public void setShort(String param_1, short param_2)
            throws SQLException {
        getCallableStatement().setShort(param_1, param_2);
    }

    public void setString(String param_1, String param_2)
            throws SQLException {
        getCallableStatement().setString(param_1, param_2);
    }

    public void setTime(String param_1, Time param_2)
            throws SQLException {
        getCallableStatement().setTime(param_1, param_2);
    }

    public void setTime(String param_1, Time param_2, Calendar param_3)
            throws SQLException {
        getCallableStatement().setTime(param_1, param_2, param_3);
    }

    public void setTimestamp(String param_1, Timestamp param_2)
            throws SQLException {
        getCallableStatement().setTimestamp(param_1, param_2);
    }

    public void setTimestamp(String param_1, Timestamp param_2, Calendar param_3)
            throws SQLException {
        getCallableStatement().setTimestamp(param_1, param_2, param_3);
    }

    public void setURL(String param_1, URL param_2)
            throws SQLException {
        getCallableStatement().setURL(param_1, param_2);
    }

    public RowId getRowId(int parameterIndex) throws SQLException {
        return getCallableStatement().getRowId(parameterIndex);
    }

    public RowId getRowId(String parameterName) throws SQLException {
        return getCallableStatement().getRowId(parameterName);
    }

    public void setRowId(String parameterName, RowId x) throws SQLException {
        getCallableStatement().setRowId(parameterName, x);
    }

    public void setNString(String parameterName, String value) throws SQLException {
        getCallableStatement().setNString(parameterName, value);
    }

    public void setNCharacterStream(String parameterName, Reader value, long length) throws SQLException {
        getCallableStatement().setNCharacterStream(parameterName, value, length);
    }

    public void setNClob(String parameterName, NClob value) throws SQLException {
        getCallableStatement().setNClob(parameterName, value);
    }

    public void setClob(String parameterName, Reader reader, long length) throws SQLException {
        getCallableStatement().setClob(parameterName, reader, length);
    }

    public void setBlob(String parameterName, InputStream inputStream, long length) throws SQLException {
        getCallableStatement().setBlob(parameterName, inputStream, length);
    }

    public void setNClob(String parameterName, Reader reader, long length) throws SQLException {
        getCallableStatement().setNClob(parameterName, reader, length);
    }

    public NClob getNClob(int parameterIndex) throws SQLException {
        return getCallableStatement().getNClob(parameterIndex);
    }

    public NClob getNClob(String parameterName) throws SQLException {
        return getCallableStatement().getNClob(parameterName);
    }

    public void setSQLXML(String parameterName, SQLXML xmlObject) throws SQLException {
        getCallableStatement().setSQLXML(parameterName, xmlObject);
    }

    public SQLXML getSQLXML(int parameterIndex) throws SQLException {
        return getCallableStatement().getSQLXML(parameterIndex);
    }

    public SQLXML getSQLXML(String parameterName) throws SQLException {
        return getCallableStatement().getSQLXML(parameterName);
    }

    public String getNString(int parameterIndex) throws SQLException {
        return getCallableStatement().getNString(parameterIndex);
    }

    public String getNString(String parameterName) throws SQLException {
        return getCallableStatement().getNString(parameterName);
    }

    public Reader getNCharacterStream(int parameterIndex) throws SQLException {
        return getCallableStatement().getNCharacterStream(parameterIndex);
    }

    public Reader getNCharacterStream(String parameterName) throws SQLException {
        return getCallableStatement().getNCharacterStream(parameterName);
    }

    public Reader getCharacterStream(int parameterIndex) throws SQLException {
        return getCallableStatement().getCharacterStream(parameterIndex);
    }

    public Reader getCharacterStream(String parameterName) throws SQLException {
        return getCallableStatement().getCharacterStream(parameterName);
    }

    public void setBlob(String parameterName, Blob x) throws SQLException {
        getCallableStatement().setBlob(parameterName, x);
    }

    public void setClob(String parameterName, Clob x) throws SQLException {
        getCallableStatement().setClob(parameterName, x);
    }

    public void setAsciiStream(String parameterName, InputStream x, long length) throws SQLException {
        getCallableStatement().setAsciiStream(parameterName, x, length);
    }

    public void setBinaryStream(String parameterName, InputStream x, long length) throws SQLException {
        getCallableStatement().setBinaryStream(parameterName, x, length);
    }

    public void setCharacterStream(String parameterName, Reader reader, long length) throws SQLException {
        getCallableStatement().setCharacterStream(parameterName, reader, length);
    }

    public void setAsciiStream(String parameterName, InputStream x) throws SQLException {
        getCallableStatement().setAsciiStream(parameterName, x);
    }

    public void setBinaryStream(String parameterName, InputStream x) throws SQLException {
        getCallableStatement().setBinaryStream(parameterName, x);
    }

    public void setCharacterStream(String parameterName, Reader reader) throws SQLException {
        getCallableStatement().setCharacterStream(parameterName, reader);
    }

    public void setNCharacterStream(String parameterName, Reader value) throws SQLException {
        getCallableStatement().setNCharacterStream(parameterName, value);
    }

    public void setClob(String parameterName, Reader reader) throws SQLException {
        getCallableStatement().setClob(parameterName, reader);
    }

    public void setBlob(String parameterName, InputStream inputStream) throws SQLException {
        getCallableStatement().setBlob(parameterName, inputStream);
    }

    public void setNClob(String parameterName, Reader reader) throws SQLException {
        getCallableStatement().setNClob(parameterName, reader);
    }

    @Override
    public <T> T getObject(int parameterIndex, Class<T> type) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <T> T getObject(String parameterName, Class<T> type) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
}
