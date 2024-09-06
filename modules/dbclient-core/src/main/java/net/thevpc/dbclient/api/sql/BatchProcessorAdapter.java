/**
 * ====================================================================
 *             DBCLient yet another Jdbc client tool
 *
 * DBClient is a new Open Source Tool for connecting to jdbc
 * compliant relational databases. Specific extensions will take care of
 * each RDBMS implementation.
 *
 * Copyright (C) 2006-2007 Taha BEN SALAH
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

package net.thevpc.dbclient.api.sql;

import net.thevpc.dbclient.api.sql.parser.SQLStatement;
import net.thevpc.common.swing.dialog.MessageDiscardContext;

import java.beans.PropertyChangeEvent;
import java.sql.SQLException;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 3 dec. 2006 15:20:14
 */
public class BatchProcessorAdapter implements BatchProcessorListener {

    public void started(BatchProcessor processor) {
        //do nothing
    }

    public void ended(BatchProcessor processor) {
        //do nothing
    }

    public void estimating(BatchProcessor processor) {
        //do nothing
    }

    /**
     * @return always true to enable script continuing
     */
    public boolean beforeExecutingStatement(BatchProcessor processor, int i, SQLStatement q) {
        return true;
    }

    /**
     * @return always true to enable script continuing
     */
    public boolean afterExecutingStatement(BatchProcessor processor, int i, SQLStatement q, SQLException th, MessageDiscardContext messageDiscardContext) {
        return true;
    }

    public void failedToStart(BatchProcessor processor, Throwable throwable) {
        //do nothing
    }

    public void propertyChange(PropertyChangeEvent evt) {
        //do nothing
    }
}
