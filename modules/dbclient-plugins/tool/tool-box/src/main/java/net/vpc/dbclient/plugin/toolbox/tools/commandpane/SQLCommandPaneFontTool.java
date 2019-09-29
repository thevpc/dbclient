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

package net.vpc.dbclient.plugin.toolbox.tools.commandpane;

import net.vpc.prs.plugin.Ignore;
import net.vpc.dbclient.api.sessionmanager.DBCSQLCommandPane;
import net.vpc.dbclient.plugin.system.SystemUtils;
import net.vpc.dbclient.plugin.system.sessionmanager.sqleditor.tool.DBCSQLCommandPaneToolImpl;
import net.vpc.swingext.PRSManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 15 juil. 2005 17:17:05
 */
@Ignore
public class SQLCommandPaneFontTool extends DBCSQLCommandPaneToolImpl {
    public SQLCommandPaneFontTool() {
    }

    public void init(DBCSQLCommandPane client) {
        super.init(client);
        JButton bigger = SystemUtils.prepareToolbarButton(PRSManager.createButton("DBCSessionView.SQLQueryFontTool.Bigger"));
        addActionButton(bigger);
        bigger.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Font font = getPane().getEditor().getComponent().getFont();
                getPane().getEditor().getComponent().setFont(font.deriveFont(((float) font.getSize()) + 1));
            }

        });

        JButton smaller = SystemUtils.prepareToolbarButton(PRSManager.createButton("DBCSessionView.SQLQueryFontTool.Smaller"));
        addActionButton(smaller);
        smaller.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Font font = getPane().getEditor().getComponent().getFont();
                getPane().getEditor().getComponent().setFont(font.deriveFont(((float) font.getSize()) - 1));
            }

        });
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Font[] fonts = ge.getAllFonts();
        ArrayList<Font> al = new ArrayList<Font>();
        FontRenderContext fontRenderContext = ((Graphics2D) getGraphics()).getFontRenderContext();
        for (Font font1 : fonts) {
            if (font1.canDisplayUpTo("abcABC") == -1) {
//                Rectangle2D v1 = fonts[i].createGlyphVector(fontRenderContext,"W").getGlyphMetrics(0).getBounds2D();
//                Rectangle2D v2 = fonts[i].createGlyphVector(fontRenderContext,"i").getGlyphMetrics(0).getBounds2D();
                Rectangle2D b1 = font1.getStringBounds("W", fontRenderContext);
                Rectangle2D b2 = font1.getStringBounds(" ", fontRenderContext);
                Rectangle2D b3 = font1.getStringBounds("i", fontRenderContext);
                Rectangle2D b4 = font1.getStringBounds("w", fontRenderContext);
                if (b1.equals(b2) && b1.equals(b3) && b1.equals(b4)) {
                    al.add(font1);
//                    al.add(fonts[i]);
                } else {
//                    System.out.println("Elimine "+fonts[i].getActionName());
                }
            }
        }
        JComboBox fontsComboBox = new JComboBox(al.toArray(new Font[al.size()]));
        fontsComboBox.setRenderer(new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                return super.getListCellRendererComponent(list, value == null ? null : ((Font) value).getName(), index, isSelected, cellHasFocus);
            }
        });
        fontsComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                Font f = (Font) e.getItem();
                Font font = getPane().getEditor().getComponent().getFont();
                getPane().getEditor().getComponent().setFont(f.deriveFont(((float) font.getSize())));
            }
        });
        addActionButton(fontsComboBox);
    }
}


