package net.thevpc.dbclient.api.sessionmanager;

import java.beans.PropertyChangeListener;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)  alias vpc
 * @creationtime 2009/08/15 10:51:14
 */
public interface DBCProgressMonitor {
    public static final String PROPERTY_RUNNING = "running";
    public static final String PROPERTY_PROGRESS = "progress";
    public static final String PROPERTY_NAME = "name";
    public static final String PROPERTY_MESSAGE = "message";

    public void setIndeterminate(boolean indeterminate);

    public boolean isIndeterminate();

    public void setMax(int indeterminate);

    public int getMax();

    public void setProgress(int progress);

    public int getProgress();

    public void start();

    public void stop();

    public boolean isRunning();

    public void addPropertychangeListener(PropertyChangeListener listener);

    public void removePropertychangeListener(PropertyChangeListener listener);

    String getId();

    String getName();

    void setName(String name);

    String getMessage();

    void setMessage(String message);

    void addPropertychangeListener(String property, PropertyChangeListener listener);

    void removePropertychangeListener(String property, PropertyChangeListener listener);
}
