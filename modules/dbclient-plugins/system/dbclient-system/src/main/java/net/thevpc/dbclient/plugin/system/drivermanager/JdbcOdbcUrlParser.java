/**
 * ====================================================================
 *             DBClient yet another Jdbc client tool
 *
 * DBClient is a new Open Source Tool for connecting to jdbc
 * compliant relational databases. Specific extensions will take care of
 * each RDBMS implementation.
 *
 * Copyright (C) 2006-2008 Taha BEN SALAH
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 * ====================================================================
 */
package net.thevpc.dbclient.plugin.system.drivermanager;

import net.thevpc.dbclient.api.pluginmanager.DBCPlugin;
import net.thevpc.common.prs.plugin.Inject;
import net.thevpc.dbclient.api.sql.urlparser.DBCDriverParameter;
import net.thevpc.dbclient.api.sql.urlparser.DBCDriverUrlParser;

import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 30 nov. 2008
 */
public class JdbcOdbcUrlParser implements DBCDriverUrlParser {
    @Inject
    private DBCPlugin plugin;

    public JdbcOdbcUrlParser() {
    }

    public int acceptDriver(String driver) {
        return "sun.jdbc.odbc.JdbcOdbcDriver".equals(driver) ? 10 : -1;
    }

    public Set<DBCDriverParameter> getParameters() {
        LinkedHashSet<DBCDriverParameter> all = new LinkedHashSet<DBCDriverParameter>();
        all.add(new DBCDriverParameter(DBCDriverUrlParser.PARAM_DATABASE, null, "odbcsrc", ParameterType.STRING, null, plugin.getMessageSet()));
        return all;
    }

    public Properties parse(String driver, String url) {
        Properties p = new Properties();
        String prefix = "jdbc:odbc:";
        if (url.startsWith(prefix) && url.length() > prefix.length()) {
            String db = url.substring(prefix.length());
            if (!("{" + PARAM_DATABASE + "}").equals(db)) {
                p.put(PARAM_DATABASE, db);
            }
        }
        return p;
    }

    public String format(String driver, Properties properties) {
        String db = properties.getProperty(PARAM_DATABASE);
        if (db == null || db.trim().length() == 0) {
            db = "{" + PARAM_DATABASE + "}";
        }
        return "jdbc:odbc:" + db;
    }
}
