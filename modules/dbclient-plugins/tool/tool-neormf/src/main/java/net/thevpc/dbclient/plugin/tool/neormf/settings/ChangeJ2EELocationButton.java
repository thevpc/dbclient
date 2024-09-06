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

import net.thevpc.common.swing.file.JFileTextField;
import net.thevpc.common.swing.layout.DumbGridBagLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.io.File;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 21 avr. 2007 12:10:39
 */
class ChangeJ2EELocationButton extends JButton {
    NSettingPanel panel;
    JFileTextFieldDefault root_path = new JFileTextFieldDefault();
    JFileTextFieldDefault metainf_path = new JFileTextFieldDefault();
    JFileTextFieldDefault webinf_path = new JFileTextFieldDefault();
    JFileTextFieldDefault setup_path = new JFileTextFieldDefault();

    public ChangeJ2EELocationButton(NSettingPanel panel) {
        super(UIManager.getIcon("FileView.directoryIcon"));
        this.panel = panel;
        root_path.getJFileChooser().setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        metainf_path.getJFileChooser().setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        webinf_path.getJFileChooser().setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        setup_path.getJFileChooser().setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        this.setToolTipText("Change " + panel.getTagTitle() + " Root Folder");
        this.setMargin(new Insets(0, 0, 0, 0));
        this.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeLocation();
            }
        });
        panel.getNeormfSettingsComponent().getProjectConfigPanel().getProjectReferenceFolder().getFileTextField().addPropertyChangeListener(JFileTextField.SELECTED_FILE, new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                revalidateFiles();
            }
        });
        root_path.getFileTextField().addPropertyChangeListener(JFileTextField.SELECTED_FILE, new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                revalidateFiles();
            }
        });
        revalidateFiles();

    }

    private void revalidateFiles(){
        File projectReferenceFile = panel.getNeormfSettingsComponent().getProjectConfigPanel().getProjectReferenceFolder().getAbsoluteFile();
        root_path.setDefaultFolder(projectReferenceFile);
        File rootFile = root_path.getAbsoluteFile();
        metainf_path.setDefaultFolder(rootFile);
        webinf_path.setDefaultFolder(rootFile);
        setup_path.setDefaultFolder(rootFile);
    }

    public void changeLocation() {
        JPanel p = new JPanel();
        p.setLayout(new DumbGridBagLayout()
                .addLine("[<sl][<-=s]")
                .addLine("[<ml][<-=m]")
                .addLine("[<wl][<-=w]")
                .addLine("[<el][<-=e]")
                .setInsets(".*", new Insets(3, 3, 3, 3))
        );
        p.add(new JLabel("Source Folder"), "sl");
        p.add(root_path, "s");
        p.add(new JLabel("META-INF Folder"), "ml");
        p.add(metainf_path, "m");
        p.add(new JLabel("WEB-INF Folder"), "wl");
        p.add(webinf_path, "w");
        p.add(new JLabel("Setup Folder"), "el");
        p.add(setup_path, "e");

        int x = JOptionPane.showConfirmDialog(this, p, "Change " + panel.getTagTitle() + " Root Folders", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (x != JOptionPane.OK_OPTION) {
            //root_path.setFile(f);
        }
    }

}
