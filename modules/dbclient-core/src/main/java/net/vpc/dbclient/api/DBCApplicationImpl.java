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
package net.vpc.dbclient.api;

import java.util.logging.Level;
import java.util.logging.Logger;
import net.vpc.dbclient.api.configmanager.DBCApplicationConfig;
import net.vpc.dbclient.api.configmanager.DBCConfigException;
import net.vpc.dbclient.api.configmanager.DBCInitMessageDiscardContext;
import net.vpc.dbclient.api.configmanager.DBCSessionInfo;
import net.vpc.dbclient.api.drivermanager.DBCConnectionPool;
import net.vpc.dbclient.api.drivermanager.DBCDriverManager;
import net.vpc.dbclient.api.encryptionmanager.DBCEncryptionManager;
import net.vpc.dbclient.api.pluginmanager.DBCApplicationFactory;
import net.vpc.dbclient.api.pluginmanager.DBCPluginManagerImpl;
import net.vpc.dbclient.api.pluginmanager.DBCPlugin;
import net.vpc.dbclient.api.pluginmanager.DBCPluginManager;
import net.vpc.dbclient.api.pluginmanager.injector.*;
import net.vpc.dbclient.api.sessionmanager.DBCSessionListEditor;
import net.vpc.dbclient.api.sessionmanager.DBCSessionListener;
import net.vpc.dbclient.api.sql.urlparser.DBCDriverUrlParser;
import net.vpc.dbclient.api.viewmanager.DBCSplashScreen;
import net.vpc.prs.plugin.BlendedFile;
import net.vpc.prs.factory.ExtensionDescriptor;
import net.vpc.prs.factory.FactoryListenerAdapter;
import net.vpc.prs.factory.FactoryEvent;
import net.vpc.prs.factory.ImplementationDescriptor;
import net.vpc.prs.plugin.FieldValueProviderManager;
import net.vpc.prs.plugin.PluginDescriptor;
import net.vpc.swingext.dialog.MessageDialogManager;
import net.vpc.swingext.dialog.MessageDialogType;
import net.vpc.swingext.dialog.MessageDiscardContext;
import net.vpc.util.*;
import net.vpc.util.ProgressMonitor;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.FileHandler;
import net.vpc.log.SimpleLogFormatter;
import net.vpc.prs.locale.LocaleManager;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com) @creationtime 3 juil. 2006
 * 09:56:14
 */
public final class DBCApplicationImpl implements DBCApplication {

    private ApplicationState applicationState = ApplicationState.INITIALIZING;
    private Vector<DBCSession> sessions = new Vector<DBCSession>();
    private DBCApplicationConfig configManager;
    private DBCPluginManager pluginManager;
    private DBCDriverManager driverManager;
    private DBCApplicationFactory factory;
    private CloseOption closeOption = CloseOption.EXIT;
    private Properties applicationProperties;
    private File workingDir = new File(".");
    private String applicationUUID = "dbclient";
    private DBCEncryptionManager encryptionManager;
    private PropertyChangeSupport pchsupport;
    private ApplicationMode applicationMode = ApplicationMode.DEFAULT;
    private HashMap<String, Object> urlVariables = new HashMap<String, Object>();
    private DBClientInfo applicationInfo = DBClientInfo.INSTANCE;
    private DBCApplicationView view;
    /**
     * config folder default is
     */
    private File configFolder;
    /**
     * DBclient install folder, not yet used
     */
    private File installFolder;
    private File varFolder;
    private FieldValueProviderManager fieldValueProviderManager;
    private Logger logger;

