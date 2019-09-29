/**
 *
 ====================================================================
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
 *
 ====================================================================
 */

package net.vpc.dbclient.plugin.dbsupport.oracle.actions;

import net.vpc.dbclient.api.actionmanager.DBCTreeNodeAction;
import net.vpc.dbclient.api.sql.objects.DBObject;
import net.vpc.dbclient.plugin.dbsupport.oracle.OracleConnection;

import java.sql.SQLException;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 20 avr. 2007 16:32:06
 */
public abstract class AbstractOracleTreeNodeAction extends DBCTreeNodeAction {
    public AbstractOracleTreeNodeAction(String name) {
        super(name);
    }

    public boolean shouldEnable(DBObject activeNode, DBObject[] selectedNodes) {
        return true;
    }

    public OracleConnection getConnection() throws SQLException {
        return (OracleConnection) getSession().getConnection();
    }

}