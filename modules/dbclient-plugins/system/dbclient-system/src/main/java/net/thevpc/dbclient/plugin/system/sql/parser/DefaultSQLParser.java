/**
 * ==================================================================== DBCLient
 * yet another Jdbc client tool
 *
 * DBClient is a new Open Source Tool for connecting to jdbc compliant
 * relational databases. Specific extensions will take care of each RDBMS
 * implementation.
 *
 * Copyright (C) 2006-2007 Taha BEN SALAH
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 * ====================================================================
 */
package net.thevpc.dbclient.plugin.system.sql.parser;

import net.thevpc.dbclient.api.sql.DBCConnection;
import net.thevpc.dbclient.api.sql.objects.DBDatatype;
import net.thevpc.dbclient.api.sql.objects.DBFunction;
import net.thevpc.dbclient.api.sql.parser.*;
import net.thevpc.dbclient.api.sql.util.SQLUtils;

import java.io.*;
import java.sql.SQLException;
import java.util.*;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com) @creationtime 3 juil. 2006
 * 09:56:14
 */
public class DefaultSQLParser implements SQLParser, Cloneable {

    protected PushbackReader charsReader;
    protected int documentIndex;
    protected boolean eof;
    public SQLToken token;
    private boolean ignoreWhites = false;
    private boolean ignoreComments = false;
    protected int documentOffset = documentIndex;
    protected TokenRichType type = TokenRichType.UNKNOWN;
    protected StringBuilder currentChars = new StringBuilder();
    private SQLToken[] tokensBuffer;
    private int tokensBufferPosition;
    protected final Object lock = new Object();
    protected Collection<TokenResolver> tokenResolvers = new ArrayList<TokenResolver>();
    protected Collection<WordTokenResolver> wordTokenResolvers = new ArrayList<WordTokenResolver>();
    protected String beginKeyWord = null;
    protected String endKeyWord = null;
    protected int currentTokenIndex = 0;
    private String goKeyWord = null;
    private boolean ifCausesBegin = false;
    private int rowIndex;
    private int columnIndex;
    private int lastNewLineColumnIndex;
    private String identifierQuoteString = "";
    private String extraNameCharacters = "";
    private DBCConnection connection;
    private boolean singleStatement;
//    public static void main(String[] args) {
//        DefaultSQLParser p=new DefaultSQLParser();
////        p.setDocument("12-");
//        p.setDocument("Insert \n" +
//                "Values .T_DRIVER\n" +
//                "Insert Into; ");
//        for (SQLStatement sqlStatement : p) {
//            for (SQLToken sqlToken : sqlStatement) {
//                System.out.println("sqlToken = " + sqlToken);
//            }
//        }
//

    //    }
    public DefaultSQLParser(String query, int pushbackBuffer) {
        setDocument(query == null ? null : new StringReader(query));
        this.tokensBufferPosition = pushbackBuffer;
        tokensBuffer = new SQLToken[tokensBufferPosition];
        configure(null);
    }

    public DefaultSQLParser(Reader reader, int pushbackBuffer) {
        setDocument(reader);
        this.tokensBufferPosition = pushbackBuffer;
        tokensBuffer = new SQLToken[tokensBufferPosition];
        configure(null);
    }

    public DefaultSQLParser() {
        this(2);
    }

    public DefaultSQLParser(int pushbackBuffer) {
        this((String) null, pushbackBuffer);
    }

    public DefaultSQLParser(String query) {
        this(query, 2);
    }

    public DefaultSQLParser(Reader reader) {
        this(reader, 2);
    }

    public void addTokenResolver(TokenResolver checker) {
        tokenResolvers.add(checker);
    }

    public void removeWordTokenResolver(WordTokenResolver validator) {
        wordTokenResolvers.remove(validator);
    }

    public void addWordTokenResolver(WordTokenResolver validator) {
        wordTokenResolvers.add(validator);
    }

    public Collection<WordTokenResolver> getWordTokenResolvers() {
        return Collections.unmodifiableCollection(wordTokenResolvers);
    }

    public SQLStatement readStatement() {
        if (isSingleStatement()) {
            return readSingleStatement();
        } else {
            return readScriptStatement();
        }
    }

