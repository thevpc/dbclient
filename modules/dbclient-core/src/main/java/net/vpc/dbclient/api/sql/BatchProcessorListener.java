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

package net.vpc.dbclient.api.sql;

import net.vpc.dbclient.api.sql.parser.SQLStatement;
import net.vpc.swingext.dialog.MessageDiscardContext;

import java.beans.PropertyChangeListener;
import java.sql.SQLException;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 3 aout 2006 14:50:25
 */
public interface BatchProcessorListener extends PropertyChangeListener {
    
    void failedToStart(BatchProcessor processor,Throwable throwable);

    void started(BatchProcessor processor);

    void ended(BatchProcessor processor);

    void estimating(BatchProcessor processor);

    /**
     * called before executing statement
     *
     * @param processor
     *@param i statement index
     * @param q statement   @return true if script execution may continue
     */
    boolean beforeExecutingStatement(BatchProcessor processor, int i, SQLStatement q);

    /**
     * called after executing statement
     *
     * @param processor
     *@param i                     statement index
     * @param q                     statement
     * @param th                    eventual exception
     * @param messageDiscardContext user discard context     @return true if script execution may continue
     */
    boolean afterExecutingStatement(BatchProcessor processor, int i, SQLStatement q, SQLException th, MessageDiscardContext messageDiscardContext);
}
