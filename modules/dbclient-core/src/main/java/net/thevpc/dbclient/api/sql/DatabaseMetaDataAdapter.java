package net.thevpc.dbclient.api.sql;

import java.sql.ResultSet;
import java.sql.RowIdLifetime;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: vpc
 * Date: 30 mars 2009
 * Time: 21:45:06
 * To change this template use File | Settings | File Templates.
 */
public class DatabaseMetaDataAdapter implements java.sql.DatabaseMetaData {
    private java.sql.DatabaseMetaData instance;

    protected DatabaseMetaDataAdapter(java.sql.DatabaseMetaData instance) {
        this.instance = instance;
    }

    public java.lang.String getURL() throws java.sql.SQLException {
        return instance.getURL();
    }

    public boolean isReadOnly() throws java.sql.SQLException {
        return instance.isReadOnly();
    }

    public java.sql.ResultSet getAttributes(java.lang.String param_1, java.lang.String param_2, java.lang.String param_3, java.lang.String param_4) throws java.sql.SQLException {
        return instance.getAttributes(param_1, param_2, param_3, param_4);
    }

    public java.lang.String getUserName() throws java.sql.SQLException {
        return instance.getUserName();
    }

    public boolean allProceduresAreCallable() throws java.sql.SQLException {
        return instance.allProceduresAreCallable();
    }

    public boolean allTablesAreSelectable() throws java.sql.SQLException {
        return instance.allTablesAreSelectable();
    }

    public boolean dataDefinitionCausesTransactionCommit() throws java.sql.SQLException {
        return instance.dataDefinitionCausesTransactionCommit();
    }

    public boolean dataDefinitionIgnoredInTransactions() throws java.sql.SQLException {
        return instance.dataDefinitionIgnoredInTransactions();
    }

    public boolean deletesAreDetected(int param_1) throws java.sql.SQLException {
        return instance.deletesAreDetected(param_1);
    }

    public boolean doesMaxRowSizeIncludeBlobs() throws java.sql.SQLException {
        return instance.doesMaxRowSizeIncludeBlobs();
    }

    public java.sql.ResultSet getBestRowIdentifier(java.lang.String param_1, java.lang.String param_2, java.lang.String param_3, int param_4, boolean param_5) throws java.sql.SQLException {
        return instance.getBestRowIdentifier(param_1, param_2, param_3, param_4, param_5);
    }

    public java.lang.String getCatalogSeparator() throws java.sql.SQLException {
        return instance.getCatalogSeparator();
    }

    public java.lang.String getCatalogTerm() throws java.sql.SQLException {
        return instance.getCatalogTerm();
    }

    public java.sql.ResultSet getCatalogs() throws java.sql.SQLException {
        return instance.getCatalogs();
    }

    public java.sql.ResultSet getColumnPrivileges(java.lang.String param_1, java.lang.String param_2, java.lang.String param_3, java.lang.String param_4) throws java.sql.SQLException {
        return instance.getColumnPrivileges(param_1, param_2, param_3, param_4);
    }

    public java.sql.ResultSet getColumns(java.lang.String param_1, java.lang.String param_2, java.lang.String param_3, java.lang.String param_4) throws java.sql.SQLException {
        return instance.getColumns(param_1, param_2, param_3, param_4);
    }

    public java.sql.Connection getConnection() throws java.sql.SQLException {
        return instance.getConnection();
    }

    public java.sql.ResultSet getCrossReference(java.lang.String param_1, java.lang.String param_2, java.lang.String param_3, java.lang.String param_4, java.lang.String param_5, java.lang.String param_6) throws java.sql.SQLException {
        return instance.getCrossReference(param_1, param_2, param_3, param_4, param_5, param_6);
    }

    public int getDatabaseMajorVersion() throws java.sql.SQLException {
        return instance.getDatabaseMajorVersion();
    }

    public int getDatabaseMinorVersion() throws java.sql.SQLException {
        return instance.getDatabaseMinorVersion();
    }

    public java.lang.String getDatabaseProductName() throws java.sql.SQLException {
        return instance.getDatabaseProductName();
    }

    public java.lang.String getDatabaseProductVersion() throws java.sql.SQLException {
        return instance.getDatabaseProductVersion();
    }

    public int getDefaultTransactionIsolation() throws java.sql.SQLException {
        return instance.getDefaultTransactionIsolation();
    }

