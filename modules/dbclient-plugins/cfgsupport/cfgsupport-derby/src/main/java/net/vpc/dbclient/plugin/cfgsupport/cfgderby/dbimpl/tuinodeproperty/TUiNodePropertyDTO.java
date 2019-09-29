package net.vpc.dbclient.plugin.cfgsupport.cfgderby.dbimpl.tuinodeproperty;

import java.util.HashMap;
import java.util.Set;

/**
 * DO NOT EDIT MANUALLY
 * GENERATED AUTOMATICALLY BY JBGen (0.1)
 *
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @framework neormf (license GPL)
 */
public class TUiNodePropertyDTO implements Cloneable {
    private HashMap dataMap;

    /**
     * Constructor
     */
    public TUiNodePropertyDTO() {
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
     * getter for unpCode
     */
    public java.lang.String getUnpCode() {
        return ((java.lang.String) dataMap.get(TUiNodePropertyPropertyList.UNP_CODE));
    }

    /**
     * setter for unpCode
     */
    public TUiNodePropertyDTO setUnpCode(java.lang.String value) {
        dataMap.put(TUiNodePropertyPropertyList.UNP_CODE, value);
        return this;
    }

    /**
     * true if record contains the field unpCode
     */
    public boolean containsUnpCode() {
        return dataMap.containsKey(TUiNodePropertyPropertyList.UNP_CODE);
    }

    /**
     * remove the field unpCode
     */
    public TUiNodePropertyDTO unsetUnpCode() {
        dataMap.remove(TUiNodePropertyPropertyList.UNP_CODE);
        return this;
    }

    /**
     * getter for unpPath
     */
    public java.lang.String getUnpPath() {
        return ((java.lang.String) dataMap.get(TUiNodePropertyPropertyList.UNP_PATH));
    }

    /**
     * setter for unpPath
     */
    public TUiNodePropertyDTO setUnpPath(java.lang.String value) {
        dataMap.put(TUiNodePropertyPropertyList.UNP_PATH, value);
        return this;
    }

    /**
     * true if record contains the field unpPath
     */
    public boolean containsUnpPath() {
        return dataMap.containsKey(TUiNodePropertyPropertyList.UNP_PATH);
    }

    /**
     * remove the field unpPath
     */
    public TUiNodePropertyDTO unsetUnpPath() {
        dataMap.remove(TUiNodePropertyPropertyList.UNP_PATH);
        return this;
    }

    /**
     * getter for unpSesId
     */
    public int getUnpSesId() {
        return ((Integer) dataMap.get(TUiNodePropertyPropertyList.UNP_SES_ID)).intValue();
    }

    /**
     * setter for unpSesId
     */
    public TUiNodePropertyDTO setUnpSesId(int value) {
        dataMap.put(TUiNodePropertyPropertyList.UNP_SES_ID, new Integer(value));
        return this;
    }

    /**
     * true if record contains the field unpSesId
     */
    public boolean containsUnpSesId() {
        return dataMap.containsKey(TUiNodePropertyPropertyList.UNP_SES_ID);
    }

    /**
     * remove the field unpSesId
     */
    public TUiNodePropertyDTO unsetUnpSesId() {
        dataMap.remove(TUiNodePropertyPropertyList.UNP_SES_ID);
        return this;
    }

    /**
     * getter for unpValue
     */
    public java.lang.String getUnpValue() {
        return ((java.lang.String) dataMap.get(TUiNodePropertyPropertyList.UNP_VALUE));
    }

    /**
     * setter for unpValue
     */
    public TUiNodePropertyDTO setUnpValue(java.lang.String value) {
        dataMap.put(TUiNodePropertyPropertyList.UNP_VALUE, value);
        return this;
    }

    /**
     * true if record contains the field unpValue
     */
    public boolean containsUnpValue() {
        return dataMap.containsKey(TUiNodePropertyPropertyList.UNP_VALUE);
    }

    /**
     * remove the field unpValue
     */
    public TUiNodePropertyDTO unsetUnpValue() {
        dataMap.remove(TUiNodePropertyPropertyList.UNP_VALUE);
        return this;
    }

    public TUiNodePropertyKey getTUiNodePropertyKey() {
        Object k0 = dataMap.get(TUiNodePropertyPropertyList.UNP_CODE);
        if (k0 == null) {
            return null;
        }
        Object k1 = dataMap.get(TUiNodePropertyPropertyList.UNP_PATH);
        if (k1 == null) {
            return null;
        }
        Object k2 = dataMap.get(TUiNodePropertyPropertyList.UNP_SES_ID);
        if (k2 == null) {
            return null;
        }
        return new TUiNodePropertyKey(((java.lang.String) k0), ((java.lang.String) k1), ((Integer) k2).intValue());
    }

    public int size() {
        return dataMap.size();
    }

    public Set keySet() {
        return dataMap.keySet();
    }

}