package net.thevpc.dbclient.api.sql.features;

import net.thevpc.dbclient.api.sql.DBCExecutionPlan;
import net.thevpc.dbclient.api.sql.DBCFeature;

import java.sql.SQLException;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)  alias vpc
 * @creationtime 2009/08/16 00:56:54
 */
public interface ExecutionPlanFeature extends DBCFeature{
    public static final ExecutionPlanFeature UNSUPPORTED=new ExecutionPlanFeature() {
        public DBCExecutionPlan getExecutionPlan() throws SQLException {
            throw new IllegalArgumentException("Unsupported");
        }

        public void setExecutionPlanEnabled(boolean enable) throws SQLException {
            throw new IllegalArgumentException("Unsupported");
        }

        public boolean isSupported() {
            return false;
        }
    };

    public boolean isSupported();
    public DBCExecutionPlan getExecutionPlan() throws SQLException;
    public void setExecutionPlanEnabled(boolean enable) throws SQLException;
}
