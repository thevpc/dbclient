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

package net.vpc.dbclient.plugin.system.sql.objects;

import net.vpc.dbclient.api.sql.DBCConnection;
import net.vpc.dbclient.api.sql.objects.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeSet;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 13 juil. 2005 14:30:59
 */
public class DBSchemaImpl extends DefaultDBObject implements DBSchema {
    private boolean isDefault;
    private String schemaTerm;

    public void init(DBCConnection session, ResultSet rs, String catalogName) throws SQLException {
        this.init(session, rs.getString(1),
                rs.getMetaData().getColumnCount() == 2 ? rs.getString(2)
                        : catalogName //workaround for sqlserver
        );
    }

    public void init(DBCConnection session, String name, String catalogName) throws SQLException {
        init(session, "Tree.Schema", catalogName, null, null, name, false);
        String defaultSchema = getConnection().getDefaultSchema();
        try {
            schemaTerm = getConnection().getMetaData().getSchemaTerm();
            if (schemaTerm == null) {
                schemaTerm = "schema";
            }
        } catch (SQLException e) {
            schemaTerm = "schema";
            //
        }
        if ("".equals(defaultSchema)) {
            defaultSchema = null;
        }
        if ("".equals(name)) {
            name = null;
        }
        isDefault = (name == defaultSchema) || (defaultSchema != null && defaultSchema.equalsIgnoreCase(name));
    }

    public DBCatalog getCatalog() {
        return (DBCatalog) getParentObject();
    }

    @Override
    protected void loadChildren() throws SQLException {
        ResultSet rs = getConnection().getMetaData().getTableTypes();
        boolean seqAdded = false;
//        boolean indexesAdded = false;
        DBCConnection cnx = getConnection();
        TreeSet<String> seenTypes = new TreeSet<String>();
        while (rs.next()) {
            String nativeName = rs.getString(1);
            //timmed because some drivers add some trailing spaces!
            if (nativeName == null) {
                nativeName = "";
            }
            nativeName = nativeName.trim();

            if (!seenTypes.contains(nativeName)) {
                seenTypes.add(nativeName);
                SQLObjectTypes sqlObjectTypes = cnx.getTypeByNativeName(nativeName);
                if (sqlObjectTypes == SQLObjectTypes.TABLE || sqlObjectTypes == SQLObjectTypes.VIEW) {
                    DBTableFolder folder = cnx.getFactory().newInstance(DBTableFolder.class);
                    folder.init(cnx, nativeName, getCatalogName(), getName());
                    addChild(folder);
                } else if (sqlObjectTypes == SQLObjectTypes.TABLE_INDEX) {
                    //ignore
//                    DBIndexFolderAsTable folder = cnx.getFactory().newInstance(DBIndexFolderAsTable.class);
//                    folder.init(cnx, nativeName, getCatalogName(), getName());
//                    addChild(folder);
//                    indexesAdded = true;
                } else if (sqlObjectTypes == SQLObjectTypes.SEQUENCE) {
                    DBSequenceFolderAsTable folder = cnx.getFactory().newInstance(DBSequenceFolderAsTable.class);
                    folder.init(cnx, rs, getCatalogName(), getName());
                    addChild(folder);
                    seqAdded = true;
                }
            }
        }
        rs.close();
        if (!seqAdded) {
            if (cnx.acceptObjectType(SQLObjectTypes.SEQUENCE)) {
                DBSequenceFolderAsTable folder = cnx.getFactory().newInstance(DBSequenceFolderAsTable.class);
                folder.init(cnx, "SEQUENCE", getCatalogName(), getName());
                addChild(folder);
            }
        }
//        if (!indexesAdded) {
//            if (cnx.acceptObjectType(SQLObjectTypes.TABLE_INDEX)) {
//                DBIndexFolder folder = cnx.getFactory().newInstance(DBIndexFolder.class);
//                folder.init(cnx, "INDEX", getCatalogName(), getName());
//                addChild(folder);
//            }
//        }

        if (cnx.acceptObjectType(SQLObjectTypes.PROCEDURE)) {
            DBProcedureFolder folder = cnx.getFactory().newInstance(DBProcedureFolder.class);
            folder.init(cnx, getCatalogName(), getName());
            addChild(folder);
        }

        if (cnx.acceptObjectType(SQLObjectTypes.PACKAGE)) {
            DBPackageFolder folder = cnx.getFactory().newInstance(DBPackageFolder.class);
            folder.init(cnx, getCatalogName(), getName());
            addChild(folder);
        }
        if (cnx.acceptObjectType(SQLObjectTypes.QUEUE)) {
            DBQueueFolder folder = cnx.getFactory().newInstance(DBQueueFolder.class);
            folder.init(cnx, getCatalogName(), getName());
            addChild(folder);
        }
        if (cnx.acceptObjectType(SQLObjectTypes.CLUSTER)) {
            DBClusterFolder folder = cnx.getFactory().newInstance(DBClusterFolder.class);
            folder.init(cnx, getCatalogName(), getName());
            addChild(folder);
        }
        if (cnx.acceptObjectType(SQLObjectTypes.JOB)) {
            DBJobFolder folder = cnx.getFactory().newInstance(DBJobFolder.class);
            folder.init(cnx, getCatalogName(), getName());
            addChild(folder);
        }
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public String getSchemaTerm() {
        return schemaTerm;
    }

    @Override
    public String getSchemaName() {
        return getName();
    }

    public SQLObjectTypes getType() {
        return SQLObjectTypes.SCHEMA;
    }

    public String getDropSQL() {
        return getFullName();
    }

}
