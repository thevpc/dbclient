package net.vpc.dbclient.plugin.toolbox.actions.tree;

import net.vpc.dbclient.api.DBClientInfo;
import net.vpc.dbclient.api.actionmanager.DBCActionLocation;
import net.vpc.dbclient.api.actionmanager.DBCTreeNodeAction;
import net.vpc.dbclient.api.sessionmanager.DBCInternalWindow;
import net.vpc.dbclient.api.sessionmanager.DBCSQLEditorPane;
import net.vpc.dbclient.api.sessionmanager.DBCSessionView;
import net.vpc.dbclient.api.sql.DBCConnection;
import net.vpc.dbclient.api.sql.features.GenerateSQLDropFeature;
import net.vpc.dbclient.api.sql.objects.DBObject;
import net.vpc.dbclient.api.sql.objects.DBProcedure;
import net.vpc.dbclient.api.sql.objects.DBTable;
import net.vpc.swingext.PRSManager;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.logging.Level;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 17 mai 2006 23:43:16
 */
public class CreateSQLCreateScriptTreeNodeAction extends DBCTreeNodeAction {

    public CreateSQLCreateScriptTreeNodeAction() {
        super("Action.CreateSQLCreateScriptAction");
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
            script.append("\n--  GENERATED DEFINITION SCRIPT BY ").append(DBClientInfo.INSTANCE.getProductLongTitle()).append(" (").append(DBClientInfo.INSTANCE.getProductURL()).append(")");
            script.append("\n--  @SESSION ").append(getSession().getConfig().getSessionInfo().getName());
            script.append("\n--  @CATALOG ").append(selectedNodes[0].getCatalogName() == null ? "" : selectedNodes[0].getCatalogName());
            script.append("\n--  @SCHEMA  ").append(selectedNodes[0].getSchemaName() == null ? "" : selectedNodes[0].getSchemaName());
            script.append("\n--  @DATE    ").append(new Date());
            script.append("\n------------------------\n");
            ArrayList<DBObject> dbObjectsForDefinitions = new ArrayList<DBObject>();


            for (DBObject node0 : selectedNodes) {
                fillNodes(node0, dbObjectsForDefinitions);
            }
            ArrayList<ItemDef> defs = new ArrayList<ItemDef>();
            for (DBObject w : dbObjectsForDefinitions) {
                if (cnx.isSQLCreateObjectSupported(w.getType())) {
                    defs.add(new ItemDef(w.getName(), cnx.getSQLCreateObject(w.getCatalogName(), w.getSchemaName(), w.getParentName(), w.getName(), w.getType(), null), w));
                }
            }
            Collections.sort(defs);
            String catName = null;
            String goKeyword = cnx.getSQLGoKeyword();
            GenerateSQLDropFeature dropFeature = cnx.getFeatureGenerateSQLDrop();
            for (int i = defs.size() - 1; i >= 0; i--) {
                ItemDef itemDef = defs.get(i);
                DBObject w = itemDef.node;
                String sql = null;
                if (dropFeature.isSupported(w.getType())) {
                    sql = dropFeature.getSQL(w.getCatalogName(), w.getSchemaName(), w.getParentName(), w.getName(), w.getType());
                } else {
                    //do nothing?
                }
                if (sql != null) {
                    script.append("\n\n").append(sql).append("\n");
                }
            }
            for (ItemDef w : defs) {
                String cn = w.node.getCatalogName();
                if (cn != null && (catName == null || !catName.equalsIgnoreCase(cn))) {
                    catName = cn;
                    String sql = cnx.getSQLUse(w.node.getCatalogName(), w.node.getSchemaName());
                    if (sql != null) {
                        script.append(sql).append("\n");
                        if (goKeyword != null) {
                            script.append(goKeyword).append("\n");
                        }
                        script.append("\n");
                    }
                }
                script.append("\n");
                script.append("------------------------").append("\n");
                script.append("--  ").append(w.node.getName()).append("\n");
                script.append("------------------------").append("\n");
                script.append(w.def).append("\n");
                if (goKeyword != null) {
                    script.append(goKeyword).append("\n");
                }
            }
            for (ItemDef itemDef : defs) {
                DBObject w = itemDef.node;
                String sql = null;
                try {
                    sql = cnx.getSQLConstraints(w.getCatalogName(), w.getSchemaName(), w.getParentName(), w.getName(), w.getType(), null);
                } catch (Exception exc) {
                    getSession().getLogger(CreateSQLCreateScriptTreeNodeAction.class.getName()).log(Level.SEVERE,"getSQLConstraints failed",e);
                }
                if (sql != null) {
                    script.append("\n");
                    script.append(sql).append("\n");
                    if (goKeyword != null) {
                        script.append(goKeyword).append("\n");
                    }
                }
            }
            //p.setEditable(false);
//            jsp.setBorder(null);
            DBCSQLEditorPane tabFrame = getPluginSession().getSession().getFactory().newInstance(DBCSQLEditorPane.class);
            tabFrame.getEditor().setSQL(script.toString());
            DBCInternalWindow win = getSession().getView().addWindow(
                    tabFrame, DBCSessionView.Side.Workspace, false);
            win.setTitle("");
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

    private static class ItemDef implements Comparable<ItemDef> {

        String name;
        String def;
        DBObject node;

        public ItemDef(String name, String def, DBObject node) {
            this.name = name;
            this.def = def;
            this.node = node;
        }

        public int compareTo(ItemDef o) {
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
