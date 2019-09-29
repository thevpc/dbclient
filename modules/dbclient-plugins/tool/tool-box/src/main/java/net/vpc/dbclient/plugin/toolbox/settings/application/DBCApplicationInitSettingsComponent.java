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


package net.vpc.dbclient.plugin.toolbox.settings.application;

import net.vpc.dbclient.api.DBCApplication;
import net.vpc.dbclient.api.configmanager.DBCApplicationSettingsComponent;
import net.vpc.prs.plugin.Inject;
import net.vpc.prs.plugin.Initializer;
import net.vpc.dbclient.api.viewmanager.DBCPluggablePanel;
import net.vpc.swingext.DumbGridBagLayout;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 31 dec. 2006 00:31:42
 */
public class DBCApplicationInitSettingsComponent extends DBCPluggablePanel implements DBCApplicationSettingsComponent {
    @Inject
    private DBCApplication application;
    private JCheckBox noAutoConnect = new JCheckBox("Disable Auto Connect");
    private JCheckBox noSplash = new JCheckBox("Disable Splash Screen");
    private Logger logger;

    public DBCApplicationInitSettingsComponent() {
        super(new BorderLayout());
    }


    public JComponent getComponent() {
        return this;
    }

    public Icon getIcon() {
        return application.getView().getIconSet().getIconW("Action.DBCApplicationInitSettingsComponent");
    }

    public String getId() {
        return getClass().getSimpleName();
    }

    public String getTitle() {
        return application.getView().getMessageSet().get("DBCApplicationInitSettingsComponent");
    }

    @Initializer
    private void init() {
        logger=application.getLogger(DBCApplicationInitSettingsComponent.class.getName());
        setLayout(
                new DumbGridBagLayout()
                        .addLine("[<-=noAutoConnect : ]")
                        .addLine("[<^|$-=noSplash : ]")
                        .setInsets(".*", new Insets(3, 3, 3, 3))
        );
        add(noAutoConnect, "noAutoConnect");
        add(noSplash, "noSplash");
        noAutoConnect.setText(application.getView().getMessageSet().get("DBCApplicationInitSettingsComponent.noAutoConnect"));
        noSplash.setText(application.getView().getMessageSet().get("DBCApplicationInitSettingsComponent.noSplash"));
    }

    public void loadConfig() {
        try {
            noSplash.setSelected(!application.getConfig().isSplashScreenEnabled());
            noAutoConnect.setSelected(!application.getConfig().getBooleanProperty("init.autoConnect", true));
        } catch (Exception e) {
            logger.log(Level.SEVERE,"LoadConfig Failed",e);
        }
    }

    public void saveConfig() {
        try {

            application.getConfig().setSplashScreenEnabled(!noSplash.isSelected());
            application.getConfig().getBooleanProperty("init.autoConnect", !noAutoConnect.isSelected());
        } catch (Exception e) {
            logger.log(Level.SEVERE,"SaveConfig Failed",e);
        }
    }

    public int getPosition() {
        return 0;
    }


}
