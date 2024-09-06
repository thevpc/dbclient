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
package net.thevpc.dbclient.plugin.cfgsupport.cfgderby;

import java.util.logging.Level;

import net.thevpc.common.prs.Version;
import net.thevpc.dbclient.api.DBCApplication;
import net.thevpc.dbclient.api.configmanager.*;
import net.thevpc.dbclient.api.pluginmanager.DBCPlugin;
import net.thevpc.common.prs.plugin.Ignore;
import net.thevpc.common.prs.plugin.Inject;
import net.thevpc.dbclient.api.sql.parser.SQLParser;
import net.thevpc.dbclient.api.sql.parser.SQLStatement;
import net.thevpc.dbclient.plugin.cfgsupport.cfgderby.dbimpl.tconfigprop.TConfigPropDAO;
import net.thevpc.dbclient.plugin.cfgsupport.cfgderby.dbimpl.tconfigprop.TConfigPropDTO;
import net.thevpc.dbclient.plugin.cfgsupport.cfgderby.dbimpl.tconfigprop.TConfigPropFilter;
import net.thevpc.dbclient.plugin.cfgsupport.cfgderby.dbimpl.tconfigprop.TConfigPropKey;
import net.thevpc.dbclient.plugin.cfgsupport.cfgderby.dbimpl.tdriver.TDriverDAO;
import net.thevpc.dbclient.plugin.cfgsupport.cfgderby.dbimpl.tdriver.TDriverDTO;
import net.thevpc.dbclient.plugin.cfgsupport.cfgderby.dbimpl.tdriver.TDriverFilter;
import net.thevpc.dbclient.plugin.cfgsupport.cfgderby.dbimpl.tdriver.TDriverKey;
import net.thevpc.dbclient.plugin.cfgsupport.cfgderby.dbimpl.tdriverlib.TDriverLibDAO;
import net.thevpc.dbclient.plugin.cfgsupport.cfgderby.dbimpl.tdriverlib.TDriverLibDTO;
import net.thevpc.dbclient.plugin.cfgsupport.cfgderby.dbimpl.tdriverlib.TDriverLibFilter;
import net.thevpc.dbclient.plugin.cfgsupport.cfgderby.dbimpl.tmessage.TMessageDAO;
import net.thevpc.dbclient.plugin.cfgsupport.cfgderby.dbimpl.tmessage.TMessageDTO;
import net.thevpc.dbclient.plugin.cfgsupport.cfgderby.dbimpl.tmessage.TMessageKey;
import net.thevpc.dbclient.plugin.cfgsupport.cfgderby.dbimpl.tsession.*;
import net.thevpc.dbclient.plugin.cfgsupport.cfgderby.dbimpl.tsessionprop.TSessionPropDAO;
import net.thevpc.dbclient.plugin.cfgsupport.cfgderby.dbimpl.tsessionprop.TSessionPropDTO;
import net.thevpc.dbclient.plugin.cfgsupport.cfgderby.dbimpl.tsessionprop.TSessionPropFilter;
import net.thevpc.dbclient.plugin.cfgsupport.cfgderby.dbimpl.tsessionprop.TSessionPropKey;
import net.thevpc.dbclient.plugin.cfgsupport.cfgderby.dbimpl.tuinodeproperty.TUiNodePropertyDAO;
import net.thevpc.dbclient.plugin.cfgsupport.cfgderby.dbimpl.tuinodeproperty.TUiNodePropertyDTO;
import net.thevpc.dbclient.plugin.cfgsupport.cfgderby.dbimpl.tuinodeproperty.TUiNodePropertyFilter;
import net.thevpc.dbclient.plugin.cfgsupport.cfgderby.dbimpl.tuinodeproperty.TUiNodePropertyKey;
import net.thevpc.dbclient.plugin.system.configmanager.DBCAbstractApplicationConfig;
import net.thevpc.dbclient.plugin.system.sql.parser.DefaultSQLParser;
import net.thevpc.common.prs.messageset.MessageSetBundle;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 28 juin 2006 22:25:00
 */
