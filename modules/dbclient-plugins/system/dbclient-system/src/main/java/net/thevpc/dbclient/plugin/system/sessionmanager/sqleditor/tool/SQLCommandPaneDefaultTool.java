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
package net.thevpc.dbclient.plugin.system.sessionmanager.sqleditor.tool;

import net.thevpc.dbclient.api.sessionmanager.DBCSQLCommandPane;
import net.thevpc.dbclient.api.viewmanager.SQLFileChooser;
import net.thevpc.common.swing.prs.PRSManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 15 juil. 2005 17:59:19
 */
public class SQLCommandPaneDefaultTool extends DBCSQLCommandPaneToolImpl {

    public SQLCommandPaneDefaultTool() {
        super();
    }

    @Override
    public void init(DBCSQLCommandPane client) {
        super.init(client);
        {
            JButton loadButton = PRSManager.createButton("DBCSessionView.SQLQueryFileTool.LoadFile");
            addActionButton(loadButton);
            loadButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    final SQLFileChooser ch = getSession().getFactory().newInstance(SQLFileChooser.class);
                    JFileChooser fileChooser = ch.toFileChooser();
                    File oldFile = getPane().getEditor().getFile();
                    if (oldFile != null) {
                        fileChooser.setSelectedFile(oldFile);
                    }
                    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(SQLCommandPaneDefaultTool.this)) {
                        File f = ch.getSelectedSQLFile();
                        try {
                            getPane().getEditor().loadFile(f);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            });
        }

        {
            JButton saveButton;
            saveButton = PRSManager.createButton("DBCSessionView.SQLQueryFileTool.SaveFile");
            addActionButton(saveButton);
            saveButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    final SQLFileChooser ch = getSession().getFactory().newInstance(SQLFileChooser.class);
                    JFileChooser fileChooser = ch.toFileChooser();
                    File oldFile = getPane().getEditor().getFile();
                    if (oldFile != null) {
                        fileChooser.setSelectedFile(oldFile);
                    }
                    if (JFileChooser.APPROVE_OPTION == fileChooser.showSaveDialog(SQLCommandPaneDefaultTool.this)) {
                        File f = ch.getSelectedSQLFile();
                        FileWriter fw = null;
                        try {
                            fw = new FileWriter(f);
                            fw.write(getPane().getSQL());
                            getPane().getEditor().setFile(f);
                        } catch (Exception e1) {
                            e1.printStackTrace();
//                            e1.printStackTrace(getQueryPane().getSession().getErr());
                        } finally {
                            if (fw != null) {
                                try {
                                    fw.close();
                                } catch (Exception e1) {
                                    e1.printStackTrace();
//                                    e1.printStackTrace(getQueryPane().getSession().getErr());
                                }
                            }
                        }
                    }
                }
            });
        }
        {
            JButton clearQuery = PRSManager.createButton("DBCSessionView.SQLQueryDefaultTool.Clear");
            addActionButton(clearQuery);
            clearQuery.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    getPane().setSQL("");
                }
            });
        }
    }

}
