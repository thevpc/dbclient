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

package net.thevpc.dbclient.plugin.tool.neormf.settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 21 avr. 2007 12:10:39
 */
class ChangeLocationButton extends JButton {
    NSettingPanel panel;
    JFileTextFieldDefault root_path = new JFileTextFieldDefault();
    public ChangeLocationButton(NSettingPanel panel) {
        super(UIManager.getIcon("FileView.directoryIcon"));
        this.panel=panel;
        root_path.getJFileChooser().setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        this.setToolTipText("Change "+panel.getTagTitle() +" Root Folder");
        this.setMargin(new Insets(0, 0, 0, 0));
        this.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeLocation();
            }
        });
    }

    public void changeLocation() {
        File f = root_path.getFile();
        int x = JOptionPane.showConfirmDialog(this, root_path, "Change "+panel.getTagTitle() +" Root Folder", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (x != JOptionPane.OK_OPTION) {
            root_path.setFile(f);
        }
    }

}
