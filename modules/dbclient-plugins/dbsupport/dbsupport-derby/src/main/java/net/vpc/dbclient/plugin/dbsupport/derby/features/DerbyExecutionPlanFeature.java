package net.vpc.dbclient.plugin.dbsupport.derby.features;

import net.vpc.dbclient.api.sql.DBCExecutionPlan;
import net.vpc.dbclient.api.sql.features.ExecutionPlanFeature;
import net.vpc.dbclient.plugin.dbsupport.derby.DerbyConnection;
import net.vpc.dbclient.plugin.dbsupport.derby.DerbyExecutionPlan;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)  alias vpc
 * @creationtime 2009/08/16 01:00:34
 */
public class DerbyExecutionPlanFeature implements ExecutionPlanFeature {
    private DerbyConnection cnx;

    public DerbyExecutionPlanFeature(DerbyConnection cnx) {
        this.cnx = cnx;
    }

    public boolean isSupported() {
        return true;
    }

    public DBCExecutionPlan getExecutionPlan() throws SQLException {
        PreparedStatement cs = null;
        try {
            cs = cnx.prepareStatement("VALUES SYSCS_UTIL.SYSCS_GET_RUNTIMESTATISTICS()");
            ResultSet rs = cs.executeQuery();
            if (rs.next()) {
                String s = rs.getString(1);
                if (s != null) {
                    return new DerbyExecutionPlan(s);
                }
            }
        } finally {
            if (cs != null) {
                cs.close();
            }
        }
        return null;
    }

    public void setExecutionPlanEnabled(boolean enable) throws SQLException {
        DerbySYSCSUTILFeature SYSCSUTIL = cnx.getFeatureSYSCSUTIL();
        if (enable) {
            SYSCSUTIL.SYSCS_SET_RUNTIMESTATISTICS(true);
            SYSCSUTIL.SYSCS_SET_STATISTICS_TIMING(true);
        } else {
            //order is inverted
            SYSCSUTIL.SYSCS_SET_STATISTICS_TIMING(false);
            SYSCSUTIL.SYSCS_SET_RUNTIMESTATISTICS(true);
        }
    }

}
