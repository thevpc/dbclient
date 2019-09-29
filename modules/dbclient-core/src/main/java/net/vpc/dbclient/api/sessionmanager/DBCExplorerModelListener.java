/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vpc.dbclient.api.sessionmanager;

/**
 *
 * @author vpc
 */
public interface DBCExplorerModelListener {
    public void modelPreUpdating(boolean structureChange);
    public void modelPostUpdating(boolean structureChange);
}
