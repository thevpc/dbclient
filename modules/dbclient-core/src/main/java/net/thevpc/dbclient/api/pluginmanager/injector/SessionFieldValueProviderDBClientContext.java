package net.thevpc.dbclient.api.pluginmanager.injector;

import net.thevpc.dbclient.api.DBCSession;
import net.thevpc.dbclient.api.DBClientContext;
import net.thevpc.common.prs.plugin.ValueProvider;
import net.thevpc.common.prs.plugin.PluginDescriptor;

/**
* Created by IntelliJ IDEA.
* User: vpc
* Date: 8 janv. 2010
* Time: 16:19:16
* To change this template use File | Settings | File Templates.
*/
public class SessionFieldValueProviderDBClientContext implements ValueProvider {
DBCSession session;

public SessionFieldValueProviderDBClientContext(DBCSession session) {
    this.session = session;
}

public Class getType() {
    return DBClientContext.class;
}

public Object getValue(PluginDescriptor pluginInfo) {
    return session;
}
}