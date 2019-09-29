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

package net.vpc.dbclient.plugin.system.actionmanager.treeactions;

import net.vpc.dbclient.api.actionmanager.DBCActionLocation;
import net.vpc.dbclient.api.actionmanager.DBCResultTableAction;
import net.vpc.dbclient.api.actionmanager.DBCTreeNodeAction;
import net.vpc.dbclient.api.sessionmanager.*;
import net.vpc.dbclient.api.sql.TypeDesc;
import net.vpc.dbclient.api.sql.TypeWrapperFactory;
import net.vpc.dbclient.api.sql.objects.DBDatatypeFolder;
import net.vpc.dbclient.api.sql.objects.DBObject;
import net.vpc.dbclient.api.sql.util.SQLUtils;
import net.vpc.dbclient.plugin.system.SystemUtils;

import java.awt.event.ActionEvent;
import java.sql.DatabaseMetaData;
import java.sql.Types;

public class DatatypesPropertiesTreeNodeAction extends DBCTreeNodeAction {
    public DatatypesPropertiesTreeNodeAction() {
        super("Action.DatatypesPropertiesNodeAction");
        addLocationPath(DBCActionLocation.POPUP, "/");
    }

    public boolean shouldEnable(DBObject activeNode, DBObject[] selectedNodes) {
        return (activeNode != null && activeNode instanceof DBDatatypeFolder);
    }

    public void actionPerformedImpl(ActionEvent e) throws Throwable {
        DBCResultTable table = getSession().getFactory().newInstance(DBCResultTable.class);
        table.getModel().setTitle("TypeInfo");
        final TypeWrapperFactory twf = getPluginSession().getSession().getConnection().getTypeWrapperFactory();
        final DBCResultTableWrapper w = new DBCResultTableWrapper() {
            @Override
            public TypeDesc wrapColumnClass(TypeDesc clz, int column) {
                switch (column) {
                    case 2: {
                        return twf.getTypeDesc(Types.VARCHAR);
                    }
                    case 7: {
                        return twf.getTypeDesc(Types.BIT);
                    }
                    case 9: {
                        return twf.getTypeDesc(Types.VARCHAR);
                    }
                }
                return super.wrapColumnClass(clz, column);
            }

            @Override
            public Object wrapCellValue(Object value, int row, int column) {
                switch (column) {
                    case 2: {
                        Integer i = (Integer) value;
                        return SQLUtils.getSqlTypeName(i == null ? -999 : i,getSession());
                    }
                    case 7: {
                        Integer i = (Integer) value;
                        if (i == null) {
                            return null;
                        } else if (i == DatabaseMetaData.typeNoNulls) {
                            return Boolean.FALSE;
                        } else if (i == DatabaseMetaData.typeNullable) {
                            return Boolean.TRUE;
                        } else {
                            return null;
                        }
                    }
                    case 9: {
                        Integer i = (Integer) value;
                        if (i == null) {
                            return null;
                        } else if (i == DatabaseMetaData.typePredNone) {
                            return "No support";
                        } else if (i == DatabaseMetaData.typePredChar) {
                            return "Only supported with WHERE .. LIKE";
                        } else if (i == DatabaseMetaData.typePredBasic) {
                            return "Supported except for WHERE .. LIKE";
                        } else if (i == DatabaseMetaData.typeSearchable) {
                            return "Supported for all WHERE ..";
                        } else {
                            return "?";
                        }
                    }
                }
                return super.wrapCellValue(value, row, column);
            }
        };

        table.setRefreshAction(new DBCResultTableAction("Refresh", getPluginSession()) {
            @Override
            public void actionPerformedImpl(ActionEvent e) throws Throwable {
                DBCTableModel model = getResultTable().getModel();
                model.displayQuery(getSession().getConnection().getMetaData().getTypeInfo(), w);
            }
        });
        table.refresh();

        DBCInternalWindow window = getSession().getView().addWindow(table, DBCSessionView.Side.Workspace, true);
        SystemUtils.configureResultTableWindow(window, this);
    }
}
