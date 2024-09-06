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
package net.thevpc.dbclient.plugin.dbsupport.hsql;

import net.thevpc.dbclient.api.pluginmanager.DBCPlugin;
import net.thevpc.common.prs.plugin.Inject;
import net.thevpc.dbclient.api.sql.urlparser.DBCDriverParameter;
import net.thevpc.dbclient.api.sql.urlparser.DBCDriverUrlParser;
import net.thevpc.dbclient.api.sql.urlparser.URLToken;
import net.thevpc.dbclient.api.sql.urlparser.URLTokenizer;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 1 juil. 2007 12:17:48
 */
public class HSqlNetworkDriverUrlParser implements DBCDriverUrlParser {

    public static final String PARAM_GET_COLUMN_NAME = "get_column_name";
    public static final String PARAM_IFEXISTS = "ifexists";
    public static final String PARAM_SHUTDOWN = "shutdown";
    public static final String PARAM_DRIVER_TYPE = "PARAM_DRIVER_TYPE";
    public static final String VALUE_PARAM_DRIVER_TYPE_HSQL = "hsql";
    public static final String VALUE_PARAM_DRIVER_TYPE_HSQLS = "hsqls";
    public static final String VALUE_PARAM_DRIVER_TYPE_HTTP = "http";
    public static final String VALUE_PARAM_DRIVER_TYPE_HTTPS = "https";
    public static final String VALUE_PARAM_DRIVER_TYPE_MEM = "mem";
    public static final String VALUE_PARAM_DRIVER_TYPE_FILE = "file";
    @Inject
    private DBCPlugin plugin;

    public HSqlNetworkDriverUrlParser() {
    }

    public int acceptDriver(String driver) {
        return driver.equals("org.hsqldb.jdbcDriver") ? 10 : -1;
    }

