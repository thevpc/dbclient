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

package net.thevpc.dbclient.plugin.toolbox.settings.session;

import net.thevpc.common.swing.layout.DumbGridBagLayout;
import net.thevpc.dbclient.api.DBCSession;
import net.thevpc.dbclient.api.configmanager.DBCSessionSettingsComponent;
import net.thevpc.common.prs.plugin.Initializer;
import net.thevpc.common.prs.plugin.Inject;
import net.thevpc.dbclient.api.sessionmanager.DBCSQLEditor;
import net.thevpc.dbclient.api.viewmanager.DBCPluggablePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import net.thevpc.common.prs.messageset.MessageSet;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 5 aout 2006 00:54:44
 */
public class DBCConnectionSettingsComponent extends DBCPluggablePanel implements DBCSessionSettingsComponent {
    private DefaultLabel autoCommitButton = new AutoCommitLabel();
    private DefaultLabel holdabilityButton = new HoldabilityLabel();
    private DefaultLabel readWriteButton = new ReadWriteLabel();
    private DefaultLabel transactionButton = new TransactionLabel();
    @Inject
    private DBCSession session;
    private DBCSQLEditor openScriptEditor = null;
    private DBCSQLEditor closeScriptEditor = null;

    public DBCConnectionSettingsComponent() {
    }

    @Override
    public boolean acceptSession(DBCSession pluginSession) {
        return true;
    }

    public DefaultLabel getAutoCommitButton() {
        return autoCommitButton;
    }

    public void setAutoCommitButton(DefaultLabel autoCommitButton) {
        this.autoCommitButton = autoCommitButton;
    }

    public DefaultLabel getHoldabilityButton() {
        return holdabilityButton;
    }

    public void setHoldabilityButton(DefaultLabel holdabilityButton) {
        this.holdabilityButton = holdabilityButton;
    }

    public DefaultLabel getReadWriteButton() {
        return readWriteButton;
    }

    public void setReadWriteButton(DefaultLabel readWriteButton) {
        this.readWriteButton = readWriteButton;
    }

    public DefaultLabel getTransactionButton() {
        return transactionButton;
    }

    public void setTransactionButton(DefaultLabel transactionButton) {
        this.transactionButton = transactionButton;
    }

    public abstract class DefaultLabel extends JPanel implements ItemListener {
        int value;
        Map<Integer, JRadioButton> components = new HashMap<Integer, JRadioButton>();
        ButtonGroup group = new ButtonGroup();


        public DefaultLabel() {
            setLayout(new GridLayout(-1, 1));
            setBorder(BorderFactory.createEtchedBorder());
//            Font f = getFont();
//            setFont(f.deriveFont(Font.PLAIN, f.getSize() * 0.9f));
            //addActionListener(this);
        }

        public void add(int id, String label) {
            JRadioButton r = new JRadioButton(label);
            r.putClientProperty("ID", id);
            components.put(id, r);
            r.addItemListener(this);
            group.add(r);
            add(r);
        }

