package net.thevpc.dbclient.api.pluginmanager;

import net.thevpc.dbclient.api.DBCSession;
import net.thevpc.common.prs.plugin.Extension;

@Extension(group = "core")
public interface DBCSessionFactory extends DBCFactory{
    void init(DBCSession session);
}
