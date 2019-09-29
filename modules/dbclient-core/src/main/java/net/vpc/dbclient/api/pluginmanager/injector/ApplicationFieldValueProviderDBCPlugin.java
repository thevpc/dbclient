package net.vpc.dbclient.api.pluginmanager.injector;

import net.vpc.dbclient.api.DBCApplication;
import net.vpc.prs.plugin.ValueProvider;
import net.vpc.prs.plugin.PluginDescriptor;

/**
* Created by IntelliJ IDEA.
* User: vpc
* Date: 8 janv. 2010
* Time: 16:18:45
* To change this template use File | Settings | File Templates.
*/
public class ApplicationFieldValueProviderDBCPlugin implements ValueProvider {
    DBCApplication application;

    public ApplicationFieldValueProviderDBCPlugin(DBCApplication application) {
        this.application = application;
    }

    public Class getType() {
        return net.vpc.dbclient.api.pluginmanager.DBCPlugin.class;
    }

    public Object getValue(PluginDescriptor pluginInfo) {
        return application.getPluginManager().getPlugin(pluginInfo.getId());
    }
}
