package net.vpc.dbclient.api.pluginmanager;

import net.vpc.prs.plugin.Extension;

/**
 * Default PluginSession is used when a plugin does not define a specific one
 *
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 23 août 2009 16:44:41
 */
@Extension(group = "core")
public interface DBCDefaultPluginSession extends DBCPluginSession{
}