    public int getDriverMajorVersion() {
        return instance.getDriverMajorVersion();
    }

    public int getDriverMinorVersion() {
        return instance.getDriverMinorVersion();
    }

    public java.lang.String getDriverName() throws java.sql.SQLException {
        return instance.getDriverName();
    }

    public java.lang.String getDriverVersion() throws java.sql.SQLException {
        return instance.getDriverVersion();
    }

    public java.sql.ResultSet getExportedKeys(java.lang.String param_1, java.lang.String param_2, java.lang.String param_3) throws java.sql.SQLException {
        return instance.getExportedKeys(param_1, param_2, param_3);
    }

    public java.lang.String getExtraNameCharacters() throws java.sql.SQLException {
        return instance.getExtraNameCharacters();
    }

    public java.lang.String getIdentifierQuoteString() throws java.sql.SQLException {
        return instance.getIdentifierQuoteString();
    }

    public java.sql.ResultSet getImportedKeys(java.lang.String param_1, java.lang.String param_2, java.lang.String param_3) throws java.sql.SQLException {
        return instance.getImportedKeys(param_1, param_2, param_3);
    }

    public java.sql.ResultSet getIndexInfo(java.lang.String param_1, java.lang.String param_2, java.lang.String param_3, boolean param_4, boolean param_5) throws java.sql.SQLException {
        return instance.getIndexInfo(param_1, param_2, param_3, param_4, param_5);
    }

    public int getJDBCMajorVersion() throws java.sql.SQLException {
        return instance.getJDBCMajorVersion();
    }

    public int getJDBCMinorVersion() throws java.sql.SQLException {
        return instance.getJDBCMinorVersion();
    }

    public int getMaxBinaryLiteralLength() throws java.sql.SQLException {
        return instance.getMaxBinaryLiteralLength();
    }

    public int getMaxCatalogNameLength() throws java.sql.SQLException {
        return instance.getMaxCatalogNameLength();
    }

    public int getMaxCharLiteralLength() throws java.sql.SQLException {
        return instance.getMaxCharLiteralLength();
    }

    public int getMaxColumnNameLength() throws java.sql.SQLException {
        return instance.getMaxColumnNameLength();
    }

    public int getMaxColumnsInGroupBy() throws java.sql.SQLException {
        return instance.getMaxColumnsInGroupBy();
    }

    public int getMaxColumnsInIndex() throws java.sql.SQLException {
        return instance.getMaxColumnsInIndex();
    }

    public int getMaxColumnsInOrderBy() throws java.sql.SQLException {
        return instance.getMaxColumnsInOrderBy();
    }

    public int getMaxColumnsInSelect() throws java.sql.SQLException {
        return instance.getMaxColumnsInSelect();
    }

    public int getMaxColumnsInTable() throws java.sql.SQLException {
        return instance.getMaxColumnsInTable();
    }

    public int getMaxConnections() throws java.sql.SQLException {
        return instance.getMaxConnections();
    }

    public int getMaxCursorNameLength() throws java.sql.SQLException {
        return instance.getMaxCursorNameLength();
    }

    public int getMaxIndexLength() throws java.sql.SQLException {
        return instance.getMaxIndexLength();
    }

    public int getMaxProcedureNameLength() throws java.sql.SQLException {
        return instance.getMaxProcedureNameLength();
    }

    public int getMaxRowSize() throws java.sql.SQLException {
        return instance.getMaxRowSize();
    }

    public int getMaxSchemaNameLength() throws java.sql.SQLException {
        return instance.getMaxSchemaNameLength();
    }

    public int getMaxStatementLength() throws java.sql.SQLException {
        return instance.getMaxStatementLength();
    }

    public int getMaxStatements() throws java.sql.SQLException {
        return instance.getMaxStatements();
    }

    public int getMaxTableNameLength() throws java.sql.SQLException {
        return instance.getMaxTableNameLength();
    }

    public int getMaxTablesInSelect() throws java.sql.SQLException {
        return instance.getMaxTablesInSelect();
    }

    public int getMaxUserNameLength() throws java.sql.SQLException {
        return instance.getMaxUserNameLength();
    }

    public java.lang.String getNumericFunctions() throws java.sql.SQLException {
        return instance.getNumericFunctions();
    }

