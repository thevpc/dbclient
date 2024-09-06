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

package net.thevpc.dbclient.plugin.dbsupport.postgresql;

import net.thevpc.dbclient.api.sql.features.GenerateSQLRenameFeature;
import net.thevpc.dbclient.api.sql.objects.SQLObjectTypes;
import net.thevpc.dbclient.api.sql.parser.SQLParser;
import net.thevpc.dbclient.plugin.dbsupport.postgresql.features.PostgreSqlGenerateSQLRenameFeature;
import net.thevpc.dbclient.plugin.system.sql.DBCAbstractConnection;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 19 juil. 2006 16:12:46
 */
public class PostgreSqlConnection extends DBCAbstractConnection {
    public PostgreSqlConnection() {
        super();
    }

    public int getConnectionSupportLevel(Connection connection) throws SQLException {
        return PostgreSqlPlugin.getConnectionSupportLevel0(connection);
    }

    @Override
    public boolean isSQLCreateObjectSupported(SQLObjectTypes objectType) throws SQLException {
        return
                SQLObjectTypes.TABLE.equals(objectType)
                        || SQLObjectTypes.VIEW.equals(objectType)
                        || SQLObjectTypes.TABLE_INDEX.equals(objectType)
//                || SQLObjectTypes.TRIGGER.equals(objectType)
                ;
    }


    @Override
    public AutoIdentityType getAutoIdentityType() throws SQLException {
        return AutoIdentityType.SEQUENCE;
    }

    public String getNativeSQLCreateDatabase(String name, String owner, String template, String encoding, String tableSpace, int connectionLimit) {
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE DATABASE ").append(name);
        boolean with = false;


        if (owner != null && owner.length() > 0) {
            if (!with) {
                with = true;
                sql.append(" WITH ");
            }
            sql.append(" OWNER=").append(owner);
        }

        if (template != null && template.length() > 0) {
            if (!with) {
                with = true;
                sql.append(" WITH ");
            }
            sql.append(" TEMPLATE=").append(template);
        }
        if (tableSpace != null && tableSpace.length() > 0) {
            if (!with) {
                with = true;
                sql.append(" WITH ");
            }
            sql.append(" TABLESPACE=").append(tableSpace);
        }
        if (connectionLimit > 0) {
            if (!with) {
                with = true;
                sql.append(" WITH ");
            }
            sql.append(" CONNECTION LIMIT=").append(connectionLimit);
        }
        return sql.toString();
    }

    @Override
    protected SQLParser createDefaultParser() {
        return new PostgreSqlParser();
    }

    PostgreSqlGenerateSQLRenameFeature generateSQLRenameFeature = new PostgreSqlGenerateSQLRenameFeature();

    @Override
    public GenerateSQLRenameFeature getFeatureGenerateSQLRename() throws SQLException {
        return generateSQLRenameFeature;
    }
    //    public String getSQLUse(String catalogName, String schemaName) throws SQLException {
//        return "SET SCHEMA "+schemaName;
//    }

}
