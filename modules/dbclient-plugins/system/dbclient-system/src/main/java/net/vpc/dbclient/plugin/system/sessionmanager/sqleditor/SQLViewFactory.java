package net.vpc.dbclient.plugin.system.sessionmanager.sqleditor;

import javax.swing.text.Element;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)  alias vpc
 * @creationtime 2009/08/08 12:53:10
 */
public class SQLViewFactory implements ViewFactory {
    private SQLEditorKit sqlEditorKit;

    public SQLViewFactory(SQLEditorKit sqlEditorKit) {
        this.sqlEditorKit = sqlEditorKit;
    }

    public View create(Element elem) {
        return new SQLCodeView(sqlEditorKit, elem);
    }

    public SQLEditorKit getSqlEditorKit() {
        return sqlEditorKit;
    }

}
