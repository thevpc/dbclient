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
package net.vpc.dbclient.plugin.dbsupport.mckoi;

import net.vpc.dbclient.api.pluginmanager.DBCPlugin;
import net.vpc.prs.plugin.Inject;
import net.vpc.dbclient.plugin.tool.servermanager.DBCServerInfo;
import net.vpc.dbclient.plugin.tool.servermanager.DBCServerInstance;
import net.vpc.dbclient.plugin.tool.servermanager.DBCServerManagerHandler;
import net.vpc.util.ProcessWatcher;
import net.vpc.util.ProcessWatcherHandler;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import net.vpc.util.ClassPath;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 4 juin 2007 16:23:55
 */
public class McKoiServerManager implements DBCServerManagerHandler {

    public static final int DEFAULT_PORT = 1527;
    public static final String TYPE = "McKoi Network Server";
    @Inject(type = DBCPlugin.class)
    private McKoiPlugin plugin;

    public McKoiServerManager() {
    }

    public String getType() {
        return TYPE;
    }

    public McKoiPlugin getPlugin() {
        return plugin;
    }

    public DBCServerInfo showDialog(Component parent, DBCServerInfo info) throws Exception {
        McKoiServerInfoEditor n = plugin.instantiate(McKoiServerInfoEditor.class);
        n.init(this);
        return n.showDialog(parent, info);
    }

    public DBCServerInfo createServerInfo() {
        return new McKoiServerInfo();
    }

    public DBCServerInstance startServer(final DBCServerInstance instance, DBCServerInfo info) throws Exception {
        McKoiServerInfo vinfo = (McKoiServerInfo) info;
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
        cmds.add("com.mckoi.runtime.McKoiDBMain");

        if (vinfo.getDatabasePath() != null && vinfo.getDatabasePath().length() > 0) {
            cmds.add("-dbpath");
            cmds.add(vinfo.getDatabasePath());
        }
        if (vinfo.getLogPath() != null && vinfo.getLogPath().length() > 0) {
            cmds.add("-logpath");
            cmds.add(vinfo.getLogPath());
        }
        cmds.add("-create");
        cmds.add(vinfo.getLogin());
        cmds.add(vinfo.getPassword());
        if (vinfo.getPort() > 0) {
            cmds.add("-jdbcport");
            cmds.add(String.valueOf(vinfo.getPort() <= 0 ? DEFAULT_PORT : vinfo.getPort()));
        }
        Process process = Runtime.getRuntime().exec(cmds.toArray(new String[cmds.size()]), null, folderFile);
        ProcessWatcher w = new ProcessWatcher(process, new ProcessWatcherHandler() {

            public void started(Process process) {
                instance.getLog().debug("McKoi Network Server started...");
            }

            public void stdout(Process process, String line) {
                instance.getLog().trace(line);
            }

            public void stderr(Process process, String line) {
                instance.getLog().error(line);
            }

            public void ended(Process process, int value) {
                instance.getLog().debug("McKoi Network Server Shutdown. Exit Value :" + value);
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
        String java_home = System.getProperty("java.home");
        ArrayList<String> cmds = new ArrayList<String>();
        //-h <host>] [-p <portnumber>
        cmds.add(java_home + "/bin/java");
        cmds.add("-classpath");
        ClassPath cp = ClassPath.getSystemClassPath();
        cp.addURLs(plugin.getDescriptor().getPluginAndLibsURLs());
        cmds.add(cp.toString());
        cmds.add("com.mckoi.runtime.McKoiDBMain");
        cmds.add("-shutdown");
        McKoiServerInfo vinfo = (McKoiServerInfo) inst.getServerInfo();
        if (!inst.getServerInfo().isLocal()) {
            cmds.add(vinfo.getHostName());
        }
        if (inst.getServerInfo().getPort() > 0) {
            cmds.add(String.valueOf(inst.getServerInfo().getPort() <= 0 ? DEFAULT_PORT : inst.getServerInfo().getPort()));
        }
        if (vinfo.getLogin() != null && vinfo.getLogin().length() > 0) {
            cmds.add(vinfo.getLogin());
        }
        if (vinfo.getPassword() != null && vinfo.getPassword().length() > 0) {
            cmds.add(vinfo.getPassword());
        }
        Process process = Runtime.getRuntime().exec(cmds.toArray(new String[cmds.size()]), null, null);
        ProcessWatcher w = new ProcessWatcher(process, new ProcessWatcherHandler() {

            public void started(Process process) {
                inst.getLog().debug("[ShutdownProcess] Shutdown Requested");
            }

            public void stdout(Process process, String line) {
                inst.getLog().debug("[ShutdownProcess] " + line);
            }

            public void stderr(Process process, String line) {
                inst.getLog().debug("[ShutdownProcess] " + line);
            }

            public void ended(Process process, int value) {
                if (value == 0) {
                    inst.getLog().debug("[ShutdownProcess] Shutdown complete");
                } else {
                    inst.getLog().debug("[ShutdownProcess] Unable to Shutdown : ERROR " + value);
                }
            }

            public void error(Process process, Throwable th) {
                inst.getLog().error("[ShutdownProcess] Shutdown Error");
                inst.getLog().error(th);
            }
        });
        w.start();
        return w.waitfor() == 0;
    }

    public void attachServer(DBCServerInstance instance) {
        //
    }

    private String getNetworkManagerClassPath(McKoiServerInfo info) {
        McKoiClassPathType type = info.getClassPathType();
        switch (type) {
            case PLUGIN: {
                DBCPlugin p = plugin.getApplication().getPluginManager().getValidPlugin(info.getLibPluginId());
                if (p == null) {
                    throw new IllegalArgumentException("Plugin " + info.getLibPluginId() + " not found");
                }
                ClassPath cp = ClassPath.getSystemClassPath();
                cp.addURLs(p.getDescriptor().getPluginAndLibsURLs());
            }
            case FOLDER: {
                String folderName = info.getLibFolderPath();
                if (folderName == null) {
                    throw new IllegalArgumentException("Invalid McKoi server lib Folder");
                }
                folderName = plugin.getApplication().rewriteString(folderName);
                if (!new File(folderName, "mckoidb.jar").exists()) {
                    throw new IllegalArgumentException("Invalid McKoi server lib Folder : " + folderName);
                }
                StringBuilder s = new StringBuilder();
                for (File file : new File(folderName).listFiles()) {
                    if (s.length() > 0) {
                        s.append(File.pathSeparator);
                    }
                    try {
                        s.append(file.getCanonicalPath());
                    } catch (IOException ex) {
                        s.append(file.getAbsolutePath());
                    }
                }
                return s.toString();
            }
        }
        throw new IllegalArgumentException("Invalid McKoi server config");
    }
}
