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
package net.vpc.dbclient.plugin.system.drivermanager;

import net.vpc.dbclient.api.DBCApplication;
import net.vpc.dbclient.api.configmanager.DBCDriverInfo;
import net.vpc.dbclient.api.drivermanager.DBCDriverManager;
import net.vpc.dbclient.api.drivermanager.DBCDriverManagerListener;
import net.vpc.dbclient.api.pluginmanager.DBCAbstractPluggable;
import net.vpc.prs.plugin.Inject;
import net.vpc.dbclient.api.sql.urlparser.DBCDriverUrlParser;
import net.vpc.util.IOUtils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.vpc.dbclient.api.drivermanager.DBCDriverLibrary;
import net.vpc.prs.plugin.Initializer;
import net.vpc.prs.classloader.ProxyClassLoader;
import net.vpc.prs.softreflect.SoftClassNotFoundException;
import net.vpc.prs.softreflect.classloader.MultiSoftClassLoader;
import net.vpc.prs.softreflect.classloader.SoftClassLoader;
import net.vpc.prs.softreflect.classloader.URLSoftClassLoader;
import net.vpc.util.Version;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com) @creationtime 4 janv. 2007
 * 01:24:53
 */
public class DBCDriverManagerImpl extends DBCAbstractPluggable implements DBCDriverManager {

    private int nextLibrariesId = 1;
    @Inject
    private DBCApplication application;
//    private HashMap<String, Boolean> loadableDrivers = new HashMap<String, Boolean>();
//    private HashMap<String, Driver> loadedDrivers = new HashMap<String, Driver>();
    private HashMap<String, DriverMetaData> driversMetadata = new HashMap<String, DriverMetaData>();
    private DBCDriverLibrary defaultLibrary = null;
//    private ClassLoader defaultDriversClassLoader = null;
//    private SoftClassLoader softDefaultDriversClassLoader = null;
//    private SoftClassLoader softAlternativeDriversClassLoader = null;
    private final Map<String, DBCDriverLibrary> libraries = new HashMap<String, DBCDriverLibrary>();
    private Vector<DBCDriverManagerListener> listeners = new Vector<DBCDriverManagerListener>();
    private Vector<DBCDriverUrlParser> driverUrlParserList = new Vector<DBCDriverUrlParser>();
    private Logger logger;

    private static class DriverMetaData {

        String className;
        Driver driver;
        Boolean loadable;
        HashSet<URL> urls;
        ClassLoader classLoader;
//        SoftClassLoader libSoftClassLoader;
        SoftClassLoader softClassLoader;
    }

    public DBCDriverManagerImpl() {
    }

    @Initializer
    private void init() {
        logger = application.getLogger(DBCDriverManagerImpl.class.getName());
    }

    @Override
    public void addLibrary(String name, List<URL> resources, Version version) {
        synchronized (libraries) {
            DBCDriverLibrary library = new DBCDriverLibrary(nextLibrariesId, application, name, resources, version);
            if (libraries.containsKey(library.getName())) {
                throw new IllegalArgumentException("Library already exists");
            }
            nextLibrariesId++;
            libraries.put(library.getName(), library);
        }
    }

    @Override
    public void removeLibrary(String name) {
        if (libraries.remove(name) == null) {
            throw new NoSuchElementException("Library not found");
        }
    }

