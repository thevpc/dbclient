/**
 * ====================================================================
 *             DBCLient yet another Jdbc client tool
 *
 * DBClient is a new Open Source Tool for connecting to jdbc
 * compliant relational databases. Specific extensions will take care of
 * each RDBMS implementation.
 *
 * Copyright (C) 2006-2007 Taha BEN SALAH
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
package net.thevpc.dbclient.plugin.system.configmanager;

import net.thevpc.common.io.FileUtils;
import net.thevpc.common.io.IOUtils;
import net.thevpc.common.prs.xml.XmlUtils;
import net.thevpc.dbclient.api.configmanager.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.logging.Level;

/**
 * @author vpc
 */
public abstract class DBCAbstractApplicationConfig extends DBCAbstractConfig implements DBCApplicationConfig {

    private int getLenientInt(String var) {
        String s = getStringProperty(var, null);
        try {
            return Integer.parseInt(s);
        } catch (Throwable e) {
            return 0;
        }
    }

    public void updateAll(URL resource, DBCIfFoundAction ifFoundAction, DBCIfNotFoundAction ifNotFoundAction) throws IOException, IllegalArgumentException {
        InputStream inputStream = null;
        try {
            inputStream = resource.openStream();
            Collection v = (Collection) XmlUtils.xmlToObject(inputStream, null, null);
            updateAll(v, ifFoundAction, ifNotFoundAction);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    public void updateAll(Collection v, DBCIfFoundAction ifFoundAction, DBCIfNotFoundAction ifNotFoundAction) throws IllegalArgumentException {
        Map<String, DBCDriverInfo> driversByDriverClassName = null;
        Map<String, DBCMessageInfo> messagesByCombinedKey = null;
        Map<String, DBCConfigPropInfo> configPropsByName = null;

        for (Object o : v) {
            if (o instanceof DBCDriverInfo) {
                DBCDriverInfo d = (DBCDriverInfo) o;
//                d.setApplication(getApplication());
                if (driversByDriverClassName == null) {
                    driversByDriverClassName = new HashMap<String, DBCDriverInfo>();
                    for (DBCDriverInfo dBCDriverInfo : getDrivers()) {
                        driversByDriverClassName.put(dBCDriverInfo.getDriverClassName(), dBCDriverInfo);
                    }
                }
                DBCDriverInfo old = driversByDriverClassName.get(d.getDriverClassName());
                if (old == null) {
                    switch (ifNotFoundAction) {
                        case ADD: {
                            addDriver(d);
                            break;
                        }
                        case FAIL: {
                            throw new IllegalArgumentException("Failed");
                        }
                        case ASK: {
                            if (doAsk(d)) {
                                addDriver(d);
                            }
                            break;
                        }
                        case IGNORE: {
                            break;
                        }
                    }
                } else {
                    switch (ifFoundAction) {
                        case OVERWRITE: {
                            old.setAllProperties(d);
                            updateDriver(old);
                            break;
                        }
                        case FAIL: {
                            throw new IllegalArgumentException("Failed");
                        }
                        case ASK: {
                            if (doAsk(d)) {
                                old.setAllProperties(d);
                                updateDriver(old);
                            }
                            break;
                        }
                        case IGNORE: {
                            break;
                        }
                    }
                }
            } else if (o instanceof DBCMessageInfo) {
                DBCMessageInfo d = (DBCMessageInfo) o;
                if (messagesByCombinedKey == null) {
                    messagesByCombinedKey=new HashMap<String, DBCMessageInfo>();
                    for (DBCMessageInfo msg : getMessages()) {
                        messagesByCombinedKey.put(msg.getCombinedKey(), msg);
                    }
                }
                DBCMessageInfo old = messagesByCombinedKey.get(d.getCombinedKey());
                if (old == null) {
                    switch (ifNotFoundAction) {
                        case ADD: {
                            addMessage(d);
                            break;
                        }
                        case ASK: {
                            if (doAsk(d)) {
                                addMessage(d);
                            }
                            break;
                        }
                        case FAIL: {
                            throw new IllegalArgumentException("Failed");
                        }
                        case IGNORE: {
                            break;
                        }
                    }
                } else {
                    switch (ifFoundAction) {
                        case OVERWRITE: {
                            old.setAllProperties(d);
                            updateMessage(old);
                            break;
                        }
                        case FAIL: {
                            throw new IllegalArgumentException("Failed");
                        }
                        case ASK: {
                            if (doAsk(d)) {
                                old.setAllProperties(d);
                                updateMessage(old);
                            }
                            break;
                        }
                        case IGNORE: {
                            break;
                        }
                    }
                }
            } else if (o instanceof DBCConfigPropInfo) {
                DBCConfigPropInfo d = (DBCConfigPropInfo) o;
                if (configPropsByName == null) {
                    configPropsByName=new HashMap<String, DBCConfigPropInfo>();
                    for (DBCConfigPropInfo msg : getStringProperties()) {
                        configPropsByName.put(msg.getCprName(), msg);
                    }
                }
                DBCConfigPropInfo old = configPropsByName.get(d.getCprName());
                if (old == null) {
                    switch (ifNotFoundAction) {
                        case ADD: {
                            setStringProperty(d.getCprName(), d.getCprValue());
                            break;
                        }
                        case FAIL: {
                            throw new IllegalArgumentException("Failed");
                        }
                        case ASK: {
                            if (doAsk(d)) {
                                setStringProperty(d.getCprName(), d.getCprValue());
                            }
                            break;
                        }
                        case IGNORE: {
                            break;
                        }
                    }
                } else {
                    switch (ifFoundAction) {
                        case OVERWRITE: {
                            //old.setAllProperties(d);
                            setStringProperty(d.getCprName(), d.getCprValue());
                            break;
                        }
                        case FAIL: {
                            throw new IllegalArgumentException("Failed");
                        }
                        case ASK: {
                            if (doAsk(d)) {
                                setStringProperty(d.getCprName(), d.getCprValue());
                            }
                            break;
                        }
                        case IGNORE: {
                            break;
                        }
                    }
                }
            }
        }
    }

    protected boolean doAsk(DBCAbstractInfo info) {
        return true;
    }

    public boolean isSplashScreenEnabled() {
        return getBooleanProperty("init.splash", false);
    }

    public void setSplashScreenEnabled(boolean enabled) {
        setBooleanProperty("init.splash", enabled);
        updateSplashFile();
    }

    protected void updateSplashFile() {
        URL u = null;
        if (getBooleanProperty("init.splash", true)) {
            u = getApplication().getView().getArtSet().getArtImageURL("dbclient-splash");
        }
        if (u == null) {
            new File(getApplication().getConfigDir(), "dbclient-splash.png").delete();
            new File(getApplication().getConfigDir(), "dbclient-splash.jpg").delete();
        } else {
            try {
                String ext = FileUtils.getFileExtension(new File(u.getFile()));
                if (ext == null || ext.length() == 0) {
                    ext = "png";
                }
                IOUtils.copy(u, new File(getApplication().getConfigDir(), "dbclient-splash." + ext));
            } catch (IOException ex) {
                getApplication().getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
