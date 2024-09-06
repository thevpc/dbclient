package net.thevpc.dbclient.plugin.system.viewmanager;

import net.thevpc.dbclient.api.viewmanager.DBCTree;

import javax.swing.*;
import java.awt.*;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)  alias vpc
 * @creationtime 2009/08/15 17:25:08
 */
public class DBCTreeImpl extends JTree implements DBCTree {
    public DBCTreeImpl() {
    }

    public Component getComponent() {
        return this;
    }

}
