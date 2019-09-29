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
import net.vpc.dbclient.api.sessionmanager.DBCSQLCommandPane;
import net.vpc.dbclient.api.sessionmanager.DBCSessionView;
import net.vpc.dbclient.api.viewmanager.SQLFileChooser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 31 janv. 2006 15:47:00
 */
public class DBCSessionLoadSQLFileAction extends DBCSessionAction {
    public DBCSessionLoadSQLFileAction() {
        super("Action.LoadSQLFileAction");
        addLocationPath(DBCActionLocation.MENUBAR, "/file");
        addLocationPath(DBCActionLocation.TOOLBAR, "/");
    }

    public void actionPerformedImpl(ActionEvent e) throws Throwable {
        final SQLFileChooser fileChooser = getSession().getFactory().newInstance(SQLFileChooser.class);
        fileChooser.toFileChooser().setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.toFileChooser().setMultiSelectionEnabled(true);
        if (JFileChooser.APPROVE_OPTION == fileChooser.toFileChooser().showOpenDialog(null)) {
            File[] files = fileChooser.toFileChooser().getSelectedFiles();
            for (File file : files) {
                DBCSQLCommandPane tabFrame = getPluginSession().getSession().getFactory().newInstance(DBCSQLCommandPane.class);
                tabFrame.getEditor().loadFile(file);
                DBCInternalWindow window = getSession().getView().addWindow(
                        tabFrame
                        , DBCSessionView.Side.Workspace, false);
                window.setTitle(file.getName());
            }
        }
    }

}
