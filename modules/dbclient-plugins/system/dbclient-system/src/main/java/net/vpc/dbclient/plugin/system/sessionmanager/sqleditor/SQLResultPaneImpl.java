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
package net.vpc.dbclient.plugin.system.sessionmanager.sqleditor;

import net.vpc.dbclient.api.DBCSession;
import net.vpc.dbclient.api.pluginmanager.DBCFactory;
import net.vpc.prs.plugin.Initializer;
import net.vpc.prs.plugin.Inject;
import net.vpc.dbclient.api.sessionmanager.*;
import net.vpc.dbclient.api.sql.*;
import net.vpc.dbclient.api.sql.parser.SQLStatement;
import net.vpc.dbclient.api.sql.parser.SQLToken;
import net.vpc.dbclient.api.sql.parser.SQLTokenGroup;
import net.vpc.dbclient.api.sql.util.ReaderProvider;
import net.vpc.dbclient.api.viewmanager.DBCApplicationMessageDialogManager;
import net.vpc.dbclient.api.viewmanager.DBCExecutionPlanComponent;
import net.vpc.dbclient.api.viewmanager.DBCPluggablePanel;
import net.vpc.dbclient.plugin.system.SystemUtils;
import net.vpc.swingext.PRSManager;
import net.vpc.swingext.JChronometerLabel;
import net.vpc.swingext.dialog.MessageDialogType;
import net.vpc.swingext.dialog.MessageDiscardContext;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 13 juil. 2005 15:24:22
 */
public class SQLResultPaneImpl extends DBCPluggablePanel implements DBCSQLResultPane {

    @Inject
    private DBCSession session;
    private DBCSessionLog log;
    protected BatchProcessorListener logWriter = new LogBatchProcessorListener();
    private DBCResultBrowser resultsFrame;
    protected JToolBar tools;
    protected StatementProcessor statementProcessor = new DefaultStatementProcessor();
    protected DBCExecutionPlanComponent executionPlanComponent;
    protected Footer footer;

    private static String messagesPanelId = "SQLCommandPane.MessagesPanel";
    private static String gridsPanelId = "SQLCommandPane.GridsPanel";
    private static String executionPlanPanelId = "SQLCommandPane.ExecutionPlanPanel";

    //    private class SimplestatusBar extends Box{
//        Hashtable<String,Component> compsMap=new Hashtable<String, Component>();
//        private SimplestatusBar() {
//            super(BoxLayout.X_AXIS);
//        }
//
//        void addStatusLabel(String name){
//
//        }
//        void addStatusComponent(String name, Component c){
//            add(c);
//            compsMap.put(name,c);
//        }
//
//
//

    //    }


    @Initializer
    private void init() throws SQLException {
        log = getSession().getFactory().newInstance(DBCSessionLog.class);
        log.setSession(getSession());
        tools = new JToolBar();
        tools.setRollover(true);
        tools.setFloatable(false);
        footer = new Footer();
        footer.init(this);

        resultsFrame = getSession().getFactory().newInstance(DBCResultBrowser.class);
        resultsFrame.init(DBCSessionView.Side.Workspace);
        resultsFrame.setLog(log);
        JTabbedPane tabbedSplitPane = new JTabbedPane();

        tabbedSplitPane.addTab(messagesPanelId, log.getComponent());
        tabbedSplitPane.addTab(gridsPanelId, resultsFrame.getComponent());
        if (getSession().getConnection().getFeatureExecutionPlan().isSupported()) {
            executionPlanComponent = getSession().getFactory().newInstance(DBCExecutionPlanComponent.class);
            tabbedSplitPane.addTab(executionPlanPanelId, executionPlanComponent.getComponent());
        }
        tabbedSplitPane.setSelectedIndex(0);

        this.setLayout(new BorderLayout());
        this.add(tools, BorderLayout.PAGE_START);
        this.add(tabbedSplitPane, BorderLayout.CENTER);
        this.add(footer, BorderLayout.PAGE_END);
        for (DBCSQLResultPaneTool implementation : getSession().getFactory().createImplementations(DBCSQLResultPaneTool.class)) {
            DBCSQLResultPaneTool t;
            try {
                t = implementation;
                t.init(this);
                this.tools.add(t.getComponent());
            } catch (Exception e) {
                //ignore
            }
        }
        PRSManager.update(this, session.getView());

    }

    public SQLResultPaneImpl() {
    }

    public DBCSession getSession() {
        return session;
    }