    private URL getDefaultLibrariesRepositoryURL() {
        try {
            String str = getDefaultLibraryRepository();
            str = application.rewriteString(str);
            return new File(str).toURI().toURL();
        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public DBCDriverLibrary getDefaultLibrary() {
        if (defaultLibrary == null) {
            URL url = getDefaultLibrariesRepositoryURL();
            ArrayList<URL> urls = new ArrayList<URL>();
            //alternatives are added by plugins so they may be loaded
            //thus we must put them as first choice in the class loader
            //urls.addAll(alternativeLibraryURLs);

            Stack<File> stack = new Stack<File>();
            stack.push(new File(url.getFile()));
            if (IOUtils.isFileURL(url)) {
                while (!stack.isEmpty()) {
                    File f = stack.pop();
                    if (f.isDirectory()) {
                        for (File file : f.listFiles()) {
                            stack.push(file);
                        }
                    } else {
                        if (f.getName().toLowerCase().endsWith(".jar") || f.getName().toLowerCase().endsWith(".zip")) {
                            try {
                                urls.add(f.toURL());
                            } catch (MalformedURLException ex) {
                                logger.log(Level.SEVERE, "Unable locate URL " + f, ex);
                            }
                        }
                    }
                }
            } else {
                throw new IllegalArgumentException("Unsupperted DefaultLibrariesURL");
            }
            defaultLibrary = new DBCDriverLibrary(0, application, "", urls, null);
            libraries.put("", defaultLibrary);
        }
        return defaultLibrary;
    }

//    public URL[] getDefaultLibraries() {
//        URL url = getDefaultLibrariesRepositoryURL();
//        ArrayList<URL> urls = new ArrayList<URL>();
//        //alternatives are added by plugins so they may be loaded
//        //thus we must put them as first choice in the class loader
////        urls.addAll(alternativeLibraryURLs);
//
//        Stack<File> stack = new Stack<File>();
//        stack.push(new File(url.getFile()));
//        if (IOUtils.isFileURL(url)) {
//            while (!stack.isEmpty()) {
//                File f = stack.pop();
//                if (f.isDirectory()) {
//                    for (File file : f.listFiles()) {
//                        stack.push(file);
//                    }
//                } else {
//                    if (f.getName().toLowerCase().endsWith(".jar") || f.getName().toLowerCase().endsWith(".zip")) {
//                        try {
//                            urls.add(f.toURL());
//                        } catch (MalformedURLException ex) {
//                            logger.log(Level.SEVERE,"Unable locate URL " + f, ex);
//                        }
//                    }
//                }
//            }
//        } else {
//            throw new IllegalArgumentException("Unsupperted DefaultLibrariesURL");
//        }
//        return urls.toArray(new URL[urls.size()]);
//    }
    public String getDefaultLibraryRepository() {
        return application.getConfig().getStringProperty("DriverManager.repository", "${app.dir}/jdbcdrivers");
    }

    public void setDefaultLibraryRepository(String newRepository) {
        if (newRepository != null) {
            application.getConfig().setStringProperty("DriverManager.repository", newRepository);
        }
    }

    public DBCDriverInfo[] getDrivers() {
        return application.getConfig().getDrivers();
    }

    public void addDriver(DBCDriverInfo desc) {
        application.getConfig().addDriver(desc);
    }

    public void updateDriver(DBCDriverInfo desc) {
        application.getConfig().updateDriver(desc);
        DriverMetaData m = getDriverMetaData(desc.getDriverClassName(), true);
        checkModification(m);
    }

    public void removeDriver(int driverId) {
        application.getConfig().removeDriver(driverId);
    }

    private DBCDriverLibrary resolveLibrary(String driverClass) {
        List<DBCDriverLibrary> list = new ArrayList<DBCDriverLibrary>();
        for (DBCDriverLibrary dBCDriverLibrary : libraries.values()) {
            if (dBCDriverLibrary.accept(driverClass)) {
                list.add(dBCDriverLibrary);
            }
        }
        Collections.sort(list);
        if (list.size() > 0) {
            //with larger version
            return list.get(list.size() - 1);
        }
        return null;
    }

    public synchronized Connection getConnection(String url, String driverClass, String user, String password, Properties properties) throws SQLException, ClassNotFoundException {
        if (driverClass == null || driverClass.length() == 0) {
            throw new RuntimeException("You must enter a correct Driver Class");
        }
        if (url == null || url.length() == 0) {
            throw new RuntimeException("You must enter a correct URL");
        }
        for (DBCDriverManagerListener listener : listeners) {
            listener.connectionCreating(url, driverClass, user, password, properties);
        }
        if (properties == null) {
            properties = new Properties();
        }
        if (user != null) {
            properties.put("user", user);
        }
        if (password != null) {
            properties.put("password", password);
        }
        DBCDriverInfo driverByClass = getDriverByClass(driverClass);
        String clz = driverByClass.getDriverClassName();
        DriverMetaData driverMetaData = getDriverMetaData(clz, true);
        if (driverMetaData.driver == null) {
            DBCDriverLibrary lib = resolveLibrary(clz);
            ClassLoader loader;
            if (lib == null && driverByClass.getLibraries().length == 0) {
                loader = application.getPluginManager().getCoreClassLoader();
                if (loader == null) {
                    loader = getClass().getClassLoader();
                }
            } else {
                List<ClassLoader> clist = new ArrayList<ClassLoader>();
                if (driverByClass.getLibraryCount() > 0) {
                    clist.add(new URLClassLoader(driverByClass.getLibraries()));
                }
                if (lib != null) {
                    clist.add(lib.getClassLoader());
                }
                clist.add(application.getPluginManager().getCoreClassLoader());
                loader = new ProxyClassLoader(clz + " Driver ClassLoader", clist.toArray(new ClassLoader[clist.size()]));
            }

//            URLClassLoader loader = driverByClass.getDriverClassLoader();
            Class<?> aClass = Class.forName(clz, true, loader);
            //I don't know why this does not work?
            //verify if class is loaded!!
//            Class<?> aClass = (loader == null ? getClass().getClassLoader() : loader). loadClass(clz);
            try {
                driverMetaData.driver = (Driver) aClass.newInstance();
            } catch (InstantiationException ex) {
                logger.log(Level.SEVERE, "Unable Create Driver " + aClass, ex);
                throw new ClassCastException(clz);
            } catch (IllegalAccessException ex) {
                logger.log(Level.SEVERE, "Unable Create Driver " + aClass, ex);
                throw new ClassCastException(clz);
            }
            driverMetaData.classLoader = loader;
        }
        Connection connection = driverMetaData.driver.connect(application.rewriteString(url), properties);
        if (connection == null) {
            throw new SQLException("No suitable driver", "08001");
        }
        for (DBCDriverManagerListener listener : listeners) {
            listener.connectionCreated(connection, url, driverClass, user, password, properties);
        }
        return connection;
    }

    public DBCDriverInfo getDriverByClass(String driverClass) {
        if (driverClass != null) {
            for (DBCDriverInfo dbcDriver : getDrivers()) {
                if (driverClass.equals(dbcDriver.getDriverClassName())) {
                    return dbcDriver;
                }
            }
        }
        throw new NoSuchElementException("Unknow Driver " + driverClass);
    }

    public void addListener(DBCDriverManagerListener listener) {
        listeners.add(listener);
    }

    public void removeListener(DBCDriverManagerListener listener) {
        listeners.remove(listener);
    }

    public DBCDriverUrlParser getDriverUrlParser(String driver) {
        int bestValue = -1;
        DBCDriverUrlParser bestParser = null;
        for (DBCDriverUrlParser parser : driverUrlParserList) {
            if (driver != null) {
                int v = parser.acceptDriver(driver);
                if (v > 0) {
                    if (bestValue < 0 || bestParser == null || v > bestValue) {
                        bestValue = v;
                        bestParser = parser;
                    }
                }
            }
        }
//        if (bestParser == null) {
//            throw new NoSuchElementException("No Parser for " + driver);
//        }
        return bestParser;
    }

    public void addDriverUrlParser(DBCDriverUrlParser parser) {
        driverUrlParserList.add(parser);
    }

    public void removeDriverUrlParser(DBCDriverUrlParser parser) {
        driverUrlParserList.remove(parser);
    }

    public Collection<DBCDriverUrlParser> getDriverUrlParsers() {
        return (Collection<DBCDriverUrlParser>) driverUrlParserList.clone();
    }

//    public synchronized boolean isLoaded() {
//        if (loadCache == null) {
//            try {
//                Class.forName(getDriverClassName(), true, getDriverClassLoader());
//                loadCache = true;
//            } catch (Throwable e) {
//                //
//                loadCache = false;
//            }
//        }
//        return loadCache;
//    }
    public boolean isLoadableDriver(String driverClassName) {
        DriverMetaData d = getDriverMetaData(driverClassName, true);
        Boolean b = d.loadable;
        if (b == null) {
            if (d.softClassLoader == null) {
                DBCDriverInfo di = getDriverByClass(driverClassName);

                DBCDriverLibrary lib = resolveLibrary(di.getDriverClassName());
                SoftClassLoader loader;
                if (lib == null && di.getLibraries().length == 0) {
                    loader = application.getPluginManager().getSoftCoreClassLoader();
//                    if(loader==null){
//                        loader=getClass().getClassLoader();
//                    }
                } else {
                    List<SoftClassLoader> clist = new ArrayList<SoftClassLoader>();
                    if (di.getLibraryCount() > 0) {
                        clist.add(new URLSoftClassLoader(d.className + ":libs", di.getLibraries(), null, application.getPluginManager().getSoftCoreClassLoader()));
                    }
                    if (lib != null) {
                        clist.add(lib.getSoftClassLoader());
                    }
                    clist.add(application.getPluginManager().getSoftCoreClassLoader());
                    loader = new MultiSoftClassLoader(di.getDriverClassName() + " Driver ClassLoader", clist.toArray(new SoftClassLoader[clist.size()]), application.getPluginManager().getSoftCoreClassLoader());
                }
                d.softClassLoader = loader;
            }
            try {
                d.softClassLoader.findClass(driverClassName);
                d.loadable = true;
            } catch (SoftClassNotFoundException e) {
                d.loadable = false;
            }
        }
        return d.loadable.booleanValue();
    }

    public DriverMetaData getDriverMetaData(String driver, boolean create) {
        DriverMetaData v = driversMetadata.get(driver);
        if (v != null || !create) {
            return v;
        }
        v = new DriverMetaData();
        v.className = driver;
        driversMetadata.put(driver, v);
        return v;
    }

    private boolean checkModification(DriverMetaData driverMetaData) {
            DBCDriverLibrary lib = resolveLibrary(driverMetaData.className);
            HashSet<URL> oldUrls = new HashSet<URL>();
            if (driverMetaData.urls != null) {
                oldUrls.addAll(driverMetaData.urls);
            }
            HashSet<URL> newUrls = new HashSet<URL>();
            if (lib != null) {
                List<URL> r = lib.getResources();
                if (r != null) {
                    newUrls.addAll(r);
                }
            }
            driverMetaData.urls=newUrls;
            if (driverMetaData.urls==null || !oldUrls.equals(newUrls)) {
                driverMetaData.softClassLoader=null;
                driverMetaData.classLoader=null;
                driverMetaData.loadable=null;
                return true;
            }
            return false;
    }
}