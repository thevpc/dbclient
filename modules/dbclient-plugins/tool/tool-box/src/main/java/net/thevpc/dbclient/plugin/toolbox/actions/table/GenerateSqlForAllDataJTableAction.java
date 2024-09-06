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

package net.thevpc.dbclient.plugin.toolbox.actions.table;

import net.thevpc.dbclient.api.DBCSession;
import net.thevpc.dbclient.api.actionmanager.DBCActionLocation;
import net.thevpc.dbclient.api.actionmanager.DBCResultTableAction;
import net.thevpc.dbclient.api.sessionmanager.DBCSQLEditorPane;
import net.thevpc.dbclient.api.sessionmanager.DBCSessionView;
import net.thevpc.dbclient.api.sql.SQLRecord;
import net.thevpc.dbclient.api.sql.objects.DBTable;

import java.awt.event.ActionEvent;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 26 avr. 2006 22:55:22
 */
public class GenerateSqlForAllDataJTableAction extends DBCResultTableAction {
    public GenerateSqlForAllDataJTableAction() {
        super("GenerateSqlForAllDataJTableAction");
        addLocationPath(DBCActionLocation.POPUP, "/sql-ddl");
    }

    public void actionPerformedImpl(ActionEvent ae) throws Throwable {
        DBTable explorerObject = getResultTable().getModel().getTableNode();
        if (explorerObject != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("--generated\n");
            for (int i = 0; i < getTableComponent().getModel().getRowCount(); i++) {
                SQLRecord v = getResultTable().getRecord(i);
                String q = getSession().getConnection().getSQLInsertRecord(
                        explorerObject.getCatalogName(), explorerObject.getSchemaName(), explorerObject.getName()
                        , v, null);
                sb.append(q).append(";\n");
            }
            DBCSession session = getResultTable().getSession();
            DBCSQLEditorPane tabFrame = getPluginSession().getSession().getFactory().newInstance(DBCSQLEditorPane.class);
            tabFrame.getEditor().setSQL(sb.toString());
            session.getView().addWindow(
                    tabFrame, DBCSessionView.Side.Workspace, true);
        }
    }

}
