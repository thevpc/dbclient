package net.thevpc.dbclient.plugin.cfgsupport.cfgderby.dbimpl.tsession;

import java.util.HashMap;
import java.util.Set;

/**
 * DO NOT EDIT MANUALLY
 * GENERATED AUTOMATICALLY BY JBGen (0.1)
 *
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @framework neormf (license GPL)
 */
public class TSessionDTO implements Cloneable {
    private HashMap dataMap;

    /**
     * Constructor
     */
    public TSessionDTO() {
        dataMap = new HashMap();
    }

    /**
     * generic getter
     */
    public Object getProperty(String name) {
        return dataMap.get(name);
    }

    /**
     * generic getter
     */
    public void setProperty(String name, Object value) {
        dataMap.put(name, value);
    }

    /**
     * getter for sesId
     */
    public int getSesId() {
        return ((Integer) dataMap.get(TSessionPropertyList.SES_ID)).intValue();
    }

    /**
     * setter for sesId
     */
    public void setSesId(int value) {
        dataMap.put(TSessionPropertyList.SES_ID, new Integer(value));
    }

    /**
     * true if record contains the field sesId
     */
    public boolean containsSesId() {
        return dataMap.containsKey(TSessionPropertyList.SES_ID);
    }

    /**
     * remove the field sesId
     */
    public TSessionDTO unsetSesId() {
        dataMap.remove(TSessionPropertyList.SES_ID);
        return this;
    }

    /**
     * getter for sesId
     */
    public boolean isAskForPassword() {
        Boolean a = (Boolean) dataMap.get(TSessionPropertyList.SES_ASK_FOR_PASSWORD);
        return a==null?false:a.booleanValue();
    }

    /**
     * setter for sesId
     */
    public void setAskForPassword(boolean value) {
        dataMap.put(TSessionPropertyList.SES_ASK_FOR_PASSWORD, value);
    }

    /**
     * true if record contains the field sesId
     */
    public boolean containsAskForPassword() {
        return dataMap.containsKey(TSessionPropertyList.SES_ASK_FOR_PASSWORD);
    }

    /**
     * remove the field sesId
     */
    public TSessionDTO unsetAskForPassword() {
        dataMap.remove(TSessionPropertyList.SES_ASK_FOR_PASSWORD);
        return this;
    }

    /**
     * getter for sesName
     */
    public java.lang.String getSesName() {
        return ((java.lang.String) dataMap.get(TSessionPropertyList.SES_NAME));
    }

    /**
     * setter for sesName
     */
    public TSessionDTO setSesName(java.lang.String value) {
        dataMap.put(TSessionPropertyList.SES_NAME, value);
        return this;
    }

    /**
     * true if record contains the field sesName
     */
    public boolean containsSesName() {
        return dataMap.containsKey(TSessionPropertyList.SES_NAME);
    }

    /**
     * remove the field sesName
     */
    public TSessionDTO unsetSesName() {
        dataMap.remove(TSessionPropertyList.SES_NAME);
        return this;
    }

    /**
     * getter for sesPath
     */
    public java.lang.String getSesPath() {
        return ((java.lang.String) dataMap.get(TSessionPropertyList.SES_PATH));
    }

    /**
     * setter for sesPath
     */
    public TSessionDTO setSesPath(java.lang.String value) {
        dataMap.put(TSessionPropertyList.SES_PATH, value);
        return this;
    }

    /**
     * true if record contains the field sesPath
     */
    public boolean containsSesPath() {
        return dataMap.containsKey(TSessionPropertyList.SES_PATH);
    }

    /**
     * remove the field sesPath
     */
    public TSessionDTO unsetSesPath() {
        dataMap.remove(TSessionPropertyList.SES_PATH);
        return this;
    }

    /**
     * getter for sesIndex
     */
    public int getSesIndex() {
        return ((Integer) dataMap.get(TSessionPropertyList.SES_INDEX)).intValue();
    }

    /**
     * setter for sesIndex
     */
    public TSessionDTO setSesIndex(int value) {
        dataMap.put(TSessionPropertyList.SES_INDEX, new Integer(value));
        return this;
    }

    /**
     * true if record contains the field sesIndex
     */
    public boolean containsSesIndex() {
        return dataMap.containsKey(TSessionPropertyList.SES_INDEX);
    }

    /**
     * remove the field sesIndex
     */
    public TSessionDTO unsetSesIndex() {
        dataMap.remove(TSessionPropertyList.SES_INDEX);
        return this;
    }

    /**
     * getter for sesCnxUrl
     */
    public java.lang.String getSesCnxUrl() {
        return ((java.lang.String) dataMap.get(TSessionPropertyList.SES_CNX_URL));
    }

