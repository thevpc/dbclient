package net.thevpc.dbclient.api.sql;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.logging.Logger;
import net.thevpc.common.prs.log.LoggerProvider;

public abstract class DefaultConnectionWrapper implements ConnectionWrapper {

    private Connection connection;
    protected PropertyChangeSupport support;
    protected LoggerProvider loggerProvider;
    private Logger logger;
    private DatabaseMetaData databaseMetaData;

    public DefaultConnectionWrapper(Connection connection,LoggerProvider loggerProvider) {
        this.connection = connection;
        support = new PropertyChangeSupport(this);
        this.loggerProvider = loggerProvider;
        logger=loggerProvider.getLogger(DefaultConnectionWrapper.class.getName());
    }

    public DefaultConnectionWrapper() {
        support = new PropertyChangeSupport(this);
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void setLoggerProvider(LoggerProvider loggerProvider) {
        this.loggerProvider = loggerProvider;
        logger=loggerProvider.getLogger(DefaultConnectionWrapper.class.getName());
    }

    
    protected Statement wrap(Statement nativeStatement) {
        return new DefaultStatementWrapper(this, nativeStatement, null);
    }

    protected PreparedStatement wrap(PreparedStatement CallableStatementPreparedStatement, String sql) {
        return new DefaultPreparedStatementWrapper(this, CallableStatementPreparedStatement, sql);
    }

    protected CallableStatement wrap(CallableStatement nativeCallableStatement, String sql) {
        return new DefaultCallableStatementWrapper(this, nativeCallableStatement, sql);
    }

    
    @Override
    public LoggerProvider getLoggerProvider() {
        return loggerProvider;
    }

    protected void fireFunctionCall(String name, Object ret, Object... parameters) {
        fireFunctionCall(new FunctionCall(name, ret, parameters));
    }

    protected void fireFunctionCall(FunctionCall call) {
//        if (!call.getName().startsWith("post-")) {
//            logger.log(Level.FINE, call.toString());
//        }
        support.firePropertyChange(call.getName(), null, call);
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        support.addPropertyChangeListener(propertyName, listener);
    }

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        support.removePropertyChangeListener(propertyName, listener);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    
    //------------------------------------------------------------------
    
    
    public String getCatalog() throws SQLException {
        logger.entering(DefaultConnectionWrapper.class.getName(), "getCatalog");
        return connection.getCatalog();
    }

    public Statement createStatement() throws SQLException {
        logger.entering(DefaultConnectionWrapper.class.getName(), "createStatement");
        fireFunctionCall("createStatement", null);
        Statement statement = wrap(connection.createStatement());
        fireFunctionCall("post-createStatement", statement);
        return statement;
    }

    public PreparedStatement prepareStatement(String sql) throws SQLException {
        logger.entering(DefaultConnectionWrapper.class.getName(), "prepareStatement");
        fireFunctionCall("prepareStatement", null, sql);
        PreparedStatement statement = wrap(connection.prepareStatement(sql), sql);
        fireFunctionCall("post-prepareStatement", statement, sql);
        return statement;
    }

    public CallableStatement prepareCall(String sql) throws SQLException {
        logger.entering(DefaultConnectionWrapper.class.getName(), "prepareCall");
        fireFunctionCall("prepareCall", null, sql);
        CallableStatement callableStatement = wrap(connection.prepareCall(sql), sql);
        fireFunctionCall("post-prepareCall", callableStatement, sql);
        return callableStatement;
    }

    public String nativeSQL(String sql) throws SQLException {
        logger.entering(DefaultConnectionWrapper.class.getName(), "nativeSQL");
        fireFunctionCall("nativeSQL", null, sql);
        String s = connection.nativeSQL(sql);
        fireFunctionCall("post-nativeSQL", s, sql);
        return s;
    }

    public void setAutoCommit(boolean autoCommit) throws SQLException {
        logger.entering(DefaultConnectionWrapper.class.getName(), "setAutoCommit");
        fireFunctionCall("setAutoCommit", null, autoCommit);
        connection.setAutoCommit(autoCommit);
        fireFunctionCall("post-setAutoCommit", null, autoCommit);
    }

    public boolean getAutoCommit() throws SQLException {
        logger.entering(DefaultConnectionWrapper.class.getName(), "getAutoCommit");
        fireFunctionCall("setAutoCommit", null);
        boolean b = connection.getAutoCommit();
        fireFunctionCall("setAutoCommit", b);
        return b;
    }

    public void commit() throws SQLException {
        logger.entering(DefaultConnectionWrapper.class.getName(), "commit");
        fireFunctionCall("commit", null);
        connection.commit();
        fireFunctionCall("post-commit", null);
    }

    public void rollback() throws SQLException {
        logger.entering(DefaultConnectionWrapper.class.getName(), "rollback");
        fireFunctionCall("rollback", null);
        connection.rollback();
        fireFunctionCall("post-rollback", null);
    }

    public void close() throws SQLException {
        logger.entering(DefaultConnectionWrapper.class.getName(), "close");
        fireFunctionCall("close", null);
        support.firePropertyChange("closing", false, true);
        connection.close();
        support.firePropertyChange("closed", false, true);
        fireFunctionCall("post-close", null);
    }

    public boolean isClosed() throws SQLException {
        logger.entering(DefaultConnectionWrapper.class.getName(), "isClosed");
        fireFunctionCall("isClosed", null);
        boolean b = connection.isClosed();
        fireFunctionCall("post-isClosed", b);
        return b;
    }

    public DatabaseMetaData getMetaData() throws SQLException {
        logger.entering(DefaultConnectionWrapper.class.getName(), "getMetaData");
        if(databaseMetaData==null){
            databaseMetaData=new DefaultDatabaseMetadataWrapper(connection.getMetaData(), connection, loggerProvider);
        }
        return databaseMetaData;
    }

    public void setReadOnly(boolean readOnly) throws SQLException {
        logger.entering(DefaultConnectionWrapper.class.getName(), "setReadOnly");
        fireFunctionCall("setReadOnly", null, readOnly);
        connection.setReadOnly(readOnly);
        fireFunctionCall("post-setReadOnly", null, readOnly);
    }

    public boolean isReadOnly() throws SQLException {
        logger.entering(DefaultConnectionWrapper.class.getName(), "isReadOnly");
        return connection.isReadOnly();
    }

    public void setCatalog(String catalog) throws SQLException {
        logger.entering(DefaultConnectionWrapper.class.getName(), "setCatalog");
        fireFunctionCall("setCatalog", null, catalog);
        connection.setCatalog(catalog);
        fireFunctionCall("post-setCatalog", null, catalog);
    }

    public void setTransactionIsolation(int level) throws SQLException {
        logger.entering(DefaultConnectionWrapper.class.getName(), "setTransactionIsolation");
        fireFunctionCall("setTransactionIsolation", null, level);
        connection.setTransactionIsolation(level);
        fireFunctionCall("posr-setTransactionIsolation", null, level);
    }

    public int getTransactionIsolation() throws SQLException {
        logger.entering(DefaultConnectionWrapper.class.getName(), "getTransactionIsolation");
        return connection.getTransactionIsolation();
    }

    public PreparedStatement prepareStatement(String sql, String[] columNames) throws SQLException {
        logger.entering(DefaultConnectionWrapper.class.getName(), "prepareStatement");
        fireFunctionCall("prepareStatement", null, sql, columNames);
        PreparedStatement statement = wrap(connection.prepareStatement(sql, columNames), sql);
        fireFunctionCall("post-prepareStatement", statement, sql, columNames);
        return statement;
    }

    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
        logger.entering(DefaultConnectionWrapper.class.getName(), "prepareStatement");
        fireFunctionCall("prepareStatement", null, sql, columnIndexes);
        PreparedStatement statement = wrap(connection.prepareStatement(sql, columnIndexes), sql);
        fireFunctionCall("post-prepareStatement", statement, sql, columnIndexes);
        return statement;
    }

