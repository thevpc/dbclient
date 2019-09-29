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


package net.vpc.dbclient.plugin.tool.servermanager.view;

import net.vpc.dbclient.api.DBCApplication;
import net.vpc.dbclient.api.viewmanager.DBCApplicationMessageDialogManager;
import net.vpc.dbclient.api.viewmanager.DBCPluggablePanel;
import net.vpc.dbclient.plugin.tool.servermanager.*;
import net.vpc.swingext.PRSManager;
import net.vpc.swingext.messageset.ComponentMessageSetUpdater;
import net.vpc.prs.messageset.MessageSet;
import net.vpc.swingext.JDropDownButton;
import net.vpc.swingext.dialog.MessageDialogType;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 4 juin 2007 16:23:46
 */
public class DBCServerManagerEditorImpl extends DBCPluggablePanel implements DBCServerManagerEditor {
    private DBCServerManagerPlugin serverManagerPlugin;
    private JTabbedPane pane;
    private JDropDownButton newServer;
    private JButton startServer;
    private JButton editServer;
    private JButton removeServer;
    private JButton closeButton;
    private JButton shutDownButton;
    private JList servers;
    private ComponentMessageSetUpdater updater = new ComponentMessageSetUpdater() {
        public void updateMessageSet(JComponent component, String id, MessageSet messageSet) {
            if (component instanceof JMenuItem) {
                String serverType = (String) component.getClientProperty("ServerType");
                ((JMenuItem) component).setText(serverManagerPlugin.getMessageSet().get(id, new Object[]{serverType}));
            }
        }

        public void install(JComponent comp, String id) {
            //
        }
    };

    public DBCServerManagerEditorImpl() {
        super(new BorderLayout());
    }

    public void init(DBCApplication _dbclient) {
        this.serverManagerPlugin = (DBCServerManagerPlugin) _dbclient.getPluginManager().getPlugin("tool-server-manager");
        JToolBar toobar = new JToolBar();
        toobar.setFloatable(false);
        newServer = new JDropDownButton("");
        newServer.setQuickActionDelay(0);
        PRSManager.addSupport(newServer, "DBCServerManagerEditor.AddServer");
        startServer = PRSManager.createButton("DBCServerManagerEditor.StartServer");
        startServer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startSelected();
            }
        });
        editServer = PRSManager.createButton("DBCServerManagerEditor.EditServer");
        editServer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editSelected();
            }
        });
        removeServer = PRSManager.createButton("DBCServerManagerEditor.RemoveServer");
        removeServer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeSelected();
            }
        });
        if (serverManagerPlugin.getServerManager().getServerTypes().length == 0) {
            JMenuItem item = PRSManager.createMenuItem("DBCServerManagerEditor.NoServer");
            item.setEnabled(false);
            newServer.add(item);
        } else {
            for (final String serverType : serverManagerPlugin.getServerManager().getServerTypes()) {
                JMenuItem item = new JMenuItem("Start " + serverType);
                item.putClientProperty("ServerType", serverType);
                PRSManager.addSupport(item, "DBCServerManagerEditor.AddServerItem", updater, null);
                PRSManager.addSupport(newServer, "DBCServerManagerEditor.AddServer");
                item.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        addLocalServer(serverType);
                    }
                });
                newServer.add(item);
            }
        }
        toobar.add(newServer);
        toobar.add(editServer);
        toobar.add(removeServer);
        toobar.addSeparator();
        toobar.add(startServer);
        shutDownButton = PRSManager.createButton("DBCServerManagerEditor.StopServer");
        shutDownButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = pane.getSelectedIndex();
                if (index >= 0) {
                    DBServerInstancePanel p = (DBServerInstancePanel) pane.getComponentAt(index);
                    if (p.getInstance().isStopped()) {
                        serverManagerPlugin.getApplication().getView().getDialogManager().showMessage(DBCServerManagerEditorImpl.this, "Server is Already Stopped", MessageDialogType.ERROR);
                    } else {
                        try {
                            serverManagerPlugin.getServerManager().stopServer(p.getInstance());
                        } catch (Exception e1) {
                            serverManagerPlugin.getApplication().getView().getDialogManager().showMessage(DBCServerManagerEditorImpl.this, "Unable to Stop server", MessageDialogType.ERROR, null, e1);
                        }
                    }
                }
            }
        });
        closeButton = PRSManager.createButton("DBCServerManagerEditor.CloseServerWindow");
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = pane.getSelectedIndex();
                if (index >= 0) {
                    DBServerInstancePanel p = (DBServerInstancePanel) pane.getComponentAt(index);
                    if (p.getInstance().isStopped()) {
                        pane.removeTabAt(index);
                    } else {
                        DBCApplicationMessageDialogManager.ReturnType rt = serverManagerPlugin.getApplication().getView().getDialogManager().showMessage(DBCServerManagerEditorImpl.this, "Server is Running. Stop It now?", MessageDialogType.WARNING);
                        if (rt == DBCApplicationMessageDialogManager.ReturnType.OK) {
                            try {
                                serverManagerPlugin.getServerManager().stopServer(p.getInstance());
                                pane.removeTabAt(index);
                            } catch (Exception e1) {
                                serverManagerPlugin.getApplication().getView().getDialogManager().showMessage(DBCServerManagerEditorImpl.this, "Unable to Stop server", MessageDialogType.ERROR, null, e1);
                            }
                        }
                    }
                }
            }
        });
        toobar.add(shutDownButton);
        toobar.add(closeButton);
        pane = new JTabbedPane();
        pane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                shutDownButton.setEnabled(pane.getTabCount() > 0);
                closeButton.setEnabled(pane.getTabCount() > 0);
            }
        });
