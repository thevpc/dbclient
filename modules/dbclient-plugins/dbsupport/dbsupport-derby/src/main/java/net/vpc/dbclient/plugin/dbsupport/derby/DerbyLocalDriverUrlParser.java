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
import net.vpc.util.StringUtils;

import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 1 juil. 2007 12:17:48
 */
public class DerbyLocalDriverUrlParser implements DBCDriverUrlParser {
    @Inject
    private DBCPlugin plugin;

    public DerbyLocalDriverUrlParser() {
    }

    public int acceptDriver(String driver) {
        return driver.equals("org.apache.derby.jdbc.EmbeddedDriver") ? 10 : -1;
    }

    public Properties parse(String driver, String url) {
        Properties p = new Properties();
        String prefix = "jdbc:derby:";
        if (url.startsWith(prefix)) {
            int i = url.indexOf(';', prefix.length());
            String dbfolderValue = i >= 0 ? StringUtils.substring(url, prefix.length(), i) : StringUtils.substring(url, prefix.length(), url.length());
            if (!("{" + DBCDriverUrlParser.PARAM_DBFOLDER + "}").equals(dbfolderValue)) {
                p.put(DBCDriverUrlParser.PARAM_DBFOLDER, dbfolderValue);
            }
            String params = i < 0 ? "" : StringUtils.substring(url, i, url.length());
            for (String paramVal : params.split(";")) {
                String[] keyVal = paramVal.split("=");
                if (keyVal.length == 2) {
                    String k = keyVal[0];
                    String v = keyVal[1];
                    if ("create".equalsIgnoreCase(k)) {
                        p.put(DBCDriverUrlParser.PARAM_CREATE, v);
                    }
                }
            }
        }
        return p;
    }

    public String format(String driver, Properties properties) {
        String folder = properties.getProperty(DBCDriverUrlParser.PARAM_DBFOLDER);
        String create = properties.getProperty(DBCDriverUrlParser.PARAM_CREATE);
        folder = ((folder == null || folder.length() == 0) ? ("{" + DBCDriverUrlParser.PARAM_DBFOLDER + "}") : folder);
        create = ((create == null || create.length() == 0) ? "create" : (";create=" + create));
        return "jdbc:derby:" + folder + create;
    }

    public Set<DBCDriverParameter> getParameters() {
        LinkedHashSet<DBCDriverParameter> all = new LinkedHashSet<DBCDriverParameter>();
        all.add(new DBCDriverParameter(DBCDriverUrlParser.PARAM_DBFOLDER, null, ".", ParameterType.FOLDER, null, plugin.getMessageSet()));
        all.add(new DBCDriverParameter(DBCDriverUrlParser.PARAM_CREATE, null, "false", ParameterType.BOOLEAN, null, plugin.getMessageSet()));
        return all;
    }
}