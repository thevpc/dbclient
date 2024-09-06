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
package net.thevpc.dbclient.plugin.tool.neormf;

import net.thevpc.dbclient.api.configmanager.DBCSessionInfo;
import net.thevpc.dbclient.api.pluginmanager.DBCAbstractPluginSession;
import net.thevpc.dbclient.api.sessionmanager.DBCInternalWindow;
import net.thevpc.dbclient.api.sessionmanager.DBCSessionView;
import net.thevpc.dbclient.plugin.tool.neormf.explorer.NeormfExplorer;
import org.vpc.neormf.jbgen.JBGenMain;
import org.vpc.neormf.jbgen.config.ConfigNode;
import org.vpc.neormf.jbgen.util.JBGenUtils;
import org.vpc.neormf.jbgen.util.JTextAreaTLog;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.logging.Level;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 27 avr. 2007 20:45:27
 */
public class NeormfPluginSession extends DBCAbstractPluginSession {

    public NeormfPluginSession() {
    }

    @Override
    public void sessionOpened() {
        if (isSupported()) {
            setSupported(true);
        }
    }

    public boolean isSupported() {
        return getConfig().getBooleanProperty("EnableNeormfSupport", false);
    }

    public void setSupported(boolean supported) {
        if (supported) {
            getNeormfExplorer();
            getNeormfConsole();
        }
        getConfig().setBooleanProperty("EnableNeormfSupport", supported);
    }

    public NeormfExplorer getNeormfExplorer() {
        NeormfExplorer tabFrame = (NeormfExplorer) getSession().getView().getClientProperties().get("NeormfExplorer");
        if (tabFrame == null) {
            tabFrame = new NeormfExplorer(NeormfPluginSession.this);
            DBCInternalWindow w = getSession().getView().addWindow(tabFrame, DBCSessionView.Side.Explorer, false);
            w.setTitle("Neormf Explorer");
            w.setClosable(false);
            w.setLocked(true);
            getSession().getView().getClientProperties().put("NeormfExplorer", tabFrame);
        }
        return tabFrame;
    }

    public JTextAreaTLog getNeormfConsole() {
        JTextAreaTLog jTextAreaTLog = (JTextAreaTLog) getSession().getView().getClientProperties().get("NeormfConsole");
        if (jTextAreaTLog == null) {
            jTextAreaTLog = new JTextAreaTLog();
            DBCInternalWindow window = getSession().getView().addWindow(
                    new JScrollPane(jTextAreaTLog.getArea()), DBCSessionView.Side.Footer, false);
            window.setTitle("Neormf Console");
            window.setClosable(false);
            window.setLocked(true);
            getSession().getView().getClientProperties().put("NeormfConsole", jTextAreaTLog);
        }
        return jTextAreaTLog;
    }

    public File getProjectFolder() {
        String s = getConfig().getStringProperty("neormf-project", null);
        return s == null ? null : new File(s);
    }

    public File getValidProjectFolder() {
        String s = getConfig().getStringProperty("neormf-project", null);
        File file = s == null ? null : new File(s);
        if (!JBGenMain.isValidProjectFolder(file)) {
            throw new IllegalArgumentException("Invalid Neormf Project.");
        }
        return file;
    }

