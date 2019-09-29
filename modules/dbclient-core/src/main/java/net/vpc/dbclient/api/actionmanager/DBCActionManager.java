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
package net.vpc.dbclient.api.actionmanager;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 2 janv. 2007 13:51:19
 */
public class DBCActionManager {

    private Map<String, DBClientAction> actions;
    private int index = 0;

    public DBCActionManager() {
    }

    public void registerAction(DBClientAction action) {
        if (actions == null) {
            actions = new HashMap<String, DBClientAction>();
        }
        String key = action.getKey();
        if (actions.containsKey(key)) {
            throw new RuntimeException("Action '" + key + "' already registred");
        }
        index++;
        action.setIndex(index);
        actions.put(key, action);
    }

    public DBClientAction getAction(String name) {
        return getActionByName(name);
    }

    public DBClientAction getActionByName(String name) {
        if (actions == null) {
            throw new NoSuchElementException(name);
        }
        DBClientAction action = actions.get(name);
        if (action == null) {
            throw new NoSuchElementException(name);
        }
        return action;
    }

    public DBClientAction[] getActions() {
        if (actions == null) {
            return null;
        }
        return actions.values().toArray(new DBClientAction[actions.size()]);
    }

    public void fillComponent(Component comp, DBCActionLocation locationId, DBCActionFilter filter) {
        DBClientActionTreeBuilder builder=new DBClientActionTreeBuilder();
        if (actions != null) {
            for (DBClientAction action : actions.values()) {
                builder.register(action, locationId);
            }
        }
        builder.fillComponent(comp,filter);
    }

}
