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

package net.vpc.dbclient.plugin.system.sessionmanager.sqleditor;

import net.vpc.dbclient.api.sql.parser.SQLToken;
import net.vpc.dbclient.api.viewmanager.DBCComponentFormat;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.text.*;
import java.awt.*;
import java.util.Collection;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 30 juin 2006 20:54:42
 */
public class SQLCodeView extends PlainView {

    private int tabBase;
    private Element longLine;
    private SQLEditorKit sqlEditorKit;
    private JLabel lineLabel;
//    public short NUMBERS_WIDTH = 25;

    public SQLCodeView(SQLEditorKit sqlEditorKit, Element element) {
        super(element);
        this.sqlEditorKit = sqlEditorKit;
        tabBase = 0;
        lineLabel = new JLabel();
    }

    private void calculateLongestLine() {
        longLine = null;
        java.awt.Container container = getContainer();
        java.awt.Font font = container.getFont();
        metrics = container.getFontMetrics(font);
//            Document document = getDocument();
        Element element = getElement();
        int i = element.getElementCount();
        int j = -1;
        for (int k = 0; k < i; k++) {
            Element element1 = element.getElement(k);
            int l = getLineWidth(element1);
            if (l > j) {
                j = l;
                longLine = element1;
            }
        }

    }

    public void changedUpdate(DocumentEvent documentevent, Shape shape, ViewFactory viewfactory) {
        updateLongestLine(documentevent, shape, viewfactory);
    }

    private void damageRange(int i, int j, Shape shape, Component component) {
        if (shape != null) {
            Rectangle rectangle = getRectangleForLine(shape, i);
            Rectangle rectangle1 = getRectangleForLine(shape, j);
            if (rectangle != null && rectangle1 != null) {
                Rectangle rectangle2 = rectangle.union(rectangle1);
                component.repaint(rectangle2.x, rectangle2.y, rectangle2.width, rectangle2.height);
            } else {
                component.repaint();
            }
        }
    }

//    protected void drawLine(int lineIndex, Graphics g, int x, int y) {
//        super.drawLine(lineIndex, g, x+NUMBERS_WIDTH, y);
//        g.drawString("XXX",x+3, y);
//    }


    protected int drawUnselectedText(Graphics g, int i, int j, int k, int l)
            throws BadLocationException {
        return drawText(g, i, j, k, l, true);
    }

    protected int drawSelectedText(Graphics g, int i, int j, int k, int l) throws BadLocationException {
        return drawText(g, i, j, k, l, false);
    }

    protected int drawText(Graphics g, int i, int j, int k, int l, boolean drawBG)
            throws BadLocationException {

        Document document = getDocument();
        Segment segment = getLineBuffer();


        Collection<SQLToken> sqlTokens = sqlEditorKit.scan(document, k, l);
//        System.out.println("sqlTokens ["+i+","+j+","+k+","+l+"]= " + sqlTokens);
        Font font = g.getFont();
        Font[] fonts = new Font[3];
        fonts[Font.BOLD] = font.deriveFont(Font.BOLD);
        fonts[Font.PLAIN] = font.deriveFont(Font.PLAIN);
        fonts[Font.ITALIC] = font.deriveFont(Font.ITALIC);
        FontMetrics fm = g.getFontMetrics();
        for (SQLToken anAitem : sqlTokens) {
            DBCComponentFormat tokenStyle = sqlEditorKit.getCharFormat(anAitem.getGroup());
            Integer style = tokenStyle.getStyle();
            if (style != null) {
                g.setFont(fonts[style]);
            }
            document.getText(anAitem.getCharStartIndex(), anAitem.getCharEndIndex() - anAitem.getCharStartIndex(), segment);
            if (drawBG) {
                Color bg = tokenStyle.getBackground();
                if (bg != null) {
                    g.setColor(bg);
                    g.fillRoundRect(i, j - fm.getAscent(), fm.stringWidth(segment.toString()), fm.getHeight(), 3, 3);
                }
            }
            g.setColor(tokenStyle.getForeground());
            i = Utilities.drawTabbedText(segment, i, j, g, this, anAitem.getCharStartIndex());
        }

        return i;
    }

