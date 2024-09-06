/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.dbclient.api;

/**
 *
 * @author vpc
 */
public interface DBCSessionTaskListener {
    public void taskProgress(int value,int max,String message);
}
