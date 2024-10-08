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

package net.thevpc.dbclient.plugin.sql.jstsql.evaluator;

import net.thevpc.dbclient.api.DBCApplication;
import net.thevpc.dbclient.api.DBCSession;
import net.thevpc.dbclient.api.sql.objects.DBObject;

import java.sql.SQLException;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 4 dec. 2006 01:52:05
 */
public class JSTSqlSessionEvalContext extends JSTSqlEvalContext {

    public JSTSqlSessionEvalContext(DBCSession session, DBObject node) throws SQLException {
        this(null, 0, session, node);
    }

    public JSTSqlSessionEvalContext(String contextId, long version, DBCSession session, DBObject node) throws SQLException {
        super(contextId, version, session == null ? null : session.getConnection());
        add("session", DBCSession.class, session, null);
        add("dbclient", DBCApplication.class, session == null ? null : session.getApplication(), null);
        add("node", node == null ? DBObject.class : node.getClass(), node, null);
    }
}
