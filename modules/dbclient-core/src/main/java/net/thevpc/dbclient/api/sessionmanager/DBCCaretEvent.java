package net.thevpc.dbclient.api.sessionmanager;

import java.util.EventObject;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)  alias vpc
 * @creationtime 2009/08/08 18:07:13
 */
public abstract class DBCCaretEvent extends EventObject {

    /**
     * Creates a new CaretEvent object.
     *
     * @param source the object responsible for the event
     */
    public DBCCaretEvent(Object source) {
        super(source);
    }

    /**
     * Fetches the location of the caret.
     *
     * @return the dot >= 0
     */
    public abstract int getDot();

    /**
     * Fetches the location of other end of a logical
     * selection.  If there is no selection, this
     * will be the same as dot.
     *
     * @return the mark >= 0
     */
    public abstract int getMark();

    public abstract int getRow();

    public abstract int getColumn();
}

