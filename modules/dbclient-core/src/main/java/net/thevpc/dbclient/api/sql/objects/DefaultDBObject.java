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
package net.thevpc.dbclient.api.sql.objects;

import net.thevpc.dbclient.api.pluginmanager.DBCAbstractPluggable;
import net.thevpc.dbclient.api.sql.DBCConnection;
import net.thevpc.dbclient.api.sql.DBObjectFilter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.logging.Level;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 13 juil. 2005 14:31:48
 */
public abstract class DefaultDBObject extends DBCAbstractPluggable implements DBObject {

    private DBObject parentObject;
    private String catalog;
    private String schema;
    private String parent;
    private String id;
    private String name;
    private String nativeType;
    protected ArrayList<DBObject> children;
    boolean leaf;
    boolean systemObject;
    private boolean childrenLoaded;
    boolean childrenLoading;
    protected DBCConnection connection;
    protected final static Object synch = new Object();
    protected DBObjectFilter.Status status;

    public DefaultDBObject() {
    }

    public void init(DBCConnection cnx, String id, String catalog, String schema, String parent, String name, boolean leaf) {
        this.name = name;
        this.leaf = leaf;
        this.id = id;
        children = new ArrayList<DBObject>();
        childrenLoaded = false;
        childrenLoading = false;
        this.catalog = catalog;
        this.schema = schema != null && schema.length() == 0 ? null : schema;
        this.parent = parent;
//        log0("init");
        this.connection = cnx;
    }

    public boolean isLoaded() {
        return leaf || childrenLoaded;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public DBObject getParentObject() {
        return parentObject;
    }

    public DBObject getChild(int i) {
        ensureLoading();
        return children.get(i);
    }

    public int getChildCount() {
        ensureLoading();
        return children.size();
    }

    public int getIndexOfChild(DBObject child) {
        ensureLoading();
        return children.indexOf(child);
    }

    public boolean isLeaf() {
        return leaf;
    }

    public synchronized void addChild(DBObject node) {
        node.setParentObject(this);
        if (!node.getStatus().equals(DBObjectFilter.Status.REJECT)) {
            children.add(node);
        } else {
            node.setParentObject(null);
        }
    }

    public void setParentObject(DBObject node) {
        parentObject = node;
        if (node != null && node.isSystemObject()) {
            setSystemObject(true);
        }
    }

    protected void addChild(DBObject node, int pos) {
        node.setParentObject(this);
        children.add(pos, node);
    }

//    private synchronized void log0(String m) {
//        if (this instanceof Database) {
//            System.out.println("[" + m + "]:" + System.identityHashCode(this) + ":" + Thread.currentThread().getActionName() + ":childrenLoading = " + childrenLoading+ ":childrenLoaded = " + childrenLoaded);
//        }
//    }
    public void ensureLoading() {
        if (!childrenLoaded) {
            synchronized (synch) {
                if (!childrenLoaded && !childrenLoading) {
                    childrenLoading = true;
                    //            log0("begin");
                    try {
                        children.clear();
                        Collection<DBObject> objectChildren = getConnection().getObjectChildren(this);
                        if (objectChildren != null) {
                            for (DBObject objectChild : objectChildren) {
                                addChild(objectChild);
                            }
                        } else {
                            loadChildren();
                        }
                    } catch (Throwable e) {
                        connection.getLoggerProvider().getLogger(DefaultDBObject.class.getName()).log(Level.SEVERE, "ensureLoading failed", e);
                    } finally {
                        childrenLoading = false;
                    }
                    childrenLoaded = true;
                }
            }
        }
    }

    public void invalidate() {
        childrenLoaded = false;
    }

    public DBObject[] getChildrenArray() {
        ensureLoading();
        return children.toArray(new DBObject[children.size()]);
    }

    protected void loadChildren() throws SQLException {
    }

//    public boolean isEmpty() {
//        if (isLeaf() || !isLoaded()) {
//            return false;
//        }
//        for (DBObject aChildren : children) {
//            DBObject node = (DBObject) aChildren;
//            if (!node.isEmpty()) {
//                return false;
//            }
//        }
//        return true;
//    }
    public String getFullName() {
        String[] x = new String[]{catalog, schema, parent, name};
        StringBuilder sb = new StringBuilder();
        for (String s : x) {
            if (s != null && s.length() > 0) {
                if (sb.length() > 0) {
                    sb.append(".");
                }
                sb.append(s);
            }
        }
        return sb.toString();
    }

    public String toString() {
        return getFullName();
    }

    public String getSQL() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    public String getStringPath() {
        DBObject p = getParentObject();
        if (p == null) {
            return getName();
        }
        String pp = p.getStringPath();
        if (pp.equals("/")) {
            return pp + getName();
        } else {
            return pp + "/" + getName();
        }
    }

    public DBObject getChildAt(int childIndex) {
        return children.get(childIndex);
    }

//    public int getIndex(TreeNode node) {
//        if (!(node instanceof DBObject)) {
//            return -1;
//        }
//        return children.indexOf(node);
//    }
    public boolean getAllowsChildren() {
        return !isLeaf();
    }

    public Enumeration children() {
        return new Enumeration() {

            Iterator<DBObject> i = new ArrayList<DBObject>(children).iterator();

            public boolean hasMoreElements() {
                return i.hasNext();
            }

            public Object nextElement() {
                return i.next();
            }
        };
    }

    @Override
    public int hashCode() {
        int result;
        result = (parentObject != null ? parentObject.hashCode() : 0);
        result = 29 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DefaultDBObject other = (DefaultDBObject) obj;
        if (this.catalog != other.catalog && (this.catalog == null || !this.catalog.equals(other.catalog))) {
            return false;
        }
        if (this.schema != other.schema && (this.schema == null || !this.schema.equals(other.schema))) {
            return false;
        }
        if (this.name != other.name && (this.name == null || !this.name.equals(other.name))) {
            return false;
        }
        return true;
    }

    protected void setId(String id) {
        this.id = id;
    }

    public DBObject[] getTreePath() {
        ArrayList<DBObject> all = new ArrayList<DBObject>();
        DBObject i = this;
        while (i != null) {
            all.add(0, i);
            i = i.getParentObject();
        }
        return (all.toArray(new DBObject[all.size()]));
    }

//    public String getType() {
//        String v = getClass().getSimpleName();
//        if (v.endsWith("node")) {
//            v = v.substring(0, v.length() - 4);
//        }
//        return v.toLowerCase();
//    }
    public String getSchemaName() {
        return schema;
    }

    public String getCatalogName() {
        return catalog;
    }

    public final String getParentName() {
        return parent;
    }

    public DBCConnection getConnection() {
        return connection;
    }

    public void setConnection(DBCConnection dialect) {
        this.connection = dialect;
    }

    public boolean isSystemObject() {
        return systemObject;
    }

    public void setSystemObject(boolean systemObject) {
        this.systemObject = systemObject;
    }

    public String getNativeType() {
        return nativeType;
    }

    public void setNativeType(String nativeType) {
        this.nativeType = nativeType;
    }

    public DBObjectFilter.Status getStatus() {
        if (status == null) {
            status = getConnection().getObjectFilter().accept(this);
        }
        return status;
    }

    public void setStatus(DBObjectFilter.Status status) {
        this.status = status;
    }
}
