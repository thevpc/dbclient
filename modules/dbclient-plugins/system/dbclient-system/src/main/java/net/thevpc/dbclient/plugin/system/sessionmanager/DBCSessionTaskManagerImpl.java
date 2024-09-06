/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.dbclient.plugin.system.sessionmanager;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import net.thevpc.dbclient.api.*;

/**
 *
 * @author vpc
 */
public class DBCSessionTaskManagerImpl implements DBCSessionTaskManager {

    private List<DBCSessionRunningTaskListener> listeners = new ArrayList<DBCSessionRunningTaskListener>();
    private final LinkedHashMap<Integer, RunningTask> tasks = new LinkedHashMap<Integer, RunningTask>();
    private DBCSession session;
    private ExecutorService pool;
    private int currentThreadId=0;
    
    @Override
    public void addTaskListener(DBCSessionRunningTaskListener listener) {
        listeners.add(listener);
    }

    @Override
    public List<DBCSessionRunningTask> getTasks() {
        synchronized (tasks) {
            return new ArrayList<DBCSessionRunningTask>(tasks.values());
        }
    }

    @Override
    public void init(DBCSession session) {
        this.session = session;
        pool = Executors.newCachedThreadPool();
    }

    @Override
    public void removeTaskListener(DBCSessionRunningTaskListener listener) {
        listeners.remove(listener);
    }

    @Override
    public int run(DBCSessionTask task) {
        RunningTask runningTask = new RunningTask(++currentThreadId, task);
        taskSubmitted(runningTask);
        pool.submit(runningTask);
        return runningTask.id;
    }

    @Override
    public int run(String name, String desc, Runnable task) {
      DBCDefaultSessionTask t = new DBCDefaultSessionTask(task);
        t.setName(name);
        t.setDescription(desc);
        t.setIndeterminate(true);
        return run(t);
    }

    
    @Override
    public void stop(int taskId) {
        getRunningTask(taskId).stop();
    }
    
    public DBCSessionRunningTask getRunningTask(int id){
        RunningTask t = tasks.get(id);
        if(t==null){
            throw new NoSuchElementException("Task with id "+id);
        }
        return t;
    }

    private void taskSubmitted(RunningTask t) {
        synchronized (tasks) {
            tasks.put(t.id,t);
            for (DBCSessionRunningTaskListener l : listeners) {
                try{
                    l.taskSubmitted(t);
                }catch(Throwable e){
                    session.getLogger(DBCSessionTaskManager.class.getName()).log(Level.SEVERE, "Error", e);
                }
            }
        }
    }

    private void fireTaskStarted(RunningTask t) {
        synchronized (tasks) {
            tasks.remove(t.id);
            for (DBCSessionRunningTaskListener l : listeners) {
                try{
                    l.taskStarted(t);
                }catch(Throwable e){
                    session.getLogger(DBCSessionTaskManager.class.getName()).log(Level.SEVERE, "Error", e);
                }
            }
        }
    }

    private void fireTaskFinished(RunningTask t) {
        synchronized (tasks) {
            tasks.remove(t.id);
            for (DBCSessionRunningTaskListener l : listeners) {
                l.taskFinished(t,t.throwable);
            }
        }
    }

    private void fireTaskProgress(RunningTask t,int value,int max,String message) {
        synchronized (tasks) {
            tasks.remove(t.id);
            for (DBCSessionRunningTaskListener l : listeners) {
                l.taskProgress(t,value,max,message);
            }
        }
    }

    private class RunningTask implements DBCSessionRunningTask,Runnable,DBCSessionTaskListener {

        private int id;
        private DBCSessionTask task;
        private Throwable throwable;
        private Thread currenThread;
        private boolean started;
        private boolean finished;

        public RunningTask(int id, DBCSessionTask task) {
            this.id = id;
            this.task = task;
        }

        @Override
        public boolean isFinished() {
            return finished;
        }

        @Override
        public boolean isStarted() {
            return started;
        }

        
        @Override
        public void dump(PrintStream out) {
            if (currenThread != null) {
                for (StackTraceElement stackTraceElement : currenThread.getStackTrace()) {
                    out.println(stackTraceElement);
                }
            } else {
                out.println("<No Running Thread>");
            }
        }

        @Override
        public boolean isIndeterminate() {
            return task.isIndeterminate();
        }
        
        

        @Override
        public String getDescription() {
            return task.getDescription();
        }

        @Override
        public int getId() {
            return id;
        }

        @Override
        public String getName() {
            return task.getName();
        }

        @Override
        public void stop() {
            task.stop();
        }

        @Override
        public int getProgressMax() {
            return task.getProgressMax();
        }

        @Override
        public String getProgressMessage() {
            return task.getProgressMessage();
        }

        @Override
        public int getProgressValue() {
            return task.getProgressValue();
        }

        @Override
        public void taskProgress(int value, int max, String message) {
            fireTaskProgress(this,value, max, message);
        }
        
        public void run() {
            started=true;
            currenThread=Thread.currentThread();
            fireTaskStarted(this);
            try {
                task.start();
            } catch (Throwable t) {
                throwable = t;
            } finally {
                finished=true;
                fireTaskFinished(this);
            }
        }
    }
}
