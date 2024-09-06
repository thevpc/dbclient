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
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 2 aout 2006 19:57:03
 */
public class CheckListColumnView extends MappedValuesColumnView {

    private static class CheckList extends JPanel {
        private LinkedHashMap<Object, JCheckBox> btns = new LinkedHashMap<Object, JCheckBox>();
        private ButtonGroup g;

        public CheckList() {
            super();
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            g = new ButtonGroup();
        }

        protected void addItem(Object o, String name) {
            JCheckBox c = new JCheckBox(name);
            c.putClientProperty("index", btns.size());
            c.putClientProperty("object", o);
            btns.put(o, c);
            add(c);
            g.add(c);
        }

        @Override
        public void setEnabled(boolean enabled) {
            super.setEnabled(enabled);
            for (JCheckBox jRadioButton : btns.values()) {
                jRadioButton.setEnabled(enabled);
            }
        }

        public void setValue(Object value) {
            for (JCheckBox jRadioButton : btns.values()) {
                Object o = jRadioButton.getClientProperty("object");
                if (o == value || (o != null && o.equals(value))) {
                    jRadioButton.setSelected(true);
                }
            }
        }

        public Object getValue() {
            for (JCheckBox jRadioButton : btns.values()) {
                if (jRadioButton.isSelected()) {
                    return jRadioButton.getClientProperty("object");
                }
            }
            return null;
        }

    }

    public CheckListColumnView(ColumnView base) {
        super(base);
        setConfigurable(true);
    }

    @Override
    protected JComponent createEditComponent() {
        CheckListColumnView.CheckList editComponent = new CheckListColumnView.CheckList();
        for (Map.Entry<Object, String> entry : getData().entrySet()) {
            editComponent.addItem(entry.getKey(), entry.getValue());
        }
        install(editComponent);
        return editComponent;
    }

    @Override
    public void setValue(Object value) {
        CheckListColumnView.CheckList com = ((CheckListColumnView.CheckList) getEditComponentDelegate());
        com.setValue(value);
    }

    @Override
    public Object getValue() {
        CheckListColumnView.CheckList com = ((CheckListColumnView.CheckList) getEditComponentDelegate());
        return com.getValue();
    }

}
