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

package net.vpc.dbclient.api.configmanager;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 29 nov. 2006 00:25:36
 */
public class DBCSessionInfo extends DBCAbstractInfo implements Cloneable {
    public static final String SES_ID = "sesId";
    public static final String SES_PARENT_ID = "sesParentId";
    public static final String SES_NAME = "sesName";
    public static final String SES_PATH = "sesPath";
    public static final String SES_INDEX = "sesIndex";
    public static final String SES_CNX_URL = "sesCnxUrl";
    public static final String SES_CNX_DRIVER = "sesCnxDriver";
    public static final String SES_CNX_LOGIN = "sesCnxLogin";
    public static final String SES_CNX_PASSWORD = "sesCnxPassword";
//    public static final String SES_CNX_AUTOCOMMIT="sesCnxAutocommit";
//    public static final String SES_CNX_HOLDABILITY="sesCnxHoldability";
//    public static final String SES_CNX_TRANS_ISOLATION="sesCnxTransIsolation";
//    public static final String SES_CNX_READ_ONLY="sesCnxReadOnly";
//    public static final String SES_CNX_OPEN_SCRIPT="sesCnxOpenScript";
    //    public static final String SES_CNX_CLOSE_SCRIPT="sesCnxCloseScript";
    public static final String SES_CNX_CREATED = "sesCnxCreated";
    public static final String SES_CNX_LAST_UPDATED = "sesCnxLastUpdated";
    public static final String SES_CNX_FACTORY_NAME = "sesCnxFactoryName";
//    public static final String SES_LAST_DIRECTORY="sesLastDirectory";
//    public static final String SES_PLAF="sesPlaf";
//    public static final String SES_ICON_SET="sesIconSet";
    //    public static final String SES_LOCALE="sesLocale";
    public static final String SES_AUTO_CONNECT = "sesAutoConnect";
    public static final String SES_DESC = "sesDesc";
    public static final String SES_SERVER = "sesServer";
    public static final String SES_READ_ONLY = "sesReadOnly";
    public static final String SES_ASK_FOR_PASSWORD = "sesAskForPassword";
    public static final String SES_PROPERTIES = "sesProperties";

    /**
     * Constructor
     */
    public DBCSessionInfo() {
    }

    /**
     * getter for sesId
     */
    public int getId() {
        return ((Integer) map.get(SES_ID)).intValue();
    }

    /**
     * setter for sesId
     *
     */
    public void setId(int value) {
        map.put(SES_ID, new Integer(value));
    }

    /**
     * true if record contains the field sesId
     */
    public boolean containsId() {
        return map.containsKey(SES_ID);
    }

    /**
     * remove the field sesId
     */
    public DBCSessionInfo unsetId() {
        map.remove(SES_ID);
        return this;
    }

    /**
     * getter for sesId
     */
    public Integer getParentId() {
        return ((Integer) map.get(SES_PARENT_ID));
    }

    /**
     * setter for sesId
     */
    public void setParentId(Integer value) {
        map.put(SES_PARENT_ID, value);
    }

    /**
     * true if record contains the field sesId
     */
    public boolean containsParentId() {
        return map.containsKey(SES_PARENT_ID);
    }

    /**
     * remove the field sesId
     */
    public DBCSessionInfo unsetParentId() {
        map.remove(SES_PARENT_ID);
        return this;
    }

    /**
     * getter for sesName
     */
    public java.lang.String getName() {
        return ((java.lang.String) map.get(SES_NAME));
    }

    /**
     * setter for sesName
     */
    public void setName(java.lang.String value) {
        map.put(SES_NAME, value);
    }

    /**
     * true if record contains the field sesName
     */
    public boolean containsName() {
        return map.containsKey(SES_NAME);
    }

    /**
     * remove the field sesName
     */
    public DBCSessionInfo unsetName() {
        map.remove(SES_NAME);
        return this;
    }

    /**
     * getter for sesPath
     */
    public java.lang.String getPath() {
        return ((java.lang.String) map.get(SES_PATH));
    }

    /**
     * setter for sesPath
     */
    public void setPath(java.lang.String value) {
        map.put(SES_PATH, value);
    }

    /**
     * true if record contains the field sesPath
     */
    public boolean containsPath() {
        return map.containsKey(SES_PATH);
    }

    /**
     * remove the field sesPath
     */
    public DBCSessionInfo unsetPath() {
        map.remove(SES_PATH);
        return this;
    }

    /**
     * getter for sesIndex
     */
    public int getIndex() {
        return ((Integer) map.get(SES_INDEX)).intValue();
    }

    /**
     * setter for sesIndex
     */
    public void setIndex(int value) {
        map.put(SES_INDEX, new Integer(value));
    }

