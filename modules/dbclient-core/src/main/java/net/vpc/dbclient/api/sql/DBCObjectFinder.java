package net.vpc.dbclient.api.sql;

import net.vpc.dbclient.api.pluginmanager.DBCPluggable;
import net.vpc.prs.plugin.Extension;
import net.vpc.dbclient.api.sql.objects.DBObject;
import net.vpc.reflect.ClassFilter;

import java.sql.SQLException;

@Extension(group = "sql")
public interface DBCObjectFinder extends DBCPluggable {
    public void init(DBCConnection connection) throws SQLException;

    public DBObject[] find(String catalogName, String schemaName, String parentName, String name, DBObject[] contextParents, ClassFilter nodeClassFilter, FindMonitor findMonitor) throws SQLException;
}
