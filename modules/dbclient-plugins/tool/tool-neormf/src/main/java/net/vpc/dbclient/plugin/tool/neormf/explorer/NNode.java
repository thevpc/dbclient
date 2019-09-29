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

package net.vpc.dbclient.plugin.tool.neormf.explorer;

import javax.swing.*;
import java.util.ArrayList;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 * @creationtime 21 avr. 2007 23:19:13
 */
public class NNode{

    public static enum Type{
        ROOT_TABLE,ROOT_DO,ROOT_BO, ROOT_BDLG,TABLE,DO,BO,BDLG, CONFIG_DO,CONFIG_BO,CONFIG_FIELD,CONFIG,FIELD
    }
    Type type;
    String name;
    Object value;
    Icon icon;
    NNode parent;
    ArrayList<NNode> children=new ArrayList<NNode>();


    public NNode(Type type, String name, Object value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }

    public void add(NNode node){
        children.add(node);
        node.parent=this;
    }

    public NNode getChild(int index){
        return children.get(index);
    }

    public int indexOf(NNode n){
        return children.indexOf(n);
    }

    public int size(){
        return children.size();
    }


    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public NNode getParent() {
        return parent;
    }
}
