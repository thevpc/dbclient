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

package net.vpc.dbclient.plugin.dbsupport.mssqlserver;

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
public class MSSqlSequelinkDriverUrlParser implements DBCDriverUrlParser {
    @Inject
    private DBCPlugin plugin;

    public MSSqlSequelinkDriverUrlParser() {
    }

    public int acceptDriver(String driver) {
        return driver.equals("com.merant.sequelink.jdbc.SequeLinkDriver") ? 10 : -1;
    }

    public Properties parse(String driver, String url) {
        Properties p = new Properties();
        String[] strings = url.split(":");
        if (strings.length >= 3 && strings[0].equals("jdbc") && strings[1].equals("sequelink") && strings[2].startsWith("//")) {
            String val = strings[2].substring(2);
            if (!("{" + PARAM_SERVER + "}").equals(val)) {
                p.put(DBCDriverUrlParser.PARAM_SERVER, val);
            }
            String portStr = "";
            if (strings.length > 3) {
                try {
                    portStr = String.valueOf(Integer.parseInt(strings[3]));
                } catch (NumberFormatException e) {
                    //
                }
            }
            if (!("{" + PARAM_PORT + "}").equals(portStr)) {
                p.put(DBCDriverUrlParser.PARAM_PORT, portStr);
            }
            String params = (portStr.length() > 0 && strings.length > 4) ? strings[4] : "";
            if (params.startsWith("/")) {
                String[] pp = params.split(";");
                for (String s : pp) {
                    String[] pf = s.split("=");
                    if (pf.length == 2) {
                        if (pf[0].equalsIgnoreCase("databaseName")) {
                            if (!("{" + PARAM_DATABASE + "}").equals(pf[1])) {
                                p.put(DBCDriverUrlParser.PARAM_DATABASE, pf[1]);
                            }
                        }
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
        String d = (database.length() > 0 ? (":/databaseName=" + database) : "");
        return "jdbc:sequelink://" + s + p + d;
    }

    public Set<DBCDriverParameter> getParameters() {
        LinkedHashSet<DBCDriverParameter> all = new LinkedHashSet<DBCDriverParameter>();
        all.add(new DBCDriverParameter(DBCDriverUrlParser.PARAM_SERVER, null, ".", ParameterType.STRING, null, plugin.getMessageSet()));
        all.add(new DBCDriverParameter(DBCDriverUrlParser.PARAM_PORT, null, ".", ParameterType.INTEGER, null, plugin.getMessageSet()));
        all.add(new DBCDriverParameter(DBCDriverUrlParser.PARAM_DATABASE, null, "master", ParameterType.STRING, null, plugin.getMessageSet()));
        return all;
    }
}