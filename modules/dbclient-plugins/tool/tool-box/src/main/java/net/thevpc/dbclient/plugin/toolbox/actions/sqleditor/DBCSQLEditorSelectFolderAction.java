package net.thevpc.dbclient.plugin.toolbox.actions.sqleditor;

import net.thevpc.dbclient.api.actionmanager.DBClientActionType;
import net.thevpc.dbclient.api.sessionmanager.DBCSQLEditorAction;

import java.awt.event.ActionEvent;

public class DBCSQLEditorSelectFolderAction extends DBCSQLEditorAction {

    public DBCSQLEditorSelectFolderAction() {
        super("SQLEditorPane.Action.SelectFolder", "/");
        setShortId("SelectFolder");
        setType(DBClientActionType.FOLDER);
    }

    public void actionPerformedImpl(ActionEvent e) {
        //do nothing, never called
    }

}