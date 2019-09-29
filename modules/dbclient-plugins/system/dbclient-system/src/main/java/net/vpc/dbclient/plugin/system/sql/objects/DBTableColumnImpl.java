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
package net.vpc.dbclient.plugin.system.sql.objects;

import net.vpc.dbclient.api.sql.DBCConnection;
import net.vpc.dbclient.api.sql.TypeWrapperFactory;
import net.vpc.dbclient.api.sql.objects.*;
import net.vpc.dbclient.api.sql.util.SQLUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import net.vpc.dbclient.plugin.system.sql.SystemSQLUtils;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 4 août 2005 14:54:07
 */
public class DBTableColumnImpl extends DefaultDBObject implements DBTableColumn {

    private int pkSeq = -1;
    private int sqlType;
    private int precision;
    private int size;
    private boolean nullable;
    private boolean insertable = true;
    public DBTable table;
    public String nativeTypeName;

    public void init(DBCConnection session, ResultSet rs) throws SQLException {
        String TABLE_CAT = null;
        String TABLE_SCHEM = null;
        String TABLE_NAME = null;
        String COLUMN_NAME = null;
        String TYPE_NAME = null;
        String IS_NULLABLE = null;
        int COLUMN_SIZE = -1;
        int DECIMAL_DIGITS = -1;
        int DATA_TYPE = -1;
        TABLE_CAT = SystemSQLUtils.getStringIfSupported("TABLE_CAT", null, "DBTableColumn", rs, session);
        TYPE_NAME = SystemSQLUtils.getStringIfSupported("TYPE_NAME", null, "DBTableColumn", rs, session);
        IS_NULLABLE = SystemSQLUtils.getStringIfSupported("IS_NULLABLE", "", "DBTableColumn", rs, session);
        TABLE_SCHEM = SystemSQLUtils.getStringIfSupported("TABLE_SCHEM", null, "DBTableColumn", rs, session);
        TABLE_NAME = SystemSQLUtils.getStringIfSupported("TABLE_NAME", null, "DBTableColumn", rs, session);
        COLUMN_NAME = SystemSQLUtils.getStringIfSupported("COLUMN_NAME", null, "DBTableColumn", rs, session);
        COLUMN_SIZE = SystemSQLUtils.getIntIfSupported("COLUMN_SIZE", -1, "DBTableColumn", rs, session);
        DECIMAL_DIGITS = SystemSQLUtils.getIntIfSupported("DECIMAL_DIGITS", -1, "DBTableColumn", rs, session);
        DATA_TYPE = SystemSQLUtils.getIntIfSupported("DATA_TYPE", -1, "DBTableColumn", rs, session);
        super.init(session, "Tree.TableColumn", TABLE_CAT, TABLE_SCHEM, TABLE_NAME, COLUMN_NAME, true);
        setSize(COLUMN_SIZE);
        setPrecision(DECIMAL_DIGITS);
        this.setNullable(!"NO".equalsIgnoreCase(IS_NULLABLE));
        nativeTypeName = TYPE_NAME;
        this.setSqlType(DATA_TYPE);
        this.setNullable(nullable);
        setInsertable(!session.isAutoIdentityColumn(getCatalogName(), getSchemaName(), getParentName(), getName(), null));
        reloadPk();
    }

    @Override
    public String getColumnName() {
        return getName();
    }

    @Override
    public String getTableName() {
        return getParentName();
    }
    
    public void setTable(DBTable table) {
        this.table = table;
    }

    public DBTable getTable() {
        return table;
    }
//    public Icon getIcon() {
//        String icon = null;
//        switch (sqlType) {
//            case Types.BIGINT: {
//                icon = "TypeNumber";
//                break;
//            }
//            case Types.BOOLEAN:
//            case Types.BIT: {
//                icon = "TypeBoolean";
//                break;
//            }
//
//            case Types.DECIMAL:
//            case Types.DOUBLE:
//            case Types.FLOAT:
//            case Types.REAL: {
//                icon = "TypeNumber";
//                break;
//            }
//
//            case Types.NUMERIC: {
//                if (precision > 0) {
//                    icon = "TypeNumber";
//                } else {
//                    icon = "TypeNumber";
//                }
//                break;
//            }
//            case Types.DATE: {
//                icon = "TypeDate";
//                break;
//            }
//            case Types.TIME: {
//                icon = "TypeTime";
//                break;
//            }
//            case Types.TIMESTAMP: {
//                icon = "TypeTimestamp";
//                break;
//            }
//
//            case Types.TINYINT:
//            case Types.SMALLINT:
//            case Types.INTEGER: {
//                icon = "TypeNumber";
//                break;
//            }
//
//            case Types.VARBINARY:
//            case Types.LONGVARBINARY:
//            case Types.BINARY:
//            case Types.LONGVARCHAR:
//            case Types.CHAR:
//            case Types.VARCHAR: {
//                icon = "TypeVarchar";
//                break;
//            }
//
//            case Types.ARRAY:
//            case Types.STRUCT:
//            case Types.NULL:
//            case Types.OTHER:
//            case Types.REF:
//            case Types.JAVA_OBJECT:
//            case Types.BLOB:
//            case Types.CLOB:
//            case Types.DISTINCT:
//            case Types.DATALINK:
//            default: {
//                icon = "TypeError";
//                break;
//            }
//        }
//        return SQLUtils.loadImageIcon("/images/net/vpc/dbclient/" + icon + ".gif");
//    }

