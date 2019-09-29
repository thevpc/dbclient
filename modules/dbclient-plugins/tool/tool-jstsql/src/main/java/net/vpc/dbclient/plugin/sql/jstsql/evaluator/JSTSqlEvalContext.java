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

package net.vpc.dbclient.plugin.sql.jstsql.evaluator;

import net.vpc.dbclient.api.sql.DBCConnection;

import java.util.Collection;
import java.util.HashMap;
import java.util.TreeSet;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 3 dec. 2006 14:49:20
 */
public class JSTSqlEvalContext {
    private String contextId;
    private long version;
    private DBCConnection connection;
    private HashMap<String, Var> map = new HashMap<String, Var>();


    public JSTSqlEvalContext(DBCConnection connection) {
        this(null, 0, connection);
    }

    public JSTSqlEvalContext(String contextId, long version, DBCConnection connection) {
        this.connection = connection;
        this.contextId = contextId;
        this.version = version;
    }

    public String getHashString() {
        TreeSet<String> h = new TreeSet<String>(map.keySet());
        StringBuilder sb = new StringBuilder("[" + contextId + "][" + version + "]");
        for (String s : h) {
            if (sb.length() > 0) {
                sb.append(";");
            }
            sb.append(s).append("=").append(map.get(s).getClazz().getName());
        }
        return sb.toString();
    }

    public void add(String name,
                    Class clazz,
                    Object value,
                    String expression) {
        map.put(name, new Var(name, clazz, value, expression));
    }

    public Object getValue(String name) {
        return map.get(name).getValue();
    }

    public static class Var {
        private String name;
        private Class clazz;
        private Object value;
        private String expression;

        public Var(String name, Class clazz, Object value, String expression) {
            this.name = name;
            this.clazz = clazz;
            this.value = value;
            this.expression = expression;
        }

        public String getName() {
            return name;
        }

        public Class getClazz() {
            return clazz;
        }

        public Object getValue() {
            return value;
        }

        public String getExpression() {
            return expression;
        }
    }

    public Collection<Var> getVars() {
        return map.values();
    }


    public DBCConnection getConnection() {
        return connection;
    }

    public String getContextId() {
        return contextId;
    }
}
