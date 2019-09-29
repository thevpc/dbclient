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

package net.vpc.dbclient.plugin.system.sessionmanager.window;

import net.vpc.dbclient.api.DBCSession;
import net.vpc.dbclient.api.sessionmanager.DBCSessionView;
import net.vpc.swingext.PRSManager;
import net.vpc.swingext.DumbGridBagLayout;
import net.vpc.swingext.JListCardPanel;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 14 févr. 2006 01:51:55
 */
public class DBCDatabaseEditor extends JPanel {
    private DBCSession session;

    public DBCDatabaseEditor(DBCSession session) throws SQLException {
        super(new BorderLayout());
        this.session = session;

        add(createConstraintsSheet());
        PRSManager.update(this, session.getView());
    }

//    private void fillMethodInvocation(ArrayList<String> paramNames, ArrayList<Object> paramValues, Object target, String method, Object[] valuesMapperPairsList, Object... parameters){
//        HashMap<Object,Object> h=new HashMap<Object,Object>();
//        for (int i = 0; i < valuesMapperPairsList.length; i=i+2) {
//            Object o1 = valuesMapperPairsList[i];
//            Object o2 = valuesMapperPairsList[i+1];
//            h.put(o1,o2);
//            fillMethodInvocation(paramNames, paramValues, target, method, h, parameters);
//        }
//    }

    private void fillMethodInvocation(ArrayList<String> paramNames, ArrayList<Object> paramValues, Object target, String method, HashMap valuesMapper, Object... parameters) {
        Object value = "?";
        try {
            value = target.getClass().getMethod(method).invoke(target, parameters);
            if (valuesMapper != null && valuesMapper.containsKey(value)) {
                value = valuesMapper.get(value);
            }
        } catch (Throwable e) {
            session.getLogger(DBCDatabaseEditor.class.getName()).log(Level.SEVERE, "Cannot resolve method "+method, e);
        }
        paramNames.add("DatabaseEditor." + method + ".Label");
        paramValues.add(value);
    }

//    private void fillMethodInvocation(ArrayList<String> paramNames, ArrayList<Object> paramValues, Object target, String method, HashMap<Object,Object> valuesMapper, Object[][] parameters){
//        StringBuilder sb=new StringBuilder();
//        for (int i = 0; i < parameters.length; i++) {
//            Object[] parameter = parameters[i];
//            Object value ="?";
//            try {
//                value =target.getClass().getMethod(method).invoke(target,parameter);
//                if(valuesMapper!=null && valuesMapper.containsKey(value)){
//                    value=valuesMapper.get(value);
//                }
//            } catch (Throwable e) {
//                System.err.println(e);
//            }
//            if(sb.length()>0){
//                sb.append("|");
//            }
//            sb.append(i);
//        }
//        paramNames.add("DBCDatabaseEditor."+method+".Label");
//        paramValues.add(sb.toString());
//    }
//

