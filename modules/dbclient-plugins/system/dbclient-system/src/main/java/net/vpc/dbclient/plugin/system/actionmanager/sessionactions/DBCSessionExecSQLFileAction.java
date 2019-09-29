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

package net.vpc.dbclient.plugin.system.actionmanager.sessionactions;

import net.vpc.dbclient.api.actionmanager.DBCActionLocation;
import net.vpc.dbclient.api.actionmanager.DBCSessionAction;
import net.vpc.dbclient.api.sessionmanager.DBCInternalWindow;
import net.vpc.dbclient.api.sessionmanager.DBCSQLResultPane;
import net.vpc.dbclient.api.sessionmanager.DBCSessionView;
import net.vpc.dbclient.api.sql.util.FileReaderProvider;
import net.vpc.dbclient.api.viewmanager.SQLFileChooser;
import net.vpc.swingext.dialog.MessageDialogType;
import net.vpc.swingext.log.TLogArea;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 31 janv. 2006 15:47:00
 */
public class DBCSessionExecSQLFileAction extends DBCSessionAction {

    public DBCSessionExecSQLFileAction() {
        super("Action.ExecSQLFileAction");
        addLocationPath(DBCActionLocation.MENUBAR, "/file");
        addLocationPath(DBCActionLocation.TOOLBAR, "/");
    }


    public void actionPerformedImpl(ActionEvent e) throws Throwable {
        final SQLFileChooser ch = getSession().getFactory().newInstance(SQLFileChooser.class);
        ch.toFileChooser().setMultiSelectionEnabled(true);
        int v = ch.toFileChooser().showOpenDialog((Component) e.getSource());
        if (v == JFileChooser.APPROVE_OPTION) {
            try {
                File[] selectedSQLFiles = ch.toFileChooser().getSelectedFiles();
                if (selectedSQLFiles != null && selectedSQLFiles.length > 0) {
                    final DBCSQLResultPane resultPane = getPluginSession().getSession().getFactory().newInstance(DBCSQLResultPane.class);

                    final TLogArea filesTrace = new TLogArea();

                    JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
                    split.setTopComponent(new JScrollPane(filesTrace));
                    split.setBottomComponent(resultPane.getComponent());
                    split.setDividerSize(2);
                    split.setDividerLocation(0.3);

                    DBCInternalWindow window = getSession().getView().addWindow(
                            split,
                            DBCSessionView.Side.Workspace,
                            false
                    );
                    if (selectedSQLFiles.length == 1) {
                        window.setTitle(selectedSQLFiles[0].getName());
                    } else {
                        window.setTitle(selectedSQLFiles[0].getName() + "...");
                    }
                    for (File selectedSQLFile : selectedSQLFiles) {
                        try {
                            filesTrace.trace("Executing " + selectedSQLFile.getName() + " as " + selectedSQLFile.getCanonicalPath() + " ...");
                            resultPane.executeSQL(new FileReaderProvider(selectedSQLFile));
                        } catch (IOException e1) {
                            //ignore
                        }
                    }
                }
            } catch (Throwable e1) {
                getSession().getView().getDialogManager().showMessage(null, null, MessageDialogType.ERROR, null, e1);
            }
        }

    }
}
