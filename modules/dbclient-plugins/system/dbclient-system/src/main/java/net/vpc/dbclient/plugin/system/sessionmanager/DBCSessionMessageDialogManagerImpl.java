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


package net.vpc.dbclient.plugin.system.sessionmanager;

import net.vpc.dbclient.api.DBCSession;
import net.vpc.dbclient.api.sessionmanager.DBCSessionMessageDialogManager;
import net.vpc.swingext.dialog.DefaultMessageDialogManager;
import net.vpc.swingext.dialog.MessageDialogType;
import net.vpc.swingext.dialog.MessageDiscardContext;

import java.awt.*;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 1 avr. 2007 12:24:09
 */
public class DBCSessionMessageDialogManagerImpl extends DefaultMessageDialogManager<DBCSession> implements DBCSessionMessageDialogManager {


    @Override
    public ReturnType showMessage(Component parentComponent, String message, MessageDialogType type, String title, Throwable th, String context) {
        return showMessage(parentComponent, message, type, title, th, context != null ? new DBCSessionMessageDiscardContext(getDialogOwner(), context) : null);
    }

    @Override
    public ReturnType showMessage(Component parentComponent, String message, MessageDialogType type, String title, Throwable th, MessageDiscardContext context) {
        return getDialogOwner().getApplication().getView().getDialogManager().showMessage(parentComponent, message, type, title, th, context);
    }


}
