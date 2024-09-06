package net.thevpc.dbclient.plugin.tool.importexport.exporter.fix;

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
public class ColumnFormatTableModelFix extends DefaultTableModel {
    public ColumnFormatTableModelFix(ExportModel dataModel) {
        Vector metaRows = new Vector();
        int colCount = dataModel.getColumnCount();
        for (int i = 0; i < colCount; i++) {
            metaRows.add(new Vector(Arrays.asList((i + 1), dataModel.getColumnName(i), null, null, null, FixAlign.LEFT)));
        }
        Vector metaCols = new Vector();
        metaCols.add("Index");
        metaCols.add("Column");
        metaCols.add("Format");
        metaCols.add("Min Length");
        metaCols.add("Max Length");
        metaCols.add("Align");
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
            case 4: {
                return Integer.class;
            }
            case 5: {
                return FixAlign.class;
            }
        }
        return Object.class;
    }

    public List<FormatItemFix> getFormatItems() {
        List<FormatItemFix> list = new ArrayList<FormatItemFix>();
        int rows = getRowCount();
        for (int i = 0; i < rows; i++) {
            //Integer index = (Integer) getValueAt(i, 0);
            //String colName = (String) getValueAt(i, 1);
            String format = (String) getValueAt(i, 2);
            Integer min = (Integer) getValueAt(i, 3);
            Integer max = (Integer) getValueAt(i, 4);
            FixAlign align = (FixAlign) getValueAt(i, 5);
            FormatItemFix f = new FormatItemFix();
            f.setFormat(format);
            f.setMin(min == null ? -1 : min);
            f.setMax(max == null ? -1 : max);
            f.setAlign(align);
            list.add(f);
        }
        return list;
    }
}