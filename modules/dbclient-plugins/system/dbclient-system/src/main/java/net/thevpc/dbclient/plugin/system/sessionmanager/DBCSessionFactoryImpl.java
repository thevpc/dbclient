package net.thevpc.dbclient.plugin.system.sessionmanager;

import net.thevpc.dbclient.api.DBCSession;
import net.thevpc.dbclient.api.pluginmanager.DBCSessionFactory;
import net.thevpc.dbclient.api.sessionmanager.DBCSessionFilter;
import net.thevpc.common.prs.factory.*;
import net.thevpc.common.prs.plugin.PluginDescriptor;

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
