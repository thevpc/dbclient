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
package net.thevpc.dbclient.plugin.system.sql;

import net.thevpc.dbclient.api.DBCSession;
import net.thevpc.dbclient.api.pluginmanager.DBCAbstractPluggable;
import net.thevpc.dbclient.api.pluginmanager.DBCFactory;
import net.thevpc.dbclient.api.sql.parser.SQLStatementType;
import net.thevpc.common.prs.plugin.Inject;
import net.thevpc.dbclient.api.sessionmanager.DBCSQLCommandFilter;
import net.thevpc.dbclient.api.sql.*;
import net.thevpc.dbclient.api.sql.parser.SQLParser;
import net.thevpc.dbclient.api.sql.parser.SQLStatement;
import net.thevpc.dbclient.api.sql.util.ReaderProvider;
import net.thevpc.common.swing.dialog.DefaultMessageDiscardContext;
import net.thevpc.common.swing.dialog.MessageDiscardContext;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Set;
import java.util.Vector;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 2 dec. 2006 23:51:58
 */
public class DefaultBatchProcessor extends DBCAbstractPluggable implements BatchProcessor {
    protected Vector<BreakPointListener> breakPointListeners;
    protected Vector<BatchProcessorListener> batchProcessorListeners;
    private boolean pauseOnError = true;
    private boolean singleStatement = false;
    private int statementsCount = 0;
    private int statementsFailedCount = 0;
    private int statementsSucceededCount = 0;
    private boolean databaseStructureChanged = false;
    private Set<Integer> lineBreakPoints;
    //    private boolean errorBreakPoint;
    private PropertyChangeSupport propertyChangeSupport;
    private Mode mode = Mode.UNINITIALIZED;
    private int currentStatementIndex;
    private SQLStatement currentStatement;
    private int currentLineIndex;
    @Inject
    private DBCSession session;
    private StatementProcessor processor;


    private static enum Mode {
        UNINITIALIZED,
        CONTINUE,
        STEP,
        PAUSE,
        STOP
    }

