package net.vpc.dbclient.api.pluginmanager.injector;

import net.vpc.dbclient.api.DBCSession;
import net.vpc.dbclient.api.pluginmanager.DBCPluginSession;
import net.vpc.prs.plugin.ValueProvider;
import net.vpc.prs.plugin.PluginDescriptor;

public class SessionFieldValueProviderDBCPluginSession implements ValueProvider {
    DBCSession session;

    public Class getType() {
        return DBCPluginSession.class;
    }

    public SessionFieldValueProviderDBCPluginSession(DBCSession session) {
        this.session = session;
    }

    public Object getValue(PluginDescriptor pluginInfo) {
        return session.getPluginSession(pluginInfo.getId());
    }
}