    protected SQLStatement readSingleStatement() {
        ArrayList<SQLToken> found = new ArrayList<SQLToken>();
        SQLToken c;
        int firstTokenIndex = currentTokenIndex;
        int statementType = SQLStatement.UNKNOWN;
        while ((c = readToken()) != null) {
            found.add(c);
            if (statementType == SQLStatement.UNKNOWN) {
                switch (c.getGroup()) {
                    case SQLTokenGroup.KEYWORD: {
                        statementType = c.getGroup();
                        break;
                    }
                }
            }
        }
        boolean nonEmptyStatement = found.size() > 0;
        if (nonEmptyStatement && found.size() > 0) {
            currentTokenIndex += found.size();
        }
        return nonEmptyStatement ? createStatement(resolveStatementType(statementType), statementType, found, firstTokenIndex) : null;
    }

    protected SQLStatement readScriptStatement() {
        ArrayList<SQLToken> found = new ArrayList<SQLToken>();
        ArrayList<SQLToken> foundNoWhites = new ArrayList<SQLToken>();
        SQLToken c;
        int createWhat = SQLTokenType.UNKNOWN;
        int statementType = SQLStatement.UNKNOWN;
        int beginCount = 0;
        int firstTokenIndex = currentTokenIndex;
        boolean nonEmptyStatement;
        boolean endWillBreak = false;
        LabelStopReading:
        while ((c = readToken()) != null) {
            found.add(c);
            foundNoWhites.add(c);
            switch (c.getGroup()) {
                case SQLTokenGroup.SEPARATOR: {
                    if ((createWhat != SQLTokenType.PROCEDURE) && beginCount == 0 && c.getValue().equals(";")) {
                        c.addDecoration(SQLToken.DECORATION_TRAILING, null);
                        break LabelStopReading;
                    }
                    break;
                }
                case SQLTokenGroup.KEYWORD: {
                    switch (c.getType()) {
                        case SQLTokenType.IF: {
                            if (ifCausesBegin) {
                                beginCount++;
                            }
                            break;
                        }
                        case SQLTokenType.BEGIN: {
                            beginCount++;
                            if (statementType == SQLTokenType.CREATE) {
                                boolean foundSelect = false;
                                for (SQLToken sqlToken : found) {
                                    switch (sqlToken.getType()) {
                                        case SQLTokenType.SELECT:
                                        case SQLTokenType.UPDATE:
                                        case SQLTokenType.DELETE:
                                        case SQLTokenType.INSERT:
                                        case SQLTokenType.SET: {
                                            foundSelect = true;
                                            break;
                                        }
                                    }
                                }
                                if (!foundSelect) {
                                    endWillBreak = true;
                                }
                            }
                            break;
                        }
                        case SQLTokenType.END: {
                            beginCount--;
//                            if (beginCount <= 0) {
//                                if (endWillBreak) {
//                                    break LabelStopReading;
//                                }
//                            }
                            break;
                        }
                        case SQLTokenType.GO: {
                            if (beginCount == 0) {
                                //remove go word
                                c.addDecoration(SQLToken.DECORATION_TRAILING, null);
//                            found.remove(found.size() - 1);
                                break LabelStopReading;
                            }
                            break;
                        }
                        case SQLTokenType.ALTER:
                        case SQLTokenType.DROP:
                        case SQLTokenType.EXEC:
                        case SQLTokenType.INSERT:
                        case SQLTokenType.DELETE:
                        case SQLTokenType.UPDATE:
                        case SQLTokenType.REVOKE:
                        case SQLTokenType.DENY:
                        case SQLTokenType.GRANT:
//                        {
//                            if (statementType == SQLStatement.UNKNOWN) {
//                                statementType = c.getType();
//                            } else if (beginCount == 0) {
//                                found.remove(found.size() - 1);
//                                unreadToken(c);
//                                break LabelStopReading;
//                            }
//                            break;
//                        }
                        case SQLTokenType.CREATE: {
                            if (statementType == SQLStatement.UNKNOWN) {
                                statementType = c.getType();
                            } else if (statementType == SQLTokenType.SELECT || statementType == SQLTokenType.UPDATE || statementType == SQLTokenType.DELETE || statementType == SQLTokenType.INSERT //                                    //the is no begin keyword
                                    //                                    beginCount == 0
                                    //                                    //not a procedure because procedures may include create and drop keywords
                                    //                                    && createWhat != SQLTokenType.PROCEDURE
                                    //                                    //not a grant nor a revoke or deny
                                    //                                    && statementType  != SQLTokenType.GRANT
                                    //                                    && statementType != SQLTokenType.DENY
                                    //                                    && statementType != SQLTokenType.REVOKE
                                    //                                    //last non white token is not a separator
                                    //                                    && (foundNoWhites.size()==0 || (foundNoWhites.get(foundNoWhites.size()-1).group!=SQLTokenGroup.COMMENTS && foundNoWhites.get(foundNoWhites.size()-1).group!=SQLTokenGroup.WHITE))
                                    ) {
                                found.remove(found.size() - 1);
                                unreadToken(c);
                                break LabelStopReading;
                            }
                            break;
                        }
                        case SQLTokenType.FUNCTION:
                        case SQLTokenType.PROCEDURE:
                        case SQLTokenType.VIEW:
                        case SQLTokenType.TABLE: {
                            if (statementType == SQLTokenType.CREATE && createWhat == SQLTokenType.UNKNOWN) {
                                createWhat = c.getType();
                            }
                            break;
                        }

                        default: {
                            if (statementType == SQLStatement.UNKNOWN) {
                                statementType = c.getType();
                            }
                            break;
                        }
                    }
                    break;
                }
                case SQLTokenGroup.COMMENTS:
                case SQLTokenGroup.WHITE: {
                    foundNoWhites.remove(foundNoWhites.size() - 1);
                    //do nothing
                    break;
                }
            }
        }
        nonEmptyStatement = found.size() > 0;
        if (nonEmptyStatement && found.size() > 0) {
            currentTokenIndex += found.size();
        }
        return nonEmptyStatement ? createStatement(resolveStatementType(statementType), statementType, found, firstTokenIndex) : null;
    }