    /**
     * Default and Only Constructor.
     * <p/>
     * One should call init() before staring to use the instance. init() code is
     * separated for the code in the constructor to give the developer the
     * possibility to customize DBClient behaviour which depends on the
     * ComponentManager customization. ComponentManager is the Most important
     * Class in the DBClient Projects. Indeed, it provides a mapping between
     * Interfaces and Implementations. DBClient is as far as possible based upon
     * Interfaces contracts. Whenever instantiation of a class is needed,
     * ComponentManager will be called to do this staff. Thus, almost any
     * 'Class' in DBClient may be replaced dynamycally (usually by Plugins).
     * <pre>
     * Example :
     * use
     * DBCApplicationConfig configManager = (DBCApplicationConfig) getFactory().newInstance(DBCApplicationConfig.class);
     * instead of
     * DBCApplicationConfig configManager = new DBCConfigManagerImpl();
     * </pre> To findServer all Customizable Components check DBCInterface
     * Annotation.
     * <p/>
     * Besides, there are two levels of ComponentManager. The Global
     * ComponentManager called directly from "dbclient.getComponentManager" and
     * the session wise ComponentManager called from session.getComponentManager
     * that may be a more particular customization (override mechanism) for a
     * given Session.
     */
    public DBCApplicationImpl() {
        applicationUUID = "dbclient-" + Integer.toString(System.identityHashCode(this), 32).toUpperCase();
        pchsupport = new PropertyChangeSupport(this);
        workingDir = new File(".");
        applicationState = ApplicationState.INITIALIZING;
        fieldValueProviderManager = new FieldValueProviderManager();
        fieldValueProviderManager.addProvider(new ApplicationFieldValueProviderDBCApplication(this));
        fieldValueProviderManager.addProvider(new ApplicationFieldValueProviderDBCApplicationFactory(this));
        fieldValueProviderManager.addProvider(new ApplicationFieldValueProviderDBCPlugin(this));
        fieldValueProviderManager.addProvider(new ApplicationFieldValueProviderDBCFactory(this));
        fieldValueProviderManager.addProvider(new ApplicationFieldValueProviderDBClientContext(this));
        view = new DBCApplicationViewImpl(this);
    }

    public DBCApplicationView getView() {
        return view;
    }

    public void start(Properties applicationProperties, ProgressMonitor monitor) {
        this.applicationProperties = new Properties();
        if (applicationProperties != null) {
            this.applicationProperties.putAll(applicationProperties);
        }
        setApplicationState(DBCApplication.ApplicationState.INITIALIZING);
        if (Boolean.getBoolean("ResetVarFolder")) {
            IOUtils.deleteFolderTree(getVarDir(), null);
        }

        Logger appLogger = getLogger("");
        try {

            String logLevelString = this.applicationProperties.getProperty("application.log.level");
            Level logLevel = (logLevelString == null || logLevelString.isEmpty()) ? null : Level.parse(logLevelString.toUpperCase());
            File file = new File(getVarDir(), "application/log/application-%u.log");
            file.getParentFile().mkdirs();
            FileHandler h = new FileHandler(file.getPath(), 5 * 1024 * 1024, 5, true);
            h.setFormatter(new SimpleLogFormatter());
            appLogger.addHandler(h);
            appLogger.setUseParentHandlers(false);
            if (logLevel != null) {
                appLogger.setLevel(logLevel);
                h.setLevel(logLevel);
            }
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Unable to set Application Log file", ex);
        }
        logger = getLogger(getClass().getName());
        logger.info("Application starting...");
//        try {
//            TLogPrintStream fileLog = new TLogPrintStream(new File(getVarDir(), "application/log/application.log"));
//            TLog lo = new TBufferedLog(fileLog);
//            if (Boolean.valueOf(this.applicationProperties.getProperty("application.log.std"))) {
//                log = new TLogList(TLog.STD, lo);
//            } else {
//                log = lo;
//            }
//        } catch (Exception e) {
//            TLogConsole.CONSOLE.error("Unable to set Application logger", e);
//        }
        DBCSplashScreen splashScreen = getView().getSplashScreen();
        //PHASE 1
        splashScreen.progressStart(new ProgressEvent(this, 0.0F, "Initializing PluginManager..."));

        LocaleManager.getInstance().registerLocale(DBCApplicationImpl.BOOT_LOCALE);
        LocaleManager.getInstance().registerLocale(Locale.ENGLISH);
        //check if workDir is Writable
        File wd = getWorkingDir();
        if (!IOUtils.isFolderWritable(wd)) {
            MessageDialogManager.ReturnType r = getView().getDialogManager().showMessage(null,
                    "Working directory '" + wd + "'\n seems to be incorrect or you have no permission to write to. \n DBClient Configuration will be store in home directory.",
                    MessageDialogType.ERROR,
                    null,
                    null,
                    new DBCInitMessageDiscardContext("[" + IOUtils.getFilePath(wd) + "].change_workdir_to_hwd"));
            if (r == MessageDialogManager.ReturnType.CANCEL) {
                throw new CancelException();
            }
            setWorkingDir(new File(DBCApplication.HOME_WORKING_DIRECTORY));
        }
        Chronometer chronometer = new Chronometer();
        chronometer.start();
        DBCPluginManager dbcPluginManager = getPluginManager();
        ProgressMonitor[] monitors = ProgressMonitorUtils.split(splashScreen, 4);
        try {
            dbcPluginManager.applicationInitializing(monitors[0]);
        } catch (Exception e) {
            getView().getDialogManager().showMessage(null, null, MessageDialogType.ERROR, null, e);
            close();
            return; // if no exit
        }
        chronometer.stop();
        logger.log(Level.INFO, "[applicationInitializing] {0}", chronometer);
        //change Application State
        setApplicationState(DBCApplication.ApplicationState.CONFIGURING);

        //configure plugins
        dbcPluginManager.applicationConfiguring(monitors[1]);

        //change Application State
        setApplicationState(DBCApplication.ApplicationState.OPENING);
        dbcPluginManager.applicationOpening(monitors[2]);

        //final initializing state = Application Ready
        setApplicationState(DBCApplication.ApplicationState.READY);
        dbcPluginManager.applicationReady(monitors[3]);
        splashScreen.progressEnd(new ProgressEvent(this, 1F, "PluginManager initialized..."));
    }

