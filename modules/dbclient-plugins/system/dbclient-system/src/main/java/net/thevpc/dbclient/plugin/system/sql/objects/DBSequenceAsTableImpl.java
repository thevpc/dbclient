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
import net.thevpc.dbclient.api.sql.objects.*;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 13 juil. 2005 14:33:48
 */
public class DBSequenceAsTableImpl extends DefaultDBObject implements DBSequenceAsTable {

    public SQLObjectTypes getType() {
        return SQLObjectTypes.SEQUENCE;
    }

    private DBTableColumnFolder columns;
    private DBTriggerFolder triggers;
    private DBTableConstraintsFolder constraints;
    private DBTableIndexFolder indices;
    public String typeName;
    public SQLObjectTypes type;

    public void init(DBCConnection cnx, ResultSet rs) throws SQLException {
        String TABLE_CAT = rs.getString("TABLE_CAT");
        String TABLE_SCHEM = rs.getString("TABLE_SCHEM");
        String TABLE_NAME = rs.getString("TABLE_NAME");
        String TABLE_TYPE = rs.getString("TABLE_TYPE");
        super.init(cnx, "Tree.Table." + TABLE_TYPE, TABLE_CAT, TABLE_SCHEM, null, TABLE_NAME, false);
        typeName = TABLE_TYPE;
//        String typeLower = typeName.toLowerCase();
        setSystemObject(cnx.isSystemType(typeName));
        type = cnx.getTypeByNativeName(typeName);
        setId("Tree.Sequence");
    }


    public String getTypeName() {
        return typeName;
    }

    public DBTableFolder getTableType() {
        return (DBTableFolder) getParentObject();
    }

    public String getPrefix() {
        StringBuilder sb = new StringBuilder();
        if (getCatalogName() != null && getCatalogName().length() > 0) {
            sb.append(getCatalogName()).append(".");
        }
        if (getSchemaName() != null && getSchemaName().length() > 0) {
            sb.append(getSchemaName()).append(".");
        }
        if (sb.length() > 0 && sb.charAt(sb.length() - 1) == '.') {
            sb.delete(sb.length() - 1, sb.length());
        }
        return sb.toString();
    }

    @Override
    protected void loadChildren() throws SQLException {
        {
            DBTableColumnFolder item = connection.getFactory().newInstance(DBTableColumnFolder.class);
            item.init(getConnection(), getCatalogName(), getSchemaName(), getName());
            columns = item;
            addChild(columns);
        }

        {
            DBTriggerFolder item = connection.getFactory().newInstance(DBTriggerFolder.class);
            item.init(getConnection(), getCatalogName(), getSchemaName(), getName());
            triggers = item;
            addChild(triggers);
        }
        {
            DBTableIndexFolder item = connection.getFactory().newInstance(DBTableIndexFolder.class);
            item.init(getConnection(), getCatalogName(), getSchemaName(), getName());
            indices = item;
            addChild(indices);
        }
        {
            DBTableConstraintsFolder item = connection.getFactory().newInstance(DBTableConstraintsFolder.class);
            item.init(getConnection(), getCatalogName(), getSchemaName(), getName());
            constraints = item;
            addChild(constraints);
        }
    }

    public DBTableColumnFolder getColumnsFolder() {
        ensureLoading();
        return columns;
    }


    @Override
    public String getStringPath() {
        DBObject parent = getParentObject();
        if (parent != null) {
            return super.getStringPath();///AllCatalogs//DBCLIENT/AllProcedures
        }
        return "/AllCatalogs/" + getCatalogName() + "/" + getSchemaName() + "/" + getTypeName() + "/" + getName();
    }

    public DBTableColumn[] getColumns() {
        return getColumnsFolder().getColumns();
    }

    public String getDropSQL() {
        return getFullName();
    }

}
