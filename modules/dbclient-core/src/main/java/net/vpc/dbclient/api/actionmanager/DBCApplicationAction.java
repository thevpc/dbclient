package net.vpc.dbclient.api.actionmanager;

import net.vpc.dbclient.api.DBCApplication;
import net.vpc.dbclient.api.pluginmanager.DBCPlugin;
import net.vpc.prs.plugin.Extension;
import net.vpc.prs.plugin.Inject;
import net.vpc.dbclient.api.viewmanager.DBCApplicationMessageDialogManager;
import net.vpc.prs.iconset.IconSet;
import net.vpc.prs.messageset.MessageSet;

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
