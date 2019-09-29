package net.vpc.dbclient.plugin.system.sessionmanager.sqleditor;

import net.vpc.dbclient.api.sessionmanager.DBCSQLEditor;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This Component is very very inspired from the excellent article at
 * http://blog.chinaunix.net/u/3176/showart_377866.html
 *
 * @author Taha BEN SALAH (taha.bensalah@gmail.com) alias vpc
 *         * @creationtime 2009/08/08 15:36:50
 */
public class LineNumberedBorder extends AbstractBorder {
//    public static void main(String[] args) {
//        javax.swing.JFrame frame = new javax.swing.JFrame("Line Numbers (as Borders)...");
//        frame.addWindowListener(
//                new java.awt.event.WindowAdapter() {
//                    public void windowClosing(java.awt.event.WindowEvent ev) {
//                        System.exit(0);
//                    }
//                });
//
//        java.awt.Container contentPane = frame.getContentPane();
//        contentPane.setLayout(new java.awt.GridLayout(0, 2));
//
//        int[] sides = {
//                LineNumberedBorder.LEFT_SIDE,
//                LineNumberedBorder.LEFT_SIDE,
//                LineNumberedBorder.RIGHT_SIDE,
//                LineNumberedBorder.RIGHT_SIDE};
//
//        int[] justified = {
//                LineNumberedBorder.LEFT_JUSTIFY,
//                LineNumberedBorder.RIGHT_JUSTIFY,
//                LineNumberedBorder.LEFT_JUSTIFY,
//                LineNumberedBorder.RIGHT_JUSTIFY};
//
//        String[] labels = {
//                "Left Side/Left Justified",
//                "Left Side/Right Justified",
//                "Right Side/Left Justified",
//                "Right Side/Right Justified"};
//
//        javax.swing.JPanel subpanel;
//        DBCSQLEditor textArea;
//
//        boolean useMultipleBorders = false;
//        if (args.length > 0 && "multiple".equals(args[0])) {
//            useMultipleBorders = true;
//        }
//
//        for (int idx = 0; idx < 1/*labels.length*/; idx++) {
//            textArea = new DBCSQLEditorImpl();
//            try {
//                textArea.init(null);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
////            LineNumberedBorder lnb = new LineNumberedBorder(sides[idx], justified[idx]);
////            if (useMultipleBorders) {
////                textArea.setBorder(
////                        javax.swing.BorderFactory.createCompoundBorder(
////                                javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5),
////                                javax.swing.BorderFactory.createCompoundBorder(
////                                        javax.swing.BorderFactory.createLineBorder(java.awt.Color.red, 1),
////                                        javax.swing.BorderFactory.createCompoundBorder(
////                                                lnb,
////                                                javax.swing.BorderFactory.createLineBorder(java.awt.Color.blue, 1)
////                                        )
////                                )
////                        )
////                );
////            } else {
////                textArea.setBorder(lnb);
////            }
//
//            subpanel = new javax.swing.JPanel(new java.awt.BorderLayout());
//            subpanel.add(new javax.swing.JLabel(labels[idx]), java.awt.BorderLayout.NORTH);
//            subpanel.add(new javax.swing.JScrollPane(textArea.getComponent()), java.awt.BorderLayout.CENTER);
//            contentPane.add(subpanel);
//        }
//
//        frame.setSize(800, 600);
//        frame.show();
//    }
    // main

    /**
     * The line numbers should be drawn on the left side of the component.
     */
    public static int LEFT_SIDE = -2;

    /**
     * The line numbers should be drawn on the right side of the component.
     */
    public static int RIGHT_SIDE = -1;

    /**
     * The line number should be right justified.
     */
    public static int RIGHT_JUSTIFY = 0;

    /**
     * The line number should be left justified.
     */
    public static int LEFT_JUSTIFY = 1;

    /**
     * Indicates the justification of the text of the line number.
     */
    private int lineNumberJustification = RIGHT_JUSTIFY;

    /**
     * Indicates the location of the line numbers, w.r.t. the component.
     */
    private JEditorPane relativeComponent;
    private int location = LEFT_SIDE;
    private Color grey;
    private Map<Integer, ImageMap> images = new HashMap<Integer, ImageMap>();

    private class ImageMap {
        LinkedHashMap<String, Image> data = new LinkedHashMap<String, Image>();
    }

