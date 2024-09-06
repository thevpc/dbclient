package net.thevpc.dbclient.plugin.tool.importexportexcel.exporter;

import net.thevpc.dbclient.plugin.tool.importexport.ExportConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)  alias vpc
 * @creationtime 2009/08/07 18:00:21
 */
public class ExportConfigExcel extends ExportConfig {
    private boolean openFile = true;
    private boolean exportHeaders = true;
    private String sheetName = "data";
    private File output;

    private List<FormatItemExcel> formats;

    public ExportConfigExcel() {

    }

    public FormatItemExcel getFormatter(int column) {
        if (formats == null || column < 0 || column >= formats.size()) {
            return null;
        }
        return formats.get(column);
    }

    public List<FormatItemExcel> getFormats() {
        return formats;
    }

    public void setFormat(int index, FormatItemExcel format) {
        if (formats == null) {
            formats = new ArrayList<FormatItemExcel>();
        }
        while (formats.size() <= index) {
            formats.add(null);
        }
        formats.set(index, format);
    }

    public void setFormats(List<FormatItemExcel> formats) {
        this.formats = formats;
    }

    public File getOutput() {
        return output;
    }

    public void setOutput(File output) {
        this.output = output;
    }

    public boolean isExportHeaders() {
        return exportHeaders;
    }

    public void setExportHeaders(boolean exportHeaders) {
        this.exportHeaders = exportHeaders;
    }

    public boolean isOpenFile() {
        return openFile;
    }

    public void setOpenFile(boolean openFile) {
        this.openFile = openFile;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public void validate() {
        if (sheetName != null && sheetName.trim().length() == 0) {
            sheetName = "data";
        }
    }

}