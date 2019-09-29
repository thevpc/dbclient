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
import javax.swing.text.DateFormatter;
import java.sql.Date;
import java.sql.Types;
import java.text.ParseException;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 2 aout 2006 19:57:03
 */
public class DateColumnView extends DefaultColumnView {
    public DateColumnView() {
        super();
    }

    @Override
    protected JComponent createEditComponent() {
        JComponent editComponent;
        editComponent = new JFormattedTextField(new DateFormatter(getSession().getConfig().getDateFormat()));
        install(editComponent);
        return editComponent;
    }

    @Override
    public void setValue(Object value) {
        ((JFormattedTextField) getEditComponentDelegate()).setValue(ensureConvert(value));
    }

    @Override
    public Object getValue() {
        return ensureConvert(((JFormattedTextField) getEditComponentDelegate()).getValue());
    }

    protected Object ensureConvert(Object value) {
        try {
            return
                    (value instanceof Date) ? (Date) value :
                            (value instanceof java.util.Date) ?
                                    new Date(((java.util.Date) value).getTime())
                                    : (value instanceof String) ?
                                    ((((String) value).length() == 0) ? null :
                                            new Date(getSession().getConfig().getDateFormat().parse((String) value).getTime())) : null;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean accept(DBTableColumn column) {
        int sqlType = column.getSqlType();
        return sqlType == Types.DATE;
    }
}
