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

package net.vpc.dbclient.api.actionmanager;


import net.vpc.dbclient.api.pluginmanager.DBCPluginSession;
import net.vpc.prs.plugin.Extension;
import net.vpc.dbclient.api.sessionmanager.DBCResultTable;
import net.vpc.dbclient.api.sql.objects.DBObject;
import net.vpc.dbclient.api.viewmanager.DBCTable;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 6 mai 2006 18:47:41
 */
@Extension(group = "ui.session", customizable = false)
public abstract class DBCResultTableAction extends DBCSessionAction {
    private DBCResultTable jtable;
    private int row = -1;
    private int column = -1;

    public DBCResultTableAction(String key) {
        super(key);
    }

    public DBCResultTableAction(String key, DBCPluginSession pluginSession) {
        super(key);
        setPluginSession(pluginSession);
    }

    public DBCResultTableAction(String key, DBCResultTable jTable) {
        super(key);
        setResultTable(jTable);
    }


    public DBCResultTable getResultTable() {
        return jtable;
    }

    public DBCTable getTableComponent() {
        return jtable==null?null:jtable.getTableComponent();
    }

    public void setResultTable(DBCResultTable jTable) {
        jtable = jTable;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public boolean shouldEnable(DBObject activeNode, DBObject[] selectedNodes) {
        return (row >= 0 && column >= 0);
    }

}

