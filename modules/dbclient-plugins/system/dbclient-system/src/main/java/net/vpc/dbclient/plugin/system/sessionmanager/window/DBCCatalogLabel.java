/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vpc.dbclient.plugin.system.sessionmanager.window;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import net.vpc.dbclient.api.DBCSession;
import net.vpc.dbclient.api.sessionmanager.DBCSessionView;
import net.vpc.dbclient.api.sql.DBCConnection;
import net.vpc.dbclient.api.sql.objects.DBCatalog;
import net.vpc.swingext.ComponentResourcesUpdater;
import net.vpc.swingext.PRSManager;
import net.vpc.prs.iconset.IconSet;
import net.vpc.prs.messageset.MessageSet;

/**
 *
 * @author vpc
 */
public class DBCCatalogLabel extends JLabel implements PropertyChangeListener, MouseListener {

    private DBCSessionView view;
    private static final String PRS_ID = "DBCCatalogLabel";

    public DBCCatalogLabel(DBCSessionView view) {
        this.view = view;
        view.getSession().addPropertyChangeListener("catalog", this);
        updateText();
        addMouseListener(this);
        PRSManager.addSupport(this, PRS_ID, new ComponentResourcesUpdater() {

            @Override
            public void update(JComponent comp, String id, MessageSet messageSet, IconSet iconSet) {
                updateText(messageSet, iconSet);
            }
        });

    }

    private void updateText() {
        updateText(view.getMessageSet(), view.getIconSet());
    }

    private void updateText(MessageSet messageSet, IconSet iconSet) {
        try {
            if (messageSet == null) {
                messageSet = view.getMessageSet();
            }
            DBCatalog cat = view.getSession().getFactory().newInstance(DBCatalog.class);
            String catalog = view.getSession().getCatalog();
            cat.init(view.getSession().getConnection(), catalog);
            setIcon(view.getObjectIcon(cat));
            String m = null;
            m = catalog==null?null:messageSet.get(PRS_ID, "{0}", true, true, new Object[]{catalog});
            if (m == null || m.length()==0) {
                m = view.getMessageSet().get("Tree.NoName");
            }
            setText(m);
            setToolTipText(messageSet.get(PRS_ID + ".tooltip", null, false, true, null));
        } catch (SQLException ex) {
            view.getSession().getLogger(DBCCatalogLabel.class.getName()).log(Level.SEVERE, "Could not resolve inuse catalog name", ex);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        updateText();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
            try {
                DBCSession session = view.getSession();
                DBCConnection connection = session.getConnection();
                ResultSet catalogs = connection.getMetaData().getCatalogs();
                ArrayList<DBCatalog> all = new ArrayList<DBCatalog>(3);
                DBCatalog old = null;
                while (catalogs.next()) {
                    DBCatalog cat = session.getFactory().newInstance(DBCatalog.class);
                    cat.init(connection, catalogs);
                    all.add(cat);
                    String cn = cat.getName();
                    String cat0 = session.getCatalog();
                    if (cn == cat0 || (cn != null && cn.equalsIgnoreCase(cat0))) {
                        old = cat;
                    }
                }
                if (all.size() > 0) {
                    DBCatalog[] sel = all.toArray(new DBCatalog[all.size()]);

                    DBCatalog v = (DBCatalog) JOptionPane.showInputDialog(view.getMainComponent(), null, "Change Catalog to", JOptionPane.QUESTION_MESSAGE, null, sel, old);
                    if (v != null) {
                        session.setCatalog(v.getName());
                    }
                } else {
                    view.getSession().getLogger(DBCCatalogLabel.class.getName()).warning("No Catalogs to change to");
                }
            } catch (SQLException ex) {
                Logger.getLogger(DBCCatalogLabel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //
    }
}
