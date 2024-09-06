package net.thevpc.dbclient.plugin.system.sessionmanager.tree.sqlgen;

///**
// * ====================================================================
// *             DBClient yet another Jdbc client tool
// *
// * DBClient is a new Open Source Tool for connecting to jdbc
// * compliant relational databases. Specific extensions will take care of
// * each RDBMS implementation.
// *
// * Copyright (C) 2006-2008 Taha BEN SALAH
// *
// * This program is free software; you can redistribute it and/or modify
// * it under the terms of the GNU General Public License as published by
// * the Free Software Foundation; either version 2 of the License, or
// * (at your option) any later version.
// *
// * This program is distributed in the hope that it will be useful,
// * but WITHOUT ANY WARRANTY; without even the implied warranty of
// * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// * GNU General Public License for more details.
// *
// * You should have received a copy of the GNU General Public License along
// * with this program; if not, write to the Free Software Foundation, Inc.,
// * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
// * ====================================================================
// */
//
//package net.thevpc.dbclient.sessionmanager.window.tree.sqlgen;
//
//import net.thevpc.dbclient.api.sessionmanager.DBCSession;
//import net.thevpc.dbclient.api.sql.SQLRecord;
//import net.thevpc.dbclient.api.sql.objects.DBObject;
//import net.thevpc.dbclient.api.sql.objects.Table;
//import net.thevpc.dbclient.api.sql.objects.TableColumn;
//import net.thevpc.dbclient.api.sql.objects.TableFolder;
//import net.thevpc.dbclient.api.sql.util.SQLUtils;
//
//import java.io.PrintStream;
//import java.sql.ResultSet;
//import java.sql.ResultSetMetaData;
//import java.sql.SQLException;
//import java.sql.Statement;
//
///**
// * @author Taha BEN SALAH (taha.bensalah@gmail.com)
// * @creationtime 12 août 2005 10:48:58
// */
//public class ValuesScriptGenerator extends TreeSQLGenerator {
//    public ValuesScriptGenerator(DBCSession session) {
//        super(session, "ValuesScriptGenerator");
////        super(tree, "Insert Into Select * From $table");
//    }
//
//    public void generateSQL(PrintStream out, DBObject[] selectedNodes) throws SQLException {
//        DBObject node = selectedNodes.length == 0 ? null : selectedNodes[0];
//
//
//        Table[] tabs;
//        if (node instanceof Table) {
//            tabs = new Table[]{(Table) node};
//        } else {
//            TableFolder w = (TableFolder) node;
//            tabs = w.getTablesArray();
//        }
//        try {
//            for (Table w : tabs) {
//                out.println("\n--Values for Table " + w.getFullName() + "\n");
//                Statement st = session.getConnection().createStatement();
//                ResultSet rs = st.executeQuery("Select * from " + w.getFullName());
//                ResultSetMetaData md = rs.getMetaData();
//                int max = md.getColumnCount();
//                String[] cols = new String[max];
//                for (int j = 0; j < cols.length; j++) {
//                    cols[j] = md.getColumnName(j + 1);
//
//                }
//                while (rs.next()) {
//                    int index = 1;
//                    SQLRecord r = new SQLRecord();
//                    while (index <= max) {
//                        TableColumn column = w.getColumnsFolder().getColumn(cols[index - 1]);
//                        r.put(cols[index - 1], SQLUtils.loadValue(rs, index, column.getSqlType(), column.getPrecision()));
//                        index++;
//                    }
//                    out.println(getSession().getConnection().getSQLInsertRecord(
//                            w.getCatalogName(),w.getSchemaName(), w.getName()
//                            , r));
//                }
//                rs.close();
//                st.close();
//            }
//        } catch (SQLException e1) {
//            e1.printStackTrace();
////            getSession().getErr().println(e1);
//        }
//    }
//
//    public boolean isEnabled(DBObject activeNode, DBObject[] selectedNodes) {
//        return activeNode != null
//                &&
//                (
//                        activeNode instanceof Table
//                                || activeNode instanceof TableFolder
//                );
//    }
//}
