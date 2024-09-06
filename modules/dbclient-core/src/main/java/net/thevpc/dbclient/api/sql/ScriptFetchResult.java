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

package net.thevpc.dbclient.api.sql;

import java.util.ArrayList;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 3 juil. 2006 09:56:14
 */
public class ScriptFetchResult {
    public ScriptFetchResult() {
    }

    private ArrayList<ScriptFetchError> errors = new ArrayList<ScriptFetchError>();

    public int count_q_s = 0;
    public int count_q_f = 0;
    public int count_u_s = 0;
    public int count_u_f = 0;
    public int count_s_s = 0;
    public int count_s_f = 0;
    public boolean shouldUpdateModel = false;

    public void error(int index, Throwable e) {
        errors.add(new ScriptFetchError(index, e));
    }

    public boolean isSuccessful() {
        return errors.size() == 0;
    }

    public boolean isFaulty() {
        return errors.size() != 0;
    }

    public int size() {
        return count_q_s + count_q_f + count_u_s + count_u_f + count_s_s + count_s_f;
    }
}
