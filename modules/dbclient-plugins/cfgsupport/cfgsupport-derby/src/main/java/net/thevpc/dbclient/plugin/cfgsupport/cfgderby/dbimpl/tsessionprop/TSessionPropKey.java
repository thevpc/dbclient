package net.thevpc.dbclient.plugin.cfgsupport.cfgderby.dbimpl.tsessionprop;

/**
 * DO NOT EDIT MANUALLY
 * GENERATED AUTOMATICALLY BY JBGen (0.1)
 *
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @framework neormf (license GPL)
 */
public class TSessionPropKey {
    public java.lang.String tspName;
    public int tspSesId;

    /**
     * Constructor
     */
    public TSessionPropKey() {
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
            Object p2 = ((TSessionPropKey) obj).keyPartAt(i);
            if (!p1.equals(p2)) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("TSessionProp");
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
    public TSessionPropKey(java.lang.String tspName, int tspSesId) {
        this.tspName = tspName;
        this.tspSesId = tspSesId;

    }

    public java.lang.String getTspName() {
        return tspName;
    }

    public int getTspSesId() {
        return tspSesId;
    }

    public Object keyPartAt(int index) {
        switch (index) {
            case 0: {
                return tspName;
            }
            case 1: {
                return new Integer(tspSesId);
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