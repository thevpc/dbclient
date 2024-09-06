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

package net.thevpc.dbclient.api.sessionmanager;

import net.thevpc.common.prs.plugin.Extension;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 1 juil. 2007 00:38:52
 */
@Extension
public interface DBCURLBuilder {

    /**
     * show a dialog of selected in a better way a url (showing all possible params)
     *
     * @param chooser chooser instance
     * @param driver  driver to build url for
     * @param oldURL  old value of URL if any, may be null
     * @return new value of URL or null if cancel
     */
    String buildURL(DBCSessionListEditor chooser, String driver, String oldURL);

    /**
     * returns <0 if this builder does not support the given driver
     * returns >=0 if this builder does support the given driver
     * When more than one Builder is supprting the same driver, he one returning
     * the greater value will be used.
     *
     * @param chooser instance
     * @param driver  driver to support or not
     * @return <0 if not accepted
     */
    int accept(DBCSessionListEditor chooser, String driver);
}
