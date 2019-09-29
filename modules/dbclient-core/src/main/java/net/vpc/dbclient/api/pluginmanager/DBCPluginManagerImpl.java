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
package net.vpc.dbclient.api.pluginmanager;

import net.vpc.log.TLog;
import net.vpc.log.TLoadableLogList;
import net.vpc.log.TLogStringBuffer;
import net.vpc.dbclient.api.DBCApplication;
import net.vpc.dbclient.api.DBCApplicationView;
import net.vpc.dbclient.api.DBCDefaultPlugin;
import net.vpc.dbclient.api.DBCSession;
import net.vpc.dbclient.api.actionmanager.DBCActionManager;
import net.vpc.dbclient.api.actionmanager.DBCApplicationAction;
import net.vpc.dbclient.api.configmanager.DBCApplicationConfig;
import net.vpc.dbclient.api.drivermanager.DBCDriverManager;
import net.vpc.dbclient.api.sql.urlparser.DBCDriverUrlParser;
import net.vpc.prs.plugin.BlendedFile;
import net.vpc.swingext.PRSManager;
import net.vpc.prs.factory.ExtensionDescriptor;
import net.vpc.prs.plugin.*;
import net.vpc.swingext.dialog.MessageDialogType;
import net.vpc.util.*;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import net.vpc.dbclient.api.drivermanager.DBCDriverLibrary;
import net.vpc.log.TBufferedLog;
import net.vpc.log.TLogPrintStream;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com) @creationtime 15 nov. 2006
 * 23:39:30
 */
@Ignore
public class DBCPluginManagerImpl extends DefaultPluginManager<DBCApplication, DBCPlugin> implements DBCPluginManager {

    private Logger logger;

    public DBCPluginManagerImpl() {


        getPluginLoader().addListener(new PluginLoaderListener() {

            public void pluginDescriptorCreated(PluginDescriptor pluginInfo) {
                TLogStringBuffer loadable = new TLogStringBuffer();
                //TLog appLog = getApplication().getLog();
                TLog lo = null;
                try {
                    TLogPrintStream fileLog = new TLogPrintStream(new File(getPluginVarFolder(pluginInfo.getId()), "log/plugin.log"));
                    lo = new TBufferedLog(fileLog);
                } catch (Exception e) {
                    throw new IllegalArgumentException("Unable to create plugin log file", e);
                }
                TLoadableLogList log = new TLoadableLogList(loadable, lo/*
                         * , appLog
                         */);
                pluginInfo.setLog(log);
                log.trace("Preparing plugin for loading...");
            }

            public void prepareResources(PluginDescriptor pluginInfo, String newCRC) {
                //
            }

            public void pluginDescriptorReloaded(PluginDescriptor pluginInfo) {
                //
            }
        });
    }

    @Override
    public void init(DBCApplication app, Version applicationVersion) throws PluginException {
        super.init(app, applicationVersion);
        logger = getApplication().getLogger(DBCPluginManagerImpl.class.getName());
    }

    @Override
    public void buildCreatePlugins(ProgressMonitor monitor) {
        getApplication().getFactory().registerExtensions(getExtensions());
        super.buildCreatePlugins(monitor);
    }

    /**
     * Default implementation will do the following <ol> <li>Load System plugin.
     * If no system plugins or more than one system plugin is found an
     * IllegalStateException will be thrown </li> <li>Initialize the System
     * plugin {call to applicationInitializing(Plugin)}</li> <li>Configure
     * DBClient.factory from
     * getApplication().getDefaultFactoryConfigurations()</li> <li>Initialize
     * Plugins that are non system, valid and enabled {call to
     * applicationInitializing(Plugin)}</li> <li>Store Factory item as Startup
     * Implementations</li> </ol>
     *
     * @param monitor
     */
    @Override
    public void applicationInitializing(ProgressMonitor monitor) {
        Chronometer chronometer = new Chronometer();
        chronometer.start();
        build(monitor);
        chronometer.stop();
        logger.log(Level.INFO, "[PERF] {0}", new Object[]{chronometer});
    }

