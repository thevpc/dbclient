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

import net.vpc.dbclient.api.sessionmanager.DBCSQLEditor;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 5 aout 2006 10:37:44
 */
public class SQLStringColumnView extends StringColumnView{
    public SQLStringColumnView() {
        setConfigurable(false);
    }

    @Override
    protected JComponent createEditComponent() {
        DBCSQLEditor j=getSession().getFactory().newInstance(DBCSQLEditor.class);
        JScrollPane p = new JScrollPane(j.getComponent());
        p.setPreferredSize(new Dimension(200, 70));
        setPreferredDimension(new Dimension(1, 2));
        install(p, (JComponent) j.getComponent());
        return p;
    }

}
