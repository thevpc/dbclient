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

package net.thevpc.dbclient.api.encryptionmanager;

import net.thevpc.dbclient.api.pluginmanager.DBCPluggable;
import net.thevpc.common.prs.plugin.Extension;

/**
 * Encryption algo is used to encrypt/decrypt passwords stored in the config
 *
 * @author vpc
 */
@Extension(group = "manager")
public interface DBCEncryptionAlgorithm extends DBCPluggable {

    /**
     * @return unique Name of the Algo
     */
    public String getName();

    /**
     * encrypt str. resulting String should not contain exotic chars.
     * using a Base64 as envelop is recommanded.
     * It is garanteed that str!=null
     *
     * @param str string to encrypt
     * @return cypher text
     */
    public String encrypt(String str);

    /**
     * decrypt str.
     * It is garanteed that str!=null
     *
     * @param str cypher text
     * @return plain text
     */
    public String decrypt(String str);
}
