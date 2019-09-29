package net.vpc.dbclient.api.pluginmanager.injector;

import net.vpc.dbclient.api.DBCApplication;
import net.vpc.dbclient.api.DBCSession;
import net.vpc.prs.plugin.ValueProvider;
import net.vpc.prs.plugin.PluginDescriptor;

public class SessionFieldValueProviderDBCApplication implements ValueProvider {
    private DBCSession session;

    public SessionFieldValueProviderDBCApplication(DBCSession session) {
        this.session = session;
    }

    public Class getType() {
        return DBCApplication.class;
    }

    public Object getValue(PluginDescriptor pluginInfo) {
        return session.getApplication();
    }
}
