package net.vpc.dbclient.ideplugins.ideaplugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataConstants;
import com.intellij.openapi.project.Project;
import net.vpc.dbclient.api.DBCLookAndFeelSupport;
import net.vpc.dbclient.api.wm.DBCWindow;
import net.vpc.dbclient.util.DBCLookAndFeelSupportNone;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 6 dec. 2006 17:22:47
 */
public class DBClientIdeaPlugin extends AnAction {

    public void actionPerformed(AnActionEvent e) {
        Project project = (Project) e.getDataContext().getData(DataConstants.PROJECT);
        DBClientProjectComponent component = DBClientProjectComponent.getInstance(project);
        if(component!=null){
            component.getApplication().getSessionChooserWindow().showWindow();
        }
    }

}
