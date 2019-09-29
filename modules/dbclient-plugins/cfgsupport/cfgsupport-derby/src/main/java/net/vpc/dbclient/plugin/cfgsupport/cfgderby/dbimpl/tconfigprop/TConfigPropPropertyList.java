package net.vpc.dbclient.plugin.cfgsupport.cfgderby.dbimpl.tconfigprop;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * DO NOT EDIT MANUALLY
 * GENERATED AUTOMATICALLY BY JBGen (0.1)
 *
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @framework neormf (license GPL)
 */
public class TConfigPropPropertyList {
    private LinkedHashMap set = new LinkedHashMap();
    public static final String TCP_NAME = "tcpName";
    public static final String TCP_VALUE = "tcpValue";

    /**
     * Constructor
     */
    public TConfigPropPropertyList() {

    }

    public TConfigPropPropertyList addTcpName() {
        set.put(TCP_NAME, null);
        return this;
    }

    public TConfigPropPropertyList removeTcpName() {
        set.remove(TCP_NAME);
        return this;
    }

    public boolean containsTcpName() {
        return set.containsKey(TCP_NAME);
    }

    public TConfigPropPropertyList addTcpValue() {
        set.put(TCP_VALUE, null);
        return this;
    }

    public TConfigPropPropertyList removeTcpValue() {
        set.remove(TCP_VALUE);
        return this;
    }

    public boolean containsTcpValue() {
        return set.containsKey(TCP_VALUE);
    }

    public Iterator keyIterator() {
        return set.keySet().iterator();
    }

    public int size() {
        return set.size();
    }

    public Set keySet() {
        return set.keySet();
    }

    public Object getPropertyConstraints(String propertyName) {
        return set.get(propertyName);
    }

}