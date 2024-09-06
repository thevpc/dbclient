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
package net.thevpc.dbclient.plugin.system.sessionmanager.sqleditor;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.thevpc.dbclient.api.DBCSession;
import net.thevpc.common.prs.plugin.Inject;
import net.thevpc.dbclient.api.sessionmanager.DBCAutoCompleteComponent;
import net.thevpc.dbclient.api.sessionmanager.DBCAutoCompleteInfo;
import net.thevpc.dbclient.api.sessionmanager.DBCAutoCompleteListener;
import net.thevpc.dbclient.api.sessionmanager.DBCSQLEditor;
import net.thevpc.dbclient.api.sql.objects.*;
import net.thevpc.common.swing.SwingUtilities3;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;
import java.util.TreeSet;
import java.util.Vector;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 26 aout 2006 11:20:22
 */
public class JPopupMenuAutoCompleteComponent extends JPopupMenu implements DBCAutoCompleteComponent {

    private int autocompleteListPageSize = 20;
    private int autocompleteListMaxSize = 100;
    private Vector<DBCAutoCompleteListener> listeners;
    private DBCAutoCompleteInfo[] data;
    @Inject
    private DBCSession session;
    private static Color preferredBg = new Color(255, 255, 240);

    public JPopupMenuAutoCompleteComponent() {
        setOpaque(true);
        setBackground(preferredBg);
    }

    public JPopupMenuAutoCompleteComponent(String label) {
        super(label);
    }

