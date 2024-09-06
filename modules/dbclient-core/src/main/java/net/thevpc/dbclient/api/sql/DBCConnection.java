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
import net.thevpc.dbclient.api.pluginmanager.DBCPluggable;
import net.thevpc.common.prs.plugin.Extension;
import net.thevpc.dbclient.api.sql.features.ExecutionPlanFeature;
import net.thevpc.dbclient.api.sql.features.GenerateSQLDropFeature;
import net.thevpc.dbclient.api.sql.features.GenerateSQLRenameFeature;
import net.thevpc.dbclient.api.sql.format.SQLFormatter;
import net.thevpc.dbclient.api.sql.objects.DBDatatype;
import net.thevpc.dbclient.api.sql.objects.DBFunction;
import net.thevpc.dbclient.api.sql.objects.DBObject;
import net.thevpc.dbclient.api.sql.objects.SQLObjectTypes;
import net.thevpc.dbclient.api.sql.parser.SQLParser;
import net.thevpc.common.prs.reflect.ClassFilter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Properties;
import net.thevpc.dbclient.api.DBCSession;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 19 juil. 2006 15:46:44
 */
@Extension(group = "sql", customizable = false)
public interface DBCConnection extends ConnectionWrapper, DBCPluggable {
    public static final String DATABASE_STRUCTURE_CHANGED = "DATABASE_STRUCTURE_CHANGED";

    void init(DBCSession session) throws SQLException;
    
    DBCSession getSession();
    
    ExecutionPlanFeature getFeatureExecutionPlan() throws SQLException;

    GenerateSQLDropFeature getFeatureGenerateSQLDrop() throws SQLException;

    GenerateSQLRenameFeature getFeatureGenerateSQLRename() throws SQLException;

    public static enum AutoIdentityType {
        NONE, SEQUENCE, POST_INSERT
    }

    public int getConnectionSupportLevel(Connection connection) throws SQLException;

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
     * return ResultSet
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
     * return ResultSet
     * <p/>
     * CATALOG_NAME
     * SCHEMA_NAME,
     * CLUSTER_NAME,
     * CLUSTER_BODY varchar
     *
     * @param catalog         catalog name or null
     * @param schemaPattern   schema
     * @param namePattern     table
     * @param extraProperties
     * @return list of triggers for catalog.schema.tableName
     * @throws java.sql.SQLException is Error
     */
    public ResultSet getClusters(String catalog, String schemaPattern, String namePattern, Properties extraProperties) throws SQLException;

    /**
     * return ResultSet
     * <p/>
     * CATALOG_NAME
     * SCHEMA_NAME,
     * JOB_NAME,
     * JOB_BODY varchar
     *
     * @param catalog         catalog name or null
     * @param schemaPattern   schema
     * @param namePattern     table
     * @param extraProperties
     * @return list of triggers for catalog.schema.tableName
     * @throws java.sql.SQLException is Error
     */
    public ResultSet getJobs(String catalog, String schemaPattern, String namePattern, Properties extraProperties) throws SQLException;

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

    public boolean isSQLCloneObjectSupported(SQLObjectTypes objectType) throws SQLException;

    public String getSQLCloneObject(
            String catalogName, String schemaName, String parentName, String objectName,
            String newCatalogName, String newSchemaName, String newParentName, String newObjectName,
            SQLObjectTypes objectType, Properties extraProperties) throws SQLException;

    public DBCFactory getFactory();

    public void setFactory(DBCFactory factory);

    public boolean isValidConnection();

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

    public int[] executeScript(String script) throws SQLException;

    public String[] getSQLKeywords() throws SQLException;

    public DBDatatype[] getDatatypes() throws SQLException;

    public String[] getOperators() throws SQLException;

    public String[] getSeparators() throws SQLException;

    public DBFunction[] getAllFunctions() throws SQLException;

    public DBFunction[] getNumericFunctions() throws SQLException;

    public DBFunction[] getAggregateFunctions() throws SQLException;

    public DBFunction[] getTimeDateFunctions() throws SQLException;

    public DBFunction[] getSystemFunctions() throws SQLException;

    public DBFunction[] getConversionFunctions() throws SQLException;

    public DBFunction[] getStringFunctions() throws SQLException;

    public String getDefaultSchema() throws SQLException;


    public String getDefaultCatalog() throws SQLException;

    public DBObject[] find(String catalogName, String schemaName, String parentName, String name, DBObject[] contextParents, ClassFilter nodeClassFilter, FindMonitor findMonitor) throws SQLException;

    public SQLFormatter createFormatter();

    public int executeInsertRecord(String catalog, String schema, String tableName, SQLRecord record) throws SQLException;

    public int executeUpdateRecord(String catalog, String schema, String tableName, SQLRecord record) throws SQLException;

    public int executeDeleteRecord(String catalog, String schema, String tableName, SQLRecord record) throws SQLException;

    public boolean isAutoIdentityColumn(String cat, String schema, String table, String col, String sequence) throws SQLException;

    public DBObject getRootbject() throws SQLException;

    public Collection<DBObject> getObjectChildren(DBObject object) throws SQLException;

    public boolean isSystemType(String name) throws SQLException;

    public SQLObjectTypes getTypeByNativeName(String name) throws SQLException;


    public void structureChanged();


    public DBObjectFilter getObjectFilter();

    public void setObjectFilter(DBObjectFilter filter);

    public TypeWrapperFactory getTypeWrapperFactory();

    public boolean acceptObjectType(SQLObjectTypes type);
    
    public Object getConnectionProperty(String key,Object defaultValue);
    
    public void setConnectionProperty(String key,Object value);
}
