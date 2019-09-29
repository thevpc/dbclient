/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vpc.dbclient.api;

import java.util.List;
import net.vpc.prs.plugin.Extension;

/**
 *
 * @author vpc
 */
@Extension(group = "core")
public interface DBCSessionTaskManager {

    public void init(DBCSession session);
    
    public int run(DBCSessionTask task);
    
    public int run(String name,String desc,Runnable task);

    public void stop(int taskId);
    
    public DBCSessionRunningTask getRunningTask(int id);

    public List<DBCSessionRunningTask> getTasks();

    public void addTaskListener(DBCSessionRunningTaskListener listener);

    public void removeTaskListener(DBCSessionRunningTaskListener listener);
}
