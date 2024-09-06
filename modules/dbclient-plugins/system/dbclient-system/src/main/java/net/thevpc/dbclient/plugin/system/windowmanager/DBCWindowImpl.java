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
package net.thevpc.dbclient.plugin.system.windowmanager;

import net.thevpc.dbclient.api.DBCApplication.ApplicationMode;
import net.thevpc.dbclient.plugin.system.viewmanager.ToolPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 8 dec. 2006 10:56:29
 */
public class DBCWindowImpl extends AbstractDBCWindow {

    private JFrame frame;

    public DBCWindowImpl() {
    }

    protected JFrame getFrame() {
        if (frame == null) {
            frame = new JFrame(getTitle() + (getApplication().getApplicationMode().equals(ApplicationMode.SAFE) ? " (safe mode)" : ""));
            if (getIcon() != null) {
                frame.setIconImage(getIcon().getImage());
            }
            Component comp = getComponent();
            switch (getKind()) {
                case DRIVER_MANAGER:
                case GLOBAL_SETTINGS:
                case PLUGIN_MANAGER:
                //case SESSION_CHOOSER:
                case TOOL: {
                    comp = new ToolPanel(comp, getApplication(), getSession());
                }
            }
            frame.add(comp);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.pack();


//            Toolkit tk = Toolkit.getDefaultToolkit();
//            Dimension screenSize = tk.getScreenSize();
//            int screenHeight = screenSize.height;
//            int screenWidth = screenSize.width;
            //frame.setSize(screenWidth / 2, screenHeight / 2);
//            frame.setLocation(screenWidth / 4, screenHeight / 4);

            frame.setLocationRelativeTo(null);
            frame.addWindowListener(new WindowAdapter() {

                public void windowOpened(WindowEvent e) {
                    frame.pack();
                    fireWindowOpenedListener();
                }

                public void windowClosed(WindowEvent e) {
                    fireWindowClosedListener();
                }

                public void windowClosing(WindowEvent e) {
                    super.windowClosing(e);
                }

                public void windowIconified(WindowEvent e) {
                    super.windowIconified(e);
                }

                public void windowDeiconified(WindowEvent e) {
                    super.windowDeiconified(e);
                }

                public void windowActivated(WindowEvent e) {
                    super.windowActivated(e);
                }

                public void windowDeactivated(WindowEvent e) {
                    super.windowDeactivated(e);
                }

                public void windowStateChanged(WindowEvent e) {
                    super.windowStateChanged(e);
                }

                public void windowGainedFocus(WindowEvent e) {
                    super.windowGainedFocus(e);
                }

                public void windowLostFocus(WindowEvent e) {
                    super.windowLostFocus(e);
                }
            });
            //frame.pack();
        }
        return frame;
    }

    public Component getTopLevelComponent() {
        return frame == null ? null : frame;
    }

    public void showWindow() {
        //PRSManager.update(getFrame(),getSession()!=null?getSession():getApplication());
        getFrame().setVisible(true);
    }

    public void closeWindow() {
        getFrame().setVisible(false);
        getFrame().dispose();
    }

    public void hideWindow() {
        getFrame().setVisible(false);
    }

    public void setTitle(String title) {
        getFrame().setTitle(title);
    }

    @Override
    public void pack() {
        final JFrame frame1 = getFrame();
        frame1.invalidate();
        frame1.validate();
        frame1.pack();
    }

    
    public void setIcon(ImageIcon icon) {
        super.setIcon(icon);
        if (frame != null) {
            frame.setIconImage(icon == null ? null : icon.getImage());
        }
    }

    public boolean isVisible() {
        return frame != null && frame.isVisible();
    }
}
