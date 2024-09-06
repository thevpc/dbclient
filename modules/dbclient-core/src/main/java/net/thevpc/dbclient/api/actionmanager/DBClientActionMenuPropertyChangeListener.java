package net.thevpc.dbclient.api.actionmanager;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.io.Serializable;

/**
* Created by IntelliJ IDEA.
* User: vpc
* Date: 13/04/11
* Time: 14:10
* To change this template use File | Settings | File Templates.
*/
class DBClientActionMenuPropertyChangeListener
        extends DBClientActionPropertyChangeListenerBase
        implements Serializable {

    DBClientActionMenuPropertyChangeListener(JMenu m, Action a) {
        super(m, a);
    }

    public void propertyChange(PropertyChangeEvent e) {
        String propertyName = e.getPropertyName();
        JMenuItem mi = (JMenuItem) getTarget();
        if (mi == null) {   //WeakRef GC'ed in 1.2
            Action action = (Action) e.getSource();
            action.removePropertyChangeListener(this);
        } else {
            if (e.getPropertyName().equals(Action.NAME)) {
                String text = (String) e.getNewValue();
                mi.setText(text);
                mi.repaint();
            } else if (propertyName.equals("enabled")) {
                Boolean enabledState = (Boolean) e.getNewValue();
                mi.setEnabled(enabledState.booleanValue());
                mi.repaint();
            } else if (e.getPropertyName().equals(Action.SMALL_ICON)) {
                Icon icon = (Icon) e.getNewValue();
                mi.setIcon(icon);
                mi.invalidate();
                mi.repaint();
            } else if (e.getPropertyName().equals(Action.MNEMONIC_KEY)) {
                Integer mn = (Integer) e.getNewValue();
                mi.setMnemonic(mn);
                mi.invalidate();
                mi.repaint();
            }
        }
    }
}
