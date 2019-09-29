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

import javax.swing.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 2 aout 2006 19:57:03
 */
public class RadioListColumnView extends MappedValuesColumnView {

    private static class RadioList extends JPanel {
        private LinkedHashMap<Object, JRadioButton> radios = new LinkedHashMap<Object, JRadioButton>();
        private ButtonGroup g;

        public RadioList() {
            super();
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            g = new ButtonGroup();
        }

        protected void addItem(Object o, String name) {
            JRadioButton c = new JRadioButton(name);
            c.putClientProperty("index", radios.size());
            c.putClientProperty("object", o);
            radios.put(o, c);
            add(c);
            g.add(c);
        }

        public void setEnabled(boolean enabled) {
            super.setEnabled(enabled);
            for (JRadioButton jRadioButton : radios.values()) {
                jRadioButton.setEnabled(enabled);
            }
        }

        public void setValue(Object value) {
            for (JRadioButton jRadioButton : radios.values()) {
                Object o = jRadioButton.getClientProperty("object");
                if (o == value || (o != null && o.equals(value))) {
                    jRadioButton.setSelected(true);
                }
            }
        }

        public Object getValue() {
            for (JRadioButton jRadioButton : radios.values()) {
                if (jRadioButton.isSelected()) {
                    return jRadioButton.getClientProperty("object");
                }
            }
            return null;
        }

    }

    public RadioListColumnView(ColumnView base) {
        super(base);
        setConfigurable(true);
    }

    protected JComponent createEditComponent() {
        RadioList editComponent = new RadioList();
        for (Map.Entry<Object, String> entry : getData().entrySet()) {
            editComponent.addItem(entry.getKey(), entry.getValue());
        }
        install(editComponent);
        return editComponent;
    }

    public void setValue(Object value) {
        RadioList com = ((RadioList) getEditComponentDelegate());
        com.setValue(value);
    }

    public Object getValue() {
        RadioList com = ((RadioList) getEditComponentDelegate());
        return com.getValue();
    }

}
