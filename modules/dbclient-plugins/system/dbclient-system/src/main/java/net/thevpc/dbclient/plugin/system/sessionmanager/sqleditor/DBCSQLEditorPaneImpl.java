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
package net.thevpc.dbclient.plugin.system.sessionmanager.sqleditor;

import net.thevpc.dbclient.api.DBCSession;
import net.thevpc.common.prs.plugin.Inject;
import net.thevpc.common.prs.plugin.Initializer;
import net.thevpc.dbclient.api.sessionmanager.*;
import net.thevpc.dbclient.api.viewmanager.DBCPluggablePanel;
import net.thevpc.common.swing.prs.PRSManager;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 13 juil. 2005 15:24:22
 */
public class DBCSQLEditorPaneImpl extends DBCPluggablePanel implements DBCSQLEditorPane {

    private DBCSQLEditor sqlEditor;
    @Inject
    private DBCSession session;
    protected Container tools;
    protected Footer footer;

    @Initializer
    private void init() throws SQLException {
        tools = new JPanel();
        tools.setLayout(new BoxLayout(tools, BoxLayout.LINE_AXIS));
        //tools.setRollover(true);
        //tools.setFloatable(false);
        sqlEditor = getSession().getFactory().newInstance(DBCSQLEditor.class);
        footer = new Footer();
        footer.init(this);

        this.setLayout(new BorderLayout());
        JScrollPane queryTextAreaScrollPane = new JScrollPane(sqlEditor.getComponent());
        add(queryTextAreaScrollPane, BorderLayout.CENTER);
        add(tools, BorderLayout.PAGE_START);
        this.add(footer, BorderLayout.PAGE_END);
        getEditor().setSQL("");
        for (DBCSQLEditorPaneTool implementation : getSession().getFactory().createImplementations(DBCSQLEditorPaneTool.class)) {
            DBCSQLEditorPaneTool t;
            try {
                t = implementation;
                t.init(this);
                this.tools.add(t.getComponent());
            } catch (Exception e) {
                //ignore
            }
        }
        PRSManager.update(this, session.getView());
    }

    public DBCSQLEditorPaneImpl() {
    }

    public DBCSQLEditor getEditor() {
        return sqlEditor;
    }

    public DBCSession getSession() {
        return session;
    }

    public JComponent getComponent() {
        return this;
    }

    private static class Footer extends JPanel implements DBCCaretListener {
        protected JLabel caretLabel = new JLabel();

        private Footer() {
            super(new GridBagLayout());

            int gridx = 0;
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(3, 3, 3, 3);
            gbc.gridx = gridx++;
            gbc.gridy = 0;
            gbc.weightx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            this.add(new JLabel(), gbc);//Ressort

            gbc.gridx = gridx++;
            gbc.gridy = 0;
            gbc.weightx = 0;
            this.add(createSeparator(), gbc);


            gbc.gridx = gridx++;
            gbc.gridy = 0;
            gbc.weightx = 0;
            this.add(caretLabel, gbc);


            caretLabel.setText("1:1");
            caretLabel.setToolTipText("Caret Position");
        }

        void init(DBCSQLEditorPane pane) {
            pane.getEditor().addCaretListener(this);
        }

        private Component createSeparator() {
//        return new JSeparator(SwingConstants.VERTICAL);
            JLabel sep = new JLabel("|");
//        sep.setBorder(BorderFactory.createEtchedBorder());
            return sep;
        }

        public void caretUpdate(DBCCaretEvent e) {
            caretLabel.setText(e.getColumn() + ":" + e.getRow());
        }


    }

}