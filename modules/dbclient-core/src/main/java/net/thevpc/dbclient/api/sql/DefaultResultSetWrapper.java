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
 * @creationtime 14 août 2007 23:09:00
 */
public class DefaultResultSetWrapper implements ResultSetWrapper {
    private StatementWrapper statementWrapper;
    protected ResultSet handledInstance;

    public DefaultResultSetWrapper(StatementWrapper statementWrapper, ResultSet handledInstance) {
        this.statementWrapper = statementWrapper;
        this.handledInstance = handledInstance;
    }

    public StatementWrapper getStatementWrapper() {
        return statementWrapper;
    }

    public ResultSet getResultSet() {
        return handledInstance;
    }

    public boolean absolute(int param_1)
            throws SQLException {
        return handledInstance.absolute(param_1);
    }

    public boolean first()
            throws SQLException {
        return handledInstance.first();
    }

    public boolean getBoolean(int param_1)
            throws SQLException {
        return handledInstance.getBoolean(param_1);
    }

    public boolean getBoolean(String param_1)
            throws SQLException {
        return handledInstance.getBoolean(param_1);
    }

    public boolean isAfterLast()
            throws SQLException {
        return handledInstance.isAfterLast();
    }

    public boolean isBeforeFirst()
            throws SQLException {
        return handledInstance.isBeforeFirst();
    }

    public boolean isFirst()
            throws SQLException {
        return handledInstance.isFirst();
    }

    public boolean isLast()
            throws SQLException {
        return handledInstance.isLast();
    }

    public boolean last()
            throws SQLException {
        return handledInstance.last();
    }

    public boolean next()
            throws SQLException {
        return handledInstance.next();
    }

    public boolean previous()
            throws SQLException {
        return handledInstance.previous();
    }

    public boolean relative(int param_1)
            throws SQLException {
        return handledInstance.relative(param_1);
    }

    public boolean rowDeleted()
            throws SQLException {
        return handledInstance.rowDeleted();
    }

    public boolean rowInserted()
            throws SQLException {
        return handledInstance.rowInserted();
    }

    public boolean rowUpdated()
            throws SQLException {
        return handledInstance.rowUpdated();
    }

    public boolean wasNull()
            throws SQLException {
        return handledInstance.wasNull();
    }

    public byte getByte(int param_1)
            throws SQLException {
        return handledInstance.getByte(param_1);
    }

    public byte getByte(String param_1)
            throws SQLException {
        return handledInstance.getByte(param_1);
    }

    public byte[] getBytes(int param_1)
            throws SQLException {
        return handledInstance.getBytes(param_1);
    }

    public byte[] getBytes(String param_1)
            throws SQLException {
        return handledInstance.getBytes(param_1);
    }

    public double getDouble(int param_1)
            throws SQLException {
        return handledInstance.getDouble(param_1);
    }

    public double getDouble(String param_1)
            throws SQLException {
        return handledInstance.getDouble(param_1);
    }

    public float getFloat(int param_1)
            throws SQLException {
        return handledInstance.getFloat(param_1);
    }

    public float getFloat(String param_1)
            throws SQLException {
        return handledInstance.getFloat(param_1);
    }

    public int findColumn(String param_1)
            throws SQLException {
        return handledInstance.findColumn(param_1);
    }

    public int getConcurrency()
            throws SQLException {
        return handledInstance.getConcurrency();
    }

    public int getFetchDirection()
            throws SQLException {
        return handledInstance.getFetchDirection();
    }

    public int getFetchSize()
            throws SQLException {
        return handledInstance.getFetchSize();
    }

    public int getInt(int param_1)
            throws SQLException {
        return handledInstance.getInt(param_1);
    }

    public int getInt(String param_1)
            throws SQLException {
        return handledInstance.getInt(param_1);
    }

    public int getRow()
            throws SQLException {
        return handledInstance.getRow();
    }

    public int getType()
            throws SQLException {
        return handledInstance.getType();
    }

    public InputStream getAsciiStream(int param_1)
            throws SQLException {
        return handledInstance.getAsciiStream(param_1);
    }

    public InputStream getAsciiStream(String param_1)
            throws SQLException {
        return handledInstance.getAsciiStream(param_1);
    }

    public InputStream getBinaryStream(int param_1)
            throws SQLException {
        return handledInstance.getBinaryStream(param_1);
    }

    public InputStream getBinaryStream(String param_1)
            throws SQLException {
        return handledInstance.getBinaryStream(param_1);
    }

    /**
     * @deprecated
     */
    public InputStream getUnicodeStream(int param_1)
            throws SQLException {
        return handledInstance.getUnicodeStream(param_1);
    }

