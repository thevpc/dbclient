package net.vpc.dbclient.plugin.cfgsupport.cfgderby.dbimpl.tconfigprop;

import java.util.HashMap;
import java.util.Set;

/**
 * DO NOT EDIT MANUALLY
 * GENERATED AUTOMATICALLY BY JBGen (0.1)
 *
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @framework neormf (license GPL)
 */
public class TConfigPropDTO implements Cloneable {
    private HashMap dataMap;

    /**
     * Constructor
     */
    public TConfigPropDTO() {
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
     * getter for tcpName
     */
    public java.lang.String getTcpName() {
        return ((java.lang.String) dataMap.get(TConfigPropPropertyList.TCP_NAME));
    }

    /**
     * setter for tcpName
     */
    public TConfigPropDTO setTcpName(java.lang.String value) {
        dataMap.put(TConfigPropPropertyList.TCP_NAME, value);
        return this;
    }

    /**
     * true if record contains the field tcpName
     */
    public boolean containsTcpName() {
        return dataMap.containsKey(TConfigPropPropertyList.TCP_NAME);
    }

    /**
     * remove the field tcpName
     */
    public TConfigPropDTO unsetTcpName() {
        dataMap.remove(TConfigPropPropertyList.TCP_NAME);
        return this;
    }

    /**
     * getter for tcpValue
     */
    public java.lang.String getTcpValue() {
        return ((java.lang.String) dataMap.get(TConfigPropPropertyList.TCP_VALUE));
    }

    /**
     * setter for tcpValue
     */
    public TConfigPropDTO setTcpValue(java.lang.String value) {
        dataMap.put(TConfigPropPropertyList.TCP_VALUE, value);
        return this;
    }

    /**
     * true if record contains the field tcpValue
     */
    public boolean containsTcpValue() {
        return dataMap.containsKey(TConfigPropPropertyList.TCP_VALUE);
    }

    /**
     * remove the field tcpValue
     */
    public TConfigPropDTO unsetTcpValue() {
        dataMap.remove(TConfigPropPropertyList.TCP_VALUE);
        return this;
    }

    public TConfigPropKey getTConfigPropKey() {
        Object k0 = dataMap.get(TConfigPropPropertyList.TCP_NAME);
        if (k0 == null) {
            return null;
        }
        return new TConfigPropKey(((java.lang.String) k0));
    }

    public int size() {
        return dataMap.size();
    }

    public Set keySet() {
        return dataMap.keySet();
    }

}