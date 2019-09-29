package net.vpc.dbclient.plugin.tool.importexport.exporter.csv;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)  alias vpc
 * @creationtime 2009/08/07 20:05:43
 */
public final class FormatItemCsv {
    private Object format;
    private int max = -1;

    public FormatItemCsv() {
    }

    public Object getFormat() {
        return format;
    }

    public void setFormat(Object format) {
        this.format = format;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
