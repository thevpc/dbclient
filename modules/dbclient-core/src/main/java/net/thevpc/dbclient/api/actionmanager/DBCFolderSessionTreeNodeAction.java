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

package net.thevpc.dbclient.api.actionmanager;

import net.thevpc.dbclient.api.sql.objects.DBObject;

import java.awt.event.ActionEvent;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 29 mars 2007 23:54:29
 */
public abstract class DBCFolderSessionTreeNodeAction extends DBCTreeNodeAction {
    public DBCFolderSessionTreeNodeAction(String actionId, String folderId) {
        super(actionId);
        setType(DBClientActionType.FOLDER);
        setShortId(folderId);
        //PRSManager.update(this,session);
    }

    public boolean shouldEnable(DBObject activeNode, DBObject[] selectedNodes) {
        return true;
    }

    public final void actionPerformedImpl(ActionEvent e) throws Throwable {

    }
}