    /**
     * @deprecated
     */
    public InputStream getUnicodeStream(String param_1)
            throws SQLException {
        return handledInstance.getUnicodeStream(param_1);
    }

    public Reader getCharacterStream(int param_1)
            throws SQLException {
        return handledInstance.getCharacterStream(param_1);
    }

    public Reader getCharacterStream(String param_1)
            throws SQLException {
        return handledInstance.getCharacterStream(param_1);
    }

    public Object getObject(int param_1)
            throws SQLException {
        return handledInstance.getObject(param_1);
    }

    public Object getObject(int param_1, Map param_2)
            throws SQLException {
        return handledInstance.getObject(param_1, param_2);
    }

    public Object getObject(String param_1)
            throws SQLException {
        return handledInstance.getObject(param_1);
    }

    public Object getObject(String param_1, Map param_2)
            throws SQLException {
        return handledInstance.getObject(param_1, param_2);
    }

    public String getCursorName()
            throws SQLException {
        return handledInstance.getCursorName();
    }

    public String getString(int param_1)
            throws SQLException {
        return handledInstance.getString(param_1);
    }

    public String getString(String param_1)
            throws SQLException {
        return handledInstance.getString(param_1);
    }

    public BigDecimal getBigDecimal(int param_1)
            throws SQLException {
        return handledInstance.getBigDecimal(param_1);
    }

    /**
     * @deprecated
     */
    public BigDecimal getBigDecimal(int param_1, int param_2)
            throws SQLException {
        return handledInstance.getBigDecimal(param_1, param_2);
    }

    public BigDecimal getBigDecimal(String param_1)
            throws SQLException {
        return handledInstance.getBigDecimal(param_1);
    }

    /**
     * @deprecated
     */
    public BigDecimal getBigDecimal(String param_1, int param_2)
            throws SQLException {
        return handledInstance.getBigDecimal(param_1, param_2);
    }

    public URL getURL(int param_1)
            throws SQLException {
        return handledInstance.getURL(param_1);
    }

    public URL getURL(String param_1)
            throws SQLException {
        return handledInstance.getURL(param_1);
    }

    public Array getArray(int param_1)
            throws SQLException {
        return handledInstance.getArray(param_1);
    }

    public Array getArray(String param_1)
            throws SQLException {
        return handledInstance.getArray(param_1);
    }

    public Blob getBlob(int param_1)
            throws SQLException {
        return handledInstance.getBlob(param_1);
    }

    public Blob getBlob(String param_1)
            throws SQLException {
        return handledInstance.getBlob(param_1);
    }

    public Clob getClob(int param_1)
            throws SQLException {
        return handledInstance.getClob(param_1);
    }

    public Clob getClob(String param_1)
            throws SQLException {
        return handledInstance.getClob(param_1);
    }

    public Date getDate(int param_1)
            throws SQLException {
        return handledInstance.getDate(param_1);
    }

    public Date getDate(int param_1, Calendar param_2)
            throws SQLException {
        return handledInstance.getDate(param_1, param_2);
    }

    public Date getDate(String param_1)
            throws SQLException {
        return handledInstance.getDate(param_1);
    }

    public Date getDate(String param_1, Calendar param_2)
            throws SQLException {
        return handledInstance.getDate(param_1, param_2);
    }

    public Ref getRef(int param_1)
            throws SQLException {
        return handledInstance.getRef(param_1);
    }

    public Ref getRef(String param_1)
            throws SQLException {
        return handledInstance.getRef(param_1);
    }

    public ResultSetMetaData getMetaData()
            throws SQLException {
        return handledInstance.getMetaData();
    }

    public SQLWarning getWarnings()
            throws SQLException {
        return handledInstance.getWarnings();
    }

    public Statement getStatement()
            throws SQLException {
        return handledInstance.getStatement();
    }

    public Time getTime(int param_1)
            throws SQLException {
        return handledInstance.getTime(param_1);
    }

    public Time getTime(int param_1, Calendar param_2)
            throws SQLException {
        return handledInstance.getTime(param_1, param_2);
    }

    public Time getTime(String param_1)
            throws SQLException {
        return handledInstance.getTime(param_1);
    }

    public Time getTime(String param_1, Calendar param_2)
            throws SQLException {
        return handledInstance.getTime(param_1, param_2);
    }

    public Timestamp getTimestamp(int param_1)
            throws SQLException {
        return handledInstance.getTimestamp(param_1);
    }

    public Timestamp getTimestamp(int param_1, Calendar param_2)
            throws SQLException {
        return handledInstance.getTimestamp(param_1, param_2);
    }

