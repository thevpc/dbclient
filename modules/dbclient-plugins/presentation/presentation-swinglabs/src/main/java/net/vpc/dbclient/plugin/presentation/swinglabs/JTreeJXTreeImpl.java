/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.vpc.dbclient.plugin.presentation.swinglabs;

import org.jdesktop.swingx.JXTree;
import net.vpc.prs.plugin.Implementation;
import net.vpc.dbclient.api.viewmanager.DBCTree;

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
