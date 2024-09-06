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

import net.thevpc.dbclient.api.sessionmanager.DBCAutoCompleteComponent;
import net.thevpc.dbclient.api.sessionmanager.DBCAutoCompleteInfo;
import net.thevpc.dbclient.api.sessionmanager.DBCAutoCompleteListener;
import net.thevpc.dbclient.api.sessionmanager.DBCSQLEditor;
import net.thevpc.common.swing.SwingUtilities3;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * largly inspered from
 * http://forums.sun.com/thread.jspa?threadID=575898
 *
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 26 aout 2006 11:20:22
 */
public class JListAutoCompleteComponent extends JWindow implements DBCAutoCompleteComponent {

    private List<DBCAutoCompleteListener> listeners;
    private DBCAutoCompleteInfo[] data;
    private JList list;
    private Map clientProperties;
    private DBCSQLEditor editor;
    private Point theRelativePosition;

    public JListAutoCompleteComponent() {
        list = new JList();
        JScrollPane jsp = new JScrollPane(list);
        jsp.setPreferredSize(new Dimension(300, 100));
        add(jsp);
        list.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e)) {
                    onSelected();
//                    dispose();
                }
            }
        });
        list.addKeyListener(new WordMenuKeyListener());
        //list.addMouseListener(new WordMenuMouseListener());

//        setAlwaysOnTop(true);
        setFocusable(true);
