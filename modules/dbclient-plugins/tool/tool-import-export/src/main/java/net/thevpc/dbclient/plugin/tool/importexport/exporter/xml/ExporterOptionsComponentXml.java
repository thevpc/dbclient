package net.thevpc.dbclient.plugin.tool.importexport.exporter.xml;

import net.thevpc.common.swing.file.ExtensionFileChooserFilter;
import net.thevpc.dbclient.api.viewmanager.DBCPluggablePanel;
import net.thevpc.dbclient.plugin.tool.importexport.ExportConfig;
import net.thevpc.dbclient.plugin.tool.importexport.ExportModel;
import net.thevpc.dbclient.plugin.tool.importexport.ExporterOptionsComponent;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ExporterOptionsComponentXml extends DBCPluggablePanel implements ExporterOptionsComponent {
    private ExportDataXmlForm form = new ExportDataXmlForm();

    public ExporterOptionsComponentXml() {
        super(new BorderLayout());
        add(form.getMainPanel());
    }

    public void setModel(ExportModel model) {
        ColumnFormatTableModelXml colsModel = new ColumnFormatTableModelXml(model);
        form.getColumnsTable().setModel(colsModel);
        //form.getOpenFileWhenFinishedCheckBox().setSelected(data.isOpenFile());

    }

    public ExportConfig getConfig() {
        ExportConfigXml config = new ExportConfigXml();
        config.validate();
        config.setOpenFile(form.getOpenFileWhenFinishedCheckBox().isSelected());
        ColumnFormatTableModelXml colsModel = (ColumnFormatTableModelXml) (form.getColumnsTable().getModel());
        config.setFormats(colsModel.getFormatItems());
        String o;
        //o=(String) form.getContentCoteComboBox().getSelectedItem();
        //data.setContentCotes(o);
        config.setDocumentElement(form.getDocumentElementText().getText());
        config.setRowElement(form.getRowElementText().getText());

        JFileChooser c = new JFileChooser();
        c.addChoosableFileFilter(new ExtensionFileChooserFilter("xml", "Xml File"));
        int i = c.showDialog(null, "Select");
        if (i == JFileChooser.APPROVE_OPTION) {
            File f = c.getSelectedFile();
            if (!f.exists() && f.getName().indexOf('.') < 0) {
                f = new File(f.getParentFile(), f.getName() + ".xml");
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