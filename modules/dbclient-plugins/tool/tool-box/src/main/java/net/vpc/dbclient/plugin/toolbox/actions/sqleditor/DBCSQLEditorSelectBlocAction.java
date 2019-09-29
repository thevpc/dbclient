package net.vpc.dbclient.plugin.toolbox.actions.sqleditor;

import net.vpc.dbclient.api.sessionmanager.DBCSQLEditor;
import net.vpc.dbclient.api.sessionmanager.DBCSQLEditorAction;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class DBCSQLEditorSelectBlocAction extends DBCSQLEditorAction {

    public DBCSQLEditorSelectBlocAction() {
        super("SQLEditorPane.Action.Select.Bloc", "/SelectFolder", KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_MASK));
    }

    public void actionPerformedImpl(ActionEvent e) {
        actionSelectBloc();
    }

    public void actionSelectBloc() {
        DBCSQLEditor editor1 = getEditor();
        int i = editor1.getCaretPosition();
        if (i < 0) {
            return;
        }
        int len = editor1.getDocument().getLength();
        char c;
        try {
            /*if (i - 1 >= 0 && this.getText(i, 1).charAt(0) == ')') {
                i = i - 1;
            } else */
            if (i + 1 < len && editor1.getText(i, 1).charAt(0) == '(') {
                i = i + 1;
            }
            int i0 = i - 1;
            int i1 = i;
            int parAfter = 0;
            int parBefore = 0;
            while (i0 >= 0 && parBefore >= 0) {
                c = editor1.getText(i0, 1).charAt(0);
                if (c == '(') {
                    parBefore--;
                } else if (c == ')') {
                    parBefore++;
                }
                if (parBefore < 0) {
                    break;
                }
                i0--;
            }
            while (i1 < len && parAfter >= 0) {
                c = editor1.getText(i1, 1).charAt(0);
                if (c == '(') {
                    parAfter++;
                } else if (c == ')') {
                    parAfter--;
                }
//                if(parAfter < 0){
//                    break;
//                }
                i1++;
            }
            editor1.select(i0, i1);
        } catch (BadLocationException e) {
            //getLog().error(e);
        }
    }


}