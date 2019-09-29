/**
 * ====================================================================
 *             DBCLient yet another Jdbc client tool
 *
 * DBClient is a new Open Source Tool for connecting to jdbc
 * compliant relational databases. Specific extensions will take care of
 * each RDBMS implementation.
 *
 * Copyright (C) 2006-2007 Taha BEN SALAH
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
package net.vpc.dbclient;

import java.util.logging.LogRecord;
import net.vpc.dbclient.api.DBCApplication;
import net.vpc.dbclient.api.DBCApplicationImpl;
import net.vpc.dbclient.api.DBClientInfo;
import net.vpc.dbclient.api.configmanager.DBCLockedConfigException;
import net.vpc.dbclient.api.configmanager.DBCShouldRestartException;
import net.vpc.dbclient.api.viewmanager.DBCSplashScreen;
import net.vpc.jcmd.*;
import net.vpc.swingext.dialog.MessageDialogType;
import net.vpc.util.Chronometer;
import net.vpc.util.ProgressMonitor;

import java.io.File;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.CancellationException;
import java.util.logging.FileHandler;
import java.util.logging.Filter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.vpc.log.SimpleLogFormatter;
import net.vpc.swingext.SwingUtilities3;
import net.vpc.util.CancelException;

/**
 * This is Main Class for Launching DBClient.
 * <p/>
 * Launcher accepts several commandline arguments.
 * Usage si as Follows:
 * <pre>
 * <p/>
 *    DBClientMain [-nosplash] [-noautoconnect] [-?] [session1 session2 ...]
 *    -nosplash      : launches DBClient whith o splash"
 *    -noautoconnect : Disables opening of Sessions Configured as Auto Connect
 *    session1   : session name to be opened, if many sessions have the same name,
 *               path will be used
 * </pre>
 *
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 6 févr. 2007 20:52:52
 */
public class DBClientMain {

    /**
     * returned (process return value) when more than one from -workdir -currentworkdir and -homeworkdir is specified
     * <pre>ERR_INVALID_WORKDIR_OPTION = 3</pre>
     */
    public static final int ERR_INVALID_WORKDIR_OPTION = 3;
    /**
     * returned (process return value) when Help is needed (and shown).
     */
    public static final int ERR_HELP = 4;
    /**
     * returned (process return value) when DBCShouldRestartException is thrown
     * <pre>ERR_SHOULD_RESTART = 5</pre>
     */
    public static final int ERR_SHOULD_RESTART = 5;
    /**
     * returned (process return value) when DBCLockedConfigException is thrown
     * <pre>ERR_LOCKED_CONFIG = 6</pre>
     */
    public static final int ERR_LOCKED_CONFIG = 6;
    /**
     * returned (process return value) when an Unknown Exception is thrown
     * <pre>ERR_UNKNOWN = 1000</pre>
     */
    public static final int ERR_UNKNOWN = 1000;

    public static void main(String[] args) {
        mainUnsafe(args);
    }

    public static void mainSafe(final String[] args) {
        SwingUtilities3.invokeLater(new Runnable() {

            public void run() {
                mainUnsafe(args);
            }
        });
    }

