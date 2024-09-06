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
package net.thevpc.dbclient.plugin.toolbox.settings.application;

import net.thevpc.common.swing.layout.DumbGridBagLayout;
import net.thevpc.dbclient.api.DBCApplication;
import net.thevpc.dbclient.api.DBCApplicationView;
import net.thevpc.dbclient.api.configmanager.DBCApplicationSettingsComponent;
import net.thevpc.common.prs.plugin.Initializer;
import net.thevpc.common.prs.plugin.Inject;
import net.thevpc.dbclient.api.viewmanager.DBCPluggablePanel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 31 dec. 2006 00:31:42
 */
public class DBCApplicationJVMSettingsComponent extends DBCPluggablePanel implements DBCApplicationSettingsComponent {

    @Inject
    private DBCApplication application;
    private Logger logger;
    private JLabel xmsLabel = new JLabel();
    private JLabel xmxLabel = new JLabel();
    private JTextField xms = new JTextField();
    private JTextField xmx = new JTextField();

    public DBCApplicationJVMSettingsComponent() {
        super(new BorderLayout());
    }

    public JComponent getComponent() {
        return this;
    }

    public Icon getIcon() {
        return application.getView().getIconSet().getIconW("Action.JVMConfig");
    }

    public String getId() {
        return getClass().getSimpleName();
    }

    public String getTitle() {
        return application.getView().getMessageSet().get("JVMConfig");
    }

    @Initializer
    private void init() {
        setLayout(
                new DumbGridBagLayout()
                        .addLine("[^<-xmsLabel][<-=xms]")
                        .addLine("[<-|xmxLabel][<-=xmx]")
                        .addLine("[$$nothing.]")
                        .setInsets(".*", new Insets(3, 3, 3, 3)));
        add(xmsLabel = new JLabel("Initial Memory"), "xmsLabel");
        add(xmxLabel = new JLabel("Maximum Memory"), "xmxLabel");
        add(xms, "xms");
        add(xmx, "xmx");
        add(new JLabel(), "nothing");
        logger=application.getLogger(DBCApplicationJVMSettingsComponent.class.getName());
    }

    public void loadConfig() {
        DBCApplicationView applicationView = application.getView();
        xmsLabel.setText(applicationView.getMessageSet().get("DBCApplicationJVMSettingsComponent.xms"));
        xmxLabel.setText(applicationView.getMessageSet().get("DBCApplicationJVMSettingsComponent.xmx"));
        try {
//            if (JLauncherRuntimeInfo.getInstance() == null) {
//                xms.setText(applicationView.getMessageSet().get("DBCApplicationJVMSettingsComponent.unsupported"));
//                xmx.setText(applicationView.getMessageSet().get("DBCApplicationJVMSettingsComponent.unsupported"));
//                xms.setEnabled(false);
//                xmx.setEnabled(false);
//            } else {
//                JLauncherOptions jfile = new JLauncherOptions(new File(JLauncherRuntimeInfo.getInstance().getFile()));
//                xms.setText(jfile.getSunVMOptionXms());
//                xmx.setText(jfile.getSunVMOptionXmx());
//            }
        } catch (Exception e) {
            logger.log(Level.SEVERE,"LoadConfig Failed",e);
        }
    }

    public void saveConfig() {
        try {
//            if (JLauncherRuntimeInfo.getInstance() != null) {
//                JLauncherOptions jfile = new JLauncherOptions(new File(JLauncherRuntimeInfo.getInstance().getFile()));
//                jfile.setSunVMOptionXms(xms.getText());
//                jfile.setSunVMOptionXmx(xmx.getText());
//                jfile.store();
//            }
        } catch (Exception e) {
            logger.log(Level.SEVERE,"SaveConfig Failed",e);
        }
    }

    public int getPosition() {
        return 0;
    }

}