    public SQLStatementType resolveStatementType(int leadingToken) {
        switch (leadingToken) {
            case SQLTokenType.SELECT: {
                return SQLStatementType.QUERY;
            }
            case SQLTokenType.DELETE:
            case SQLTokenType.INSERT:
            case SQLTokenType.UPDATE: {
                return SQLStatementType.UPDATE_DATA;
            }
            case SQLTokenType.CREATE:
            case SQLTokenType.ALTER:
            case SQLTokenType.DROP: {
                return SQLStatementType.UPDATE_STRUCTURE;
            }
            case SQLTokenType.SET:
            case SQLTokenType.DENY:
            case SQLTokenType.GRANT:
            case SQLTokenType.REVOKE: {
                return SQLStatementType.UPDATE_DATA;
            }
            case SQLStatement.UNKNOWN: {
                return SQLStatementType.UNKNOWN;
            }
            default: {
                return SQLStatementType.UNKNOWN;
            }
        }
    }

    public SQLStatement createStatement(SQLStatementType statementType, int leadingToken, Collection<SQLToken> tokens, int firstTokenIndex) {
        return new DefaultSQLStatement(statementType, leadingToken, tokens, firstTokenIndex);
    }

    public String getBeginKeyWord() {
        return beginKeyWord;
    }

    public void setBeginKeyWord(String beginKeyWord) {
        this.beginKeyWord = beginKeyWord;
    }

    public String getEndKeyWord() {
        return endKeyWord;
    }

    public void setEndKeyWord(String endKeyWord) {
        this.endKeyWord = endKeyWord;
    }

    public String getGoKeyWord() {
        return goKeyWord;
    }

    public void setGoKeyword(String goKeyWord) {
        this.goKeyWord = goKeyWord;
    }

    private SQLToken readToken0() {
        if (ignoreWhites || ignoreComments) {
            while (true) {
                SQLToken x = readToken(documentIndex);
                if (x != null) {
                    documentIndex = x.getCharEndIndex();
                    int t = x.getGroup();
                    if ((ignoreWhites && t == SQLTokenGroup.WHITE) || (ignoreComments && t == SQLTokenGroup.COMMENTS)) {
                        continue;
                    }
                }
                return x;
            }
        } else {
            SQLToken x = readToken(documentIndex);
            if (x != null) {
                documentIndex = x.getCharEndIndex();
            }
            return x;
        }
    }

    public int getDocumentIndex() {
        return documentIndex;
    }

