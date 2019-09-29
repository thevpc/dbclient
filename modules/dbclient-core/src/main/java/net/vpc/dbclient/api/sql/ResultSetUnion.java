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
package net.vpc.dbclient.api.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * ResultSet Implementation as an ordered union (concat) of resultSets
 *
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 27 mars. 2009
 */
public class ResultSetUnion extends DefaultResultSetWrapper {
    private List<ResultSet> handledInstances;
    private int handledInstanceIndex;

    public ResultSetUnion(ResultSet... handledInstances) {
        this(Arrays.asList(handledInstances));
    }

    public ResultSetUnion(List<ResultSet> handledInstances) {
        super(null,handledInstances.get(0));
        this.handledInstances = handledInstances;
        handledInstanceIndex = 0;
    }

    public boolean next()
            throws SQLException {
        boolean b = handledInstance.next();
        if (b) {
            return true;
        }
        handledInstanceIndex++;
        if (handledInstanceIndex < handledInstances.size()) {
            handledInstance = handledInstances.get(handledInstanceIndex);
            return next();
        }
        return false;
    }


}
