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

package net.thevpc.dbclient.api.sessionmanager;

import net.thevpc.dbclient.api.DBCSession;
import net.thevpc.common.prs.plugin.Extension;
import net.thevpc.dbclient.api.sql.parser.SQLParser;
import net.thevpc.dbclient.api.sql.parser.SQLStatement;
import net.thevpc.dbclient.api.viewmanager.DBCComponent;
import net.thevpc.dbclient.api.viewmanager.DBCNamedFormat;
import net.thevpc.common.swing.iswing.IJTextComponent;
import net.thevpc.common.prs.log.TLog;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.Set;

/**
 * @author vpc
 */
@Extension(group = "ui.session")
public interface DBCSQLEditor extends DBCComponent, IJTextComponent {

    File getFile();

    void setText(Reader reader) throws IOException;

    void loadFile(File file) throws IOException;

    void setFile(File file);

    TLog getLog();

    void setLog(TLog log);

    DBCSession getSession();

    void setFormats(DBCNamedFormat[] formats);

    void reloadFormats();

    void highlightErrorStatement(SQLStatement q);

    public String getSelectedSQL();

    public int getCaretRow();

    public int getCaretColumn();

    public void addCaretListener(DBCCaretListener listener);

    public void removeCaretListener(DBCCaretListener listener);

    public void setLineImage(int index, String name, Image image);

    Set<Integer> getLineBreakPoints();

    void addLineBreakPoint(int i);

    void removeLineBreakPoint(int i);

    boolean isLineBreakPoint(int i);

    public void replaceSelection(String content);

    String getSQL();

    void setSQL(String text);

    SQLParser getParser();

    void setParser(SQLParser parser);

    void actionAutoComplete();

    void actionCancelAutoComplete();

    public boolean isSingleStatement();

    public void setSingleStatement(boolean singleStatement);
}
