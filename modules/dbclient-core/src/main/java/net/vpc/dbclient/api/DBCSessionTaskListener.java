/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vpc.dbclient.api;

/**
 *
 * @author vpc
 */
public interface DBCSessionTaskListener {
    public void taskProgress(int value,int max,String message);
}
