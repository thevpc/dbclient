/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vpc.dbclient.api.actionmanager;

import net.vpc.prs.plugin.Ignore;

import java.awt.event.ActionEvent;

/**
 * @author vpc
 */
@Ignore
public class DBCApplicationSeparatorAction extends DBCApplicationAction {
    private static int counter = 0;

    public DBCApplicationSeparatorAction() {
        this(nextId());
    }

    private static String nextId() {
        counter++;
        return "ApplicationSeparator#" + String.valueOf(counter);
    }

    public DBCApplicationSeparatorAction(String id) {
        super(id);
        setType(DBClientActionType.SEPARATOR);
        setMessageId(null);
        setIconId(null);
    }

    public final void actionPerformedImpl(ActionEvent e) throws Throwable {
    }
}
