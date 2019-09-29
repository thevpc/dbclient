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
package net.vpc.dbclient.plugin.system.encryptionmanager;

import net.vpc.dbclient.api.DBCApplication;
import net.vpc.dbclient.api.encryptionmanager.DBCEncryptionAlgorithm;
import net.vpc.dbclient.api.pluginmanager.DBCAbstractPluggable;
import net.vpc.prs.plugin.Inject;
import net.vpc.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.net.URL;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

/**
 * @author vpc
 */
public class DBCEncryptionDESBase64AlgorithmImpl extends DBCAbstractPluggable implements DBCEncryptionAlgorithm {

    @Inject
    private DBCApplication app;
    private AlgorithmParameterSpec paramSpec = new IvParameterSpec(new byte[]{
            (byte) 0x8E, 0x12, 0x39, (byte) 0x9C,
            0x07, 0x72, 0x6F, 0x5A
    });

    public DBCEncryptionDESBase64AlgorithmImpl() {

    }

    public Key getKey() {
        File confDir = app == null ? new File(System.getProperty("user.home") + "/.java-apps") : app.getConfigDir();
        File keyFile = new File(confDir, ".key");
        Key key = null;
        URL url = null;
        if (keyFile.exists()) {
            try {
                url = keyFile.toURI().toURL();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (url == null) {
            url = getClass().getResource("DBCEncryptionDESBase64Algorithm.key");
        }
        if (url == null && !keyFile.exists()) {
            try {
                confDir.mkdirs();
                key = KeyGenerator.getInstance("DES").generateKey();
                ObjectOutputStream s = new ObjectOutputStream(new FileOutputStream(keyFile));
                s.writeObject(key);
                s.flush();
                s.close();
            } catch (Exception exc) {
                throw new RuntimeException(exc);
            }
        }
        if (key == null && url != null) {
            try {
                ObjectInputStream s = new ObjectInputStream(url.openStream());
                key = (Key) s.readObject();
                s.close();
            } catch (Exception exc) {
                throw new RuntimeException(exc);
            }
        }
        return key;
    }

    public String decrypt(String str) {
        Cipher ecipher = null;
        try {
            ecipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            ecipher.init(Cipher.DECRYPT_MODE, getKey(), paramSpec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            byte[] b = Base64.decode(str);
            ByteArrayInputStream baos = new ByteArrayInputStream(b);
            CipherInputStream cos = new CipherInputStream(baos, ecipher);
            byte[] ok = null;
            while (true) {
                byte[] bf = new byte[1024];
                int k = cos.read(bf);
                if (k > 0) {
                    if (ok == null) {
                        ok = new byte[k];
                        System.arraycopy(bf, 0, ok, 0, k);
                    } else {
                        byte[] ok2 = new byte[ok.length + k];
                        System.arraycopy(ok, 0, ok2, 0, ok.length);
                        System.arraycopy(bf, 0, ok2, ok.length, k);
                        ok = ok2;
                    }
                } else {
                    break;
                }
            }
            cos.close();
            return ok == null ? "" : new String(ok);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String encrypt(String str) {
        Cipher ecipher = null;
        try {
            ecipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            ecipher.init(Cipher.ENCRYPT_MODE, getKey(), paramSpec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            CipherOutputStream cos = new CipherOutputStream(baos, ecipher);
            cos.write(str.getBytes());
            cos.close();
            baos.flush();
            byte[] bb = baos.toByteArray();
            return Base64.encode(bb);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getName() {
        return "des/b64";
    }

}
