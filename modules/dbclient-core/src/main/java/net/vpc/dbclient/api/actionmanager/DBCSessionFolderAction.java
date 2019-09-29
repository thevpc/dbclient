/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vpc.dbclient.api.actionmanager;

import java.awt.event.ActionEvent;

/**
 * @author vpc
 */
public class DBCSessionFolderAction extends DBCSessionAction {

    public DBCSessionFolderAction(String actionId, String folderId) {
        super(actionId);
        setType(DBClientActionType.FOLDER);
        setShortId(folderId);
    }

    public final void actionPerformedImpl(ActionEvent e) throws Throwable {
    }
}
