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

package net.vpc.dbclient.plugin.system.sessionmanager.sqleditor;

import net.vpc.dbclient.api.sessionmanager.DBCAutoCompleteInfo;
import net.vpc.dbclient.api.sql.objects.DBObject;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 26 aout 2006 11:49:21
 */
public class DBCAutoCompleteInfoImpl implements DBCAutoCompleteInfo {
    private String title;
    private String wordToReplace;
    private int index;
    private DBObject object;
    private String value;

    public DBCAutoCompleteInfoImpl(int index, String value, String title, String wordToReplace, DBObject object) {
        this.object = object;
        this.index = index;
        this.value = value;
        this.title = title;
        this.wordToReplace = wordToReplace;
    }

    public int getIndex() {
        return index;
    }

    public String getTitle() {
        return title;
    }

    public String getValue() {
        return value;
    }

    public String getWordToReplace() {
        return wordToReplace;
    }

    public String toString() {
        return title;
    }

    public DBObject getObject() {
        return object;
    }
}
