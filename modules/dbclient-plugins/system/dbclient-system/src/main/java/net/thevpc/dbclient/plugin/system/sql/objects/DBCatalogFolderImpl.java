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
import net.thevpc.dbclient.api.sql.objects.DBCatalog;
import net.thevpc.dbclient.api.sql.objects.DBCatalogFolder;
import net.thevpc.dbclient.api.sql.objects.SQLObjectTypes;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 13 juil. 2005 14:31:48
 */
public class DBCatalogFolderImpl extends DBObjectFolderImpl implements DBCatalogFolder {

    public void init(DBCConnection session) {
        super.init(session, "Tree.AllCatalogs", null, null, null, "AllCatalogs", false);
    }


    @Override
    protected void loadChildren() throws SQLException {
        ResultSet rs;

        DatabaseMetaData metaData = getConnection().getMetaData();
        String catalogTerm = "catalog";
        try {
            catalogTerm = metaData.getCatalogTerm();
        } catch (SQLException e) {
            //
        }

        rs = metaData.getCatalogs();
        DBCatalog catDefault = null;
        int pos = this.children.size();
        int countCatalogs = 0;
        while (rs.next()) {
            countCatalogs++;
            DBCatalog cat = connection.getFactory().newInstance(DBCatalog.class);
            cat.init(getConnection(), rs);
//            cat.setDefault(cat.getActionName().equals(dbClient.getConnection().getCatalog()));
            if (catDefault == null && cat.isDefault()) {
                catDefault = cat;
            } else {
                this.addChild(cat);
            }
        }
        if (countCatalogs > 0) {
            if (catDefault != null) {
                this.addChild(catDefault, pos);
            }
        } else {
            DBCatalog cat = connection.getFactory().newInstance(DBCatalog.class);
            cat.init(getConnection(),(String)null);
//            cat.setDefault(true);
            this.addChild(cat, pos);
        }
        rs.close();

        if (this.children.isEmpty()) {
            DBCatalog cat = connection.getFactory().newInstance(DBCatalog.class);
            cat.init(getConnection(),(String)null);

            this.addChild(cat);
        }
    }

    public SQLObjectTypes getType() {
        return SQLObjectTypes.FOLDER;
    }

}
