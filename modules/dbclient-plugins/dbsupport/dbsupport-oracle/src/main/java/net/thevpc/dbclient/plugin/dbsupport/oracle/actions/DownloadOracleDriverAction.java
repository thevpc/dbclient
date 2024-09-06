/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.dbclient.plugin.dbsupport.oracle.actions;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import net.thevpc.common.io.FileUtils;
import net.thevpc.common.io.IOUtils;
import net.thevpc.common.io.URLUtils;
import net.thevpc.common.prs.Version;
import net.thevpc.dbclient.api.actionmanager.DBCActionLocation;
import net.thevpc.dbclient.api.actionmanager.DBCApplicationAction;
import net.thevpc.dbclient.api.pluginmanager.DBCPlugin;
import net.thevpc.common.prs.plugin.Inject;
import net.thevpc.common.swing.html.HtmlComponent;
import net.thevpc.common.swing.html.HtmlComponentsCollection;

/**
 *
 * @author vpc
 */
public class DownloadOracleDriverAction extends DBCApplicationAction {

    private static final String DEFAULT_LICENCE_URL = "http://www.oracle.com/technetwork/licenses/distribution-license-152002.html";
    @Inject
    private DBCPlugin plugin;

    public DownloadOracleDriverAction() {
        super("Action.DownloadOracleDriverAction");
        addLocationPath(DBCActionLocation.MENUBAR, "/tools");
    }

    @Override
    public void actionPerformedImpl(ActionEvent e) throws Throwable {
        JEditorPane p = new JEditorPane();
        p.setContentType("text/html");
        DriverURLInfo[] all = get();
        Map<String, DriverURLInfo> map = new HashMap<String, DriverURLInfo>();
        for (DriverURLInfo d : all) {
            map.put(d.getName(), d);
        }
        p.setText(generateHtml());
        HtmlComponentsCollection c = HtmlComponentsCollection.parse(p);
        p.addHyperlinkListener(new HyperlinkListener() {

            @Override
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)) {
                    try {
                        Desktop.getDesktop().browse(e.getURL().toURI());
                    } catch (Exception ex) {
                        Logger.getLogger(DownloadOracleDriverAction.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        JScrollPane sp = new JScrollPane(p);
        sp.setPreferredSize(new Dimension(400, 300));
        JOptionPane.showMessageDialog(null, sp);
        for (HtmlComponent comp : c) {
            if (comp.isSelected() && comp.isEnabled()) {
                downloadAsLibrary(map.get(comp.getId()), false);
            }
        }
//        for (DriverURLInfo driverURLInfo : get()) {
//            JCheckBox v = list.addItem("<html><body>"+driverURLInfo.getName()+"<a href='#'>(see licence terms)</a></body>");
//            v.putClientProperty("DriverURLInfo", driverURLInfo);
//        }
//        JCheckBox acceptLicence = new JCheckBox("Accept Licence");
//        JPanel p=new JPanel(new BorderLayout());
//        p.add(new JLabel("Download Oracle Drivers"),BorderLayout.NORTH);
//        p.add(list,BorderLayout.CENTER);
//        p.add(acceptLicence,BorderLayout.SOUTH);
    }

    private String generateHtml() throws MalformedURLException, IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append("<body>");
        sb.append("<table border=0>");
        for (DriverURLInfo driverURLInfo : get()) {
            sb.append("<tr>");
            if (isLocallyAvailable(driverURLInfo)) {
                sb.append("<td>");
                sb.append("<input id='").append(driverURLInfo.getName()).append("' type='checkbox' checked enabled='false'>");
                sb.append(driverURLInfo.getDescription());
                sb.append("</td>");
                sb.append("<td>");
                sb.append("already downloaded");
                sb.append("</td>");
            } else {
                sb.append("<td>");
                sb.append("<input id='").append(driverURLInfo.getName()).append("' type='checkbox'>");
                sb.append(driverURLInfo.getDescription());
                sb.append("</td>");
                sb.append("<td>");
                sb.append("<a href='").append(driverURLInfo.getLicence()).append("'>");
                sb.append("<h6>(see licence terms)</h6>");
                sb.append("</a>");
                sb.append("</td>");
            }
            sb.append("</tr>");
        }
        sb.append("</table>");
        sb.append("</body>");
        sb.append("</html>");
        return sb.toString();
    }

    public void downloadAsLibrary(DriverURLInfo u, boolean overwrite) throws IOException {
        URL[] urls = download(u, overwrite);
        plugin.getApplication().getDriverManager().addLibrary(u.getName(), Arrays.asList(urls), u.getVersion());
    }

    public boolean isLocallyAvailable(DriverURLInfo u) throws IOException {
        File root = new File(new File(plugin.getVarFolder(), "drivers"), u.getName());
        int index = 1;
        for (URL url : u.getUrls()) {
            String name = URLUtils.getURLName(url);
            if (name.isEmpty()) {
                name = FileUtils.getNameWithExtension("" + index, "jar");
            }
            File file = new File(root, name);
            if (!file.exists()) {
                return false;
            }
        }
        return true;
    }

    public URL[] download(DriverURLInfo u, boolean overwrite) throws IOException {
        List<URL> local = new ArrayList<URL>();
        File root = new File(new File(plugin.getVarFolder(), "drivers"), u.getName());
        int index = 1;
        for (URL url : u.getUrls()) {
            String name = URLUtils.getURLName(url);
            if (name.isEmpty()) {
                name = FileUtils.getNameWithExtension("" + index, "jar");
            }
            File file = new File(root, name);
            if (overwrite || !file.exists()) {
                IOUtils.copy(url, file);
            }
            local.add(file.toURI().toURL());
        }
        return local.toArray(new URL[local.size()]);
    }

    public DriverURLInfo[] get() throws MalformedURLException {
        DriverURLInfo[] all = new DriverURLInfo[]{
            new DriverURLInfo("oracle-8i-jdbc2", "Oracle Thin Driver 8i", DEFAULT_LICENCE_URL, new Version("8.2"), new URL("http://download.oracle.com/otn/utilities_drivers/jdbc/8171/classes12.zip")), new DriverURLInfo("oracle-9i-jdbc2", "Oracle Thin Driver 9i", DEFAULT_LICENCE_URL, new Version("9.2"), new URL("http://download.oracle.com/otn/utilities_drivers/jdbc/9014/classes12.zip")), new DriverURLInfo("oracle-10g-jdbc4", "Oracle Thin Driver 10g", DEFAULT_LICENCE_URL, new Version("10.4"), new URL("http://download.oracle.com/otn/utilities_drivers/jdbc/10105/ojdbc14.jar")), new DriverURLInfo("oracle-11g-jdbc5", "Oracle Thin Driver 11g", DEFAULT_LICENCE_URL, new Version("11.5"), new URL("http://download.oracle.com/otn/utilities_drivers/jdbc/11203/ojdbc5.jar"))
        };
        return all;
    }
}
