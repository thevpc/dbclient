package net.vpc.dbclient.plugin.cfgsupport.cfgderby.dbimpl.tcnxproperty;

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
public class TCnxPropertyPropertyList {
    private LinkedHashMap set = new LinkedHashMap();
    public static final String CPR_NAME = "cprName";
    public static final String CPR_SES_ID = "cprSesId";
    public static final String CPR_VALUE = "cprValue";

    /**
     * Constructor
     */
    public TCnxPropertyPropertyList() {

    }

    public TCnxPropertyPropertyList addCprName() {
        set.put(CPR_NAME, null);
        return this;
    }

    public TCnxPropertyPropertyList removeCprName() {
        set.remove(CPR_NAME);
        return this;
    }

    public boolean containsCprName() {
        return set.containsKey(CPR_NAME);
    }

    public TCnxPropertyPropertyList addCprSesId() {
        set.put(CPR_SES_ID, null);
        return this;
    }

    public TCnxPropertyPropertyList removeCprSesId() {
        set.remove(CPR_SES_ID);
        return this;
    }

    public boolean containsCprSesId() {
        return set.containsKey(CPR_SES_ID);
    }

    public TCnxPropertyPropertyList addCprValue() {
        set.put(CPR_VALUE, null);
        return this;
    }

    public TCnxPropertyPropertyList removeCprValue() {
        set.remove(CPR_VALUE);
        return this;
    }

    public boolean containsCprValue() {
        return set.containsKey(CPR_VALUE);
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