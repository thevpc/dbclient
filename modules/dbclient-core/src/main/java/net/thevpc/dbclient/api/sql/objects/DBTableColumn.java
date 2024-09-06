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
package net.thevpc.dbclient.api.sql.objects;

import net.thevpc.common.prs.plugin.Extension;
import net.thevpc.dbclient.api.sql.DBCConnection;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 4 août 2005 14:54:07
 */
@Extension(group = "sql")
public interface DBTableColumn extends DBObject {

    public void init(DBCConnection session, ResultSet rs) throws SQLException;

    public void setTable(DBTable table);

    public String getTableName();

    public String getColumnName();

    public DBTable getTable();

    public boolean isNullable();

    public boolean isPk();

    public int getPkSeq();

    public int getSqlType();

    public String getSqlTypeName();

    public int getPrecision();

    public int getSize();

    public String getSQL(String prototype);

    public void setSqlType(int sqlType);

    public void setPrecision(int precision);

    public void setSize(int size);

    public void setNullable(boolean nullable);

    public Object getValue(ResultSet resultSet, int index) throws SQLException;

    public void reloadPk() throws SQLException;

    public boolean isInsertable();

    public void setInsertable(boolean insertable);

    public String getNativeTypeName();

    public String getDropSQL();
}
