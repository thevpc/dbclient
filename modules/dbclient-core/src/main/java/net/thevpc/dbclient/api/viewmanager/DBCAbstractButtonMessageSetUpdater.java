/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.dbclient.api.viewmanager;

import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.JComponent;

import net.thevpc.common.prs.messageset.MessageSet;
import net.thevpc.common.swing.messageset.AbstractButtonMessageSetUpdater;
import net.thevpc.dbclient.api.actionmanager.DBClientAction;

/**
 *
 * @author vpc
 */
public class DBCAbstractButtonMessageSetUpdater extends AbstractButtonMessageSetUpdater {

    @Override
    public void updateMessageSet(JComponent component, String id, MessageSet messageSet) {
        AbstractButton comp = (AbstractButton) component;
        Action a = comp.getAction();
        if(a!=null && a instanceof DBClientAction){
            DBClientAction zz=(DBClientAction)a;
            final MessageSet mm = zz.getMessageSet();
            if(mm!=null){
               messageSet=mm; 
            }
        }
        super.updateMessageSet(component, id, messageSet);
    }
}
