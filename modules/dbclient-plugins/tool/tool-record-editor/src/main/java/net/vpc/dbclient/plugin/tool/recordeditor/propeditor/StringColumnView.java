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

package net.vpc.dbclient.plugin.tool.recordeditor.propeditor;

import net.vpc.dbclient.api.sql.objects.DBTableColumn;
import net.vpc.swingext.ComponentResourcesUpdater;
import net.vpc.swingext.PRSManager;
import net.vpc.prs.iconset.IconSet;
import net.vpc.prs.messageset.MessageSet;
import net.vpc.swingext.TextManipSupport;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.sql.Types;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 2 aout 2006 19:57:03
 */
public class StringColumnView extends DefaultColumnView {
    protected static enum TypeEnum {
        Single, Multi, Pwd
    }

    protected TypeEnum type = TypeEnum.Single;
    protected TextManipSupport textSupport;

    public StringColumnView() {
        super();
        setConfigurable(true);
    }

    public void configure() {
        String type = getConfigValue("Type");
        if ("multi".equalsIgnoreCase(type)) {
            this.type = TypeEnum.Multi;
        } else if ("pwd".equalsIgnoreCase(type)) {
            this.type = TypeEnum.Pwd;
        } else {
            this.type = TypeEnum.Single;
        }
    }

    protected JComponent createEditComponent() {
        JComponent editComponent = null;
        switch (type) {
            case Single: {
                editComponent = new JTextField("");
                install(editComponent);
                textSupport = new TextManipSupport((JTextComponent) editComponent);
                break;
            }
            case Multi: {
                JTextArea j = new JTextArea("");
                JScrollPane p = new JScrollPane(j);
                p.setPreferredSize(new Dimension(200, 70));
                setPreferredDimension(new Dimension(1, 2));
                editComponent = p;
                install(editComponent, j);
                textSupport = new TextManipSupport(j);
                break;
            }
            case Pwd: {
                editComponent = new JPasswordField("");
                install(editComponent);
                textSupport = new TextManipSupport((JTextComponent) editComponent);
                break;
            }
        }
        PRSManager.addSupport(editComponent, "Text", new ComponentResourcesUpdater() {
            public void update(JComponent comp, String id, MessageSet messegSet, IconSet iconSet) {
                PRSManager.update(textSupport.getActions(), messegSet, iconSet);
            }
        });
        return editComponent;
    }

    public void setValue(Object value) {
        ((JTextComponent) getEditComponentDelegate()).setText(value == null ? "" : (String) value);
    }

    public Object getValue() {
        return (((JTextComponent) getEditComponentDelegate()).getText());
    }

    public boolean accept(DBTableColumn column) {
        int sqlType = column.getSqlType();
        return
                sqlType == Types.CHAR
                        || sqlType == Types.VARCHAR
                        || sqlType == Types.BINARY
                        || sqlType == Types.VARBINARY
                        || sqlType == Types.LONGVARBINARY
                        || sqlType == Types.LONGVARCHAR
                ;
    }

    protected ConfigPanel createConfigPanel() {
        return new ConfigStringViewPanel();
    }

    class ConfigStringViewPanel extends ConfigPanel {
        JRadioButton singleLine;
        JRadioButton multiLine;
        JRadioButton password;
        ButtonGroup group = new ButtonGroup();

        public ConfigStringViewPanel() {
            super(new GridLayout(3, 1));
            singleLine = new JRadioButton();
            PRSManager.addMessageSetSupport(singleLine, "StringColumnView.singleLine");
            add(singleLine);
            group.add(singleLine);

            multiLine = new JRadioButton();
            PRSManager.addMessageSetSupport(multiLine, "StringColumnView.multiLine");
            add(multiLine);
            group.add(multiLine);

            password = new JRadioButton();
            PRSManager.addMessageSetSupport(password, "StringColumnView.password");
            add(password);
            group.add(password);
            PRSManager.setComponentResourceSetHolder(ConfigStringViewPanel.this,getPluginSession());
            PRSManager.update(this, getSession().getView());

        }

        public void load() {
            String stype = getConfigValue("Type");
            if ("multi".equalsIgnoreCase(stype)) {
                multiLine.setSelected(true);
            } else if ("pwd".equalsIgnoreCase(stype)) {
                password.setSelected(true);
            } else {
                singleLine.setSelected(true);
            }

        }

        public void store() {
            if (multiLine.isSelected()) {
                setConfigValue("Type", "multi");
            } else if (password.isSelected()) {
                setConfigValue("Type", "pwd");
            } else {
                setConfigValue("Type", "single");
            }
        }
    }

}
