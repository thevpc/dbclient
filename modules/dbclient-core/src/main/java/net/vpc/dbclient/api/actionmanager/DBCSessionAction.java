/**
 * ==================================================================== DBClient
 * yet another Jdbc client tool
 *
 * DBClient is a new Open Source Tool for connecting to jdbc compliant
 * relational databases. Specific extensions will take care of each RDBMS
 * implementation.
 *
 * Copyright (C) 2006-2008 Taha BEN SALAH
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 * ====================================================================
 */
package net.vpc.dbclient.api.actionmanager;

import java.awt.event.ActionEvent;
import net.vpc.dbclient.api.DBCSession;
import net.vpc.dbclient.api.pluginmanager.DBCPluginSession;
import net.vpc.prs.plugin.Extension;
import net.vpc.prs.plugin.Inject;
import net.vpc.dbclient.api.sessionmanager.DBCSessionMessageDialogManager;
import net.vpc.prs.iconset.IconSet;
import net.vpc.prs.messageset.MessageSet;

@Extension(customizable = false)
public abstract class DBCSessionAction extends DBClientAction {

    @Inject
    private DBCPluginSession pluginSession;

    protected DBCSessionAction(String id) {
        super(id);
    }

    @Override
    protected void actionPerformedAsynch(final ActionEvent event) {
        pluginSession.getSession().getTaskManager().run(
                getName(), getName(), new Runnable() {

            @Override
            public void run() {
                actionPerformedSynch(event);
            }
        }
        );
    }

    public DBCSession getSession() {
        return getPluginSession() == null ? null : getPluginSession().getSession();
    }

    public DBCPluginSession getPluginSession() {
        return pluginSession;
    }

    protected void setPluginSession(DBCPluginSession pluginSession) {
        this.pluginSession = pluginSession;
    }

    @Override
    public MessageSet getMessageSet() {
        return pluginSession.getMessageSet();
    }

    @Override
    public IconSet getIconSet() {
        return pluginSession.getIconSet();
    }

    @Override
    public DBCSessionMessageDialogManager getDialogManager() {
        return getSession().getView().getDialogManager();
    }

    /**
     * Should return true if this action has valid meaning for the current
     * session. For instance, some actions are valid only if particular
     * Connection types (only for Oracle...). Default implementation returns
     * true. Override this method to do the test of validity;
     *
     * @return true if this action has valid meaning for the current session
     */
    public boolean isValid() {
        return true;
    }
}
