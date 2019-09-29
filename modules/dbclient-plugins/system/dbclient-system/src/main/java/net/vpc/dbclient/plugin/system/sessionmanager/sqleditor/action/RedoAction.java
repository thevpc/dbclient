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

package net.vpc.dbclient.plugin.system.sessionmanager.sqleditor.action;

import javax.swing.*;
import javax.swing.undo.CannotRedoException;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RedoAction extends AbstractAction {
    private UndoRedoSupport support;

    public RedoAction() {
        super("Redo");
        setEnabled(false);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            support.getUndoManager().redo();
        } catch (CannotRedoException ex) {
            Logger.getLogger(RedoAction.class.getName()).log(Level.SEVERE,"Unable to redo",ex);
        }
        update();
        support.getUndoAction().update();
    }

    protected void update() {
        if (support.getUndoManager().canRedo()) {
            setEnabled(true);
            putValue(Action.NAME, support.getUndoManager().getRedoPresentationName());
        } else {
            setEnabled(false);
            putValue(Action.NAME, "Redo");
        }
    }

    public UndoRedoSupport getSupport() {
        return support;
    }

    public void setSupport(UndoRedoSupport support) {
        this.support = support;
    }
}