    public boolean isNullable() {
        return nullable;
    }

    public boolean isPk() {
        return getPkSeq() >= 0;
    }

    public int getPkSeq() {
        return pkSeq;
    }

    public int getSqlType() {
        return sqlType;
    }

    public String getSqlTypeName() {
        return SQLUtils.getSqlTypeName(sqlType,getConnection().getSession());
    }

    public int getPrecision() {
        return precision;
    }

    public int getSize() {
        return size;
    }

    @Override
    public DBObject getChild(int i) {
        return null;
    }

    @Override
    public int getChildCount() {
        return 0;
    }

    @Override
    public int getIndexOfChild(DBObject obj) {
        return 0;
    }

    @Override
    public boolean isLeaf() {
        return true;
    }

    public String getSQL(String prototype) {
        return getName();
    }

    @Override
    public String getSQL() {
        return getName();
    }

    @Override
    public boolean isLoaded() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final DBTableColumn tableColumnNode = (DBTableColumn) o;

        if (isNullable() != tableColumnNode.isNullable()) {
            return false;
        }
        if (getPkSeq() != tableColumnNode.getPkSeq()) {
            return false;
        }
        if (getPrecision() != tableColumnNode.getPrecision()) {
            return false;
        }
        if (getSize() != tableColumnNode.getSize()) {
            return false;
        }
        if (getSqlType() != tableColumnNode.getSqlType()) {
            return false;
        }
        if (table != null ? !table.equals(tableColumnNode.getTable()) : tableColumnNode.getTable() != null) {
            return false;
        }
        if (getName() != null ? !getName().equals(tableColumnNode.getName()) : tableColumnNode.getName() != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 29 * result + (table != null ? table.hashCode() : 0);
        return result;
    }

//    public void setPkSeq(int pkSeq) {
//        this.pkSeq = pkSeq;
//    }
    public void setSqlType(int sqlType) {
        this.sqlType = sqlType;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    @Override
    public String toString() {
        return getName();
    }

    public SQLObjectTypes getType() {
        return SQLObjectTypes.TABLE_COLUMN;
    }

    public Object getValue(ResultSet resultSet, int index) throws SQLException {
        TypeWrapperFactory twf = getConnection().getTypeWrapperFactory();
        return twf.getTypeDesc(sqlType).getWrapper().getObject(resultSet, index);
    }

    public void reloadPk() throws SQLException {
        ResultSet rs = null;
        try {
            try {
                rs = getConnection().getMetaData().getPrimaryKeys(
                        getCatalogName(),
                        getSchemaName(),
                        getParentName());
                while (rs.next()) {
                    String colName = rs.getString("COLUMN_NAME");
                    int keyseq = rs.getInt("KEY_SEQ");
                    if (getName().equalsIgnoreCase(colName)) {
                        this.pkSeq = keyseq;
                        break;
                    }
                }
                rs.close();
            } finally {
                if (rs != null) {
                    rs.close();
                }
            }
        } catch (Exception ex) {
            getConnection().getLoggerProvider().getLogger(DBTableColumnImpl.class.getName()).log(Level.SEVERE,"Cannot getPrimaryKeys(" + getCatalogName() + "," + getSchemaName() + "," + getParentName() + ")",ex);
        }
    }

    public boolean isInsertable() {
        return insertable;
    }

    public void setInsertable(boolean insertable) {
        this.insertable = insertable;
    }

    public String getNativeTypeName() {
        return nativeTypeName;
    }

    public String getDropSQL() {
        return getName();
    }
}
