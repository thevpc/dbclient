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
package net.thevpc.dbclient.plugin.system.sessionmanager;

import java.util.logging.Level;
import net.thevpc.dbclient.api.DBCApplication;
import net.thevpc.dbclient.api.DBCSession;
import net.thevpc.dbclient.api.configmanager.DBCSessionConfig;
import net.thevpc.dbclient.api.configmanager.DBCSessionInfo;
import net.thevpc.dbclient.api.drivermanager.DBCConnectionPool;
import net.thevpc.dbclient.api.pluginmanager.*;
import net.thevpc.dbclient.api.pluginmanager.injector.*;
import net.thevpc.dbclient.api.sessionmanager.*;
import net.thevpc.dbclient.api.sql.DBCConnection;
import net.thevpc.dbclient.api.sql.DBObjectFilter;
import net.thevpc.dbclient.api.sql.objects.DBObject;
import net.thevpc.common.prs.factory.ExtensionDescriptor;
import net.thevpc.common.prs.factory.FactoryListenerAdapter;
import net.thevpc.common.prs.factory.FactoryEvent;
import net.thevpc.common.prs.factory.ImplementationDescriptor;
import net.thevpc.common.prs.plugin.FieldValueProviderManager;
import net.thevpc.common.prs.plugin.PluginDescriptor;
import net.thevpc.common.prs.classloader.ProxyClassLoader;
import net.thevpc.common.swing.dialog.MessageDialogType;
import net.thevpc.common.swing.util.ClassPath;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import net.thevpc.dbclient.api.DBCSessionTaskManager;
import net.thevpc.dbclient.api.pluginmanager.DBCPlugin;
import net.thevpc.common.prs.log.SimpleLogFormatter;
import net.thevpc.common.prs.factory.ImplementationFactoryDescriptor;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com) @creationtime 3 juil. 2006
 * 09:56:14
 */
public class DBCSessionImpl extends DBCAbstractPluggable implements DBCSession {

    private DBCSessionView view;
    private DBCApplication application;
    private DBCSessionConfig config;
//    private DBCSessionLog log;
    private String type;
    private DBCConnection defaultConnection;
    private DBCSessionFactory factory;
    private DBCSessionConnectionPool connectionPool;
    private HashMap<Object, Object> clientProperties = new HashMap<Object, Object>();
    private List<DBCSessionListener> listeners;
    private Map<String, DBCPluginSession> pluginSessions = new LinkedHashMap<String, DBCPluginSession>();
    private DBCSessionInfo configInfo;
    private DBCSessionTaskManager taskManager;
    private PropertyChangeSupport pchsupport;
    private DBCConnectionPool applicationConnectionPool;
    private Connection defaultNativeConnection;
    private List<DBCProgressMonitor> progressMonitors = new ArrayList<DBCProgressMonitor>();
    private FieldValueProviderManager fieldValueProviderManager;
    private String sessionUUID;
    private String catalog;
    private ClassLoader classLoader;
    private HashMap<String, Object> userObjects;

    public DBCSessionImpl() {
        pchsupport = new PropertyChangeSupport(this);
        fieldValueProviderManager = new FieldValueProviderManager();
        fieldValueProviderManager.addProvider(new SessionFieldValueProviderDBCApplication(this));
        fieldValueProviderManager.addProvider(new SessionFieldValueProviderDBClientContext(this));
        fieldValueProviderManager.addProvider(new SessionFieldValueProviderDBCFactory(this));
        fieldValueProviderManager.addProvider(new SessionFieldValueProviderDBCPlugin(this));
        fieldValueProviderManager.addProvider(new SessionFieldValueProviderDBCPluginSession(this));
        fieldValueProviderManager.addProvider(new SessionFieldValueProviderDBCSession(this));
        fieldValueProviderManager.addProvider(new SessionFieldValueProviderDBCSessionFactory(this));
    }

