package net.thevpc.dbclient.api.pluginmanager;

import net.thevpc.common.prs.factory.FactoryImpl;
import net.thevpc.common.prs.factory.FactoryOwnerFilter;
import net.thevpc.common.prs.plugin.PluginDescriptor;
import net.thevpc.dbclient.api.DBCApplication;


public class DBCApplicationFactory extends FactoryImpl implements DBCFactory{
    private DBCApplication application;
    public DBCApplicationFactory() {
        super(null);
        setOwnerFilter(new FactoryOwnerFilter() {
            public boolean acceptOwner(Object owner) {
                PluginDescriptor pi=(PluginDescriptor) owner;
                if(pi==null || application.getPluginManager().getPlugin(pi.getId()).isEnabled()){
                    return true;
                }
                return false;
            }
        });
    }

    public void setApplication(DBCApplication application) {
        this.application = application;
    }
}
