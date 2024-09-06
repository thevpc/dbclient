package net.thevpc.dbclient.api.viewmanager;

import net.thevpc.common.prs.plugin.Extension;
import net.thevpc.common.swing.dialog.MessageDialogManager;
import net.thevpc.dbclient.api.pluginmanager.DBCPluggable;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)  alias vpc
 * @creationtime 2009/08/15 14:20:29
 */
@Extension(group = "manager")
public interface DBCApplicationMessageDialogManager<T> extends MessageDialogManager<T>, DBCPluggable {
}
