package net.thevpc.dbclient.api.pluginmanager.injector;

import net.thevpc.dbclient.api.DBCSession;
import net.thevpc.dbclient.api.pluginmanager.DBCPluginSession;
import net.thevpc.common.prs.plugin.ValueProvider;
import net.thevpc.common.prs.plugin.PluginDescriptor;

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