    /**
     * setter for sesCnxUrl
     */
    public TSessionDTO setSesCnxUrl(java.lang.String value) {
        dataMap.put(TSessionPropertyList.SES_CNX_URL, value);
        return this;
    }

    /**
     * true if record contains the field sesCnxUrl
     */
    public boolean containsSesCnxUrl() {
        return dataMap.containsKey(TSessionPropertyList.SES_CNX_URL);
    }

    /**
     * remove the field sesCnxUrl
     */
    public TSessionDTO unsetSesCnxUrl() {
        dataMap.remove(TSessionPropertyList.SES_CNX_URL);
        return this;
    }

    /**
     * getter for sesCnxDriver
     */
    public java.lang.String getSesCnxDriver() {
        return ((java.lang.String) dataMap.get(TSessionPropertyList.SES_CNX_DRIVER));
    }

    /**
     * setter for sesCnxDriver
     */
    public TSessionDTO setSesCnxDriver(java.lang.String value) {
        dataMap.put(TSessionPropertyList.SES_CNX_DRIVER, value);
        return this;
    }

    /**
     * true if record contains the field sesCnxDriver
     */
    public boolean containsSesCnxDriver() {
        return dataMap.containsKey(TSessionPropertyList.SES_CNX_DRIVER);
    }

    /**
     * remove the field sesCnxDriver
     */
    public TSessionDTO unsetSesCnxDriver() {
        dataMap.remove(TSessionPropertyList.SES_CNX_DRIVER);
        return this;
    }

    /**
     * getter for sesCnxLogin
     */
    public java.lang.String getSesCnxLogin() {
        return ((java.lang.String) dataMap.get(TSessionPropertyList.SES_CNX_LOGIN));
    }

    /**
     * setter for sesCnxLogin
     */
    public TSessionDTO setSesCnxLogin(java.lang.String value) {
        dataMap.put(TSessionPropertyList.SES_CNX_LOGIN, value);
        return this;
    }

    /**
     * true if record contains the field sesCnxLogin
     */
    public boolean containsSesCnxLogin() {
        return dataMap.containsKey(TSessionPropertyList.SES_CNX_LOGIN);
    }

    /**
     * remove the field sesCnxLogin
     */
    public TSessionDTO unsetSesCnxLogin() {
        dataMap.remove(TSessionPropertyList.SES_CNX_LOGIN);
        return this;
    }

    /**
     * getter for sesCnxPassword
     */
    public java.lang.String getSesCnxPassword() {
        return ((java.lang.String) dataMap.get(TSessionPropertyList.SES_CNX_PASSWORD));
    }

    /**
     * setter for sesCnxPassword
     */
    public TSessionDTO setSesCnxPassword(java.lang.String value) {
        dataMap.put(TSessionPropertyList.SES_CNX_PASSWORD, value);
        return this;
    }

    /**
     * true if record contains the field sesCnxPassword
     */
    public boolean containsSesCnxPassword() {
        return dataMap.containsKey(TSessionPropertyList.SES_CNX_PASSWORD);
    }

    /**
     * remove the field sesCnxPassword
     */
    public TSessionDTO unsetSesCnxPassword() {
        dataMap.remove(TSessionPropertyList.SES_CNX_PASSWORD);
        return this;
    }

    /**
     * getter for sesCnxAutocommit
     */
    public int getSesCnxAutocommit() {
        return ((Integer) dataMap.get(TSessionPropertyList.SES_CNX_AUTOCOMMIT)).intValue();
    }

    /**
     * setter for sesCnxAutocommit
     */
    public TSessionDTO setSesCnxAutocommit(int value) {
        dataMap.put(TSessionPropertyList.SES_CNX_AUTOCOMMIT, new Integer(value));
        return this;
    }

    /**
     * true if record contains the field sesCnxAutocommit
     */
    public boolean containsSesCnxAutocommit() {
        return dataMap.containsKey(TSessionPropertyList.SES_CNX_AUTOCOMMIT);
    }

    /**
     * remove the field sesCnxAutocommit
     */
    public TSessionDTO unsetSesCnxAutocommit() {
        dataMap.remove(TSessionPropertyList.SES_CNX_AUTOCOMMIT);
        return this;
    }

    /**
     * getter for sesCnxHoldability
     */
    public int getSesCnxHoldability() {
        return ((Integer) dataMap.get(TSessionPropertyList.SES_CNX_HOLDABILITY)).intValue();
    }

