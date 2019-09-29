package net.vpc.dbclient.api.viewmanager;

import net.vpc.prs.plugin.Extension;
import net.vpc.dbclient.api.sql.DBCExecutionPlan;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)  alias vpc
 * @creationtime 2009/08/14 18:55:03
 */
@Extension(group = "ui.session")
public interface DBCExecutionPlanComponent extends DBCComponent {
    void setExecutionPlan(DBCExecutionPlan plan);

    DBCExecutionPlan getExecutionPlan();
}
