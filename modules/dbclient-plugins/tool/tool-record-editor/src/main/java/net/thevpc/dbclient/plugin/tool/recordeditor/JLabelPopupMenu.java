package net.thevpc.dbclient.plugin.tool.recordeditor;

import net.thevpc.dbclient.plugin.tool.recordeditor.propeditor.ColumnView;
import net.thevpc.dbclient.plugin.tool.recordeditor.actions.RecordEditorAction;
import net.thevpc.dbclient.api.sql.objects.DBTableColumn;
import net.thevpc.dbclient.api.actionmanager.DBClientAction;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * User: taha
 * Date: 20 aout 2003
 * Time: 17:40:32
 */
public class JLabelPopupMenu extends JPopupMenu{
    private JComponent label;
    private ColumnView columnView;
    private RecordEditorPluginSession pluginSession;
    public JLabelPopupMenu(JComponent label,ColumnView columnView,RecordEditorPluginSession pluginSession) {
        this.label=label;
        this.columnView=columnView;
        this.pluginSession=pluginSession;
        add(new ConfigureFieldAction());
        addSeparator();
        add(new CopyJLabelComponentAction());
        add(new CopyJLabelAction());
        label.addMouseListener(
                new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        if(e.getClickCount()==1 && SwingUtilities.isRightMouseButton(e)){
                           show((JLabel) e.getSource(),e.getX(),e.getY());
                        }else if(e.getClickCount()==1 && SwingUtilities.isLeftMouseButton(e)){
                            JLabelPopupMenu.this.columnView.getEditComponent().requestFocus();
                        }
                    }
                }
        );
    }

    private class ConfigureFieldAction extends RecordEditorAction{
        private ConfigureFieldAction() {
            super("Configure Field...");
        }

        public void actionPerformedImpl(ActionEvent ae) {
            new ColumnProperties(new DBTableColumn[]{columnView.getColumn()}, pluginSession).showConfig();
            columnView.configureLabel();
        }
    }
    private class ConfigureEntityAction extends RecordEditorAction {
        private ConfigureEntityAction() {
            super("Configure Entity...");
        }

        public void actionPerformedImpl(ActionEvent ae) {
            new ColumnProperties(new DBTableColumn[]{columnView.getColumn()}, pluginSession).showConfig();
            columnView.getTableView().getTable();
        }
    }

    private class CopyJLabelAction extends RecordEditorAction{
        private CopyJLabelAction() {
            super("Copy Text...");
        }

        public void actionPerformedImpl(ActionEvent ae) {
            Clipboard cp = Toolkit.getDefaultToolkit().getSystemClipboard();
            String value="";
            if(label instanceof JLabel){
                value=((JLabel)label).getText();
            }else if(label instanceof AbstractButton){
                value=((AbstractButton)label).getText();
            }
            cp.setContents(new StringSelection(String.valueOf(value)), null);
        }
    }

    private class CopyJLabelComponentAction extends RecordEditorAction{
        private CopyJLabelComponentAction() {
            super("Copy Name...");
        }

        public void actionPerformedImpl(ActionEvent ae) {
            Clipboard cp = Toolkit.getDefaultToolkit().getSystemClipboard();
            cp.setContents(new StringSelection(columnView.getColumn().getName()), null);
        }
    }

}
