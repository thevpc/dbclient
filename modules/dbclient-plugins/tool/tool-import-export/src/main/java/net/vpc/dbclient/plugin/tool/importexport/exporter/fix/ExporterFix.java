package net.vpc.dbclient.plugin.tool.importexport.exporter.fix;

import net.vpc.dbclient.api.pluginmanager.DBCPluginSession;
import net.vpc.prs.plugin.Inject;
import net.vpc.dbclient.plugin.tool.importexport.*;
import net.vpc.swingext.SwingUtilities3;

import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 7 août 2009 17:53:15
 */
public class ExporterFix implements DBCExporter {
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
            ExportConfigFix d = (ExportConfigFix) data;
            try {
                FormatManager formatManager = new FormatManager();
                FileOutputStream o = new FileOutputStream(d.getOutput());
                out = d.getEncoding() != null ? new PrintStream(o, false, d.getEncoding()) : new PrintStream(o, false);
                int columnsCount = model.getColumnCount();
                String lineSeparator = d.getLineSeparator();
                String fieldSeparator = d.getFieldSeparator();
                if (fieldSeparator == null) {
                    fieldSeparator = "";
                }
                if (d.isExportHeaders()) {
                    for (int c = 0; c < columnsCount; c++) {
                        if (c > 0) {
                            out.print(fieldSeparator);
                        }
                        String h = model.getColumnName(c);
                        out.print(format(h, d, c, formatManager));
                    }
                    out.print(lineSeparator);
                }
                while (model.next()) {
                    for (int c = 0; c < columnsCount; c++) {
                        if (c > 0) {
                            out.print(fieldSeparator);
                        }
                        Object v = model.getValue(c);
                        out.print(format(v, d, c, formatManager));
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

    public String format(Object value, ExportConfigFix d, int column, FormatManager formatManager) throws ExportException {
        FormatItemFix format = d.getFormatter(column);
        String svalue = formatManager.format(value, format == null ? null : format.getFormat());
        if (format != null) {
            int len = format.getMax();
            if (len > 0) {
                if (svalue.length() > len) {
                    svalue = svalue.substring(0, len);
                } else {
                    switch (format.getAlign()) {
                        case LEFT: {
                            while (svalue.length() < len) {
                                svalue = svalue + " ";
                            }
                            break;
                        }
                        case RIGTH: {
                            while (svalue.length() < len) {
                                svalue = " " + svalue;
                            }
                            break;
                        }
                        case CENTER: {
                            boolean left = true;
                            while (svalue.length() < len) {
                                if (left) {
                                    svalue = svalue + " ";
                                } else {
                                    svalue = " " + svalue;
                                }
                                left = !left;
                            }
                            break;
                        }
                    }
                }
            }
        }
        return svalue;
    }

    public ExporterOptionsComponent createOptionsComponent() {
        return new ExporterOptionsComponentFix();
    }
}