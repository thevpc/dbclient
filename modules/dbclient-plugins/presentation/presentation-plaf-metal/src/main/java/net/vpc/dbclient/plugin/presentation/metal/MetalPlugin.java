package net.vpc.dbclient.plugin.presentation.metal;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarInputStream;
import java.util.logging.Level;
import java.util.zip.ZipEntry;

import net.vpc.dbclient.api.pluginmanager.DBCAbstractPlugin;
import net.vpc.swingext.plaf.MetalPlafHandler;
import net.vpc.swingext.plaf.PlafHandler;
import net.vpc.swingext.plaf.UIManager2;
import net.vpc.util.IOUtils;

/**
 * @author vpc
 */
public class MetalPlugin extends DBCAbstractPlugin {
    @Override
    public void applicationInitializing() {
        File folder = new File(getApplication().getConfigDir(), "plaf-config");
        folder.mkdirs();
        //copy .theme to some folder
        URL url = getDescriptor().getPluginURL();
        URLClassLoader ucl = new URLClassLoader(new URL[]{url});
        JarInputStream jar = null;
        try {
            jar = new JarInputStream(url.openStream());
            ZipEntry nextEntry;
            while ((nextEntry = jar.getNextJarEntry()) != null) {
                String path = nextEntry.getName();
                boolean ddd = false;
                if (!nextEntry.isDirectory() && path.startsWith("net/vpc/dbclient/plugin/presentation/metal/themes/")) {
                    try {
                        URL fileUrl = ucl.getResource(path);
                        File dest = new File(folder, new File(fileUrl.getFile()).getName());
                        IOUtils.copy(fileUrl, dest);
                    } catch (Throwable ex) {
                        getApplication().getLogger(MetalPlugin.class.getName()).log(Level.SEVERE,"Unable to load (" + path + ") due to " + ex.getClass().getSimpleName(),ex);
                    }
                }
            }
        } catch (Exception e) {
            //ignore
        } finally {
            if (jar != null) {
                try {
                    jar.close();
                } catch (Exception e) {
                    //ignore
                }
            }
        }
        //add this folder as alternative metal provider
        PlafHandler ph = UIManager2.getPlafHandler(MetalPlafHandler.METAL);
        if (ph instanceof MetalPlafHandler) {
            MetalPlafHandler mph = (MetalPlafHandler) ph;
            mph.addFolder(folder);
        }
    }


}
