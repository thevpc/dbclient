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

package net.vpc.dbclient.plugin.dbsupport.mysql;

import net.vpc.dbclient.api.sql.objects.SQLObjectTypes;
import net.vpc.dbclient.plugin.system.sql.DBCAbstractConnection;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 19 juil. 2006 16:12:46
 */
public class MySqlConnection extends DBCAbstractConnection {
    public MySqlConnection() {
        super();
    }

    public int getConnectionSupportLevel(Connection connection) throws SQLException {
        return MySqlPlugin.getConnectionSupportLevel0(connection);
    }

    @Override
    public boolean isSQLCreateObjectSupported(SQLObjectTypes objectType) throws SQLException {
        return
                SQLObjectTypes.TABLE.equals(objectType);
    }

    @Override
    public boolean acceptObjectType(SQLObjectTypes type) {
        switch (type) {
            case PACKAGE:
            case QUEUE: {
                return false;
            }
        }
        return true;
    }
}
