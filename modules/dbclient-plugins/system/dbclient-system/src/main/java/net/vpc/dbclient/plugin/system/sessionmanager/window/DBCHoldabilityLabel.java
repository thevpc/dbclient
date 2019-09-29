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

import java.sql.ResultSet;
import java.util.logging.Level;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 29 nov. 2006 22:55:46
 */
public class DBCHoldabilityLabel extends DBCSessionStatusBarLabel {
    public DBCHoldabilityLabel(DBCSessionView defaultSessionMainPanel) {
        super(defaultSessionMainPanel);
        this.defaultSessionMainPanel = defaultSessionMainPanel;
        addValue(ResultSet.HOLD_CURSORS_OVER_COMMIT, "Hold Cursors Over Commit");
        addValue(ResultSet.CLOSE_CURSORS_AT_COMMIT, "Close Cursors At Commit");
        setUnknown("Holdability-Unknown");
        try {
            setValue(defaultSessionMainPanel.getSession().getConnection().getHoldability());
        } catch (Throwable e) {
            setText("Holdability-Unsupported");
            defaultSessionMainPanel.getSession().getLogger(getClass().getName()).log(Level.SEVERE,"Driver does not support Connection Holdability",e);
        }
    }

    public void actionPerformedImpl(int e) throws Throwable {
        defaultSessionMainPanel.getSession().getConnection().setHoldability(e);
    }

}
