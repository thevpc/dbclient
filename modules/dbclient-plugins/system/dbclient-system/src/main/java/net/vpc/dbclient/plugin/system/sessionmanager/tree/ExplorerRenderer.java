/**
 * ====================================================================
 *             DBClient yet another Jdbc client tool
 *
 * DBClient is a new Open Source Tool for connecting to jdbc
 * compliant relational databases. Specific extensions will take care of
 * each RDBMS implementation.
 *
 * Copyright (C) 2006-2008 Taha BEN SALAH
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 * ====================================================================
 */

package net.vpc.dbclient.plugin.system.sessionmanager.tree;

import net.vpc.dbclient.api.DBCSession;
import net.vpc.dbclient.api.configmanager.DBCConfigUpdatedEvent;
import net.vpc.dbclient.api.configmanager.DBCConfigUpdatedListener;
import net.vpc.dbclient.api.sessionmanager.DBCExplorerNode;
import net.vpc.dbclient.api.sessionmanager.DBCSessionView;
import net.vpc.dbclient.api.sql.DBObjectFilter;
import net.vpc.dbclient.api.sql.objects.DBObject;
import net.vpc.dbclient.api.viewmanager.DBCComponentFormat;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)
 * @creationtime 13 juil. 2005 14:33:23
 */
public class ExplorerRenderer extends DefaultTreeCellRenderer implements DBCConfigUpdatedListener {
    DBCSession session;
    DBCSessionView view;

    private static boolean debug = Boolean.getBoolean("debug");
    DBCComponentFormat formatDefault;
    DBCComponentFormat formatMain;
    DBCComponentFormat formatExcluded;
    DBCComponentFormat formatUnloaded;
    DBCComponentFormat formatSystem;
    private final static DBCComponentFormat formatDefault0 = DBCComponentFormat.DEFAULT_FORMAT;
    private final static DBCComponentFormat formatMain0 = new DBCComponentFormat(null, null, null, null, Font.BOLD);
    private final static DBCComponentFormat formatExcluded0 = new DBCComponentFormat(Color.RED, null, null, null, null);
    private final static DBCComponentFormat formatUnloaded0 = new DBCComponentFormat(Color.GRAY, null, null, null, null);
    private final static DBCComponentFormat formatSystem0 = new DBCComponentFormat(Color.RED.darker().darker(), null, null, null, null);
    Font font0;
    Color fore0;
    Color bkg0;

    public ExplorerRenderer(DBCSession session) {
        super();
        this.session = session;
        session.getConfig().addConfigUpdatedListener(this);
        view = session.getView();
        formatDefault = formatDefault0;
        formatMain = formatMain0;
        formatExcluded = formatExcluded0;
        formatUnloaded = formatUnloaded0;
        formatSystem = formatSystem0;
        configUpdated(null);
    }

    public void configUpdated(DBCConfigUpdatedEvent event) {
        formatDefault = session.getConfig().getComponentFormatProperty("ExplorerRenderer.formatDefault", formatDefault0);
        formatMain = session.getConfig().getComponentFormatProperty("ExplorerRenderer.formatMain", formatMain0);
        formatExcluded = session.getConfig().getComponentFormatProperty("ExplorerRenderer.formatExcluded", formatExcluded0);
        formatUnloaded = session.getConfig().getComponentFormatProperty("ExplorerRenderer.formatUnloaded", formatUnloaded0);
        formatSystem = session.getConfig().getComponentFormatProperty("ExplorerRenderer.formatSystem", formatSystem0);
        stylesChanged();
        if (event!=null) {
            session.getView().getExplorer().getTreeComponent().repaint();
        }
    }

//    private DBCComponentFormat getComponentFormat(String id){
//
//    }

    public void stylesChanged() {
//        font0 = null;
        compiled = false;
    }

    private void compile() {
        if (!compiled) {
            if (font0 == null) {
                font0 = getFont();
                fore0 = getForeground();
                bkg0 = getBackground();
            }
            formatDefault.compile(bkg0, fore0, font0);
            formatMain.compile(bkg0, fore0, font0);
            formatExcluded.compile(bkg0, fore0, font0);
            formatUnloaded.compile(bkg0, fore0, font0);
            formatSystem.compile(bkg0, fore0, font0);
            compiled = true;
        }
    }

    boolean compiled = false;

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        DBCExplorerNode n = (value != null && (value instanceof DBCExplorerNode)) ? ((DBCExplorerNode) value) : null;
        DBObject o = (value != null && (value instanceof DBCExplorerNode)) ? ((DBCExplorerNode) value).getDBObject() : null;
        super.getTreeCellRendererComponent(tree,
                o != null ? view.getObjectTreeName(o) :
                        value, sel, expanded, leaf, row, hasFocus);
        compile();
        Icon image = o == null ? null : view.getObjectIcon(o);
        if (image == null) {
            image = session == null ? null : view.getSession().getView().getIconSet().getIconR("Var");
        }
        setAllIcons(image);
        if (o != null && view.isDefaultObject(o)) {
            formatMain.applyTo(this);
        } else if (o != null && !(o).getStatus().equals(DBObjectFilter.Status.ACCEPT)) {
            formatExcluded.applyTo(this);
        } else if (o != null && !o.isLoaded()) {
            formatUnloaded.applyTo(this);
        } else if (o != null && o.isSystemObject()) {
            formatSystem.applyTo(this);
        } else {
            formatDefault.applyTo(this);
        }
        String s = getText();
        if (s.length() == 0) {

            try {
                setText(session.getView().getMessageSet().get("Tree.NoName"));
            } catch (Exception e) {
                //e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        String dbgString = (!debug || o == null || o.isLeaf()) ? "" : (o.isLoaded() ? (" " + n.getChildCount() + "/" + o.getChildCount()) : " (loading ...)") + (o != null ? (" " + o.getType() + "/" + o.getClass().getSimpleName()) : "");
        setText(getText() + dbgString);
//        setFont(en.isLoaded()?bold:plain);
        return this;
    }

    public void setAllIcons(Icon i) {
        setLeafIcon(i);
        setOpenIcon(i);
        setClosedIcon(i);
        setIcon(i);
    }


    public DBCComponentFormat getFormatMain() {
        return formatMain;
    }

    public void setFormatMain(DBCComponentFormat formatMain) {
        this.formatMain = formatMain;
        stylesChanged();
    }

    public DBCComponentFormat getFormatExcluded() {
        return formatExcluded;
    }

    public void setFormatExcluded(DBCComponentFormat formatExcluded) {
        this.formatExcluded = formatExcluded;
        stylesChanged();
    }

    public DBCComponentFormat getFormatSystem() {
        return formatSystem;
    }

    public void setFormatSystem(DBCComponentFormat formatSystem) {
        this.formatSystem = formatSystem;
        stylesChanged();
    }

    public DBCComponentFormat getFormatUnloaded() {
        return formatUnloaded;
    }

    public void setFormatUnloaded(DBCComponentFormat formatUnloaded) {
        this.formatUnloaded = formatUnloaded;
        stylesChanged();
    }
}
