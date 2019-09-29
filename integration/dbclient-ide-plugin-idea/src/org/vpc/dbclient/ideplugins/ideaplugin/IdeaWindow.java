package net.vpc.dbclient.ideplugins.ideaplugin;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import net.vpc.dbclient.api.wm.AbstractDBCWindow;
import net.vpc.dbclient.api.windowmanager.AbstractDBCWindow;

import javax.swing.*;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 10 dec. 2006 00:58:00
 */
public class IdeaWindow extends AbstractDBCWindow {
    ToolWindow window;

    public IdeaWindow() {
    }


    public void showWindow() {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    if (window == null) {
                        try {
                            Project project = (Project) getApplication().getClientProperties().get("project");
                            switch (getKind()) {
                                case SESSION: {
                                    window = ToolWindowManager.getInstance(project).registerToolWindow("DBC Session", getJComponent(), ToolWindowAnchor.RIGHT);
                                    window.setTitle(getTitle());
                                    break;
                                }
                                case SESSION_WORKSPACE: {
                                    window = ToolWindowManager.getInstance(project).registerToolWindow("DBC Workspace", getJComponent(), ToolWindowAnchor.RIGHT);
                                    window.setTitle(getTitle());
                                    break;
                                }
                                case SESSION_CHOOSER: {
                                    window = ToolWindowManager.getInstance(project).registerToolWindow("DBC Chooser", getJComponent(), ToolWindowAnchor.LEFT);
                                    window.setTitle(getTitle());
                                    break;
                                }
                                case SESSION_EXPLORER:{
                                    window = ToolWindowManager.getInstance(project).registerToolWindow("DBC Explorer", getJComponent(), ToolWindowAnchor.LEFT);
                                    window.setTitle(getTitle());
                                    break;
                                }
                                case SESSION_TRACER:{
                                    window = ToolWindowManager.getInstance(project).registerToolWindow("DBC Trace", getJComponent(), ToolWindowAnchor.BOTTOM);
                                    window.setTitle(getTitle());
                                    break;
                                }
                                default:{
                                    window = ToolWindowManager.getInstance(project).registerToolWindow(getTitle(), getJComponent(), ToolWindowAnchor.BOTTOM);
                                    window.setTitle(getTitle());
                                }
                            }
                            //window.
                        } catch (Throwable e) {
                            e.printStackTrace();
                            JOptionPane.showMessageDialog(null, e);
                        }
                    }
                    window.show(null);
                }
            });
    }

    public void closeWindow() {
    }

    public void hideWindow() {
    }
}
