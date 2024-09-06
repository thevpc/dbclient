package net.thevpc.dbclient.api.pluginmanager;

import net.thevpc.common.prs.factory.Factory;

import java.util.List;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)  alias vpc
 * @creationtime 2009/08/15 17:48:22
 */
public interface DBCFactory extends Factory {
    public <T> List<T> createImplementations(Class<? extends T> cls);
}
