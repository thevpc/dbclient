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
package net.thevpc.dbclient.plugin.system;

import net.thevpc.common.swing.prs.PRSManager;
import net.thevpc.dbclient.api.DBCApplication;
import net.thevpc.dbclient.api.actionmanager.DBCTreeNodeAction;
import net.thevpc.dbclient.api.actionmanager.DBClientAction;
import net.thevpc.dbclient.api.sessionmanager.DBCInternalWindow;
import net.thevpc.dbclient.api.sessionmanager.DBCResultTable;
import net.thevpc.dbclient.api.sql.objects.DBObject;

import javax.swing.*;
import java.awt.*;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 2 févr. 2007 15:41:26
 */
public final class SystemUtils {

    private SystemUtils() {
    }

    public static ImageIcon getDBCImageIcon(String name) {
        return new ImageIcon(DBCApplication.class.getResource("/net/thevpc/dbclient/iconset/" + name + ".jpg"));
    }

    public static void configureResultTableWindow(DBCInternalWindow window, DBClientAction action) {
        window.setTitle(action == null ? null : action.getName());
        if (action instanceof DBCTreeNodeAction) {
            //TODO ROMBATAKAYA
//            window.setHeader(((DBCTreeNodeAction) action).getNodeActionName(), null);
//            window.setIcon(((DBCTreeNodeAction) action).getNodeActionIcon());
        } else {
//            window.setHeader(null, null);
//            window.setIcon(action == null ? null : action.getIcon());

        }
        DBCResultTable t = (DBCResultTable) window.getMainComponent();
        Component statusBar = t.getStatusBar();
        if (statusBar != null) {
//            JComponent headerComponent = window.getHeaderComponent();
//            headerComponent.add(statusBar, 1);
        }
        if (action != null) {
            PRSManager.update(window.getComponent(), action);
        }
    }

    private static boolean validateComponent(Component child, DBObject[] nodes) {
        if (child instanceof AbstractButton) {
            AbstractButton b = (AbstractButton) child;
            if (b.getAction() instanceof DBCTreeNodeAction) {
                DBCTreeNodeAction mi = (DBCTreeNodeAction) b.getAction();
//                System.out.println("validating = " + mi.getKey());
                boolean b1 = mi.setSelectedNodes(nodes);
                b.getAction().setEnabled(b1);
                return b1;
            }
        }
        return false;
    }

    public static void validateTree(Component menuElement, DBObject[] nodes) {
        if (menuElement instanceof JMenu) {
            JMenu m = (JMenu) menuElement;
            for (Component c : m.getMenuComponents()) {
                validateTree(c, nodes);
            }
        } else if (menuElement instanceof JPopupMenu) {
            JPopupMenu m = (JPopupMenu) menuElement;
            for (Component c : m.getComponents()) {
                validateTree(c, nodes);
            }
        } else if (menuElement instanceof JMenuBar) {
            JMenuBar m = (JMenuBar) menuElement;
            for (Component c : m.getComponents()) {
                validateTree(c, nodes);
            }
        } else {
            validateComponent(menuElement, nodes);
        }
    }

//    private static boolean validateMenuVisibility(Component menuElement) {
//        boolean v = false;
//        Component[] children = null;
//        if (menuElement instanceof JMenu) {
//            JMenu m = (JMenu) menuElement;
//            children = m.getMenuComponents();
//        } else if (menuElement instanceof JPopupMenu) {
//            JPopupMenu m = (JPopupMenu) menuElement;
//            children = (m.getComponents());
//        } else if (menuElement instanceof JMenuBar) {
//            JMenuBar m = (JMenuBar) menuElement;
//            children = (m.getComponents());
//        } else if(menuElement instanceof JPopupMenu.Separator || menuElement instanceof JToolBar.Separator){
//            return false;
//        }else{
//            boolean b = menuElement.isEnabled();
//            menuElement.setVisible(b);
//            return b;
//        }
//        if(children!=null){
//            Component lastVisible=null;
//            for (Component c : children) {
//                if (validateMenuVisibility(c)) {
//                    v = true;
//                    lastVisible=c;
//                }else if(c instanceof JPopupMenu.Separator || c instanceof JToolBar.Separator){
//                    if(lastVisible==null){
//                        c.setVisible(false);
//                    }else if(lastVisible instanceof JPopupMenu.Separator || lastVisible instanceof JToolBar.Separator){
//                        c.setVisible(false);
//                    }else{
//                        c.setVisible(true);
//                        lastVisible=c;
//                    }
//                }
//            }
//            menuElement.setVisible(v);
//        }
//        return v;

    //    }

    /**
     * change button Font to smaller
     *
     * @param button
     * @return
     */
    public static JButton prepareToolbarButton(JButton button) {
        button.setFont(button.getFont().deriveFont(Font.PLAIN, button.getFont().getSize() * 0.8f));
        return button;
    }

    public static JToggleButton prepareToolbarButton(JToggleButton button) {
        button.setFont(button.getFont().deriveFont(Font.PLAIN, button.getFont().getSize() * 0.8f));
        return button;
    }
}

