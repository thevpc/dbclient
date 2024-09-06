package net.thevpc.dbclient.plugin.tool.importexport;

import net.thevpc.common.prs.plugin.Extension;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 7 août 2009 17:53:15
 */
@Extension(group = "manager", customizable = false)
public interface DBCExporter {
    public String getId();

    public String getTitle();

    public void exportTableModel(ExportConfig data, ExportModel model) throws ExportException;

    public abstract ExporterOptionsComponent createOptionsComponent();
}
