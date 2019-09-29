package net.vpc.dbclient.api.pluginmanager;

import net.vpc.swingext.iconset.SwingIconSetManager;
import java.util.HashMap;
import net.vpc.dbclient.api.DBCApplicationView;
import net.vpc.dbclient.api.DBCSession;
import net.vpc.dbclient.api.configmanager.DBCPluginSessionConfig;
import net.vpc.dbclient.api.sessionmanager.DBCPluginIconSet;
import net.vpc.dbclient.api.sessionmanager.DBCSessionIconSet;
import net.vpc.dbclient.api.sessionmanager.DBCSessionListener;
import net.vpc.prs.classloader.MultiClassLoader;
import net.vpc.prs.iconset.*;
import net.vpc.prs.messageset.MessageSet;
import net.vpc.swingext.messageset.SwingMessageSetManager;
import net.vpc.prs.messageset.MessageSetResourceBundleWrapper;

import java.util.Locale;
import net.vpc.prs.messageset.MessageSetManager;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com) @creationtime 27 avr. 2007
 * 20:33:33
 */
public abstract class DBCAbstractPluginSession extends DBCAbstractPluggable implements DBCPluginSession, DBCSessionListener {

    private Locale locale;
    private MessageSet messageSet;
    private IconSetWrapper userIconSetWrapper;
    private IconSet iconSet;
    private DBCPlugin plugin;
    private DBCSession session;
    private DBCPluginSessionConfig config;
    private HashMap<String, Object> userObjects;

    public DBCAbstractPluginSession() {
    }

    public void init(DBCPlugin plugin, DBCSession session) {
        this.plugin = plugin;
        this.session = session;
        userIconSetWrapper = new IconSetWrapper(session);
        session.addSessionListener(this);
        final DBCSession _session = getSession();
        iconSet = new MultiIconSet(
                session,
                //PluginSession iconSet
                userIconSetWrapper,
                //Session iconSet
                new DBCSessionIconSet(_session),
                //Plugin iconSet
                new DBCPluginIconSet(getPlugin()),
                //default iconSet
                IconSetManager.getIconSet(DBCApplicationView.DEFAULT_ICONSET_NAME));
        this.messageSet = new MessageSet(_session, _session.getView().getMessageSet(), getPlugin().getMessageSet());
    }

    public DBCPlugin getPlugin() {
        return plugin;
    }

    public DBCSession getSession() {
        return session;
    }

    public void sessionClosing() {
    }

    public void sessionOpening() {
    }

    public Locale getLocale() {
        return locale == null ? Locale.getDefault() : locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
        if (messageSet != null) {
            messageSet.setLocale(getLocale());
        }

        if (iconSet != null) {
            iconSet.setLocale(getLocale());
        }

    }

    /**
     * combine session,plugin and the provided messagesets
     *
     * @param messageSet the top level (most priority) message set
     */
    public void setMessageSet(MessageSet messageSet) {
        this.messageSet = new MessageSet(getSession(), getSession().getView().getMessageSet(), getPlugin().getMessageSet(), messageSet);
    }

    public void setMessageSet(String bundleName) {
        this.messageSet = new MessageSet(getSession(), getSession().getView().getMessageSet(), getPlugin().getMessageSet(), new MessageSetResourceBundleWrapper(bundleName, getLocale(), new MultiClassLoader(getPlugin().getDescriptor().getClassLoader(), MessageSetManager.getDefaultClassLoader())));
    }

    public IconSet getIconSet() {
        return iconSet == null ? getSession().getView().getIconSet() : iconSet;
    }

    public void setIconSet(IconSet iconSet) {
        userIconSetWrapper.setIconSet(iconSet);
    }

    public MessageSet getMessageSet() {
        return messageSet == null ? getSession().getView().getMessageSet() : messageSet;
    }

    public void sessionOpened() {
        //
    }

    public void setIconSet(String iconSetBundleName) {
        setIconSet(new DefaultIconSet(iconSetBundleName, getClass().getClassLoader(), getLocale(), getSession()));
    }

    public void sessionClosed(DBCSession session) {
        //do nothing
    }

    public DBCPluginSessionConfig getConfig() {
        if (config == null) {
            config = new DBCPluginSessionConfig();
            config.init(this);
        }
        return config;
    }

    public <T> T instantiate(Class<T> clazz) {
        return getSession().getFactory().instantiate(clazz, getPlugin().getDescriptor());
    }

    @Override
    public Object getUserObject(String name) {
        if(userObjects==null){
            return null;
        }
        return userObjects.get(name);
    }

    @Override
    public void setUserObject(String name, Object value) {
        if(value==null){
            if(userObjects!=null){
                userObjects.remove(name);
            }
        }else{
            if(userObjects==null){
                userObjects=new HashMap<String, Object>();
            }
            userObjects.put(name, value);
        }
    }
    
    
}
