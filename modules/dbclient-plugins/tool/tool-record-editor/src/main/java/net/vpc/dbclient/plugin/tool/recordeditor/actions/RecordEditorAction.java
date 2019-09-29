package net.vpc.dbclient.plugin.tool.recordeditor.actions;

import net.vpc.dbclient.api.actionmanager.DBClientAction;
import net.vpc.dbclient.api.actionmanager.DBCApplicationAction;

/**
 * Created by IntelliJ IDEA.
 * User: vpc
 * Date: 29 août 2009
 * Time: 17:15:04
 * To change this template use File | Settings | File Templates.
 */
public abstract class RecordEditorAction extends DBCApplicationAction {
    public RecordEditorAction(String id) {
        super(id);
    }
}
