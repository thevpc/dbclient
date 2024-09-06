/**
 * ====================================================================
 *             DBClient yet another Jdbc client tool
 *
 * DBClient is a new Open Source Tool for connecting to jdbc
 * compliant relational databases. Specific extensions will take care of
 * each RDBMS implementation.
 *
 * Copyright (C) 2006-2008 Taha BEN SALAH
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 * ====================================================================
 */
package net.thevpc.dbclient.plugin.system.sessionmanager.window;

import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import net.thevpc.dbclient.api.DBCApplication;
import net.thevpc.dbclient.api.DBCApplicationView;
import net.thevpc.dbclient.api.DBCSession;
import net.thevpc.dbclient.api.actionmanager.DBCActionManager;
import net.thevpc.dbclient.api.actionmanager.DBCApplicationAction;
import net.thevpc.dbclient.api.actionmanager.DBCSessionAction;
import net.thevpc.dbclient.api.actionmanager.DBClientAction;
import net.thevpc.dbclient.api.pluginmanager.DBCAbstractPluggable;
import net.thevpc.dbclient.api.pluginmanager.DBCPluginSession;
import net.thevpc.dbclient.api.sessionmanager.*;
import net.thevpc.dbclient.api.sql.DBCConnection;
import net.thevpc.dbclient.api.sql.objects.*;
import net.thevpc.dbclient.api.sql.util.SQLUtils;
import net.thevpc.dbclient.api.viewmanager.DBCComponent;
import net.thevpc.dbclient.api.viewmanager.DBCMenuBar;
import net.thevpc.dbclient.api.viewmanager.DBCStatusBar;
import net.thevpc.dbclient.api.viewmanager.DBCToolBar;
import net.thevpc.common.swing.prs.PRSManager;
import net.thevpc.common.prs.artset.ArtSet;
import net.thevpc.common.prs.artset.ArtSetManager;
import net.thevpc.common.prs.artset.NotArtSet;
import net.thevpc.common.prs.iconset.IconSet;
import net.thevpc.common.swing.iconset.SwingIconSetManager;
import net.thevpc.common.prs.iconset.MultiIconSet;
import net.thevpc.common.prs.messageset.MessageSet;
import net.thevpc.common.swing.plaf.PlafItem;
import net.thevpc.common.swing.plaf.UIManager2;
import net.thevpc.common.prs.plugin.Initializer;
import net.thevpc.common.prs.plugin.Inject;
import net.thevpc.common.swing.dialog.MessageDialogType;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.ErrorManager;
import java.util.logging.Level;
import net.thevpc.common.prs.log.SimpleLogFormatter;
import net.thevpc.common.prs.iconset.IconSetManager;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 16 fevr. 2006 10:49:07
 */
public class DBCSessionViewImpl extends DBCAbstractPluggable implements DBCSessionView {

    protected Component mainComponent;
    protected JMenuBar menu;
    private Component toolbar;
    private Component statusBar;
    protected DBCSessionExplorer tree;
    protected DBCResultBrowser results;
    @Inject
    private DBCSession session;
    private boolean explodedLayout = false;
    private DBCSessionInternalWindowBrowser workspaceContainer;
    private DBCSessionInternalWindowBrowser traceContainer;
    private DBCSessionInternalWindowBrowser explorerContainer;
    private ArrayList<Component> components;
    private DBCSessionMessageDialogManager messageDialogManager;
    private DBCSessionLayoutManager sessionLayoutManager;
    private MessageSet messageSet;
    private IconSet iconSet;
    private ArtSet artSet;
    private PlafItem plaf;
    private PropertyChangeSupport pchsupport;
    private DBCActionManager actionManager;
    private HashMap<Object, Object> clientProperties = new HashMap<Object, Object>();

    public DBCSessionViewImpl() {
        pchsupport = new PropertyChangeSupport(this);
    }

