/**
 * ==================================================================== DBClient
 * yet another Jdbc client tool
 *
 * DBClient is a new Open Source Tool for connecting to jdbc compliant
 * relational databases. Specific extensions will take care of each RDBMS
 * implementation.
 *
 * Copyright (C) 2006-2008 Taha BEN SALAH
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 * ====================================================================
 */
package net.thevpc.dbclient.api.actionmanager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.thevpc.dbclient.api.DBCSession;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com) @creationtime 20 août 2005
 * 13:23:57
 */
public abstract class DBCAsynchActionListener implements ActionListener {

    private DBCSession session;
    private String name;
    private String desc;

    protected DBCAsynchActionListener(String name, String desc, DBCSession session) {
        this.session = session;
        this.name = name;
        this.desc = desc;
    }

    public final void actionPerformed(final ActionEvent e) {
        session.getTaskManager().run(name, desc, new Runnable() {

            @Override
            public void run() {
                Object source = e.getSource();
                if (source instanceof AbstractButton) {
                    ((AbstractButton) source).setEnabled(false);
                }
                try {
                    actionPerformedImpl(e);
                } catch (Exception e1) {
                    session.getLogger(DBCAsynchActionListener.class.getName()).log(Level.SEVERE, "Unable to execute action", e1);
                } finally {
                    if (source instanceof AbstractButton) {
                        ((AbstractButton) source).setEnabled(true);
                    }
                }
            }
        });
    }

    public abstract void actionPerformedImpl(ActionEvent e);
}
