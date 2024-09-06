/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.dbclient.api.actionmanager;

import net.thevpc.common.prs.plugin.Ignore;

import java.awt.event.ActionEvent;

/**
 * @author vpc
 */
@Ignore
public class DBCSessionSeparatorAction extends DBCSessionAction {
    private static int counter = 0;

    public DBCSessionSeparatorAction() {
        this(nextId());
    }

    private static String nextId() {
        counter++;
        return "SessionSeparator#" + String.valueOf(counter);
    }

    public DBCSessionSeparatorAction(String id) {
        super(id);
        setType(DBClientActionType.SEPARATOR);
        setMessageId(null);
        setIconId(null);
    }

    public final void actionPerformedImpl(ActionEvent e) throws Throwable {
    }
}