    @Initializer
    protected void init() throws SQLException {
        messageSet = new MessageSet(session);
        getSession().addSessionListener(new DBCSessionListener() {

            public void sessionClosed(DBCSession session) {
                saveView();
                uninstallLogHandler();
            }
        });
        getSession().getConnection().addPropertyChangeListener(DBCConnection.DATABASE_STRUCTURE_CHANGED, new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                refreshView();
            }
        });
        try {
            String sesLocale = getSession().getConfig().getStringProperty(DBCApplicationView.PROPERTY_LOCALE, null);
            if (sesLocale != null) {
                String[] parts = sesLocale.split("_");
                String lang = parts.length > 0 ? parts[0] : "";
                String count = parts.length > 1 ? parts[1] : "";
                String vari = parts.length > 2 ? parts[2] : "";
                setLocale(new Locale(lang, count, vari));
            }
        } catch (Exception ex) {
            getSession().getLogger(getClass().getName()).log(Level.SEVERE, "Set Locale Failed", ex);
        }
        try {
            String sesPlaf = getSession().getConfig().getStringProperty(DBCApplicationView.PROPERTY_PLAF, null);
            if (sesPlaf != null && sesPlaf.length() > 0) {
                plaf = UIManager2.getPlafItem(sesPlaf);
                getSession().getApplication().getView().getLookAndFeelManager().setLookAndFeel(plaf);
            }
        } catch (Exception ex) {
            getSession().getLogger(getClass().getName()).log(Level.SEVERE, "Set Plaf Failed", ex);
        }
//        registerComponent((Component) getSession().getLog());
    }

    private void uninstallLogHandler() {
        Logger sessionRootLogger = getSession().getLogger("");
        Handler[] handlers = sessionRootLogger.getHandlers();
        SessionLogHandler found = null;
        for (Handler handler : handlers) {
            if (handler instanceof SessionLogHandler) {
                SessionLogHandler h = (SessionLogHandler) handler;
                //reference comparision
                if (h.session == getSession()) {
                    found = h;
                    break;
                }
            }
        }
        if (found != null) {
            sessionRootLogger.removeHandler(found);
        }
    }

    public String getInsertSQL(DBObject n) {
        return null;
    }

    public DBCSessionExplorer getExplorer() {
        if (tree == null) {
            tree = getSession().getFactory().newInstance(DBCSessionExplorer.class);
            registerComponent(tree.getComponent());
        }
        return tree;
    }

    public JMenuBar getMenu() {
        if (menu == null) {
            DBCMenuBar bar = getSession().getFactory().newInstance(DBCMenuBar.class);
            menu = bar.getMenuBarComponent();
            registerComponent(menu);
        }
        return menu;
    }

    public DBCSession getSession() {
        return session;
    }

