/**
 * ==================================================================== DBClient
 * yet another Jdbc client tool
 *
 * DBClient is a new Open Source Tool for connecting to jdbc compliant
 * relational databases. Specific extensions will take care of each RDBMS
 * implementation.
 *
 * Copyright (C) 2006-2008 Taha BEN SALAH
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 * ====================================================================
 */
package net.vpc.dbclient.plugin.system.sessionmanager.sqleditor.tool;

import net.vpc.dbclient.api.actionmanager.DBCAsynchActionListener;
import net.vpc.dbclient.api.sessionmanager.DBCSQLCommandPane;
import net.vpc.dbclient.api.sessionmanager.DBCSQLEditor;
import net.vpc.dbclient.api.sql.BatchProcessor;
import net.vpc.dbclient.api.sql.util.StringReaderProvider;
import net.vpc.swingext.PRSManager;
import net.vpc.swingext.JDropDownButton;
import net.vpc.swingext.dialog.MessageDialogType;
import net.vpc.log.TLog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com) @creationtime 15 juil. 2005
 * 17:59:19
 */
public class SQLCommandPaneExecTool extends DBCSQLCommandPaneToolImpl {

    private JButton pauseButton;
    private JButton fetchButton;
    private JButton debugButton;
    private JButton continueButton;
    private JButton stepButton;
    //    private JButton pauseOnErrorButton;
    private JButton stopButton;
    private BatchProcessor processor;
    protected boolean enableExecutionPlan;
    protected JDropDownButton optionsMenu;

    public SQLCommandPaneExecTool() {
        super();
    }

