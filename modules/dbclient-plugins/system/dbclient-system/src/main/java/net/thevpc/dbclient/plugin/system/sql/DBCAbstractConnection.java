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

package net.thevpc.dbclient.plugin.system.sql;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.sql.*;
import java.util.*;

import net.thevpc.common.swing.util.SwingPrivateIOUtils;
import net.thevpc.common.swing.util.SwingsStringUtils;
import net.thevpc.dbclient.api.DBCSession;
import net.thevpc.dbclient.api.pluginmanager.DBCFactory;
import net.thevpc.dbclient.api.sql.*;
import net.thevpc.dbclient.api.sql.features.ExecutionPlanFeature;
import net.thevpc.dbclient.api.sql.features.GenerateSQLDropFeature;
import net.thevpc.dbclient.api.sql.features.GenerateSQLRenameFeature;
import net.thevpc.dbclient.api.sql.format.SQLFormatter;
import net.thevpc.dbclient.api.sql.objects.*;
import net.thevpc.dbclient.api.sql.parser.SQLParser;
import net.thevpc.dbclient.api.sql.parser.SQLStatement;
import net.thevpc.dbclient.api.sql.util.SQLUtils;
import net.thevpc.dbclient.plugin.system.sql.features.DefaultFeatureGenerateSQLDrop;
import net.thevpc.common.prs.reflect.ClassFilter;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 19 juil. 2006 15:47:01
 */
public abstract class DBCAbstractConnection extends DefaultConnectionWrapper implements DBCConnection {
    public static final DBObjectFilter DEFAULT_OBJECT_FILTER = new DBObjectFilter() {
        public Status accept(DBObject object) {
            return DBObjectFilter.Status.ACCEPT;
        }
    };
    private static Comparator<DBObject> dbobjectNameComparator = new Comparator<DBObject>() {
        public int compare(DBObject o1, DBObject o2) {
            return o1.getName().compareTo(o2.getName());
        }
    };
    private DBCFactory factory;
    private DBObjectFilter objectFilter;

    protected DBDatatype[] datatypes;
    protected String[] operators;
    protected String[] separators;
    protected DBFunction[] allFunctions;
    protected DBFunction[] numericFunctions;
    protected DBFunction[] conversionFunctions;
    protected DBFunction[] stringFunctions;
    protected DBFunction[] timeDateFunctions;
    protected DBFunction[] systemFunctions;
    protected DBFunction[] aggregateFunctions;
    protected String[] keywords;
    private SQLParser defaultParser;
    private TypeWrapperFactory typeWrapperFactory;
    private boolean closable = true;
    private DBCObjectFinder finder;
    private Map<String,Object> properties;
    private DBCSession session;

    public DBCAbstractConnection() {
    }

    @Override
    public void init(DBCSession session) throws SQLException {
        this.session=session;
    }

    @Override
    public DBCSession getSession() {
        return session;
    }

    
    

    public TypeWrapperFactory getTypeWrapperFactory() {
        if (typeWrapperFactory == null) {
            typeWrapperFactory = new TypeWrapperFactory();
        }
        return typeWrapperFactory;
    }


    public boolean isSQLCloneObjectSupported(SQLObjectTypes objectType) throws SQLException {
        return false;
    }

    public String getSQLCloneObject(String catalogName, String schemaName, String parentName, String objectName, String newCatalogName, String newSchemaName, String newParentName, String newObjectName, SQLObjectTypes objectType, Properties extraProperties) throws SQLException {
        throw new IllegalArgumentException("Unsupported Clone");
    }

    public String getSQLConstraints(String catalogName, String schemaName, String parentName, String objectName, SQLObjectTypes objectType, Properties extraProperties) throws SQLException {
        if (
                SQLObjectTypes.TABLE.equals(objectType)

                ) {
            ResultSet rs = getMetaData().getPrimaryKeys(
                    catalogName,
                    schemaName,
                    objectName);
            Map<Integer, String> all = new HashMap<Integer, String>();
            String pkn = null;
            while (rs.next()) {
                String colName = rs.getString("COLUMN_NAME");
                int keySeq = rs.getInt("KEY_SEQ");
                if (pkn == null) {
                    pkn = rs.getString("PK_NAME");
                }
                all.put(keySeq, colName);
            }
            if (pkn == null) {
                pkn = "PK_" + objectName;
            }
            if (all.size() > 0) {
                StringBuilder sb = new StringBuilder(100);
                sb.append("ALTER TABLE ").append(objectName).append(" ADD CONSTRAINT ").append(pkn).append(" PRIMARY KEY (");
                for (int i = 0; i < all.size(); i++) {
                    if (i > 0) {
                        sb.append(", ");
                    }
                    sb.append(all.get(i + 1));
                }
                sb.append(")");
                return sb.toString();
            }

        }
        return null;
    }

//    public SQLBatchIterator iterateBatch(Reader in) throws IOException {
//        return new DefaultSQLBatchIterator(in, new String[]{";"}, null, null, null);
//    }
//
//    public SQLBatchIterator iterateBatch(String in) throws IOException {
//        return iterateBatch(new StringReader(in == null ? "" : in));
//    }