//    public ResourcesSwingHelper getResources() {
//        if (helper == null) {
//            helper = new ResourcesSwingHelper(getSession().getResources());
//        }
//        return helper;
    //    }
    public void updateResources() {
        try {
            PRSManager.update(getActionManager().getActions(), this);
            for (Component component : getComponents()) {
                PRSManager.update(component, this);
            }
        } catch (Throwable th) {
            getDialogManager().showMessage(null, getMessageSet().get("Error.UnableToUpdateResource"), MessageDialogType.ERROR, null, th);
        }
    }

    public Component getMainComponent() {
        if (mainComponent == null) {
            DBCSessionViewLayoutComponent mc = getSession().getFactory().newInstance(DBCSessionViewLayoutComponent.class);
            mc.init(this);
            mainComponent = mc.getComponent();
            registerComponent(mainComponent);
            DBCInternalWindow w;
            w = addWindow(getExplorer(), Side.Explorer, false);
            w.setClosable(false);
            w.setLocked(true);
            w.setTitle(getMessageSet().get("Explorer.title"));
            Logger sessionRootLogger = getSession().getLogger("");
            uninstallLogHandler();
            DBCSessionLog dd = getSession().getFactory().newInstance(DBCSessionLog.class);
            SessionLogHandler hh = new SessionLogHandler(getSession(), dd);
            sessionRootLogger.addHandler(hh);
            w = addWindow(dd, Side.Footer, false);
            w.setClosable(false);
            w.setLocked(true);
            w.setTitle(getMessageSet().get("Log.title"));
            try {
                DBCSQLCommandPane tabFrame = session.getFactory().newInstance(DBCSQLCommandPane.class);
                w = addWindow(tabFrame, Side.Workspace, false);
                w.setClosable(false);
                w.setLocked(true);
                w.setTitle(getMessageSet().get("SQLCmd.title"));
            } catch (Exception e) {
                //nothing
            }
            updateResources();
        }
        return mainComponent;
    }

    public void saveView() {
        getExplorer().saveView();
    }

    public void refreshView() {
        try {
            getSession().revalidateConnection();
        } catch (Throwable e) {
            getDialogManager().showMessage(mainComponent, "Current Connection was closed and DBClient is unable to reconnect", MessageDialogType.ERROR, null, e);
            return;
        }
        saveView();
        reloadView();
    }

    public void reloadView() {
        getExplorer().reloadStructure();
    }

    public Icon getObjectIcon(DBObject node) {
        if (node instanceof DBProcedureColumn) {
            return getIconSet().getIconR(
                    "Tree.TableColumn.NORMAL." + SQLUtils.getSqlTypeName(((DBProcedureColumn) node).getSqlType(), getSession()));
        }
        if (node instanceof DBTableColumn) {
            DBTableColumn n = (DBTableColumn) node;
            return getIconSet().getIconR(
                    "Tree.TableColumn." + (n.getPkSeq() >= 0 ? "PK" : "NORMAL") + "." + SQLUtils.getSqlTypeName(n.getSqlType(), getSession()));
        }
        return getIconSet().getIconR(node.getId());
    }

    public boolean isDefaultObject(DBObject value) {
        return (value instanceof DBCatalog && ((DBCatalog) value).isDefault())
                || (value instanceof DBSchema && ((DBSchema) value).isDefault());
    }

    public String getObjectTreeName(DBObject node) {
        if (node instanceof DBServer) {
            return ((DBServer) node).getPreferredLabel();
        }
        if (node instanceof DBTableColumn) {
            return getMessageSet().get(
                    "Tree.TableColumn.NORMAL", new Object[]{node.getName(), 0, ((DBTableColumn) node).getSqlType()});
        }
        if (node instanceof DBProcedureColumn) {
            return getMessageSet().get(
                    "Tree.TableColumn.NORMAL", new Object[]{node.getName(), 0, ((DBProcedureColumn) node).getSqlType()});
        }
        if (node instanceof DBDatatype || node instanceof DBKeyword) {
            return getMessageSet().get(node.getId(), "{0}", true, true, new Object[]{node.getName()}).toLowerCase();
        }
        return getMessageSet().get(node.getId(), "{0}", true, true, new Object[]{node.getName()});
    }

    public Component getToolbar() {
        if (toolbar == null) {
            DBCToolBar bar = getSession().getFactory().newInstance(DBCToolBar.class);
            bar.init(getSession().getApplication(), getSession());
            toolbar = bar.getComponent();
            registerComponent(toolbar);
        }
        return toolbar;
    }

    public Component getStatusBar() {
        if (statusBar == null) {
            DBCStatusBar bar = getSession().getFactory().newInstance(DBCStatusBar.class);
            bar.init(getSession().getApplication(), getSession());
            statusBar = bar.getComponent();
            registerComponent(statusBar);
        }
        return statusBar;
    }

    public boolean isExplodedLayout() {
        return explodedLayout;
    }

    public void setExplodedLayout(boolean explodedLayout) {
        this.explodedLayout = explodedLayout;
    }

    protected void registerComponent(Component comp) {
        if (components == null) {
            components = new ArrayList<Component>();
        }
        components.add(comp);
    }

    public Component[] getComponents() {
        if (components == null) {
            return new Component[0];
        }
        return components.toArray(new Component[components.size()]);
    }

    public final DBCSessionInternalWindowBrowser getExplorerContainer() {
        if (explorerContainer == null) {
            explorerContainer = createExplorerContainer();
            if (explorerContainer != null) {
                registerComponent(explorerContainer.getComponent());
            }
        }
        return explorerContainer;
    }

    public final DBCSessionInternalWindowBrowser getTracerContainer() {
        if (traceContainer == null) {
            traceContainer = createTracerContainer();
            if (traceContainer != null) {
                registerComponent(traceContainer.getComponent());
            }
        }
        return traceContainer;
    }

    public final DBCSessionInternalWindowBrowser getWorkspaceContainer() {
        if (workspaceContainer == null) {
            workspaceContainer = createWorkspaceContainer();
            if (workspaceContainer != null) {
                registerComponent(workspaceContainer.getComponent());
            }
        }
        return workspaceContainer;
    }

    protected DBCSessionInternalWindowBrowser createExplorerContainer() {
        DBCSessionInternalWindowBrowser w = getSession().getFactory().newInstance(DBCSessionInternalWindowBrowser.class);
        w.init(Side.Explorer);
        return w;
    }

    protected DBCSessionInternalWindowBrowser createTracerContainer() {
        DBCSessionInternalWindowBrowser w = getSession().getFactory().newInstance(DBCSessionInternalWindowBrowser.class);
        w.init(Side.Footer);
        return w;
    }

    protected DBCSessionInternalWindowBrowser createWorkspaceContainer() {
        DBCSessionInternalWindowBrowser w = getSession().getFactory().newInstance(DBCSessionInternalWindowBrowser.class);
        w.init(Side.Workspace);
        return w;
    }

    public DBCInternalWindow addWindow(Component tabFrame, Side side, boolean replaceUnlocked) {
        switch (side) {
            case Workspace: {
                return getWorkspaceContainer().addWindow(tabFrame, replaceUnlocked);
            }
            case Footer: {
                return getTracerContainer().addWindow(tabFrame, replaceUnlocked);
            }
            case Explorer: {
                return getExplorerContainer().addWindow(tabFrame, replaceUnlocked);
            }
        }

        throw new IllegalArgumentException("Unsupported side " + side);
    }

    public DBCInternalWindow addWindow(DBCComponent tabFrame, Side side, boolean replaceUnlocked) {
        return addWindow(tabFrame.getComponent(), side, replaceUnlocked);
    }

    public DBCSessionMessageDialogManager getDialogManager() {
        if (messageDialogManager == null) {
            messageDialogManager = getSession().getFactory().newInstance(DBCSessionMessageDialogManager.class);
            messageDialogManager.init(getSession());
        }
        return messageDialogManager;
    }

    public void openSession() {
        DBCSession _session = getSession();
        DBCApplication _application = _session.getApplication();
        DBCApplicationView _application_view = _application.getView();
        messageSet.setLocale(DBCApplication.BOOT_LOCALE);
        messageSet.addBundle(_application_view.getMessageSet());
        messageSet.setLocale(_application_view.getLocale());
        for (DBCPluginSession dbcPlugin : _session.getPluginSessions()) {
            dbcPlugin.setLocale(_application_view.getLocale());
            messageSet.addBundle(dbcPlugin.getPlugin().getMessageSet());
        }
        iconSet = null;
        try {
            String sesIconSetName = _session.getConfig().getStringProperty(DBCApplicationView.PROPERTY_ICONSET, null);
            setIconSet(sesIconSetName == null ? null : PRSManager.getIconSet(sesIconSetName));
        } catch (Exception ex) {
            _session.getLogger(getClass().getName()).log(Level.SEVERE, "Set IconSet Failed", ex);
        }
        artSet = NotArtSet.INSTANCE;
        try {
            String artSetName = _session.getConfig().getStringProperty(DBCApplicationView.PROPERTY_ARTSET, null);
            artSet = ArtSetManager.getArtSet(artSetName);
            if (artSet == null) {
                artSet = NotArtSet.INSTANCE;
            }
        } catch (Exception ex) {
            _session.getLogger(getClass().getName()).log(Level.SEVERE, "Set ArSet Failed", ex);
        }

        ArrayList<DBClientAction> all = new ArrayList<DBClientAction>();
        for (DBCApplicationAction implementation : _session.getFactory().createImplementations(DBCApplicationAction.class)) {
            try {
                if (implementation.acceptSession(session)) {
                    all.add(implementation);
                }
            } catch (Throwable ee) {
                _session.getLogger(getClass().getName()).log(Level.SEVERE, "Unable to create Application Action " + implementation.getClass().getName() + ")", ee);
            }
        }
        for (DBCSessionAction implementation : _session.getFactory().createImplementations(DBCSessionAction.class)) {
            try {
                if (implementation.acceptSession(session)) {
                    all.add(implementation);
                }
            } catch (Throwable ee) {
                _session.getLogger(getClass().getName()).log(Level.SEVERE, "Unable to create Application Action (" + implementation.getClass().getName() + ")", ee);
            }
        }

        Collections.sort(all);
        DBCActionManager _actionManager = getActionManager();
        for (DBClientAction action : all) {
            PRSManager.update(action, action);
            _actionManager.registerAction(action);
        }

        getSessionLayoutManager().doLayout();
    }

    public DBCSessionLayoutManager getSessionLayoutManager() {
        if (sessionLayoutManager == null) {
            sessionLayoutManager = getSession().getFactory().newInstance(DBCSessionLayoutManager.class);
        }
        return sessionLayoutManager;
    }

    public MessageSet getMessageSet() {
        return messageSet;
    }

    public void setIconSet(IconSet iconSet) {
        IconSet old = this.iconSet;
        this.iconSet = new MultiIconSet(
                getSession(),
                iconSet,
                getSession().getApplication().getView().getIconSet(),
                //default iconSet
                IconSetManager.getIconSet(DBCApplicationView.DEFAULT_ICONSET_NAME));
        getSession().getConfig().setStringProperty(DBCApplicationView.PROPERTY_ICONSET, iconSet == null ? null : iconSet.getId());
        pchsupport.firePropertyChange(DBCApplicationView.PROPERTY_ICONSET, old, artSet);
        updateResources();
    }

    public void setArtSet(ArtSet artSet) {
        ArtSet old = this.artSet;
        this.artSet = artSet;
        pchsupport.firePropertyChange(DBCApplicationView.PROPERTY_ARTSET, old, artSet);
    }

    public ArtSet getArtSet() {
        return (artSet == null || artSet == NotArtSet.INSTANCE) ? getSession().getApplication().getView().getArtSet() : artSet;
    }

    public IconSet getIconSet() {
        return iconSet == null ? getSession().getApplication().getView().getIconSet() : iconSet;
    }

    public void setLocale(Locale locale) {
        Locale old = messageSet.getLocale();
        getSession().getConfig().setStringProperty(DBCApplicationView.PROPERTY_LOCALE, locale == null ? null : locale.toString());
        locale = locale == null ? DBCApplication.BOOT_LOCALE : locale;
        messageSet.setLocale(locale);
        for (DBCPluginSession dbcPlugin : getSession().getPluginSessions()) {
            dbcPlugin.setLocale(locale);
        }
        pchsupport.firePropertyChange(DBCApplicationView.PROPERTY_LOCALE, old, old);
        updateResources();
    }

    public Locale getLocale() {
        return getMessageSet().getLocale();
    }

    public void setPlaf(PlafItem plaf) {
        try {
            this.plaf = plaf;
            getSession().getApplication().getView().getLookAndFeelManager().setLookAndFeel(plaf);
            getSession().getConfig().setStringProperty(DBCApplicationView.PROPERTY_PLAF, plaf == null ? null : plaf.getId());
        } catch (Exception ex) {
            getDialogManager().showMessage(null, null, MessageDialogType.ERROR, null, ex);
        }
    }

    public PlafItem getPlaf() {
        return plaf;
    }

    public DBCActionManager getActionManager() {
        if (actionManager == null) {
            actionManager = new DBCActionManager();
        }
        return actionManager;
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        pchsupport.addPropertyChangeListener(propertyName, listener);
    }

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        pchsupport.removePropertyChangeListener(propertyName, listener);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pchsupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pchsupport.removePropertyChangeListener(listener);
    }

    public HashMap<Object, Object> getClientProperties() {
        return clientProperties;
    }

    private static class SessionLogHandler extends Handler {

        private DBCSessionLog log;
        private DBCSession session;

        public SessionLogHandler(DBCSession session, DBCSessionLog log) {
            this.log = log;
            this.session = session;
            setFormatter(new SimpleLogFormatter());
        }

        @Override
        public void publish(LogRecord record) {
            String msg;
            try {
                msg = getFormatter().format(record);
            } catch (Exception ex) {
                // We don't want to throw an exception here, but we
                // report the exception to any registered ErrorManager.
                reportError(null, ex, ErrorManager.FORMAT_FAILURE);
                return;
            }
            Level level = record.getLevel();
            if (Level.SEVERE.equals(level)) {
                log.error(msg.trim());
            } else if (Level.WARNING.equals(level)) {
                log.warning(msg.trim());
            } else if (Level.INFO.equals(level)) {
                log.trace(msg.trim());
            } else if (Level.CONFIG.equals(level)) {
                log.trace(msg.trim());
            } else {
                log.debug(msg.trim());
            }
        }

        @Override
        public void flush() {
            //
        }

        @Override
        public void close() throws SecurityException {
            //
        }
    }
}