    @Override
    public void init(DBCSQLCommandPane commandPane) {
        super.init(commandPane);
        PropertyChangeListener updateBreakPointsListener = new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                updateBreakPoints();
            }
        };
        getPane().getEditor().addPropertyChangeListener("lineBreakPoint.Added", updateBreakPointsListener);
        getPane().getEditor().addPropertyChangeListener("lineBreakPoint.Removed", updateBreakPointsListener);

        optionsMenu = new JDropDownButton();
        optionsMenu.setQuickActionDelay(0);
        PRSManager.addSupport(optionsMenu, "Preferences");
        optionsMenu.setQuickActionDelay(0);
        addActionButton(optionsMenu);

        JCheckBoxMenuItem singleStatementPlanMenu = PRSManager.createCheckBoxMenuItem("SQLCommandPane.SingleStatement");
        PRSManager.update(singleStatementPlanMenu, getSession().getView());
        singleStatementPlanMenu.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {
                getPane().getEditor().setSingleStatement(e.getStateChange() == ItemEvent.SELECTED);
            }
        });
        optionsMenu.add(singleStatementPlanMenu);

        try {
            if (getSession().getConnection().getFeatureExecutionPlan().isSupported()) {
                JCheckBoxMenuItem enableExecutionPlanMenu = PRSManager.createCheckBoxMenuItem("SQLCommandPane.EnableExecutionPlan");
                PRSManager.update(enableExecutionPlanMenu, getSession().getView());
                enableExecutionPlanMenu.addItemListener(new ItemListener() {

                    public void itemStateChanged(ItemEvent e) {
                        enableExecutionPlan = (e.getStateChange() == ItemEvent.SELECTED);
                    }
                });
                optionsMenu.add(enableExecutionPlanMenu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        fetchButton = PRSManager.createButton("DBCSessionView.SQLQueryDefaultTool.Fetch");
        addActionButton(fetchButton);

        fetchButton.addActionListener(new DBCAsynchActionListener(fetchButton.getName(),null, getSession()) {

            public void actionPerformedImpl(final ActionEvent e) {
                actionFetch(false);
            }
        });

        debugButton = PRSManager.createButton("DBCSessionView.SQLQueryDefaultTool.Debug");
        addActionButton(debugButton);

        debugButton.addActionListener(new DBCAsynchActionListener(debugButton.getName(),null,getSession()) {

            public void actionPerformedImpl(final ActionEvent e) {
                actionFetch(true);
            }
        });

        continueButton = PRSManager.createButton("DBCSessionView.SQLQueryDefaultTool.Continue");
        continueButton.setEnabled(false);
        addActionButton(continueButton);
        continueButton.addActionListener(new DBCAsynchActionListener(continueButton.getName(),null,getSession()) {

            public void actionPerformedImpl(final ActionEvent e) {
                if (processor != null) {
                    processor.doContinue();
                }
            }
        });


        stepButton = PRSManager.createButton("DBCSessionView.SQLQueryDefaultTool.Step");
        stepButton.setEnabled(false);
        //TODO corriger puis remettre
        addActionButton(stepButton);
        stepButton.addActionListener(new DBCAsynchActionListener(stepButton.getName(),null,getSession()) {

            public void actionPerformedImpl(final ActionEvent e) {
                if (processor != null) {
                    processor.step();
                }
            }
        });

        pauseButton = PRSManager.createButton("DBCSessionView.SQLQueryDefaultTool.Pause");
        pauseButton.setEnabled(false);
        //TODO corriger puis remettre
        addActionButton(pauseButton);
        pauseButton.addActionListener(new DBCAsynchActionListener(pauseButton.getName(),null,getSession()) {

            public void actionPerformedImpl(final ActionEvent e) {
                if (processor != null) {
                    processor.pause();
                }
            }
        });


//        pauseOnErrorButton = PRSManager.createButton("DBCSessionView.SQLQueryDefaultTool.PauseOnError");
//        pauseOnErrorButton.setEnabled(false);
//        //TODO corriger puis remettre
//        addActionButton(pauseOnErrorButton);
//        pauseOnErrorButton.addActionListener(new DBCAsynchActionListener(log) {
//
//            public void actionPerformedImpl(final ActionEvent e) {
//                BatchProcessor processor = getBatchProcessor();
//                if (processor != null) {
//                    processor.setErrorBreakPoint(pauseOnErrorButton.isSelected());
//                }
//            }
//        });

        stopButton = PRSManager.createButton("DBCSessionView.SQLQueryDefaultTool.Stop");
        stopButton.setEnabled(false);
        addActionButton(stopButton);
        stopButton.addActionListener(new DBCAsynchActionListener(stopButton.getName(),null,getSession()) {

            public void actionPerformedImpl(final ActionEvent e) {
                if (processor != null) {
                    processor.stop();
                }
            }
        });

        revalidateButtons();
    }

    public JButton getFetchButton() {
        return fetchButton;
    }

    public JButton getPauseButton() {
        return pauseButton;
    }

//    public JButton getPauseOnErrorButton() {
//        return pauseOnErrorButton;
//    }
    public JButton getStopButton() {
        return stopButton;
    }

    private void revalidateButtons() {
        BatchProcessor p = processor;
        if (p == null) {
            stopButton.setEnabled(false);
            stepButton.setEnabled(false);
            continueButton.setEnabled(false);
            pauseButton.setEnabled(false);
            fetchButton.setEnabled(true);
            debugButton.setEnabled(true);
        } else {
            stopButton.setEnabled(p.isStopEnabled());
            stepButton.setEnabled(p.isStepEnabled());
            continueButton.setEnabled(p.isContinueEnabled());
            pauseButton.setEnabled(p.isPauseEnabled());
            fetchButton.setEnabled(!p.isRunning());
            debugButton.setEnabled(!p.isRunning());
        }
    }

    private void actionFetch(boolean debug) {
        if (getPane().getSQL().trim().length() == 0) {
            return;
        }
        fetchButton.setEnabled(false);
        pauseButton.setEnabled(true);
        stepButton.setEnabled(true);
//        pauseOnErrorButton.setEnabled(true);
        stopButton.setEnabled(true);
        try {
            if (processor != null) {
                processor.removePropertyChangeListener(revalidateButtonsListener);
                processor.removePropertyChangeListener("currentLineIndex", currentLineIndexUpdateListener);
            }

            processor = getPane().createBatchProcessor();
            processor.setPauseOnError(true);
            processor.addPropertyChangeListener(revalidateButtonsListener);
            processor.addPropertyChangeListener("currentLineIndex", currentLineIndexUpdateListener);
            if (enableExecutionPlan && getSession().getConnection().getFeatureExecutionPlan().isSupported()) {
                try {
                    getSession().getConnection().getFeatureExecutionPlan().setExecutionPlanEnabled(true);
                    try {
                        processor.execute(new StringReaderProvider(getPane().getSQL()), debug);
                    } finally {
                        if (processor != null) {
                            processor.close();
                        }
                    }
                    getPane().setExecutionPlan(getSession().getConnection().getFeatureExecutionPlan().getExecutionPlan());
                } finally {
                    getSession().getConnection().getFeatureExecutionPlan().setExecutionPlanEnabled(false);
                }
            } else {
                try {
                    processor.execute(new StringReaderProvider(getPane().getSQL()), debug);
                } finally {
                    if (processor != null) {
                        processor.close();
                    }
                }
            }
        } catch (Exception e) {
            getSession().getView().getDialogManager().showMessage(null, null, MessageDialogType.ERROR, null, e);
        } finally {
            fetchButton.setEnabled(true);
            pauseButton.setEnabled(false);
            stepButton.setEnabled(false);
//            pauseOnErrorButton.setEnabled(false);
            stopButton.setEnabled(false);
        }
    }
    PropertyChangeListener revalidateButtonsListener = new PropertyChangeListener() {

        public void propertyChange(PropertyChangeEvent evt) {
            revalidateButtons();
        }
    };
    PropertyChangeListener currentLineIndexUpdateListener = new PropertyChangeListener() {

        public void propertyChange(PropertyChangeEvent evt) {
            int oldIndex = (Integer) evt.getOldValue();
            int newIndex = (Integer) evt.getNewValue();
            DBCSQLEditor e = getPane().getEditor();
            if (oldIndex > 0) {
                e.setLineImage(oldIndex, "currentLineIndex", null);
            }
            if (newIndex > 0) {
                ImageIcon ico = (ImageIcon) getSession().getView().getIconSet().getIcon("AnchorCurrentLineIndex");
                e.setLineImage(newIndex, "currentLineIndex", ico.getImage());
            }

        }
    };

    private void updateBreakPoints() {
        if (processor != null) {
            processor.setBreakPoints(getPane().getEditor().getLineBreakPoints());
        }
    }
}