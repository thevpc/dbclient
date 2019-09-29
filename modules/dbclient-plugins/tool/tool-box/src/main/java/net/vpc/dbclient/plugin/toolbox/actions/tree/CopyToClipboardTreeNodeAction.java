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

package net.vpc.dbclient.plugin.toolbox.actions.tree;

import net.vpc.dbclient.api.actionmanager.DBCActionLocation;
import net.vpc.dbclient.api.actionmanager.DBCTreeNodeAction;
import net.vpc.dbclient.api.sql.objects.DBObject;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class CopyToClipboardTreeNodeAction extends DBCTreeNodeAction implements ActionListener {
    private String contentType;

    public CopyToClipboardTreeNodeAction(String type) {
        super("Action.CopyToClipboard." + type);
        this.contentType = type;
        addLocationPath(DBCActionLocation.POPUP, "/");
        addLocationPath(DBCActionLocation.MENUBAR, "/edit");
    }

    public boolean shouldEnable(DBObject activeNode, DBObject[] selectedNodes) {
        return (activeNode != null && getContent() != null);
    }

    public void actionPerformedImpl(ActionEvent e) throws Throwable {
        Clipboard cp = Toolkit.getDefaultToolkit().getSystemClipboard();
        cp.setContents(new StringSelection(String.valueOf(getContent())), null);
    }

    protected String getContent() {
        DBObject n = getSelectedNode();
        String c = null;
        if ("fullName".equals(contentType)) {
            c = n.getFullName();
        } else if ("name".equals(contentType)) {
            c = n.getName();
        } else if ("sql".equals(contentType)) {
            c = n.getSQL();
        }
        return c;
    }

    public String getContentType() {
        return contentType;
    }

}
