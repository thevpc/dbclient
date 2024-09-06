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

package net.thevpc.dbclient.plugin.toolbox.settings.session;

import net.thevpc.common.swing.layout.DumbGridBagLayout;
import net.thevpc.dbclient.api.DBCSession;
import net.thevpc.dbclient.api.configmanager.DBCSessionSettingsComponent;
import net.thevpc.common.prs.plugin.Inject;
import net.thevpc.common.prs.plugin.Initializer;
import net.thevpc.dbclient.api.viewmanager.DBCComponentFormat;
import net.thevpc.dbclient.api.viewmanager.DBCNamedFormat;
import net.thevpc.dbclient.api.viewmanager.DBCPluggablePanel;
import net.thevpc.dbclient.plugin.system.viewmanager.ComponentFormatEditor;
import net.thevpc.common.swing.prs.PRSManager;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 7 févr. 2007 04:29:31
 */
public class DBCExplorerTreeSettingsComponent extends DBCPluggablePanel implements DBCSessionSettingsComponent {
    private ComponentFormatEditor editor;
    @Inject
    protected DBCSession session;
    private DBCNamedFormat[] namedFormats;
    private JList list;
    private JCheckBox hideExcludedNodes;
    private JCheckBox clearExcludedNodesList;

    public DBCExplorerTreeSettingsComponent() {

    }


    public boolean acceptSession(DBCSession pluginSession) {
        return true;
    }

    public JComponent getComponent() {
        return this;
    }

    public Icon getIcon() {
        return session.getView().getIconSet().getIconR("Preferences");
    }

    public String getId() {
        return "ExplorerTreeConfig";
    }

    public String getTitle() {
        return "Explorer Tree";
    }

    @Initializer
    public void init() {
        editor = new ComponentFormatEditor(null);
        namedFormats = new DBCNamedFormat[]{
                new DBCNamedFormat(-1, "ExplorerRenderer.formatDefault", "Default Nodes"),
                new DBCNamedFormat(2, "ExplorerRenderer.formatUnloaded", "Unloaded Nodes"),
                new DBCNamedFormat(3, "ExplorerRenderer.formatMain", "Main Nodes"),
                new DBCNamedFormat(4, "ExplorerRenderer.formatExcluded", "Excluded Nodes"),
                new DBCNamedFormat(5, "ExplorerRenderer.formatSystem", "System Nodes"),
        };
        list = new JList(
                namedFormats
        );
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JPanel formatsPanel = new JPanel();
        formatsPanel.setLayout(new DumbGridBagLayout(
                "[^$List+=][^Editor]"
        ).setInsets(".*", new Insets(4, 4, 4, 4))
        );
        formatsPanel.add(new JScrollPane(list), "List");
        formatsPanel.add(editor, "Editor");
        list.setSelectedIndex(0);
        list.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int index = list.getSelectedIndex();
                    editor.setFormat(namedFormats[index].getComponentFormat());
                }
            }
        });
        formatsPanel.setBorder(BorderFactory.createTitledBorder("Explorer Nodes Formats"));

        JPanel excludeListPanel = new JPanel();
        excludeListPanel.setLayout(new DumbGridBagLayout()
                .addLine("[<hideExcludedNodes=]")
                .addLine("[<clearExcludedNodesList=]")
                .setInsets(".*", new Insets(4, 4, 4, 4))
        );
        hideExcludedNodes = PRSManager.createCheck("Tree.hideExcludedNodes", false);
        clearExcludedNodesList = PRSManager.createCheck("Tree.clearExcludedNodesList", false);

        excludeListPanel.add(hideExcludedNodes, "hideExcludedNodes");
        excludeListPanel.add(clearExcludedNodesList, "clearExcludedNodesList");
        excludeListPanel.setBorder(BorderFactory.createTitledBorder("Exclude List"));

        setLayout(new DumbGridBagLayout()
                .addLine("[<+=excludeListPanel]")
                .addLine("[<+$$==formatsPanel]")
                .setInsets(".*", new Insets(5, 5, 5, 5))
        );
        add(excludeListPanel, "excludeListPanel");
        add(formatsPanel, "formatsPanel");
    }

    public void loadConfig() {
        for (DBCNamedFormat namedFormat : namedFormats) {
            DBCComponentFormat componentFormat = session.getConfig().getComponentFormatProperty(namedFormat.getId(), null);
            namedFormat.setFormat(componentFormat);
        }
        editor.setFormat(namedFormats[list.getSelectedIndex()].getComponentFormat());
        hideExcludedNodes.setSelected(session.getConfig().getBooleanProperty("Tree.IgnoredNodeIsInvisible", true));
        clearExcludedNodesList.setSelected(false);
    }

    public void saveConfig() {
        for (DBCNamedFormat namedFormat : namedFormats) {
            session.getConfig().setComponentFormatProperty(namedFormat.getId(), namedFormat.getComponentFormat());
        }
        session.getConfig().setBooleanProperty("Tree.IgnoredNodeIsInvisible", hideExcludedNodes.isSelected());

        if (clearExcludedNodesList.isSelected()) {
            session.getConfig().clearExcludedPaths();
            session.getLogger(getClass().getName()).warning("Ignore List cleared");
        }
        clearExcludedNodesList.setSelected(false);
        session.getView().refreshView();
    }

    public int getPosition() {
        return 0;
    }
}
