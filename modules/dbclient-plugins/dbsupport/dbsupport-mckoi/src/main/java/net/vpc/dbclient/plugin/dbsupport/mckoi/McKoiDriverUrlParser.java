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
package net.vpc.dbclient.plugin.dbsupport.mckoi;

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
public class McKoiDriverUrlParser implements DBCDriverUrlParser {
    public static final String PARAM_DRIVER_TYPE = "driverType";
    public static final String DRIVER_LOCAL = "local";
    public static final String DRIVER_SERVER = "server";
    @Inject
    private DBCPlugin plugin;

    public McKoiDriverUrlParser() {
    }

    public int acceptDriver(String driver) {
        return driver.equals("com.mckoi.JDBCDriver") ? 10 : -1;
    }

    public Properties parse(String driver, String url) {
        Properties p = new Properties();
        URLTokenizer t = new URLTokenizer(url);
        if (!t.skip("jdbc:mckoi:")) {
            return p;
        }
        String val = null;
        if (t.skip("local:")) {
            //local
            p.put(PARAM_DRIVER_TYPE, DRIVER_LOCAL);
            t.skip("//");
            URLToken ut = t.nextToken(true, "?");
            if (ut != null) {
                int x = ut.getValue().lastIndexOf('/');
                if (x > 0 && ut.getValue().substring(x + 1).indexOf('.') < 0) {
                    val = ut.getValue().substring(0, x);
                    if (!("{" + PARAM_DBFILE + "}").equals(val)) {
                        p.put(PARAM_DBFILE, val);
                    }
                    val = ut.getValue().substring(x + 1);
                    if (!("{" + PARAM_DATABASE + "}").equals(val)) {
                        p.put(PARAM_DATABASE, val);
                    }
                } else {
                    val = ut.getValue();
                    if (!("{" + PARAM_DATABASE + "}").equals(val)) {
                        p.put(PARAM_DBFILE, val);
                    }
                }
                while ((ut = t.nextToken(true, "&")) != null) {
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
        } else {
            p.put(PARAM_DRIVER_TYPE, DRIVER_SERVER);
            URLToken ut = t.nextToken(true, ":", "/");
            if (ut != null) {
                val = ut.getValue();
                if (!("{" + PARAM_SERVER + "}").equals(val)) {
                    p.put(PARAM_SERVER, val);
                }
                if (":".equals(ut.getDelimiter())) {
                    ut = t.nextToken(true, "/");
                    val = ut.getValue();
                    if (!("{" + PARAM_SERVER + "}").equals(val)) {
                        p.put(PARAM_PORT, val);
                    }
                }
                ut = t.nextToken(true, "?");
                if (ut != null) {
                    val = ut.getValue();
                    if (!("{" + PARAM_DATABASE + "}").equals(val)) {
                        p.put(PARAM_DATABASE, val);
                    }
                }
                while ((ut = t.nextToken(true, "&")) != null) {
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
        }


        return p;
    }

    public String format(String driver, Properties properties) {
        Properties props = (Properties) properties.clone();
        String server = properties.getProperty(DBCDriverUrlParser.PARAM_SERVER);
        String port = properties.getProperty(DBCDriverUrlParser.PARAM_PORT);
        String database = properties.getProperty(DBCDriverUrlParser.PARAM_DATABASE);
        String r = null;
        if (DRIVER_LOCAL.equals(properties.getProperty(PARAM_DRIVER_TYPE))) {
            String l = "local:";
            String s = properties.getProperty(PARAM_DBFILE) == null ? "./data.conf" : properties.getProperty(PARAM_DBFILE);
            String d = (database.length() > 0 ? ("/" + database) : "/NONAME");
            r = "jdbc:mckoi:" + l + "//" + s + d;
        } else {
            String s = ((server.length() > 0 ? server : "localhost"));
            String p = (port.length() > 0 ? (":" + port) : "");
            String d = (database.length() > 0 ? ("/" + database) : "/NONAME");
            r = "jdbc:mckoi://" + s + p + d;
        }
        props.remove(DBCDriverUrlParser.PARAM_SERVER);
        props.remove(DBCDriverUrlParser.PARAM_PORT);
        props.remove(DBCDriverUrlParser.PARAM_DATABASE);
        props.remove(DBCDriverUrlParser.PARAM_DBFILE);
        props.remove(PARAM_DRIVER_TYPE);
        boolean first = true;
        for (Map.Entry<Object, Object> entry : props.entrySet()) {
            if (first) {
                first = false;
                r = r + "?";
            } else {
                r = r + "&";
            }
            r = r + (entry.getKey() + "=" + entry.getValue());
        }
        return r;
    }

    public Set<DBCDriverParameter> getParameters() {
        LinkedHashSet<DBCDriverParameter> all = new LinkedHashSet<DBCDriverParameter>();
        all.add(new DBCDriverParameter(PARAM_DRIVER_TYPE, null, DRIVER_LOCAL, ParameterType.ENUM, new String[]{DRIVER_LOCAL, DRIVER_SERVER}, plugin.getMessageSet()));
        all.add(new DBCDriverParameter(DBCDriverUrlParser.PARAM_SERVER, null, ".", ParameterType.STRING, null, plugin.getMessageSet()));
        all.add(new DBCDriverParameter(DBCDriverUrlParser.PARAM_PORT, null, ".", ParameterType.INTEGER, null, plugin.getMessageSet()));
        all.add(new DBCDriverParameter(DBCDriverUrlParser.PARAM_CREATE, null, "false", ParameterType.BOOLEAN, null, plugin.getMessageSet()));
        all.add(new DBCDriverParameter(DBCDriverUrlParser.PARAM_DATABASE, null, "NONAME", ParameterType.STRING, null, plugin.getMessageSet()));
        all.add(new DBCDriverParameter(DBCDriverUrlParser.PARAM_DBFILE, null, "NONAME", ParameterType.FILE, null, plugin.getMessageSet()));
        return all;
    }
}