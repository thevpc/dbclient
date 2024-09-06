package net.thevpc.dbclient.plugin.system.viewmanager;

import net.thevpc.dbclient.api.sql.DBCExecutionPlan;
import net.thevpc.dbclient.api.viewmanager.DBCExecutionPlanComponent;
import net.thevpc.dbclient.api.viewmanager.DBCPluggablePanel;

import javax.swing.*;
import java.awt.*;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)  alias vpc
 * @creationtime 2009/08/14 19:50:23
 */
public class DBCExecutionPlanComponentImpl extends DBCPluggablePanel implements DBCExecutionPlanComponent {
    private JTextArea area = new JTextArea();
    private DBCExecutionPlan executionPlan;

    public DBCExecutionPlanComponentImpl() {
        setLayout(new BorderLayout());
        add(new JScrollPane(area));
        area.setEditable(false);
    }

    public Component getComponent() {
        return this;
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
