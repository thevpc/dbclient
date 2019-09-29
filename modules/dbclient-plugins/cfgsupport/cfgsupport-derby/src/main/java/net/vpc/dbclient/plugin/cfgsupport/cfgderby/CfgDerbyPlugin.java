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

package net.vpc.dbclient.plugin.cfgsupport.cfgderby;

import net.vpc.dbclient.api.configmanager.DBCApplicationConfig;
import net.vpc.dbclient.api.pluginmanager.DBCAbstractPlugin;
import net.vpc.prs.factory.ExtensionDescriptor;
import net.vpc.prs.factory.ImplementationDescriptor;

import javax.swing.*;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 16 nov. 2006 00:00:32
 */
public class CfgDerbyPlugin extends DBCAbstractPlugin {

    private ImplementationDescriptor derbyConfigManagerImpl;

    public CfgDerbyPlugin() {
    }

//    @Override
//    public void pluginInstalled() {
//        ExtensionDescriptor cc = getApplication().getFactory().getExtension(DBCApplicationConfig.class);
//        if ("net.vpc.dbclient.impl.configmanager.xmlconfig.DBCXMLApplicationConfigImpl".equals(cc.getValidImpl().getImplementationType().getName())) {
//            getApplication().getView().hideSplashScreen();
//            if (JOptionPane.YES_NO_OPTION == JOptionPane.showConfirmDialog(null, "Do you want to use " + derbyConfigManagerImpl.getImplementationType() + " for configuration", "New Config Manager found!", JOptionPane.YES_NO_OPTION)) {
//                cc.setImpl(derbyConfigManagerImpl);
//                getApplication().setPreferredConfigImpl(derbyConfigManagerImpl.getImplementationType().getName());
//                JOptionPane.showMessageDialog(null, derbyConfigManagerImpl.getImplementationType() + " successfully installed. You need Restarting Application.", "New Config Installed", JOptionPane.OK_OPTION);
//            }
//        }
//    }
}
