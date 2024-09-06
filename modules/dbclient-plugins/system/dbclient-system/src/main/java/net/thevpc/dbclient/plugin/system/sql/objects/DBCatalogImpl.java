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
import net.thevpc.dbclient.api.sql.objects.DBSchema;
import net.thevpc.dbclient.api.sql.objects.SQLObjectTypes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;


/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 13 juil. 2005 14:31:48
 */
public class DBCatalogImpl extends DBObjectFolderImpl implements DBCatalog {
    private boolean isDefault;

    public void init(DBCConnection connection, ResultSet rs) throws SQLException {
        this.init(connection, rs.getString("TABLE_CAT"));
    }

    public void init(DBCConnection connection, String name) throws SQLException {
        super.init(connection, "Tree.Catalog", null, null, null, name == null ? "" : name, false);
        String defaultCatalog = getConnection().getCatalog();
        if ("".equals(defaultCatalog)) {
            defaultCatalog = null;
        }
        if ("".equals(name)) {
            name = null;
        }
        isDefault = name == defaultCatalog || (defaultCatalog!=null && defaultCatalog.equalsIgnoreCase(name));
    }

    @Override
    protected void loadChildren() throws SQLException {
//        this.addSchema(new Schema(dbClient,""));
        ResultSet rs = null;
        try {
            rs = getConnection().getMetaData().getSchemas();
//        boolean shemaForCatalog = rs.getMetaData().getColumnCount() == 2;
            while (rs.next()) {
                DBSchema schemaNode = getConnection().getFactory().newInstance(DBSchema.class);
                schemaNode.init(getConnection(), rs, getName());
                String catalogName = schemaNode.getCatalogName();
                if (catalogName == null || catalogName.length() == 0 || this.getName().equalsIgnoreCase(catalogName)) {
                    this.addChild(schemaNode);
                }
            }
            rs.close();
        } catch (SQLException ex) {
            getConnection().getLoggerProvider().getLogger(DBCatalogImpl.class.getName()).log(Level.SEVERE,"Unable to Load Catalog Content",ex);
        }
        if (children.isEmpty()) {
            DBSchema item = getConnection().getFactory().newInstance(DBSchema.class);
            item.init(getConnection(), "", getName());
            this.addChild(item);
        }
    }

//    public Icon getIcon() {
//        return IconSetManager.getIcon("ExplorerCatalogIcon");
//    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public SQLObjectTypes getType() {
        return SQLObjectTypes.CATALOG;
    }

    @Override
    public String getCatalogName() {
        return getName();
    }

    public String getDropSQL() {
        return getFullName();
    }
}
