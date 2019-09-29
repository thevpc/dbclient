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

package net.vpc.dbclient.plugin.system.sessionmanager.tree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;

/**
 * Simple animation in the style of a radar screen.
 * Used to indicate that background tasks are running.
 * <p/>
 * this code is base on the Blib class provided by Joseph Bowbeer in the  DynamicTree example
 *
 * @author Taha BEN SALAH to handle multiple tasks
 * @version 1.0
 */
public class TreeNodeExpansionMonitor extends JComponent implements ActionListener {

    /**
     * Delay per frame, in milliseconds.
     */
    public static final int DELAY = 50;

    /**
     * Sweep increment per frame, in degrees.
     */
    public static final int ANGLE = 36;

    private final Timer timer = new Timer(DELAY, this);
    private int startAngle;
    private int endAngle;
    private int tasksCount = 0;

    /**
     * Creates a blip.
     */
    public TreeNodeExpansionMonitor() {
        setFont(new Font("Arial", Font.BOLD, 8));
    }

    /**
     * Starts the animation timer.
     */
    public void start() {
        timer.start();
    }

    /**
     * Stops the animation timer.
     */
    public void stop() {
        timer.stop();
        startAngle = endAngle = 0;
        repaint();
    }

    /**
     * Advances animation to next frame. Called by timer.
     */
    public void actionPerformed(ActionEvent e) {
        endAngle += ANGLE;
        if (endAngle > 360) {
            endAngle = 360;
            startAngle += ANGLE;
            if (startAngle > 360) {
                startAngle = endAngle = 0;
            }
        }
        repaint();
    }

    /**
     * Paints the current frame.
     */
    public void paintComponent(Graphics g) {
        int w = getWidth();
        int h = getHeight();
        if (isOpaque()) {
            g.setColor(getBackground());
            g.fillRect(0, 0, w, h);
            g.setColor(getForeground());
        }
        Insets ins = getInsets();
        w -= (ins.left + ins.right);
        h -= (ins.top + ins.bottom);
        // Antialiasing improves appearance
        ((Graphics2D) g).setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
        g.fillArc(ins.left, ins.top, w, h,
                90 - startAngle, startAngle - endAngle);
        if (tasksCount > 0) {
            g.setColor(Color.RED);
            g.drawString(String.valueOf(tasksCount), 4, 12);
        }
    }

    public void taskStarted() {
        if (tasksCount == 0) {
            start();
        }
        tasksCount++;
    }

    public void taskStopped() {
        if (tasksCount > 0) {
            tasksCount--;
        }
        if (tasksCount <= 0) {
            stop();
        }
    }


}
