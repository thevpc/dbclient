package net.thevpc.dbclient.plugin.cfgsupport.cfgderby.dbimpl.tdriver;

import java.util.HashMap;
import java.util.Set;

/**
 * DO NOT EDIT MANUALLY
 * GENERATED AUTOMATICALLY BY JBGen (0.1)
 *
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @framework neormf (license GPL)
 */
public class TDriverDTO implements Cloneable {
    private HashMap dataMap;

    /**
     * Constructor
     */
    public TDriverDTO() {
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
     * getter for drvId
     */
    public int getDrvId() {
        return ((Integer) dataMap.get(TDriverPropertyList.DRV_ID)).intValue();
    }

    /**
     * setter for drvId
     */
    public TDriverDTO setDrvId(int value) {
        dataMap.put(TDriverPropertyList.DRV_ID, new Integer(value));
        return this;
    }

    /**
     * true if record contains the field drvId
     */
    public boolean containsDrvId() {
        return dataMap.containsKey(TDriverPropertyList.DRV_ID);
    }

    /**
     * remove the field drvId
     */
    public TDriverDTO unsetDrvId() {
        dataMap.remove(TDriverPropertyList.DRV_ID);
        return this;
    }

    /**
     * getter for drvName
     */
    public java.lang.String getDrvName() {
        return ((java.lang.String) dataMap.get(TDriverPropertyList.DRV_NAME));
    }

    /**
     * setter for drvName
     */
    public TDriverDTO setDrvName(java.lang.String value) {
        dataMap.put(TDriverPropertyList.DRV_NAME, value);
        return this;
    }

    /**
     * true if record contains the field drvName
     */
    public boolean containsDrvName() {
        return dataMap.containsKey(TDriverPropertyList.DRV_NAME);
    }

    /**
     * remove the field drvName
     */
    public TDriverDTO unsetDrvName() {
        dataMap.remove(TDriverPropertyList.DRV_NAME);
        return this;
    }

    /**
     * getter for drvIndex
     */
    public int getDrvIndex() {
        return ((Integer) dataMap.get(TDriverPropertyList.DRV_INDEX)).intValue();
    }

    /**
     * setter for drvIndex
     */
    public TDriverDTO setDrvIndex(int value) {
        dataMap.put(TDriverPropertyList.DRV_INDEX, new Integer(value));
        return this;
    }

    /**
     * true if record contains the field drvIndex
     */
    public boolean containsDrvIndex() {
        return dataMap.containsKey(TDriverPropertyList.DRV_INDEX);
    }

    /**
     * remove the field drvIndex
     */
    public TDriverDTO unsetDrvIndex() {
        dataMap.remove(TDriverPropertyList.DRV_INDEX);
        return this;
    }

    /**
     * getter for drvClassName
     */
    public java.lang.String getDrvClassName() {
        return ((java.lang.String) dataMap.get(TDriverPropertyList.DRV_CLASS_NAME));
    }

    /**
     * setter for drvClassName
     */
    public TDriverDTO setDrvClassName(java.lang.String value) {
        dataMap.put(TDriverPropertyList.DRV_CLASS_NAME, value);
        return this;
    }

    /**
     * true if record contains the field drvClassName
     */
    public boolean containsDrvClassName() {
        return dataMap.containsKey(TDriverPropertyList.DRV_CLASS_NAME);
    }

    /**
     * remove the field drvClassName
     */
    public TDriverDTO unsetDrvClassName() {
        dataMap.remove(TDriverPropertyList.DRV_CLASS_NAME);
        return this;
    }

    /**
     * getter for drvDefaultUrl
     */
    public java.lang.String getDrvDefaultUrl() {
        return ((java.lang.String) dataMap.get(TDriverPropertyList.DRV_DEFAULT_URL));
    }

    /**
     * setter for drvDefaultUrl
     */
    public TDriverDTO setDrvDefaultUrl(java.lang.String value) {
        dataMap.put(TDriverPropertyList.DRV_DEFAULT_URL, value);
        return this;
    }

    /**
     * true if record contains the field drvDefaultUrl
     */
    public boolean containsDrvDefaultUrl() {
        return dataMap.containsKey(TDriverPropertyList.DRV_DEFAULT_URL);
    }

    /**
     * remove the field drvDefaultUrl
     */
    public TDriverDTO unsetDrvDefaultUrl() {
        dataMap.remove(TDriverPropertyList.DRV_DEFAULT_URL);
        return this;
    }

    /**
     * getter for drvDefaultLogin
     */
    public java.lang.String getDrvDefaultLogin() {
        return ((java.lang.String) dataMap.get(TDriverPropertyList.DRV_DEFAULT_LOGIN));
    }

    /**
     * setter for drvDefaultLogin
     */
    public TDriverDTO setDrvDefaultLogin(java.lang.String value) {
        dataMap.put(TDriverPropertyList.DRV_DEFAULT_LOGIN, value);
        return this;
    }

    /**
     * true if record contains the field drvDefaultLogin
     */
    public boolean containsDrvDefaultLogin() {
        return dataMap.containsKey(TDriverPropertyList.DRV_DEFAULT_LOGIN);
    }

    /**
     * remove the field drvDefaultLogin
     */
    public TDriverDTO unsetDrvDefaultLogin() {
        dataMap.remove(TDriverPropertyList.DRV_DEFAULT_LOGIN);
        return this;
    }

    /**
     * getter for drvDefaultPassword
     */
    public java.lang.String getDrvDefaultPassword() {
        return ((java.lang.String) dataMap.get(TDriverPropertyList.DRV_DEFAULT_PASSWORD));
    }

    /**
     * setter for drvDefaultPassword
     */
    public TDriverDTO setDrvDefaultPassword(java.lang.String value) {
        dataMap.put(TDriverPropertyList.DRV_DEFAULT_PASSWORD, value);
        return this;
    }

    /**
     * true if record contains the field drvDefaultPassword
     */
    public boolean containsDrvDefaultPassword() {
        return dataMap.containsKey(TDriverPropertyList.DRV_DEFAULT_PASSWORD);
    }

    /**
     * remove the field drvDefaultPassword
     */
    public TDriverDTO unsetDrvDefaultPassword() {
        dataMap.remove(TDriverPropertyList.DRV_DEFAULT_PASSWORD);
        return this;
    }

    /**
     * getter for drvDefaultDesc
     */
    public java.lang.String getDrvDefaultDesc() {
        return ((java.lang.String) dataMap.get(TDriverPropertyList.DRV_DEFAULT_DESC));
    }

    /**
     * setter for drvDefaultDesc
     */
    public TDriverDTO setDrvDefaultDesc(java.lang.String value) {
        dataMap.put(TDriverPropertyList.DRV_DEFAULT_DESC, value);
        return this;
    }

    /**
     * true if record contains the field drvDefaultDesc
     */
    public boolean containsDrvDefaultDesc() {
        return dataMap.containsKey(TDriverPropertyList.DRV_DEFAULT_DESC);
    }

    /**
     * remove the field drvDefaultDesc
     */
    public TDriverDTO unsetDrvDefaultDesc() {
        dataMap.remove(TDriverPropertyList.DRV_DEFAULT_DESC);
        return this;
    }

    /**
     * getter for drvEnabled
     */
    public int getDrvEnabled() {
        return ((Integer) dataMap.get(TDriverPropertyList.DRV_ENABLED)).intValue();
    }

    /**
     * setter for drvEnabled
     */
    public TDriverDTO setDrvEnabled(int value) {
        dataMap.put(TDriverPropertyList.DRV_ENABLED, new Integer(value));
        return this;
    }

    /**
     * true if record contains the field drvEnabled
     */
    public boolean containsDrvEnabled() {
        return dataMap.containsKey(TDriverPropertyList.DRV_ENABLED);
    }

    /**
     * remove the field drvEnabled
     */
    public TDriverDTO unsetDrvEnabled() {
        dataMap.remove(TDriverPropertyList.DRV_ENABLED);
        return this;
    }

    public TDriverKey getTDriverKey() {
        Object k0 = dataMap.get(TDriverPropertyList.DRV_ID);
        if (k0 == null) {
            return null;
        }
        return new TDriverKey(((Integer) k0).intValue());
    }

    public int size() {
        return dataMap.size();
    }

    public Set keySet() {
        return dataMap.keySet();
    }

}