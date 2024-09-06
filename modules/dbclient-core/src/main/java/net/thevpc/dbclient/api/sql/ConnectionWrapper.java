package net.thevpc.dbclient.api.sql;


import java.beans.PropertyChangeListener;
import java.sql.Connection;
import net.thevpc.common.prs.log.LoggerProvider;

public interface ConnectionWrapper extends Connection {
    public Connection getConnection();

    public void setConnection(Connection connection);

    public LoggerProvider getLoggerProvider();
    
    public void setLoggerProvider(LoggerProvider loggerProvider);

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener);

    public void addPropertyChangeListener(PropertyChangeListener listener);

    public void removePropertyChangeListener(PropertyChangeListener listener);

}
