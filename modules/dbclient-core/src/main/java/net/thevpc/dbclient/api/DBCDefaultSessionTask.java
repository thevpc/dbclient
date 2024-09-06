/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.dbclient.api;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vpc
 */
public class DBCDefaultSessionTask implements DBCSessionTask{
    private List<DBCSessionTaskListener> listeners=new ArrayList<DBCSessionTaskListener>();
    private String description;
    private String name;
    private Runnable runnable;
    private boolean indeterminate=true;
    private int progressValue=0;
    private int progressMax=0;
    private String progressMessage=null;

    public DBCDefaultSessionTask() {
    }

    public DBCDefaultSessionTask(Runnable runnable) {
        this.runnable = runnable;
    }
    
    
    @Override
    public void addTaskListener(DBCSessionTaskListener listener) {
        listeners.add(listener);
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    

    @Override
    public int getProgressMax() {
        return progressMax;
    }

    @Override
    public String getProgressMessage() {
        return progressMessage;
    }

    @Override
    public int getProgressValue() {
       return progressValue;
    }

    @Override
    public boolean isIndeterminate() {
        return indeterminate;
    }

    public void setIndeterminate(boolean indeterminate) {
        this.indeterminate = indeterminate;
        progress();
    }

    public void setProgressMax(int progressMax) {
        this.progressMax = progressMax;
        progress();
    }

    public void setProgressMessage(String progressMessage) {
        this.progressMessage = progressMessage;
        progress();
    }

    public void setProgressValue(int progressValue) {
        this.progressValue = progressValue;
        progress();
    }
    

    private void progress(){
        for (DBCSessionTaskListener a : listeners) {
            a.taskProgress(progressValue, progressMax, name);
        }
    }
    
    @Override
    public void removeTaskListener(DBCSessionTaskListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void start() {
        if(runnable!=null){
            runnable.run();
        }
    }

    @Override
    public void stop() {
        //do nothing
    }
    
}
