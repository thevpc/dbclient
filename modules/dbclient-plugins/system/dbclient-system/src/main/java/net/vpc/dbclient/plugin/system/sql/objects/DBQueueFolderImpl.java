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

package net.vpc.dbclient.plugin.system.sql.objects;

import net.vpc.dbclient.api.sql.DBCConnection;
import net.vpc.dbclient.api.sql.objects.DBQueue;
import net.vpc.dbclient.api.sql.objects.DBQueueFolder;
import net.vpc.dbclient.api.sql.objects.DBSchema;
import net.vpc.dbclient.api.sql.objects.SQLObjectTypes;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 13 juil. 2005 14:32:34
 */
public class DBQueueFolderImpl extends DBObjectFolderImpl implements DBQueueFolder {

    public void init(DBCConnection session, String catalogName, String schemaName) {
        super.init(session, "Tree.AllQueues", catalogName, schemaName, null, "AllQueues", false);
    }

    public DBSchema getSchema() {
        return (DBSchema) getParentObject();
    }

    @Override
    protected void loadChildren() throws SQLException {
        ResultSet rs = getConnection().getQueues(
                getCatalogName(),
                getSchemaName(),
                "%", null);
        while (rs.next()) {
            DBQueue item = getConnection().getFactory().newInstance(DBQueue.class);
            item.init(getConnection(), rs);
            this.addChild(item);
        }
        rs.close();
    }

    @Override
    public String getSQL() {
        return null;
    }

    public SQLObjectTypes getType() {
        return SQLObjectTypes.FOLDER;
    }

}