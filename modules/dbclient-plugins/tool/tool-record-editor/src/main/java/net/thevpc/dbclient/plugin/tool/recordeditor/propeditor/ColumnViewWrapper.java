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

package net.thevpc.dbclient.plugin.tool.recordeditor.propeditor;

import net.thevpc.dbclient.api.DBCSession;
import net.thevpc.dbclient.plugin.tool.recordeditor.RecordEditorPluginSession;
import net.thevpc.dbclient.api.sql.objects.DBTableColumn;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 2 aout 2006 19:57:03
 */
public abstract class ColumnViewWrapper extends DefaultColumnView {

    private ColumnView base;

    public ColumnViewWrapper(ColumnView base) {
        super();
        this.base = base;
    }

    @Override
    public void configure() {
        base.configure();
    }

    @Override
    public boolean accept(DBTableColumn node) {
        return base.accept(node);
    }

    @Override
    public DBTableColumn getColumn() {
        return base.getColumn();
    }

    @Override
    public DBCSession getSession() {
        return base.getSession();
    }

    @Override
    public void setColumn(DBTableColumn column) {
        base.setColumn(column);
    }

    @Override
    public void init(RecordEditorPluginSession session) {
        base.init(session);
    }

    @Override
    public void setTableView(TableView defaultTableView) {
        base.setTableView(defaultTableView);
    }

    public ColumnView getBase() {
        return base;
    }

}
