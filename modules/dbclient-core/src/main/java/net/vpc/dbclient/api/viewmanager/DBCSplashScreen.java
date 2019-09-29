package net.vpc.dbclient.api.viewmanager;

import net.vpc.dbclient.api.DBCApplication;

import javax.swing.*;

public interface DBCSplashScreen extends net.vpc.util.ProgressMonitor {

    public void setImageIcon(ImageIcon imageIcon);

    public float getProgress();

    public void setProgressText(String progressText);

    void addDefaultMessages();

    public ImageIcon getImageIcon();

    public void show();

    public void hide();

    public void setApplication(DBCApplication application);
}
