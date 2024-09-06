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

package net.thevpc.dbclient.plugin.system.viewmanager;

import net.thevpc.dbclient.api.DBCApplication;
import net.thevpc.dbclient.api.DBCApplicationView;
import net.thevpc.dbclient.api.pluginmanager.DBCAbstractPluggable;
import net.thevpc.dbclient.api.pluginmanager.DBCPluggableResourcesSupport;
import net.thevpc.common.prs.plugin.Inject;
import net.thevpc.common.prs.plugin.Initializer;
import net.thevpc.common.swing.prs.PRSManager;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 10 dec. 2006 12:04:00
 */
public class DBCPluggableResourcesSupportImpl extends DBCAbstractPluggable implements DBCPluggableResourcesSupport {
    @Inject
    private DBCApplication application;

    public DBCPluggableResourcesSupportImpl() {
    }


    @Initializer
    private void init() {
        loadAvailableMessageSets();
        loadAvailableIconSets();
        loadAvailableArtSets();
    }


    public void loadAvailableMessageSets() {
        //nothing to do
    }

    public void loadAvailableIconSets() {
//        PRSManager.registerIconSet(new IconSetDescriptor(
//                DBClientView.DEFAULT_ICONSET_NAME,
//                "Default",null,
//                DefaultIconSet.class.getName(), new String[]{"net.thevpc.dbclient.plugin.system.viewmanager.iconset.dbclient-iconset-default"},
//                getClass().getClassLoader()
//        ));
        PRSManager.setIconSet(DBCApplicationView.DEFAULT_ICONSET_NAME);

    }

    public void loadAvailableArtSets() {
        //nothing to do
    }

    public DBCApplication getApplication() {
        return application;
    }
}
