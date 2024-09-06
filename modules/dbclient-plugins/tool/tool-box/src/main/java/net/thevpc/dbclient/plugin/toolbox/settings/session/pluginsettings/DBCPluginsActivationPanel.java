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

package net.thevpc.dbclient.plugin.toolbox.settings.session.pluginsettings;

import net.thevpc.dbclient.api.DBCSession;
import net.thevpc.dbclient.api.pluginmanager.DBCPlugin;
import net.thevpc.dbclient.api.pluginmanager.DBCPluginSession;
import net.thevpc.dbclient.api.viewmanager.DBCPluggablePanel;
import net.thevpc.dbclient.api.viewmanager.DBCTable;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * </pre>
 *
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 31 dec. 2006 00:31:42
 */
class DBCPluginsActivationPanel extends DBCPluggablePanel {
    DBCSession session;
    Map<String, Boolean> enabledPlugins = new HashMap<String, Boolean>();

    public DBCPluginsActivationPanel() {
        super(new BorderLayout());
    }


    public void init(DBCPluginSession pluginSession) {
        this.session = pluginSession.getSession();
        setLayout(new BorderLayout());
        final DBCTable tableInterface = session.getFactory().newInstance(DBCTable.class);
        tableInterface.setModel(new DBCSessionPluginsModel(this));
        tableInterface.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableInterface.getComponent().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
                    int index = tableInterface.getSelectedRow();
                    index = tableInterface.convertRowIndexToModel(index);
                    if (index >= 0) {
                        DBCSessionPluginsModel pm = (DBCSessionPluginsModel) tableInterface.getModel();
                        DBCPlugin plugin = pm.plugins[index];
                        if (!plugin.getDescriptor().isSystem()) {
                            Boolean b = enabledPlugins.get(plugin.getId());
                            b = !(b == null ? Boolean.TRUE : b);
                            session.setPluginEnabled(plugin.getId(), b);
                            enabledPlugins.put(plugin.getId(), b);
                            tableInterface.getComponent().repaint();
                        }
                    }
                }
            }

        });
        tableInterface.setDefaultRenderer(String.class, new DefaultTableCellRenderer() {
            Color initialFG = getForeground();
            Color initialBG = getBackground();

            //            Color initialBg=getBackground();
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                DBCSessionPluginsModel pm = (DBCSessionPluginsModel) table.getModel();
                row = tableInterface.convertRowIndexToModel(row);
                DBCPlugin plugin = pm.plugins[row];
                Boolean b = enabledPlugins.get(plugin.getId());
                b = (b == null ? Boolean.TRUE : b);
                if (!b) {
                    setForeground(Color.GRAY);
                    if (!isSelected) {
                        setBackground(Color.GRAY.brighter().brighter());
                    }
                } else {
                    setForeground(initialFG);
                    if (!isSelected) {
                        setForeground(initialBG);
                    }
                }
                return this;
            }
        });
        JScrollPane mappingJsp = new JScrollPane(tableInterface.getComponent());
        mappingJsp.setPreferredSize(new Dimension(600, 400));
        add(mappingJsp, BorderLayout.CENTER);
        add(new JLabel(session.getView().getMessageSet().get("DBCSessionPluginsSettingsComponent.Description")), BorderLayout.PAGE_START);
    }

    public void loadConfig() {
    }

    public void saveConfig() {
    }

    public int getPosition() {
        return 0;
    }
}