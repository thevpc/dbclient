/**
 *
 ====================================================================
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
 *
 ====================================================================
 */

package net.thevpc.dbclient.plugin.tool.neormf.settings;

import net.thevpc.dbclient.api.DBCSession;
import net.thevpc.dbclient.api.configmanager.DBCSessionInfo;
import net.thevpc.dbclient.api.sql.DBCConnection;
import net.thevpc.dbclient.api.sql.DefaultClassFilter;
import net.thevpc.dbclient.api.sql.objects.DBObject;
import net.thevpc.dbclient.api.sql.objects.DBTable;
import net.thevpc.dbclient.api.sql.objects.DBTableFolder;
import net.thevpc.dbclient.plugin.tool.neormf.NUtils;
import org.vpc.neormf.jbgen.config.ConfigNode;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 20 avr. 2007 04:47:11
 */
class SourcePanel extends NSettingPanel {
    ChangeFilterPanel sourceFilter;

    public SourcePanel(NeormfSettingsComponent neormfSettingsComponent) {
        super("source", "Source", neormfSettingsComponent);
        setLayout(new BorderLayout());
        sourceFilter = new ChangeFilterPanel("source", "Source", neormfSettingsComponent);
        JLabel comp = new JLabel("Select Tables and views that will be mapped to DTO Classes...");
        comp.setFont(NeormfSettingsComponent.descFont);
        add(comp, BorderLayout.NORTH);
        add(sourceFilter, BorderLayout.CENTER);
        this.setBorder(BorderFactory.createTitledBorder("Database Objects Filter"));
        PropertyChangeListener preActionTablesListener = new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                ChangeFilterPanel.RegExpText s = (ChangeFilterPanel.RegExpText) evt.getSource();
                s.setValues(getAllTables());
            }
        };
        PropertyChangeListener preActionTypesListener = new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                ChangeFilterPanel.RegExpText s = (ChangeFilterPanel.RegExpText) evt.getSource();
                s.setValues(getAllTypes());
            }
        };
        sourceFilter.getIncludeName().addPropertyChangeListener("RegExpText.PreAction", preActionTablesListener);
        sourceFilter.getExcludeName().addPropertyChangeListener("RegExpText.PreAction", preActionTablesListener);
        sourceFilter.getIncludeType().addPropertyChangeListener("RegExpText.PreAction", preActionTypesListener);
        sourceFilter.getExcludeType().addPropertyChangeListener("RegExpText.PreAction", preActionTypesListener);
    }

    private Collection<String> getAllTables() {
        Collection<String> tables = null;
        try {
            DBCSession dbcSession = getPluginSession().getSession();
            DBCConnection cnx = dbcSession.getConnection();
            DBObject[] objects = cnx.find(cnx.getDefaultCatalog(), cnx.getDefaultSchema(), null, "%", null, new DefaultClassFilter(DBTable.class), null);
            tables = new ArrayList<String>(objects.length);
            for (DBObject object : objects) {
                tables.add(object.getName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (tables == null) {
            return new ArrayList<String>(0);
        }
        return tables;
    }

    private Collection<String> getAllTypes() {
        Collection<String> tables = null;
        try {
            DBCSession dbcSession = getPluginSession().getSession();
            DBCConnection cnx = dbcSession.getConnection();
            DBObject[] objects = cnx.find(null, null, null, "%", null, new DefaultClassFilter(DBTableFolder.class), null);
            tables = new ArrayList<String>(objects.length);
            for (DBObject object : objects) {
                tables.add(object.getName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (tables == null) {
            return new ArrayList<String>(0);
        }
        return tables;
    }

    public void load(ConfigNode _parent) {
        ConfigNode node_source = _parent == null ? null : NUtils.findChildByPath(_parent, "source.jdbc-connection", true, false, NUtils.NotFoundAction.DISABLE);
        sourceFilter.load(node_source);
    }

    public void store(ConfigNode _parent) {
        ConfigNode node_source = NUtils.findChildByPath(_parent, "source.jdbc-connection", NUtils.NotFoundAction.DELETE);

        ConfigNode node_driver = NUtils.findChild(node_source, "driver", NUtils.NotFoundAction.DELETE);
        DBCSessionInfo si = getPluginSession().getSession().getConfig().getSessionInfo();
        node_driver.setValue(si.getCnxDriver());

        ConfigNode node_url = NUtils.findChild(node_source, "url", NUtils.NotFoundAction.DELETE);
        node_url.setValue(
                getPluginSession().getSession().getApplication().rewriteString(si.getCnxUrl())
        );

        ConfigNode node_login = NUtils.findChild(node_source, "user", NUtils.NotFoundAction.DELETE);
        node_login.setValue(si.getCnxLogin());

        ConfigNode node_password = NUtils.findChild(node_source, "password", NUtils.NotFoundAction.DELETE);
        node_password.setValue(si.getCnxPassword());


        ConfigNode node_schema = NUtils.findChild(node_source, "schema", NUtils.NotFoundAction.DELETE);
        try {
            node_schema.setValue(getPluginSession().getSession().getConnection().getDefaultSchema());
        } catch (SQLException e) {
            //
        }
        sourceFilter.store(node_source);
    }


    public void setEnabledComponent(boolean enabled) {
        sourceFilter.setEnabled(enabled);
    }
}