    public String getSQLLiteral(Object obj, int sqlType) {
        switch (sqlType) {
            case Types.DECIMAL:
            case Types.DOUBLE:
            case Types.FLOAT:
            case Types.REAL:
            case Types.BIGINT:
            case Types.TINYINT:
            case Types.SMALLINT:
            case Types.INTEGER:
            case Types.NUMERIC: {
                return obj == null ? "null" : obj.toString();

            }
            case Types.BOOLEAN:
            case Types.BIT: {
                return obj == null ? "null" : ((Boolean) obj) ? "1" : "0";
            }

            case Types.DATE: {
                return obj == null ? "null" :
                        "{d '" + (SQLUtils.DATE_FORMAT.format((java.util.Date) obj)).replace("'", "''") + "'}"
                        ;
            }
            case Types.TIME: {
                return obj == null ? "null" :
                        "{t '" + (SQLUtils.TIME_FORMAT.format((java.util.Date) obj)).replace("'", "''") + "'}"
                        ;
            }
            case Types.TIMESTAMP: {
                return obj == null ? "null" :
                        "{ts '" + (SQLUtils.TIMESTAMP_FORMAT.format((java.util.Date) obj)).replace("'", "''") + "'}"
                        ;
            }


            case Types.CHAR:
            case Types.VARCHAR:
            case Types.LONGVARCHAR: {
                return obj == null ? "null" :
                        "'" + (obj.toString()).replace("'", "''") + "'"
                        ;
            }

            case Types.VARBINARY:
            case Types.ARRAY:
            case Types.STRUCT:
            case Types.NULL:
            case Types.OTHER:
            case Types.REF:
            case Types.JAVA_OBJECT:
            case Types.LONGVARBINARY:
            case Types.BINARY:
            case Types.BLOB:
            case Types.CLOB:
            case Types.DISTINCT:
            case Types.DATALINK:
            default: {
                return "$UNSUPPORTED_TYPE$";
            }
        }

    }

//    public String getInsertCommand(String catalog, String schema, String table, String[] fields, int[] sqlTypes, Object[] values) throws SQLException {
//        StringBuilder q = new StringBuilder("Insert Into ").append(getFullName(catalog, schema, table)).append(" (");
//        for (int i = 0; i < fields.length; i++) {
//            if (i > 0) {
//                q.append(" , ");
//            }
//            q.append(fields[i]);
//        }
//        q.append(") values (");
//        for (int i = 0; i < fields.length; i++) {
//            if (i > 0) {
//                q.append(" , ");
//            }
//            q.append(" ").append(
//                    getSQLLiteral(values[i], sqlTypes[i])
//            ).append("");
//        }
//
//        q.append(");");
//
//        return q.toString();
//    }

//    public String getFullName(String catalogName, String schemaName, String objectName) {
//        StringBuilder sb = new StringBuilder();
//        if (catalogName != null && catalogName.length() > 0) {
//            sb.append(catalogName).append(".");
//        }
//        if (schemaName != null && schemaName.length() > 0) {
//            sb.append(schemaName).append(".");
//        }
//        return sb.toString() + objectName;
//    }

    public String getSQLCreateObject(String catalog, String schema, String parentName, String objectName, SQLObjectTypes objectType, Properties extraProperties) throws SQLException {
        switch (objectType) {
            case VIEW: {
                return getSQLCreateView(catalog, schema, objectName);
            }
            case TABLE: {
                return getSQLCreateTable(catalog, schema, objectName);
            }
            case PROCEDURE: {
                return getSQLCreateProcedure(catalog, schema, objectName);
            }
            case TRIGGER: {
                return getSQLCreateTrigger(catalog, schema, parentName, objectName);
            }
            case TABLE_INDEX: {
                return getSQLCreateIndex(catalog, schema, parentName, objectName);
            }
        }
        return null;
    }

    public String getSQLCreateTable(String catalog, String schema, String objectName) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE ").append(objectName).append("(\n");
        ResultSet rs = getConnection().getMetaData().getColumns(catalog, schema, objectName, "%");
        boolean firstCol = true;
        while (rs.next()) {
            if (firstCol) {
                firstCol = false;
                sb.append("   ");
            } else {
                sb.append("\n  ,");
            }
            DBTableColumn item = this.getFactory().newInstance(DBTableColumn.class);
            item.init(this, rs);

            sb.append(item.getName()).append(" ").append(item.getNativeTypeName());
            int sqlType = item.getSqlType();
            if (sqlType == Types.VARBINARY || sqlType == Types.VARCHAR) {
                sb.append("(");
                sb.append(item.getSize());
                sb.append(")");
            } else if (sqlType == Types.DECIMAL || sqlType == Types.DOUBLE || sqlType == Types.NUMERIC || sqlType == Types.FLOAT) {
                sb.append("(");
                sb.append(item.getSize());
                sb.append(",");
                sb.append(item.getPrecision());
                sb.append(")");
            }
            if (!item.isNullable()) {
                sb.append(" NOT NULL");
            } else {
//                sb.append(" NULL");
            }
            boolean commentStarted = false;
            if (!item.getSqlTypeName().equalsIgnoreCase(item.getNativeTypeName())) {
                if (!commentStarted) {
                    commentStarted = true;
                    sb.append(" -- ");
                }
                sb.append(SQLUtils.getSqlTypeName(item.getSqlType(),getSession()));
                if (!SQLUtils.getSqlTypeName(item.getSqlType(),getSession()).equalsIgnoreCase(item.getSqlTypeName())) {
                    if (!commentStarted) {
                        commentStarted = true;
                        sb.append(" -- ");
                    }
                    sb.append(" / ");
                    sb.append(SQLUtils.getSqlTypeName(item.getSqlType(),getSession()));
                }
            }
            if (item.getPkSeq() >= 0) {
                if (!commentStarted) {
                    commentStarted = true;
                    sb.append(" -- ");
                }
                sb.append(" PRIMARY KEY [").append(item.getPkSeq()).append("]");
            }
            if (!item.isInsertable()) {
                if (!commentStarted) {
                    //commentStarted=true;
                    sb.append(" -- ");
                }
                sb.append(" AUTO INCREMENT");
            }
        }
        sb.append("\n");
        rs.close();
        sb.append(")");
        return sb.toString();
    }


    public boolean isSQLCreateObjectSupported(SQLObjectTypes objectType) throws SQLException {
        return SQLObjectTypes.TABLE.equals(objectType);
    }


    /**
     * return ResultSet (TRIGGER_NAME varchar)
     *
     * @param catalog            catalog name or null
     * @param schemaPattern      schema
     * @param tableNamePattern   table
     * @param triggerNamePattern @return list of triggers for catalog.schema.tableName
     * @param extraProperties
     */
    public ResultSet getTriggers(String catalog, String schemaPattern, String tableNamePattern, String triggerNamePattern, Properties extraProperties) throws SQLException {
        Map<String, String> p = new HashMap<String, String>();
        p.put("catalog", catalog == null ? "%" : catalog);
        p.put("schema", schemaPattern == null ? "%" : schemaPattern);
        p.put("table", tableNamePattern == null ? "%" : tableNamePattern);
        p.put("trigger", triggerNamePattern == null ? "%" : triggerNamePattern);
        try {
            return getSQLResourceValueAsResultSet("sql/getTriggers.sql", p);
        } catch (IOException e) {
            throw new SQLException("sql/getTriggers.sql not found");
        }
    }