    /**
     * DBCApplicationConfig is the Class responsible of storing and querying
     * DBClient configuration.
     *
     * @return ConfigManager instance
     */
    public synchronized DBCApplicationConfig getConfig() {
        if (configManager == null) {
            checkApplicationState(ApplicationState.CONFIGURING, ApplicationState.CLOSED);
            try {
                configManager = getFactory().newInstance(DBCApplicationConfig.class);
            } catch (Throwable e) {
                getView().getDialogManager().showMessage(null, "Unable to initialize config system. Will be reset.", MessageDialogType.ERROR);
                setPreferredConfigImpl(null);
                close();
                throw new RuntimeException("Unable to initialize config system");// fall into this if no exit
            }
            try {
                configManager.init();
            } catch (DBCConfigException e) {
                getView().getDialogManager().showMessage(null, "Unable to initialize config system. Will be reset.", MessageDialogType.ERROR, null, e, (MessageDiscardContext) null);
                setPreferredConfigImpl(null);
                close();
                throw new RuntimeException("Unable to initialize config system");// fall into this if no exit
            }
        }
        return configManager;
    }

    /**
     * Plugin Manager Factory is NOT used for creating plugin manager instance
     *
     * @return Plugin Manager
     */
    public DBCPluginManager getPluginManager() {
        if (pluginManager == null) {
            pluginManager = new DBCPluginManagerImpl();
            pluginManager.init(this, getApplicationInfo().getProductVersion());
        }
        return pluginManager;
    }

    /**
     * Driver Manager
     *
     * @return Driver Manager
     */
    public DBCDriverManager getDriverManager() {
        if (driverManager == null) {
            driverManager = getFactory().newInstance(DBCDriverManager.class);
        }
        return driverManager;
    }

    public DBCApplicationFactory getFactory() {
        if (factory == null) {
            factory = new DBCApplicationFactory();
            factory.setApplication(this);
            fieldValueProviderManager.setFactory(factory);
            factory.addFactoryListener(new FactoryListenerAdapter() {

                public void implementationSelectionChanged(FactoryEvent e) {
                    if (getApplicationState().ordinal() >= ApplicationState.OPENING.ordinal()) {
                        ExtensionDescriptor cc = e.getConfiguration();
                        ImplementationDescriptor i = (ImplementationDescriptor) e.getNewValue();
                        getConfig().setStringProperty("factory." + cc.getId().getName(), i == null ? null : i.getImplementationType().getName());
                        if (DBCApplicationConfig.class.equals(cc.getId())) {
                            setPreferredConfigImpl(i == null ? null : i.getImplementationType().getName());
                        }
                    }
                }

                public void instanceCreated(FactoryEvent event) {
                    Object instance = event.getInstance();
                    PluginDescriptor pluginInfo = (PluginDescriptor) event.getOwner();
                    if (pluginInfo != null) {
                        getPluginManager().initializeInstance(instance, fieldValueProviderManager, pluginInfo.getId());
                    } else {
                        logger.log(Level.WARNING, "Core Application Component Non Initialized : {0}", instance.getClass().getName());
                    }
                }
            });
        }
        return factory;
    }