    private int getLineWidth(Element element) {
        if (element == null) {
            return 10;
        }
        int i = element.getStartOffset();
        int j = element.getEndOffset();
        Container container = getContainer();
        Font font = container.getFont();
        metrics = container.getFontMetrics(font);
        Segment segment = getLineBuffer();
        int k;
        try {
            element.getDocument().getText(i, j - i, segment);
            k = Utilities.getTabbedTextWidth(segment, metrics, tabBase, this, i);
        } catch (BadLocationException _ex) {
            return 0;
        }
        return k;
    }

    public float getPreferredSpan(int i) {
        if (longLine == null) {
            calculateLongestLine();
        }
        Container container = getContainer();
        Font font = container.getFont();
        metrics = container.getFontMetrics(font);
        int j = getElement().getElementCount() * metrics.getHeight();
        int k = getLineWidth(longLine);
        if (container.getParent() != null && j < container.getParent().getSize().height) {
            j = container.getParent().getSize().height - 6;
        }
        if (container.getParent() != null && k < container.getParent().getSize().width) {
            k = container.getParent().getSize().width - 6;
        }
        switch (i) {
            case 0: // '\0'
                return (float) k;

            case 1: // '\001'
                return (float) j;
        }
        throw new IllegalArgumentException("Invalid axis: " + i);
    }

    protected int getTabSize() {
        return 4;
    }

    public void insertUpdate(DocumentEvent documentevent, Shape shape, ViewFactory viewfactory) {
        updateLongestLine(documentevent, shape, viewfactory);
    }

    private Rectangle getRectangleForLine(Shape shape, int i) {
        Rectangle rectangle = null;
        if (metrics != null) {
            Rectangle rectangle1 = shape.getBounds();
            rectangle = new Rectangle(rectangle1.x, rectangle1.y + i * metrics.getHeight(), rectangle1.width, metrics.getHeight());
        }
        return rectangle;
    }

    public void paint(Graphics g, Shape shape) {
        Rectangle rectangle = (Rectangle) shape;
        tabBase = rectangle.x;
        super.paint(g, shape);
    }

    public void removeUpdate(DocumentEvent documentevent, Shape shape, ViewFactory viewfactory) {
        updateLongestLine(documentevent, shape, viewfactory);
    }

    private void updateLongestLine(DocumentEvent documentevent, Shape shape, ViewFactory viewfactory) {
//        if(true){
//            return;
//        }
        Container container = getContainer();
        if (container.isShowing()) {
            Element element = getElement();
            DocumentEvent.ElementChange elementchange = documentevent.getChange(element);
            Element aelement[] = elementchange == null ? null : elementchange.getChildrenAdded();
            Element aelement1[] = elementchange == null ? null : elementchange.getChildrenRemoved();
            if (aelement != null && aelement.length > 0 || aelement1 != null && aelement1.length > 0) {
                if (aelement != null) {
                    int i = getLineWidth(longLine);
                    for (Element anAelement : aelement) {
                        int i1 = getLineWidth(anAelement);
                        if (i1 > i) {
                            i = i1;
                            longLine = anAelement;
                        }
                    }

                }
                if (aelement1 != null) {
                    for (Element anAelement1 : aelement1) {
                        if (anAelement1 != longLine) {
                            continue;
                        }
                        calculateLongestLine();
                        break;
                    }

                }
                preferenceChanged(null, true, true);
                container.repaint();
            } else {
                Element element1 = getElement();
                int l = element1.getElementIndex(documentevent.getOffset());
                damageRange(l, l, shape, container);
                if (documentevent.getType() == DocumentEvent.EventType.INSERT) {
                    int j1 = getLineWidth(longLine);
                    Element element2 = element1.getElement(l);
                    if (element2 == longLine) {
                        preferenceChanged(null, true, false);
                    } else if (getLineWidth(element2) > j1) {
                        longLine = element2;
                        preferenceChanged(null, true, false);
                    }
                } else if (documentevent.getType() == DocumentEvent.EventType.REMOVE && element1.getElement(l) == longLine) {
                    calculateLongestLine();
                    preferenceChanged(null, true, false);
                }
            }
        }
    }

//    protected void drawLine(int lineIndex, Graphics g, int x, int y) {
//        System.out.println("lineIndex = " + lineIndex);
//        super.drawLine(lineIndex, g, x, y);
//    }
}
