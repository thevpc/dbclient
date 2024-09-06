package net.thevpc.dbclient.plugin.toolbox.actions.sqleditor;

import net.thevpc.dbclient.api.sessionmanager.DBCSQLEditorAction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import net.thevpc.dbclient.api.DBCDefaultSessionTask;

public class DBCSQLEditorAutoCompleteAction extends DBCSQLEditorAction {

    public DBCSQLEditorAutoCompleteAction() {
        super("SQLEditorPane.Action.AutoComplete", "/", KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, InputEvent.CTRL_MASK));
    }

    public void actionPerformedImpl(ActionEvent e) {
        getSession().getTaskManager().run(getName(),null,new Runnable() {

            @Override
            public void run() {
                if (!getEditor().isEditable() || !getEditor().isEnabled()) {
                    return;
                }
                getEditor().actionAutoComplete();
            }
        });
    }
}