package net.thevpc.dbclient.api.viewmanager;

import net.thevpc.dbclient.api.pluginmanager.DBCPluggable;
import net.thevpc.common.swing.iswing.IComponent;

import java.awt.*;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)  alias vpc
 * @creationtime 2009/08/12 14:08:38
 */
public interface DBCComponent extends DBCPluggable, IComponent {
    public Component getComponent();

    public void requestFocus();

    public void putClientProperty(Object key, Object value);

    public Object getClientProperty(Object key);
}
