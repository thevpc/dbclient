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

package net.vpc.dbclient.api;

import net.vpc.dbclient.api.configmanager.DBCConfig;
import net.vpc.dbclient.api.pluginmanager.DBCFactory;
import net.vpc.log.LoggerProvider;

/**
 * @author vpc
 */
public interface DBClientContext extends LoggerProvider{

    public DBClientViewContext getView();

//    public TLog getLog();


    /**
     * Factory is the MOST Important Class in DBClient as it is responsible of instantiating
     * almost all objects. It implements the Factory Design Pattern.
     * Application May be overridden in Sessions by Session Factory.
     * Factory holds mapping between interfaces and Implementing Classes;
     * Thus to use a JTable, use instead JTableInterface by
     * <pre>
     * JTableInterface c=dbclient.getFactory().newInstance(JTableInterface.class,null);
     * JPanel p=new JPanel();
     * p.add(c.toComponent());
     * </pre>
     *
     * @return Application Factory
     */
    public DBCFactory getFactory();

    /**
     * DBCApplicationConfig is the Class responsible of storing and querying
     * DBClient configuration.
     *
     * @return ConfigManager instance
     */
    public DBCConfig getConfig();

}
