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

package net.vpc.dbclient.plugin.dbsupport.oracle;

import net.vpc.dbclient.api.sql.features.GenerateSQLDropFeature;
import net.vpc.dbclient.api.sql.features.GenerateSQLRenameFeature;
import net.vpc.dbclient.api.sql.objects.SQLObjectTypes;
import net.vpc.dbclient.api.sql.parser.SQLParser;
import net.vpc.dbclient.plugin.dbsupport.oracle.features.OracleGenerateSQLDropFeature;
import net.vpc.dbclient.plugin.dbsupport.oracle.features.OracleGenerateSQLRenameFeature;
import net.vpc.dbclient.plugin.system.sql.DBCAbstractConnection;
import net.vpc.dbclient.plugin.system.sql.parser.DefaultSQLParser;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.TreeSet;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 19 juil. 2006 16:12:46
 */
public class OracleConnection extends DBCAbstractConnection {
    public OracleConnection() {
        super();
    }

    public int getConnectionSupportLevel(Connection connection) throws SQLException {
        return OraclePlugin.getConnectionSupportLevel0(connection);
    }

    @Override
    public String getDefaultSchema() throws SQLException {
        return getMetaData().getUserName();
    }

    @Override
    protected SQLParser createDefaultParser() {
        DefaultSQLParser parser = new DefaultSQLParser();
        parser.setBeginKeyWord("begin");
        parser.setEndKeyWord("end");
        parser.setIfCausesBegin(true);
        return parser;
    }


    @Override
    public boolean isSQLCreateObjectSupported(SQLObjectTypes objectType) throws SQLException {
        return
                SQLObjectTypes.TABLE.equals(objectType)
                        || SQLObjectTypes.VIEW.equals(objectType)
                        || SQLObjectTypes.PROCEDURE.equals(objectType)
                        || SQLObjectTypes.TRIGGER.equals(objectType)
                ;
    }


    @Override
    public boolean isAutoIdentityColumn(String catalog, String schema, String table, String col, String sequence) throws SQLException {
        return sequence != null;
    }

    @Override
    public AutoIdentityType getAutoIdentityType() throws SQLException {
        return AutoIdentityType.SEQUENCE;
    }


