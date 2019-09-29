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

import net.vpc.prs.plugin.Extension;
import net.vpc.dbclient.api.sql.DBCConnection;

import java.io.Reader;
import java.util.Collection;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 13 juil. 2005 14:32:52
 */
@Extension(group = "sql")
public interface SQLParser extends Iterable<SQLStatement> {
    public void configure(DBCConnection connection);

    public DBCConnection getConnection();

    public SQLToken readToken();

    public SQLStatement readStatement();

    public SQLParser clone();

//    public void setDocument(String query);

    void setDocument(Reader query);

    //public long skipTokens(long i) throws IOException;

    //void setDocumentIndex(int documentIndex);

    //int getDocumentIndex();

    //SQLStatement createStatement(int statementType, Collection<SQLToken> tokens,int firstTokenIndex);

    void removeWordTokenResolver(WordTokenResolver validator);

    void addWordTokenResolver(WordTokenResolver validator);

    Collection<WordTokenResolver> getWordTokenResolvers();
    
    public void setSingleStatement(boolean value);
    public boolean isSingleStatement();
}
