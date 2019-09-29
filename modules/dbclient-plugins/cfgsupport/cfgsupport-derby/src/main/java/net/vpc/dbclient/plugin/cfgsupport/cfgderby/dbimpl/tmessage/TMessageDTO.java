package net.vpc.dbclient.plugin.cfgsupport.cfgderby.dbimpl.tmessage;

import java.util.HashMap;
import java.util.Set;

/**
 * DO NOT EDIT MANUALLY
 * GENERATED AUTOMATICALLY BY JBGen (0.1)
 *
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @framework neormf (license GPL)
 */
public class TMessageDTO implements Cloneable {
    private HashMap dataMap;

    /**
     * Constructor
     */
    public TMessageDTO() {
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
     * getter for msgId
     */
    public java.lang.String getMsgId() {
        return ((java.lang.String) dataMap.get(TMessagePropertyList.MSG_ID));
    }

    /**
     * setter for msgId
     */
    public TMessageDTO setMsgId(java.lang.String value) {
        dataMap.put(TMessagePropertyList.MSG_ID, value);
        return this;
    }

    /**
     * true if record contains the field msgId
     */
    public boolean containsMsgId() {
        return dataMap.containsKey(TMessagePropertyList.MSG_ID);
    }

    /**
     * remove the field msgId
     */
    public TMessageDTO unsetMsgId() {
        dataMap.remove(TMessagePropertyList.MSG_ID);
        return this;
    }

    /**
     * getter for msgLocName
     */
    public java.lang.String getMsgLocName() {
        return ((java.lang.String) dataMap.get(TMessagePropertyList.MSG_LOC_NAME));
    }

    /**
     * setter for msgLocName
     */
    public TMessageDTO setMsgLocName(java.lang.String value) {
        dataMap.put(TMessagePropertyList.MSG_LOC_NAME, value);
        return this;
    }

    /**
     * true if record contains the field msgLocName
     */
    public boolean containsMsgLocName() {
        return dataMap.containsKey(TMessagePropertyList.MSG_LOC_NAME);
    }

    /**
     * remove the field msgLocName
     */
    public TMessageDTO unsetMsgLocName() {
        dataMap.remove(TMessagePropertyList.MSG_LOC_NAME);
        return this;
    }

    /**
     * getter for msgData
     */
    public java.lang.String getMsgData() {
        return ((java.lang.String) dataMap.get(TMessagePropertyList.MSG_DATA));
    }

    /**
     * setter for msgData
     */
    public TMessageDTO setMsgData(java.lang.String value) {
        dataMap.put(TMessagePropertyList.MSG_DATA, value);
        return this;
    }

    /**
     * true if record contains the field msgData
     */
    public boolean containsMsgData() {
        return dataMap.containsKey(TMessagePropertyList.MSG_DATA);
    }

    /**
     * remove the field msgData
     */
    public TMessageDTO unsetMsgData() {
        dataMap.remove(TMessagePropertyList.MSG_DATA);
        return this;
    }

    public TMessageKey getTMessageKey() {
        Object k0 = dataMap.get(TMessagePropertyList.MSG_ID);
        if (k0 == null) {
            return null;
        }
        Object k1 = dataMap.get(TMessagePropertyList.MSG_LOC_NAME);
        if (k1 == null) {
            return null;
        }
        return new TMessageKey(((java.lang.String) k0), ((java.lang.String) k1));
    }

    public int size() {
        return dataMap.size();
    }

    public Set keySet() {
        return dataMap.keySet();
    }

}