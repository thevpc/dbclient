/**
 *
 ====================================================================
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
 *
 ====================================================================
 */

package net.vpc.dbclient.plugin.dbsupport.oracle.actions;

import net.vpc.dbclient.api.actionmanager.DBCActionLocation;
import net.vpc.dbclient.api.pluginmanager.DBCPluginSession;
import net.vpc.dbclient.api.sql.objects.DBCatalog;
import net.vpc.dbclient.api.sql.objects.DBObject;
import net.vpc.dbclient.plugin.dbsupport.oracle.OracleConnection;
import net.vpc.swingext.DumbGridBagLayout;
import net.vpc.swingext.JListTwins;
import net.vpc.swingext.dialog.MessageDialogType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 20 avr. 2007 16:32:06
 */
public class NewUserTreeNode extends AbstractOracleTreeNodeAction {
    public NewUserTreeNode(DBCPluginSession plugin) {
        super("Action.NewUserAction");
        addLocationPath(DBCActionLocation.POPUP, "New");
        setIconId("Add");
    }

    @Override
    public boolean shouldEnable(DBObject activeNode, DBObject[] selectedNodes) {
        return activeNode != null && activeNode instanceof DBCatalog;
    }

    @Override
    public boolean isValid() {
        try {
            return getSession().getConnection() instanceof OracleConnection;
        } catch (SQLException ex) {
            getSession().getLogger(NewUserTreeNode.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }


    public void actionPerformedImpl(ActionEvent e) throws Throwable {
        AddUserPanel p = new AddUserPanel();
        int r;
        while (true) {
            r = JOptionPane.showConfirmDialog(getPluginSession().getSession().getView().getMainComponent(), p, getActionName(), JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (r == JOptionPane.OK_OPTION) {
                try {
                    String sql = getConnection().getSQLCreateUser(p.getProperties().getProperty("username"), p.getProperties().getProperty("password"), p.getProperties());
                    getConnection().executeScript(sql);
                    getPluginSession().getSession().getView().refreshView();
                    return;
                } catch (Throwable e1) {
                    getPluginSession().getSession().getView().getDialogManager().showMessage(null, null, MessageDialogType.ERROR, null, e1);
                }
            } else {
                return;
            }
        }
    }

    public class AddUserPanel extends JPanel {
        JTextField name = new JTextField();
        JPasswordField pwd = new JPasswordField();
        JPasswordField pwd2 = new JPasswordField();
        JListTwins privs = new JListTwins();
        JListTwins roles = new JListTwins();

        public AddUserPanel() throws SQLException {
            super(new BorderLayout());
//            name.setColumns(20);

            roles.setModel(getConnection().getRoles(), new int[0]);

            privs.setModel(getConnection().getPrivileges(), new int[0]);

            JTabbedPane pane = new JTabbedPane();
            JPanel general = new JPanel(
                    new DumbGridBagLayout()
                            .addLine("[^<-usernameL][<-=username]")
                            .addLine("[<-passwordL][<-=password]")
                            .addLine("[^<|-password2L][<-=password2]")
                            .setInsets(".*", new Insets(3, 3, 3, 3))
            );
            general.add(new JLabel("Username"), "usernameL");
            general.add(name, "username");
            general.add(new JLabel("Password"), "passwordL");
            general.add(pwd, "password");
            general.add(new JLabel("Confirm Password"), "password2L");
            general.add(pwd2, "password2");

            JPanel security = new JPanel(
                    new DumbGridBagLayout()
                            .addLine("[^<-=rolesL]")
                            .addLine("[^<+=$$roles]")
                            .addLine("[^<-=privsL]")
                            .addLine("[^<+=$$privs]")
                            .setInsets(".*", new Insets(3, 3, 3, 3))
            );
            security.add(new JLabel("Roles"), "rolesL");
            security.add(new JLabel("Privileges"), "privsL");
            security.add(new JScrollPane(roles), "roles");
            security.add(new JScrollPane(privs), "privs");
            pane.addTab("General", general);
            pane.addTab("Security", security);
            this.add(pane);
        }

        public Properties getProperties() throws IllegalArgumentException {
            if (!new String(pwd.getPassword()).equals(new String(pwd2.getPassword()))) {
                throw new IllegalArgumentException("Password does not match");
            }
            Properties p = new Properties();
            p.put("username", name.getText());
            p.put("password", new String(pwd.getPassword()));
            StringBuilder sbroles = new StringBuilder();
            for (int i = 0; i < roles.getModel().getRightSize(); i++) {
                Object rv = roles.getModel().getRightElementAt(i);
                if (i > 0) {
                    sbroles.append(",");
                }
                sbroles.append(rv);
            }

            StringBuilder sbprivs = new StringBuilder();
            if (privs.getModel().getLeftSize() == 0) {
                sbprivs.append("ALL PRIVILEGES");
            } else {
                for (int i = 0; i < privs.getModel().getRightSize(); i++) {
                    Object pv = privs.getModel().getRightElementAt(i);
                    if (i > 0) {
                        sbroles.append(",");
                    }
                    sbroles.append(pv);
                }
            }

            p.put("roles", sbroles.toString());
            p.put("privileges", sbprivs.toString());
            return p;
        }
    }
}