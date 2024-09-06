package net.thevpc.dbclient.plugin.toolbox.actions.sqleditor;

import net.thevpc.dbclient.api.sessionmanager.DBCSQLEditorAction;
import net.thevpc.dbclient.api.sql.format.SQLFormatter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.logging.Level;

public class DBCSQLEditorFormatAction extends DBCSQLEditorAction {
    public DBCSQLEditorFormatAction() {
        super("SQLEditorPane.Action.Format", "/", KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
    }

    public void actionPerformedImpl(ActionEvent e) {
        getSession().getTaskManager().run(getName(),null,new Runnable() {
            public void run() {
                if (!getEditor().isEditable() || !getEditor().isEnabled()) {
                    try {
                        String q = getEditor().getText();
                        SQLFormatter formatter = getEditor().getSession().getConnection().createFormatter();
                        getEditor().setText(formatter.formatDocument(q));
                    } catch (Exception ex) {
                        getSession().getLogger(getClass().getName()).log(Level.SEVERE,"Action Failed", ex);
                    }
                }
            }
        });
    }
}