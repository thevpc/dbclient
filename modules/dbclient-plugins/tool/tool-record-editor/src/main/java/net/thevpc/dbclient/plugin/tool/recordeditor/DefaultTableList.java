/**
 *
====================================================================
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
 *
====================================================================
 */
package net.thevpc.dbclient.plugin.tool.recordeditor;

import net.thevpc.dbclient.api.sessionmanager.DBCResultTable;
import net.thevpc.dbclient.api.sessionmanager.DBCTableModel;
import net.thevpc.dbclient.api.sql.SQLRecord;
import net.thevpc.dbclient.api.sql.TypeWrapperFactory;
import net.thevpc.dbclient.api.sql.objects.DBTable;
import net.thevpc.dbclient.api.sql.objects.DBTableColumn;
import net.thevpc.dbclient.api.sql.util.SQLUtils;
import net.thevpc.dbclient.api.viewmanager.DBCTable;
import net.thevpc.common.swing.prs.PRSManager;

import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 6 mai 2007 22:14:16
 */
public class DefaultTableList extends JPanel {

    DBCTable jtable;
    DBTable table;
    RecordEditorPluginSession pluginSession;
    DBCResultTable tablePane;

    public DefaultTableList(RecordEditorPluginSession pluginSession, DBTable table) {
        this.pluginSession = pluginSession;
        this.table = table;
        tablePane = pluginSession.getSession().getFactory().newInstance(DBCResultTable.class);
        tablePane.getTableComponent().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(tablePane.getComponent());
        PRSManager.setComponentResourceSetHolder(this, pluginSession);
        PRSManager.update(this, pluginSession);
    }

    public SQLRecord showDialog() {
        DBTableColumn[] columns = table.getColumns();
        StringBuilder sb = new StringBuilder("Select ");
        int count = 0;
        for (int i = 0; i < columns.length; i++) {
            DBTableColumn column = columns[i];
            if (column.isPk() || pluginSession.isSelector(column)) {
                if (count > 0) {
                    sb.append(", ");
                } else {
                    sb.append(" ");
                }
                sb.append(column.getName());
                count++;
            }
        }
        sb.append(" From ");
        sb.append(table.getFullName());
        try {
            DBCTableModel dbcTableModel = tablePane.getModel();
            dbcTableModel.executeStatement(sb.toString());
            int index = 0;
            for (int i = 0; i < columns.length; i++) {
                DBTableColumn column = columns[i];
                if (column.isPk() || pluginSession.isSelector(column)) {
                    dbcTableModel.setColumnName(index, pluginSession.getColumnTitle(column));
                    index++;
                }
            }
        } catch (SQLException e) {
            //ex = e;
        }
        tablePane.getComponent().setPreferredSize(new Dimension(500, 300));
        int r = JOptionPane.showConfirmDialog(null, this, "Select Row...", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (r == JOptionPane.OK_OPTION) {

            int index = tablePane.getTableComponent().getSelectedRow();
            index = tablePane.getTableComponent().convertRowIndexToModel(index);
            if (index >= 0) {
                SQLRecord record = tablePane.getRecord(index);
                sb = new StringBuilder("SELECT ");
                for (int i = 0; i < columns.length; i++) {
                    DBTableColumn column = columns[i];
                    if (i > 0) {
                        sb.append(", ");
                    }
                    sb.append(column.getName());
                }
                sb.append(" FROM ");
                sb.append(table.getFullName());
                sb.append(" WHERE ");
                count = 0;
                for (int i = 0; i < columns.length; i++) {
                    DBTableColumn column = columns[i];
                    if (column.isPk()) {
                        if (count > 0) {
                            sb.append(" AND ");
                        }
                        sb.append(" ");
                        sb.append(column.getName());
                        sb.append("=?");
                        count++;
                    }
                }
                SQLRecord fullRecord = null;
                PreparedStatement stmt = null;
                ResultSet resultSet = null;
                try {
                    try {
                        stmt = pluginSession.getSession().getConnection().prepareStatement(sb.toString());
                        for (int i = 0; i < columns.length; i++) {
                            DBTableColumn column = columns[i];
                            if (column.isPk()) {
                                SQLUtils.saveValue(stmt, i + 1, record.get(column.getName()), column);
                            }
                        }
                        resultSet = stmt.executeQuery();
                        fullRecord = new SQLRecord();
                        if (resultSet.next()) {
                            TypeWrapperFactory twf = pluginSession.getSession().getConnection().getTypeWrapperFactory();
                            for (int i = 0; i < columns.length; i++) {
                                DBTableColumn column = columns[i];
                                //fullRecord.put(column.getName(),SQLUtils.loadValue(resultSet,i+1,column));
                                fullRecord.put(column.getName(), twf.getTypeDesc(column.getSqlType()).getWrapper().getObject(resultSet, i + 1));
                            }
                        }
                    } finally {
                        if (resultSet != null) {
                            resultSet.close();
                        }
                        if (stmt != null) {
                            stmt.close();
                        }
                    }
                } catch (SQLException ex) {
                    pluginSession.getSession().getLogger(DefaultTableList.class.getName()).log(Level.SEVERE, "Internal Error", ex);
                }
                return fullRecord;
            }
        }
        return null;
    }
}