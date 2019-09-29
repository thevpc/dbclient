package net.vpc.dbclient.plugin.cfgsupport.cfgderby.dbimpl.tuinodeproperty;

/**
 * DO NOT EDIT MANUALLY
 * GENERATED AUTOMATICALLY BY JBGen (0.1)
 *
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @framework neormf (license GPL)
 */
public class TUiNodePropertyKey {
    public java.lang.String unpCode;
    public java.lang.String unpPath;
    public int unpSesId;

    /**
     * Constructor
     */
    public TUiNodePropertyKey() {
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
            Object p2 = ((TUiNodePropertyKey) obj).keyPartAt(i);
            if (!p1.equals(p2)) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("TUiNodeProperty");
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
    public TUiNodePropertyKey(java.lang.String unpCode, java.lang.String unpPath, int unpSesId) {
        this.unpCode = unpCode;
        this.unpPath = unpPath;
        this.unpSesId = unpSesId;

    }

    public java.lang.String getUnpCode() {
        return unpCode;
    }

    public java.lang.String getUnpPath() {
        return unpPath;
    }

    public int getUnpSesId() {
        return unpSesId;
    }

    public Object keyPartAt(int index) {
        switch (index) {
            case 0: {
                return unpCode;
            }
            case 1: {
                return unpPath;
            }
            case 2: {
                return new Integer(unpSesId);
            }
            default: {
                throw new ArrayIndexOutOfBoundsException(index);
            }
        }
    }

    public int keySize() {
        return 3;
    }

}