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

package net.vpc.dbclient.plugin.presentation.infonode;

import net.infonode.docking.properties.RootWindowProperties;
import net.infonode.docking.theme.*;
import net.infonode.docking.util.PropertiesUtil;
import net.vpc.dbclient.api.DBCSession;
import net.vpc.dbclient.api.configmanager.DBCPluginSessionConfig;
import net.vpc.dbclient.api.configmanager.DBCSessionSettingsComponent;
import net.vpc.dbclient.api.pluginmanager.DBCPluginSession;
import net.vpc.prs.plugin.Initializer;
import net.vpc.prs.plugin.Inject;
import net.vpc.dbclient.api.viewmanager.DBCPluggablePanel;
import net.vpc.swingext.DumbGridBagLayout;

import javax.swing.*;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 7 févr. 2007 02:14:32
 */
public class InfoNodeSettingsComponent extends DBCPluggablePanel implements DBCSessionSettingsComponent {
    private DBCSession session;
    private JCheckBox titleBarStyle;
    private JCheckBox enableClose;
    private JCheckBox freezeLayout;
    private JRadioButton[] themesRadios;
    private final RootWindowProperties titleBarStyleProperties = PropertiesUtil.createTitleBarStyleRootWindowProperties();
    @Inject
    private DBCPluginSession pluginSession;

    public InfoNodeSettingsComponent() {
    }


    public boolean acceptSession(DBCSession pluginSession) {
        return true;
    }

    public JComponent getComponent() {
        return this;
    }

    public Icon getIcon() {
        return session.getView().getIconSet().getIconR("Preferences");
    }

    public String getId() {
        return pluginSession.getPlugin().getId();
    }

    public String getTitle() {
        return pluginSession.getPlugin().getDescriptor().getTitle();
    }

    @Initializer
    public void init() {
        this.session = pluginSession.getSession();


        titleBarStyle = new JCheckBox("Title Bar Style Theme");
        enableClose = new JCheckBox("Enable Close");
        freezeLayout = new JCheckBox("Freeze Layout");

        DockingWindowsTheme[] themes = {
                new DefaultDockingTheme(),
                new LookAndFeelDockingTheme(),
                new BlueHighlightDockingTheme(),
                new SlimFlatDockingTheme(),
                new GradientDockingTheme(),
                new ShapedGradientDockingTheme(),
                new SoftBlueIceDockingTheme(),
                new ClassicDockingTheme()
        };

        ButtonGroup group = new ButtonGroup();
        Box themesBox = Box.createVerticalBox();
        themesRadios = new JRadioButton[themes.length];
        for (int i = 0; i < themes.length; i++) {
            final DockingWindowsTheme theme = themes[i];
            themesRadios[i] = new JRadioButton(theme.getName());
            group.add(themesRadios[i]);
            themesRadios[i].putClientProperty("Theme", theme);
            themesBox.add(themesRadios[i]);
        }

        setLayout(new DumbGridBagLayout()
                .addLine("[<~+=themes$   ] [<+=~options$   ]")
        );

        Box optionsBox = Box.createVerticalBox();
        optionsBox.add(titleBarStyle, "titleBarStyle");
        optionsBox.add(enableClose, "enableClose");
        optionsBox.add(freezeLayout, "freezeLayout");

        JScrollPane themesPane = new JScrollPane(themesBox);
        themesPane.setBorder(BorderFactory.createTitledBorder("Available Themes"));
        JScrollPane optionsPane = new JScrollPane(optionsBox);
        optionsPane.setBorder(BorderFactory.createTitledBorder("Options"));
        add(themesPane, "themes");
        add(optionsPane, "options");

        setReadOnly(false);
    }

    public void setReadOnly(boolean ro) {
        titleBarStyle.setEnabled(!ro);
        enableClose.setEnabled(!ro);
        freezeLayout.setEnabled(!ro);
        titleBarStyle.setEnabled(!ro);
        for (JRadioButton themesRadio : themesRadios) {
            themesRadio.setEnabled(!ro);
        }
    }

    public void loadConfig() {
        DBCPluginSessionConfig c=pluginSession.getConfig();
        titleBarStyle.setSelected(c.getBooleanProperty("InfoNodeSQLViewManager.properties.titlebar", false));
        enableClose.setSelected(c.getBooleanProperty("InfoNodeSQLViewManager.properties.close", true));
        freezeLayout.setSelected(c.getBooleanProperty("InfoNodeSQLViewManager.properties.freeze", false));
        String cwt = c.getStringProperty("InfoNodeSQLViewManager.windowtheme", null);
        for (JRadioButton themesRadio : themesRadios) {
            DockingWindowsTheme themeInstance = (DockingWindowsTheme) themesRadio.getClientProperty("Theme");
            themesRadio.setSelected(themeInstance.getClass().getName().equals(cwt));
        }
    }

    public void saveConfig() {
        InfoNodeSessionViewLayoutComponent viewManager = (InfoNodeSessionViewLayoutComponent) session.getView().getClientProperties().get("InfoNodeSessionViewLayoutComponent");
        RootWindowProperties properties = viewManager.getDockingProperties();
        DBCPluginSessionConfig c=pluginSession.getConfig();

        c.setBooleanProperty("InfoNodeSQLViewManager.properties.close", enableClose.isSelected());
        c.setBooleanProperty("InfoNodeSQLViewManager.properties.titlebar", titleBarStyle.isSelected());
        c.setBooleanProperty("InfoNodeSQLViewManager.properties.freeze", freezeLayout.isSelected());

        boolean freeze = freezeLayout.isSelected();

        // Freeze tab reordering inside tabbed panel
        for (JRadioButton themesRadio : themesRadios) {
            if (themesRadio.isSelected()) {
                properties.getMap().clear(true);
                DockingWindowsTheme themeInstance = (DockingWindowsTheme) themesRadio.getClientProperty("Theme");
                viewManager.setTheme(themeInstance);
                c.setStringProperty("InfoNodeSQLViewManager.windowtheme", themeInstance.getClass().getName());
            }
        }
        properties.getTabWindowProperties().getTabbedPanelProperties().setTabReorderEnabled(!freeze);
        if (titleBarStyle.isSelected()) {
            properties.addSuperObject(titleBarStyleProperties);
        } else {
            properties.removeSuperObject(titleBarStyleProperties);
        }

        properties.getDockingWindowProperties().setDragEnabled(!freeze);
        properties.getDockingWindowProperties().setCloseEnabled(!freeze && enableClose.isSelected());
        properties.getDockingWindowProperties().setMinimizeEnabled(!freeze);
        properties.getDockingWindowProperties().setRestoreEnabled(!freeze);
        properties.getDockingWindowProperties().setMaximizeEnabled(!freeze);
        properties.getDockingWindowProperties().setUndockEnabled(!freeze);
        properties.getDockingWindowProperties().setDockEnabled(!freeze);
        session.getLogger(getClass().getName()).warning("InfoNode Changes will not take effect until you reconnect.");
    }

    public int getPosition() {
        return 0;
    }

}