    private SQLToken readToken(int startOffset) {
        try {
            if (charsReader == null) {
                return null;
            }
            documentOffset = startOffset;
            type = TokenRichType.UNKNOWN;
            currentChars.delete(0, currentChars.length());
            boolean found = false;
            for (TokenResolver tokenChecker : tokenResolvers) {
//                if (isEof()) {
//                    break;
//                }
                TokenRichType foundType = tokenChecker.resolve(currentChars, this);
                if (foundType != null && foundType.group != SQLTokenGroup.UNKNOWN) {
                    type = foundType;
                    found = true;
                    break;
                }
            }
            if (!found) {
                if (!readAny()) {
                    charsReader.close();
                    charsReader = null;
                    token = null;
                    return token;
                }
            }
            token = new SQLToken(documentOffset, documentOffset + currentChars.toString().length(), type.group, type.type, currentChars.toString(), rowIndex, columnIndex);
            String v = token.getValue();
            int xmax = v.length();
            for (int i = 0; i < xmax; i++) {
                if (v.charAt(i) == '\n') {
                    rowIndex++;
                    lastNewLineColumnIndex = token.getCharStartIndex() + i + 1;
                }
            }
            columnIndex = token.getCharEndIndex() - lastNewLineColumnIndex;
            return token;
        } catch (IOException e) {
            throw new SQLParserException(e);
        }
    }

    public char readChar() throws IOException {
        int i = charsReader.read();
        if (i == -1) {
//            charsReader.close();
            eof = true;
        }
        return (char) i;
    }

    public void unreadChar(char c) throws IOException {
//        if (c!=-1) {
        charsReader.unread(c);
//        }
    }

    public boolean isIgnoreWhites() {
        return ignoreWhites;
    }

    public void setIgnoreWhites(boolean ignoreWhites) {
        this.ignoreWhites = ignoreWhites;
    }

    public boolean readAny() throws IOException {
        if (eof) {
            if (currentChars.length() > 0) {
                type = TokenRichType.UNKNOWN;
                return true;
            }
            return false;
        }
        char c = readChar();
        if (!eof) {
            currentChars.append(c);
            type = TokenRichType.UNKNOWN;
            return true;
        }
//        pushback(c);
        return false;
    }

//    public void setDocument(String query) {
//        setDocument(query == null ? (Reader) null : new StringReader(query));
    //    }
    public void setDocument(InputStream reader) throws IOException {
        setDocument(new InputStreamReader(reader));
    }

