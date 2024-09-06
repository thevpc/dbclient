package net.thevpc.dbclient.plugin.system.sessionmanager.tree.sqlgen;

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
//package net.thevpc.dbclient.sessionmanager.window.tree.sqlgen;
//
//import net.thevpc.dbclient.api.sessionmanager.DBCSession;
//import net.thevpc.dbclient.api.sql.SQLDialectManager;
//import net.thevpc.dbclient.api.sql.objects.*;
//
//import java.io.PrintStream;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Date;
//
///**
// * @author Taha BEN SALAH (taha.bensalah@gmail.com)
// * @creationtime 12 août 2005 10:48:58
// */
//public class DefinitionScriptGenerator extends TreeSQLGenerator {
//    public DefinitionScriptGenerator(DBCSession session) {
//        super(session, "DefinitionScriptGenerator");
////        super(tree, "Insert Into Select * From $table");
//    }
//
//    private static class ItemDef implements Comparable<ItemDef> {
//        String name;
//        String def;
//        DBObject node;
//
//
//        public ItemDef(String name, String def, DBObject node) {
//            this.name = name;
//            this.def = def;
//            this.node = node;
//        }
//
//        public int compareTo(ItemDef o) {
//            if (defined(name.toLowerCase(), o.def.toLowerCase())) {
//                return -1;
//            }
//            if (defined(o.name.toLowerCase(), def.toLowerCase())) {
//                return 1;
//            }
//            return name.toLowerCase().compareTo(o.name.toLowerCase());
//        }
//
//        private boolean defined(String val, String paragraph) {
//            int i = -val.length();
//            while ((i = paragraph.indexOf(val, i + val.length())) > 0) {
//                boolean before = i == 0 || (!Character.isLetterOrDigit(paragraph.charAt(i - 1)) && paragraph.charAt(i - 1) != '$' && paragraph.charAt(i - 1) != '_');
//                boolean after = i == paragraph.length() - val.length() || (!Character.isLetterOrDigit(paragraph.charAt(i + 1)) && paragraph.charAt(i + 1) != '$' && paragraph.charAt(i + 1) != '_');
//                if (before && after) {
//                    return true;
//                }
//            }
//            return false;
//        }
//    }
//
//    public void generateSQL(PrintStream out, DBObject[] selectedNodes) throws SQLException {
//        if (selectedNodes.length == 0) {
//            return;
//        }
//        try {
//            out.println("------------------------");
//            out.println("--  GENERATED DEFINITION SCRIPT BY DBCLIENT (http://dbclient.java.net)");
//            out.println("--  @SESSION " + session.getConfig().getSessionInfo().getName());
//            out.println("--  @CATALOG " + selectedNodes[0].getCatalogName());
//            out.println("--  @SCHEMA  " + selectedNodes[0].getSchemaName());
//            out.println("--  @DATE    " + new Date());
//            out.println("------------------------");
//
//            ArrayList<DBObject> dbObjectsForDefinitions = new ArrayList<DBObject>();
//
//
//            for (DBObject node0 : selectedNodes) {
//                fillNodes(node0, dbObjectsForDefinitions);
//            }
//            ArrayList<ItemDef> defs = new ArrayList<ItemDef>();
//            SQLDialectManager sqlDialectManager = session.getSQLDialectManager();
//            for (DBObject w : dbObjectsForDefinitions) {
//                if (sqlDialectManager.isSQLCreateObjectSupported(w.getType())) {
//                    defs.add(new ItemDef(w.getName(), sqlDialectManager.getSQLCreate(w.getCatalogName(), w.getSchemaName(), w.getParentName(), w.getName(), w.getType()), w));
//                }
//            }
//            Collections.sort(defs);
//            String catName = null;
//            String goKeyword = session.getSQLDialectManager().getSQLGoKeyword();
//            for (int i = defs.size() - 1; i >= 0; i--) {
//                ItemDef itemDef = defs.get(i);
//                DBObject w = itemDef.node;
//                String sql = sqlDialectManager.getSQLDropObject(w.getCatalogName(), w.getSchemaName(), w.getParentName(), w.getName(), w.getType());
//                if (sql != null) {
//                    out.println();
//                    out.println(sql);
//                }
//            }
//            for (ItemDef w : defs) {
//                String cn = w.node.getCatalogName();
//                if (cn != null && (catName == null || !catName.equalsIgnoreCase(cn))) {
//                    catName = cn;
//                    String sql = session.getSQLDialectManager().getSQLUse(w.node.getCatalogName(), w.node.getSchemaName());
//                    if (sql != null) {
//                        out.println(sql);
//                        if (goKeyword != null) {
//                            out.println(goKeyword);
//                        }
//                        out.println();
//                    }
//                }
//                out.println();
//                out.println("------------------------");
//                out.println("--  " + w.node.getName());
//                out.println("------------------------");
//                out.println(w.def);
//                if (goKeyword != null) {
//                    out.println(goKeyword);
//                }
//            }
//            for (ItemDef itemDef : defs) {
//                DBObject w = itemDef.node;
//                String sql = sqlDialectManager.getSQLConstraints(w.getCatalogName(), w.getSchemaName(), w.getParentName(), w.getName(), w.getType());
//                if (sql != null) {
//                    out.println();
//                    out.println(sql);
//                    if (goKeyword != null) {
//                        out.println(goKeyword);
//                    }
//                }
//            }
//        } catch (Throwable e1) {
//            e1.printStackTrace();
////            getSession().getErr().println(e1);
//        }
//    }
//
//    protected void fillNodes(DBObject node0, ArrayList<DBObject> dbObjectsForDefinitions) {
//        if (node0 instanceof Table || node0 instanceof Procedure) {
//            dbObjectsForDefinitions.add(node0);
//            return;
//        }
//        int count = node0.getChildCount();
//        for (int i = 0; i < count; i++) {
//            fillNodes(node0.getChild(i), dbObjectsForDefinitions);
//        }
//    }
//
//
//    public boolean isEnabled(DBObject activeNode, DBObject[] selectedNodes) {
//        DBObject n = activeNode;
//        return n != null
//                &&
//                (
//                        n instanceof Table
//                                || n instanceof TableFolder
//                                || n instanceof Catalog
//                                || n instanceof Schema
//                                || n instanceof Procedure
//                                || n instanceof ProcedureFolder
//                );
//    }
//}
