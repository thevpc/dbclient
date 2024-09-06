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

package net.thevpc.dbclient.plugin.system.sessionmanager.window;

import net.thevpc.common.swing.button.JDropDownButton;
import net.thevpc.dbclient.api.sessionmanager.DBCSessionView;
import net.thevpc.common.swing.dialog.MessageDialogType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 29 nov. 2006 22:51:54
 */
public abstract class DBCSessionStatusBarLabel extends JDropDownButton implements ActionListener {
    HashMap<Integer, String> values = new HashMap<Integer, String>();
    HashMap<Integer, JRadioButtonMenuItem> comps = new HashMap<Integer, JRadioButtonMenuItem>();
    String unknown = "UNKNOWN";
    int value = Integer.MIN_VALUE;
    Font f;
    ButtonGroup group = new ButtonGroup();
    protected DBCSessionView defaultSessionMainPanel;

    public DBCSessionStatusBarLabel(DBCSessionView defaultSessionMainPanel) {
        super.setQuickActionDelay(0);
        this.defaultSessionMainPanel = defaultSessionMainPanel;
        setBorderPainted(false);
//        setRollover(true);

//            setBorder(BorderFactory.createEtchedBorder());
        f = getFont();
        f = f.deriveFont(Font.PLAIN, f.getSize() * 0.9f);
        setFont(f);
        setMargin(new Insets(1, 1, 1, 1));
//            JMenuItem item = new JMenuItem("Save as Default");
//            group.add(item);
    }

    public void addValue(int v, String s) {
        values.put(v, s);
        JRadioButtonMenuItem item = new JRadioButtonMenuItem(s);
        comps.put(v, item);
        group.add(item);
        item.setFont(f);
        item.putClientProperty("Key", v);
        item.addActionListener(this);
        add(item);
    }

    public void setValue(int value) {
        String s = values.get(value);
        if (s == null) {
            s = unknown;
        }
        setText(s);
        JRadioButtonMenuItem item = comps.get(value);
        if (item != null) {
            item.setSelected(true);
        }
    }

    public void setText(String text) {
        super.setText(" " + text + " ");
    }

    public void actionPerformed(ActionEvent e) {
        try {
            JComponent comp = (JComponent) e.getSource();
            int k = (Integer) comp.getClientProperty("Key");
            actionPerformedImpl(k);
            setValue(k);
        } catch (Throwable throwable) {
            defaultSessionMainPanel.getSession().getView().getDialogManager().showMessage(null, null, MessageDialogType.ERROR, null, throwable);
        }
    }

    protected void refresh() {
        setValue(value);
    }

    public int getValue() {
        return value;
    }

    public String getUnknown() {
        return unknown;
    }

    public void setUnknown(String unknown) {
        this.unknown = unknown;
    }

    public abstract void actionPerformedImpl(int e) throws Throwable;
}
