package net.thevpc.dbclient.plugin.system.actionmanager.treeactions.folders;

import net.thevpc.dbclient.api.actionmanager.DBCActionLocation;
import net.thevpc.dbclient.api.actionmanager.DBCSessionFolderAction;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 19 avr. 2007 01:02:11
 */
public class ListFolderAction extends DBCSessionFolderAction {
    public ListFolderAction() {
        super("Tree.ListFolderAction", "List");
        addLocationPath(DBCActionLocation.POPUP, "/");
        //PRSManager.update(this,session);
    }
}