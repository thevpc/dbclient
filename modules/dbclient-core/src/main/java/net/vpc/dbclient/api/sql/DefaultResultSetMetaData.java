package net.vpc.dbclient.api.sql;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)  alias vpc
 * @creationtime 2009/08/14 12:53:32
 */
public class DefaultResultSetMetaData implements ResultSetMetaData {

    private List<ColumnMetaData> columns = new ArrayList<ColumnMetaData>();

    public DefaultResultSetMetaData() {
    }

    public DefaultResultSetMetaData(ResultSetMetaData base) throws SQLException {
        int count = base.getColumnCount();
        for (int i = 0; i < count; i++) {
            ColumnMetaData c = new ColumnMetaData();
            c.setAutoIncrement(base.isAutoIncrement(i));
            c.setCaseSensitive(base.isCaseSensitive(i));
            c.setCurrency(base.isCurrency(i));
            c.setDefinitelyWritable(base.isDefinitelyWritable(i));
            c.setReadOnly(base.isReadOnly(i));
            c.setSearchable(base.isSearchable(i));
            c.setSigned(base.isSigned(i));
            c.setWritable(base.isWritable(i));
            c.setCatalogName(base.getCatalogName(i));
            c.setColumnClassName(base.getColumnClassName(i));
            c.setColumnDisplaySize(base.getColumnDisplaySize(i));
            c.setColumnLabel(base.getColumnLabel(i));
            c.setColumnName(base.getColumnName(i));
            c.setColumnType(base.getColumnType(i));
            c.setColumnTypeName(base.getColumnTypeName(i));
            c.setPrecision(base.getPrecision(i));
            c.setScale(base.getScale(i));
            c.setSchemaName(base.getSchemaName(i));
            c.setTableName(base.getTableName(i));
        }
    }

    public void removeColumn(int index) {
        columns.remove(index);
    }

    public void addColumn(int index,ResultSetMetaData base, int baseIndex) throws SQLException {
        addColumn(index,copyColumn(base, baseIndex));
    }

    public void addColumn(ResultSetMetaData base, int baseIndex) throws SQLException {
        addColumn(copyColumn(base, baseIndex));
    }
    
    private ColumnMetaData copyColumn(ResultSetMetaData base, int index) throws SQLException{
        ColumnMetaData c = new ColumnMetaData();
        c.setAutoIncrement(base.isAutoIncrement(index));
        c.setCaseSensitive(base.isCaseSensitive(index));
        c.setCurrency(base.isCurrency(index));
        c.setDefinitelyWritable(base.isDefinitelyWritable(index));
        c.setReadOnly(base.isReadOnly(index));
        c.setSearchable(base.isSearchable(index));
        c.setSigned(base.isSigned(index));
        c.setWritable(base.isWritable(index));
        c.setCatalogName(base.getCatalogName(index));
        c.setColumnClassName(base.getColumnClassName(index));
        c.setColumnDisplaySize(base.getColumnDisplaySize(index));
        c.setColumnLabel(base.getColumnLabel(index));
        c.setColumnName(base.getColumnName(index));
        c.setColumnType(base.getColumnType(index));
        c.setColumnTypeName(base.getColumnTypeName(index));
        c.setPrecision(base.getPrecision(index));
        c.setScale(base.getScale(index));
        c.setSchemaName(base.getSchemaName(index));
        c.setTableName(base.getTableName(index));
        return c;
    }

    public void addColumn(ColumnMetaData c) {
        columns.add(c);
    }

    public void addColumn(int index,ColumnMetaData c) {
        columns.add(index,c);
    }

    public ColumnMetaData getColumn(int index) {
        return columns.get(index - 1);
    }

    public List<ColumnMetaData> getColumns() {
        return Collections.unmodifiableList(columns);
    }

    public int getColumnCount() throws SQLException {
        return columns.size();
    }

    public boolean isAutoIncrement(int column) throws SQLException {
        return getColumn(column).isAutoIncrement();
    }

    public boolean isCaseSensitive(int column) throws SQLException {
        return getColumn(column).isCaseSensitive();
    }

    public boolean isSearchable(int column) throws SQLException {
        return getColumn(column).isSearchable();
    }

    public boolean isCurrency(int column) throws SQLException {
        return getColumn(column).isCurrency();
    }

    public int isNullable(int column) throws SQLException {
        return getColumn(column).getNullable();
    }

    public boolean isSigned(int column) throws SQLException {
        return getColumn(column).isSigned();
    }

    public int getColumnDisplaySize(int column) throws SQLException {
        return getColumn(column).getColumnDisplaySize();
    }

    public String getColumnLabel(int column) throws SQLException {
        return getColumn(column).getColumnLabel();
    }

    public String getColumnName(int column) throws SQLException {
        return getColumn(column).getColumnName();
    }

    public String getSchemaName(int column) throws SQLException {
        return getColumn(column).getSchemaName();
    }

    public int getPrecision(int column) throws SQLException {
        return getColumn(column).getPrecision();
    }

    public int getScale(int column) throws SQLException {
        return getColumn(column).getScale();
    }

    public String getTableName(int column) throws SQLException {
        return getColumn(column).getTableName();
    }

    public String getCatalogName(int column) throws SQLException {
        return getColumn(column).getCatalogName();
    }

    public int getColumnType(int column) throws SQLException {
        return getColumn(column).getColumnType();
    }

    public String getColumnTypeName(int column) throws SQLException {
        return getColumn(column).getColumnTypeName();
    }

    public boolean isReadOnly(int column) throws SQLException {
        return getColumn(column).isReadOnly();
    }

    public boolean isWritable(int column) throws SQLException {
        return getColumn(column).isWritable();
    }

    public boolean isDefinitelyWritable(int column) throws SQLException {
        return getColumn(column).isDefinitelyWritable();
    }

    public String getColumnClassName(int column) throws SQLException {
        return getColumn(column).getColumnClassName();
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        return unwrap(iface);
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return isWrapperFor(iface);
    }
}
