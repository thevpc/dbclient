/**
 *
 ====================================================================
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
 *
 ====================================================================
 */

package net.thevpc.dbclient.plugin.dbsupport.derby;

import net.thevpc.dbclient.api.sql.features.ExecutionPlanFeature;
import net.thevpc.dbclient.api.sql.objects.SQLObjectTypes;
import net.thevpc.dbclient.plugin.dbsupport.derby.features.DerbyAdminFeature;
import net.thevpc.dbclient.plugin.dbsupport.derby.features.DerbyExecutionPlanFeature;
import net.thevpc.dbclient.plugin.dbsupport.derby.features.DerbyGenerateSQLRenameFeature;
import net.thevpc.dbclient.plugin.dbsupport.derby.features.DerbySYSCSUTILFeature;
import net.thevpc.dbclient.plugin.system.sql.DBCAbstractConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 19 juil. 2006 16:03:11
 */
public class DerbyConnection extends DBCAbstractConnection {

    public DerbyConnection() {
        super();
    }

    public int getConnectionSupportLevel(Connection connection) throws SQLException {
        return DerbyPlugin.getConnectionSupportLevel0(connection);
    }

    @Override
    public boolean isSQLCreateObjectSupported(SQLObjectTypes objectType) throws SQLException {
        return
                SQLObjectTypes.TABLE.equals(objectType)
                        || SQLObjectTypes.VIEW.equals(objectType)
                        || SQLObjectTypes.TRIGGER.equals(objectType);
    }

    @Override
    public boolean isAutoIdentityColumn(String cat, String schema, String table, String col, String sequence) throws SQLException {
        Map<String, String> p = new HashMap<String, String>();
        p.put("catalog", cat);
        p.put("schema", schema);
        p.put("table", table);
        p.put("column", col);
        p.put("sequence", sequence);
        ResultSet rs = null;
        try {
            rs = getSQLResourceValueAsResultSet("sql/isAutoIdentityColumn.sql", p);
            if (rs == null) {
                return false;
            }
            return rs.next();
        } catch (IOException e) {
            throw new SQLException("sql/isAutoIdentityColumn.sql not found");
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
    }

    @Override
    public AutoIdentityType getAutoIdentityType() throws SQLException {
        return AutoIdentityType.POST_INSERT;
    }


    @Override
    public String getSQLUse(String catalogName, String schemaName) throws SQLException {
        return "SET SCHEMA " + schemaName;
    }

    @Override
    public String getDefaultSchema() throws SQLException {
        return super.getDefaultSchema().toUpperCase();
    }

    public boolean isCreateSchemaSupported() throws SQLException {
        return true;
    }

    public void createSchema(String schemaName, Object parameters) throws SQLException {
        CreateSchemaParams params = (CreateSchemaParams) parameters;
        PreparedStatement cs = null;
        try {
            String authorizationStatement = "";
            if (params != null) {
                if (params.getAuthorization() != null) {
                    authorizationStatement = "  AUTHORIZATION " + params.getAuthorization();
                }
            }
            cs = prepareStatement("CREATE SCHEMA " + schemaName + " " + authorizationStatement);
            cs.executeUpdate();
        } finally {
            if (cs != null) {
                cs.close();
            }
        }
    }

    public static class CreateSchemaParams {
        private String authorization;

        public CreateSchemaParams(String authorization) {
            setAuthorization(authorization);
        }

        public String getAuthorization() {
            return authorization;
        }

        public void setAuthorization(String authorization) {
            if (authorization != null && authorization.trim().length() == 0) {
                authorization = null;
            }
            this.authorization = authorization;
        }
    }

    private DerbySYSCSUTILFeature derbySYSCSUTILFeature = new DerbySYSCSUTILFeature(this);

    public DerbySYSCSUTILFeature getFeatureSYSCSUTIL() {
        return derbySYSCSUTILFeature;
    }

    private DerbyAdminFeature derbyAdminFeature = new DerbyAdminFeature(this);

    public DerbyAdminFeature getFeatureAdmin() {
        return derbyAdminFeature;
    }

    private DerbyExecutionPlanFeature derbyExecutionPlanFeature = new DerbyExecutionPlanFeature(this);

    @Override
    public ExecutionPlanFeature getFeatureExecutionPlan() throws SQLException {
        return derbyExecutionPlanFeature;
    }

    private DerbyGenerateSQLRenameFeature generateSQLRenameFeature = new DerbyGenerateSQLRenameFeature();

    @Override
    public DerbyGenerateSQLRenameFeature getFeatureGenerateSQLRename() throws SQLException {
        return generateSQLRenameFeature;
    }
}