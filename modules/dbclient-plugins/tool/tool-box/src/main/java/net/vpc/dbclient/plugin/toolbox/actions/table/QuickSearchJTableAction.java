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
import net.vpc.dbclient.api.sessionmanager.DBCResultTable;
import net.vpc.dbclient.plugin.system.sessionmanager.table.DBCResultTableQuickSearchTextField;
import net.vpc.swingext.SwingUtilities3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 6 mai 2006 18:47:41
 */
public class QuickSearchJTableAction extends DBCResultTableAction {

    public QuickSearchJTableAction() {
        super("QuickSearchJTableAction");
        addLocationPath(DBCActionLocation.POPUP, "/");
//        addActionEnabler(CELL_SELECTED_ENABLER);
    }

    @Override
    public void setResultTable(DBCResultTable jTable) {
        super.setResultTable(jTable);
        Component cmp = jTable.getTableComponent().getComponent();
        if (cmp instanceof JComponent) {
            InputMap inputMap = ((JComponent) cmp).getInputMap(JComponent.WHEN_FOCUSED);

            if (inputMap != null) {
                ActionMap actionMap = ((JComponent) cmp).getActionMap();
                inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK), this);

                if (actionMap != null) {
                    actionMap.put(this, this);
                }
            }
        }
    }

    public void actionPerformedImpl(ActionEvent e) throws Throwable {
        if (getTableComponent().getSelectedColumn() < 0) {
            return;
        }
        Window owner = (Window) SwingUtilities.getAncestorOfClass(Window.class, getTableComponent().getComponent());
        JWindow w = new JWindow(owner);
        JPanel p = new JPanel(new BorderLayout());

        w.getContentPane().setLayout(new BorderLayout());
        final DBCResultTableQuickSearchTextField quickSearchTextField = new DBCResultTableQuickSearchTextField(getResultTable(), DBCResultTableQuickSearchTextField.Mode.Selection);
        quickSearchTextField.getFilterTextField().putClientProperty("JWindow", w);
        quickSearchTextField.getFilterTextField().addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
                    JWindow w = (JWindow) (((JComponent) e.getSource()).getClientProperty("JWindow"));
                    w.setVisible(false);
                    w.dispose();
                    getTableComponent().getComponent().requestFocus();
                }
            }

            public void keyPressed(KeyEvent e) {
            }

            public void keyReleased(KeyEvent e) {
            }
        });
        p.add(quickSearchTextField, BorderLayout.CENTER);
        p.setBorder(BorderFactory.createEtchedBorder());
        String columnName = getTableComponent().getModel().getColumnName(getTableComponent().getSelectedColumn());
        Box b = Box.createHorizontalBox();
        b.add(Box.createHorizontalStrut(10));
        b.add(new JLabel(columnName));
        b.add(Box.createHorizontalStrut(10));
        p.add(b, BorderLayout.LINE_START);
        w.getContentPane().add(p, BorderLayout.CENTER);
        Component c = SwingUtilities.getAncestorOfClass(JScrollPane.class, getTableComponent().getComponent());
        w.pack();
        w.setLocationRelativeTo(c == null ? getTableComponent().getComponent() : c);
        w.addWindowFocusListener(new WindowFocusListener() {
            public void windowGainedFocus(WindowEvent e) {
            }

            public void windowLostFocus(WindowEvent e) {
                JWindow jWindow = ((JWindow) e.getSource());
                jWindow.setVisible(false);
                jWindow.dispose();
                getTableComponent().getComponent().requestFocus();
            }
        });
        w.setVisible(true);
        SwingUtilities3.invokeLater(new Runnable() {
            public void run() {
                quickSearchTextField.getFilterTextField().requestFocus();
            }
        });
    }
}
