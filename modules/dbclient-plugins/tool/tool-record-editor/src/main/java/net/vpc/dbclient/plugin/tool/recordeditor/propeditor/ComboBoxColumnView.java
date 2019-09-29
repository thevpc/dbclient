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
import java.awt.*;
import java.util.Set;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 2 aout 2006 19:57:03
 */
public class ComboBoxColumnView extends MappedValuesColumnView {
    private ListCellRenderer renderer = new DefaultListCellRenderer() {
        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            return super.getListCellRendererComponent(list, getData().get(value), index, isSelected, cellHasFocus);
        }
    };

    public ComboBoxColumnView(ColumnView base) {
        super(base);
        setConfigurable(true);
    }



    @Override
    protected JComponent createEditComponent() {
        JComboBox editComponent;
        Set<Object> d = getData().keySet();
        editComponent = new JComboBox((Object[]) d.toArray(new Object[d.size()]));
        editComponent.setRenderer(renderer);
        install(editComponent);
        return editComponent;
    }

    @Override
    public void setValue(Object value) {
        JComboBox com = ((JComboBox) getEditComponentDelegate());
        com.setSelectedItem(value);
    }

    @Override
    public Object getValue() {
        JComboBox com = ((JComboBox) getEditComponentDelegate());
        return com.getSelectedItem();
    }

    public ListCellRenderer getRenderer() {
        return renderer;
    }

}
