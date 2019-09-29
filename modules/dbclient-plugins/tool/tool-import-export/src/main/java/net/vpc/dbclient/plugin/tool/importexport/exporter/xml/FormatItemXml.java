package net.vpc.dbclient.plugin.tool.importexport.exporter.xml;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)  alias vpc
 * @creationtime 2009/08/07 20:05:43
 */
public final class FormatItemXml {
    private Object format;
    private int max = -1;
    private boolean attribute;

    public FormatItemXml() {
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

    public boolean isAttribute() {
        return attribute;
    }

    public void setAttribute(boolean attribute) {
        this.attribute = attribute;
    }
}