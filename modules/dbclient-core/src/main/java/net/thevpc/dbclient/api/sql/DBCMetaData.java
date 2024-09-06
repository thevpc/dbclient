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

package net.thevpc.dbclient.api.sql;

import net.thevpc.dbclient.api.pluginmanager.DBCFactory;
import net.thevpc.dbclient.api.sql.objects.DBDatatype;
import net.thevpc.dbclient.api.sql.objects.DBFunction;
import net.thevpc.dbclient.api.sql.objects.SQLObjectTypes;
import net.thevpc.dbclient.api.sql.parser.SQLParser;
import net.thevpc.common.prs.log.TLog;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 19 juil. 2006 15:46:44
 */
public interface DBCMetaData extends DatabaseMetaData {
    public static final String DATABASE_STRUCTURE_CHANGED = "DATABASE_STRUCTURE_CHANGED";

    public static enum AutoIdentityType {
        NONE, SEQUENCE, POST_INSERT
    }


    public Object getAutoIdentityValue(String cat, String schema, String table, String col, String sequenceName) throws SQLException;

    public AutoIdentityType getAutoIdentityType() throws SQLException;

    /**
     * return ResultSet
     * <pre>
     * CATALOG_NAME
     * SCHEMA_NAME,
     * TABLE_NAME,
     * TRIGGER_NAME varchar
     * TRIGGER_EVENT varchar  : UPDATE|DELETE|INSERT
     * TRIGGER_ACTION varchar : BEFORE|AFTER
     * TRIGGER_TARGET varchar : ROW|STATEMENT
     *
     * @param catalog            catalog name or null
     * @param schemaPattern      schema
     * @param tableNamePattern   table
     * @param triggerNamePattern trigger
     * @param extraProperties
     * @return list of triggers for catalog.schema.tableName
     * @throws java.sql.SQLException is Error
     */
    public ResultSet getTriggers(String catalog, String schemaPattern, String tableNamePattern, String triggerNamePattern, Properties extraProperties) throws SQLException;

    /**
     * return ResultSet (INDEX_NAME varchar)
     * <p/>
     * CATALOG_NAME
     * SCHEMA_NAME,
     * TABLE_NAME,
     * INDEX_NAME varchar
     *
     * @param catalog          catalog name or null
     * @param schemaPattern    schema
     * @param tableNamePattern table
     * @param indexNamePattern index
     * @param extraProperties
     * @return list of triggers for catalog.schema.tableName
     * @throws java.sql.SQLException is Error
     */
    public ResultSet getIndexes(String catalog, String schemaPattern, String tableNamePattern, String indexNamePattern, Properties extraProperties) throws SQLException;

    /**
     * return ResultSet (INDEX_NAME varchar)
     * <p/>
     * CATALOG_NAME
     * SCHEMA_NAME,
     * PACKAGE_NAME,
     * PACKAGE_BODY varchar
     *
     * @param catalog         catalog name or null
     * @param schemaPattern   schema
     * @param namePattern     table
     * @param extraProperties
     * @return list of triggers for catalog.schema.tableName
     * @throws java.sql.SQLException is Error
     */
    public ResultSet getPackages(String catalog, String schemaPattern, String namePattern, Properties extraProperties) throws SQLException;

    /**
     * return ResultSet (INDEX_NAME varchar)
     * <p/>
     * CATALOG_NAME
     * SCHEMA_NAME,
     * QUEUE_NAME,
     * QUEUE_BODY varchar
     *
     * @param catalog         catalog name or null
     * @param schemaPattern   schema
     * @param namePattern     table
     * @param extraProperties
     * @return list of triggers for catalog.schema.tableName
     * @throws java.sql.SQLException is Error
     */
    public ResultSet getQueues(String catalog, String schemaPattern, String namePattern, Properties extraProperties) throws SQLException;

    /**
     * return ResultSet (INDEX_NAME varchar)
     *
     * @param catalog               catalog name or null
     * @param schemaPattern         schema
     * @param tableNamePattern      table
     * @param constraintNamePattern index
     * @param extraProperties
     * @return list of triggers for catalog.schema.tableName
     * @throws java.sql.SQLException is Error
     */
    public ResultSet getConstraints(String catalog, String schemaPattern, String tableNamePattern, String constraintNamePattern, Properties extraProperties) throws SQLException;

    public boolean isSQLDropObjectSupported(SQLObjectTypes objectType) throws SQLException;

    public String getSQLDropObject(String catalog, String schema, String parentName, String objectName, SQLObjectTypes objectType);

    public boolean isSQLRenameObjectSupported(SQLObjectTypes objectType) throws SQLException;

    public boolean isSQLCloneObjectSupported(SQLObjectTypes objectType) throws SQLException;

    public String getSQLRenameObject(String catalogName, String schemaName, String parentName, String objectName, String newName, SQLObjectTypes objectType, Properties extraProperties) throws SQLException;

    public String getSQLCloneObject(
            String catalogName, String schemaName, String parentName, String objectName,
            String newCatalogName, String newSchemaName, String newParentName, String newObjectName,
            SQLObjectTypes objectType, Properties extraProperties) throws SQLException;

    public DBCFactory getFactory();

    public void setFactory(DBCFactory factory);

    public boolean isValidConnection(Connection cnx);

    public String getSQLCreateUser(String username, String password, Properties properties) throws SQLException;

    public String getSQLLiteral(Object obj, int sqlType);

    public String getSQLGoKeyword();

    public String getEscapedName(String name);

    public String getSQLConstraints(String catalogName, String schemaName, String parentName, String objectName, SQLObjectTypes objectType, Properties extraProperties) throws SQLException;

    public String getSQLCreateObject(String catalog, String schema, String parentName, String objectName, SQLObjectTypes objectType, Properties extraProperties) throws SQLException;

    public String getSQLInsertRecord(String catalog, String schema, String objectName, SQLRecord record, Properties extraProperties) throws SQLException;

    public String getSQLUse(String catalogName, String schemaName) throws SQLException;


    public boolean isSQLCreateObjectSupported(SQLObjectTypes objectType) throws SQLException;

    public SQLParser createParser();

    public String[] getSQLKeywordsArray() throws SQLException;

    public DBDatatype[] getDatatypes() throws SQLException;

    public String[] getOperators() throws SQLException;

    public String[] getSeparators() throws SQLException;

    public DBFunction[] getAllFunctionsArray() throws SQLException;

    public DBFunction[] getNumericFunctionsArray() throws SQLException;

    public DBFunction[] getAggregateFunctionsArray() throws SQLException;

    public DBFunction[] getTimeDateFunctionsArray() throws SQLException;

    public DBFunction[] getSystemFunctionsArray() throws SQLException;

    public DBFunction[] getConversionFunctionsArray() throws SQLException;

    public DBFunction[] getStringFunctionsArray() throws SQLException;

    public String getDefaultSchema() throws SQLException;

    public Connection getConnection();

    public String getDefaultCatalog() throws SQLException;

    public boolean isSystemType(String name) throws SQLException;

    public SQLObjectTypes getTypeByNativeName(String name) throws SQLException;

    public TLog getLog();

    public void setLog(TLog log);

    public boolean acceptObjectType(SQLObjectTypes type);
}