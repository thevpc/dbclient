package net.vpc.dbclient.plugin.system.configmanager.xmlconfig;

import java.util.logging.Level;
import net.vpc.dbclient.api.DBCApplication;
import net.vpc.dbclient.api.configmanager.*;
import net.vpc.dbclient.plugin.system.configmanager.DBCAbstractApplicationConfig;
import net.vpc.prs.messageset.MessageSetBundle;
import net.vpc.prs.plugin.Inject;
import net.vpc.util.StringShellFilter;
import net.vpc.util.Version;
import net.vpc.xml.XmlUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 9 sept. 2007 16:00:47
 */
public class DBCXMLApplicationConfigImpl extends DBCAbstractApplicationConfig {

    @Inject
    private DBCApplication application;
    private DBCXMLConfigApplicationModel app = new DBCXMLConfigApplicationModel();
    private Map<Integer, DBCXMLConfigSessionModel> sessions = new HashMap<Integer, DBCXMLConfigSessionModel>();

    public DBCXMLApplicationConfigImpl() {
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

    public void storeAll() throws DBCConfigException {
        storeApp();
        for (Map.Entry<Integer, DBCXMLConfigSessionModel> entry : sessions.entrySet()) {
            storeSession(entry.getValue());
        }
    }

    public void storeApp() throws DBCConfigException {
        getConfigFolder().mkdirs();
        try {
            XmlUtils.objectToXml(app, getConfigFile(),getClass().getClassLoader(),null);
        } catch (IOException e) {
            throw new DBCConfigException(e.toString());
        }
    }

    public void storeSession(DBCXMLConfigSessionModel sessionModel) throws DBCConfigException {
        getConfigFolder().mkdirs();
        try {
            String ss = sessionModel.sessionConfig.getCnxPassword();
            sessionModel.sessionConfig.setCnxPassword(application.getEncryptionManager().encrypt(ss));
            XmlUtils.objectToXml(sessionModel, getSessionConfigFile(sessionModel.sessionConfig.getId()),getClass().getClassLoader(),null);
            sessionModel.sessionConfig.setCnxPassword(ss);
        } catch (IOException e) {
            throw new DBCConfigException(e.toString());
        }
    }

    public DBCXMLConfigSessionModel getSessionConfig(int id) {
        DBCXMLConfigSessionModel sessionModel = sessions.get(id);
        if (sessionModel == null) {
            try {
                File sessionConfigFile = getSessionConfigFile(id);
                if(sessionConfigFile.exists()){
                    sessionModel = (DBCXMLConfigSessionModel) XmlUtils.xmlToObject(sessionConfigFile,getClass().getClassLoader(),null);
                    if (sessionModel != null) {
                        //decrypt pwd
                        String ss = sessionModel.sessionConfig.getCnxPassword();
                        sessionModel.sessionConfig.setCnxPassword(application.getEncryptionManager().decrypt(ss));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                //
            }
            if (sessionModel != null) {
                this.sessions.put(id, sessionModel);
            }
        }
        return sessionModel;
    }

    public void load() throws DBCConfigException {
        try {
            app = (DBCXMLConfigApplicationModel) XmlUtils.xmlToObject(getConfigFile(),getClass().getClassLoader(),null);
//            for (DBCDriverInfo dbcDriverInfo : app.drivers.values()) {
//                dbcDriverInfo.setApplication(application);
//            }Hashtable
            this.sessions = new HashMap<Integer, DBCXMLConfigSessionModel>();
        } catch (IOException e) {
            throw new DBCConfigException(e.toString());
        }
    }

    public void dispose() throws DBCConfigException {
        storeAll();
    }

    public File getConfigFolder() {
        return new File(application.getConfigDir(), "xmlcfg");
    }

    public File getConfigFile() {
        return new File(getConfigFolder(), "application.xml");
    }

    public File getSessionConfigFile(Integer i) {
        return new File(getConfigFolder(), "session-" + i + ".xml");
    }

    public void install() throws DBCConfigException {
        try {
            if (getConfigFile().exists()) {
                File file = new File(getConfigFolder(), "../backup/xmlconfig-" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new java.util.Date()));
                application.getLogger(DBCXMLApplicationConfigImpl.class.getName()).log(Level.CONFIG, "renaming old config file to : {0}", file);
                file.getParentFile().mkdirs();
                if (!getConfigFolder().renameTo(file)) {
                    throw new DBCShouldRestartException("You seem to have un older version of config.\n" +
                            "We were unable to rename it to :\n" +
                            file.getPath() + "\n" +
                            "Please Restart Application!");
                } else {
                    sessions = new HashMap<Integer, DBCXMLConfigSessionModel>();
                    setStringProperty("configVersion", getApplication().getApplicationInfo().getConfigurationVersion().toString());
                    storeAll();
                    throw new DBCShouldRestartException("You seem to have un older version of config.\n" +
                            "It was renamed to :\n" +
                            file.getPath() + "\n" +
                            "Please Restart Application!");
                }
            }
            File[] files = getConfigFolder().listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.getName().endsWith("xml")) {
                        file.delete();
                    }
                }
            }
            sessions = new HashMap<Integer, DBCXMLConfigSessionModel>();
            setStringProperty("configVersion", getApplication().getApplicationInfo().getConfigurationVersion().toString());
            storeAll();
        } catch (DBCConfigException e) {
            throw e;
        } catch (Throwable e) {
            throw new DBCConfigException(e);
        }
    }

    public void checkInstallation() throws DBCConfigException {
        try {
            DBCXMLConfigApplicationModel app0 = (DBCXMLConfigApplicationModel) XmlUtils.xmlToObject(getConfigFile(),getClass().getClassLoader(),null);
            if (app0 != null) {
                String v = app0.props.get("configVersion");
                if ((v != null && new Version(v).compareTo(getApplication().getApplicationInfo().getConfigurationVersion()) < 0)) {
                    throw new DBCConfigOldVersionException("Version " + v + " is too old for current " + getApplication().getApplicationInfo().getConfigurationVersion().toString());
                } else if (v == null) {
                    throw new DBCCorruptedConfigException("Could not retrieve Config Version");
                }
                load();
                return;
            }
            throw new DBCCorruptedConfigException("Could not retrieve Config Version");
        } catch (FileNotFoundException e) {
            throw new DBCConfigNotFoundException(e);
        } catch (IOException e) {
            throw new DBCCorruptedConfigException(e);
        } catch (DBCConfigException e) {
            throw e;
        } catch (Throwable e) {
            throw new DBCCorruptedConfigException(e.getMessage());
        }
    }

    public DBCDriverInfo[] getDrivers() {
        DBCDriverInfo[] all = app.drivers.values().toArray(new DBCDriverInfo[app.drivers.size()]);
//        for (DBCDriverInfo dBCDriverInfo : all) {
//            dBCDriverInfo.invalidateClassLoader();
//        }
        return all;
    }

    public void addDriver(DBCDriverInfo desc) throws DBCConfigException {
//        desc.setApplication(application);
        int driverId = 0;
        while (app.drivers.containsKey(driverId)) {
            driverId++;
        }
        desc.setId(driverId);
        app.drivers.put(driverId, desc);
        storeApp();
    }

    public void updateDriver(DBCDriverInfo desc) {
//        desc.setApplication(application);
        app.drivers.get(desc.getId()).setAllProperties(desc);
        storeApp();
    }

    public void removeDriver(int driverId) {
        app.drivers.remove(driverId);
        storeApp();
    }

    public DBCSessionInfo[] getSessions() throws DBCConfigException {
        //load all sessions
        File[] files = getConfigFolder().listFiles();
        List<Integer> validIds = new ArrayList<Integer>();
        if (files != null) {
            for (File file : files) {
                String fn = file.getName().toLowerCase();
                if (fn.startsWith("session-") && fn.endsWith(".xml")) {
                    try {
                        String id = file.getName().substring("session-".length(), file.getName().length() - ".xml".length());
                        int iid = Integer.parseInt(id);
                        validIds.add(iid);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        ArrayList<DBCSessionInfo> all = new ArrayList<DBCSessionInfo>();
        for (Integer id : validIds) {
            DBCXMLConfigSessionModel dBCXMLConfigSessionModel = getSessionConfig(id);
            if (dBCXMLConfigSessionModel != null) {
                all.add(dBCXMLConfigSessionModel.sessionConfig);
            }
        }
        return all.toArray(new DBCSessionInfo[all.size()]);
    }

    public void removeSession(int id) {
        sessions.remove(id);
        getSessionConfigFile(id).delete();
    }

    public void addSession(DBCSessionInfo info, int sessionTemplate)
            throws DBCConfigException {
        try {
            int sessionsId = 0;
            while (getSessionConfig(sessionsId) != null) {
                sessionsId++;
            }
            info.setId(sessionsId);
            DBCXMLConfigSessionModel sessionModel = new DBCXMLConfigSessionModel();
            sessionModel.sessionConfig = info;
            sessions.put(sessionsId, sessionModel);
//            DefaultXmlSerializer ser = new DefaultXmlSerializer();
//            ser.setDefaultClassLoader(getClass().getClassLoader());
//            try {
//                ser.storeToFile(sessionModel, getSessionConfigFile(sessionsId));
//            } catch (IOException e) {
//                throw new DBCConfigException(e.toString());
//            }

            if (sessionTemplate != SESSION_TEMPLATE_NONE) {
                //TODO copy preferences from sessionTemplate
            }
            storeSession(sessionModel);
        } catch (Throwable e) {
            throw new DBCConfigException(e.toString());
        }
    }

    public void updateSession(DBCSessionInfo info)
            throws DBCConfigException {
        DBCXMLConfigSessionModel sessionModel = sessions.get(info.getId());
        sessionModel.sessionConfig.setAllProperties(info);
        storeSession(sessionModel);
    }

    public DBCSessionInfo getSession(int id) throws DBCConfigException {
        return getSessionConfig(id).sessionConfig;//TODO clone
    }

    public void setStringProperty(int sessionId, String name, String value) {
        try {
            if (value == null) {
                getSessionConfig(sessionId).sessionProps.remove(name);
            } else {
                getSessionConfig(sessionId).sessionProps.put(name, value);
            }
        } catch (Throwable e) {
            throw new DBCConfigException(e);
        }
    }

    public void setStringProperty(String name, String value) {
        try {
            if (value == null) {
                app.props.remove(name);
            } else {
                app.props.put(name, value);
            }
            storeApp();
        } catch (Throwable e) {
            throw new DBCConfigException(e);
        }
    }

    public String getStringProperty(int sessionId, String name, String defaultValue) {
        try {
            String found = getSessionConfig(sessionId).sessionProps.get(name);
            return found == null ? defaultValue : found;
        } catch (Throwable e) {
            throw new DBCConfigException(e);
        }
    }

    public String getStringProperty(String name, String defaultValue) {
        String found = app.props.get(name);
        return found == null ? defaultValue : found;
    }

    public MessageSetBundle getMessageSetBundle() {
        return new DBCConfigBundle(this);
    }

    public Map<String, String> getPathsValues(int sessionId, String code) {
        try {
            Map<String, String> hashtable = new HashMap<String, String>();
            Pattern codeObject = Pattern.compile(StringShellFilter.shellToRegexpPattern(code));
            for (DBCUiNodePropertyInfo info : getSessionConfig(sessionId).uiNodeProperties.values()) {
                if (info.getUnpSesId() == sessionId
                        && codeObject.matcher(info.getUnpCode()).matches()
                        ) {
                    hashtable.put(info.getUnpPath(), info.getUnpValue());
                }
            }
            return hashtable;
        } catch (Throwable e) {
            throw new DBCConfigException(e);
        }
    }

    public void clearPathsValues(int sessionId, String path, String code) {
        DBCXMLConfigSessionModel ss = getSessionConfig(sessionId);
        Pattern pathObject = Pattern.compile(StringShellFilter.shellToRegexpPattern(path));
        Pattern codeObject = Pattern.compile(StringShellFilter.shellToRegexpPattern(code));
        for (Iterator<DBCUiNodePropertyInfo> iterator = ss.uiNodeProperties.values().iterator(); iterator.hasNext();) {
            DBCUiNodePropertyInfo info = iterator.next();
            if (info.getUnpSesId() == sessionId
                    && pathObject.matcher(info.getUnpPath()).matches()
                    && codeObject.matcher(info.getUnpCode()).matches()
                    ) {
                iterator.remove();
            }
        }
        storeSession(ss);
    }

    public void clearProperties(String namePattern) {
        Pattern namePatternObject = Pattern.compile(StringShellFilter.shellToRegexpPattern(namePattern));
        for (Iterator<String> i = app.props.keySet().iterator(); i.hasNext();) {
            String k = i.next();
            if (namePatternObject.matcher(k).matches()) {
                i.remove();
            }
        }
    }

    public void clearProperties(int sessionId, String namePattern) {
        DBCXMLConfigSessionModel ss = getSessionConfig(sessionId);
        Pattern namePatternObject = Pattern.compile(StringShellFilter.shellToRegexpPattern(namePattern));
        for (Iterator<String> i = ss.sessionProps.keySet().iterator(); i.hasNext();) {
            String k = i.next();
            if (namePatternObject.matcher(k).matches()) {
                i.remove();
            }
        }
    }


    public void setPathValue(int sessionId, String path, String code,
                             String value) {
        try {
            DBCUiNodePropertyInfo d = new DBCUiNodePropertyInfo();
            d.setUnpCode(code);
            d.setUnpSesId(sessionId);
            d.setUnpPath(path);
            d.setUnpValue(value);
            DBCXMLConfigSessionModel ss = getSessionConfig(sessionId);
            if (value != null) {
                ss.uiNodeProperties.put(d.getCombinedKey(), d);
            } else {
                ss.uiNodeProperties.remove(d.getCombinedKey());
            }
            storeSession(ss);
        } catch (Throwable e) {
            throw new DBCConfigException(e);
        }
    }

    public String getPathValue(int sessionId, String path, String code) {
        try {
            DBCUiNodePropertyInfo d = new DBCUiNodePropertyInfo();
            d.setUnpCode(code);
            d.setUnpSesId(sessionId);
            d.setUnpPath(path);
            DBCUiNodePropertyInfo dbcUiNodePropertyInfo = getSessionConfig(sessionId).uiNodeProperties.get(d.getCombinedKey());
            return dbcUiNodePropertyInfo == null ? null : dbcUiNodePropertyInfo.getUnpValue();
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
        DBCMessageInfo dbcMessageInfo = new DBCMessageInfo();
        DBCMessageInfo messageInfo = app.messages.get(dbcMessageInfo.getCombinedKey());
        return messageInfo == null ? null : messageInfo.getMsgData();
    }

    public void updateMessage(DBCMessageInfo msg) {
        String locale = msg.getMsgLocName();
        msg.setMsgLocName((locale == null || locale.trim().length() == 0) ? "*" : locale);
        app.messages.put(msg.getCombinedKey(), msg);
        storeApp();
    }

    public DBCMessageInfo[] getMessages() {
        return app.messages.values().toArray(new DBCMessageInfo[app.messages.size()]);
    }

    public void addMessage(DBCMessageInfo msg) {
        String locale = msg.getMsgLocName();
        msg.setMsgLocName((locale == null || locale.trim().length() == 0) ? "*" : locale);
        app.messages.put(msg.getCombinedKey(), msg);
        storeApp();
    }

    public void removeMessage(String id, String locale) {
        DBCMessageInfo dbcMessageInfo = new DBCMessageInfo();
        dbcMessageInfo.setMsgId(id);
        dbcMessageInfo.setMsgLocName((locale == null || locale.trim().length() == 0) ? "*" : locale);
        app.messages.remove(dbcMessageInfo.getCombinedKey());
        storeApp();
    }

    public DBCConfigPropInfo[] getStringProperties() {
        DBCConfigPropInfo[] k = new DBCConfigPropInfo[app.props.size()];
        String[] s = app.props.keySet().toArray(new String[k.length]);
        for (int i = 0; i < k.length; i++) {
            DBCConfigPropInfo dBCConfigPropInfo = new DBCConfigPropInfo();
            dBCConfigPropInfo.setCprName(s[i]);
            dBCConfigPropInfo.setCprValue(app.props.get(s[i]));
            k[i] = dBCConfigPropInfo;

        }
        return k;

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
