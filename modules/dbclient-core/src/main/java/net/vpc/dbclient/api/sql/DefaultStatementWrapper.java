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


package net.vpc.dbclient.api.sql;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 14 août 2007 22:12:11
 */
public class DefaultStatementWrapper implements StatementWrapper {
    private Statement statement;
    private ConnectionWrapper connectionWrapper;
    private PropertyChangeSupport support;
    private Logger logger;
    private String sql;
    private ArrayList<String> sqlBatch;


    public DefaultStatementWrapper(ConnectionWrapper connectionWrapper, Statement statement, String sql) {
        this.statement = statement;
        this.connectionWrapper = connectionWrapper;
        logger = connectionWrapper.getLoggerProvider().getLogger(DefaultStatementWrapper.class.getName());
        support = new PropertyChangeSupport(this);
        this.sql = sql;
    }

    public ConnectionWrapper getConnectionWrapper() {
        return connectionWrapper;
    }

    public Statement getStatement() {
        return statement;
    }

    protected void fireFunctionCall(String name, Object ret, Object... parameters) {
        fireFunctionCall(new FunctionCall(name, ret, parameters));
    }

    protected void fireFunctionCall(FunctionCall call) {
        //log only pre-* calls as calls may fail !
//        if (call.getName().startsWith("pre-")) {
//            logger.log(Level.FINE,call.toString());
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

    protected ResultSet wrap(ResultSet rs) {
        if(rs==null){
            return null;
        }
        return new DefaultResultSetWrapper(this, rs);
    }

    public String getSql() {
        return sql;
    }

    //---------------------------------------------------------
    
    public ResultSet executeQuery(String sql) throws SQLException {
        logger.entering(DefaultStatementWrapper.class.getName(),"executeQuery");
        this.sql = sql;
        fireFunctionCall("pre-executeQuery", null, sql);
        ResultSet resultSet = wrap(statement.executeQuery(sql));
        fireFunctionCall("executeQuery", resultSet, sql);
        return resultSet;
    }

    public int executeUpdate(String sql) throws SQLException {
        logger.entering(DefaultStatementWrapper.class.getName(),"executeUpdate");
        this.sql = sql;
        fireFunctionCall("pre-executeUpdate", null, sql);
        int i = statement.executeUpdate(sql);
        fireFunctionCall("executeUpdate", i, sql);
        return i;
    }

    public void close() throws SQLException {
        logger.entering(DefaultStatementWrapper.class.getName(),"close");
        fireFunctionCall("pre-close", null);
        statement.close();
        fireFunctionCall("close", null);
    }

    public int getMaxFieldSize() throws SQLException {
        logger.entering(DefaultStatementWrapper.class.getName(),"getMaxFieldSize");
        return statement.getMaxFieldSize();
    }

    public void setMaxFieldSize(int max) throws SQLException {
        logger.entering(DefaultStatementWrapper.class.getName(),"setMaxFieldSize");
        fireFunctionCall("pre-setMaxFieldSize", null, max);
        statement.setMaxFieldSize(max);
        fireFunctionCall("setMaxFieldSize", null, max);
    }

    public int getMaxRows() throws SQLException {
        logger.entering(DefaultStatementWrapper.class.getName(),"getMaxRows");
        return statement.getMaxRows();
    }

    public void setMaxRows(int max) throws SQLException {
        logger.entering(DefaultStatementWrapper.class.getName(),"setMaxRows");
        fireFunctionCall("pre-setMaxRows", null, max);
        statement.setMaxRows(max);
        fireFunctionCall("setMaxRows", null, max);
    }

    public void setEscapeProcessing(boolean enable) throws SQLException {
        logger.entering(DefaultStatementWrapper.class.getName(),"setEscapeProcessing");
        fireFunctionCall("pre-setEscapeProcessing", null, enable);
        statement.setEscapeProcessing(enable);
        fireFunctionCall("setEscapeProcessing", null, enable);
    }

    public int getQueryTimeout() throws SQLException {
        logger.entering(DefaultStatementWrapper.class.getName(),"getQueryTimeout");
        return statement.getQueryTimeout();
    }

    public void setQueryTimeout(int seconds) throws SQLException {
        logger.entering(DefaultStatementWrapper.class.getName(),"setQueryTimeout");
        fireFunctionCall("pre-setQueryTimeout", null, seconds);
        statement.setQueryTimeout(seconds);
        fireFunctionCall("setQueryTimeout", null, seconds);
    }

    public void cancel() throws SQLException {
        logger.entering(DefaultStatementWrapper.class.getName(),"cancel");
        fireFunctionCall("pre-cancel", null);
        statement.cancel();
        fireFunctionCall("cancel", null);
    }

    public SQLWarning getWarnings() throws SQLException {
        logger.entering(DefaultStatementWrapper.class.getName(),"getWarnings");
        return statement.getWarnings();
    }

    public void clearWarnings() throws SQLException {
        logger.entering(DefaultStatementWrapper.class.getName(),"clearWarnings");
        fireFunctionCall("pre-clearWarnings", null);
        statement.clearWarnings();
        fireFunctionCall("clearWarnings", null);
    }

    public void setCursorName(String name) throws SQLException {
        logger.entering(DefaultStatementWrapper.class.getName(),"setCursorName");
        fireFunctionCall("pre-setCursorName", null, name);
        statement.setCursorName(name);
        fireFunctionCall("setCursorName", null, name);
    }

    public boolean execute(String sql) throws SQLException {
        logger.entering(DefaultStatementWrapper.class.getName(),"execute");
        this.sql = sql;
        fireFunctionCall("pre-execute", null, sql);
        boolean b = statement.execute(sql);
        fireFunctionCall("execute", b, sql);
        return b;
    }

    public ResultSet getResultSet() throws SQLException {
        logger.entering(DefaultStatementWrapper.class.getName(),"getResultSet");
        fireFunctionCall("pre-getResultSet", null);
        ResultSet resultSet = wrap(statement.getResultSet());
        fireFunctionCall("getResultSet", resultSet);
        return resultSet;
    }

    public int getUpdateCount() throws SQLException {
        logger.entering(DefaultStatementWrapper.class.getName(),"getUpdateCount");
        return statement.getUpdateCount();
    }

    public boolean getMoreResults() throws SQLException {
        logger.entering(DefaultStatementWrapper.class.getName(),"getMoreResults");
        return statement.getMoreResults();
    }

    public void setFetchDirection(int direction) throws SQLException {
        logger.entering(DefaultStatementWrapper.class.getName(),"setFetchDirection");
        fireFunctionCall("pre-setFetchDirection", null, direction);
        statement.setFetchDirection(direction);
        fireFunctionCall("setFetchDirection", null, direction);
    }

    public int getFetchDirection() throws SQLException {
        logger.entering(DefaultStatementWrapper.class.getName(),"getFetchDirection");
        return statement.getFetchDirection();
    }

    public void setFetchSize(int rows) throws SQLException {
        logger.entering(DefaultStatementWrapper.class.getName(),"setFetchSize");
        fireFunctionCall("pre-setFetchSize", null, rows);
        statement.setFetchSize(rows);
        fireFunctionCall("setFetchSize", null, rows);
    }

    public int getFetchSize() throws SQLException {
        logger.entering(DefaultStatementWrapper.class.getName(),"getFetchSize");
        return statement.getFetchSize();
    }

    public int getResultSetConcurrency() throws SQLException {
        logger.entering(DefaultStatementWrapper.class.getName(),"getResultSetConcurrency");
        return statement.getResultSetConcurrency();
    }

    public int getResultSetType() throws SQLException {
        logger.entering(DefaultStatementWrapper.class.getName(),"getResultSetType");
        return statement.getResultSetType();
    }

    public void addBatch(String sql) throws SQLException {
        logger.entering(DefaultStatementWrapper.class.getName(),"addBatch");
        if (sqlBatch == null) {
            sqlBatch = new ArrayList<String>();
        }
        sqlBatch.add(sql);
        fireFunctionCall("pre-addBatch", null, sql, sqlBatch);
        statement.addBatch(sql);
        fireFunctionCall("addBatch", null, sql, sqlBatch);
    }

    public void clearBatch() throws SQLException {
        logger.entering(DefaultStatementWrapper.class.getName(),"clearBatch");
        fireFunctionCall("pre-clearBatch", null, sqlBatch);
        sqlBatch = null;
        statement.clearBatch();
        fireFunctionCall("clearBatch", null, sqlBatch);
    }

    public int[] executeBatch() throws SQLException {
        logger.entering(DefaultStatementWrapper.class.getName(),"executeBatch");
        fireFunctionCall("pre-executeBatch", null);
        int[] ints = statement.executeBatch();
        fireFunctionCall("executeBatch", ints);
        return ints;
    }

    public Connection getConnection() throws SQLException {
        logger.entering(DefaultStatementWrapper.class.getName(),"getConnection");
        return getConnectionWrapper();
    }

    public boolean getMoreResults(int current) throws SQLException {
        logger.entering(DefaultStatementWrapper.class.getName(),"getMoreResults");
        return statement.getMoreResults(current);
    }

    public ResultSet getGeneratedKeys() throws SQLException {
        logger.entering(DefaultStatementWrapper.class.getName(),"getGeneratedKeys");
        fireFunctionCall("pre-getResultSet", null);
        ResultSet rs = wrap(statement.getGeneratedKeys());
        fireFunctionCall("getResultSet", rs);
        return rs;
    }

    public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
        logger.entering(DefaultStatementWrapper.class.getName(),"executeUpdate");
        fireFunctionCall("pre-executeUpdate", null, sql, autoGeneratedKeys);
        int c = statement.executeUpdate(sql, autoGeneratedKeys);
        fireFunctionCall("executeUpdate", c, sql, autoGeneratedKeys);
        return c;
    }

