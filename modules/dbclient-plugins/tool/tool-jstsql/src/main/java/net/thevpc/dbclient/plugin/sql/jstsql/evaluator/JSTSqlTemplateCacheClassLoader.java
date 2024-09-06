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
package net.thevpc.dbclient.plugin.sql.jstsql.evaluator;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 10 avr. 2007 18:20:43
 */
public class JSTSqlTemplateCacheClassLoader extends ClassLoader {
    private JSTSqlTemplateCache templateCache;
    private JSTSqlEvalContext context;

    public JSTSqlTemplateCacheClassLoader(ClassLoader parent, JSTSqlTemplateCache templateCache, JSTSqlEvalContext context) {
        super(parent);
        this.templateCache = templateCache;
        this.context = context;
    }

    public Class findClass(String name) throws ClassNotFoundException {
        if (templateCache.getClassName().equals(name)) {
            if (templateCache.getImplClassNoLoad() == null) {
                byte[] b = templateCache.getClassBytes();
                templateCache.setImplClass(defineClass(name, b, 0, b.length));
            }
            return templateCache.getImplClass(context);
        }
        throw new ClassNotFoundException(name);
    }
}
