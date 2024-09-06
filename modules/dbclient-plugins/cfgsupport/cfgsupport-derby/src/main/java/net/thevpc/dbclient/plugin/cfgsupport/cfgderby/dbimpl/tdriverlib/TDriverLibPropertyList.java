package net.thevpc.dbclient.plugin.cfgsupport.cfgderby.dbimpl.tdriverlib;

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
public class TDriverLibPropertyList {
    private LinkedHashMap set = new LinkedHashMap();
    public static final String TDL_DRV_ID = "tdlDrvId";
    public static final String TDL_INDEX = "tdlIndex";
    public static final String TDL_URL = "tdlUrl";

    /**
     * Constructor
     */
    public TDriverLibPropertyList() {

    }

    public TDriverLibPropertyList addTdlDrvId() {
        set.put(TDL_DRV_ID, null);
        return this;
    }

    public TDriverLibPropertyList removeTdlDrvId() {
        set.remove(TDL_DRV_ID);
        return this;
    }

    public boolean containsTdlDrvId() {
        return set.containsKey(TDL_DRV_ID);
    }

    public TDriverLibPropertyList addTdlIndex() {
        set.put(TDL_INDEX, null);
        return this;
    }

    public TDriverLibPropertyList removeTdlIndex() {
        set.remove(TDL_INDEX);
        return this;
    }

    public boolean containsTdlIndex() {
        return set.containsKey(TDL_INDEX);
    }

    public TDriverLibPropertyList addTdlUrl() {
        set.put(TDL_URL, null);
        return this;
    }

    public TDriverLibPropertyList removeTdlUrl() {
        set.remove(TDL_URL);
        return this;
    }

    public boolean containsTdlUrl() {
        return set.containsKey(TDL_URL);
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