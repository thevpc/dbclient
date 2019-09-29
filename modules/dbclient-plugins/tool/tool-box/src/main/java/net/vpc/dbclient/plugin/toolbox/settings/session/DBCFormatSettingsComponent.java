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

package net.vpc.dbclient.plugin.toolbox.settings.session;

import net.vpc.dbclient.api.DBCSession;
import net.vpc.dbclient.api.configmanager.DBCSessionSettingsComponent;
import net.vpc.prs.plugin.Inject;
import net.vpc.prs.plugin.Initializer;
import net.vpc.dbclient.api.viewmanager.DBCPluggablePanel;
import net.vpc.swingext.DumbGridBagLayout;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 7 févr. 2007 04:29:31
 */
public class DBCFormatSettingsComponent extends DBCPluggablePanel implements DBCSessionSettingsComponent {
    @Inject
    private DBCSession session;

    private JTextField dateFormat;
    private JTextField timeFormat;
    private JTextField timestampFormat;
    private JCheckBox showDisabledMenus;

    public DBCFormatSettingsComponent() {

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
        return "FormatsConfig";
    }

    public String getTitle() {
        return "User Formats";
    }

    @Initializer
    public void init() {
        dateFormat = new JTextField("");
        timeFormat = new JTextField("");
        timestampFormat = new JTextField("");
        showDisabledMenus = new JCheckBox("");
        setLayout(new DumbGridBagLayout()
                .addLine("[^~<dateLabel+][<+=date]")
                .addLine("[<timeLabel+][<+=time]")
                .addLine("[<timestampLabel+][<+=timestamp]")
                .addLine("[<+= showDisabledMenus : ]")
                .addLine("[+$$==nothing:]")
                .setInsets(".*", new Insets(4, 4, 4, 4))
        );
        add(new JPanel(), "nothing");
        add(new JLabel("Date"), "dateLabel");
        add(new JLabel("Time"), "timeLabel");
        add(new JLabel("Timestamp"), "timestampLabel");
        add(dateFormat, "date");
        add(timeFormat, "time");
        add(timestampFormat, "timestamp");
        add(showDisabledMenus, "showDisabledMenus");
        setBorder(BorderFactory.createTitledBorder("Date Formats"));
    }

    public void loadConfig() {
        dateFormat.setText(session.getConfig().getDateFormat().toPattern());
        timeFormat.setText(session.getConfig().getTimeFormat().toPattern());
        timestampFormat.setText(session.getConfig().getTimestampFormat().toPattern());
        showDisabledMenus.setSelected(session.getConfig().getBooleanProperty("ui.showDisabledMenus", false));
    }

    public void saveConfig() {
        SimpleDateFormat sdf = null;
        try {
            sdf = dateFormat.getText().length() == 0 ? null : new SimpleDateFormat(dateFormat.getText());
            session.getConfig().setStringProperty("ui.format.date", sdf.toPattern());
        } catch (Exception e) {
            //
        }
        try {
            sdf = timeFormat.getText().length() == 0 ? null : new SimpleDateFormat(timeFormat.getText());
            session.getConfig().setStringProperty("ui.format.time", sdf.toPattern());
        } catch (Exception e) {
            //
        }
        try {
            sdf = timestampFormat.getText().length() == 0 ? null : new SimpleDateFormat(timestampFormat.getText());
            session.getConfig().setStringProperty("ui.format.timestamp", sdf.toPattern());
        } catch (Exception e) {
            //
        }
    }

    public int getPosition() {
        return 0;
    }
}