//        setPreferredSize(new Dimension(400, 200));
        shutDownButton.setEnabled(pane.getTabCount() > 0);
        closeButton.setEnabled(pane.getTabCount() > 0);
        JPanel p = new JPanel(new BorderLayout());
        servers = new JList(serverManagerPlugin.getServerManager().getServersInfos());

        p.add(new JScrollPane(servers), BorderLayout.LINE_START);
        p.add(pane, BorderLayout.CENTER);
        p.add(toobar, BorderLayout.PAGE_START);
        add(p);
        PRSManager.update(this, serverManagerPlugin);
        serverManagerPlugin.getServerManager().addServerListener(new DBCServerListener() {
            public void serverInitialized(DBCServerEvent e) {
                DBServerInstancePanel area = new DBServerInstancePanel();
                final DBCServerInstance instance = e.getInstance();
                instance.setLog(area.getLog());
                area.setInstance(instance);
                int index = pane.getComponentCount();
                pane.addTab(area.getInstance().toString(), area);
                pane.setSelectedIndex(index);
                updateIcon(index);
            }

            public void serverStarted(DBCServerEvent e) {
                //
            }

            public void serverStopped(DBCServerEvent e) {
                int count = pane.getTabCount();
                for (int i = 0; i < count; i++) {
                    if (getDBServerInstancePanel(i).getInstance().equals(e.getInstance())) {
                        updateIcon(i);
                        return;
                    }
                }

            }
        });
    }

    private void addLocalServer(String type) {
        try {
            DBCServerManagerHandler serverManagerHandler = serverManagerPlugin.getServerManager().getDBServerManagerHandler(type);
            DBCServerInfo serverInfo = serverManagerHandler.showDialog(this, null);
            if (serverInfo != null) {
                serverInfo.setType(type);
                String n = serverInfo.getConfigName();
                if (n == null || n.trim().length() == 0) {
                    serverInfo.setConfigName("NoName");
                }
                serverManagerPlugin.getServerManager().addServersInfo(serverInfo);
                final DBCServerInfo[] model = serverManagerPlugin.getServerManager().getServersInfos();
                servers.setModel(new AbstractListModel() {
                    public int getSize() {
                        return model.length;
                    }

                    public Object getElementAt(int i) {
                        return model[i];
                    }
                });
                servers.setSelectedValue(serverInfo, true);
            }
        } catch (Exception e) {
            serverManagerPlugin.getApplication().getView().getDialogManager().showMessage(null, null, MessageDialogType.ERROR, null, e);
        }
    }

    private void startSelected() {
        DBCServerInfo serverInfo = (DBCServerInfo) servers.getSelectedValue();
        try {
            if (serverInfo != null) {
                serverManagerPlugin.getServerManager().startServer(serverInfo);
            }
        } catch (Exception e) {
            serverManagerPlugin.getApplication().getView().getDialogManager().showMessage(null, null, MessageDialogType.ERROR, null, e);
        }
    }

    private void removeSelected() {
        DBCServerInfo serverInfo = (DBCServerInfo) servers.getSelectedValue();
        if (serverInfo == null) {
            return;
        }
        DBCApplicationMessageDialogManager.ReturnType r = serverManagerPlugin.getApplication().getView().getDialogManager().showMessage(this, "Are you sure to remove this server configuration", MessageDialogType.WARNING);
        if (r == DBCApplicationMessageDialogManager.ReturnType.OK) {
            serverManagerPlugin.getServerManager().removeServersInfo(serverInfo.getId());
            final DBCServerInfo[] model = serverManagerPlugin.getServerManager().getServersInfos();
            servers.setModel(new AbstractListModel() {
                public int getSize() {
                    return model.length;
                }

                public Object getElementAt(int i) {
                    return model[i];
                }
            });
            if (model.length > 0) {
                servers.setSelectedIndex(0);
            }
        }
    }

    private void editSelected() {
        DBCServerInfo serverInfo = (DBCServerInfo) servers.getSelectedValue();
        if (serverInfo == null) {
            return;
        }
        try {
            String type = serverInfo.getType();
            DBCServerManagerHandler serverManagerHandler = serverManagerPlugin.getServerManager().getDBServerManagerHandler(type);
            DBCServerInfo serverInfo2 = serverManagerHandler.showDialog(this, serverInfo);
            if (serverInfo2 != null) {
                serverInfo2.setId(serverInfo.getId());
                serverInfo = serverInfo2;
                serverInfo.setType(type);
                String n = serverInfo.getConfigName();
                if (n == null || n.trim().length() == 0) {
                    serverInfo.setConfigName("NoName");
                }
                serverManagerPlugin.getServerManager().updateServersInfo(serverInfo);
                final DBCServerInfo[] model = serverManagerPlugin.getServerManager().getServersInfos();
                servers.setModel(new AbstractListModel() {
                    public int getSize() {
                        return model.length;
                    }

                    public Object getElementAt(int i) {
                        return model[i];
                    }
                });
                servers.setSelectedValue(serverInfo, true);
            }
        } catch (Exception e) {
            serverManagerPlugin.getApplication().getView().getDialogManager().showMessage(null, null, MessageDialogType.ERROR, null, e);
        }
    }

    private void updateIcon(int index) {
        DBCServerInstance instance = getDBServerInstancePanel(index).getInstance();
        pane.setIconAt(index, instance.isStopped() ? serverManagerPlugin.getIconSet().getIconR("Stop") : serverManagerPlugin.getIconSet().getIconR("Running"));
    }

    public DBServerInstancePanel getDBServerInstancePanel(int index) {
        return (DBServerInstancePanel) pane.getComponentAt(index);
    }


    public JComponent getComponent() {
        return this;
    }
}
