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
package net.vpc.dbclient.plugin.toolbox.settings.session.pluginsettings;

import net.vpc.dbclient.api.DBCSession;
import net.vpc.dbclient.api.pluginmanager.DBCPluginSession;
import net.vpc.dbclient.api.viewmanager.DBCPluggablePanel;
import net.vpc.dbclient.api.viewmanager.DBCTable;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * <pre>
 * </pre>
 *
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 31 dec. 2006 00:31:42
 */
class DBCFactoryTablePanel extends DBCPluggablePanel {

    DBCSession session;

    public DBCFactoryTablePanel() {
        super(new BorderLayout());
    }


    public String getTitle() {
        return session.getView().getMessageSet().get("Action.FactoryMappingAction");
    }

    public void init(DBCPluginSession pluginSession) {
        this.session = pluginSession.getSession();
        final DBCTable mappingTable = session.getFactory().newInstance(DBCTable.class);
        mappingTable.setModel(new DBCClassMappingModel(this,
                session.getFactory().getExtensions()));
        JScrollPane mappingJsp = new JScrollPane(mappingTable.getComponent());
        mappingTable.setDefaultRenderer(String.class, new DefaultTableCellRenderer() {

            Color initialColor = getForeground();
//            Font initialFont=getFont();

            //            Font boldFont =initialFont.deriveFont(Font.BOLD);
            // implements javax.swing.pluginsTable.TableCellRenderer

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                int rowModel = mappingTable.convertRowIndexToModel(row);
                String c1 = (String) table.getModel().getValueAt(rowModel, 0);
                String c2 = (String) table.getModel().getValueAt(rowModel, 1);
                String c3 = (String) table.getModel().getValueAt(rowModel, 2);
                if (c3==c2 || (c3!=null && c3.equals(c2))) {
                    super.getTableCellRendererComponent(table, toSimple((String) value, true), isSelected, hasFocus, row, column);
                    setForeground(initialColor);
                } else {
                    super.getTableCellRendererComponent(table, toSimple((String) value, true), isSelected, hasFocus, row, column);
                    setForeground(Color.BLUE);
                }
//                if(column==0){
//                    setFont(boldFont);
//                }else{
//                    setFont(initialFont);
//                }
                return this;
            }

            private String toSimple(String s, boolean isSimple) {
                if (isSimple && s != null) {
                    int x = s.lastIndexOf('.');
                    return s.substring(x + 1);
                }
                return s;
            }
        });
        mappingJsp.setPreferredSize(new Dimension(600, 400));
        add(mappingJsp);
    }

    public void loadConfig() {
    }

    public void saveConfig() {
    }

    public int getPosition() {
        return 0;
    }
}
