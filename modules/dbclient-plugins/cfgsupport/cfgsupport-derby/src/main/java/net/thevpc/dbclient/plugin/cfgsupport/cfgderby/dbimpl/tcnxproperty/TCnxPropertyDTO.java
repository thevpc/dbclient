package net.thevpc.dbclient.plugin.cfgsupport.cfgderby.dbimpl.tcnxproperty;

import java.util.HashMap;
import java.util.Set;

/**
 * DO NOT EDIT MANUALLY
 * GENERATED AUTOMATICALLY BY JBGen (0.1)
 *
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @framework neormf (license GPL)
 */
public class TCnxPropertyDTO implements Cloneable {
    private HashMap dataMap;

    /**
     * Constructor
     */
    public TCnxPropertyDTO() {
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
     * getter for cprName
     */
    public java.lang.String getCprName() {
        return ((java.lang.String) dataMap.get(TCnxPropertyPropertyList.CPR_NAME));
    }

    /**
     * setter for cprName
     */
    public TCnxPropertyDTO setCprName(java.lang.String value) {
        dataMap.put(TCnxPropertyPropertyList.CPR_NAME, value);
        return this;
    }

    /**
     * true if record contains the field cprName
     */
    public boolean containsCprName() {
        return dataMap.containsKey(TCnxPropertyPropertyList.CPR_NAME);
    }

    /**
     * remove the field cprName
     */
    public TCnxPropertyDTO unsetCprName() {
        dataMap.remove(TCnxPropertyPropertyList.CPR_NAME);
        return this;
    }

    /**
     * getter for cprSesId
     */
    public int getCprSesId() {
        return ((Integer) dataMap.get(TCnxPropertyPropertyList.CPR_SES_ID)).intValue();
    }

    /**
     * setter for cprSesId
     */
    public TCnxPropertyDTO setCprSesId(int value) {
        dataMap.put(TCnxPropertyPropertyList.CPR_SES_ID, new Integer(value));
        return this;
    }

    /**
     * true if record contains the field cprSesId
     */
    public boolean containsCprSesId() {
        return dataMap.containsKey(TCnxPropertyPropertyList.CPR_SES_ID);
    }

    /**
     * remove the field cprSesId
     */
    public TCnxPropertyDTO unsetCprSesId() {
        dataMap.remove(TCnxPropertyPropertyList.CPR_SES_ID);
        return this;
    }

    /**
     * getter for cprValue
     */
    public java.lang.String getCprValue() {
        return ((java.lang.String) dataMap.get(TCnxPropertyPropertyList.CPR_VALUE));
    }

    /**
     * setter for cprValue
     */
    public TCnxPropertyDTO setCprValue(java.lang.String value) {
        dataMap.put(TCnxPropertyPropertyList.CPR_VALUE, value);
        return this;
    }

    /**
     * true if record contains the field cprValue
     */
    public boolean containsCprValue() {
        return dataMap.containsKey(TCnxPropertyPropertyList.CPR_VALUE);
    }

    /**
     * remove the field cprValue
     */
    public TCnxPropertyDTO unsetCprValue() {
        dataMap.remove(TCnxPropertyPropertyList.CPR_VALUE);
        return this;
    }

    public TCnxPropertyKey getTCnxPropertyKey() {
        Object k0 = dataMap.get(TCnxPropertyPropertyList.CPR_NAME);
        if (k0 == null) {
            return null;
        }
        Object k1 = dataMap.get(TCnxPropertyPropertyList.CPR_SES_ID);
        if (k1 == null) {
            return null;
        }
        return new TCnxPropertyKey(((java.lang.String) k0), ((Integer) k1).intValue());
    }

    public int size() {
        return dataMap.size();
    }

    public Set keySet() {
        return dataMap.keySet();
    }

}