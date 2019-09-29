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


package net.vpc.dbclient.api.sql;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 14 août 2007 22:28:00
 */
public class DefaultPreparedStatementWrapper extends DefaultStatementWrapper implements PreparedStatementWrapper {

    public DefaultPreparedStatementWrapper(ConnectionWrapper connectionWrapper, PreparedStatement statement, String sql) {
        super(connectionWrapper, statement, sql);
    }


    public void setNull(int paramIndex, int sqlType, String typeName) throws SQLException {
        getPreparedStatement().setNull(paramIndex, sqlType, typeName);
    }

    public ResultSet executeQuery() throws SQLException {
        fireFunctionCall("pre-executeQuery", getSql());
        ResultSet resultSet = wrap(getPreparedStatement().executeQuery());
        fireFunctionCall("executeQuery", resultSet, getSql());
        return resultSet;

    }

    public boolean execute()
            throws SQLException {
        fireFunctionCall("pre-execute", getSql());
        boolean b = getPreparedStatement().execute();
        fireFunctionCall("execute", b, getSql());
        return b;
    }

    public int executeUpdate()
            throws SQLException {
        fireFunctionCall("pre-executeUpdate", getSql());
        int i = getPreparedStatement().executeUpdate();
        fireFunctionCall("executeUpdate", i, getSql());
        return i;
    }

    public ParameterMetaData getParameterMetaData()
            throws SQLException {
        return getPreparedStatement().getParameterMetaData();
    }


    public ResultSetMetaData getMetaData()
            throws SQLException {
        return getPreparedStatement().getMetaData();
    }

    public void addBatch()
            throws SQLException {
        fireFunctionCall("pre-addBatch", getSql());
        getPreparedStatement().addBatch();
        fireFunctionCall("addBatch", getSql());
    }

    public void clearParameters()
            throws SQLException {
        fireFunctionCall("pre-clearParameters", getSql());
        getPreparedStatement().clearParameters();
        fireFunctionCall("clearParameters", getSql());
    }

    public void setArray(int param_1, Array param_2)
            throws SQLException {
        getPreparedStatement().setArray(param_1, param_2);
    }

    public void setAsciiStream(int param_1, InputStream param_2, int param_3)
            throws SQLException {
        getPreparedStatement().setAsciiStream(param_1, param_2, param_3);
    }

    public void setBigDecimal(int param_1, BigDecimal param_2)
            throws SQLException {
        getPreparedStatement().setBigDecimal(param_1, param_2);
    }

    public void setBinaryStream(int param_1, InputStream param_2, int param_3)
            throws SQLException {
        getPreparedStatement().setBinaryStream(param_1, param_2, param_3);
    }

    public void setBlob(int param_1, Blob param_2)
            throws SQLException {
        getPreparedStatement().setBlob(param_1, param_2);
    }

    public void setBoolean(int param_1, boolean param_2)
            throws SQLException {
        getPreparedStatement().setBoolean(param_1, param_2);
    }

    public void setByte(int param_1, byte param_2)
            throws SQLException {
        getPreparedStatement().setByte(param_1, param_2);
    }

    public void setBytes(int param_1, byte[] param_2)
            throws SQLException {
        getPreparedStatement().setBytes(param_1, param_2);
    }

    public void setCharacterStream(int param_1, Reader param_2, int param_3)
            throws SQLException {
        getPreparedStatement().setCharacterStream(param_1, param_2, param_3);
    }

    public void setClob(int param_1, Clob param_2)
            throws SQLException {
        getPreparedStatement().setClob(param_1, param_2);
    }

    public void setDate(int param_1, Date param_2)
            throws SQLException {
        getPreparedStatement().setDate(param_1, param_2);
    }

    public void setDate(int param_1, Date param_2, Calendar param_3)
            throws SQLException {
        getPreparedStatement().setDate(param_1, param_2, param_3);
    }

    public void setDouble(int param_1, double param_2)
            throws SQLException {
        getPreparedStatement().setDouble(param_1, param_2);
    }

    public void setFloat(int param_1, float param_2)
            throws SQLException {
        getPreparedStatement().setFloat(param_1, param_2);
    }

    public void setInt(int param_1, int param_2)
            throws SQLException {
        getPreparedStatement().setInt(param_1, param_2);
    }

