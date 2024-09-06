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

package net.thevpc.dbclient.plugin.tool.searchengine;

import net.thevpc.dbclient.api.DBCSession;
import net.thevpc.dbclient.api.sql.DBCConnection;
import net.thevpc.dbclient.api.sql.objects.SQLObjectTypes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 2006/11/07
 */
public class SearchEngine {
    DBCSession session;
    Connection connection;
    SearchOptions options;
    SearchFilter filter;
    Vector<SearchListener> listeners;

    public SearchEngine(DBCSession session, Connection connection, SearchOptions options, SearchFilter filter) {
        this.session = session;
        this.options = options;
        this.connection = connection;
        this.filter = filter;
    }

    public List<SearchResult> search() throws SQLException {
        ArrayList<SearchResult> result = new ArrayList<SearchResult>();
        DBCConnection d = session.getConnection();
        if (options == null) {
            options = new SearchOptions();
        }
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        //TABLES, VIEWS
        if (options.isLookInDefinition()) {
            if (isSearchCancelled()) {
                return result;
            }
            addLog("info", "Searching in Table/View names...");
            ResultSet set = null;
            try {
                set = databaseMetaData.getTables(options.getCatalog(), options.getSchema(), options.getTable(), options.getTableTypes());
                search("{TABLE_CAT}.{TABLE_SCHEM}.{TABLE_NAME}", SQLObjectTypes.TABLE, "Definition", set, result);
            } finally {
                if (set != null) {
                    set.close();
                }
            }
            if (isSearchCancelled()) {
                return result;
            }
            addLog("info", "Searching in Table Column names...");
            try {
                set = databaseMetaData.getColumns(options.getCatalog(), options.getSchema(), options.getTable(), null);
                search("{TABLE_CAT}.{TABLE_SCHEM}.{TABLE_NAME}.{COLUMN_NAME}", SQLObjectTypes.TABLE_COLUMN, "Definition", set, result);
            } finally {
                if (set != null) {
                    set.close();
                }
            }
            if (isSearchCancelled()) {
                return result;
            }
            addLog("info", "Searching in UDT names...");
            try {
                set = databaseMetaData.getUDTs(options.getCatalog(), options.getSchema(), options.getTable(), null);
                search("{TYPE_CAT}.{TYPE_SCHEM}.{TYPE_NAME}", SQLObjectTypes.UDT, "Definition", set, result);
            } finally {
                if (set != null) {
                    set.close();
                }
            }
            if (isSearchCancelled()) {
                return result;
            }
            addLog("info", "Searching in Table/View definitions...");
            set = databaseMetaData.getTables(options.getCatalog(), options.getSchema(), options.getTable(), options.getTableTypes());
            int row = 0;
            while (set.next()) {
                String cat = set.getString("TABLE_CAT");
                String schem = set.getString("TABLE_SCHEM");
                String name = set.getString("TABLE_NAME");
                String type = set.getString("TABLE_TYPE");
                SQLObjectTypes sqlObjectTypes = d.getTypeByNativeName(type == null ? "" : type);
                if (session.getConnection().isSQLCreateObjectSupported(sqlObjectTypes)) {
                    String sqlCreate = session.getConnection().getSQLCreateObject(cat, schem, null, name, sqlObjectTypes, null);
                    if (filter.accept(sqlCreate, Types.VARCHAR)) {
                        SearchResult result1 = new SearchResult();
                        result1.setObjectName(SearchEngineUtils.buildName(d, cat, schem, name));
                        result1.setObjectType(sqlObjectTypes);
                        result1.setContext("Definition");
                        result1.setValue(sqlCreate);
                        result1.setColumnName("DDL");
                        result1.setRow(row);
                        fireItemFound(result1);
                        result.add(result1);
                    }
                }
                row++;
            }
            set.close();

            if (isSearchCancelled()) {
                return result;
            }
            addLog("info", "Searching in Procedure names...");
            set = null;
            try {
                set = databaseMetaData.getProcedures(options.getCatalog(), options.getSchema(), options.getProcedure());
                search("{PROCEDURE_CAT}.{PROCEDURE_SCHEM}.{PROCEDURE_NAME}", SQLObjectTypes.PROCEDURE, "Definition", set, result);
            } finally {
                if (set != null) {
                    set.close();
                }
            }

            if (isSearchCancelled()) {
                return result;
            }
            addLog("info", "Searching in Procedure Column names...");
            try {
                set = databaseMetaData.getProcedureColumns(options.getCatalog(), options.getSchema(), options.getProcedure(), null);
                search("{PROCEDURE_CAT}.{PROCEDURE_SCHEM}.{PROCEDURE_NAME}.{COLUMN_NAME}", SQLObjectTypes.PROCEDURE_COLUMN, "Definition", set, result);
            } finally {
                if (set != null) {
                    set.close();
                }
            }

            if (isSearchCancelled()) {
                return result;
            }
            try {
                addLog("info", "Searching in Procedures definition...");
                set = databaseMetaData.getProcedures(options.getCatalog(), options.getSchema(), options.getProcedure());
                row = 0;
                while (set.next()) {
                    if (isSearchCancelled()) {
                        return result;
                    }
                    String cat = set.getString("PROCEDURE_CAT");
                    String schem = set.getString("PROCEDURE_SCHEM");
                    String name = set.getString("PROCEDURE_NAME");
                    SQLObjectTypes type = SQLObjectTypes.PROCEDURE;
                    if (session.getConnection().isSQLCreateObjectSupported(type)) {
                        String sqlCreate = d.getSQLCreateObject(cat, schem, null, name, type, null);
                        if (filter.accept(sqlCreate, Types.VARCHAR)) {
                            SearchResult result1 = new SearchResult();
                            result1.setObjectName(SearchEngineUtils.buildName(cat, schem, name));
                            result1.setContext("Procedure DDL");
                            result1.setValue(sqlCreate);
                            result1.setObjectType(type);
                            result1.setColumnName("DDL");
                            result1.setRow(row);
                            fireItemFound(result1);
                            result.add(result1);
                        }
                    }
                    row++;
                }
            } finally {
                if (set != null) {
                    set.close();
                }
            }
        }
        if (options.isLookInData()) {
            if (isSearchCancelled()) {
                return result;
            }
            addLog("info", "Searching in Tables/Views content...");
            ResultSet set = null;
            try {
                set = databaseMetaData.getTables(options.getCatalog(), options.getSchema(), options.getTable(), options.getTableTypes());
                while (set.next()) {
                    if (isSearchCancelled()) {
                        return result;
                    }
                    String cat = set.getString("TABLE_CAT");
                    String schem = set.getString("TABLE_SCHEM");
                    String name = set.getString("TABLE_NAME");
//                    String type = set.getString("TABLE_TYPE");
                    Statement statement = null;
                    ResultSet rs = null;
                    try {
                        statement = connection.createStatement();
                        String sql = "Select * From " + SearchEngineUtils.buildName(d, cat, schem, name);
                        addLog("info", "Searching in Table \"" + SearchEngineUtils.buildName(cat, schem, name) + "\" content...");
                        rs = statement.executeQuery(sql);
                        search(SearchEngineUtils.buildName(cat, schem, name) + ".{*}", SQLObjectTypes.TABLE_COLUMN, "Content", rs, result);
                        rs.close();
                    } catch (Throwable e) {
                        addLog("error", "Unable to search into " + SearchEngineUtils.buildName(cat, schem, name) + " : " + e);
                    } finally {
                        if (rs != null) {
                            rs.close();
                        }
                        if (statement != null) {
                            statement.close();
                        }
                    }
                }
            } finally {
                if (set != null) {
                    set.close();
                }
            }
        }

        return result;
    }

