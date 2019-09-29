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

package net.vpc.dbclient.plugin.toolbox.actions.table;

import net.vpc.dbclient.api.actionmanager.DBCActionLocation;
import net.vpc.dbclient.api.actionmanager.DBCResultTableAction;
import net.vpc.swingext.dialog.MessageDialogType;
import net.vpc.swingext.SwingUtilities3;

import java.awt.event.ActionEvent;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 6 mai 2006 18:47:41
 */
public class ColumnAverageJTableAction extends DBCResultTableAction {

    public ColumnAverageJTableAction() {
        super("ColumnAverageJTableAction");
        addLocationPath(DBCActionLocation.POPUP, "/");
    }

    public void actionPerformedImpl(ActionEvent ae) throws Throwable {
        int j = getColumn();
        if (j < 0) {
            return;
        }
        double d = 0.0D;
        int max = getTableComponent().getModel().getRowCount();

        for (int i = 0; i < max; i++) {
            Object o = getTableComponent().getModel().getValueAt(i, j);
            if (o instanceof Number) {
                d += ((Number) o).doubleValue();
            }
        }

        d /= max;
        String f = "";
        if (Math.rint(d) == d) {
            f = "" + (int) d;
        } else {
            f = "" + String.valueOf(d);
        }
        //JTextField jtf = new JTextField();
        //jtf.setHorizontalAlignment(0);
        //jtf.setEditable(false);
        getSession().getView().getDialogManager().showMessage(
                SwingUtilities3.getAncestorOfClass(new Class[]{
                        java.awt.Frame.class, java.awt.Dialog.class
                }, getTableComponent().getComponent()), f, MessageDialogType.INFO,
//                "Average is " + max
                getSession().getView().getMessageSet().get("JTable.function.average.msgPattern", new Object[]{max})
        );
    }
}
