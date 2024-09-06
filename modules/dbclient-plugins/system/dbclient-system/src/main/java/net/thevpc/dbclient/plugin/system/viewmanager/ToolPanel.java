package net.thevpc.dbclient.plugin.system.viewmanager;

import net.thevpc.common.swing.layout.DumbGridBagLayout;
import net.thevpc.dbclient.api.DBCApplication;
import net.thevpc.dbclient.api.DBCApplicationView;
import net.thevpc.dbclient.api.DBCSession;
import net.thevpc.dbclient.api.windowmanager.DBCWindow;
import net.thevpc.common.swing.prs.PRSManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 21 oct. 2007 01:34:02
 */
public class ToolPanel extends JPanel {

    private Component comp;
    private JButton button;
    private DBCApplication application;
    private DBCSession session;
    private JLabel leftLabel;

    public ToolPanel(Component _comp, DBCApplication app, DBCSession sess) {
        super(
                new DumbGridBagLayout().addLine("[img][<===+$$$comp.]").addLine("[   ][-===empty][btn]").setInsets("btn", new Insets(3, 3, 3, 3)));


        this.comp = _comp;
        this.application = app;
        this.session = sess;
        button = PRSManager.createButton("window.hide");
        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                Component c = comp;
                while (c != null) {
                    if (c instanceof DBCWindow) {
                        ((DBCWindow) c).closeWindow();
                        return;
                    }
                    if (c instanceof JComponent) {
                        DBCWindow clientProperty = (DBCWindow) ((JComponent) c).getClientProperty(DBCWindow.COMPONENT_PROPERTY);
                        if (clientProperty != null) {
                            clientProperty.closeWindow();
                            return;
                        }
                    }
                    c = c.getParent();
                }
            }
        });
        if (session == null) {
            application.getView().addPropertyChangeListener(DBCApplicationView.PROPERTY_ARTSET, new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent evt) {
                    artSetChanged();
                }
            });
            application.addPropertyChangeListener(DBCApplicationView.PROPERTY_MESSAGESET, new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent evt) {
                    messageSetChanged();
                }
            });
        } else {
            session.getView().addPropertyChangeListener(DBCApplicationView.PROPERTY_ARTSET, new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent evt) {
                    artSetChanged();
                }
            });
            session.addPropertyChangeListener(DBCApplicationView.PROPERTY_MESSAGESET, new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent evt) {
                    messageSetChanged();
                }
            });
        }
        artSetChanged();
        add(comp, "comp");
        add(Box.createHorizontalGlue(), "empty");
        add(button, "btn");
    }

    public void messageSetChanged() {
        PRSManager.update(button, session == null ? application.getView() : session.getView());
    }

    public void artSetChanged() {
        ImageIcon image = session != null ? session.getView().getArtSet().getArtImage(DBCApplicationView.ARTSET_VERTICAL_BANNER_THIN) : application.getView().getArtSet().getArtImage(DBCApplicationView.ARTSET_VERTICAL_BANNER_THIN);
        if (leftLabel == null) {
            leftLabel = new JLabel();
            add(leftLabel, "img");
        }
        leftLabel.setIcon(image);
        setPreferredSize(new Dimension(700, image == null ? 400 : image.getIconHeight()));
        if (isVisible()) {
            repaint();
        }
        PRSManager.update(button, session == null ? application.getView() : session.getView());
    }
}
