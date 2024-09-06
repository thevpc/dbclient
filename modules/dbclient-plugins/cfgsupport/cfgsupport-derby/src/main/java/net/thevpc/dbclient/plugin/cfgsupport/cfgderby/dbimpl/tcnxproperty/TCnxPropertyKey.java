package net.thevpc.dbclient.plugin.cfgsupport.cfgderby.dbimpl.tcnxproperty;

/**
 * DO NOT EDIT MANUALLY
 * GENERATED AUTOMATICALLY BY JBGen (0.1)
 *
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @framework neormf (license GPL)
 */
public class TCnxPropertyKey {
    public java.lang.String cprName;
    public int cprSesId;

    /**
     * Constructor
     */
    public TCnxPropertyKey() {
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
            Object p2 = ((TCnxPropertyKey) obj).keyPartAt(i);
            if (!p1.equals(p2)) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("TCnxProperty");
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
    public TCnxPropertyKey(java.lang.String cprName, int cprSesId) {
        this.cprName = cprName;
        this.cprSesId = cprSesId;

    }

    public java.lang.String getCprName() {
        return cprName;
    }

    public int getCprSesId() {
        return cprSesId;
    }

    public Object keyPartAt(int index) {
        switch (index) {
            case 0: {
                return cprName;
            }
            case 1: {
                return new Integer(cprSesId);
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