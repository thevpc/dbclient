package net.vpc.dbclient.api.viewmanager;

import net.vpc.prs.plugin.Extension;

import javax.swing.*;
import java.io.File;

@Extension(group = "ui")
public interface SQLFileChooser extends DBCComponent {
    public JFileChooser toFileChooser();

    public File getSelectedSQLFile();

}
