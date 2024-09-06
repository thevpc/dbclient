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

package net.thevpc.dbclient.plugin.dbsupport.postgresql.actions;

import net.thevpc.dbclient.api.actionmanager.DBCActionLocation;
import net.thevpc.dbclient.api.sql.objects.DBObject;

import java.awt.event.ActionEvent;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 20 avr. 2007 16:32:06
 */
public class NewDatabaseTreeNode extends AbstractPostgreSqlTreeNodeAction {
    public NewDatabaseTreeNode() {
        super("Action.NewDatabaseAction");
        addLocationPath(DBCActionLocation.POPUP, "/New");
        setIconId(null);
        setIconId("Add");
    }

    @Override
    public boolean shouldEnable(DBObject activeNode, DBObject[] selectedNodes) {
        return false;
    }

    @Override
    public void actionPerformedImpl(ActionEvent e) throws Throwable {
//        getConnection().
    }
}
