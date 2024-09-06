package net.thevpc.dbclient.plugin.sql.jstsql;

import net.thevpc.dbclient.api.DBCSession;
import net.thevpc.dbclient.api.pluginmanager.DBCAbstractPluggable;
import net.thevpc.dbclient.api.pluginmanager.DBCPluginSession;
import net.thevpc.common.prs.plugin.Inject;
import net.thevpc.dbclient.api.sessionmanager.DBCSQLCommandFilter;
import net.thevpc.dbclient.api.sql.util.ReaderProvider;
import net.thevpc.dbclient.plugin.sql.jstsql.evaluator.JSTSqlEvalContext;
import net.thevpc.dbclient.plugin.sql.jstsql.evaluator.JSTSqlEvaluator;
import net.thevpc.dbclient.plugin.sql.jstsql.evaluator.JSTSqlSessionEvalContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;

public class JSTSqlSQLCommandFilter extends DBCAbstractPluggable implements DBCSQLCommandFilter {
    @Inject
    DBCPluginSession pluginSession;

    public JSTSqlSQLCommandFilter() {
    }

    public ReaderProvider filter(ReaderProvider provider, DBCSession session) throws SQLException, IOException {
        Reader r = provider.getReader();
        BufferedReader br = r instanceof BufferedReader ? (BufferedReader) r : new BufferedReader(r);
        String line = null;
        boolean compilable = false;
        while ((line = br.readLine()) != null) {
            if (line.indexOf("<%") >= 0) {
                compilable = true;
                break;
            }
        }
        if (!compilable) {
            return provider;
        }
        JSTSqlEvalContext context = new JSTSqlSessionEvalContext(session, session.getView().getExplorer().getCurrentNode());
        return new JSTSqlReaderProvider(provider, JSTSqlPlugin.resolveSqlEvaluator(pluginSession), context);
    }

}
