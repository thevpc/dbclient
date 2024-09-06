package net.thevpc.dbclient.plugin.cfgsupport.cfgderby.dbimpl.tdriver;

/**
 * DO NOT EDIT MANUALLY
 * GENERATED AUTOMATICALLY BY JBGen (0.1)
 *
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @framework neormf (license GPL)
 */
public class TDriverKey {
    public int drvId;

    /**
     * Constructor
     */
    public TDriverKey() {
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
            Object p2 = ((TDriverKey) obj).keyPartAt(i);
            if (!p1.equals(p2)) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("TDriver");
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
    public TDriverKey(int drvId) {
        this.drvId = drvId;

    }

    public int getDrvId() {
        return drvId;
    }

    public Object keyPartAt(int index) {
        return new Integer(drvId);

    }

    public int keySize() {
        return 1;
    }

}