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
package net.vpc.dbclient.plugin.dbsupport.derby;

import net.vpc.dbclient.api.pluginmanager.DBCPlugin;
import net.vpc.prs.plugin.Inject;
import net.vpc.dbclient.api.sql.urlparser.DBCDriverParameter;
import net.vpc.dbclient.api.sql.urlparser.DBCDriverUrlParser;
import net.vpc.dbclient.api.sql.urlparser.URLToken;
import net.vpc.dbclient.api.sql.urlparser.URLTokenizer;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 1 juil. 2007 12:17:48
 */
public class DerbyNetworkDriverUrlParser implements DBCDriverUrlParser {

    @Inject
    private DBCPlugin plugin;

    public DerbyNetworkDriverUrlParser() {
    }

    public int acceptDriver(String driver) {
        return driver.equals("org.apache.derby.jdbc.ClientDriver") ? 10 : -1;
    }

    public Properties parse(String driver, String url) {
        Properties p = new Properties();
        URLTokenizer t = new URLTokenizer(url);
        if (!t.skip("jdbc:derby://")) {
            return p;
        }

        URLToken ut = t.nextToken(true, ":", "/");
        if (ut != null) {
            String val = ut.getValue();
            if (val.endsWith("[")) {
                val = val.substring(0, val.length() - 1);
            }
            if (!("{" + PARAM_SERVER + "}").equals(val)) {
                p.put(PARAM_SERVER, val);
            }
            if (":".equals(ut.getDelimiter())) {
                ut = t.nextToken(true, "/");
                val = ut.getValue();
                if (val.endsWith("]")) {
                    val = val.substring(0, val.length() - 1);
                }
                if (!("{" + PARAM_PORT + "}").equals(val)) {
                    p.put(PARAM_PORT, val);
                }
            }
            ut = t.nextToken(true, ";");
            if (ut != null) {
                val = ut.getValue();
                if (!("{" + PARAM_DATABASE + "}").equals(val)) {
                    p.put(PARAM_DATABASE, val);
                }
            }
            while ((ut = t.nextToken(true, ";")) != null) {
                URLTokenizer t2 = new URLTokenizer(ut.getValue());
                URLToken n = t2.nextToken(true, "=");
                if (n != null) {
                    String v = t2.nextToken();
                    if (v != null) {
                        p.put(n.getValue(), v);
                    }
                }
            }
        }

        return p;
    }

    public String format(String driver, Properties properties) {
        Properties props = (Properties) properties.clone();
        String server = properties.getProperty(DBCDriverUrlParser.PARAM_SERVER);
        String port = properties.getProperty(DBCDriverUrlParser.PARAM_PORT);
        String database = properties.getProperty(DBCDriverUrlParser.PARAM_DATABASE);

        String s = (server.length() > 0 ? server : "localhost");
        String p = (port.length() > 0 ? (":" + port) : "");
        String d = (database.length() > 0 ? ("/" + database) : ("/" + ("{" + PARAM_DATABASE + "}")));
        String r = "jdbc:derby://" + s + p + d;
        props.remove(DBCDriverUrlParser.PARAM_SERVER);
        props.remove(DBCDriverUrlParser.PARAM_PORT);
        props.remove(DBCDriverUrlParser.PARAM_DATABASE);
        props.remove(DBCDriverUrlParser.PARAM_DBFOLDER);
        for (Map.Entry<Object, Object> entry : props.entrySet()) {
            r = r + (";" + entry.getKey() + "=" + entry.getValue());
        }
        return r;
    }

    public Set<DBCDriverParameter> getParameters() {
        LinkedHashSet<DBCDriverParameter> all = new LinkedHashSet<DBCDriverParameter>();
        all.add(new DBCDriverParameter(DBCDriverUrlParser.PARAM_SERVER, null, ".", ParameterType.STRING, null, plugin.getMessageSet()));
        all.add(new DBCDriverParameter(DBCDriverUrlParser.PARAM_PORT, null, ".", ParameterType.INTEGER, null, plugin.getMessageSet()));
        all.add(new DBCDriverParameter(DBCDriverUrlParser.PARAM_CREATE, null, "false", ParameterType.BOOLEAN, null, plugin.getMessageSet()));
        all.add(new DBCDriverParameter(DBCDriverUrlParser.PARAM_DATABASE, null, "NONAME", ParameterType.STRING, null, plugin.getMessageSet()));
        return all;
    }
}