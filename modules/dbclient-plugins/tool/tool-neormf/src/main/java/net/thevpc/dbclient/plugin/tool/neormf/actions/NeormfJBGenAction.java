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
package net.thevpc.dbclient.plugin.tool.neormf.actions;

import net.thevpc.dbclient.api.actionmanager.DBCActionLocation;
import net.thevpc.dbclient.api.actionmanager.DBCSessionAction;
import net.thevpc.dbclient.plugin.tool.neormf.NeormfPluginSession;
import org.vpc.neormf.jbgen.JBGenConnectionProvider;
import org.vpc.neormf.jbgen.JBGenMain;
import org.vpc.neormf.jbgen.util.JTextAreaTLog;

import java.awt.event.ActionEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import net.thevpc.common.swing.util.ClassPath;

public class NeormfJBGenAction extends DBCSessionAction {

    boolean inProgress = false;

    public NeormfJBGenAction() {
        super("Action.NeormfJBGen");
        addLocationPath(DBCActionLocation.MENUBAR, "/tools");
        addLocationPath(DBCActionLocation.TOOLBAR, "/");
//        setIcon(new ImageIcon(getClass().getResource("/net/vpc/dbclient/plugin/tool/neormf/neormf.png")));
    }

    public NeormfPluginSession getPluginSession() {
        return (NeormfPluginSession) super.getPluginSession();
    }

    public void revalidateAbility() {
        setEnabled(!inProgress && getPluginSession().isProjectCreated());
    }

    public void actionPerformedImpl(ActionEvent e) throws Throwable {
        try {
            if (!getPluginSession().isSupported()) {
                throw new IllegalArgumentException("Please Enable Neormf Support first in Neormf Settings");
            }
            File file = getPluginSession().getValidProjectFolder();
            inProgress = true;
            setEnabled(false);
            JBGenMain b = new JBGenMain();
            JTextAreaTLog log = getPluginSession().getNeormfConsole();
            log.clear();
            b.setLog(log);
            ClassPath cp = ClassPath.getSystemClassPath();
            cp.addURLs(getPluginSession().getPlugin().getDescriptor().getPluginAndLibsURLs());
            b.setClasspath(cp.toString());
            b.setConnectionProvider(new JBGenConnectionProvider() {

                public Connection getConnection(String url, String driverClass, String user, String password, Properties properties) throws SQLException, ClassNotFoundException {
                    return getPluginSession().getPlugin().getApplication().getDriverManager().getConnection(url, driverClass, user, password, properties);
                }
            });
            b.generate(file);
            getPluginSession().getNeormfExplorer().updateModel(null);
        } finally {
            inProgress = false;
            revalidateAbility();
        }
    }
}
