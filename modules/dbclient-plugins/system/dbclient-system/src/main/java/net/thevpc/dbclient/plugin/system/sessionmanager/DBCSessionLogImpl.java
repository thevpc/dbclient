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

package net.thevpc.dbclient.plugin.system.sessionmanager;

import net.thevpc.common.swing.dialog.JFindReplaceDialog;
import net.thevpc.common.swing.prs.ComponentResourcesUpdater;
import net.thevpc.common.swing.text.TextManipSupport;
import net.thevpc.dbclient.api.DBCSession;
import net.thevpc.dbclient.api.sessionmanager.DBCSessionLog;
import net.thevpc.dbclient.api.viewmanager.DBCPluggablePanel;
import net.thevpc.dbclient.plugin.system.SystemUtils;
import net.thevpc.dbclient.plugin.system.sessionmanager.table.StyledConsoleRedirectorOutputStream;
import net.thevpc.common.swing.prs.PRSManager;
import net.thevpc.common.prs.iconset.IconSet;
import net.thevpc.common.prs.messageset.MessageSet;
import net.thevpc.common.prs.log.TLogPrintStream;

import javax.swing.*;
import java.awt.*;
import java.io.PrintStream;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 13 juil. 2005 14:32:52
 */
public class DBCSessionLogImpl extends DBCPluggablePanel implements DBCSessionLog {


    protected TLogPrintStream uilog;
//    protected TLog log;
    private JTextPane logTextArea;
    private JFindReplaceDialog findReplaceDialog;
    private TextManipSupport support;
    JToggleButton debugEnabled = new JToggleButton("debug", false);
    JToggleButton errorEnabled = new JToggleButton("error", true);
    JToggleButton traceEnabled = new JToggleButton("trace", true);
    JToggleButton warningEnabled = new JToggleButton("warning", true);
//    private Vector<Action> actions=new Vector<Action>();

    @SuppressWarnings({"IOResourceOpenedButNotSafelyClosed"})
    public DBCSessionLogImpl() {
        super(new BorderLayout());
        SystemUtils.prepareToolbarButton(debugEnabled);
        SystemUtils.prepareToolbarButton(errorEnabled);
        SystemUtils.prepareToolbarButton(traceEnabled);
        SystemUtils.prepareToolbarButton(warningEnabled);
        logTextArea = new JTextPane();
        support = new TextManipSupport(logTextArea);
        logTextArea.putClientProperty("clearEnabled", Boolean.TRUE);
//        for (MenuElement menuElement : support.getPopup().getSubElements()) {
//            if (menuElement instanceof JMenuItem) {
//                JMenuItem jmi = (JMenuItem) menuElement;
//                Action action = jmi.getAction();
//                if (action != null && MessageSetManager.isMessageSetSupported(action)) {
//                    PRSManager.addSupport(action, (String) action.getValue(Action.NAME));
//                }
//            }
//        }

        logTextArea.setEditable(false);
        PRSManager.addSupport(logTextArea, "LogTextArea", new ComponentResourcesUpdater() {
            public void update(JComponent comp, String id, MessageSet messageSet, IconSet iconSet) {
                PRSManager.update(support.getActions(), messageSet, iconSet);
            }
        });
        findReplaceDialog = new JFindReplaceDialog(null, logTextArea);
        findReplaceDialog.installKeyBindings();
        JScrollPane logTextAreaScrollPane = new JScrollPane(logTextArea);
//        logTextAreaScrollPane.setBorder(null);
//        System.setOut(

        uilog = new TLogPrintStream();

        uilog.setErrorStream(new PrintStream(new StyledConsoleRedirectorOutputStream(logTextArea, Color.RED, true) {
            protected void showError() {
                super.showError();
//                int x=getTabIndex();
//                if(x>=0){
//                    getTabbedPane().setSelectedIndex(x);
//                }
            }
        }));
        uilog.setWarnStream(new PrintStream(new StyledConsoleRedirectorOutputStream(logTextArea, Color.RED.darker().darker(), true)));
        uilog.setTraceStream(new PrintStream(new StyledConsoleRedirectorOutputStream(logTextArea, Color.BLUE.darker(), true)));
        uilog.setDebugStream(new PrintStream(new StyledConsoleRedirectorOutputStream(logTextArea, Color.DARK_GRAY, true)));

//        JToolBar t = new JToolBar();
//        t.setFloatable(false);
//        JButton clearText = PRSManager.createButton("Clear");
//        t.add(clearText);
//        add(t, BorderLayout.PAGE_START);
//        clearText.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                logTextArea.setText("");
//            }
//        });
        add(logTextAreaScrollPane, BorderLayout.CENTER);
        JToolBar toobar = new JToolBar(JToolBar.HORIZONTAL);
        toobar.setFloatable(false);
        toobar.add(debugEnabled);
        toobar.add(traceEnabled);
        toobar.add(warningEnabled);
        toobar.add(errorEnabled);
        add(toobar, BorderLayout.PAGE_START);
        setPreferredSize(new Dimension(600, 100));
    }

    public void setSession(DBCSession session) {
        support.setDateFormat(session.getConfig().getDateFormat());
        support.setTimeFormat(session.getConfig().getTimeFormat());
        support.setDateTimeFormat(session.getConfig().getTimestampFormat());
//        try {
//            TLog fileLog=new TLogPrintStream(new File(session.getApplication().getVarDir(),"sessions/"+session.getInfo().getId()+"/log/session.log"));
//            log=new TBufferedLog(fileLog);
//        } catch (Exception ex) {
//            session.getApplication().getLogger(getClass().getName()).log(Level.SEVERE,"Unable to Initilize Session Log File",ex);
//            log=session.getApplication().getLog();
//        }
    }

    public void debug(Object msg) {
        if (debugEnabled.isSelected()) {
            uilog.debug(msg);
        }
//        log.debug(msg);
    }

    public void error(Object msg) {
        if (errorEnabled.isSelected()) {
            uilog.error(msg);
        }
//        log.error(msg);
    }


    public void trace(Object msg) {
        if (traceEnabled.isSelected()) {
            uilog.trace(msg);
        }
//        log.trace(msg);
    }

    public void warning(Object msg) {
        if (warningEnabled.isSelected()) {
            uilog.warning(msg);
        }
//        log.warning(msg);
    }

    public void updateUI() {
        super.updateUI();
        if (support != null) {
            support.updateUI();
        }
    }

    public JComponent getComponent() {
        return this;
    }

    public void error(String message, Object msg) {
        if (errorEnabled.isSelected()) {
            uilog.error(message);
            uilog.error(msg);
        }
//        log.error(message);
//        log.error(msg);
    }

    public void close() {
       uilog.close();
//       log.close();
    }
}