    public DefaultBatchProcessor() {
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public void init(StatementProcessor processor) throws IOException {
        this.processor = processor;
    }

    public void execute(ReaderProvider sqlProvider, boolean debugMode) throws IOException {
        setMode(debugMode ? Mode.PAUSE : Mode.CONTINUE);


        MessageDiscardContext context = new DefaultMessageDiscardContext();
        DBCConnection cnx = null;
        SQLParser parser = null;
        try {
            if (batchProcessorListeners != null) {
                for (BatchProcessorListener batchProcessorListener : batchProcessorListeners) {
                    batchProcessorListener.estimating(this);
                }
            }
            Reader reader;
            SQLStatement q;
            try {
                DBCFactory factory = session.getFactory();
                DBCSQLCommandFilter filter = factory.newInstance(DBCSQLCommandFilter.class);
                ReaderProvider filtered = filter.filter(sqlProvider, session);
                if (filtered != null) {
                    sqlProvider = filtered;
                }
                cnx = session.getConnection();
                parser = cnx.createParser();
                parser.setSingleStatement(isSingleStatement());
                SystemSQLUtils.ScriptInfo scriptInfo = SystemSQLUtils.getScriptInfo(sqlProvider.getReader(), parser);
                reader = sqlProvider.getReader();
                parser.setDocument(reader);
                setStatementsCount(scriptInfo.getStatementsCount());
                setCurrentStatementIndex(-1);
                setCurrentStatement(null);
                setCurrentLineIndex(0);
                setCurrentStatementIndex(-1);
            } catch (SQLException e) {
                if (batchProcessorListeners != null) {
                    for (BatchProcessorListener batchProcessorListener : batchProcessorListeners) {
                        batchProcessorListener.failedToStart(this, e);
                    }
                }
            }
            startProcess();
            if (batchProcessorListeners != null) {
                for (BatchProcessorListener batchProcessorListener : batchProcessorListeners) {
                    batchProcessorListener.started(this);
                }
            }
            STATEMENTS_LOOP:
            while ((q = parser.readStatement()) != null) {
                if (!q.isEmpty()) {
                    setCurrentStatement(q);
                    if (mode == Mode.STOP) {
                        break;
                    }
                    setCurrentStatementIndex(getCurrentStatementIndex() + 1);
                    /// q.getLineStartIndex() is zero based while CurrentLineIndex is 1 based
                    setCurrentLineIndex(q.getNonWhiteLineStartIndex() + 1);
                    if (isCurrentLineBreakPoint()) {
                        setMode(Mode.PAUSE);
                        fireBreakPoint(new BreakpointEvent(getCurrentStatementIndex()));
                    }
                    try {
                        while (mode == Mode.PAUSE) {
                            Thread.sleep(500);
                        }
                    } catch (InterruptedException e) {
                        setMode(Mode.STOP);
                        break;
                    }
                    if (mode == Mode.STOP) {
                        break;
                    }
                    if (batchProcessorListeners != null) {
                        for (BatchProcessorListener batchProcessorListener : batchProcessorListeners) {
                            if (!batchProcessorListener.beforeExecutingStatement(this, getCurrentStatementIndex(), q)) {
                                setMode(Mode.STOP);
                                break STATEMENTS_LOOP;
                            }
                        }
                    }

                    try {
                        processor.executeStatement(q, getCurrentStatementIndex());
                        if (q.getStatementType()== SQLStatementType.UPDATE_STRUCTURE) {
                            databaseStructureChanged();
                        }
                        setStatementsSucceededCount(getStatementsSucceededCount() + 1);
                        if (batchProcessorListeners != null) {
                            for (BatchProcessorListener batchProcessorListener : batchProcessorListeners) {
                                if (!batchProcessorListener.afterExecutingStatement(this, getCurrentStatementIndex(), q, null, context)) {
                                    setMode(Mode.STOP);
                                    break STATEMENTS_LOOP;
                                }
                            }
                        }
                        if (mode == Mode.STEP) {
                            setMode(Mode.PAUSE);
                        }
                    } catch (SQLException e) {
                        setStatementsFailedCount(getStatementsFailedCount() + 1);
                        if (batchProcessorListeners != null) {
                            for (BatchProcessorListener batchProcessorListener : batchProcessorListeners) {
                                if (!batchProcessorListener.afterExecutingStatement(this, getCurrentStatementIndex(), q, e, context)) {
                                    setMode(Mode.STOP);
                                    break STATEMENTS_LOOP;
                                }
                            }
                        }
                        if (isPauseOnError()) {
                            setMode(Mode.PAUSE);
                        }
                    }
                }
            }
        } finally {
            endProcess(cnx);
            if (batchProcessorListeners != null) {
                for (BatchProcessorListener batchProcessorListener : batchProcessorListeners) {
                    batchProcessorListener.ended(this);
                }
            }
            setMode(Mode.UNINITIALIZED);
            setCurrentStatement(null);
            setCurrentLineIndex(0);
            setCurrentStatementIndex(-1);
        }
    }

    protected void setMode(Mode newMode) {
        Mode oldMode = mode;
        mode = newMode;
        propertyChangeSupport.firePropertyChange("mode", oldMode.toString().toLowerCase(), newMode.toString().toLowerCase());
        if (!mode.equals(oldMode)) {
            propertyChangeSupport.firePropertyChange(mode.toString().toLowerCase(), false, true);
        }
    }

    public int getStatementsFailedCount() {
        return statementsFailedCount;
    }

    protected void startProcess() {
        statementsFailedCount = 0;
        statementsSucceededCount = 0;
        databaseStructureChanged = false;
    }

    protected void endProcess(DBCConnection cnx) {
        if (databaseStructureChanged) {
            cnx.structureChanged();
        }
    }

    public void databaseStructureChanged() {
        databaseStructureChanged = true;
    }

    public int getStatementsSucceededCount() {
        return statementsSucceededCount;
    }

    private boolean isCurrentLineBreakPoint() {
        if (lineBreakPoints == null) {
            return false;
        }
        int l1 = currentStatement.getNonWhiteLineStartIndex();
        int l2 = currentStatement.getLineEndIndex();
        for (int i = Math.max(0, l1); i < l2; i++) {
            if (lineBreakPoints.contains(i + 1)) {
                return true;
            }
        }
        return false;
    }

    public int getCurrentStatementIndex() {
        return currentStatementIndex;
    }

    public SQLStatement getCurrentStatement() {
        return currentStatement;
    }

    public int getCurrentLineIndex() {
        return currentLineIndex;
    }


//    public boolean isErrorBreakPoint() {
//        return errorBreakPoint;
//    }
//
//    public void setErrorBreakPoint(boolean errorBreakPoint) {
//        boolean old = this.errorBreakPoint;
//        this.errorBreakPoint = errorBreakPoint;
//        propertyChangeSupport.firePropertyChange("errorBreakPoint", old, errorBreakPoint);
//    }

    public void addBreakPointListner(BreakPointListener listener) {
        if (listener != null) {
            if (breakPointListeners == null) {
                breakPointListeners = new Vector<BreakPointListener>();
            }
            breakPointListeners.add(listener);
        }
    }

    public void addBatchProcessorListener(BatchProcessorListener listener) {
        if (listener != null) {
            if (batchProcessorListeners == null) {
                batchProcessorListeners = new Vector<BatchProcessorListener>();
            }
            batchProcessorListeners.add(listener);
            propertyChangeSupport.addPropertyChangeListener(listener);
        }
    }

    public void removeBreakPointListner(BreakPointListener listener) {
        if (listener != null && breakPointListeners != null) {
            breakPointListeners.remove(listener);
        }
    }

    public void removeBatchProcessorListener(BatchProcessorListener listener) {
        if (listener != null && batchProcessorListeners != null) {
            batchProcessorListeners.remove(listener);
            propertyChangeSupport.removePropertyChangeListener(listener);
        }
    }

    protected void fireBreakPoint(BreakpointEvent e) {
        if (breakPointListeners != null) {
            for (BreakPointListener breakPointListener : breakPointListeners) {
                breakPointListener.breakPointReached(e);
            }
        }
    }

    public void addPropertyChangeListener(String property, PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(property, listener);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(String property, PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(property, listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    public void doContinue() {
        if (isContinueEnabled()) {
            setMode(Mode.CONTINUE);
        }
    }

    public void pause() {
        if (isPauseEnabled()) {
            setMode(Mode.CONTINUE);
        }
    }

    public void step() {
        if (isStepEnabled()) {
            setMode(Mode.STEP);
        }
    }

    public void stop() {
        if (isStopEnabled()) {
            setMode(Mode.STOP);
        }
    }

    public boolean isContinueEnabled() {
        switch (mode) {
            case PAUSE: {
                return true;
            }
            case UNINITIALIZED:
            case CONTINUE:
            case STEP:
            case STOP: {
                return false;
            }
        }
        return false;
    }

    public boolean isPauseEnabled() {
        switch (mode) {
            case UNINITIALIZED:
            case STOP:
            case PAUSE: {
                return false;
            }
            case CONTINUE:
            case STEP: {
                return true;
            }
        }
        return false;
    }

    public boolean isStopEnabled() {
        switch (mode) {
            case STOP: {
                return false;
            }
            case UNINITIALIZED:
            case CONTINUE:
            case STEP:
            case PAUSE: {
                return true;
            }
        }
        return false;
    }

    public boolean isStepEnabled() {
        switch (mode) {
            case PAUSE: {
                return true;
            }
            case UNINITIALIZED:
            case CONTINUE:
            case STEP:
            case STOP: {
                return false;
            }
        }
        return false;
    }

    public boolean isRunning() {
        return mode != Mode.UNINITIALIZED;
    }

    public void setCurrentStatementIndex(int currentStatementIndex) {
        int old = this.currentStatementIndex;
        this.currentStatementIndex = currentStatementIndex;
        propertyChangeSupport.firePropertyChange("currentStatementIndex", old, currentStatementIndex);
    }

    public void setCurrentStatement(SQLStatement currentStatement) {
        SQLStatement old = this.currentStatement;
        this.currentStatement = currentStatement;
        propertyChangeSupport.firePropertyChange("currentStatement", old, currentStatement);
    }

    public void setCurrentLineIndex(int currentLineIndex) {
        int old = this.currentLineIndex;
        this.currentLineIndex = currentLineIndex;
        propertyChangeSupport.firePropertyChange("currentLineIndex", old, currentLineIndex);
    }

    public void setStatementsCount(int statementsCount) {
        int old = this.statementsCount;
        this.statementsCount = statementsCount;
        propertyChangeSupport.firePropertyChange("statementsCount", old, statementsCount);
    }

    public void setStatementsFailedCount(int statementsFailedCount) {
        int old = this.statementsFailedCount;
        this.statementsFailedCount = statementsFailedCount;
        propertyChangeSupport.firePropertyChange("statementsFailedCount", old, statementsFailedCount);
    }

    public void setStatementsSucceededCount(int statementsSucceededCount) {
        int old = this.statementsSucceededCount;
        this.statementsSucceededCount = statementsSucceededCount;
        propertyChangeSupport.firePropertyChange("statementsSucceededCount", old, statementsSucceededCount);
    }

    public void setPauseOnError(boolean pauseOnError) {
        boolean old = this.pauseOnError;
        this.pauseOnError = pauseOnError;
        propertyChangeSupport.firePropertyChange("pauseOnError", old, pauseOnError);
    }

    public int getStatementsCount() {
        return statementsCount;
    }

    public void setBreakPoints(Set<Integer> breakPoints) {
        this.lineBreakPoints = breakPoints;
    }

    public Set<Integer> getBreakPoints() {
        return lineBreakPoints != null ? lineBreakPoints : Collections.EMPTY_SET;
    }

    public void close() throws IOException {
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        close();
    }

    public boolean isPauseOnError() {
        return pauseOnError;
    }

    public boolean isSingleStatement() {
        return singleStatement;
    }

    public void setSingleStatement(boolean singleStatement) {
        this.singleStatement = singleStatement;
    }
    

}
