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

package net.thevpc.dbclient.plugin.system.sessionmanager.table;

import net.thevpc.common.swing.SwingUtilities3;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.io.OutputStream;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 13 août 2005 12:11:43
 */
public class StyledConsoleRedirectorOutputStream extends OutputStream {
    private SimpleAttributeSet attr = new SimpleAttributeSet();
    boolean autoscroll;
    private JTextPane jTextPane;

    public StyledConsoleRedirectorOutputStream(JTextPane jTextPane, Color c, boolean autoscroll) {
        if (c != null) {
            StyleConstants.setForeground(attr, c);
        }
        this.autoscroll = autoscroll;
        this.jTextPane = jTextPane;
    }

    private void _writeString(final String str) {
        try {
            SwingUtilities3.invokeAndWait(new Runnable() {
                public void run() {
                    StyledDocument styledDocument = jTextPane.getStyledDocument();
                    try {
                        styledDocument.insertString(styledDocument.getLength(), str, attr);
                        if (styledDocument.getLength() > 1024 * 10) {
                            styledDocument.remove(0, 1024);
                        }
                    } catch (BadLocationException e) {
                        //e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            //ignore
        }

        showError();
    }

    public void write(int b) {
        _writeString(String.valueOf(b));
    }

    public void write(byte b[]) {
        _writeString(new String(b));
    }

    public void write(byte b[], int off, int len) {
        _writeString(new String(b, off, len));
    }

    protected void showError() {
        try {
            SwingUtilities3.invokeAndWait(new Runnable() {
                public void run() {
                    if (autoscroll) {
                        JScrollPane jScrollPane = (JScrollPane) SwingUtilities.getAncestorOfClass(JScrollPane.class, jTextPane);
                        jScrollPane.getVerticalScrollBar().setValue(jScrollPane.getVerticalScrollBar().getMaximum());
                        //            resultsTabbedPane.setSelectedIndex(0);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
