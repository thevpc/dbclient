package net.thevpc.dbclient.api.viewmanager;

import net.thevpc.common.prs.util.ProgressMonitor;
import net.thevpc.dbclient.api.DBCApplication;

import javax.swing.*;

public interface DBCSplashScreen extends ProgressMonitor {

    public void setImageIcon(ImageIcon imageIcon);

    public float getProgress();

    public void setProgressText(String progressText);

    void addDefaultMessages();

    public ImageIcon getImageIcon();

    public void show();

    public void hide();

    public void setApplication(DBCApplication application);
}
