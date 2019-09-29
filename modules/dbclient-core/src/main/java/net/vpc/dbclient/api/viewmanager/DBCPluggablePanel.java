package net.vpc.dbclient.api.viewmanager;

import net.vpc.dbclient.api.pluginmanager.DBCPluggable;

import javax.swing.*;
import java.awt.*;

public class DBCPluggablePanel extends JPanel implements DBCPluggable{
    public DBCPluggablePanel(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
    }

    public DBCPluggablePanel(LayoutManager layout) {
        super(layout);
    }

    public DBCPluggablePanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    public DBCPluggablePanel() {
    }
}
