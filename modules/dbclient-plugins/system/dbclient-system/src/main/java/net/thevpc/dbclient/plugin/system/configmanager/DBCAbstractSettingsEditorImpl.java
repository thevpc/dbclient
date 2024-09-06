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
package net.thevpc.dbclient.plugin.system.configmanager;

import java.awt.event.ComponentEvent;

import net.thevpc.common.swing.panel.JListCardPanel;
import net.thevpc.dbclient.api.DBCApplication;
import net.thevpc.dbclient.api.DBCSession;
import net.thevpc.dbclient.api.DBClientContext;
import net.thevpc.dbclient.api.configmanager.DBCApplicationSettingsComponent;
import net.thevpc.dbclient.api.configmanager.DBCSessionSettingsComponent;
import net.thevpc.dbclient.api.configmanager.DBCSettingsComponent;
import net.thevpc.dbclient.api.viewmanager.DBCPluggablePanel;
import net.thevpc.common.swing.prs.PRSManager;
import net.thevpc.common.prs.plugin.Initializer;
import net.thevpc.common.prs.plugin.Inject;
import net.thevpc.common.swing.dialog.MessageDialogType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com) @creationtime 6 févr. 2007
 * 23:54:24
 */
public abstract class DBCAbstractSettingsEditorImpl extends DBCPluggablePanel {

    private JListCardPanel card;
    @Inject
    private JButton saveButton;
    private JButton loadButton;
    public JProgressBar progressBar;

    public DBCAbstractSettingsEditorImpl() {
    }

    public JComponent getComponent() {
        return this;
    }

    protected synchronized void startProgress() {
        progressBar.setIndeterminate(true);
        saveButton.setEnabled(false);
        loadButton.setEnabled(false);
    }

    protected synchronized void endProgress() {
        progressBar.setIndeterminate(false);
        saveButton.setEnabled(true);
        loadButton.setEnabled(true);
    }

    protected void run(String name, String desc, Runnable r) {
        new Thread(r).start();
    }

