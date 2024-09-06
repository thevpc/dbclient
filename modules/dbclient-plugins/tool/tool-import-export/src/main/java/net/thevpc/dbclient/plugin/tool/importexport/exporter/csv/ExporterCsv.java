package net.thevpc.dbclient.plugin.tool.importexport.exporter.csv;

import net.thevpc.dbclient.api.pluginmanager.DBCPluginSession;
import net.thevpc.common.prs.plugin.Inject;
import net.thevpc.dbclient.plugin.tool.importexport.*;
import net.thevpc.common.swing.SwingUtilities3;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 7 août 2009 17:53:15
 */
public class ExporterCsv implements DBCExporter {
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
        PrintStream out = null;
        try {
            ExportConfigCsv d = (ExportConfigCsv) data;
            try {
                FileOutputStream o = new FileOutputStream(d.getOutput());
                out = d.getEncoding() != null ? new PrintStream(o, false, d.getEncoding()) : new PrintStream(o, false);
                int columnsCount = model.getColumnCount();
                String lineSeparator = d.getLineSeparator();
                String fieldSeparator = d.getFieldSeparator();
                TreeSet<Character> badChars = new TreeSet<Character>();
                char[] chars = (lineSeparator + fieldSeparator + (d.getContentCotes() == null ? "" : d.getContentCotes())).toCharArray();
                for (char cc : chars) {
                    badChars.add(cc);
                }
                FormatManager formatManager = new FormatManager();
                if (d.isExportHeaders()) {
                    for (int c = 0; c < columnsCount; c++) {
                        if (c > 0) {
                            out.print(fieldSeparator);
                        }
                        String h = model.getColumnName(c);
                        out.print(format(h, d, c, badChars, formatManager));
                    }
                    out.print(lineSeparator);
                }
                while (model.next()) {
                    for (int c = 0; c < columnsCount; c++) {
                        if (c > 0) {
                            out.print(fieldSeparator);
                        }
                        Object v = model.getValue(c);
                        out.print(format(v, d, c, badChars, formatManager));
                    }
                    out.print(lineSeparator);
                }
            } finally {
                if (out != null) {
                    out.close();
                }
            }

            if (d.isOpenFile()) {
                SwingUtilities3.openFile(d.getOutput());
            }
        } catch (Exception e) {
            throw new ExportException(e);
        }
    }

    public String format(Object value, ExportConfigCsv d, int column, Set<Character> badChars, FormatManager formatManager) throws ExportException {
        FormatItemCsv format = d.getFormatter(column);
        String svalue = formatManager.format(value, format == null ? null : format.getFormat());
        if (format != null) {
            int len = format.getMax();
            if (len > 0) {
                if (svalue.length() > len) {
                    svalue = svalue.substring(0, len);
                }
            }
        }
        String cotes = d.getContentCotes();
        boolean cote = (d.getContentCotes() != null && (d.isCoteAlways() || isCoteNeed(svalue, badChars)));
        if (cote) {
            return cotes + svalue + cotes;
        }
        return svalue;
    }

    public boolean isCoteNeed(String value, Set<Character> badChars) throws ExportException {
        char[] chars = value.toCharArray();
        for (char aChar : chars) {
            if (badChars.contains(aChar)) {
                return true;
            }
        }
        return false;
    }

    public ExporterOptionsComponent createOptionsComponent() {
        return new ExporterOptionsComponentCsv();
    }
}
