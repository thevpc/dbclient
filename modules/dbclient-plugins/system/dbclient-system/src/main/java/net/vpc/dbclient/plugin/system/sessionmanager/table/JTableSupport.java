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
package net.vpc.dbclient.plugin.system.sessionmanager.table;

import net.vpc.dbclient.api.sessionmanager.DBCResultTable;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 26 avr. 2006 20:18:39
 */
public class JTableSupport {

    private DBCResultTable jTable;
//    private DBCResultTableAction quickSearchJTableAction;
//    private DBCResultTableAction copyCellJTableAction;
//    private DBCResultTableAction copyColumnNameJTableAction;
//    private DBCResultTableAction columnAverageJTableAction;
//    private DBCResultTableAction columnSumJTableAction;
    private int row = -1;
    private int column = -1;
    private JPopupMenu popup;
    private boolean installed;

    public JTableSupport(DBCResultTable jTable) {
        this.jTable = jTable;
//        quickSearchJTableAction = new QuickSearchJTableAction(jTable);
//        copyCellJTableAction = new CopyCellJTableAction(jTable);
//        copyColumnNameJTableAction = new CopyColumnNameJTableAction(jTable);
//        columnAverageJTableAction = new ColumnAverageJTableAction(jTable);
//        columnSumJTableAction = new ColumnSumJTableAction(jTable);
    }

    public Action[] getDefaultActions() {
        return new Action[]{
                //quickSearchJTableAction, copyCellJTableAction, copyColumnNameJTableAction, columnAverageJTableAction, columnSumJTableAction
        };
    }

    public void install() {
        if (!installed) {
            installSearchSupport();
            installPopupSupportToParentScrollPane();
            installPopupSupport();
            installPopupDefaultActions();
            installed = true;
        }
    }

    public void installPopupDefaultActions() {
//        JPopupMenu popup = getPopup();
//        popup.add(quickSearchJTableAction);
//        popup.add(copyCellJTableAction);
//        popup.add(copyColumnNameJTableAction);
//        popup.addSeparator();
//        popup.add(columnSumJTableAction);
//        popup.add(columnAverageJTableAction);
    }

    public void installSearchSupport() {
//        Component cmp = jTable.getTable().getComponent();
//        if (cmp instanceof JComponent) {
//            InputMap inputMap = ((JComponent)cmp).getInputMap(JComponent.WHEN_FOCUSED);
//
//            if (inputMap != null) {
//                AbstractAction searchAction = new AbstractAction() {
//
//                    public void actionPerformed(ActionEvent e) {
//                        quickSearchJTableAction.setResultTable(jTable);
//                        quickSearchJTableAction.actionPerformed(e);
//                    }
//                };
//                ActionMap actionMap = ((JComponent)cmp).getActionMap();
//                inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK), searchAction);
//
//                if (actionMap != null) {
//                    actionMap.put(searchAction, searchAction);
//                }
//            }
//        }
    }

    public void installPopupSupport() {
        jTable.getTableComponent().getComponent().addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                row = -1;
                column = -1;
                if (e.getClickCount() == 1 && SwingUtilities.isRightMouseButton(e)) {
                    row = jTable.getTableComponent().rowAtPoint(e.getPoint());
                    if (row >= 0) {
                        column = jTable.getTableComponent().columnAtPoint(e.getPoint());
                        if (column >= 0) {
//                            jTable.setColumnSelectionInterval(x, x);
//                            jTable.setRowSelectionInterval(i, i);
                            DBCResultTableEnableIsVisibleSwingWorker sw = new DBCResultTableEnableIsVisibleSwingWorker(getPopup(), e, jTable, true);
                            sw.execute();
                        }
                    } else {
                        DBCResultTableEnableIsVisibleSwingWorker sw = new DBCResultTableEnableIsVisibleSwingWorker(getPopup(), e, jTable, true);
                        sw.execute();
                    }
                }
            }
        });
    }

    public void installPopupSupportToParentScrollPane() {
        JScrollPane jScrollPane = (JScrollPane) SwingUtilities.getAncestorOfClass(JScrollPane.class, jTable.getTableComponent().getComponent());
        if (jScrollPane != null) {
            jScrollPane.addMouseListener(new MouseAdapter() {

                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 1 && SwingUtilities.isRightMouseButton(e)) {
                        DBCResultTableEnableIsVisibleSwingWorker sw = new DBCResultTableEnableIsVisibleSwingWorker(getPopup(), e, jTable, true);
                        sw.execute();
                    }
                }
            });
        }
    }

    public JPopupMenu getPopup() {
        if (popup == null) {
            popup = new JPopupMenu();
        }
        return popup;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
