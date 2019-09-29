/**
 * ==================================================================== DBClient
 * yet another Jdbc client tool
 *
 * DBClient is a new Open Source Tool for connecting to jdbc compliant
 * relational databases. Specific extensions will take care of each RDBMS
 * implementation.
 *
 * Copyright (C) 2006-2008 Taha BEN SALAH
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 * ====================================================================
 */
package net.vpc.dbclient.api.actionmanager;

import net.vpc.dbclient.api.DBCSession;
import net.vpc.dbclient.api.sessionmanager.DBCSessionFilter;
import net.vpc.swingext.PRSManager;
import net.vpc.prs.ResourceSetHolder;
import net.vpc.swingext.iconset.DefaultActionIconSetUpdater;
import net.vpc.prs.iconset.IconSet;
import net.vpc.swingext.messageset.DefaultActionMessageSetUpdater;
import net.vpc.prs.messageset.MessageSet;
import net.vpc.swingext.dialog.MessageDialogManager;
import net.vpc.swingext.dialog.MessageDialogType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.concurrent.CancellationException;

public abstract class DBClientAction extends AbstractAction implements Comparable<DBClientAction>, ResourceSetHolder, DBCSessionFilter {

    private static String[] STRINGS_0 = new String[0];
    protected boolean synch;
    private String id;
    private String shortId;
    private String before;
    private String after;
    private Map<DBCActionLocation, TreeSet<String>> location = new EnumMap<DBCActionLocation, TreeSet<String>>(DBCActionLocation.class);
    private int index;
    private int position = 0;
    private DBClientActionType type = DBClientActionType.ACTION;
    private String messageId;
    private String iconId;

    public DBClientAction(String id) {
        super("!-UNKNOWN-!");
        setId(id);
        setMessageId(id);
        setIconId(id);
    }
//    protected DBClientAction(String id, DBCPlugin plugin) {
//        this(id, plugin, false);
//    }
//
//    protected DBClientAction(String id, DBCPlugin plugin, boolean synch) {
//        this(id, null, null, plugin, synch);
//    }
//
//    protected DBClientAction(String id, String messageId, String iconId, final DBCPlugin plugin, boolean isSynch) {
//        super("!-" + id + "-!");
//        init(id, messageId, iconId, plugin, isSynch);
//    }
//    public void init(String id, String messageId, String iconId, final DBCPlugin plugin, boolean isSynch) {
//        setId(id);
//        setPlugin(plugin);
//        setSynch(synch);
//        revalidate();
//        setType(ActionType.ACTION);
//        setIconId(iconId);
//        setMessageId(messageId);

    //    }
    public final void setId(String id) {
        this.id = id;
        putValue(ACTION_COMMAND_KEY, id);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return (String) getValue(Action.NAME);
    }

    public Icon getIcon() {
        return (Icon) getValue(Action.SMALL_ICON);
    }

    public void setIcon(Icon icon) {
        putValue(Action.SMALL_ICON, icon);
    }

    /**
     * used as id for folders
     *
     * @return shoort id
     */
    public String getShortId() {
        return shortId == null ? getId() : shortId;
    }

    public void setShortId(String shortId) {
        this.shortId = shortId;
        revalidate();
    }

    public String getMessageId() {
        return messageId;
    }

    public final void setMessageId(String messageId) {
        this.messageId = messageId;
        if (this.messageId == null) {
            PRSManager.removeMessageSetSupport(this);
        } else if (!"".equals(messageId)) {
            PRSManager.addMessageSetSupport(this, messageId, new DefaultActionMessageSetUpdater() {

                @Override
                public void updateResources(Action action, String id, MessageSet messageSet) {
                    revalidate();
                    super.updateResources(action, id, getMessageSet());
                }
            });
        }
        revalidate();
    }

    public boolean acceptSession(DBCSession session) {
        return true;
    }

    public MessageSet getMessageSet() {
        return null;
    }

    public IconSet getIconSet() {
        return null;
    }

    public String getIconId() {
        return iconId;
    }

    public final void setIconId(String iconId) {
        this.iconId = iconId;
        if (this.iconId == null) {
            PRSManager.removeIconSetSupport(this);
        } else if (!"".equals(iconId)) {
            PRSManager.addIconSetSupport(this, iconId, new DefaultActionIconSetUpdater() {

                @Override
                public void updateIconSet(Action action, IconSet iconSet) {
                    super.updateIconSet(action, getIconSet());
                }
            });
        }
        revalidate();
    }
    private boolean revalidating = false;

