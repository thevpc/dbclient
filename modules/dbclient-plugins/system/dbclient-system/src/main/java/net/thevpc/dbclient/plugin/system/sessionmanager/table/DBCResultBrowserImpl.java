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

package net.thevpc.dbclient.plugin.system.sessionmanager.table;

import net.thevpc.dbclient.api.sessionmanager.DBCInternalWindow;
import net.thevpc.dbclient.api.sessionmanager.DBCResultBrowser;
import net.thevpc.dbclient.api.sessionmanager.DBCResultTable;
import net.thevpc.dbclient.plugin.system.sessionmanager.window.DBCSessionInternalWindowBrowserImpl;
import net.thevpc.common.swing.prs.PRSManager;
import net.thevpc.common.swing.SwingUtilities3;
import net.thevpc.common.prs.log.TLog;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 12 juil. 2005 19:12:51
 */
public class DBCResultBrowserImpl extends DBCSessionInternalWindowBrowserImpl implements DBCResultBrowser {
    private TLog log;

    public DBCResultBrowserImpl() {
        super();
    }

    public void setLog(TLog log) {
        this.log = log;
    }

    public TLog getLog() {
        return log;
    }

    public void executeStatement(final String query, final boolean replaceUnlocked) throws SQLException {
        Statement statement = null;
        try {
            statement = getSession().getConnection().createStatement();
            SQLWarning warnings = getSession().getConnection().getWarnings();
            boolean isResultSet = statement.execute(query);
            TLog theLog = getLog();
            while (warnings != null) {
                theLog.debug(warnings.getSQLState() + " : " + warnings.getMessage());
                warnings = warnings.getNextWarning();
            }
            getSession().getConnection().clearWarnings();
            if (isResultSet) {
                ResultSet rs = statement.getResultSet();
                final DBCResultTable table = getSession().getFactory().newInstance(DBCResultTable.class);
                table.getModel().displayQuery(rs, null);
                table.getModel().setQuery(query);
                SwingUtilities3.invokeLater(new Runnable() {
                    public void run() {
                        DBCInternalWindow f = addWindow(table.getComponent(), replaceUnlocked);
                        f.setLocked(isNewWindowLocked());
                        f.setTitle(generateName());
                        PRSManager.update(f.getComponent(), getSession().getView());
                    }
                });
            } else {
                int count = statement.getUpdateCount();
                getLog().trace("Update Count : " + count);
            }
            while (true) {
                isResultSet = statement.getMoreResults();
                if (isResultSet) {
                    ResultSet rs = statement.getResultSet();
                    final DBCResultTable table = getSession().getFactory().newInstance(DBCResultTable.class);
                    table.getModel().displayQuery(rs, null);
                    SwingUtilities3.invokeLater(new Runnable() {
                        public void run() {
                            DBCInternalWindow f = addWindow(table.getComponent(), replaceUnlocked);
                            f.setLocked(isNewWindowLocked());
                            f.setTitle(generateName());
                            PRSManager.update(f.getComponent(), getSession().getView());
                        }
                    });
                } else {
                    int count = statement.getUpdateCount();
                    if (count < 0) {
                        break;
                    }
                    getLog().trace("Update Count : " + count);
                }
            }
        } finally {
            if (statement != null) {
                statement.close();
            }
        }

    }
}