    public Timestamp getTimestamp(String param_1)
            throws SQLException {
        return handledInstance.getTimestamp(param_1);
    }

    public Timestamp getTimestamp(String param_1, Calendar param_2)
            throws SQLException {
        return handledInstance.getTimestamp(param_1, param_2);
    }

    public long getLong(int param_1)
            throws SQLException {
        return handledInstance.getLong(param_1);
    }

    public long getLong(String param_1)
            throws SQLException {
        return handledInstance.getLong(param_1);
    }

    public short getShort(int param_1)
            throws SQLException {
        return handledInstance.getShort(param_1);
    }

    public short getShort(String param_1)
            throws SQLException {
        return handledInstance.getShort(param_1);
    }

    public void afterLast()
            throws SQLException {
        handledInstance.afterLast();
    }

    public void beforeFirst()
            throws SQLException {
        handledInstance.beforeFirst();
    }

    public void cancelRowUpdates()
            throws SQLException {
        handledInstance.cancelRowUpdates();
    }

    public void clearWarnings()
            throws SQLException {
        handledInstance.clearWarnings();
    }

    public void close()
            throws SQLException {
        handledInstance.close();
    }

    public void deleteRow()
            throws SQLException {
        handledInstance.deleteRow();
    }

    public void insertRow()
            throws SQLException {
        handledInstance.insertRow();
    }

    public void moveToCurrentRow()
            throws SQLException {
        handledInstance.moveToCurrentRow();
    }

    public void moveToInsertRow()
            throws SQLException {
        handledInstance.moveToInsertRow();
    }

    public void refreshRow()
            throws SQLException {
        handledInstance.refreshRow();
    }

    public void setFetchDirection(int param_1)
            throws SQLException {
        handledInstance.setFetchDirection(param_1);
    }

    public void setFetchSize(int param_1)
            throws SQLException {
        handledInstance.setFetchSize(param_1);
    }

    public void updateArray(int param_1, Array param_2)
            throws SQLException {
        handledInstance.updateArray(param_1, param_2);
    }

    public void updateArray(String param_1, Array param_2)
            throws SQLException {
        handledInstance.updateArray(param_1, param_2);
    }

    public void updateAsciiStream(int param_1, InputStream param_2, int param_3)
            throws SQLException {
        handledInstance.updateAsciiStream(param_1, param_2, param_3);
    }

    public void updateAsciiStream(String param_1, InputStream param_2, int param_3)
            throws SQLException {
        handledInstance.updateAsciiStream(param_1, param_2, param_3);
    }

    public void updateBigDecimal(int param_1, BigDecimal param_2)
            throws SQLException {
        handledInstance.updateBigDecimal(param_1, param_2);
    }

    public void updateBigDecimal(String param_1, BigDecimal param_2)
            throws SQLException {
        handledInstance.updateBigDecimal(param_1, param_2);
    }

    public void updateBinaryStream(int param_1, InputStream param_2, int param_3)
            throws SQLException {
        handledInstance.updateBinaryStream(param_1, param_2, param_3);
    }

    public void updateBinaryStream(String param_1, InputStream param_2, int param_3)
            throws SQLException {
        handledInstance.updateBinaryStream(param_1, param_2, param_3);
    }

    public void updateBlob(int param_1, Blob param_2)
            throws SQLException {
        handledInstance.updateBlob(param_1, param_2);
    }

    public void updateBlob(String param_1, Blob param_2)
            throws SQLException {
        handledInstance.updateBlob(param_1, param_2);
    }

    public void updateBoolean(int param_1, boolean param_2)
            throws SQLException {
        handledInstance.updateBoolean(param_1, param_2);
    }

    public void updateBoolean(String param_1, boolean param_2)
            throws SQLException {
        handledInstance.updateBoolean(param_1, param_2);
    }

    public void updateByte(int param_1, byte param_2)
            throws SQLException {
        handledInstance.updateByte(param_1, param_2);
    }

    public void updateByte(String param_1, byte param_2)
            throws SQLException {
        handledInstance.updateByte(param_1, param_2);
    }

    public void updateBytes(int param_1, byte[] param_2)
            throws SQLException {
        handledInstance.updateBytes(param_1, param_2);
    }

    public void updateBytes(String param_1, byte[] param_2)
            throws SQLException {
        handledInstance.updateBytes(param_1, param_2);
    }

    public void updateCharacterStream(int param_1, Reader param_2, int param_3)
            throws SQLException {
        handledInstance.updateCharacterStream(param_1, param_2, param_3);
    }

    public void updateCharacterStream(String param_1, Reader param_2, int param_3)
            throws SQLException {
        handledInstance.updateCharacterStream(param_1, param_2, param_3);
    }

