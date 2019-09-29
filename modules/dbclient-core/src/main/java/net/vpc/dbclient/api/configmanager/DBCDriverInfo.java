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
package net.vpc.dbclient.api.configmanager;


import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 4 janv. 2007 00:54:36
 */
public final class DBCDriverInfo extends DBCAbstractInfo implements Serializable {
//    private transient URLClassLoader driverClassLoader;
//    private transient Boolean loadCache = null;
//    private transient DBCApplication application;

    public DBCDriverInfo() {
    }

    public DBCDriverInfo(int id, int index, String name, boolean enabled, String driverClassName, String defaultURL, String defaultLogin, String defaultPassword, URL[] libraries, String decription) {
        setId(id);
        setIndex(index);
        setName(name);
        setEnabled(enabled);
        setDefaultURL(defaultURL);
        setDefaultLogin(defaultLogin);
        setDefaultPassword(defaultPassword);
        setLibraries(libraries);
        setDriverClassName(driverClassName);
        setDescription(decription);
    }

//    public DBCDriverInfo(DBCApplication application) {
//        this.application = application;
//    }
    public int getId() {
        return (Integer) map.get("id");
    }

    public void setId(int id) {
        map.put("id", id);
    }

    public int getIndex() {
        return (Integer) map.get("index");
    }

    public void setIndex(int index) {
        map.put("index", index);
    }

    public String getDefaultURL() {
        return (String) map.get("defaultURL");
    }

    public void setDefaultURL(String defaultURL) {
        map.put("defaultURL", defaultURL);
    }

    public String getDefaultLogin() {
        return (String) map.get("defaultLogin");
    }

    public void setDefaultLogin(String defaultLogin) {
        map.put("defaultLogin", defaultLogin);
    }

    public String getDefaultPassword() {
        return (String) map.get("defaultPassword");
    }

    public void setDefaultPassword(String defaultPassword) {
        map.put("defaultPassword", defaultPassword);
    }

    public String getDescription() {
        return (String) map.get("description");
    }

    public void setDescription(String description) {
        map.put("description", description);
    }

    public String getName() {
        return (String) map.get("name");
    }

    public void setName(String name) {
        map.put("name", name);
    }

    public boolean isEnabled() {
        return (Boolean) map.get("enabled");
    }

    public void setEnabled(boolean enabled) {
        map.put("enabled", enabled);
    }

    public void addLibrary(URL file) {
        ArrayList<URL> libraries = (ArrayList<URL>) map.get("libraries");
        if (libraries == null) {
            libraries = new ArrayList<URL>();
            map.put("libraries", libraries);
        }
        libraries.add(file);
//        invalidateClassLoader();
    }

//    public void invalidateClassLoader() {
//        driverClassLoader = null;
//        loadCache = null;
//    }
    public URL getLibrayAt(int index) {
        ArrayList<URL> libraries = (ArrayList<URL>) map.get("libraries");
        return libraries.get(index);
    }

    public URL[] getLibraries() {
        ArrayList<URL> libraries = (ArrayList<URL>) map.get("libraries");
        return libraries == null ? new URL[0] : libraries.toArray(new URL[libraries.size()]);
    }

    public void setLibraries(URL[] urls) {
        map.put("libraries", new ArrayList<URL>(Arrays.asList(urls == null ? new URL[0] : urls)));
//        invalidateClassLoader();
    }

    public int getLibraryCount() {
        ArrayList<URL> libraries = (ArrayList<URL>) map.get("libraries");
        return libraries == null ? 0 : libraries.size();
    }

    public void moveUpLibraryAt(int index) {
        ArrayList<URL> libraries = (ArrayList<URL>) map.get("libraries");
        if (index < libraries.size() - 1) {
            URL tmp = libraries.get(index + 1);
            libraries.set(index + 1, libraries.get(index));
            libraries.set(index, tmp);
//            invalidateClassLoader();
        }
    }

    public void moveDownLibraryAt(int index) {
        ArrayList<URL> libraries = (ArrayList<URL>) map.get("libraries");
        if (index > 1) {
            URL tmp = libraries.get(index - 1);
            libraries.set(index - 1, libraries.get(index));
            libraries.set(index, tmp);
//            invalidateClassLoader();
        }
    }

    public String getDriverClassName() {
        return (String) map.get("driverClassName");
    }

    public void setDriverClassName(String driverClassName) {
        map.put("driverClassName", driverClassName);
    }

    public String getBestName() {
        if (getName() == null || getName().length() == 0) {
            return getDriverClassName();
        }
        return getName();
    }

//    public synchronized URLClassLoader getDriverClassLoader() {
//        ArrayList<URL> libraries = (ArrayList<URL>) map.get("libraries");
//        if (driverClassLoader == null) {
//            ArrayList<URL> all = new ArrayList<URL>();
//            all.addAll(Arrays.asList(application.getDriverManager().getDefaultLibraries()));
//            if (libraries != null) {
//                all.addAll(libraries);
//            }
//            driverClassLoader = new URLClassLoader(all.toArray(new URL[all.size()]), getClass().getClassLoader());
//        }
//        return driverClassLoader;
//    }
    public String toString() {
        return getBestName();
    }
//    public void setApplication(DBCApplication dbClient) {
//        this.application = dbClient;
//    }
}
