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

import java.util.logging.Level;
import net.vpc.dbclient.api.sessionmanager.DBCSessionView;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 29 nov. 2006 22:55:29
 */
public class DBCReadWriteLabel extends DBCSessionStatusBarLabel {

    public DBCReadWriteLabel(DBCSessionView defaultSessionMainPanel) {
        super(defaultSessionMainPanel);
        addValue(1, "Read-Write");
        addValue(0, "Read-Only");
        setUnknown("Read-Unknown");
        try {
            setValue(defaultSessionMainPanel.getSession().getConnection().isReadOnly() ? 1 : 0);
        } catch (Throwable e) {
            setText("Read-Unsupported");
            defaultSessionMainPanel.getSession().getLogger(getClass().getName()).log(Level.SEVERE,"Driver does not support Connection Read/Write Property",e);
        }
    }

    public void actionPerformedImpl(int e) throws Throwable {
        defaultSessionMainPanel.getSession().getConnection().setReadOnly(e == 1);
    }

}