    private JPanel createConstraintsSheet() throws SQLException {
        DatabaseMetaData md = session.getConnection().getMetaData();
        JListCardPanel p = new JListCardPanel();
        p.getSplitPane().getLeftComponent().setMinimumSize(new Dimension(150, 100));
        ArrayList<String> paramNames;
        ArrayList<Object> paramValues;
        HashMap noMapper = null;

        HashMap<Object, Object> transactionIsolationMap = new HashMap<Object, Object>();
        transactionIsolationMap.put(Connection.TRANSACTION_NONE, "TRANSACTION_NONE [" + Connection.TRANSACTION_NONE + "]");
        transactionIsolationMap.put(Connection.TRANSACTION_READ_COMMITTED, "TRANSACTION_NONE [" + Connection.TRANSACTION_READ_COMMITTED + "]");
        transactionIsolationMap.put(Connection.TRANSACTION_READ_UNCOMMITTED, "TRANSACTION_NONE [" + Connection.TRANSACTION_READ_UNCOMMITTED + "]");
        transactionIsolationMap.put(Connection.TRANSACTION_REPEATABLE_READ, "TRANSACTION_NONE [" + Connection.TRANSACTION_REPEATABLE_READ + "]");
        transactionIsolationMap.put(Connection.TRANSACTION_SERIALIZABLE, "TRANSACTION_SERIALIZABLE [" + Connection.TRANSACTION_SERIALIZABLE + "]");

        HashMap<Object, Object> resultSetHoldabilityMap = new HashMap<Object, Object>();
        resultSetHoldabilityMap.put(ResultSet.HOLD_CURSORS_OVER_COMMIT, "TRANSACTION_NONE [" + ResultSet.HOLD_CURSORS_OVER_COMMIT + "]");
        resultSetHoldabilityMap.put(ResultSet.CLOSE_CURSORS_AT_COMMIT, "CLOSE_CURSORS_AT_COMMIT [" + ResultSet.CLOSE_CURSORS_AT_COMMIT + "]");

        HashMap<Object, Object> sQLStateTypeMap = new HashMap<Object, Object>();
        sQLStateTypeMap.put(DatabaseMetaData.sqlStateSQL99, "sqlStateSQL99 [" + DatabaseMetaData.sqlStateSQL99 + "]");
        sQLStateTypeMap.put(DatabaseMetaData.sqlStateXOpen, "sqlStateXOpen [" + DatabaseMetaData.sqlStateXOpen + "]");

        HashMap<Object, Object> resulSetTypeMap = new HashMap<Object, Object>();
        resulSetTypeMap.put(ResultSet.TYPE_FORWARD_ONLY, "FORWARD_ONLY [" + ResultSet.TYPE_FORWARD_ONLY + "]");
        resulSetTypeMap.put(ResultSet.TYPE_SCROLL_INSENSITIVE, "TYPE_SCROLL_INSENSITIVE [" + ResultSet.TYPE_SCROLL_INSENSITIVE + "]");
        resulSetTypeMap.put(ResultSet.TYPE_SCROLL_SENSITIVE, "TYPE_SCROLL_SENSITIVE [" + ResultSet.TYPE_SCROLL_SENSITIVE + "]");

        Object[] resultSetTypes = new Object[][]{{ResultSet.TYPE_FORWARD_ONLY}, {ResultSet.TYPE_SCROLL_INSENSITIVE}, {ResultSet.TYPE_SCROLL_SENSITIVE}};


        DBCSessionView sessionView = session.getView();
        p.addPage("General", sessionView.getMessageSet().get("DatabaseEditor.General.Label"), null, createGeneralSheet());


        paramNames = new ArrayList<String>();
        paramValues = new ArrayList<Object>();
        fillMethodInvocation(paramNames, paramValues, md, "getCatalogTerm", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "getMaxCatalogNameLength", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "getCatalogSeparator", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "isCatalogAtStart", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "supportsCatalogsInDataManipulation", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "supportsCatalogsInIndexDefinitions", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "supportsCatalogsInProcedureCalls", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "supportsCatalogsInTableDefinitions", noMapper);
        p.addPage("CatalogSupport", sessionView.getMessageSet().get("DatabaseEditor.CatalogSupport.Label"), null, new PropertiesPanel(paramNames, paramValues));


        paramNames = new ArrayList<String>();
        paramValues = new ArrayList<Object>();
        fillMethodInvocation(paramNames, paramValues, md, "getSchemaTerm", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "getMaxSchemaNameLength", noMapper);
        p.addPage("SchemaSupport", sessionView.getMessageSet().get("DatabaseEditor.SchemaSupport.Label"), null, new PropertiesPanel(paramNames, paramValues));

        paramNames = new ArrayList<String>();
        paramValues = new ArrayList<Object>();
        fillMethodInvocation(paramNames, paramValues, md, "allTablesAreSelectable", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "getMaxTableNameLength", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "getMaxColumnsInTable", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "getMaxColumnNameLength", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "supportsAlterTableWithAddColumn", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "supportsAlterTableWithDropColumn", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "supportsNonNullableColumns", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "supportsIntegrityEnhancementFacility", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "supportsGetGeneratedKeys", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "supportsTableCorrelationNames", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "supportsDifferentTableCorrelationNames", noMapper);
        p.addPage("TableSupport", sessionView.getMessageSet().get("DatabaseEditor.TableSupport.Label"), null, new PropertiesPanel(paramNames, paramValues));

        paramNames = new ArrayList<String>();
        paramValues = new ArrayList<Object>();
        fillMethodInvocation(paramNames, paramValues, md, "getMaxIndexLength", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "getMaxColumnsInIndex", noMapper);
        p.addPage("IndexesSupport", sessionView.getMessageSet().get("DatabaseEditor.IndexesSupport.Label"), null, new PropertiesPanel(paramNames, paramValues));

        paramNames = new ArrayList<String>();
        paramValues = new ArrayList<Object>();
        fillMethodInvocation(paramNames, paramValues, md, "getProcedureTerm", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "getMaxProcedureNameLength", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "allProceduresAreCallable", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "supportsNamedParameters", noMapper);
        p.addPage("Procedures", sessionView.getMessageSet().get("DatabaseEditor.Procedures.Label"), null, new PropertiesPanel(paramNames, paramValues));

        paramNames = new ArrayList<String>();
        paramValues = new ArrayList<Object>();
        fillMethodInvocation(paramNames, paramValues, md, "getSearchStringEscape", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "getMaxCharLiteralLength", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "getMaxBinaryLiteralLength", noMapper);
        p.addPage("Strings", sessionView.getMessageSet().get("DatabaseEditor.Strings.Label"), null, new PropertiesPanel(paramNames, paramValues));

        paramNames = new ArrayList<String>();
        paramValues = new ArrayList<Object>();
        fillMethodInvocation(paramNames, paramValues, md, "getIdentifierQuoteString", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "getExtraNameCharacters", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "storesLowerCaseIdentifiers", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "storesLowerCaseQuotedIdentifiers", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "storesMixedCaseIdentifiers", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "storesMixedCaseQuotedIdentifiers", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "storesUpperCaseIdentifiers", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "storesUpperCaseQuotedIdentifiers", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "supportsMixedCaseIdentifiers", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "supportsMixedCaseQuotedIdentifiers", noMapper);
        p.addPage("IdentifiersSupport", sessionView.getMessageSet().get("DatabaseEditor.IdentifiersSupport.Label"), null, new PropertiesPanel(paramNames, paramValues));

        paramNames = new ArrayList<String>();
        paramValues = new ArrayList<Object>();
        fillMethodInvocation(paramNames, paramValues, md, "locatorsUpdateCopy", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "doesMaxRowSizeIncludeBlobs", noMapper);
        p.addPage("LOB", sessionView.getMessageSet().get("DatabaseEditor.LOB.Label"), null, new PropertiesPanel(paramNames, paramValues));

        paramNames = new ArrayList<String>();
        paramValues = new ArrayList<Object>();
        fillMethodInvocation(paramNames, paramValues, md, "nullPlusNonNullIsNull", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "nullsAreSortedAtEnd", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "nullsAreSortedAtStart", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "nullsAreSortedHigh", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "nullsAreSortedLow", noMapper);
        p.addPage("Nulls", sessionView.getMessageSet().get("DatabaseEditor.Nulls.Label"), null, new PropertiesPanel(paramNames, paramValues));


        paramNames = new ArrayList<String>();
        paramValues = new ArrayList<Object>();
        fillMethodInvocation(paramNames, paramValues, md, "supportsANSI92EntryLevelSQL", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "supportsANSI92FullSQL", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "supportsCoreSQLGrammar", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "supportsMinimumSQLGrammar", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "supportsExtendedSQLGrammar", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "supportsCorrelatedSubqueries", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "getSQLStateType", sQLStateTypeMap);
        p.addPage("SQLLevel", sessionView.getMessageSet().get("DatabaseEditor.SQLLevel.Label"), null, new PropertiesPanel(paramNames, paramValues));

        paramNames = new ArrayList<String>();
        paramValues = new ArrayList<Object>();
        fillMethodInvocation(paramNames, paramValues, md, "supportsLikeEscapeClause", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "supportsColumnAliasing", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "supportsConvert", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "getMaxStatementLength", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "getMaxTablesInSelect", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "getMaxColumnsInSelect", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "getMaxRowSize", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "supportsBatchUpdates", noMapper);
        p.addPage("SQL", sessionView.getMessageSet().get("DatabaseEditor.SQL.Label"), null, new PropertiesPanel(paramNames, paramValues));

        paramNames = new ArrayList<String>();
        paramValues = new ArrayList<Object>();
        fillMethodInvocation(paramNames, paramValues, md, "supportsUnion", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "supportsUnionAll", noMapper);
        p.addPage("SQLUnion", sessionView.getMessageSet().get("DatabaseEditor.SQLUnion.Label"), null, new PropertiesPanel(paramNames, paramValues));

        paramNames = new ArrayList<String>();
        paramValues = new ArrayList<Object>();
        fillMethodInvocation(paramNames, paramValues, md, "supportsOuterJoins", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "supportsLimitedOuterJoins", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "supportsFullOuterJoins", noMapper);
        p.addPage("SQLJoin", sessionView.getMessageSet().get("DatabaseEditor.SQLJoin.Label"), null, new PropertiesPanel(paramNames, paramValues));

        paramNames = new ArrayList<String>();
        paramValues = new ArrayList<Object>();
        fillMethodInvocation(paramNames, paramValues, md, "supportsGroupBy", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "supportsGroupByBeyondSelect", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "supportsGroupByUnrelated", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "getMaxColumnsInGroupBy", noMapper);
        p.addPage("SQLGroupBy", sessionView.getMessageSet().get("DatabaseEditor.SQLGroupBy.Label"), null, new PropertiesPanel(paramNames, paramValues));

        paramNames = new ArrayList<String>();
        paramValues = new ArrayList<Object>();
        fillMethodInvocation(paramNames, paramValues, md, "supportsOrderByUnrelated", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "supportsExpressionsInOrderBy", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "getMaxColumnsInOrderBy", noMapper);
        p.addPage("SQLOrderBy", sessionView.getMessageSet().get("DatabaseEditor.SQLOrderBy.Label"), null, new PropertiesPanel(paramNames, paramValues));

        //md.deletesAreDetected();

//        md.getSQLStateType();
//        md.supportsConvert(1,2);
//        //md.supportsTransactionIsolationLevel();

        paramNames = new ArrayList<String>();
        paramValues = new ArrayList<Object>();
        fillMethodInvocation(paramNames, paramValues, md, "supportsTransactions", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "supportsSavepoints", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "dataDefinitionCausesTransactionCommit", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "dataDefinitionIgnoredInTransactions", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "supportsDataDefinitionAndDataManipulationTransactions", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "supportsDataManipulationTransactionsOnly", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "supportsOpenCursorsAcrossCommit", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "supportsOpenCursorsAcrossRollback", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "supportsOpenStatementsAcrossCommit", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "supportsOpenStatementsAcrossRollback", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "supportsMultipleTransactions", noMapper);
        p.addPage("Transactions", sessionView.getMessageSet().get("DatabaseEditor.Transactions.Label"), null, new PropertiesPanel(paramNames, paramValues));

        paramNames = new ArrayList<String>();
        paramValues = new ArrayList<Object>();
        fillMethodInvocation(paramNames, paramValues, md, "getDefaultTransactionIsolation", transactionIsolationMap);
        fillMethodInvocation(paramNames, paramValues, md, "insertsAreDetected", resulSetTypeMap, resultSetTypes);
        fillMethodInvocation(paramNames, paramValues, md, "updatesAreDetected", resulSetTypeMap, resultSetTypes);
        fillMethodInvocation(paramNames, paramValues, md, "deletesAreDetected", resulSetTypeMap, resultSetTypes);
        fillMethodInvocation(paramNames, paramValues, md, "ownInsertsAreVisible", resulSetTypeMap, resultSetTypes);
        fillMethodInvocation(paramNames, paramValues, md, "ownUpdatesAreVisible", resulSetTypeMap, resultSetTypes);
        fillMethodInvocation(paramNames, paramValues, md, "ownDeletesAreVisible", resulSetTypeMap, resultSetTypes);
        fillMethodInvocation(paramNames, paramValues, md, "othersInsertsAreVisible", resulSetTypeMap, resultSetTypes);
        fillMethodInvocation(paramNames, paramValues, md, "othersUpdatesAreVisible", resulSetTypeMap, resultSetTypes);
        fillMethodInvocation(paramNames, paramValues, md, "othersDeletesAreVisible", resulSetTypeMap, resultSetTypes);
        p.addPage("Isolation", sessionView.getMessageSet().get("DatabaseEditor.Isolation.Label"), null, new PropertiesPanel(paramNames, paramValues));

        paramNames = new ArrayList<String>();
        paramValues = new ArrayList<Object>();
        fillMethodInvocation(paramNames, paramValues, md, "getMaxCursorNameLength", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "getResultSetHoldability", resultSetHoldabilityMap);
        fillMethodInvocation(paramNames, paramValues, md, "supportsResultSetType", resulSetTypeMap, resultSetTypes);
        fillMethodInvocation(paramNames, paramValues, md, "supportsMultipleOpenResults", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "supportsMultipleResultSets", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "supportsPositionedDelete", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "supportsPositionedUpdate", noMapper);
        p.addPage("CursorsSupport", sessionView.getMessageSet().get("DatabaseEditor.CursorsSupport.Label"), null, new PropertiesPanel(paramNames, paramValues));


        paramNames = new ArrayList<String>();
        paramValues = new ArrayList<Object>();
        fillMethodInvocation(paramNames, paramValues, md, "getMaxUserNameLength", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "usesLocalFilePerTable", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "usesLocalFiles", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "isReadOnly", noMapper);
        fillMethodInvocation(paramNames, paramValues, md, "getMaxConnections", noMapper);
        p.addPage("Files", sessionView.getMessageSet().get("DatabaseEditor.File.Label"), null, new PropertiesPanel(paramNames, paramValues));

        return p;

    }


