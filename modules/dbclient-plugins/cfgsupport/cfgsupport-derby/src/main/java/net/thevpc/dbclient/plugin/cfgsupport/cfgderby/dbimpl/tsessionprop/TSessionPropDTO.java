package net.thevpc.dbclient.plugin.cfgsupport.cfgderby.dbimpl.tsessionprop;

import java.util.HashMap;
import java.util.Set;

/**
 * DO NOT EDIT MANUALLY
 * GENERATED AUTOMATICALLY BY JBGen (0.1)
 *
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @framework neormf (license GPL)
 */
public class TSessionPropDTO implements Cloneable {
    private HashMap dataMap;

    /**
     * Constructor
     */
    public TSessionPropDTO() {
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
     * getter for tspName
     */
    public java.lang.String getTspName() {
        return ((java.lang.String) dataMap.get(TSessionPropPropertyList.TSP_NAME));
    }

    /**
     * setter for tspName
     */
    public TSessionPropDTO setTspName(java.lang.String value) {
        dataMap.put(TSessionPropPropertyList.TSP_NAME, value);
        return this;
    }

    /**
     * true if record contains the field tspName
     */
    public boolean containsTspName() {
        return dataMap.containsKey(TSessionPropPropertyList.TSP_NAME);
    }

    /**
     * remove the field tspName
     */
    public TSessionPropDTO unsetTspName() {
        dataMap.remove(TSessionPropPropertyList.TSP_NAME);
        return this;
    }

    /**
     * getter for tspSesId
     */
    public int getTspSesId() {
        return ((Integer) dataMap.get(TSessionPropPropertyList.TSP_SES_ID)).intValue();
    }

    /**
     * setter for tspSesId
     */
    public TSessionPropDTO setTspSesId(int value) {
        dataMap.put(TSessionPropPropertyList.TSP_SES_ID, value);
        return this;
    }

    /**
     * true if record contains the field tspSesId
     */
    public boolean containsTspSesId() {
        return dataMap.containsKey(TSessionPropPropertyList.TSP_SES_ID);
    }

    /**
     * remove the field tspSesId
     */
    public TSessionPropDTO unsetTspSesId() {
        dataMap.remove(TSessionPropPropertyList.TSP_SES_ID);
        return this;
    }

    /**
     * getter for tspValue
     */
    public java.lang.String getTspValue() {
        return ((java.lang.String) dataMap.get(TSessionPropPropertyList.TSP_VALUE));
    }

    /**
     * setter for tspValue
     */
    public TSessionPropDTO setTspValue(java.lang.String value) {
        dataMap.put(TSessionPropPropertyList.TSP_VALUE, value);
        return this;
    }

    /**
     * true if record contains the field tspValue
     */
    public boolean containsTspValue() {
        return dataMap.containsKey(TSessionPropPropertyList.TSP_VALUE);
    }

    /**
     * remove the field tspValue
     */
    public TSessionPropDTO unsetTspValue() {
        dataMap.remove(TSessionPropPropertyList.TSP_VALUE);
        return this;
    }

    public TSessionPropKey getTSessionPropKey() {
        Object k0 = dataMap.get(TSessionPropPropertyList.TSP_NAME);
        if (k0 == null) {
            return null;
        }
        Object k1 = dataMap.get(TSessionPropPropertyList.TSP_SES_ID);
        if (k1 == null) {
            return null;
        }
        return new TSessionPropKey(((java.lang.String) k0), ((Integer) k1).intValue());
    }

    public int size() {
        return dataMap.size();
    }

    public Set keySet() {
        return dataMap.keySet();
    }

}
