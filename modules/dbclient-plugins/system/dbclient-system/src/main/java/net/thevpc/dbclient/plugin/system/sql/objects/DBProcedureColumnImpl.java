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

package net.thevpc.dbclient.plugin.system.sql.objects;

import net.thevpc.dbclient.api.sql.objects.DBObject;
import net.thevpc.dbclient.api.sql.objects.DBProcedure;
import net.thevpc.dbclient.api.sql.objects.DBProcedureColumn;
import net.thevpc.dbclient.api.sql.objects.SQLObjectTypes;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 4 août 2005 14:54:07
 */
public class DBProcedureColumnImpl extends DBObjectFolderImpl implements DBProcedureColumn {
    private int sqlType;
    private int precision;
    private int size;
    private boolean nullable;
    public DBProcedure procedure;

    public void init(DBProcedure dbTableInfos, String catalog, String schema, String procedure, String name, int sqlType, boolean nullable) {
        super.init(dbTableInfos.getConnection(), "Tree.TableColumn", catalog, schema, procedure, name, true);
        this.procedure = dbTableInfos;
        this.setSqlType(sqlType);
        this.setNullable(nullable);
    }

    @Override
    public String getProcedureName() {
        return getParentName();
    }

    @Override
    public String getColumnName() {
        return getName();
    }

    
    
    public DBProcedure getProcedure() {
        return procedure;
    }
//    public Icon getIcon() {
//        String icon = null;
//        switch (sqlType) {
//            case Types.BIGINT: {
//                icon = "TypeNumber";
//                break;
//            }
//            case Types.BOOLEAN:
//            case Types.BIT: {
//                icon = "TypeBoolean";
//                break;
//            }
//
//            case Types.DECIMAL:
//            case Types.DOUBLE:
//            case Types.FLOAT:
//            case Types.REAL: {
//                icon = "TypeNumber";
//                break;
//            }
//
//            case Types.NUMERIC: {
//                if (precision > 0) {
//                    icon = "TypeNumber";
//                } else {
//                    icon = "TypeNumber";
//                }
//                break;
//            }
//            case Types.DATE: {
//                icon = "TypeDate";
//                break;
//            }
//            case Types.TIME: {
//                icon = "TypeTime";
//                break;
//            }
//            case Types.TIMESTAMP: {
//                icon = "TypeTimestamp";
//                break;
//            }
//
//            case Types.TINYINT:
//            case Types.SMALLINT:
//            case Types.INTEGER: {
//                icon = "TypeNumber";
//                break;
//            }
//
//            case Types.VARBINARY:
//            case Types.LONGVARBINARY:
//            case Types.BINARY:
//            case Types.LONGVARCHAR:
//            case Types.CHAR:
//            case Types.VARCHAR: {
//                icon = "TypeVarchar";
//                break;
//            }
//
//            case Types.ARRAY:
//            case Types.STRUCT:
//            case Types.NULL:
//            case Types.OTHER:
//            case Types.REF:
//            case Types.JAVA_OBJECT:
//            case Types.BLOB:
//            case Types.CLOB:
//            case Types.DISTINCT:
//            case Types.DATALINK:
//            default: {
//                icon = "TypeError";
//                break;
//            }
//        }
//        return SQLUtils.loadImageIcon("/images/net/vpc/dbclient/" + icon + ".gif");
//    }


    public boolean isNullable() {
        return nullable;
    }

    public int getSqlType() {
        return sqlType;
    }

    public int getPrecision() {
        return precision;
    }

    public int getSize() {
        return size;
    }

    @Override
    public DBObject getChild(int i) {
        return null;
    }

    @Override
    public int getChildCount() {
        return 0;
    }

    @Override
    public int getIndexOfChild(DBObject obj) {
        return 0;
    }

    @Override
    public boolean isLeaf() {
        return true;
    }

    public String getSQL(String prototype) {
        return getName();
    }

    @Override
    public String getSQL() {
        return getName();
    }

    @Override
    public boolean isLoaded() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final DBProcedureColumn fieldNode = (DBProcedureColumn) o;

        if (isNullable() != fieldNode.isNullable()) {
            return false;
        }
        if (getPrecision() != fieldNode.getPrecision()) {
            return false;
        }
        if (getSize() != fieldNode.getSize()) {
            return false;
        }
        if (getSqlType() != fieldNode.getSqlType()) {
            return false;
        }
        if (procedure != null ? !procedure.equals(fieldNode.getProcedure()) : fieldNode.getProcedure() != null) {
            return false;
        }
        if (getName() != null ? !getName().equals(fieldNode.getName()) : fieldNode.getName() != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 29 * result + (procedure != null ? procedure.hashCode() : 0);
        return result;
    }

    public void setSqlType(int sqlType) {
        this.sqlType = sqlType;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }


    public SQLObjectTypes getType() {
        return SQLObjectTypes.PROCEDURE_COLUMN;
    }

    public String getDropSQL() {
        return getName();
    }
}
