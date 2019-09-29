package net.vpc.dbclient.plugin.dbsupport.derby.features;

import net.vpc.dbclient.api.sql.DBCFeature;
import net.vpc.dbclient.plugin.dbsupport.derby.DerbyConnection;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)  alias vpc
 * @creationtime 2009/08/16 01:00:34
 */
public class DerbyAdminFeature implements DBCFeature {
    private DerbyConnection cnx;

    public DerbyAdminFeature(DerbyConnection cnx) {
        this.cnx = cnx;
    }

    public boolean isSupported() {
        return true;
    }


}