    public ResultSet getIndexes(String catalog, String schemaPattern, String tableNamePattern, String indexNamePattern, Properties extraProperties) throws SQLException {
        Map<String, String> p = new HashMap<String, String>();
        p.put("catalog", catalog == null ? "%" : catalog);
        p.put("schema", schemaPattern == null ? "%" : schemaPattern);
        p.put("table", tableNamePattern == null ? "%" : tableNamePattern);
        p.put("index", indexNamePattern == null ? "%" : indexNamePattern);
        try {
            return getSQLResourceValueAsResultSet("sql/getIndexes.sql", p);
        } catch (IOException e) {
            throw new SQLException("sql/getIndexes.sql not found");
        }
    }

    public ResultSet getConstraints(String catalog, String schemaPattern, String tableNamePattern, String constraintNamePattern, Properties extraProperties) throws SQLException {
        Map<String, String> p = new HashMap<String, String>();
        p.put("catalog", catalog == null ? "%" : catalog);
        p.put("schema", schemaPattern == null ? "%" : schemaPattern);
        p.put("table", tableNamePattern == null ? "%" : tableNamePattern);
        p.put("constraint", constraintNamePattern == null ? "%" : constraintNamePattern);
        try {
            return getSQLResourceValueAsResultSet("sql/getConstraints.sql", p);
        } catch (IOException e) {
            throw new SQLException("sql/getConstraints.sql not found");
        }
    }

    public boolean isSystemType(String name) throws SQLException {
        String typeLower = name.toLowerCase();
        return (typeLower.contains("system") || typeLower.contains("temp"));
    }

    public SQLObjectTypes getTypeByNativeName(String name) throws SQLException {
        String typeLower = name.toLowerCase();
        if (typeLower.contains("table") || typeLower.contains("synonym") || typeLower.contains("alias")) {
            return SQLObjectTypes.TABLE;
        }
        if (typeLower.contains("view")) {
            return SQLObjectTypes.VIEW;
        }
        if (typeLower.contains("procedure")) {
            return SQLObjectTypes.PROCEDURE;
        }
        if (typeLower.contains("index")) {
            return SQLObjectTypes.TABLE_INDEX;
        }
        if (typeLower.contains("sequence")) {
            return SQLObjectTypes.SEQUENCE;
        }
        return SQLObjectTypes.UNKNOWN;
    }

    public SQLParser createParser() {
        if (defaultParser == null) {
            SQLParser parser = createDefaultParser();
            parser.configure(this);
            defaultParser = parser;
        }
        return defaultParser.clone();
    }

