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

package net.thevpc.dbclient.plugin.sql.jstsql.actions;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.thevpc.dbclient.api.DBCSession;
import net.thevpc.dbclient.api.pluginmanager.DBCPluginSession;
import net.thevpc.common.prs.plugin.Inject;
import net.thevpc.dbclient.api.sessionmanager.DBCSessionExplorer;
import net.thevpc.dbclient.api.sql.objects.DBObject;
import net.thevpc.dbclient.plugin.sql.jstsql.JSTSqlPlugin;
import net.thevpc.dbclient.plugin.sql.jstsql.evaluator.JSTSqlEvaluator;
import net.thevpc.dbclient.plugin.sql.jstsql.evaluator.JSTSqlSessionEvalContext;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.sql.SQLException;
import net.thevpc.dbclient.api.sql.util.StringReaderProvider;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 12 août 2005 10:46:51
 */
public abstract class TreeSQLGenerator {
    @Inject
    protected DBCPluginSession pluginSession;
    protected String name;
    protected boolean showJst4sql = true;
    protected String defaultName = null;

    protected TreeSQLGenerator() {
    }

    protected void setName(String name) {
        this.name = name;
    }

    public String getDefaultName() {
        return defaultName;
    }

    public abstract void generateSQL(PrintStream printStream, DBObject[] selectedNodes) throws SQLException;

    public String generateSQLString(DBObject[] selectedNodes) throws SQLException {
        if (showJst4sql) {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(os);
            this.generateSQL(ps, selectedNodes);
            return os.toString();
        } else {
            ByteArrayOutputStream o = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(o);
            for (DBObject selectedNode : selectedNodes) {
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                PrintStream ps = new PrintStream(os);
                this.generateSQL(ps, new DBObject[]{selectedNode});
                JSTSqlSessionEvalContext context = new JSTSqlSessionEvalContext(getSession(), selectedNode);
                try {
                    JSTSqlPlugin.resolveSqlEvaluator(getPluginSession()).execute(new StringReaderProvider(os.toString()), context, printStream);
                } catch (IOException ex) {
                    throw new SQLException(ex);
                }
            }
            printStream.flush();
            return o.toString();
        }
    }

    public String getName() {
        return name;
    }

    public DBCSession getSession() {
        return getPluginSession().getSession();
    }

    public DBCPluginSession getPluginSession() {
        return pluginSession;
    }

    public DBCSessionExplorer getTree() throws SQLException {
        return getSession().getView().getExplorer();
    }

    public boolean isShowJst4sql() {
        return showJst4sql;
    }

    public void setShowJst4sql(boolean showJst4sql) {
        this.showJst4sql = showJst4sql;
    }

    public abstract boolean isEnabled(DBObject activeNode, DBObject[] selectedNodes);

    public boolean isConfirmEnabled() {
        return false;
    }

    public String getConfirmMessage() {
        return null;
    }

}
