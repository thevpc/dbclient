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

package net.vpc.dbclient.plugin.tool.recordeditor.propeditor;

import net.vpc.dbclient.api.sql.objects.DBTableColumn;

import java.sql.Types;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 2 aout 2006 19:57:03
 */
public class DoubleColumnView extends NumberColumnView {
    public DoubleColumnView() {
        super(Double.class);
    }

    public void setValue(Object value) {
        Double b = (value instanceof Number) ? new Double(((Number) value).doubleValue()) : (value instanceof String) ? new Double((String) value) : null;
        super.setValue(b);
    }

    public boolean accept(DBTableColumn column) {
        int sqlType = column.getSqlType();
        return
                sqlType == Types.DOUBLE
                || sqlType == Types.REAL
                || sqlType == Types.FLOAT
                || sqlType == Types.DECIMAL
                || (sqlType == Types.NUMERIC && column.getPrecision()>0)
                ;
    }
}
