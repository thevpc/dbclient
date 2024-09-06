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

package net.thevpc.dbclient.plugin.tool.importexport.actions;

import net.thevpc.dbclient.api.actionmanager.DBCActionLocation;
import net.thevpc.dbclient.api.actionmanager.DBCResultTableAction;
import net.thevpc.dbclient.api.viewmanager.DBCTable;
import net.thevpc.dbclient.plugin.tool.importexport.ExportConfig;
import net.thevpc.dbclient.plugin.tool.importexport.ExportPanel;
import net.thevpc.dbclient.plugin.tool.importexport.ExportTableModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ExportResultTableAction extends DBCResultTableAction {
//    boolean inProgress = false;

    public ExportResultTableAction() {
        super("Action.ExportResultTableAction");
        addLocationPath(DBCActionLocation.MENUBAR, "/tools");
        addLocationPath(DBCActionLocation.POPUP, "/");
//        setIcon(new ImageIcon(getClass().getResource("/net/vpc/dbclient/plugin/tool/neormf/neormf.png")));
    }

    public void actionPerformedImpl(ActionEvent e) throws Throwable {

        DBCTable tableComponent = getTableComponent();
        if(tableComponent==null){
            throw new IllegalArgumentException("No Table is Selected for export");
        }
        ExportTableModel model = new ExportTableModel(tableComponent.getModel());
        ExportPanel exportPanel = new ExportPanel(getSession(), model);

        if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(null, exportPanel, "Exporting...", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE)) {
            ExportConfig config = exportPanel.getConfig();
            if (config != null) {
                exportPanel.getExporter().exportTableModel(config, model);
            }
        }

    }
}