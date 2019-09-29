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

package net.vpc.dbclient.api.sql.parser;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 28 juin 2007 10:16:37
 */
public final class SQLTokenType {
    public static final int UNKNOWN = 0;
    public static final int COMMENTS_LINE_DASH = 1000;
    public static final int COMMENTS_LINE_SLASH = 1001;
    public static final int COMMENTS_MULTI_LINE = 1002;
    public static final int STR1 = 1003;
    public static final int STR2 = 1004;

    public static final int SELECT = 1;
    public static final int UPDATE = 2;
    public static final int CREATE = 3;
    public static final int DROP = 4;
    public static final int INSERT = 5;
    public static final int DELETE = 6;
    public static final int ALTER = 7;
    public static final int SET = 8;
    public static final int EXEC = 9;
    public static final int CALL = 10;
    public static final int GRANT = 11;
    public static final int DENY = 12;
    public static final int REVOKE = 13;

    public static final int BEGIN = 207;
    public static final int END = 208;
    public static final int GO = 209;

    public static final int IF = 307;

    public static final int TABLE = 109;
    public static final int VIEW = 110;
    public static final int PROCEDURE = 111;
    public static final int FUNCTION = 112;

    private SQLTokenType() {
    }

}
