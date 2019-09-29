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
package net.vpc.dbclient.plugin.update;

import java.util.logging.Level;
import net.vpc.dbclient.api.pluginmanager.DBCAbstractPlugin;
import net.vpc.prs.plugin.PluginException;
import net.vpc.prs.plugin.PluginDescriptor;
import net.vpc.util.IOUtils;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarInputStream;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 6 oct. 2007 11:09:46
 */
public class DBClientUpdatePlugin extends DBCAbstractPlugin {
    public DBClientUpdatePlugin() {
    }

    @Override
    public void applicationOpening() {
        PluginDescriptor.Status s = getApplication().getPluginManager().getPluginStatus(getId());
        if (s.equals(PluginDescriptor.Status.INSTALLABLE)) {
            Logger logger = getApplication().getLogger(DBClientUpdatePlugin.class.getName());
            try {
                if (getDescriptor().getApplicationVersion().compare(getApplication().getApplicationInfo().getProductVersion()) != 0) {
                    logger.log(Level.WARNING, "Updates for DBClient ignored ({0}).", getDescriptor().getVersion());
                    getApplication().getPluginManager().uninstallPlugin(getId());
                    return;
                }
                URL mainUrl = getDescriptor().getPluginURL();
                URLClassLoader ucl2 = new URLClassLoader(new URL[]{mainUrl}, null);
                JarInputStream jar = new JarInputStream(mainUrl.openStream());
                ZipEntry nextEntry;
                while ((nextEntry = jar.getNextEntry()) != null) {
                    String path = nextEntry.getName();
                    String pathlc = path.toLowerCase();
                    if (pathlc.startsWith("meta-inf/jex-resources/") && !pathlc.endsWith("/")) {
                        File ww = new File(getApplication().getWorkingDir(), ".jex/" + path.substring("meta-inf/bundled-resources/".length()));
                        File parentFile = ww.getParentFile();
                        if (parentFile != null) {
                            parentFile.mkdirs();
                        }
                        IOUtils.copy(ucl2.getResource(path), ww);
                    }
                }
                getApplication().getPluginManager().uninstallPlugin(getId());
                getApplication().getView().hideSplashScreen();
                JOptionPane.showMessageDialog(null, "New version (" + getDescriptor().getVersion() + ") of DBClient installed successfully.\n Please do restart the Application.");
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Updates for DBClient failed " + getDescriptor().getVersion(), e);
            } catch (PluginException e) {
                logger.log(Level.SEVERE, "Updates for DBClient failed " + getDescriptor().getVersion(), e);
            }
        }
    }
}
