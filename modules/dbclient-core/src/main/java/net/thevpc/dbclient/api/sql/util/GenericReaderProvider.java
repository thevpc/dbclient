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

package net.thevpc.dbclient.api.sql.util;

import java.io.*;


/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 13 juil. 2005 14:32:52
 */
public class GenericReaderProvider implements ReaderProvider {
    private File temp;

    public GenericReaderProvider(Reader base) throws IOException {
        this(base, null);
    }

    public GenericReaderProvider(Reader base, File defaultRootFolder) throws IOException {
        BufferedReader bs = (base instanceof BufferedReader) ? (BufferedReader) base : new BufferedReader(base);
        temp = File.createTempFile("jrdp-", null, defaultRootFolder);
        PrintStream out = new PrintStream(temp);
        String line;
        while ((line = bs.readLine()) != null) {
            out.println(line);
        }
        out.close();
        base.close();

    }

    public Reader getReader() throws IOException {
        return new FileReader(temp);
    }

    public void close() throws IOException {
        if (temp != null) {
            temp.delete();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        close();
    }
}
