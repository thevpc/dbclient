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

package net.vpc.dbclient.plugin.dbsupport.hsql;

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
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import net.vpc.util.ClassPath;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 4 juin 2007 16:23:55
 */
public class HSqlServerManager implements DBCServerManagerHandler {
    public static final int DEFAULT_PORT = 9001;
    public static final String TYPE = "HSql Server";
    @Inject(type = DBCPlugin.class)
    private HSqlPlugin plugin;

    public HSqlServerManager() {
    }

    public String getType() {
        return TYPE;
    }

    public DBCPlugin getPlugin() {
        return plugin;
    }


    public DBCServerInfo createServerInfo() {
        return new HSqlServerInfo();
    }

    public DBCServerInfo showDialog(Component parent, DBCServerInfo info) throws Exception {
        HSqlServerInfoEditor n = plugin.instantiate(HSqlServerInfoEditor.class);
        n.init(this);
        return n.showDialog(parent, info);
    }

    public DBCServerInstance startServer(final DBCServerInstance instance, DBCServerInfo info) throws Exception {
        HSqlServerInfo vinfo = (HSqlServerInfo) info;
        File folderFile = new File(plugin.getApplication().rewriteString(vinfo.getDatabasePath()));
        folderFile.mkdirs();
        if (!folderFile.exists() && !folderFile.isDirectory()) {
            throw new IllegalArgumentException(folderFile.getPath() + " is an unvalid Folder");
        }

        String java_home = System.getProperty("java.home");
        //String java_class_path = System.getProperty("java.class.path");
        ArrayList<String> cmds = new ArrayList<String>();
        //-h <host>] [-p <portnumber>
        cmds.add(java_home + "/bin/java");
        cmds.add("-classpath");
        cmds.add(getNetworkManagerClassPath(vinfo));
        cmds.add("org.hsqldb.server.Server");
        cmds.add("-database.0");
        cmds.add("file:" + folderFile.getCanonicalPath()+"/"+vinfo.getDBName());
        cmds.add("-dbname.0");
        cmds.add(vinfo.getDBName());
        cmds.add("-port");
        cmds.add(String.valueOf(vinfo.getPort() <= 0 ? DEFAULT_PORT : vinfo.getPort()));
        StringBuilder sb = new StringBuilder();
        for (String i : cmds) {
            if (sb.length() > 0) {
                sb.append(" ");
            }
            sb.append(i);
        }
        plugin.getLogger(HSqlServerManager.class.getName()).log(Level.INFO, "Running new Process : {0}", sb.toString());
        Process process = Runtime.getRuntime().exec(cmds.toArray(new String[cmds.size()]), null, folderFile);
        ProcessWatcher w = new ProcessWatcher(process, new ProcessWatcherHandler() {
            public void started(Process process) {
                plugin.getLogger(HSqlServerManager.class.getName()).log(Level.INFO, "HSql Network Server started...");
                instance.getLog().debug("HSql Network Server started...");
            }

            public void stdout(Process process, String line) {
                plugin.getLogger(HSqlServerManager.class.getName()).log(Level.INFO, line);
                instance.getLog().trace(line);
            }

            public void stderr(Process process, String line) {
                plugin.getLogger(HSqlServerManager.class.getName()).log(Level.SEVERE, line);
                instance.getLog().error(line);
            }

            public void ended(Process process, int value) {
                plugin.getLogger(HSqlServerManager.class.getName()).log(Level.INFO, "HSql Network Server Shutdown. Exit Value :{0}", value);
                instance.setStopped(true);
            }

            public void error(Process process, Throwable th) {
                plugin.getLogger(HSqlServerManager.class.getName()).log(Level.SEVERE, "Internal error",th);
                instance.getLog().error(th);
            }
        });
        w.start();
        return instance;
    }

    public boolean stopServer(final DBCServerInstance inst) throws Exception {
        HSqlServerInfo vinfo = (HSqlServerInfo) inst.getServerInfo();
        String url = "jdbc:hsqldb:hsql://"+vinfo.getHostName()+":" + String.valueOf(vinfo.getPort() <= 0 ? DEFAULT_PORT : vinfo.getPort()) + "/"+vinfo.getDBName();
        String driver = "org.hsqldb.jdbcDriver";
        String login = "sa";
        String password = "";
        Connection connection = null;
        Statement statement = null;
        try {
            connection = plugin.getApplication().getDriverManager().getConnection(
                    url,
                    driver,
                    login,
                    password,
                    null
            );

            statement = connection.createStatement();
            statement.executeUpdate("SHUTDOWN");
            inst.getLog().debug("[ShutdownProcess] Shutdown complete");
            return true;
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

//        File folderFile = new File(plugin.getApplication().rewriteString(vinfo.getDatabasePath()));
//        folderFile.mkdirs();
//        if (!folderFile.exists() && !folderFile.isDirectory()) {
//            throw new IllegalArgumentException(folderFile.getPath() + " is an unvalid Folder");
//        }
//        String java_home = System.getProperty("java.home");
//        ArrayList<String> cmds = new ArrayList<String>();
//        cmds.add(java_home + "/bin/java");
//        cmds.add("-classpath");
//        cmds.add(getNetworkManagerClassPath(vinfo));
//        cmds.add("org.hsqldb.util.SqlTool");
//        if (vinfo.getPort() > 0) {
//            cmds.add("-port");
//            cmds.add(String.valueOf(vinfo.getPort() <= 0 ? DEFAULT_PORT : vinfo.getPort()));
//        }
//        cmds.add("--sql");
//        cmds.add("shutdown");
//        cmds.add(vinfo.getDBName());
//        Process process = Runtime.getRuntime().exec(cmds.toArray(new String[cmds.size()]), null, folderFile);
//        ProcessWatcher w = new ProcessWatcher(process, new ProcessWatcherHandler() {
//
//            public void started(Process process) {
//                inst.getLog().debug("[ShutdownProcess] Shutdown Requested");
//            }
//
//            public void stdout(Process process, String line) {
//                inst.getLog().debug("[ShutdownProcess] " + line);
//            }
//
//            public void stderr(Process process, String line) {
//                inst.getLog().debug("[ShutdownProcess] " + line);
//            }
//
//            public void ended(Process process, int value) {
//                if (value == 0) {
//                    inst.getLog().debug("[ShutdownProcess] Shutdown complete");
//                } else {
//                    inst.getLog().debug("[ShutdownProcess] Unable to Shutdown : ERROR " + value);
//                }
//            }
//
//            public void error(Process process, Throwable th) {
//                inst.getLog().error("[ShutdownProcess] Shutdown Error");
//                inst.getLog().error(th);
//            }
//        });
//        w.start();
//        return w.waitfor() == 0;
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
