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

package net.thevpc.dbclient.plugin.system.sql.objects;

import net.thevpc.dbclient.api.sql.DBCConnection;
import net.thevpc.dbclient.api.sql.objects.DBDateFunctionFolder;
import net.thevpc.dbclient.api.sql.objects.DBFunction;
import net.thevpc.dbclient.api.sql.objects.SQLObjectTypes;

import java.sql.SQLException;


/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 13 juil. 2005 14:32:11
 */
public class DBDateFunctionFolderImpl extends DBAbstractFunctionFolderImpl implements DBDateFunctionFolder {
    public void init(DBCConnection session) {
        super.init(session, "DateFunctions");
    }

    @Override
    protected void loadChildren() throws SQLException {
        DBFunction[] fcts = getConnection().getTimeDateFunctions();
        for (DBFunction fct : fcts) {
            addFunction(fct);
        }
    }

    public SQLObjectTypes getType() {
        return SQLObjectTypes.FOLDER;
    }
}
