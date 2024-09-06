package net.thevpc.dbclient.plugin.toolbox.actions.sqleditor;

import net.thevpc.dbclient.api.sessionmanager.DBCSQLEditor;
import net.thevpc.dbclient.api.sessionmanager.DBCSQLEditorAction;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class DBCSQLEditorDuplicateAction extends DBCSQLEditorAction {

    public DBCSQLEditorDuplicateAction() {
        super("SQLEditorPane.Action.Duplicate.Line", "/", KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_MASK));
    }

    public void actionPerformedImpl(ActionEvent e) {
        actionErase();
    }

    public void actionDuplicate() {
        DBCSQLEditor editor1 = getEditor();
        int i0 = editor1.getSelectionStart();
        int i1 = editor1.getSelectionEnd();
        if (i1 <= i0) {
            actionSelectLine();
            i0 = editor1.getSelectionStart();
            i1 = editor1.getSelectionEnd();
            try {
                String toComment = editor1.getText(i0, i1 - i0);
                int len = toComment.length();
                editor1.replaceSelection(toComment + '\n' + toComment);
                editor1.select(i0 + len, i0 + len * 2 + 1);
            } catch (BadLocationException e) {
                //ignore
            }
        } else {
            i0 = editor1.getSelectionStart();
            i1 = editor1.getSelectionEnd();
            try {
                String toComment = editor1.getText(i0, i1 - i0);
                int len = toComment.length();
                editor1.replaceSelection(toComment + toComment);
                editor1.select(i0 + len, i0 + len * 2);
            } catch (BadLocationException e) {
                //ignore
            }
        }
    }

    public void actionErase() {
        actionSelectLine();
        getEditor().setSelectionEnd(getEditor().getSelectionEnd() + 1);
        getEditor().cut();
    }

    public void actionSelectLine() {
        DBCSQLEditor editor1 = getEditor();
        int i0 = editor1.getSelectionStart();
        int i1 = editor1.getSelectionEnd();
        if (i0 < 0) {
            i0 = editor1.getCaretPosition();
            i1 = editor1.getCaretPosition();
        }
        try {
            if (editor1.getText(i0, 1).equals("\n")) {
                i0--;
            }
            while (i0 >= 0 && !editor1.getText(i0, 1).equals("\n")) {
                i0--;
            }
            i0++;
            while (i1 < editor1.getText().length() && !editor1.getText(i1, 1).equals("\n")) {
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