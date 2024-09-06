package org.dbclient.tutorial.hello;

import javax.swing.JOptionPane;
import net.thevpc.dbclient.api.pluginmanager.DBCAbstractPlugin;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 9 oct. 2007 21:20:39
 */
public class HelloPlugin extends DBCAbstractPlugin {
    public HelloPlugin() {
    }

    @Override
    public void applicationReady() {
        JOptionPane.showMessageDialog(null,"Hello");
    }

}
