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

import net.thevpc.common.prs.reflect.ClassFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 2 aout 2006 13:09:22
 */
public class DefaultClassFilter implements ClassFilter {
    ArrayList<Class> clzs;

    public DefaultClassFilter(Class... clz) {
        this(Arrays.asList(clz));
    }

    public DefaultClassFilter(Collection<Class> clz) {
        this.clzs = new ArrayList<Class>(clz);
    }

    public boolean accept(Class clz) {
        for (Class aClass : clzs) {
            if (aClass.isAssignableFrom(clz)) {
                return true;
            }
        }
        return false;
    }
}
