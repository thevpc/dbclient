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

//package net.vpc.dbclient.plugin.dbsupport.mssqlserver;
//
//import java.awt.event.ActionEvent;
//import java.io.IOException;
//import java.sql.SQLException;
//
//import javax.swing.JButton;
//import javax.swing.JToggleButton;
//
//import net.vpc.dbclient.api.sql.BatchProcessorListener;
//import net.vpc.dbclient.session.window.sqleditor.SQLCommandPane;
//import net.vpc.dbclient.session.window.sqleditor.tool.SQLQueryTool;
//import net.vpc.dbclient.api.session.actions.AsynchActionListener;
//import net.vpc.prs.PRSManager;
//import net.vpc.util.TLog;
//
///**
// * @author Taha BEN SALAH (taha.bensalah@gmail.com)
// * @creationtime 13 juil. 2005 14:32:52
// */
//public class MSSqlServerQueryScriptTool extends SQLQueryTool {
//    private BatchProcessorListener fetchListener;
//    private JButton reorderScript;
//    private JToggleButton pauseButton;
//    private JButton stopButton;
//
//    public MSSqlServerQueryScriptTool(SQLCommandPane client, boolean collapsed) {
//        super(client, collapsed);
//
//        fetchListener = new DefaultFetchListener(getQueryPane().getProgressBar());
//        JButton reorderScript = PRSManager.createButton("DBCSessionView.MSSqlServerQueryScriptTool.StartReorder");
//        this.add(reorderScript);
//        TLog log = getSession().getLog();
//        reorderScript.addActionListener(new AsynchActionListener(log) {
//
//            public void actionPerformedImpl(ActionEvent e) {
//                try {
//                    reorderScript();
//                } catch (Exception e1) {
//                    e1.printStackTrace();
//                }
//            }
//
//        });
//
//        pauseButton = PRSManager.createToggleButton("DBCSessionView.MSSqlServerQueryScriptTool.PauseReorder");
//        this.add(pauseButton);
//        pauseButton.addActionListener(new AsynchActionListener(log) {
//            public void actionPerformedImpl(final ActionEvent e) {
//                fetchListener.setPaused(pauseButton.isSelected());
//            }
//
//        });
//
//        stopButton = PRSManager.createButton("DBCSessionView.MSSqlServerQueryScriptTool.StopReorder");
//        this.add(stopButton);
//        stopButton.addActionListener(new AsynchActionListener(log) {
//            public void actionPerformedImpl(final ActionEvent e) {
//                fetchListener.setStopped(true);
//            }
//        });
//
//    }
//
//    private void reorderScript() throws SQLException, IOException {
//        //TODO
////        SQLParser sqlparser = getQueryPane().getSession().getConnection().getParser();
////        SQLScript x = sqlparser.createScript(getQueryPane().getSQL());
////        SQLScript y = sqlparser.createScript("");
////        SQLScript onHold = sqlparser.createScript("");
////        boolean shouldUpdate = false;
////        getQueryPane().getProgressBar().setMinimum(0);
////        getQueryPane().getProgressBar().setMaximum(x.size() - 1);
////        getQueryPane().getProgressBar().setValue(0);
////        fetchListener.setStopped(false);
////        fetchListener.setPaused(pauseButton.isSelected());
////        for (int i = 0; i < x.size(); i++) {
////            fetchListener.waitIfPaused();
////            if (fetchListener.isStopped()) {
////                return;
////            }
////            ScriptFetchResult result = null;
////            result = getQueryPane().fetchBatch(
////                    (String) x.get(i),
////                    null,
////                    false,
////                    true,
////                    true,
////                    true,
////                    true,
////                    false
////            );
////            shouldUpdate |= result.shouldUpdateModel;
////            if (result.isSuccessful()) {
////                getQueryPane().getProgressBar().setValue(getQueryPane().getProgressBar().getValue() + 1);
////                y.add(x.get(i));
////                for (Iterator j = onHold.iterator(); j.hasNext();) {
////                    fetchListener.waitIfPaused();
////                    if (fetchListener.isStopped()) {
////                        return;
////                    }
////                    String q2 = (String) j.next();
////                    ScriptFetchResult r2 = getQueryPane().fetchBatch(
////                            q2,
////                            null,
////                            false,
////                            true,
////                            true,
////                            true,
////                            true,
////                            false
////                    );
////                    if (r2.isSuccessful()) {
////                        getQueryPane().getProgressBar().setValue(getQueryPane().getProgressBar().getValue() + 1);
////                        j.remove();
////                        y.add(q2);
////                    }
////                }
////            } else {
////                if (!x.isCreate(i)) {
////                    getQueryPane().getProgressBar().setValue(getQueryPane().getProgressBar().getValue() + 1);
////                    y.add("-- UNKNOWN ERROR ");
////                    y.add(x.get(i));
////                } else {
////                    onHold.add(x.get(i));
////                }
////            }
////        }
////        for (Iterator j = onHold.iterator(); j.hasNext();) {
////            String q2 = (String) j.next();
////            ScriptFetchResult r2 = getQueryPane().fetchBatch(
////                    q2,
////                    null,
////                    false,
////                    true,
////                    true,
////                    true,
////                    true,
////                    false
////            );
////            fetchListener.waitIfPaused();
////            if (fetchListener.isStopped()) {
////                return;
////            }
////            if (r2.isSuccessful()) {
////                getQueryPane().getProgressBar().setValue(getQueryPane().getProgressBar().getValue() + 1);
////                j.remove();
////                y.add(q2);
////            }
////        }
////        getQueryPane().setStatusSuccess(SQLCommandPane.RunStatus.Success);
////        for (Iterator j = onHold.iterator(); j.hasNext();) {
////            fetchListener.waitIfPaused();
////            if (fetchListener.isStopped()) {
////                return;
////            }
////            String q2 = (String) j.next();
////            y.add("-- UNRECOVRABLE ERROR ");
////            y.add(q2);
////        }
////        getQueryPane().setStatusSuccess(SQLCommandPane.RunStatus.Error);
////        if (shouldUpdate) {
////            getQueryPane().getSession().getViewManager().refreshView();
////        }
////        getQueryPane().addQueryToHistory(getQueryPane().getSQL());
////
////        getQueryPane().setSQL(y.getQuery());
//    }
//}