    @Override
    protected void buildPostCreatePlugins(ProgressMonitor monitor) throws PluginException {
        DBCPlugin[] validPlugins = getEnabledPlugins();
        for (DBCPlugin plugin : validPlugins) {
            try {
                PluginDescriptor pluginInfo = plugin.getDescriptor();
                UrlCacheManager actualUrlCacheManager = pluginInfo.getUrlCacheManager();
                ClassLoader pluginClassLoader = pluginInfo.getClassLoader();
                URL pluginURL = pluginInfo.getPluginURL();
                try {
                    PRSManager.registerMessageSet(pluginURL, pluginClassLoader, pluginInfo, actualUrlCacheManager, getApplication());
                } catch (NoSuchElementException e) {
                    //ignore
                } catch (Throwable e) {
                    throw new PluginException(plugin.getId(), "Unable to register message set " + pluginURL, e);
                }
                //load iconsets sets if any
                try {
                    PRSManager.registerIconSet(pluginURL, pluginClassLoader, pluginInfo, actualUrlCacheManager, getApplication());
                } catch (NoSuchElementException e) {
                    //ignore
                } catch (Throwable e) {
                    throw new PluginException(plugin.getId(), "Unable to register icon set " + pluginURL, e);
                }
                //load artsets sets if any
                try {
                    PRSManager.registerArtSets(pluginURL, pluginClassLoader, pluginInfo);
                } catch (NoSuchElementException e) {
                    //ignore
                } catch (Throwable e) {
                    throw new PluginException(plugin.getId(), "Unable to register art set " + pluginURL, e);
                }
                plugin.setInitialized(true);
                plugin.applicationInitializing();
            } catch (Throwable e) {
                logger.log(Level.SEVERE, "Faild to initialize" + plugin, e);
                getApplication().getView().getDialogManager().showMessage(null, e.toString(), MessageDialogType.ERROR, null, e);
            }
            //splashScreen.setProgress(progressInit + 3 * progressStep + progressStep * progressIndex / validPlugins.length);
            //progressIndex++;
        }
    }

    public Collection<PluginDescriptor> getValidPluginDescriptors() {
        Collection<PluginDescriptor> descriptors = getPluginDescriptors();
        Collection<PluginDescriptor> ret = new ArrayList<PluginDescriptor>(descriptors.size());

        for (PluginDescriptor pluginInfo : descriptors) {
            if (pluginInfo.isValid() && isPluginEnabled(pluginInfo.getId())) {
                ret.add(pluginInfo);
            }
        }
        return ret;
    }

