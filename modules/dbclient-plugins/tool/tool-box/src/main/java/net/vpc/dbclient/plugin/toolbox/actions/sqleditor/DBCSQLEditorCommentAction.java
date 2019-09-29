package net.vpc.dbclient.plugin.toolbox.actions.sqleditor;

import net.vpc.dbclient.api.sessionmanager.DBCSQLEditor;
import net.vpc.dbclient.api.sessionmanager.DBCSQLEditorAction;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class DBCSQLEditorCommentAction extends DBCSQLEditorAction {

    public DBCSQLEditorCommentAction() {
        super("SQLEditorPane.Action.Comment", "/", KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
    }

    public void actionPerformedImpl(ActionEvent e) {
        actionComment();
    }

    public void actionComment() {
        DBCSQLEditor editor1 = getEditor();
        int car0 = editor1.getCaretPosition();
        int i00 = editor1.getSelectionStart();
        int i10 = editor1.getSelectionEnd();
        actionSelectLine();
        int i0 = editor1.getSelectionStart();
        int i1 = editor1.getSelectionEnd();
        try {
            String toComment = editor1.getText(i0, i1 - i0);
            String[] s = toComment.split("\n");
            StringBuilder sb = new StringBuilder();
            int offset = 0;
            for (int i = 0; i < s.length; i++) {
                if (s[i].startsWith("--")) {
                    sb.append(s[i].substring(2));
                    offset = offset - 2;
                } else {
                    sb.append("--").append(s[i]);
                    offset = offset + 2;
                }
                if (i < s.length - 1) {
                    sb.append("\n");
                }
            }
            editor1.replaceSelection(sb.toString());
            if (i10 > i00) {
                editor1.select(i0, i0 + sb.toString().length());
            } else {
                editor1.setCaretPosition(car0 + offset);
            }
        } catch (BadLocationException e) {
            //ignore
        }
    }

    public void actionSelectLine() {
//        queryTextArea.
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
//        if(i0>=0 && i0>=0){
//            if(i0==0 || queryTextArea.getText(i0-1,1).equals('\'))
//        }
    }


}