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
import net.vpc.dbclient.api.sql.objects.*;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;


/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 13 juil. 2005 14:32:11
 */
public class DBServerImpl extends DefaultDBObject implements DBServer {
    private String catalogTerm;
    private String preferredLabel;
    private DBFunctionFolder functionFolder;
    private DBKeywordsFolder keywordsFolder;
    private DBDatatypeFolder datatypeFolder;
    private DBCatalogFolder catalogFolder;

    public void init(DBCConnection cnx) {
        super.init(cnx, "Tree.DBServer", null, null, null, "", false);
        ensureLoading();
    }


    @Override
    public void invalidate() {
        super.invalidate();
    }

    @Override
    public String getSQL() {
        return null;
    }

    public String getCatalogTerm() {
        return catalogTerm;
    }

    @Override
    protected synchronized void loadChildren() throws SQLException {

        if (connection == null || connection.getConnection() == null) {
            preferredLabel = ("RETRIEVING INFO...");
            catalogTerm = "Database";
            return;
        }
        DatabaseMetaData databaseMetaData = connection.getMetaData();

        {
            DBFunctionFolder item = getConnection().getFactory().newInstance(DBFunctionFolder.class);
            item.init(getConnection());
            addChild(functionFolder=item);
        }
        {
            DBKeywordsFolder item = getConnection().getFactory().newInstance(DBKeywordsFolder.class);
            item.init(getConnection());
            addChild(keywordsFolder= item);
        }
        {
            DBDatatypeFolder item = getConnection().getFactory().newInstance(DBDatatypeFolder.class);
            item.init(getConnection());
            addChild(datatypeFolder= item);
        }
        {
            DBCatalogFolder item = getConnection().getFactory().newInstance(DBCatalogFolder.class);
            item.init(getConnection());
            addChild(catalogFolder=item);
        }
        preferredLabel = databaseMetaData.getDatabaseProductName();

    }

    public String getPreferredLabel() {
        return preferredLabel;
    }

    public SQLObjectTypes getType() {
        return SQLObjectTypes.DATABASE;
    }

    @Override
    public String getStringPath() {
        return "/";
    }

    public String getDropSQL() {
        return getName();
    }

    public DBCatalogFolder getCatalogFolder() {
        ensureLoading();
        return catalogFolder;
    }

    public DBDatatypeFolder getDatatypeFolder() {
        ensureLoading();
        return datatypeFolder;
    }

    public DBFunctionFolder getFunctionFolder() {
        ensureLoading();
        return functionFolder;
    }

    public DBKeywordsFolder getKeywordsFolder() {
        ensureLoading();
        return keywordsFolder;
    }
    
}
