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

package net.thevpc.dbclient.plugin.toolbox.actions.table;

import net.thevpc.dbclient.api.actionmanager.DBCActionLocation;
import net.thevpc.dbclient.api.actionmanager.DBCResultTableAction;
import net.thevpc.common.swing.SwingUtilities3;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 6 mai 2006 18:47:41
 */
public class ColumnSumJTableAction extends DBCResultTableAction {

    public ColumnSumJTableAction() {
        super("ColumnSumJTableAction");
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

        String f = "";
        if (Math.rint(d) == d)
            f = "" + (int) d;
        else
            f = "" + String.valueOf(d);
        JTextField jtf = new JTextField(f);
        jtf.setHorizontalAlignment(0);
        jtf.setEditable(false);
        JOptionPane.showMessageDialog(SwingUtilities3.getAncestorOfClass(new Class[]{
                java.awt.Frame.class, java.awt.Dialog.class
        }, getTableComponent().getComponent()), jtf,
//                "Sum is " + max
                getSession().getView().getMessageSet().get("JTable.function.sum.msgPattern", new Object[]{max})
//                    SwingUtilities3.getResources().get("JTable.function.sum.msgPattern",new Integer(max))
                , 1);
    }
}
