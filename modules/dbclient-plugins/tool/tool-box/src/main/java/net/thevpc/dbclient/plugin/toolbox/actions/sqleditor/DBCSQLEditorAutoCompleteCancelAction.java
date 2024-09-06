package net.thevpc.dbclient.plugin.toolbox.actions.sqleditor;

import net.thevpc.dbclient.api.sessionmanager.DBCSQLEditorAction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class DBCSQLEditorAutoCompleteCancelAction extends DBCSQLEditorAction {
    public DBCSQLEditorAutoCompleteCancelAction() {
        super("SQLEditorPane.Action.AutoComplete.Esc", "/", KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
    }

    public void actionPerformedImpl(ActionEvent e) {
        getSession().getTaskManager().run(getName(),null,new Runnable() {
            public void run() {
                getEditor().actionCancelAutoComplete();
            }
        });
    }
}