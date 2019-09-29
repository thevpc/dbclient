package net.vpc.dbclient.plugin.toolbox.actions.tree;

import net.vpc.dbclient.api.actionmanager.DBCActionLocation;
import net.vpc.dbclient.api.actionmanager.DBCTreeNodeAction;
import net.vpc.dbclient.api.sessionmanager.DBCInternalWindow;
import net.vpc.dbclient.api.sessionmanager.DBCSQLEditorPane;
import net.vpc.dbclient.api.sessionmanager.DBCSessionView;
import net.vpc.dbclient.api.sql.DBCConnection;
import net.vpc.dbclient.api.sql.SQLRecord;
import net.vpc.dbclient.api.sql.TypeWrapperFactory;
import net.vpc.dbclient.api.sql.objects.DBObject;
import net.vpc.dbclient.api.sql.objects.DBProcedure;
import net.vpc.dbclient.api.sql.objects.DBTable;
import net.vpc.dbclient.api.sql.objects.DBTableColumn;
import net.vpc.swingext.PRSManager;

import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 17 mai 2006 23:43:16
 */
public class CreateSQLSelectScriptTreeNodeAction extends DBCTreeNodeAction {

    public CreateSQLSelectScriptTreeNodeAction() {
        super("Action.CreateSQLSelectScriptAction");
        addLocationPath(DBCActionLocation.POPUP, "/sql-ddl");
    }

    public boolean shouldEnable(DBObject activeNode, DBObject[] selectedNodes) {
        if (activeNode == null) {
            return false;
        }
        switch (activeNode.getType()) {
            case DATATYPE:
            case FUNCTION:
            case FOLDER:
            case KEYWORD:
            case PROCEDURE_COLUMN:
            case TABLE_COLUMN:
            case TABLE_COLUMN_TYPE:
            case UNKNOWN: {
                return false;
            }
        }
        return true;
    }

    public void actionPerformedImpl(ActionEvent e) throws Throwable {
        DBCConnection cnx = getSession().getConnection();
        StringBuilder script = new StringBuilder();
        DBObject[] selectedNodes = getSelectedNodes();
        if (selectedNodes.length > 0) {
            script.append("------------------------");
            script.append("\n--  GENERATED DATA SCRIPT BY DBCLIENT (http://dbclient.java.net)");
            script.append("\n--  @SESSION ").append(getSession().getConfig().getSessionInfo().getName());
            script.append("\n--  @CATALOG ").append(selectedNodes[0].getCatalogName());
            script.append("\n--  @SCHEMA  ").append(selectedNodes[0].getSchemaName());
            script.append("\n--  @DATE    ").append(new Date());
            script.append("\n------------------------\n");
            ArrayList<DBObject> dbObjectsForDefinitions = new ArrayList<DBObject>();


            for (DBObject node0 : selectedNodes) {
                fillNodes(node0, dbObjectsForDefinitions);
            }
            ArrayList<CreateSQLSelectScriptTreeNodeAction.ItemDef> defs = new ArrayList<CreateSQLSelectScriptTreeNodeAction.ItemDef>();
            for (DBObject w : dbObjectsForDefinitions) {
                if (cnx.isSQLCreateObjectSupported(w.getType())) {
                    defs.add(new CreateSQLSelectScriptTreeNodeAction.ItemDef(w.getName(), cnx.getSQLCreateObject(w.getCatalogName(), w.getSchemaName(), w.getParentName(), w.getName(), w.getType(), null), w));
                }
            }
            Collections.sort(defs);
//            String catName = null;
//            String goKeyword = cnx.getSQLGoKeyword();

            for (CreateSQLSelectScriptTreeNodeAction.ItemDef wd : defs) {
                if (!(wd.node instanceof DBTable)) {
                    continue;
                }
                DBTable w = (DBTable) wd.node;
                script.append("\n----------------------------");
                script.append("\n-- Values for Table ").append(w.getFullName());
                script.append("\n----------------------------");
                script.append("\n");
                Statement st = getSession().getConnection().createStatement();
                ResultSet rs = st.executeQuery("Select * from " + w.getFullName());
                ResultSetMetaData md = rs.getMetaData();
                int max = md.getColumnCount();
                String[] cols = new String[max];
                for (int j = 0; j < cols.length; j++) {
                    cols[j] = md.getColumnName(j + 1);

                }
                TypeWrapperFactory twf = getPluginSession().getSession().getConnection().getTypeWrapperFactory();
                while (rs.next()) {
                    int index = 1;
                    SQLRecord r = new SQLRecord();
                    while (index <= max) {
                        DBTableColumn column = w.getColumnsFolder().getColumn(cols[index - 1]);
//                        r.put(cols[index - 1], SQLUtils.loadValue(rs, index, column.getSqlType(), column.getPrecision()));
                        r.put(cols[index - 1], twf.getTypeDesc(column.getSqlType()).getWrapper().getObject(rs, index));
                        index++;
                    }
                    script.append(getSession().getConnection().getSQLInsertRecord(
                            w.getCatalogName(), w.getSchemaName(), w.getName(), r, null)).append("\n");
                }
                rs.close();
                st.close();
            }
            DBCSQLEditorPane tabFrame = getPluginSession().getSession().getFactory().newInstance(DBCSQLEditorPane.class);
            tabFrame.getEditor().setSQL(script.toString());
            DBCInternalWindow win = getSession().getView().addWindow(
                    tabFrame, DBCSessionView.Side.Workspace, false);

            PRSManager.update(win.getComponent(), getSession().getView());

        }
    }

    protected void fillNodes(DBObject node0, ArrayList<DBObject> dbObjectsForDefinitions) {
        if (node0 instanceof DBTable || node0 instanceof DBProcedure) {
            dbObjectsForDefinitions.add(node0);
            return;
        }
        int count = node0.getChildCount();
        for (int i = 0; i < count; i++) {
            fillNodes(node0.getChild(i), dbObjectsForDefinitions);
        }
    }

    private static class ItemDef implements Comparable<CreateSQLSelectScriptTreeNodeAction.ItemDef> {

        String name;
        String def;
        DBObject node;

        public ItemDef(String name, String def, DBObject node) {
            this.name = name;
            this.def = def;
            this.node = node;
        }

        public int compareTo(CreateSQLSelectScriptTreeNodeAction.ItemDef o) {
            if (defined(name.toLowerCase(), o.def.toLowerCase())) {
                return -1;
            }
            if (defined(o.name.toLowerCase(), def.toLowerCase())) {
                return 1;
            }
            return name.toLowerCase().compareTo(o.name.toLowerCase());
        }

        private boolean defined(String val, String paragraph) {
            int i = -val.length();
            while ((i = paragraph.indexOf(val, i + val.length())) > 0) {
                boolean before = i == 0 || (!Character.isLetterOrDigit(paragraph.charAt(i - 1)) && paragraph.charAt(i - 1) != '$' && paragraph.charAt(i - 1) != '_');
                boolean after = i == paragraph.length() - val.length() || (!Character.isLetterOrDigit(paragraph.charAt(i + 1)) && paragraph.charAt(i + 1) != '$' && paragraph.charAt(i + 1) != '_');
                if (before && after) {
                    return true;
                }
            }
            return false;
        }
    }
}
