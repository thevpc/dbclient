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
package net.vpc.dbclient.plugin.sql.jstsql;

import net.vpc.dbclient.api.pluginmanager.DBCAbstractPluginSession;

import java.util.ArrayList;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 29 août 2007 19:58:38
 */
public class JSTSqlPluginSession extends DBCAbstractPluginSession {
    public static final int TOOLBAR = 1;
    public static final int MENU_INSERT = 2;
    public static final int MENU_SAVE = 4;
    public static final int MENU_EXEC = 8;

    public JSTSqlPluginSession() {
    }

    public JSTSqlTemplateInfo[] loadTemplates() {
        Integer templatesCount = getConfig().getIntegerProperty("count", null);
        if (templatesCount == null) {
            //add default values
            JSTSqlTemplateInfo[] df = createDefaults();
            for (int i = 0; i < df.length; i++) {
                df[i].setSstId(i + 1);
                df[i].setSstIndex(i + 1);
            }
            storeTemplates(df);
            return df;
        } else {
            ArrayList<JSTSqlTemplateInfo> a = new ArrayList<JSTSqlTemplateInfo>();
            for (int i = 0; i < templatesCount; i++) {
                String cc = getConfig().getStringProperty(String.valueOf((i + 1)), null);
                if (cc != null && cc.trim().length() > 0) {
                    JSTSqlTemplateInfo in = new JSTSqlTemplateInfo();
                    in.decode(cc);
                    a.add(in);
                }
            }
            return a.toArray(new JSTSqlTemplateInfo[a.size()]);
        }
    }

    private JSTSqlTemplateInfo[] createDefaults() {
        return new JSTSqlTemplateInfo[]{
                create(null, MENU_INSERT | MENU_SAVE | MENU_EXEC, "SelectStar", false, false, true, false, "table|system table|view", null, "Select * From <%=node.getFullName()%>"),
                create(null, MENU_INSERT | MENU_SAVE | MENU_EXEC, "SelectAll", false, false, true, false, "table|system table|view", null, "Select <%=node.getColumns()%> From <%=node.getName()%>"),
                create(null, MENU_INSERT, "SelectWhere", false, false, true, false, "table|system table|view", null, "Select <%=node.getColumns()%> From <%=node.getFullName()%> Where 1=1\n<%for(DBTableColumn column : node.getColumns()){\n%><%=LC%> AND <%= column.getName()%> = ? <%=LC%> <%=column.getSqlTypeName()%>\n<%}\n%>"),
                create(null, MENU_INSERT | MENU_SAVE | MENU_EXEC, "SelectCount", false, false, true, false, "table|system table|view", null, "Select Count(1) From <%=node.getFullName()%>"),
                create(null, MENU_INSERT | MENU_SAVE | MENU_EXEC, "Drop", false, true, true, false, "table|view|procedure", null, "Drop <%=node.getType()%> <%=node.getName()%>"),
                create(null, MENU_INSERT | MENU_SAVE | MENU_EXEC, "Delete", true, true, true, false, "table", null, "Delete From <%=node.getName()%>"),
                create(null, MENU_INSERT, "AlterColumn", false, true, true, false, "column", null, "Alter Table <%=node.getTable().getFullName()%> Modify (\n" +
                        "<%= node.getName()%>\n" +
                        "<%= node.getSqlTypeName()%> <%=LC%>new type\n" +
                        "<%=LC%> default ''AM'' <%=LC%>new default\n" +
                        "<%if(!node.isNullable()){%>\n" +
                        "<%=LC%>constraint  <%=node.getParent().getSQL() + \"_\" + node.getSQL() + \"_NNC Not Null\"%>\n" +
                        "<%}else{%>\n" +
                        "<%=LC%>constraint  Null\n" +
                        "<%}%>\n" +
                        ");"
                )
                , create(null, MENU_INSERT | MENU_SAVE | MENU_EXEC, "DropColumn", true, true, true, false, "column", null,
                        "Alter Table <%=node.getParent().getFullName()%> Drop <%= node.getName()%>"
                )
                , create(null, MENU_INSERT, "Update", false, true, true, false, "table|system table", null,
                        "Update <%=node.getFullName()%>\n" +
                                "Set\n" +
                                "<%boolean first=true;\n" +
                                "for(DBTableColumn column : node.getUpdatableColumns()){\n" +
                                "%><%=LC%> <%if(first){first= !first;}else{out.print(\", \");}%> <%= column.getName()%> = ? <%=LC%> <%=column.getSqlTypeName()%>\n" +
                                "<%}\n" +
                                "%>Where 1=1\n" +
                                "<%for(DBTableColumn column : node.getPrimaryColumns()){\n" +
                                "%><%=LC%> AND <%= column.getName()%> = ? <%=LC%> <%=column.getSqlTypeName()%>\n" +
                                "<%}\n" +
                                "%><%=LC%>\n" +
                                "<%for(DBTableColumn column : node.getUpdatableColumns()){\n" +
                                "%><%=LC%> AND <%= column.getName()%> = ? <%=LC%> <%=column.getSqlTypeName()%>\n" +
                                "<%}\n" +
                                "%>"
                )
                , create(null, MENU_INSERT, "Insert", true, false, true, false, "table|system table", null,
                        "Insert Into <%=node.getFullName()%>(<%=node.getInsertableColumns()%>)\n" +
                                "Values(\n" +
                                "<%boolean first=true;\n" +
                                "for(DBTableColumn column : node.getUpdatableColumns()){\n" +
                                "%><%=LC%> <%if(first){first= !first;}else{out.print(\", \");}%> <%= column.getName()%> = ? <%=LC%> <%=column.getSqlTypeName()%>\n" +
                                "<%}\n" +
                                "%>)"
                )
                , create(null, MENU_INSERT, "InsertSimple", false, false, true, false, "table|system table", null,
                        "Insert Into <%=node.getFullName()%>(<%=node.getInsertableMandatoryColumns()%>)\n" +
                                "Values(\n" +
                                "<%boolean first=true;\n" +
                                "for(DBTableColumn column : node.getUpdatableColumns()){\n" +
                                "%><%=LC%> <%if(first){first= !first;}else{out.print(\", \");}%> <%= column.getName()%> = ? <%=LC%> <%=column.getSqlTypeName()%>\n" +
                                "<%}\n" +
                                "%>)"
                )
        };
    }

