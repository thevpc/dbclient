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

import javax.swing.*;
import java.sql.Types;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 2 aout 2006 19:57:03
 */
public class BooleanColumnView extends DefaultColumnView {
    public BooleanColumnView() {
        super();
    }

    @Override
    protected JComponent createEditComponent() {
        JComponent editComponent;
        editComponent = new JCheckBox();
        install(editComponent);
        return editComponent;
    }

    @Override
    public void setValue(Object value) {
        boolean b = (value instanceof Boolean) ? ((Boolean) value).booleanValue() : (value instanceof String) && ("yes".equals(value) || "true".equals(value) || "on".equals(value));
        ((JCheckBox) getEditComponentDelegate()).setSelected(b);
    }

    @Override
    public Object getValue() {
        return (((JCheckBox) getEditComponentDelegate()).isSelected()) ? new Integer(1) : new Integer(0);
    }

    @Override
    public boolean accept(DBTableColumn column) {
        int sqlType = column.getSqlType();
        return sqlType == Types.BOOLEAN || sqlType == Types.BIT;
    }

}
