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
package net.thevpc.dbclient.plugin.system.encryptionmanager;

import net.thevpc.dbclient.api.DBCApplication;
import net.thevpc.dbclient.api.encryptionmanager.DBCEncryptionAlgorithm;
import net.thevpc.dbclient.api.encryptionmanager.DBCEncryptionManager;
import net.thevpc.dbclient.api.pluginmanager.DBCAbstractPluggable;
import net.thevpc.common.prs.factory.ExtensionDescriptor;
import net.thevpc.common.prs.factory.ImplementationDescriptor;
import net.thevpc.common.prs.plugin.Inject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author vpc
 */
public class DBCEncryptionManagerImpl extends DBCAbstractPluggable implements DBCEncryptionManager {

    @Inject
    private DBCApplication application;
    private Map<Class, DBCEncryptionAlgorithm> algorithms = new HashMap<Class, DBCEncryptionAlgorithm>();

    public String decrypt(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        ExtensionDescriptor<DBCEncryptionAlgorithm> conf = application.getFactory().getExtension(DBCEncryptionAlgorithm.class);
        for (ImplementationDescriptor<DBCEncryptionAlgorithm> componentImplementation : conf.getImplementations()) {
            if (componentImplementation != null) {
                DBCEncryptionAlgorithm al = getCachedInstance(componentImplementation);
                if (al != null && str.startsWith(al.getName() + ":")) {
                    return al.decrypt(str.substring(al.getName().length() + 1));
                }
            }
        }
        throw new SecurityException("Unable to decrypt " + str);
    }

    public DBCEncryptionAlgorithm[] getAlgorithms() {
        ArrayList<DBCEncryptionAlgorithm> all = new ArrayList<DBCEncryptionAlgorithm>();
        ExtensionDescriptor<DBCEncryptionAlgorithm> conf = application.getFactory().getExtension(DBCEncryptionAlgorithm.class);
        for (ImplementationDescriptor<DBCEncryptionAlgorithm> componentImplementation : conf.getImplementations()) {
            if (componentImplementation != null) {
                DBCEncryptionAlgorithm al = getCachedInstance(componentImplementation);
                if (al != null) {
                    all.add(al);
                }
            }
        }
        return all.toArray(new DBCEncryptionAlgorithm[all.size()]);
    }

    private DBCEncryptionAlgorithm getCachedInstance(ImplementationDescriptor<DBCEncryptionAlgorithm> clz) {
        if (clz != null) {
            DBCEncryptionAlgorithm al = algorithms.get(clz.getImplementationType());
            if (al == null) {
                try {
                    al = (DBCEncryptionAlgorithm) application.getFactory().instantiate(clz.getImplementationType(),clz.getOwner());
                    algorithms.put(clz.getImplementationType(), al);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
            return al;
        }
        return null;
    }

    public String encrypt(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        DBCEncryptionAlgorithm a = getCachedInstance(application.getFactory().getExtension(DBCEncryptionAlgorithm.class).getValidImpl());
        if (a != null) {
            return a.getName() + ":" + a.encrypt(str);
        }
        throw new SecurityException("Unale to encrypt " + str);
    }

}