    public void init(DBCSQLEditor editor) {
    }
    private ActionListener autoCompleteActionListener = new ActionListener() {

        public void actionPerformed(ActionEvent e) {
            try {
                DBCAutoCompleteInfo node = ((DBCAutoCompleteInfo) (((JMenuItem) e.getSource()).getClientProperty("AutoCompleteInfo")));
                fireAutoCompleteListener(node);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    };

    public void setData(DBCAutoCompleteInfo[] valid) {
        data = valid;
        Stack<Component> stack = new Stack<Component>();
        stack.push(this);
        while (!stack.isEmpty()) {
            Object m = stack.pop();
            if (m instanceof JPopupMenu) {
                JPopupMenu p = (JPopupMenu) m;
                for (int i = 0; i < p.getComponentCount(); i++) {
                    stack.push(p.getComponent(i));
                }
            } else if (m instanceof JMenu) {
                JMenu p = (JMenu) m;
                for (int i = 0; i < p.getMenuComponentCount(); i++) {
                    stack.push(p.getMenuComponent(i));
                }
            } else if (m instanceof JMenuItem) {
                ((JMenuItem) m).removeActionListener(autoCompleteActionListener);
            }
        }
        this.removeAll();
        MenuElement menuElement = this;
        TreeSet<String> alreadyAdded = new TreeSet<String>();
        int autocompleteListSize = 0;
        for (DBCAutoCompleteInfo aValid : valid) {
            if (autocompleteListSize == autocompleteListMaxSize) {
                JMenuItem m = new JMenuItem("...");
                m.setFont(this.getFont());
                m.setOpaque(true);
                m.setBackground(preferredBg);
                if (menuElement instanceof JPopupMenu) {
                    ((JPopupMenu) menuElement).add(m);
                } else {
                    ((JMenu) menuElement).add(m);
                }
                break;
            }
            if ((menuElement instanceof JPopupMenu && ((JPopupMenu) menuElement).getComponentCount() == autocompleteListPageSize)
                    || (menuElement instanceof JMenu && ((JMenu) menuElement).getMenuComponentCount() == autocompleteListPageSize)) {
                JMenu m = new JMenu("...");
                m.setOpaque(true);
                m.setBackground(preferredBg);
                m.setFont(this.getFont());
                if (menuElement instanceof JPopupMenu) {
                    ((JPopupMenu) menuElement).add(m);
                } else {
                    ((JMenu) menuElement).add(m);
                }
                menuElement = m;
                //break;
            }
            String id = aValid.getTitle();
            if (id == null) {
                id = aValid.getValue();
            }
            if (id != null) {
                if (alreadyAdded.contains(id)) {
                    continue;
                }
                alreadyAdded.add(id);
                JMenuItem m = new SelectionMenu(aValid);
                m.setFont(this.getFont());
                if (menuElement instanceof JPopupMenu) {
                    ((JPopupMenu) menuElement).add(m);
                } else {
                    ((JMenu) menuElement).add(m);
                }
                m.putClientProperty("AutoCompleteInfo", aValid);
                m.addActionListener(autoCompleteActionListener);
            }
        }
    }

    public void showAutoComplete(DBCAutoCompleteInfo[] valid, final JTextComponent textComponent) throws BadLocationException {
        setData(valid);
        showAutoComplete(textComponent);
    }

    public void showAutoComplete(final JTextComponent textComponent) throws BadLocationException {
        if (data.length == 1) {
            fireAutoCompleteListener(data[0]);
        } else if (data.length > 0) {
            Rectangle r = textComponent.modelToView(data[0].getIndex());
            int he = 16;//(int) (getFont().getLineMetrics(data[0].getWordToReplace(), frc).getHeight()
            Point pt = new Point(r.x, r.y + he);
            this.putClientProperty("Location", pt);
            SwingUtilities3.invokeLater(new Runnable() {

                public void run() {
                    Point pt = (Point) JPopupMenuAutoCompleteComponent.this.getClientProperty("Location");
                    JPopupMenuAutoCompleteComponent.this.show(textComponent, pt.x, pt.y);
                    JPopupMenuAutoCompleteComponent.this.getSelectionModel().setSelectedIndex(0);
                }
            });
        }
    }

    public void addAutoCompleteListener(DBCAutoCompleteListener listener) {
        if (listener != null) {
            if (listeners == null) {
                listeners = new Vector<DBCAutoCompleteListener>();
            }
            listeners.add(listener);
        }
    }

    public void removeAutoCompleteListener(DBCAutoCompleteListener listener) {
        if (listener != null) {
            if (listeners != null) {
                listeners.remove(listener);
            }
        }
    }

    private void fireAutoCompleteListener(DBCAutoCompleteInfo info) {
        if (listeners != null) {
            for (DBCAutoCompleteListener autoCompleteListener : listeners) {
                autoCompleteListener.autoComplete(info);
            }
        }
    }

    private class SelectionMenu extends JMenuItem {

        String objectTypeName = null;
        DBCAutoCompleteInfoImpl info;

        private SelectionMenu(DBCAutoCompleteInfo info) {
            super("");
            setOpaque(true);
            setBackground(preferredBg);
            DBObject node = info.getObject();
            //String spaces="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
            String spaces = "";
            if (node instanceof DBCatalog) {
                try {
                    objectTypeName = session.getConnection().getMetaData().getCatalogTerm();
                } catch (SQLException ex) {
                    objectTypeName = "database";
                }
                setText("<HTML><B><Font color=black>" + node.getSQL() + "</Font></B>" + spaces + "</Html>");
            }
            if (node instanceof DBDatatype) {
                objectTypeName = "type";
                setText("<HTML><B><Font color=green>" + node.getSQL() + "</Font></B>" + spaces + "</Html>");
            }
            if (node instanceof DBFunction) {
                objectTypeName = "function";
                setText("<HTML><B><Font color=black>" + node.getSQL() + "(...)</Font></B>" + spaces + "</Html>");
            }
            if (node instanceof DBKeyword) {
                objectTypeName = "Keyword";
                setText("<HTML><B><Font color=blue>" + node.getSQL() + "</Font></B>" + spaces + "</Html>");
            }
            if (node instanceof DBProcedureColumn) {
                objectTypeName = "field";
                setText("<HTML><B><Font color=black>" + node.getSQL() + "</Font></B>" + spaces + "</Html>");
            }
            if (node instanceof DBProcedure) {
                objectTypeName = ((DBProcedure) node).getProcedureTerm();
                setText("<HTML><B><Font color=black>" + node.getSQL() + "</Font></B>" + spaces + "</Html>");
            }
            if (node instanceof DBSchema) {
                objectTypeName = ((DBSchema) node).getSchemaTerm();
                setText("<HTML><B><Font color=black>" + node.getSQL() + "</Font></B>" + spaces + "</Html>");
            }
            if (node instanceof DBTableColumn) {
                objectTypeName = "field";
                setText("<HTML><B><Font color=black>" + node.getSQL() + "</Font></B>" + spaces + "</Html>");
            }
            if (node instanceof DBTable) {
                String type = node.getParentObject() == null ? "table" : (node.getParentObject()).getName().toLowerCase();
                type += " / " + ((DBTable) node).getPrefix();
                objectTypeName = type;
                setText("<HTML><B><Font color=black>" + node.getSQL() + "</Font></B>" + spaces + "</Html>");
            }
            setText(node.getSQL());
            setIcon(session.getView().getObjectIcon(node));
        }
//        @Override
//        protected void paintComponent(Graphics g) {
//            super.paintComponent(g);
//            if (objectTypeName != null) {
//                g.setColor(Color.RED);
//                Dimension s = getSize();
//                int ww = g.getFontMetrics().charsWidth(objectTypeName.toCharArray(), 0, objectTypeName.length());
//                int hh = g.getFontMetrics().getHeight();
//                g.drawString(objectTypeName,s.width-ww-3,3);
//
//            }
//        }
    }
}
