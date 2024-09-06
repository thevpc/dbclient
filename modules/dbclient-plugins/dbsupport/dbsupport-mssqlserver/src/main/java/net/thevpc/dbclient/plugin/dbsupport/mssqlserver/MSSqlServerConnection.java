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

package net.thevpc.dbclient.plugin.dbsupport.mssqlserver;

import net.thevpc.dbclient.api.sql.features.GenerateSQLDropFeature;
import net.thevpc.dbclient.api.sql.objects.SQLObjectTypes;
import net.thevpc.dbclient.plugin.dbsupport.mssqlserver.features.MSSqlServerGenerateSQLDropFeature;
import net.thevpc.dbclient.plugin.system.sql.DBCAbstractConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 19 juil. 2006 16:06:38
 */
public class MSSqlServerConnection extends DBCAbstractConnection {
    public MSSqlServerConnection() {
    }

    public int getConnectionSupportLevel(Connection connection) throws SQLException {
        return MSSqlServerPlugin.getConnectionSupportLevel0(connection);
    }

//    public SQLBatchIterator iterateBatch(Reader in) throws IOException {
//        return new DefaultSQLBatchIterator(in, new String[]{";", "GO"}, "GO", null, null);
//    }

    @Override
    public boolean isSQLCreateObjectSupported(SQLObjectTypes objectType) throws SQLException {
        return
                SQLObjectTypes.TABLE.equals(objectType)
                        || SQLObjectTypes.VIEW.equals(objectType)
                        || SQLObjectTypes.PROCEDURE.equals(objectType);
    }

//    public String getSQLCreateView(String catalog, String schema, String objectName) throws SQLException {
//        String sql = getResourceString("sql/getSQLCreateView.sql");
//        sql = sql.replace("${catalog}", catalog);
//        sql = sql.replace("${schema}", schema);
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        StringBuffer sb = new StringBuffer();
//        try {
//            ps = getConnection().prepareStatement(sql);
//            ps.setString(1, schema);
//            ps.setString(2, objectName);
//            rs = ps.executeQuery();
//            while (rs.next()) {
//                sb.append(rs.getString(1));
//            }
//            return sb.toString();
//        } catch (SQLException e) {
//            //bug workaround, some times, the first call does not succeed
//            System.err.println(e);
//            if (rs != null) {
//                rs.close();
//            }
//            if (ps != null) {
//                ps.close();
//            }
//            rs = null;
//            ps = null;
//
//            sb = new StringBuffer();
//            try {
//                ps = getConnection().prepareStatement(sql);
//                ps.setString(1, schema);
//                ps.setString(2, objectName);
//                rs = ps.executeQuery();
//                while (rs.next()) {
//                    sb.append(rs.getString(1));
//                }
//                return sb.toString();
//            } catch (Exception e2) {
//                System.err.println(e2);
//                return null;
//            } finally {
//                if (rs != null) {
//                    rs.close();
//                }
//                if (ps != null) {
//                    ps.close();
//                }
//                rs = null;
//                ps = null;
//            }
//        } finally {
//            if (rs != null) {
//                rs.close();
//            }
//            if (ps != null) {
//                ps.close();
//            }
//        }
//    }


    @Override
    public String getDefaultSchema() throws SQLException {
        Map<String, String> p = new HashMap<String, String>();
        p.put("user", getMetaData().getUserName());
        try {
            return getSQLResourceValueAsString("sql/getDefaultSchema.sql", p, null);
        } catch (IOException e) {
            throw new SQLException("sql/getDefaultSchema.sql not found");
        }
    }

    @Override
    public String getSQLUse(String catalogName, String schemaName) throws SQLException {
        if (catalogName != null) {
            return "USE " + catalogName;
        }
        return null;
    }

    @Override
    public String getSQLGoKeyword() {
        return "GO";
    }


    @Override
    public AutoIdentityType getAutoIdentityType() throws SQLException {
        return AutoIdentityType.POST_INSERT;
    }
//    public String getSQLCreateProcedure(String catalog, String schema, String objectName) throws SQLException {
//        String sql = getResourceString("sql/getSQLCreateProcedure.sql");
//        sql = sql.replace("${catalog}", catalog);
//        sql = sql.replace("${schema}", schema);
//        sql = sql.replace("${procedure}", objectName);
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        StringBuffer sb = new StringBuffer();
//        try {
//            ps = getConnection().prepareStatement(sql);
//            ps.setString(1, schema);
//            ps.setString(2, objectName);
//            rs = ps.executeQuery();
//            while (rs.next()) {
//                sb.append(rs.getString(1));
//            }
//            return sb.toString();
//        } catch (Exception e) {
//            if (rs != null) {
//                rs.close();
//            }
//            if (ps != null) {
//                ps.close();
//            }
//            rs = null;
//            ps = null;
//            System.err.println(e);
//            sb = new StringBuffer();
//            try {
//                ps = getConnection().prepareStatement(sql);
//                ps.setString(1, schema);
//                ps.setString(2, objectName);
//                rs = ps.executeQuery();
//                while (rs.next()) {
//                    sb.append(rs.getString(1));
//                }
//                return sb.toString();
//            } catch (Exception e2) {
//                System.err.println(e2);
//                return null;
//            } finally {
//                if (rs != null) {
//                    rs.close();
//                }
//                if (ps != null) {
//                    ps.close();
//                }
//                rs = null;
//                ps = null;
//            }
//
//        } finally {
//            if (rs != null) {
//                rs.close();
//            }
//            if (ps != null) {
//                ps.close();
//            }
//        }
//    }

    @Override
    public boolean acceptObjectType(SQLObjectTypes type) {
        switch (type) {
            case CLUSTER:
            case JOB:
            case SEQUENCE:
            case PACKAGE:
            case QUEUE: {
                return false;
            }
        }
        return true;
    }

    private MSSqlServerGenerateSQLDropFeature generateSQLDropFeature = new MSSqlServerGenerateSQLDropFeature();

    @Override
    public GenerateSQLDropFeature getFeatureGenerateSQLDrop() throws SQLException {
        return generateSQLDropFeature;
    }
}
