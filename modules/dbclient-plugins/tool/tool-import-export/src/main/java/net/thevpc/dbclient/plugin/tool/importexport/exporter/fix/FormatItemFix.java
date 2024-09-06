package net.thevpc.dbclient.plugin.tool.importexport.exporter.fix;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)  alias vpc
 * @creationtime 2009/08/07 20:05:43
 */
public final class FormatItemFix {
    private Object format;
    private int max = -1;
    private int min = -1;
    private FixAlign align = FixAlign.LEFT;

    public FormatItemFix() {
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

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public FixAlign getAlign() {
        return align;
    }

    public void setAlign(FixAlign align) {
        this.align = align;
    }
}