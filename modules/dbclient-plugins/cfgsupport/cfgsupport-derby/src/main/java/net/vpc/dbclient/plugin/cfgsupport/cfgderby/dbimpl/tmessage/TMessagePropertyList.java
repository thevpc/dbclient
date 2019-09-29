package net.vpc.dbclient.plugin.cfgsupport.cfgderby.dbimpl.tmessage;

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
public class TMessagePropertyList {
    private LinkedHashMap set = new LinkedHashMap();
    public static final String MSG_ID = "msgId";
    public static final String MSG_LOC_NAME = "msgLocName";
    public static final String MSG_DATA = "msgData";

    /**
     * Constructor
     */
    public TMessagePropertyList() {

    }

    public TMessagePropertyList addMsgId() {
        set.put(MSG_ID, null);
        return this;
    }

    public TMessagePropertyList removeMsgId() {
        set.remove(MSG_ID);
        return this;
    }

    public boolean containsMsgId() {
        return set.containsKey(MSG_ID);
    }

    public TMessagePropertyList addMsgLocName() {
        set.put(MSG_LOC_NAME, null);
        return this;
    }

    public TMessagePropertyList removeMsgLocName() {
        set.remove(MSG_LOC_NAME);
        return this;
    }

    public boolean containsMsgLocName() {
        return set.containsKey(MSG_LOC_NAME);
    }

    public TMessagePropertyList addMsgData() {
        set.put(MSG_DATA, null);
        return this;
    }

    public TMessagePropertyList removeMsgData() {
        set.remove(MSG_DATA);
        return this;
    }

    public boolean containsMsgData() {
        return set.containsKey(MSG_DATA);
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