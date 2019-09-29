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
package net.vpc.dbclient.plugin.sql.jstsql.actions;

import java.io.IOException;
import net.vpc.dbclient.api.pluginmanager.DBCPluginSession;
import net.vpc.dbclient.api.sql.objects.DBObject;
import net.vpc.dbclient.api.sql.util.StringReaderProvider;
import net.vpc.dbclient.plugin.sql.jstsql.JSTSqlPlugin;
import net.vpc.dbclient.plugin.sql.jstsql.JSTSqlTemplateInfo;
import net.vpc.dbclient.plugin.sql.jstsql.evaluator.JSTSqlEvaluator;
import net.vpc.dbclient.plugin.sql.jstsql.evaluator.JSTSqlSessionEvalContext;

import java.io.PrintStream;
import java.sql.SQLException;
import java.util.regex.Pattern;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 12 aout 2005 10:48:58
 */
public class GenericTreeSQLGenerator extends TreeSQLGenerator {

    private Pattern filterPattern;
    private JSTSqlTemplateInfo data;

    public GenericTreeSQLGenerator() {
    }

    public void init(JSTSqlTemplateInfo data) {
        setName("GenericTreeSQLGenerator." + data.getSstName());
        this.data = data;
        filterPattern = Pattern.compile(data.getSstNodeFilter(), Pattern.CASE_INSENSITIVE);
    }

    public void generateSQL(PrintStream printStream, DBObject[] selectedNodes) throws SQLException {
        if (data.getSstPreferTemplate()) {
            printStream.println(data.getSstSql());
        } else {
            for (DBObject selectedNode : selectedNodes) {
                JSTSqlSessionEvalContext context = new JSTSqlSessionEvalContext(String.valueOf(data.getSstId()), data.getSstVersion(), getSession(), selectedNode);
                try {
                    JSTSqlPlugin.resolveSqlEvaluator(getPluginSession()).execute(
                            new StringReaderProvider(data.getSstSql()),
                            context,
                            printStream);
                } catch (IOException ex) {
                    throw new SQLException(ex);
                }
            }
        }
    }

    public boolean isConfirmEnabled() {
        return data.getSstConfirmEnabled();
    }

    public String getConfirmMessage() {
        String msg = data.getSstConfirmMsg();
        if (msg != null) {
            msg = getSession().getView().getMessageSet().get(msg);
        }
        return msg;
    }

    public boolean isEnabled(DBObject activeNode, DBObject[] selectedNodes) {
        return activeNode != null && filterPattern.matcher(activeNode.getType().toString()).matches();
    }
}