    public boolean isProjectCreated() {
        try {
            getValidProjectFolder();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void setProjectFolder(File file) throws SQLException, IOException {
        getConfig().setStringProperty("neormf-project", file == null ? null : file.getAbsolutePath());
    }

    public void createProject() throws SQLException, IOException {
        if (!isProjectCreated()) {
            File folder = getProjectFolder();
            if (folder != null) {
                folder.mkdirs();
            }

            ConfigNode node_jbgen = new ConfigNode("jbgen");
            ConfigNode node_source0 = NUtils.findChild(node_jbgen, "source", NUtils.NotFoundAction.DISABLE);
            ConfigNode node_source = NUtils.findChild(node_source0, "jdbc-connection", NUtils.NotFoundAction.DELETE);

            ConfigNode node_driver = NUtils.findChild(node_source, "driver", NUtils.NotFoundAction.DELETE);
            DBCSessionInfo si = getSession().getConfig().getSessionInfo();
            node_driver.setValue(si.getCnxDriver());

            ConfigNode node_url = NUtils.findChild(node_source, "url", NUtils.NotFoundAction.DELETE);
            node_url.setValue(getSession().getApplication().rewriteString(si.getCnxUrl()));

            ConfigNode node_login = NUtils.findChild(node_source, "user", NUtils.NotFoundAction.DELETE);
            node_login.setValue(si.getCnxLogin());

            ConfigNode node_password = NUtils.findChild(node_source, "password", NUtils.NotFoundAction.DELETE);
            node_password.setValue(si.getCnxPassword());


            ConfigNode node_schema = NUtils.findChild(node_source, "schema", NUtils.NotFoundAction.DELETE);
            node_schema.setValue(getSession().getConnection().getDefaultSchema());

            ConfigNode node_include = NUtils.findChild(node_source, "include", NUtils.NotFoundAction.DELETE);
            node_include.setAttribute("type", "TABLE|VIEW");
            node_include.setName("*");

            ConfigNode node_exclude = NUtils.findChild(node_source, "exclude", NUtils.NotFoundAction.DELETE);
            node_exclude.setAttribute("type", "*");
            node_exclude.setName("sys*|dtprop*");


            node_jbgen.store(JBGenMain.getProjectFile(getProjectFolder()));
        } else {
            throw new IllegalArgumentException("Project Already Created");
        }
    }

    public void storeRootConfigNode(ConfigNode root) {
        try {
            root.store(JBGenMain.getProjectFile(getValidProjectFolder()));
        } catch (FileNotFoundException e1) {
            getSession().getLogger(getClass().getName()).log(Level.SEVERE,"storeRootConfigNode failed",e1);
        }
    }

    public ConfigNode getRootConfigNode(boolean autocreate) throws RuntimeException {
        File file = JBGenMain.getProjectFile(getValidProjectFolder());
        ConfigNode root = null;
        try {
            root = ConfigNode.load(file);
        } catch (Throwable ex) {
            getSession().getLogger(getClass().getName()).log(Level.SEVERE,"load config failed",ex);
        }
        if (root == null && autocreate) {
            root = new ConfigNode("jbgen");
            try {
                root.store(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return root;
    }

    public String[] getDONames() {
        ArrayList<String> all = new ArrayList<String>();
        TreeSet<String> found = new TreeSet<String>();
        ConfigNode configNode = null;
        try {
            configNode = ConfigNode.load(new File(getValidProjectFolder(), "jbgen-output.xml"));
        } catch (Throwable e) {
            getSession().getLogger(NeormfPluginSession.class.getName()).log(Level.SEVERE,"Unable to load config",e);
        }
        if (configNode != null) {
            ConfigNode node = configNode.getChild("do-elements", false);
            if (node != null) {
                for (ConfigNode boNode : node.getChildren()) {
                    if (!found.contains(boNode.getName())) {
                        all.add(boNode.getName());
                        found.add(boNode.getName());
                    }
                }
            }
        }

        ConfigNode[] uboNodes = getRootConfigNode(true).getChildren("source.user-objects.do");
        for (ConfigNode uboNode : uboNodes) {
            if (!found.contains(uboNode.getName())) {
                all.add(uboNode.getName());
                found.add(uboNode.getName());
            }
        }
        return all.toArray(new String[all.size()]);
    }

    public String[] getBONames() {
        ArrayList<String> all = new ArrayList<String>();
        TreeSet<String> found = new TreeSet<String>();
        ConfigNode configNode = null;
        try {
            configNode = ConfigNode.load(new File(getValidProjectFolder(), "jbgen-output.xml"));
        } catch (Throwable e) {
            getSession().getLogger(NeormfPluginSession.class.getName()).log(Level.SEVERE,"Unable to load config",e);
            //
        }
        if (configNode != null) {
            ConfigNode node = configNode.getChild("bo-elements", false);
            if (node != null) {
                for (ConfigNode boNode : node.getChildren()) {
                    if (!found.contains(boNode.getName())) {
                        all.add(boNode.getName());
                        found.add(boNode.getName());
                    }
                }
            }
        }

        ConfigNode[] uboNodes = getRootConfigNode(true).getChildren("source.user-objects.bo");
        for (ConfigNode uboNode : uboNodes) {
            if (!found.contains(uboNode.getName())) {
                all.add(uboNode.getName());
                found.add(uboNode.getName());
            }
        }
        return all.toArray(new String[all.size()]);
    }

    public ConfigNode getDORootNode(String name, boolean autocreate) {
        File file = findDOConfigFile(name, getValidProjectFolder());
        ConfigNode doNode = null;
        if (file.exists()) {
            try {
                doNode = ConfigNode.load(file);
            } catch (Throwable e) {
                //
            }
        }
        if (doNode == null && autocreate) {
            doNode = new ConfigNode("jbgen");
            doNode.setFile(file);
            doNode.getChildOrCreateIt("object-definition").getChildOrCreateIt("do");
            try {
                doNode.store(file);
            } catch (Throwable e) {
                //
            }
        } else if (doNode != null) {
            doNode.getChildOrCreateIt("object-definition").getChildOrCreateIt("do");
        }
        return doNode;
    }

    public ConfigNode getTableRootNode(String name, boolean autocreate) {
        File file = findTableConfigFile(name, getValidProjectFolder());
        ConfigNode doNode = null;
        if (file.exists()) {
            try {
                doNode = ConfigNode.load(file);
            } catch (Throwable e) {
                //
            }
        }
        if (doNode == null && autocreate) {
            doNode = new ConfigNode("jbgen");
            doNode.setFile(file);
            doNode.getChildOrCreateIt("object-definition").getChildOrCreateIt("table");
            try {
                doNode.store(file);
            } catch (Throwable e) {
                //
            }
        } else if (doNode != null) {
            doNode.getChildOrCreateIt("object-definition").getChildOrCreateIt("table");
        }
        return doNode;
    }

    public ConfigNode getBORootNode(String name, boolean autocreate) {
        File file = findBOConfigFile(name, getValidProjectFolder());
        ConfigNode doNode = null;
        if (file.exists()) {
            try {
                doNode = ConfigNode.load(file);
            } catch (Throwable e) {
                //
            }
        }
        if (doNode == null && autocreate) {
            doNode = new ConfigNode("jbgen");
            doNode.getChildOrCreateIt("object-definition").getChildOrCreateIt("bo");
            try {
                doNode.store(file);
            } catch (Throwable e) {
                //
            }
        } else if (autocreate) {
            doNode.getChildOrCreateIt("object-definition").getChildOrCreateIt("bo");
        }
        return doNode;
    }

    public void groupTables(String[] tables) {
        JOptionPane.showMessageDialog(null, "Not yet implemented.");
    }

    public void groupDOs(String[] tables) {
        JOptionPane.showMessageDialog(null, "Not yet implemented.");
    }

    public File findDOConfigFile(String name, File folder) {
        String fileName = name + ".do.xml";
        File[] possibleFiles = JBGenUtils.searchFilesByDosFilterName(fileName, folder);
        if (possibleFiles.length == 0) {
            File file = new File(folder, "do-xml/" + fileName);
            file.getParentFile().mkdirs();
            return file;
        } else {
            return possibleFiles[0];
        }
    }

    public File findTableConfigFile(String name, File folder) {
        String fileName = name + ".table.xml";
        File[] possibleFiles = JBGenUtils.searchFilesByDosFilterName(fileName, folder);
        if (possibleFiles.length == 0) {
            File file = new File(folder, "table-xml/" + fileName);
            file.getParentFile().mkdirs();
            return file;
        } else {
            return possibleFiles[0];
        }
    }

    public File findBOConfigFile(String name, File folder) {
        String fileName = name + ".bo.xml";
        File[] possibleFiles = JBGenUtils.searchFilesByDosFilterName(fileName, folder);
        if (possibleFiles.length == 0) {
            File file = new File(folder, "bo-xml/" + fileName);
            file.getParentFile().mkdirs();
            return file;
        } else {
            return possibleFiles[0];
        }
    }
}
