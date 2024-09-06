package net.thevpc.dbclient.plugin.toolbox.actions.sqleditor;

import net.thevpc.dbclient.api.sessionmanager.DBCSQLEditor;
import net.thevpc.dbclient.api.sessionmanager.DBCSQLEditorAction;
import net.thevpc.dbclient.api.sql.parser.SQLParser;
import net.thevpc.dbclient.api.sql.parser.SQLStatement;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.StringReader;

public class DBCSQLEditorSelectStatementAction extends DBCSQLEditorAction {

    public DBCSQLEditorSelectStatementAction() {
        super("SQLEditorPane.Action.Select.Statement", "/SelectFolder", KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_MASK));
    }

    public void actionPerformedImpl(ActionEvent e) {
        actionSelectStatement();
    }

    public void actionSelectStatement() {
        DBCSQLEditor editor1 = getEditor();
        int i = editor1.getCaretPosition();
        if (i < 0) {
            return;
        }
        SQLParser p = editor1.getParser().clone();
        String sql = editor1.getSQL();
        p.setDocument(new StringReader(sql));
        SQLStatement statement = null;
        while ((statement = p.readStatement()) != null) {
            int startIndex = statement.getCharStartIndex();
            int endIndex = statement.getCharEndIndex();
            if ((i >= startIndex && i < endIndex) || i == sql.length() && i == endIndex) {
                editor1.select(startIndex, endIndex);
            }
        }
        //getParser().
    }

}