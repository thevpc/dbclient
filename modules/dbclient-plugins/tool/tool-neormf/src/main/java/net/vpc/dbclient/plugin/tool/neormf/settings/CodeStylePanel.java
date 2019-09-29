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

package net.vpc.dbclient.plugin.tool.neormf.settings;

import net.vpc.dbclient.plugin.tool.neormf.NUtils;
import net.vpc.dbclient.plugin.tool.neormf.NeormfPluginSession;
import org.vpc.neormf.jbgen.config.ConfigNode;
import net.vpc.swingext.DumbGridBagLayout;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 9 août 2007 18:53:35
 */
class CodeStylePanel extends JPanel {
    private LinkedHashMap<String, String> codeStyleToDefaultsMapping = new LinkedHashMap<String, String>();
    private LinkedHashMap<String, JComponent> codeStyleToComponent = new LinkedHashMap<String, JComponent>();
    private NeormfPluginSession plugin;

    public CodeStylePanel(NeormfPluginSession plugin) {
        this.plugin = plugin;
        initMap();
        DumbGridBagLayout g = new DumbGridBagLayout();
        int i = 0;
        for (Map.Entry<String, String> entry : codeStyleToDefaultsMapping.entrySet()) {
            g.addLine("[<-label" + i + "][<-=editor" + i + "]");
            i++;
        }
        g.setInsets(".*", new Insets(3, 3, 3, 3));
        setLayout(g);
        i = 0;
        for (Map.Entry<String, String> entry : codeStyleToDefaultsMapping.entrySet()) {
            JLabel b = new JLabel(entry.getKey());
            b.putClientProperty("ID", entry.getKey());
            JTextField f = new JTextField(entry.getValue());
            f.putClientProperty("ID", entry.getKey());
            f.putClientProperty("DEFAULT_VALUE", entry.getValue());
            codeStyleToComponent.put(entry.getKey() + "$LABEL", b);
            codeStyleToComponent.put(entry.getKey() + "$EDITOR", f);
            add(b, "label" + i);
            add(f, "editor" + i);
            i++;
        }
    }

    public void load(ConfigNode _target) {
        if (_target == null) {
            for (Map.Entry<String, String> entry : codeStyleToDefaultsMapping.entrySet()) {
                ((JTextField) codeStyleToComponent.get(entry.getKey() + "$EDITOR")).setText(entry.getValue());
            }
        } else {
            for (Map.Entry<String, String> entry : codeStyleToDefaultsMapping.entrySet()) {
                ConfigNode[] configNodes = _target.getChildren("codestyle.object-name<type=\"" + entry.getKey() + "\">", true, true);
                ((JTextField) codeStyleToComponent.get(entry.getKey() + "$EDITOR")).setText(configNodes[0].getValue());
            }
        }
    }

    public void store(ConfigNode _target) {
        if (_target != null) {
            for (Map.Entry<String, String> entry : codeStyleToDefaultsMapping.entrySet()) {
                ConfigNode[] configNodes = _target.getChildren("codestyle.object-name<type=\"" + entry.getKey() + "\">", false, true);
                if (configNodes.length == 0) {
                    configNodes = _target.getChildren("codestyle.object-name<type=\"" + entry.getKey() + "\">", false, false);
                }
                JTextField t = ((JTextField) codeStyleToComponent.get(entry.getKey() + "$EDITOR"));
                if (configNodes.length == 0) {
                    ConfigNode createIt = NUtils.findChild(_target,"codestyle",NUtils.NotFoundAction.DELETE);
                    ConfigNode c = new ConfigNode("object-name");
                    createIt.add(c);
                    c.setAttribute("type", entry.getKey());
                    NUtils.storeValue(c, t, "", true);
                } else {
                    NUtils.storeValue(configNodes[0], t, "", true);
                    for (int i = 1; i < configNodes.length; i++) {
                        configNodes[i].remove();
                    }
                }
            }
        }
    }

    public void setEnabledComponent(boolean enabled) {
        for (Map.Entry<String, String> entry : codeStyleToDefaultsMapping.entrySet()) {
            ((JTextField) codeStyleToComponent.get(entry.getKey() + "$EDITOR")).setEditable(enabled);
        }
    }

    private void initMap() {

        codeStyleToDefaultsMapping.put("do", "{TableName:class}");
        codeStyleToDefaultsMapping.put("bo", "{BeanName}BO");

        codeStyleToDefaultsMapping.put("dto-package", "{ModulePackage}.data.{TableName:package}");
        codeStyleToDefaultsMapping.put("dto-data", "{TableName:class}");
        codeStyleToDefaultsMapping.put("dto-property-list", "{TableName:class}PropertyList");
        codeStyleToDefaultsMapping.put("dto-order-list", "{TableName:class}OrderList");
        codeStyleToDefaultsMapping.put("dto-key", "{TableName:class}Key");
        codeStyleToDefaultsMapping.put("dto-filter", "{TableName:class}Filter");

        codeStyleToDefaultsMapping.put("dao", "{TableName:class}DAO");
        codeStyleToDefaultsMapping.put("dao-package", "{ModulePackage}.dao.{TableName:package}");

        codeStyleToDefaultsMapping.put("table-prefix", "");
        codeStyleToDefaultsMapping.put("table-sequence", "{TableName:upper}_SEQ");


        codeStyleToDefaultsMapping.put("entity-bean", "{TableName:class}Bean");
        codeStyleToDefaultsMapping.put("entity-remote", "{TableName:class}Remote");
        codeStyleToDefaultsMapping.put("entity-home", "{TableName:class}Home");
        codeStyleToDefaultsMapping.put("entity-local-home", "{TableName:class}LocalHome");
        codeStyleToDefaultsMapping.put("entity-local", "{TableName:class}Local");
        codeStyleToDefaultsMapping.put("entity-package", "{ModulePackage}.server.ejb.{TableName:package}");

        codeStyleToDefaultsMapping.put("session", "{SessionName:class}");
        codeStyleToDefaultsMapping.put("session-bean", "{SessionName:class}Bean");
        codeStyleToDefaultsMapping.put("session-remote", "{SessionName:class}Remote");
        codeStyleToDefaultsMapping.put("session-home", "{SessionName:class}Home");
        codeStyleToDefaultsMapping.put("session-local-home", "{SessionName:class}LocalHome");
        codeStyleToDefaultsMapping.put("session-local", "{SessionName:class}Local");
        codeStyleToDefaultsMapping.put("session-package", "{ModulePackage}.server.ejb.{SessionName:package}");

        codeStyleToDefaultsMapping.put("ejb-business-delegate", "{SessionName:class}BD");
        codeStyleToDefaultsMapping.put("ejb-business-delegate-package", "{ModulePackage}.bd.{SessionName:package}");
    }
}
