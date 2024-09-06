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

package net.thevpc.dbclient.plugin.system.sql.objects;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import net.thevpc.dbclient.api.sql.DBCConnection;
import net.thevpc.dbclient.api.sql.DBOrder;
import net.thevpc.dbclient.api.sql.objects.DBTableIndex;
import net.thevpc.dbclient.api.sql.objects.DefaultDBObject;
import net.thevpc.dbclient.api.sql.objects.SQLObjectTypes;
import net.thevpc.dbclient.plugin.system.sql.SystemSQLUtils;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 13 juil. 2005 14:33:48
 */
public class DBTableIndexImpl extends DefaultDBObject implements DBTableIndex {
    private String tableCatalogName;
    private String tableSchemaName;
    private String tableName;
    private boolean nonUnique;
    private String indexQualifier;
    private String indexName;
    private Type indexType;
    private int ordinalPosition;
    private String columnName;
    private DBOrder order;
    private int cardinality;
    private int pages;
    private String filterCondition;

    @Override
    public void init(DBCConnection connection, ResultSet rs) throws SQLException {
        String _tableCatalogName;
        String _tableSchemaName;
        String _tableName;
        boolean _nonUnique;
        String _indexQualifier;
        String _indexName;
        int _indexType;
        int _ordinalPosition;
        String _columnName;
        String _orderString;
        int _cardinality;
        int _pages;
        String _filterCondition;
        _tableCatalogName=SystemSQLUtils.getStringIfSupported("TABLE_CAT", null, "DBIndexImpl", rs, connection);
        _tableSchemaName=SystemSQLUtils.getStringIfSupported("TABLE_SCHEM", null, "DBIndexImpl", rs, connection);
        _tableName=SystemSQLUtils.getStringIfSupported("TABLE_NAME", null, "DBIndexImpl", rs, connection);
        _nonUnique=SystemSQLUtils.getBooleanIfSupported("NON_UNIQUE", false, "DBIndexImpl", rs, connection);
        _indexQualifier=SystemSQLUtils.getStringIfSupported("INDEX_QUALIFIER", null, "DBIndexImpl", rs, connection);
        _indexName=SystemSQLUtils.getStringIfSupported("INDEX_NAME", null, "DBIndexImpl", rs, connection);
        _indexType=SystemSQLUtils.getIntIfSupported("TYPE", -1, "DBIndexImpl", rs, connection);
        _ordinalPosition=SystemSQLUtils.getIntIfSupported("ORDINAL_POSITION", -1, "DBIndexImpl", rs, connection);
        _columnName=SystemSQLUtils.getStringIfSupported("COLUMN_NAME", null, "DBIndexImpl", rs, connection);
        _orderString=SystemSQLUtils.getStringIfSupported("ASC_OR_DESC", null, "DBIndexImpl", rs, connection);
        _cardinality=SystemSQLUtils.getIntIfSupported("CARDINALITY", -1, "DBIndexImpl", rs, connection);
        _pages=SystemSQLUtils.getIntIfSupported("PAGES", -1, "DBIndexImpl", rs, connection);
        _filterCondition=SystemSQLUtils.getStringIfSupported("FILTER_CONDITION", null, "DBIndexImpl", rs, connection);
        Type type= Type.tableIndexOther;
        switch(_indexType){
            case DatabaseMetaData.tableIndexClustered:{
                type= Type.tableIndexClustered;
                break;
            }
            case DatabaseMetaData.tableIndexHashed:{
                type= Type.tableIndexHashed;
                break;
            }
            case DatabaseMetaData.tableIndexStatistic:{
                type= Type.tableIndexStatistic;
                break;
            }
            case DatabaseMetaData.tableIndexOther:{
                type= Type.tableIndexOther;
                break;
            }
        }
        DBOrder order=null;
        if("A".equals(_orderString)){
            order= DBOrder.ASC;
        }else if("D".equals(_orderString)){
            order= DBOrder.DESC;
        }
        init(connection, _tableCatalogName, _tableSchemaName, _tableName, _nonUnique, _indexQualifier, _indexName, type, _ordinalPosition, _columnName, order, _cardinality, _pages, _filterCondition);
    }

    @Override
    public void init(DBCConnection connection, String tableCatalogName, String tableSchemaName, String tableName, boolean nonUnique, String indexQualifier, String indexName, Type indexType, int ordinalPosition, String columnName, DBOrder order, int cardinality, int pages, String filterCondition) throws SQLException {
        this.connection=connection;
        this.tableCatalogName=tableCatalogName;
        this.tableSchemaName=tableSchemaName;
        this.tableName=tableName;
        this.nonUnique=nonUnique;
        this.indexQualifier=indexQualifier;
        this.indexName=indexName;
        this.indexType=indexType;
        this.ordinalPosition=ordinalPosition;
        this.columnName=columnName;
        this.order=order;
        this.cardinality=cardinality;
        this.pages=pages;
        this.filterCondition=filterCondition;
        super.init(connection, "Tree.TableIndex", indexQualifier, tableSchemaName, null, indexName, true);
    }

    
    
    public int getCardinality() {
        return cardinality;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getFilterCondition() {
        return filterCondition;
    }

    public String getIndexName() {
        return indexName;
    }

    public String getIndexQualifier() {
        return indexQualifier;
    }

    public Type getIndexType() {
        return indexType;
    }

    public boolean isNonUnique() {
        return nonUnique;
    }

    public DBOrder getOrder() {
        return order;
    }

    public int getOrdinalPosition() {
        return ordinalPosition;
    }

    public int getPages() {
        return pages;
    }

    public String getTableCatalogName() {
        return tableCatalogName;
    }

    public String getTableName() {
        return tableName;
    }

    public String getTableSchemaName() {
        return tableSchemaName;
    }

    @Override
    public SQLObjectTypes getType() {
        return SQLObjectTypes.TABLE_INDEX;
    }

    @Override
    public String getDropSQL() {
        return getFullName();
    }

}
