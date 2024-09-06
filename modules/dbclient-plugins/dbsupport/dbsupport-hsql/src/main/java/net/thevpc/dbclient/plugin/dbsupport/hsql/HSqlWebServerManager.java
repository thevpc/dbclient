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
package net.thevpc.dbclient.plugin.dbsupport.hsql;

import net.thevpc.common.swing.util.ProcessWatcher;
import net.thevpc.common.swing.util.ProcessWatcherHandler;
import net.thevpc.dbclient.api.pluginmanager.DBCPlugin;
import net.thevpc.common.prs.plugin.Inject;
import net.thevpc.dbclient.plugin.tool.servermanager.DBCServerInfo;
import net.thevpc.dbclient.plugin.tool.servermanager.DBCServerInstance;
import net.thevpc.dbclient.plugin.tool.servermanager.DBCServerManagerHandler;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import net.thevpc.common.swing.util.ClassPath;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 4 juin 2007 16:23:55
 */
public class HSqlWebServerManager implements DBCServerManagerHandler {

    public static final int DEFAULT_PORT = 9001;
    public static final String TYPE = "HSql Web Server";
    @Inject
    private DBCPlugin plugin;

    public HSqlWebServerManager() {
    }

    public DBCServerInfo showDialog(Component parent, DBCServerInfo serverInfo) throws Exception {
        HSqlServerInfoEditor n = plugin.instantiate(HSqlServerInfoEditor.class);
        n.init(this);
        return n.showDialog(parent, serverInfo);
    }

    public DBCServerInfo createServerInfo() {
        return new HSqlServerInfo();
    }

    public DBCPlugin getPlugin() {
        return plugin;
    }

    public String getType() {
        return TYPE;
    }

    public DBCServerInstance startServer(final DBCServerInstance instance, DBCServerInfo info) throws Exception {
        HSqlServerInfo vinfo = (HSqlServerInfo) info;
        File folderFile = new File(plugin.getApplication().rewriteString(vinfo.getDatabasePath()));
        folderFile.mkdirs();
        if (!folderFile.exists() && !folderFile.isDirectory()) {
            throw new IllegalArgumentException(folderFile.getPath() + " is an unvalid Folder");
        }

        String java_home = System.getProperty("java.home");
        ArrayList<String> cmds = new ArrayList<String>();
        //-h <host>] [-p <portnumber>
        cmds.add(java_home + "/bin/java");
        cmds.add("-classpath");
        cmds.add(getNetworkManagerClassPath(vinfo));
        cmds.add("org.hsqldb.WebServer");
        cmds.add("-database.0");
        cmds.add("file:data");
        cmds.add("-dbname.0");
        cmds.add("xdb");
        cmds.add("-port");
        cmds.add(String.valueOf(info.getPort() <= 0 ? DEFAULT_PORT : info.getPort()));
        Process process = Runtime.getRuntime().exec(cmds.toArray(new String[cmds.size()]), null, folderFile);
        ProcessWatcher w = new ProcessWatcher(process, new ProcessWatcherHandler() {

            public void started(Process process) {
                instance.getLog().debug("HSql Network Server started...");
            }

            public void stdout(Process process, String line) {
                instance.getLog().trace(line);
            }

            public void stderr(Process process, String line) {
                instance.getLog().error(line);
            }

            public void ended(Process process, int value) {
                instance.getLog().debug("HSql Network Server Shutdown. Exit Value :" + value);
                instance.setStopped(true);
            }

            public void error(Process process, Throwable th) {
                instance.getLog().error(th);
            }
        });
        w.start();
        return instance;
    }

    public boolean stopServer(final DBCServerInstance inst) throws Exception {
        throw new IllegalArgumentException("Not Yet Supported");
    }

    public void attachServer(DBCServerInstance instance) {
        //
    }

    private String getNetworkManagerClassPath(HSqlServerInfo info) {
        HSqlClassPathType type = info.getClassPathType();
        switch (type) {
            case PLUGIN: {
                DBCPlugin p = plugin.getApplication().getPluginManager().getValidPlugin(info.getLibPluginId());
                if (p == null) {
                    throw new IllegalArgumentException("Plugin " + info.getLibPluginId() + " not found");
                }
                ClassPath cp = ClassPath.getSystemClassPath();
                cp.addURLs(p.getDescriptor().getPluginAndLibsURLs());
                return cp.toString();
            }
            case FOLDER: {
                String folderName = info.getLibFolderPath();
                if (folderName == null) {
                    throw new IllegalArgumentException("Invalid HSql server lib Folder");
                }
                folderName = plugin.getApplication().rewriteString(folderName);
                if (!new File(folderName, "hsqldb.jar").exists()) {
                    throw new IllegalArgumentException("Invalid HSql server lib Folder : " + folderName);
                }
                ClassPath s = new ClassPath();
                for (File file : new File(folderName).listFiles()) {
                    try {
                        s.add(file.getCanonicalPath());
                    } catch (IOException ex) {
                        s.add(file.getAbsolutePath());
                    }
                }
                return s.toString();
            }
        }
        throw new IllegalArgumentException("Invalid HSql server config");
    }
}
