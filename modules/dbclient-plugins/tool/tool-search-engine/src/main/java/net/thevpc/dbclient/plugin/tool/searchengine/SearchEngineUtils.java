package net.thevpc.dbclient.plugin.tool.searchengine;

import net.thevpc.dbclient.api.sql.DBCConnection;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)  alias vpc
 * @creationtime 2009/08/12 11:58:34
 */
public final class SearchEngineUtils {
    public static String buildName(DBCConnection d, String... parts) {
        String[] parts2 = new String[parts.length];
        for (int i = 0; i < parts.length; i++) {
            parts2[i] = d.getEscapedName(parts[i]);

        }
        return buildName(parts2);
    }

    public static String buildName(String... parts) {
        StringBuilder sb = new StringBuilder();
        for (String part : parts) {
            if (part != null && part.length() > 0) {
                if (sb.length() > 0) {
                    sb.append(".");
                }
                sb.append(part);
            }
        }
        return sb.toString();
    }
}
