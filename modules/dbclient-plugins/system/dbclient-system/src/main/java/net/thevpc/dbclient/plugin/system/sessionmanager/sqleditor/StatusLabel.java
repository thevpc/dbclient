package net.thevpc.dbclient.plugin.system.sessionmanager.sqleditor;

import net.thevpc.common.swing.icon.EmptyIcon;
import net.thevpc.common.swing.prs.ComponentResourcesUpdater;
import net.thevpc.dbclient.api.DBCSession;
import net.thevpc.dbclient.api.sessionmanager.DBCRunStatus;
import net.thevpc.common.swing.prs.PRSManager;
import net.thevpc.common.prs.iconset.IconSet;
import net.thevpc.common.prs.messageset.MessageSet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StatusLabel extends JLabel {
    private Icon defaultNoIcon;
    private Dimension defaultIconDim;
    private boolean odd = false;
    private StatusIconUpdater updater = new StatusIconUpdater();
    private DBCSession session;
    private DBCRunStatus statusValue = DBCRunStatus.Empty;
    private Timer currentTimer = null;

    public StatusLabel() {
        PRSManager.addSupport(this, "StatusLabel", updater);
    }

    public void setStatusSuccess(DBCRunStatus ok) {
        statusValue = ok;
        if (currentTimer == null) {
            currentTimer = new Timer(1000, updater);
            currentTimer.setRepeats(true);
            currentTimer.start();
        }
        revalidateStatusIcon();
    }

    public void init(DBCSession session) {
        this.session = session;
    }

    public DBCSession getSession() {
        return session;
    }

//    public void setSession(DBCPluginSession session) {
//        this.session = session;
//    }

    //
    private void showNoIcon() {
        if (defaultNoIcon == null) {
            if (defaultIconDim != null) {
                defaultNoIcon = new EmptyIcon(defaultIconDim.width, defaultIconDim.height);
            } else {
                defaultNoIcon = new EmptyIcon(16, 16);
            }
        } else {
            if (defaultIconDim != null && !defaultIconDim.equals(new Dimension(defaultNoIcon.getIconWidth(), defaultNoIcon.getIconHeight()))) {
                defaultNoIcon = new EmptyIcon(defaultIconDim.width, defaultIconDim.height);
            }
        }
        this.setIcon(defaultNoIcon);
    }

    void revalidateStatusIcon() {
        IconSet iconSet = getSession().getView().getIconSet();
        switch (statusValue) {
            case Empty: {
                showNoIcon();
                break;
            }
            case Success: {
                Icon i = iconSet.getIconR("Success");
                if (i != null) {
                    defaultIconDim = new Dimension(i.getIconWidth(), i.getIconHeight());
                    this.setIcon(i);
                } else {
                    showNoIcon();
                }
                break;
            }
            case Error: {
                if (odd) {
                    Icon i = iconSet.getIconR("Error");
                    if (i != null) {
                        defaultIconDim = new Dimension(i.getIconWidth(), i.getIconHeight());
                        this.setIcon(i);
                    } else {
                        showNoIcon();
                    }
                } else {
                    showNoIcon();
                }
                break;
            }
            case Running: {
                if (odd) {
                    Icon i = iconSet.getIconR("Running");
                    if (i != null) {
                        defaultIconDim = new Dimension(i.getIconWidth(), i.getIconHeight());
                        this.setIcon(i);
                    } else {
                        showNoIcon();
                    }
                } else {
                    showNoIcon();
                }
                break;
            }
        }
    }

    protected class StatusIconUpdater extends ComponentResourcesUpdater implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            odd = !odd;
            revalidateStatusIcon();
        }

        public void update(JComponent comp, String id, MessageSet messageSet, IconSet iconSet) {
            revalidateStatusIcon();
        }
    }
}
