package net.thevpc.dbclient.api;

import net.thevpc.dbclient.api.viewmanager.DBCApplicationMessageDialogManager;
import net.thevpc.dbclient.api.viewmanager.DBCLookAndFeelManager;
import net.thevpc.dbclient.api.viewmanager.DBCSplashScreen;
import net.thevpc.dbclient.api.windowmanager.DBCWindowManager;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)  alias vpc
 * @creationtime 2009/08/15 11:21:10
 */
public interface DBCApplicationView extends DBClientViewContext {
    String DEFAULT_ICONSET_NAME = "dbclient-iconset-default";
    String PROPERTY_LOCALE = "ui.locale";
    String PROPERTY_ARTSET = "ui.artset";
    String PROPERTY_ICONSET = "ui.iconset";
    String PROPERTY_PLAF = "ui.plaf";
    String PROPERTY_MESSAGESET = "messageSet";

    public static final String ARTSET_SPLASH_SCREEN = "dbclient-splash";
    public static final String ARTSET_ICON16 = "dbclient16";
    public static final String ARTSET_ICON32 = "dbclient32";
    public static final String ARTSET_ICON48 = "dbclient48";
    public static final String ARTSET_ICON64 = "dbclient64";
    public static final String ARTSET_ICON128 = "dbclient128";
    public static final String ARTSET_VERTICAL_BANNER_FAT = "dbclient-banner-margin1";
    public static final String ARTSET_VERTICAL_BANNER_THIN = "dbclient-banner-margin2";
    public static final String ARTSET_HORIZONTAL_BANNER_FAT = "dbclient-banner-header1";
    public static final String ARTSET_HORIZONTAL_BANNER_THIN = "dbclient-banner-header2";


    public DBCApplicationMessageDialogManager getDialogManager();

    public DBCLookAndFeelManager getLookAndFeelManager();

    public DBCWindowManager getWindowManager();

    /**
     * Splash Screen
     *
     * @return DBCSplashScreen is created or null if not
     */
    public DBCSplashScreen getSplashScreen();

    /**
     * create splash screen and shows it.
     * Splash creen is garanteeed to be create only once.
     */
    public void showSplashScreen();

    /**
     * Hide Splash Screen if visible
     */
    public void hideSplashScreen();


}
