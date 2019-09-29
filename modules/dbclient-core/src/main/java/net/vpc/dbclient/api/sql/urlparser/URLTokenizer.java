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
package net.vpc.dbclient.api.sql.urlparser;

public class URLTokenizer {
    private String url;
    private int index;

    public URLTokenizer(String url) {
        this.url = url;
    }

    public boolean skipChars(String token) {
        boolean b = true;
        for (int i = 0; i < token.length(); i++) {
            if (!skip(String.valueOf(token.charAt(i)))) {
                b = false;
            }
        }
        return b;
    }

    public boolean skip(String token) {
        if (index + token.length() <= url.length() && url.substring(index, index + token.length()).equals(token)) {
            index += token.length();
            return true;
        }
        return false;
    }

    /**
     * returns all remaining
     *
     * @return
     */
    public String nextToken() {
        if (index >= url.length()) {
            return null;
        }
        String s = url.substring(index);
        index = url.length();
        return s;
    }

    public URLToken nextToken(boolean consumeDelimiter, String... delimiters) {
        if (index >= url.length()) {
            return null;
        }
        int x = -1;
        String d = null;
        for (String delimiter : delimiters) {
            int y = url.indexOf(delimiter, index);
            if (y >= 0 && (x < 0 || y < x)) {
                x = y;
                d = delimiter;
            }
        }
        if (x >= 0) {
            URLToken t = new URLToken(url.substring(index, x), d, index);
            index = x + (consumeDelimiter ? d.length() : 0);
            return t;
        }
        URLToken t = new URLToken(url.substring(index), null, index);
        index = url.length() + 1;
        return t;
    }
}
