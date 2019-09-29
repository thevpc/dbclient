/**
 * 
====================================================================
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
 * 
====================================================================
 */

package net.vpc.dbclient.plugin.tool.neormf.actions;

import net.vpc.dbclient.api.actionmanager.DBCActionLocation;
import net.vpc.dbclient.api.actionmanager.DBCApplicationAction;
import net.vpc.dbclient.api.sql.objects.DBObject;
import org.vpc.neormf.commons.CommonsVersion;
import org.vpc.neormf.jbgen.JBGenVersion;
import org.vpc.neormf.wws.WWSVersion;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class NeormfAboutAction extends DBCApplicationAction {
    public NeormfAboutAction() {
        super("Action.NeormfAboutAction");
        addLocationPath(DBCActionLocation.MENUBAR, "/help");
    }

    public boolean shouldEnable(DBObject activeNode, DBObject[] selectedNodes) {
        return true;
    }

    public void actionPerformedImpl(ActionEvent e) throws Throwable {
        String html="<html><table border=0>" +
                "<tr><td>Neormf JBGen Version</td><td>"+JBGenVersion.getVersion()+" ("+JBGenVersion.getBuildDate()+")</td></tr>"+
                "<tr><td>Neormf Commons Version</td><td>"+ CommonsVersion.getVersion()+" ("+CommonsVersion.getBuildDate()+")</td></tr>"+
                "<tr><td>Neormf WWS Version</td><td>"+ WWSVersion.PRODUCT_VERSION+" ("+WWSVersion.PRODUCT_BUILD_DATE+")</td></tr>"+
                "</table></html>";
        JOptionPane.showMessageDialog(null, html,"About Neormf",JOptionPane.INFORMATION_MESSAGE);
    }

}