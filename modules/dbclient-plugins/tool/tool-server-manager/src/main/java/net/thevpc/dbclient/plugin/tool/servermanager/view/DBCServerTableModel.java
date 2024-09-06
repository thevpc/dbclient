///**
// * ====================================================================
// *             DBClient yet another Jdbc client tool
// *
// * DBClient is a new Open Source Tool for connecting to jdbc
// * compliant relational databases. Specific extensions will take care of
// * each RDBMS implementation.
// *
// * Copyright (C) 2006-2008 Taha BEN SALAH
// *
// * This program is free software; you can redistribute it and/or modify
// * it under the terms of the GNU General Public License as published by
// * the Free Software Foundation; either version 2 of the License, or
// * (at your option) any later version.
// *
// * This program is distributed in the hope that it will be useful,
// * but WITHOUT ANY WARRANTY; without even the implied warranty of
// * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// * GNU General Public License for more details.
// *
// * You should have received a copy of the GNU General Public License along
// * with this program; if not, write to the Free Software Foundation, Inc.,
// * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
// * ====================================================================
// */
//
//
//package net.thevpc.dbclient.plugin.tool.servermanager.view;
//
//import net.thevpc.dbclient.api.DBClient;
//import net.thevpc.dbclient.plugin.tool.servermanager.DBCServerManager;
//import net.thevpc.dbclient.plugin.tool.servermanager.DBCServerInstance;
//
//import javax.swing.table.AbstractTableModel;
//
///**
// * @author Taha Ben Salah (taha.bensalah@gmail.com)
// * @creationtime 4 juin 2007 16:34:51
// */
//public class DBCServerTableModel extends AbstractTableModel {
//    private DBClient plugin;
//
//    public DBCServerTableModel(DBClient plugin) {
//        this.plugin = plugin;
//    }
//
//    public DBCServerManager getDerbyServerManager() {
//        return plugin == null ? null : plugin.getServerManager();
//    }
//
//    public int getRowCount() {
//        return plugin == null ? 0 : getDerbyServerManager().getServersCount();
//    }
//
//    public int getColumnCount() {
//        return 6;
//    }
//
//    public DBCServerInstance getRow(int r) {
//        return plugin == null ? null : getDerbyServerManager().getServer(r);
//    }
//
//    @Override
//    public String getColumnName(int columnIndex) {
//        switch (columnIndex) {
//            case 0: {
//                return "Server";
//            }
//            case 1: {
//                return "Port";
//            }
//            case 2: {
//                return "Local";
//            }
//            case 3: {
//                return "Stop On Exit";
//            }
//        }
//        return super.getColumnName(columnIndex);
//    }
//
//    public Object getValueAt(int rowIndex, int columnIndex) {
//        DBCServerInstance r = getRow(rowIndex);
//        switch (columnIndex) {
//            case 0: {
//                return r.getServerInfo().getType();
//            }
//            case 1: {
//                return r.getServerInfo().getPort();
//            }
//            case 2: {
//                return r.getServerInfo().isLocal();
//            }
//            case 3: {
//                return r.getServerInfo().isStopOnExit();
//            }
//        }
//        return null;
//    }
//
//    public Class<?> getColumnClass(int columnIndex) {
//        switch (columnIndex) {
//            case 0: {
//                return Boolean.class;
//            }
//            case 1: {
//                return Integer.class;
//            }
//            case 2: {
//                return Boolean.class;
//            }
//            case 3: {
//                return Boolean.class;
//            }
//        }
//        return Object.class;
//    }
//
//    public boolean isCellEditable(int rowIndex, int columnIndex) {
//        return false;//columnIndex == 3;
//    }
//
//
//    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
//        switch (columnIndex) {
//            case 3: {
//                //getRow(rowIndex).setStopOnExit((Boolean) aValue);
//            }
//        }
//    }
//}