    public void updateClob(int param_1, Clob param_2)
            throws SQLException {
        handledInstance.updateClob(param_1, param_2);
    }

    public void updateClob(String param_1, Clob param_2)
            throws SQLException {
        handledInstance.updateClob(param_1, param_2);
    }

    public void updateDate(int param_1, Date param_2)
            throws SQLException {
        handledInstance.updateDate(param_1, param_2);
    }

    public void updateDate(String param_1, Date param_2)
            throws SQLException {
        handledInstance.updateDate(param_1, param_2);
    }

    public void updateDouble(int param_1, double param_2)
            throws SQLException {
        handledInstance.updateDouble(param_1, param_2);
    }

    public void updateDouble(String param_1, double param_2)
            throws SQLException {
        handledInstance.updateDouble(param_1, param_2);
    }

    public void updateFloat(int param_1, float param_2)
            throws SQLException {
        handledInstance.updateFloat(param_1, param_2);
    }

    public void updateFloat(String param_1, float param_2)
            throws SQLException {
        handledInstance.updateFloat(param_1, param_2);
    }

    public void updateInt(int param_1, int param_2)
            throws SQLException {
        handledInstance.updateInt(param_1, param_2);
    }

    public void updateInt(String param_1, int param_2)
            throws SQLException {
        handledInstance.updateInt(param_1, param_2);
    }

    public void updateLong(int param_1, long param_2)
            throws SQLException {
        handledInstance.updateLong(param_1, param_2);
    }

    public void updateLong(String param_1, long param_2)
            throws SQLException {
        handledInstance.updateLong(param_1, param_2);
    }

    public void updateNull(int param_1)
            throws SQLException {
        handledInstance.updateNull(param_1);
    }

    public void updateNull(String param_1)
            throws SQLException {
        handledInstance.updateNull(param_1);
    }

    public void updateObject(int param_1, Object param_2)
            throws SQLException {
        handledInstance.updateObject(param_1, param_2);
    }

    public void updateObject(int param_1, Object param_2, int param_3)
            throws SQLException {
        handledInstance.updateObject(param_1, param_2, param_3);
    }

    public void updateObject(String param_1, Object param_2)
            throws SQLException {
        handledInstance.updateObject(param_1, param_2);
    }

    public void updateObject(String param_1, Object param_2, int param_3)
            throws SQLException {
        handledInstance.updateObject(param_1, param_2, param_3);
    }

    public void updateRef(int param_1, Ref param_2)
            throws SQLException {
        handledInstance.updateRef(param_1, param_2);
    }

    public void updateRef(String param_1, Ref param_2)
            throws SQLException {
        handledInstance.updateRef(param_1, param_2);
    }

    public void updateRow()
            throws SQLException {
        handledInstance.updateRow();
    }

    public void updateShort(int param_1, short param_2)
            throws SQLException {
        handledInstance.updateShort(param_1, param_2);
    }

    public void updateShort(String param_1, short param_2)
            throws SQLException {
        handledInstance.updateShort(param_1, param_2);
    }

    public void updateString(int param_1, String param_2)
            throws SQLException {
        handledInstance.updateString(param_1, param_2);
    }

    public void updateString(String param_1, String param_2)
            throws SQLException {
        handledInstance.updateString(param_1, param_2);
    }

    public void updateTime(int param_1, Time param_2)
            throws SQLException {
        handledInstance.updateTime(param_1, param_2);
    }

    public void updateTime(String param_1, Time param_2)
            throws SQLException {
        handledInstance.updateTime(param_1, param_2);
    }

    public void updateTimestamp(int param_1, Timestamp param_2)
            throws SQLException {
        handledInstance.updateTimestamp(param_1, param_2);
    }

    public void updateTimestamp(String param_1, Timestamp param_2)
            throws SQLException {
        handledInstance.updateTimestamp(param_1, param_2);
    }


    public RowId getRowId(int columnIndex) throws SQLException {
        return handledInstance.getRowId(columnIndex);  
    }

    public RowId getRowId(String columnLabel) throws SQLException {
        return handledInstance.getRowId(columnLabel);  
    }

    public void updateRowId(int columnIndex, RowId x) throws SQLException {
        handledInstance.updateRowId(columnIndex, x);
    }

    public void updateRowId(String columnLabel, RowId x) throws SQLException {
        handledInstance.updateRowId(columnLabel, x);
    }

    public int getHoldability() throws SQLException {
        return handledInstance.getHoldability();  
    }

    public boolean isClosed() throws SQLException {
        return handledInstance.isClosed();  
    }

