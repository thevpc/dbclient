/**
 *
 ====================================================================
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
 *
 ====================================================================
 */

package net.vpc.dbclient.plugin.dbsupport.oracle;

import net.vpc.dbclient.api.pluginmanager.DBCPlugin;
import net.vpc.prs.plugin.Inject;
import net.vpc.dbclient.api.sql.urlparser.DBCDriverParameter;
import net.vpc.dbclient.api.sql.urlparser.DBCDriverUrlParser;

import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 1 juil. 2007 12:17:48
 */
public class OracleDriverUrlParser implements DBCDriverUrlParser {
    @Inject
    private DBCPlugin plugin;

    public OracleDriverUrlParser() {
    }

    public int acceptDriver(String driver) {
        return driver.equals("oracle.jdbc.driver.OracleDriver") ? 10 : -1;
    }

    public Properties parse(String driver, String url) {
        //    'jdbc:oracle:thin:@{serverAddress}:1521:{databaseName}',
        Properties p = new Properties();
        String[] strings = url.split(":");
        if (strings.length >= 2 && strings[0].equals("jdbc") && strings[1].equals("oracle") && strings[2].equals("thin") && strings[3].startsWith("@")) {
            String serverAndParams = strings[3].substring(1);
            if (!("{" + PARAM_SERVER + "}").equals(serverAndParams)) {
                p.put(DBCDriverUrlParser.PARAM_SERVER, serverAndParams);
            }
            String portStr = "";
            if (strings.length > 4) {
                try {
                    portStr = String.valueOf(Integer.parseInt(strings[4]));
                } catch (NumberFormatException e) {
                    //
                }
            }
            p.put(DBCDriverUrlParser.PARAM_PORT, portStr);
            if (strings.length > 5) {
                if (!("{" + PARAM_DATABASE + "}").equals(strings[5])) {
                    p.put(DBCDriverUrlParser.PARAM_DATABASE, strings[5]);
                }
            }
        }
        return p;
    }

    public String format(String driver, Properties properties) {
        String server = properties.getProperty(DBCDriverUrlParser.PARAM_SERVER);
        String port = properties.getProperty(DBCDriverUrlParser.PARAM_PORT);
        String database = properties.getProperty(DBCDriverUrlParser.PARAM_DATABASE);

        String s = (server.length() > 0 ? server : "localhost");
        String p = (port.length() > 0 ? (port) : "1521");
        String d = (database.length() > 0 ? (database) : "ORCL");
        //    'jdbc:oracle:thin:@{serverAddress}:1521:{databaseName}',
        return "jdbc:oracle:thin:@" + s + ":" + p + ":" + d;
    }

    public Set<DBCDriverParameter> getParameters() {
        LinkedHashSet<DBCDriverParameter> all = new LinkedHashSet<DBCDriverParameter>();
        all.add(new DBCDriverParameter(DBCDriverUrlParser.PARAM_SERVER, null, ".", ParameterType.STRING, null, plugin.getMessageSet()));
        all.add(new DBCDriverParameter(DBCDriverUrlParser.PARAM_PORT, null, ".", ParameterType.INTEGER, null, plugin.getMessageSet()));
        all.add(new DBCDriverParameter(DBCDriverUrlParser.PARAM_DATABASE, null, "master", ParameterType.STRING, null, plugin.getMessageSet()));
        return all;
    }
}