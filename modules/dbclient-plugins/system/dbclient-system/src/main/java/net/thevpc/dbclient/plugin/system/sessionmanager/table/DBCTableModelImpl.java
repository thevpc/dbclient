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
package net.thevpc.dbclient.plugin.system.sessionmanager.table;

import net.thevpc.common.swing.util.SwingsStringUtils;
import net.thevpc.dbclient.api.DBCSession;
import net.thevpc.common.prs.plugin.Initializer;
import net.thevpc.common.prs.plugin.Inject;
import net.thevpc.dbclient.api.sessionmanager.DBCResultTableWrapper;
import net.thevpc.dbclient.api.sessionmanager.DBCTableModel;
import net.thevpc.dbclient.api.sql.DefaultClassFilter;
import net.thevpc.dbclient.api.sql.SQLRecord;
import net.thevpc.dbclient.api.sql.TypeDesc;
import net.thevpc.dbclient.api.sql.TypeWrapperFactory;
import net.thevpc.dbclient.api.sql.objects.DBObject;
import net.thevpc.dbclient.api.sql.objects.DBTable;
import net.thevpc.dbclient.api.sql.objects.DBTableColumn;
import net.thevpc.dbclient.api.sql.parser.*;

import javax.swing.table.AbstractTableModel;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.StringReader;
import java.sql.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBCTableModelImpl extends AbstractTableModel implements DBCTableModel {
    //    DefaultConnectionWrapper connection;

    private boolean nullable[];
    private String columnNames[];
    private String defaultColumnNames[];
    @SuppressWarnings({"FieldCanBeLocal"})
    private TypeDesc columnClasses0[];
    private TypeDesc columnClasses[];
    private DBTableColumn tableColumnNodes[];
    private int columnSizes[];
    private int columnPrecisions[];
    private Vector<Vector<Object>> rows;
    private ResultSetMetaData metaData;
    private boolean editableTable;
    //    private String tableName;
    private DBTable tableObject;
    //    private String catalogName;
    //    private String schemaName;
    private SQLName[] mayBeTableName;
    private SQLName tableName;
    @Inject
    private DBCSession session;
    private Logger logger;
    private boolean moreRows = false;
    private String lastQuery = null;
    private DBCResultTableWrapper wrapper = new DBCResultTableWrapper();
    private String title;
    private PropertyChangeSupport propertyChangeSupport;

    public DBCTableModelImpl() {
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    @Initializer
    private void init() {
        columnNames = new String[0];
        defaultColumnNames = new String[0];
        columnClasses0 = new TypeDesc[0];
        columnClasses = new TypeDesc[0];
        rows = new Vector<Vector<Object>>();
        editableTable = false;
        logger = session.getLogger(DBCTableModelImpl.class.getName());
    }

    private void reset() {
        editableTable = false;
        tableObject = null;
        tableColumnNodes = null;
        tableName = null;
    }
//    public DBCTableModel(String url, String driverName, String user, String passwd) throws ClassNotFoundException, SQLException {
//        columnNames = new String[0];
//        columnClasses = new Class[0];
//        rows = new Vector();
//        editableTable = false;
//        Class.forName(driverName);
////        System.out.println("Opening db connection");
//        connection = Utils.createWrapper(DriverManager.getConnection(url, user, passwd));

    //    }
    public SQLRecord getRecord(int row) {
        Vector<Object> v = rows.get(row);
        SQLRecord rec = new SQLRecord();
        int max = v.size();
        for (int i = 0; i < max; i++) {
            rec.put(getColumnName(i), v.get(i));
        }
        return rec;
    }

    public Object[] getRow(int row) {
        Vector<Object> v = rows.get(row);
        return v.toArray(new Object[v.size()]);
    }

    public void close()
            throws SQLException {
//        System.out.println("Closing db connection");
//        connection.close();
    }

//    public String dbRepresentation(int column, Object value) {
//        if (value == null) {
//            return "null";
//        }
//        int type;
//        try {
//            type = metaData.getColumnType(column + 1);
//        } catch (SQLException e) {
//            return value.toString();
//        }
//        switch (type) {
//            case 4: // '\004'
//            case 6: // '\006'
//            case 8: // '\b'
//                return value.toString();
//
//            case -7:
//                return (Boolean) value ? "1" : "0";
//
//            case 91: // '['
//                return value.toString();
//        }
//        return "\"" + value.toString() + "\"";
//    }
//    public void displayMessage(String title, String value) throws SQLException {
//        reset();
//        TypeWrapperFactory twf = getPluginSession().getSession().getConnection().getTypeWrapperFactory();
//        columnNames = new String[]{title};
//        defaultColumnNames =new String[]{title};
//        columnClasses0 = (new TypeDesc[]{
//            twf.getTypeDesc(Types.VARCHAR)
//        });
//        columnClasses = columnClasses0;
//        nullable = new boolean[]{false};
//        columnSizes = new int[]{1024};
//        columnPrecisions = new int[]{10};
//        tableName = null;
////        catalogName = null;
////        schemaName = null;
//        rows = new Vector<Vector<Object>>();
//        Vector<Object> newRow = new Vector<Object>();
//        newRow.addElement(value);
//        rows.addElement(newRow);
//        fireTableStructureChanged();
//    }
    public synchronized void displayQuery(ResultSet resultSet, DBCResultTableWrapper w)
            throws SQLException {
        if (w == null) {
            w = wrapper;
        }
        reset();
        int start = 0;
        try {
            metaData = resultSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            columnNames = new String[numberOfColumns + start];
            defaultColumnNames = new String[numberOfColumns + start];
            columnClasses0 = new TypeDesc[numberOfColumns + start];
            columnClasses = new TypeDesc[numberOfColumns + start];
            columnSizes = new int[numberOfColumns + start];
            columnPrecisions = new int[numberOfColumns + start];
            nullable = new boolean[numberOfColumns + start];
//        Vector columnsDesc = new Vector();
            String tempTableName = null;
            String tempSchemaName = null;
            String tempCatalogName = null;
            for (int column = 0; column < numberOfColumns; column++) {
                try {
                    defaultColumnNames[start + column] = metaData.getColumnLabel(column + 1);
                    columnNames[start + column] = defaultColumnNames[start + column];
                } catch (Throwable e) {
                    logger.log(Level.SEVERE, "Failed to retieve ColumnLabel(" + (column + 1) + ")", e);
                }
                try {
                    columnSizes[start + column] = metaData.getColumnDisplaySize(column + 1);
                } catch (Throwable e) {
                    logger.log(Level.SEVERE, "Failed to retieve ColumnDisplaySize(" + (column + 1) + ")", e);
                }
                try {
                    columnPrecisions[start + column] = metaData.getPrecision(column + 1);
                } catch (Throwable e) {
                    logger.log(Level.SEVERE, "Failed to retieve ColumnPrecision(" + (column + 1) + ")", e);
                }
                try {
                    nullable[start + column] = metaData.isNullable(column + 1) != 0;
                } catch (Throwable e) {
                    logger.log(Level.SEVERE, "Failed to retieve ColumnNullable(" + (column + 1) + ")", e);
                }
                //            columnsDesc.add(column + "-" + Utils.getSqlTypeName(sqlTypes[column]));
                Class<?> defaultJavaClass = Object.class;
                try {
                    defaultJavaClass = Class.forName(metaData.getColumnClassName(column + 1));
                } catch (Throwable e) {
                    //
                }
                TypeWrapperFactory twf = getSession().getConnection().getTypeWrapperFactory();
                columnClasses0[start + column] = twf.getTypeDesc(metaData.getColumnType(column + 1), defaultJavaClass);
                columnClasses[start + column] = w.wrapColumnClass(columnClasses0[start + column], column + 1);

                if (tempTableName == null || tempTableName.length() == 0) {
                    tempTableName = metaData.getTableName(column + 1);
                }
                if (tempCatalogName == null || tempCatalogName.length() == 0) {
                    tempCatalogName = metaData.getCatalogName(column + 1);
                }
                if (tempSchemaName == null || tempSchemaName.length() == 0) {
                    tempSchemaName = metaData.getSchemaName(column + 1);
                }
            }
            if (tempTableName == null || tempTableName.length() == 0) {
                tableName = (mayBeTableName == null || mayBeTableName.length == 0) ? null : mayBeTableName[0];
            } else {
                tableName = new SQLName(new SQLToken[]{
                            new SQLToken(-1, SQLTokenGroup.CATALOG, tempCatalogName),
                            new SQLToken(-1, SQLTokenGroup.DOT, "."),
                            new SQLToken(-1, SQLTokenGroup.SCHEMA, tempSchemaName),
                            new SQLToken(-1, SQLTokenGroup.DOT, "."),
                            new SQLToken(-1, SQLTokenGroup.TABLE, tempTableName),});
            }
            rows = new Vector<Vector<Object>>();
            Vector<Object> newRow;
            int max = session.getConfig().getIntegerProperty("ui.result.rowsLimit", -1);//session.getSession().getConfig().getSessionInfo().getSesUiRowsLimit();
            
            //force some max row count even though not set explicitly 
            //because of memory issues
            if(max<=0){
                max=1000;
            }
            
            moreRows = false;
            int x = 0;
            while ((max <= 0 || x < max) && resultSet.next()) {
                newRow = new Vector<Object>();
                for (int i = 1; i <= numberOfColumns; i++) {
                    Object o = null;
                    try {
                        o = columnClasses0[(i - 1)].getWrapper().getObject(resultSet, i);
                    } catch (Throwable e) {
                        logger.log(Level.SEVERE, "Failed to retieve ColumnValue(" + (i) + ")", e);
                    }
                    newRow.addElement(w.wrapCellValue(o, x + 1, i));
                }
                rows.addElement(newRow);
                x++;
            }
            if (resultSet.next()) {
                moreRows = true;
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }
        fireTableStructureChanged();
    }

//    public void displayDBinfoForMethodIntrospection()
//            throws SQLException {
//        DatabaseMetaData meta = getSession().getConnection().getMetaData();
//        Method methods[] = DatabaseMetaData.class.getMethods();
//        Vector<String> columnNamesVector = new Vector<String>();
//        Vector<Class> columnClassesVector = new Vector<Class>();
//        rows = new Vector<Vector<Object>>();
//        Vector<Object> newRow = new Vector<Object>();
//        for (int i = 0; i < methods.length; i++)
//            if (methods[i].getParameterTypes().length == 0 && Modifier.isPublic(methods[i].getModifiers())) {
//                Class returnType = methods[i].getReturnType();
//                if (!returnType.equals(Void.TYPE) && (returnType.isPrimitive() || returnType.equals(String.class)))
//                    try {
//                        Object o = methods[i].invoke(meta, new Object[0]);
//                        newRow.add(o);
//                        columnNamesVector.add(methods[i].getActionName());
//                        columnClassesVector.add(o.getClass());
//                    } catch (Throwable ee) {
//                        getLog().getErr().println("problem at " + methods[i]);
//                        getLog().getErr().println(ee);
//                    }
//            }
//
//        rows.add(newRow);
//        columnNames = new String[columnNamesVector.size()];
//        columnClasses0 = new TypeDesc[columnClassesVector.size()];
//        columnClasses = new TypeDesc[columnClassesVector.size()];
//        nullable = new boolean[columnClasses.length];
//        columnSizes = new int[columnClasses.length];
//        columnPrecisions = new int[columnClasses.length];
//        tableName = null;
//        catalogName = null;
//        schemaName = null;
//        for (int i = 0; i < columnNames.length; i++) {
//            columnNames[i] = (String) columnNamesVector.get(i);
//            columnClasses[i] = TypeWrapperFactory.getTypeDesc(Types.VARCHAR, (Class) columnClassesVector.get(i));
//            columnSizes[i] = 1024;
//            columnSizes[i] = 10;
//        }
//
//        fireTableChanged(null);
//    }
//    public void displayDBinfo()
//            throws SQLException {
//        DatabaseMetaData meta = getSession().getConnection().getMetaData();
//        Method methods[] = DatabaseMetaData.class.getMethods();
//        columnNames = (new String[]{
//                "Name", "Type", "Litteral"
//        });
//        columnClasses = (new TypeDesc[]{
//                TypeWrapperFactory.getTypeDesc(Types.VARCHAR),
//                TypeWrapperFactory.getTypeDesc(Types.JAVA_OBJECT),
//                TypeWrapperFactory.getTypeDesc(Types.VARCHAR)
//        });
//        rows = new Vector<Vector<Object>>();
//        for (int i = 0; i < methods.length; i++)
//            if (methods[i].getParameterTypes().length == 0 && Modifier.isPublic(methods[i].getModifiers())) {
//                Class returnType = methods[i].getReturnType();
//                if (!returnType.equals(Void.TYPE) && (returnType.isPrimitive() || returnType.equals(String.class))) {
//                    Vector<Object> newRow = new Vector<Object>();
//                    try {
//                        newRow.add(methods[i].getActionName());
//                        newRow.add(returnType.toString());
//                        Object o = methods[i].invoke(meta, new Object[0]);
//                        newRow.add(o);
//                    } catch (InvocationTargetException ee) {
//                        newRow.add("(Invocation Exception) : " + ee.getTargetException());
//                    } catch (Throwable ee) {
//                        newRow.add("(Exception) : " + ee);
//                    }
//                    rows.add(newRow);
//                }
//            }
//
//        fireTableChanged(null);
    //    }
    public void displayError(String message) throws SQLException {
        logger.log(Level.SEVERE, message);
        displayMessage("Error", "<html><Font color=red><B>" + SwingsStringUtils.textToHtml(message) + "</B></Font></Html>");
    }

    public void displayError(SQLException message) throws SQLException {
        logger.log(Level.SEVERE, "Query failed", message);
        displayMessage("Error", "<html><Font color=red><B>" + SwingsStringUtils.textToHtml(message.getMessage()) + "</B></Font></Html>");
        throw message;
    }

    public void displayMessage(String title, String message) throws SQLException {
        editableTable = false;
        mayBeTableName = null;

        metaData = null;
        columnNames = new String[]{title};
        defaultColumnNames = new String[]{title};
        TypeWrapperFactory twf = getSession().getConnection().getTypeWrapperFactory();
        columnClasses0 = new TypeDesc[]{
            twf.getTypeDesc(Types.VARCHAR)
        };
        columnClasses = columnClasses0;
        columnSizes = new int[1];
        columnPrecisions = new int[1];
        nullable = new boolean[]{true};
        tableName = null;
//        catalogName = null;
//        schemaName = null;
        rows = new Vector<Vector<Object>>();
        Vector<Object> newRow = new Vector<Object>();
        newRow.add(message);
        rows.add(newRow);
        fireTableStructureChanged();

    }

    //TODO Deprecated
    public void executeStatement(String query) throws SQLException {
        Statement statement = getSession().getConnection().createStatement();
        SQLWarning warnings = getSession().getConnection().getWarnings();
        boolean isResultSet = statement.execute(query);
        while (warnings != null) {
            logger.log(Level.WARNING, "SQL-WARNINGS {0} : {1}", new Object[]{warnings.getSQLState(), warnings.getMessage()});
            warnings = warnings.getNextWarning();
        }
        getSession().getConnection().clearWarnings();
        if (isResultSet) {
            ResultSet rs = statement.getResultSet();
            displayQuery(rs, null);
            setQuery(query);
        } else {
            int count = statement.getUpdateCount();
            logger.log(Level.WARNING, "SQL-UPDATE-COUNT {0}", 1);
            displayMessage("Update Count", String.valueOf(count));
        }
    }

    public void executeQueryOld(String query) throws SQLException {
        setQuery(query);
        if (getSession().getConnection() == null) {
            displayError(new SQLException("There is no database to execute the query."));
        } else {
            try {
                editableTable = false;
                Statement statement = null;
                try {
                    statement = getSession().getConnection().createStatement();
                    SQLWarning warnings = getSession().getConnection().getWarnings();
                    ResultSet resultSet = statement.executeQuery(query);
                    while (warnings != null) {
                        logger.log(Level.WARNING, "SQL-WARNINGS {0} : {1}", new Object[]{warnings.getSQLState(), warnings.getMessage()});
                        warnings = warnings.getNextWarning();
                    }
                    getSession().getConnection().clearWarnings();
                    displayQuery(resultSet, null);
                } finally {
                    if (statement != null) {
                        statement.close();
                    }
                }
            } catch (SQLException ex) {
                displayError(ex);
                //throw ex;
            }
        }
    }

    private SQLName[] resolveTableNameFromQuery(String query) {
        SQLParser parser = null;
        try {
            parser = getSession().getConnection().createParser();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Unable to parse Query", e);
            return new SQLName[0];
        }
        parser.setDocument(query == null ? null : new StringReader(query));
        SQLStatement sqlStatement = parser.readStatement();
        SQLDeclaration[] tables = sqlStatement.getQueryTables();
        SQLName[] names = new SQLName[tables.length];
        for (int i = 0; i < names.length; i++) {
            names[i] = new SQLName(tables[i].getType());
        }
        return names;
    }

    @Override
    protected void finalize()
            throws Throwable {
        close();
        super.finalize();
    }

    @Override
    public Class getColumnClass(int column) {
        if (columnClasses[column] != null) {
            return columnClasses[column].getJavaType();
        } else {
            return Object.class;
        }
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public String getDefaultColumnName(int column) {
        return defaultColumnNames[column];
    }

    @Override
    public String getColumnName(int column) {
        if (columnNames[column] != null) {
            return columnNames[column];
        } else {
            return "";
        }
    }

    public void setColumnName(int column, String value) {
        columnNames[column] = value;
    }

//    public DefaultConnectionWrapper getConnection() {
//        return connection;
//    }
    //
    public int getRowCount() {
        return rows.size();
    }

    public Object getValueAt(int aRow, int aColumn) {
        Vector row = rows.elementAt(aRow);
        return row.elementAt(aColumn);
    }

    public TypeDesc getTypeDesc(int aColumn) {
        return columnClasses[aColumn];
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return editableTable;
    }

    @Override
    public void setValueAt(Object obj, int i, int j) {
    }

    //TODO to remove
    private Vector splitUpdateQueries(String query) {
        Vector<Object> v = new Vector<Object>();
        StringBuffer curentStatement = new StringBuffer();
        String s;
        for (int current_i = 0; current_i < query.length();) {
            if (query.charAt(current_i) == '\'') {
                curentStatement.append(query.charAt(current_i));
                for (current_i++; current_i < query.length(); current_i++) {
                    curentStatement.append(query.charAt(current_i));
                    if (query.charAt(current_i) == '\'') {
                        break;
                    }
                    if (query.charAt(current_i) == '\\') {
                        current_i++;
                        curentStatement.append(query.charAt(current_i));
                    }
                }

                current_i++;
            } else {
                if (query.charAt(current_i) == ';') {
                    s = curentStatement.toString().trim();
                    if (s.length() > 0) {
                        v.add(s);
                    }
                    curentStatement.delete(0, curentStatement.length());
                } else {
                    curentStatement.append(query.charAt(current_i));
                }
                current_i++;
            }
        }

        s = curentStatement.toString().trim();
        if (s.length() > 0) {
            v.add(s);
        }
        return v;
    }

    public boolean[] getNullable() {
        return nullable;
    }

    public String[] getColumnNames() {
        return columnNames;
    }

//    public TypeDesc[] getColumnClasses() {
//        return columnClasses;
    //    }
    public int[] getColumnSizes() {
        return columnSizes;
    }

    public int[] getColumnPrecisions() {
        return columnPrecisions;
    }

//    public String getTableName() {
//        return tableName;
//    }
//
//    public String getCatalogName() {
//        return catalogName;
//    }
//
//    public String getSchemaName() {
//        return schemaName;
    //    }
    public DBTableColumn getFieldNode(int i) throws SQLException {
        if (tableColumnNodes == null) {
            tableColumnNodes = new DBTableColumn[columnClasses.length];
        }
        if (tableColumnNodes[i] == null) {
            DBTable tableObject = getTableNode();
            if (tableObject != null) {
                DBTableColumn[] fields = tableObject.getColumnsFolder().getColumns();
                for (DBTableColumn tableColumn : fields) {
                    if (tableColumn.getName().equalsIgnoreCase(defaultColumnNames[i])) {
                        tableColumnNodes[i] = tableColumn;
                        break;
                    }
                }
            }
        }
        return tableColumnNodes[i];
    }

    public DBTable getTableNode() throws SQLException {
        if (tableObject == null && tableName != null) {
            DBObject[] dbObjects = getSession().getConnection().find(null, null, null, tableName.toSQL(), null, new DefaultClassFilter(DBTable.class), null);
            tableObject = dbObjects.length == 0 ? null : (DBTable) dbObjects[0];
        }
        return tableObject;
    }

    public DBCSession getSession() {
        return session;
    }

    public String getQuery() {
        return lastQuery;
    }

    public void setQuery(String lastQuery) {
        String old = this.lastQuery;
        this.lastQuery = lastQuery;
        mayBeTableName = resolveTableNameFromQuery(lastQuery);
        propertyChangeSupport.firePropertyChange("lastQuery", old, lastQuery);
    }

    public DBCResultTableWrapper getWrapper() {
        return wrapper;
    }

    public void setWrapper(DBCResultTableWrapper wrapper) {
        DBCResultTableWrapper old = this.wrapper;
        this.wrapper = wrapper;
        propertyChangeSupport.firePropertyChange("resultSetWrapper", old, wrapper);
    }

    public boolean isMoreRows() {
        return moreRows;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        String old = this.title;
        this.title = title;
        propertyChangeSupport.firePropertyChange("title", old, title);
    }

    public boolean reexecuteQuery() throws SQLException {
        String lQuery = getQuery();
        if (lQuery != null) {
            executeStatement(lQuery);
            return true;
        }
        return false;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public synchronized void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
    }

    public synchronized void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    public synchronized void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
    }
}