    @Override
    public void applicationConfiguring(ProgressMonitor monitor) {
        DBCApplicationView applicationView = getApplication().getView();
        ProgressMonitor[] mon = ProgressMonitorUtils.split(monitor, 2);

        //now reload implementations from config
        DBCApplicationConfig applicationConfig = getApplication().getConfig();
        ExtensionDescriptor[] extensions = getApplication().getFactory().getExtensions();

        StepProgressMonitor extensionsMonitor = new StepProgressMonitor(mon[0], extensions.length);

        extensionsMonitor.progressStart(this, "Loading components configuration...");
        for (ExtensionDescriptor extension : extensions) {
            String s = applicationConfig.getStringProperty("factory." + extension.getId().getName(), null);
            try {
                extension.setImpl(s);
            } catch (NoSuchElementException e) {
                //ignore if not found!
            }
            extensionsMonitor.progressStep(this, "Loading components configuration...");
        }
        extensionsMonitor.progressEnd(this, "Loading components configuration...");

        //verify plugin state (installed or not) for the current Config
        //if not call plugin.pluginInstalled() and mark it as installed
        DBCPlugin[] validPlugins = getEnabledPlugins();
        StepProgressMonitor pluginsMonitor = new StepProgressMonitor(mon[0], validPlugins.length);
        pluginsMonitor.progressStart(this, "Loading plugins configuration...");
        for (DBCPlugin plugin : validPlugins) {
            String pluginPrefix = "Plugin." + plugin.getId() + ".";
            String s = applicationConfig.getStringProperty(pluginPrefix + "InstallStatus", null);
            String v = applicationConfig.getStringProperty(pluginPrefix + "Version", null);
            if ("installed".equals(s)) {
                //do nothing
            } else if (s == null || ("failed".equals(s) && new Version(v).equals(plugin.getDescriptor().getVersion()))) {
                applicationConfig.setStringProperty(pluginPrefix + "Version", plugin.getDescriptor().getVersion().toString());
                try {
                    plugin.pluginInstalled();
                    applicationConfig.setStringProperty(pluginPrefix + "InstallStatus", "installed");
                } catch (Throwable e) {
                    applicationConfig.setStringProperty(pluginPrefix + "InstallStatus", "failed");
                    applicationView.getDialogManager().showMessage(null, null, MessageDialogType.ERROR, null, e);
                }
            }
            pluginsMonitor.progressStep(this, "Loading plugins configuration...");
        }

        //call plugin.applicationOpening() for all Plugins
        DBCApplicationFactory factory = getApplication().getFactory();
        DBCDriverManager dbcDriverManager = getApplication().getDriverManager();

        for (DBCDriverUrlParser implementation : factory.createImplementations(DBCDriverUrlParser.class)) {
            dbcDriverManager.addDriverUrlParser(implementation);
        }
        for (DBCPlugin plugin : validPlugins) {
            try {
                PluginManagerCache.PluginCache cache = getCache().getPluginCache(plugin.getId());

                PluginDescriptor pluginInfo = plugin.getDescriptor();
                if (cache.getMessageSet() != null && cache.getMessageSet().length() > 0) {
                    plugin.setMessageSet(cache.getMessageSet());
                }
                if (cache.getIconSet() != null && cache.getIconSet().length() > 0) {
                    plugin.setIconSet(cache.getIconSet());
                }
                if (DBCPlugin.CATEGORY_DBDRIVER.equals(pluginInfo.getCategory())) {
                    try {
                        List<URL> libURLs = new ArrayList<URL>();
                        for (URL url : pluginInfo.getResources()) {
                            if (!url.equals(pluginInfo.getPluginURL())) {
                                libURLs.add(url);
                            }
                        }
                        if(libURLs.size()>0){
                            getApplication().getDriverManager().addLibrary(pluginInfo.getId(), libURLs, null);
                        }
                    } catch (Throwable e) {
                        logger.log(Level.SEVERE, "Faild to configure dbdriver libraries for " + plugin, e);
                    }
                }
            } catch (Throwable e) {
                getApplication().getView().getDialogManager().showMessage(null, null, MessageDialogType.ERROR, null, e);
            }

            //splashScreen.progressStart(new ProgressEvent(this,progressInit + progressStep * progressIndex / validPlugins.length,"Opening plugins..."));
        }
        for (DBCPlugin plugin : validPlugins) {
            plugin.applicationConfiguring();
        }
//        getApplication().getDriverManager().configure();

        getApplication().getView().getMessageSet().revalidate();//TOTO
        pluginsMonitor.progressEnd(this, "Application configured...");
    }

    @Override
    public void applicationOpening(ProgressMonitor monitor) {
        DBCPlugin[] validPlugins = getEnabledPlugins();

        //call plugin.applicationOpening() for all Plugins
        for (DBCPlugin plugin : validPlugins) {
            try {
                plugin.applicationOpening();
            } catch (Throwable e) {
                getApplication().getView().getDialogManager().showMessage(null, null, MessageDialogType.ERROR, null, e);
            }
            //splashScreen.progressStart(new ProgressEvent(this,progressInit + progressStep * progressIndex / validPlugins.length,"Opening plugins..."));
        }

        //create Action for All Plugins

        ArrayList<DBCApplicationAction> all = new ArrayList<DBCApplicationAction>();
        for (DBCApplicationAction ii : getApplication().getFactory().createImplementations(DBCApplicationAction.class)) {
            all.add(ii);
            //splashScreen.progressStart(new ProgressEvent(this,progressInit + progressStep + progressStep * progressIndex / validPlugins.length,"Creating Actions..."));
        }

        Collections.sort(all);
        DBCActionManager actionManager = getApplication().getView().getActionManager();
        for (DBCApplicationAction action : all) {
            PRSManager.update(action, action);
            actionManager.registerAction(action);
            //splashScreen.progressStart(new ProgressEvent(this,progressInit + progressStep + progressStep * progressIndex / all.size(),"Registering Actions..."));
        }
        //splashScreen.progressStart(new ProgressEvent(this,progressInit + progressMax,"Opening Main Window ..."));
    }

