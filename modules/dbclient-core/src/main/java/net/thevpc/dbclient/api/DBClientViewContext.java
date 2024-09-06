package net.thevpc.dbclient.api;

import net.thevpc.dbclient.api.actionmanager.DBCActionManager;
import net.thevpc.dbclient.api.pluginmanager.DBCPluggable;
import net.thevpc.common.prs.ResourceSetHolder;
import net.thevpc.common.prs.artset.ArtSet;
import net.thevpc.common.prs.iconset.IconSet;
import net.thevpc.common.prs.messageset.MessageSet;
import net.thevpc.common.swing.plaf.PlafItem;
import net.thevpc.common.swing.dialog.MessageDialogManager;

import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Locale;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)  alias vpc
 * @creationtime 2009/08/15 11:21:27
 */
public interface DBClientViewContext extends ResourceSetHolder, DBCPluggable {

    public MessageDialogManager getDialogManager();

    /**
     * get Action Manager which holds all Actions
     *
     * @return Action Manager
     */
    public DBCActionManager getActionManager();


    public void setPlaf(PlafItem plaf);

    public PlafItem getPlaf();

    /**
     * add a Listener on particular DBClient Properties.
     * standard properties are DBClient.PROPERTY_*
     *
     * @param propertyName
     * @param listener
     */
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);

    /**
     * add a Listener on ALL DBClient Properties.
     *
     * @param listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener);

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener);

    public void removePropertyChangeListener(PropertyChangeListener listener);

    /**
     * Client Properties is generic use HashMap that Holds any mapping used by user
     *
     * @return Client Properties
     */
    public HashMap<Object, Object> getClientProperties();

    public MessageSet getMessageSet();

    /**
     * Change Current Application ArtSet
     *
     * @param artSet new artSet
     */
    public void setArtSet(ArtSet artSet);

    /**
     * Change Current Application IconSet
     *
     * @param iconSet new iconSet
     */
    public void setIconSet(IconSet iconSet);

    /**
     * Current IconSet
     *
     * @return Current IconSet
     */
    public IconSet getIconSet();

    /**
     * Current ArtSet
     *
     * @return Current ArtSet
     */
    public ArtSet getArtSet();

    /**
     * Change Current Application Locale
     *
     * @param locale new locale
     */
    public void setLocale(Locale locale);

    /**
     * Current Application Locale
     *
     * @return Current Application Locale
     */
    public Locale getLocale();


}
