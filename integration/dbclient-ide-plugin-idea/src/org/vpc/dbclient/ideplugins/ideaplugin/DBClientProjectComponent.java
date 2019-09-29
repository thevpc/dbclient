package net.vpc.dbclient.ideplugins.ideaplugin;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindowManager;
import net.vpc.dbclient.api.DBCApplication;
import net.vpc.dbclient.api.DBCLookAndFeelSupport;
import net.vpc.dbclient.api.windowmanager.DBCWindow;
import net.vpc.dbclient.api.session.DBCSessionLayoutManager;
import net.vpc.dbclient.api.session.DBCSessionLayoutManagerExploded;
import net.vpc.dbclient.api.wm.DBCWindow;
import net.vpc.dbclient.util.DBCLookAndFeelSupportNone;

import java.util.HashMap;
import net.vpc.prs.componentmanager.ComponentManager.ComponentImplementation;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 6 dec. 2006 21:53:05
 */
public class DBClientProjectComponent implements ProjectComponent {
    private static HashMap<Project,DBClientProjectComponent> map=new HashMap<Project, DBClientProjectComponent>();

    private Project project;
    private DBCApplication dbClient;

    public DBClientProjectComponent(Project project) {
        this.project = project;
    }

    public void initComponent() {
        map.put(project,this);
        try {
            DBCApplication.showSplashScreen();
            dbClient = new DBCApplication();
            dbClient.setQuitOption(DBCApplication.QuitOption.DO_NOTHING);
            dbClient.getFactory().getExtension(DBCLookAndFeelSupport.class).setDefaultImpl(new Implementation(DBCLookAndFeelSupportNone.class, "PLAF disabled", null, null));
            dbClient.getFactory().getExtension(DBCWindow.class).setDefaultImpl(new ComponentImplementation(IdeaWindow.class,"Idea Window",null,null));
            dbClient.getFactory().getExtension(DBCSessionLayoutManager.class).setDefaultImpl(new ComponentImplementation(IdeaDBCSessionLayoutManager.class,"Idea Window",null,null));
            dbClient.getClientProperties().put("project", project);
            dbClient.getClientProperties().put("DBClientProjectComponent", this);
            dbClient.start(monitor);
        } catch (Throwable e) {
            DBCApplication.hideSplashScreen();
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
        }finally{
            DBCApplication.hideSplashScreen();
        }
    }

    public void disposeComponent() {
        try {
            dbClient.setQuitOption(DBCApplication.QuitOption.DISPOSE);
            dbClient.quit();
        } catch (Throwable e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public String getComponentName() {
        return "DBClientProjectComponent";
    }

    public void projectOpened() {
    }

    public void projectClosed() {
        map.remove(project);
    }

    public Project getProject() {
        return project;
    }

    public static DBClientProjectComponent getInstance(Project project){
        return map.get(project);
    }


    public DBCApplication getApplication() {
        return dbClient;
    }
}
