package net.thevpc.dbclient.api;

import net.thevpc.common.prs.artset.ArtSet;
import net.thevpc.common.prs.artset.ArtSetManager;
import net.thevpc.common.prs.artset.NotArtSet;
import net.thevpc.common.prs.iconset.IconSet;
import net.thevpc.common.prs.messageset.MessageSet;
import net.thevpc.common.swing.SwingLocaleManager;
import net.thevpc.common.swing.dialog.DefaultMessageDialogManager;
import net.thevpc.common.swing.dialog.MessageDialogType;
import net.thevpc.common.swing.messageset.SwingMessageSetManager;
import net.thevpc.common.swing.plaf.PlafItem;
import net.thevpc.common.swing.prs.PRSManager;
import net.thevpc.dbclient.api.actionmanager.DBCActionManager;
import net.thevpc.dbclient.api.pluginmanager.DBCAbstractPluggable;
import net.thevpc.dbclient.api.pluginmanager.DBCPlugin;
import net.thevpc.dbclient.api.sessionmanager.DBCSessionListener;
import net.thevpc.dbclient.api.viewmanager.DBCApplicationMessageDialogManager;
import net.thevpc.dbclient.api.viewmanager.DBCLookAndFeelManager;
import net.thevpc.dbclient.api.viewmanager.DBCSplashScreen;
import net.thevpc.dbclient.api.viewmanager.DBCSplashScreenImpl;
import net.thevpc.dbclient.api.windowmanager.DBCWindow;
import net.thevpc.dbclient.api.windowmanager.DBCWindowKind;
import net.thevpc.dbclient.api.windowmanager.DBCWindowManager;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

import net.thevpc.dbclient.api.viewmanager.DBCAbstractButtonMessageSetUpdater;
import net.thevpc.dbclient.api.viewmanager.DBCSilentSplashScreen;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)  alias vpc
 * @creationtime 2009/08/15 11:23:51
 */
public class DBCApplicationViewImpl extends DBCAbstractPluggable implements DBCApplicationView {

    private DBCApplication application;
    private PlafItem plafItem;
    private DBCApplicationMessageDialogManager messageDialogManager;
    private DBCLookAndFeelManager lookAndFeelSupport;
    private DBCWindowManager windowManager;
    private DBCActionManager actionManager;
    private MessageSet messageSet;
    private IconSet iconSet;
    private ArtSet artSet = NotArtSet.INSTANCE;
    private DBCApplicationMessageDialogManager defaultMessageDialogManager;
    private DBCSplashScreen splash;
    private Locale locale;
    private PropertyChangeSupport pchsupport;
    private HashMap<Object, Object> clientProperties = new HashMap<Object, Object>();

    static {
        SwingMessageSetManager.setDefaultComponentMessageSetUpdater(AbstractButton.class, new DBCAbstractButtonMessageSetUpdater());
    }

