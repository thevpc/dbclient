package net.thevpc.dbclient.plugin.toolbox.actions.sqleditor;

import net.thevpc.dbclient.api.sessionmanager.DBCSQLEditor;
import net.thevpc.dbclient.api.sessionmanager.DBCSQLEditorAction;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class DBCSQLEditorSelectLineAction extends DBCSQLEditorAction {

    public DBCSQLEditorSelectLineAction() {
        super("SQLEditorPane.Action.Select.Line", "/SelectFolder", KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK));
    }

    public void actionPerformedImpl(ActionEvent e) {
        actionSelectLine();
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