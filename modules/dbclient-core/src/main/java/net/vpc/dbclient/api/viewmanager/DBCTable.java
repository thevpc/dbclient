package net.vpc.dbclient.api.viewmanager;

import net.vpc.prs.plugin.Extension;
import net.vpc.swingext.iswing.IJTable;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)  alias vpc
 * @creationtime 2009/08/15 17:28:35
 */
@Extension(group = "ui", shares = {IJTable.class})
public interface DBCTable extends IJTable, DBCComponent{
}