    public void setLong(int param_1, long param_2)
            throws SQLException {
        getPreparedStatement().setLong(param_1, param_2);
    }

    public void setNull(int param_1, int param_2)
            throws SQLException {
        getPreparedStatement().setNull(param_1, param_2);
    }

    public void setObject(int param_1, Object param_2)
            throws SQLException {
        getPreparedStatement().setObject(param_1, param_2);
    }

    public void setObject(int param_1, Object param_2, int param_3)
            throws SQLException {
        getPreparedStatement().setObject(param_1, param_2, param_3);
    }

    public void setObject(int param_1, Object param_2, int param_3, int param_4)
            throws SQLException {
        getPreparedStatement().setObject(param_1, param_2, param_3, param_4);
    }

    public void setRef(int param_1, Ref param_2)
            throws SQLException {
        getPreparedStatement().setRef(param_1, param_2);
    }

    public void setShort(int param_1, short param_2)
            throws SQLException {
        getPreparedStatement().setShort(param_1, param_2);
    }

    public void setString(int param_1, String param_2)
            throws SQLException {
        getPreparedStatement().setString(param_1, param_2);
    }

    public void setTime(int param_1, Time param_2)
            throws SQLException {
        getPreparedStatement().setTime(param_1, param_2);
    }

    public void setTime(int param_1, Time param_2, Calendar param_3)
            throws SQLException {
        getPreparedStatement().setTime(param_1, param_2, param_3);
    }

    public void setTimestamp(int param_1, Timestamp param_2)
            throws SQLException {
        getPreparedStatement().setTimestamp(param_1, param_2);
    }

    public void setTimestamp(int param_1, Timestamp param_2, Calendar param_3)
            throws SQLException {
        getPreparedStatement().setTimestamp(param_1, param_2, param_3);
    }

    public void setURL(int param_1, URL param_2)
            throws SQLException {
        getPreparedStatement().setURL(param_1, param_2);
    }

    /**
     * @deprecated
     */
    public void setUnicodeStream(int param_1, InputStream param_2, int param_3)
            throws SQLException {
        getPreparedStatement().setUnicodeStream(param_1, param_2, param_3);
    }


    public PreparedStatement getPreparedStatement() {
        return (PreparedStatement) getStatement();
    }

    public void setRowId(int parameterIndex, RowId x) throws SQLException {
        getPreparedStatement().setRowId(parameterIndex, x);
    }

    public void setNString(int parameterIndex, String value) throws SQLException {
        getPreparedStatement().setNString(parameterIndex, value);
    }

    public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {
        getPreparedStatement().setNCharacterStream(parameterIndex, value, length);
    }

    public void setNClob(int parameterIndex, NClob value) throws SQLException {
        getPreparedStatement().setNClob(parameterIndex, value);
    }

    public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {
        getPreparedStatement().setClob(parameterIndex, reader, length);
    }

    public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {
        getPreparedStatement().setBlob(parameterIndex, inputStream, length);
    }

    public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
        getPreparedStatement().setNClob(parameterIndex, reader, length);
    }

    public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
        getPreparedStatement().setSQLXML(parameterIndex, xmlObject);
    }

    public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {
        getPreparedStatement().setAsciiStream(parameterIndex, x, length);
    }

    public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {
        getPreparedStatement().setBinaryStream(parameterIndex, x, length);
    }

    public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
        getPreparedStatement().setCharacterStream(parameterIndex, reader, length);
    }

    public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
        getPreparedStatement().setAsciiStream(parameterIndex, x);
    }

    public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
        getPreparedStatement().setBinaryStream(parameterIndex, x);
    }

    public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {
        getPreparedStatement().setCharacterStream(parameterIndex, reader);
    }

    public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {
        getPreparedStatement().setNCharacterStream(parameterIndex, value);
    }

    public void setClob(int parameterIndex, Reader reader) throws SQLException {
        getPreparedStatement().setClob(parameterIndex, reader);
    }

    public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {
        getPreparedStatement().setBlob(parameterIndex, inputStream);
    }

    public void setNClob(int parameterIndex, Reader reader) throws SQLException {
        getPreparedStatement().setNClob(parameterIndex, reader);
    }
}
