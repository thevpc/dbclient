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

import net.vpc.dbclient.plugin.tool.neormf.NeormfPluginSession;
import org.vpc.neormf.jbgen.config.ConfigNode;
import net.vpc.swingext.DumbGridBagLayout;

import javax.swing.*;
import java.awt.*;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 20 avr. 2007 23:23:09
 */
abstract class NSettingPanel extends JPanel {
    protected String tagId;
    protected String tagTitle;
    protected String tagDescription;
    NeormfSettingsComponent neormfSettingsComponent;
    public NSettingPanel(String tagId, String tagTitle, NeormfSettingsComponent neormfSettingsComponent) {
        this.tagId = tagId;
        this.tagTitle = tagTitle;
        this.neormfSettingsComponent = neormfSettingsComponent;
    }

    public String getTagId() {
        return tagId;
    }

    public String getTagTitle() {
        return tagTitle;
    }

    public String getTagDescription() {
        return tagDescription;
    }

    public void setTagDescription(String tagDescription) {
        this.tagDescription = tagDescription;
    }

    public abstract void load(ConfigNode _target);

    public abstract void store(ConfigNode _target);

    public abstract void setEnabledComponent(boolean enabled);

    protected DumbGridBagLayout addModules(DumbGridBagLayout layout, NSettingPanel[] modules) {
        if (modules != null) {
            for (int i = 0; i < modules.length; i++) {
                layout.addLine("[<=-module" + i + " . ]");
            }
        }
        layout.setInsets("module.*", new Insets(2, 40, 2, 2));
        if (modules != null) {
            for (int i = 0; i < modules.length; i++) {
                NSettingPanel module = modules[i];
                add(module, "module" + i);
            }
        }
        return layout;
    }

    public NeormfSettingsComponent getNeormfSettingsComponent() {
        return neormfSettingsComponent;
    }
    public NeormfPluginSession getPluginSession(){
        return getNeormfSettingsComponent().getPluginSession();
    }
}
