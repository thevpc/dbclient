package net.vpc.dbclient.api.pluginmanager.injector;

import net.vpc.dbclient.api.DBCApplication;
import net.vpc.dbclient.api.pluginmanager.DBCApplicationFactory;
import net.vpc.prs.plugin.ValueProvider;
import net.vpc.prs.plugin.PluginDescriptor;

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