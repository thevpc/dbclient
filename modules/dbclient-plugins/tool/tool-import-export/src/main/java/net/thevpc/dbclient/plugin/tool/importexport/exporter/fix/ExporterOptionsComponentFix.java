package net.thevpc.dbclient.plugin.tool.importexport.exporter.fix;

import net.thevpc.common.swing.file.ExtensionFileChooserFilter;
import net.thevpc.dbclient.api.viewmanager.DBCPluggablePanel;
import net.thevpc.dbclient.plugin.tool.importexport.ExportConfig;
import net.thevpc.dbclient.plugin.tool.importexport.ExportModel;
import net.thevpc.dbclient.plugin.tool.importexport.ExporterOptionsComponent;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.io.File;

public class ExporterOptionsComponentFix extends DBCPluggablePanel implements ExporterOptionsComponent {
    private ExportDataFixForm form = new ExportDataFixForm();

    public ExporterOptionsComponentFix() {
        super(new BorderLayout());
        add(form.getMainPanel());
    }

    public void setModel(ExportModel model) {
        ColumnFormatTableModelFix colsModel = new ColumnFormatTableModelFix(model);
        form.getColumnsTable().setModel(colsModel);
        JComboBox comboBox = new JComboBox();
        comboBox.addItem(FixAlign.LEFT);
        comboBox.addItem(FixAlign.CENTER);
        comboBox.addItem(FixAlign.RIGTH);

        TableColumn colorColumn = form.getColumnsTable().getColumn("Align");
        // Use the combo box as the editor in the "Favorite Color" column.
        colorColumn.setCellEditor(new DefaultCellEditor(comboBox));
    }

    public ExportConfig getConfig() {
        ExportConfigFix config = new ExportConfigFix();
        config.validate();
        ColumnFormatTableModelFix colsModel = (ColumnFormatTableModelFix) (form.getColumnsTable().getModel());
        config.setOpenFile(form.getOpenFileWhenFinishedCheckBox().isSelected());
        config.setExportHeaders(form.getExportHeadersCheckBox().isSelected());
        config.setFormats(colsModel.getFormatItems());
        String o;
        //o=(String) form.getContentCoteComboBox().getSelectedItem();
        //data.setContentCotes(o);

        o = (String) form.getFieldSeparatorComboBox().getSelectedItem();
        if (o != null) {
            String s = ExportDataFixForm.encodedStrings.get(o);
            if (s != null) {
                o = s;
            }
        }
        config.setFieldSeparator(o);

        o = (String) form.getLineSeparatorComboBox().getSelectedItem();
        if (o != null) {
            String s = ExportDataFixForm.encodedStrings.get(o);
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