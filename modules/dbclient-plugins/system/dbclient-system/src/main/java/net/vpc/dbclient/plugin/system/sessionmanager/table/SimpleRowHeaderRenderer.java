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

package net.vpc.dbclient.plugin.system.sessionmanager.table;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class SimpleRowHeaderRenderer extends JLabel implements ListCellRenderer {
    private JTable table;

    public SimpleRowHeaderRenderer(JTable table) {
        this.table = table;
        setOpaque(true);
        setBorder(UIManager.getBorder("TableHeader.cellBorder"));
        setHorizontalAlignment(CENTER);
        JTableHeader header = table.getTableHeader();
        setForeground(header.getForeground());
        setBackground(header.getBackground());
        setFont(header.getFont());
        setMinimumSize(new Dimension(30, 5));
        setFocusable(false);
    }

    public Component getListCellRendererComponent(JList list,
                                                  Object value, int index, boolean isSelected, boolean cellHasFocus) {
        setText(String.valueOf(index + 1));
        JTableHeader header = table.getTableHeader();
        if (isSelected) {
            setForeground(header.getBackground());
            setBackground(header.getForeground());
        } else {
            setForeground(header.getForeground());
            setBackground(header.getBackground());
        }
        return this;
    }
}
