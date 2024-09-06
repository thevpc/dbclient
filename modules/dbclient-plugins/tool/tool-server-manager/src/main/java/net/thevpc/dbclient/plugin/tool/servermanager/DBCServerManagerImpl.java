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
package net.thevpc.dbclient.plugin.tool.servermanager;

import net.thevpc.dbclient.api.DBCApplication;
import net.thevpc.dbclient.api.pluginmanager.DBCAbstractPluggable;
import net.thevpc.dbclient.api.pluginmanager.DBCPlugin;
import net.thevpc.common.prs.plugin.Initializer;
import net.thevpc.common.prs.plugin.Inject;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 4 juin 2007 16:23:55
 */
public class DBCServerManagerImpl  extends DBCAbstractPluggable implements DBCServerManager {

    private final Vector<DBCServerInstance> instances = new Vector<DBCServerInstance>();
    private Hashtable<String, DBCServerManagerHandler> handlers = new Hashtable<String, DBCServerManagerHandler>();
    @Inject
    private DBCApplication application;
    private Logger logger;
    @Inject
    private DBCPlugin plugin;
    private LinkedHashMap<Integer, DBCServerInfo> serversModel;
    private int maxIds = 0;
    private Hashtable<Integer, Integer> idToConfigIndex = new Hashtable<Integer, Integer>();
    private Vector<DBCServerListener> listeners;

    public DBCServerManagerImpl() {
    }

    @Initializer
    public void init() {
        logger=application.getLogger(DBCServerManagerImpl.class.getName());
        for (DBCServerManagerHandler implementation : application.getFactory().createImplementations(DBCServerManagerHandler.class)) {
            try {
                registerServerType(implementation);
            } catch (Exception ex) {
                plugin.getLogger(DBCServerManagerImpl.class.getName()).log(Level.SEVERE, "Unable to register DBCServerManagerHandler " + implementation.getClass().getName(), ex);
            }
        }
        reload();
    }

    public void addServerListener(DBCServerListener listener) {
        if (listeners == null) {
            listeners = new Vector<DBCServerListener>();
        }
        listeners.add(listener);
    }

    public void removeServerListener(DBCServerListener listener) {
        if (listeners != null) {
            listeners.add(listener);
        }
    }

    public void reload() {
        maxIds = 0;
        serversModel = new LinkedHashMap<Integer, DBCServerInfo>();
        int configCount = application.getConfig().getIntegerProperty("ServerManager.count", 0);
        for (int i = 0; i < configCount; i++) {
            String prefix = "ServerManager.item." + (i + 1) + ".";
            String type = application.getConfig().getStringProperty(prefix + "type", null);
            String value = application.getConfig().getStringProperty(prefix + "value", null);
            if (type != null) {
                DBCServerManagerHandler h = handlers.get(type);
                if (h != null) {
                    DBCServerInfo inst = h.createServerInfo();
                    inst.decode(value);
                    int instId = inst.getId();
                    if (maxIds <= instId) {
                        maxIds = instId + 1;
                    }
                    serversModel.put(instId, inst);
                    idToConfigIndex.put(instId, i);
                    if (inst.isAutoStart()) {
                        try {
                            startServer(inst);
                        } catch (Exception ex) {
                            application.getLogger(DBCServerManagerImpl.class.getName()).log(Level.SEVERE, "start server failed", ex);
                        }
                    }
                }
            }
        }
    }

    public DBCServerInfo getServerInfo(int id) {
        return serversModel.get(id);
    }

    public DBCServerInfo[] getServersInfos() {
        return serversModel.values().toArray(new DBCServerInfo[serversModel.size()]);
    }

    public void addServersInfo(DBCServerInfo info) {
        info.setId(maxIds);
        maxIds++;
        serversModel.put(info.getId(), info);
        int i = idToConfigIndex.size();
        String prefix = "ServerManager.item." + (i + 1) + ".";
        application.getConfig().setIntegerProperty("ServerManager.count", maxIds);
        application.getConfig().setStringProperty(prefix + "type", info.getType());
        application.getConfig().setStringProperty(prefix + "value", info.encode());
        idToConfigIndex.put(info.getId(), i);
    }

    public void removeServersInfo(int id) {
        Integer i = idToConfigIndex.get(id);
        if (i != null) {
            String prefix = "ServerManager.item." + (i + 1) + ".";
            application.getConfig().setStringProperty(prefix + "type", null);
            application.getConfig().setStringProperty(prefix + "value", null);
            serversModel.remove(id);
        }
    }

    public void updateServersInfo(DBCServerInfo info) {
        Integer i = idToConfigIndex.get(info.getId());
        if (i != null) {
            String prefix = "ServerManager.item." + (i + 1) + ".";
            application.getConfig().setStringProperty(prefix + "type", info.getType());
            application.getConfig().setStringProperty(prefix + "value", info.encode());
            serversModel.put(info.getId(), info);
        }
    }

    public String[] getServerTypes() {
        return new TreeSet<String>(handlers.keySet()).toArray(new String[handlers.size()]);
    }

    public DBCServerManagerHandler getDBServerManagerHandler(String type) {
        return handlers.get(type);
    }

    public void registerServerType(DBCServerManagerHandler instance) {
        handlers.put(instance.getType(), instance);
    }

    public void unregisterServerType(String type) {
        handlers.remove(type);
    }

    public DBCServerInstance getServer(int index) {
        return instances.get(index);
    }

    public DBCServerInstance[] getServers() {
        return instances.toArray(new DBCServerInstance[instances.size()]);
    }

    public int getServersCount() {
        return instances.size();
    }

    public DBCServerInstance startServer(DBCServerInfo info) throws Exception {
        if (info.isStarted()) {
            throw new IllegalArgumentException("Already started");
        }
        DBCServerInstance instance = new DBCServerInstance(info.getType(), info);
        if (listeners != null) {
            DBCServerEvent e = new DBCServerEvent(this, instance);
            for (DBCServerListener listener : listeners) {
                listener.serverInitialized(e);
            }
        }
        getDBServerManagerHandler(info.getType()).startServer(instance, info);
        info.setStarted(true);
        if (listeners != null) {
            DBCServerEvent e = new DBCServerEvent(this, instance);
            for (DBCServerListener listener : listeners) {
                listener.serverStarted(e);
            }
        }
        instances.add(instance);
        instance.addPropertyChangeListener("stopped", new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                DBCServerInstance src = (DBCServerInstance) evt.getSource();
                src.getServerInfo().setStarted(false);
                if (listeners != null) {
                    DBCServerEvent e = new DBCServerEvent(DBCServerManagerImpl.this, src);
                    for (DBCServerListener listener : listeners) {
                        listener.serverStopped(e);
                    }
                }
            }
        });
        return instance;
    }

    public void stopServer(DBCServerInstance inst) throws Exception {
        if (getDBServerManagerHandler(inst.getType()).stopServer(inst)) {
            inst.setStopped(true);
            instances.remove(inst);
        }
    }

    public void attachServer(String type, DBCServerInfo info) throws Exception {
        //DBCServerInstance instance = new DBCServerInstance(null, type, info.getInetAddress(), info.getPort(), false);
        //getDBServerManagerHandler(type).stopServer(instance);
    }

    public void close() {
        for (DBCServerInstance instance : new ArrayList<DBCServerInstance>(instances)) {
            if (instance.getServerInfo().isStopOnExit()) {
                try {
                    stopServer(instance);
                } catch (Exception e) {
                    if (application != null) {
                        logger.log(Level.SEVERE,"StopServer failed",e);
                    }
                }
            }
        }
    }
}
