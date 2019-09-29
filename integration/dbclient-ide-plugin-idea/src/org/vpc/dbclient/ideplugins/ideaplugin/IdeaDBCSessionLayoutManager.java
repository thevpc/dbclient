package net.vpc.dbclient.ideplugins.ideaplugin;

import net.vpc.dbclient.DBClientVersion;
import net.vpc.dbclient.api.session.DBCSession;
import net.vpc.dbclient.api.session.DBCSessionLayoutManager;
import net.vpc.dbclient.api.session.DBCSessionView;
import net.vpc.dbclient.api.windowmanager.DBCWindowListener;
import net.vpc.dbclient.api.windowmanager.DBCWindow;
import net.vpc.dbclient.api.windowmanager.DBCWindowKind;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 10 dec. 2006 16:46:57
 */
public class IdeaDBCSessionLayoutManager implements DBCSessionLayoutManager, DBCWindowListener {
    private DBCSession session;

    public IdeaDBCSessionLayoutManager() {

    }

    public void init(DBCSession session) {
        this.session = session;
    }

    DBCWindow we;
    DBCWindow wt;
    DBCWindow ws;

    public void doLayout() {
        DBCSessionView view = session.getView();
        JPanel explorerBase=new JPanel(new BorderLayout());
        JPanel explorerBase2=new JPanel(new BorderLayout());
        explorerBase.add(view.getMenu(),BorderLayout.NORTH);
        explorerBase.add(view.getToolbar(),BorderLayout.CENTER);
        explorerBase2.add(explorerBase,BorderLayout.NORTH);
        explorerBase2.add(view.getExplorerContainer(),BorderLayout.CENTER);
        we = session.getApplication().getView().getWindowManager().addWindow(
                explorerBase2,
                DBCWindowKind.SESSION_EXPLORER,
                session,
                session.getConfig().getSessionInfo().getSesName() + " : " + DBClientVersion.PRODUCT_NAME,
                null
        );

        wt = session.getApplication().getView().getWindowManager().addWindow(
                view.getTracerContainer(),
                DBCWindowKind.SESSION_TRACER,
                session,
                session.getConfig().getSessionInfo().getSesName() + " : " + DBClientVersion.PRODUCT_NAME,
                null
        );


        ws = session.getApplication().getView().getWindowManager().addWindow(
                view.getWorkspaceContainer(),
                DBCWindowKind.SESSION_WORKSPACE,
                session,
                session.getConfig().getSessionInfo().getSesName() + " : " + DBClientVersion.PRODUCT_NAME,
                null
        );

        we.addWindowListener(this);
        wt.addWindowListener(this);
        ws.addWindowListener(this);

        we.showWindow();
        wt.showWindow();
        ws.showWindow();
    }


    public void windowClosed(DBCWindow window) {
        if (we == window) {
            we = null;
        }
        if (ws == window) {
            ws = null;
        }
        if (wt == window) {
            wt = null;
        }
        if (ws == null && we == null && wt == null) {
            try {
                session.close();
            } catch (SQLException e) {
                session.getMessageDialogManager().showError(e,null);
            }
        }
    }

    public void windowHidden(DBCWindow window) {
    }

    public void windowOpened(DBCWindow window) {
    }
}