    private class PropertiesPanel extends JPanel {
        public PropertiesPanel(ArrayList<String> names, ArrayList<Object> values) {
            this(names.toArray(new String[names.size()]), values.toArray(new Object[values.size()]));
        }

        public PropertiesPanel(String[] names, Object[] values) {
            super(new DumbGridBagLayout());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < names.length; i++) {
                sb.append("[^<-ai][^<bi-=]\n".replaceAll("i", String.valueOf(i)));
            }
            ((DumbGridBagLayout) getLayout())
                    .setPattern(sb.toString())
                    .setInsets(".*", new Insets(3, 3, 3, 3))
                    ;
            for (int i = 0; i < names.length; i++) {
                add(PRSManager.createLabel(names[i]), "a" + i);
                if (values[i] instanceof Boolean) {
                    JCheckBox check = PRSManager.createCheck("DatabaseEditor.CheckBox", (Boolean) (values[i]));
                    check.setEnabled(false);
                    add(check, "b" + i);
                } else if (values[i] instanceof Component) {
                    add((Component) values[i], "b" + i);
                } else {
                    add(createText(String.valueOf(values[i])), "b" + i);
                }
            }
        }
    }

    private JPanel createGeneralSheet() throws SQLException {
        JPanel general = new JPanel(new DumbGridBagLayout(
                "[<-nameLabel][<-=nameText]\n" +
                        "[<-versionLabel  :             ]\n" +
                        "[<-versionText   :             ]\n" +
                        "[<-driverLabel][<-=driverText]\n" +
                        "[<-driverClassText :           ]\n" +
                        "[<-userLabel][<-=userText]\n" +
                        "[<-urlLabel:         ]\n" +
                        "[<-=urlText :         ]\n" +
                        "[<-cnxLabel :         ]\n" +
                        "[<-=cnxText :         ]\n" +
                        ""
        ).setInsets(".*", new Insets(3, 3, 3, 3))
        );
        general.add(PRSManager.createLabel("DatabaseEditor.nameLabel"), "nameLabel");
        general.add(PRSManager.createLabel("DatabaseEditor.versionLabel"), "versionLabel");
        general.add(PRSManager.createLabel("DatabaseEditor.driverLabel"), "driverLabel");
        general.add(PRSManager.createLabel("DatabaseEditor.userLabel"), "userLabel");
        general.add(PRSManager.createLabel("DatabaseEditor.urlLabel"), "urlLabel");
        general.add(PRSManager.createLabel("DatabaseEditor.cnxLabel"), "cnxLabel");
        DatabaseMetaData md = session.getConnection().getMetaData();
        String getDatabaseProductName = "?";
        String getDatabaseMajorVersion = "?";
        String getDatabaseProductVersion = "?";
        String getDriverName = "?";
        String getDriverMajorVersion = "?";
        String getUserName = "?";
        String getURL = "?";
        String getDatabaseMinorVersion = "?";
        String getDriverMinorVersion = "?";
        String driverClass = session.getConfig().getSessionInfo().getCnxDriver();
        try {
            getDatabaseProductName = md.getDatabaseProductName();
        } catch (Throwable e) {
            //e.printStackTrace();
        }
        try {
            getDatabaseMajorVersion = String.valueOf(md.getDatabaseMajorVersion());
        } catch (Throwable e) {
            //e.printStackTrace();
        }
        try {
            getDatabaseProductVersion = md.getDatabaseProductVersion();
        } catch (Throwable e) {
            //e.printStackTrace();
        }
        try {
            getDriverName = md.getDriverName();
        } catch (Throwable e) {
            //e.printStackTrace();
        }
        try {
            getDriverMajorVersion = String.valueOf(md.getDriverMajorVersion());
        } catch (Throwable e) {
            //e.printStackTrace();
        }
        try {
            getUserName = md.getUserName();
        } catch (Throwable e) {
            //e.printStackTrace();
        }
        try {
            getURL = md.getURL();
        } catch (Throwable e) {
            //e.printStackTrace();
        }

        try {
            getDatabaseMinorVersion = String.valueOf(md.getDatabaseMinorVersion());
        } catch (Throwable e) {
            //e.printStackTrace();
        }

        try {
            getDriverMinorVersion = String.valueOf(md.getDriverMinorVersion());
        } catch (Throwable e) {
            //e.printStackTrace();
        }

        general.add(createText(getDatabaseProductName + " [" + getDatabaseMajorVersion + "." + getDatabaseMinorVersion + "]"), "nameText");
        general.add(createText(getDatabaseProductVersion), "versionText");
        general.add(createText(getDriverName + "[" + getDriverMajorVersion + "." + getDriverMinorVersion + "]"), "driverText");
        general.add(createText(getUserName), "userText");
        general.add(createText(getURL), "urlText");
        general.add(createText(driverClass), "driverClassText");
        general.add(createText(session.getConnection().getClass().getName()), "cnxText");
        return general;
    }

    private JTextField createText(String v) {
        JTextField t = new JTextField(v);
        t.setEditable(false);
        t.setColumns(40);
        return t;
    }
}
