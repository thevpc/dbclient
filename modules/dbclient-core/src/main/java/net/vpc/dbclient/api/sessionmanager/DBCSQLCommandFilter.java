package net.vpc.dbclient.api.sessionmanager;

import net.vpc.dbclient.api.DBCSession;
import net.vpc.dbclient.api.pluginmanager.DBCAbstractPluggable;
import net.vpc.dbclient.api.pluginmanager.DBCPluggable;
import net.vpc.prs.plugin.Extension;
import net.vpc.dbclient.api.sql.util.ReaderProvider;

import java.io.IOException;
import java.sql.SQLException;

@Extension(group = "sql")
public interface DBCSQLCommandFilter extends DBCPluggable {
    public static final DBCSQLCommandFilter NONE = new DBCSQLCommandFilterNone();

    public static final class DBCSQLCommandFilterNone extends DBCAbstractPluggable implements DBCSQLCommandFilter{
        public DBCSQLCommandFilterNone() {
        }

        public ReaderProvider filter(ReaderProvider provider, DBCSession session) {
            return provider;
        }
    }

    ReaderProvider filter(ReaderProvider provider, DBCSession session) throws SQLException, IOException;
}
