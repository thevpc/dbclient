/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vpc.dbclient.api.sql;

import java.beans.PropertyChangeSupport;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.RowIdLifetime;
import java.sql.SQLException;
import java.util.logging.Logger;
import net.vpc.log.LoggerProvider;

/**
 *
 * @author vpc
 */
public class DefaultDatabaseMetadataWrapper implements DatabaseMetaData {

    private java.sql.DatabaseMetaData instance;
    protected PropertyChangeSupport support;
    protected Logger logger;
    protected Connection connection;

    protected DefaultDatabaseMetadataWrapper(java.sql.DatabaseMetaData instance, Connection connection,LoggerProvider loggerProvider) {
        support = new PropertyChangeSupport(this);
        this.instance = instance;
        this.logger = loggerProvider.getLogger(DefaultDatabaseMetadataWrapper.class.getName());
        this.connection = connection;
    }

    public java.lang.String getURL() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getURL");
        return instance.getURL();
    }

    public boolean isReadOnly() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "isReadOnly");
        return instance.isReadOnly();
    }

    public java.sql.ResultSet getAttributes(java.lang.String param_1, java.lang.String param_2, java.lang.String param_3, java.lang.String param_4) throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getAttributes");
        return instance.getAttributes(param_1, param_2, param_3, param_4);
    }

    public java.lang.String getUserName() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getUserName");
        return instance.getUserName();
    }

    public boolean allProceduresAreCallable() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "allProceduresAreCallable");
        return instance.allProceduresAreCallable();
    }

    public boolean allTablesAreSelectable() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "allTablesAreSelectable");
        return instance.allTablesAreSelectable();
    }

    public boolean dataDefinitionCausesTransactionCommit() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "dataDefinitionCausesTransactionCommit");
        return instance.dataDefinitionCausesTransactionCommit();
    }

    public boolean dataDefinitionIgnoredInTransactions() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "dataDefinitionIgnoredInTransactions");
        return instance.dataDefinitionIgnoredInTransactions();
    }

    public boolean deletesAreDetected(int param_1) throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "deletesAreDetected");
        return instance.deletesAreDetected(param_1);
    }

    public boolean doesMaxRowSizeIncludeBlobs() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "doesMaxRowSizeIncludeBlobs");
        return instance.doesMaxRowSizeIncludeBlobs();
    }

    public java.sql.ResultSet getBestRowIdentifier(java.lang.String param_1, java.lang.String param_2, java.lang.String param_3, int param_4, boolean param_5) throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getBestRowIdentifier");
        return instance.getBestRowIdentifier(param_1, param_2, param_3, param_4, param_5);
    }

    public java.lang.String getCatalogSeparator() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getCatalogSeparator");
        return instance.getCatalogSeparator();
    }

    public java.lang.String getCatalogTerm() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getCatalogTerm");
        return instance.getCatalogTerm();
    }

    public java.sql.ResultSet getCatalogs() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getCatalogs");
        return instance.getCatalogs();
    }

    public java.sql.ResultSet getColumnPrivileges(java.lang.String param_1, java.lang.String param_2, java.lang.String param_3, java.lang.String param_4) throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getColumnPrivileges");
        return instance.getColumnPrivileges(param_1, param_2, param_3, param_4);
    }

    public java.sql.ResultSet getColumns(java.lang.String param_1, java.lang.String param_2, java.lang.String param_3, java.lang.String param_4) throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getColumns");
        return instance.getColumns(param_1, param_2, param_3, param_4);
    }

    public java.sql.Connection getConnection() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getConnection");
        return instance.getConnection();
    }

    public java.sql.ResultSet getCrossReference(java.lang.String param_1, java.lang.String param_2, java.lang.String param_3, java.lang.String param_4, java.lang.String param_5, java.lang.String param_6) throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getCrossReference");
        return instance.getCrossReference(param_1, param_2, param_3, param_4, param_5, param_6);
    }

    public int getDatabaseMajorVersion() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getDatabaseMajorVersion");
        return instance.getDatabaseMajorVersion();
    }

    public int getDatabaseMinorVersion() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getDatabaseMinorVersion");
        return instance.getDatabaseMinorVersion();
    }

    public java.lang.String getDatabaseProductName() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getDatabaseProductName");
        return instance.getDatabaseProductName();
    }

    public java.lang.String getDatabaseProductVersion() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getDatabaseProductVersion");
        return instance.getDatabaseProductVersion();
    }

    public int getDefaultTransactionIsolation() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getDefaultTransactionIsolation");
        return instance.getDefaultTransactionIsolation();
    }

    public int getDriverMajorVersion() {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getDriverMajorVersion");
        return instance.getDriverMajorVersion();
    }

    public int getDriverMinorVersion() {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getDriverMinorVersion");
        return instance.getDriverMinorVersion();
    }

    public java.lang.String getDriverName() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getDriverName");
        return instance.getDriverName();
    }

    public java.lang.String getDriverVersion() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getDriverVersion");
        return instance.getDriverVersion();
    }

    public java.sql.ResultSet getExportedKeys(java.lang.String param_1, java.lang.String param_2, java.lang.String param_3) throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getExportedKeys");
        return instance.getExportedKeys(param_1, param_2, param_3);
    }

    public java.lang.String getExtraNameCharacters() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getExtraNameCharacters");
        return instance.getExtraNameCharacters();
    }

    public java.lang.String getIdentifierQuoteString() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getIdentifierQuoteString");
        return instance.getIdentifierQuoteString();
    }

    public java.sql.ResultSet getImportedKeys(java.lang.String param_1, java.lang.String param_2, java.lang.String param_3) throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getImportedKeys");
        return instance.getImportedKeys(param_1, param_2, param_3);
    }

    public java.sql.ResultSet getIndexInfo(java.lang.String param_1, java.lang.String param_2, java.lang.String param_3, boolean param_4, boolean param_5) throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getIndexInfo");
        return instance.getIndexInfo(param_1, param_2, param_3, param_4, param_5);
    }

    public int getJDBCMajorVersion() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getJDBCMajorVersion");
        return instance.getJDBCMajorVersion();
    }

    public int getJDBCMinorVersion() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getJDBCMinorVersion");
        return instance.getJDBCMinorVersion();
    }

    public int getMaxBinaryLiteralLength() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getMaxBinaryLiteralLength");
        return instance.getMaxBinaryLiteralLength();
    }

    public int getMaxCatalogNameLength() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getMaxCatalogNameLength");
        return instance.getMaxCatalogNameLength();
    }

    public int getMaxCharLiteralLength() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getMaxCharLiteralLength");
        return instance.getMaxCharLiteralLength();
    }

    public int getMaxColumnNameLength() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getMaxColumnNameLength");
        return instance.getMaxColumnNameLength();
    }

    public int getMaxColumnsInGroupBy() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getMaxColumnsInGroupBy");
        return instance.getMaxColumnsInGroupBy();
    }

    public int getMaxColumnsInIndex() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getMaxColumnsInIndex");
        return instance.getMaxColumnsInIndex();
    }

    public int getMaxColumnsInOrderBy() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getMaxColumnsInOrderBy");
        return instance.getMaxColumnsInOrderBy();
    }

    public int getMaxColumnsInSelect() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getMaxColumnsInSelect");
        return instance.getMaxColumnsInSelect();
    }

    public int getMaxColumnsInTable() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getMaxColumnsInTable");
        return instance.getMaxColumnsInTable();
    }

    public int getMaxConnections() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getMaxConnections");
        return instance.getMaxConnections();
    }

    public int getMaxCursorNameLength() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getMaxCursorNameLength");
        return instance.getMaxCursorNameLength();
    }

    public int getMaxIndexLength() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getMaxIndexLength");
        return instance.getMaxIndexLength();
    }

    public int getMaxProcedureNameLength() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getMaxProcedureNameLength");
        return instance.getMaxProcedureNameLength();
    }

    public int getMaxRowSize() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getMaxRowSize");
        return instance.getMaxRowSize();
    }

    public int getMaxSchemaNameLength() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getMaxSchemaNameLength");
        return instance.getMaxSchemaNameLength();
    }

    public int getMaxStatementLength() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getMaxStatementLength");
        return instance.getMaxStatementLength();
    }

    public int getMaxStatements() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getMaxStatements");
        return instance.getMaxStatements();
    }

    public int getMaxTableNameLength() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getMaxTableNameLength");
        return instance.getMaxTableNameLength();
    }

    public int getMaxTablesInSelect() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getMaxTablesInSelect");
        return instance.getMaxTablesInSelect();
    }

    public int getMaxUserNameLength() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getMaxUserNameLength");
        return instance.getMaxUserNameLength();
    }

    public java.lang.String getNumericFunctions() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getNumericFunctions");
        return instance.getNumericFunctions();
    }

    public java.sql.ResultSet getPrimaryKeys(java.lang.String param_1, java.lang.String param_2, java.lang.String param_3) throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getPrimaryKeys");
        return instance.getPrimaryKeys(param_1, param_2, param_3);
    }

    public java.sql.ResultSet getProcedureColumns(java.lang.String param_1, java.lang.String param_2, java.lang.String param_3, java.lang.String param_4) throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getProcedureColumns");
        return instance.getProcedureColumns(param_1, param_2, param_3, param_4);
    }

    public java.lang.String getProcedureTerm() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getProcedureTerm");
        return instance.getProcedureTerm();
    }

    public java.sql.ResultSet getProcedures(java.lang.String param_1, java.lang.String param_2, java.lang.String param_3) throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getProcedures");
        return instance.getProcedures(param_1, param_2, param_3);
    }

    public int getResultSetHoldability() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getResultSetHoldability");
        return instance.getResultSetHoldability();
    }

    public java.lang.String getSQLKeywords() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getSQLKeywords");
        return instance.getSQLKeywords();
    }

    public int getSQLStateType() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getSQLStateType");
        return instance.getSQLStateType();
    }

    public java.lang.String getSchemaTerm() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getSchemaTerm");
        return instance.getSchemaTerm();
    }

    public java.sql.ResultSet getSchemas() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getSchemas");
        return instance.getSchemas();
    }

    public java.lang.String getSearchStringEscape() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getSearchStringEscape");
        return instance.getSearchStringEscape();
    }

    public java.lang.String getStringFunctions() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getStringFunctions");
        return instance.getStringFunctions();
    }

    public java.sql.ResultSet getSuperTables(java.lang.String param_1, java.lang.String param_2, java.lang.String param_3) throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getSuperTables");
        return instance.getSuperTables(param_1, param_2, param_3);
    }

    public java.sql.ResultSet getSuperTypes(java.lang.String param_1, java.lang.String param_2, java.lang.String param_3) throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getSuperTypes");
        return instance.getSuperTypes(param_1, param_2, param_3);
    }

    public java.lang.String getSystemFunctions() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getSystemFunctions");
        return instance.getSystemFunctions();
    }

    public java.sql.ResultSet getTablePrivileges(java.lang.String param_1, java.lang.String param_2, java.lang.String param_3) throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getTablePrivileges");
        return instance.getTablePrivileges(param_1, param_2, param_3);
    }

    public java.sql.ResultSet getTableTypes() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getTableTypes");
        return instance.getTableTypes();
    }

    public java.sql.ResultSet getTables(java.lang.String param_1, java.lang.String param_2, java.lang.String param_3, java.lang.String[] param_4) throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getTables");
        return instance.getTables(param_1, param_2, param_3, param_4);
    }

    public java.lang.String getTimeDateFunctions() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getTimeDateFunctions");
        return instance.getTimeDateFunctions();
    }

    public java.sql.ResultSet getTypeInfo() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getTypeInfo");
        return instance.getTypeInfo();
    }

    public java.sql.ResultSet getUDTs(java.lang.String param_1, java.lang.String param_2, java.lang.String param_3, int[] param_4) throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getUDTs");
        return instance.getUDTs(param_1, param_2, param_3, param_4);
    }

    public java.sql.ResultSet getVersionColumns(java.lang.String param_1, java.lang.String param_2, java.lang.String param_3) throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getVersionColumns");
        return instance.getVersionColumns(param_1, param_2, param_3);
    }

    public boolean insertsAreDetected(int param_1) throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "insertsAreDetected");
        return instance.insertsAreDetected(param_1);
    }

    public boolean isCatalogAtStart() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "isCatalogAtStart");
        return instance.isCatalogAtStart();
    }

    public boolean locatorsUpdateCopy() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "locatorsUpdateCopy");
        return instance.locatorsUpdateCopy();
    }

    public boolean nullPlusNonNullIsNull() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "nullPlusNonNullIsNull");
        return instance.nullPlusNonNullIsNull();
    }

    public boolean nullsAreSortedAtEnd() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "nullsAreSortedAtEnd");
        return instance.nullsAreSortedAtEnd();
    }

    public boolean nullsAreSortedAtStart() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "nullsAreSortedAtStart");
        return instance.nullsAreSortedAtStart();
    }

    public boolean nullsAreSortedHigh() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "nullsAreSortedHigh");
        return instance.nullsAreSortedHigh();
    }

    public boolean nullsAreSortedLow() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "nullsAreSortedLow");
        return instance.nullsAreSortedLow();
    }

    public boolean othersDeletesAreVisible(int param_1) throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "othersDeletesAreVisible");
        return instance.othersDeletesAreVisible(param_1);
    }

    public boolean othersInsertsAreVisible(int param_1) throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "othersInsertsAreVisible");
        return instance.othersInsertsAreVisible(param_1);
    }

    public boolean othersUpdatesAreVisible(int param_1) throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "othersUpdatesAreVisible");
        return instance.othersUpdatesAreVisible(param_1);
    }

    public boolean ownDeletesAreVisible(int param_1) throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "ownDeletesAreVisible");
        return instance.ownDeletesAreVisible(param_1);
    }

    public boolean ownInsertsAreVisible(int param_1) throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "ownInsertsAreVisible");
        return instance.ownInsertsAreVisible(param_1);
    }

    public boolean ownUpdatesAreVisible(int param_1) throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "ownUpdatesAreVisible");
        return instance.ownUpdatesAreVisible(param_1);
    }

    public boolean storesLowerCaseIdentifiers() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "storesLowerCaseIdentifiers");
        return instance.storesLowerCaseIdentifiers();
    }

    public boolean storesLowerCaseQuotedIdentifiers() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "storesLowerCaseQuotedIdentifiers");
        return instance.storesLowerCaseQuotedIdentifiers();
    }

    public boolean storesMixedCaseIdentifiers() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "storesMixedCaseIdentifiers");
        return instance.storesMixedCaseIdentifiers();
    }

    public boolean storesMixedCaseQuotedIdentifiers() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "storesMixedCaseQuotedIdentifiers");
        return instance.storesMixedCaseQuotedIdentifiers();
    }

    public boolean storesUpperCaseIdentifiers() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "storesUpperCaseIdentifiers");
        return instance.storesUpperCaseIdentifiers();
    }

    public boolean storesUpperCaseQuotedIdentifiers() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "storesUpperCaseQuotedIdentifiers");
        return instance.storesUpperCaseQuotedIdentifiers();
    }

    public boolean supportsANSI92EntryLevelSQL() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsANSI92EntryLevelSQL");
        return instance.supportsANSI92EntryLevelSQL();
    }

    public boolean supportsANSI92FullSQL() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsANSI92FullSQL");
        return instance.supportsANSI92FullSQL();
    }

    public boolean supportsANSI92IntermediateSQL() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsANSI92IntermediateSQL");
        return instance.supportsANSI92IntermediateSQL();
    }

    public boolean supportsAlterTableWithAddColumn() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsAlterTableWithAddColumn");
        return instance.supportsAlterTableWithAddColumn();
    }

    public boolean supportsAlterTableWithDropColumn() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsAlterTableWithDropColumn");
        return instance.supportsAlterTableWithDropColumn();
    }

    public boolean supportsBatchUpdates() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsBatchUpdates");
        return instance.supportsBatchUpdates();
    }

    public boolean supportsCatalogsInDataManipulation() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsCatalogsInDataManipulation");
        return instance.supportsCatalogsInDataManipulation();
    }

    public boolean supportsCatalogsInIndexDefinitions() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsCatalogsInIndexDefinitions");
        return instance.supportsCatalogsInIndexDefinitions();
    }

    public boolean supportsCatalogsInPrivilegeDefinitions() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsCatalogsInPrivilegeDefinitions");
        return instance.supportsCatalogsInPrivilegeDefinitions();
    }

    public boolean supportsCatalogsInProcedureCalls() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsCatalogsInProcedureCalls");
        return instance.supportsCatalogsInProcedureCalls();
    }

    public boolean supportsCatalogsInTableDefinitions() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsCatalogsInTableDefinitions");
        return instance.supportsCatalogsInTableDefinitions();
    }

    public boolean supportsColumnAliasing() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsColumnAliasing");
        return instance.supportsColumnAliasing();
    }

    public boolean supportsConvert() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsConvert");
        return instance.supportsConvert();
    }

    public boolean supportsConvert(int param_1, int param_2) throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsConvert");
        return instance.supportsConvert(param_1, param_2);
    }

    public boolean supportsCoreSQLGrammar() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsCoreSQLGrammar");
        return instance.supportsCoreSQLGrammar();
    }

    public boolean supportsCorrelatedSubqueries() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsCorrelatedSubqueries");
        return instance.supportsCorrelatedSubqueries();
    }

    public boolean supportsDataDefinitionAndDataManipulationTransactions() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsDataDefinitionAndDataManipulationTransactions");
        return instance.supportsDataDefinitionAndDataManipulationTransactions();
    }

    public boolean supportsDataManipulationTransactionsOnly() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsDataManipulationTransactionsOnly");
        return instance.supportsDataManipulationTransactionsOnly();
    }

    public boolean supportsDifferentTableCorrelationNames() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsDifferentTableCorrelationNames");
        return instance.supportsDifferentTableCorrelationNames();
    }

    public boolean supportsExpressionsInOrderBy() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsExpressionsInOrderBy");
        return instance.supportsExpressionsInOrderBy();
    }

    public boolean supportsExtendedSQLGrammar() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsExtendedSQLGrammar");
        return instance.supportsExtendedSQLGrammar();
    }

    public boolean supportsFullOuterJoins() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsFullOuterJoins");
        return instance.supportsFullOuterJoins();
    }

    public boolean supportsGetGeneratedKeys() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsGetGeneratedKeys");
        return instance.supportsGetGeneratedKeys();
    }

    public boolean supportsGroupBy() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsGroupBy");
        return instance.supportsGroupBy();
    }

    public boolean supportsGroupByBeyondSelect() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsGroupByBeyondSelect");
        return instance.supportsGroupByBeyondSelect();
    }

    public boolean supportsGroupByUnrelated() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsGroupByUnrelated");
        return instance.supportsGroupByUnrelated();
    }

    public boolean supportsIntegrityEnhancementFacility() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsIntegrityEnhancementFacility");
        return instance.supportsIntegrityEnhancementFacility();
    }

    public boolean supportsLikeEscapeClause() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsLikeEscapeClause");
        return instance.supportsLikeEscapeClause();
    }

    public boolean supportsLimitedOuterJoins() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsLimitedOuterJoins");
        return instance.supportsLimitedOuterJoins();
    }

    public boolean supportsMinimumSQLGrammar() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsMinimumSQLGrammar");
        return instance.supportsMinimumSQLGrammar();
    }

    public boolean supportsMixedCaseIdentifiers() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsMixedCaseIdentifiers");
        return instance.supportsMixedCaseIdentifiers();
    }

    public boolean supportsMixedCaseQuotedIdentifiers() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsMixedCaseQuotedIdentifiers");
        return instance.supportsMixedCaseQuotedIdentifiers();
    }

    public boolean supportsMultipleOpenResults() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsMultipleOpenResults");
        return instance.supportsMultipleOpenResults();
    }

    public boolean supportsMultipleResultSets() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsMultipleResultSets");
        return instance.supportsMultipleResultSets();
    }

    public boolean supportsMultipleTransactions() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsMultipleTransactions");
        return instance.supportsMultipleTransactions();
    }

    public boolean supportsNamedParameters() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsNamedParameters");
        return instance.supportsNamedParameters();
    }

    public boolean supportsNonNullableColumns() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsNonNullableColumns");
        return instance.supportsNonNullableColumns();
    }

    public boolean supportsOpenCursorsAcrossCommit() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsOpenCursorsAcrossCommit");
        return instance.supportsOpenCursorsAcrossCommit();
    }

    public boolean supportsOpenCursorsAcrossRollback() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsOpenCursorsAcrossRollback");
        return instance.supportsOpenCursorsAcrossRollback();
    }

    public boolean supportsOpenStatementsAcrossCommit() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsOpenStatementsAcrossCommit");
        return instance.supportsOpenStatementsAcrossCommit();
    }

    public boolean supportsOpenStatementsAcrossRollback() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsOpenStatementsAcrossRollback");
        return instance.supportsOpenStatementsAcrossRollback();
    }

    public boolean supportsOrderByUnrelated() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsOrderByUnrelated");
        return instance.supportsOrderByUnrelated();
    }

    public boolean supportsOuterJoins() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsOuterJoins");
        return instance.supportsOuterJoins();
    }

    public boolean supportsPositionedDelete() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsPositionedDelete");
        return instance.supportsPositionedDelete();
    }

    public boolean supportsPositionedUpdate() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsPositionedUpdate");
        return instance.supportsPositionedUpdate();
    }

    public boolean supportsResultSetConcurrency(int param_1, int param_2) throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsResultSetConcurrency");
        return instance.supportsResultSetConcurrency(param_1, param_2);
    }

    public boolean supportsResultSetHoldability(int param_1) throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsResultSetHoldability");
        return instance.supportsResultSetHoldability(param_1);
    }

    public boolean supportsResultSetType(int param_1) throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsResultSetType");
        return instance.supportsResultSetType(param_1);
    }

    public boolean supportsSavepoints() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsSavepoints");
        return instance.supportsSavepoints();
    }

    public boolean supportsSchemasInDataManipulation() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsSchemasInDataManipulation");
        return instance.supportsSchemasInDataManipulation();
    }

    public boolean supportsSchemasInIndexDefinitions() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsSchemasInIndexDefinitions");
        return instance.supportsSchemasInIndexDefinitions();
    }

    public boolean supportsSchemasInPrivilegeDefinitions() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsSchemasInPrivilegeDefinitions");
        return instance.supportsSchemasInPrivilegeDefinitions();
    }

    public boolean supportsSchemasInProcedureCalls() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsSchemasInProcedureCalls");
        return instance.supportsSchemasInProcedureCalls();
    }

    public boolean supportsSchemasInTableDefinitions() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsSchemasInTableDefinitions");
        return instance.supportsSchemasInTableDefinitions();
    }

    public boolean supportsSelectForUpdate() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsSelectForUpdate");
        return instance.supportsSelectForUpdate();
    }

    public boolean supportsStatementPooling() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsStatementPooling");
        return instance.supportsStatementPooling();
    }

    public boolean supportsStoredProcedures() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsStoredProcedures");
        return instance.supportsStoredProcedures();
    }

    public boolean supportsSubqueriesInComparisons() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsSubqueriesInComparisons");
        return instance.supportsSubqueriesInComparisons();
    }

    public boolean supportsSubqueriesInExists() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsSubqueriesInExists");
        return instance.supportsSubqueriesInExists();
    }

    public boolean supportsSubqueriesInIns() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsSubqueriesInIns");
        return instance.supportsSubqueriesInIns();
    }

    public boolean supportsSubqueriesInQuantifieds() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsSubqueriesInQuantifieds");
        return instance.supportsSubqueriesInQuantifieds();
    }

    public boolean supportsTableCorrelationNames() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsTableCorrelationNames");
        return instance.supportsTableCorrelationNames();
    }

    public boolean supportsTransactionIsolationLevel(int param_1) throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsTransactionIsolationLevel");
        return instance.supportsTransactionIsolationLevel(param_1);
    }

    public boolean supportsTransactions() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsTransactions");
        return instance.supportsTransactions();
    }

    public boolean supportsUnion() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsUnion");
        return instance.supportsUnion();
    }

    public boolean supportsUnionAll() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsUnionAll");
        return instance.supportsUnionAll();
    }

    public boolean updatesAreDetected(int param_1) throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "updatesAreDetected");
        return instance.updatesAreDetected(param_1);
    }

    public boolean usesLocalFilePerTable() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "usesLocalFilePerTable");
        return instance.usesLocalFilePerTable();
    }

    public boolean usesLocalFiles() throws java.sql.SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "usesLocalFiles");
        return instance.usesLocalFiles();
    }

    public RowIdLifetime getRowIdLifetime() throws SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getRowIdLifetime");
        return instance.getRowIdLifetime();
    }

    public ResultSet getSchemas(String catalog, String schemaPattern) throws SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getSchemas");
        return instance.getSchemas(catalog, schemaPattern);
    }

    public boolean supportsStoredFunctionsUsingCallSyntax() throws SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "supportsStoredFunctionsUsingCallSyntax");
        return instance.supportsStoredFunctionsUsingCallSyntax();
    }

    public boolean autoCommitFailureClosesAllResultSets() throws SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "autoCommitFailureClosesAllResultSets");
        return instance.autoCommitFailureClosesAllResultSets();
    }

    public ResultSet getClientInfoProperties() throws SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getClientInfoProperties");
        return instance.getClientInfoProperties();
    }

    public ResultSet getFunctions(String catalog, String schemaPattern, String functionNamePattern) throws SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getFunctions");
        return instance.getFunctions(catalog, schemaPattern, functionNamePattern);
    }

    public ResultSet getFunctionColumns(String catalog, String schemaPattern, String functionNamePattern, String columnNamePattern) throws SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "getFunctionColumns");
        return instance.getFunctionColumns(catalog, schemaPattern, functionNamePattern, columnNamePattern);
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "unwrap");
        return instance.unwrap(iface);
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        logger.entering(DefaultDatabaseMetadataWrapper.class.getName(), "isWrapperFor");
        return instance.isWrapperFor(iface);
    }
    
    

