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

package net.vpc.dbclient.plugin.sql.jstsql;

import net.vpc.dbclient.api.sql.util.ReaderProvider;
import net.vpc.dbclient.plugin.sql.jstsql.evaluator.JSTSqlEvalContext;
import net.vpc.dbclient.plugin.sql.jstsql.evaluator.JSTSqlEvaluator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 29 août 2007 20:14:29
 */
public class JSTSqlReaderProvider implements ReaderProvider {
    private ReaderProvider baseProvider;
    private JSTSqlEvaluator jstsqlEvaluator;
    private JSTSqlEvalContext context;
    private ReaderProvider compiledReaderProvider;

    public JSTSqlReaderProvider(ReaderProvider baseProvider, JSTSqlEvaluator jstsqlEvaluator, JSTSqlEvalContext context) {
        this.baseProvider = baseProvider;
        this.jstsqlEvaluator = jstsqlEvaluator;
        this.context = context;
    }

    public Reader getReader() throws IOException {
        if (compiledReaderProvider == null) {
            compiledReaderProvider = jstsqlEvaluator.compileToReaderProvider(baseProvider, context);
        }
        Reader reader = compiledReaderProvider.getReader();
        return (reader instanceof BufferedReader) ? ((BufferedReader) reader) : new BufferedReader(reader);
    }

    public void close() throws IOException {
        jstsqlEvaluator.close();
        if (compiledReaderProvider != null) {
            compiledReaderProvider.close();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        close();
    }
}