        private int getItemId(Object o) {
            return (Integer) ((JComponent) o).getClientProperty("ID");
        }

        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                value = getItemId(e.getSource());
            }
        }


        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
            JRadioButton button = components.get(value);
            if (button != null) {
                button.setSelected(true);
            }
        }
    }

    public class AutoCommitLabel extends DefaultLabel {
        public AutoCommitLabel() {
            add(-1, "Default");
            add(1, "Auto-Commit");
            add(0, "User-Commit");
        }
    }

    public class ReadWriteLabel extends DefaultLabel {
        public ReadWriteLabel() {
            add(-1, "Default");
            add(1, "Read-Write");
            add(0, "Read-Only");
        }
    }


    public class TransactionLabel extends DefaultLabel {
        public TransactionLabel() {
            add(-1, "Default");
            add(Connection.TRANSACTION_READ_UNCOMMITTED, "Read-Uncommitted");
            add(Connection.TRANSACTION_REPEATABLE_READ, "Repeatable-Read");
            add(Connection.TRANSACTION_SERIALIZABLE, "Serializable");
            add(Connection.TRANSACTION_NONE, "None");
        }
    }

    public class HoldabilityLabel extends DefaultLabel {
        public HoldabilityLabel() {
            add(-1, "Default");
            add(ResultSet.HOLD_CURSORS_OVER_COMMIT, "Hold Cursors Over Commit");
            add(ResultSet.CLOSE_CURSORS_AT_COMMIT, "Close Cursors At Commit");
        }
    }


    public JComponent getComponent() {
        return this;
    }

    public Icon getIcon() {
        return getSession().getView().getIconSet().getIconR("Preferences");
    }

    public DBCSession getSession() {
        return session;
    }

    public String getId() {
        return getClass().getSimpleName();
    }

    public String getTitle() {
        return getSession().getView().getMessageSet().get(getId() + ".Title");
    }

    @Initializer
    public void init() {
        JPanel conn = new JPanel();
        conn.setLayout(new DumbGridBagLayout()
                .addLine("[^<commitLabel][^<-commitButton]")
                .addLine("[^SEPARATOR1 : ]")
                .addLine("[^<rwLabel][^<-rwButton]")
                .addLine("[^SEPARATOR2 : ]")
                .addLine("[^<holdLabel][^<-holdButton]")
                .addLine("[^SEPARATOR3 : ]")
                .addLine("[^<transLabel][^<-transButton]")
                .addLine("[+$$==nothing:]")
                .setInsets(".*", new Insets(5, 5, 5, 5))
        );
        conn.add(Box.createVerticalStrut(10), "SEPARATOR1");
        conn.add(Box.createVerticalStrut(10), "SEPARATOR2");
        conn.add(Box.createVerticalStrut(10), "SEPARATOR3");
        MessageSet messageSet = session.getView().getMessageSet();
        conn.add(new JLabel(messageSet.get(getId() + ".CommitLabel")), "commitLabel");
        conn.add(new JLabel(messageSet.get(getId() + ".ReadWriteLabel")), "rwLabel");
        conn.add(new JLabel(messageSet.get(getId() + ".HoldabilityLabel")), "holdLabel");
        conn.add(new JLabel(messageSet.get(getId() + ".TransactionLabel")), "transLabel");
        conn.add(autoCommitButton, "commitButton");
        conn.add(readWriteButton, "rwButton");
        conn.add(holdabilityButton, "holdButton");
        conn.add(transactionButton, "transButton");
        conn.add(new JLabel(), "nothing");
        conn.setBorder(BorderFactory.createTitledBorder(messageSet.get(getId() + ".ConnectionFlagsTitle")));
//        conn.setBorder(BorderFactory.createTitledBorder("Initial Connection Properties"));

        JPanel openScript = new JPanel();
        openScript.setLayout(new DumbGridBagLayout()
                .addLine("[<-openScriptLabel]")
                .addLine("[<+=^**$$openScript**]")
                .setInsets(".*", new Insets(5, 5, 5, 5))
        );
        openScript.add(new JLabel(messageSet.get(getId() + ".OpenScriptLabel")), "openScriptLabel");
        openScript.setBorder(BorderFactory.createTitledBorder(""));

        JPanel closeScript = new JPanel();
        closeScript.setLayout(new DumbGridBagLayout()
                .addLine("[<-closeScriptLabel]")
                .addLine("[<+=^**$$closeScript]")
                .setInsets(".*", new Insets(5, 5, 5, 5))
        );
        closeScript.add(new JLabel(messageSet.get(getId() + ".CloseScriptLabel")), "closeScriptLabel");
        closeScript.setBorder(BorderFactory.createTitledBorder(""));

        try {
            openScriptEditor = session.getFactory().newInstance(DBCSQLEditor.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        JScrollPane openScriptPane = new JScrollPane(openScriptEditor.getComponent());
        openScriptPane.setPreferredSize(new Dimension(200, 70));

        try {
            closeScriptEditor = session.getFactory().newInstance(DBCSQLEditor.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        JScrollPane closeScriptPane = new JScrollPane(closeScriptEditor.getComponent());
        closeScriptPane.setPreferredSize(new Dimension(200, 70));

        openScript.add(openScriptPane, "openScript");
        closeScript.add(closeScriptPane, "closeScript");

        setLayout(new BorderLayout());

        JTabbedPane pane = new JTabbedPane();
        pane.addTab(messageSet.get(getId() + ".ConnectionTitle"), conn);
        pane.addTab(messageSet.get(getId() + ".OpenScriptTitle"), openScript);
        pane.addTab(messageSet.get(getId() + ".CloseScriptTitle"), closeScript);
        add(pane, BorderLayout.CENTER);
    }

    public void loadConfig() {
        Boolean bac = session.getConfig().getBooleanProperty("connection.autoCommit", null);
        int bhl = session.getConfig().getIntegerProperty("connection.holdability", -1);
        Boolean bro = session.getConfig().getBooleanProperty("connection.readOnly", null);
        int bti = session.getConfig().getIntegerProperty("connection.transIsolation", -1);
        getAutoCommitButton().setValue(bac == null ? -1 : bac ? 1 : 0);
        getHoldabilityButton().setValue(bhl);
        getReadWriteButton().setValue(bro == null ? -1 : bro ? 1 : 0);
        getTransactionButton().setValue(bti);
        String onOpenScript = session.getConfig().getStringProperty("connection.script.onopen", null);
        String onCloseScript = session.getConfig().getStringProperty("connection.script.onclose", null);
        openScriptEditor.setText(onOpenScript);
        closeScriptEditor.setText(onCloseScript);
    }

    public void saveConfig() {
        session.getConfig().setBooleanProperty("connection.autoCommit", getAutoCommitButton().getValue() == -1 ? null : getAutoCommitButton().getValue() == 1);
        session.getConfig().setIntegerProperty("connection.holdability", getHoldabilityButton().getValue());
        session.getConfig().setBooleanProperty("connection.readOnly", getReadWriteButton().getValue() == -1 ? null : getReadWriteButton().getValue() == 1);
        session.getConfig().setIntegerProperty("connection.transIsolation", getTransactionButton().getValue());
        String onCloseScript = closeScriptEditor.getText().trim();
        String onOpenScript = openScriptEditor.getText().trim();
        session.getConfig().setStringProperty("connection.script.onopen", onOpenScript.length() == 0 ? null : onOpenScript);
        session.getConfig().setStringProperty("connection.script.onclose", onCloseScript.length() == 0 ? null : onCloseScript);
    }

    public int getPosition() {
        return 0;
    }

}
