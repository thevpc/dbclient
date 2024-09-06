/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.dbclient.api.viewmanager;

import javax.swing.ImageIcon;

import net.thevpc.common.prs.util.ProgressEvent;
import net.thevpc.dbclient.api.DBCApplication;

/**
 *
 * @author vpc
 */
public final class DBCSilentSplashScreen implements DBCSplashScreen{

    public static DBCSplashScreen INSTANCE=new DBCSilentSplashScreen();
    
    @Override
    public void addDefaultMessages() {
        //
    }

    @Override
    public ImageIcon getImageIcon() {
        return null;
    }

    @Override
    public float getProgress() {
        return 0;
    }

    @Override
    public void hide() {
        //
    }

    @Override
    public void setApplication(DBCApplication application) {
        //
    }

    @Override
    public void setImageIcon(ImageIcon imageIcon) {
        //
    }

    @Override
    public void setProgressText(String progressText) {
        //
    }

    @Override
    public void show() {
        //
    }

    @Override
    public void progressStart(ProgressEvent e) {
        //
    }

    @Override
    public void progressUpdate(ProgressEvent e) {
        //
    }

    @Override
    public void progressEnd(ProgressEvent e) {
        //
    }
    
}