@Ignore
public class DerbyApplicationConfig extends DBCAbstractApplicationConfig {

    private Connection configConnection;
    private boolean derbyLoaded = false;
    @Inject
    private DBCApplication application;
    private DBCPlugin plugin;

    public DerbyApplicationConfig() {
    }

    public void init() throws DBCConfigException {
        try {
            checkInstallation();
        } catch (DBCFatalConfigException e) {
            throw e;
        } catch (DBCConfigException e) {
            install();
        }
    }

    public Connection getConfigConnection() throws SQLException {
        if (configConnection == null) {
            configConnection = createConnection(false, false);
        }
        return configConnection;
    }

    public void dispose() throws DBCConfigException {
        if (!derbyLoaded) {
            return;
        }
        if (configConnection != null) {
            try {
                configConnection.close();
            } catch (SQLException e) {
                application.getLogger(DerbyApplicationConfig.class.getName()).log(Level.SEVERE, "Unable to close connection", e);
            }
            configConnection = null;
        }
        try {
            createConnection(false, true);
        } catch (SQLException e) {
                application.getLogger(DerbyApplicationConfig.class.getName()).log(Level.SEVERE, "Unable to create connection", e);
        }
//		System.out.print("after dispose : ");
//		debugShowDrivers();
    }

    private Connection createConnection(boolean create, boolean shutdown) throws SQLException {
        File file = getConfigFolder();
        try {
            file = file.getCanonicalFile();
        } catch (Exception e) {
            file = file.getAbsoluteFile();
        }
        application.getLogger(DerbyApplicationConfig.class.getName()).log(Level.CONFIG, "looking for dbclient config in folder : {0}", file.getPath());
        String url = null;
        if (shutdown) {
            url = "jdbc:derby:;shutdown=true";
        } else {
//		debugShowDrivers();
            url = "jdbc:derby:" + getConfigFolder().getPath().replace('\\', '/') + ";create=" + create;
        }
        DBCPlugin dbcPlugin = application.getPluginManager().getValidPlugin("dbdriver-derby");
        Class clz = null;
        if (dbcPlugin != null) {
            try {
                clz = Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            } catch (ClassNotFoundException e) {
                try {
                    clz = Class.forName("org.apache.derby.jdbc.EmbeddedDriver", true, dbcPlugin.getDescriptor().getClassLoader());
                } catch (ClassNotFoundException ee) {
                    throw new SQLException(ee.toString(), "vpc-class-not-found");
                }
            }

        } else {
            try {
                clz = Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e.toString(), "vpc-class-not-found");
            }
        }
        Driver d = null;
        try {
            d = (Driver) clz.newInstance();
        } catch (Throwable e) {
            throw new SQLException(e.toString(), "vpc-class-not-found");
        }
        Properties properties = new Properties();
        properties.put("user", "dbclient");
        properties.put("password", "dbclient");

        Connection c = d.connect(url, properties);
        derbyLoaded = true;
        return c;
    }