    public CloseOption getCloseOption() {
        return closeOption;
    }

    public void setCloseOption(CloseOption quitOption) {
        this.closeOption = quitOption;
    }

    public Properties getApplicationProperties() {
        return applicationProperties;
    }

    protected void setApplicationState(ApplicationState newState) {
        ApplicationState old = applicationState;
        applicationState = newState;
        pchsupport.firePropertyChange(PROPERTY_APPLICATION_STATE, old, newState);
    }

    public void close() {
        setApplicationState(DBCApplication.ApplicationState.CLOSING);
        pchsupport.firePropertyChange(PROPERTY_CLOSING, false, true);
        try {
            if (CloseOption.DO_NOTHING.equals(closeOption)) {
                return;
            }
            try {
                if (pluginManager != null) {
                    try {
                        pluginManager.close();
                    } catch (Throwable e) {
                        logger.log(java.util.logging.Level.SEVERE, null, e);
                    }
                }
                if (configManager != null) {
                    try {
                        configManager.dispose();
                    } catch (Throwable e) {
                        logger.log(java.util.logging.Level.SEVERE, null, e);
                    }
                }
                pchsupport.firePropertyChange(PROPERTY_CLOSED, false, true);
                if (CloseOption.EXIT.equals(closeOption)) {
                    System.exit(0);
                }
            } catch (Throwable ee) {
                logger.log(java.util.logging.Level.SEVERE, "Error Occurred on exit. Exit forced", ee);
                System.exit(-3);
            }
        } finally {
            setApplicationState(DBCApplication.ApplicationState.CLOSED);
        }
    }

    public File getWorkingDir() {
        return workingDir;
    }

    public File getVarDir() {
        return varFolder != null ? varFolder : new File(getWorkingDir(), "var");
    }

    public void setConfigDir(File cf) {
        checkApplicationState(ApplicationState.INITIALIZING, ApplicationState.INITIALIZING);
        File old = this.configFolder;
        this.configFolder = cf;
        pchsupport.firePropertyChange(PROPERTY_CONFIG_DIR, old, this.configFolder);
    }

    public File getConfigDir() {
        if (configFolder == null) {
            return new File(workingDir, "config");
        } else {
            if (configFolder.isAbsolute()) {
                return configFolder;
            } else {
                return new File(workingDir, configFolder.getPath());
            }
        }
    }

    public void setWorkingDir(File workingDir) {
        checkApplicationState(ApplicationState.INITIALIZING, ApplicationState.INITIALIZING);

        if (workingDir == null) {
            workingDir = new File(".");
        }
        File old = this.workingDir;
        this.workingDir = workingDir;
        this.workingDir.mkdirs();
        pchsupport.firePropertyChange(PROPERTY_WORKING_DIR, old, this.workingDir);
    }

    public void setApplicationMode(ApplicationMode mode) {
        checkApplicationState(ApplicationState.INITIALIZING, ApplicationState.INITIALIZING);
        if (mode == null) {
            throw new NullPointerException();
        }
        ApplicationMode old = this.applicationMode;
        this.applicationMode = mode;
        pchsupport.firePropertyChange(PROPERTY_APPLICATION_MODE, old, this.applicationMode);
    }

    public ApplicationMode getApplicationMode() {
        return applicationMode;
    }

    //////////////////////////////////////////////////////////////
    //
    // Session handling
    //
    //////////////////////////////////////////////////////////////
    /**
     * @return all open sessions
     */
    public DBCSession[] getSessions() {
        return sessions.toArray(new DBCSession[sessions.size()]);
    }

    public DBCSession openSession(String sessionName) throws SQLException, ClassNotFoundException {
        for (DBCSessionInfo dbcSessionInfo : getConfig().getSessions()) {
            if (dbcSessionInfo.getQualifiedName().equals(sessionName)) {
                return openSession(getConfig().getSession(dbcSessionInfo.getId()));
            }
        }
        for (DBCSessionInfo dbcSessionInfo : getConfig().getSessions()) {
            if (dbcSessionInfo.getName().equals(sessionName)) {
                return openSession(getConfig().getSession(dbcSessionInfo.getId()));
            }
        }
        return null;
    }

    public DBCSession openSession(int sessionId) throws SQLException, ClassNotFoundException {
        return openSession(getConfig().getSession(sessionId));
    }

