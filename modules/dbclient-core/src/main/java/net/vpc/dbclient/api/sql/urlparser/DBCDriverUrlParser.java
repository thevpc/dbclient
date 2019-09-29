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

package net.vpc.dbclient.api.sql.urlparser;

import net.vpc.prs.plugin.Extension;

import java.util.Properties;
import java.util.Set;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 22 nov. 2006 19:58:46
 */
@Extension(group = "sql")
public interface DBCDriverUrlParser {
    public static final String PARAM_LOGIN = "login";
    public static final String PARAM_PASSWORD = "password";
    public static final String PARAM_SERVER = "server";
    public static final String PARAM_DBFOLDER = "dbfolder";
    public static final String PARAM_DBFILE = "dbfile";
    public static final String PARAM_PORT = "port";
    public static final String PARAM_INSTANCE = "instance";
    public static final String PARAM_DATABASE = "database";
    public static final String PARAM_CREATE = "create";

    public int acceptDriver(String driver);

    public Set<DBCDriverParameter> getParameters();

    public Properties parse(String driver, String url);

    public String format(String driver, Properties properties);

    public static enum ParameterType {
        STRING, INTEGER, ENUM, FOLDER, FILE, BOOLEAN
    }

}
