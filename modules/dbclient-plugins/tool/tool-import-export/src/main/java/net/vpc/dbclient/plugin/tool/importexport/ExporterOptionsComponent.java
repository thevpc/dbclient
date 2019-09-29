package net.vpc.dbclient.plugin.tool.importexport;

import net.vpc.dbclient.api.viewmanager.DBCComponent;

public interface ExporterOptionsComponent extends DBCComponent {
    public void setModel(ExportModel model);

    public ExportConfig getConfig();
}