    public DBCSession openSession(DBCSessionInfo info) throws SQLException, ClassNotFoundException {
        checkApplicationState(ApplicationState.READY, ApplicationState.CLOSED);
        getConfig().setStringProperty(DBCSessionListEditor.PRP_SESSION_ID, String.valueOf(info.getId()));
        DBCConnectionPool pool = getFactory().newInstance(DBCConnectionPool.class);
        Connection nativeConnection = null;
        DBCSession session = null;
        try {
            nativeConnection = pool.createConnection(this, info);

            ArrayList<DBCPluginSupport> supportInfos = new ArrayList<DBCPluginSupport>();
            for (DBCPlugin p : getPluginManager().getAllPlugins()) {
                if (p.isEnabled()) {
                    int c = p.getConnectionSupportLevel(nativeConnection);
                    if (c >= 0) {
                        DBCPluginSupport s = new DBCPluginSupport();
                        s.plugin = p;
                        s.support = c;
                        supportInfos.add(s);
                    }
                }
            }
            Collections.sort(supportInfos);
            Class<DBCSession> DBCSessionClass = null;
            PluginDescriptor DBCSessionClassPlugin = null;
            String bestConnectionType = null;
            for (DBCPluginSupport supportInfo : supportInfos) {
                DBCPlugin dbcPlugin = supportInfo.plugin;
                int level = dbcPlugin.getConnectionSupportLevel(nativeConnection);
                if (level >= 0) {
                    Map<Class, Class> map = dbcPlugin.getConnectionFactoryMap(nativeConnection);
                    if (map != null) {
                        Class<DBCSession> DBCSessionClassCurrent = map == null ? null : map.get(DBCSession.class);
                        if (DBCSessionClassCurrent != null) {
                            DBCSessionClass = DBCSessionClassCurrent;
                            DBCSessionClassPlugin = dbcPlugin.getDescriptor();
                        }
                    }
                    String connectionTypeCurrent = dbcPlugin.getConnectionType(nativeConnection);
                    if (connectionTypeCurrent != null) {
                        bestConnectionType = connectionTypeCurrent;
                    }
                }
            }
            if (DBCSessionClass != null) {
                session = getFactory().newInstance(null, DBCSessionClass, DBCSessionClassPlugin);
            } else {
                session = getFactory().newInstance(DBCSession.class);
            }
            session.init(this, info, nativeConnection, pool, bestConnectionType);
            if (info.getId() >= 0) {
                DBCSessionInfo info0 = new DBCSessionInfo();
                info0.setId(info.getId());
                info0.setCnxFactoryName(session.getType());
                DBCDriverUrlParser parser = getDriverManager().getDriverUrlParser(info.getCnxDriver());
                if (parser != null) {
                    Properties properties = parser.parse(info.getCnxDriver(), info.getCnxUrl());
                    String serverValue = properties.getProperty(DBCDriverUrlParser.PARAM_SERVER);
                    info0.setServer(serverValue == null ? "(local)" : serverValue);
                }
                info0.setCnxLastUpdated(new java.sql.Date(System.currentTimeMillis()));
                getConfig().updateSession(info0);
            }
            sessions.add(session);
            pchsupport.firePropertyChange("session.added", null, session);
            session.addSessionListener(new DBCSessionListener() {

                public void sessionClosed(DBCSession session) {
                    sessions.remove(session);
                }
            });
            session.openSession();
//            if (SwingUtilities.isEventDispatchThread()) {
//                session.openSession();
//            } else {
//                Throwable exception = null;
//                class CallbackRunnable implements Runnable {
//
//                    private DBCSession session;
//
//                    CallbackRunnable(DBCSession session) {
//                        this.session = session;
//                    }
//
//                    public void run() {
//                        session.openSession();
//                    }
//                }
//                CallbackRunnable cr = new CallbackRunnable(session);
//                try {
//                    SwingUtilities.invokeAndWait(cr);
//                } catch (InvocationTargetException e) {
//                    exception = e.getTargetException();
//                } catch (Throwable e) {
//                    exception = e;
//                }
//                if (exception != null) {
//                    throw exception;
//                }
//            }
            return session;
        } catch (SQLException e) {
            if (session != null) {
                sessions.remove(session);
            }
            //this.getWindowManager().getSessionListWindow().showWindow();
            if (nativeConnection != null) {
                nativeConnection.close();
            }
            throw e;
        } catch (RuntimeException e) {
            if (session != null) {
                sessions.remove(session);
            }
            //this.getWindowManager().getSessionListWindow().showWindow();
            if (nativeConnection != null) {
                nativeConnection.close();
            }
            throw e;
        } catch (Throwable e) {
            if (session != null) {
                sessions.remove(session);
            }
            //this.getWindowManager().getSessionListWindow().showWindow();
            if (nativeConnection != null) {
                nativeConnection.close();
            }
            throw new RuntimeException(e);
        }
    }