    public java.sql.ResultSet getPrimaryKeys(java.lang.String param_1, java.lang.String param_2, java.lang.String param_3) throws java.sql.SQLException {
        return instance.getPrimaryKeys(param_1, param_2, param_3);
    }

    public java.sql.ResultSet getProcedureColumns(java.lang.String param_1, java.lang.String param_2, java.lang.String param_3, java.lang.String param_4) throws java.sql.SQLException {
        return instance.getProcedureColumns(param_1, param_2, param_3, param_4);
    }

    public java.lang.String getProcedureTerm() throws java.sql.SQLException {
        return instance.getProcedureTerm();
    }

    public java.sql.ResultSet getProcedures(java.lang.String param_1, java.lang.String param_2, java.lang.String param_3) throws java.sql.SQLException {
        return instance.getProcedures(param_1, param_2, param_3);
    }

    public int getResultSetHoldability() throws java.sql.SQLException {
        return instance.getResultSetHoldability();
    }

    public java.lang.String getSQLKeywords() throws java.sql.SQLException {
        return instance.getSQLKeywords();
    }

    public int getSQLStateType() throws java.sql.SQLException {
        return instance.getSQLStateType();
    }

    public java.lang.String getSchemaTerm() throws java.sql.SQLException {
        return instance.getSchemaTerm();
    }

    public java.sql.ResultSet getSchemas() throws java.sql.SQLException {
        return instance.getSchemas();
    }

    public java.lang.String getSearchStringEscape() throws java.sql.SQLException {
        return instance.getSearchStringEscape();
    }

    public java.lang.String getStringFunctions() throws java.sql.SQLException {
        return instance.getStringFunctions();
    }

    public java.sql.ResultSet getSuperTables(java.lang.String param_1, java.lang.String param_2, java.lang.String param_3) throws java.sql.SQLException {
        return instance.getSuperTables(param_1, param_2, param_3);
    }

    public java.sql.ResultSet getSuperTypes(java.lang.String param_1, java.lang.String param_2, java.lang.String param_3) throws java.sql.SQLException {
        return instance.getSuperTypes(param_1, param_2, param_3);
    }

    public java.lang.String getSystemFunctions() throws java.sql.SQLException {
        return instance.getSystemFunctions();
    }

    public java.sql.ResultSet getTablePrivileges(java.lang.String param_1, java.lang.String param_2, java.lang.String param_3) throws java.sql.SQLException {
        return instance.getTablePrivileges(param_1, param_2, param_3);
    }

    public java.sql.ResultSet getTableTypes() throws java.sql.SQLException {
        return instance.getTableTypes();
    }

    public java.sql.ResultSet getTables(java.lang.String param_1, java.lang.String param_2, java.lang.String param_3, java.lang.String[] param_4) throws java.sql.SQLException {
        return instance.getTables(param_1, param_2, param_3, param_4);
    }

    public java.lang.String getTimeDateFunctions() throws java.sql.SQLException {
        return instance.getTimeDateFunctions();
    }

    public java.sql.ResultSet getTypeInfo() throws java.sql.SQLException {
        return instance.getTypeInfo();
    }

    public java.sql.ResultSet getUDTs(java.lang.String param_1, java.lang.String param_2, java.lang.String param_3, int[] param_4) throws java.sql.SQLException {
        return instance.getUDTs(param_1, param_2, param_3, param_4);
    }

    public java.sql.ResultSet getVersionColumns(java.lang.String param_1, java.lang.String param_2, java.lang.String param_3) throws java.sql.SQLException {
        return instance.getVersionColumns(param_1, param_2, param_3);
    }

    public boolean insertsAreDetected(int param_1) throws java.sql.SQLException {
        return instance.insertsAreDetected(param_1);
    }

    public boolean isCatalogAtStart() throws java.sql.SQLException {
        return instance.isCatalogAtStart();
    }

    public boolean locatorsUpdateCopy() throws java.sql.SQLException {
        return instance.locatorsUpdateCopy();
    }

    public boolean nullPlusNonNullIsNull() throws java.sql.SQLException {
        return instance.nullPlusNonNullIsNull();
    }

    public boolean nullsAreSortedAtEnd() throws java.sql.SQLException {
        return instance.nullsAreSortedAtEnd();
    }

    public boolean nullsAreSortedAtStart() throws java.sql.SQLException {
        return instance.nullsAreSortedAtStart();
    }

