package net.vpc.dbclient.api.sql;

import java.sql.Types;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)  alias vpc
 * @creationtime 2009/08/14 13:00:30
 */
public class ColumnMetaData {
    private boolean autoIncrement;

    private boolean caseSensitive;

    private boolean searchable;

    private boolean currency;

    private int nullable;

    private boolean signed;

    private int columnDisplaySize;

    private String columnLabel;

    private String columnName;

    private String schemaName;

    private int precision;

    private int scale;

    private String tableName;

    private String catalogName;

    private int columnType;

    private String columnTypeName;

    private boolean readOnly;

    private boolean writable;

    private boolean definitelyWritable;

    private String columnClassName;

    public ColumnMetaData() {

    }

    public ColumnMetaData(String name, Class clazz) {
        setColumnName(name);
        setColumnLabel(name);
        setExtJavaClass(clazz);
    }

    public boolean isAutoIncrement() {
        return autoIncrement;
    }

    public void setAutoIncrement(boolean autoIncrement) {
        this.autoIncrement = autoIncrement;
    }


    public boolean isCaseSensitive() {
        return caseSensitive;
    }

    public void setCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

    public boolean isSearchable() {
        return searchable;
    }

    public void setSearchable(boolean searchable) {
        this.searchable = searchable;
    }

    public boolean isCurrency() {
        return currency;
    }

    public void setCurrency(boolean currency) {
        this.currency = currency;
    }

    public int getNullable() {
        return nullable;
    }

    public void setNullable(int nullable) {
        this.nullable = nullable;
    }

    public boolean isSigned() {
        return signed;
    }

    public void setSigned(boolean signed) {
        this.signed = signed;
    }

    public int getColumnDisplaySize() {
        return columnDisplaySize;
    }

    public void setColumnDisplaySize(int columnDisplaySize) {
        this.columnDisplaySize = columnDisplaySize;
    }

    public String getColumnLabel() {
        return columnLabel;
    }

    public void setColumnLabel(String columnLabel) {
        this.columnLabel = columnLabel;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public int getColumnType() {
        return columnType;
    }

    public void setExtJavaClass(Class clazz) {
        columnClassName = clazz.getName();
        if (String.class.equals(clazz)) {
            columnType = Types.VARCHAR;
        } else if (Boolean.class.equals(clazz)) {
            columnType = Types.BOOLEAN;
        } else if (Integer.class.equals(clazz)) {
            columnType = Types.INTEGER;
        } else if (Double.class.equals(clazz)) {
            columnType = Types.DOUBLE;
        } else if (Float.class.equals(clazz)) {
            columnType = Types.FLOAT;
        } else if (Long.class.equals(clazz)) {
            columnType = Types.NUMERIC;
        } else if (java.sql.Date.class.equals(clazz)) {
            columnType = Types.DATE;
        } else if (java.sql.Time.class.equals(clazz)) {
            columnType = Types.TIME;
        } else if (java.sql.Timestamp.class.equals(clazz)) {
            columnType = Types.TIMESTAMP;
        } else if (java.util.Date.class.equals(clazz)) {
            columnType = Types.TIMESTAMP;
        } else {
            throw new IllegalArgumentException("Unknown java type mapping " + clazz);
        }
    }

    public void setColumnType(int columnType) {
        this.columnType = columnType;
    }

    public String getColumnTypeName() {
        return columnTypeName;
    }

    public void setColumnTypeName(String columnTypeName) {
        this.columnTypeName = columnTypeName;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public boolean isWritable() {
        return writable;
    }

    public void setWritable(boolean writable) {
        this.writable = writable;
    }

    public boolean isDefinitelyWritable() {
        return definitelyWritable;
    }

    public void setDefinitelyWritable(boolean definitelyWritable) {
        this.definitelyWritable = definitelyWritable;
    }

    public String getColumnClassName() {
        return columnClassName;
    }

    public void setColumnClassName(String columnClassName) {
        this.columnClassName = columnClassName;
    }

}
