package net.vpc.dbclient.plugin.system.configmanager;

import net.vpc.dbclient.api.DBCSession;
import net.vpc.dbclient.api.DBClientContext;
import net.vpc.dbclient.api.configmanager.DBCSessionSettingsEditor;
import net.vpc.prs.plugin.Inject;

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