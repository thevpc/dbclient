package net.vpc.dbclient.plugin.system.sessionmanager;

import net.vpc.dbclient.api.DBCSession;
import net.vpc.dbclient.api.pluginmanager.DBCSessionFactory;
import net.vpc.dbclient.api.sessionmanager.DBCSessionFilter;
import net.vpc.prs.factory.*;
import net.vpc.prs.plugin.PluginDescriptor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;


public class DBCSessionFactoryImpl extends FactoryImpl implements DBCSessionFactory {
    private DBCSession session;

    public DBCSessionFactoryImpl() {
        super(null);
        addFactoryVetoListener(new FactoryVetoListener() {
            public void instanceCreated(FactoryEvent event) throws FactoryVetoException {
                Object newValue = event.getNewValue();
                if (newValue instanceof DBCSessionFilter) {
                    DBCSessionFilter dcf = (DBCSessionFilter) newValue;
                    if (!dcf.acceptSession(session)) {
                        throw new FactoryVetoException();
                    }
                }
            }
        });
        setOwnerFilter(new FactoryOwnerFilter() {
            public boolean acceptOwner(Object owner) {
                PluginDescriptor pi = (PluginDescriptor) owner;
                if (pi == null ||
                        (session.getPluginSession(pi.getId()) != null
                                && session.isPluginEnabled(pi.getId()))) {
                    return true;
                }
                return false;
            }
        });
    }

    public void init(DBCSession session) {
        this.session = session;
    }
}
