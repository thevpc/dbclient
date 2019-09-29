package net.vpc.dbclient.plugin.cfgsupport.cfgderby.dbimpl.tuinodeproperty;

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
public class TUiNodePropertyPropertyList {
    private LinkedHashMap set = new LinkedHashMap();
    public static final String UNP_CODE = "unpCode";
    public static final String UNP_PATH = "unpPath";
    public static final String UNP_SES_ID = "unpSesId";
    public static final String UNP_VALUE = "unpValue";

    /**
     * Constructor
     */
    public TUiNodePropertyPropertyList() {

    }

    public TUiNodePropertyPropertyList addUnpCode() {
        set.put(UNP_CODE, null);
        return this;
    }

    public TUiNodePropertyPropertyList removeUnpCode() {
        set.remove(UNP_CODE);
        return this;
    }

    public boolean containsUnpCode() {
        return set.containsKey(UNP_CODE);
    }

    public TUiNodePropertyPropertyList addUnpPath() {
        set.put(UNP_PATH, null);
        return this;
    }

    public TUiNodePropertyPropertyList removeUnpPath() {
        set.remove(UNP_PATH);
        return this;
    }

    public boolean containsUnpPath() {
        return set.containsKey(UNP_PATH);
    }

    public TUiNodePropertyPropertyList addUnpSesId() {
        set.put(UNP_SES_ID, null);
        return this;
    }

    public TUiNodePropertyPropertyList removeUnpSesId() {
        set.remove(UNP_SES_ID);
        return this;
    }

    public boolean containsUnpSesId() {
        return set.containsKey(UNP_SES_ID);
    }

    public TUiNodePropertyPropertyList addUnpValue() {
        set.put(UNP_VALUE, null);
        return this;
    }

    public TUiNodePropertyPropertyList removeUnpValue() {
        set.remove(UNP_VALUE);
        return this;
    }

    public boolean containsUnpValue() {
        return set.containsKey(UNP_VALUE);
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