//        setFocusableWindowState(true);
    }

    private void onSelected() {
        int i = list.getSelectedIndex();
        if (i >= 0) {
            fireAutoCompleteListener(data[i]);
        }
        setVisible(false);
    }

    private class WordMenuKeyListener extends KeyAdapter {

        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                onSelected();
            }
        }
    }

    public void display(Point aPoint) {
        theRelativePosition = aPoint;
        Point p = editor.getLocationOnScreen();
        setLocation(new Point(p.x + aPoint.x, p.y + aPoint.y));
        setVisible(true);
    }

    public void move() {
        if (theRelativePosition != null) {
            Point p = editor.getLocationOnScreen();
            setLocation(new Point(p.x + theRelativePosition.x, p.y + theRelativePosition.y));
        }
    }

    public void moveDown() {
        if (data.length < 1) {
            return;
        }
        int current = list.getSelectedIndex();
        int newIndex = Math.min(data.length - 1, current + 1);
        list.setSelectionInterval(newIndex, newIndex);
        list.scrollRectToVisible(list.getCellBounds(newIndex, newIndex));
    }

    public void moveUp() {
        if (data.length < 1) {
            return;
        }
        int current = list.getSelectedIndex();
        int newIndex = Math.max(0, current - 1);
        list.setSelectionInterval(newIndex, newIndex);
        list.scrollRectToVisible(list.getCellBounds(newIndex, newIndex));
    }

    public void moveStart() {
        if (data.length < 1) {
            return;
        }
        list.setSelectionInterval(0, 0);
        list.scrollRectToVisible(list.getCellBounds(0, 0));
    }

    public void moveEnd() {
        if (data.length < 1) {
            return;
        }
        int endIndex = data.length - 1;
        list.setSelectionInterval(endIndex, endIndex);
        list.scrollRectToVisible(list.getCellBounds(endIndex, endIndex));
    }

    public void movePageUp() {
        if (data.length < 1) {
            return;
        }
        int current = list.getSelectedIndex();
        int newIndex = Math.max(0, current - Math.max(0, list.getVisibleRowCount() - 1));
        list.setSelectionInterval(newIndex, newIndex);
        list.scrollRectToVisible(list.getCellBounds(newIndex, newIndex));
    }

    public void movePageDown() {
        if (data.length < 1) {
            return;
        }
        int current = list.getSelectedIndex();
        int newIndex = Math.min(data.length - 1, current + Math.max(0, list.getVisibleRowCount() - 1));
        list.setSelectionInterval(newIndex, newIndex);
        list.scrollRectToVisible(list.getCellBounds(newIndex, newIndex));
    }

    public void init(DBCSQLEditor editor) {
        this.editor = editor;
        editor.addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                if (e.isConsumed()) {
                    return;
                }
                if (isVisible()) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        onSelected();
                        e.consume();
                    } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                        setVisible(false);
                        e.consume();
                    } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                        moveDown();
                        e.consume();
                    } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                        moveUp();
                        e.consume();
                    } else if (e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
                        movePageDown();
                        e.consume();
                    } else if (e.getKeyCode() == KeyEvent.VK_PAGE_UP) {
                        movePageUp();
                        e.consume();
                    }
                }
            }
        });
        editor.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                if (isVisible()) {
                    setVisible(false);
                }
            }
        });

        editor.addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent e) {
                //do nothing
            }

            public void focusLost(FocusEvent e) {
                if (isVisible()) {
                    setVisible(false);
                }
            }
        });
        //TODO
    }

    public void setData(DBCAutoCompleteInfo[] valid) {
        data = valid;
        list.setModel(new AbstractListModel() {

            public int getSize() {
                return data.length;
            }

            public Object getElementAt(int i) {
                return data[i];
            }
        });
        if (data.length > 0) {
            list.setSelectedIndex(0);
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
//            show0(textComponent,r.x,r.y + (int) (getFont().getLineMetrics(data[0].getWordToReplace(), SwingUtilities2.getFontRenderContext(this)).getHeight()));
            Point t0 = textComponent.getLocationOnScreen();
//            int he = (int) (getFont().getLineMetrics(data[0].getWordToReplace(), frc).getHeight());
            int he = 16;
            final Point pt = new Point(t0.x + r.x, t0.y + r.y + he);
            SwingUtilities3.invokeLater(new Runnable() {

                public void run() {
//                    setLocationRelativeTo(textComponent);
                    setLocation(pt.x, pt.y);
                    pack();
                    setVisible(true);
                    requestFocus();
                    list.requestFocus();
                }
            });
        }
    }
    FontRenderContext frc;

    public void paint(Graphics g) {
        super.paint(g);
        frc = ((Graphics2D) g).getFontRenderContext();
    }

    public void addAutoCompleteListener(DBCAutoCompleteListener listener) {
        if (listener != null) {
            if (listeners == null) {
                synchronized (this) {
                    if (listeners == null) {
                        listeners = new ArrayList<DBCAutoCompleteListener>();
                    }
                    listeners.add(listener);
                }
            } else {
                synchronized (this) {
                    listeners.add(listener);
                }
            }
        }
    }

    public void removeAutoCompleteListener(DBCAutoCompleteListener listener) {
        if (listener != null) {
            if (listeners != null) {
                synchronized (this) {
                    if (listeners != null) {
                        listeners.remove(listener);
                    }
                }
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

    public Component getComponent() {
        return this;
    }

    public void show0(Component owner, int x, int y) {
        Container parent = null;

        if (owner != null) {
            parent = (owner instanceof Container ? (Container) owner : owner.getParent());
        }

        // Try to find a JLayeredPane and Window to add
        for (Container p = parent; p != null; p = p.getParent()) {
            if (p instanceof JRootPane) {
                if (p.getParent() instanceof JInternalFrame) {
                    continue;
                }
                parent = ((JRootPane) p).getLayeredPane();
                // Continue, so that if there is a higher JRootPane, we'll
                // pick it up.
            } else if (p instanceof Window) {
                if (parent == null) {
                    parent = p;
                }
                break;
            } else if (p instanceof JApplet) {
                // Painting code stops at Applets, we don't want
                // to add to a Component above an Applet otherwise
                // you'll never see it painted.
                break;
            }
        }

        Point p = convertScreenLocationToParent(parent, x,
                y);
        Component component = getComponent();

        component.setLocation(p.x, p.y);
        if (parent instanceof JLayeredPane) {
            parent.add(component, JLayeredPane.POPUP_LAYER, 0);
        } else {
            parent.add(component);
        }
    }

    static Point convertScreenLocationToParent(Container parent, int x, int y) {
        for (Container p = parent; p != null; p = p.getParent()) {
            if (p instanceof Window) {
                Point point = new Point(x, y);

                SwingUtilities.convertPointFromScreen(point, parent);
                return point;
            }
        }
        throw new Error("convertScreenLocationToParent: no window ancestor");
    }

    /**
     * Returns the value of the property with the specified key.  Only
     * properties added with <code>putClientProperty</code> will return
     * a non-<code>null</code> value.
     *
     * @param key the being queried
     * @return the value of this property or <code>null</code>
     * @see #putClientProperty
     */
    public final Object getClientProperty(Object key) {
        if (clientProperties == null) {
            return null;
        } else {
            return getClientProperties().get(key);
        }
    }

    /**
     * Returns a <code>HashTable</code> containing all of the
     * key/value "client properties" for this component. If the
     * <code>clientProperties</code> hash table doesn't previously
     * exist, an empty one (of size 2) will be created.
     *
     * @return a small <code>Hashtable</code>
     * @see #putClientProperty
     * @see #getClientProperty
     */
    private Map getClientProperties() {
        if (clientProperties == null) {
            clientProperties = new HashMap(2);
        }
        return clientProperties;
    }

    /**
     * Adds an arbitrary key/value "client property" to this component.
     * <p/>
     * The <code>get/putClientProperty</code> methods provide access to
     * a small per-instance hashtable. Callers can use get/putClientProperty
     * to annotate components that were created by another module.
     * For example, a
     * layout manager might store per child constraints this way. For example:
     * <pre>
     * componentA.putClientProperty("to the left of", componentB);
     * </pre>
     * If value is <code>null</code> this method will remove the property.
     * Changes to client properties are reported with
     * <code>PropertyChange</code> events.
     * The name of the property (for the sake of PropertyChange
     * events) is <code>key.toString()</code>.
     * <p/>
     * The <code>clientProperty</code> dictionary is not intended to
     * support large
     * scale extensions to JComponent nor should be it considered an
     * alternative to subclassing when designing a new component.
     *
     * @param key   the new client property key
     * @param value the new client property value; if <code>null</code>
     *              this method will remove the property
     * @see #getClientProperty
     * @see #addPropertyChangeListener
     */
    public final void putClientProperty(Object key, Object value) {
        if (value == null && clientProperties == null) {
            // Both the value and Hashtable are null, implying we don't
            // have to do anything.
            return;
        }
        Object oldValue = getClientProperties().get(key);

        if (value != null) {
            getClientProperties().put(key, value);
            firePropertyChange(key.toString(), oldValue, value);
        } else if (oldValue != null) {
            getClientProperties().remove(key);
            firePropertyChange(key.toString(), oldValue, value);
        }
    }
}
