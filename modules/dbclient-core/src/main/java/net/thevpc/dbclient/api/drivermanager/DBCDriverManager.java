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

package net.thevpc.dbclient.api.drivermanager;

import net.thevpc.common.prs.Version;
import net.thevpc.dbclient.api.configmanager.DBCDriverInfo;
import net.thevpc.dbclient.api.pluginmanager.DBCPluggable;
import net.thevpc.common.prs.plugin.Extension;
import net.thevpc.dbclient.api.sql.urlparser.DBCDriverUrlParser;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 15 nov. 2006 23:17:35
 */
@Extension(group = "manager")
public interface DBCDriverManager extends DBCPluggable {
    /**
     * URL of Default libraries.
     * <p/>
     * Actually it corresponds to the Folder containing All JDBC jar files.
     * It defaults to <pre>new File(DBClient.getWorkingDir(),"jdbcdrivers")</pre>
     * <p/>
     * It is recommanded that URL corresponds to a Local Directory.
     * In that case, The directory will be parsed recursively, and all jar/zip files
     * will be added as Jdbc libraries.
     *
     * @return URL of Default libraries
     */
    public String getDefaultLibraryRepository();

    /**
     * URL of Default libraries.
     * <p/>
     * Actually it corresponds to the Folder containing All JDBC jar files.
     * It defaults to <pre>new File(DBClient.getWorkingDir(),"jdbcdrivers")</pre>
     * It is recommanded that URL corresponds to a Local Directory.
     * In that case, The directory will be parsed recursively, and all jar/zip files
     * will be added as Jdbc libraries.
     *
     * @param newRepository
     */
    public void setDefaultLibraryRepository(String newRepository);

    /**
     * Drivers registred (in ApplicationConfig)
     *
     * @return All Drivers
     */
    public DBCDriverInfo[] getDrivers();


    /**
     * add new Driver information to the ApplicationConfig
     *
     * @param desc Driver info
     */
    public void addDriver(DBCDriverInfo desc);

    /**
     * updates existing Driver information to the ApplicationConfig.
     * <p/>
     * info must contain at list the id and another property to update.
     * Only specified properties will be updated (Light Weight DTO design Pattern used).
     *
     * @param desc Driver info
     */
    public void updateDriver(DBCDriverInfo desc);

    /**
     * remove definitively Driver by Id
     *
     * @param driverId id to remove
     */
    public void removeDriver(int driverId);

    /**
     * create Connection Instance.
     * Connection created should never be used directly but wrapped in the DBCConnection object.
     * So do never call this directly, it is used in the <pre>DBCConnection DBCSession.createConnection()</pre>.
     * Use instead <pre>DBCConnection DBCSession.createConnection()</pre> method.
     * this method is also responsible for calling <pre>DBClient.rewriteString(url)</pre>
     *
     * @param url         jdbc url (may contain variables)
     * @param driverClass
     * @param user
     * @param password
     * @param properties
     * @return
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     *
     */
    public Connection getConnection(String url, String driverClass, String user, String password, Properties properties) throws SQLException, ClassNotFoundException;

    /**
     * List of AllLivraries found in Default Libraries Repository
     *
     * @return List of AllLivraries found in Default Libraries Repository
     */
    public DBCDriverLibrary getDefaultLibrary();

    /**
     * add library
     *
     * @param name library name, should not already been registered
     * @param resources library URLs to jar files
     * @param version library version, may be null
     */
    public void addLibrary(String name, List<URL> resources, Version version);
    /**
     * remove library named <code>name</code>
     *
     * @param name for name
     */
    public void removeLibrary(String name);

    /**
     * add DriverManager Listener
     *
     * @param listener new listener
     */
    public void addListener(DBCDriverManagerListener listener);

    /**
     * remove DriverManager Listener
     *
     * @param listener listener to remove
     */
    public void removeListener(DBCDriverManagerListener listener);

    /**
     * Driver Parser for the specified driver
     *
     * @param driver driver to parse
     * @return Driver Parser
     * @throws java.util.NoSuchElementException
     *
     */
    public DBCDriverUrlParser getDriverUrlParser(String driver) throws NoSuchElementException;

    /**
     * Add new Driver URL parser.
     * shall be called in the following method :
     * <pre>
     * public void DBCPlugin.applicationOpening();
     * </pre>
     *
     * @param parser
     */
    public void addDriverUrlParser(DBCDriverUrlParser parser);

    /**
     * remove the specified parser
     *
     * @param parser parser to remove
     */
    public void removeDriverUrlParser(DBCDriverUrlParser parser);

    /**
     * All Driver URL parsers
     *
     * @return All Driver URL parsers
     */
    public Collection<DBCDriverUrlParser> getDriverUrlParsers();

    boolean isLoadableDriver(String driverClassName);

}
