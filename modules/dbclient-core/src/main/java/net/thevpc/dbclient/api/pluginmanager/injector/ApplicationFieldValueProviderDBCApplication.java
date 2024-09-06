package net.thevpc.dbclient.api.pluginmanager.injector;

import net.thevpc.common.prs.plugin.PluginDescriptor;
import net.thevpc.common.prs.plugin.ValueProvider;
import net.thevpc.dbclient.api.DBCApplication;

/**
* Created by IntelliJ IDEA.
* User: vpc
* Date: 8 janv. 2010
* Time: 16:18:21
* To change this template use File | Settings | File Templates.
*/
public class ApplicationFieldValueProviderDBCApplication implements ValueProvider {
    private DBCApplication application;

    public Class getType() {
        return DBCApplication.class;
    }

    public ApplicationFieldValueProviderDBCApplication(DBCApplication application) {
        this.application = application;
    }

    public Object getValue(PluginDescriptor pluginInfo) {
        return application;
    }
}
