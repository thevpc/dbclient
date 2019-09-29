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


package net.vpc.dbclient.api;

import net.vpc.dbclient.api.configmanager.DBCApplicationConfig;
import net.vpc.dbclient.api.configmanager.DBCSessionInfo;
import net.vpc.dbclient.api.drivermanager.DBCDriverManager;
import net.vpc.dbclient.api.encryptionmanager.DBCEncryptionManager;
import net.vpc.dbclient.api.pluginmanager.DBCApplicationFactory;
import net.vpc.dbclient.api.pluginmanager.DBCPluginManager;
import net.vpc.prs.plugin.BlendedFile;
import net.vpc.prs.plugin.PluggableApplication;
import net.vpc.util.ProgressMonitor;
import net.vpc.util.Version;

import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * This is DBClient API Main Interface.
 * <p/>
 * It represents the Application Interface that holds ALL of DBClient elements.
 *
 * @author vpc
 */
public interface DBCApplication extends PluggableApplication, DBClientContext {
    public static final Locale BOOT_LOCALE = Locale.getDefault();
    public static final String HOME_WORKING_DIRECTORY = System.getProperty("user.home") + "/.java-apps/database/dbclient/"+DBClientInfo.INSTANCE.getProductMajorVersion()+"/";
    public static final String PROPERTY_APPLICATION_STATE = "applicationState";
    public static final String PROPERTY_APPLICATION_MODE = "applicationMode";
    public static final String PROPERTY_WORKING_DIR = "workingDir";
    public static final String PROPERTY_INSTALL_DIR = "installDir";
    public static final String PROPERTY_VAR_DIR = "varDir";
    public static final String PROPERTY_CONFIG_DIR = "configDir";
    public static final String PROPERTY_PREFERRED_CONFIG = "preferredConfig";
    public static final String PROPERTY_CLOSING = "closing";
    public static final String PROPERTY_CLOSED = "closed";

    public Properties getApplicationProperties();
    
    public DBCApplicationView getView();

    void checkApplicationState(ApplicationState min, ApplicationState max);

