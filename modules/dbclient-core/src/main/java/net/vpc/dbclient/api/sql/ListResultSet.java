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
package net.vpc.dbclient.api.sql;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * ResultSet Implementation as a List of Object[] rows
 *
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 27 mars. 2009
 */
public class ListResultSet implements ResultSet {
    private ResultSetMetaData metadata;
    private ResultSet rs;
    private int lastLine;
    private int line;
    private int type = ResultSet.TYPE_SCROLL_INSENSITIVE;
    private int fetchDirection = ResultSet.FETCH_UNKNOWN;
    private int fetchSize = 0;
    private int concurrentcy = ResultSet.CONCUR_UPDATABLE;
    private List<Object[]> rows;
    private boolean wasNull;
    private Map<String, Integer> columnIndexTable = new HashMap<String, Integer>();

    public ListResultSet(ResultSet rs, ResultSetMetaData metadata, Object[][] rows) {
        this(rs, metadata, new ArrayList<Object[]>(Arrays.asList(rows)));
    }

    public ListResultSet(ResultSet rs, ResultSetMetaData metadata, List<Object[]> rows) {
        this.rows = rows;
        this.rs = rs;
        this.metadata = metadata;
        line = -1;
    }

    private int getColumnIndex(String name) throws SQLException {
        Integer index = columnIndexTable.get(name);
        if (index == null && metadata != null) {
            int count = metadata.getColumnCount();
            for (int i = 0; i < count; i++) {
                String s = metadata.getColumnName(i + 1);
                if (name.equals(s)) {
                    columnIndexTable.put(name, i);
                    index = i;
                    break;
                }
            }
        }
        if (index == null) {
            return -1;
        }
        return index;
    }

    public boolean absolute(int param_1)
            throws SQLException {
        if (param_1 >= 0) {
            line = param_1 - 1;
            if (line < 0) {
                line = -1;
            }
        } else {
            line = rows.size() - param_1;
            if (line < 0) {
                line = -1;
            }
        }
        return true;
    }

    public boolean first()
            throws SQLException {
        line = 0;
        return rows.size() > 0;
    }

    public boolean getBoolean(int param_1)
            throws SQLException {
        Boolean b = (Boolean) getObject(param_1);
        return wasNull ? false : b;
    }

    public boolean getBoolean(String param_1)
            throws SQLException {
        Boolean b = (Boolean) getObject(param_1);
        return wasNull ? false : b;
    }

    public boolean isAfterLast()
            throws SQLException {
        return line == (rows.size());
    }

    public boolean isBeforeFirst()
            throws SQLException {
        return line == -1;
    }

    public boolean isFirst()
            throws SQLException {
        return line == 0;
    }

    public boolean isLast()
            throws SQLException {
        return line >= 0 && line == (rows.size() - 1);
    }

    public boolean last()
            throws SQLException {
        if (rows.size() > 0) {
            line = rows.size() - 1;
            return true;
        }
        return false;
    }

    public boolean next()
            throws SQLException {
        return relative(1);
    }

    public boolean previous()
            throws SQLException {
        return relative(-1);
    }

    public boolean relative(int param_1)
            throws SQLException {
        line = line + param_1;
        if (line < -1) {
            line = -1;
            return false;
        } else if (line >= rows.size()) {
            line = rows.size();
            return false;
        }
        return true;
    }

    public boolean rowDeleted()
            throws SQLException {
        return false;
    }

    public boolean rowInserted()
            throws SQLException {
        return false;
    }

    public boolean rowUpdated()
            throws SQLException {
        return false;
    }

    public boolean wasNull()
            throws SQLException {
        return wasNull;
    }

    public byte getByte(int param_1)
            throws SQLException {
        Byte b = (Byte) getObject(param_1);
        return wasNull ? 0 : b;
    }

    public byte getByte(String param_1)
            throws SQLException {
        Byte b = (Byte) getObject(param_1);
        return wasNull ? 0 : b;
    }

    public byte[] getBytes(int param_1)
            throws SQLException {
        return (byte[]) getObject(param_1);
    }

    public byte[] getBytes(String param_1)
            throws SQLException {
        return (byte[]) getObject(param_1);
    }

    public double getDouble(int param_1)
            throws SQLException {
        Double b = (Double) getObject(param_1);
        return wasNull ? 0.0 : b;
    }

    public double getDouble(String param_1)
            throws SQLException {
        Double b = (Double) getObject(param_1);
        return wasNull ? 0.0 : b;
    }

    public float getFloat(int param_1)
            throws SQLException {
        Float b = (Float) getObject(param_1);
        return wasNull ? 0.0f : b;
    }

    public float getFloat(String param_1)
            throws SQLException {
        Float b = (Float) getObject(param_1);
        return wasNull ? 0.0f : b;
    }

