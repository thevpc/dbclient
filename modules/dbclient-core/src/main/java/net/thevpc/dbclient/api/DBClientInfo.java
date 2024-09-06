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
package net.thevpc.dbclient.api;

import net.thevpc.common.prs.Version;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 3 juil. 2006 09:56:14
 */
public class DBClientInfo {

    public static final DBClientInfo INSTANCE = new DBClientInfo();
    private static final String PRODUCT_NAME = "DBClient";
    private static final String PRODUCT_DESC = "Yet Another Jdbc Based Database Client";
    private static final String AUTHOR_NAME = "Taha BEN SALAH";
    private static final String AUTHOR_EMAIL = "taha.bensalah@gmail.com";
    private static final String PRODUCT_URL = "http://dbclient.java.net";
    private static final String CONFIG_VERSION = "0.5.0";
    private static final String PRODUCT_VERSION;
    private static final String PRODUCT_BUILD_DATE;

    static {
        Properties p = new Properties();
        InputStream in = null;
        String _product_version="Unknown";
        String _product_build_date="2011-05-09";
        try {
            try {
                in = DBClientInfo.class.getResource("dbclient-core.build").openStream();
                p.load(in);
                String s=p.getProperty("DBClient.Core.Version");
                if(s!=null && s.trim().length()>0){
                    _product_version=s.trim();
                }
                s=p.getProperty("DBClient.Core.Date");
                if(s!=null && s.trim().length()>0){
                    _product_build_date=s.trim();
                }
            } finally {
                if (in != null) {
                    in.close();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        PRODUCT_VERSION=_product_version;
        PRODUCT_BUILD_DATE = _product_build_date;

    }
    private static final Version _PRODUCT_VERSION = new Version(PRODUCT_VERSION);
    private static final Version _PRODUCT_MAJOR_VERSION = _PRODUCT_VERSION.head(2);
    private static final Version _CONFIG_VERSION = new Version(CONFIG_VERSION);

    private DBClientInfo() {
    }

    public String getProductName() {
        return PRODUCT_NAME;
    }

    public String getProductDescription() {
        return PRODUCT_DESC;
    }

    public Version getConfigurationVersion() {
        return _CONFIG_VERSION;
    }

    public Version getProductVersion() {
        return _PRODUCT_VERSION;
    }

    public Version getProductMajorVersion() {
        return _PRODUCT_MAJOR_VERSION;
    }

    public String getProductBuildDate() {
        return PRODUCT_BUILD_DATE;
    }

    public String getAuthorName() {
        return AUTHOR_NAME;
    }

    public String getAuthorEmail() {
        return AUTHOR_EMAIL;
    }

    public String getProductURL() {
        return PRODUCT_URL;
    }

    public String getPrimaryRepositoryURL() {
        return System.getProperty("primary-repository", getProductURL() + "/plugins-repository/" + getProductMajorVersion());
    }

    public String getProductLongTitle() {
        return getProductName() + " v" + getProductVersion();
    }
}
