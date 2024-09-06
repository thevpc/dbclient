package net.thevpc.dbclient.plugin.dbsupport.derby;

import net.thevpc.dbclient.api.sql.DBCExecutionPlan;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)  alias vpc
 * @creationtime 2009/08/14 18:23:26
 */
public class DerbyExecutionPlan implements DBCExecutionPlan {
    private String statistics;

    public DerbyExecutionPlan(String statistics) {
        this.statistics = statistics;
    }

    public String toString() {
        return statistics;
    }
}