    public int[] executeScript(String sql) throws SQLException {
        if (sql == null) {
            return new int[0];
        }
        SQLParser parser = createParser();
        int[] all;
        Statement statement = null;
        try {
            statement = createStatement();
            parser.setDocument(new StringReader(sql));
            ArrayList<Integer> a = new ArrayList<Integer>();
            SQLStatement s;
            while ((s = parser.readStatement()) != null) {
                if (!s.isEmpty()) {
                    String sql1 = s.toSQL().trim();
                    int x = statement.executeUpdate(sql1);
                    a.add(x);
                }
            }
            statement.close();
            all = new int[a.size()];
            for (int i = 0; i < all.length; i++) {
                all[i] = a.get(i);
            }
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
        return all;
    }

    protected SQLParser createDefaultParser() {
        return getFactory().newInstance(SQLParser.class);
    }


    public String[] getSQLKeywords() throws SQLException {
        if (keywords == null) {
            keywords = loadSQLKeywords();
        }
        return keywords;
    }

    protected DBDatatype[] getDefaultDatatypes() {
        ArrayList<DBDatatype> all = new ArrayList<DBDatatype>();
        TreeSet<String> found = new TreeSet<String>();
        String sql = null;
        try {
            sql = getResourceString("sql/getDefaultDatatypes.sql");
        } catch (IOException e) {
//            e.printStackTrace();
        }
        if (sql == null || sql.trim().length() == 0) {
            sql = "*";
        }
        for (StringTokenizer tokenizer = new StringTokenizer(sql, " ,;\n\r\t"); tokenizer.hasMoreTokens();) {
            String s = tokenizer.nextToken();
            s = s.trim();
            if (s.equals("*")) {
                for (String s1 : SQLUtils.DEFAULT_DATATYPES) {
                    s1 = s1.trim();
                    if (s1.length() > 0) {
                        if (!found.contains(s1.toLowerCase())) {
                            found.add(s1.toLowerCase());
                            DBDatatype item = this.getFactory().newInstance(DBDatatype.class);
                            item.init(this, s1);
                            all.add(item);
                        }
                    }
                }
            } else if (s.length() > 0) {
                if (!found.contains(s.toLowerCase())) {
                    found.add(s.toLowerCase());
                    DBDatatype item = this.getFactory().newInstance(DBDatatype.class);
                    item.init(this, s);
                    all.add(item);
                }
            }
        }
        return all.toArray(new DBDatatype[all.size()]);
    }

    public DBDatatype[] getDatatypes() throws SQLException {
        if (datatypes == null) {
            datatypes = loadDatatypes();
        }
        return datatypes;
    }

    protected String[] loadOperators() throws SQLException {
        return SQLUtils.DEFAULT_OPERATORS;
    }

    public String[] getOperators() throws SQLException {
        if (operators == null) {
            operators = loadOperators();
        }
        return operators;
    }

    public String[] getSeparators() throws SQLException {
        if (separators == null) {
            separators = loadSeparators();
        }
        return separators;
    }

    protected String[] loadSeparators() throws SQLException {
        return SQLUtils.DEFAULT_SEPARATORS;
    }

    public DBFunction[] getAllFunctions() throws SQLException {
        if (allFunctions == null) {
            ArrayList<DBFunction> a = new ArrayList<DBFunction>();
            a.addAll(Arrays.asList(getNumericFunctions()));
            a.addAll(Arrays.asList(getStringFunctions()));
            a.addAll(Arrays.asList(getSystemFunctions()));
            a.addAll(Arrays.asList(getTimeDateFunctions()));
            a.addAll(Arrays.asList(getAggregateFunctions()));
            a.addAll(Arrays.asList(getConversionFunctions()));
            allFunctions = a.toArray(new DBFunction[a.size()]);
        }
        return allFunctions;
    }

    protected DBFunction[] loadNumericFunctions() throws SQLException {
        TreeSet<String> found = new TreeSet<String>();
        String sql = null;
        try {
            sql = getResourceString("sql/getNumericFunctions.sql");
        } catch (IOException e) {
            //e.printStackTrace();
        }
        if (sql == null || sql.trim().length() == 0) {
            sql = "*";
        }
        ArrayList<DBFunction> all = new ArrayList<DBFunction>();
        for (StringTokenizer tokenizer = new StringTokenizer(sql, " ,;\n\r\t"); tokenizer.hasMoreTokens();) {
            String s = tokenizer.nextToken();
            s = s.trim();
            if (s.equals("*")) {
                DatabaseMetaData databaseMetaData = getConnection().getMetaData();
                for (StringTokenizer stringTokenizer = new StringTokenizer(databaseMetaData.getNumericFunctions(), " \t,;"); stringTokenizer.hasMoreTokens();) {
                    DBFunction item = this.getFactory().newInstance(DBFunction.class);
                    item.init(this, stringTokenizer.nextToken());
                    if (!found.contains(item.getName().toLowerCase())) {
                        all.add(item);
                        found.add(item.getName().toLowerCase());
                    }
                }
            } else {
                DBFunction item = this.getFactory().newInstance(DBFunction.class);
                item.init(this, s);
                if (!found.contains(item.getName().toLowerCase())) {
                    all.add(item);
                    found.add(item.getName().toLowerCase());
                }
            }
        }
        Collections.sort(all, dbobjectNameComparator);
        return all.toArray(new DBFunction[all.size()]);
    }

    protected DBFunction[] loadConversionFunctions() throws SQLException {
        TreeSet<String> found = new TreeSet<String>();
        String sql = null;
        try {
            sql = getResourceString("sql/getConversionFunctions.sql");
        } catch (IOException e) {
            //e.printStackTrace();
        }
        if (sql == null || sql.trim().length() == 0) {
            sql = "*";
        }
        ArrayList<DBFunction> all = new ArrayList<DBFunction>();
        for (StringTokenizer tokenizer = new StringTokenizer(sql, " ,;\n\r\t"); tokenizer.hasMoreTokens();) {
            String s = tokenizer.nextToken();
            s = s.trim();
            if (s.equals("*")) {
                String defaultConversionFunctions = "";
                DatabaseMetaData databaseMetaData = getConnection().getMetaData();
                for (StringTokenizer stringTokenizer = new StringTokenizer(defaultConversionFunctions, " \t,;"); stringTokenizer.hasMoreTokens();) {
                    DBFunction item = this.getFactory().newInstance(DBFunction.class);
                    item.init(this, stringTokenizer.nextToken());
                    if (!found.contains(item.getName().toLowerCase())) {
                        all.add(item);
                        found.add(item.getName().toLowerCase());
                    }
                }
            } else {
                DBFunction item = this.getFactory().newInstance(DBFunction.class);
                item.init(this, s);
                if (!found.contains(item.getName().toLowerCase())) {
                    all.add(item);
                    found.add(item.getName().toLowerCase());
                }
            }
        }
        Collections.sort(all, dbobjectNameComparator);
        return all.toArray(new DBFunction[all.size()]);
    }

    public DBFunction[] getNumericFunctions() throws SQLException {
        if (numericFunctions == null) {
            numericFunctions = loadNumericFunctions();
        }
        return numericFunctions;
    }

    public DBFunction[] getConversionFunctions() throws SQLException {
        if (conversionFunctions == null) {
            conversionFunctions = loadConversionFunctions();
        }
        return conversionFunctions;
    }

    protected DBFunction[] loadAggregateFunctions() throws SQLException {
        TreeSet<String> found = new TreeSet<String>();
        String sql = null;
        try {
            sql = getResourceString("sql/getAggregateFunctions.sql");
        } catch (IOException e) {
            //e.printStackTrace();
        }
        if (sql == null || sql.trim().length() == 0) {
            sql = "*";
        }
        ArrayList<DBFunction> all = new ArrayList<DBFunction>();
        for (StringTokenizer tokenizer = new StringTokenizer(sql, " ,;\n\r\t"); tokenizer.hasMoreTokens();) {
            String s = tokenizer.nextToken();
            s = s.trim();
            if (s.equals("*")) {
                DatabaseMetaData databaseMetaData = getConnection().getMetaData();
                for (StringTokenizer stringTokenizer = new StringTokenizer("max,min,sum,count,avg", " \t,;"); stringTokenizer.hasMoreTokens();) {
                    DBFunction item = this.getFactory().newInstance(DBFunction.class);
                    item.init(this, stringTokenizer.nextToken());
                    if (!found.contains(item.getName().toLowerCase())) {
                        all.add(item);
                        found.add(item.getName().toLowerCase());
                    }
                }
            } else {
                DBFunction item = this.getFactory().newInstance(DBFunction.class);
                item.init(this, s);
                if (!found.contains(item.getName().toLowerCase())) {
                    all.add(item);
                    found.add(item.getName().toLowerCase());
                }
            }
        }
        Collections.sort(all, dbobjectNameComparator);
        return all.toArray(new DBFunction[all.size()]);
    }

    public DBFunction[] getAggregateFunctions() throws SQLException {
        if (aggregateFunctions == null) {
            aggregateFunctions = loadAggregateFunctions();
        }
        return aggregateFunctions;
    }

    protected DBFunction[] loadTimeDateFunctions() throws SQLException {
        TreeSet<String> found = new TreeSet<String>();
        String sql = null;
        try {
            sql = getResourceString("sql/getTimeDateFunctions.sql");
        } catch (IOException e) {
            //e.printStackTrace();
        }
        if (sql == null || sql.trim().length() == 0) {
            sql = "*";
        }
        ArrayList<DBFunction> all = new ArrayList<DBFunction>();
        for (StringTokenizer tokenizer = new StringTokenizer(sql, " ,;\n\r\t"); tokenizer.hasMoreTokens();) {
            String s = tokenizer.nextToken();
            s = s.trim();
            if (s.equals("*")) {
                DatabaseMetaData databaseMetaData = getConnection().getMetaData();
                for (StringTokenizer stringTokenizer = new StringTokenizer(databaseMetaData.getTimeDateFunctions(), " \t,;"); stringTokenizer.hasMoreTokens();) {
                    DBFunction item = this.getFactory().newInstance(DBFunction.class);
                    item.init(this, stringTokenizer.nextToken());
                    if (!found.contains(item.getName().toLowerCase())) {
                        all.add(item);
                        found.add(item.getName().toLowerCase());
                    }
                }
            } else {
                DBFunction item = this.getFactory().newInstance(DBFunction.class);
                item.init(this, s);
                if (!found.contains(item.getName().toLowerCase())) {
                    all.add(item);
                    found.add(item.getName().toLowerCase());
                }
            }
        }
        Collections.sort(all, dbobjectNameComparator);
        return all.toArray(new DBFunction[all.size()]);
    }

    public DBFunction[] getTimeDateFunctions() throws SQLException {
        if (timeDateFunctions == null) {
            timeDateFunctions = loadTimeDateFunctions();
        }
        return timeDateFunctions;
    }

    public DBFunction[] loadSystemFunctions() throws SQLException {
        TreeSet<String> found = new TreeSet<String>();
        String sql = null;
        try {
            sql = getResourceString("sql/getSystemFunctions.sql");
        } catch (IOException e) {
            //e.printStackTrace();
        }
        if (sql == null || sql.trim().length() == 0) {
            sql = "*";
        }
        ArrayList<DBFunction> all = new ArrayList<DBFunction>();
        for (StringTokenizer tokenizer = new StringTokenizer(sql, " ,;\n\r\t"); tokenizer.hasMoreTokens();) {
            String s = tokenizer.nextToken();
            s = s.trim();
            if (s.equals("*")) {
                DatabaseMetaData databaseMetaData = getConnection().getMetaData();
                for (StringTokenizer stringTokenizer = new StringTokenizer(databaseMetaData.getSystemFunctions(), " \t,;"); stringTokenizer.hasMoreTokens();) {
                    DBFunction item = this.getFactory().newInstance(DBFunction.class);
                    item.init(this, stringTokenizer.nextToken());
                    if (!found.contains(item.getName().toLowerCase())) {
                        all.add(item);
                        found.add(item.getName().toLowerCase());
                    }
                }
            } else {
                DBFunction item = this.getFactory().newInstance(DBFunction.class);
                item.init(this, s);
                if (!found.contains(item.getName().toLowerCase())) {
                    all.add(item);
                    found.add(item.getName().toLowerCase());
                }
            }
        }
        Collections.sort(all, dbobjectNameComparator);
        return all.toArray(new DBFunction[all.size()]);
    }

    public DBFunction[] getSystemFunctions() throws SQLException {
        if (systemFunctions == null) {
            systemFunctions = loadSystemFunctions();
        }
        return systemFunctions;
    }

    protected DBFunction[] loadStringFunctions() throws SQLException {
        TreeSet<String> found = new TreeSet<String>();
        String sql = null;
        try {
            sql = getResourceString("sql/getStringFunctions.sql");
        } catch (IOException e) {
            //e.printStackTrace();
        }
        if (sql == null || sql.trim().length() == 0) {
            sql = "*";
        }
        ArrayList<DBFunction> all = new ArrayList<DBFunction>();
        for (StringTokenizer tokenizer = new StringTokenizer(sql, " ,;\n\r\t"); tokenizer.hasMoreTokens();) {
            String s = tokenizer.nextToken();
            s = s.trim();
            if (s.equals("*")) {
                DatabaseMetaData databaseMetaData = getConnection().getMetaData();
                for (StringTokenizer stringTokenizer = new StringTokenizer(databaseMetaData.getStringFunctions(), " \t,;"); stringTokenizer.hasMoreTokens();) {
                    DBFunction item = this.getFactory().newInstance(DBFunction.class);
                    item.init(this, stringTokenizer.nextToken());
                    if (!found.contains(item.getName().toLowerCase())) {
                        all.add(item);
                        found.add(item.getName().toLowerCase());
                    }
                }
            } else {
                DBFunction item = this.getFactory().newInstance(DBFunction.class);
                item.init(this, s);
                if (!found.contains(item.getName().toLowerCase())) {
                    all.add(item);
                    found.add(item.getName().toLowerCase());
                }
            }
        }
        Collections.sort(all, dbobjectNameComparator);
        return all.toArray(new DBFunction[all.size()]);
    }

    public DBFunction[] getStringFunctions() throws SQLException {
        if (stringFunctions == null) {
            stringFunctions = loadStringFunctions();
        }
        return stringFunctions;
    }

    protected String[] loadSQLKeywords() throws SQLException {
        TreeSet<String> found = new TreeSet<String>();
        String sql = null;
        try {
            sql = getResourceString("sql/getSQLKeywords.sql");
        } catch (IOException e) {
            //e.printStackTrace();
        }
        if (sql == null || sql.trim().length() == 0) {
            sql = "*";
        }
        ArrayList<String> all = new ArrayList<String>();
        for (StringTokenizer tokenizer = new StringTokenizer(sql, " ,;\n\r\t"); tokenizer.hasMoreTokens();) {
            String s = tokenizer.nextToken();
            s = s.trim();
            if (s.equals("*")) {
                DatabaseMetaData databaseMetaData = getConnection().getMetaData();
                for (StringTokenizer stringTokenizer = new StringTokenizer(databaseMetaData.getSQLKeywords(), " \t,;"); stringTokenizer.hasMoreTokens();) {
                    String ss = stringTokenizer.nextToken();
                    if (!found.contains(ss.toLowerCase())) {
                        all.add(ss);
                        found.add(ss.toLowerCase());
                    }
                }
            } else {
                if (!found.contains(s.toLowerCase())) {
                    all.add(s);
                    found.add(s.toLowerCase());
                }
            }
        }
        Collections.sort(all);
        return all.toArray(new String[all.size()]);
    }

    protected String[] getDefaultKeywords() {
        ArrayList<String> all = new ArrayList<String>();
        TreeSet<String> found = new TreeSet<String>();
        String sql = null;
        try {
            sql = getResourceString("sql/getDefaultKeywords.sql");
        } catch (IOException e) {
            //
        }
        if (sql == null || sql.trim().length() == 0) {
            sql = "*";
        }
        for (StringTokenizer tokenizer = new StringTokenizer(sql, " ,;\n\r\t"); tokenizer.hasMoreTokens();) {
            String s = tokenizer.nextToken();
            s = s.trim();
            if (s.equals("*")) {
                for (String s1 : SQLUtils.DEFAULT_KEYWORDS) {
                    s1 = s1.trim();
                    if (s1.length() > 0) {
                        if (!found.contains(s1.toLowerCase())) {
                            found.add(s1.toLowerCase());
                            all.add(s1);
                        }
                    }
                }
            } else if (s.length() > 0) {
                if (!found.contains(s.toLowerCase())) {
                    found.add(s.toLowerCase());
                    all.add(s);
                }
            }
        }
        return all.toArray(new String[all.size()]);
    }


    public DBDatatype[] loadDatatypes() throws SQLException {
        DatabaseMetaData databaseMetaData = getConnection().getMetaData();
        ResultSet rs = null;
        ArrayList<DBDatatype> all = new ArrayList<DBDatatype>();
        try {
            rs = databaseMetaData.getTypeInfo();
            while (rs.next()) {
                String typeName = rs.getString(1);
                DBDatatype item = this.getFactory().newInstance(DBDatatype.class);
                item.init(this, typeName.toUpperCase());
                all.add(item);
            }
        } catch (Throwable e) {
            getSession().getLogger(DBCAbstractConnection.class.getName()).log(java.util.logging.Level.SEVERE, null, e);
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
        try {
            all.addAll(Arrays.asList(getDefaultDatatypes()));
        } catch (Throwable e) {
            getSession().getLogger(DBCAbstractConnection.class.getName()).log(java.util.logging.Level.SEVERE, null, e);
        }
        Collections.sort(all, dbobjectNameComparator);
        return all.toArray(new DBDatatype[all.size()]);
    }

    public String getDefaultSchema() throws SQLException {
        return getConnection().getMetaData().getUserName();
    }

    public String getDefaultCatalog() throws SQLException {
        return getConnection().getCatalog();
    }

    public boolean isValidConnection() {
        try {
            ResultSet set = getMetaData().getCatalogs();
            set.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }


    /**
     * TODO prendre en compte catalogName, schemaName et parentName etc.
     *
     * @param catalogName     catalog
     * @param schemaName      schema
     * @param parentName      parent
     * @param name            name
     * @param contextParents  contextParents
     * @param nodeClassFilter nodeClassFilter
     * @param findMonitor     findMonitor
     * @return objects that verifie those criteria
     * @throws SQLException
     */
    public DBObject[] find(String catalogName, String schemaName, String parentName, String name, DBObject[] contextParents, ClassFilter nodeClassFilter, FindMonitor findMonitor) throws SQLException {
        if (finder == null) {
            finder = createFinder();
        }
        return finder.find(catalogName, schemaName, parentName, name, contextParents, nodeClassFilter, findMonitor);
    }


    public DBCObjectFinder createFinder() throws SQLException {
        DBCObjectFinder f = getFactory().newInstance(DBCObjectFinder.class);
        f.init(this);
        return f;
    }

    public SQLFormatter createFormatter() {
        SQLFormatter f = getFactory().newInstance(SQLFormatter.class);
        f.setParser(createParser());
        return f;
    }

    public int executeInsertRecord(String catalog, String schema, String tableName, SQLRecord record) throws SQLException {
        DBObject[] nodes = this.find(catalog, schema, null, tableName, null, new DefaultClassFilter(DBTable.class), null);
        if (nodes.length == 0 || !(nodes[0] instanceof DBTable)) {
            return -1;
        }
        DBTable table = (DBTable) nodes[0];
        StringBuilder q = new StringBuilder("Insert Into ").append(SystemSQLUtils.getPreferredName(table, getDefaultSchema(), getDefaultCatalog())).append(" (");
        boolean first = true;
        for (Map.Entry<String, Object> entry : record.entrySet()) {
            DBTableColumn column = table.getColumnsFolder().getColumn(entry.getKey());
            if (column.isInsertable()) {
                if (first) {
                    first = false;
                } else {
                    q.append(" , ");
                }
                q.append(column.getName());
            }
        }
        q.append(") values (");
        first = true;
        for (Map.Entry<String, Object> entry : record.entrySet()) {
            DBTableColumn column = table.getColumnsFolder().getColumn(entry.getKey());
            if (column.isInsertable()) {
                if (first) {
                    first = false;
                } else {
                    q.append(" , ");
                }
                q.append(" ? ");
            }
        }

        q.append(")");

        PreparedStatement ps = prepareStatement(q.toString());
        int index = 1;
        for (Map.Entry<String, Object> entry : record.entrySet()) {
            DBTableColumn column = table.getColumnsFolder().getColumn(entry.getKey());
            if (column.isInsertable()) {
                index += SQLUtils.saveValue(ps, index, entry.getValue(), column.getSqlType(), column.getPrecision());
            }
        }
        return ps.executeUpdate();
    }

    public String getSQLInsertRecord(String catalog, String schema, String objectName, SQLRecord record, Properties extraProperties) throws SQLException {
        DBObject[] nodes = this.find(catalog, schema, null, objectName, null, new DefaultClassFilter(DBTable.class), null);
        if (nodes.length == 0 || !(nodes[0] instanceof DBTable)) {
            return null;
        }
        DBTable table = (DBTable) nodes[0];
        StringBuilder q = new StringBuilder("Insert Into ").append(SystemSQLUtils.getPreferredName(table, getDefaultSchema(), getDefaultCatalog())).append(" (");
        boolean first = true;
        for (Map.Entry<String, Object> entry : record.entrySet()) {
            DBTableColumn column = table.getColumnsFolder().getColumn(entry.getKey());
            if (column.isInsertable()) {
                if (first) {
                    first = false;
                } else {
                    q.append(" , ");
                }
                q.append(column.getName());
            }
        }
        q.append(") values (");
        first = true;
        for (Map.Entry<String, Object> entry : record.entrySet()) {
            DBTableColumn column = table.getColumnsFolder().getColumn(entry.getKey());
            if (column.isInsertable()) {
                if (first) {
                    first = false;
                } else {
                    q.append(" , ");
                }
                q.append(getSQLLiteral(entry.getValue(), column.getSqlType()));
            }
        }

        q.append(")");

        return q.toString();
    }

    public String getSQLUse(String catalogName, String schemaName) throws SQLException {
        return null;
    }

    public boolean isAutoIdentityColumn(String catalog, String schema, String table, String col, String sequence) throws SQLException {
        return false;
    }

    public Object getAutoIdentityValue(String catalog, String schema, String table, String column, String sequence) throws SQLException {
        Map<String, String> p = new HashMap<String, String>();
        p.put("catalog", catalog);
        p.put("schema", schema);
        p.put("table", table);
        p.put("column", column);
        p.put("sequence", sequence);
        try {
            return getSQLResourceValueAsObject("sql/getAutoIdentityValue.sql", p);
        } catch (IOException e) {
            throw new SQLException("sql/getAutoIdentityValue.sql not found");
        }
    }

    public AutoIdentityType getAutoIdentityType() throws SQLException {
        return AutoIdentityType.NONE;
    }

    public int executeUpdateRecord(String catalog, String schema, String tableName, SQLRecord record) throws SQLException {
        DBObject[] nodes = this.find(catalog, schema, null, tableName, null, new DefaultClassFilter(DBTable.class), null);
        if (nodes.length == 0 || !(nodes[0] instanceof DBTable)) {
            return -1;
        }
        DBTable table = (DBTable) nodes[0];
        StringBuilder q = new StringBuilder("Update ").append(SystemSQLUtils.getPreferredName(table, getDefaultSchema(), getDefaultCatalog())).append(" Set ");
        boolean first = true;
        for (Map.Entry<String, Object> entry : record.entrySet()) {
            DBTableColumn column = table.getColumnsFolder().getColumn(entry.getKey());
            if (column.isPk()) {
                continue;
            }
            if (first) {
                first = false;
            } else {
                q.append(" , ");
            }
            q.append(column.getName()).append(" = ? ");
        }
        q.append(" Where ");
        first = true;
        for (DBTableColumn tableColumnNode : table.getColumnsFolder().getPrimaryColumns()) {
            if (first) {
                first = false;
            } else {
                q.append(" and ");
            }
            q.append(tableColumnNode.getName()).append(" = ?");
        }
        if (first) {
            // no pk found
            throw new SQLException("Unable to update. No Primary key found");
        }
        PreparedStatement ps = prepareStatement(q.toString());
        int index = 1;
        for (Map.Entry<String, Object> entry : record.entrySet()) {
            DBTableColumn column = table.getColumnsFolder().getColumn(entry.getKey());
            if (column.isPk()) {
                continue;
            }
            index += SQLUtils.saveValue(ps, index, entry.getValue(), column.getSqlType(), column.getPrecision());
        }
        for (DBTableColumn column : table.getColumnsFolder().getPrimaryColumns()) {
            index += SQLUtils.saveValue(ps, index, record.get(column.getName()), column.getSqlType(), column.getPrecision());
        }
        return ps.executeUpdate();
    }

    public int executeDeleteRecord(String catalog, String schema, String tableName, SQLRecord record) throws SQLException {
        DBObject[] nodes = this.find(catalog, schema, null, tableName, null, new DefaultClassFilter(DBTable.class), null);
        if (nodes.length == 0 || !(nodes[0] instanceof DBTable)) {
            return -1;
        }
        DBTable table = (DBTable) nodes[0];
        StringBuilder q = new StringBuilder("Delete From ").append(SystemSQLUtils.getPreferredName(table, getDefaultSchema(), getDefaultCatalog())).append(" Where ");
        boolean first = true;
        for (DBTableColumn tableColumnNode : table.getColumnsFolder().getPrimaryColumns()) {
            if (first) {
                first = false;
            } else {
                q.append(" and ");

            }
            q.append(tableColumnNode.getName()).append(" = ?");
        }
        if (first) {
            // no pk found
            throw new SQLException("Unable to delete. No Primary key found");
        }
        PreparedStatement ps = prepareStatement(q.toString());
        int index = 1;
        for (DBTableColumn column : table.getColumnsFolder().getPrimaryColumns()) {
            index += SQLUtils.saveValue(ps, index, record.get(column.getName()), column.getSqlType(), column.getPrecision());
        }
        return ps.executeUpdate();
    }

    public String getSQLGoKeyword() {
        return null;
    }

    private String valueIdentifierQuoteString = null;

    public String getEscapedName(String name) {
        if (valueIdentifierQuoteString == null) {
            try {
                valueIdentifierQuoteString = getConnection().getMetaData().getIdentifierQuoteString();
            } catch (SQLException e) {
                valueIdentifierQuoteString = "*NONE*";
            }
        }
        if (valueIdentifierQuoteString != null && "*NONE*".equals(valueIdentifierQuoteString)) {
            return valueIdentifierQuoteString + name + valueIdentifierQuoteString;
        } else {
            return name;
        }
    }

    public DBObject getRootbject() throws SQLException {
        return null;
    }

    public Collection<DBObject> getObjectChildren(DBObject object) throws SQLException {
        return null;
    }

    public DBObjectFilter getObjectFilter() {
        return objectFilter == null ? DEFAULT_OBJECT_FILTER : objectFilter;
    }


    public void close() throws SQLException {
        if (isClosable()) {
            super.close();
        }
    }

    public void setObjectFilter(DBObjectFilter filter) {
        this.objectFilter = filter;
    }

    public void structureChanged() {
        support.firePropertyChange(DATABASE_STRUCTURE_CHANGED, false, true);
    }

    protected String getResourceString(String urlPath) throws IOException {
        URL resource = getClass().getResource(urlPath);
        if (resource == null) {
            return null;
        }
        return SwingPrivateIOUtils.loadStreamAsString(resource);
    }

    protected String getSQLResourceValueAsString(String urlPath, Map<String, String> params, String separator) throws SQLException, IOException {
        StringBuilder sb = new StringBuilder();
        ResultSet rs = null;
        try {
            rs = getSQLResourceValueAsResultSet(urlPath, params);
            if (rs == null) {
                return null;
            }
            while (rs.next()) {
                if (sb.length() > 0) {
                    sb.append(separator);
                }
                sb.append(rs.getString(1));
            }
            return sb.toString();
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
    }

    protected Object getSQLResourceValueAsObject(String urlPath, Map<String, String> params) throws SQLException, IOException {
        ResultSet rs = null;
        try {
            rs = getSQLResourceValueAsResultSet(urlPath, params);
            if (rs == null) {
                return null;
            }
            if (rs.next()) {
                return rs.getObject(1);
            }
            return null;
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
    }

    protected ResultSet getSQLResourceValueAsResultSet(String urlPath, Map<String, String> params) throws SQLException, IOException {
        String sql = getResourceString(urlPath);
        if (sql == null) {
            return null;
        }
        ArrayList<String> values = new ArrayList<String>();
        boolean again = true;
        while (again) {
            again = false;
            for (Map.Entry<String, String> s : params.entrySet()) {
                String k = "${" + s.getKey() + "}";
                int i = sql.indexOf(k);
                if (i >= 0) {
                    sql = SwingsStringUtils.substring(sql, 0, i) + s.getValue() + SwingsStringUtils.substring(sql, i + k.length(), sql.length());
                    again = true;
                }
            }
        }
        again = true;
        int pos = 1;
        int startIndex = 0;
        while (again) {
            again = false;
            int bestIndex = -1;
            String bestK = null;
            String bestV = null;
            for (Map.Entry<String, String> s : params.entrySet()) {
                String k = "${" + s.getKey() + "?}";
                int i = sql.indexOf(k, startIndex);
                if (i > 0 && (bestIndex < 0 || i < bestIndex)) {
                    bestK = k;
                    bestIndex = i;
                    bestV = s.getValue();
                }
            }
            if (bestIndex >= 0) {
                sql = SwingsStringUtils.substring(sql, 0, bestIndex) + "?" + SwingsStringUtils.substring(sql, bestIndex + bestK.length(), sql.length());
                values.add(bestV);
                pos++;
                startIndex++;
                again = true;
            }
        }
        PreparedStatement ps;
        ResultSet rs;
        ps = prepareStatement(sql);
        for (int i = 1; i < pos; i++) {
            ps.setString(i, values.get(i - 1));
        }
        rs = ps.executeQuery();
        return rs;
    }

    public DBCFactory getFactory() {
        return factory;
    }

    public void setFactory(DBCFactory componentManager) {
        this.factory = componentManager;
    }

    public String getSQLCreateView(String catalog, String schema, String objectName) throws SQLException {
        Map<String, String> p = new HashMap<String, String>();
        p.put("catalog", catalog);
        p.put("schema", schema);
        p.put("view", objectName);
        try {
            return getSQLResourceValueAsString("sql/getSQLCreateView.sql", p, "");
        } catch (IOException e) {
            throw new SQLException("sql/getSQLCreateView.sql not found");
        }
    }

    public String getSQLCreateUser(String username, String password, Properties properties) throws SQLException {
        throw new SQLException("Not supported");
    }

    public String getSQLCreateTrigger(String catalog, String schema, String table, String objectName) throws SQLException {
        Map<String, String> p = new HashMap<String, String>();
        p.put("catalog", catalog);
        p.put("schema", schema);
        p.put("table", table);
        p.put("trigger", objectName);
        try {
            return getSQLResourceValueAsString("sql/getSQLCreateTrigger.sql", p, "");
        } catch (IOException e) {
            throw new SQLException("sql/getSQLCreateTrigger.sql not found");
        }
    }

    public String getSQLCreateIndex(String catalog, String schema, String table, String index) throws SQLException {
        Map<String, String> p = new HashMap<String, String>();
        p.put("catalog", catalog);
        p.put("schema", schema);
        p.put("table", table);
        p.put("index", index);
        try {
            return getSQLResourceValueAsString("sql/getSQLCreateIndex.sql", p, "");
        } catch (IOException e) {
            throw new SQLException("sql/getSQLCreateIndex.sql not found");
        }
    }

    public String getSQLCreateProcedure(String catalog, String schema, String object) throws SQLException {
        Map<String, String> p = new HashMap<String, String>();
        p.put("catalog", catalog);
        p.put("schema", schema);
        p.put("procedure", object);
        try {
            return getSQLResourceValueAsString("sql/getSQLCreateProcedure.sql", p, "");
        } catch (IOException e) {
            throw new SQLException("sql/getSQLCreateProcedure.sql not found");
        }
    }


    public boolean isClosable() {
        return closable;
    }

    public void setClosable(boolean closable) {
        fireFunctionCall("pre-setClosable", null, closable);
        this.closable = closable;
        fireFunctionCall("setClosable", null, closable);
    }

    public ResultSet getPackages(String catalog, String schemaPattern, String namePattern, Properties extraProperties) throws SQLException {
        return new ListResultSet(null, null, new Object[][]{});
    }

    public ResultSet getQueues(String catalog, String schemaPattern, String namePattern, Properties extraProperties) throws SQLException {
        return new ListResultSet(null, null, new Object[][]{});
    }

    public ResultSet getClusters(String catalog, String schemaPattern, String namePattern, Properties extraProperties) throws SQLException {
        return new ListResultSet(null, null, new Object[][]{});
    }

    public ResultSet getJobs(String catalog, String schemaPattern, String namePattern, Properties extraProperties) throws SQLException {
        return new ListResultSet(null, null, new Object[][]{});
    }

    public boolean acceptObjectType(SQLObjectTypes type) {
        switch (type) {
            case PACKAGE:
            case QUEUE:
            case CLUSTER:
            case JOB: {
                return false;
            }
        }
        return true;
    }

    public ExecutionPlanFeature getFeatureExecutionPlan() throws SQLException {
        return ExecutionPlanFeature.UNSUPPORTED;
    }

    public GenerateSQLRenameFeature getFeatureGenerateSQLRename() throws SQLException {
        return GenerateSQLRenameFeature.UNSUPPORTED;
    }

    private GenerateSQLDropFeature generateSQLDropFeature = new DefaultFeatureGenerateSQLDrop();

    public GenerateSQLDropFeature getFeatureGenerateSQLDrop() throws SQLException {
        return generateSQLDropFeature;
    }

    @Override
    public Object getConnectionProperty(String value, Object defaultValue) {
        Object v=null;
        if(properties!=null){
            v=properties.get(value);
        }
        
        if(v==null){
            v=defaultValue;
        }
        return v;
    }

    @Override
    public void setConnectionProperty(String key, Object value) {
        if(value==null){
            if(properties!=null){
                properties.remove(key);
            }
        }else{
            if(properties==null){
                properties=new HashMap<String, Object>();
            }
            properties.put(key, value);
        }
    }

    
}
