package net.vpc.dbclient.plugin.tool.recordeditor;

import net.vpc.dbclient.api.DBCSession;
import net.vpc.dbclient.api.sessionmanager.DBCInternalWindow;
import net.vpc.dbclient.api.sessionmanager.DBCSessionView;

import javax.swing.*;
import java.awt.*;

public class RecordEditorExplorer extends JPanel {
    private JDesktopPane desktop;

    public RecordEditorExplorer() {
        super(new BorderLayout());
        desktop = new JDesktopPane();
        add(desktop, BorderLayout.CENTER);
    }

    public JDesktopPane getDesktop() {
        return desktop;
    }

}