    private JSTSqlTemplateInfo create(
            String SST_SFC_NAME,
            int SST_GROUP_INDEX,
            String SST_NAME,
            boolean SST_IS_SEPARATOR,
            boolean SST_CONFIRM_ENABLED, boolean SST_ENABLED, boolean SST_PREFER_TEMPLATE, String SST_NODE_FILTER, String SST_CONFIRM_MSG, String SST_SQL
    ) {
        JSTSqlTemplateInfo i = new JSTSqlTemplateInfo();
        i.setSstConfirmEnabled(SST_CONFIRM_ENABLED);
        i.setSstConfirmMsg(SST_CONFIRM_MSG);
        i.setSstEnabled(SST_ENABLED);
        i.setSstGroupIndex(SST_GROUP_INDEX);
        i.setSstIsSeparator(SST_IS_SEPARATOR);
        i.setSstName(SST_NAME);
        i.setSstNodeFilter(SST_NODE_FILTER);
        i.setSstPreferTemplate(SST_PREFER_TEMPLATE);
        i.setSstSfcName(SST_SFC_NAME);
        i.setSstSql(SST_SQL);
        i.setSstVersion((int) System.currentTimeMillis());
        return i;
    }

    public void storeTemplates(JSTSqlTemplateInfo[] infos) {
        getConfig().clearProperties("*");
        getConfig().setIntegerProperty("count", infos.length);
        for (int i = 0; i < infos.length; i++) {
            getConfig().setStringProperty(String.valueOf(i + 1), infos[i].encode());
        }
    }

}
/**
 *

 INSERT INTO T_SQL_TEMPLATE(SST_SFC_NAME, SST_INDEX,  SST_GROUP_INDEX, SST_NAME, SST_IS_SEPARATOR, SST_SQL, SST_NODE_FILTER, SST_PREFER_TEMPLATE, SST_CONFIRM_MSG , SST_CONFIRM_ENABLED, SST_ENABLED) VALUES(
 );


 */