    public void init(DBCApplication dbClient, DBCSessionInfo info, Connection defaultNativeConnection, DBCConnectionPool connectionPool, String connectionType) throws ClassNotFoundException, SQLException {
        sessionUUID = "session-" + Integer.toString(info.getId(), 32).toUpperCase() + "-" + Integer.toString(System.identityHashCode(this), 32).toUpperCase();
        this.application = dbClient;
        this.configInfo = info;
        this.type = connectionType;
        this.applicationConnectionPool = connectionPool;
        this.defaultNativeConnection = defaultNativeConnection;
        DBCFactory cm = getFactory();
        for (ExtensionDescriptor conf : getApplication().getPluginManager().getExtensions()) {
            //TODO should filter implementations that does not match?
            conf = conf.clone();
            PluginDescriptor pi = (PluginDescriptor) conf.getOwner();
            if (pi == null || isPluginEnabled(pi.getId())) {
                for (ImplementationDescriptor impl : conf.getImplementations()) {
                    pi = (PluginDescriptor) impl.getOwner();
                    if (pi != null && !isPluginEnabled(pi.getId())) {
//                        System.out.println("$$$ REMOVED " + impl + " : " + pi);
                        conf.remove(impl);
                    } else {
//                        System.out.println("$$$ ADDED " + impl + " : " + pi);
                    }
                }
                for (ImplementationFactoryDescriptor impl : conf.getImplementationFactories()) {
                    pi = (PluginDescriptor) impl.getOwner();
                    if (pi != null && !isPluginEnabled(pi.getId())) {
                        conf.remove(impl);
                    }
                }
                cm.registerExtension(conf);
            }
        }

        ArrayList<DBCPluginSupport> supportInfos = new ArrayList<DBCPluginSupport>();
        ArrayList<ClassLoader> pluginClassLoaders = new ArrayList<ClassLoader>();
        for (DBCPlugin p : getApplication().getPluginManager().getAllPlugins()) {
            if (p.isEnabled() && isPluginEnabled(p.getId())) {
                pluginClassLoaders.add(p.getDescriptor().getClassLoader());
                int c = p.getConnectionSupportLevel(defaultNativeConnection);
                if (c >= 0) {
                    DBCPluginSupport s = new DBCPluginSupport();
                    s.plugin = p;
                    s.support = c;
                    supportInfos.add(s);
                }
            }
        }
        classLoader = new ProxyClassLoader("Session " + info.getName(), pluginClassLoaders.toArray(new ClassLoader[pluginClassLoaders.size()]));
        Collections.sort(supportInfos);
        for (DBCPluginSupport supportInfo : supportInfos) {
            Map<Class, Class> map = supportInfo.plugin.getConnectionFactoryMap(defaultNativeConnection);
            if (map != null) {
                for (Map.Entry<Class, Class> classClassEntry : map.entrySet()) {
                    cm.registerImplementation(classClassEntry.getKey(), classClassEntry.getValue(), supportInfo.plugin.getDescriptor(), true);
                }
            }
        }
    }

    @Override
    public DBCSessionTaskManager getTaskManager() {
        if (taskManager == null) {
            synchronized (this) {
                if (taskManager == null) {
                    taskManager = getFactory().newInstance(DBCSessionTaskManager.class);
                    taskManager.init(this);
                }
            }
        }
        return taskManager;
    }

    @Override
    public ClassLoader getClassLoader() {
        return classLoader;
    }

    private static class DBCPluginSupport implements Comparable<DBCPluginSupport> {

        DBCPlugin plugin;
        int support;

        @Override
        public int compareTo(DBCPluginSupport o) {
            return support - o.support;
        }
    }

    public void openSession() {
        config = getFactory().newInstance(DBCSessionConfig.class);

        Logger sessionLogger = getLogger("");
        File sessionLogPattern = new File(getApplication().getVarDir(), "sessions/" + getInfo().getId() + "/log/session-%u.log");
        sessionLogPattern.getParentFile().mkdirs();
        boolean loggerAlreadyInitialized = false;
        for (Handler handler : sessionLogger.getHandlers()) {
            if (handler instanceof SessionFileHandler) {
                SessionFileHandler fh = (SessionFileHandler) handler;
                if (fh.getSessionId() == getInfo().getId()) {
                    loggerAlreadyInitialized = true;
                    break;
                }
            }
        }
        if (!loggerAlreadyInitialized) {
            try {
                sessionLogger.addHandler(new SessionFileHandler(getInfo().getId(), sessionLogPattern.getPath(), 1024 * 1024 * 5, 3, true));
            } catch (Exception ex) {
                application.getLogger(DBCSessionImpl.class.getName()).log(Level.SEVERE, "Unable to configure session logger", ex);
            }
        }
        DBCPluginManager pluginManager = getApplication().getPluginManager();
        DBCPlugin[] plugins = pluginManager.getEnabledPlugins();
        for (DBCPlugin plugin : plugins) {
            int level = -1;
            if (isPluginEnabled(plugin.getId())) {
                try {
                    level = plugin.getConnectionSupportLevel(getConnection());
                } catch (SQLException e) {
                    //ignore plugin not added
                }
                if (level >= 0) {
                    try {
                        getLogger(getClass().getName()).log(Level.CONFIG, "AddPluginSession {0}", plugin.getId());
                        addPluginSession(pluginManager.createPluginSession(plugin, this));
                    } catch (Throwable e) {
                        getView().getDialogManager().showMessage(null, null, MessageDialogType.ERROR, null, e);
                    }
                }
            }
        }

        for (DBCPluginSession plugin : getPluginSessions()) {
            try {
                plugin.sessionOpening();
            } catch (Throwable e) {
                getView().getDialogManager().showMessage(null, null, MessageDialogType.ERROR, null, e);
            }
        }

        getView().openSession();
        for (DBCPluginSession plugin : getPluginSessions()) {
            try {
                plugin.sessionOpened();
            } catch (Throwable e) {
                getView().getDialogManager().showMessage(null, null, MessageDialogType.ERROR, null, e);
            }
        }
    }