    public static void mainUnsafe(final String[] args) {
        long startupTime = System.currentTimeMillis();
        DBClientInfo v = DBClientInfo.INSTANCE;
        System.out.println(
                v.getProductName() + " version " + v.getProductVersion() + ", "
                + "Copyright (C) 2006-" + v.getProductBuildDate().substring(0, 4) + " " + v.getAuthorName() + " (" + v.getAuthorEmail() + ")\n"
                + "    " + v.getProductDescription() + " .\n"
                + "    " + v.getProductName() + " comes with ABSOLUTELY NO WARRANTY; for details see license.txt.\n"
                + "    This is free software, and you are welcome to redistribute it\n"
                + "    under certain conditions; for details see license.txt.);");
        JCmdLine cmdLine = new JCmdLine(args);
        if (cmdLine.isHelpNeeded()) {
            showHelp();
            System.exit(ERR_HELP);
        }
        String dbclientImpl = null;
        String installdir = null;
        String configDir = null;
        String varDir = null;
        String workDir = ".";
        ArrayList<String> sessions = new ArrayList<String>();
        boolean isUWD = false;
        boolean isCWD = false;
        boolean isHWD = false;
        boolean safemode = false;
        boolean nosplash = false;
        boolean logConsole = false;
        boolean logDrawings = false;
        Level logLevel = null;
        for (int i = 0; i < cmdLine.size(); i++) {
            CmdArg arg = cmdLine.getArgument(i);
            if (arg instanceof CmdOption) {
                CmdOption opt = (CmdOption) arg;
                if (opt.isAnyOption("impl", "dbclientImpl")) {
                    i++;
                    CmdParam p = (CmdParam) cmdLine.getArgument(i);
                    dbclientImpl = p.getValue();
                } else if (opt.isAnyOption("i", "install", "installdir")) {
                    i++;
                    CmdParam p = (CmdParam) cmdLine.getArgument(i);
                    installdir = p.getValue();
                } else if (opt.isAnyOption("c", "config", "configdir")) {
                    i++;
                    CmdParam p = (CmdParam) cmdLine.getArgument(i);
                    configDir = p.getValue();
                } else if (opt.isAnyOption("v", "var", "vardir")) {
                    i++;
                    CmdParam p = (CmdParam) cmdLine.getArgument(i);
                    varDir = p.getValue();
                } else if (opt.isAnyOption("wd", "work", "workdir")) {
                    isUWD = true;
                    i++;
                    CmdParam p = (CmdParam) cmdLine.getArgument(i);
                    workDir = p.getValue();
                } else if (opt.isAnyOption("cwd", "current", "currentworkdir")) {
                    isCWD = true;
                    workDir = System.getProperty("user.dir");
                } else if (opt.isAnyOption("hwd", "home", "homeworkdir")) {
                    isHWD = true;
                    workDir = DBCApplication.HOME_WORKING_DIRECTORY;
                } else if (opt.isAnyOption("sm", "safemode", "failsafe")) {
                    safemode = (opt.isSelected());
                } else if (opt.isAnyOption("ns", "nosplash", "nosplash")) {
                    nosplash = (opt.isSelected());
                } else if (opt.isAnyOption("lc", "logconsole")) {
                    logConsole = (opt.isSelected());
                } else if (opt.isAnyOption("ld", "logdrawings")) {
                    logDrawings = (opt.isSelected());
                } else if (opt.isAnyOption("ll", "loglevel")) {
                    logLevel = Level.parse(opt.getValue().toUpperCase());
                } else {
                    throw new IllegalArgumentException("Unsupported option " + opt);
                }
            } else if (arg instanceof CmdParam) {
                sessions.add(((CmdParam) arg).getValue());
            } else if (arg instanceof OptionParam) {
                throw new IllegalArgumentException("Unsupported option " + arg);
                //ignore
            }
        }
        if (((isUWD ? 1 : 0) + (isCWD ? 1 : 0) + (isHWD ? 1 : 0)) > 1) {
            System.err.println("you must specify only one choice from --workdir --currentworkdir and --homeworkdir");
            System.exit(ERR_INVALID_WORKDIR_OPTION);
        }

        try {
            File file = new File(varDir == null ? workDir == null ? new File("var") : new File(workDir, "var") : new File(varDir), "application/log/default-%u.log");
            file.getParentFile().mkdirs();
            FileHandler h = new FileHandler(file.getPath(), 5 * 1024 * 1024, 5, true);
            h.setFormatter(new SimpleLogFormatter());
            final Logger logger = Logger.getLogger("");
            if(logLevel!=null){
                h.setLevel(logLevel);
                logger.setLevel(logLevel);
            }
            for (Handler handler : logger.getHandlers()) {
                //remove default handlers
                logger.removeHandler(handler);
            }
            if (!logDrawings) {
                h.setFilter(new Filter() {

                    @Override
                    public boolean isLoggable(LogRecord record) {
                        final String loggerName = record.getLoggerName();
                        return !loggerName.startsWith("sun.awt.X11")
                                && !loggerName.startsWith("java.awt")
                                && !loggerName.startsWith("javax.swing");
                    }
                });
            }
            logger.addHandler(h);

        } catch (Exception ex) {
            Logger.getLogger(DBClientMain.class.getName()).log(Level.SEVERE, "Unable to set Application Log file", ex);
        }

        DBCApplication d = null;
        try {
            if (dbclientImpl == null) {
                d = DBCApplicationImpl.class.newInstance();
            } else {
                d = (DBCApplication) Class.forName(dbclientImpl).newInstance();
            }
            Properties props = new Properties();
            props.setProperty("application.log.console", logConsole ? "true" : "false");
            props.setProperty("application.log.drawings", logDrawings ? "true" : "false");
            if (logLevel!=null) {
                props.setProperty("application.log.level", logLevel.getName());
            }
            if (workDir != null) {
                d.setWorkingDir(new File(workDir));
            }
            if (configDir != null) {
                d.setConfigDir(new File(configDir));
            }
            if (varDir != null) {
                d.setVarDir(new File(varDir));
            }
            if (installdir != null) {
                d.setInstallDir(new File(installdir));
            } else if (isHWD) {
                //Home config but no install dir specified
                d.setInstallDir(new File(System.getProperty("user.dir")));
            }
            DBCSplashScreen splash = null;
            if (!nosplash) {
                d.getView().showSplashScreen();
                splash = d.getView().getSplashScreen();
                if (splash != null) {
                    splash.setApplication(d);
                }
            }
            d.setApplicationMode(safemode ? DBCApplication.ApplicationMode.SAFE : DBCApplication.ApplicationMode.DEFAULT);
            System.out.println("Current Dir : " + new File(".").getCanonicalPath());
            System.out.println("Install Dir : " + d.getInstallDir());
            System.out.println("Work    Dir : " + d.getWorkingDir());
            System.out.println("Config  Dir : " + d.getConfigDir());
            System.out.println("Var     Dir : " + d.getVarDir());
            try {
                d.start(props, splash == null ? ProgressMonitor.NONE : splash);
            } catch (CancelException e) {
                return;
            }
            boolean someShown = d.getSessions().length > 0;
            for (CmdParam cmdParam : cmdLine.getParameters()) {
                try {
                    if (d.openSession(cmdParam.getValue()) != null) {
                        someShown = true;
                    }
                } catch (CancellationException e) {
                    //do nothing
                } catch (Throwable e) {
                    d.getView().getDialogManager().showMessage(null, "Unable to start Session " + cmdParam.getValue(), MessageDialogType.ERROR, null, e);
                }
            }

            if (!nosplash) {
                d.getView().hideSplashScreen();
            }
            if (!someShown) {
                d.getView().getWindowManager().getSessionListWindow().showWindow();
            }
            d.getLogger(DBClientMain.class.getName()).log(Level.INFO, "[PERF] APPLICATION STARTUP IN " + Chronometer.formatPeriodFixed(System.currentTimeMillis() - startupTime, Chronometer.DatePart.ms, Chronometer.DatePart.s));
        } catch (DBCShouldRestartException e) {
            if (d != null && d.getView() != null) {
                d.getView().getDialogManager().showMessage(null, null, MessageDialogType.INFO, null, e);
            } else {
                System.err.println("DBClient DBCShouldRestartException");
                e.printStackTrace();
            }
            System.exit(ERR_SHOULD_RESTART);
        } catch (DBCLockedConfigException e) {
            if (d != null && d.getView() != null) {
                d.getView().getDialogManager().showMessage(null, null, MessageDialogType.ERROR, null, e);
            } else {
                System.err.println("DBClient DBCLockedConfigException");
                e.printStackTrace();
            }
            System.exit(ERR_LOCKED_CONFIG);
        } catch (Throwable e) {
            if (d != null && d.getView() != null) {
                d.getView().getDialogManager().showMessage(null, "Could not start DBClient", MessageDialogType.ERROR, null, e);
            } else {
                System.err.println("Could not start DBClient");
                e.printStackTrace();
            }
            System.exit(ERR_UNKNOWN);
        } finally {
            d.getView().hideSplashScreen();
        }
    }

    /**
     * Prints Usage Help to the command-line
     */
    public static void showHelp() {
        System.out.println("DBClientMain [-s] [-c] [-wd <path> | -cwd | -hwd] [-?] [session_1 session_2 ...]" + true);
        System.out.println("--workdir <path> : (or -wd ) Specify path working directory (default is './')");
        System.out.println("--config <path>  : (or -c  ) Specify config directory (default is '${workdir}/config')");
        System.out.println("--currentworkdir : (or -cwd) set System.getProperty('user.dir')  as working directory (current working directory)");
        System.out.println("--homeworkdir    : (or -hwd) set a shared working directory in the user home directory)");
        System.out.println("--safemode       : (or -sm)  run Dbclient in safe mode (no plugins will be loaded)");
        System.out.println("session_i        : session name to be opened, if many sessions have the same name, path will be used");
    }

    private DBClientMain() {
    }
}