    private void revalidate() {
        if (revalidating) {
            return;
        }
        revalidating = true;
        try {
            if (type != null) {
                switch (type) {
                    case ACTION:
                    case FOLDER: {
//                        PRSManager.removeSupport(this);
//                        PRSManager.addSupport(this, messageId == null ? getKey() : messageId, iconId == null ? getKey() : iconId);
                        break;
                    }
                    case SEPARATOR: {
//                        PRSManager.removeSupport(this);
                        break;
                    }
                }
            }
            if (Boolean.getBoolean("debug")) {
                StringBuilder sb = new StringBuilder("<table>"
                        + "<tr><td>name" + "</td>" + "<td>" + getActionName() + "</td></tr>"
                        + "<tr><td>id" + "</td>" + "<td>" + getKey() + "</td></tr>"
                        + "<tr><td>position" + "</td>" + "<td>" + getPosition() + "</td></tr>"
                        + "<tr><td>before" + "</td>" + "<td>" + getBefore() + "</td></tr>"
                        + "<tr><td>after" + "</td>" + "<td>" + getAfter() + "</td></tr>"
                        + "<tr><td>type" + "</td>" + "<td>" + getActionType() + "</td></tr>"
                        + "<tr><td>index" + "</td>" + "<td>" + getIndex() + "</td></tr>");
                for (DBCActionLocation actionLocation : DBCActionLocation.values()) {
                    sb.append(
                            "<tr><td>" + actionLocation + "</td>" + "<td>" + getLocationPaths(actionLocation) + "</td></tr>");
                }
                sb.append(
                        "</table>"
                        + "<html>");
                super.putValue(SHORT_DESCRIPTION, sb.toString());
            }
        } finally {
            revalidating = false;
        }
    }

    public Collection<String> getLocationPaths(DBCActionLocation id) {
        TreeSet<String> found = location == null ? null : location.get(id);
        return found == null ? Arrays.asList(STRINGS_0) : Collections.unmodifiableCollection(found);
    }

    public void addLocationPath(DBCActionLocation id, String path) {
        if (path == null || path.length() == 0 || path.contains("//") || !path.startsWith("/") || (path.length() > 1 && path.endsWith("/"))) {
            throw new IllegalArgumentException("Bad path : " + path);
        }
        if (location == null) {
            location = new EnumMap<DBCActionLocation, TreeSet<String>>(DBCActionLocation.class);
        }
        TreeSet<String> found = location.get(id);
        if (found == null) {
            found = new TreeSet<String>();
            location.put(id, found);
        }
        found.add(path);
        revalidate();
    }

    public boolean preActionPerformed(ActionEvent event) {
        return true;
    }

    public MessageDialogManager getDialogManager() {
        return null;
    }

    public final void actionPerformed(final ActionEvent event) {
        if (!preActionPerformed(event)) {
            return;
        }
        if (synch) {
            actionPerformedSynch(event);
        } else {
            actionPerformedAsynch(event);
        }
    }

    protected void actionPerformedSynch(final ActionEvent event) {
        try {
            actionPerformedImpl(event);
        } catch (CancellationException throwable) {
            //do nothing
        } catch (Throwable throwable) {
            getDialogManager().showMessage(null, null, MessageDialogType.ERROR, null, throwable);
        }
    }

    protected void actionPerformedAsynch(final ActionEvent event) {
        new Thread() {

            @Override
            public void run() {
                actionPerformedSynch(event);
            }
        }.start();
    }

    public abstract void actionPerformedImpl(ActionEvent e) throws Throwable;

    public void setSynch(boolean synch) {
        this.synch = synch;
    }

    public boolean isSynch() {
        return synch;
    }

    public String getActionName() {
        return (String) getValue(Action.NAME);
    }

    public String getKey() {
        return (String) getValue(ACTION_COMMAND_KEY);
    }

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
        revalidate();
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
        revalidate();
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
        revalidate();
    }

    public DBClientActionType getActionType() {
        return type;
    }

    public void setType(DBClientActionType type) {
        this.type = type;
        revalidate();
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
        revalidate();
    }

    public int compareTo(DBClientAction o) {
        if (o == null) {
            return 1;
        }
        int x = ((Integer) this.position).compareTo(o.position);
        if (x != 0) {
            return x;
        }
        String compareTo = o.getId();
        if (this.before != null && this.before.equals(compareTo)) {
            return -1;
        }
        if (this.after != null && this.after.equals(compareTo)) {
            return 1;
        }

        if (o.before != null && o.before.equals(compareTo)) {
            return 1;
        }
        if (o.after != null && o.after.equals(compareTo)) {
            return -1;
        }

        return this.index - o.index;
    }

    private JMenu findParent(Component c, DBCActionLocation locType, String id) {
        if (id == null || "/".equals(id)) {
            return null;
        }
        if (c instanceof JMenu) {
            Action a = (Action) ((JComponent) c).getClientProperty("Action");
            if (a != null && a instanceof DBClientAction) {
                DBClientAction aa = (DBClientAction) a;
                if (aa.getActionType().equals(DBClientActionType.FOLDER) && aa.getShortId().equals(id)) {
                    return (JMenu) c;
                }
            }
            if (a == null) {
                String folderName = (String) ((JComponent) c).getClientProperty("FolderName");
                if (folderName != null && folderName.equals(id)) {
                    return (JMenu) c;
                }
            }
        }
        Component[] children = null;
        if (c instanceof JMenu) {
            children = ((JMenu) c).getMenuComponents();
        } else if (c instanceof Container) {
            children = ((Container) c).getComponents();
        }
        if (children != null) {
            for (Component aChildren : children) {
                JMenu m = findParent(aChildren, locType, id);
                if (m != null) {
                    return m;
                }
            }
        }
        return null;
    }

    private static JButton prepareToolbarButton(JButton button) {
        button.setFont(button.getFont().deriveFont(Font.PLAIN, button.getFont().getSize() * 0.8f));
        return button;
    }

    private static JToggleButton prepareToolbarButton(JToggleButton button) {
        button.setFont(button.getFont().deriveFont(Font.PLAIN, button.getFont().getSize() * 0.8f));
        return button;
    }