    public void applicationReady(ProgressMonitor monitor) {
        //just call plugin.applicationReady() for all plugins if any further things to do
        DBCPlugin[] validPlugins = getEnabledPlugins();

        for (DBCPlugin plugin : validPlugins) {
            try {
                plugin.applicationReady();
            } catch (Throwable e) {
                getApplication().getView().getDialogManager().showMessage(null, null, MessageDialogType.ERROR, null, e);
            }
            //splashScreen.progressStart(new ProgressEvent(this,progressInit + progressMax,"Plugins components setup..."));
        }
        //splashScreen.progressEnd(new ProgressEvent(this,progressInit + progressMax,"Application Ready..."));
    }

    public DBCPluginSession createPluginSession(DBCPlugin plugin, DBCSession session) {
        DBCPluginSession s = plugin.createPluginSession(session);
        if (s == null) {
            DBCPluginSession ps = null;
            PluginDescriptor pluginInfo = plugin.getDescriptor();
            if (ps == null) {
                PluginManagerCache.PluginCache cache = getCache().getPluginCache(pluginInfo.getId());
                Set<PluginManagerCache.ImplementationCache> pluginSessionImpls = cache.getImplementations(DBCPluginSession.class);
                if (pluginSessionImpls != null) {
                    for (PluginManagerCache.ImplementationCache aClassName : pluginSessionImpls) {
                        //Ok
                        try {
                            Class<?> aClass = Class.forName(aClassName.getImplementationName(), true, pluginInfo.getClassLoader());
                            ps = (DBCPluginSession) session.getFactory().newInstance(null, aClass, pluginInfo);
                            break;
                        } catch (Exception ex) {
                            plugin.getLogger(DBCPluginManagerImpl.class.getName()).log(Level.SEVERE, "Unable to create plugin session", ex);
                            //fall back
                        }
                    }
                }
            }

            if (ps == null) {
                ps = session.getFactory().newInstance(DBCDefaultPluginSession.class);
            }
            ps.init(plugin, session);
            return ps;
        }
        s.init(plugin, session);
        return s;
    }

//    public Set<Class> getInterfaces(DBCPlugin plugin) {
//        DBCPluginCache cc = getPluginCache(plugin);
//        return cc.getInterfaces();
//    }
//
    public Class<DBCPlugin> getPluginClass() {
        return DBCPlugin.class;
    }

    public File getPluginVarFolder(String pluginId) throws PluginException {
        return new File(getPluginsVarFolder(), pluginId);
    }

    public File getPluginsVarFolder() throws PluginException {
        return new File(getApplication().getVarDir(), "plugins");
    }

    public BlendedFile getPluginsFolder() throws PluginException {
        return getApplication().getAppFile("plugins");
    }