    public Properties parse(String driver, String url) {
        Properties p = new Properties();
        URLTokenizer t = new URLTokenizer(url);
        if (!t.skip("jdbc:hsqldb:")) {
            return p;
        }
        String val = null;
        if (t.skip(VALUE_PARAM_DRIVER_TYPE_HSQL)) {
            p.put(PARAM_DRIVER_TYPE, VALUE_PARAM_DRIVER_TYPE_HSQL);
            t.skipChars("://");
            URLToken ut = t.nextToken(true, ":", "/");
            if (ut != null) {
                val = ut.getValue();
                if (!("{" + PARAM_SERVER + "}").equals(val)) {
                    p.put(PARAM_SERVER, val);
                }
                if (":".equals(ut.getDelimiter())) {
                    ut = t.nextToken(true, "/");
                    val = ut.getValue();
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
        } else if (t.skip(VALUE_PARAM_DRIVER_TYPE_HSQLS)) {
            p.put(PARAM_DRIVER_TYPE, VALUE_PARAM_DRIVER_TYPE_HSQLS);
            t.skipChars("://");
            URLToken ut = t.nextToken(true, ":", "/");
            if (ut != null) {
                val = ut.getValue();
                if (!("{" + PARAM_SERVER + "}").equals(val)) {
                    p.put(PARAM_SERVER, val);
                }
                if (":".equals(ut.getDelimiter())) {
                    ut = t.nextToken(true, "/");
                    val = ut.getValue();
                    if (!("{" + PARAM_PORT + "}").equals(val)) {
                        p.put(PARAM_PORT, val);
                    }
                }
                ut = t.nextToken(true, ";");
                if (ut != null) {
                    val = ut.getValue();
                    if (!("{" + PARAM_SERVER + "}").equals(val)) {
                        p.put(PARAM_SERVER, val);
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

        } else if (t.skip(VALUE_PARAM_DRIVER_TYPE_HTTP)) {
            p.put(PARAM_DRIVER_TYPE, VALUE_PARAM_DRIVER_TYPE_HTTP);
            t.skipChars("://");
            URLToken ut = t.nextToken(true, ":", "/");
            if (ut != null) {
                val = ut.getValue();
                if (!("{" + PARAM_SERVER + "}").equals(val)) {
                    p.put(PARAM_SERVER, val);
                }
                if (":".equals(ut.getDelimiter())) {
                    ut = t.nextToken(true, "/");
                    val = ut.getValue();
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

        } else if (t.skip(VALUE_PARAM_DRIVER_TYPE_HTTPS)) {
            p.put(PARAM_DRIVER_TYPE, VALUE_PARAM_DRIVER_TYPE_HTTPS);
            t.skipChars("://");
            URLToken ut = t.nextToken(true, ":", "/");
            if (ut != null) {
                val = ut.getValue();
                if (!("{" + PARAM_SERVER + "}").equals(val)) {
                    p.put(PARAM_SERVER, val);
                }
                if (":".equals(ut.getDelimiter())) {
                    ut = t.nextToken(true, "/");
                    val = ut.getValue();
                    if (!("{" + PARAM_PORT + "}").equals(val)) {
                        p.put(PARAM_PORT, val);
                    }
                }
                ut = t.nextToken(true, ";");
                if (ut != null) {
                    val = ut.getValue();
                    if (!("{" + PARAM_SERVER + "}").equals(val)) {
                        p.put(PARAM_SERVER, ut.getValue());
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

        } else if (t.skip(VALUE_PARAM_DRIVER_TYPE_MEM + ":")) {
            p.put(PARAM_DRIVER_TYPE, VALUE_PARAM_DRIVER_TYPE_MEM);
            URLToken ut = t.nextToken(true, ";");
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

        } else if (t.skip(VALUE_PARAM_DRIVER_TYPE_FILE + ":")) {
            p.put(PARAM_DRIVER_TYPE, VALUE_PARAM_DRIVER_TYPE_FILE);
            URLToken ut = t.nextToken(true, ";");
            if (ut != null) {
                val = ut.getValue();
                if (!("{" + PARAM_DBFOLDER + "}").equals(val)) {
                    p.put(PARAM_DBFOLDER, val);
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
        String driverType = properties.getProperty(PARAM_DRIVER_TYPE);
        String server = properties.getProperty(DBCDriverUrlParser.PARAM_SERVER);
        String port = properties.getProperty(DBCDriverUrlParser.PARAM_PORT);
        String r = null;
        if (VALUE_PARAM_DRIVER_TYPE_FILE.equals(driverType)) {
            String database = properties.getProperty(DBCDriverUrlParser.PARAM_DBFOLDER);
            String d = (database.length() > 0 ? (database) : "NONAME");
            r = "jdbc:hsqldb:file:" + d;
        } else if (VALUE_PARAM_DRIVER_TYPE_MEM.equals(driverType)) {
            String database = properties.getProperty(DBCDriverUrlParser.PARAM_DATABASE);
            String d = (database.length() > 0 ? (database) : "NONAME");
            r = "jdbc:hsqldb:mem:" + d;
        } else {
            String database = properties.getProperty(DBCDriverUrlParser.PARAM_DATABASE);
            String s = (server.length() > 0 ? server : "localhost");
            String p = (port.length() > 0 ? (":" + port) : "");
            String d = (database.length() > 0 ? ("/" + database) : "/");
            r = "jdbc:hsqldb:" + properties.getProperty(PARAM_DRIVER_TYPE) + "://" + s + p + d;
        }
        props.remove(PARAM_DRIVER_TYPE);
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
        all.add(new DBCDriverParameter(DBCDriverUrlParser.PARAM_DATABASE, null, "NONAME", ParameterType.STRING, null, plugin.getMessageSet()));
        all.add(new DBCDriverParameter(DBCDriverUrlParser.PARAM_DBFOLDER, null, "NONAME", ParameterType.FOLDER, null, plugin.getMessageSet()));
        all.add(new DBCDriverParameter(PARAM_GET_COLUMN_NAME, null, null, ParameterType.BOOLEAN, null, plugin.getMessageSet()));
        all.add(new DBCDriverParameter(PARAM_IFEXISTS, null, null, ParameterType.BOOLEAN, null, plugin.getMessageSet()));
        all.add(new DBCDriverParameter(PARAM_SHUTDOWN, null, null, ParameterType.BOOLEAN, null, plugin.getMessageSet()));
        all.add(new DBCDriverParameter(PARAM_CREATE, null, null, ParameterType.BOOLEAN, null, plugin.getMessageSet()));
        all.add(new DBCDriverParameter(PARAM_DRIVER_TYPE, null, "mem", ParameterType.ENUM, new String[]{VALUE_PARAM_DRIVER_TYPE_MEM, VALUE_PARAM_DRIVER_TYPE_FILE, VALUE_PARAM_DRIVER_TYPE_HSQL, VALUE_PARAM_DRIVER_TYPE_HSQLS, VALUE_PARAM_DRIVER_TYPE_HTTP, VALUE_PARAM_DRIVER_TYPE_HTTPS}, plugin.getMessageSet()));
        return all;
    }
}