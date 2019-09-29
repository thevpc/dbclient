package net.vpc.dbclient.plugin.sql.jstsql;

import net.vpc.dbclient.api.DBCSession;
import net.vpc.dbclient.api.pluginmanager.DBCAbstractPluggable;
import net.vpc.dbclient.api.pluginmanager.DBCPluginSession;
import net.vpc.prs.plugin.Inject;
import net.vpc.dbclient.api.sessionmanager.DBCSQLCommandFilter;
import net.vpc.dbclient.api.sql.util.ReaderProvider;
import net.vpc.dbclient.plugin.sql.jstsql.evaluator.JSTSqlEvalContext;
import net.vpc.dbclient.plugin.sql.jstsql.evaluator.JSTSqlEvaluator;
import net.vpc.dbclient.plugin.sql.jstsql.evaluator.JSTSqlSessionEvalContext;

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
