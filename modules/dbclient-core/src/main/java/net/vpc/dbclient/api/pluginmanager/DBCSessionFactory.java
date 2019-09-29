package net.vpc.dbclient.api.pluginmanager;

import net.vpc.dbclient.api.DBCSession;
import net.vpc.prs.plugin.Extension;

@Extension(group = "core")
public interface DBCSessionFactory extends DBCFactory{
    void init(DBCSession session);
}
