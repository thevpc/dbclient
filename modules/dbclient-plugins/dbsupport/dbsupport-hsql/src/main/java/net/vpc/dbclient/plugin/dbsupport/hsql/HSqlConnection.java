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

package net.vpc.dbclient.plugin.dbsupport.hsql;

import net.vpc.dbclient.plugin.system.sql.DBCAbstractConnection;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 19 juil. 2006 16:03:11
 */
public class HSqlConnection extends DBCAbstractConnection {

    public HSqlConnection() {
        super();
    }

    public int getConnectionSupportLevel(Connection connection) throws SQLException {
        return HSqlPlugin.getConnectionSupportLevel0(connection);
    }



//    public boolean isSQLCreateObjectSupported(SQLObjectTypes objectType) throws SQLException {
//        return
//                SQLObjectTypes.TABLE.equals(objectType)
//                        || SQLObjectTypes.VIEW.equals(objectType)
//                        || SQLObjectTypes.TRIGGER.equals(objectType);
//    }
//
//    public boolean isAutoIdentityColumn(String cat, String schema, String table, String col, String sequence) throws SQLException {
//        Map<String,String> p=new HashMap<String, String>();
//        p.put("catalog",cat);
//        p.put("schema",schema);
//        p.put("table",table);
//        p.put("column",col);
//        p.put("sequence",sequence);
//        ResultSet rs = null;
//        try {
//            rs = getSQLResourceValueAsResultSet("sql/isAutoIdentityColumn.sql", p);
//            if(rs==null){
//                return false;
//            }
//            return rs.next();
//        } finally {
//            if (rs != null) {
//                rs.close();
//            }
//        }
//    }
//
//    public AutoIdentityType getAutoIdentityType() throws SQLException {
//        return AutoIdentityType.POST_INSERT;
//    }
//
//
//    public boolean isSQLRenameObjectSupported(SQLObjectTypes objectType) throws SQLException {
//        switch (objectType) {
//            case TABLE_INDEX:
//            case TABLE:
//            case TABLE_COLUMN: {
//                return true;
//            }
//        }
//        return false;
//    }
//
//
//    public String getSQLRenameObject(String catalogName, String schemaName, String parentName, String objectName, String newName, SQLObjectTypes objectType) throws SQLException {
//        switch (objectType) {
//            case TABLE_INDEX: {
//            }
//            case TABLE: {
//            }
//            case TABLE_COLUMN: {
//            }
//        }
//        return super.getSQLRenameObject(catalogName, schemaName, parentName, objectName, newName, objectType);
//    }


//    @Override
//    public String getSQLRenameIndex(String catalog, String schema, String parentName, String objectName, String newName) {
//        return "RENAME INDEX " + SystemSQLUtils.getFullName(catalog, schema, parentName, objectName, SQLObjectTypes.TABLE_INDEX) + " TO " + newName;
//    }
//
//    @Override
//    public String getSQLRenameTable(String catalog, String schema, String parentName, String objectName, String newName) {
//        return "RENAME TABLE " + SystemSQLUtils.getFullName(catalog, schema, parentName, objectName, SQLObjectTypes.TABLE) + " TO " + newName;
//    }
//
//    @Override
//    public String getSQLRenameTableColumn(String catalog, String schema, String parentName, String objectName, String newName) {
//        return "RENAME COLUMN " + SystemSQLUtils.getFullName(catalog, schema, parentName, objectName, SQLObjectTypes.TABLE_COLUMN) + " TO " + newName;
//    }

    @Override
    public String getSQLUse(String catalogName, String schemaName) throws SQLException {
        return "SET SCHEMA " + schemaName;
    }

    @Override
    public String getDefaultSchema() throws SQLException {
        return super.getDefaultSchema().toUpperCase();
    }

}
