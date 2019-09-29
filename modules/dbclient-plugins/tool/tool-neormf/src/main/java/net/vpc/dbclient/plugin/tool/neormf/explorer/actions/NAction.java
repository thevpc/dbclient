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

package net.vpc.dbclient.plugin.tool.neormf.explorer.actions;

import java.util.logging.Logger;
import net.vpc.dbclient.plugin.tool.neormf.explorer.NeormfExplorer;

import javax.swing.*;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 21 avr. 2007 23:55:01
 */
public abstract class NAction extends AbstractAction {
    NeormfExplorer explorer;

    protected NAction(NeormfExplorer explorer) {
        this.explorer = explorer;
    }

    public void revalidate(){

    }
    
    protected NAction(String name, NeormfExplorer explorer) {
        super(explorer.pluginSession.getMessageSet().get(name));
        this.explorer = explorer;
    }

    protected NAction(String name, Icon icon, NeormfExplorer explorer) {
        super(name, icon);
        this.explorer = explorer;
    }

    public NeormfExplorer getExplorer() {
        return explorer;
    }
    public Logger getLogger(){
        return getExplorer().pluginSession.getSession().getLogger(getClass().getName());
    }
}
