package net.thevpc.dbclient.api.pluginmanager.injector;

import net.thevpc.common.prs.plugin.PluginDescriptor;
import net.thevpc.common.prs.plugin.ValueProvider;
import net.thevpc.dbclient.api.DBCApplication;

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
        return net.thevpc.dbclient.api.pluginmanager.DBCPlugin.class;
    }

    public Object getValue(PluginDescriptor pluginInfo) {
        return application.getPluginManager().getPlugin(pluginInfo.getId());
    }
}
