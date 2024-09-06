package net.thevpc.dbclient.plugin.system.viewmanager;

import net.thevpc.dbclient.api.viewmanager.DBCTable;

import javax.swing.*;
import java.awt.*;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)  alias vpc
 * @creationtime 2009/08/15 17:29:17
 */
public class DBCTableImpl extends JTable implements DBCTable {
    public DBCTableImpl() {
    }

    public int convertRowIndexToView(int i) {
        return i;
    }

    public int convertRowIndexToModel(int i) {
        return i;
    }

    public Component getComponent() {
        return this;
    }

}
