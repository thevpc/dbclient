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

package net.thevpc.dbclient.api.sessionmanager;

import net.thevpc.dbclient.api.DBCSession;
import net.thevpc.common.prs.plugin.Extension;
import net.thevpc.dbclient.api.sql.BatchProcessor;
import net.thevpc.dbclient.api.sql.DBCExecutionPlan;
import net.thevpc.dbclient.api.viewmanager.DBCComponent;

import java.io.IOException;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 31 janv. 2006 15:47:00
 */
@Extension(group = "ui.session")
public interface DBCSQLCommandPane extends DBCComponent {
    BatchProcessor createBatchProcessor() throws IOException;

    void executeSQL() throws IOException;

    DBCSession getSession();

    void setLayoutDefault();

    void showMessagesPanel();

    void showGridsPanel();

    String getSQL();

    void setSQL(String sql);

    DBCSQLEditor getEditor();

    void setExecutionPlan(DBCExecutionPlan plan);

}