    /**
     * setter for sesCnxHoldability
     */
    public TSessionDTO setSesCnxHoldability(int value) {
        dataMap.put(TSessionPropertyList.SES_CNX_HOLDABILITY, new Integer(value));
        return this;
    }

    /**
     * true if record contains the field sesCnxHoldability
     */
    public boolean containsSesCnxHoldability() {
        return dataMap.containsKey(TSessionPropertyList.SES_CNX_HOLDABILITY);
    }

    /**
     * remove the field sesCnxHoldability
     */
    public TSessionDTO unsetSesCnxHoldability() {
        dataMap.remove(TSessionPropertyList.SES_CNX_HOLDABILITY);
        return this;
    }

    /**
     * getter for sesCnxTransIsolation
     */
    public int getSesCnxTransIsolation() {
        return ((Integer) dataMap.get(TSessionPropertyList.SES_CNX_TRANS_ISOLATION)).intValue();
    }

    /**
     * setter for sesCnxTransIsolation
     */
    public TSessionDTO setSesCnxTransIsolation(int value) {
        dataMap.put(TSessionPropertyList.SES_CNX_TRANS_ISOLATION, new Integer(value));
        return this;
    }

    /**
     * true if record contains the field sesCnxTransIsolation
     */
    public boolean containsSesCnxTransIsolation() {
        return dataMap.containsKey(TSessionPropertyList.SES_CNX_TRANS_ISOLATION);
    }

    /**
     * remove the field sesCnxTransIsolation
     */
    public TSessionDTO unsetSesCnxTransIsolation() {
        dataMap.remove(TSessionPropertyList.SES_CNX_TRANS_ISOLATION);
        return this;
    }

    /**
     * getter for sesCnxReadOnly
     */
    public int getSesCnxReadOnly() {
        return ((Integer) dataMap.get(TSessionPropertyList.SES_CNX_READ_ONLY)).intValue();
    }

    /**
     * setter for sesCnxReadOnly
     */
    public TSessionDTO setSesCnxReadOnly(int value) {
        dataMap.put(TSessionPropertyList.SES_CNX_READ_ONLY, new Integer(value));
        return this;
    }

    /**
     * true if record contains the field sesCnxReadOnly
     */
    public boolean containsSesCnxReadOnly() {
        return dataMap.containsKey(TSessionPropertyList.SES_CNX_READ_ONLY);
    }

    /**
     * remove the field sesCnxReadOnly
     */
    public TSessionDTO unsetSesCnxReadOnly() {
        dataMap.remove(TSessionPropertyList.SES_CNX_READ_ONLY);
        return this;
    }

    /**
     * getter for sesCnxOpenScript
     */
    public java.lang.String getSesCnxOpenScript() {
        return ((java.lang.String) dataMap.get(TSessionPropertyList.SES_CNX_OPEN_SCRIPT));
    }

    /**
     * setter for sesCnxOpenScript
     */
    public TSessionDTO setSesCnxOpenScript(java.lang.String value) {
        dataMap.put(TSessionPropertyList.SES_CNX_OPEN_SCRIPT, value);
        return this;
    }

    /**
     * true if record contains the field sesCnxOpenScript
     */
    public boolean containsSesCnxOpenScript() {
        return dataMap.containsKey(TSessionPropertyList.SES_CNX_OPEN_SCRIPT);
    }

    /**
     * remove the field sesCnxOpenScript
     */
    public TSessionDTO unsetSesCnxOpenScript() {
        dataMap.remove(TSessionPropertyList.SES_CNX_OPEN_SCRIPT);
        return this;
    }

    /**
     * getter for sesCnxCloseScript
     */
    public java.lang.String getSesCnxCloseScript() {
        return ((java.lang.String) dataMap.get(TSessionPropertyList.SES_CNX_CLOSE_SCRIPT));
    }

    /**
     * setter for sesCnxCloseScript
     */
    public TSessionDTO setSesCnxCloseScript(java.lang.String value) {
        dataMap.put(TSessionPropertyList.SES_CNX_CLOSE_SCRIPT, value);
        return this;
    }

    /**
     * true if record contains the field sesCnxCloseScript
     */
    public boolean containsSesCnxCloseScript() {
        return dataMap.containsKey(TSessionPropertyList.SES_CNX_CLOSE_SCRIPT);
    }

    /**
     * remove the field sesCnxCloseScript
     */
    public TSessionDTO unsetSesCnxCloseScript() {
        dataMap.remove(TSessionPropertyList.SES_CNX_CLOSE_SCRIPT);
        return this;
    }

    /**
     * getter for sesCnxCreated
     */
    public java.sql.Date getSesCnxCreated() {
        return ((java.sql.Date) dataMap.get(TSessionPropertyList.SES_CNX_CREATED));
    }

