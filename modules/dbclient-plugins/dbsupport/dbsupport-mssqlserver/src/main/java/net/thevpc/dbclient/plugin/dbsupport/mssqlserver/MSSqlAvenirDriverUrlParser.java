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

package net.thevpc.dbclient.plugin.dbsupport.mssqlserver;

import net.thevpc.dbclient.api.pluginmanager.DBCPlugin;
import net.thevpc.common.prs.plugin.Inject;
import net.thevpc.dbclient.api.sql.urlparser.DBCDriverParameter;
import net.thevpc.dbclient.api.sql.urlparser.DBCDriverUrlParser;

import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 1 juil. 2007 12:17:48
 */
public class MSSqlAvenirDriverUrlParser implements DBCDriverUrlParser {
    @Inject
    private DBCPlugin plugin;

    public MSSqlAvenirDriverUrlParser() {
    }

    public int acceptDriver(String driver) {
        return driver.equals("net.avenir.jdbc2.Driver") ? 10 : -1;
    }

    public Properties parse(String driver, String url) {
        //jdbc:AvenirDriver://{serverAddress}[:{serverPort}]/{databaseName}
        Properties p = new Properties();
        String[] strings = url.split(":");
        if (strings.length >= 2 && strings[0].equals("jdbc") && strings[1].equals("AvenirDriver") && strings[2].startsWith("//")) {
            String serverAndParams = strings[3].substring(2);
            int serverAndParamsCommaPos = serverAndParams.indexOf('/');
            String val = serverAndParamsCommaPos > 0 ? serverAndParams.substring(0, serverAndParamsCommaPos) : serverAndParams;
            if (!("{" + PARAM_SERVER + "}").equals(val)) {
                p.put(DBCDriverUrlParser.PARAM_SERVER, val);
            }
            String portStr = "";
            String params = "";
            if (serverAndParamsCommaPos > 0) {
                params = serverAndParams.substring(serverAndParamsCommaPos + 1);
            }
            if (strings.length > 3) {
                String portAndParams = strings[3];
                int portAndParamsCommaPos = portAndParams.indexOf('/');
                if (portAndParamsCommaPos > 0) {
                    try {
                        portStr = String.valueOf(Integer.parseInt(strings[3].substring(0, portAndParamsCommaPos)));
                    } catch (NumberFormatException e) {
                        //
                    }
                    params = strings[3].substring(portAndParamsCommaPos + 1);
                } else {
                    try {
                        portStr = String.valueOf(Integer.parseInt(strings[3]));
                    } catch (NumberFormatException e) {
                        //
                    }
                }
            }
            if (!("{" + PARAM_PORT + "}").equals(portStr)) {
                p.put(DBCDriverUrlParser.PARAM_PORT, portStr);
            }
            String[] pp = params.split(";");
            for (int i = 0; i < pp.length; i++) {
                String s = pp[i];
                if (i == 0) {
                    if (!("{" + PARAM_DATABASE + "}").equals(s)) {
                        p.put(DBCDriverUrlParser.PARAM_DATABASE, s);
                    }
                } else {
                    String[] pf = s.split("=");
                    if (pf.length == 2) {
                        //other params...
                    }
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
        String p = (port.length() > 0 ? (":" + port) : "");
        String d = (database.length() > 0 ? ("/" + database) : "");
        //jdbc:AvenirDriver://{serverAddress}[:{serverPort}]/{databaseName}
        return "jdbc:AvenirDriver://" + s + p + d;
    }

    public Set<DBCDriverParameter> getParameters() {
        LinkedHashSet<DBCDriverParameter> all = new LinkedHashSet<DBCDriverParameter>();
        all.add(new DBCDriverParameter(DBCDriverUrlParser.PARAM_SERVER, null, ".", ParameterType.STRING, null, plugin.getMessageSet()));
        all.add(new DBCDriverParameter(DBCDriverUrlParser.PARAM_PORT, null, ".", ParameterType.INTEGER, null, plugin.getMessageSet()));
        all.add(new DBCDriverParameter(DBCDriverUrlParser.PARAM_DATABASE, null, "master", ParameterType.STRING, null, plugin.getMessageSet()));
        return all;
    }
}