    public boolean nullsAreSortedHigh() throws java.sql.SQLException {
        return instance.nullsAreSortedHigh();
    }

    public boolean nullsAreSortedLow() throws java.sql.SQLException {
        return instance.nullsAreSortedLow();
    }

    public boolean othersDeletesAreVisible(int param_1) throws java.sql.SQLException {
        return instance.othersDeletesAreVisible(param_1);
    }

    public boolean othersInsertsAreVisible(int param_1) throws java.sql.SQLException {
        return instance.othersInsertsAreVisible(param_1);
    }

    public boolean othersUpdatesAreVisible(int param_1) throws java.sql.SQLException {
        return instance.othersUpdatesAreVisible(param_1);
    }

    public boolean ownDeletesAreVisible(int param_1) throws java.sql.SQLException {
        return instance.ownDeletesAreVisible(param_1);
    }

    public boolean ownInsertsAreVisible(int param_1) throws java.sql.SQLException {
        return instance.ownInsertsAreVisible(param_1);
    }

    public boolean ownUpdatesAreVisible(int param_1) throws java.sql.SQLException {
        return instance.ownUpdatesAreVisible(param_1);
    }

    public boolean storesLowerCaseIdentifiers() throws java.sql.SQLException {
        return instance.storesLowerCaseIdentifiers();
    }

    public boolean storesLowerCaseQuotedIdentifiers() throws java.sql.SQLException {
        return instance.storesLowerCaseQuotedIdentifiers();
    }

    public boolean storesMixedCaseIdentifiers() throws java.sql.SQLException {
        return instance.storesMixedCaseIdentifiers();
    }

    public boolean storesMixedCaseQuotedIdentifiers() throws java.sql.SQLException {
        return instance.storesMixedCaseQuotedIdentifiers();
    }

    public boolean storesUpperCaseIdentifiers() throws java.sql.SQLException {
        return instance.storesUpperCaseIdentifiers();
    }

    public boolean storesUpperCaseQuotedIdentifiers() throws java.sql.SQLException {
        return instance.storesUpperCaseQuotedIdentifiers();
    }

    public boolean supportsANSI92EntryLevelSQL() throws java.sql.SQLException {
        return instance.supportsANSI92EntryLevelSQL();
    }

    public boolean supportsANSI92FullSQL() throws java.sql.SQLException {
        return instance.supportsANSI92FullSQL();
    }

    public boolean supportsANSI92IntermediateSQL() throws java.sql.SQLException {
        return instance.supportsANSI92IntermediateSQL();
    }

    public boolean supportsAlterTableWithAddColumn() throws java.sql.SQLException {
        return instance.supportsAlterTableWithAddColumn();
    }

    public boolean supportsAlterTableWithDropColumn() throws java.sql.SQLException {
        return instance.supportsAlterTableWithDropColumn();
    }

    public boolean supportsBatchUpdates() throws java.sql.SQLException {
        return instance.supportsBatchUpdates();
    }

    public boolean supportsCatalogsInDataManipulation() throws java.sql.SQLException {
        return instance.supportsCatalogsInDataManipulation();
    }

    public boolean supportsCatalogsInIndexDefinitions() throws java.sql.SQLException {
        return instance.supportsCatalogsInIndexDefinitions();
    }

    public boolean supportsCatalogsInPrivilegeDefinitions() throws java.sql.SQLException {
        return instance.supportsCatalogsInPrivilegeDefinitions();
    }

    public boolean supportsCatalogsInProcedureCalls() throws java.sql.SQLException {
        return instance.supportsCatalogsInProcedureCalls();
    }

    public boolean supportsCatalogsInTableDefinitions() throws java.sql.SQLException {
        return instance.supportsCatalogsInTableDefinitions();
    }

    public boolean supportsColumnAliasing() throws java.sql.SQLException {
        return instance.supportsColumnAliasing();
    }

    public boolean supportsConvert() throws java.sql.SQLException {
        return instance.supportsConvert();
    }

    public boolean supportsConvert(int param_1, int param_2) throws java.sql.SQLException {
        return instance.supportsConvert(param_1, param_2);
    }

    public boolean supportsCoreSQLGrammar() throws java.sql.SQLException {
        return instance.supportsCoreSQLGrammar();
    }

    public boolean supportsCorrelatedSubqueries() throws java.sql.SQLException {
        return instance.supportsCorrelatedSubqueries();
    }

