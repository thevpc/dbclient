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

package net.vpc.dbclient.plugin.toolbox.actions.application;

import net.vpc.dbclient.api.DBCApplication;
import net.vpc.dbclient.api.actionmanager.DBCActionLocation;
import net.vpc.dbclient.api.actionmanager.DBCApplicationAction;
import net.vpc.dbclient.api.viewmanager.DBCTable;
import net.vpc.dbclient.api.windowmanager.DBCWindowKind;
import net.vpc.swingext.SystemPropertiesPanel;

import java.awt.event.ActionEvent;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 31 janv. 2006 15:47:00
 */
public class DBCSystemPropertiesAction extends DBCApplicationAction {
    public DBCSystemPropertiesAction() {
        super("Action.SystemPropertiesAction");
        addLocationPath(DBCActionLocation.MENUBAR, "/help");
    }

    public void actionPerformedImpl(ActionEvent e) throws Throwable {
        getApplication().getView().getWindowManager().addWindow(
                new DBCSystemPropertiesPanel(getApplication()), DBCWindowKind.TOOL, null, "System Properties", null
        ).showWindow();
    }

    private static class DBCSystemPropertiesPanel extends SystemPropertiesPanel {
        private DBCApplication dbclient;

        @Override
        protected DBCTable createTable() {
            return dbclient.getFactory().newInstance(DBCTable.class);
        }

        public DBCSystemPropertiesPanel(DBCApplication _dbclient) {
            super();
            this.dbclient = _dbclient;
            init();
        }
    }

}
