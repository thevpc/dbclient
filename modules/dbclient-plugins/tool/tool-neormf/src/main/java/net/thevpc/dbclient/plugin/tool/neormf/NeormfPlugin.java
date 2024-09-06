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

package net.thevpc.dbclient.plugin.tool.neormf;

import net.thevpc.common.io.FileUtils;
import net.thevpc.common.io.IOUtils;
import net.thevpc.common.prs.Version;
import net.thevpc.dbclient.api.pluginmanager.DBCAbstractPlugin;
import net.thevpc.common.prs.plugin.PluginInfo;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Hashtable;
import java.util.jar.JarInputStream;
import java.util.logging.Level;
import java.util.zip.ZipEntry;

/**
 * @author vpc
 */
@PluginInfo(
        messageSet = "net.thevpc.dbclient.plugin.tool.neormf.messageset.Plugin",
        iconSet = "net.thevpc.dbclient.plugin.tool.neormf.iconset.Plugin"
)
public class NeormfPlugin extends DBCAbstractPlugin {

    /**
     * Creates a new instance of NeormfPlugin
     */
    public NeormfPlugin() {
    }

    @Override
    public void applicationOpening() {
        updateLibFolder();
    }

    public void updateLibFolder() {
        File rootFolder = getApplication().getWorkingDir();
        URL mainUrl = getDescriptor().getPluginURL();
        File r0 = FileUtils.canonize(new File(rootFolder + "/misc-lib/neormf"));
        File v = new File(r0, "version.txt");
        Hashtable<String, String> map = new Hashtable<String, String>();
        map.put("neormf-commons.jar", "java-lib");
        map.put("neormf-wws.jar", "java-lib");
        map.put("log4net.dll", "dotnet-lib");
        map.put("neormf-commons.dll", "dotnet-lib");
        map.put("neormf-wws.dll", "dotnet-lib");
        try {
            Version newVersion = getDescriptor().getVersion();
            boolean override = !v.exists() || new Version(v.toURI().toURL(), "neormf").compareTo(newVersion) < 0;
            if (override) {
                v.getParentFile().mkdirs();
                newVersion.store(v, "neormf", "Neormf library Version");
            }
            URLClassLoader ucl2 = new URLClassLoader(new URL[]{mainUrl}, null);
            JarInputStream jar = new JarInputStream(mainUrl.openStream());
            ZipEntry nextEntry;
            while ((nextEntry = jar.getNextEntry()) != null) {
                String path = nextEntry.getName();
                String pathlc = path.toLowerCase();
                if (pathlc.startsWith("meta-inf/lib/") || pathlc.startsWith("meta-inf/bundled-resources/")) {

                    for (String suffix : map.keySet()) {
                        URL url = ucl2.getResource(path);
                        if (url.getFile().endsWith(suffix)) {
                            File f = new File(r0, "/" + map.get(suffix) + "/" + suffix);
                            if (!f.exists() || override) {
                                f.getParentFile().mkdirs();
                                IOUtils.copy(url, f);
                            }
                        }
                    }
                }
            }
        } catch (Throwable e) {
            getApplication().getLogger(getClass().getName()).log(Level.SEVERE,"Unable to update neormf resources",e);
        }
    }
}