    public boolean supportsDataDefinitionAndDataManipulationTransactions() throws java.sql.SQLException {
        return instance.supportsDataDefinitionAndDataManipulationTransactions();
    }

    public boolean supportsDataManipulationTransactionsOnly() throws java.sql.SQLException {
        return instance.supportsDataManipulationTransactionsOnly();
    }

    public boolean supportsDifferentTableCorrelationNames() throws java.sql.SQLException {
        return instance.supportsDifferentTableCorrelationNames();
    }

    public boolean supportsExpressionsInOrderBy() throws java.sql.SQLException {
        return instance.supportsExpressionsInOrderBy();
    }

    public boolean supportsExtendedSQLGrammar() throws java.sql.SQLException {
        return instance.supportsExtendedSQLGrammar();
    }

    public boolean supportsFullOuterJoins() throws java.sql.SQLException {
        return instance.supportsFullOuterJoins();
    }

    public boolean supportsGetGeneratedKeys() throws java.sql.SQLException {
        return instance.supportsGetGeneratedKeys();
    }

    public boolean supportsGroupBy() throws java.sql.SQLException {
        return instance.supportsGroupBy();
    }

    public boolean supportsGroupByBeyondSelect() throws java.sql.SQLException {
        return instance.supportsGroupByBeyondSelect();
    }

    public boolean supportsGroupByUnrelated() throws java.sql.SQLException {
        return instance.supportsGroupByUnrelated();
    }

    public boolean supportsIntegrityEnhancementFacility() throws java.sql.SQLException {
        return instance.supportsIntegrityEnhancementFacility();
    }

    public boolean supportsLikeEscapeClause() throws java.sql.SQLException {
        return instance.supportsLikeEscapeClause();
    }

    public boolean supportsLimitedOuterJoins() throws java.sql.SQLException {
        return instance.supportsLimitedOuterJoins();
    }

    public boolean supportsMinimumSQLGrammar() throws java.sql.SQLException {
        return instance.supportsMinimumSQLGrammar();
    }

    public boolean supportsMixedCaseIdentifiers() throws java.sql.SQLException {
        return instance.supportsMixedCaseIdentifiers();
    }

    public boolean supportsMixedCaseQuotedIdentifiers() throws java.sql.SQLException {
        return instance.supportsMixedCaseQuotedIdentifiers();
    }

    public boolean supportsMultipleOpenResults() throws java.sql.SQLException {
        return instance.supportsMultipleOpenResults();
    }

    public boolean supportsMultipleResultSets() throws java.sql.SQLException {
        return instance.supportsMultipleResultSets();
    }

    public boolean supportsMultipleTransactions() throws java.sql.SQLException {
        return instance.supportsMultipleTransactions();
    }

    public boolean supportsNamedParameters() throws java.sql.SQLException {
        return instance.supportsNamedParameters();
    }

    public boolean supportsNonNullableColumns() throws java.sql.SQLException {
        return instance.supportsNonNullableColumns();
    }

    public boolean supportsOpenCursorsAcrossCommit() throws java.sql.SQLException {
        return instance.supportsOpenCursorsAcrossCommit();
    }

    public boolean supportsOpenCursorsAcrossRollback() throws java.sql.SQLException {
        return instance.supportsOpenCursorsAcrossRollback();
    }

    public boolean supportsOpenStatementsAcrossCommit() throws java.sql.SQLException {
        return instance.supportsOpenStatementsAcrossCommit();
    }

    public boolean supportsOpenStatementsAcrossRollback() throws java.sql.SQLException {
        return instance.supportsOpenStatementsAcrossRollback();
    }

    public boolean supportsOrderByUnrelated() throws java.sql.SQLException {
        return instance.supportsOrderByUnrelated();
    }

    public boolean supportsOuterJoins() throws java.sql.SQLException {
        return instance.supportsOuterJoins();
    }

    public boolean supportsPositionedDelete() throws java.sql.SQLException {
        return instance.supportsPositionedDelete();
    }

    public boolean supportsPositionedUpdate() throws java.sql.SQLException {
        return instance.supportsPositionedUpdate();
    }

    public boolean supportsResultSetConcurrency(int param_1, int param_2) throws java.sql.SQLException {
        return instance.supportsResultSetConcurrency(param_1, param_2);
    }

    public boolean supportsResultSetHoldability(int param_1) throws java.sql.SQLException {
        return instance.supportsResultSetHoldability(param_1);
    }