    public int findColumn(String param_1)
            throws SQLException {
        return getColumnIndex(param_1) + 1;
    }

    public int getConcurrency()
            throws SQLException {
        return concurrentcy;
    }

    public int getFetchDirection()
            throws SQLException {
        return fetchDirection;
    }

    public int getFetchSize()
            throws SQLException {
        return fetchSize;
    }

    public int getInt(int param_1)
            throws SQLException {
        Integer b = (Integer) getObject(param_1);
        return wasNull ? 0 : b;
    }

    public int getInt(String param_1)
            throws SQLException {
        Integer b = (Integer) getObject(param_1);
        return wasNull ? 0 : b;
    }

    public int getRow()
            throws SQLException {
        return line;
    }

    public int getType()
            throws SQLException {
        return type;
    }

    public void setType(int type)
            throws SQLException {
        this.type = type;
    }


    public InputStream getAsciiStream(int param_1)
            throws SQLException {
        return (InputStream) getObject(param_1);
    }

    public InputStream getAsciiStream(String param_1)
            throws SQLException {
        return (InputStream) getObject(param_1);
    }

    public InputStream getBinaryStream(int param_1)
            throws SQLException {
        return (InputStream) getObject(param_1);
    }

    public InputStream getBinaryStream(String param_1)
            throws SQLException {
        return (InputStream) getObject(param_1);
    }

    /**
     * @deprecated
     */
    public InputStream getUnicodeStream(int param_1)
            throws SQLException {
        return (InputStream) getObject(param_1);
    }

    /**
     * @deprecated
     */
    public InputStream getUnicodeStream(String param_1)
            throws SQLException {
        return (InputStream) getObject(param_1);
    }

    public Reader getCharacterStream(int param_1)
            throws SQLException {
        return (Reader) getObject(param_1);
    }

    public Reader getCharacterStream(String param_1)
            throws SQLException {
        return (Reader) getObject(param_1);
    }

    public Object getObject(int param_1)
            throws SQLException {
        Object o = rows.get(line)[param_1 - 1];
        wasNull = o == null;
        return o;
    }

    public Object getObject(int param_1, Map param_2)
            throws SQLException {
        return getObject(param_1);
    }

    public Object getObject(String param_1)
            throws SQLException {
        return getObject(getColumnIndex(param_1) + 1);
    }

    public Object getObject(String param_1, Map param_2)
            throws SQLException {
        return getObject(param_1);
    }

    public String getCursorName()
            throws SQLException {
        return "*";
    }

    public String getString(int param_1)
            throws SQLException {
        return (String) getObject(param_1);
    }

    public String getString(String param_1)
            throws SQLException {
        return (String) getObject(param_1);
    }

    public BigDecimal getBigDecimal(int param_1)
            throws SQLException {
        return (BigDecimal) getObject(param_1);
    }

    /**
     * @deprecated
     */
    public BigDecimal getBigDecimal(int param_1, int param_2)
            throws SQLException {
        return (BigDecimal) getObject(param_1);
    }

    public BigDecimal getBigDecimal(String param_1)
            throws SQLException {
        return (BigDecimal) getObject(param_1);
    }

    /**
     * @deprecated
     */
    public BigDecimal getBigDecimal(String param_1, int param_2)
            throws SQLException {
        return (BigDecimal) getObject(param_1);
    }

    public URL getURL(int param_1)
            throws SQLException {
        return (URL) getObject(param_1);
    }

    public URL getURL(String param_1)
            throws SQLException {
        return (URL) getObject(param_1);
    }

    public Array getArray(int param_1)
            throws SQLException {
        return (Array) getObject(param_1);
    }

    public Array getArray(String param_1)
            throws SQLException {
        return (Array) getObject(param_1);
    }

    public Blob getBlob(int param_1)
            throws SQLException {
        return (Blob) getObject(param_1);
    }

    public Blob getBlob(String param_1)
            throws SQLException {
        return (Blob) getObject(param_1);
    }

    public Clob getClob(int param_1)
            throws SQLException {
        return (Clob) getObject(param_1);
    }

    public Clob getClob(String param_1)
            throws SQLException {
        return (Clob) getObject(param_1);
    }

    public Date getDate(int param_1)
            throws SQLException {
        return (Date) getObject(param_1);
    }

    public Date getDate(int param_1, Calendar param_2)
            throws SQLException {
        return (Date) getObject(param_1);
    }

    public Date getDate(String param_1)
            throws SQLException {
        return (Date) getObject(param_1);
    }

    public Date getDate(String param_1, Calendar param_2)
            throws SQLException {
        return (Date) getObject(param_1);
    }

    public Ref getRef(int param_1)
            throws SQLException {
        return (Ref) getObject(param_1);
    }

    public Ref getRef(String param_1)
            throws SQLException {
        return (Ref) getObject(param_1);
    }