    public int executeUpdate(String sql, int columnIndexes[]) throws SQLException {
        logger.entering(DefaultStatementWrapper.class.getName(),"executeUpdate");
        fireFunctionCall("pre-executeUpdate", null, sql, columnIndexes);
        int c = statement.executeUpdate(sql, columnIndexes);
        fireFunctionCall("executeUpdate", null, c, sql, columnIndexes);
        return c;
    }

    public int executeUpdate(String sql, String columnNames[]) throws SQLException {
        logger.entering(DefaultStatementWrapper.class.getName(),"executeUpdate");
        fireFunctionCall("pre-executeUpdate", null, sql, columnNames);
        int c = statement.executeUpdate(sql, columnNames);
        fireFunctionCall("executeUpdate", null, c, sql, columnNames);
        return c;
    }

    public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
        logger.entering(DefaultStatementWrapper.class.getName(),"execute");
        this.sql = sql;
        fireFunctionCall("pre-execute", null, sql, autoGeneratedKeys);
        boolean c = statement.execute(sql, autoGeneratedKeys);
        fireFunctionCall("execute", null, c, sql, autoGeneratedKeys);
        return c;
    }

    public boolean execute(String sql, int columnIndexes[]) throws SQLException {
        logger.entering(DefaultStatementWrapper.class.getName(),"execute");
        this.sql = sql;
        fireFunctionCall("pre-execute", null, sql, columnIndexes);
        boolean c = statement.execute(sql, columnIndexes);
        fireFunctionCall("execute", null, c, sql, columnIndexes);
        return c;
    }

    public boolean execute(String sql, String columnNames[]) throws SQLException {
        logger.entering(DefaultStatementWrapper.class.getName(),"execute");
        this.sql = sql;
        fireFunctionCall("pre-execute", null, sql, columnNames);
        boolean c = statement.execute(sql, columnNames);
        fireFunctionCall("execute", null, c, sql, columnNames);
        return c;
    }

    public int getResultSetHoldability() throws SQLException {
        logger.entering(DefaultStatementWrapper.class.getName(),"getResultSetHoldability");
        return statement.getResultSetHoldability();
    }


    public boolean isClosed() throws SQLException {
        logger.entering(DefaultStatementWrapper.class.getName(),"isClosed");
        return statement.isClosed();
    }

    public void setPoolable(boolean poolable) throws SQLException {
        logger.entering(DefaultStatementWrapper.class.getName(),"setPoolable");
        statement.setPoolable(poolable);
    }

    public boolean isPoolable() throws SQLException {
        logger.entering(DefaultStatementWrapper.class.getName(),"isPoolable");
        return statement.isPoolable();
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        logger.entering(DefaultStatementWrapper.class.getName(),"unwrap");
        return statement.unwrap(iface);
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        logger.entering(DefaultStatementWrapper.class.getName(),"isWrapperFor");
        return statement.isWrapperFor(iface);
    }

    @Override
    public void closeOnCompletion() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isCloseOnCompletion() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
}
