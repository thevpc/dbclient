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

package net.vpc.dbclient.plugin.sql.jstsql.evaluator;

import net.vpc.prs.classloader.MultiClassLoader;

import java.io.File;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 16 aout 2006 00:08:31
 */
public class JSTSqlTemplateCache implements Serializable {
    private static final long serialVersionUID = -1010101010101000001L;
    private String id;
    private String cache;
    private Date date;
    private byte[] classBytes;
    private transient Class implClass;
    private String classPath;

    public JSTSqlTemplateCache(String id, String cache, Date date, String classPath, byte[] classBytes) {
        this.cache = cache;
        this.id = id;
        this.date = date;
        this.classPath = classPath;
        this.classBytes = classBytes;
    }


    public String getClassName() {
        return cache;
    }

    public String getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    private ClassLoader createClassLoader(JSTSqlEvalContext context) {
        ArrayList<URL> list = new ArrayList<URL>();
        String[] paths = classPath.split(System.getProperty("path.separator"));
        for (String path : paths) {
            try {
                list.add(new File(path).toURI().toURL());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        HashSet<ClassLoader> loaders = new HashSet<ClassLoader>();
        for (JSTSqlEvalContext.Var var : context.getVars()) {
            Object o = var.getValue();
            if (o != null) {
                loaders.add(o.getClass().getClassLoader());
            }
        }
        ArrayList<ClassLoader> clist = new ArrayList<ClassLoader>(loaders);
        clist.add(new URLClassLoader(list.toArray(new URL[list.size()]), getClass().getClassLoader()));
        MultiClassLoader mcl = new MultiClassLoader(getClass().getClassLoader(), clist.toArray(new ClassLoader[clist.size()]));
        return mcl;
        //return new URLClassLoader(list.toArray(new URL[list.size()]),getClass().getClassLoader());
    }

    public Class getImplClassNoLoad() {
        return implClass;
    }

    public Class getImplClass(JSTSqlEvalContext context) {
        if (implClass == null) {
            ClassLoader ucl = createClassLoader(context);
            JSTSqlTemplateCacheClassLoader loader = new JSTSqlTemplateCacheClassLoader(ucl, this, context);
            try {
                implClass = loader.loadClass(getClassName());
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        return implClass;
    }

    public void setImplClass(Class implClass) {
        this.implClass = implClass;
    }

    public byte[] getClassBytes() {
        return classBytes;
    }

    public void setClassBytes(byte[] classBytes) {
        this.classBytes = classBytes;
    }
}
