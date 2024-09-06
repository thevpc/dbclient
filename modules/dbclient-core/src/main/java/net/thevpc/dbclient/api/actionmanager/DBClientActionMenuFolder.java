package net.thevpc.dbclient.api.actionmanager;

import javax.swing.*;
import java.beans.PropertyChangeListener;

/**
* Created by IntelliJ IDEA.
* User: vpc
* Date: 13/04/11
* Time: 14:13
* To change this template use File | Settings | File Templates.
*/
class DBClientActionMenuFolder extends JMenu {

    public DBClientActionMenuFolder(DBClientAction a) {
        super((String) a.getValue(Action.NAME));
        setIcon((Icon) a.getValue(Action.SMALL_ICON));
        setHorizontalTextPosition(JButton.TRAILING);
        setVerticalTextPosition(JButton.CENTER);
        setEnabled(a.isEnabled());
        putClientProperty("Action", a);
        a.addPropertyChangeListener(createActionPropertyChangeListener(a));
    }

    public DBClientAction getMenuAction() {
        return (DBClientAction) getClientProperty("Action");
    }

    @Override
    protected PropertyChangeListener createActionPropertyChangeListener(Action a) {
        PropertyChangeListener pcl = new DBClientActionMenuPropertyChangeListener(DBClientActionMenuFolder.this, a);
        if (pcl == null) {
            pcl = super.createActionPropertyChangeListener(a);
        }
        return pcl;
    }
}