    public ResultSetMetaData getMetaData()
            throws SQLException {
        return metadata;
    }

    public SQLWarning getWarnings()
            throws SQLException {
        return null;
    }

    public Statement getStatement()
            throws SQLException {
        return null;
    }

    public Time getTime(int param_1)
            throws SQLException {
        return (Time) getObject(param_1);
    }

    public Time getTime(int param_1, Calendar param_2)
            throws SQLException {
        return (Time) getObject(param_1);
    }

    public Time getTime(String param_1)
            throws SQLException {
        return (Time) getObject(param_1);
    }

    public Time getTime(String param_1, Calendar param_2)
            throws SQLException {
        return (Time) getObject(param_1);
    }

    public Timestamp getTimestamp(int param_1)
            throws SQLException {
        return (Timestamp) getObject(param_1);
    }

    public Timestamp getTimestamp(int param_1, Calendar param_2)
            throws SQLException {
        return (Timestamp) getObject(param_1);
    }

    public Timestamp getTimestamp(String param_1)
            throws SQLException {
        return (Timestamp) getObject(param_1);
    }

    public Timestamp getTimestamp(String param_1, Calendar param_2)
            throws SQLException {
        return (Timestamp) getObject(param_1);
    }

    public long getLong(int param_1)
            throws SQLException {
        Long b = (Long) getObject(param_1);
        return wasNull ? 0 : b;
    }

    public long getLong(String param_1)
            throws SQLException {
        Long b = (Long) getObject(param_1);
        return wasNull ? 0 : b;
    }

    public short getShort(int param_1)
            throws SQLException {
        Short b = (Short) getObject(param_1);
        return wasNull ? 0 : b;
    }

    public short getShort(String param_1)
            throws SQLException {
        Short b = (Short) getObject(param_1);
        return wasNull ? 0 : b;
    }

    public void afterLast()
            throws SQLException {
        line = rows.size();
    }

    public void beforeFirst()
            throws SQLException {
        line = -1;
    }

    public void cancelRowUpdates()
            throws SQLException {
    }

    public void clearWarnings()
            throws SQLException {
    }

    public void close()
            throws SQLException {
        if (rs != null) {
            rs.close();
        }
    }

    public void deleteRow()
            throws SQLException {
        rows.remove(line);
    }

    public void insertRow()
            throws SQLException {
    }

    public void moveToCurrentRow()
            throws SQLException {
        line = lastLine;
    }

    public void moveToInsertRow()
            throws SQLException {
        if (metadata == null) {
           //?
        } else {
            rows.add(new Object[metadata.getColumnCount()]);
            lastLine = line;
            line = rows.size() - 1;
        }
    }

    public void refreshRow()
            throws SQLException {
    }

    public void setFetchDirection(int param_1)
            throws SQLException {
    }

    public void setFetchSize(int param_1)
            throws SQLException {
    }

    public void updateArray(int param_1, Array param_2)
            throws SQLException {
        updateObject(param_1, param_2);
    }

    public void updateArray(String param_1, Array param_2)
            throws SQLException {
        updateObject(param_1, param_2);
    }

    public void updateAsciiStream(int param_1, InputStream param_2, int param_3)
            throws SQLException {
        updateObject(param_1, param_2);
    }

    public void updateAsciiStream(String param_1, InputStream param_2, int param_3)
            throws SQLException {
        updateObject(param_1, param_2);
    }

    public void updateBigDecimal(int param_1, BigDecimal param_2)
            throws SQLException {
        updateObject(param_1, param_2);
    }

    public void updateBigDecimal(String param_1, BigDecimal param_2)
            throws SQLException {
        updateObject(param_1, param_2);
    }

    public void updateBinaryStream(int param_1, InputStream param_2, int param_3)
            throws SQLException {
        updateObject(param_1, param_2);
    }

    public void updateBinaryStream(String param_1, InputStream param_2, int param_3)
            throws SQLException {
        updateObject(param_1, param_2);
    }

    public void updateBlob(int param_1, Blob param_2)
            throws SQLException {
        updateObject(param_1, param_2);
    }

    public void updateBlob(String param_1, Blob param_2)
            throws SQLException {
        updateObject(param_1, param_2);
    }

    public void updateBoolean(int param_1, boolean param_2)
            throws SQLException {
        updateObject(param_1, param_2);
    }

    public void updateBoolean(String param_1, boolean param_2)
            throws SQLException {
        updateObject(param_1, param_2);
    }

    public void updateByte(int param_1, byte param_2)
            throws SQLException {
        updateObject(param_1, param_2);
    }

    public void updateByte(String param_1, byte param_2)
            throws SQLException {
        updateObject(param_1, param_2);
    }

    public void updateBytes(int param_1, byte[] param_2)
            throws SQLException {
        updateObject(param_1, param_2);
    }

