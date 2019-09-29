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

package net.vpc.dbclient.plugin.tool.importexport.actions;

import net.vpc.dbclient.api.actionmanager.DBCActionLocation;
import net.vpc.dbclient.api.actionmanager.DBCTreeNodeAction;
import net.vpc.dbclient.api.sql.objects.DBObject;
import net.vpc.dbclient.api.sql.objects.DBTable;
import net.vpc.dbclient.plugin.tool.importexport.ExportConfig;
import net.vpc.dbclient.plugin.tool.importexport.ExportModel;
import net.vpc.dbclient.plugin.tool.importexport.ExportPanel;
import net.vpc.dbclient.plugin.tool.importexport.ExportResultSetModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.Statement;

public class TableExportNodeAction extends DBCTreeNodeAction {

    public TableExportNodeAction() {
        super("Action.TableExportNodeAction");
        addLocationPath(DBCActionLocation.POPUP, "/");
    }

    public boolean shouldEnable(DBObject activeNode, DBObject[] selectedNodes) {
        return (activeNode != null && activeNode instanceof DBTable);
    }

    public void actionPerformedImpl(ActionEvent e) throws Throwable {
        ResultSet rs = null;
        Statement statement = null;
        for (DBObject object : getSelectedNodes()) {
            try {
                statement = getSession().getConnection().createStatement();
                rs = statement.executeQuery("Select * from " + object.getFullName());

                ExportModel model = new ExportResultSetModel(rs);
                ExportPanel exportPanel = new ExportPanel(getSession(), model);

                if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(null, exportPanel, "Exporting " + object.getFullName() + "...", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE)) {
                    ExportConfig config = exportPanel.getConfig();
                    if (config != null) {
                        exportPanel.getExporter().exportTableModel(config, model);
                    }
                }
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (statement != null) {
                    statement.close();
                }
            }
        }
    }
}