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

package net.thevpc.dbclient.plugin.presentation.swinglabs;

import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.error.ErrorInfo;
import net.thevpc.common.prs.plugin.Implementation;
import net.thevpc.dbclient.plugin.system.viewmanager.DBCMessageDialogManagerImpl;
import net.thevpc.common.swing.dialog.MessageDialogType;
import net.thevpc.common.swing.dialog.MessageDiscardContext;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.HashMap;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 3 dec. 2006 23:21:44
 */
@Implementation(priority = 10)
public class JXMessageDialogManager extends DBCMessageDialogManagerImpl {

    public JXMessageDialogManager() {
    }


    public ReturnType showMessage(Component parentComponent, String message, MessageDialogType type, String title, Throwable th, MessageDiscardContext context) {
        getDialogOwner().getView().hideSplashScreen();
        if(context!=null && context.isDiscarded()){
            return ReturnType.DISCARDED;
        }
        int optionPaneType=JOptionPane.ERROR_MESSAGE;
        int optionPaneButtons=JOptionPane.OK_CANCEL_OPTION;
        String optionPaneLongMessage="Some error occurs. see details...";
        String optionPaneTitle=title ;
        if (optionPaneTitle == null) {
            switch (type) {
                case ERROR:{
                    optionPaneTitle="Error";
                    optionPaneType=JOptionPane.ERROR_MESSAGE;
                    optionPaneButtons=JOptionPane.OK_CANCEL_OPTION;
                    optionPaneLongMessage="Some error occurs. see details...";
                    break;
                }
                case WARNING:{
                    optionPaneTitle="Warning";
                    optionPaneType=JOptionPane.WARNING_MESSAGE;
                    optionPaneButtons=JOptionPane.OK_OPTION;
                    optionPaneLongMessage="Some warnings. see details...";
                    break;
                }
            }
        }

        Component f=parentComponent;
        if(f!=null && !(f instanceof Window)){
            f= SwingUtilities.getAncestorOfClass(Window.class,parentComponent);
        }
        if(title==null || title.length()==0){
            title="Error";
        }
        if(message==null || message.length()==0 && th!=null){
            message=th.getMessage();
            if(message==null || message.length()==0){
                message=th.getClass().getSimpleName();
            }
        }
        Level level=null;
        switch (type){
            case ERROR:{
                level= Level.SEVERE;
                break;
            }
            case INFO:{
                level= Level.INFO;
                break;
            }
            case WARNING:{
                level= Level.WARNING;
                break;
            }
        }
        if(UIManager.get("ErrorPaneUI")==null){
            UIManager.put("ErrorPaneUI","org.jdesktop.swingx.plaf.basic.BasicErrorPaneUI");
        }
        if(th!=null){
            th.printStackTrace();
        }
        JXErrorPane.showDialog(parentComponent,new ErrorInfo(title,message,message,"category",th,level,new HashMap<String, String>()));
        return ReturnType.OK;
    }
}