    public void setExecutionPlan(DBCExecutionPlan plan) {
        if (executionPlanComponent != null) {
            executionPlanComponent.setExecutionPlan(plan);
        }
    }

    public JComponent getComponent() {
        return this;
    }

    protected class DefaultStatementProcessor implements StatementProcessor {

        public void executeStatement(SQLStatement q, int index) throws SQLException {
            log.trace("Query : " + q.toSQL());
            resultsFrame.executeStatement(q.toSQL(), index == 0);
        }
    }

    public void executeSQL(ReaderProvider provider) throws IOException {
        BatchProcessor processor = null;
        try {
            processor = createBatchProcessor();
            processor.execute(provider, false);
        } finally {
            if (processor != null) {
                processor.close();
            }
        }

    }

    protected class LogBatchProcessorListener extends BatchProcessorAdapter {

        public void failedToStart(BatchProcessor processor, Throwable throwable) {
            log.error("Error to start process");
        }

        @Override
        public boolean afterExecutingStatement(BatchProcessor processor, int i, SQLStatement q, SQLException th, MessageDiscardContext messageDiscardContext) {
            DBCApplicationMessageDialogManager.ReturnType type = DBCApplicationMessageDialogManager.ReturnType.OK;
            if (th != null) {
                SQLToken[] tokens = q.getTokens();
                SQLToken firstToken = null;
                for (SQLToken token : tokens) {
                    int g = token.getGroup();
                    if (g != SQLTokenGroup.COMMENTS && g != SQLTokenGroup.WHITE) {
                        firstToken = token;
                        break;
                    }
                }
                if (firstToken == null) {
                    log.error("Error executing statement #" + (i + 1));
                    log.error("<SQL>\n");
                    log.error(q.toSQL());
                    log.error("</SQL>\n");
                    log.error(th);
                    String sqlPart = q.toSQL().trim().split("\n")[0];
                    if (sqlPart.length() > 50) {
                        //
                        sqlPart = sqlPart.substring(0, 50) + "...";
                        String message = th.getMessage();
                        if (message != null) {
                            sqlPart = sqlPart + "\n" + message.trim();
                        }
                    }
                    sqlPart = "Error executing statement #" + (i + 1) + " \n" + sqlPart;
                    type = getSession().getView().getDialogManager().showMessage(null, sqlPart, MessageDialogType.ERROR, null, th, messageDiscardContext);
                } else {
                    int row = firstToken.getRow() + 1;
                    int col = firstToken.getColumn() + 1;
                    log.error("Error executing statement #" + (i + 1) + " (line=" + row + " ; column=" + col + ")");
                    log.error("<SQL>\n");
                    log.error(q.toSQL());
                    log.error("</SQL>\n");
                    log.error(th);
                    String sqlPart = q.toSQL().trim().split("\n")[0];
                    if (sqlPart.length() > 50) {
                        //
                        sqlPart = sqlPart.substring(0, 50) + "...";
                        String message = th.getMessage();
                        if (message != null) {
                            sqlPart = sqlPart + "\n" + message.trim();
                        }
                    }
                    sqlPart = "Error executing statement #" + (i + 1) + " (line=" + row + " ; column=" + col + ") \n" + sqlPart;
                    type = getSession().getView().getDialogManager().showMessage(null, sqlPart, MessageDialogType.ERROR, null, th, messageDiscardContext);
                }
                //queryTextArea.getComponent().setSelectionEnd(q.getCharEndIndex());
            }
            switch (type) {
                case CANCEL: {
                    return false;
                }
            }
            return true;
        }
    }


    public static JToggleButton prepareToolbarButton(JToggleButton button) {
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setMargin(new Insets(0, 0, 0, 0));
        return SystemUtils.prepareToolbarButton(button);
    }

    public BatchProcessor createBatchProcessor() throws IOException {
        DBCFactory factory = getSession().getFactory();
        BatchProcessor processor = factory.newInstance(BatchProcessor.class);
        processor.init(statementProcessor);
        processor.setPauseOnError(false);
        processor.addBatchProcessorListener(logWriter);
        processor.addBatchProcessorListener(footer);
        return processor;
    }

    private class Footer extends JPanel implements BatchProcessorListener {
        private JProgressBar progressBar = new JProgressBar();
        private StatusLabel statusLabel;
        protected JLabel countErrorsLabel = new JLabel();
        protected JLabel countSuccessesLabel = new JLabel();
        protected JLabel countAllLabel = new JLabel();
        private JChronometerLabel chronoLabel = new JChronometerLabel();

