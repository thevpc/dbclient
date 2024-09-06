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

package net.thevpc.dbclient.plugin.tool.neormf.settings;

import net.thevpc.dbclient.plugin.tool.neormf.NeormfPluginSession;
import net.thevpc.dbclient.plugin.tool.neormf.actions.NeormfJBGenAction;
import net.thevpc.common.swing.prs.PRSManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 27 avr. 2007 19:10:16
 */
public class NeormfSettingsStandalonePanel extends JPanel {
    private NeormfSettingsComponent settingsComponent;
    public NeormfSettingsStandalonePanel(final NeormfPluginSession plugin) {
        super(new BorderLayout());
        settingsComponent = plugin.getSession().getFactory().newInstance(null,NeormfSettingsComponent.class,plugin.getPlugin().getDescriptor());
        add(settingsComponent,BorderLayout.CENTER);

        JButton save= PRSManager.createButton("Save");
        JButton load= PRSManager.createButton("Refresh");
        final NeormfJBGenAction neormfJbgenAction=(NeormfJBGenAction)plugin.getSession().getView().getActionManager().getAction("Action.NeormfJBGen");
        JButton jbgen=new JButton(neormfJbgenAction);
        JToolBar jtb=new JToolBar();
        jtb.add(Box.createHorizontalGlue());
        jtb.add(save);
        jtb.add(load);
        jtb.addSeparator();
        jtb.add(jbgen);
        jtb.setFloatable(false);
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                settingsComponent.saveConfig();
                neormfJbgenAction.revalidateAbility();
            }
        });
        load.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                settingsComponent.loadConfig();
                neormfJbgenAction.revalidateAbility();
            }
        });
        load.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                neormfJbgenAction.revalidateAbility();
                if(neormfJbgenAction.isEnabled()){
                   neormfJbgenAction.actionPerformed(null); 
                }
            }
        });
        add(jtb,BorderLayout.PAGE_END);
        PRSManager.update(this,plugin);
    }

    public NeormfSettingsComponent getSettingsComponent() {
        return settingsComponent;
    }
}
