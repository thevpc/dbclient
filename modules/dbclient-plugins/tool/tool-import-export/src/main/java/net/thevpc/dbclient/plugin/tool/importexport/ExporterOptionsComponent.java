package net.thevpc.dbclient.plugin.tool.importexport;

import net.thevpc.dbclient.api.viewmanager.DBCComponent;

public interface ExporterOptionsComponent extends DBCComponent {
    public void setModel(ExportModel model);

    public ExportConfig getConfig();
}
