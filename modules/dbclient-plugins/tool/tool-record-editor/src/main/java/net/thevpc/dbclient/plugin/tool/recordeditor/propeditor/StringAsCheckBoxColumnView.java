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

package net.thevpc.dbclient.plugin.tool.recordeditor.propeditor;

import javax.swing.*;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 2 aout 2006 19:57:03
 */
public class StringAsCheckBoxColumnView extends StringColumnView {
    private String yesValue = "Y";
    private String noValue = "N";

    public StringAsCheckBoxColumnView() {
        super();
    }

    @Override
    protected JComponent createEditComponent() {
        JComponent editComponent;
        editComponent = new JCheckBox("");
        install(editComponent);
        return editComponent;
    }

    @Override
    public void setValue(Object value) {
        ((JCheckBox) getEditComponentDelegate()).setSelected(yesValue.equalsIgnoreCase(String.valueOf(value)));
    }

    @Override
    public Object getValue() {
        return (((JCheckBox) getEditComponentDelegate()).isSelected()) ? yesValue : noValue;
    }

    @Override
    public void configure() {
        yesValue = getPluginSession().getConfig().getStringProperty(getColumn().getStringPath() + ".StringAsCheckBoxColumnView.YES","Y");
        noValue = getPluginSession().getConfig().getStringProperty(getColumn().getStringPath() + ".StringAsCheckBoxColumnView.NO","N");
        if (noValue.equals(yesValue)) {
            noValue = "";
        }
        if (noValue.equals(yesValue)) {
            noValue = "!"+yesValue;
        }
    }

}