    @Override
    protected void buildStart(ProgressMonitor monitor) throws PluginException {
//        setLog(getApplication().getLog());
        BlendedFile folderPlugins = getPluginsFolder();
        try {
            //findout core library
            File coreFile = null;
            Pattern corePattern = Pattern.compile("(.*[/\\\\]dbclient-core.*\\.jar)|(.*[/\\\\]dbclient-core[/\\\\]target[/\\\\]classes)");
            for (String path : System.getProperty("java.class.path").split(File.pathSeparator)) {
                if (corePattern.matcher(path).matches()) {
                    coreFile = new File(path);
                    break;
                }
            }
            if (coreFile == null) {
                throw new PluginException("dbclient-core", "dbclient-core jar folder not found");
            }
//            AppFile dbclient_jar=getApplication().getAppFile("lib/dbclient.jar");
            setCoreURLs(Arrays.asList(coreFile.toURI().toURL()));
        } catch (MalformedURLException ex) {
            logger.log(Level.SEVERE, "prePrepare failed", ex);
        }

        //setUrlCacheManager(new UrlCacheManager(folderWorkJar));
        //getUrlCacheManager().clearCacheFolder();
        setLocalRepository(new LocalRepositoryImpl(folderPlugins, getApplication().getVarDir()));
        setSafeMode(getApplication().getApplicationMode().equals(DBCApplication.ApplicationMode.SAFE));
    }

    @Override
    protected void buildPostPrepare(ProgressMonitor monitor) throws PluginException {
        try {
            addPluginRepository(new DefaultPluginRepository(new URL(getApplication().getApplicationInfo().getPrimaryRepositoryURL())));
        } catch (IOException e) {
            logger.log(java.util.logging.Level.SEVERE, null, e);
        }

        //support for personalized repositories list
        //the only supported format is plan text
//        File folderPlugins = getPluginsFolder();
        File extraRepositoriesFile = new File(getApplication().getConfigDir(), "repositories");
        if (extraRepositoriesFile.exists()) {
            BufferedReader r = null;
            try {
                r = new BufferedReader(new FileReader(extraRepositoriesFile));
                String line = null;
                if ((line = r.readLine()) != null) {
                    try {
                        line = line.trim();
                        if (!line.startsWith("#") && !line.startsWith("%") && !line.startsWith("--") && line.length() > 0) {
                            addPluginRepository(new DefaultPluginRepository(new URL(line)));
                        }
                    } catch (Exception e) {
                        logger.log(java.util.logging.Level.SEVERE, "Unable to add repository : " + line, e);
                    }
                }
            } catch (Exception e) {
                logger.log(java.util.logging.Level.SEVERE, null, e);
            } finally {
                try {
                    if (r != null) {
                        r.close();
                    }
                } catch (Exception e) {
                    //ignore
                }
            }
        }
    }

    protected DBCPlugin createPluginImpl(PluginDescriptor pluginInfo) {
        String pluginClassName = pluginInfo.getPluginClassName();
        if (pluginClassName == null || pluginClassName.trim().length() == 0) {
            PluginManagerCache.PluginCache cache = getCache().getPluginCache(pluginInfo.getId());
            Set<PluginManagerCache.ImplementationCache> implementations = cache.getImplementations(DBCPlugin.class);
            if (implementations != null) {
                for (PluginManagerCache.ImplementationCache implementation : implementations) {
                    try {
                        Class<?> implClass = Class.forName(implementation.getImplementationName(), true, pluginInfo.getClassLoader());
                        if (!DBCDefaultPlugin.class.isAssignableFrom(implClass)) {
                            DBCPlugin plugin = (DBCPlugin) getApplication().getFactory().newInstance(null, implClass, pluginInfo);
                            return plugin;
                        }
                    } catch (Exception e) {
                        //ignore and fallback
                    }
                }
            }
            return getApplication().getFactory().newInstance(DBCDefaultPlugin.class);
        } else {
            try {
                Class<DBCPlugin> aClass = (Class<DBCPlugin>) Class.forName(pluginInfo.getPluginClassName(), true, pluginInfo.getClassLoader());
                DBCPlugin plugin = getApplication().getFactory().newInstance(null, aClass, pluginInfo);
                return plugin;
            } catch (Throwable e) {
                pluginInfo.setLoaded(false);
                pluginInfo.getLog().error("Unable to load Plugin " + pluginInfo.getId(), e);
                return getApplication().getFactory().newInstance(DBCDefaultPlugin.class);
            }
        }
    }
}