    public PreparedStatement prepareStatement(String string, int i) throws SQLException {
        logger.entering(DefaultConnectionWrapper.class.getName(), "prepareStatement");
        fireFunctionCall("prepareStatement", null, string, i);
        PreparedStatement statement = connection.prepareStatement(string, i);
        fireFunctionCall("post-prepareStatement", statement, string, i);
        return statement;
    }

    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        logger.entering(DefaultConnectionWrapper.class.getName(), "prepareCall");
        fireFunctionCall("prepareCall", null, sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        CallableStatement callableStatement = wrap(connection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability), sql);
        fireFunctionCall("post-prepareCall", callableStatement, sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        return callableStatement;
    }

    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
        logger.entering(DefaultConnectionWrapper.class.getName(), "releaseSavepoint");
        fireFunctionCall("releaseSavepoint", null, savepoint);
        connection.releaseSavepoint(savepoint);
        fireFunctionCall("post-releaseSavepoint", null, savepoint);
    }

    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        logger.entering(DefaultConnectionWrapper.class.getName(), "prepareStatement");
        fireFunctionCall("prepareStatement", null, sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        PreparedStatement statement = wrap(connection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability), sql);
        fireFunctionCall("post-prepareStatement", null, sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        return statement;
    }

    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        logger.entering(DefaultConnectionWrapper.class.getName(), "createStatement");
        fireFunctionCall("createStatement", null, resultSetType, resultSetConcurrency, resultSetHoldability);
        Statement statement = wrap(connection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability));
        fireFunctionCall("post-createStatement", statement, resultSetType, resultSetConcurrency, resultSetHoldability);
        return statement;
    }

    public void rollback(Savepoint savepoint) throws SQLException {
        logger.entering(DefaultConnectionWrapper.class.getName(), "rollback");
        fireFunctionCall("rollback", null, savepoint);
        connection.rollback(savepoint);
        fireFunctionCall("post-rollback", savepoint);
    }

    public Savepoint setSavepoint(String name) throws SQLException {
        logger.entering(DefaultConnectionWrapper.class.getName(), "setSavepoint");
        fireFunctionCall("setSavepoint", null, name);
        Savepoint savepoint = connection.setSavepoint(name);
        fireFunctionCall("post-setSavepoint", savepoint, name);
        return savepoint;
    }

    public Savepoint setSavepoint() throws SQLException {
        logger.entering(DefaultConnectionWrapper.class.getName(), "setSavepoint");
        fireFunctionCall("setSavepoint", null);
        Savepoint savepoint = connection.setSavepoint();
        fireFunctionCall("post-setSavepoint", savepoint);
        return savepoint;
    }

    public int getHoldability() throws SQLException {
        logger.entering(DefaultConnectionWrapper.class.getName(), "getHoldability");
        return connection.getHoldability();
    }

    public void setHoldability(int i) throws SQLException {
        logger.entering(DefaultConnectionWrapper.class.getName(), "setHoldability");
        fireFunctionCall("setHoldability", null, i);
        connection.setHoldability(i);
        fireFunctionCall("post-setHoldability", null, i);
    }

    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
        logger.entering(DefaultConnectionWrapper.class.getName(), "setTypeMap");
        fireFunctionCall("setTypeMap", null, map);
        connection.setTypeMap(map);
        fireFunctionCall("post-setTypeMap", null, map);
    }

    public Map<String, Class<?>> getTypeMap() throws SQLException {
        logger.entering(DefaultConnectionWrapper.class.getName(), "getTypeMap");
        return connection.getTypeMap();
    }

    public CallableStatement prepareCall(String sql, int resultsetType, int resultsetConcurrency) throws SQLException {
        logger.entering(DefaultConnectionWrapper.class.getName(), "prepareCall");
        fireFunctionCall("prepareCall", null, sql, resultsetType, resultsetConcurrency);
        CallableStatement callableStatement = wrap(connection.prepareCall(sql, resultsetType, resultsetConcurrency), sql);
        fireFunctionCall("post-prepareCall", callableStatement, sql, resultsetType, resultsetConcurrency);
        return callableStatement;
    }

    public PreparedStatement prepareStatement(String string, int i, int i1) throws SQLException {
        logger.entering(DefaultConnectionWrapper.class.getName(), "prepareStatement");
        fireFunctionCall("prepareStatement", null, string, i, i1);
        PreparedStatement statement = connection.prepareStatement(string, i, i1);
        fireFunctionCall("post-prepareStatement", statement, string, i, i1);
        return statement;
    }

    public Statement createStatement(int i, int i1) throws SQLException {
        logger.entering(DefaultConnectionWrapper.class.getName(), "createStatement");
        fireFunctionCall("createStatement", null, i, i1);
        Statement statement = wrap(connection.createStatement(i, i1));
        fireFunctionCall("post-createStatement", statement, i, i1);
        return statement;
    }

    public SQLWarning getWarnings() throws SQLException {
        logger.entering(DefaultConnectionWrapper.class.getName(), "getWarnings");
        return connection.getWarnings();
    }

    public void clearWarnings() throws SQLException {
        logger.entering(DefaultConnectionWrapper.class.getName(), "clearWarnings");
        fireFunctionCall("clearWarnings", null);
        connection.clearWarnings();
        fireFunctionCall("post-clearWarnings", null);
    }


    public Clob createClob() throws SQLException {
        logger.entering(DefaultConnectionWrapper.class.getName(), "createClob");
        return connection.createClob();
    }

    public Blob createBlob() throws SQLException {
        logger.entering(DefaultConnectionWrapper.class.getName(), "createBlob");
        return connection.createBlob();
    }

    public NClob createNClob() throws SQLException {
        logger.entering(DefaultConnectionWrapper.class.getName(), "createNClob");
        return connection.createNClob();
    }

    public SQLXML createSQLXML() throws SQLException {
        logger.entering(DefaultConnectionWrapper.class.getName(), "createSQLXML");
        return connection.createSQLXML();
    }

    public boolean isValid(int timeout) throws SQLException {
        logger.entering(DefaultConnectionWrapper.class.getName(), "isValid");
        return connection.isValid(timeout);
    }

    public void setClientInfo(String name, String value) throws SQLClientInfoException {
        logger.entering(DefaultConnectionWrapper.class.getName(), "setClientInfo");
        connection.setClientInfo(name, value);
    }

    public void setClientInfo(Properties properties) throws SQLClientInfoException {
        logger.entering(DefaultConnectionWrapper.class.getName(), "setClientInfo");
        connection.setClientInfo(properties);
    }

    public String getClientInfo(String name) throws SQLException {
        logger.entering(DefaultConnectionWrapper.class.getName(), "getClientInfo");
        return connection.getClientInfo(name);
    }

    public Properties getClientInfo() throws SQLException {
        logger.entering(DefaultConnectionWrapper.class.getName(), "getClientInfo");
        return connection.getClientInfo();
    }

    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
        logger.entering(DefaultConnectionWrapper.class.getName(), "createArrayOf");
        return connection.createArrayOf(typeName, elements);
    }

    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
        logger.entering(DefaultConnectionWrapper.class.getName(), "createStruct");
        return connection.createStruct(typeName, attributes);
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        logger.entering(DefaultConnectionWrapper.class.getName(), "unwrap");
        return connection.unwrap(iface);
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        logger.entering(DefaultConnectionWrapper.class.getName(), "isWrapperFor");
        return connection.isWrapperFor(iface);
    }

    @Override
    public void abort(Executor executor) throws SQLException {
        connection.abort(executor);
    }

    @Override
    public int getNetworkTimeout() throws SQLException {
        return connection.getNetworkTimeout();
    }

    @Override
    public String getSchema() throws SQLException {
        return connection.getSchema();
    }

    @Override
    public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
        connection.setNetworkTimeout(executor, milliseconds);
    }

    @Override
    public void setSchema(String schema) throws SQLException {
        connection.setSchema(schema);
    }
    
    
}
