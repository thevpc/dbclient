package net.vpc.dbclient.api.viewmanager;

import net.vpc.dbclient.api.pluginmanager.DBCPluggable;
import net.vpc.prs.plugin.Extension;
import net.vpc.swingext.dialog.MessageDialogManager;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)  alias vpc
 * @creationtime 2009/08/15 14:20:29
 */
@Extension(group = "manager")
public interface DBCApplicationMessageDialogManager<T> extends MessageDialogManager<T>, DBCPluggable {
}
