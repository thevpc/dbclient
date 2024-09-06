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

import net.thevpc.dbclient.api.pluginmanager.DBCPluggable;
import net.thevpc.common.prs.plugin.Extension;
import net.thevpc.dbclient.api.sql.parser.SQLStatement;
import net.thevpc.dbclient.api.sql.util.ReaderProvider;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Set;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 2 dec. 2006 23:51:23
 */
@Extension(group = "sql")
public interface BatchProcessor extends DBCPluggable{

    public void init(StatementProcessor processor) throws IOException;

    public void execute(ReaderProvider sqlProvider, boolean debugMode) throws IOException;
    
    public void close() throws IOException;

    int getStatementsFailedCount();

    void setBreakPoints(Set<Integer> breakPoints);

    Set<Integer> getBreakPoints();

    void addBreakPointListner(BreakPointListener listener);

    void removeBreakPointListner(BreakPointListener listener);

    void addBatchProcessorListener(BatchProcessorListener listener);

    void removeBatchProcessorListener(BatchProcessorListener listener);

    void addPropertyChangeListener(String property, PropertyChangeListener listener);

    void addPropertyChangeListener(PropertyChangeListener listener);

    void removePropertyChangeListener(String property, PropertyChangeListener listener);

    void removePropertyChangeListener(PropertyChangeListener listener);

    void doContinue();

    void pause();

    void step();

    void stop();

    boolean isContinueEnabled();

    boolean isPauseEnabled();

    boolean isStopEnabled();

    boolean isStepEnabled();

    boolean isRunning();

    int getCurrentStatementIndex();

    SQLStatement getCurrentStatement();

    int getCurrentLineIndex();

    int getStatementsSucceededCount();

    int getStatementsCount();

    void setPauseOnError(boolean pauseOnError);

    boolean isPauseOnError();
    
    public boolean isSingleStatement();

    public void setSingleStatement(boolean singleStatement);
}
