package net.thevpc.dbclient.plugin.tool.importexport;

import net.thevpc.dbclient.api.DBCSession;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.LinkedHashMap;

public class ExportPanel extends JPanel {
    final JComboBox exportersList = new JComboBox();
    ExportModel model;

    private static class ExportInfo {
        DBCExporter exporter;
        ExporterOptionsComponent optionsComponent;

        public String toString() {
            return exporter.getTitle();
        }
    }

    public ExportPanel(DBCSession session, final ExportModel model) {
        super(new BorderLayout());
        this.model = model;
        LinkedHashMap<String, ExportInfo> exporters = new LinkedHashMap<String, ExportInfo>();
        for (DBCExporter impl : session.getFactory().createImplementations(DBCExporter.class)) {
            DBCExporter o = null;
            try {
                o = impl;
                ExportInfo ei = new ExportInfo();
                ei.exporter = o;
                ei.optionsComponent = ei.exporter.createOptionsComponent();
                ei.optionsComponent.setModel(model);
                exporters.put(ei.exporter.getId(), ei);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//        exportersList.addItem(null);
        final CardLayout cardLayout = new CardLayout();
        final JPanel exporterOptionsPanel = new JPanel(cardLayout);
//        exporterOptionsPanel.setBorder(BorderFactory.createLineBorder(Color.RED,5));
        for (ExportInfo exporter : exporters.values()) {
            exportersList.addItem(exporter);
            exporterOptionsPanel.add(exporter.optionsComponent.getComponent(), exporter.exporter.getId());
        }
        exportersList.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    ExportInfo exporter = (ExportInfo) exportersList.getSelectedItem();
                    if (exporter != null) {
                        cardLayout.show(exporterOptionsPanel, exporter.exporter.getId());
//                        exporter.optionsComponent.setModel(model);
//                        Dialog a = (Dialog) SwingUtilities.getAncestorOfClass(JDialog.class, exportersList);
//                        if (a != null) {
//                            a.pack();
//                        }
                    }
                }
            }
        });

        this.add(exportersList, BorderLayout.PAGE_START);
        this.add(exporterOptionsPanel, BorderLayout.CENTER);
    }

    public DBCExporter getExporter() {
        ExportInfo ei = (ExportInfo) exportersList.getSelectedItem();
        if (ei != null) {
            return ei.exporter;
        }
        return null;
    }

    public ExportConfig getConfig() {
        ExportInfo ei = (ExportInfo) exportersList.getSelectedItem();
        if (ei != null) {
            return ei.optionsComponent.getConfig();
        }
        return null;
    }


}
