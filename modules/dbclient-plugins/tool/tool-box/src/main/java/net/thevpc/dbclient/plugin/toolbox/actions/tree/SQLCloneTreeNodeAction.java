package net.thevpc.dbclient.plugin.toolbox.actions.tree;

import net.thevpc.dbclient.api.actionmanager.DBCActionLocation;
import net.thevpc.dbclient.api.actionmanager.DBCTreeNodeAction;
import net.thevpc.dbclient.api.sql.DBCConnection;
import net.thevpc.dbclient.api.sql.objects.DBObject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 17 mai 2006 23:43:16
 */
public class SQLCloneTreeNodeAction extends DBCTreeNodeAction {

    public SQLCloneTreeNodeAction() {
        super("Action.SQLCloneNodeAction");
        addLocationPath(DBCActionLocation.POPUP, "/");
        addLocationPath(DBCActionLocation.MENUBAR, "/edit");
//        super("Properties");
    }

    public boolean shouldEnable(DBObject activeNode, DBObject[] selectedNodes) {
        try {
            return (activeNode != null
                    && getSession().getConnection().getFeatureGenerateSQLRename().isSupported(activeNode.getType()));
        } catch (SQLException e) {
            getSession().getLogger(SQLCloneTreeNodeAction.class.getName()).log(Level.SEVERE, "shouldEnable failed", e);
            return false;
        }
    }

    public void actionPerformedImpl(ActionEvent e) throws Throwable {
        DBCConnection cnx = getSession().getConnection();
        boolean someChanges = false;
        for (DBObject dbObject : getSelectedNodes()) {
            String n = JOptionPane.showInputDialog(
                    getSession().getView().getMainComponent(),
                    "Clone " + dbObject.getFullName() + " as ...", "Clone", JOptionPane.QUESTION_MESSAGE);
            if (n != null) {
                cnx.executeScript(cnx.getSQLCloneObject(
                        dbObject.getCatalogName(),
                        dbObject.getSchemaName(),
                        dbObject.getParentName(),
                        dbObject.getName(),
                        dbObject.getCatalogName(),
                        dbObject.getSchemaName(),
                        dbObject.getParentName(),
                        n, dbObject.getType(),
                        null));
                someChanges = true;
            }
        }
        if (someChanges) {
            getSession().getView().refreshView();
        }
    }
}
