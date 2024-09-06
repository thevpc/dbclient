package net.thevpc.dbclient.plugin.tool.importexport.exporter.csv;

import net.thevpc.common.swing.file.ExtensionFileChooserFilter;
import net.thevpc.dbclient.api.viewmanager.DBCPluggablePanel;
import net.thevpc.dbclient.plugin.tool.importexport.ExportConfig;
import net.thevpc.dbclient.plugin.tool.importexport.ExportModel;
import net.thevpc.dbclient.plugin.tool.importexport.ExporterOptionsComponent;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ExporterOptionsComponentCsv extends DBCPluggablePanel implements ExporterOptionsComponent {
    private ExportDataCsvForm form = new ExportDataCsvForm();

    public ExporterOptionsComponentCsv() {
        super(new BorderLayout());
        add(form.getMainPanel());
    }

    public void setModel(ExportModel model) {
        ColumnFormatTableModelCsv colsModel = new ColumnFormatTableModelCsv();
        colsModel.setExporterModel(model);
        form.getColumnsTable().setModel(colsModel);
    }

    public ExportConfig getConfig() {
        ExportConfigCsv config = new ExportConfigCsv();
        config.validate();
        config.setCoteAlways(form.getCoteAlwaysCheckBox().isSelected());
        config.setOpenFile(form.getOpenFileWhenFinishedCheckBox().isSelected());
        config.setExportHeaders(form.getExportHeadersCheckBox().isSelected());
        ColumnFormatTableModelCsv colsModel = (ColumnFormatTableModelCsv) (form.getColumnsTable().getModel());
        config.setFormats(colsModel.getFormatItems());
        String o = (String) form.getContentCoteComboBox().getSelectedItem();
        config.setContentCotes(o);

        o = (String) form.getFieldSeparatorComboBox().getSelectedItem();
        if (o != null) {
            String s = ExportDataCsvForm.encodedStrings.get(o);
            if (s != null) {
                o = s;
            }
        }
        config.setFieldSeparator(o);

        o = (String) form.getLineSeparatorComboBox().getSelectedItem();
        if (o != null) {
            String s = ExportDataCsvForm.encodedStrings.get(o);
            if (s != null) {
                o = s;
            }
        }
        config.setLineSeparator(o);
        JFileChooser c = new JFileChooser();
        c.addChoosableFileFilter(new ExtensionFileChooserFilter("txt", "Text File"));
        c.addChoosableFileFilter(new ExtensionFileChooserFilter("csv", "CSV File"));
        int i = c.showDialog(null, "Select");
        if (i == JFileChooser.APPROVE_OPTION) {
            File f = c.getSelectedFile();
            if (!f.exists() && f.getName().indexOf('.') < 0) {
                f = new File(f.getParentFile(), f.getName() + ".csv");
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
