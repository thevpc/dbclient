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
package net.thevpc.dbclient.plugin.system.sessionmanager.sqleditor.tool;

import net.thevpc.dbclient.api.DBCSession;
import net.thevpc.dbclient.api.sessionmanager.DBCSQLCommandPane;
import net.thevpc.dbclient.api.sessionmanager.DBCSQLCommandPaneTool;
import net.thevpc.dbclient.api.viewmanager.DBCPluggablePanel;
import net.thevpc.dbclient.plugin.system.SystemUtils;
import net.thevpc.common.swing.prs.PRSManager;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 15 juil. 2005 7:54:42
 */
public abstract class DBCSQLCommandPaneToolImpl extends DBCPluggablePanel implements DBCSQLCommandPaneTool {

    private DBCSQLCommandPane commandPane;
    private JToggleButton hideButton;
    private JToolBar toolBar;

    public DBCSQLCommandPaneToolImpl() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        setBorder(new RoundBorder());
    }

    public void init(DBCSQLCommandPane commandPane) {
        toolBar = new JToolBar(JToolBar.HORIZONTAL);
        toolBar.setRollover(true);
        toolBar.setFloatable(false);
        toolBar.setBorderPainted(false);
        hideButton = PRSManager.createToggleButton("SQLQueryTool");
        hideButton.setBorderPainted(false);
        hideButton.setContentAreaFilled(false);
        hideButton.setFocusPainted(false);
        Insets oldMargin = hideButton.getMargin();
        if (oldMargin == null) {
            oldMargin = new Insets(0, 0, 0, 0);
        }
        hideButton.setMargin(new Insets(oldMargin.top, 0, oldMargin.bottom, 0));
        this.commandPane = commandPane;
        hideButton.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {
                updateVisibility();
            }
        });
        add(hideButton);
        add(toolBar);
    }

    public void addActionButton(Component comp) {
        toolBar.add(comp);
    }

    public void setCollapsed(boolean collapsed) {
        hideButton.setSelected(collapsed);
    }

    @Override
    protected void addImpl(Component comp, Object constraints, int index) {
        if (comp instanceof JButton) {
            JButton bb = SystemUtils.prepareToolbarButton((JButton) comp);
            PRSManager.setShortNamePreferred(bb, true);
        } else if (comp instanceof JToggleButton) {
            JToggleButton bb = SystemUtils.prepareToolbarButton((JToggleButton) comp);
            PRSManager.setShortNamePreferred(bb, true);
        }
        super.addImpl(comp, constraints, index);
        updateVisibility();
    }

    protected void updateVisibility() {
        toolBar.setVisible(!hideButton.isSelected());
    }

    public DBCSQLCommandPane getPane() {
        return commandPane;
    }

    public DBCSession getSession() {
        return getPane().getSession();
    }

    public Component getComponent() {
        return this;
    }

    public class RoundBorder extends AbstractBorder {
        /**
         * Raised etched type.
         */
        public static final int RAISED = 0;
        /**
         * Lowered etched type.
         */
        public static final int LOWERED = 1;

        protected int etchType;
        protected Color highlight;
        protected Color shadow;

        /**
         * Creates a lowered etched border whose colors will be derived
         * from the background color of the component passed into
         * the paintBorder method.
         */
        public RoundBorder() {
            this(LOWERED);
        }

        /**
         * Creates an etched border with the specified etch-type
         * whose colors will be derived
         * from the background color of the component passed into
         * the paintBorder method.
         *
         * @param etchType the type of etch to be drawn by the border
         */
        public RoundBorder(int etchType) {
            this(etchType, null, null);
        }

        /**
         * Creates a lowered etched border with the specified highlight and
         * shadow colors.
         *
         * @param highlight the color to use for the etched highlight
         * @param shadow    the color to use for the etched shadow
         */
        public RoundBorder(Color highlight, Color shadow) {
            this(LOWERED, highlight, shadow);
        }

        /**
         * Creates an etched border with the specified etch-type,
         * highlight and shadow colors.
         *
         * @param etchType  the type of etch to be drawn by the border
         * @param highlight the color to use for the etched highlight
         * @param shadow    the color to use for the etched shadow
         */
        public RoundBorder(int etchType, Color highlight, Color shadow) {
            this.etchType = etchType;
            this.highlight = highlight;
            this.shadow = shadow;
        }

        /**
         * Paints the border for the specified component with the
         * specified position and size.
         *
         * @param c      the component for which this border is being painted
         * @param g      the paint graphics
         * @param x      the x position of the painted border
         * @param y      the y position of the painted border
         * @param width  the width of the painted border
         * @param height the height of the painted border
         */
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            int w = width;
            int h = height;

            g.translate(x, y);

            g.setColor(etchType == LOWERED ? getShadowColor(c) : getHighlightColor(c));
            g.drawRoundRect(2, 2, w - 6, h - 6, 10, 10);
//        g.drawRect(0, 0, w-2, h-2);

            g.setColor(etchType == LOWERED ? getHighlightColor(c) : getShadowColor(c));
            g.drawRoundRect(2, 2, w - 6 - 1, h - 6 - 1, 10, 10);
//        g.drawLine(1, h-3, 1, 1);
//        g.drawLine(1, 1, w-3, 1);
//
//        g.drawLine(0, h-1, w-1, h-1);
//        g.drawLine(w-1, h-1, w-1, 0);

            g.translate(-x, -y);
        }

        /**
         * Returns the insets of the border.
         *
         * @param c the component for which this border insets value applies
         */
        public Insets getBorderInsets(Component c) {
            return new Insets(2, 2, 2, 2);
        }

        /**
         * Reinitialize the insets parameter with this Border's current Insets.
         *
         * @param c      the component for which this border insets value applies
         * @param insets the object to be reinitialized
         */
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.top = insets.right = insets.bottom = 2;
            return insets;
        }

        /**
         * Returns whether or not the border is opaque.
         */
        public boolean isBorderOpaque() {
            return true;
        }

        /**
         * Returns which etch-type is set on the etched border.
         */
        public int getEtchType() {
            return etchType;
        }

        /**
         * Returns the highlight co,lor of the etched border
         * when rendered on the specified component.  If no highlight
         * color was specified at instantiation, the highlight color
         * is derived from the specified component's background color.
         *
         * @param c the component for which the highlight may be derived
         */
        public Color getHighlightColor(Component c) {
            return highlight != null ? highlight :
                    c.getBackground().brighter();
        }

        /**
         * Returns the highlight color of the etched border.
         * Will return null if no highlight color was specified
         * at instantiation.
         */
        public Color getHighlightColor() {
            return highlight;
        }

        /**
         * Returns the shadow color of the etched border
         * when rendered on the specified component.  If no shadow
         * color was specified at instantiation, the shadow color
         * is derived from the specified component's background color.
         *
         * @param c the component for which the shadow may be derived
         */
        public Color getShadowColor(Component c) {
            return shadow != null ? shadow : c.getBackground().darker();
        }

        /**
         * Returns the shadow color of the etched border.
         * Will return null if no shadow color was specified
         * at instantiation.
         */
        public Color getShadowColor() {
            return shadow;
        }

    }
}
