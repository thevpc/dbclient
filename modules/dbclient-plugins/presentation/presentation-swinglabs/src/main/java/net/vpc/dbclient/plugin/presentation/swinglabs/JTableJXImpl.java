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

package net.vpc.dbclient.plugin.presentation.swinglabs;

import org.jdesktop.swingx.JXTable;
import net.vpc.prs.plugin.Implementation;
import net.vpc.dbclient.api.viewmanager.DBCTable;

import java.awt.*;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 31 dec. 2006 02:59:24
 */
@Implementation(priority = 10)
public class JTableJXImpl extends JXTable implements DBCTable {

    public JTableJXImpl() {
        setColumnControlVisible(true);
    }


    @Override
    public int convertRowIndexToView(int i) {
        return i < 0 ? i : super.convertRowIndexToView(i);
    }

    @Override
    public int convertRowIndexToModel(int i) {
        return i < 0 ? i : super.convertRowIndexToModel(i);
    }

    public Component getComponent() {
        return this;
    }

}
