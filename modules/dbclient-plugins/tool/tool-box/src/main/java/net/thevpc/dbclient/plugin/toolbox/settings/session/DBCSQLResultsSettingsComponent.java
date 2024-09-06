/**
 * ====================================================================
 *             DBClient yet another Jdbc client tool
 *
 * DBClient is a new Open Source Tool for connecting to jdbc
 * compliant relational databases. Specific extensions will take care of
 * each RDBMS implementation.
 *
 * Copyright (C) 2006-2008 Taha BEN SALAH
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 * ====================================================================
 */
package net.thevpc.dbclient.plugin.toolbox.settings.session;

import net.thevpc.common.swing.layout.DumbGridBagLayout;
import net.thevpc.dbclient.api.DBCApplication;
import net.thevpc.dbclient.api.DBCSession;
import net.thevpc.dbclient.api.configmanager.DBCSessionSettingsComponent;
import net.thevpc.common.prs.plugin.Initializer;
import net.thevpc.common.prs.plugin.Inject;
import net.thevpc.dbclient.api.viewmanager.DBCNamedFormat;
import net.thevpc.dbclient.api.viewmanager.DBCPluggablePanel;
import net.thevpc.dbclient.plugin.system.viewmanager.ComponentFormatEditor;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 7 févr. 2007 04:38:45
 */
public class DBCSQLResultsSettingsComponent extends DBCPluggablePanel implements DBCSessionSettingsComponent {

    private ComponentFormatEditor editor;
    @Inject
    private DBCSession session;
    @Inject
    private DBCApplication app;
    private DBCNamedFormat[] namedFormats;
    private JList list;
    private JTextField maxRowsCount;
    private JTextField nullString;

    public DBCSQLResultsSettingsComponent() {
    }

    public String getId() {
        return "SQLResultsConfig";
    }

    public String getTitle() {
        return "SQL Results";
    }

    public JComponent getComponent() {
        return this;
    }

    public Icon getIcon() {
        return session.getView().getIconSet().getIconR("Preferences");
    }

    @Initializer
    public void init() {
        maxRowsCount = new JTextField();
        nullString = new JTextField();
        editor = new ComponentFormatEditor(null);
        namedFormats = new DBCNamedFormat[]{
                new DBCNamedFormat(-1, "ResultTable.formatDefault", "Default Cells"),
                new DBCNamedFormat(1, "ResultTable.formatNull", "Null Cells"),
                new DBCNamedFormat(2, "ResultTable.formatKey", "Key Cells"),
                new DBCNamedFormat(3, "ResultTable.formatForeign", "Foreign Cells")


                ,
        };
        list = new JList(
                namedFormats
        );
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JPanel cellsConfig = new JPanel();
        cellsConfig.setLayout(new DumbGridBagLayout(
                "[^$List+=][^Editor]").setInsets(".*", new Insets(4, 4, 4, 4)));
        cellsConfig.setBorder(BorderFactory.createTitledBorder("Table Cell Formats"));
        cellsConfig.add(new JScrollPane(list), "List");
        cellsConfig.add(editor, "Editor");
        list.setSelectedIndex(0);
        list.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int index = list.getSelectedIndex();
                    editor.setFormat(namedFormats[index].getComponentFormat());
                }
            }
        });
        JPanel other = new JPanel(new DumbGridBagLayout().addLine("[<MaxRowsCountLabel][<-=MaxRowsCount]").addLine("[<NullStringLabel][<-=NullString]").setInsets(".*", new Insets(2, 2, 2, 2)));
        other.add(new JLabel("Max Rows Count"), "MaxRowsCountLabel");
        other.add(new JLabel("Null String"), "NullStringLabel");
        other.add(maxRowsCount, "MaxRowsCount");
        other.add(nullString, "NullString");

        other.setBorder(BorderFactory.createTitledBorder("Options"));
        setLayout(new DumbGridBagLayout()
                .addLine("[<+=other]")
                .addLine("[<~+=$$cellsConfig]"));
        add(other, "other");
        add(cellsConfig, "cellsConfig");
    }

    public void loadConfig() {
        if (session != null) {
            int t = session.getConfig().getIntegerProperty("ui.result.rowsLimit", -1); //sessionDTO.getSesUiRowsLimit();
            maxRowsCount.setText(t <= 0 ? "" : String.valueOf(t));
            nullString.setText(session.getConfig().getStringProperty("ui.format.null", null));
        } else {
            //DBCSessionInfo sessionDTO = ;
            //String t = app.getConfig().getStringProperty("defaultsession.maxRows");
            //maxRowsCount.setText(t <= 0 ? "" : String.valueOf(t));
            //nullString.setText(sessionDTO.getSesUiFormatNull());

        }

    }

    public void saveConfig() {
        int maxRowsCountValue = 0;
        try {
            maxRowsCountValue = Integer.parseInt(maxRowsCount.getText());
        } catch (NumberFormatException e) {
            //
        }
        session.getConfig().setIntegerProperty("ui.result.rowsLimit", maxRowsCountValue);
        session.getConfig().setStringProperty("ui.format.null", nullString.getText());
    }

    public int getPosition() {
        return 0;
    }

    public boolean acceptSession(DBCSession session) {
        return true;
    }
}
