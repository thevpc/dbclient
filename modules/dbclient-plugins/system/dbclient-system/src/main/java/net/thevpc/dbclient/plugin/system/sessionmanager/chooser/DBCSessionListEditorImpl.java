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
package net.thevpc.dbclient.plugin.system.sessionmanager.chooser;

import net.thevpc.common.swing.label.JChronometerLabel;
import net.thevpc.common.swing.layout.DumbGridBagLayout;
import net.thevpc.dbclient.api.DBCApplication;
import net.thevpc.dbclient.api.DBCApplicationView;
import net.thevpc.dbclient.api.configmanager.DBCApplicationConfig;
import net.thevpc.dbclient.api.configmanager.DBCSessionInfo;
import net.thevpc.dbclient.api.sessionmanager.DBCSessionListEditor;
import net.thevpc.dbclient.api.sessionmanager.DBCSessionListItemEditor;
import net.thevpc.dbclient.api.sessionmanager.DBCSessionListListener;
import net.thevpc.dbclient.api.sessionmanager.DBCURLBuilder;
import net.thevpc.dbclient.api.viewmanager.DBCMenuBar;
import net.thevpc.dbclient.api.viewmanager.DBCPluggablePanel;
import net.thevpc.dbclient.plugin.system.SystemUtils;
import net.thevpc.common.swing.ComponentTreeVisitor;
import net.thevpc.common.swing.prs.PRSManager;
import net.thevpc.common.prs.plugin.Initializer;
import net.thevpc.common.prs.plugin.Inject;
import net.thevpc.common.swing.dialog.MessageDialogType;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 3 dec. 2006 23:46:55
 */
public class DBCSessionListEditorImpl extends DBCPluggablePanel implements DBCSessionListEditor {

    private DBCSessionListItemEditor sessionPanel;
    @Inject
    private DBCApplication application;
    private Vector<DBCSessionListListener> listeners;
    private JLabel leftLabel;
    private boolean readOnly;
    private JList connectionList;
    private JButton newButton;
    private JButton updateButton;
    private JButton delButton;
    private JButton copyButton;
    private JButton connectButton;
    private JButton cancelButton;
    private JProgressBar progressBar;
    private JChronometerLabel timerLabel;
    private Logger logger;


    public DBCSessionListEditorImpl() {
//        System.out.println("??");
    }

    public DBCApplication getApplication() {
        return application;
    }

    public DBCURLBuilder getURLBuilder(String driver) {
        DBCURLBuilder best = null;
        int maxHint = -1;
        ArrayList<DBCURLBuilder> builders = new ArrayList<DBCURLBuilder>();
        for (DBCURLBuilder implementation : application.getFactory().createImplementations(DBCURLBuilder.class)) {
            builders.add(implementation);
        }

        for (DBCURLBuilder builder : builders) {
            int hint = builder.accept(this, driver);
            if (hint > 0 && (maxHint < 0 || best == null || maxHint < hint)) {
                maxHint = hint;
                best = builder;
            }
        }
        return best;
    }

    public JComponent getComponent() {
        return this;
    }

    protected void fireCancel() {
        if (listeners != null) {
            for (DBCSessionListListener listener : listeners) {
                listener.cancel();
            }
        }
    }

    protected void fireConnect(int sessionId) {
        if (listeners != null) {
            for (DBCSessionListListener listener : listeners) {
                listener.connect(sessionId);
            }
        }
    }

    public void addSessionChooserListener(DBCSessionListListener listener) {
        if (listeners == null) {
            listeners = new Vector<DBCSessionListListener>();
        }
        listeners.add(listener);
    }

    protected DBCSessionInfo showConnectionDialog() {
        sessionPanel.reset();
        PRSManager.update(sessionPanel.getComponent(), application.getView());
        DBCSessionListItemEditor.ActionType t = sessionPanel.getActionType();
        String title = null;
        switch (t) {
            case ADD: {
                title = getApplication().getView().getMessageSet().get2("DBCSessionListEditor.Add.windowTitle");
                break;
            }
            case UPDATE: {
                title = getApplication().getView().getMessageSet().get2("DBCSessionListEditor.Update.windowTitle", sessionPanel.getConfigName());
                break;
            }
            case CLONE: {
                title = getApplication().getView().getMessageSet().get2("DBCSessionListEditor.Clone.windowTitle", sessionPanel.getConfigName());
                break;
            }
        }
        if (JOptionPane.showOptionDialog(this, sessionPanel,
                title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null) == JOptionPane.OK_OPTION) {
            return sessionPanel.getConnectionInfo();
        } else {
            return null;
        }
    }

