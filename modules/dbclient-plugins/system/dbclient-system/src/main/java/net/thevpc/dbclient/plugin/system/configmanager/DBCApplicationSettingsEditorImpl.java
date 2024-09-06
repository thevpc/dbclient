package net.thevpc.dbclient.plugin.system.configmanager;

import net.thevpc.dbclient.api.DBCApplication;
import net.thevpc.dbclient.api.DBClientContext;
import net.thevpc.dbclient.api.configmanager.DBCApplicationSettingsEditor;
import net.thevpc.common.prs.plugin.Inject;

public class DBCApplicationSettingsEditorImpl extends DBCAbstractSettingsEditorImpl implements DBCApplicationSettingsEditor {
    @Inject
    private DBCApplication application;

    @Override
    public DBClientContext getContext() {
        return application;
    }
}
