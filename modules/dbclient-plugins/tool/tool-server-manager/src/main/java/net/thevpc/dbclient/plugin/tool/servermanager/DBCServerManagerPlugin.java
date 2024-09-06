package net.thevpc.dbclient.plugin.tool.servermanager;

import net.thevpc.dbclient.api.DBCApplication;
import net.thevpc.dbclient.api.DBCApplicationView;
import net.thevpc.dbclient.api.pluginmanager.DBCAbstractPlugin;
import net.thevpc.common.prs.plugin.PluginInfo;
import net.thevpc.dbclient.api.windowmanager.DBCWindow;
import net.thevpc.dbclient.api.windowmanager.DBCWindowKind;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

@PluginInfo(
        iconSet = "net.thevpc.dbclient.plugin.tool.servermanager.iconset.Plugin",
        messageSet = "net.thevpc.dbclient.plugin.tool.servermanager.messageset.Plugin"
)
public class DBCServerManagerPlugin extends DBCAbstractPlugin {
    public DBCServerManagerPlugin() {
    }

    public DBCServerManager serverManager;

    @Override
    public void applicationReady() {
        super.applicationReady();
        //initialize Server Manager to start auto servers
        getServerManager();
    }

    public DBCServerManager getServerManager() {
        if (serverManager == null) {
            serverManager = getApplication().getFactory().newInstance(DBCServerManager.class);
            getApplication().addPropertyChangeListener(DBCApplication.PROPERTY_CLOSING, new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent evt) {
                    try {
                        serverManager.close();
                    } catch (Exception e) {
                        //ignore
                    }
                }
            });
        }
        return serverManager;
    }

    private DBCWindow<DBCServerManagerEditor> serverManagerWindow;

    public DBCWindow<DBCServerManagerEditor> getServerManagerWindow() {
        if (serverManagerWindow == null) {
            DBCServerManagerEditor settings = getApplication().getFactory().newInstance(DBCServerManagerEditor.class);
            settings.init(getApplication());
            serverManagerWindow = getApplication().getView().getWindowManager().addWindow(
                    settings.getComponent(),
                    DBCWindowKind.TOOL,
                    null, getMessageSet().get("Action.ServerManagerAction"), null
            );
            getApplication().addPropertyChangeListener(DBCApplicationView.PROPERTY_LOCALE, new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent evt) {
                    if (serverManagerWindow != null) {
                        serverManagerWindow.setTitle(getMessageSet().get("Action.ServerManagerAction"));
                    }
                }
            });
        }
        return serverManagerWindow;
    }

}
