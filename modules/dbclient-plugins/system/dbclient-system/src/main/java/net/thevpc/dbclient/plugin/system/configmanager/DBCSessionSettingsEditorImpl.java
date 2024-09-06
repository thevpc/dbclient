package net.thevpc.dbclient.plugin.system.configmanager;

import net.thevpc.dbclient.api.DBCSession;
import net.thevpc.dbclient.api.DBClientContext;
import net.thevpc.dbclient.api.configmanager.DBCSessionSettingsEditor;
import net.thevpc.common.prs.plugin.Inject;

public class DBCSessionSettingsEditorImpl extends DBCAbstractSettingsEditorImpl implements DBCSessionSettingsEditor {
    @Inject
    private DBCSession session;

    @Override
    public DBClientContext getContext() {
        return session;
    }
    
    protected void run(String name,String desc,Runnable r){
        session.getTaskManager().run(name, desc, r);
    }
}