    public DBCConnection getConnection() throws SQLException {
        if (defaultConnection == null) {
            try {
                defaultConnection = prepareConnection(defaultNativeConnection);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return defaultConnection;
    }

    public DBCSessionView getView() {
        if (view == null) {
            try {
                view = getFactory().newInstance(DBCSessionView.class);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return view;
    }

    public void close() throws SQLException {
        for (DBCPluginSession plugin : getPluginSessions()) {
            try {
                plugin.sessionClosing();
            } catch (Throwable e) {
                getView().getDialogManager().showMessage(null, null, MessageDialogType.ERROR, null, e);
            }
        }
        if (defaultConnection != null) {
            defaultConnection.close();
            defaultConnection = null;
            if (defaultNativeConnection != null) {
                defaultNativeConnection.close();
                defaultNativeConnection = null;
            }
            fireSessionClosed();
        }
    }

    public DBCConnection createConnection() throws ClassNotFoundException, SQLException, IOException {
        Connection nativeConnection = getConnectionPool().createConnection();
        return prepareConnection(nativeConnection);
    }

    protected DBCConnection prepareConnection(Connection nativeConnection) throws ClassNotFoundException, SQLException, IOException {
        DBCConnection connection = null;
        int cnxSupportLevel = Integer.MIN_VALUE;
        for (DBCConnection ii : getApplication().getFactory().createImplementations(DBCConnection.class)) {
            DBCConnection cnxi = ii;
            if (cnxi != null) {
                cnxi.init(this);
                if (connection == null) {
                    connection = cnxi;
                    cnxSupportLevel = cnxi.getConnectionSupportLevel(nativeConnection);
                } else {
                    int level = cnxi.getConnectionSupportLevel(nativeConnection);
                    if (level > cnxSupportLevel) {
                        connection = cnxi;
                        cnxSupportLevel = level;
                    }
                }
            }
        }
        connection.setLoggerProvider(this);
        connection.setConnection(nativeConnection);
        //final DBCConnection con2 = getApplication().getConnectionManager().createConnection(nativeConnection);
        //con2.setConnection(nativeConnection);
        final DBCConnection con2 = connection;
//        con2.setLog(getLog());
        con2.setFactory(getFactory());
//        con2.setLog(getLog());
        con2.setObjectFilter(new DBObjectFilter() {

            public Status accept(DBObject object) {
                boolean invisible = getConfig().getBooleanProperty("Tree.IgnoredNodeIsInvisible", true);
                boolean excludedPath = getConfig().isExcludedPath(object.getStringPath());
                if (invisible) {
                    return excludedPath ? DBObjectFilter.Status.REJECT : DBObjectFilter.Status.ACCEPT;
                } else {
                    return excludedPath ? DBObjectFilter.Status.IGNORE : DBObjectFilter.Status.ACCEPT;
                }
            }
        });
        final String onCloseScript = getConfig().getStringProperty("connexion.script.onclose", null);
        final String onOpenScript = getConfig().getStringProperty("connexion.script.onopen", null);


        if (onCloseScript != null && onCloseScript.trim().length() > 0) {
            con2.addPropertyChangeListener("closing", new PropertyChangeListener() {

                public void propertyChange(PropertyChangeEvent evt) {
                    try {
                        con2.executeScript(onCloseScript);
                    } catch (SQLException e) {
                        getView().getDialogManager().showMessage(null, "Unable to execute Close Script", MessageDialogType.ERROR, null, e);
                    }

                }
            });
        }
        Boolean bac = getConfig().getBooleanProperty("connexion.autoCommit", null);
        Integer bhl = getConfig().getIntegerProperty("connexion.holdability", null);
        Boolean bro = getConfig().getBooleanProperty("connexion.readOnly", null);
        Integer bti = getConfig().getIntegerProperty("connexion.transIsolation", null);

        if (bro != null) {
            con2.setReadOnly(bro);
        }
        if (bac != null) {
            con2.setAutoCommit(bac);
        }
        if (bhl != null) {
            switch (bhl) {
                case ResultSet.HOLD_CURSORS_OVER_COMMIT:
                case ResultSet.CLOSE_CURSORS_AT_COMMIT: {
                    try {
                        con2.setHoldability(bhl);
                    } catch (Exception e) {
                        getLogger(getClass().getName()).log(Level.SEVERE, "Unsupported Holdability " + bhl, e);
                    }
                }
                default: {
                    if (bhl != -1) {
                        getLogger(getClass().getName()).log(Level.SEVERE, "Bad Holdability : {0}. Ignored.", bhl);
                    }
                }
            }
        }
        if (bti != null) {
            switch (bti) {
                case Connection.TRANSACTION_READ_UNCOMMITTED:
                case Connection.TRANSACTION_READ_COMMITTED:
                case Connection.TRANSACTION_REPEATABLE_READ:
                case Connection.TRANSACTION_SERIALIZABLE: {
                    try {
                        con2.setTransactionIsolation(bti);
                    } catch (Exception e) {
                        getLogger(getClass().getName()).log(Level.SEVERE, "Unsupported Transaction Isolation " + bti, e);
                    }
                }
                default: {
                    if (bti != -1) {
                        getLogger(getClass().getName()).log(Level.SEVERE, "Bad Transaction Isolation : {0}. Ignored.", bti);
                    }
                }
            }
        }
        try {
            if (onOpenScript != null && onOpenScript.trim().length() > 0) {
                con2.executeScript(onOpenScript);
            }
        } catch (SQLException e) {
            getView().getDialogManager().showMessage(null, "Unable to execute Open Script", MessageDialogType.ERROR, null, e);
            //
        }
        return con2;
    }

//    public TLog getLog() {
//        return log;
//    }
    public DBCApplication getApplication() {
        return application;
    }

    public DBCSessionConfig getConfig() {
        return config;
    }

    public String getType() {
        return type;
    }

    public DBCSessionFactory getFactory() {
        if (factory == null) {
            factory = getApplication().getFactory().newInstance(DBCSessionFactory.class);
            factory.init(this);
            fieldValueProviderManager.setFactory(factory);
            factory.addFactoryListener(new FactoryListenerAdapter() {

                public void instanceCreated(FactoryEvent event) {
                    Object instance = event.getInstance();
                    PluginDescriptor pluginInfo = (PluginDescriptor) event.getOwner();
                    if (pluginInfo != null) {
                        getApplication().getPluginManager().initializeInstance(instance, fieldValueProviderManager, pluginInfo.getId());
                    } else {
                        getLogger(DBCSessionImpl.class.getName()).log(Level.SEVERE, "Core Session Component Non Initialized : {0}", instance.getClass().getName());
                    }
                }
            });
        }
        return factory;
    }

    public DBCSessionConnectionPool getConnectionPool() {
        if (connectionPool == null) {
            connectionPool = getFactory().newInstance(DBCSessionConnectionPool.class);
            connectionPool.init(getApplicationConnectionPool());
        }
        return connectionPool;
    }

    public HashMap<Object, Object> getClientProperties() {
        return clientProperties;
    }

    public void fireSessionClosed() {
        if (listeners != null) {
            for (DBCSessionListener listener : listeners) {
                listener.sessionClosed(this);
            }
        }
    }

    public void addSessionListener(DBCSessionListener listener) {
        if (listeners == null) {
            listeners = new Vector<DBCSessionListener>();
        }
        listeners.add(listener);
    }

    public boolean isPluginEnabled(String pluginId) {
        //plugin enabled or not must be stored into application config
        String v = getApplication().getConfig().getStringProperty(getInfo().getId(), "Plugin." + pluginId + ".Enabled", null);
        return v == null ? true : Boolean.parseBoolean(v);
    }

    public void setPluginEnabled(String pluginId, boolean enable) {
        //plugin enabled or not must be stored into application config
        //getConfig().setBooleanProperty("Plugin." + plugin.getId() + ".Enabled", enable ? null : Boolean.FALSE);
        getApplication().getConfig().setStringProperty(getInfo().getId(), "Plugin." + pluginId + ".Enabled", String.valueOf(enable));
    }

    public Collection<DBCPluginSession> getPluginSessions() {
        return pluginSessions.values();
    }

    public void addPluginSession(DBCPluginSession pluginSession) {
        pluginSessions.put(pluginSession.getPlugin().getId(), pluginSession);
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        pchsupport.addPropertyChangeListener(propertyName, listener);
    }

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        pchsupport.removePropertyChangeListener(propertyName, listener);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pchsupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pchsupport.removePropertyChangeListener(listener);
    }

    public DBCPluginSession getPluginSession(String pluginId) {
        return pluginSessions.get(pluginId);
    }

    public DBCConnectionPool getApplicationConnectionPool() {
        return applicationConnectionPool;
    }

    public DBCSessionInfo getInfo() {
        return configInfo;
    }

    public DBCProgressMonitor createMonitor(String id) {
        DBCProgressMonitor progressMonitor = new DefaultDBCProgressMonitor(id);
        progressMonitors.add(progressMonitor);
        pchsupport.firePropertyChange(PROPERTY_PROGRESS_MONITOR_ADDED, null, progressMonitor);

        progressMonitor.addPropertychangeListener(DBCProgressMonitor.PROPERTY_RUNNING, new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                boolean running = (Boolean) evt.getNewValue();
                if (!running) {
                    DBCProgressMonitor mon = (DBCProgressMonitor) evt.getSource();
                    progressMonitors.add(mon);
                    pchsupport.firePropertyChange(PROPERTY_PROGRESS_MONITOR_REMOVED, null, mon);
                }
            }
        });
        return progressMonitor;
    }

    @Override
    public ClassPath getClassPath() {
        ClassPath cp = ClassPath.getSystemClassPath();
        DBCApplication app = getApplication();
        for (net.thevpc.dbclient.api.pluginmanager.DBCPlugin dbcPlugin : app.getPluginManager().getEnabledPlugins()) {
            if (isPluginEnabled(dbcPlugin.getId())) {
                PluginDescriptor pluginDesc = dbcPlugin.getDescriptor();
                String category = pluginDesc.getCategory();
                if (DBCPlugin.CATEGORY_DBDRIVER.equals(category)) {
                    try {
                        Connection nativeConnection = getConnection().getConnection();
                        ClassLoader cloader = nativeConnection.getClass().getClassLoader();
                        if (cloader.equals(pluginDesc.getClassLoader())) {
                            cp.addURLs(pluginDesc.getPluginAndLibsURLs());
                        }
                    } catch (Exception ex) {
                        getLogger(getClass().getName()).log(Level.SEVERE, "Unable to load connection", ex);
                    }
                } else {
                    cp.addURLs(pluginDesc.getPluginAndLibsURLs());
                }
            }
        }
        return cp;
    }

    private static class SessionFileHandler extends FileHandler {

        private int sessionId;

        public SessionFileHandler(int sessionId, String pattern, int limit, int count, boolean append) throws IOException, SecurityException {
            super(pattern, limit, count, append);
            this.sessionId = sessionId;
            setFormatter(new SimpleLogFormatter());
        }

        public int getSessionId() {
            return sessionId;
        }
    }

    @Override
    public Logger getLogger(String category) {
        if (category == null || category.isEmpty()) {
            return application.getLogger("sessions." + sessionUUID);
        }
        return application.getLogger("sessions." + sessionUUID + "." + category);
    }

    @Override
    public String getCatalog() throws SQLException {
        return getConnection().getCatalog();
    }

    @Override
    public void setCatalog(String catalog) throws SQLException {
        DBCConnection _connection = getConnection();
        final String oldCat = _connection.getCatalog();
        _connection.setCatalog(catalog);
        this.catalog = _connection.getCatalog();
        pchsupport.firePropertyChange("catalog", oldCat, this.catalog);
    }

    @Override
    public void revalidateConnection() throws SQLException {
        final DBCConnection connection = getConnection();
        if (!connection.isValidConnection()) {
            try {
                DBCConnection c = createConnection();
                if (catalog != null && catalog.trim().length() > 0) {
                    c.setCatalog(catalog);
                }
                connection.setConnection(c);
            } catch (ClassNotFoundException ex) {
                throw new SQLException("Unable to create connection", ex);
            } catch (IOException ex) {
                throw new SQLException("Unable to create connection", ex);
            }
        }
    }

    public Object getUserObject(String name) {
        if (userObjects == null) {
            return null;
        }
        return userObjects.get(name);
    }

    public void setUserObject(String name, Object value) {
        if (value == null) {
            if (userObjects != null) {
                userObjects.remove(name);
            }
        } else {
            if (userObjects == null) {
                userObjects = new HashMap<String, Object>();
            }
            userObjects.put(name, value);
        }
    }
}
