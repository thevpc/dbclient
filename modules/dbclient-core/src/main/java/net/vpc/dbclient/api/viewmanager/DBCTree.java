package net.vpc.dbclient.api.viewmanager;

import net.vpc.prs.plugin.Extension;
import net.vpc.swingext.iswing.IJTree;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)  alias vpc
 * @creationtime 2009/08/15 15:29:03
 */
@Extension(group = "ui", shares = {IJTree.class})
public interface DBCTree extends IJTree, DBCComponent {


}
