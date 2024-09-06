package net.thevpc.dbclient.api.sessionmanager;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)  alias vpc
 * @creationtime 2009/08/15 10:56:11
 */
public class DefaultDBCProgressMonitor implements DBCProgressMonitor {

    private boolean indeterminate;

    private String id;

    private String name;

    private String message;

    private int max;

    private int progress;

    private boolean running;

    private PropertyChangeSupport pcsupport;

    public DefaultDBCProgressMonitor(String id) {
        pcsupport = new PropertyChangeSupport(this);
        this.id = id;
        this.name = id;
    }


    public boolean isIndeterminate() {
        return indeterminate;
    }

    public void setIndeterminate(boolean indeterminate) {
        boolean old = this.indeterminate;
        this.indeterminate = indeterminate;
        pcsupport.firePropertyChange(PROPERTY_PROGRESS, old, indeterminate);
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        int old = this.progress;
        this.max = max;
        pcsupport.firePropertyChange(PROPERTY_PROGRESS, old, progress);
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        int old = this.progress;
        this.progress = progress;
        pcsupport.firePropertyChange(PROPERTY_PROGRESS, old, progress);
        if (isRunning() && progress > getMax()) {
            stop();
        }
    }

    public boolean isRunning() {
        return running;
    }

    private void setRunning(boolean running) {
        boolean old = this.running;
        this.running = running;
        pcsupport.firePropertyChange(PROPERTY_PROGRESS, old, running);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        String old = this.name;
        this.name = name;
        pcsupport.firePropertyChange(PROPERTY_PROGRESS, old, name);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        String old = this.message;
        this.message = message;
        pcsupport.firePropertyChange(PROPERTY_PROGRESS, old, message);
    }

    public void start() {
        setProgress(0);
        setRunning(true);
    }

    public void stop() {
        setRunning(false);
    }

    public void addPropertychangeListener(PropertyChangeListener listener) {
        pcsupport.addPropertyChangeListener(listener);
    }

    public void addPropertychangeListener(String property, PropertyChangeListener listener) {
        pcsupport.addPropertyChangeListener(property, listener);
    }

    public void removePropertychangeListener(PropertyChangeListener listener) {
        pcsupport.removePropertyChangeListener(listener);
    }

    public void removePropertychangeListener(String property, PropertyChangeListener listener) {
        pcsupport.removePropertyChangeListener(property, listener);
    }
}