//	private void debugShowDrivers(){
//		System.out.println("Drivers{");
//		for(Enumeration<Driver> d=DriverManager.getDrivers();d.hasMoreElements();){
//			Driver dr=d.nextElement();
//			System.out.printf("\t%s (%d.%d)\n",dr.getClass(),dr.getMajorVersion(),dr.getMinorVersion());
//		}
//		System.out.println("}");
//	}
    public File getConfigFolder() {
        return new File(application.getConfigDir(), "derbycfg");
    }

    public void install() throws DBCConfigException {
        try {
            File root = getConfigFolder();
            if (root.exists()) {
                dispose();
                File file = new File(application.getConfigDir(), "backup/derbycfg-" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new java.util.Date()));
                file.getParentFile().mkdirs();
                application.getLogger(DerbyApplicationConfig.class.getName()).log(Level.CONFIG, "renaming old config folder to : {0}", file);
                if (!root.renameTo(file)) {
                    throw new DBCShouldRestartException("You seem to have un older version of config.\n"
                            + "We were unable to rename it to :\n"
                            + file.getPath() + "\n"
                            + "Please Restart Application!");
                } else {
                    throw new DBCShouldRestartException("You seem to have un older version of config.\n"
                            + "It was renamed to :\n"
                            + file.getPath() + "\n"
                            + "Please Restart Application!");
                }
            }
            if (configConnection == null) {
                configConnection = createConnection(true, false);
            }
            installExecuteScript("InstallSchema.sql",
                    configConnection, false);
            setStringProperty("configVersion", getApplication().getApplicationInfo().getConfigurationVersion().toString());
        } catch (DBCConfigException e) {
            throw e;
        } catch (Throwable e) {
            throw new DBCConfigException(e);
        }
    }

    private void installExecuteScript(String scriptName, Connection con,
            boolean ignoreErrors) throws SQLException, IOException {
        URL resource = getClass().getResource(scriptName);
        if (resource == null) {
            throw new IOException("Resource not found " + scriptName);
        }
        InputStreamReader in = new InputStreamReader(resource.openStream());
        SQLParser it = new DefaultSQLParser(in);
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            SQLStatement ostmt;
            while ((ostmt = it.readStatement()) != null) {
                if (!ostmt.isEmpty()) {
                    try {
                        // System.out.println("ostmt = " + ostmt);
                        String sql = ostmt.toSQL().trim();
                        stmt.executeUpdate(sql);
                    } catch (SQLException e) {
                        application.getLogger(DerbyApplicationConfig.class.getName()).log(Level.SEVERE, "Error executing "+ostmt.toSQL(), e);
                        SQLException n = e;
                        while (n != null) {
                            application.getLogger(DerbyApplicationConfig.class.getName()).log(Level.SEVERE, "Next Error", n);
                            n = n.getNextException();
                        }
                        if (!ignoreErrors) {
                            throw e;
                        }
                    }
                }
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public void checkInstallation() throws DBCConfigException {
        Connection con = null;
        try {
            con = getConfigConnection();
        } catch (SQLException e) {
            if ("vpc-class-not-found".equals(e.getSQLState())) {
                throw new DBCFatalConfigException("Unable to open config database : Driver not found. \nPlease ensure 'derby.jar' is in the classpath ('lib' folder) or plugin 'dbdriver-derby' is installed and enabled.");
            }
            if ( //                    e.getMessage().equals("Database '" + getConfigDir().getName() + "' not found.")
                    //                    ||
                    "XJ004.C".equals(getSQLExceptionMessageId(e))) {
                throw new DBCConfigNotFoundException(e);
            }
            SQLException nextException = e.getNextException();
            if (nextException != null && /*"XJ040.C"*/ "XSDB6.D".equals(getSQLExceptionMessageId(nextException))) {//SQLState XJ040
                throw new DBCLockedConfigException("DBClient is already running...", nextException);
            }
            throw new DBCCorruptedConfigException("unable to connect to config database : " + e);
        } catch (Throwable e) {
            throw new DBCCorruptedConfigException("unable to connect to config database : " + e.toString());
        }
        try {
            String v = getStringProperty("configVersion", null);
            if ((v != null && new Version(v).compareTo(getApplication().getApplicationInfo().getConfigurationVersion()) < 0)) {
                throw new DBCConfigOldVersionException("Version " + v + " is too old for current " + getApplication().getApplicationInfo().getConfigurationVersion());
            } else if (v == null) {
                throw new DBCCorruptedConfigException("Could not retrieve Config Version");
            }
        } catch (DBCConfigException e) {
            throw e;
        } catch (Throwable e) {
            throw new DBCCorruptedConfigException("Could not retrieve Config Version", e);
        }
    }

    private String getSQLExceptionMessageId(SQLException e) {
        try {
            Class clz = e.getClass();
            if (clz.getName().equals("org.apache.derby.impl.jdbc.EmbedSQLException")) {
                return (String) clz.getDeclaredMethod("getMessageId").invoke(e);
            }
        } catch (Throwable e1) {
            e1.printStackTrace();
        }
        return null;
    }

//    public TDriverDTO[] loadDrivers() throws DBCConfigException {
//        try {
//            return new TDriverDAO(getConfigConnection()).select(null,
//                    new TDriverFilter().setAll(
//                    new TDriverDTO().setDrvEnabled(1)).orderByDrvIndex(true));
//        } catch (SQLException e) {
//            throw new DBCConfigException(e);
//        }
//    }
    public DBCDriverInfo[] getDrivers() {
        try {
            TDriverDTO[] all = new TDriverDAO(getConfigConnection()).select(null,
                    new TDriverFilter().setAll(
                    new TDriverDTO().setDrvEnabled(1)).orderByDrvIndex(true));
            TDriverLibDAO driverLibDAO = new TDriverLibDAO(getConfigConnection());
            DBCDriverInfo[] ret = new DBCDriverInfo[all.length];
            for (int i = 0; i < all.length; i++) {
                ret[i] = dtoToDriver(all[i]);
                TDriverLibFilter driverLibFilter = new TDriverLibFilter();
                driverLibFilter.setAll(new TDriverLibDTO().setTdlDrvId(ret[i].getId()));
                TDriverLibDTO[] driverLibDTOs = driverLibDAO.select(null, driverLibFilter);
                for (TDriverLibDTO driverLibDTO : driverLibDTOs) {
                    try {
                        ret[i].addLibrary(new URL(driverLibDTO.getTdlUrl()));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
            }
            return ret;
        } catch (SQLException e) {
            throw new DBCConfigException(e);
        }
    }

    private TDriverDTO driverToDTO(DBCDriverInfo driver) {
        TDriverDTO dto = new TDriverDTO();
        dto.setDrvClassName(driver.getDriverClassName());
        dto.setDrvDefaultLogin(driver.getDefaultLogin());
        dto.setDrvDefaultUrl(driver.getDefaultURL());
        if (driver.containsProperty("enabled")) {
            dto.setDrvEnabled(driver.isEnabled() ? 1 : 0);
        }
        dto.setDrvDefaultPassword(driver.getDefaultPassword());
        dto.setDrvDefaultDesc(driver.getDescription());
        if (driver.containsProperty("id")) {
            dto.setDrvId(driver.getId());
        }
        if (driver.containsProperty("index")) {
            dto.setDrvIndex(driver.getIndex());
        }
        dto.setDrvName(driver.getName());
        return dto;
    }

    private DBCDriverInfo dtoToDriver(TDriverDTO dto) {
        DBCDriverInfo driver = new DBCDriverInfo();
//        driver.setApplication(application);
        driver.setDriverClassName(dto.getDrvClassName());
        driver.setDefaultLogin(dto.getDrvDefaultLogin());
        driver.setDefaultURL(dto.getDrvDefaultUrl());
        driver.setEnabled(dto.getDrvEnabled() == 1);
        driver.setDefaultPassword(dto.getDrvDefaultPassword());
        driver.setDescription(dto.getDrvDefaultDesc());
        driver.setId(dto.getDrvId());
        driver.setIndex(dto.getDrvIndex());
        driver.setName(dto.getDrvName());
        return driver;
    }

    public void addDriver(DBCDriverInfo desc) throws DBCConfigException {
        try {
            TDriverDAO tDriverDAO = new TDriverDAO(getConfigConnection());
            TDriverDTO tDriverDTO = driverToDTO(desc);
            tDriverDTO.unsetDrvId();
            TDriverKey key = tDriverDAO.insert(tDriverDTO);
            TDriverLibDAO driverLibDAO = new TDriverLibDAO(getConfigConnection());
            desc.setId(key.getDrvId());
            int count = desc.getLibraryCount();
            for (int i = 0; i < count; i++) {
                URL url = desc.getLibrayAt(i);
                TDriverLibDTO dto = new TDriverLibDTO();
                dto.setTdlIndex(i);
                dto.setTdlDrvId(key.getDrvId());
                dto.setTdlUrl(url.toString());
                driverLibDAO.insert(dto);
            }
        } catch (SQLException e) {
            throw new DBCConfigException(e);
        }
    }

    public void updateDriver(DBCDriverInfo desc) {
        try {
            TDriverDAO tDriverDAO = new TDriverDAO(getConfigConnection());
            tDriverDAO.update(driverToDTO(desc));
            TDriverLibDAO driverLibDAO = new TDriverLibDAO(getConfigConnection());
            int count = desc.getLibraryCount();
            driverLibDAO.delete(new TDriverLibFilter().setAll(new TDriverLibDTO().setTdlDrvId(desc.getId())));
            for (int i = 0; i < count; i++) {
                URL url = desc.getLibrayAt(i);
                TDriverLibDTO dto = new TDriverLibDTO();
                dto.setTdlDrvId(desc.getId());
                dto.setTdlIndex(i);
                dto.setTdlUrl(url.toString());
                driverLibDAO.insert(dto);
            }
        } catch (SQLException e) {
            throw new DBCConfigException(e);
        }
    }

    public void removeDriver(int driverId) {
        try {
            TDriverLibDAO driverLibDAO = new TDriverLibDAO(getConfigConnection());
            driverLibDAO.delete(new TDriverLibFilter().setAll(new TDriverLibDTO().setTdlDrvId(driverId)));
            TDriverDAO tDriverDAO = new TDriverDAO(getConfigConnection());
            tDriverDAO.delete(new TDriverKey(driverId));
        } catch (SQLException e) {
            throw new DBCConfigException(e);
        }
    }

    public DBCSessionInfo[] getSessions() throws DBCConfigException {
        try {
            TSessionDAO d = new TSessionDAO(getConfigConnection());
            TSessionDTO[] found = d.select(
                    null, new TSessionFilter().orderBySesIndex(true).orderBySesCnxLastUpdated(false));

            DBCSessionInfo[] ret = new DBCSessionInfo[found.length];
            for (int i = 0; i < ret.length; i++) {
                ret[i] = createDBCSessionInfo(found[i], application);
            }
            return ret;
        } catch (SQLException e) {
            throw new DBCConfigException(e);
        }
    }

    protected TSessionDTO createTSessionDTO(DBCSessionInfo s) {
        TSessionDTO dto = new TSessionDTO();
        for (Iterator iterator = s.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            Object value = s.getProperty(key);
            if (TSessionPropertyList.SES_CNX_CREATED.equals(key) || TSessionPropertyList.SES_CNX_LAST_UPDATED.equals(key)) {
                dto.setProperty(key, value == null ? null : new java.sql.Date(((java.util.Date) value).getTime()));
            } else if (TSessionPropertyList.SES_CNX_READ_ONLY.equals(key) || TSessionPropertyList.SES_AUTO_CONNECT.equals(key) || TSessionPropertyList.SES_CNX_AUTOCOMMIT.equals(key)) {
                dto.setProperty(key, value == null ? null : ((Boolean) value ? 1 : 0));
            } else if (DBCSessionInfo.SES_SERVER.equals(key)) {
                //nothing
            } else {
                dto.setProperty(key, value);
            }
        }
        String ss = dto.getSesCnxPassword();
        dto.setSesCnxPassword(application.getEncryptionManager().encrypt(ss));
        return dto;
    }

    protected DBCSessionInfo createDBCSessionInfo(TSessionDTO s, DBCApplication dbc) {
        DBCSessionInfo dto = new DBCSessionInfo();
        for (Object o : s.keySet()) {
            String key = (String) o;
            Object value = s.getProperty(key);
            if (TSessionPropertyList.SES_CNX_READ_ONLY.equals(key) || (TSessionPropertyList.SES_AUTO_CONNECT.equals(key)) || (TSessionPropertyList.SES_CNX_AUTOCOMMIT.equals(key))) {
                dto.setProperty(key, value == null ? null : ((Integer) value).intValue() != 0);
            } else {
                dto.setProperty(key, value);
            }
        }
        String ss = dto.getCnxPassword();
        dto.setCnxPassword(application.getEncryptionManager().decrypt(ss));
        dto.setReadOnly(dto.getId() == 1);//config session
        dto.setAskForPassword(s.isAskForPassword());
        return dto;
    }

    public void removeSession(int id) {
        try {
            new TSessionDAO(getConfigConnection()).delete(new TSessionKey(id));
        } catch (Throwable e) {
            throw new DBCConfigException(e.toString());
        }
    }

    public void addSession(DBCSessionInfo info, int sessionTemplate)
            throws DBCConfigException {
        try {
            TSessionDAO tSessionManager = new TSessionDAO(
                    getConfigConnection());
            info.setIndex(1);
            TSessionKey key = tSessionManager.insert(createTSessionDTO(info));
            info.setId(key.getSesId());
            if (sessionTemplate != SESSION_TEMPLATE_NONE) {
                //TODO copy preferences from sessionTemplate
            }
        } catch (Throwable e) {
            throw new DBCConfigException(e.toString());
        }
    }

    public void updateSession(DBCSessionInfo info)
            throws DBCConfigException {
        try {
            TSessionDAO tSessionManager = new TSessionDAO(
                    getConfigConnection());
            tSessionManager.update(createTSessionDTO(info));
        } catch (Throwable e) {
            throw new DBCConfigException(e.toString());
        }
    }

    public DBCSessionInfo getSession(int id) throws DBCConfigException {
        try {
            TSessionDAO tSessionManager = new TSessionDAO(
                    getConfigConnection());
            return createDBCSessionInfo(tSessionManager.select(null, new TSessionKey(id)), application);
        } catch (Throwable e) {
            throw new DBCConfigException(e.toString());
        }
    }

    public void setStringProperty(int sessionId, String name, String value) {
        try {
            TSessionPropDAO manager = new TSessionPropDAO(
                    getConfigConnection());
            TSessionPropDTO data = manager.select(null, new TSessionPropKey(
                    name, sessionId));
            if (data != null) {
                if ((value != null && !value.equals(data.getTspValue())) || (value == null && data.getTspValue() != null)) {
                    data = new TSessionPropDTO();
                    data.setTspSesId(sessionId);
                    data.setTspName(name);
                    data.setTspValue(value);
                    manager.update(data);
                }
            } else {
                data = new TSessionPropDTO();
                data.setTspSesId(sessionId);
                data.setTspName(name);
                data.setTspValue(value);
                manager.insert(data);
            }
        } catch (Throwable e) {
            throw new DBCConfigException(e);
        }
    }

    public void setStringProperty(String name, String value) {
        try {
            TConfigPropDAO manager = new TConfigPropDAO(
                    getConfigConnection());
            TConfigPropDTO data = manager.select(null, new TConfigPropKey(
                    name));
            if (data != null) {
                if ((value != null && !value.equals(data.getTcpValue())) || (value == null && data.getTcpValue() != null)) {
                    data = new TConfigPropDTO();
                    data.setTcpName(name);
                    data.setTcpValue(value);
                    manager.update(data);
                }
            } else {
                data = new TConfigPropDTO();
                data.setTcpName(name);
                data.setTcpValue(value);
                manager.insert(data);
            }
        } catch (Throwable e) {
            throw new DBCConfigException(e);
        }
    }

    public String getStringProperty(int sessionId, String name, String defaultValue) {
        try {
            TSessionPropDAO manager = new TSessionPropDAO(
                    getConfigConnection());
            TSessionPropDTO data = manager.select(null, new TSessionPropKey(
                    name, sessionId));
            String val = data == null ? null : data.getTspValue();
            return val == null ? defaultValue : val;
        } catch (Throwable e) {
            throw new DBCConfigException(e);
        }
    }

    public String getStringProperty(String name, String defaultValue) {
        try {
            TConfigPropDAO manager = new TConfigPropDAO(
                    getConfigConnection());
            TConfigPropDTO data = manager.select(null, new TConfigPropKey(name));
            String val = (data == null ? null : data.getTcpValue());
            return val == null ? defaultValue : val;
        } catch (Throwable e) {
            throw new DBCConfigException(e);
        }
    }

    public DBCConfigPropInfo[] getStringProperties() {

        try {
            TConfigPropDAO manager = new TConfigPropDAO(
                    getConfigConnection());
            TConfigPropDTO[] data = manager.select(null);
            DBCConfigPropInfo[] ret = new DBCConfigPropInfo[data.length];
            for (int i = 0; i < ret.length; i++) {
                ret[i] = new DBCConfigPropInfo();
                ret[i].setCprName(data[i].getTcpName());
                ret[i].setCprValue(data[i].getTcpValue());
            }
            return ret;
        } catch (Throwable e) {
            throw new DBCConfigException(e);
        }
    }

    public MessageSetBundle getMessageSetBundle() {
        return new DBCConfigBundle(this);
//        try {
//            return new DerbyBundle(getConfigConnection());
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }

    public Map<String, String> getPathsValues(int sessionId, String code) {
        try {
            TUiNodePropertyDAO manager = new TUiNodePropertyDAO(
                    getConfigConnection());
            TUiNodePropertyDTO d = new TUiNodePropertyDTO();
            d.setUnpSesId(sessionId);
            d.setUnpCode(code);
            TUiNodePropertyDTO[] found = manager.select(null,
                    new TUiNodePropertyFilter().setAll(d));
            Map<String, String> m = new HashMap<String, String>();
            for (TUiNodePropertyDTO aFound : found) {
                m.put(aFound.getUnpPath(), aFound.getUnpValue());
            }
            return m;
        } catch (Throwable e) {
            throw new DBCConfigException(e);
        }
    }

    public void clearPathsValues(int sessionId, String path, String code) {
        try {
            TUiNodePropertyDAO dao = new TUiNodePropertyDAO(getConfigConnection());
            TUiNodePropertyFilter filter = new TUiNodePropertyFilter();
            filter.setWhereClause("UNP_SES_ID=? AND UNP_CODE LIKE ? AND UNP_PATH LIKE ?");
            filter.setInt(1, sessionId);
            filter.setString(2, shell2Sql(code));
            filter.setString(3, shell2Sql(path));
            dao.delete(filter);
        } catch (SQLException e) {
            throw new DBCConfigException(e);
        }
    }

    public void clearProperties(int sessionId, String namePattern) {
        try {
            TSessionPropDAO manager = new TSessionPropDAO(
                    getConfigConnection());
            TSessionPropFilter crit = new TSessionPropFilter();
            crit.setWhereClause("TSP_SES_ID= ? AND TSP_NAME Like ?");
            crit.setInt(1, sessionId);
            crit.setString(2, shell2Sql(namePattern));
            manager.delete(crit);
        } catch (Throwable e) {
            throw new DBCConfigException(e);
        }
    }

    public void clearProperties(String namePattern) {
        try {
            TConfigPropDAO manager = new TConfigPropDAO(
                    getConfigConnection());
            TConfigPropFilter crit = new TConfigPropFilter();
            crit.setWhereClause("TCP_SES_ID= ? AND TCP_NAME Like ?");
            crit.setString(1, shell2Sql(namePattern));
            manager.delete(crit);
        } catch (Throwable e) {
            throw new DBCConfigException(e);
        }
    }

    private static final String shell2Sql(String str) {
        return str.replace("*", "%").replace('?', '_');
    }

    public void setPathValue(int sessionId, String path, String code,
            String value) {
        try {
            TUiNodePropertyDAO manager = new TUiNodePropertyDAO(
                    getConfigConnection());
            String found = getPathValue(sessionId, path, code);
            if (value != null && found == null) {
                TUiNodePropertyDTO d = new TUiNodePropertyDTO();
                d.setUnpCode(code);
                d.setUnpSesId(sessionId);
                d.setUnpPath(path);
                d.setUnpValue(value);
                manager.insert(d);
            } else if (value != null && !value.equals(found)) {
                TUiNodePropertyDTO d = new TUiNodePropertyDTO();
                d.setUnpCode(code);
                d.setUnpSesId(sessionId);
                d.setUnpPath(path);
                d.setUnpValue(value);
                manager.update(d);
            } else if (value == null && found != null) {
                manager.delete(new TUiNodePropertyKey(code, path,
                        sessionId));
            }
        } catch (Throwable e) {
            throw new DBCConfigException(e);
        }
    }

    public String getPathValue(int sessionId, String path, String code) {
        try {
            TUiNodePropertyDAO manager = new TUiNodePropertyDAO(
                    getConfigConnection());
            TUiNodePropertyDTO found = manager.select(null,
                    new TUiNodePropertyKey(code, path, sessionId));
            return found != null ? found.getUnpValue() : null;
        } catch (Throwable e) {
            throw new DBCConfigException(e);
        }
    }

    public String[] getAncestorPaths(String path) {
        ArrayList<String> all = new ArrayList<String>();
        String current = path;
        while (current.length() > 0) {
            all.add(current);
            int i = current.lastIndexOf('/');
            if (i < 0) {
                current = "";
            } else {
                current = current.substring(0, i);
            }
        }
        return all.toArray(new String[all.size()]);
    }

    public String getMessage(String id, String locale) {
        try {
            TMessageDAO manager = new TMessageDAO(getConfigConnection());
            TMessageDTO m = manager.select(null, new TMessageKey(id, locale.toString()));
            return m == null ? null : m.getMsgData();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public DBCMessageInfo[] getMessages() {
        try {
            TMessageDAO manager = new TMessageDAO(getConfigConnection());
            TMessageDTO[] m = manager.select(null);
            DBCMessageInfo[] ret = new DBCMessageInfo[m.length];
            for (int i = 0; i < ret.length; i++) {
                ret[i] = new DBCMessageInfo();
                ret[i].setMsgData(m[i].getMsgData());
                ret[i].setMsgId(m[i].getMsgId());
                ret[i].setMsgLocName(m[i].getMsgLocName());
            }
            return ret;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addMessage(DBCMessageInfo msg) {
        try {
            TMessageDTO dbcMessageInfo = new TMessageDTO();
            dbcMessageInfo.setMsgData(msg.getMsgData());
            dbcMessageInfo.setMsgId(msg.getMsgId());
            dbcMessageInfo.setMsgLocName(msg.getMsgLocName());
            TMessageDAO manager = new TMessageDAO(getConfigConnection());
            String locale = dbcMessageInfo.getMsgLocName();
            dbcMessageInfo.setMsgLocName((locale == null || locale.trim().length() == 0) ? "*" : locale);
            manager.insert(dbcMessageInfo);
        } catch (SQLException e) {
            throw new DBCConfigException(e);
        }
    }

    public void updateMessage(DBCMessageInfo msg) {
        try {
            TMessageDTO dbcMessageInfo = new TMessageDTO();
            if (msg.containsMsgId()) {
                dbcMessageInfo.setMsgId(msg.getMsgId());
            }
            if (msg.containsMsgData()) {
                dbcMessageInfo.setMsgData(msg.getMsgData());
            }
            if (msg.containsMsgLocName()) {
                dbcMessageInfo.setMsgLocName(msg.getMsgLocName());
            }
            TMessageDAO manager = new TMessageDAO(getConfigConnection());
            String locale = dbcMessageInfo.getMsgLocName();
            dbcMessageInfo.setMsgLocName((locale == null || locale.trim().length() == 0) ? "*" : locale);
            manager.update(dbcMessageInfo);
        } catch (SQLException e) {
            throw new DBCConfigException(e);
        }
    }

    public void removeMessage(String id, String locale) {
        try {
            TMessageDAO manager = new TMessageDAO(getConfigConnection());
            TMessageDTO dbcMessageInfo = new TMessageDTO();
            dbcMessageInfo.setMsgId(id);
            dbcMessageInfo.setMsgLocName((locale == null || locale.trim().length() == 0) ? "*" : locale);
            manager.delete(dbcMessageInfo.getTMessageKey());
        } catch (SQLException e) {
            throw new DBCConfigException(e);
        }
    }

    public DBCApplication getApplication() {
        return application;
    }

    public void clearPathsValues(String path, String code) {
        clearPathsValues(-1, path, code);
    }

    public String getPathValue(String path, String code) {
        return getPathValue(-1, path, code);
    }

    public void setPathValue(String path, String code, String value) {
        setPathValue(-1, path, code, value);
    }
}