    private void search(String namePattern, SQLObjectTypes objectType, String context, ResultSet rs, ArrayList<SearchResult> result) throws SQLException {
        ResultSetMetaData mdata = rs.getMetaData();
        int colCount = mdata.getColumnCount();
        int[] types = new int[colCount];
        String[] cnames = new String[colCount];
        ArrayList<Integer> colLabelIndex = new ArrayList<Integer>();
        for (int i = 0; i < types.length; i++) {
            types[i] = mdata.getColumnType(i + 1);
            cnames[i] = mdata.getColumnName(i + 1);
            if (namePattern.contains("{" + cnames[i] + "}")) {
                colLabelIndex.add(i);
            }
        }
        int row = 0;
        while (rs.next()) {
            if (isSearchCancelled()) {
                return;
            }
            Object[] values = new Object[colCount];
            for (int i = 1; i <= colCount; i++) {
                values[i - 1] = rs.getObject(i);
            }
            for (int i = 0; i < colCount; i++) {
                if (filter.accept(values[i], types[i])) {
                    SearchResult result1 = new SearchResult();
                    String n;
                    n = namePattern.replace("{*}", cnames[i]);
                    for (int x : colLabelIndex) {
                        n = n.replace("{" + cnames[x] + "}", String.valueOf(values[x]));
                    }
                    if (n.startsWith(".")) {
                        n = n.substring(1);
                    } else if (n.startsWith("null.")) {
                        n = n.substring(5);
                    }
                    result1.setObjectName(n);
                    result1.setObjectType(objectType);
                    result1.setContext(context);
                    result1.setRow(row);
                    result1.setValue(values[i]);
                    result1.setColumnName(cnames[i]);
                    fireItemFound(result1);
                    result.add(result1);
                }
            }

            row++;
        }
    }

    private void addLog(String type, String message) {
        if (listeners != null) {
            for (SearchListener listener : listeners) {
                listener.log(type, message);
            }
        }
    }

    private void fireItemFound(SearchResult result1) {
        if (listeners != null) {
            for (SearchListener listener : listeners) {
                listener.itemFound(result1);
            }
        }
//        System.out.println("\t\tFound = " + result1);
    }

    public void addSearchListener(SearchListener s) {
        if (listeners == null) {
            listeners = new Vector<SearchListener>();
        }
        listeners.add(s);
    }

    public boolean isSearchCancelled() {
        if (listeners != null) {
            for (SearchListener listener : listeners) {
                if (listener.isSearchCancelled()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void removeSearchListener(SearchListener s) {
        if (listeners != null) {
            listeners.remove(s);
        }
    }

}
