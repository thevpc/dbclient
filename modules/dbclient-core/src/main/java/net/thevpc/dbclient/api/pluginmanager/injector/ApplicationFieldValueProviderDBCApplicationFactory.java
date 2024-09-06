package net.thevpc.dbclient.api.pluginmanager.injector;

import net.thevpc.common.prs.plugin.PluginDescriptor;
import net.thevpc.common.prs.plugin.ValueProvider;
import net.thevpc.dbclient.api.DBCApplication;
import net.thevpc.dbclient.api.pluginmanager.DBCApplicationFactory;

/**
* Created by IntelliJ IDEA.
* User: vpc
* Date: 8 janv. 2010
* Time: 16:18:21
* To change this template use File | Settings | File Templates.
*/
public class ApplicationFieldValueProviderDBCApplicationFactory implements ValueProvider {
    DBCApplication application;

    public Class getType() {
        return DBCApplicationFactory.class;
    }

    public ApplicationFieldValueProviderDBCApplicationFactory(DBCApplication application) {
        this.application = application;
    }

    public Object getValue(PluginDescriptor pluginInfo) {
        return application.getFactory();
    }
}