    public DBCApplicationViewImpl(DBCApplication application) {
        this.application = application;
        this.locale = DBCApplicationImpl.BOOT_LOCALE;
        pchsupport = new PropertyChangeSupport(this);

        application.addPropertyChangeListener("session.added", new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                DBCSession session = (DBCSession) evt.getNewValue();
                session.addSessionListener(new DBCSessionListener() {

                    public void sessionClosed(DBCSession session) {
                        getWindowManager().getSessionListWindow().showWindow();
                    }
                });
            }
        });
    }

    public DBCLookAndFeelManager getLookAndFeelManager() {
        application.checkApplicationState(DBCApplication.ApplicationState.OPENING, DBCApplication.ApplicationState.CLOSED);
        if (lookAndFeelSupport == null) {
            lookAndFeelSupport = application.getFactory().newInstance(DBCLookAndFeelManager.class);
        }
        return lookAndFeelSupport;
    }

    public DBCWindowManager getWindowManager() {
        if (windowManager == null) {
            application.checkApplicationState(DBCApplication.ApplicationState.READY, DBCApplication.ApplicationState.CLOSED);
            windowManager = application.getFactory().newInstance(DBCWindowManager.class);
        }
        return windowManager;
    }

    private static class DBCDefaultMessageDialogManager extends DefaultMessageDialogManager implements DBCApplicationMessageDialogManager {

        private DBCDefaultMessageDialogManager() {
        }
    }

    public DBCApplicationMessageDialogManager getDialogManager() {
        if (messageDialogManager == null) {
            if (application.getApplicationState().compareTo(DBCApplication.ApplicationState.CONFIGURING) < 0) {
                if (defaultMessageDialogManager == null) {
                    defaultMessageDialogManager = new DBCDefaultMessageDialogManager();
                }
                return defaultMessageDialogManager;
            }
            application.checkApplicationState(DBCApplication.ApplicationState.CONFIGURING, DBCApplication.ApplicationState.CLOSED);
            messageDialogManager = application.getFactory().newInstance(DBCApplicationMessageDialogManager.class);
            messageDialogManager.init(application);
        }
        return messageDialogManager;
    }

    public DBCActionManager getActionManager() {
        if (actionManager == null) {
            actionManager = new DBCActionManager();
        }
        return actionManager;
    }

    public MessageSet getMessageSet() {
        if (messageSet == null) {
            messageSet = new MessageSet(application);
            messageSet.setLocale(DBCApplicationImpl.BOOT_LOCALE);
        }
        return messageSet;
    }

    public IconSet getIconSet() {
        return iconSet == null ? PRSManager.getIconSet() : iconSet;
    }

    public void setIconSet(IconSet iconSet) {
        IconSet old = this.iconSet;
        this.iconSet = iconSet;
        if (application.getApplicationState().ordinal() >= DBCApplication.ApplicationState.READY.ordinal()) {
            application.getConfig().setStringProperty(PROPERTY_ICONSET, iconSet == null ? null : iconSet.getId());
            updateResources();
        }
        pchsupport.firePropertyChange(PROPERTY_ICONSET, old, this.iconSet);
    }

    /////////////////////////////////////////////////////
    //
    // Splash screen handling
    //
    /////////////////////////////////////////////////////
    /**
     * create splash creen and shows it
     */
    public void showSplashScreen() {
        File cfolder = application.getConfigDir();
        if (splash == null) {
            splash = new DBCSplashScreenImpl(cfolder == null ? new File("config") : cfolder);
            splash.addDefaultMessages();
        }
        if (splash.getImageIcon() != null) {
            splash.show();
        }
    }

    /**
     * hide splash screen if visible
     */
    public void hideSplashScreen() {
        if (splash != null) {
            splash.hide();
        }
    }

    /**
     * DBClient Splash Screen
     *
     * @return Splash screen (or null if not created)
     */
    public DBCSplashScreen getSplashScreen() {
        return splash == null ? DBCSilentSplashScreen.INSTANCE : splash;
    }

    public void updateResources() {
        try {
            PRSManager.update(getActionManager().getActions(), this);
            if (application.getApplicationState().compareTo(DBCApplication.ApplicationState.READY) >= 0) {
                Locale _locale = getLocale();
                IconSet _iconSet = getIconSet();
                DBCWindow[] windows = getWindowManager().getWindows(
                        DBCWindowKind.values());
                for (DBCWindow window : windows) {
                    if (window.getSession() == null) {
                        Component component = window.getComponent();
                        if (component instanceof JComponent) {
                            SwingLocaleManager.setComponentLocale((JComponent) component, _locale);
                            //PRSManager.setComponentIconSet((JComponent) component, iconSet.getId());
                        }
                        PRSManager.update(window.getComponent(), this);
                    }
                }
            }
        } catch (Throwable th) {
            getDialogManager().showMessage(null, "Unable to update resources", MessageDialogType.ERROR, null, th);
        }
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        Locale old = this.locale;
        if (locale == null) {
            locale = DBCApplicationImpl.BOOT_LOCALE;
        }
        this.locale = locale;
        if (!Objects.equals(old, locale)) {
            SwingLocaleManager.setLocale(locale);
            if (messageSet != null) {
                messageSet.setLocale(locale);
            }
            DBCPlugin[] dbcPlugins = application.getPluginManager().getEnabledPlugins();
            for (DBCPlugin dbcPlugin : dbcPlugins) {
                dbcPlugin.setLocale(locale);
            }
        }
        if (application.getApplicationState().ordinal() >= DBCApplication.ApplicationState.READY.ordinal()) {
            if (!Objects.equals(old, locale)) {
                application.getConfig().setStringProperty(PROPERTY_LOCALE, locale.toString());
                updateResources();
            }
        }
        if (!Objects.equals(old, locale)) {
            pchsupport.firePropertyChange(PROPERTY_LOCALE, old, locale);
        }
    }

    public ArtSet getArtSet() {
        return artSet == null ? NotArtSet.INSTANCE : artSet;
    }

    public void setArtSet(ArtSet artSet) {
        ArtSet old = this.artSet;
        this.artSet = artSet;
        boolean nullArtSet = artSet == null || (artSet instanceof NotArtSet);
        if (application.getApplicationState().ordinal() >= DBCApplication.ApplicationState.READY.ordinal()) {
            application.getConfig().setStringProperty(PROPERTY_ARTSET, artSet == null ? null : artSet.getId());
            application.getConfig().setSplashScreenEnabled(!nullArtSet);
        }
        ArtSetManager.setCurrent(nullArtSet ? null : artSet.getId());
        pchsupport.firePropertyChange(PROPERTY_ARTSET, old == null ? null : old.getId(), artSet == null ? null : artSet.getId());
    }

    public PlafItem getPlaf() {
        return plafItem;
    }

    public void setPlaf(PlafItem plafItem) {
        this.plafItem = plafItem;
        try {
            getLookAndFeelManager().setLookAndFeel(plafItem);
        } catch (Exception ex) {
            getDialogManager().showMessage(null, null, MessageDialogType.ERROR, null, ex);
        }
        if (application.getApplicationState().ordinal() > DBCApplication.ApplicationState.OPENING.ordinal()) {
            application.getConfig().setStringProperty(DBCApplicationView.PROPERTY_PLAF, plafItem == null ? null : plafItem.toString());
        }
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
}
