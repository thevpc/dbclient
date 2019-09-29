package net.vpc.dbclient.api.pluginmanager.injector;

import net.vpc.dbclient.api.DBCApplication;
import net.vpc.dbclient.api.DBClientContext;
import net.vpc.prs.plugin.ValueProvider;
import net.vpc.prs.plugin.PluginDescriptor;

/**
* Created by IntelliJ IDEA.
* User: vpc
* Date: 8 janv. 2010
* Time: 16:18:21
* To change this template use File | Settings | File Templates.
*/
public class ApplicationFieldValueProviderDBClientContext implements ValueProvider {
DBCApplication application;

public Class getType() {
    return DBClientContext.class;
}

public ApplicationFieldValueProviderDBClientContext(DBCApplication application) {
    this.application = application;
}

public Object getValue(PluginDescriptor pluginInfo) {
    return application;
}
}