    public boolean supportsResultSetType(int param_1) throws java.sql.SQLException {
        return instance.supportsResultSetType(param_1);
    }

    public boolean supportsSavepoints() throws java.sql.SQLException {
        return instance.supportsSavepoints();
    }

    public boolean supportsSchemasInDataManipulation() throws java.sql.SQLException {
        return instance.supportsSchemasInDataManipulation();
    }

    public boolean supportsSchemasInIndexDefinitions() throws java.sql.SQLException {
        return instance.supportsSchemasInIndexDefinitions();
    }

    public boolean supportsSchemasInPrivilegeDefinitions() throws java.sql.SQLException {
        return instance.supportsSchemasInPrivilegeDefinitions();
    }

    public boolean supportsSchemasInProcedureCalls() throws java.sql.SQLException {
        return instance.supportsSchemasInProcedureCalls();
    }

    public boolean supportsSchemasInTableDefinitions() throws java.sql.SQLException {
        return instance.supportsSchemasInTableDefinitions();
    }

    public boolean supportsSelectForUpdate() throws java.sql.SQLException {
        return instance.supportsSelectForUpdate();
    }

    public boolean supportsStatementPooling() throws java.sql.SQLException {
        return instance.supportsStatementPooling();
    }

    public boolean supportsStoredProcedures() throws java.sql.SQLException {
        return instance.supportsStoredProcedures();
    }

    public boolean supportsSubqueriesInComparisons() throws java.sql.SQLException {
        return instance.supportsSubqueriesInComparisons();
    }

    public boolean supportsSubqueriesInExists() throws java.sql.SQLException {
        return instance.supportsSubqueriesInExists();
    }

    public boolean supportsSubqueriesInIns() throws java.sql.SQLException {
        return instance.supportsSubqueriesInIns();
    }

    public boolean supportsSubqueriesInQuantifieds() throws java.sql.SQLException {
        return instance.supportsSubqueriesInQuantifieds();
    }

    public boolean supportsTableCorrelationNames() throws java.sql.SQLException {
        return instance.supportsTableCorrelationNames();
    }

    public boolean supportsTransactionIsolationLevel(int param_1) throws java.sql.SQLException {
        return instance.supportsTransactionIsolationLevel(param_1);
    }

    public boolean supportsTransactions() throws java.sql.SQLException {
        return instance.supportsTransactions();
    }

    public boolean supportsUnion() throws java.sql.SQLException {
        return instance.supportsUnion();
    }

    public boolean supportsUnionAll() throws java.sql.SQLException {
        return instance.supportsUnionAll();
    }

    public boolean updatesAreDetected(int param_1) throws java.sql.SQLException {
        return instance.updatesAreDetected(param_1);
    }

    public boolean usesLocalFilePerTable() throws java.sql.SQLException {
        return instance.usesLocalFilePerTable();
    }

    public boolean usesLocalFiles() throws java.sql.SQLException {
        return instance.usesLocalFiles();
    }


    public RowIdLifetime getRowIdLifetime() throws SQLException {
        return instance.getRowIdLifetime();
    }

    public ResultSet getSchemas(String catalog, String schemaPattern) throws SQLException {
        return instance.getSchemas(catalog, schemaPattern);  
    }

    public boolean supportsStoredFunctionsUsingCallSyntax() throws SQLException {
        return instance.supportsStoredFunctionsUsingCallSyntax();  
    }

    public boolean autoCommitFailureClosesAllResultSets() throws SQLException {
        return instance.autoCommitFailureClosesAllResultSets();  
    }

    public ResultSet getClientInfoProperties() throws SQLException {
        return instance.getClientInfoProperties();  
    }

    public ResultSet getFunctions(String catalog, String schemaPattern, String functionNamePattern) throws SQLException {
        return instance.getFunctions(catalog, schemaPattern, functionNamePattern);  
    }

    public ResultSet getFunctionColumns(String catalog, String schemaPattern, String functionNamePattern, String columnNamePattern) throws SQLException {
        return instance.getFunctionColumns(catalog, schemaPattern, functionNamePattern, columnNamePattern);  
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        return instance.unwrap(iface);  
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return instance.isWrapperFor(iface);  
    }

    @Override
    public boolean generatedKeyAlwaysReturned() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ResultSet getPseudoColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
}