//    protected void fireFunctionCallEnter(String name, Object... parameters) {
//        FunctionCall call = new FunctionCall(name, null, parameters);
//        logger.log(Level.FINE, call.toString());
//        support.firePropertyChange(call.getName(), null, call);
//    }
//
//    protected void fireFunctionCallExit(String name, Object ret,Object... parameters) {
//        FunctionCall call = new FunctionCall("post-"+name, null, parameters);
//        support.firePropertyChange(call.getName(), null, call);
//    }

//    protected void fireFunctionCall(String name, Object ret, Object... parameters) {
//        fireFunctionCall(new FunctionCall(name, ret, parameters));
//    }

//    protected void fireFunctionCall(FunctionCall call) {
//        if (!call.getName().startsWith("post-")) {
//            logger.log(Level.FINE, call.toString());
//        }
//        support.firePropertyChange(call.getName(), null, call);
//    }

//    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
//        support.addPropertyChangeListener(propertyName, listener);
//    }
//
//    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
//        support.removePropertyChangeListener(propertyName, listener);
//    }
//
//    public void addPropertyChangeListener(PropertyChangeListener listener) {
//        support.addPropertyChangeListener(listener);
//    }
//
//    public void removePropertyChangeListener(PropertyChangeListener listener) {
//        support.removePropertyChangeListener(listener);
//    }

    @Override
    public boolean generatedKeyAlwaysReturned() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ResultSet getPseudoColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
