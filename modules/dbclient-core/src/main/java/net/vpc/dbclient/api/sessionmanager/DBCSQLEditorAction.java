package net.vpc.dbclient.api.sessionmanager;

import net.vpc.dbclient.api.actionmanager.DBCActionLocation;
import net.vpc.dbclient.api.actionmanager.DBCSessionAction;
import net.vpc.prs.plugin.Extension;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: vpc
 * Date: 28 août 2009
 * Time: 23:25:30
 * To change this template use File | Settings | File Templates.
 */
@Extension(group = "ui.session", customizable = false)
public abstract class DBCSQLEditorAction extends DBCSessionAction{
    private DBCSQLEditor editor;
    public DBCSQLEditorAction(String name, String path,KeyStroke... keyStroke) {
        super(name);
        putValue(ACTION_COMMAND_KEY, name);
        putValue("KeyStroke", keyStroke);
        putValue(Action.ACCELERATOR_KEY, keyStroke.length == 0 ? null : keyStroke[0]);
        addLocationPath(DBCActionLocation.POPUP, path);
    }

    @Override
    public void addLocationPath(DBCActionLocation id, String path) {
        switch (id){
            case MENUBAR:
            case STATUSBAR:
            case TOOLBAR:{
                throw new IllegalArgumentException("DBCSQLEditorAction supports only POPUP locations");
            }
        }
        super.addLocationPath(id, path);
    }

    public void init(DBCSQLEditor editor){
        this.editor=editor;
    }

    public DBCSQLEditor getEditor() {
        return editor;
    }

    public KeyStroke[] getKeyStrokes() {
        return (KeyStroke[]) getValue("KeyStroke");
    }

}
