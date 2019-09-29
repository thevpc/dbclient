package net.vpc.dbclient.plugin.tool.importexport;

import javax.swing.table.TableModel;

public class ExportTableModel implements ExportModel {
    private TableModel tableModel;
    private int row = -1;

    public ExportTableModel(TableModel tableModel) {
        this.tableModel = tableModel;
    }

    public int getColumnCount() {
        return tableModel.getColumnCount();
    }

    public String getColumnName(int columnIndex) {
        return tableModel.getColumnName(columnIndex);
    }

    public Class<?> getColumnClass(int columnIndex) {
        return tableModel.getColumnClass(columnIndex);
    }

    public Object getValue(int columnIndex) {
        return tableModel.getValueAt(row, columnIndex);
    }

    public boolean next() {
        row++;
        return row < tableModel.getRowCount();
    }
}