    public LineNumberedBorder(int location, int justify, JEditorPane relativeComponent) {
        setLocation(location);
        setLineNumberJustification(justify);
        grey = new JLabel().getBackground();
        this.relativeComponent = relativeComponent;
    }

    public void updateUI() {
        grey = new JLabel().getBackground();
    }

    public Insets getBorderInsets(Component c) {
        return getBorderInsets(c, new Insets(0, 0, 0, 0));
    }

    public void setLineImage(int index, String name, Image image) {
        LineNumberedBorder.ImageMap map = images.get(index);
        if (image != null) {
            if (map == null) {
                map = new ImageMap();
                images.put(index, map);
            }
            map.data.put(name, image);
        } else {
            if (map != null) {
                if (name == null) {
                    images.remove(index);
                } else {
                    map.data.remove(name);
                    if (map.data.size() == 0) {
                        images.remove(index);
                    }
                }
            }
        }
    }

    /**
     * This modifies the insets, by adding space for the line number on the
     * left. Should be modified to add space on the right, depending upon
     * Locale.
     *
     * @param c      Description of the Parameter
     * @param insets Description of the Parameter
     * @return The borderInsets value
     */
    public Insets getBorderInsets(Component c, Insets insets) {
        // if c is not a JTextArea...nothing is done...
        if (c instanceof JEditorPane) {
            int width = lineNumberWidth((JEditorPane) c);
            if (location == LEFT_SIDE) {
                insets.left = width;
            } else {
                insets.right = width;
            }
        }
        return insets;
    }

    public int getLineNumberJustification() {
        return lineNumberJustification;
    }

    public final void setLineNumberJustification(int justify) {
        if (justify == RIGHT_JUSTIFY || justify == LEFT_JUSTIFY) {
            lineNumberJustification = justify;
        }
    }

    public int getLocation() {
        return location;
    }

    public final void setLocation(int loc) {
        if (loc == RIGHT_SIDE || loc == LEFT_SIDE) {
            location = loc;
        }
    }

    /**
     * Returns the width, in pixels, of the maximum line number, plus a trailing
     * space.
     *
     * @param textArea Description of the Parameter
     * @return Description of the Return Value
     */
    private int lineNumberWidth(JEditorPane textArea) {
        //
        // note: should this be changed to use all nines for the lineCount?
        // for example, if the number of rows is 111...999 could be wider
        // (in pixels) in a proportionally spaced font...
        //
        int lineCount = 9999;
        ///Math.max(textArea.getRows(), textArea.getLineCount() + 1);
        return textArea.getFontMetrics(
                textArea.getFont()).stringWidth(lineCount + " ") + 3;
    }

    public int getLineNumberMargin() {
        return lineNumberWidth(relativeComponent);
    }

    BasicStroke dashed = new BasicStroke(
            1f,
            BasicStroke.CAP_ROUND,
            BasicStroke.JOIN_ROUND,
            1f,
            new float[]{2f},
            0f);

    //
    // NOTE: This method is called every time the cursor blinks...
    //       so...optimize (later and if possible) for speed...
    //

