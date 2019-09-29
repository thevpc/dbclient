/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vpc.dbclient.api;

/**
 *
 * @author vpc
 */
public interface DBCSessionTask {

    public String getName();

    public String getDescription();

    public void start();

    public int getProgressMax();

    public int getProgressValue();

    public String getProgressMessage();

    public boolean isIndeterminate();

    public void stop();
    
    public void addTaskListener(DBCSessionTaskListener listener);
    
    public void removeTaskListener(DBCSessionTaskListener listener);
}
