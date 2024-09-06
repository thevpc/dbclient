///**
// * ====================================================================
// *             DBClient yet another Jdbc client tool
// *
// * DBClient is a new Open Source Tool for connecting to jdbc
// * compliant relational databases. Specific extensions will take care of
// * each RDBMS implementation.
// *
// * Copyright (C) 2006-2008 Taha BEN SALAH
// *
// * This program is free software; you can redistribute it and/or modify
// * it under the terms of the GNU General Public License as published by
// * the Free Software Foundation; either version 2 of the License, or
// * (at your option) any later version.
// *
// * This program is distributed in the hope that it will be useful,
// * but WITHOUT ANY WARRANTY; without even the implied warranty of
// * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// * GNU General Public License for more details.
// *
// * You should have received a copy of the GNU General Public License along
// * with this program; if not, write to the Free Software Foundation, Inc.,
// * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
// * ====================================================================
// */
//package net.thevpc.dbclient.api.sql.objects;
//
//import java.sql.ResultSet;
//import net.thevpc.common.prs.plugin.Extension;
//import net.thevpc.dbclient.api.sql.DBCConnection;
//
//import java.sql.SQLException;
//import net.thevpc.dbclient.api.sql.DBOrder;
//
///**
// * @author Taha BEN SALAH (taha.bensalah@gmail.com)
// * @creationtime 13 juil. 2005 14:33:48
// */
//@Extension(group = "sql")
//public interface DBIndex extends DBObject {
//
//    public static enum Type {
//
//        /**
//         * this identifies table statistics that are returned in conjuction with a table's index descriptions
//         */
//        tableIndexStatistic,
//        /**
//         * this is a clustered index
//         */
//        tableIndexClustered,
//        /**
//         * this is a hashed index
//         */
//        tableIndexHashed,
//        /**
//         * this is some other style of index
//         */
//        tableIndexOther
//    }
//
//    /**
//     * 
//     *  <OL>
//     *	<LI><B>TABLE_CAT</B> String => table catalog (may be <code>null</code>)
//     *	<LI><B>TABLE_SCHEM</B> String => table schema (may be <code>null</code>)
//     *	<LI><B>TABLE_NAME</B> String => table name
//     *	<LI><B>NON_UNIQUE</B> boolean => Can index values be non-unique. 
//     *      false when TYPE is tableIndexStatistic
//     *	<LI><B>INDEX_QUALIFIER</B> String => index catalog (may be <code>null</code>); 
//     *      <code>null</code> when TYPE is tableIndexStatistic
//     *	<LI><B>INDEX_NAME</B> String => index name; <code>null</code> when TYPE is 
//     *      tableIndexStatistic
//     *	<LI><B>TYPE</B> short => index type:
//     *      <UL>
//     *      <LI> tableIndexStatistic - this identifies table statistics that are
//     *           returned in conjuction with a table's index descriptions
//     *      <LI> tableIndexClustered - this is a clustered index
//     *      <LI> tableIndexHashed - this is a hashed index
//     *      <LI> tableIndexOther - this is some other style of index
//     *      </UL>
//     *	<LI><B>ORDINAL_POSITION</B> short => column sequence number 
//     *      within index; zero when TYPE is tableIndexStatistic
//     *	<LI><B>COLUMN_NAME</B> String => column name; <code>null</code> when TYPE is 
//     *      tableIndexStatistic
//     *	<LI><B>ASC_OR_DESC</B> String => column sort sequence, "A" => ascending, 
//     *      "D" => descending, may be <code>null</code> if sort sequence is not supported; 
//     *      <code>null</code> when TYPE is tableIndexStatistic	
//     *	<LI><B>CARDINALITY</B> int => When TYPE is tableIndexStatistic, then 
//     *      this is the number of rows in the table; otherwise, it is the 
//     *      number of unique values in the index.
//     *	<LI><B>PAGES</B> int => When TYPE is  tableIndexStatisic then 
//     *      this is the number of pages used for the table, otherwise it 
//     *      is the number of pages used for the current index.
//     *	<LI><B>FILTER_CONDITION</B> String => Filter condition, if any.  
//     *      (may be <code>null</code>)
//     *  </OL>
//    
//     * @param session
//     * @param rs
//     * @throws SQLException 
//     */
//    public void init(
//            DBCConnection session,
//            String tableCatalogName,
//            String tableSchemaName,
//            String tableName,
//            boolean nonUnique,
//            String indexQualifier,
//            String indexName,
//            Type indexType,
//            int ordinalPosition,
//            String columnName,
//            DBOrder order,
//            int cardinality,
//            int pages,
//            String filterCondition) throws SQLException;
//
//    public void init(DBCConnection session,ResultSet rs) throws SQLException;
//    
//    String getTableCatalogName();
//
//    String getTableSchemaName();
//
//    String getTableName();
//
//    boolean isNonUnique();
//
//    String getIndexQualifier();
//
//    String getIndexName();
//
//    Type getIndexType();
//
//    int getOrdinalPosition();
//
//    String getColumnName();
//
//    DBOrder getOrder();
//
//    int getCardinality();
//
//    int getPages();
//
//    String getFilterCondition();
//}
