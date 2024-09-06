package net.thevpc.dbclient.plugin.tool.importexport.exporter.csv;

import net.thevpc.dbclient.plugin.tool.importexport.ExportModel;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)  alias vpc
 * @creationtime 2009/08/07 20:23:28
 */
public class ColumnFormatTableModelCsv extends DefaultTableModel {
    public ColumnFormatTableModelCsv() {
    }

    public void setExporterModel(ExportModel dataModel) {
        Vector metaRows = new Vector();
        int colCount = dataModel.getColumnCount();
        for (int i = 0; i < colCount; i++) {
            metaRows.add(new Vector(Arrays.asList((i + 1), dataModel.getColumnName(i), null, null)));
        }
        Vector metaCols = new Vector();
        metaCols.add("Index");
        metaCols.add("Column");
        metaCols.add("Format");
        metaCols.add("Max Length");
        setDataVector(metaRows, metaCols);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return column > 1;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0: {
                return Integer.class;
            }
            case 1: {
                return String.class;
            }
            case 2: {
                return String.class;
            }
            case 3: {
                return Integer.class;
            }
        }
        return Object.class;
    }

    public List<FormatItemCsv> getFormatItems() {
        List<FormatItemCsv> list = new ArrayList<FormatItemCsv>();
        int rows = getRowCount();
        for (int i = 0; i < rows; i++) {
            //Integer index = (Integer) getValueAt(i, 0);
            //String colName = (String) getValueAt(i, 1);
            String format = (String) getValueAt(i, 2);
            Integer len = (Integer) getValueAt(i, 3);
            FormatItemCsv f = new FormatItemCsv();
            f.setFormat(format);
            f.setMax(len == null ? -1 : len);
            list.add(f);
        }
        return list;
    }
}