//    public void addToComponent(Component component, DBCActionLocation locType, String path) {
//        for (String locPathId : getLocationPaths(locType)) {
//            if (path.equals(locPathId)) {
//                switch (type) {
//                    case ACTION: {
//                        if (component instanceof JPopupMenu) {
//                            ((JPopupMenu) component).add(this);
//                        } else if (component instanceof JMenu) {
//                            ((JMenu) component).add(this);
//                        } else if (component instanceof JToolBar) {
//                            JButton bb = ((JToolBar) component).add(this);
//                            prepareToolbarButton(bb);
//                            PRSManager.setShortNamePreferred(bb, true);
//                        } else {
//                            System.err.println("[DBClientAction] (id=" + getKey() + ";location=" + locType + ";path=" + locPathId + ") not found, probably folder does not exist");
//                        //throw new IllegalArgumentException();
//                        }
//                        break;
//                    }
//                    case FOLDER: {
//                        final JMenu mi = new JMenuFolder(this);
//                        if (component instanceof JPopupMenu) {
//                            ((JPopupMenu) component).add(mi);
//                        } else if (component instanceof JMenu) {
//                            ((JMenu) component).add(mi);
//                        } else if (component instanceof JMenuBar) {
//                            ((JMenuBar) component).add(mi);
//                        } else if (component instanceof JToolBar) {
//                            ((JToolBar) component).add(mi);
//                        } else {
//                            System.err.println("[DBClientAction] (id=" + getKey() + ";location=" + locType + ";path=" + locPathId + ") not found, probably folder does not exist");
//                        //throw new IllegalArgumentException();
//                        }
//                        break;
//                    }
//                    case SEPARATOR: {
//                        if (component instanceof JPopupMenu) {
//                            ((JPopupMenu) component).addSeparator();
//                        } else if (component instanceof JMenu) {
//                            ((JMenu) component).addSeparator();
//                        } else if (component instanceof JToolBar) {
//                            ((JToolBar) component).addSeparator();
//                        } else {
//                            System.err.println("[DBClientAction] (id=" + getKey() + ";location=" + locType + ";path=" + locPathId + ") not found, probably folder does not exist");
//                        }
//                        break;
//                    }
//                }
//            }
//        }
    //    }
    public Component addToComponent(Component component) {
        switch (type) {
            case ACTION: {
                if (component instanceof JPopupMenu) {
                    return ((JPopupMenu) component).add(this);
                } else if (component instanceof JMenu) {
                    return ((JMenu) component).add(this);
                } else if (component instanceof JToolBar) {
                    JButton bb = ((JToolBar) component).add(this);
                    prepareToolbarButton(bb);
                    PRSManager.setShortNamePreferred(bb, true);
                    return bb;
                } else {
                    System.err.println("[DBClientAction] (id=" + getKey() + " for component type (" + component.getClass().getName() + ") unsupported");
                    //throw new IllegalArgumentException();
                }
                break;
            }
            case FOLDER: {
                final JMenu mi = new DBClientActionMenuFolder(this);
                if (component instanceof JPopupMenu) {
                    return ((JPopupMenu) component).add(mi);
                } else if (component instanceof JMenu) {
                    return ((JMenu) component).add(mi);
                } else if (component instanceof JMenuBar) {
                    return ((JMenuBar) component).add(mi);
                } else if (component instanceof JToolBar) {
                    return ((JToolBar) component).add(mi);
                } else {
                    System.err.println("[DBClientAction] (id=" + getKey() + " for component type (" + component.getClass().getName() + ") unsupported");
                    //throw new IllegalArgumentException();
                }
                break;
            }
            case SEPARATOR: {
                if (component instanceof JPopupMenu) {
                    Component r = new JPopupMenu.Separator();
                    ((JPopupMenu) component).add(r);
                    return r;
                } else if (component instanceof JMenu) {
                    ((JMenu) component).addSeparator();
                    return null;
                } else if (component instanceof JToolBar) {
                    ((JToolBar) component).addSeparator();
                    return null;
                } else {
                    System.err.println("[DBClientAction] (id=" + getKey() + " for component type (" + component.getClass().getName() + ") unsupported");
                }
                break;
            }
        }
        return null;
    }

    @Override
    public void putValue(String key, Object newValue) {
        super.putValue(key, newValue);
        revalidate();
    }
}
