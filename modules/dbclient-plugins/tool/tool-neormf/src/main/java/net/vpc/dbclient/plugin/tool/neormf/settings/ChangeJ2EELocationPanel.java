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

import net.vpc.swingext.DumbGridBagLayout;
import net.vpc.swingext.JFileTextField;
import net.vpc.dbclient.plugin.tool.neormf.NUtils;
import org.vpc.neormf.jbgen.config.ConfigNode;
import org.vpc.neormf.jbgen.projects.J2eeTarget;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.io.File;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 21 avr. 2007 12:10:39
 */
class ChangeJ2EELocationPanel extends NSettingPanel{
    JFileTextFieldDefault root_path = new JFileTextFieldDefault();
    JFileTextFieldDefault metainf_path = new JFileTextFieldDefault();
    JFileTextFieldDefault webinf_path = new JFileTextFieldDefault();
    JFileTextFieldDefault setup_path = new JFileTextFieldDefault();

    public ChangeJ2EELocationPanel(String tagId, String tagTitle, NeormfSettingsComponent neormfSettingsComponent) {
        super(tagId, tagTitle, neormfSettingsComponent);
        root_path.getJFileChooser().setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        metainf_path.getJFileChooser().setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        webinf_path.getJFileChooser().setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        setup_path.getJFileChooser().setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        getNeormfSettingsComponent().getProjectConfigPanel().getProjectReferenceFolder().getFileTextField().addPropertyChangeListener(JFileTextField.SELECTED_FILE, new PropertyChangeListener() {
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
        setLayout(new DumbGridBagLayout()
                .addLine("[<sl][<-=s]")
                .addLine("[<ml][<-=m]")
                .addLine("[<wl][<-=w]")
                .addLine("[<el][<-=e]")
                .setInsets(".*", new Insets(3, 3, 3, 3))
        );
        this.add(new JLabel("Source Folder"), "sl");
        this.add(root_path, "s");
        this.add(new JLabel("META-INF Folder"), "ml");
        this.add(metainf_path, "m");
        this.add(new JLabel("WEB-INF Folder"), "wl");
        this.add(webinf_path, "w");
        this.add(new JLabel("Setup Folder"), "el");
        this.add(setup_path, "e");

    }

    private void revalidateFiles(){
        File projectReferenceFile = getNeormfSettingsComponent().getProjectConfigPanel().getProjectReferenceFolder().getAbsoluteFile();
        root_path.setDefaultFolder(projectReferenceFile);
        File rootFile = root_path.getAbsoluteFile();
        metainf_path.setDefaultFolder(rootFile);
        webinf_path.setDefaultFolder(rootFile);
        setup_path.setDefaultFolder(rootFile);
    }

    public void store(ConfigNode _dao_target) {
        NUtils.storeValue(NUtils.findChild(_dao_target,"root-path",NUtils.NotFoundAction.DISABLE),root_path,"",true);
        NUtils.storeValue(NUtils.findChildByPath(_dao_target, J2eeTarget.MODULE_EJB+".deployment.meta-inf-path",NUtils.NotFoundAction.DISABLE),metainf_path,"",true);
        NUtils.storeValue(NUtils.findChildByPath(_dao_target,J2eeTarget.MODULE_EJB+".deployment.setup-path",NUtils.NotFoundAction.DISABLE),setup_path,"",true);
        NUtils.storeValue(NUtils.findChildByPath(_dao_target,J2eeTarget.MODULE_WEB+".deployment.web-inf-path",NUtils.NotFoundAction.DISABLE),webinf_path,"",true);
    }

    public void load(ConfigNode _dao_target) {
        ConfigNode _root_path = _dao_target==null?null :_dao_target.getChild("root-path", false);
        root_path.setFile(_root_path == null ? "" : _root_path.getValue());

        ConfigNode[] _deployment = _dao_target==null?null :_dao_target.getChildren(J2eeTarget.MODULE_EJB+".deployment.meta-inf-path");
        metainf_path.setFile((_deployment==null || _deployment.length==0)?"" : _deployment[0].getValue());

        _deployment = _dao_target==null?null:_dao_target.getChildren(J2eeTarget.MODULE_EJB+".deployment.setup-path");
        setup_path.setFile((_deployment==null || _deployment.length==0)?"" : _deployment[0].getValue());

        _deployment = _dao_target==null?null:_dao_target.getChildren(J2eeTarget.MODULE_WEB+".deployment.web-inf-path");
        webinf_path.setFile((_deployment==null || _deployment.length==0)?"" : _deployment[0].getValue());
    }

    public void setEnabledComponent(boolean enabled) {
        root_path.setEnabled(enabled);
        metainf_path.setEnabled(enabled);
        webinf_path.setEnabled(enabled);
        setup_path.setEnabled(enabled);
    }
}