    void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);

    void removePropertyChangeListener(String propertyName, PropertyChangeListener listener);

    void addPropertyChangeListener(PropertyChangeListener listener);

    void removePropertyChangeListener(PropertyChangeListener listener);

    DBCApplicationFactory getFactory();

    /**
     * Defines the different States for the DBClient Application
     * Order is as follow
     * <ol>
     * <li>INITIALIZING</li>
     * <li>PLUGINS_INITIALIZED</li>
     * <li>OPENING</li>
     * <li>READY</li>
     * <li>CLOSING</li>
     * <li>CLOSED</li>
     * </ol>
     */
    public static enum ApplicationState {

        /**
         * Initial State, No Plugin Initialized
         */
        INITIALIZING,

        /**
         * After All Plugins Are Initialized
         */
        CONFIGURING,

        /**
         * Plugins and Configuration are Initialized (NEw Plugins installed) And Application Is Almost ready
         */
        OPENING,

        /**
         * Application Initialization In finished, Application Is Ready
         */
        READY,

        /**
         * Application is Closing
         */
        CLOSING,

        /**
         * Application is Closed
         */
        CLOSED,


    }

    /**
     * Option for Quitting DBClient (quit method)
     */
    public static enum CloseOption {
        /**
         * Call dispose() and then System.exist(0)
         */
        EXIT,
        /**
         * Call dispose Only
         */
        DISPOSE,
        /**
         * Do nothing
         */
        DO_NOTHING
    }

    /**
     * Mode on which Application will start
     */
    public static enum ApplicationMode {
        /**
         * Default Mode, All plugins will be loaded
         */
        DEFAULT,
        /**
         * Safe Mode, NO plugins will be loaded
         */
        SAFE
    }


    /**
     * Application State
     *
     * @return Application State
     */
    public ApplicationState getApplicationState();


    /**
     * DBCApplicationConfig is the Class responsible of storing and querying
     * DBClient configuration.
     *
     * @return ConfigManager instance
     */
    public DBCApplicationConfig getConfig();

    /**
     * Preferred implementation of DBCApplicationConfig.
     *
     * @return preferred implementation of DBCApplicationConfig
     */
    public String getPreferredConfigImpl();

    /**
     * Change Preferred implementation of DBCApplicationConfig.
     *
     * @param className new Implementation Class Name
     */
    public void setPreferredConfigImpl(String className);

    /**
     * Install Folder is the Root Folder for DBClient Installation
     *
     * @return Install Folder
     */
    public File getInstallDir();

    /**
     * Change Install Folder
     * Install Folder is the Root Folder for DBClient Installation
     *
     * @param installFolder new location
     */
    public void setInstallDir(File installFolder);

    public void setVarDir(File varFolder);

    /**
     * Working Folder is the Root DBClient Folder (for config, plugins, temp, ...)
     * Working Folder usually is <pre>getInstallDir()</pre> but could be <pre>HOME_WORKING_DIRECTORY</pre>
     *
     * @return Working Folder
     */
    public File getWorkingDir();

    public File getVarDir();

    /**
     * Change Working Folder
     * Working Folder is the Root DBClient Folder (for config, plugins, temp, ...)
     * Working Folder usually is <pre>getInstallDir()</pre> but could be <pre>HOME_WORKING_DIRECTORY</pre>
     *
     * @param workingDir new location
     */
    public void setWorkingDir(File workingDir);

    /**
     * Config Folder is used by DBCApplicationConfig to store all DBClient configuration information
     * Config Folder usually is <pre>getWorkingDir()+"/config"</pre>
     *
     * @return Config Folder
     */
    public File getConfigDir();

    /**
     * Change Config Folder location
     * Config Folder is used by DBCApplicationConfig to store all DBClient configuration information
     * Config Folder usually is <pre>getWorkingDir()+"/config"</pre>
     *
     * @param cf
     */
    void setConfigDir(File cf);

    /**
     * Latest Available Version.
     * To get Current Version simply use <pre>new Version(DBClientInfo.PRODUCT_VERSION)</pre>
     *
     * @return Latest Version deployed on DBClient Web Site
     * @throws java.io.IOException is server is non accessible
     */
    public Version getLatestVersion() throws IOException;


    public DBClientInfo getApplicationInfo();

    /**
     * Driver Manager
     *
     * @return Driver Manager
     */
    public DBCDriverManager getDriverManager();

    /**
     * EncryptionManager is responsible or encrypting/decrypting passwords by config managers
     *
     * @return EncryptionManager
     */
    public DBCEncryptionManager getEncryptionManager();

    /**
     * Plugin Manager
     *
     * @return Plugin Manager
     */
    public DBCPluginManager getPluginManager();


    /**
     * @return all open sessions
     */
    public DBCSession[] getSessions();


    public void start(Properties applicationProperties, ProgressMonitor monitor);

    public DBCSession openSession(String sessionName) throws SQLException, ClassNotFoundException;

    public DBCSession openSession(int sessionId) throws SQLException, ClassNotFoundException;

    public DBCSession openSession(DBCSessionInfo info) throws SQLException, ClassNotFoundException;


    /**
     * returns CloseOption
     *
     * @return CloseOption
     */
    public CloseOption getCloseOption();

    /**
     * Changes CloseOption
     *
     * @param closeOption new Option
     */
    public void setCloseOption(CloseOption closeOption);

    /**
     * closes DBClient.
     * Behaviour depends on the CloseOption
     */
    public void close();


    /**
     * Arms DBClient for the given Mode
     * Safe Mode tells to DBClient NOT to load Plugins
     * Normal Mode tells to DBClient to load All Plugins
     * Callable only if ApplicationState &lt;= INITIALIZING.
     *
     * @param mode the mode
     */
    public void setApplicationMode(ApplicationMode mode);

    /**
     * True if Safe mode.
     * <p/>
     * Safe Mode tells to DBClient NOT to load Plugins
     *
     * @return true if safe mode
     */
    public ApplicationMode getApplicationMode();

    /**
     * Map for variables that may be used in JDBC urls (mainly but not solely).
     * Returned Map may content Any Object but when rewriting objects toString()
     * will be called on each of them.
     *
     * @return Map<String, Object>
     */
    public Map<String, Object> getVariables();

    /**
     * relaces all <pre>${varName}</pre> by <pre>getVariables().get("varName").toString()</pre>
     *
     * @param string string to rewrite (actually jdbc url)
     * @return rewritten string
     */
    public String rewriteString(String string);

    public BlendedFile getAppFile(String path);
    
//    public Logger getLogger(String category);
    
//    public Logger getApplicationLogCategory();
//    
//    public Logger getApplicationLog(String category);
//    public Logger getSessionLog(int sessionId,String category);
//    public Logger getPluginLog(String plugin,String category);

}
