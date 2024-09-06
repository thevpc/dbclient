package net.thevpc.dbclient.api.sql;

import net.thevpc.dbclient.api.pluginmanager.DBCPluggable;
import net.thevpc.common.prs.plugin.Extension;
import net.thevpc.dbclient.api.sql.objects.DBObject;
import net.thevpc.common.prs.reflect.ClassFilter;

import java.sql.SQLException;

@Extension(group = "sql")
public interface DBCObjectFinder extends DBCPluggable {
    public void init(DBCConnection connection) throws SQLException;

    public DBObject[] find(String catalogName, String schemaName, String parentName, String name, DBObject[] contextParents, ClassFilter nodeClassFilter, FindMonitor findMonitor) throws SQLException;
}
