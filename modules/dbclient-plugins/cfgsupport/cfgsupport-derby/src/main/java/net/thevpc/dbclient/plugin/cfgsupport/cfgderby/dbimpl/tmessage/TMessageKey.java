package net.thevpc.dbclient.plugin.cfgsupport.cfgderby.dbimpl.tmessage;

/**
 * DO NOT EDIT MANUALLY
 * GENERATED AUTOMATICALLY BY JBGen (0.1)
 *
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @framework neormf (license GPL)
 */
public class TMessageKey {
    public java.lang.String msgId;
    public java.lang.String msgLocName;

    /**
     * Constructor
     */
    public TMessageKey() {
        super();
    }

    public int hashCode() {
        int hash = 17;
        for (int i = keySize() - 1; i >= 0; i--) {
            hash = 31 * hash + keyPartAt(i).hashCode();
        }
        return hash;
    }

    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        for (int i = keySize() - 1; i >= 0; i--) {
            Object p1 = keyPartAt(i);
            Object p2 = ((TMessageKey) obj).keyPartAt(i);
            if (!p1.equals(p2)) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("TMessage");
        sb.append("(");
        int max = keySize();
        for (int i = 0; i < max; i++) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(keyPartAt(i));
        }
        sb.append(")");
        return sb.toString();
    }

    /**
     * Constructor
     */
    public TMessageKey(java.lang.String msgId, java.lang.String msgLocName) {
        this.msgId = msgId;
        this.msgLocName = msgLocName;

    }

    public java.lang.String getMsgId() {
        return msgId;
    }

    public java.lang.String getMsgLocName() {
        return msgLocName;
    }

    public Object keyPartAt(int index) {
        switch (index) {
            case 0: {
                return msgId;
            }
            case 1: {
                return msgLocName;
            }
            default: {
                throw new ArrayIndexOutOfBoundsException(index);
            }
        }
    }

    public int keySize() {
        return 2;
    }

}