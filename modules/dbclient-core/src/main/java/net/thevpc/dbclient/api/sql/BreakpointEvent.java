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

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 3 dec. 2006 15:29:55
 */
public class BreakpointEvent {
    public boolean errorBreakpoint;
    public int lineNumber;
    public Throwable exception;

    public BreakpointEvent(int lineNumber) {
        this(false, lineNumber, null);
    }

    public BreakpointEvent(int lineNumber, Throwable exception) {
        this(true, lineNumber, exception);
    }

    public BreakpointEvent(boolean errorBreakpoint, int lineNumber, Throwable exception) {
        this.errorBreakpoint = errorBreakpoint;
        this.lineNumber = lineNumber;
        this.exception = exception;
    }


    public boolean isErrorBreakpoint() {
        return errorBreakpoint;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public Throwable getException() {
        return exception;
    }
}
