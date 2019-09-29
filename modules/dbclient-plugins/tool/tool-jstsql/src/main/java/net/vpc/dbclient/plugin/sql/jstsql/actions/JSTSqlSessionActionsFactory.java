package net.vpc.dbclient.plugin.sql.jstsql.actions;

import net.vpc.dbclient.api.DBCSession;
import net.vpc.dbclient.api.actionmanager.DBCActionLocation;
import net.vpc.dbclient.api.actionmanager.DBCSessionAction;
import net.vpc.dbclient.api.actionmanager.DBCSessionSeparatorAction;
import net.vpc.dbclient.api.pluginmanager.DBCPlugin;
import net.vpc.dbclient.api.pluginmanager.DBCPluginSession;
import net.vpc.dbclient.api.pluginmanager.DBCSessionFactory;
import net.vpc.dbclient.plugin.sql.jstsql.JSTSqlPluginSession;
import net.vpc.dbclient.plugin.sql.jstsql.JSTSqlTemplateInfo;
import net.vpc.prs.plugin.ExtensionFactory;
import net.vpc.prs.plugin.ExtensionFactoryType;
import net.vpc.prs.plugin.Inject;
import net.vpc.prs.plugin.PluginDescriptor;

import java.util.ArrayList;
import java.util.List;

@ExtensionFactoryType(type = "net.vpc.dbclient.api.actionmanager.DBCSessionAction")
public class JSTSqlSessionActionsFactory implements ExtensionFactory<DBCSessionAction>{
    @Inject
    DBCPlugin plugin;

    @Inject
    DBCPluginSession pluginSession;

    @Inject
    DBCSession session;

    public List<DBCSessionAction> createExtensions() {
        ArrayList<DBCSessionAction> all = new ArrayList<DBCSessionAction>();
        JSTSqlTemplateInfo[] templates = ((JSTSqlPluginSession)pluginSession).loadTemplates();
        PluginDescriptor pluginInfo = plugin.getDescriptor();
        DBCSessionFactory factory = session.getFactory();
        for (JSTSqlTemplateInfo tSqlTemplateData : templates) {
            boolean sqlExecPopup = tSqlTemplateData.isSstGroupIndex(JSTSqlPluginSession.MENU_EXEC);
            boolean sqlSavePopup = tSqlTemplateData.isSstGroupIndex(JSTSqlPluginSession.MENU_SAVE);
            boolean sqlInsertPopup = tSqlTemplateData.isSstGroupIndex(JSTSqlPluginSession.MENU_INSERT);
            boolean sqlToolbar = tSqlTemplateData.isSstGroupIndex(JSTSqlPluginSession.TOOLBAR);
            boolean separator = tSqlTemplateData.getSstIsSeparator();
            if (sqlExecPopup || sqlToolbar) {
                GenericTreeSQLGenerator g=factory.newInstance(null,GenericTreeSQLGenerator.class,pluginInfo);
                g.init(tSqlTemplateData);
                FetchSQLTreeNodeAction action = new FetchSQLTreeNodeAction(pluginSession, g, sqlExecPopup, sqlToolbar);
                if (separator) {
                    DBCSessionSeparatorAction sessionSeparatorAction = factory.newInstance(null,DBCSessionSeparatorAction.class,pluginInfo);
                    all.add(sessionSeparatorAction);
                    if (sqlInsertPopup) {
                        sessionSeparatorAction.addLocationPath(DBCActionLocation.POPUP, "/sql-ddl/SQLExec");
                    }
                    if (sqlToolbar) {
                        sessionSeparatorAction.addLocationPath(DBCActionLocation.TOOLBAR, "/");
                    }
                }
                all.add(action);
            }
            if (sqlInsertPopup || sqlToolbar) {
                GenericTreeSQLGenerator g=factory.newInstance(null,GenericTreeSQLGenerator.class,pluginInfo);
                g.init(tSqlTemplateData);
                InsertSQLTreeNodeAction action = new InsertSQLTreeNodeAction(pluginSession, g, sqlInsertPopup, sqlToolbar);
                if (separator) {
                    DBCSessionSeparatorAction sessionSeparatorAction = factory.newInstance(null,DBCSessionSeparatorAction.class,pluginInfo);
                    all.add(sessionSeparatorAction);
                    if (sqlInsertPopup) {
                        sessionSeparatorAction.addLocationPath(DBCActionLocation.POPUP, "/sql-ddl/SQLInsert");
                    }
                    if (sqlToolbar) {
                        sessionSeparatorAction.addLocationPath(DBCActionLocation.TOOLBAR, "/");
                    }
                }
                all.add(action);
            }
            if (sqlSavePopup) {
                GenericTreeSQLGenerator g=factory.newInstance(null,GenericTreeSQLGenerator.class,pluginInfo);
                g.init(tSqlTemplateData);
                SaveFileSQLTreeNodeAction action = new SaveFileSQLTreeNodeAction(pluginSession, g, sqlSavePopup, sqlToolbar);
                if (separator) {
                    DBCSessionSeparatorAction sessionSeparatorAction = factory.newInstance(null,DBCSessionSeparatorAction.class,pluginInfo);
                    all.add(sessionSeparatorAction);
                    if (sqlInsertPopup) {
                        sessionSeparatorAction.addLocationPath(DBCActionLocation.POPUP, "/sql-ddl/SQLSave");
                    }
                    if (sqlToolbar) {
                        sessionSeparatorAction.addLocationPath(DBCActionLocation.TOOLBAR, "/");
                    }
                }
                all.add(action);
            }
        }
        return all;
    }
}
