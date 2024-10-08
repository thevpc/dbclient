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

import net.thevpc.dbclient.api.configmanager.DBCApplicationSettingsComponent;
import net.thevpc.common.prs.plugin.Ignore;
import net.thevpc.dbclient.api.viewmanager.DBCPluggablePanel;

import javax.swing.*;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 6 févr. 2007 23:59:26
 */
@Ignore
public class DBCApplicationSummarySettingsComponent extends DBCPluggablePanel implements DBCApplicationSettingsComponent {

    public DBCApplicationSummarySettingsComponent() {
        add(new JLabel("TODO..."));
    }


    public JComponent getComponent() {
        return this;
    }

    public Icon getIcon() {
        return null;
    }

    public String getId() {
        return "SummaryConfig";
    }

    public String getTitle() {
        return "Summary";
    }

    public void loadConfig() {
    }

    public void saveConfig() {
    }

    public int getPosition() {
        return 0;
    }

}
