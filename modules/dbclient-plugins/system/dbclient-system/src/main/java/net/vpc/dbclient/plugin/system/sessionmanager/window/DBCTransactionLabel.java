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

package net.vpc.dbclient.plugin.system.sessionmanager.window;

import net.vpc.dbclient.api.sessionmanager.DBCSessionView;

import java.sql.Connection;
import java.util.logging.Level;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 29 nov. 2006 22:55:38
 */
public class DBCTransactionLabel extends DBCSessionStatusBarLabel {

    public DBCTransactionLabel(DBCSessionView defaultSessionMainPanel) {
        super(defaultSessionMainPanel);
        addValue(Connection.TRANSACTION_READ_UNCOMMITTED, "Transaction-Read-Uncommitted");
        addValue(Connection.TRANSACTION_READ_COMMITTED, "Transaction-Read-Committed");
        addValue(Connection.TRANSACTION_REPEATABLE_READ, "Transaction-Repeatable-Read");
        addValue(Connection.TRANSACTION_SERIALIZABLE, "Transaction-Serializable");
        addValue(Connection.TRANSACTION_NONE, "Transaction-None");
        setUnknown("Transaction-Unknown");
        try {
            setValue(defaultSessionMainPanel.getSession().getConnection().getTransactionIsolation());
        } catch (Throwable e) {
            setText("Transaction-Unsupported");
            defaultSessionMainPanel.getSession().getLogger(getClass().getName()).log(Level.SEVERE,"Driver does not support Transactions",e);
        }
    }

    public void actionPerformedImpl(int e) throws Throwable {
        defaultSessionMainPanel.getSession().getConnection().setTransactionIsolation(e);
    }

}