    public final void setDocument(Reader reader) {
        if (this.charsReader != null) {
            try {
                this.charsReader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        this.charsReader = reader == null ? null : new PushbackReader(reader, 2);
        documentIndex = 0;
        currentTokenIndex = 0;
        rowIndex = 0;
        columnIndex = 0;
        lastNewLineColumnIndex = 0;
        eof = false;
        token = null;
    }

    @Override
    public SQLParser clone() {
        try {
            SQLParser t = (SQLParser) super.clone();
            t.setDocument((Reader) null);
            return t;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public long skipTokens(long i) throws IOException {
        long x = 0;
        while (x < i) {
            readToken();
        }
        return x;
    }

    public boolean isIgnoreComments() {
        return ignoreComments;
    }

    public void setIgnoreComments(boolean ignoreComments) {
        this.ignoreComments = ignoreComments;
    }

    protected Map<String, Integer> createKeywordsMap() {
        Map<String, Integer> hashtable = new HashMap<String, Integer>();
        hashtable.put("select", SQLTokenType.SELECT);
        hashtable.put("update", SQLTokenType.UPDATE);
        hashtable.put("create", SQLTokenType.CREATE);
        hashtable.put("insert", SQLTokenType.INSERT);
        hashtable.put("delete", SQLTokenType.DELETE);
        hashtable.put("revoke", SQLTokenType.REVOKE);
        hashtable.put("grant", SQLTokenType.GRANT);
        hashtable.put("deny", SQLTokenType.DENY);
        hashtable.put("alter", SQLTokenType.ALTER);
        hashtable.put("if", SQLTokenType.IF);
        hashtable.put("call", SQLTokenType.CALL);
        if (getGoKeyWord() != null) {
            hashtable.put(getGoKeyWord(), SQLTokenType.GO);
        }
        if (getBeginKeyWord() != null) {
            hashtable.put(getBeginKeyWord(), SQLTokenType.BEGIN);
        }
        if (getEndKeyWord() != null) {
            hashtable.put(getEndKeyWord(), SQLTokenType.END);
        }
        return hashtable;
    }

    protected Map<String, Integer> createDatatypesMap() {
        return new HashMap<String, Integer>();
    }

    protected Map<String, Integer> createFunctionsMap() {
        return new HashMap<String, Integer>();
    }

    public final void configure(DBCConnection cnx) {
        tokenResolvers.clear();
        wordTokenResolvers.clear();
        this.connection = cnx;
        addTokenResolver(new WhitesResolver());
        addTokenResolver(new CommentsResolver());
        addTokenResolver(new JSTSQLResolver());
        addTokenResolver(new OperatorResolver());
        addTokenResolver(new DotResolver());
        addTokenResolver(new SeparatorResolver());
        addTokenResolver(new OpenParResolver());
        addTokenResolver(new CloseParResolver());
        addTokenResolver(new NumberResolver());
        addTokenResolver(new StringResolver());
        addTokenResolver(new WordResolver());
        if (cnx == null) {
            addWordTokenResolver(new CollectionWordTokenResolver(SQLTokenGroup.DATATYPE, createDatatypesMap(), SQLUtils.DEFAULT_DATATYPES));
            addWordTokenResolver(new FunctionCollectionConvertChecker(createFunctionsMap(), SQLUtils.DEFAULT_FUNCTIONS));
            addWordTokenResolver(new KeywordCollectionConvertChecker(createKeywordsMap(), SQLUtils.DEFAULT_KEYWORDS));
            addWordTokenResolver(new CollectionWordTokenResolver(SQLTokenGroup.CATALOG, null));
            addWordTokenResolver(new CollectionWordTokenResolver(SQLTokenGroup.PROCEDURE, null));
            addWordTokenResolver(new CollectionWordTokenResolver(SQLTokenGroup.TABLE, null));
            addWordTokenResolver(new CollectionWordTokenResolver(SQLTokenGroup.SCHEMA, null));

        } else {
            setGoKeyword(cnx.getSQLGoKeyword());
            try {
                setIdentifierQuoteString(cnx.getMetaData().getIdentifierQuoteString());
            } catch (SQLException e) {
                cnx.getSession().getApplication().getLogger(DefaultSQLParser.class.getName()).log(java.util.logging.Level.SEVERE, null, e);
            }
            try {
                setExtraNameCharacters(cnx.getMetaData().getExtraNameCharacters());
            } catch (SQLException e) {
                cnx.getSession().getLogger(DefaultSQLParser.class.getName()).log(java.util.logging.Level.SEVERE, null, e);
            }
            TreeSet<String> t = new TreeSet<String>();
            String[] all = new String[0];
            try {
                all = cnx.getSQLKeywords();
            } catch (SQLException e) {
                cnx.getSession().getLogger(DefaultSQLParser.class.getName()).log(java.util.logging.Level.SEVERE, null, e);
            }
            for (String keyword : all) {
                t.add(keyword.toUpperCase());
            }
            addWordTokenResolver(new KeywordCollectionConvertChecker(createKeywordsMap(), t));

            t = new TreeSet<String>();
            DBDatatype[] dtall = new DBDatatype[0];
            try {
                dtall = cnx.getDatatypes();
            } catch (SQLException e) {
                cnx.getSession().getLogger(DefaultSQLParser.class.getName()).log(java.util.logging.Level.SEVERE, null, e);
            }
            for (DBDatatype dt : dtall) {
                t.add(dt.getName().toUpperCase());
            }
            addWordTokenResolver(new DatatypeCollectionConvertChecker(createDatatypesMap(), t));

            t = new TreeSet<String>();
            DBFunction[] f = new DBFunction[0];
            try {
                f = cnx.getAllFunctions();
            } catch (SQLException e) {
                cnx.getSession().getLogger(DefaultSQLParser.class.getName()).log(java.util.logging.Level.SEVERE, null, e);
            }
            for (DBFunction keyword : f) {
                t.add(keyword.getName().toUpperCase());
            }
            addWordTokenResolver(new FunctionCollectionConvertChecker(createFunctionsMap(), t));
            addWordTokenResolver(new SQLCatalogWordTokenResolver());
            addWordTokenResolver(new SQLSchemaWordTokenResolver());
            addWordTokenResolver(new SQLTableWordTokenResolver());

//        t = new TreeSet<String>();
//        try {
//            ResultSet rs = null;
//            try {
//                rs = cnx.getConnection().getMetaData().getCatalogs();
//                while (rs.next()) {
//                    String x = rs.getString("TABLE_CAT");
//                    if (x != null && x.length() > 0) {
//                        t.add(x);
//                    }
//                }
//            } finally {
//                if (rs != null) {
//                    rs.close();
//                }
//            }
//        } catch (SQLException e) {
//            java.util.logging.Logger.getLogger(DefaultSQLParser.class.getName()).log(java.util.logging.Level.SEVERE, null, e);
//        }
//        registerWordLikeConvertChecker(new CollectionWordLikeConvertChecker(SQLTokenGroup.CATALOG, null, t));
//
//        t = new TreeSet<String>();
//        try {
//            ResultSet rs = null;
//            try {
//                rs = cnx.getConnection().getMetaData().getSchemas();
//                while (rs.next()) {
//                    String x = rs.getString("TABLE_SCHEM");
//                    if (x != null && x.length() > 0) {
//                        t.add(x);
//                    }
//                }
//            } finally {
//                if (rs != null) {
//                    rs.close();
//                }
//            }
//        } catch (SQLException e) {
//            java.util.logging.Logger.getLogger(DefaultSQLParser.class.getName()).log(java.util.logging.Level.SEVERE, null, e);
//        }
//        registerWordLikeConvertChecker(new CollectionWordLikeConvertChecker(SQLTokenGroup.SCHEMA, null, t));
//
//        t = new TreeSet<String>();
//        try {
//            ResultSet rs = null;
//            try {
//                rs = cnx.getConnection().getMetaData().getTables(null,null,null,null);
//                while (rs.next()) {
//                    String x = rs.getString("TABLE_NAME");
//                    if (x != null && x.length() > 0) {
//                        t.add(x);
//                    }
//                }
//            } finally {
//                if (rs != null) {
//                    rs.close();
//                }
//            }
//        } catch (SQLException e) {
//            java.util.logging.Logger.getLogger(DefaultSQLParser.class.getName()).log(java.util.logging.Level.SEVERE, null, e);
//        }
//        registerWordLikeConvertChecker(new CollectionWordLikeConvertChecker(SQLTokenGroup.TABLE, null, t));
        }
    }

    public DBCConnection getConnection() {
        return connection;
    }

    public SQLToken readToken() {
        synchronized (lock) {
            if (tokensBufferPosition < tokensBuffer.length) {
                return tokensBuffer[tokensBufferPosition++];
            } else {
                return readToken0();
            }
        }
    }

    /**
     * Push back a single character.
     *
     * @param c The character to push back
     * @throws SQLParserException If the pushback buffer is full, or if some
     * other I/O error occurs
     */
    public void unreadToken(SQLToken c) {
        synchronized (lock) {
            if (tokensBufferPosition == 0) {
                throw new SQLParserException("Pushback buffer overflow");
            }
            tokensBuffer[--tokensBufferPosition] = c;
        }
    }

    public void setDocumentIndex(int documentIndex) {
        this.documentIndex = documentIndex;
    }

    public boolean isEof() {
        return eof;
    }

    public Iterator<SQLStatement> iterator() {
        return new Iterator<SQLStatement>() {

            SQLStatement current = null;

            public boolean hasNext() {
                current = readStatement();
                return current != null;
            }

            public SQLStatement next() {
                return current;
            }

            public void remove() {
            }
        };
    }

    public boolean isIfCausesBegin() {
        return ifCausesBegin;
    }

    public void setIfCausesBegin(boolean ifCausesBegin) {
        this.ifCausesBegin = ifCausesBegin;
    }

    public String getIdentifierQuoteString() {
        return identifierQuoteString;
    }

    public void setIdentifierQuoteString(String identifierQuoteString) {
        this.identifierQuoteString = identifierQuoteString;
        if (this.identifierQuoteString == null) {
            this.identifierQuoteString = "";
        }
    }

    public String getExtraNameCharacters() {
        return extraNameCharacters;
    }

    public void setExtraNameCharacters(String extraNameCharacters) {
        this.extraNameCharacters = extraNameCharacters;
        if (this.extraNameCharacters == null) {
            this.extraNameCharacters = "";
        }
    }

    @Override
    public boolean isSingleStatement() {
        return singleStatement;
    }

    @Override
    public void setSingleStatement(boolean value) {
        this.singleStatement = value;
    }
}
