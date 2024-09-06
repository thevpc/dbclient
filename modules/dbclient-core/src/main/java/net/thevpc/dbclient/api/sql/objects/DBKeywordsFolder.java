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

package net.thevpc.dbclient.api.sql.objects;

import net.thevpc.common.prs.plugin.Extension;
import net.thevpc.dbclient.api.sql.DBCConnection;


/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 13 juil. 2005 14:32:11
 */
@Extension(group = "sql")
public interface DBKeywordsFolder extends DBObjectFolder {
//    protected TreeSet<String> keywords92 = new TreeSet<String>(Arrays.asList(new String[]{
//        "SELECT", "FROM", "WHERE", "AND", "OR", "NOT", "IN", "IS", "BY", "DESC", "ASC",
//        "INNER", "OUTER", "FULL", "JOIN", "ON", "ORDER", "UPDATE", "DELETE", "INSERT",
//        "INTO", "VALUES", "CREATE", "TABLE", "DATABASE", "DROP", "COMMIT", "ALTER", "VIEW",
//        "LEFT", "RIGHT", "PROCEDURE", "FUNCTION", "BEGIN", "END", "IF", "THEN", "ELSE", "CONSTRAINT",
//        "PRIMARY", "FOREIGN", "INDEX", "DATABASE", "KEY", "UNIQUE",
//        "REFERENCES", "SET", "EXEC", "AS"
//        , "GO","COLUMN", "MODIFY","GROUP"
//    }));

    public void init(DBCConnection session);

}
