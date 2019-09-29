package net.vpc.dbclient.api.pluginmanager;

import net.vpc.dbclient.api.DBCApplication;
import net.vpc.prs.factory.*;
import net.vpc.prs.plugin.PluginDescriptor;


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
