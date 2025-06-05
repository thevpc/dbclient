package net.thevpc.dbclient.plugin.cfgsupport.cfgderby.dbimpl.tdriverlib;

import java.util.HashMap;
import java.util.Set;

/**
 * DO NOT EDIT MANUALLY
 * GENERATED AUTOMATICALLY BY JBGen (0.1)
 *
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @framework neormf (license GPL)
 */
public class TDriverLibDTO implements Cloneable {
    private HashMap dataMap;

    /**
     * Constructor
     */
    public TDriverLibDTO() {
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
     * getter for tdlDrvId
     */
    public int getTdlDrvId() {
        return ((Integer) dataMap.get(TDriverLibPropertyList.TDL_DRV_ID)).intValue();
    }

    /**
     * setter for tdlDrvId
     */
    public TDriverLibDTO setTdlDrvId(int value) {
        dataMap.put(TDriverLibPropertyList.TDL_DRV_ID, (value));
        return this;
    }

    /**
     * true if record contains the field tdlDrvId
     */
    public boolean containsTdlDrvId() {
        return dataMap.containsKey(TDriverLibPropertyList.TDL_DRV_ID);
    }

    /**
     * remove the field tdlDrvId
     */
    public TDriverLibDTO unsetTdlDrvId() {
        dataMap.remove(TDriverLibPropertyList.TDL_DRV_ID);
        return this;
    }

    /**
     * getter for tdlIndex
     */
    public int getTdlIndex() {
        return ((Integer) dataMap.get(TDriverLibPropertyList.TDL_INDEX)).intValue();
    }

    /**
     * setter for tdlIndex
     */
    public TDriverLibDTO setTdlIndex(int value) {
        dataMap.put(TDriverLibPropertyList.TDL_INDEX, (value));
        return this;
    }

    /**
     * true if record contains the field tdlIndex
     */
    public boolean containsTdlIndex() {
        return dataMap.containsKey(TDriverLibPropertyList.TDL_INDEX);
    }

    /**
     * remove the field tdlIndex
     */
    public TDriverLibDTO unsetTdlIndex() {
        dataMap.remove(TDriverLibPropertyList.TDL_INDEX);
        return this;
    }

    /**
     * getter for tdlUrl
     */
    public java.lang.String getTdlUrl() {
        return ((java.lang.String) dataMap.get(TDriverLibPropertyList.TDL_URL));
    }

    /**
     * setter for tdlUrl
     */
    public TDriverLibDTO setTdlUrl(java.lang.String value) {
        dataMap.put(TDriverLibPropertyList.TDL_URL, value);
        return this;
    }

    /**
     * true if record contains the field tdlUrl
     */
    public boolean containsTdlUrl() {
        return dataMap.containsKey(TDriverLibPropertyList.TDL_URL);
    }

    /**
     * remove the field tdlUrl
     */
    public TDriverLibDTO unsetTdlUrl() {
        dataMap.remove(TDriverLibPropertyList.TDL_URL);
        return this;
    }

    public TDriverLibKey getTDriverLibKey() {
        Object k0 = dataMap.get(TDriverLibPropertyList.TDL_DRV_ID);
        if (k0 == null) {
            return null;
        }
        Object k1 = dataMap.get(TDriverLibPropertyList.TDL_INDEX);
        if (k1 == null) {
            return null;
        }
        return new TDriverLibKey(((Integer) k0).intValue(), ((Integer) k1).intValue());
    }

    public int size() {
        return dataMap.size();
    }

    public Set keySet() {
        return dataMap.keySet();
    }

}
