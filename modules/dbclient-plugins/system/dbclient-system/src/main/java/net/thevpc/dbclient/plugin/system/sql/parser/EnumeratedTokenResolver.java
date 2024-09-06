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

package net.thevpc.dbclient.plugin.system.sql.parser;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 28 juil. 2006 19:40:53
 */
public abstract class EnumeratedTokenResolver extends DefaultTokenResolver {
    private Collection<String> all;
    private TreeSet<String> ok;

    protected EnumeratedTokenResolver(int theType, String... all) {
        this(theType, Arrays.asList(all));
    }

    protected EnumeratedTokenResolver(int theType, Collection<String> all) {
        super(theType);
        this.all = new TreeSet<String>();
        for (String s : all) {
            this.all.add(s.toLowerCase());
        }
    }

    public boolean accept(char c, StringBuilder currentChars) throws IOException {
        if (currentChars.length() == 0) {
            ok = new TreeSet<String>(all);
        }
        String cur = currentChars.toString().toLowerCase();
        char c0 = Character.toLowerCase(c);
        int curLen = cur.length();
        for (Iterator<String> i = ok.iterator(); i.hasNext();) {
            String s = i.next();
            if (s.length() > curLen && s.substring(0, curLen).equalsIgnoreCase(cur) && Character.toLowerCase(s.charAt(curLen)) == c0) {
                return true;
            } else {
                i.remove();
            }
        }
        return false;
    }
}
