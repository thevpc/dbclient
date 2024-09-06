package net.thevpc.dbclient.api.actionmanager;

import net.thevpc.common.prs.iconset.IconSet;
import net.thevpc.common.prs.messageset.MessageSet;
import net.thevpc.common.prs.plugin.Extension;
import net.thevpc.common.prs.plugin.Inject;
import net.thevpc.dbclient.api.DBCApplication;
import net.thevpc.dbclient.api.pluginmanager.DBCPlugin;
import net.thevpc.dbclient.api.viewmanager.DBCApplicationMessageDialogManager;

/**
 * @author vpc
 */
@Extension(customizable = false)
public abstract class DBCApplicationAction extends DBClientAction {
    @Inject
    private DBCPlugin plugin;

    public DBCApplicationAction(String id) {
        super(id);
    }

    public DBCPlugin getPlugin() {
        return plugin;
    }

    public DBCApplication getApplication() {
        return plugin.getApplication();
    }

    public MessageSet getMessageSet() {
        return plugin.getMessageSet();
    }

    public IconSet getIconSet() {
        return plugin.getIconSet();
    }

    public DBCApplicationMessageDialogManager getDialogManager() {
        return getApplication().getView().getDialogManager();
    }
}