    private static class DBCPluginSupport implements Comparable<DBCPluginSupport> {

        DBCPlugin plugin;
        int support;

        @Override
        public int compareTo(DBCPluginSupport o) {
            return support - o.support;
        }
    }
//    public void checkApplicationState(ApplicationState min) {
//        checkApplicationState(min,min);

    //    }
    public void checkApplicationState(ApplicationState min, ApplicationState max) {
        if (min != null) {
            if (applicationState.compareTo(min) < 0) {
                throw new IllegalStateException("Operation not allowed in state " + applicationState + ". Must wait for " + min);
            }
        }
        if (max != null) {
            if (applicationState.compareTo(max) > 0) {
                throw new IllegalStateException("Operation not allowed in state " + applicationState + ". Must not be after " + max);
            }
        }
    }

    public ApplicationState getApplicationState() {
        return applicationState;
    }

//    public TLog getLog() {
//        return log;
//    }
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

    public Version getLatestVersion() throws IOException {
        return new Version(new URL(getApplicationInfo().getPrimaryRepositoryURL() + "/version.txt"), "version");
    }

    public String getPreferredConfigImpl() {
        Properties properties = new Properties();//
        try {
            FileInputStream is = null;
            try {
                final File f = new File(getConfigDir(), ".config-impl");
                is = new FileInputStream(f);
                properties.loadFromXML(is);
            } finally {
                if (is != null) {
                    is.close();
                }
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return properties.getProperty("preferredConfigImpl");
    }

    public void setPreferredConfigImpl(String confName) {
        String old = getPreferredConfigImpl();
        FileOutputStream os = null;
        try {
            try {
                final File f = new File(getConfigDir(), ".config-impl");
                if (f.getParentFile() != null) {
                    f.getParentFile().mkdirs();
                }
                os = new FileOutputStream(f);
                Properties properties = new Properties();
                if (confName != null) {
                    properties.put("preferredConfigImpl", confName);
                }
                properties.storeToXML(os, "PreferredConfigImpl");
            } finally {
                if (os != null) {
                    os.close();
                }
            }
        } catch (IOException e) {
            //
        }
        pchsupport.firePropertyChange(PROPERTY_PREFERRED_CONFIG, old, confName);
    }

    public File getInstallDir() {
        return installFolder == null ? getWorkingDir() : installFolder;
    }

    public void setInstallDir(File installFolder) {
        File old = this.installFolder;
        this.installFolder = installFolder;
        pchsupport.firePropertyChange(PROPERTY_INSTALL_DIR, old, this.installFolder);
    }

    public void setVarDir(File varFolder) {
        File old = this.varFolder;
        this.varFolder = varFolder;
        pchsupport.firePropertyChange(PROPERTY_VAR_DIR, old, this.varFolder);
    }

    public DBCEncryptionManager getEncryptionManager() {
        checkApplicationState(ApplicationState.READY, ApplicationState.CLOSED);
        if (encryptionManager == null) {
            encryptionManager = getFactory().newInstance(DBCEncryptionManager.class);
        }
        return encryptionManager;
    }

    public Map<String, Object> getVariables() {
        return urlVariables;
    }

    public String rewriteString(String string) {
        for (Map.Entry<String, Object> entry : getVariables().entrySet()) {
            string = string.replace("${" + entry.getKey() + "}", String.valueOf(entry.getValue()));
        }
        return string;
    }

    public DBClientInfo getApplicationInfo() {
        return applicationInfo;
    }

    public BlendedFile getAppFile(String path) {
        return new BlendedFile(new File(getWorkingDir(), path), new File(getInstallDir(), path));
    }

    @Override
    public Logger getLogger(String category) {
        if (category == null || category.isEmpty()) {
            return Logger.getLogger(applicationUUID);
        }
        return Logger.getLogger(applicationUUID + "." + category);
    }
}
