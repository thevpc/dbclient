package net.thevpc.dbclient.plugin.tool.importexport.exporter.xml;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import net.thevpc.dbclient.api.pluginmanager.DBCPluginSession;
import net.thevpc.common.prs.plugin.Inject;
import net.thevpc.dbclient.plugin.tool.importexport.*;
import net.thevpc.common.swing.SwingUtilities3;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 7 août 2009 17:53:15
 */
public class ExporterXml implements DBCExporter {
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
        OutputStreamWriter out = null;
        try {
            ExportConfigXml d = (ExportConfigXml) data;
            try {
                FormatManager formatManager = new FormatManager();
                FileOutputStream o = new FileOutputStream(d.getOutput());
                out = d.getEncoding() != null ? new OutputStreamWriter(o, d.getEncoding()) : new OutputStreamWriter(o);


                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                org.w3c.dom.Document dom = db.newDocument();

                Element rootEle = dom.createElement("Document");
                dom.appendChild(rootEle);

                int columnsCount = model.getColumnCount();
                while (model.next()) {
                    Element row = dom.createElement("Row");
                    rootEle.appendChild(row);
                    for (int c = 0; c < columnsCount; c++) {
                        String n = model.getColumnName(c);
                        Object v = model.getValue(c);
                        String s = format(v, d, c, formatManager);
                        if (d.getFormatter(c).isAttribute()) {
                            row.setAttribute(n, s);
                        } else {
                            Element col = dom.createElement(n);
                            col.setTextContent(s);
                            row.appendChild(col);
                        }
                    }
                }
                OutputFormat format = new OutputFormat(dom);
                format.setIndenting(true);
                XMLSerializer serializer = new XMLSerializer(out, format);
                serializer.serialize(dom);
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

    public String format(Object value, ExportConfigXml d, int column, FormatManager formatManager) throws ExportException {
        FormatItemXml format = d.getFormatter(column);
        String svalue = formatManager.format(value, format == null ? null : format.getFormat());
        if (format != null) {
            int len = format.getMax();
            if (len > 0) {
                if (svalue.length() > len) {
                    svalue = svalue.substring(0, len);
                }
            }
        }
        return svalue;
    }

    public ExporterOptionsComponent createOptionsComponent() {
        return new ExporterOptionsComponentXml();
    }
}