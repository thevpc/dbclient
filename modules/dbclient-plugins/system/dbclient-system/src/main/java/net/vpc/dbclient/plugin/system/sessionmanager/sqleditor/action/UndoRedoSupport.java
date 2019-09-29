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

import javax.swing.undo.UndoManager;

public class UndoRedoSupport {
    protected UndoManager undoManager = new UndoManager();
    private UndoAction undoAction = new UndoAction();
    private RedoAction redoAction = new RedoAction();
    private UndoHandler undoableEditListener = new UndoHandler();

    public UndoRedoSupport() {
        undoAction.setSupport(this);
        redoAction.setSupport(this);
        undoableEditListener.setSupport(this);
    }

    public UndoManager getUndoManager() {
        return undoManager;
    }

    public UndoAction getUndoAction() {
        return undoAction;
    }

    public RedoAction getRedoAction() {
        return redoAction;
    }

    public UndoHandler getUndoableEditListener() {
        return undoableEditListener;
    }

    public void resetUndoManager() {
        undoManager.discardAllEdits();
        undoAction.update();
        redoAction.update();
    }

}
