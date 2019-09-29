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

package net.vpc.dbclient.plugin.system.viewmanager;

import net.vpc.dbclient.api.DBCSession;
import net.vpc.prs.plugin.Inject;
import net.vpc.prs.plugin.Initializer;
import net.vpc.dbclient.api.viewmanager.SQLFileChooser;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Locale;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 20 juil. 2006 23:08:30
 */
public class SQLFileChooserImpl extends JFileChooser implements SQLFileChooser {
//    public SQLFileChooser() {
//        init();
//    }
    @Inject
    DBCSession session;

    public SQLFileChooserImpl() {
        super();
    }

    public Component getComponent() {
        return this;
    }

    public JFileChooser toFileChooser() {
        return this;
    }

    @Initializer
    public void init() {
        setLocale(Locale.getDefault());
        this.session = session;
        String sesLastDirectory = session.getConfig().getStringProperty("history.lastdir", null);
        File workingDir = sesLastDirectory == null ? null : new File(sesLastDirectory);
        setCurrentDirectory(workingDir);
        SqlFileFilter filter = new SqlFileFilter();
        addChoosableFileFilter(filter);
        setFileFilter(filter);
    }

    public int showSaveDialog(Component parent) throws HeadlessException {
        int ret = super.showSaveDialog(parent);
        if (JFileChooser.APPROVE_OPTION != ret) {
            return ret;
        }
        File selectedFile = getSelectedSQLFile();
        if (selectedFile != null) {
            if (selectedFile.exists()) {
                if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, "File Already exists, Override?", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE)) {
                    return JFileChooser.APPROVE_OPTION;
                }
                return JFileChooser.CANCEL_OPTION;
            }
            return JFileChooser.APPROVE_OPTION;
        }
        return CANCEL_OPTION;
    }

//    public File getSelectedFile() {
//        File f = super.getSelectedFile();
//        if (f == null || f.exists()) {
//            return f;
//        }
//        if (!f.getActionName().toLowerCase().endsWith(".sql")) {
//            return new File(f.getParentFile(), f.getActionName() + ".sql");
//        }
//        return f;
//    }

    public File getSelectedSQLFile() {
        File f = super.getSelectedFile();
        if (f != null && !f.exists() && !f.getName().toLowerCase().endsWith(".sql")) {
            return new File(f.getParentFile(), f.getName() + ".sql");
        }
        if (f != null) {
            File workingDir = (f.getParentFile());
            session.getConfig().setStringProperty("history.lastdir", workingDir.getPath());
        }
        return f;
    }

    public int showOpenDialog(Component parent) throws HeadlessException {
        return super.showOpenDialog(parent);
    }

}
