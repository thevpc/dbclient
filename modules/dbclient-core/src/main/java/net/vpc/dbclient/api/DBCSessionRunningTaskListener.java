/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vpc.dbclient.api;

/**
 *
 * @author vpc
 */
public interface DBCSessionRunningTaskListener {
    public void taskSubmitted(DBCSessionRunningTask task);
    public void taskStarted(DBCSessionRunningTask task);
    public void taskFinished(DBCSessionRunningTask task,Throwable error);
    public void taskProgress(DBCSessionRunningTask task,int value,int max,String message);
}
