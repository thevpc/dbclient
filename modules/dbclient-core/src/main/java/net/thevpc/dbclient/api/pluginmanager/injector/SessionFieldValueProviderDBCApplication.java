package net.thevpc.dbclient.api.pluginmanager.injector;

import net.thevpc.dbclient.api.DBCApplication;
import net.thevpc.dbclient.api.DBCSession;
import net.thevpc.common.prs.plugin.ValueProvider;
import net.thevpc.common.prs.plugin.PluginDescriptor;

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