    /**
     * true if record contains the field sesIndex
     */
    public boolean containsIndex() {
        return map.containsKey(SES_INDEX);
    }

    /**
     * remove the field sesIndex
     */
    public DBCSessionInfo unsetIndex() {
        map.remove(SES_INDEX);
        return this;
    }

    /**
     * getter for sesCnxUrl
     */
    public java.lang.String getCnxUrl() {
        return ((java.lang.String) map.get(SES_CNX_URL));
    }

    /**
     * setter for sesCnxUrl
     */
    public void setCnxUrl(java.lang.String value) {
        map.put(SES_CNX_URL, value);
    }

    /**
     * true if record contains the field sesCnxUrl
     */
    public boolean containsCnxUrl() {
        return map.containsKey(SES_CNX_URL);
    }

    /**
     * remove the field sesCnxUrl
     */
    public DBCSessionInfo unsetCnxUrl() {
        map.remove(SES_CNX_URL);
        return this;
    }

    /**
     * getter for sesCnxDriver
     */
    public java.lang.String getCnxDriver() {
        return ((java.lang.String) map.get(SES_CNX_DRIVER));
    }

    /**
     * setter for sesCnxDriver
     */
    public void setCnxDriver(java.lang.String value) {
        map.put(SES_CNX_DRIVER, value);
    }

    /**
     * true if record contains the field sesCnxDriver
     */
    public boolean containsCnxDriver() {
        return map.containsKey(SES_CNX_DRIVER);
    }

    /**
     * remove the field sesCnxDriver
     */
    public DBCSessionInfo unsetCnxDriver() {
        map.remove(SES_CNX_DRIVER);
        return this;
    }

    /**
     * getter for sesCnxLogin
     */
    public java.lang.String getCnxLogin() {
        return ((java.lang.String) map.get(SES_CNX_LOGIN));
    }

    /**
     * setter for sesCnxLogin
     */
    public void setCnxLogin(java.lang.String value) {
        map.put(SES_CNX_LOGIN, value);
    }

    /**
     * true if record contains the field sesCnxLogin
     */
    public boolean containsCnxLogin() {
        return map.containsKey(SES_CNX_LOGIN);
    }

    /**
     * remove the field sesCnxLogin
     */
    public DBCSessionInfo unsetCnxLogin() {
        map.remove(SES_CNX_LOGIN);
        return this;
    }

    /**
     * getter for sesCnxPassword
     */
    public java.lang.String getCnxPassword() {
        return ((java.lang.String) map.get(SES_CNX_PASSWORD));
    }

    /**
     * setter for sesCnxPassword
     */
    public void setCnxPassword(java.lang.String value) {
        map.put(SES_CNX_PASSWORD, value);
    }

    /**
     * true if record contains the field sesCnxPassword
     */
    public boolean containsCnxPassword() {
        return map.containsKey(SES_CNX_PASSWORD);
    }

    /**
     * remove the field sesCnxPassword
     */
    public DBCSessionInfo unsetCnxPassword() {
        map.remove(SES_CNX_PASSWORD);
        return this;
    }

    /**
     * getter for sesCnxCreated
     */
    public java.util.Date getCnxCreated() {
        return ((java.util.Date) map.get(SES_CNX_CREATED));
    }

    /**
     * setter for sesCnxCreated
     */
    public void setCnxCreated(java.util.Date value) {
        map.put(SES_CNX_CREATED, value);
    }

    /**
     * true if record contains the field sesCnxCreated
     */
    public boolean containsCnxCreated() {
        return map.containsKey(SES_CNX_CREATED);
    }

    /**
     * remove the field sesCnxCreated
     */
    public DBCSessionInfo unsetCnxCreated() {
        map.remove(SES_CNX_CREATED);
        return this;
    }

    /**
     * getter for sesCnxLastUpdated
     */
    public java.util.Date getCnxLastUpdated() {
        return ((java.util.Date) map.get(SES_CNX_LAST_UPDATED));
    }

    /**
     * setter for sesCnxLastUpdated
     */
    public void setCnxLastUpdated(java.util.Date value) {
        map.put(SES_CNX_LAST_UPDATED, value);
    }

    /**
     * true if record contains the field sesCnxLastUpdated
     */
    public boolean containsCnxLastUpdated() {
        return map.containsKey(SES_CNX_LAST_UPDATED);
    }

    /**
     * remove the field sesCnxLastUpdated
     */
    public DBCSessionInfo unsetCnxLastUpdated() {
        map.remove(SES_CNX_LAST_UPDATED);
        return this;
    }

    /**
     * getter for sesCnxFactoryName
     */
    public java.lang.String getCnxFactoryName() {
        return ((java.lang.String) map.get(SES_CNX_FACTORY_NAME));
    }