    public void updateNString(int columnIndex, String nString) throws SQLException {
        handledInstance.updateNString(columnIndex, nString);
    }

    public void updateNString(String columnLabel, String nString) throws SQLException {
        handledInstance.updateNString(columnLabel, nString);
    }

    public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
       handledInstance.updateNClob(columnIndex, nClob); 
    }

    public void updateNClob(String columnLabel, NClob nClob) throws SQLException {
      handledInstance.updateNClob(columnLabel, nClob);  
    }

    public NClob getNClob(int columnIndex) throws SQLException {
        return handledInstance.getNClob(columnIndex);  
    }

    public NClob getNClob(String columnLabel) throws SQLException {
        return handledInstance.getNClob(columnLabel);  
    }

    public SQLXML getSQLXML(int columnIndex) throws SQLException {
        return handledInstance.getSQLXML(columnIndex);  
    }

    public SQLXML getSQLXML(String columnLabel) throws SQLException {
        return handledInstance.getSQLXML(columnLabel);  
    }

    public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {
       handledInstance.updateSQLXML(columnIndex,xmlObject ); 
    }

    public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {
      handledInstance.updateSQLXML(columnLabel,xmlObject );  
    }

    public String getNString(int columnIndex) throws SQLException {
        return handledInstance.getNString(columnIndex);  
    }

    public String getNString(String columnLabel) throws SQLException {
        return handledInstance.getNString(columnLabel);  
    }

    public Reader getNCharacterStream(int columnIndex) throws SQLException {
        return handledInstance.getNCharacterStream(columnIndex);  
    }

    public Reader getNCharacterStream(String columnLabel) throws SQLException {
        return handledInstance.getNCharacterStream(columnLabel);  
    }

    public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
       handledInstance.updateNCharacterStream(columnIndex, x,length); 
    }

    public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
       handledInstance.updateNCharacterStream(columnLabel, reader,length); 
    }

    public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {
      handledInstance.updateAsciiStream(columnIndex, x,length);  
    }

    public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {
       handledInstance.updateBinaryStream(columnIndex, x,length); 
    }

    public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
      handledInstance.updateCharacterStream(columnIndex, x,length);  
    }

    public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {
      handledInstance.updateAsciiStream(columnLabel, x,length);  
    }

    public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {
       handledInstance.updateBinaryStream(columnLabel, x,length); 
    }

    public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
       handledInstance.updateCharacterStream(columnLabel, reader,length); 
    }

    public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException {
       handledInstance.updateBlob(columnIndex, inputStream,length); 
    }

    public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException {
      handledInstance.updateBlob(columnLabel, inputStream,length);  
    }

    public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {
       handledInstance.updateClob(columnIndex, reader,length); 
    }

    public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {
       handledInstance.updateClob(columnLabel, reader,length); 
    }

    public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {
      handledInstance.updateNClob(columnIndex, reader,length);  
    }

    public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {
       handledInstance.updateNClob(columnLabel, reader,length); 
    }

    public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {
       handledInstance.updateNCharacterStream(columnIndex, x); 
    }

    public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {
      handledInstance.updateNCharacterStream(columnLabel, reader);  
    }

    public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {
      handledInstance.updateAsciiStream(columnIndex, x);  
    }

    public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {
      handledInstance.updateBinaryStream(columnIndex, x);  
    }

    public void updateCharacterStream(int columnIndex, Reader x) throws SQLException {
      handledInstance.updateCharacterStream(columnIndex, x);  
    }

    public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {
      handledInstance.updateAsciiStream(columnLabel, x);  
    }

    public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {
      handledInstance.updateBinaryStream(columnLabel, x);  
    }

    public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException {
      handledInstance.updateCharacterStream(columnLabel, reader);  
    }

    public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException {
      handledInstance.updateBlob(columnIndex, inputStream);  
    }

    public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException {
      handledInstance.updateBlob(columnLabel, inputStream);  
    }

    public void updateClob(int columnIndex, Reader reader) throws SQLException {
      handledInstance.updateClob(columnIndex, reader);  
    }

    public void updateClob(String columnLabel, Reader reader) throws SQLException {
      handledInstance.updateClob(columnLabel, reader);  
    }

    public void updateNClob(int columnIndex, Reader reader) throws SQLException {
      handledInstance.updateNClob(columnIndex, reader);  
    }

    public void updateNClob(String columnLabel, Reader reader) throws SQLException {
      handledInstance.updateNClob(columnLabel, reader);  
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        return handledInstance.unwrap(iface);  
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return handledInstance.isWrapperFor(iface);  
    }

    @Override
    public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
}