    public DBCSessionListItemEditor getSessionDialog() {
        if (sessionPanel == null) {
            sessionPanel = getApplication().getFactory().newInstance(DBCSessionListItemEditor.class);
            sessionPanel.init(this);
        }
        return sessionPanel;
    }

    public void updateUI() {
        super.updateUI();
        if (sessionPanel != null) {
            SwingUtilities.updateComponentTreeUI(getSessionDialog().getComponent());
        }
    }

    @Initializer
    private void init() {
        logger=application.getLogger(DBCSessionListEditorImpl.class.getName());
        newButton = SystemUtils.prepareToolbarButton(PRSManager.createButton("CreateSession"));
        updateButton = SystemUtils.prepareToolbarButton(PRSManager.createButton("UpdateSession"));
        delButton = SystemUtils.prepareToolbarButton(PRSManager.createButton("RemoveSession"));
        copyButton = SystemUtils.prepareToolbarButton(PRSManager.createButton("CopySession"));
        connectButton = PRSManager.createButton("ConnectSession");
        cancelButton = PRSManager.createButton("CancelSession");
        progressBar = new JProgressBar();
        progressBar.setIndeterminate(false);
        timerLabel = new JChronometerLabel();
        timerLabel.setBorder(BorderFactory.createEtchedBorder());
        timerLabel.setPreferredSize(new Dimension(90, 20));
        timerLabel.setFont(timerLabel.getFont().deriveFont(9f));
        timerLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        timerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
//        JToolBar toolbar = new JToolBar();
//        showConnectionInfoButton.setToolTipText("Connect");
//        IconSet preferredIconSet = PRSManager.getIconSet();
        newButton.setMargin(new Insets(0, 0, 0, 0));
        copyButton.setMargin(new Insets(0, 0, 0, 0));
        updateButton.setMargin(new Insets(0, 0, 0, 0));
        delButton.setMargin(new Insets(0, 0, 0, 0));

        setLayout(new BorderLayout());
        DumbGridBagLayout layout = new DumbGridBagLayout(
                "[BAN<+ ][LIST  $+=  :           :           :           ]\n" +
                        "[BAN   ][TOOLBAR+>= :           :           :           ]\n" +
                        "[BAN   ][+=        ]:[_>CONNECT]:[<_CANCEL ]:[+         ]:\n").setInsets("LIST", new Insets(4, 4, 4, 4)).setInsets("TOOLBAR", new Insets(4, 4, 4, 4)).setInsets("CONNECT", new Insets(36, 2, 8, 2)).setInsets("CANCEL", new Insets(2, 2, 8, 12));
        JPanel defaultPanel = new JPanel(layout);
        layout.compileWhite(defaultPanel);
        DBCMenuBar menu = getApplication().getFactory().newInstance(DBCMenuBar.class);
        ComponentTreeVisitor treeVisitor = new ComponentTreeVisitor() {

            public void visit(Component comp, Object userObject) {
                if (comp instanceof AbstractButton) {
                    AbstractButton ab = (AbstractButton) comp;
                    Action action = ab.getAction();
                    if (action != null && "Action.OpenSessionAction".equals(action.getValue(Action.ACTION_COMMAND_KEY))) {
                        comp.getParent().remove(comp);
                    }
                }
            }
        };
        PRSManager.visit(treeVisitor, menu.getMenuBarComponent(), null);
        add(menu.getMenuBarComponent(), BorderLayout.PAGE_START);
        add(defaultPanel, BorderLayout.CENTER);
        Box horizontalBox = Box.createHorizontalBox();
        horizontalBox.add(Box.createHorizontalStrut(5));
        horizontalBox.add(progressBar);
        horizontalBox.add(Box.createHorizontalStrut(5));
        horizontalBox.add(timerLabel);
        horizontalBox.add(Box.createHorizontalStrut(5));
        add(horizontalBox, BorderLayout.PAGE_END);
//        GridBagConstraints c;//= new GridBagConstraints();

//        Font font = new Font("Arial", Font.BOLD, 14);
//        Color color = Color.blue;
//        JLabel connectionLabel = new JLabel("Configuration Name");
//        connectionLabel.setFont(font);
//        connectionLabel.setForeground(color);

        Box t = Box.createHorizontalBox();
        t.add(Box.createHorizontalGlue());
        t.add(Box.createHorizontalStrut(2));
        t.add(newButton);
        t.add(Box.createHorizontalStrut(2));
        t.add(copyButton);
        t.add(Box.createHorizontalStrut(2));
        t.add(updateButton);
        t.add(Box.createHorizontalStrut(2));
        t.add(delButton);
        t.add(Box.createHorizontalStrut(2));

        connectButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                new Thread() {

                    public void run() {
                        doConnect();
                    }
                }.start();
            }
        });

        cancelButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                doCancel();
            }
        });


        newButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    doAddConnection();
                } catch (Exception e1) {

                    getApplication().getView().getDialogManager().showMessage(null, getApplication().getView().getMessageSet().get("DBCSessionListEditor.Add.error"), MessageDialogType.ERROR, null, e1);
                }
            }
        });

        copyButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    doCopyConnection();
                } catch (Exception e1) {
                    getApplication().getView().getDialogManager().showMessage(null, getApplication().getView().getMessageSet().get("DBCSessionListEditor.Copy.error"), MessageDialogType.ERROR, null, e1);
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    doUpdateConnection();
                } catch (Exception e1) {
                    getApplication().getView().getDialogManager().showMessage(null, getApplication().getView().getMessageSet().get("DBCSessionListEditor.Update.error"), MessageDialogType.ERROR, null, e1);
                }
            }
        });

        delButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    doRemoveConnection();
                } catch (Exception e1) {
                    getApplication().getView().getDialogManager().showMessage(null, getApplication().getView().getMessageSet().get("DBCSessionListEditor.Remove.error"), MessageDialogType.ERROR, null, e1);
                }
            }
        });
        application.getView().addPropertyChangeListener(DBCApplicationView.PROPERTY_ARTSET, new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                artSetChanged();
            }
        });
        defaultPanel.add(createSessionList(), "LIST");
        defaultPanel.add(connectButton, "CONNECT");
        defaultPanel.add(cancelButton, "CANCEL");
        defaultPanel.add(t, "TOOLBAR");
        leftLabel = new JLabel();
        defaultPanel.add(leftLabel, "BAN");
        artSetChanged();
        loadConfig();
        updateButtonsStatus();
        PRSManager.update(this, application.getView());
    }

    protected void artSetChanged() {
        ImageIcon image = getApplication().getView().getArtSet().getArtImage(DBCApplicationView.ARTSET_VERTICAL_BANNER_THIN);
        leftLabel.setIcon(image);
    }

    private boolean doCancel() {
        fireCancel();
        return true;
    }

    private boolean doConnect() {
        DBCSessionInfo selectedSession = getSelectedSession();
        if (selectedSession != null) {
            setReadOnly(true);
            progressBar.setIndeterminate(true);
            timerLabel.start();
            try {
                try {
                    DBCSessionInfo session1 = getSelectedSession();
                    if (session1 != null) {
                        fireConnect(session1.getId());
                    }
                } finally {
                    timerLabel.stop();
                    progressBar.setIndeterminate(false);
                    setReadOnly(false);
                }
            } catch (Exception e) {
                getApplication().getView().getDialogManager().showMessage(null, getApplication().getView().getMessageSet().get("DBCSessionListEditor.Connect.error"), MessageDialogType.ERROR, null, e);
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean doAddConnection() {
        getSessionDialog().setConnectionInfo(null);
        getSessionDialog().setReadOnly(false);
        getSessionDialog().setActionType(DBCSessionListItemEditor.ActionType.ADD);
        DBCSessionInfo info = showConnectionDialog();
        if (info != null) {
            info.setCnxCreated(new java.sql.Date(System.currentTimeMillis()));
            info.unsetId();
            getApplication().getConfig().addSession(info, DBCApplicationConfig.SESSION_TEMPLATE_NONE);
            getApplication().getConfig().setStringProperty(PRP_SESSION_ID, String.valueOf(info.getId()));
            loadConfig();
            updateButtonsStatus();
            JDialog d = (JDialog) SwingUtilities.getAncestorOfClass(JDialog.class, this);
            if (d != null) {
                d.pack();
            }
            return true;
        }
        return false;
    }

    public boolean doUpdateConnection() {
        DBCSessionInfo sn = getSelectedSession();
        if (sn != null) {
            DBCSessionInfo info = getApplication().getConfig().getSession(sn.getId());
            getSessionDialog().setReadOnly(info.isReaOnly() != null && info.isReaOnly());
            getSessionDialog().setConnectionInfo(info);
            getSessionDialog().setActionType(DBCSessionListItemEditor.ActionType.UPDATE);
            info = showConnectionDialog();
            if (info != null) {
                info.setCnxLastUpdated(new java.sql.Date(System.currentTimeMillis()));
                getApplication().getConfig().updateSession(info);
                getApplication().getConfig().setStringProperty(PRP_SESSION_ID, String.valueOf(info.getId()));
                loadConfig();
                updateButtonsStatus();
                JDialog d = (JDialog) SwingUtilities.getAncestorOfClass(JDialog.class, this);
                if (d != null) {
                    d.pack();
                }
                getApplication().getConfig().setStringProperty(PRP_SESSION_ID, String.valueOf(info.getId()));
                return true;
            }
        }
        return false;
    }

    public boolean doCopyConnection() {
        DBCSessionInfo sn = getSelectedSession();
        if (sn != null) {
            DBCSessionInfo info = getApplication().getConfig().getSession(sn.getId());
            getSessionDialog().setReadOnly(false);
            int template = info.getId();
            getSessionDialog().setConnectionInfo(info);
            getSessionDialog().setActionType(DBCSessionListItemEditor.ActionType.CLONE);
            info = showConnectionDialog();
            if (info != null) {
                info.unsetId();
                info.setCnxCreated(new java.sql.Date(System.currentTimeMillis()));
                getApplication().getConfig().addSession(info, template);
                getApplication().getConfig().setStringProperty(PRP_SESSION_ID, String.valueOf(info.getId()));
                loadConfig();
                updateButtonsStatus();
                //connectionList.setSelectedValue(n, true);
                JDialog d = (JDialog) SwingUtilities.getAncestorOfClass(JDialog.class, this);
                if (d != null) {
                    d.pack();
                }
                getApplication().getConfig().setStringProperty(PRP_SESSION_ID, String.valueOf(info.getId()));
                return true;
            }
        }
        return false;
    }

    public boolean doRemoveConnection() {
        DBCSessionInfo sn = getSelectedSession();
        if (sn != null) {
            if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, getApplication().getView().getMessageSet().get("DBCSessionListEditor.Remove.Confirm"), application.getView().getMessageSet().get("Warning"), JOptionPane.YES_NO_OPTION)) {
                if (sn.isReaOnly() != null && sn.isReaOnly()) {
                    getApplication().getView().getDialogManager().showMessage(null, getApplication().getView().getMessageSet().get("DBCSessionListEditor.Remove.ReadOnlyError"), MessageDialogType.ERROR);
                    return false;
                }
                getApplication().getConfig().removeSession(sn.getId());
                getApplication().getConfig().setStringProperty(PRP_SESSION_ID, String.valueOf(sn.getId()));
                loadConfig();
                updateButtonsStatus();
                return true;
            }
        }
        return false;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
        updateButtonsStatus();
    }

    protected void updateButtonsStatus() {
        DBCSessionInfo sn = getSelectedSession();
        connectButton.setEnabled(!readOnly && sn != null);
        cancelButton.setEnabled(!readOnly);
        newButton.setEnabled(!readOnly);
        updateButton.setEnabled(!readOnly && sn != null);
        copyButton.setEnabled(!readOnly && sn != null);
        delButton.setEnabled(!readOnly && sn != null);
    }


    protected JComponent createSessionList() {
        connectionList = new JList(new DefaultComboBoxModel());
        connectionList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (value != null && value instanceof DBCSessionInfo) {
                    value = ((DBCSessionInfo) value).getName();
                }
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        });
        connectionList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                updateButtonsStatus();
            }

        });

        return new JScrollPane(connectionList);
    }


    protected DBCSessionInfo getSelectedSession() {
        if (connectionList.getModel().getSize() > 0) {
            return (DBCSessionInfo) connectionList.getSelectedValue();
        }
        return null;
    }

    public void loadConfig() {
        DefaultComboBoxModel m = (DefaultComboBoxModel) connectionList.getModel();
        m.removeAllElements();
        DBCSessionInfo[] sessionFiles = getApplication().getConfig().getSessions();
        for (DBCSessionInfo file : sessionFiles) {
            try {
                m.addElement(file);
            } catch (Throwable ex) {
                logger.log(Level.SEVERE,"LoadConfig Filed",ex);
            }
        }
        try {
            int selectedSession = getApplication().getConfig().getIntegerProperty(PRP_SESSION_ID, -1);
            for (int j = 0; j < m.getSize(); j++) {
                DBCSessionInfo sessionInfo = (DBCSessionInfo) m.getElementAt(j);
                int id = sessionInfo.getId();
                if (id == selectedSession) {
                    connectionList.setSelectedIndex(j);
                    break;
                }
            }
        } catch (NumberFormatException e) {
            //
        }
    }

}
