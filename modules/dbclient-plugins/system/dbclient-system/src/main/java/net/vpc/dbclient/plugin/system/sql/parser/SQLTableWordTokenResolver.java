package net.vpc.dbclient.plugin.system.sql.parser;

import java.util.logging.Level;
import net.vpc.dbclient.api.sql.parser.SQLParser;
import net.vpc.dbclient.api.sql.parser.SQLTokenGroup;
import net.vpc.dbclient.api.sql.parser.TokenRichType;
import net.vpc.dbclient.api.sql.parser.WordTokenResolver;
import net.vpc.util.Chronometer;

import java.io.IOException;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeSet;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)  alias vpc
 * @creationtime 2009/08/12 17:55:00
 */
public class SQLTableWordTokenResolver implements WordTokenResolver {
    private static TokenRichType TABLE = TokenRichType.valueOf(SQLTokenGroup.TABLE);

    public SQLTableWordTokenResolver() {
    }

    public int getGroup() {
        return SQLTokenGroup.TABLE;
    }

    private TreeSet<String> loaded = new TreeSet<String>();
    private long lastLoaded = 0;
    private static final long DELTA = 60000;

    public TokenRichType resolve(String word, int supposedType, SQLParser parser) throws IOException {
        String lower = word.toLowerCase();
        long now = System.currentTimeMillis();
        if (now - lastLoaded > DELTA) {
            lastLoaded = now;
            Chronometer chronometer = new Chronometer();
            chronometer.start();
            parser.getConnection().getSession().getLogger(SQLTableWordTokenResolver.class.getName()).log(Level.FINER, "[SQLTableWordTokenResolver] start resolve : {0}", word);
            try {
                ResultSet rs = null;
                try {
                    DatabaseMetaData metadata = parser.getConnection().getMetaData();
                    rs = metadata.getTables(null, null, null, null);
                    while (rs.next()) {
                        String x = rs.getString("TABLE_NAME");
                        if (x != null && x.length() > 0) {
                            String lc = x.toLowerCase();
                            loaded.add(lc);
                        }
                    }
                } finally {
                    if (rs != null) {
                        rs.close();
                    }
                }
            } catch (SQLException e) {
                parser.getConnection().getSession().getLogger(DefaultSQLParser.class.getName()).log(java.util.logging.Level.SEVERE, null, e);
            }
            chronometer.stop();
            parser.getConnection().getSession().getLogger(SQLTableWordTokenResolver.class.getName()).log(Level.FINER, "end in {0}", chronometer);
        }

        if (loaded.contains(lower)) {
            return TABLE;
        }
        return null;
    }
}