package net.thevpc.dbclient.plugin.cfgsupport.cfgderby.dbimpl.tsessionprop;

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
public class TSessionPropPropertyList {
    private LinkedHashMap set = new LinkedHashMap();
    public static final String TSP_NAME = "tspName";
    public static final String TSP_SES_ID = "tspSesId";
    public static final String TSP_VALUE = "tspValue";

    /**
     * Constructor
     */
    public TSessionPropPropertyList() {

    }

    public TSessionPropPropertyList addTspName() {
        set.put(TSP_NAME, null);
        return this;
    }

    public TSessionPropPropertyList removeTspName() {
        set.remove(TSP_NAME);
        return this;
    }

    public boolean containsTspName() {
        return set.containsKey(TSP_NAME);
    }

    public TSessionPropPropertyList addTspSesId() {
        set.put(TSP_SES_ID, null);
        return this;
    }

    public TSessionPropPropertyList removeTspSesId() {
        set.remove(TSP_SES_ID);
        return this;
    }

    public boolean containsTspSesId() {
        return set.containsKey(TSP_SES_ID);
    }

    public TSessionPropPropertyList addTspValue() {
        set.put(TSP_VALUE, null);
        return this;
    }

    public TSessionPropPropertyList removeTspValue() {
        set.remove(TSP_VALUE);
        return this;
    }

    public boolean containsTspValue() {
        return set.containsKey(TSP_VALUE);
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