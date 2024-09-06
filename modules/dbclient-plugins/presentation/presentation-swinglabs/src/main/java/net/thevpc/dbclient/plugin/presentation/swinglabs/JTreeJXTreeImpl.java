/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.thevpc.dbclient.plugin.presentation.swinglabs;

import org.jdesktop.swingx.JXTree;
import net.thevpc.common.prs.plugin.Implementation;
import net.thevpc.dbclient.api.viewmanager.DBCTree;

import java.awt.*;

/**
 *
 * @author vpc
 */
@Implementation(priority = 10)
public class JTreeJXTreeImpl extends JXTree implements DBCTree {
    public JTreeJXTreeImpl() {
    }
    
    public Component getComponent() {
        return this;
    }
}