        private Footer() {
            super(new GridBagLayout());
            statusLabel = new StatusLabel();

            int gridx = 0;
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(3, 3, 3, 3);
            gbc.gridx = gridx++;
            gbc.gridy = 0;
            gbc.weightx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            this.add(progressBar, gbc);

            gbc.gridx = gridx++;
            gbc.gridy = 0;
            gbc.weightx = 0;
            this.add(createSeparator(), gbc);

            gbc.gridx = gridx++;
            gbc.gridy = 0;
            gbc.weightx = 0;
            this.add(countAllLabel, gbc);

            gbc.gridx = gridx++;
            gbc.gridy = 0;
            gbc.weightx = 0;
            this.add(createSeparator(), gbc);

            gbc.gridx = gridx++;
            gbc.gridy = 0;
            gbc.weightx = 0;
            this.add(countSuccessesLabel, gbc);


            gbc.gridx = gridx++;
            gbc.gridy = 0;
            gbc.weightx = 0;
            this.add(createSeparator(), gbc);

            gbc.gridx = gridx++;
            gbc.gridy = 0;
            gbc.weightx = 0;
            this.add(countErrorsLabel, gbc);


            gbc.gridx = gridx++;
            gbc.gridy = 0;
            gbc.weightx = 0;
            this.add(createSeparator(), gbc);
            gbc.gridx = gridx++;
            gbc.gridy = 0;
            gbc.weightx = 0;
            this.add(chronoLabel, gbc);
            gbc.gridx = gridx++;
            gbc.gridy = 0;
            gbc.weightx = 0;
            this.add(createSeparator(), gbc);
            gbc.gridx = gridx++;
            gbc.gridy = 0;
            gbc.weightx = 0;
            this.add(statusLabel, gbc);

            countAllLabel.setText("0");
            countErrorsLabel.setText("0");
            countSuccessesLabel.setText("0");
            chronoLabel.setText("00:00");
            countErrorsLabel.setForeground(Color.RED);
            countSuccessesLabel.setForeground(Color.GREEN.darker());
            //TODO : localize me
            countAllLabel.setToolTipText("All Statements Count");
            countErrorsLabel.setToolTipText("Failed Statements Count");
            countSuccessesLabel.setToolTipText("Successful Statements Count");
            chronoLabel.setToolTipText("Execution time");
        }

        void init(DBCSQLResultPane pane) {
            statusLabel.init(session);
            setStatusSuccess(DBCRunStatus.Empty);
        }

        private Component createSeparator() {
//        return new JSeparator(SwingConstants.VERTICAL);
            JLabel sep = new JLabel("|");
//        sep.setBorder(BorderFactory.createEtchedBorder());
            return sep;
        }

        public void setStatusSuccess(DBCRunStatus ok) {
            statusLabel.setStatusSuccess(ok);
        }

        public void failedToStart(BatchProcessor processor, Throwable throwable) {

        }

        public boolean beforeExecutingStatement(BatchProcessor processor, int i, SQLStatement q) {
            return true;
        }

        public boolean afterExecutingStatement(BatchProcessor processor, int i, SQLStatement q, SQLException th, MessageDiscardContext messageDiscardContext) {
            progressBar.setValue(i);
            return true;
        }

        public void estimating(BatchProcessor processor) {
            progressBar.setStringPainted(true);
            progressBar.setString("estimating...");
            progressBar.setIndeterminate(true);
        }

        public void propertyChange(PropertyChangeEvent evt) {
            BatchProcessor p = (BatchProcessor) evt.getSource();
            revalidateShowing(p);
        }

        public void revalidateShowing(BatchProcessor processor) {
            countAllLabel.setText(String.valueOf(processor.getStatementsCount()));
            countErrorsLabel.setText(String.valueOf(processor.getStatementsFailedCount()));
            countSuccessesLabel.setText(String.valueOf(processor.getStatementsSucceededCount()));
        }

        public void ended(BatchProcessor processor) {
            progressBar.setString("");
            progressBar.setIndeterminate(false);
            chronoLabel.stop();
            setStatusSuccess(processor.getStatementsFailedCount() > 0 ? DBCRunStatus.Error : DBCRunStatus.Success);
        }

        public void started(BatchProcessor processor) {
            int max = processor.getStatementsCount();
            setStatusSuccess(DBCRunStatus.Running);
            progressBar.setIndeterminate(false);
            progressBar.setMinimum(0);
            progressBar.setMaximum(max - 1);
            progressBar.setValue(0);
            progressBar.setString("running...");
            chronoLabel.start();
        }

    }
}