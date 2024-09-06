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

package net.thevpc.dbclient.plugin.system.sessionmanager;

import net.thevpc.dbclient.api.DBCSession;
import net.thevpc.common.swing.dialog.MessageDiscardContext;

/**
 * @author vpc
 */
public class DBCSessionMessageDiscardContext implements MessageDiscardContext {
    private DBCSession session;
    private String parameter;

    public DBCSessionMessageDiscardContext(DBCSession session, String parameter) {
        this.session = session;
        this.parameter = parameter;
    }

    public boolean isDiscarded() {
        try {
            return session.getConfig().getBooleanProperty("messageDiscardContext." + parameter, Boolean.FALSE);
        } catch (Exception ex) {
            return false;
            //Logger.getLogger(FileMessageDiscardContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setDiscarded(boolean val) {
        try {
            session.getConfig().setBooleanProperty("messageDiscardContext." + parameter, val);
        } catch (Exception ex) {
            //Logger.getLogger(FileMessageDiscardContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
