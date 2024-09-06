package net.thevpc.dbclient.api.sessionmanager;

import net.thevpc.dbclient.api.DBCSession;

/**
 * Created by IntelliJ IDEA.
 * User: vpc
 * Date: 26 août 2009
 * Time: 04:55:16
 * To change this template use File | Settings | File Templates.
 */
public interface DBCSessionFilter {
    public boolean acceptSession(DBCSession session);
}
