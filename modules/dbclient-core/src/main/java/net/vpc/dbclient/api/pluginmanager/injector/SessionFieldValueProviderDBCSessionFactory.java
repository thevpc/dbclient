package net.vpc.dbclient.api.pluginmanager.injector;

import net.vpc.dbclient.api.DBCSession;
import net.vpc.dbclient.api.pluginmanager.DBCSessionFactory;
import net.vpc.prs.plugin.ValueProvider;
import net.vpc.prs.plugin.PluginDescriptor;

/**
* Created by IntelliJ IDEA.
* User: vpc
* Date: 8 janv. 2010
* Time: 16:18:21
* To change this template use File | Settings | File Templates.
*/
public class SessionFieldValueProviderDBCSessionFactory implements ValueProvider {
private DBCSession session;

public Class getType() {
    return DBCSessionFactory.class;
}

public SessionFieldValueProviderDBCSessionFactory(DBCSession session) {
    this.session = session;
}

public Object getValue(PluginDescriptor pluginInfo) {
    return session.getFactory();
}
}