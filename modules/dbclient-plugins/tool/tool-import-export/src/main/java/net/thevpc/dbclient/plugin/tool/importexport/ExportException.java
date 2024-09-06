package net.thevpc.dbclient.plugin.tool.importexport;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)  alias vpc
 * @creationtime 2009/08/07 18:11:51
 */
public class ExportException extends Exception {
    public ExportException() {
    }

    public ExportException(String message) {
        super(message);
    }

    public ExportException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExportException(Throwable cause) {
        super(cause);
    }
}
