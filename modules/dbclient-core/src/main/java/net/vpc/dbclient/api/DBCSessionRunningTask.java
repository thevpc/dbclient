/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vpc.dbclient.api;

import java.io.PrintStream;

/**
 *
 * @author vpc
 */
public interface DBCSessionRunningTask {

    public int getId();

    public String getName();

    public String getDescription();

    public void stop();

    public boolean isIndeterminate();

    public void dump(PrintStream out);

    public int getProgressMax();

    public int getProgressValue();
    
    public boolean isStarted();
    
    public boolean isFinished();

    public String getProgressMessage();
}
