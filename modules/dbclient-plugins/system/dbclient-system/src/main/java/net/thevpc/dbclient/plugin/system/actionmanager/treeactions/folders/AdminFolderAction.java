package net.thevpc.dbclient.plugin.system.actionmanager.treeactions.folders;

import net.thevpc.dbclient.api.actionmanager.DBCActionLocation;
import net.thevpc.dbclient.api.actionmanager.DBCSessionFolderAction;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 19 avr. 2007 01:02:11
 */
public class AdminFolderAction extends DBCSessionFolderAction {
    public AdminFolderAction() {
        super("Tree.AdminFolderAction", "Admin");
        addLocationPath(DBCActionLocation.POPUP, "/");
        //PRSManager.update(this,session);
    }
}