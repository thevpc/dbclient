package net.vpc.dbclient.plugin.cfgsupport.cfgderby.dbimpl.tdriver;

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
public class TDriverPropertyList {
    private LinkedHashMap set = new LinkedHashMap();
    public static final String DRV_ID = "drvId";
    public static final String DRV_NAME = "drvName";
    public static final String DRV_INDEX = "drvIndex";
    public static final String DRV_CLASS_NAME = "drvClassName";
    public static final String DRV_DEFAULT_URL = "drvDefaultUrl";
    public static final String DRV_DEFAULT_LOGIN = "drvDefaultLogin";
    public static final String DRV_DEFAULT_PASSWORD = "drvDefaultPassword";
    public static final String DRV_DEFAULT_DESC = "drvDefaultDesc";
    public static final String DRV_ENABLED = "drvEnabled";

    /**
     * Constructor
     */
    public TDriverPropertyList() {

    }

    public TDriverPropertyList addDrvId() {
        set.put(DRV_ID, null);
        return this;
    }

    public TDriverPropertyList removeDrvId() {
        set.remove(DRV_ID);
        return this;
    }

    public boolean containsDrvId() {
        return set.containsKey(DRV_ID);
    }

    public TDriverPropertyList addDrvName() {
        set.put(DRV_NAME, null);
        return this;
    }

    public TDriverPropertyList removeDrvName() {
        set.remove(DRV_NAME);
        return this;
    }

    public boolean containsDrvName() {
        return set.containsKey(DRV_NAME);
    }

    public TDriverPropertyList addDrvIndex() {
        set.put(DRV_INDEX, null);
        return this;
    }

    public TDriverPropertyList removeDrvIndex() {
        set.remove(DRV_INDEX);
        return this;
    }

    public boolean containsDrvIndex() {
        return set.containsKey(DRV_INDEX);
    }

    public TDriverPropertyList addDrvClassName() {
        set.put(DRV_CLASS_NAME, null);
        return this;
    }

    public TDriverPropertyList removeDrvClassName() {
        set.remove(DRV_CLASS_NAME);
        return this;
    }

    public boolean containsDrvClassName() {
        return set.containsKey(DRV_CLASS_NAME);
    }

    public TDriverPropertyList addDrvDefaultUrl() {
        set.put(DRV_DEFAULT_URL, null);
        return this;
    }

    public TDriverPropertyList removeDrvDefaultUrl() {
        set.remove(DRV_DEFAULT_URL);
        return this;
    }

    public boolean containsDrvDefaultUrl() {
        return set.containsKey(DRV_DEFAULT_URL);
    }

    public TDriverPropertyList addDrvDefaultLogin() {
        set.put(DRV_DEFAULT_LOGIN, null);
        return this;
    }

    public TDriverPropertyList removeDrvDefaultLogin() {
        set.remove(DRV_DEFAULT_LOGIN);
        return this;
    }

    public boolean containsDrvDefaultLogin() {
        return set.containsKey(DRV_DEFAULT_LOGIN);
    }

    public TDriverPropertyList addDrvDefaultPassword() {
        set.put(DRV_DEFAULT_PASSWORD, null);
        return this;
    }

    public TDriverPropertyList removeDrvDefaultPassword() {
        set.remove(DRV_DEFAULT_PASSWORD);
        return this;
    }

    public boolean containsDrvDefaultPassword() {
        return set.containsKey(DRV_DEFAULT_PASSWORD);
    }

    public TDriverPropertyList addDrvDefaultDesc() {
        set.put(DRV_DEFAULT_DESC, null);
        return this;
    }

    public TDriverPropertyList removeDrvDefaultDesc() {
        set.remove(DRV_DEFAULT_DESC);
        return this;
    }

    public boolean containsDrvDefaultDesc() {
        return set.containsKey(DRV_DEFAULT_DESC);
    }

    public TDriverPropertyList addDrvEnabled() {
        set.put(DRV_ENABLED, null);
        return this;
    }

    public TDriverPropertyList removeDrvEnabled() {
        set.remove(DRV_ENABLED);
        return this;
    }

    public boolean containsDrvEnabled() {
        return set.containsKey(DRV_ENABLED);
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