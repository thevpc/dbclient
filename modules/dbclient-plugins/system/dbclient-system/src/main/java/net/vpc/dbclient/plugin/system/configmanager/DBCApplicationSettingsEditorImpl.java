package net.vpc.dbclient.plugin.system.configmanager;

import net.vpc.dbclient.api.DBCApplication;
import net.vpc.dbclient.api.DBClientContext;
import net.vpc.dbclient.api.configmanager.DBCApplicationSettingsEditor;
import net.vpc.prs.plugin.Inject;

public class DBCApplicationSettingsEditorImpl extends DBCAbstractSettingsEditorImpl implements DBCApplicationSettingsEditor {
    @Inject
    private DBCApplication application;

    @Override
    public DBClientContext getContext() {
        return application;
    }
}
