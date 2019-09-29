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

import net.vpc.dbclient.api.sql.DBCConnection;

import java.io.Reader;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 3 juil. 2006 09:56:14
 */
public class SQLParserWrapper implements SQLParser, Cloneable {
    private SQLParser base;

    public SQLParserWrapper(SQLParser base) {
        this.base = base;
    }

    public SQLStatement readStatement() {
        return base.readStatement();
    }

    public SQLToken readToken() {
        return base.readToken();
    }

    public void setDocument(Reader query) {
        base.setDocument(query);
    }

//    public void setDocument(String query) {
//        base.setDocument(query);
//    }

//    public long skipTokens(long i) throws IOException {
//        return base.skipTokens(i);
//    }

    @Override
    public SQLParser clone() {
        return new SQLParserWrapper(base);
    }

//    public int getDocumentIndex() {
//        return base.getDocumentIndex();
//    }
//
//    public void setDocumentIndex(int documentIndex) {
//        base.setDocumentIndex(documentIndex);
//    }

    public SQLParser getBase() {
        return base;
    }

//    public SQLStatement createStatement(int statementType, Collection<SQLToken> tokens, int firstTokenIndex) {
//        return base.createStatement(statementType, tokens, firstTokenIndex);
//    }

    public Iterator<SQLStatement> iterator() {
        return base.iterator();
    }

    public void configure(DBCConnection connection) {
        base.configure(connection);
    }

    public DBCConnection getConnection() {
        return base.getConnection();
    }

    public void removeWordTokenResolver(WordTokenResolver validator) {
        base.removeWordTokenResolver(validator);
    }

    public void addWordTokenResolver(WordTokenResolver validator) {
        base.addWordTokenResolver(validator);
    }

    public Collection<WordTokenResolver> getWordTokenResolvers() {
        return base.getWordTokenResolvers();
    }

    @Override
    public boolean isSingleStatement() {
        return base.isSingleStatement();
    }

    @Override
    public void setSingleStatement(boolean value) {
        base.setSingleStatement(value);
    }
    
    
}
