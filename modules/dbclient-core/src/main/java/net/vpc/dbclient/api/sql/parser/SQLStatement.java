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
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 3 juil. 2006 11:38:38
 */
public interface SQLStatement extends SQLPortion {
    public static final int UNKNOWN = -1;
//    public static final int OTHER = -2;
//    public static final int SELECT = 1;
//    public static final int UPDATE = 2;
//    public static final int DELETE = 3;
//    public static final int INSERT = 4;
//    public static final int CREATE = 5;
//    public static final int ALTER = 6;
//    public static final int DROP = 7;
//    public static final int EXEC = 8;

//    public static final int CREATE_TABLE = 13;
//    public static final int ALTER_TABLE = 14;
//    public static final int DROP_TABLE = 15;
//
//    public static final int CREATE_VIEW = 16;
//    public static final int ALTER_VIEW = 17;
//    public static final int DROP_VIEW = 18;
//
//    public static final int CREATE_PROCEDURE = 19;
//    public static final int ALTER_PROCEDURE = 20;
//    public static final int DROP_PROCEDURE = 21;

    /**
     * @return SQLTokenType.*
     */
    public SQLStatementType getStatementType();
    public int getStatementLeadingToken();


//    public boolean isIgnoreWhites();
//
//    public void setIgnoreWhites(boolean ignoreWhites);

    public SQLToken[] getTokens();

    public SQLDeclaration getTokenDeclaration(SQLToken alias);

    public SQLDeclaration[] getQueryTables();

    public boolean isEmpty();

    /**
     * return an equivalement statement by trimming white yokens and comments (when trimComments)
     *
     * @param trimComments if true trailing and finishing comments are removed too
     * @return SQLStatement
     */
    public SQLStatement trim(boolean trimComments);

    public int getLineStartIndex();

    public int getLineEndIndex();

//    public SQLDeclaration[] getTokenDeclarationsAt(int charIndex);

    int getNonWhiteLineStartIndex();
}
