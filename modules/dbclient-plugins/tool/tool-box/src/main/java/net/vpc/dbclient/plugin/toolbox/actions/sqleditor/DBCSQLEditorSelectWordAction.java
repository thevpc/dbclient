package net.vpc.dbclient.plugin.toolbox.actions.sqleditor;

import net.vpc.dbclient.api.sessionmanager.DBCSQLEditor;
import net.vpc.dbclient.api.sessionmanager.DBCSQLEditorAction;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class DBCSQLEditorSelectWordAction extends DBCSQLEditorAction {

    public DBCSQLEditorSelectWordAction() {
        super("SQLEditorPane.Action.Select.Word", "/SelectFolder", KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_MASK));
    }

    public void actionPerformedImpl(ActionEvent e) {
        actionSelectWord();
    }

    public void actionSelectWord() {
//        queryTextArea.
        DBCSQLEditor editor1 = getEditor();
        int i0 = editor1.getSelectionStart();
        int i1 = editor1.getSelectionEnd();
        if (i0 < 0) {
            i0 = editor1.getCaretPosition();
            i1 = editor1.getCaretPosition();
        }
        try {
            if (!Character.isJavaIdentifierPart(editor1.getText(i0, 1).charAt(0))) {
                i0--;
            }
            while (i0 >= 0 && Character.isJavaIdentifierPart(editor1.getText(i0, 1).charAt(0))) {
                i0--;
            }
            i0++;
            while (i1 < editor1.getText().length() && Character.isJavaIdentifierPart(editor1.getText(i1, 1).charAt(0))) {
                i1++;
            }
            if (i1 < i0) {
                i1 = i0;
            }
            editor1.select(i0, i1);
        } catch (BadLocationException e) {
            //ignore
        }
    }

}