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

package net.thevpc.dbclient.plugin.tool.neormf;

import net.thevpc.common.swing.file.JFileTextField;
import org.vpc.neormf.jbgen.config.ConfigNode;
import net.thevpc.dbclient.plugin.tool.neormf.settings.JFileTextFieldDefault;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 24 avr. 2007 18:51:22
 */
public class NUtils {

    public static void storeValue(ConfigNode c, Component comp, String defaultValueForEmpty, boolean removeIfEmpty) {
        storeAttr(c, comp, ConfigNode.ATTRIBUTE_VALUE, defaultValueForEmpty, removeIfEmpty);
    }

    public static void storeName(ConfigNode c, Component comp, String defaultValueForEmpty, boolean removeIfEmpty) {
        storeAttr(c, comp, ConfigNode.ATTRIBUTE_NAME, defaultValueForEmpty, removeIfEmpty);
    }

    public static void storeAttr(ConfigNode c, Component comp, String name, String defaultValueForEmpty) {
        storeAttr(c, comp, name, defaultValueForEmpty, false);
    }

    public static void storeAttr(ConfigNode c, Component comp, String name, String defaultValueForEmpty, boolean removeIfEmpty) {
        if (defaultValueForEmpty == null) {
            defaultValueForEmpty = "**RoMbAtAkAyA**";//Never:)
        }
        if (comp instanceof JTextField) {
            JTextField t = (JTextField) comp;
            String s = t.getText().trim();
            if (defaultValueForEmpty.equals(s)) {
                c.setAttribute(name, null);
            } else {
                c.setAttribute(name, s);
            }
        } else if (comp instanceof JFileTextField) {
            JFileTextField t = (JFileTextField) comp;
            String s = t.getText().trim();
            if (defaultValueForEmpty.equals(s)) {
                c.setAttribute(name, null);
            } else {
                c.setAttribute(name, s);
            }
        } else if (comp instanceof JFileTextFieldDefault) {
            JFileTextFieldDefault t = (JFileTextFieldDefault) comp;
            String s = t.getFilePath();
            if(s!=null){
                s=s.trim();
            }
            if (defaultValueForEmpty.equals(s)) {
                c.setAttribute(name, null);
            } else {
                c.setAttribute(name, s);
            }
        } else if (comp instanceof JCheckBox) {
            JCheckBox t = (JCheckBox) comp;
            String s = t.isSelected() ? "true" : "false";
            if (defaultValueForEmpty.equals(s)) {
                c.setAttribute(name, null);
            } else {
                c.setAttribute(name, s);
            }
        } else if (comp instanceof JComboBox) {
            JComboBox t = (JComboBox) comp;
            String s = (String) t.getSelectedItem();
            if (defaultValueForEmpty.equals(s)) {
                c.setAttribute(name, null);
            } else {
                c.setAttribute(name, s);
            }
        } else {
            throw new IllegalArgumentException("What?");
        }
        if (removeIfEmpty && c.getAttributes().size() == 0) {
            c.remove();
        }
    }

    public static void loadAttr(ConfigNode c, Component comp, String name, String defaultValueForEmpty) {
        String v = c == null ? null : c.getAttribute(name);
        if (v == null) {
            v = defaultValueForEmpty;
        }
        if (comp instanceof JTextField) {
            JTextField t = (JTextField) comp;
            t.setText(v);
        } else if (comp instanceof JCheckBox) {
            JCheckBox t = (JCheckBox) comp;
            t.setSelected("true".equalsIgnoreCase(v));
        } else if (comp instanceof JComboBox) {
            JComboBox t = (JComboBox) comp;
            t.setSelectedItem(v);
        } else {
            throw new IllegalArgumentException("What?");
        }
    }

    public static ConfigNode findChild(ConfigNode node, String name, NotFoundAction notFoundAction) {
        return findChild(node, name, true, true, notFoundAction);
    }

    public static ConfigNode findChildByPath(ConfigNode node, String path, NotFoundAction notFoundAction) {
        return findChildByPath(node, path, true, true, notFoundAction);
    }

    public static ConfigNode findChildByPath(ConfigNode node, String path, boolean onlyEnabled, boolean createIfNotFound,NotFoundAction notFoundAction) {
        String[] strings = path.split("\\.");
        ConfigNode parent = node;
        for (String name : strings) {
            parent = findChild(parent, name, onlyEnabled, createIfNotFound, notFoundAction);
        }
        return parent;
    }

    public static enum NotFoundAction{
      NOTHING,
      DELETE,
      DISABLE
    }

    /**
     * @param node
     * @param name
     * @param onlyEnabled
     * @param createIfNotFound
     * @param notFoundAction
     * @return
     */
    public static ConfigNode findChild(ConfigNode node, String name, boolean onlyEnabled, boolean createIfNotFound, NotFoundAction notFoundAction) {
        ConfigNode[] child = node.getChildren(name, false, false);
        ArrayList<ConfigNode> enabled=new ArrayList<ConfigNode>(child.length);
        ArrayList<ConfigNode> disabled=new ArrayList<ConfigNode>(child.length);
        for (ConfigNode configNode : child) {
            if(configNode.isEnabled()){
                enabled.add(configNode);
            }else{
                disabled.add(configNode);
            }
        }
        ConfigNode returnValue=null;
        ArrayList<ConfigNode> all=new ArrayList<ConfigNode>(enabled.size()+disabled.size());
        if(onlyEnabled){
            all=enabled;
        }else{
            all=new ArrayList<ConfigNode>(enabled.size()+disabled.size());
            all.addAll(enabled);
            all.addAll(disabled);
        }
        if(all.size()==0){
            if(createIfNotFound){
                returnValue = new ConfigNode(name);
                node.add(returnValue);
            }
        }else if(all.size()>0){
           returnValue=all.get(0);
        }
        switch (notFoundAction){
            case DELETE:{
                for (ConfigNode n : disabled) {
                    if(n!=returnValue){
                        n.remove();
                    }
                }
                for (ConfigNode n : enabled) {
                    if(n!=returnValue){
                        n.remove();
                    }
                }
                break;
            }
            case DISABLE:{
                for (ConfigNode n : disabled) {
                    if(n!=returnValue){
                        n.setEnabled(false);
                    }
                }
                for (ConfigNode n : enabled) {
                    if(n!=returnValue){
                        n.setEnabled(false);
                    }
                }
                break;
            }
        }
        return returnValue;
    }

    private NUtils() {
    }
}
