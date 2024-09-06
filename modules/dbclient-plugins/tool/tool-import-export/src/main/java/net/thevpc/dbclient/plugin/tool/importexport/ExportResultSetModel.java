package net.thevpc.dbclient.plugin.tool.importexport;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExportResultSetModel implements ExportModel {
    private ResultSet tableModel;
    private int row = -1;

    public ExportResultSetModel(ResultSet tableModel) {
        this.tableModel = tableModel;
    }

    public int getColumnCount() {
        try {
            return tableModel.getMetaData().getColumnCount();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getColumnName(int columnIndex) {
        try {
            return tableModel.getMetaData().getColumnName(columnIndex + 1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Class<?> getColumnClass(int columnIndex) {
        try {
            return Class.forName(tableModel.getMetaData().getColumnClassName(columnIndex + 1));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Object getValue(int columnIndex) {
        try {
            return tableModel.getObject(columnIndex + 1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean next() {
        try {
            return tableModel.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}