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

package net.vpc.dbclient.plugin.toolbox.tools.commandpane;

import net.vpc.dbclient.api.DBCSession;
import net.vpc.dbclient.api.actionmanager.DBCAsynchActionListener;
import net.vpc.dbclient.api.sessionmanager.DBCSQLCommandPane;
import net.vpc.dbclient.plugin.system.sessionmanager.sqleditor.tool.DBCSQLCommandPaneToolImpl;
import net.vpc.swingext.PRSManager;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 15 juil. 2005 17:59:19
 */
public class SQLCommandPaneJdbcEscapeTool extends DBCSQLCommandPaneToolImpl {
    JButton dateButton;
    JButton timeButton;
    JButton timestampButton;
    JButton ojButton;
    JButton fnButton;
    JButton likeEscapeButton;

    public SQLCommandPaneJdbcEscapeTool() {

    }

    public void init(DBCSQLCommandPane client) {
        super.init(client);

        dateButton = PRSManager.createButton("DBCSessionView.SQLQueryJdbcEscapeTool.Date");
        addActionButton(dateButton);
        DBCSession session = client.getSession();
        dateButton.addActionListener(new DBCAsynchActionListener(dateButton.getText(),null, session) {
            public void actionPerformedImpl(final ActionEvent e) {
                getPane().getEditor().replaceSelection("{d 'yyyy-MM-dd'}");
                getPane().getEditor().getComponent().requestFocus();
            }

        });

        timeButton = PRSManager.createButton("DBCSessionView.SQLQueryJdbcEscapeTool.Time");
        addActionButton(timeButton);
        timeButton.addActionListener(new DBCAsynchActionListener(timeButton.getText(),null, session) {
            public void actionPerformedImpl(final ActionEvent e) {
                getPane().getEditor().replaceSelection("{t 'HH:mm:ss'}");
                getPane().getEditor().requestFocus();
            }

        });

        timestampButton = PRSManager.createButton("DBCSessionView.SQLQueryJdbcEscapeTool.Timestamp");
        addActionButton(timestampButton);
        timestampButton.addActionListener(new DBCAsynchActionListener(timestampButton.getText(),null, session) {
            public void actionPerformedImpl(final ActionEvent e) {
                getPane().getEditor().replaceSelection("{ts 'yyyy-MM-dd HH:mm:ss.f'}");
                getPane().getEditor().requestFocus();
            }

        });

        fnButton = PRSManager.createButton("DBCSessionView.SQLQueryJdbcEscapeTool.Function");
        addActionButton(fnButton);
        fnButton.addActionListener(new DBCAsynchActionListener(fnButton.getText(),null, session) {
            public void actionPerformedImpl(final ActionEvent e) {
                getPane().getEditor().replaceSelection("{fn the_function()}");
                getPane().getEditor().requestFocus();
            }

        });

        ojButton = PRSManager.createButton("DBCSessionView.SQLQueryJdbcEscapeTool.OuterJoin");
        addActionButton(ojButton);
        ojButton.addActionListener(new DBCAsynchActionListener(ojButton.getText(),null, session) {
            public void actionPerformedImpl(final ActionEvent e) {
                getPane().getEditor().replaceSelection("{oj the_table LEFT OUTER JOIN ON the_search_criteria}");
                getPane().getEditor().requestFocus();
            }

        });

        likeEscapeButton = PRSManager.createButton("DBCSessionView.SQLQueryJdbcEscapeTool.LikeEscape");
        addActionButton(likeEscapeButton);
        likeEscapeButton.addActionListener(new DBCAsynchActionListener(likeEscapeButton.getText(),null, session) {
            public void actionPerformedImpl(final ActionEvent e) {
                getPane().getEditor().replaceSelection("{escape '\\'}");
                getPane().getEditor().requestFocus();
            }

        });

    }

}
