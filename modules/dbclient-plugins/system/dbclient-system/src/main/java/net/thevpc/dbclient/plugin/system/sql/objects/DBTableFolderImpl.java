/**
 * ====================================================================
 *             DBCLient yet another Jdbc client tool
 *
 * DBClient is a new Open Source Tool for connecting to jdbc
 * compliant relational databases. Specific extensions will take care of
 * each RDBMS implementation.
 *
 * Copyright (C) 2006-2007 Taha BEN SALAH
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
import net.thevpc.dbclient.api.sql.objects.DBSchema;
import net.thevpc.dbclient.api.sql.objects.DBTable;
import net.thevpc.dbclient.api.sql.objects.DBTableFolder;
import net.thevpc.dbclient.api.sql.objects.SQLObjectTypes;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 13 juil. 2005 14:32:34
 */
public class DBTableFolderImpl extends DBObjectFolderImpl implements DBTableFolder {
    private String typeName;

    public void init(DBCConnection session, ResultSet rs, String catalogName, String schemaName) throws SQLException {
        this.init(session, rs.getString(1), catalogName, schemaName);
    }

    public void init(DBCConnection session, String name, String catalogName, String schemaName) throws SQLException {
        super.init(session, "Tree.TableType." + name, catalogName, schemaName, null, name, false);
        typeName = name;
        setSystemObject(session.isSystemType(name));
    }

    public DBSchema getSchema() {
        return (DBSchema) getParentObject();
    }

    public void addTable(DBTable tn) {
        super.addChild(tn);
    }

    public DBTable[] getTablesArray() {
        ensureLoading();
        return children.toArray(new DBTable[children.size()]);
    }

    @Override
    protected void loadChildren() throws SQLException {
        String sn = getSchemaName();
        if (sn != null && sn.length() == 0) {
            sn = null;
        }
        String cn = getCatalogName();
        if (cn != null && cn.length() == 0) {
            cn = null;
        }
        ResultSet rs = getConnection().getMetaData().getTables(
                cn,
                sn,
                "%",
                new String[]{typeName});
        while (rs.next()) {
            DBTable item = getConnection().getFactory().newInstance(DBTable.class);
            item.init(getConnection(), rs);
            DBTable tn = item;
            String t = tn.getTypeName();
            if (typeName != null && typeName.equalsIgnoreCase(t)) {//workaround
                this.addTable(tn);
            }
        }
        rs.close();
    }

    @Override
    public String getSQL() {
        return null;
    }

    public String getTypeName() {
        return typeName;
    }

    public SQLObjectTypes getType() {
        return SQLObjectTypes.TABLE_TYPE;
    }
}
