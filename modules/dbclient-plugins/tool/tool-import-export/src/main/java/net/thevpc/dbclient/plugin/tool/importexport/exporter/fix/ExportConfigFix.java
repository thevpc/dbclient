package net.thevpc.dbclient.plugin.tool.importexport.exporter.fix;

import net.thevpc.dbclient.plugin.tool.importexport.ExportConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)  alias vpc
 * @creationtime 2009/08/07 18:00:21
 */
public class ExportConfigFix extends ExportConfig {
    private boolean openFile = true;
    private boolean exportHeaders = true;
    //private boolean coteAlways=true;
    private File output;
    private String lineSeparator;
    private String encoding;
    private String fieldSeparator = "";
    //private String contentCotes="\"";

    private List<FormatItemFix> formats;

    public ExportConfigFix() {

    }

    public FormatItemFix getFormatter(int column) {
        if (formats == null || column < 0 || column >= formats.size()) {
            return null;
        }
        return formats.get(column);
    }

    public List<FormatItemFix> getFormats() {
        return formats;
    }

    public void setFormat(int index, FormatItemFix format) {
        if (formats == null) {
            formats = new ArrayList<FormatItemFix>();
        }
        while (formats.size() <= index) {
            formats.add(null);
        }
        formats.set(index, format);
    }

    public void setFormats(List<FormatItemFix> formats) {
        this.formats = formats;
    }

    public File getOutput() {
        return output;
    }

    public void setOutput(File output) {
        this.output = output;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getFieldSeparator() {
        return fieldSeparator;
    }

    public void setFieldSeparator(String fieldSeparator) {
        this.fieldSeparator = fieldSeparator;
    }

//    public String getContentCotes() {
//        return contentCotes;
//    }
//
//    public void setContentCotes(String contentCotes) {
//        this.contentCotes = contentCotes;
//    }

    public boolean isExportHeaders() {
        return exportHeaders;
    }

    public void setExportHeaders(boolean exportHeaders) {
        this.exportHeaders = exportHeaders;
    }

//    public boolean isCoteAlways() {
//        return coteAlways;
//    }
//
//    public void setCoteAlways(boolean coteAlways) {
//        this.coteAlways = coteAlways;
//    }

    public String getLineSeparator() {
        return lineSeparator;
    }

    public void setLineSeparator(String lineSeparator) {
        this.lineSeparator = lineSeparator;
    }

    public void validate() {
        if (encoding != null && encoding.trim().length() == 0) {
            encoding = null;
        }
        if (fieldSeparator == null || fieldSeparator.trim().length() == 0) {
            fieldSeparator = null;
        }
        if (lineSeparator == null || lineSeparator.trim().length() == 0) {
            lineSeparator = System.getProperty("line.separator");
        }

    }

    public boolean isOpenFile() {
        return openFile;
    }

    public void setOpenFile(boolean openFile) {
        this.openFile = openFile;
    }
}