    /**
     * setter for sesCnxFactoryName
     */
    public void setCnxFactoryName(java.lang.String value) {
        map.put(SES_CNX_FACTORY_NAME, value);
    }

    /**
     * true if record contains the field sesCnxFactoryName
     */
    public boolean containsCnxFactoryName() {
        return map.containsKey(SES_CNX_FACTORY_NAME);
    }

    /**
     * remove the field sesCnxFactoryName
     */
    public DBCSessionInfo unsetCnxFactoryName() {
        map.remove(SES_CNX_FACTORY_NAME);
        return this;
    }

    /**
     * getter for sesAutoConnect
     */
    public Boolean isAutoConnect() {
        return ((Boolean) map.get(SES_AUTO_CONNECT));
    }

    /**
     * setter for sesAutoConnect
     */
    public void setAutoConnect(Boolean value) {
        map.put(SES_AUTO_CONNECT, value);
    }

    /**
     * true if record contains the field sesAutoConnect
     */
    public boolean containsAutoConnect() {
        return map.containsKey(SES_AUTO_CONNECT);
    }

    /**
     * remove the field sesAutoConnect
     */
    public DBCSessionInfo unsetAutoConnect() {
        map.remove(SES_AUTO_CONNECT);
        return this;
    }

    /**
     * getter for sesDesc
     */
    public java.lang.String getDesc() {
        return ((java.lang.String) map.get(SES_DESC));
    }

    /**
     * setter for sesDesc
     */
    public void setDesc(java.lang.String value) {
        map.put(SES_DESC, value);
    }

    /**
     * true if record contains the field sesDesc
     */
    public boolean containsDesc() {
        return map.containsKey(SES_DESC);
    }

    /**
     * remove the field sesDesc
     */
    public DBCSessionInfo unsetDesc() {
        map.remove(SES_DESC);
        return this;
    }

    /**
     * getter for sesDesc
     */
    public java.lang.String getServer() {
        return ((java.lang.String) map.get(SES_SERVER));
    }

    /**
     * setter for sesDesc
     */
    public void setServer(java.lang.String value) {
        map.put(SES_SERVER, value);
    }

    /**
     * true if record contains the field sesDesc
     */
    public boolean containsServer() {
        return map.containsKey(SES_SERVER);
    }

    /**
     * remove the field sesDesc
     */
    public DBCSessionInfo unsetServer() {
        map.remove(SES_SERVER);
        return this;
    }

    /**
     * setter for sesDesc
     */
    public void setReadOnly(Boolean value) {
        map.put(SES_READ_ONLY, value);
    }

    /**
     * true if record contains the field sesDesc
     */
    public boolean containsReadOnly() {
        return map.containsKey(SES_READ_ONLY);
    }

    /**
     * remove the field sesDesc
     */
    public DBCSessionInfo unsetReadOnly() {
        map.remove(SES_READ_ONLY);
        return this;
    }

    /**
     * getter for sesDesc
     */
    public Boolean isReaOnly() {
        return ((java.lang.Boolean) map.get(SES_READ_ONLY));
    }


    /**
     * setter for sesDesc
     */
    public void setAskForPassword(Boolean value) {
        map.put(SES_ASK_FOR_PASSWORD, value);
    }

    /**
     * true if record contains the field sesDesc
     */
    public boolean containsAskForPassword() {
        return map.containsKey(SES_ASK_FOR_PASSWORD);
    }

    /**
     * remove the field sesDesc
     */
    public DBCSessionInfo unsetAskForPassword() {
        map.remove(SES_ASK_FOR_PASSWORD);
        return this;
    }

    /**
     * getter for sesDesc
     */
    public Boolean isAskForPassword() {
        return ((java.lang.Boolean) map.get(SES_ASK_FOR_PASSWORD));
    }

    /**
     * setter for sesDesc
     *
     */
    public void setProperties(DBCSessionProperties value) {
        map.put(SES_PROPERTIES, value);
    }

    /**
     * true if record contains the field sesDesc
     */
    public boolean containsProperties() {
        return map.containsKey(SES_PROPERTIES);
    }

    /**
     * remove the field sesDesc
     *
     * @return this instance
     */
    public DBCSessionInfo unsetProperties() {
        map.remove(SES_PROPERTIES);
        return this;
    }

    /**
     * getter for sesDesc
     *
     * @return Properties
     */
    public DBCSessionProperties getProperties() {
        return ((DBCSessionProperties) map.get(SES_PROPERTIES));
    }

    public String getQualifiedName() {
        if (getPath() != null && getPath().length() > 0) {
            return getPath() + "/" + getName();
        } else {
            return getName();
        }
    }
}
