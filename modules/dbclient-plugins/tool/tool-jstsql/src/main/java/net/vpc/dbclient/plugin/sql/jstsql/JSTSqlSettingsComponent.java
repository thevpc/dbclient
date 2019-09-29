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

package net.vpc.dbclient.plugin.sql.jstsql;

import net.vpc.dbclient.api.DBCSession;
import net.vpc.dbclient.api.configmanager.DBCSessionSettingsComponent;
import net.vpc.dbclient.api.pluginmanager.DBCPluginSession;
import net.vpc.prs.plugin.Initializer;
import net.vpc.prs.plugin.Inject;
import net.vpc.dbclient.api.viewmanager.DBCPluggablePanel;

import javax.swing.*;
import java.awt.*;


/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 5 aout 2006 00:54:44
 */
public class JSTSqlSettingsComponent extends DBCPluggablePanel implements DBCSessionSettingsComponent {
    @Inject
    private DBCPluginSession pluginSession;
    private JSTSqlSettingsComponentFormNB component;

    public JSTSqlSettingsComponent() {
    }

    public JComponent getComponent() {
        return this;
    }

    public boolean acceptSession(DBCSession pluginSession) {
        return true;
    }


    public Icon getIcon() {
//        return pluginSession.getSession().getIconSet().getIconW(getId() + ".icon");
        return pluginSession.getSession().getView().getIconSet().getIconR("Preferences");
    }

    public String getId() {
        return getClass().getSimpleName();
    }

    public String getTitle() {
        return pluginSession.getMessageSet().get(getId() + ".Title");
    }

    @Initializer()
    public void init() {
        component = new JSTSqlSettingsComponentFormNB((JSTSqlPluginSession) pluginSession);
        setLayout(new BorderLayout());
        add(component);
    }

    public void loadConfig() {
        component.loadTemplates();
    }

    public void saveConfig() {
        component.saveTemplates();
    }

    public int getPosition() {
        return -1;
    }
}