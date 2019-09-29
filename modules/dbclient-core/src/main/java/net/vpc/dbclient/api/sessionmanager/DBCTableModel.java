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

package net.vpc.dbclient.api.sessionmanager;

import net.vpc.dbclient.api.DBCSession;
import net.vpc.dbclient.api.pluginmanager.DBCPluggable;
import net.vpc.prs.plugin.Extension;
import net.vpc.dbclient.api.sql.objects.DBTable;
import net.vpc.dbclient.api.sql.objects.DBTableColumn;
import net.vpc.log.TLog;

import javax.swing.table.TableModel;
import java.beans.PropertyChangeListener;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author vpc
 */
@Extension(group = "ui.session")
public interface DBCTableModel extends TableModel, DBCPluggable {

    void close() throws SQLException;

//    String dbRepresentation(int column, Object value);

    void displayQuery(ResultSet resultSet, DBCResultTableWrapper w) throws SQLException;

    void displayError(String message) throws SQLException;

    void displayError(SQLException message) throws SQLException;

    void displayMessage(String title, String message) throws SQLException;

    void executeStatement(String query) throws SQLException;

    public void setColumnName(int column, String value);

    public String getDefaultColumnName(int column);

    String getQuery();

    boolean reexecuteQuery() throws SQLException;

    void setQuery(String lastQuery);

    void setWrapper(DBCResultTableWrapper wrapper);

    DBCSession getSession();

    Object[] getRow(int row);

    boolean isMoreRows();

    DBTableColumn getFieldNode(int i) throws SQLException;

    DBTable getTableNode() throws SQLException;

    void setTitle(String toolTipText);

    void addPropertyChangeListener(PropertyChangeListener listener);

    void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);

    void removePropertyChangeListener(PropertyChangeListener listener);

    void removePropertyChangeListener(String propertyName, PropertyChangeListener listener);

    String getTitle();

}
