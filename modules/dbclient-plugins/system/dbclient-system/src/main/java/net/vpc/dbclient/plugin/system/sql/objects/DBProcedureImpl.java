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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.logging.Level;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 13 juil. 2005 14:33:48
 */
public class DBProcedureImpl extends DefaultDBObject implements DBProcedure {
    public LinkedHashMap<String, DBProcedureColumn> allFields = new LinkedHashMap<String, DBProcedureColumn>();
    public short procType;

    public void init(DBCConnection connection, ResultSet rs) throws SQLException {
        super.init(connection, "Tree.Procedure", rs.getString("PROCEDURE_CAT"), rs.getString("PROCEDURE_SCHEM"), null, rs.getString("PROCEDURE_NAME"), false);
        this.procType = rs.getShort("PROCEDURE_TYPE");
    }

    @Override
    public String getProcedureName() {
        return getName();
    }

    
    public DBSchema getSchema() {
        return ((DBProcedureFolder) getParentObject()).getSchema();
    }

    public String getProcedureTerm() {
        String procedureTerm = "procedure";
        try {
            procedureTerm = getConnection().getMetaData().getProcedureTerm();
        } catch (SQLException ex) {
            getConnection().getLoggerProvider().getLogger(DBProcedureImpl.class.getName()).log(Level.SEVERE,"getProcedureTerm Failed",ex);
        }
        return procedureTerm;
    }

    public String getPrefix() {
        StringBuilder sb = new StringBuilder();
        if (getCatalogName().length() > 0) {
            sb.append(getCatalogName()).append(".");
        }
        if (getSchemaName().length() > 0) {
            sb.append(getSchemaName()).append(".");
        }
        if (sb.length() > 0 && sb.charAt(sb.length() - 1) == '.') {
            sb.delete(sb.length() - 1, sb.length());
        }
        return sb.toString();
    }

    @Override
    protected void loadChildren() throws SQLException {
        allFields.clear();

        ResultSet rs = getConnection().getMetaData().getProcedureColumns(
                getCatalogName(),
                getSchemaName(),
                getName(),
                null);

        while (rs.next()) {
            String colName = rs.getString("COLUMN_NAME");
//            short colType = rs.getShort("COLUMN_TYPE");
            int sqltype = rs.getInt("DATA_TYPE");
//            String typeName = rs.getString("TYPE_NAME");
            DBProcedureColumn item = connection.getFactory().newInstance(DBProcedureColumn.class);
            item.init(this, getCatalogName(), getSchemaName(), getName(), colName, sqltype, false);

            DBProcedureColumn field = item;
            allFields.put(field.getName(), field);
            addChild(field);

        }
        rs.close();

    }


    public DBProcedureColumn[] getColumns() {
        ensureLoading();
        Collection<DBProcedureColumn> c = allFields.values();
        return c.toArray(new DBProcedureColumn[c.size()]);
    }

    public SQLObjectTypes getType() {
        return SQLObjectTypes.PROCEDURE;
    }

    @Override
    public String getStringPath() {
        DBObject parent = getParentObject();
        if (parent != null) {
            return super.getStringPath();
        }
        return "/AllCatalogs/" + getCatalogName() + "/" + getSchemaName() + "/AllProcedures/" + getName();
    }

    public String getDropSQL() {
        return getFullName();
    }
}
