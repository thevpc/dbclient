package net.vpc.dbclient.plugin.dbsupport.derby;

import net.vpc.dbclient.api.DBCSession;
import net.vpc.dbclient.api.sessionmanager.DBCSessionFilter;
import net.vpc.dbclient.api.sql.DBCExecutionPlan;
import net.vpc.dbclient.api.viewmanager.DBCExecutionPlanComponent;
import net.vpc.dbclient.api.viewmanager.DBCPluggablePanel;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)  alias vpc
 * @creationtime 2009/08/14 19:46:34
 */
public class DerbyExecutionPlanComponent extends DBCPluggablePanel implements DBCExecutionPlanComponent, DBCSessionFilter {
    private JTextArea area = new JTextArea();
    private DBCExecutionPlan executionPlan;

    public DerbyExecutionPlanComponent() {
        setLayout(new BorderLayout());
        add(new JScrollPane(area));
    }

    public Component getComponent() {
        return this;
    }

    public boolean acceptSession(DBCSession session) {
        try {
            return session.getConnection() instanceof DerbyConnection;
        } catch (SQLException e) {
            return false;
        }
    }

    public void setExecutionPlan(DBCExecutionPlan plan) {
        this.executionPlan = plan;
        if (plan != null) {
            area.setText(plan.toString());
        } else {
            area.setText(null);
        }
    }

    public DBCExecutionPlan getExecutionPlan() {
        return executionPlan;
    }

}