    @Override
    public String getSQLCreateUser(String username, String password, Properties properties) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE USER " + username + " IDENTIFIED BY " + password + ";\n");
        String privs = properties.getProperty("privileges");
        if (privs != null && privs.trim().length() > 0) {
            if ("ALL PRIVILEGES".equals(privs)) {
                sql.append("GRANT ALL PRIVILEGES TO " + username + ";\n");
            } else {
                sql.append("GRANT " + privs + " TO " + username + ";\n");
            }
        }
        String roles = properties.getProperty("roles");
        if (roles != null && roles.trim().length() > 0) {
            sql.append("GRANT " + roles + " TO " + username + ";\n");
        }
        return sql.toString();
    }

    public String[] getPrivileges() {
        TreeSet<String> foundL = new TreeSet<String>();
        TreeSet<String> found = new TreeSet<String>();
        String sql = null;
        try {
            sql = getResourceString("sql/getPrivileges.sql");
        } catch (IOException e) {
            //e.printStackTrace();
        }
        for (StringTokenizer tokenizer = new StringTokenizer(sql, "\n\r"); tokenizer.hasMoreTokens();) {
            String s = tokenizer.nextToken().trim();
            if (s.length() > 0 && !foundL.contains(s.toLowerCase())) {
                found.add(s);
                foundL.add(s.toLowerCase());
            }
        }
        return found.toArray(new String[found.size()]);
    }

    public String[] getRoles() {
        TreeSet<String> foundL = new TreeSet<String>();
        TreeSet<String> found = new TreeSet<String>();
        String sql = null;
        try {
            sql = getResourceString("sql/getRoles.sql");
        } catch (IOException e) {
            //e.printStackTrace();
        }
        for (StringTokenizer tokenizer = new StringTokenizer(sql, "\n\r"); tokenizer.hasMoreTokens();) {
            String s = tokenizer.nextToken().trim();
            if (s.length() > 0 && !foundL.contains(s.toLowerCase())) {
                found.add(s);
                foundL.add(s.toLowerCase());
            }
        }
        return found.toArray(new String[found.size()]);
    }

    private DatabaseMetaData cachedMetaData = null;

    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        if (cachedMetaData == null) {
            cachedMetaData = new OracleDatabaseMetadata(getConnection().getMetaData());
        }
        return cachedMetaData;
    }

    @Override
    public ResultSet getPackages(String catalog, String schemaPattern, String namePattern, Properties extraProperties) throws SQLException {
        PreparedStatement stmt = null;

        String q = "select null as CATALOG_NAME, OWNER as SCHEMA_NAME, OBJECT_NAME as PACKAGE_NAME,null as PACKAGE_BODY from all_objects where object_type='PACKAGE' ";
        //ignore catalog
        if (schemaPattern != null) {
            q += " AND OWNER like ? ";
        }
        if (namePattern != null) {
            q += " AND OBJECT_NAME like ? ";
        }
        stmt = getConnection().prepareStatement(q);
        int index = 1;
        if (schemaPattern != null) {
            stmt.setString(index, schemaPattern);
            index++;
        }
        if (namePattern != null) {
            stmt.setString(index, namePattern);
            //index++;
        }
        return stmt.executeQuery();
    }

    @Override
    public ResultSet getQueues(String catalog, String schemaPattern, String namePattern, Properties extraProperties) throws SQLException {
        PreparedStatement stmt = null;

        String q = "select null as CATALOG_NAME, OWNER as SCHEMA_NAME, OBJECT_NAME as QUEUE_NAME,null as QUEUE_BODY from all_objects where object_type='QUEUE' ";
        //ignore catalog
        if (schemaPattern != null) {
            q += " AND OWNER like ? ";
        }
        if (namePattern != null) {
            q += " AND OBJECT_NAME like ? ";
        }
        stmt = getConnection().prepareStatement(q);
        int index = 1;
        if (schemaPattern != null) {
            stmt.setString(index, schemaPattern);
            index++;
        }
        if (namePattern != null) {
            stmt.setString(index, namePattern);
            //index++;
        }
        return stmt.executeQuery();
    }

    @Override
    public ResultSet getClusters(String catalog, String schemaPattern, String namePattern, Properties extraProperties) throws SQLException {
        PreparedStatement stmt = null;

        String q = "select null as CATALOG_NAME, OWNER as SCHEMA_NAME, OBJECT_NAME as QUEUE_NAME,null as QUEUE_BODY from all_objects where object_type='CLUSTER' ";
        //ignore catalog
        if (schemaPattern != null) {
            q += " AND OWNER like ? ";
        }
        if (namePattern != null) {
            q += " AND OBJECT_NAME like ? ";
        }
        stmt = getConnection().prepareStatement(q);
        int index = 1;
        if (schemaPattern != null) {
            stmt.setString(index, schemaPattern);
            index++;
        }
        if (namePattern != null) {
            stmt.setString(index, namePattern);
            //index++;
        }
        return stmt.executeQuery();
    }

    @Override
    public ResultSet getJobs(String catalog, String schemaPattern, String namePattern, Properties extraProperties) throws SQLException {
        PreparedStatement stmt = null;

        String q = "select null as CATALOG_NAME, OWNER as SCHEMA_NAME, OBJECT_NAME as QUEUE_NAME,null as QUEUE_BODY from all_objects where object_type='JOB' ";
        //ignore catalog
        if (schemaPattern != null) {
            q += " AND OWNER like ? ";
        }
        if (namePattern != null) {
            q += " AND OBJECT_NAME like ? ";
        }
        stmt = getConnection().prepareStatement(q);
        int index = 1;
        if (schemaPattern != null) {
            stmt.setString(index, schemaPattern);
            index++;
        }
        if (namePattern != null) {
            stmt.setString(index, namePattern);
            //index++;
        }
        return stmt.executeQuery();
    }

    @Override
    public boolean acceptObjectType(SQLObjectTypes type) {
        return true;
    }


    OracleGenerateSQLRenameFeature generateSQLRenameFeature = new OracleGenerateSQLRenameFeature();

    @Override
    public GenerateSQLRenameFeature getFeatureGenerateSQLRename() throws SQLException {
        return generateSQLRenameFeature;
    }

    OracleGenerateSQLDropFeature generateSQLDropFeature = new OracleGenerateSQLDropFeature();

    @Override
    public GenerateSQLDropFeature getFeatureGenerateSQLDrop() throws SQLException {
        return generateSQLDropFeature;
    }

}
