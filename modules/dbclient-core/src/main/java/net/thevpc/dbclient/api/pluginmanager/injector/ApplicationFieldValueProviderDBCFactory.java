package net.thevpc.dbclient.api.pluginmanager.injector;

import net.thevpc.dbclient.api.DBCApplication;
import net.thevpc.dbclient.api.pluginmanager.DBCFactory;
import net.thevpc.common.prs.plugin.ValueProvider;
import net.thevpc.common.prs.plugin.PluginDescriptor;

/**
* Created by IntelliJ IDEA.
* User: vpc
* Date: 8 janv. 2010
* Time: 16:18:21
* To change this template use File | Settings | File Templates.
*/
public class ApplicationFieldValueProviderDBCFactory implements ValueProvider {
DBCApplication application;

public Class getType() {
    return DBCFactory.class;
}

public ApplicationFieldValueProviderDBCFactory(DBCApplication application) {
    this.application = application;
}

public Object getValue(PluginDescriptor pluginInfo) {
    return application.getFactory();
}
}