package net.thevpc.dbclient.plugin.tool.importexportexcel.exporter;

import net.thevpc.common.swing.file.ExtensionFileChooserFilter;
import net.thevpc.dbclient.api.viewmanager.DBCPluggablePanel;
import net.thevpc.dbclient.plugin.tool.importexport.ExportConfig;
import net.thevpc.dbclient.plugin.tool.importexport.ExportModel;
import net.thevpc.dbclient.plugin.tool.importexport.ExporterOptionsComponent;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ExporterOptionsComponentExcel extends DBCPluggablePanel implements ExporterOptionsComponent {
    private ExportDataExcelForm form = new ExportDataExcelForm();

    public ExporterOptionsComponentExcel() {
        super(new BorderLayout());
        add(form.getMainPanel());
    }

    public void setModel(ExportModel model) {
        ColumnFormatTableModelExcel colsModel = new ColumnFormatTableModelExcel(model);
        form.getColumnsTable().setModel(colsModel);
    }

    public ExportConfig getConfig() {
        ExportConfigExcel config = new ExportConfigExcel();
        config.validate();
        config.setOpenFile(form.getOpenFileWhenFinishedCheckBox().isSelected());
        config.setExportHeaders(form.getExportHeadersCheckBox().isSelected());
        ColumnFormatTableModelExcel colsModel = (ColumnFormatTableModelExcel) (form.getColumnsTable().getModel());
        config.setFormats(colsModel.getFormatItems());
        config.setSheetName(form.getSheetNameText().getText());
        JFileChooser c = new JFileChooser();
        c.addChoosableFileFilter(new ExtensionFileChooserFilter("xls", "Excel File (2000/XP/2003)"));
        int i = c.showDialog(null, "Select");
        if (i == JFileChooser.APPROVE_OPTION) {
            File f = c.getSelectedFile();
            if (!f.exists() && f.getName().indexOf('.') < 0) {
                f = new File(f.getParentFile(), f.getName() + ".xls");
            }
            config.setOutput(f);
            config.validate();
            return config;
        }
        return null;
    }

    public Component getComponent() {
        return this;
    }
}