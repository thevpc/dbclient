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

package net.thevpc.dbclient.plugin.tool.recordeditor.propeditor;

import net.thevpc.dbclient.api.sql.SQLRecord;
import net.thevpc.dbclient.api.sql.objects.DBTable;
import net.thevpc.dbclient.api.sql.objects.DBTableColumn;

import javax.swing.*;
import java.awt.*;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 29 juil. 2006 03:35:44
 */
public interface TableView {
    ColumnView getFieldEditor(DBTableColumn f);

    JComponent getComponent();

    DBTable getTable();

    Component getMainAncestor();

    @Deprecated
    void setMainAncestor(Component mainAncestor);

    SQLRecord getRecord();

    void setRecord(SQLRecord record);

    boolean isInsertMode();

    void setInsertMode(boolean insertMode);


    public void showInfo(String message) ;

    public void showError(String message) ;
    public void showWarning(String message) ;
}