    private ActionListener createActionListener(final String name, final String desc, final Runnable r) {
        return new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                run(name, desc, new Runnable() {

                    @Override
                    public void run() {
                        startProgress();
                        try {
                            r.run();
                        } catch (Exception e1) {
                            getContext().getView().getDialogManager().showMessage(null, null, MessageDialogType.ERROR, null, e1);
                        } finally {
                            endProgress();
                        }
                    }
                });
            }
        };
    }

    @Initializer
    private void init() {
        progressBar = new JProgressBar();
        saveButton = PRSManager.createButton("Save");
        loadButton = PRSManager.createButton("Refresh");
        saveButton.setEnabled(false);
        loadButton.setEnabled(false);
        saveButton.addActionListener(createActionListener(saveButton.getText(), saveButton.getText(), new Runnable() {

            @Override
            public void run() {
                saveConfig();
            }
        }));
        loadButton.addActionListener(createActionListener(loadButton.getText(), loadButton.getText(), new Runnable() {

            @Override
            public void run() {
                loadConfig();
            }
        }));
        card = new JListCardPanel();
        card.getSplitPane().addComponentListener(new ComponentListener() {

            @Override
            public void componentResized(ComponentEvent e) {
                card.getSplitPane().setDividerLocation(0.3);
            }

            @Override
            public void componentMoved(ComponentEvent e) {
            }

            @Override
            public void componentShown(ComponentEvent e) {
            }

            @Override
            public void componentHidden(ComponentEvent e) {
            }
        });
//        card.getSplitPane().setDividerSize(2);
        card.getList().setVisibleRowCount(4);
        card.getList().setCellRenderer(new DefaultListCellRenderer() {

            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JListCardPanel.PanelPage i = (JListCardPanel.PanelPage) value;
                super.getListCellRendererComponent(list, i.getTitle(), index, isSelected, cellHasFocus);
                setIcon(i.getIcon());
                setVerticalTextPosition(SwingConstants.BOTTOM);
                setVerticalAlignment(SwingConstants.CENTER);
                setHorizontalAlignment(SwingConstants.CENTER);
                setHorizontalTextPosition(SwingConstants.CENTER);
                setPreferredSize(new Dimension(10, 60));
                return this;
            }
        });

        card.getSplitPane().setDividerLocation(0.3);
        card.getSplitPane().setDividerSize(4);
        card.getSplitPane().getLeftComponent().setPreferredSize(new Dimension(150, 100));
        setLayout(new BorderLayout());
        add(card, BorderLayout.CENTER);
        Box tb = Box.createHorizontalBox();
        tb.add(Box.createHorizontalStrut(5));
        tb.add(progressBar);
        tb.add(Box.createHorizontalStrut(5));
        tb.add(saveButton);
        tb.add(Box.createHorizontalStrut(2));
        tb.add(loadButton);
        tb.add(Box.createHorizontalStrut(5));
        add(tb, BorderLayout.PAGE_END);
        PRSManager.update(this, getContext().getView());
        if (getContext() instanceof DBCApplication) {
            DBCApplication application = (DBCApplication) getContext();
            for (DBCApplicationSettingsComponent ii : application.getFactory().createImplementations(DBCApplicationSettingsComponent.class)) {
                addSettings(ii);
            }
            loadConfig();
            saveButton.setEnabled(true);
            loadButton.setEnabled(true);
        } else {
            DBCSession session = (DBCSession) getContext();
            session.getTaskManager().run("Adding Settings Components", null, new Runnable() {

                @Override
                public void run() {
                    DBCSession session = (DBCSession) getContext();
                    for (DBCSessionSettingsComponent implementation : session.getFactory().createImplementations(DBCSessionSettingsComponent.class)) {
                        addSettings(implementation);
                    }
                    loadConfig();
                    PRSManager.update(DBCAbstractSettingsEditorImpl.this, getContext().getView());
                    saveButton.setEnabled(true);
                    loadButton.setEnabled(true);
                }
            });
        }

    }

    public void addSettings(DBCApplicationSettingsComponent settings) {
        this.addSettings((DBCSettingsComponent) settings);
    }

    public void addSettings(DBCSessionSettingsComponent settings) {
        this.addSettings((DBCSettingsComponent) settings);
    }

    private void addSettings(DBCSettingsComponent settings) {
        settings.putClientProperty("Settings", settings);
        JPanel panel = new JPanel(new BorderLayout());
        JLabel header = new JLabel(settings.getTitle());
        header.setForeground(Color.BLACK);
        header.setBackground(Color.WHITE);
        header.setOpaque(true);
        header.setIcon(settings.getIcon());
        header.setBorder(BorderFactory.createLineBorder(Color.darkGray, 1));
        Font oldFont = header.getFont();
        header.setFont(oldFont.deriveFont(Font.BOLD, oldFont.getSize() * 1.5f));
        panel.add(header, BorderLayout.PAGE_START);
        panel.add(settings.getComponent(), BorderLayout.CENTER);
        panel.putClientProperty("Settings", settings);
        card.addPage(settings.getId(), settings.getTitle(), settings.getIcon(), panel);
    }

    public boolean isEmpty() {
        return card.getPageComponents().length == 0;
    }

    public void loadConfig() {
        for (JComponent component : card.getPageComponents()) {
            DBCSettingsComponent settings = (DBCSettingsComponent) component.getClientProperty("Settings");
            try {
                settings.loadConfig();
            } catch (Throwable e) {
                getContext().getView().getDialogManager().showMessage(null, null, MessageDialogType.ERROR, null, e);
            }
        }
    }

    public void saveConfig() {
        for (JComponent component : card.getPageComponents()) {
            DBCSettingsComponent settings = (DBCSettingsComponent) component.getClientProperty("Settings");
            try {
                settings.saveConfig();
            } catch (Throwable e) {
                e.printStackTrace();
                getContext().getView().getDialogManager().showMessage(null, null, MessageDialogType.ERROR, null, e);
            }
        }
    }

    public abstract DBClientContext getContext();
}
