package net.thevpc.dbclient.api.pluginmanager.injector;

import net.thevpc.dbclient.api.DBCSession;
import net.thevpc.dbclient.api.pluginmanager.DBCPlugin;
import net.thevpc.common.prs.plugin.ValueProvider;
import net.thevpc.common.prs.plugin.PluginDescriptor;

/**
* Created by IntelliJ IDEA.
* User: vpc
* Date: 8 janv. 2010
* Time: 16:20:18
* To change this template use File | Settings | File Templates.
*/
public class SessionFieldValueProviderDBCPlugin implements ValueProvider {
    DBCSession session;

    public SessionFieldValueProviderDBCPlugin(DBCSession session) {
        this.session = session;
    }

    public Class getType() {
        return DBCPlugin.class;
    }

    public Object getValue(PluginDescriptor pluginInfo) {
        return session.getApplication().getPluginManager().getPlugin(pluginInfo.getId());
    }
}