    public void updateBytes(String param_1, byte[] param_2)
            throws SQLException {
        updateObject(param_1, param_2);
    }

    public void updateCharacterStream(int param_1, Reader param_2, int param_3)
            throws SQLException {
        updateObject(param_1, param_2);
    }

    public void updateCharacterStream(String param_1, Reader param_2, int param_3)
            throws SQLException {
        updateObject(param_1, param_2);
    }

    public void updateClob(int param_1, Clob param_2)
            throws SQLException {
        updateObject(param_1, param_2);
    }

    public void updateClob(String param_1, Clob param_2)
            throws SQLException {
        updateObject(param_1, param_2);
    }

    public void updateDate(int param_1, Date param_2)
            throws SQLException {
        updateObject(param_1, param_2);
    }

    public void updateDate(String param_1, Date param_2)
            throws SQLException {
        updateObject(param_1, param_2);
    }

    public void updateDouble(int param_1, double param_2)
            throws SQLException {
        updateObject(param_1, param_2);
    }

    public void updateDouble(String param_1, double param_2)
            throws SQLException {
        updateObject(param_1, param_2);
    }

    public void updateFloat(int param_1, float param_2)
            throws SQLException {
        updateObject(param_1, param_2);
    }

    public void updateFloat(String param_1, float param_2)
            throws SQLException {
        updateObject(param_1, param_2);
    }

    public void updateInt(int param_1, int param_2)
            throws SQLException {
        updateObject(param_1, param_2);
    }

    public void updateInt(String param_1, int param_2)
            throws SQLException {
        updateObject(param_1, param_2);
    }

    public void updateLong(int param_1, long param_2)
            throws SQLException {
        updateObject(param_1, param_2);
    }

    public void updateLong(String param_1, long param_2)
            throws SQLException {
        updateObject(param_1, param_2);
    }

    public void updateNull(int param_1)
            throws SQLException {
        updateObject(param_1, null);
    }

    public void updateNull(String param_1)
            throws SQLException {
        updateObject(param_1, null);
    }

    public void updateObject(int param_1, Object param_2)
            throws SQLException {
        rows.get(line)[param_1 - 1] = param_2;
    }

    public void updateObject(int param_1, Object param_2, int param_3)
            throws SQLException {
        updateObject(param_1, param_2);
    }

    public void updateObject(String param_1, Object param_2)
            throws SQLException {
        updateObject(getColumnIndex(param_1) + 1, param_2);
    }

    public void updateObject(String param_1, Object param_2, int param_3)
            throws SQLException {
        updateObject(param_1, param_2);
    }

    public void updateRef(int param_1, Ref param_2)
            throws SQLException {
        updateObject(param_1, param_2);
    }

    public void updateRef(String param_1, Ref param_2)
            throws SQLException {
        updateObject(param_1, param_2);
    }

    public void updateRow()
            throws SQLException {
    }

    public void updateShort(int param_1, short param_2)
            throws SQLException {
        updateObject(param_1, param_2);
    }

    public void updateShort(String param_1, short param_2)
            throws SQLException {
        updateObject(param_1, param_2);
    }

    public void updateString(int param_1, String param_2)
            throws SQLException {
        updateObject(param_1, param_2);
    }

    public void updateString(String param_1, String param_2)
            throws SQLException {
        updateObject(param_1, param_2);
    }

    public void updateTime(int param_1, Time param_2)
            throws SQLException {
        updateObject(param_1, param_2);
    }

    public void updateTime(String param_1, Time param_2)
            throws SQLException {
        updateObject(param_1, param_2);
    }

    public void updateTimestamp(int param_1, Timestamp param_2)
            throws SQLException {
        updateObject(param_1, param_2);
    }

    public void updateTimestamp(String param_1, Timestamp param_2)
            throws SQLException {
        updateObject(param_1, param_2);
    }

    public RowId getRowId(int columnIndex) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public RowId getRowId(String columnLabel) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateRowId(int columnIndex, RowId x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateRowId(String columnLabel, RowId x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getHoldability() throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isClosed() throws SQLException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateNString(int columnIndex, String nString) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateNString(String columnLabel, String nString) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateNClob(String columnLabel, NClob nClob) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public NClob getNClob(int columnIndex) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public NClob getNClob(String columnLabel) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public SQLXML getSQLXML(int columnIndex) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public SQLXML getSQLXML(String columnLabel) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getNString(int columnIndex) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getNString(String columnLabel) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Reader getNCharacterStream(int columnIndex) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Reader getNCharacterStream(String columnLabel) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateCharacterStream(int columnIndex, Reader x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateClob(int columnIndex, Reader reader) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateClob(String columnLabel, Reader reader) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateNClob(int columnIndex, Reader reader) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateNClob(String columnLabel, Reader reader) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
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