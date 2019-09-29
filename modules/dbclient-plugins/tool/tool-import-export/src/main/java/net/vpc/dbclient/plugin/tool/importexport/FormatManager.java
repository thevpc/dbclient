package net.vpc.dbclient.plugin.tool.importexport;

import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)  alias vpc
 * @creationtime 2009/08/07 21:29:14
 */
public class FormatManager {
    private Map<String, Format> formatters = new HashMap<String, Format>();

    public FormatManager() {
    }

    public String format(Object value, Object format) {
        if (value == null) {
            return "";
        }
        if (value instanceof String) {
            return value.toString();
        }
        Format f = getFormat(value, format);
        return f == null ? String.valueOf(value) : f.format(value);
    }

    public Format getFormat(Object value, Object sformat) {
        Format jformat = null;
        if (sformat != null) {
            String kk = value.getClass().getName() + ":" + sformat;
            jformat = formatters.get(kk);
            if (jformat == null) {
                if (sformat instanceof Format) {
                    formatters.put(kk, (Format) sformat);
                } else if (sformat instanceof String) {
                    String ssformat = (String) sformat;
                    if (value instanceof Number) {
                        jformat = new DecimalFormat(ssformat);
                        formatters.put(kk, jformat);
                    } else if (value instanceof Date) {
                        jformat = new SimpleDateFormat(ssformat);
                    } else {
                        throw new IllegalArgumentException("Unsupported Format " + sformat + " type " + value.getClass());
                    }
                } else {
                    throw new IllegalArgumentException("Unsupported Format " + sformat);
                }
            }
        }
        return jformat;
    }
}