    /**
     * setter for sesCnxCreated
     */
    public TSessionDTO setSesCnxCreated(java.sql.Date value) {
        dataMap.put(TSessionPropertyList.SES_CNX_CREATED, value);
        return this;
    }

    /**
     * true if record contains the field sesCnxCreated
     */
    public boolean containsSesCnxCreated() {
        return dataMap.containsKey(TSessionPropertyList.SES_CNX_CREATED);
    }

    /**
     * remove the field sesCnxCreated
     */
    public TSessionDTO unsetSesCnxCreated() {
        dataMap.remove(TSessionPropertyList.SES_CNX_CREATED);
        return this;
    }

    /**
     * getter for sesCnxLastUpdated
     */
    public java.sql.Date getSesCnxLastUpdated() {
        return ((java.sql.Date) dataMap.get(TSessionPropertyList.SES_CNX_LAST_UPDATED));
    }

    /**
     * setter for sesCnxLastUpdated
     */
    public TSessionDTO setSesCnxLastUpdated(java.sql.Date value) {
        dataMap.put(TSessionPropertyList.SES_CNX_LAST_UPDATED, value);
        return this;
    }

    /**
     * true if record contains the field sesCnxLastUpdated
     */
    public boolean containsSesCnxLastUpdated() {
        return dataMap.containsKey(TSessionPropertyList.SES_CNX_LAST_UPDATED);
    }

    /**
     * remove the field sesCnxLastUpdated
     */
    public TSessionDTO unsetSesCnxLastUpdated() {
        dataMap.remove(TSessionPropertyList.SES_CNX_LAST_UPDATED);
        return this;
    }

    /**
     * getter for sesCnxFactoryName
     */
    public java.lang.String getSesCnxFactoryName() {
        return ((java.lang.String) dataMap.get(TSessionPropertyList.SES_CNX_FACTORY_NAME));
    }

    /**
     * setter for sesCnxFactoryName
     */
    public TSessionDTO setSesCnxFactoryName(java.lang.String value) {
        dataMap.put(TSessionPropertyList.SES_CNX_FACTORY_NAME, value);
        return this;
    }

    /**
     * true if record contains the field sesCnxFactoryName
     */
    public boolean containsSesCnxFactoryName() {
        return dataMap.containsKey(TSessionPropertyList.SES_CNX_FACTORY_NAME);
    }

    /**
     * remove the field sesCnxFactoryName
     */
    public TSessionDTO unsetSesCnxFactoryName() {
        dataMap.remove(TSessionPropertyList.SES_CNX_FACTORY_NAME);
        return this;
    }

    /**
     * getter for sesAutoConnect
     */
    public int getSesAutoConnect() {
        return ((Integer) dataMap.get(TSessionPropertyList.SES_AUTO_CONNECT)).intValue();
    }

    /**
     * setter for sesAutoConnect
     */
    public TSessionDTO setSesAutoConnect(int value) {
        dataMap.put(TSessionPropertyList.SES_AUTO_CONNECT, new Integer(value));
        return this;
    }

    /**
     * true if record contains the field sesAutoConnect
     */
    public boolean containsSesAutoConnect() {
        return dataMap.containsKey(TSessionPropertyList.SES_AUTO_CONNECT);
    }

    /**
     * remove the field sesAutoConnect
     */
    public TSessionDTO unsetSesAutoConnect() {
        dataMap.remove(TSessionPropertyList.SES_AUTO_CONNECT);
        return this;
    }

    /**
     * getter for sesDesc
     */
    public java.lang.String getSesDesc() {
        return ((java.lang.String) dataMap.get(TSessionPropertyList.SES_DESC));
    }

    /**
     * setter for sesDesc
     */
    public TSessionDTO setSesDesc(java.lang.String value) {
        dataMap.put(TSessionPropertyList.SES_DESC, value);
        return this;
    }

    /**
     * true if record contains the field sesDesc
     */
    public boolean containsSesDesc() {
        return dataMap.containsKey(TSessionPropertyList.SES_DESC);
    }

    /**
     * remove the field sesDesc
     */
    public TSessionDTO unsetSesDesc() {
        dataMap.remove(TSessionPropertyList.SES_DESC);
        return this;
    }

    public TSessionKey getTSessionKey() {
        Object k0 = dataMap.get(TSessionPropertyList.SES_ID);
        if (k0 == null) {
            return null;
        }
        return new TSessionKey(((Integer) k0).intValue());
    }

    public int size() {
        return dataMap.size();
    }

    public Set keySet() {
        return dataMap.keySet();
    }

}