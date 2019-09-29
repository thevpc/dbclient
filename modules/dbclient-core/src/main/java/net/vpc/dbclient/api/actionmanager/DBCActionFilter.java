package net.vpc.dbclient.api.actionmanager;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 7 août 2009
 */
public interface DBCActionFilter {
    public static final DBCActionFilter NONE = new DBCActionFilter() {
        public boolean accept(DBClientAction action) {
            return true;
        }
    };

    public boolean accept(DBClientAction action);
}
