package net.vpc.dbclient.plugin.system.actionmanager.treeactions.folders;

import net.vpc.dbclient.api.actionmanager.DBCActionLocation;
import net.vpc.dbclient.api.actionmanager.DBCSessionFolderAction;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 19 avr. 2007 01:02:11
 */
public class NewFolderAction extends DBCSessionFolderAction {
    public NewFolderAction() {
        super("Tree.NewFolderAction", "New");
        addLocationPath(DBCActionLocation.POPUP, "/");
    }
}
