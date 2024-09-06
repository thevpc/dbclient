package net.thevpc.dbclient.api.viewmanager;

import net.thevpc.common.prs.plugin.Extension;
import net.thevpc.common.swing.iswing.IJTree;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)  alias vpc
 * @creationtime 2009/08/15 15:29:03
 */
@Extension(group = "ui", shares = {IJTree.class})
public interface DBCTree extends IJTree, DBCComponent {


}
