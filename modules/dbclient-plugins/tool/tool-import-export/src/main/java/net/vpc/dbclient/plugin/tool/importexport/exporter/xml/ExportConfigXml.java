package net.vpc.dbclient.plugin.tool.importexport.exporter.xml;

import net.vpc.dbclient.plugin.tool.importexport.ExportConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)  alias vpc
 * @creationtime 2009/08/07 18:00:21
 */
public class ExportConfigXml extends ExportConfig {
    private boolean openFile = true;
    private File output;
    private String encoding;
    private String documentElement;
    private String rowElement;

    private List<FormatItemXml> formats;

    public ExportConfigXml() {

    }

    public FormatItemXml getFormatter(int column) {
        if (formats == null || column < 0 || column >= formats.size()) {
            return null;
        }
        return formats.get(column);
    }

    public List<FormatItemXml> getFormats() {
        return formats;
    }

    public void setFormat(int index, FormatItemXml format) {
        if (formats == null) {
            formats = new ArrayList<FormatItemXml>();
        }
        while (formats.size() <= index) {
            formats.add(null);
        }
        formats.set(index, format);
    }

    public void setFormats(List<FormatItemXml> formats) {
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

    public String getDocumentElement() {
        return documentElement;
    }

    public void setDocumentElement(String documentElement) {
        this.documentElement = documentElement;
    }

    public String getRowElement() {
        return rowElement;
    }

    public void setRowElement(String rowElement) {
        this.rowElement = rowElement;
    }

    public void validate() {
        if (encoding != null && encoding.trim().length() == 0) {
            encoding = null;
        }
        if (documentElement == null || documentElement.trim().length() == 0) {
            documentElement = "Document";
        }
        if (rowElement == null || rowElement.trim().length() == 0) {
            rowElement = "Row";
        }
    }

    public boolean isOpenFile() {
        return openFile;
    }

    public void setOpenFile(boolean openFile) {
        this.openFile = openFile;
    }
}