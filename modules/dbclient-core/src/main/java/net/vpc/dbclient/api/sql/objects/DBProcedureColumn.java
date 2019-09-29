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
package net.vpc.dbclient.api.sql.objects;

import net.vpc.prs.plugin.Extension;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 4 août 2005 14:54:07
 */
@Extension(group = "sql")
public interface DBProcedureColumn extends DBObject {

    public void init(DBProcedure dbTableInfos, String catalog, String schema, String procedure, String name, int sqlType, boolean nullable);

    public DBProcedure getProcedure();

    public String getProcedureName();
    
    public String getColumnName();

    public boolean isNullable();

    public int getSqlType();

    public int getPrecision();

    public int getSize();

    public String getSQL(String prototype);

    public void setSqlType(int sqlType);

    public void setPrecision(int precision);

    public void setSize(int size);

    public void setNullable(boolean nullable);
}
