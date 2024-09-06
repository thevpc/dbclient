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

package net.thevpc.dbclient.plugin.toolbox.settings.session.pluginsettings;

import net.thevpc.dbclient.api.DBCSession;
import net.thevpc.dbclient.api.configmanager.DBCSessionSettingsComponent;
import net.thevpc.dbclient.api.pluginmanager.DBCPluginSession;
import net.thevpc.common.prs.plugin.Inject;
import net.thevpc.common.prs.plugin.Initializer;
import net.thevpc.dbclient.api.viewmanager.DBCPluggablePanel;
import net.thevpc.common.swing.prs.PRSManager;

import javax.swing.*;
import java.awt.*;

/**
 * <pre>
 * </pre>
 *
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 31 dec. 2006 00:31:42
 */
public class DBCSessionPluginsSettingsComponent extends DBCPluggablePanel implements DBCSessionSettingsComponent {
    @Inject
    private DBCPluginSession pluginSession;
    private DBCPluginsActivationPanel plugins;
    private DBCFactoryTablePanel components;

    public DBCSessionPluginsSettingsComponent() {
        super(new BorderLayout());
    }


    public boolean acceptSession(DBCSession pluginSession) {
        return true;
    }


    public JComponent getComponent() {
        return this;
    }

    public Icon getIcon() {
        return pluginSession.getSession().getView().getIconSet().getIconW("Plugin");
    }

    public String getId() {
        return getClass().getSimpleName();
    }

    public String getTitle() {
        return pluginSession.getSession().getView().getMessageSet().get("DBCSessionPluginsSettingsComponent");
    }

    @Initializer
    public void init() {
        setLayout(new BorderLayout());
        plugins = new DBCPluginsActivationPanel();
        plugins.init(pluginSession);
        components = new DBCFactoryTablePanel();
        components.init(pluginSession);
        JTabbedPane pane = new JTabbedPane();
        PRSManager.addSupport(pane, "DBCSessionPluginsSettingsComponent.TabbedPane");
        pane.addTab("Plugins", plugins);
        pane.addTab("Components", components);
        this.add(pane);
    }

    public void loadConfig() {
        plugins.loadConfig();
        components.loadConfig();
    }

    public void saveConfig() {
        plugins.saveConfig();
        components.saveConfig();
    }


    public int getPosition() {
        return 0;
    }

}