    public void paintBorder(Component c, Graphics g, int x, int y,
                            int width, int height) {

        java.awt.Rectangle clip = g.getClipBounds();
        int clipY = clip.y;
//        int clipY = 0;

        FontMetrics fm = g.getFontMetrics();
        int fontHeight = fm.getHeight();

        // starting location at the "top" of the page...
        // y is the starting baseline for the font...
        // should "font leading" be applied?
        int ybaseline = y + fm.getAscent();

        //
        // now determine if it is the "top" of the page...or somewhere else
        //
        int startingLineNumber = (clipY / fontHeight) + 1;

        //
        // use any one of the following if's:
        //
//		if (startingLineNumber != 1)
        if (ybaseline < clipY) {
            //
            // not within the clip rectangle...move it...
            // determine how many fontHeight's there are between
            // y and clip.y...then add that many fontHeights
            //
            ybaseline = y + startingLineNumber * fontHeight -
                    (fontHeight - fm.getAscent());
        }

        //
        // options:
        // . write the number rows in the document (current)
        // . write the number of existing lines in the document (to do)
        //   see getLineCount()
        //

        // determine which the "drawing" should end...
        // add fontHeight: make sure...part of the line number is drawn
        //
        // could also do this by determining what the last line
        // number to draw.
        // then the "while" loop whould change accordingly.
        //
        //int	yend = y + clip.height + fontHeight;
        //int	yend = ybaseline + height + fontHeight; // original
        int yend = ybaseline + height;
        if (yend > (y + height)) {
            yend = y + height;
        }

        DBCSQLEditor sqlEditor = (DBCSQLEditor) c;
        JEditorPane jta = (JEditorPane) c;
        int currentLine = sqlEditor.getCaretRow();
        int lineWidth = lineNumberWidth(jta);

        // base x position of the line number
        int lnxstart = x;
        if (location == LEFT_SIDE) {
            // x (LEFT) or (x + lineWidth) (RIGHT)
            // (depends upon justification)
            if (lineNumberJustification == LEFT_JUSTIFY) {
                lnxstart = x;
            } else {
                // RIGHT JUSTIFY
                lnxstart = x + lineWidth;
            }
        } else {
            // RIGHT SIDE
            // (y + width) - lineWidth (LEFT) or (y + width) (RIGHT)
            // (depends upon justification)
            if (lineNumberJustification == LEFT_JUSTIFY) {
                lnxstart = (y + width) - lineWidth;
            } else {
                // RIGHT JUSTIFY
                lnxstart = (y + width);
            }
        }

        g.setColor(grey/*c.getBackground()*/);
        if (location == LEFT_SIDE) {
            g.fillRect(0, y, x + lineWidth - 1, yend);
        } else {
            g.fillRect(x + width - lineWidth - 1, y, x + width, yend);
        }
        Color simpleLineCounterColor = c.getForeground();
        Color selectedLineCounterColor = Color.RED;
        g.setColor(simpleLineCounterColor);
        //
        // loop until out of the "visible" region...
        //
        int length = 4;//("" + Math.max(jta.getRows(), jta.getLineCount() + 1)).length();
        while (ybaseline < yend) {
            //
            // options:
            // . left justify the line numbers
            // . right justify the line numbers
            //
            if (startingLineNumber == currentLine) {
                g.setColor(selectedLineCounterColor);
            } else {
                g.setColor(simpleLineCounterColor);
            }
            LineNumberedBorder.ImageMap map = images.get(startingLineNumber);
            if (lineNumberJustification == LEFT_JUSTIFY) {
                g.drawString(startingLineNumber + " ", lnxstart, ybaseline);
                if (map != null) {
                    int imagex = 1;
                    for (Image image : map.data.values()) {
                        g.drawImage(image, imagex, ybaseline, c);
                        imagex += image.getWidth(c);
                    }
                }
            } else {
                // right justify
                String label = padLabel(startingLineNumber, length, true);
                int xx = lnxstart - fm.stringWidth(label);
                int yy = ybaseline - fontHeight + 3;
                g.drawString(label, xx, ybaseline);
//                g.drawRect(xx, yy,x + lineWidth - 1,fontHeight);
                if (map != null) {
                    int imagex = 1;
                    for (Image image : map.data.values()) {
                        int ih = image.getHeight(c);
                        g.drawImage(image, imagex, yy + fontHeight / 2 - ih / 2, c);
                        imagex += image.getWidth(c);
                    }
                }
            }
            ybaseline += fontHeight;
            startingLineNumber++;
        }
        g.setColor(Color.RED);
        Graphics2D g2d = (Graphics2D) g;
        Stroke st = g2d.getStroke();
        g2d.setStroke(dashed);
        if (location == LEFT_SIDE) {
            g.drawLine(x + lineWidth - 1, y, x + lineWidth - 1, yend);
        } else {
            g.drawLine(x + width - lineWidth - 1, y, x + width - lineWidth - 1, yend);
        }
        g2d.setStroke(st);

    }
    // paintComponent

    /**
     * Create the string for the line number. NOTE: The <tt>length</tt> param
     * does not include the <em>optional</em> space added after the line number.
     *
     * @param lineNumber to stringize
     * @param length     the length desired of the string
     * @param addSpace   Description of the Parameter
     * @return the line number for drawing
     */
    private static String padLabel(int lineNumber, int length, boolean addSpace) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(lineNumber);
        for (int count = (length - buffer.length()); count > 0; count--) {
            buffer.insert(0, ' ');
        }
        if (addSpace) {
            buffer.append(' ');
        }
        return buffer.toString();
    }
}