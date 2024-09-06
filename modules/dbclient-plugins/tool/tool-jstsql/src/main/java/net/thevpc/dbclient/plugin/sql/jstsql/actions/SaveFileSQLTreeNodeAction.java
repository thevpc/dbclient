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

package net.thevpc.dbclient.plugin.sql.jstsql.actions;

import net.thevpc.dbclient.api.actionmanager.DBCActionLocation;
import net.thevpc.dbclient.api.actionmanager.DBCTreeNodeAction;
import net.thevpc.dbclient.api.pluginmanager.DBCPluginSession;
import net.thevpc.dbclient.api.sql.objects.DBObject;
import net.thevpc.dbclient.api.viewmanager.SQLFileChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class SaveFileSQLTreeNodeAction extends DBCTreeNodeAction implements ActionListener {
    private TreeSQLGenerator treeSQLGenerator;

    public SaveFileSQLTreeNodeAction(DBCPluginSession pluginSession, TreeSQLGenerator treeSQLGenerator, boolean popup, boolean toolbar) {
        super("Action.SaveFileSQLNodeAction." + treeSQLGenerator.getName());
        setPluginSession(pluginSession);
        //not to show override message
        setMessageId(null);
        setIconId(null);
        setMessageId("Action." + treeSQLGenerator.getName());
        setIconId("Action.SaveFileSQLNodeAction");
        this.treeSQLGenerator = treeSQLGenerator;
        if (popup) {
            addLocationPath(DBCActionLocation.POPUP, "/sql-ddl/SQLSave");
        }
        if (toolbar) {
            addLocationPath(DBCActionLocation.TOOLBAR, "/");
        }
    }

    @Override
    public void putValue(String key, Object newValue) {
        if (key != null && Action.NAME.equals(key) && getMessageId() != null && getMessageId().equals(newValue)) {
            String s = treeSQLGenerator.getDefaultName();
            if (s != null) {
                newValue = s;
            }
        }
        super.putValue(key, newValue);
    }

    public boolean shouldEnable(DBObject activeNode, DBObject[] selectedNodes) {
        return (this.treeSQLGenerator.isEnabled(activeNode, selectedNodes));
    }

    public void actionPerformedImpl(ActionEvent e) throws Throwable {
        final SQLFileChooser ch = getSession().getFactory().newInstance(SQLFileChooser.class);
        JFileChooser c = ch.toFileChooser();
        c.setFileSelectionMode(JFileChooser.FILES_ONLY);
        DBObject[] nodes = getSelectedNodes();
        for (DBObject node : nodes) {
            c.setSelectedFile(new File(node.getName() + ".sql"));
            if (JFileChooser.APPROVE_OPTION == c.showSaveDialog((Component) e.getSource())) {
                File f = ch.getSelectedSQLFile();
                PrintStream fw = new PrintStream(new FileOutputStream(f));
                treeSQLGenerator.generateSQL(fw, new DBObject[]{node});
                fw.close();
            }
        }
    }
}
