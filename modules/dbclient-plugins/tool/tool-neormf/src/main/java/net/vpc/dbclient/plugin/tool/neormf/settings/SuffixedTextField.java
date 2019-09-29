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

import javax.swing.*;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 21 avr. 2007 12:06:34
 */
class SuffixedTextField extends JPanel{
    private JComponent suffix;
    private JTextField textField;

    public SuffixedTextField(JTextField textField,JComponent suffix) {
        this.suffix = suffix;
        this.textField = textField;
        textField.setColumns(20);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(textField);
        add(suffix);
    }

    public JComponent getSuffix() {
        return suffix;
    }

    public JTextField getTextField() {
        return textField;
    }

    public String getText(){
        return textField.getText();
    }

    public void setText(String text){
        textField.setText(text);
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        textField.setEnabled(enabled);
        suffix.setEnabled(enabled);
    }
}
