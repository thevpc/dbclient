/**
 * ====================================================================
 *             DBCLient yet another Jdbc client tool
 *
 * DBClient is a new Open Source Tool for connecting to jdbc
 * compliant relational databases. Specific extensions will take care of
 * each RDBMS implementation.
 *
 * Copyright (C) 2006-2007 Taha BEN SALAH
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
package net.vpc.dbclient.plugin.system.pluginmanager;

import net.vpc.dbclient.api.DBCApplication;
import net.vpc.dbclient.api.pluginmanager.DBCPluginManagerEditor;
import net.vpc.prs.plugin.Inject;
import net.vpc.prs.plugin.Initializer;
import net.vpc.swingext.pluginmanager.PluginManagerEditor;

import java.awt.*;

/**
 * @author Taha BEN SALAH
 */
public class DBCPluginManagerEditorImpl extends PluginManagerEditor<DBCApplication> implements DBCPluginManagerEditor {

    @Inject
    private DBCApplication application;

    public DBCPluginManagerEditorImpl() {
        super();
    }

    @Initializer
    private void init() {
        init(application, application.getView(), application.getPluginManager(), application.getFactory(), application.getView().getDialogManager());
    }

    public Component getComponent() {
        return this;
    }

}
