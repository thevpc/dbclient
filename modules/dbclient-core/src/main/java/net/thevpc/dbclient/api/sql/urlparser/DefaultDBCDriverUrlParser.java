/**
 * ====================================================================
 *             DBCLient yet another Jdbc client tool
 *
 * DBClient is a new Open Source Tool for connecting to jdbc
 * compliant relational databases. Specific extensions will take care of
 * each RDBMS implementation.
 *
 * Copyright (C) 2006-2007 Taha BEN SALAH
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

package net.thevpc.dbclient.api.sql.urlparser;


import java.util.*;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 22 nov. 2006 20:18:55
 */
public abstract class DefaultDBCDriverUrlParser implements DBCDriverUrlParser {
    private String pattern;
    private Map<String, DBCDriverParameter> parameters;

    public DefaultDBCDriverUrlParser(String pattern, Map<String, DBCDriverParameter> parameters) {
        this.pattern = pattern;
        this.parameters = parameters;
    }

    public int acceptDriver(String driver) {
        return -1;
    }

    public Properties parse(String driver, String url) {
        return null;
    }

    public String format(String driver, Properties properties) {
        return null;
    }

    /**
     * TODO
     *
     * @param url
     * @return
     */
    public Properties parse(String url) {
        return null;
    }

    public String replaceAll(String url, Properties properties) {
        String s = url;
        if (properties != null) {
            for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                String k = (String) entry.getKey();
                String v = (String) entry.getValue();
                s = s.replaceAll("\\{" + k + "\\}", v);
            }
        }
        return s;
    }

    /**
     * TODO
     * enlever les parties non obligatoire (exemple myServer[:{port}] ==> myServer
     */
    public String compile(String url) {
        return null;
    }

    public Set<DBCDriverParameter> getParameters() {
        return new HashSet<DBCDriverParameter>(parameters.values());
    }
}
