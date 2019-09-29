package net.vpc.dbclient.plugin.tool.importexportexcel.exporter;

import jxl.Workbook;
import jxl.format.BorderLineStyle;
import jxl.write.*;
import net.vpc.dbclient.api.pluginmanager.DBCPluginSession;
import net.vpc.prs.plugin.Inject;
import net.vpc.dbclient.plugin.tool.importexport.*;
import net.vpc.swingext.SwingUtilities3;

import java.lang.Boolean;
import java.lang.Number;
import java.util.Date;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 7 août 2009 17:53:15
 */
public class ExporterExcel implements DBCExporter {
    @Inject
    private DBCPluginSession pluginSession;
    private String id = getClass().getSimpleName();

    public String getId() {
        return id;
    }

    public String getTitle() {
        return pluginSession.getPlugin().getMessageSet().get("DBCExporter." + getId());
    }

    public void exportTableModel(ExportConfig data, ExportModel model) throws ExportException {
        WritableWorkbook workbook = null;
        try {
            ExportConfigExcel d = (ExportConfigExcel) data;
            try {
                workbook = Workbook.createWorkbook(d.getOutput());
                String sheetName = d.getSheetName();
                if (sheetName == null || sheetName.trim().length() == 0) {
                    sheetName = "data";
                }
                sheetName = sheetName.trim();
                WritableSheet writableSheet = workbook.createSheet(sheetName, 0);
                int columnsCount = model.getColumnCount();
                FormatManager formatManager = new FormatManager();
                int col = 0;
                int row = 0;
                if (d.isExportHeaders()) {

                    for (int c = 0; c < columnsCount; c++) {
                        String h = model.getColumnName(c);
                        WritableCell writableCell = createCell(h, col, row, d, formatManager, c, -1);
                        writableSheet.addCell(writableCell);
                        col++;
                    }
                    col = 0;
                    row++;
                }
                int r = 0;
                while (model.next()) {
                    for (int c = 0; c < columnsCount; c++) {
                        Object v = model.getValue(c);
                        WritableCell writableCell = createCell(v, col, row, d, formatManager, c, r);
                        writableSheet.addCell(writableCell);
                        col++;
                    }
                    col = 0;
                    row++;
                    r++;
                }
                workbook.write();
            } finally {
                if (workbook != null) {
                    workbook.close();
                }
            }
            if (d.isOpenFile()) {
                SwingUtilities3.openFile(d.getOutput());
            }
        } catch (Exception e) {
            throw new ExportException(e);
        }
    }


    public WritableCell createCell(Object value, int col, int row, ExportConfigExcel d, FormatManager formatManager, int dataCol, int dataRow) throws WriteException {
        WritableCell writableCell = null;
        if (value == null || value.toString().length() == 0) {
            writableCell = new Label(col, row, "");
        } else {
            if (value instanceof String) {
                writableCell = new jxl.write.Label(col, row, (String) value);
            } else if (value instanceof Boolean) {
                writableCell = new jxl.write.Boolean(col, row, ((Boolean) value));
            } else if (value instanceof Number) {
                writableCell = new jxl.write.Number(col, row, ((Number) value).doubleValue());
            } else if (value instanceof Date) {
                writableCell = new jxl.write.DateTime(col, row, ((Date) value));
            } else {
                writableCell = new jxl.write.Label(col, row, value.toString());
            }
        }
        //TODO think of making these formats user personnalizable
        WritableCellFormat headerFormat = null;
        if (dataRow >= 0) {
            //FormatItemExcel format = d.getFormatter(column);

            WritableFont wf = new WritableFont(WritableFont.ARIAL, 10);
//            wf.setColour(jxl.write.Colour.BLUE_GREY);
//            wf.setBoldStyle(jxl.write.WritableFont.BOLD);
            //arial12Blue.setUnderlineStyle(UnderlineStyle.SINGLE);
            headerFormat = new WritableCellFormat(wf);
//            headerFormat.setAlignment(Alignment.CENTRE);
//            headerFormat.setBackground(jxl.write.Colour.GRAY_25);
            headerFormat.setBorder(jxl.format.Border.ALL, BorderLineStyle.THIN);
            if (dataRow % 2 == 0) {
                headerFormat.setBackground(jxl.format.Colour.IVORY);
            } else {
//                headerFormat.setBackground(jxl.format.Colour.TAN);
            }

        } else {
            //header

            WritableFont wf = new WritableFont(WritableFont.ARIAL, 10);
            wf.setColour(jxl.write.Colour.BLUE_GREY);
            wf.setBoldStyle(jxl.write.WritableFont.BOLD);
            //arial12Blue.setUnderlineStyle(UnderlineStyle.SINGLE);
            headerFormat = new WritableCellFormat(wf);
            headerFormat.setAlignment(Alignment.CENTRE);
            headerFormat.setBackground(jxl.write.Colour.GRAY_25);
            headerFormat.setBorder(jxl.format.Border.ALL, BorderLineStyle.THIN);

        }
        writableCell.setCellFormat(headerFormat);
        return writableCell;
    }

    public ExporterOptionsComponent createOptionsComponent() {
        return new ExporterOptionsComponentExcel();
    }
}