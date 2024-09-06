package net.thevpc.dbclient.plugin.dbsupport.derby.features;

import net.thevpc.dbclient.api.sql.DBCFeature;
import net.thevpc.dbclient.plugin.dbsupport.derby.DerbyConnection;

import java.io.File;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)  alias vpc
 * @creationtime 2009/08/16 01:00:34
 */
public class DerbySYSCSUTILFeature implements DBCFeature {
    private DerbyConnection cnx;

    public DerbySYSCSUTILFeature(DerbyConnection cnx) {
        this.cnx = cnx;
    }

    public boolean isSupported() {
        return true;
    }

    public void SYSCS_SET_RUNTIMESTATISTICS(boolean enable) throws SQLException {
        CallableStatement cs = null;
        try {
            cs = cnx.prepareCall("CALL SYSCS_UTIL.SYSCS_SET_RUNTIMESTATISTICS(?)");
            cs.setInt(1, enable ? 1 : 0);
            cs.execute();
        } finally {
            if (cs != null) {
                cs.close();
            }
        }
    }

    public void SYSCS_SET_STATISTICS_TIMING(boolean enable) throws SQLException {
        CallableStatement cs = null;
        try {
            cs = cnx.prepareCall("CALL SYSCS_UTIL.SYSCS_SET_STATISTICS_TIMING(?)");
            cs.setInt(1, enable ? 1 : 0);
            cs.execute();
        } finally {
            if (cs != null) {
                cs.close();
            }
        }
    }

    public void SYSCS_CHECKPOINT_DATABASE() throws SQLException {
        CallableStatement cs = null;
        try {
            cs = cnx.prepareCall("CALL SYSCS_UTIL.SYSCS_CHECKPOINT_DATABASE()");
            cs.execute();
        } finally {
            if (cs != null) {
                cs.close();
            }
        }
    }

    public void SYSCS_BACKUP_DATABASE(File backupDirectory) throws SQLException, IOException {
        CallableStatement cs = null;
        try {
            cs = cnx.prepareCall("CALL SYSCS_UTIL.SYSCS_BACKUP_DATABASE(?)");
            cs.setString(1, backupDirectory.getCanonicalPath());
            cs.execute();
        } finally {
            if (cs != null) {
                cs.close();
            }
        }
    }

    public void SYSCS_FREEZE_DATABASE() throws SQLException, IOException {
        CallableStatement cs = null;
        try {
            cs = cnx.prepareCall("CALL SYSCS_UTIL.SYSCS_FREEZE_DATABASE()");
            cs.execute();
        } finally {
            if (cs != null) {
                cs.close();
            }
        }
    }

    public void SYSCS_UNFREEZE_DATABASE() throws SQLException, IOException {
        CallableStatement cs = null;
        try {
            cs = cnx.prepareCall("CALL SYSCS_UTIL.SYSCS_UNFREEZE_DATABASE(()");
            cs.execute();
        } finally {
            if (cs != null) {
                cs.close();
            }
        }
    }

    public void SYSCS_COMPRESS_TABLE(String catalogName, String schemaName, String tableName, boolean sequential) throws SQLException {
        CallableStatement cs = null;
        try {
            cs = cnx.prepareCall("CALL SYSCS_UTIL.SYSCS_COMPRESS_TABLE(?,?,?)");
            cs.setString(1, schemaName);
            cs.setString(2, tableName);
            cs.setInt(3, sequential ? 1 : 0);
            cs.execute();
        } finally {
            if (cs != null) {
                cs.close();
            }
        }
    }

    public void SYSCS_INPLACE_COMPRESS_TABLE(String catalogName, String schemaName, String tableName, boolean purgeRows, boolean defragmentRows, boolean truncateEnd) throws SQLException {
        CallableStatement cs = null;
        try {
            //cs = cnx.prepareCall("CALL SYSCS_UTIL.SYSCS_INPLACE_COMPRESS_TABLE(?,?,?,?,?)");
            cs = cnx.prepareCall("CALL SYSCS_UTIL.SYSCS_INPLACE_COMPRESS_TABLE(?,?,?,?,?)");
            cs.setString(1, schemaName);
            cs.setString(2, tableName);
            cs.setInt(3, purgeRows ? 1 : 0);
            cs.setInt(4, defragmentRows ? 1 : 0);
            cs.setInt(5, truncateEnd ? 1 : 0);
            cs.execute();
        } finally {
            if (cs != null) {
                cs.close();
            }
        }
    }

    public boolean SYSCS_CHECK_TABLE(String catalogName, String schemaName, String tableName) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = cnx.prepareStatement("SELECT " +
                    " SYSCS_UTIL.SYSCS_CHECK_TABLE(?, tablename)" +
                    " FROM sys.sysschemas s, sys.systables t" +
                    " WHERE s.schemaname = ? AND s.schemaid = t.schemaid" +
                    " and tablename=?");
            ps.setString(1, schemaName);
            ps.setString(2, schemaName);
            ps.setString(3, tableName);
            try {
                rs = ps.executeQuery();
                if (rs.next()) {
                    int v = rs.getInt(1);
                    return v != 0;
                }
            } catch (SQLException nonConstsentException) {
                //do nothing
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
        return false;
    }

}