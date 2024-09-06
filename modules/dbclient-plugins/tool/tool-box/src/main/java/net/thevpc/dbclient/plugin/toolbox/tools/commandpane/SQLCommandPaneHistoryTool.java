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

package net.thevpc.dbclient.plugin.toolbox.tools.commandpane;

import net.thevpc.dbclient.api.sessionmanager.DBCSQLCommandPane;
import net.thevpc.dbclient.plugin.system.SystemUtils;
import net.thevpc.dbclient.plugin.system.sessionmanager.sqleditor.tool.DBCSQLCommandPaneToolImpl;
import net.thevpc.common.swing.prs.PRSManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 15 juil. 2005 17:59:19
 */
public class SQLCommandPaneHistoryTool extends DBCSQLCommandPaneToolImpl {

    public SQLCommandPaneHistoryTool() {
        history = new ArrayList<String>();
        indexOfCurrentQuery = -1;
    }

    public void init(DBCSQLCommandPane client) {
        super.init(client);

        JButton addQuery = SystemUtils.prepareToolbarButton(PRSManager.createButton("DBCSessionView.SQLQueryHistoryTool.Add"));
        addActionButton(addQuery);
        addQuery.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                addQueryToHistory(getPane().getSQL());
            }

        });

        JButton removeQuery = SystemUtils.prepareToolbarButton(PRSManager.createButton("DBCSessionView.SQLQueryHistoryTool.Remove"));
        addActionButton(removeQuery);
        removeQuery.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                discardFromHistory(getPane().getSQL());
                showPreviousQuery();
                if (getHistoryIndex() < 0) {
                    getPane().setSQL("");
                }
            }

        });

        JButton previousQuery = SystemUtils.prepareToolbarButton(PRSManager.createButton("DBCSessionView.SQLQueryHistoryTool.Previous"));
        addActionButton(previousQuery);
        previousQuery.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                showPreviousQuery();
            }

        });

        JButton nextQuery = SystemUtils.prepareToolbarButton(PRSManager.createButton("DBCSessionView.SQLQueryHistoryTool.Next"));
        addActionButton(nextQuery);
        nextQuery.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                showNextQuery();
            }

        });


    }


    private int indexOfCurrentQuery;
    private ArrayList<String> history;

    public void clearHistory() {
        history.clear();
        indexOfCurrentQuery = -1;
    }

    public void showNextQuery() {
        int ind = indexOfCurrentQuery + 1;
        if (ind >= 0 && ind < history.size()) {
            indexOfCurrentQuery = ind;
            getPane().setSQL(history.get(indexOfCurrentQuery));
        }
    }

    public void showPreviousQuery() {
        int ind = indexOfCurrentQuery - 1;
        if (ind >= 0 && ind < history.size()) {
            indexOfCurrentQuery = ind;
            getPane().setSQL(history.get(indexOfCurrentQuery));
        }
    }

    public void addQueryToHistory(String query) {
        if (!history.contains(query)) {
            history.add(query);
            indexOfCurrentQuery = history.size() - 1;
        }
    }

    public boolean selectHistory(String query) {
        addQueryToHistory(getPane().getSQL());
        for (String aHistory : history) {
            if (aHistory.equals(query)) {
                getPane().setSQL(aHistory);
                return true;
            }
        }
        return false;
    }

    public boolean discardFromHistory(String query) {
        for (Iterator iterator = history.iterator(); iterator.hasNext();) {
            String s = (String) iterator.next();
            if (s.equals(query)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    public int getHistorySize() {
        return history.size();
    }

    public int getHistoryIndex() {
        return indexOfCurrentQuery;
    }


}
